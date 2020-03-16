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
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Session;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import sv.gob.mined.boleta.model.CodigoGenerado;
import sv.gob.mined.boleta.model.CorreoDocente;
import sv.gob.mined.utils.jsf.JsfUtil;

@Stateless
@LocalBean
public class LeerBoletasEJB {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("parametros");

    @EJB
    private EMailEJB eMailEJB;
    @EJB
    private PersistenciaFacade persistenciaFacade;

    //@Asynchronous
    /*public void leerArchivosPendientes(Session mailSession, String codDepa, String usuario) {
        Properties info = chargeEmailsProperties("emails0212");

        File carpetaRoot = new File(RESOURCE_BUNDLE.getString("path_archivo"));
        for (File carpetaDepa : carpetaRoot.listFiles()) {
            if (carpetaDepa.isDirectory() && carpetaDepa.getName().equals(codDepa)) {
                for (File carpetaPorFecha : carpetaDepa.listFiles()) {
                    if (carpetaPorFecha.isDirectory()) {
                        for (File archivoBoleta : carpetaPorFecha.listFiles()) {
                            if (archivoBoleta.isFile() && (archivoBoleta.getName().toUpperCase().contains("PDF"))) {
                                splitPages(archivoBoleta, codDepa, mailSession, info, usuario, "12_2019", RESOURCE_BUNDLE.getString("path_archivo"));

                                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, archivoBoleta.getName());
                            }
                        }
                    }
                }
            }
        }
    }*/

