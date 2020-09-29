/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
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
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

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
    @Resource(mappedName = "java:/MailPaqueteRA")
    private Session mailSessionRa;
    @Resource(mappedName = "java:/MailPaqueteProv")
    private Session mailSessionProv;

    /**
     * Este método envía un mail
     *
     * @param subject asunto del mail
     * @param remitente - destinatarios.
     * @param message - contenido del mensaje.
     * @param codigoDepartamento
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void enviarMail(String subject, String remitente, String message, String codigoDepartamento) {
        try {
            int i;
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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean enviarMail(String remitente, String titulo, String cuerpo) {
        try {
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress(utilEJB.getValorDeParametro("PAGO_CORREO_NOTIFICACION"));

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, remitente);
            m.setSubject(titulo, "UTF-8");
            m.setSentDate(new java.util.Date());
            m.setText(cuerpo, "UTF-8", "html");
            Transport.send(m);
            Logger.getLogger(EMailEJB.class.getName()).log(Level.INFO, "Modulo Proveedores: Se envio el correo a.{0}", remitente);
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.WARNING, "El correo registrado para el proveedor no existe.", ex);
            return false;
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean enviarMail(String remitente,
            String titulo, String mensaje, List<String> to, List<String> cc, List<String> bcc) {
        try {
            MimeMessage m = new MimeMessage(mailSessionProv);
            Address from = new InternetAddress(remitente);

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
    public Boolean enviarMail(String remitente, String titulo, String mensaje,
            List<String> to, List<String> cc, List<String> bcc,
            Map<String, String> archivos) {
        try {
            MimeMessage m = new MimeMessage(mailSessionRa);
            Address from = new InternetAddress(remitente);

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
//                try {
//                    multipart.addBodyPart(addFilesAttachment(key, value, new MimeBodyPart()));
//                } catch (MessagingException ex) {
//                    Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, null, ex);
//                }
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
            DataSource source = new FileDataSource("C:\\Users\\MISanchez\\Documents\\MINED\\paquete\\Paquete 2021\\" + value);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(value);
            multipart.addBodyPart(messageBodyPart);
        } catch (MessagingException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private MimeBodyPart addFilesAttachment(String key, String value, MimeBodyPart messageBodyPart) {
        try {

            switch (key) {
                case "PDF":
                    try (PDDocument document = PDDocument.load(new File("C:\\Users\\MISanchez\\Documents\\MINED\\paquete\\Paquete 2021\\" + value))) {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        document.save(out);
                        byte[] bytes = out.toByteArray();

                        ByteArrayDataSource ds = new ByteArrayDataSource(bytes, "application/pdf");
                        messageBodyPart.setDataHandler(new DataHandler(ds));
                        messageBodyPart.setFileName(value);
                        out.close();
                    } catch (MessagingException ex) {
                        Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "XLS":
                case "XLXS":
                    try {
                        File f = new File("C:\\Users\\MISanchez\\Documents\\MINED\\paquete\\Paquete 2021\\" + value);
                        DataSource source = new FileDataSource(f);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(value);
                    } catch (MessagingException ex) {
                        Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }

        } catch (IOException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return messageBodyPart;
    }
}
