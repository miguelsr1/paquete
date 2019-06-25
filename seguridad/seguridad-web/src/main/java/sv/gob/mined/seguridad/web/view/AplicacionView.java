/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.web.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import sv.gob.mined.seguridad.model.Aplicacion;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class AplicacionView implements Serializable{
    
    private Aplicacion aplicacion;
    
    @PostConstruct
    public void init(){
        aplicacion = new Aplicacion();
    }

    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }
    
    public void limpiar(){
    }
    public void nuevo(){
    }
    public void guardar(){
    }
    
    public void agregarModulo(){
        Map<String, Object> options = new HashMap<>();
        options.put("contentHeight", 140);
        options.put("contentWidth", 250);
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("closable", true);
        options.put("modal", true);

        PrimeFaces.current().dialog().openDynamic("/app/mantto/dialog/dlgModulo", options, null);
    }
}
