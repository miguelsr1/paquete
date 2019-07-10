/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.web.view;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import sv.gob.mined.seguridad.api.facade.AplicacionFacade;
import sv.gob.mined.seguridad.api.facade.AplicacionService;
import sv.gob.mined.seguridad.model.Usuario;

/**
 *
 * @author misanchez
 */
@Named
public class LoginView implements Serializable {

    @Inject
    private Usuario usuario;
    @Inject
    private Principal principal;

    @Inject
    public AplicacionService appFacade;

    @PostConstruct
    public void init() {
        principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        usuario = appFacade.getUsuarioByLogin(principal.getName());

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void logout() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().clear();
            context.getExternalContext().invalidateSession();

            ExternalContext externalContext = context.getExternalContext();

            externalContext.redirect(((ServletContext) externalContext.getContext()).getContextPath() + "/app/principal.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
