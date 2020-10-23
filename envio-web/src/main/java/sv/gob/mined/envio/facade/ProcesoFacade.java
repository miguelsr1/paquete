/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.envio.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.jboss.ejb3.annotation.TransactionTimeout;
import sv.gob.mined.envio.model.DetalleEnvio;

/**
 *
 * @author MISanchez
 */
@Stateful
public class ProcesoFacade {

    @Inject
    private LeerArchivoFacade leerArchivoFacade;

    @Inject
    private PersistenciaFacade persistenciaFacade;

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NEVER)
    @TransactionTimeout(unit = TimeUnit.HOURS, value = 2)
    public void enviarCorreos(String pathArchivo, String titulo, String mensaje, Session mailSession, Transport transport,
            String remitente, String password, String server, String port) {
        iniciar(pathArchivo, titulo, mensaje, mailSession, transport, remitente, password, server, port);
    }

    private void iniciar(String pathArchivo, String titulo, String mensaje, Session mailSession, Transport transport,
            String remitente, String password, String server, String port) {
        System.out.println("ok");
        BigDecimal idEnvio = leerArchivoFacade.guardarRegistros(pathArchivo, remitente, titulo, mensaje);
        envio(remitente, password, titulo, mensaje, persistenciaFacade.findDetalleEnvio(idEnvio), transport, mailSession, server, port);
        System.out.println("fin");
    }

    private void envio(String remitente, String password, String titulo, String mensaje,
            List<DetalleEnvio> lstDetalle,
            Transport transport, Session mailSession,
            String server, String port) {
        Integer cont = 1;
        Integer contReset = 1;
        List<BigDecimal> correosEnviados = new ArrayList<>();
        try {
            try {
                Address from = new InternetAddress(remitente);

                for (DetalleEnvio detalleEnvio : lstDetalle) {
                    if (transport.isConnected()) {

                    } else {
                        transport.connect();
                    }

                    try {
                        String msjTemp;
                        if ((detalleEnvio.getNombreDestinatario() == null || detalleEnvio.getNombreDestinatario().isEmpty())
                                && (detalleEnvio.getNip() == null || detalleEnvio.getNip().isEmpty())) {
                            msjTemp = mensaje;
                        } else {
                            msjTemp = mensaje.replace(":DOCENTE:", detalleEnvio.getNombreDestinatario().concat(" - ").concat(detalleEnvio.getNip() == null ? "" : detalleEnvio.getNip()));
                        }
                        MimeMessage message = new MimeMessage(mailSession);

                        message.setFrom(from);

                        InternetAddress[] address = {new InternetAddress(detalleEnvio.getCorreoDestinatario())};
                        message.setRecipients(Message.RecipientType.TO, address);
                        //message.setRecipients(Message.RecipientType.BCC, "miguel.sanchez@admin.mined.edu.sv");

                        BodyPart messageBodyPart1 = new MimeBodyPart();

                        messageBodyPart1.setContent(msjTemp, "text/html; charset=utf-8");

                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(messageBodyPart1);

                        message.setContent(multipart);
                        message.setSubject(titulo, "UTF-8");

                        transport.sendMessage(message, message.getAllRecipients());

                        Logger.getLogger(ProcesoFacade.class.getName()).log(Level.INFO, "Numero {0}", cont);

                        correosEnviados.add(detalleEnvio.getIdDetalle());
                        cont++;
                        contReset++;

                        if (contReset == 401) {
                            transport.close();
                            contReset = 1;
                        }
                    } catch (AddressException ex) {
                        System.out.println("Error 1");
                        if (transport.isConnected()) {
                            transport.close();

                            transport = mailSession.getTransport("smtp");
                            transport.connect(server, Integer.parseInt(port), remitente, password);
                        }
                        Logger.getLogger(ProcesoFacade.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (MessagingException ex) {
                        System.out.println("Error 2");
                        if (transport.isConnected()) {
                            transport.close();

                            transport = mailSession.getTransport("smtp");
                            transport.connect(server, Integer.parseInt(port), remitente, password);
                        } else {
                            transport = mailSession.getTransport("smtp");
                            transport.connect(server, Integer.parseInt(port), remitente, password);
                        }
                        Logger.getLogger(ProcesoFacade.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //JsfUtil.mensajeInformacion("Ha finalizado el proceso de envio de correos.");
            } catch (AddressException e) {
                e.printStackTrace();
                //JsfUtil.mensajeError("Ah ocurrido un error en el envio de correos.");
            }
            transport.close();

            persistenciaFacade.actualizarDetalleEnviado(correosEnviados);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ProcesoFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ProcesoFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
    }
}
