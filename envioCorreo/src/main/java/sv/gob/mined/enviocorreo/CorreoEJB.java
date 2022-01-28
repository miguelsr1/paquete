/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.enviocorreo;

import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import sv.gob.mined.boleta.ejb.LeerBoletasEJB;

/**
 *
 * @author DesarrolloPc
 */
@Stateless
@LocalBean
public class CorreoEJB {
    
    @EJB
    LeerBoletasEJB leerBoletasEJB;
    
    @Schedule(minute = "15", hour = "8")
    public void iniciarEnvioDeBoletas(){
        String usuario = "boletas02@admin.mined.edu.sv";
        String clave = "Fot12752";
        Session mailSession;
        
        Properties configEmail = new Properties();

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
                return new PasswordAuthentication(usuario, clave);
            }
        });
        
        leerBoletasEJB.leerArchivosPendientes(mailSession, "02", usuario);
    }
}
