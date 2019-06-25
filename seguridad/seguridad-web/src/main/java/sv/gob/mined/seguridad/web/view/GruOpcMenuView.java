/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.web.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import sv.gob.mined.seguridad.model.GruApp;
import sv.gob.mined.seguridad.model.GruOpcMenu;
import sv.gob.mined.seguridad.model.Grupo;
import sv.gob.mined.seguridad.model.dto.OpcionMenuDto;
import sv.gob.mined.seguridad.web.util.JsfUtil;
import sv.gob.mined.seguridad.web.view.util.BundleView;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class GruOpcMenuView extends BundleView implements Serializable {

    @EJB
    public AplicacionFacade appFacade;

    public Long idApp;
    public Long idGrupo;

    public GruOpcMenu gruOpcMenu = new GruOpcMenu();

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

    public void seleccionarOpcionMenu() {
        Map<String, Object> options = new HashMap<>();
        options.put("contentHeight", 400);
        options.put("contentWidth", 900);
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("closable", true);
        options.put("modal", true);

        Map<String, List<String>> params = new HashMap();
        List<String> values = new ArrayList();
        values.add(idApp.toString());
        params.put("idApp", values);

        PrimeFaces.current().dialog().openDynamic("/app/mantto/dialog/dlgOpcionMenu", options, params);
    }

    public void onAgregarOpcMenu(SelectEvent event) {
        GruApp gruApp = appFacade.getGruAppByIdAppAndIdGru(idApp, idGrupo, "MISANCHEZ");

        List<OpcionMenuDto> lst = (List<OpcionMenuDto>) event.getObject();
        List<Long> lstIdsOpcMenuToUsuario = new ArrayList();

        for (OpcionMenuDto opc : lst) {
            //registrar la opción del menu al gruApp
            lstIdsOpcMenuToUsuario.add(opc.getIdOpcMenu());

            //verificar que el gruApp ya cuente con la opcion padre
            if (opc.getPadreIdOpcMenu() != null) {
                //si el gruApp no cuenta con la opción padre, se debe de agregar
                lstIdsOpcMenuToUsuario.addAll(appFacade.validarOpcPadreToGruApp(opc.getPadreIdOpcMenu(), idGrupo, lstIdsOpcMenuToUsuario));
            }
        }

        appFacade.guardarOpcMenuToGruApp(lstIdsOpcMenuToUsuario, gruApp.getIdGruApp());

        JsfUtil.mensajeInformacion(getBundle().getString("msj.insercion"));
    }
}
