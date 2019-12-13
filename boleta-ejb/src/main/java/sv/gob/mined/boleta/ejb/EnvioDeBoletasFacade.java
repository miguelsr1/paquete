package sv.gob.mined.boleta.ejb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import sv.gob.mined.utils.jsf.JsfUtil;
import sv.gob.mined.utils.seguridad.Seguridad;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class EnvioDeBoletasFacade {

    private static final ResourceBundle RESOURCE_CUENTAS = ResourceBundle.getBundle("cuenta_office365");
    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("parametros");

    @EJB
    private EMailEJB eMailEJB;
    @EJB
    private BitacoraDeProcesoEJB bitacoraDeProcesoEJB;
    @EJB
    private PersistenciaFacade persistenciaFacade;

    @Asynchronous
    public void enviarBoletasDePago(String codDepa, String mesAnho) {
        String remitente = RESOURCE_CUENTAS.getString("usuario_".concat(codDepa));
        Session mailSession = getMailSession(remitente, RESOURCE_CUENTAS.getString("clave_".concat(codDepa)));

        String keyGenerado = codDepa.concat(mesAnho);
        String pathRoot = RESOURCE.getString("path_archivo");
        File carpeta = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator);
        Properties info = chargeEmailsProperties("emails0212");
        Boolean errorEnvioEmail = false;

        int boletasEnviadas = 0;
        int docenteNoEncontrados = 0;
        int correosNoEnviados = 0;

        Long idCodigoGenerado = persistenciaFacade.getPkCodigoGeneradoByCodDepaAndMesAnho(codDepa, mesAnho);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb = sb.append("Se han enviado boletas de pago del departamento ").append(JsfUtil.getNombreDepartamentoByCodigo(codDepa)).append(".").append("<br/>")
                .append("Hora de inicio: ").append(sdf.format(new Date())).append("<br/>");

        for (File boleta : carpeta.listFiles()) {
            if (boleta.isFile() && boleta.getName().toUpperCase().contains("PDF")) {
                String nip = boleta.getName().toUpperCase().replace(".PDF", "");

                if (info.containsKey(nip)) {

                    String destinatario = info.getProperty(nip);
                    String keyGeneradoNip = Seguridad.encriptar(keyGenerado.concat(nip));
                    String urlDescarga = "<a href='http://aplicaciones.mined.gob.sv/boleta-web/DescargarBoleta?codigoGenerado=" + keyGeneradoNip + "'>Boleta de Pago</a>";
                    String mensajeDelCorreo;

                    mensajeDelCorreo = MessageFormat.format(RESOURCE.getString("mail.message"), JsfUtil.getNombreMesYAnhoByParam(mesAnho), urlDescarga);
                    //System.out.println(mensajeDelCorreo);

                    errorEnvioEmail = eMailEJB.enviarUrlBoleta(remitente, destinatario, mensajeDelCorreo, mailSession, "Boleta de Pago - " + JsfUtil.getNombreMesYAnhoByParam(mesAnho));

                    if (!errorEnvioEmail) {
                        bitacoraDeProcesoEJB.correoNoEnviado(codDepa, mesAnho, pathRoot, nip);

                        try {
                            //mover NO enviado
                            File folderProcesado = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator + "no_enviado" + File.separator);
                            if (!folderProcesado.exists()) {
                                folderProcesado.mkdir();
                            }
                            Path temp = Files.move(Paths.get(boleta.getAbsolutePath()),
                                    Paths.get(folderProcesado.getAbsolutePath() + File.separator + boleta.getName()), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException ex) {
                            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        correosNoEnviados++;
                    } else {
                        persistenciaFacade.guardarCodigoGeneradoPorNip(idCodigoGenerado, nip, keyGeneradoNip);
                        try {
                            //mover archivo procesado
                            File folderProcesado = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator + "procesado" + File.separator);
                            if (!folderProcesado.exists()) {
                                folderProcesado.mkdir();
                            }
                            Path temp = Files.move(Paths.get(boleta.getAbsolutePath()),
                                    Paths.get(folderProcesado.getAbsolutePath() + File.separator + boleta.getName()), StandardCopyOption.REPLACE_EXISTING);
                            boletasEnviadas++;
                        } catch (IOException ex) {
                            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    bitacoraDeProcesoEJB.escribirEmpleadoNoEncontrado(codDepa, mesAnho, pathRoot, nip);

                    try {
                        //mover NO archivo procesado
                        File folderProcesado = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator + "no_encontrado" + File.separator);
                        if (!folderProcesado.exists()) {
                            folderProcesado.mkdir();
                        }
                        Path temp = Files.move(Paths.get(boleta.getAbsolutePath()),
                                Paths.get(folderProcesado.getAbsolutePath() + File.separator + boleta.getName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    docenteNoEncontrados++;
                    Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.WARNING, "No existe este empleado: {0}", nip);
                }
            }
        }

        sb = sb.append("Hora de fin: ").append(sdf.format(new Date())).append("<br/>");
        sb = sb.append("Número de boletas enviadas: ").append(boletasEnviadas).append("<br/>");
        sb = sb.append("Número de docente no encontrados: ").append(docenteNoEncontrados).append("<br/>");
        sb = sb.append("Número de correos no enviados debido a un error: ").append(correosNoEnviados).append("<br/>");

        eMailEJB.enviarMailDeConfirmacion("Envio de boletas de pago", sb.toString(), remitente, mailSession);
    }
    
    
    @Asynchronous
    public void enviarBoletasDePagoPdf(String codDepa, String mesAnho) {
        String remitente = RESOURCE_CUENTAS.getString("usuario_".concat(codDepa));
        Session mailSession = getMailSession(remitente, RESOURCE_CUENTAS.getString("clave_".concat(codDepa)));

        String keyGenerado = codDepa.concat(mesAnho);
        String pathRoot = RESOURCE.getString("path_archivo");
        File carpeta = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator);
        Properties info = chargeEmailsProperties("emails0212");
        Boolean errorEnvioEmail = false;

        int boletasEnviadas = 0;
        int docenteNoEncontrados = 0;
        int correosNoEnviados = 0;

        Long idCodigoGenerado = persistenciaFacade.getPkCodigoGeneradoByCodDepaAndMesAnho(codDepa, mesAnho);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb = sb.append("Se han enviado boletas de pago del departamento ").append(JsfUtil.getNombreDepartamentoByCodigo(codDepa)).append(".").append("<br/>")
                .append("Hora de inicio: ").append(sdf.format(new Date())).append("<br/>");

        for (File boleta : carpeta.listFiles()) {
            if (boleta.isFile() && boleta.getName().toUpperCase().contains("PDF")) {
                String nip = boleta.getName().toUpperCase().replace(".PDF", "");

                if (info.containsKey(nip)) {

                    String destinatario = info.getProperty(nip);
                    String keyGeneradoNip = Seguridad.encriptar(keyGenerado.concat(nip));
                    String mensajeDelCorreo;

                    mensajeDelCorreo = MessageFormat.format(RESOURCE.getString("mail.messagePdf"), JsfUtil.getNombreMesYAnhoByParam(mesAnho));
                    //System.out.println(mensajeDelCorreo);

                    //errorEnvioEmail = eMailEJB.enviarUrlBoleta(remitente, destinatario, mensajeDelCorreo, mailSession, "Boleta de Pago - " + JsfUtil.getNombreMesYAnhoByParam(mesAnho));
                    errorEnvioEmail = eMailEJB.enviarMail(remitente, destinatario, mensajeDelCorreo, boleta, mailSession);

                    if (!errorEnvioEmail) {
                        bitacoraDeProcesoEJB.correoNoEnviado(codDepa, mesAnho, pathRoot, nip);

                        try {
                            //mover NO enviado
                            File folderProcesado = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator + "no_enviado" + File.separator);
                            if (!folderProcesado.exists()) {
                                folderProcesado.mkdir();
                            }
                            Path temp = Files.move(Paths.get(boleta.getAbsolutePath()),
                                    Paths.get(folderProcesado.getAbsolutePath() + File.separator + boleta.getName()), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException ex) {
                            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        correosNoEnviados++;
                    } else {
                        persistenciaFacade.guardarCodigoGeneradoPorNip(idCodigoGenerado, nip, keyGeneradoNip);
                        try {
                            //mover archivo procesado
                            File folderProcesado = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator + "procesado" + File.separator);
                            if (!folderProcesado.exists()) {
                                folderProcesado.mkdir();
                            }
                            Path temp = Files.move(Paths.get(boleta.getAbsolutePath()),
                                    Paths.get(folderProcesado.getAbsolutePath() + File.separator + boleta.getName()), StandardCopyOption.REPLACE_EXISTING);
                            boletasEnviadas++;
                        } catch (IOException ex) {
                            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    bitacoraDeProcesoEJB.escribirEmpleadoNoEncontrado(codDepa, mesAnho, pathRoot, nip);

                    try {
                        //mover NO archivo procesado
                        File folderProcesado = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator + "no_encontrado" + File.separator);
                        if (!folderProcesado.exists()) {
                            folderProcesado.mkdir();
                        }
                        Path temp = Files.move(Paths.get(boleta.getAbsolutePath()),
                                Paths.get(folderProcesado.getAbsolutePath() + File.separator + boleta.getName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    docenteNoEncontrados++;
                    Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.WARNING, "No existe este empleado: {0}", nip);
                }
            }
        }

        sb = sb.append("Hora de fin: ").append(sdf.format(new Date())).append("<br/>");
        sb = sb.append("Número de boletas enviadas: ").append(boletasEnviadas).append("<br/>");
        sb = sb.append("Número de docente no encontrados: ").append(docenteNoEncontrados).append("<br/>");
        sb = sb.append("Número de correos no enviados debido a un error: ").append(correosNoEnviados).append("<br/>");

        eMailEJB.enviarMailDeConfirmacion("Envio de boletas de pago", sb.toString(), remitente, mailSession);
    }

    public Session getMailSession(String usuario, String clave) {
        Session mailSession;

        Properties configEmail = new Properties();

        configEmail.put("mail.smtp.auth", "true");
        configEmail.put("mail.smtp.starttls.enable", "true");

        configEmail.put("mail.smtp.host", "smtp.office365.com");
        configEmail.put("mail.smtp.port", "587");

        configEmail.put("mail.user", usuario);
        configEmail.put("mail.user.pass", clave);
        configEmail.put("mail.from", usuario);

        mailSession = Session.getInstance(configEmail, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });

        return mailSession;
    }

    private Properties chargeEmailsProperties(String nombre) {
        Properties info = null;
        try {
            info = new Properties();
            try (InputStream input = LeerBoletasEJB.class.getClassLoader().getResourceAsStream(nombre + ".properties")) {
                info.load(input);
            }

        } catch (IOException ex) {
            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;
    }
}
