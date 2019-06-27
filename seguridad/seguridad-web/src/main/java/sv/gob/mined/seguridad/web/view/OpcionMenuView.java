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
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import sv.gob.mined.seguridad.api.facade.AplicacionFacade;
import sv.gob.mined.seguridad.model.Aplicacion;
import sv.gob.mined.seguridad.model.Modulo;
import sv.gob.mined.seguridad.model.OpcionMenu;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class OpcionMenuView implements Serializable {

    public Long idApp;
    public Long idModulo;
    public List<OpcionMenu> lstOpcionMenu = new ArrayList();

    @EJB
    public AplicacionFacade appFacade;

    public Long getIdApp() {
        return idApp;
    }

    public void setIdApp(Long idApp) {
        this.idApp = idApp;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }

    public List<OpcionMenu> getLstOpcionMenu() {
        return lstOpcionMenu;
    }

    public void setLstOpcionMenu(List<OpcionMenu> lstOpcionMenu) {
        this.lstOpcionMenu = lstOpcionMenu;
    }

    public List<Aplicacion> getLstAplicaciones() {
        return appFacade.getLstAplicaciones();
    }

    public List<Modulo> getLstModulos() {
        return appFacade.getLstModulosByIdApp(idApp);
    }

    public void limpiar() {
    }

    public void nuevo() {
    }

    public void guardar() {
    }
    
    public void agregarOpcionMenu(){
        Map<String, Object> options = new HashMap<>();
        options.put("contentHeight", 400);
        options.put("contentWidth", 900);
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("closable", true);
        options.put("modal", true);
        options.put("showEffect", "fade");
        options.put("hideEffect", "fade");

        PrimeFaces.current().dialog().openDynamic("/app/mantto/dialog/dlgOpcionMenu", options, null);
    }
    
    public void onAgregarOpcMenu(SelectEvent event){
        
    }
    
    public void actualizarTabla(){
        lstOpcionMenu = appFacade.getLstOpcionMenuByIdAppAndIdMod(idApp, idModulo);
    }
}
