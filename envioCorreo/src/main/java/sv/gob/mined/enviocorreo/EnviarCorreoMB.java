/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.enviocorreo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import sv.gob.mined.boleta.ejb.LeerBoletasEJB;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class EnviarCorreoMB implements Serializable {

    private String codigoUltimo;

    private Session mailSession;

    private String usuario = "miguel.sanchez@admin.mined.edu.sv";
    private String clave = "miguelsr15.";
    private String mesAnho = "12_2019";
    private Properties config = new Properties();
    private Properties configEmail = new Properties();

    @EJB
    private LeerBoletasEJB leerBoletasEJB;
    
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("parametros");

    @PostConstruct
    public void init() {
        //usuario = context.getExternalContext().getSessionMap().get("usuario").toString();

        mesAnho = "12_2019";

        config = chargeEmailsProperties("config");

        configEmail.put("mail.smtp.auth", "true");
        configEmail.put("mail.smtp.starttls.enable", "true");

        configEmail.put("mail.smtp.host", "smtp.office365.com");
        configEmail.put("mail.smtp.port", "587");

        configEmail.put("mail.user", usuario);
        configEmail.put("mail.user.pass", clave);
        configEmail.put("mail.from", usuario);

        mailSession = Session.getInstance(configEmail, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario,
                        clave);
            }
        });
    }

    public Properties chargeEmailsProperties(String nombre) {
        Properties info = null;
        try {
            info = new Properties();
            try (InputStream input = EnviarCorreoMB.class.getClassLoader().getResourceAsStream(nombre + ".properties")) {
                info.load(input);
            }

        } catch (IOException ex) {
            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;
    }

    public String getCodigoUltimo() {
        return codigoUltimo;
    }

    public void setCodigoUltimo(String codigoUltimo) {
        this.codigoUltimo = codigoUltimo;
    }

    public void buscarCodigoUltimo() {
        leerBoletasEJB.leerArchivosPendientesByUltimoProcesado(mailSession, "12", usuario, codigoUltimo, mesAnho);
    }
}
