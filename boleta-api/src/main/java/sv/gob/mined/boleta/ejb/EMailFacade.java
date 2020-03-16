/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.ejb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class EMailFacade {

    public Boolean enviarMail(String destinatario, String remitente,
            String titulo, String mensaje,
            String nombreMesAnho, File path, Session mailSession) {
        try {
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress(remitente);

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, destinatario);
            m.setRecipients(Message.RecipientType.BCC, "miguel.sanchez@admin.mined.edu.sv");

            BodyPart messageBodyPart1 = new MimeBodyPart();

            messageBodyPart1.setContent(mensaje, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);

            ByteArrayOutputStream out;
            try ( PDDocument document = PDDocument.load(path)) {
                out = new ByteArrayOutputStream();
                document.save(out);
                byte[] bytes = out.toByteArray();
                MimeBodyPart messageBodyPart2 = new MimeBodyPart();
                ByteArrayDataSource ds = new ByteArrayDataSource(bytes, "application/pdf");
                messageBodyPart2.setDataHandler(new DataHandler(ds));
                messageBodyPart2.setFileName("boleta_pago.pdf");
                multipart.addBodyPart(messageBodyPart2);
            }
            out.close();

            m.setContent(multipart);
            m.setSubject(titulo, "UTF-8");
            //m.setSubject("Boleta de Pago de " + nombreMesAnho, "UTF-8");
            Transport.send(m);
            return true;
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(EMailFacade.class.getName()).log(Level.WARNING, "Error en el envio de correo a: {0} - código: {1}", new Object[]{destinatario, path.getName()});
            Logger.getLogger(EMailFacade.class.getName()).log(Level.WARNING, "Error", ex);

            enviarMailDeError("eMail Boleta - Error", "Error en el envio de correo a: " + destinatario + " - código: " + path.getName(), remitente, mailSession);
            return false;
        }
    }

    public void enviarMailDeError(String subject, String message, String usuario, Session mailSession) {
        try {
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress(usuario);
            m.setSubject(subject, "UTF-8");

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, "miguel.sanchez@mined.gob.sv");
            m.setSentDate(new java.util.Date());
            m.setText(message, "UTF-8", "html");
            Transport.send(m);
        } catch (MessagingException ex) {
            Logger.getLogger(EMailFacade.class.getName()).log(Level.SEVERE, "Error en el envio de correo");
        }
    }

    public void enviarMailDeConfirmacion(String subject, String message, String remitente, Session mailSession) {
        try {
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress(remitente);
            m.setSubject(subject, "UTF-8");

            m.setFrom(from);

            Address[] lstDestinatarios = new Address[]{new InternetAddress("miguel.sanchez@mined.gob.sv"), new InternetAddress("guillermo.castro@mined.gob.sv")};

            m.setRecipients(Message.RecipientType.TO, remitente);
            m.setRecipients(Message.RecipientType.BCC, lstDestinatarios);

            m.setSentDate(new java.util.Date());
            m.setText(message, "UTF-8", "html");
            Transport.send(m);
        } catch (MessagingException ex) {
            Logger.getLogger(EMailFacade.class.getName()).log(Level.SEVERE, "Error en el envio de correo");
        }
    }
}
