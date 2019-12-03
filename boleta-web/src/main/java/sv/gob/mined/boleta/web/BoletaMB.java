/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.activation.DataHandler;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import sv.gob.mined.boleta.dto.DatosDto;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class BoletaMB implements Serializable {

    private Boolean visible = true;
    private String usuario, clave, codDepa, mesAnho;
    private Date fecha;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM_yyyy");
    private List<DatosDto> lstPendientes = new ArrayList();
    private List<DatosDto> lstProcesados = new ArrayList();

    private UploadedFile file;
    private PDDocument document;
    private List<PDDocument> lstPages;
    private Iterator<PDDocument> iterator;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");

    private Session mailSession;

    private Properties config = new Properties();
    private Properties configEmail = new Properties();

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

    public void cargarArchivos() throws IOException {
        mesAnho = sdf.format(fecha);
        lstPendientes.clear();
        lstProcesados.clear();
        File carpetaPendientes = new File("/opt/soporte/" + codDepa + "/" + mesAnho);
        cargarDatosDeArchivos(lstPendientes, carpetaPendientes);
        /*if (carpetaPendientes.exists()) {
            for (File listFile : carpetaPendientes.listFiles()) {
                if (listFile.isFile()) {
                    DatosDto dato = new DatosDto();

                    Path pathFile = listFile.toPath();
                    FileTime fTime = (FileTime) Files.getAttribute(pathFile, "creationTime");
                    PDDocument pdc = PDDocument.load(listFile);

                    dato.setFechaCreado(new Date(fTime.toMillis()));
                    dato.setNombreArchivo(listFile.getName());
                    dato.setNumeroPaginas(pdc.getNumberOfPages());

                    lstPendientes.add(dato);
                }
            }
        } else {
            carpetaPendientes.mkdir();
        }*/

        File carpetaProcesados = new File("/opt/soporte/" + codDepa + "/" + mesAnho + "/procesado/");
        cargarDatosDeArchivos(lstProcesados, carpetaProcesados);
        /*if (carpetaProcesados.exists()) {
            for (File listFile : carpetaProcesados.listFiles()) {
                if (listFile.isFile()) {
                    DatosDto dato = new DatosDto();

                    Path pathFile = listFile.toPath();
                    FileTime fTime = (FileTime) Files.getAttribute(pathFile, "creationTime");
                    PDDocument pdc = PDDocument.load(listFile);

                    dato.setFechaCreado(new Date(fTime.toMillis()));
                    dato.setNombreArchivo(listFile.getName());
                    dato.setNumeroPaginas(pdc.getNumberOfPages());

                    lstProcesados.add(dato);
                }
            }
        } else {
            carpetaProcesados.mkdir();
        }*/
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

    public Boolean getVisible() {
        return visible;
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

        File carpeta = new File("/opt/soporte/" + codDepa + "/" + mesAnho + "/");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        Path folder = Paths.get("/opt/soporte/" + codDepa + "/" + mesAnho);
        String filename = FilenameUtils.getBaseName(file.getFile().getFileName());
        String extension = FilenameUtils.getExtension(file.getFile().getFileName());
        Path arc = Files.createTempFile(folder, filename + "-", "." + extension);

        try (InputStream input = file.getFile().getInputstream()) {
            Files.copy(input, arc, StandardCopyOption.REPLACE_EXISTING);
        }
        visible = false;
    }

    public List<DatosDto> getLstPendientes() {
        return lstPendientes;
    }

    public List<DatosDto> getLstProcesados() {
        return lstProcesados;
    }

    public void upload() {
        if (file != null) {
            splitPages();
            file = null;

            document = null;
        }
    }

    public void splitPages() {
        try {
            InputStream is = file.getInputstream();
            document = PDDocument.load(is, MemoryUsageSetting.setupTempFileOnly());

            Splitter splitter = new Splitter();

            lstPages = splitter.split(document);

            //Creating an iterator 
            iterator = lstPages.listIterator();

            final Properties info = chargeEmailsProperties("emails0212");
            int i = 1;

            while (iterator.hasNext()) {
                DatosDto rowData = new DatosDto();

                PDDocument pd = iterator.next();

                String code = getCode2(pd, "         )", 0, 15).substring(8);
                if (info.containsKey(code)) {
                    String email = info.getProperty(code);
                    /*rowData.setCodigo(code);
                    rowData.setCorreoElectronico(email);*/

                    //enviarMail(code, email, pd);
                    lstPendientes.add(rowData);
                    PrimeFaces.current().ajax().update("tblDatos");
                    System.out.println("envio " + i);
                }
                pd.close();

                i++;
            }

            document.close();
            iterator.remove();
            is.close();
        } catch (IOException ex) {
            try {
                document.close();

            } catch (IOException ex1) {
                Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, null, ex1);
            }

            Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Properties chargeEmailsProperties(String nombre) {
        Properties info = null;
        try {
            info = new Properties();
            InputStream input = BoletaMB.class
                    .getClassLoader().getResourceAsStream(nombre + ".properties");
            //fin            
            info.load(input);
            input.close();

        } catch (IOException ex) {
            Logger.getLogger(BoletaMB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return info;
    }

    public static String getCode2(PDDocument pDDocument, String strEndIdentifier, int offSet, int back) {
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
}
