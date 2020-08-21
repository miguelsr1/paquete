/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.envio.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import sv.gob.mined.envio.facade.EMailFacade;
import sv.gob.mined.envio.facade.RegistrosFacade;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author MISanchez
 */
@ManagedBean
@ViewScoped
public class EnvioView {

    private Integer progeso;
    private String correoRemitente;
    private String remitente;
    private String password;
    private String usuario;

    private String titulo;
    private String mensaje;
    private String pathArchivo;

    private String idDominioCorreo = "2";
    private UploadedFile file;

    private Session mailSession;

    @EJB
    private RegistrosFacade registrosFacade;
    @EJB
    private EMailFacade eMailFacade;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("parametros");

    public EnvioView() {
    }

    public Integer getProgeso() {
        return progeso;
    }

    public void setProgeso(Integer progeso) {
        this.progeso = progeso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreoRemitente() {
        return correoRemitente;
    }

    public void setCorreoRemitente(String correoRemitente) {
        this.correoRemitente = correoRemitente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getIdDominioCorreo() {
        return idDominioCorreo;
    }

    public void setIdDominioCorreo(String idDominioCorreo) {
        if (idDominioCorreo != null) {
            this.idDominioCorreo = idDominioCorreo;
        }
    }

    public void handleFileUpload(FileUploadEvent file) throws FileNotFoundException, IOException {
        if (file != null) {
            pathArchivo = RESOURCE_BUNDLE.getString("path_archivo") + File.separator + file.getFile().getFileName();
            Path folder = Paths.get(pathArchivo);

            Path arc;

            if (folder.toFile().exists()) {
                arc = folder;
            } else {
                arc = Files.createFile(folder);
            }

            try (InputStream input = file.getFile().getInputstream()) {

                if (validarArchivo(input)) {
                    pathArchivo = folder.toString();
                    Files.copy(file.getFile().getInputstream(), arc, StandardCopyOption.REPLACE_EXISTING);

                } else {
                    JsfUtil.mensajeError("El archivo cargado no contiene el formato requerido");
                }
            }
        }
    }

    public void validacion() {
        String error = "";

        if (correoRemitente == null || correoRemitente.trim().isEmpty()) {
            error += "Debe de ingresar un correo remitente.<br/>";
        }

        if (idDominioCorreo.equals("1")) {
            error += "Debe de ingresar el dominio de la cuenta de correo a utilizar.<br/>";
        }

        if (password == null || password.trim().isEmpty()) {
            error += "Debe de ingresar una clave de acceso de la cuenta de correo a utilizar.<br/>";
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            error += "Debe de ingresar un Titulo del Mensaje.<br/>";
        }
        if (mensaje == null || mensaje.trim().isEmpty()) {
            error += "Debe de ingresar el Mensaje a enviar.<br/>";
        } else if (!mensaje.contains(":DOCENTE:")) {
            error += "El mensaje no contiene la palabra comodin <b>:DOCENTE:</b><br/>";
        }

        if (!error.isEmpty()) {
            JsfUtil.mensajeError("<br/>" + error);
        }
    }

    public void subirUnArchivo() throws FileNotFoundException, IOException {
        String error = "";

        if (correoRemitente == null || correoRemitente.trim().isEmpty()) {
            error += "Debe de ingresar un correo remitente.<br/>";
        }
        if (idDominioCorreo.equals("1")) {
            error += "Debe de ingresar el dominio de la cuenta de correo a utilizar.<br/>";
        }
        if (password == null || password.trim().isEmpty()) {
            error += "Debe de ingresar una clave de acceso de la cuenta de correo a utilizar.<br/>";
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            error += "Debe de ingresar un Titulo del Mensaje.<br/>";
        }
        if (mensaje == null || mensaje.trim().isEmpty()) {
            error += "Debe de ingresar el Mensaje a enviar.<br/>";
        } else if (!mensaje.contains(":DOCENTE:")) {
            error += "El mensaje no contiene la palabra comodin <b>:DOCENTE:</b><br/>";
        }
        if (file == null){
            error += "Debe de seleccionar un archivo con la lista de correos a enviar.<br/>";
        }
        if (!error.isEmpty()) {
            JsfUtil.mensajeError("<br/>" + error);
        }

        if (error.isEmpty()) {
            pathArchivo = RESOURCE_BUNDLE.getString("path_archivo") + File.separator + file.getFileName();
            Path folder = Paths.get(pathArchivo);
            Path arc;

            if (folder.toFile().exists()) {
                arc = folder;
            } else {
                arc = Files.createFile(folder);
            }

            try (InputStream input = file.getInputstream()) {
                if (validarArchivo(input)) {
                    pathArchivo = folder.toString();
                    Files.copy(file.getInputstream(), arc, StandardCopyOption.REPLACE_EXISTING);

                    BigDecimal idEnvio = enviarCorreos();
                    
                    //registrosFacade.enviarCorreos(idEnvio, mailSession);
                    
                    JsfUtil.mensajeInformacion("Se ha iniciado el proceso de envio, puede cerrar la ventana y el proceso continuará ejecutandose.");
                } else {
                    JsfUtil.mensajeError("El archivo cargado no contiene el formato requerido");
                }
            }
        }
    }

    private Boolean validarArchivo(InputStream input) throws IOException {
        Workbook wb = WorkbookFactory.create(input);
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cellNip = row.getCell(0);
        Cell cellNombre = row.getCell(1);
        Cell cellCorreo = row.getCell(2);

        try {
            return cellNip.getStringCellValue().toUpperCase().equals("NIP")
                    && cellNombre.getStringCellValue().toUpperCase().equals("NOMBRE")
                    && cellCorreo.getStringCellValue().toUpperCase().equals("CORREO");
        } catch (Exception e) {
            return false;
        }

    }

    public BigDecimal enviarCorreos() throws FileNotFoundException, IOException {
        File fTmp = new File(pathArchivo);
        InputStream input = new FileInputStream(fTmp);
        DecimalFormat df = new DecimalFormat("#0");
        
        String nip;
        String nombre;
        String correo;
        Boolean primerCorreo = true;
        Boolean sendCorreo;
        BigDecimal idEnvio = BigDecimal.ZERO;

        remitente = correoRemitente.concat("@").concat(idDominioCorreo.equals("1") ? "mined.gob.sv" : "admin.mined.edu.sv");

        Workbook wb = WorkbookFactory.create(input);

        Sheet sheet = wb.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (row.getRowNum() != 0) {
                
                switch(row.getCell(0).getCellType()){
                    case STRING:
                        nip = row.getCell(0).getStringCellValue();
                        break;
                    case NUMERIC:
                        nip = String.valueOf(df.format(row.getCell(0).getNumericCellValue()));
                        break;
                    default:
                        nip = "";
                        break;
                }
                
                nombre = row.getCell(1).getStringCellValue();
                correo = row.getCell(2).getStringCellValue();

                String correoMensaje = mensaje.replace(":DOCENTE:", nombre.concat(" - ").concat(nip));

                System.out.println(correoMensaje);
                /*if (primerCorreo) {
                    sendCorreo = eMailFacade.enviarMail(row.getCell(2).getStringCellValue(),
                            remitente,
                            titulo,
                            correoMensaje,
                            idDominioCorreo.equals("1") ? getMailSessionMined() : getMailSessionOffice());

                    if (!sendCorreo) {
                        JsfUtil.mensajeError("Ocurrio un error en el envio del primero correo, por favor verifique la cuenta de correo y la contraseña");
                        return BigDecimal.ZERO;
                    } else {
                        //Crear registro maestro en la base
                        idEnvio = registrosFacade.guardarEnvio(remitente, titulo, mensaje, pathArchivo);
                        registrosFacade.guardarCorreoEnviado(nip, nombre, correo, idEnvio, true);
                    }
                    primerCorreo = false;
                } else {
                    registrosFacade.guardarCorreoEnviado(nip, nombre, correo, idEnvio, false);
                }*/
            }
        }
        
        return idEnvio;
    }

    public void upload() throws FileNotFoundException, IOException {
        if (file != null) {
            Path folder = Paths.get(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + file.getFileName());

            Files.createFile(folder);
        }
    }

    private Session getMailSessionOffice() {
        if (mailSession == null) {
            Properties configEmail = new Properties();

            configEmail.put("mail.smtp.auth", "true");
            configEmail.put("mail.smtp.starttls.enable", "true");

            configEmail.put("mail.smtp.host", "smtp.office365.com");
            configEmail.put("mail.smtp.port", "587");

            configEmail.put("mail.user", remitente);
            configEmail.put("mail.user.pass", password);
            configEmail.put("mail.from", remitente);

            mailSession = Session.getInstance(configEmail, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });
        }

        return mailSession;
    }

    private Session getMailSessionMined() {
        if (mailSession == null) {
            Properties configEmail = new Properties();

            configEmail.put("mail.smtp.auth", "true");
            configEmail.put("mail.smtp.starttls.enable", "true");

            configEmail.put("mail.smtp.host", "svr2k13mail01.mined.gob.sv");
            configEmail.put("mail.smtp.port", "2525");

            configEmail.put("mail.user", remitente);
            configEmail.put("mail.user.pass", password);
            configEmail.put("mail.from", remitente);

            mailSession = Session.getInstance(configEmail, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });
        }

        return mailSession;
    }
    
    private Integer updateProgress(Integer progress) {
        if(progress == null) {
            progress = 0;
        }
        else {
            progress = progress + (int)(Math.random() * 35);
             
            if(progress > 100)
                progress = 100;
        }
         
        return progress;
    }
}
