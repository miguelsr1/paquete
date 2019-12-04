/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import sv.gob.mined.boleta.dto.DatosDto;
import sv.gob.mined.boleta.ejb.LeerBoletasEJB;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class BoletaMB implements Serializable {

    private String usuario;
    private String clave;
    private String codDepa;
    private String mesAnho;
    private Date fecha;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM_yyyy");
    private List<DatosDto> lstPendientes = new ArrayList();
    private List<DatosDto> lstProcesados = new ArrayList();

    private UploadedFile file;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("parametros");

    private Session mailSession;

    private Properties config = new Properties();
    private Properties configEmail = new Properties();

    @EJB
    private LeerBoletasEJB leerBoletasEJB;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getExternalContext().getSessionMap().containsKey("usuario")) {

            usuario = context.getExternalContext().getSessionMap().get("usuario").toString();
            codDepa = usuario.substring(7, 9);
            mesAnho = "12_2019";
            clave = context.getExternalContext().getSessionMap().get("clave").toString();

            config = chargeEmailsProperties("config");

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
                    return new PasswordAuthentication(usuario,
                            clave);
                }
            });
        } else {
            try {
                JsfUtil.limpiarVariableSession();
                context.getExternalContext().getSessionMap().clear();
                ExternalContext externalContext = context.getExternalContext();
                externalContext.redirect(((ServletContext) externalContext.getContext()).getContextPath() + "/index.mined");
                System.gc();
            } catch (IOException ex) {
                Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, "Error haciendo logout", ex);
            }
        }
    }

    public Properties chargeEmailsProperties(String nombre) {
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

    public void cargarArchivos() throws IOException {
        mesAnho = sdf.format(fecha);
        lstPendientes.clear();
        lstProcesados.clear();
        File carpetaPendientes = new File(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + codDepa + File.separator + mesAnho);
        cargarDatosDeArchivos(lstPendientes, carpetaPendientes);

        File carpetaProcesados = new File(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + codDepa + File.separator + mesAnho + File.separator + "procesado" + File.separator);
        cargarDatosDeArchivos(lstProcesados, carpetaProcesados);
    }

    private void cargarDatosDeArchivos(List<DatosDto> lst, File carpeta) throws IOException {
        if (carpeta.exists()) {
            for (File listFile : carpeta.listFiles()) {
                if (listFile.isFile()) {
                    DatosDto dato = new DatosDto();

                    Path pathFile = listFile.toPath();
                    FileTime fTime = (FileTime) Files.getAttribute(pathFile, "creationTime");
                    PDDocument pdc = PDDocument.load(listFile);

                    dato.setFechaCreado(new Date(fTime.toMillis()));
                    dato.setNombreArchivo(listFile.getName());
                    dato.setNumeroPaginas(pdc.getNumberOfPages());

                    lst.add(dato);
                    
                    pdc.close();
                }
            }
        } else {
            carpeta.mkdir();
        }
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(FileUploadEvent file) throws FileNotFoundException, IOException {
        this.file = file.getFile();
        JsfUtil.mensajeInformacion("El archivo esta en cola para ser procesado. Al finalizar se enviara un correo notificando el envío de las boletas.");

        File carpeta = new File(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + codDepa + File.separator + mesAnho + File.separator);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        Path folder = Paths.get(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + codDepa + File.separator + mesAnho);
        String filename = FilenameUtils.getBaseName(file.getFile().getFileName());
        String extension = FilenameUtils.getExtension(file.getFile().getFileName());
        Path arc = Files.createTempFile(folder, filename + "-", "." + extension);

        try (InputStream input = file.getFile().getInputstream()) {
            Files.copy(input, arc, StandardCopyOption.REPLACE_EXISTING);
        }

        cargarArchivos();
    }

    public List<DatosDto> getLstPendientes() {
        return lstPendientes;
    }

    public List<DatosDto> getLstProcesados() {
        return lstProcesados;
    }
//
//    public void upload() {
//        if (file != null) {
//            splitPages();
//            file = null;
//
//            document = null;
//        }
//    }

//    //@Asynchronous
//    public void enviarMail(String code, String remitente, PDDocument pDDocument) throws IOException {
//        try {
//            MimeMessage m = new MimeMessage(mailSession);
//            Address from = new InternetAddress(usuario);
//
//            m.setFrom(from);
//            //remitente = "miguel.sanchez@admin.mined.edu.sv";
//            m.setRecipients(Message.RecipientType.TO, remitente);
//
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            pDDocument.save(out);
//            byte[] bytes = out.toByteArray();
//
//            BodyPart messageBodyPart1 = new MimeBodyPart();
//            messageBodyPart1.setText(RESOURCE_BUNDLE.getString("mail.message"));
//
//            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//
//            ByteArrayDataSource ds = new ByteArrayDataSource(bytes, "application/pdf");
//            messageBodyPart2.setDataHandler(new DataHandler(ds));
//            messageBodyPart2.setFileName("Boleta.pdf");
//
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(messageBodyPart1);
//            multipart.addBodyPart(messageBodyPart2);
//
//            m.setContent(multipart);
//            m.setSubject(code + " Boleta", "UTF-8");
//            Transport.send(m);
//
//            pDDocument.close();
//            out.close();
//        } catch (MessagingException ex) {
//            Logger.getLogger(BoletaMB.class.getName()).log(Level.INFO, "Error en el envio de correo a: {0} - código: {1}", new Object[]{remitente, code});
//
//            enviarMailDeError("eMail Boleta - Error", "Error en el envio de correo a: " + remitente + " - código: " + code);
//        }
//    }
//
//    public void enviarMailDeError(String subject, String message) {
//        try {
//            MimeMessage m = new MimeMessage(mailSession);
//            Address from = new InternetAddress(usuario);
//
//            m.setFrom(from);
//            m.setRecipients(Message.RecipientType.TO, "miguel.sanchez@mined.gob.sv");
//            m.setSubject(subject, "UTF-8");
//            m.setSentDate(new java.util.Date());
//            m.setText(message, "UTF-8", "html");
//            Transport.send(m);
//        } catch (MessagingException ex) {
//            Logger.getLogger(BoletaMB.class.getName()).log(Level.INFO, "Error en el envio de correo", ex);
//        }
//    }
    public void verificarArchivosPendientes() {
        leerBoletasEJB.leerArchivosPendientes(mailSession, codDepa);
    }
}
