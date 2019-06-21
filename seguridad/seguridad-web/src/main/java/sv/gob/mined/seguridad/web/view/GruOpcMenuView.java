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
import sv.gob.mined.seguridad.api.facade.AplicacionFacade;
import sv.gob.mined.seguridad.model.Aplicacion;
import sv.gob.mined.seguridad.model.GruOpcMenu;
import sv.gob.mined.seguridad.model.Grupo;
import sv.gob.mined.seguridad.model.OpcionMenu;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class GruOpcMenuView implements Serializable {

    @EJB
    public AplicacionFacade appFacade;

    public Long idApp;
    public Long idGrupo;

    public GruOpcMenu gruOpcMenu = new GruOpcMenu();
    
    public List<OpcionMenu> lstOpcionMenuSelected = new ArrayList();

    public Long getIdApp() {
        return idApp;
    }

    public void setIdApp(Long idApp) {
        this.idApp = idApp;
    }

    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public GruOpcMenu getGruOpcMenu() {
        return gruOpcMenu;
    }

    public void setGruOpcMenu(GruOpcMenu gruOpcMenu) {
        this.gruOpcMenu = gruOpcMenu;
    }

    public List<Aplicacion> getLstAplicaciones() {
        return appFacade.getLstAplicaciones();
    }

    public List<Grupo> getLstGrupos() {
        return appFacade.getLstGrupos();
    }
    
    public List<OpcionMenu> getLstOpcionMenu(){
        return appFacade.getLstOpcionMenuNotInIdApp(idApp);
    }

    public List<OpcionMenu> getLstOpcionMenuSelected() {
        return lstOpcionMenuSelected;
    }

    public void setLstOpcionMenuSelected(List<OpcionMenu> lstOpcionMenuSelected) {
        this.lstOpcionMenuSelected = lstOpcionMenuSelected;
    }
    
    public void seleccionarOpcionMenu() {
        Map<String,Object> options = new HashMap<>();
        options.put("contentHeight", 400);
        options.put("contentWidth", 600);
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("closable", true);
        options.put("modal", true);
        PrimeFaces.current().dialog().openDynamic("/app/mantto/dialog/dlgOpcionMenu", options, null);
    }
}
