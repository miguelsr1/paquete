/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class EMailEJB {

    private StringBuilder sb;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //@Resource(mappedName = "java:/MailService365")
    private Session mailSession;

    private final Properties config = new Properties();

    private void configuracionesDeSession() {
        config.put("mail.transport.protocol", "smtp");
        config.put("mail.smtp.host", "svr2k13mail01.mined.gob.sv");
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");
        config.put("mail.smtp.port", "587");

        mailSession = Session.getInstance(config, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("cesar.nieves@mined.edu.sv", "Nixon00183504");
            }

        });
    }

    /**
     * Este método envía un mail
     *
     * @param subject asunto del mail
     * @param remitente - destinatarios.
     * @param message - contenido del mensaje.
     */
    @Asynchronous
    public void enviarMail(String subject, String remitente, String message) {
        try {
            //configuracionesDeSession();
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress("rafael.arias@mined.gob.sv");

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, remitente);
            m.setRecipients(Message.RecipientType.BCC, "rafael.arias@mined.gob.sv");
            m.setSubject(subject, "UTF-8");
            m.setSentDate(new java.util.Date());
            m.setText(message, "UTF-8", "html");
            Transport.send(m);
        } catch (MessagingException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.INFO, "Error en el envio de correo", ex);
        }
    }

    /**
     *
     * @param subject
     * @param message
     * @param e
     */
    @Asynchronous
    public void enviarMailDeError(String subject, String message, Exception e) {
        try {
            sb = new StringBuilder();
            if (e != null) {
                sb.append(message).append("<br/>").append(ExceptionUtils.getStackTrace(e));
            }

            configuracionesDeSession();
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress("cesar.nieves@mined.edu.sv");

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, "miguel.sanchez@mined.gob.sv");
            m.setSubject(subject, "UTF-8");
            m.setSentDate(new java.util.Date());
            m.setText(e != null ? sb.toString() : message, "UTF-8", "html");
            Transport.send(m);
        } catch (MessagingException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.INFO, "Error en el envio de correo", ex);
        }
    }
}
