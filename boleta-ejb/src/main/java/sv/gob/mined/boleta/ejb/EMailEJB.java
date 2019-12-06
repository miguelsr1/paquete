/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.ejb;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.ejb.Asynchronous;
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
public class EMailEJB {

    
    public void enviarMail(String code, String remitente, String usuario, String mensaje,
            PDDocument pDDocument, Session mailSession) {
        try {
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress(usuario);

            m.setFrom(from);
            //remitente = "miguel.sanchez@admin.mined.edu.sv";
            m.setRecipients(Message.RecipientType.TO, remitente);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pDDocument.save(out);
            byte[] bytes = out.toByteArray();

            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(mensaje);

            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            ByteArrayDataSource ds = new ByteArrayDataSource(bytes, "application/pdf");
            messageBodyPart2.setDataHandler(new DataHandler(ds));
            messageBodyPart2.setFileName("Boleta.pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            m.setContent(multipart);
            m.setSubject("Boleta de Pago", "UTF-8");
            Transport.send(m);

            pDDocument.close();
            out.close();
        } catch (MessagingException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.INFO, "Error en el envio de correo a: {0} - código: {1}", new Object[]{remitente, code});

            enviarMailDeError("eMail Boleta - Error", "Error en el envio de correo a: " + remitente + " - código: " + code, usuario, mailSession);
        } catch (IOException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, "Error en el envio de correo");
        }
    }
    
    public void enviarMailDeConfirmacion(String subject, String message, String usuario, Session mailSession){
        try {
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress(usuario);
            m.setSubject(subject, "UTF-8");

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, usuario);
            m.setRecipients(Message.RecipientType.BCC, "miguel.sanchez@admin.mined.edu.sv");
            m.setSentDate(new java.util.Date());
            m.setText(message, "UTF-8", "html");
            Transport.send(m);
        } catch (MessagingException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, "Error en el envio de correo");
        }
    }

    @Asynchronous
    public void escribirEmpleadoNoEncontrado(String codDepa, String mesAnho, String path, String codigoEmpleado) {
        try {
            File file = new File(path + File.separator + codDepa + File.separator + mesAnho + File.separator + "no_encontrado.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            
            Files.write(Paths.get(file.getAbsolutePath()), codigoEmpleado.concat("\n").getBytes(), StandardOpenOption.APPEND);

        } catch (IOException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
