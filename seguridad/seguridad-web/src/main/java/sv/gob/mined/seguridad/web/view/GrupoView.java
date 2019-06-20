/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.web.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import sv.gob.mined.seguridad.api.facade.AplicacionFacade;
import sv.gob.mined.seguridad.model.Grupo;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class GrupoView implements Serializable {

    @ManagedProperty("#{bundle}")
    private ResourceBundle bundle;

    @EJB
    public AplicacionFacade appFacade;

    private List<Grupo> lstGrupo = new ArrayList();
    private Grupo grupoSelected = new Grupo();

    @PostConstruct
    public void init() {
        lstGrupo = appFacade.getLstGrupos();
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public List<Grupo> getLstGrupo() {
        return lstGrupo;
    }

    public void setLstGrupo(List<Grupo> lstGrupo) {
        this.lstGrupo = lstGrupo;
    }

    public Grupo getGrupoSelected() {
        return grupoSelected;
    }

    public void setGrupoSelected(Grupo grupoSelected) {
        this.grupoSelected = grupoSelected;
    }

    public void limpiar() {
        nuevo();
    }

    public void nuevo() {
        grupoSelected = new Grupo();
    }

    public void guardar() {
        appFacade.guardarGrupo(grupoSelected);
        grupoSelected = new Grupo();
    }
}