 /*public void leerArchivosPendientesByUltimoProcesado(Session mailSession, String codDepa, String usuario,
            String codigo, String mesAnho, String nombreArchivo) {
        Properties info = chargeEmailsProperties("emails0212");

        File carpetaRoot = new File(RESOURCE_BUNDLE.getString("path_archivo"));
        for (File carpetaDepa : carpetaRoot.listFiles()) {
            if (carpetaDepa.isDirectory() && carpetaDepa.getName().contains(codDepa)) {
                for (File carpetaPorFecha : carpetaDepa.listFiles()) {
                    if (carpetaPorFecha.isDirectory()) {
                        for (File archivoBoleta : carpetaPorFecha.listFiles()) {
                            if (archivoBoleta.isFile() && (archivoBoleta.getName().toUpperCase().contains("PDF")) && archivoBoleta.getName().equals(nombreArchivo)) {
                                splitPagesByCodigo(archivoBoleta, codDepa, mailSession, info, usuario, codigo, mesAnho, RESOURCE_BUNDLE.getString("path_archivo"));

                                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, archivoBoleta.getName());
                            }
                        }
                    }
                }
            }
        }
    }*/
    public void splitPagesByCodigo(File file, String codDepa, Session mailSession, Properties info, String usuario, String ultimoCodigoProcesado, String mesAnho, String path) {
        StringBuilder sb = new StringBuilder("");
        PDDocument document = null;
        Boolean ultimo = false;
        Boolean continuar = false;
        int interacion;
        int siguienteInteracion = 0;
        int contadorDeCortes;

        int boletasEnviadas = 0;
        int docenteNoEncontrados = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        try {
            sb = sb.append("Se han enviado boletas de pago del archivo : ").append(file.getName()).append("<br/>")
                    .append("Hora de inicio: ").append(sdf.format(new Date())).append("<br/>");
            document = PDDocument.load(file);

            Splitter splitter = new Splitter();
            splitter.setStartPage(1);

            if (document.getNumberOfPages() > 1000) {
                siguienteInteracion = 1000;
                splitter.setEndPage(siguienteInteracion);

                interacion = ((int) (document.getNumberOfPages() / 1000)) + 1;
            } else {
                splitter.setEndPage(document.getNumberOfPages());
                interacion = 1;
            }

            do {
                interacion--;
                for (PDDocument pd : splitter.split(document)) {
                    String code = getCodigoDocente(pd, "         )", 0, 15).substring(8);

                    if (!ultimo) {
                        ultimo = code.equals(ultimoCodigoProcesado);
                    }

                    if (ultimo && continuar) {
                        if (info.containsKey(code)) {
                            String email = info.getProperty(code);
                            //Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, email);
                            //eMailEJB.enviarMail(code, email, usuario, RESOURCE_BUNDLE.getString("mail.message"), pd, mailSession);
                            boletasEnviadas++;
                        } else {
                            //bitacoraDeProcesoEJB.escribirEmpleadoNoEncontrado(codDepa, mesAnho, path, file.getName().concat("::").concat(code));
                            docenteNoEncontrados++;
                            //Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.WARNING, "No existe este empleado: {0}", code);
                        }
                    }

                    if (ultimo && !continuar) {
                        continuar = true;
                    }
                    pd.close();
                }
                if (interacion > 0) {
                    contadorDeCortes = siguienteInteracion + 1;
                    siguienteInteracion = siguienteInteracion + 1000;
                    splitter = new Splitter();
                    splitter.setStartPage(contadorDeCortes);
                    if (document.getNumberOfPages() > siguienteInteracion) {
                        splitter.setEndPage(siguienteInteracion);
                    } else {
                        splitter.setEndPage(document.getNumberOfPages());
                    }
                }
            } while (interacion != 0);

            document.close();

            sb = sb.append("Hora de fin: ").append(sdf.format(new Date())).append("<br/>");
            sb = sb.append("Número de boletas enviadas: ").append(boletasEnviadas).append("<br/>");
            sb = sb.append("Número de docente no encontrados: ").append(docenteNoEncontrados).append("<br/>");

            eMailEJB.enviarMailDeConfirmacion("Envio de boletas de pago", sb.toString(), usuario, mailSession);

            File folderProcesado = new File(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + codDepa + File.separator + mesAnho + File.separator + "procesado" + File.separator);
            if (!folderProcesado.exists()) {
                folderProcesado.mkdir();
            }
            //mover archivo a carpeta de procesados
            Path temp = Files.copy(Paths.get(file.getAbsolutePath()),
                    Paths.get(folderProcesado.getAbsolutePath() + File.separator + file.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {

            sb = new StringBuilder("");
            sb = sb.append("Ha ocurrido un error en el envio de las boletas de pago del archivo : ").append(file.getName()).append("<br/>")
                    .append("Se ha enviado una notificación del error al administrador.");

            eMailEJB.enviarMailDeError("Error en envio de boletas de pago", sb.toString(), usuario, mailSession);
            try {
                if (document != null) {
                    document.close();
                }

            } catch (IOException ex1) {
                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex1);
            }

            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void splitPages(File file, String codDepa, Session mailSession, Properties info, String usuario, String mesAnho, String path) {
        StringBuilder sb = new StringBuilder("");
        PDDocument document = null;
        int interacion;
        int contadorDeCortes;
        int siguienteInteracion = 0;

        int boletasEnviadas = 0;
        int docenteNoEncontrados = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, sdf.format(new Date()));
        try {
            sb = sb.append("Se han enviado boletas de pago del archivo : ").append(file.getName()).append("<br/>")
                    .append("Hora de inicio: ").append(sdf.format(new Date())).append("<br/>");
            document = PDDocument.load(file);

            Splitter splitter = new Splitter();
            splitter.setStartPage(1);

            if (document.getNumberOfPages() > 1000) {
                siguienteInteracion = 1000;
                splitter.setEndPage(siguienteInteracion);

                interacion = ((int) (document.getNumberOfPages() / 1000)) + 1;
            } else {
                splitter.setEndPage(document.getNumberOfPages());
                interacion = 1;
            }

            do {
                interacion--;
                for (PDDocument pd : splitter.split(document)) {
                    String code = getCodigoDocente(pd, "         )", 0, 15).substring(8);

                    if (info.containsKey(code)) {
                        String email = info.getProperty(code);
                        Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, "{0} - {1}", new Object[]{email, code});
                        //eMailEJB.enviarMail(code, email, usuario, RESOURCE_BUNDLE.getString("mail.message"), pd, mailSession);
                        boletasEnviadas++;
                    } else {
                        //bitacoraDeProcesoEJB.escribirEmpleadoNoEncontrado(codDepa, mesAnho, path, file.getName().concat("::").concat(code));
                        docenteNoEncontrados++;
                        //Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.WARNING, "No existe este empleado: {0}", code);
                    }

                    pd.close();
                }
                if (interacion > 0) {
                    contadorDeCortes = siguienteInteracion + 1;
                    siguienteInteracion = siguienteInteracion + 1000;
                    splitter = new Splitter();
                    splitter.setStartPage(contadorDeCortes);
                    if (document.getNumberOfPages() > siguienteInteracion) {
                        splitter.setEndPage(siguienteInteracion);
                    } else {
                        splitter.setEndPage(document.getNumberOfPages());
                    }
                }
            } while (interacion != 0);

            document.close();

            sb = sb.append("Hora de fin: ").append(sdf.format(new Date())).append("<br/>");
            sb = sb.append("Número de boletas enviadas: ").append(boletasEnviadas).append("<br/>");
            sb = sb.append("Número de docente no encontrados: ").append(docenteNoEncontrados).append("<br/>");

            eMailEJB.enviarMailDeConfirmacion("Envio de boletas de pago", sb.toString(), usuario, mailSession);

            File folderProcesado = new File(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + codDepa + File.separator + mesAnho + File.separator + "procesado" + File.separator);
            if (!folderProcesado.exists()) {
                folderProcesado.mkdir();
            }
            //mover archivo a carpeta de procesados
            Path temp = Files.move(Paths.get(file.getAbsolutePath()),
                    Paths.get(folderProcesado.getAbsolutePath() + File.separator + file.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {

            sb = new StringBuilder("");
            sb = sb.append("Ha ocurrido un error en el envio de las boletas de pago del archivo : ").append(file.getName()).append("<br/>")
                    .append("Se ha enviado una notificación del error al administrador.");

            eMailEJB.enviarMailDeError("Error en envio de boletas de pago", sb.toString(), usuario, mailSession);
            try {
                if (document != null) {
                    document.close();
                }

            } catch (IOException ex1) {
                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex1);
            }

            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private String getCodigoDocente(PDDocument pDDocument, String strEndIdentifier, int offSet, int back) {
        String returnString;
        try {
            PDFTextStripper tStripper = new PDFTextStripper();
            tStripper.setStartPage(1);
            tStripper.setEndPage(1);
            String pdfFileInText = tStripper.getText(pDDocument);
            String strEnd = strEndIdentifier;
            int endInddex = pdfFileInText.indexOf(strEnd) + offSet;
            int startInddex = endInddex - back;
            returnString = pdfFileInText.substring(startInddex, endInddex); // + strEnd;
        } catch (IOException ex) {
            returnString = "No ParaGraph Found";
        }
        return returnString;
    }

    @Asynchronous
    public void enviarUnSoloCorreo(String codDepa, String mesAnho,
            Session mailSession, String usuario,
            String mensajeCorreo, String tituloCorreo) {

        String nombreMesAnho = getNombreMes(mesAnho.split("_")[0]).concat(" de ").concat(mesAnho.split("_")[1]);
        String pathRoot = RESOURCE_BUNDLE.getString("path_archivo");
        File carpeta = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        Boolean errorEnvioEmail;
        String email;

        if (mensajeCorreo != null && !mensajeCorreo.isEmpty()) {
            
        }else{
            mensajeCorreo = MessageFormat.format(RESOURCE_BUNDLE.getString("mail.message"), nombreMesAnho);
            tituloCorreo = "Boleta de Pago de " + nombreMesAnho;
        }

        List<CorreoDocente> lstCorreo = persistenciaFacade.getLstCorreoDocentes();

        File folderError = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator + "errores" + File.separator);
        if (!folderError.exists()) {
            folderError.mkdir();
        }
        File folderProcesado = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator + "procesado" + File.separator);
        if (!folderProcesado.exists()) {
            folderProcesado.mkdir();
        }
        File folderNoEncontrado = new File(pathRoot + File.separator + codDepa + File.separator + mesAnho + File.separator + "no_encontrado" + File.separator);
        if (!folderNoEncontrado.exists()) {
            folderNoEncontrado.mkdir();
        }

        persistenciaFacade.getPkCodigoGeneradoByCodDepaAndMesAnho(codDepa, mesAnho);

        for (File boleta : carpeta.listFiles()) {
            if (boleta.isFile() && boleta.getName().toUpperCase().contains("PDF")) {
                email = getCorreoByNip(boleta.getName().toUpperCase().replace(".PDF", ""), lstCorreo);

                if (email != null) {
                    errorEnvioEmail = eMailEJB.enviarMail(email, usuario, tituloCorreo, mensajeCorreo, nombreMesAnho, boleta, mailSession);

                    Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, "{0} - {1}", new Object[]{email, boleta.getName().toUpperCase().replace(".PDF", "")});
                    if (!errorEnvioEmail) {
                        //bitacoraDeProcesoEJB.correoNoEnviadoPorErrorGenerado(codDepa, mesAnho, pathRoot, boleta.getName().toUpperCase().replace(".PDF", ""));
                        moverBoletaAOtraUbicacion(boleta, folderError);
                    } else {
                        //mover archivo procesado
                        moverBoletaAOtraUbicacion(boleta, folderProcesado);
                    }
                } else {
                    //bitacoraDeProcesoEJB.escribirEmpleadoNoEncontrado(codDepa, mesAnho, pathRoot, boleta.getName().toUpperCase().replace(".PDF", ""));
                    moverBoletaAOtraUbicacion(boleta, folderNoEncontrado);
                    Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.WARNING, "No existe este empleado: {0}", boleta.getName().toUpperCase().replace(".PDF", ""));
                }
            }
        }

        CodigoGenerado codigoGenerado = persistenciaFacade.registrarFinDeProcesoDeEnvio(pathRoot, codDepa, mesAnho);

        StringBuilder sb = new StringBuilder();
        sb = sb.append("Se han enviado boletas de pago del departamento ").append(JsfUtil.getNombreDepartamentoByCodigo(codDepa)).append(".").append("<br/>")
                .append("Hora de inicio: ").append(sdf.format(codigoGenerado.getFechaInicio())).append("<br/>");

        sb = sb.append("Hora de fin: ").append(sdf.format(codigoGenerado.getFechaFin())).append("<br/>");
        sb = sb.append("Número de boletas enviadas: ").append(codigoGenerado.getEnviado()).append("<br/>");
        sb = sb.append("Número de docente no encontrados: ").append(codigoGenerado.getSinCorreo()).append("<br/>");
        sb = sb.append("Número de correos no enviados debido a un error: ").append(codigoGenerado.getError()).append("<br/>");

        eMailEJB.enviarMailDeConfirmacion("Envio de boletas de pago", sb.toString(), usuario, mailSession);
    }

    private String getCorreoByNip(String nip, List<CorreoDocente> lstCorreo) {
        Optional<CorreoDocente> correoDoc = lstCorreo.stream().parallel().
                filter(cDoc -> cDoc.getNip().equals(nip)).findAny();
        if (correoDoc.isPresent()) {
            return correoDoc.get().getCorreoElectronico();
        } else {
            return null;
        }
    }

    private String getNombreMes(String mes) {
        switch (mes) {
            case "01":
                return "Enero";
            case "02":
                return "Febrero";
            case "03":
                return "Marzo";
            case "04":
                return "Abril";
            case "05":
                return "Mayo";
            case "06":
                return "Junio";
            case "07":
                return "Julio";
            case "08":
                return "Agosto";
            case "09":
                return "Septiembre";
            case "10":
                return "Octubre";
            case "11":
                return "Noviembre";
            default:
                return "Diciembre";
        }

    }

    private void moverBoletaAOtraUbicacion(File boleta, File folder) {
        try {
            Path temp = Files.move(Paths.get(boleta.getAbsolutePath()),
                    Paths.get(folder.getAbsolutePath() + File.separator + boleta.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
