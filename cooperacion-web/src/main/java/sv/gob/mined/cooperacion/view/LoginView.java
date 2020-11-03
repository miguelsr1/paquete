/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import sv.gob.mined.cooperacion.facade.CatalogoFacade;
import sv.gob.mined.cooperacion.facade.EMailFacade;
import sv.gob.mined.cooperacion.model.Director;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class LoginView implements Serializable {

    private Boolean correoValido = false;
    private String correoRemitente;
    private String idDominioCorreo = "2";
    private String dominio;
    private String password;

    private String remitente;
    private String port;
    private String server;
    private Session mailSession;
    private Transport transport;

    @Inject
    private EMailFacade eMailFacade;
    @Inject
    private CatalogoFacade catalogoFacade;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().clear();
    }

    public Boolean getCorreoValido() {
        return correoValido;
    }

    public void setCorreoValido(Boolean correoValido) {
        this.correoValido = correoValido;
    }

    public String getCorreoRemitente() {
        return correoRemitente;
    }

    public void setCorreoRemitente(String correoRemitente) {
        this.correoRemitente = correoRemitente;
    }

    public String getIdDominioCorreo() {
        return idDominioCorreo;
    }

    public void setIdDominioCorreo(String idDominioCorreo) {
        this.idDominioCorreo = idDominioCorreo;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String validarCrendecialesDelCorreo() {
        String url = "";
        try {
            if (correoRemitente != null && password != null) {
                if (idDominioCorreo.equals("1")) {
                    remitente = correoRemitente.concat("@").concat("mined.gob.sv");
                    port = "2525";
                    server = "svr2k13mail01.mined.gob.sv";
                    mailSession = eMailFacade.getMailSessionMined(mailSession, dominio, password, remitente);
                } else {
                    remitente = correoRemitente.concat("@").concat("admin.mined.edu.sv");
                    port = "587";
                    server = "smtp.office365.com";
                    mailSession = eMailFacade.getMailSessionOffice(mailSession, remitente, password);
                }

                transport = mailSession.getTransport("smtp");
                transport.connect(server, Integer.parseInt(port), remitente, password);

                correoValido = true;

                transport.close();

                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("sessionMail", mailSession);
                context.getExternalContext().getSessionMap().put("server", server);
                context.getExternalContext().getSessionMap().put("port", port);
                context.getExternalContext().getSessionMap().put("remitente", remitente);
                context.getExternalContext().getSessionMap().put("password", password);

                Director director = catalogoFacade.findDirectorByEmail(remitente);
                if (director != null) {
                    context.getExternalContext().getSessionMap().put("director", director);
                    url = "/app/datosCe?faces-redirect=true";
                }else{
                    url = "/app/listadoProyectos?faces-redirect=true";
                }
            }
        } catch (NoSuchProviderException ex) {
            JsfUtil.mensajeError("Error en el usuario o  clave de acceso.");
            correoValido = false;
        } catch (MessagingException ex) {
            JsfUtil.mensajeError("Error en el usuario o  clave de acceso.");
            correoValido = false;
        }

        return url;
    }
}
