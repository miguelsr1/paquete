/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.envio.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.servlet.ServletContext;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import sv.gob.mined.envio.facade.ProcesoFacade;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author MISanchez
 */
@ManagedBean
@SessionScoped
public class EnvioView {

    private Boolean correoValido = false;
    private Boolean showUploadFile = true;

    private BigDecimal idEnvio;
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

    @Inject
    private ProcesoFacade procesoFacade;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("parametros");

    public EnvioView() {
    }

    @PreDestroy
    public void destroy() {
        if (!transport.isConnected()) {
            try {
                transport.close();
            } catch (MessagingException ex) {
                Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">
    public BigDecimal getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(BigDecimal idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getRemitente() {
        return remitente;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
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
    // </editor-fold>

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        file = event.getFile();

        try {
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
                    showUploadFile = false;
                } else {
                    JsfUtil.mensajeError("El archivo cargado no contiene el formato requerido");
                    showUploadFile = true;
                }

                input.close();
            } catch (IOException ex) {
                Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
                JsfUtil.mensajeError("Ah ocurrido un error en la carga del archivo, por favor dar aviso al adminstrado de la página");
            }
        } catch (IOException ex) {
            Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtil.mensajeError("Ah ocurrido un error en la carga del archivo, por favor dar aviso al adminstrado de la página");
        }
    }

    public void validarFormulario() {
        String error = "";

        if (correoRemitente == null || correoRemitente.trim().isEmpty()) {
            error += "Debe de ingresar un correo remitente.<br/>";
        }
        if (idDominioCorreo == null) {
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

        if (mensaje.length() > 4000 && mensaje.contains("data:image")) {
            error += "La imagen es muy grande por favor, reduzca el peso de la imagen.<br/>";
        }
        if (file == null) {
            error += "Debe de seleccionar un archivo con la lista de correos a enviar.<br/>";
        }
        if (error.isEmpty()) {
            PrimeFaces.current().executeScript("onClick('btnSend');");
        } else {
            JsfUtil.mensajeError("<br/>" + error);
        }
    }

    public void enviarCorreos() {
        procesoFacade.enviarCorreos(pathArchivo, titulo, mensaje, mailSession, transport, remitente, password);
        JsfUtil.mensajeInformacion("El proceso de envio de correos se realizara en background.");
        limpiarFormato();
    }

    private void limpiarFormato() {
        titulo = "";
        mensaje = "";
        mensaje = "";
        pathArchivo = "";
        reemplazarArchivo();
    }

    public void enviarProcesoPendiente() {
        JsfUtil.mensajeInformacion("El proceso de envio de correos se realizara en background.");
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
        mailSession = null;
        if (mailSession == null) {
            Properties configEmail = new Properties();

            configEmail.put("mail.smtp.auth", "true");
            configEmail.put("mail.smtp.starttls.enable", "false");

            configEmail.put("mail.smtp.host", "svr2k13mail01.mined.gob.sv");
            configEmail.put("mail.smtp.port", "2525");

            configEmail.put("mail.user", "MINED\\" + dominio);
            configEmail.put("mail.user.pass", password);
            configEmail.put("mail.from", remitente);

            mailSession = Session.getInstance(configEmail, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("MINED\\" + dominio, password);
                }
            });
        }

        return mailSession;
    }

    public String validarCrendecialesDelCorreo() {
        String url = "";
        try {
            if (correoRemitente != null && password != null) {
                if (idDominioCorreo.equals("1")) {
                    remitente = correoRemitente.concat("@").concat("mined.gob.sv");
                    port = "2525";
                    server = "svr2k13mail01.mined.gob.sv";
                    mailSession = getMailSessionMined();
                } else {
                    remitente = correoRemitente.concat("@").concat("admin.mined.edu.sv");
                    port = "587";
                    server = "smtp.office365.com";
                    mailSession = getMailSessionOffice();
                }

                transport = mailSession.getTransport("smtp");
                transport.connect(server, Integer.parseInt(port), remitente, password);

                correoValido = true;

                transport.close();

                url = "mensaje?faces-redirect=true";
            }
        } catch (NoSuchProviderException ex) {
            JsfUtil.mensajeError("Error en el usuario o  clave de acceso.");
            correoValido = false;
        } catch (MessagingException ex) {
            JsfUtil.mensajeError("Error en el usuario o  clave de acceso.");
            correoValido = false;
        }

        return url;
    }

    public String regresar() {
        if (transport.isConnected()) {
            try {
                transport.close();
            } catch (MessagingException ex) {
                Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        remitente = "";
        correoRemitente = "";
        dominio = "";
        password = "";
        correoValido = false;
        showUploadFile = true;
        titulo = "";
        mensaje = "";
        pathArchivo = "";

        idDominioCorreo = "2";
        return "index?faces-redirect=true";
    }

    public void reemplazarArchivo() {
        file = null;
        showUploadFile = true;
    }

    public void logout() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().clear();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.redirect(((ServletContext) externalContext.getContext()).getContextPath() + "/index.mined");
            System.gc();
        } catch (IOException ex) {
            Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
