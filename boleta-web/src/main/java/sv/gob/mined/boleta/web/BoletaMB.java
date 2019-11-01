/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.primefaces.model.UploadedFile;
import sv.gob.mined.boleta.Dto.DatosDto;

/**
 *
 * @author misanchez
 */
@ManagedBean
@SessionScoped
public class BoletaMB implements Serializable {

    private List<DatosDto> lstDatos = new ArrayList();

    private UploadedFile file;
    private PDDocument document;
    private List<PDDocument> lstPages;
    private Iterator<PDDocument> iterator;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");

    @Resource(mappedName = "java:/MailService365")
    private Session mailSession;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<DatosDto> getLstDatos() {
        return lstDatos;
    }

    public void upload() {
        if (file != null) {
            splitPages();
        }
    }

    public void splitPages() {
        List<PDDocument> Pages2;
        try {
            document = PDDocument.load(file.getInputstream(), MemoryUsageSetting.setupTempFileOnly());

            //Instantiating Splitter class
            Splitter splitter = new Splitter();

            //splitting the pages of a PDF document
            lstPages = splitter.split(document);

            //Creating an iterator 
            iterator = lstPages.listIterator();

            final Properties info = chargeEmailsProperties2();
            int i = 1;

            while (i < 10 || iterator.hasNext()) {
                DatosDto rowData = new DatosDto();

                PDDocument pd = iterator.next();

                String code = getCode2(pd, "         )", 0, 15).substring(8);
                if (info.containsKey(code)) {
                    String email = info.getProperty(code);
                    rowData.setCodigo(code);
                    rowData.setCorreoElectronico(email);

                    enviarMail(code, email, pd);

                    lstDatos.add(rowData);
                    System.out.println("envio " + i);
                }
                pd.close();

                i++;
            }

            document.close();
        } catch (IOException ex) {
            try {
                document.close();
            } catch (IOException ex1) {
                Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, null, ex1);
            }

            Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Properties chargeEmailsProperties2() {
        Properties info = null;
        try {
            info = new Properties();
            InputStream input = BoletaMB.class.getClassLoader().getResourceAsStream("emails.properties");
            //fin            
            info.load(input);
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;
    }

    public static String getCode2(PDDocument pDDocument, String strEndIdentifier, int offSet, int back) {
        String returnString = "";
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
            ex.printStackTrace();
        }
        return returnString;
    }

    //@Asynchronous
    public void enviarMail(String code, String remitente, PDDocument pDDocument) throws IOException {
        try {
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress("cesar.nieves@mined.gob.sv");

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, remitente);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pDDocument.save(out);
            byte[] bytes = out.toByteArray();

            BodyPart messageBodyPart1 = new MimeBodyPart(); 
            messageBodyPart1.setText(RESOURCE_BUNDLE.getString("mail.message"));
            
            MimeBodyPart messageBodyPart2 = new MimeBodyPart(); 
            DataSource source = new FileDataSource("Boleta.pdf"); 
            messageBodyPart2.setDataHandler(new DataHandler(source)); 
            messageBodyPart2.setFileName("Boleta.pdf");
            
            ByteArrayDataSource ds = new ByteArrayDataSource(bytes, "application/pdf");
            messageBodyPart2.setDataHandler(new DataHandler(ds));
            
            Multipart multipart = new MimeMultipart();    
            multipart.addBodyPart(messageBodyPart1);     
            multipart.addBodyPart(messageBodyPart2);
            
            m.setContent(multipart);
            
            
            //m.setDataHandler(new DataHandler(ds));
            //m.setFileName("Boleta.pdf");*/

            m.setSubject(code + " Boleta", "UTF-8");
            /*m.setSentDate(new java.util.Date());
            m.setText(RESOURCE_BUNDLE.getString("mail.message"), "UTF-8", "html");*/
            Transport.send(m);
        } catch (MessagingException ex) {
            Logger.getLogger(EMailMB.class.getName()).log(Level.INFO, "Error en el envio de correo", ex);
        }
    }
}
