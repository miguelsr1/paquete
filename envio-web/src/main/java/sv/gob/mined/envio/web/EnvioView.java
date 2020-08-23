/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.envio.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import sv.gob.mined.envio.facade.EMailFacade;
import sv.gob.mined.envio.facade.RegistrosFacade;
import sv.gob.mined.envio.model.DetalleEnvio;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author MISanchez
 */
@ManagedBean
@ViewScoped
public class EnvioView {

    private BigDecimal idEnvio = BigDecimal.ZERO;
    private Boolean showBarProgress = false;
    private Boolean correoValido = false;
    private Boolean showUploadFile = true;
    private Integer progreso;
    private Integer totalRegistros = 0;
    private Integer totalEnviados = 0;
    private String correoRemitente;
    private String remitente;
    private String password;

    private String dominio;

    private String titulo;
    private String mensaje;
    private String pathArchivo;

    private String idDominioCorreo = "2";

    private String port;
    private String server;

    private UploadedFile file;

    private Transport transport;
    private Session mailSession;

    @EJB
    private RegistrosFacade registrosFacade;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("parametros");

    public EnvioView() {
    }

    @PreDestroy
    public void destroy() {
        if (transport.isConnected()) {
            try {
                transport.close();
            } catch (MessagingException ex) {
                Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public Boolean getShowBarProgress() {
        return showBarProgress;
    }

    public Integer getProgreso() {
        progreso = updateProgreso();
        return progreso;
    }

    public void setProgreso(Integer progreso) {
        this.progreso = progreso;
    }

    public String getCorreoRemitente() {
        return correoRemitente;
    }

    public void setCorreoRemitente(String correoRemitente) {
        this.correoRemitente = correoRemitente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getIdDominioCorreo() {
        return idDominioCorreo;
    }

    public void setIdDominioCorreo(String idDominioCorreo) {
        if (idDominioCorreo != null) {
            this.idDominioCorreo = idDominioCorreo;
        }
    }

    public Boolean getShowUploadFile() {
        return showUploadFile;
    }

    public Boolean getCorreoValido() {
        return correoValido;
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        file = event.getFile();

        pathArchivo = RESOURCE_BUNDLE.getString("path_archivo") + File.separator + file.getFileName();
        Path folder = Paths.get(pathArchivo);
        Path arc;

        if (folder.toFile().exists()) {
            arc = folder;
        } else {
            arc = Files.createFile(folder);
        }

        try (InputStream input = file.getInputStream()) {
            if (validarArchivo(input)) {
                pathArchivo = folder.toString();
                Files.copy(file.getInputStream(), arc, StandardCopyOption.REPLACE_EXISTING);
                guardarRegistros();
                showUploadFile = false;
            } else {
                JsfUtil.mensajeError("El archivo cargado no contiene el formato requerido");
                showUploadFile = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRemitente() {
        return remitente;
    }

    public void validarFormulario(){
        String error = "";

        if (correoRemitente == null || correoRemitente.trim().isEmpty()) {
            error += "Debe de ingresar un correo remitente.<br/>";
        }
        if (idDominioCorreo.equals("1")) {
            error += "Debe de ingresar el dominio de la cuenta de correo a utilizar.<br/>";
        }
        if (password == null || password.trim().isEmpty()) {
            error += "Debe de ingresar una clave de acceso de la cuenta de correo a utilizar.<br/>";
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            error += "Debe de ingresar un Titulo del Mensaje.<br/>";
        }
        if (mensaje == null || mensaje.trim().isEmpty()) {
            error += "Debe de ingresar el Mensaje a enviar.<br/>";
        } else if (!mensaje.contains(":DOCENTE:")) {
            error += "El mensaje no contiene la palabra comodin <b>:DOCENTE:</b><br/>";
        }
        if (file == null) {
            error += "Debe de seleccionar un archivo con la lista de correos a enviar.<br/>";
        }
        if (error.isEmpty()) {
            enviarCorreos();
            PrimeFaces.current().executeScript("PF('pbAjax').start();PF('dlgBar').show();");
        } else {
            JsfUtil.mensajeError("<br/>" + error);
        }
    }

    public void enviarCorreos() {
        try {
            List<DetalleEnvio> lstDetalle = registrosFacade.findDetalleEnvio(idEnvio);
            totalRegistros = lstDetalle.size();

            //transport = mailSession.getTransport("smtp");
            //transport.connect(server, remitente, password);
            if (transport.isConnected()) {
                transport.connect();
            }

            try {
                lstDetalle.forEach((detalleEnvio) -> {
                    try {
                        totalEnviados += 1;

                        String msjTemp = mensaje.replace(":DOCENTE:", detalleEnvio.getNombreDestinatario().
                                concat(" - ").
                                concat(detalleEnvio.getNip() == null ? "" : detalleEnvio.getNip()));

                        MimeMessage message = new MimeMessage(mailSession);
                        Address from = new InternetAddress(remitente);
                        message.setFrom(from);

                        InternetAddress[] address = {new InternetAddress(detalleEnvio.getCorreoDestinatario())};
                        message.setRecipients(Message.RecipientType.TO, address);

                        BodyPart messageBodyPart1 = new MimeBodyPart();

                        messageBodyPart1.setContent(msjTemp, "text/html; charset=utf-8");

                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(messageBodyPart1);

                        message.setContent(multipart);
                        message.setSubject(titulo, "UTF-8");

                        message.saveChanges();

                        transport.sendMessage(message, address);

                    } catch (AddressException ex) {
                        Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (MessagingException ex) {
                        Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
            } catch (Exception e) {
                e.printStackTrace();
                JsfUtil.mensajeError("Ah ocurrido un error en el envio de correos.");
            }

            transport.close();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Boolean validarArchivo(InputStream input) throws IOException {
        Workbook wb = WorkbookFactory.create(input);
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cellNip = row.getCell(0);
        Cell cellNombre = row.getCell(1);
        Cell cellCorreo = row.getCell(2);

        try {
            return cellNip.getStringCellValue().toUpperCase().equals("NIP")
                    && cellNombre.getStringCellValue().toUpperCase().equals("NOMBRE")
                    && cellCorreo.getStringCellValue().toUpperCase().equals("CORREO");
        } catch (Exception e) {
            return false;
        }

    }

    public void upload() throws FileNotFoundException, IOException {
        if (file != null) {
            Path folder = Paths.get(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + file.getFileName());

            Files.createFile(folder);
        }
    }

    private Session getMailSessionOffice() {
        if (mailSession == null) {
            Properties configEmail = new Properties();

            configEmail.put("mail.smtp.auth", "true");
            configEmail.put("mail.smtp.starttls.enable", "true");

            configEmail.put("mail.smtp.host", "smtp.office365.com");
            configEmail.put("mail.smtp.port", "587");

            configEmail.put("mail.user", remitente);
            configEmail.put("mail.user.pass", password);
            configEmail.put("mail.from", remitente);

            mailSession = Session.getInstance(configEmail, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });
        }

        return mailSession;
    }

    private Session getMailSessionMined() {
        if (mailSession == null) {
            Properties configEmail = new Properties();

            configEmail.put("mail.smtp.auth", "true");
            configEmail.put("mail.smtp.starttls.enable", "true");

            configEmail.put("mail.smtp.host", "svr2k13mail01.mined.gob.sv");
            configEmail.put("mail.smtp.port", "2525");

            configEmail.put("mail.user", remitente);
            configEmail.put("mail.user.pass", password);
            configEmail.put("mail.from", remitente);

            mailSession = Session.getInstance(configEmail, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });
        }

        return mailSession;
    }

    private Integer updateProgreso() {
        if (progreso == null) {
            return 0;
        }
        try {
            return (int) ((totalEnviados * 100) / totalRegistros);
        } catch (Exception e) {
            return 0;
        }
    }

    public void validarCrendecialesDelCorreo() {
        try {
            if (correoRemitente != null && password != null) {
                if (idDominioCorreo.equals("1")) {
                    remitente = correoRemitente.concat("@").concat("mined.gob.sv");
                    port = "2525";
                    server = "svr2k13mail01.mined.gob.sv";
                } else {
                    remitente = correoRemitente.concat("@").concat("admin.mined.edu.sv");
                    port = "587";
                    server = "smtp.office365.com";
                }

                mailSession = getMailSessionOffice();

                transport = mailSession.getTransport("smtp");
                transport.connect("smtp.office365.com", Integer.parseInt(port), remitente, password);

                correoValido = true;
            }
        } catch (NoSuchProviderException ex) {
            JsfUtil.mensajeError("Error en el usuario o  clave de acceso.");
            correoValido = false;
        } catch (MessagingException ex) {
            JsfUtil.mensajeError("Error en el usuario o  clave de acceso.");
            correoValido = false;
        }
    }

    private void guardarRegistros() throws FileNotFoundException, IOException {
        Boolean primerRegistro = true;
        File fTmp = new File(pathArchivo);
        InputStream input = new FileInputStream(fTmp);
        DecimalFormat df = new DecimalFormat("#0");

        String nip;
        String nombre;
        String correo;

        remitente = correoRemitente.concat("@").concat(idDominioCorreo.equals("1") ? "mined.gob.sv" : "admin.mined.edu.sv");

        Workbook wb = WorkbookFactory.create(input);

        Sheet sheet = wb.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (row.getRowNum() != 0) {
                if (row.getCell(0) != null) {
                    switch (row.getCell(0).getCellType()) {
                        case STRING:
                            nip = row.getCell(0).getStringCellValue();
                            break;
                        case NUMERIC:
                            nip = String.valueOf(df.format(row.getCell(0).getNumericCellValue()));
                            break;
                        default:
                            nip = "";
                            break;
                    }
                } else {
                    nip = "";
                }

                if (row.getCell(1) != null && row.getCell(2) != null) {
                    nombre = row.getCell(1).getStringCellValue();
                    correo = row.getCell(2).getStringCellValue();

                    //Crear registro maestro en la base
                    if (primerRegistro) {
                        idEnvio = registrosFacade.guardarEnvio(remitente, titulo, mensaje, pathArchivo);
                        primerRegistro = false;
                    }
                    registrosFacade.guardarDetalleEnviado(nip, nombre, correo, idEnvio, false);
                }
            }
        }
    }

    public void ocultarBarProgress() {
        showBarProgress = false;
    }
}
