/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
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

    @EJB
    private UtilEJB utilEJB;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Resource(mappedName = "java:/MailService365")
    private Session mailSession;

    /*private Properties config = new Properties();

    private void configuracionesDeSession() {
        config.put("mail.transport.protocol", "smtp");
        config.put("mail.smtp.host", "svr2k13mail01.mined.gob.sv");
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "false");
        config.put("mail.smtp.port", "587");

        mailSession = Session.getInstance(config, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("MISanchez", "miguelsr15.");
            }

        });
    }*/
    /**
     * Este método envía un mail
     *
     * @param subject asunto del mail
     * @param remitente - destinatarios.
     * @param message - contenido del mensaje.
     */
    @Asynchronous
    public void enviarMail(String subject, String remitente, String message, String codigoDepartamento) {
        try {
            int i = 0;
            String listaDeCorreosBcc = utilEJB.getValorDeParametro("PAGO_CORREO_NOTIFICACION_COPIA");
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress(utilEJB.getValorDeParametro("PAGO_CORREO_NOTIFICACION"));
            List<String> lstCcCorreo = utilEJB.getLstCcPagoByCodDepa(codigoDepartamento);

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, remitente);

            Address[] lstAddressBcc = new Address[listaDeCorreosBcc.split(",").length + lstCcCorreo.size()];
            for (i = 0; i < listaDeCorreosBcc.split(",").length; i++) {
                lstAddressBcc[i] = new InternetAddress(listaDeCorreosBcc.split(",")[i]);
            }
            for (String string : utilEJB.getLstCcPagoByCodDepa(codigoDepartamento)) {
                lstAddressBcc[i] = new InternetAddress(string);
                i++;
            }
            m.setRecipients(Message.RecipientType.BCC, lstAddressBcc);

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
                sb.append(message).append("<br/><br/>").append(ExceptionUtils.getStackTrace(e));
            }

            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress(utilEJB.getValorDeParametro("PAGO_CORREO_NOTIFICACION"));

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
