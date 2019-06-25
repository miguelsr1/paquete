/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.web.view.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import sv.gob.mined.seguridad.api.facade.AplicacionFacade;
import sv.gob.mined.seguridad.model.dto.OpcionMenuDto;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class SelectOpcionMenuView implements Serializable {

    private Long idApp;

    @EJB
    public AplicacionFacade appFacade;

    @PostConstruct
    public void init() {
        String idAppString = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("idApp");
        if (idAppString != null && !idAppString.isEmpty()) {
            idApp = Long.parseLong(idAppString);
        }
    }

    public List<OpcionMenuDto> lstOpcionMenuSelected = new ArrayList();

    public List<OpcionMenuDto> getLstOpcionMenuSelected() {
        return lstOpcionMenuSelected;
    }

    public void setLstOpcionMenuSelected(List<OpcionMenuDto> lstOpcionMenuSelected) {
        this.lstOpcionMenuSelected = lstOpcionMenuSelected;
    }

    public List<OpcionMenuDto> getLstOpcionMenu() {
        return appFacade.getLstOpcionMenuNotInIdApp(idApp);
    }

    public void agregarOpciones() {
        PrimeFaces.current().dialog().closeDynamic(lstOpcionMenuSelected);
    }
}
