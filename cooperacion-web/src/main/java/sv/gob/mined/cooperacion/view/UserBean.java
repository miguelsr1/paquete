/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.view;

import javax.inject.Named;
import java.io.Serializable;
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

    public void isAdmin(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        if (!"ADMIN".equals(fc.getExternalContext().getSessionMap().get("role"))) {
            ConfigurableNavigationHandler nav
                    = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();

            nav.performNavigation("access-denied");
        }
    }

    public void isCentroEscolar(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        if (!"CE".equals(fc.getExternalContext().getSessionMap().get("role"))) {
            ConfigurableNavigationHandler nav
                    = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();

            nav.performNavigation("access-denied.mined");
        }
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
