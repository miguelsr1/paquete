/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ApplicationScoped
public class EMailMB implements Serializable {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("Bundle");

    @Resource(mappedName = "java:/MailBoleta")
    private Session mailSession;

    @Asynchronous
    public void enviarMail(String remitente, String message, PDDocument pDDocument) throws IOException {
        try {
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress(RESOURCE_BUNDLE.getString("mail.from"));

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, remitente);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pDDocument.save(out);
            byte[] bytes = out.toByteArray();

            ByteArrayDataSource ds = new ByteArrayDataSource(bytes, "application/pdf");
            m.setDataHandler(new DataHandler(ds));
            m.setFileName("Boleta.pdf");

            m.setSubject("Boleta", "UTF-8");
            m.setSentDate(new java.util.Date());
            m.setText(message, "UTF-8", "html");
            Transport.send(m);
        } catch (MessagingException ex) {
            Logger.getLogger(EMailMB.class.getName()).log(Level.INFO, "Error en el envio de correo", ex);
        }
    }
}
