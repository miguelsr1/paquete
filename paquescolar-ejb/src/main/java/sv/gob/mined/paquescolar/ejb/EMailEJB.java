package sv.gob.mined.paquescolar.ejb;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
    //@Resource(mappedName = "java:/MailService365")
    //private Session mailSession;
    //@Resource(mappedName = "java:/MailPaqueteProv")
    //private Session mailSessionProv;

    //private Session mailSessionRa;

    /**
     * Este método envía un mail
     *
     * @param subject asunto del mail
     * @param destinatario - destinatarios.
     * @param message - contenido del mensaje.
     * @param codigoDepartamento
     * @param mailSessionG
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void enviarMail(String subject, String destinatario, String message, String codigoDepartamento, Session mailSessionG) {
        try {
            int i;
            String listaDeCorreosBcc = utilEJB.getValorDeParametro("PAGO_CORREO_NOTIFICACION_COPIA");
            MimeMessage m = new MimeMessage(mailSessionG);
            Address from = new InternetAddress(mailSessionG.getProperty("mail.from"));
            List<String> lstCcCorreo = utilEJB.getLstCcPagoByCodDepa(codigoDepartamento);

            m.setFrom(from);

            if (destinatario.contains(",")) {
                Address[] lstAddressTo = new Address[destinatario.split(",").length];
                for (i = 0; i < destinatario.split(",").length; i++) {
                    lstAddressTo[i] = new InternetAddress(destinatario.split(",")[i]);
                }
                m.setRecipients(Message.RecipientType.TO, lstAddressTo);
            } else {
                m.setRecipients(Message.RecipientType.TO, destinatario);
            }

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
     * @param mailSessionG
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void enviarMailDeError(String subject, String message, Exception e, Session mailSessionG) {
        try {
            sb = new StringBuilder();
            if (e != null) {
                sb.append(message).append("<br/><br/>").append(ExceptionUtils.getStackTrace(e));
            }

            MimeMessage m = new MimeMessage(mailSessionG);
            Address from = new InternetAddress(mailSessionG.getProperty("mail.from"));

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

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean enviarMail(String destinatario ,String titulo, String cuerpo, Session mailSessionG) {
        try {
            MimeMessage m = new MimeMessage(mailSessionG);
            Address from = new InternetAddress(mailSessionG.getProperty("mail.from"));

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, destinatario);
            m.setSubject(titulo, "UTF-8");
            m.setSentDate(new java.util.Date());
            m.setText(cuerpo, "UTF-8", "html");
            Transport.send(m);
            Logger.getLogger(EMailEJB.class.getName()).log(Level.INFO, "Modulo Proveedores: Se envio el correo a.{0}", destinatario);
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.WARNING, "El correo registrado para el proveedor no existe.", ex);
            return false;
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean enviarMail(String titulo, String mensaje, List<String> to, List<String> cc, List<String> bcc, Session mailSessionG) {
        try {
            MimeMessage m = new MimeMessage(mailSessionG);
            Address from = new InternetAddress(mailSessionG.getProperty("mail.from"));

            Address[] destinatarios = new Address[to.size()];
            Address[] copia = new Address[cc.size()];
            Address[] copiaOcultos = new Address[bcc.size()];

            for (int i = 0; i < to.size(); i++) {
                destinatarios[i] = new InternetAddress(to.get(i));
            }
            for (int i = 0; i < cc.size(); i++) {
                copia[i] = new InternetAddress(cc.get(i));
            }
            for (int i = 0; i < bcc.size(); i++) {
                copiaOcultos[i] = new InternetAddress(bcc.get(i));
            }

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, destinatarios);
            m.setRecipients(Message.RecipientType.CC, copia);
            m.setRecipients(Message.RecipientType.BCC, copiaOcultos);

            BodyPart messageBodyPart1 = new MimeBodyPart();

            messageBodyPart1.setContent(mensaje, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);

            m.setContent(multipart);
            m.setSubject(titulo, "UTF-8");

            Transport.send(m);
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.WARNING, "Error", ex);

            return false;
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean enviarMail(String titulo, String mensaje,
            List<String> to, List<String> cc, List<String> bcc,
            Map<String, String> archivos, Session mailSessionG) {
        try {
            MimeMessage m = new MimeMessage(mailSessionG);
            Address from = new InternetAddress(mailSessionG.getProperty("mail.from"));

            Address[] destinatarios = new Address[to.size()];
            Address[] copia = new Address[cc.size()];
            Address[] copiaOcultos = new Address[bcc.size()];

            for (int i = 0; i < to.size(); i++) {
                destinatarios[i] = new InternetAddress(to.get(i));
            }
            for (int i = 0; i < cc.size(); i++) {
                copia[i] = new InternetAddress(cc.get(i));
            }
            for (int i = 0; i < bcc.size(); i++) {
                copiaOcultos[i] = new InternetAddress(bcc.get(i));
            }

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, destinatarios);
            m.setRecipients(Message.RecipientType.CC, copia);
            m.setRecipients(Message.RecipientType.BCC, copiaOcultos);

            BodyPart messageBodyPart1 = new MimeBodyPart();

            messageBodyPart1.setContent(mensaje, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);

            archivos.forEach((key, value) -> {

                addAttachment(value, multipart);
            });

            m.setContent(multipart);
            m.setSubject(titulo, "UTF-8");

            Transport.send(m);
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.WARNING, "Error", ex);

            return false;
        }
    }

    private void addAttachment(String value, Multipart multipart) {
        try {
            DataSource source = new FileDataSource("//opt//soporte//paquete//archivos//" + value);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(value);
            multipart.addBodyPart(messageBodyPart);
        } catch (MessagingException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
