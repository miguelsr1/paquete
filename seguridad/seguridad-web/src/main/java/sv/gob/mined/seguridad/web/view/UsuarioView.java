/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.web.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.model.DualListModel;
import sv.gob.mined.seguridad.api.facade.AplicacionFacade;
import sv.gob.mined.seguridad.model.GruApp;
import sv.gob.mined.seguridad.model.Usuario;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class UsuarioView implements Serializable {

    @EJB
    public AplicacionFacade appFacade;

    public String pass1, pass2;

    public Usuario usuario = new Usuario();

    private DualListModel<GruApp> appGrpDualListModel;

    @PostConstruct
    public void init() {
        usuario = appFacade.getUsuarioByLogin("MISANCHEZ");
        inicializarModelList();
    }
    
    private void inicializarModelList(){
        List<GruApp> lstAllAppGrp = appFacade.getLstAppGrp();
        List<GruApp> lstAllAppGrpByUser = appFacade.getLstAppGrpByUsu(usuario);

        appGrpDualListModel = new DualListModel(lstAllAppGrp, lstAllAppGrpByUser);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public DualListModel<GruApp> getAppGrpDualListModel() {
        return appGrpDualListModel;
    }

    public void setAppGrpDualListModel(DualListModel<GruApp> appGrpDualListModel) {
        this.appGrpDualListModel = appGrpDualListModel;
    }

    public void limpiar() {
        nuevo();
    }

    public void nuevo() {
        usuario = new Usuario();
    }

    public void guardar() {
        usuario.setClaveAcceso(pass1);
        appFacade.guardarUsuario(usuario);
        appFacade.guardarUsuGruApp(appGrpDualListModel.getTarget(), usuario);
        
        usuario = new Usuario();
        inicializarModelList();
    }

    @FacesConverter(forClass = GruApp.class, value = "gruAppConverter")
    public static class GruAppConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioView controller = (UsuarioView) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioView");

            return controller.appFacade.getGruAppById(getKey(value));
        }

        Long getKey(String value) {
            return Long.parseLong(value);
        }

        String getStringKey(Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof GruApp) {
                GruApp o = (GruApp) object;
                return getStringKey(o.getIdGruApp());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + GruApp.class.getName());
            }
        }
    }
}
