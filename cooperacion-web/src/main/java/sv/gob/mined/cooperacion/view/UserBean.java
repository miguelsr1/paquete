/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.view;

import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author MISanchez
 */
@Named
@ViewScoped
public class UserBean implements Serializable {

    private void isSessionValida(String role) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.getExternalContext().getSessionMap().get("role") == null || !role.equals(fc.getExternalContext().getSessionMap().get("role"))) {
            ConfigurableNavigationHandler nav
                    = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();

            nav.performNavigation("access-denied");
        }
    }

    public void validarAdmin(ComponentSystemEvent event) {
        isSessionValida("ADMIN");
    }

    public void validarCentroEscolar(ComponentSystemEvent event) {
        isSessionValida("CE");
    }

    public Boolean getCentroEscolar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return "CE".equals(fc.getExternalContext().getSessionMap().get("role"));
    }

    public String getPerfilUsuario() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getExternalContext().getSessionMap().get("role").toString();
    }
}
