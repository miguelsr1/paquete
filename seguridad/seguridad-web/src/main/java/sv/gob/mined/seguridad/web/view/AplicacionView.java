/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.web.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import sv.gob.mined.seguridad.api.facade.AplicacionFacade;
import sv.gob.mined.seguridad.model.Aplicacion;
import sv.gob.mined.seguridad.model.Modulo;
import sv.gob.mined.seguridad.web.util.JsfUtil;
import sv.gob.mined.seguridad.web.view.util.BundleView;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class AplicacionView extends BundleView implements Serializable {

    private Aplicacion aplicacion;
    private List<Modulo> lstModulos;

    @EJB
    public AplicacionFacade appFacade;

    @PostConstruct
    public void init() {
        aplicacion = new Aplicacion();
        lstModulos = new ArrayList();
        aplicacion = appFacade.findEntity(Aplicacion.class, 2l);
        
        lstModulos = aplicacion.getModuloList();
    }

    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    public List<Modulo> getLstModulos() {
        return lstModulos;
    }

    public void setLstModulos(List<Modulo> lstModulos) {
        this.lstModulos = lstModulos;
    }

    public void limpiar() {
    }

    public void nuevo() {
    }

    public void guardar() {
        if(appFacade.guardarAplicacion(aplicacion)){
            JsfUtil.mensajeInformacion(getBundle().getString("msj.insercion"));
        }else{
            JsfUtil.mensajeError(getBundle().getString("msj.error"));
        }
    }

    public void agregarModulo() {
        Map<String, Object> options = new HashMap<>();
        options.put("contentHeight", 170);
        options.put("contentWidth", 300);
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("closable", true);
        options.put("modal", true);
        options.put("showEffect", "fade");
        options.put("hideEffect", "fade");

        PrimeFaces.current().dialog().openDynamic("/app/mantto/dialog/dlgModulo", options, null);
    }

    public void onAgregarModulo(SelectEvent event) {
        if (event.getObject() != null && event.getObject() instanceof Modulo) {
            Modulo mod = (Modulo) event.getObject();
            mod.setIdAplicacion(aplicacion);
            
            if(aplicacion.getModuloList().isEmpty()){
                aplicacion.setModuloList(new ArrayList());
            }
            
            aplicacion.getModuloList().add(mod);
        }
    }
}
