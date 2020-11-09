/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.envio.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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

    @Inject
    private EMailFacade eMailFacade;

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
        envio(remitente, password, titulo, mensaje, persistenciaFacade.findDetalleEnvio(idEnvio), transport, mailSession, server, port, pathArchivo);
        System.out.println("fin");
    }

    @SuppressWarnings("empty-statement")
    private void envio(String remitente, String password, String titulo, String mensaje,
            List<DetalleEnvio> lstDetalle,
            Transport transport, Session mailSession,
            String server, String port, String pathArchivo) {
        Boolean envioPorBloque = false;
        Integer cont = 1;
        Integer contReset = 1;
        Integer maxCorreoEnviado = 0;
        Integer numBloque = 1;
        Integer correosEnviandos = 0;
        Integer serverCorreo = 0;
        //String mensaje = "";
        List<BigDecimal> correosEnviados = new ArrayList<>();
        HashMap<String, String> remitentes;

        try {
            try {
                remitentes = getRemitentes(pathArchivo, remitente, password);

                if (remitentes.size() > 1) {
                    if (transport.isConnected()) {
                        transport.close();
                    }

                    mailSession = null;

                    envioPorBloque = true;
                    if (remitentes.get("correo1").contains("admin.mined.edu.sv")) {
                        //server office365
                        port = "587";
                        server = "smtp.office365.com";
                        maxCorreoEnviado = 5; //9999;
                        serverCorreo = 1;
                    } else {
                        //server gmail
                        port = "587";
                        server = "smtp.gmail.com";
                        maxCorreoEnviado = 70; //1999;
                        serverCorreo = 2;
                    }
                }

                mensaje = lstDetalle.get(0).getIdEnvio().getMensaje();

                for (DetalleEnvio detalleEnvio : lstDetalle) {
                    if (envioPorBloque) {
                        remitente = remitentes.get("correo" + numBloque).split("::")[0];
                        password = remitentes.get("correo" + numBloque).split("::")[1];

                        if (mailSession == null) {
                            switch (serverCorreo) {
                                case 1:
                                    mailSession = eMailFacade.getMailSessionOffice(mailSession, remitente, password);
                                    break;
                                case 2:
                                    mailSession = eMailFacade.getMailSessionGmail(mailSession, remitente, password);
                                    break;
                            }

                            transport = mailSession.getTransport("smtp");
                            transport.connect(server, Integer.parseInt(port), remitente, password);
                        }
                    }

                    Address from = new InternetAddress(remitente);
                    envioDeCorreo(from, transport, detalleEnvio, mensaje, titulo, mailSession, server, port, remitente, password, detalleEnvio.getIdEnvio().getIdEnvio());
                    correosEnviandos++;

                    Logger.getLogger(ProcesoFacade.class.getName()).log(Level.INFO, "Numero {0} - {1}", new Object[]{remitente, cont});

                    correosEnviados.add(detalleEnvio.getIdDetalle());
                    cont++;
                    contReset++;

                    if (contReset == 71) {
                        transport.close();
                        contReset = 1;
                    }

                    if (correosEnviandos.equals(maxCorreoEnviado)) {
                        correosEnviandos = 0;
                        numBloque++;
                        if (numBloque == 42) {
                            numBloque = 1;
                        }

                        if (numBloque <= remitentes.size()) {
                            remitente = remitentes.get("correo" + numBloque).split("::")[0];
                            password = remitentes.get("correo" + numBloque).split("::")[1];

                            if (transport.isConnected()) {
                                transport.close();
                            }
                            mailSession = null;

                            if (mailSession == null) {
                                switch (serverCorreo) {
                                    case 1:
                                        mailSession = eMailFacade.getMailSessionOffice(mailSession, remitente, password);
                                        break;
                                    case 2:
                                        mailSession = eMailFacade.getMailSessionGmail(mailSession, remitente, password);
                                        break;
                                }

                                transport = mailSession.getTransport("smtp");
                                transport.connect(server, Integer.parseInt(port), remitente, password);

                            }
                        }
                    }
                }
            } catch (AddressException e) {
                e.printStackTrace();
            }
            transport.close();

            persistenciaFacade.actualizarDetalleEnviado(correosEnviados);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ProcesoFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(ProcesoFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
    }

    public void envioPendiente(String remitente, String password, String titulo, String mensaje,
            List<DetalleEnvio> lstDetalle,
            Transport transport, Session mailSession,
            String server, String port) {
        envio(remitente, password, mensaje, titulo, lstDetalle, transport, mailSession, server, port, "");
    }

    private HashMap<String, String> getRemitentes(String pathArchivo,
            String remitenteLogeado, String passwordLogeado) throws FileNotFoundException, IOException {
        HashMap<String, String> remitentes = new HashMap();
        File fTmp = new File(pathArchivo);
        InputStream input = new FileInputStream(fTmp);
        Workbook wb = WorkbookFactory.create(input);
        int cantidadRemitente = 1;
        if (wb.getNumberOfSheets() > 1) {
            Sheet sheet = wb.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getCell(0) != null) {
                    remitentes.put("correo" + cantidadRemitente, row.getCell(0).getStringCellValue().concat("::").concat(row.getCell(1).getStringCellValue()));
                    cantidadRemitente++;
                }
            }

        } else {
            remitentes.put("correo" + cantidadRemitente, remitenteLogeado.concat("::").concat(passwordLogeado));
        }

        input.close();

        return remitentes;
    }

    public void envioDeCorreo(Address from, Transport transport, DetalleEnvio detalleEnvio, String mensaje, String titulo,
            Session mailSession, String server, String port, String remitente, String password) throws MessagingException {
        if (transport.isConnected()) {

        } else {
            transport.connect();
        }

        try {
            String valores = detalleEnvio.getNip();
            String msjTemp = mensaje;

            for (String valor : valores.split("&&")) {
                msjTemp = msjTemp.replace(":".concat(valor.split("::")[0]).concat(":"), valor.split("::")[1]);
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

    public void envioDeCorreo(Address from, Transport transport, DetalleEnvio detalleEnvio, String mensaje, String titulo,
            Session mailSession, String server, String port, String remitente, String password, BigDecimal idEnvio) throws MessagingException, IOException {
        if (transport.isConnected()) {

        } else {
            transport.connect();
        }

        try {
            String valores = detalleEnvio.getNip();
            String msjTemp = mensaje;

            for (String valor : valores.split("&&")) {
                msjTemp = msjTemp.replace(":".concat(valor.split("::")[0]).concat(":"), valor.split("::")[1]);
            }

            MimeMessage message = new MimeMessage(mailSession);

            message.setFrom(from);

            InternetAddress[] address = {new InternetAddress(detalleEnvio.getCorreoDestinatario())};
            message.setRecipients(Message.RecipientType.TO, address);
            //message.setRecipients(Message.RecipientType.BCC, "miguel.sanchez@admin.mined.edu.sv");

            BodyPart messageBodyPart1 = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();
            messageBodyPart1.setContent(msjTemp, "text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart1);

            addImagenAlMensaje(idEnvio, multipart);

            message.setContent(multipart);
            message.setSubject(titulo, "UTF-8");
            message.saveChanges();

            transport.sendMessage(message, message.getAllRecipients());

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

    private void addImagenAlMensaje(BigDecimal idEnvio, Multipart multipart) {
        BodyPart messageBodyPart1;
        File folderImagenes = new File("/opt/soporte/envio_masivo/envio" + idEnvio.intValue() + "/");

        if (folderImagenes.exists()) {
            for (File imagen : folderImagenes.listFiles()) {
                try {
                    messageBodyPart1 = new MimeBodyPart();

                    DataSource fds = new FileDataSource(imagen);
                    messageBodyPart1.setDataHandler(new DataHandler(fds));
                    messageBodyPart1.addHeader("Content-ID", "<" + imagen.getName().substring(0, imagen.getName().indexOf(".")) + ">");

                    multipart.addBodyPart(messageBodyPart1);
                } catch (MessagingException ex) {
                    Logger.getLogger(ProcesoFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
