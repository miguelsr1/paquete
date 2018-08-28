/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.credito;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.paquescolar.ejb.CreditosEJB;
import sv.gob.mined.paquescolar.ejb.LoginEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;
import sv.gob.mined.paquescolar.model.Usuario;
import sv.gob.mined.paquescolar.model.UsuarioEntidadFinanciera;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class UsuarioEntidadFinancieraController implements Serializable{

    private UsuarioEntidadFinanciera current;
    private Usuario usuario;
    private Boolean mostrarDetalle = false;
    private List<EntidadFinanciera> lstEntUsuario = new ArrayList<EntidadFinanciera>();
    private List<EntidadFinanciera> lstEntDisponibles = new ArrayList<EntidadFinanciera>();
    private DualListModel<EntidadFinanciera> model = new DualListModel<EntidadFinanciera>();
    private EntidadFinanciera entEnt;
    private EntidadFinanciera entSal;
    @EJB
    public CreditosEJB creditosEJB;
    @EJB
    public LoginEJB loginEJB;
    @EJB
    public UtilEJB utilEJB;

    /**
     * Creates a new instance of UsuarioEntidadFinancieraController
     */
    public UsuarioEntidadFinancieraController() {
    }

    public UsuarioEntidadFinanciera getSelected() {
        if (current == null) {
            current = new UsuarioEntidadFinanciera();
        }
        return current;
    }

    public List<Usuario> getLstUsuario() {
        return loginEJB.findUsuarios();
    }

    public void buscarEntFinanUsuario(Usuario usuario) {
        lstEntUsuario = creditosEJB.getlstEntFinanUsuario(usuario);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void onRowSelect(SelectEvent event) {
        buscarEntFinanUsuario(usuario);
        buscarEntDisponibles();
        mostrarDetalle = true;
    }

    public void onRowSelectEntSal(SelectEvent event) {
        lstEntUsuario.add((EntidadFinanciera) event.getObject());
        lstEntDisponibles.remove((EntidadFinanciera) event.getObject());
    }

    public void onRowSelectEntEnt(SelectEvent event) {
        lstEntDisponibles.add((EntidadFinanciera) event.getObject());
        lstEntUsuario.remove((EntidadFinanciera) event.getObject());
    }

    public List<EntidadFinanciera> getLstEntUsuario() {
        return lstEntUsuario;
    }

    public void setLstEntUsuario(List<EntidadFinanciera> lstUsuEntFina) {
        this.lstEntUsuario = lstUsuEntFina;
    }

    private void buscarEntDisponibles() {
        String codigos = "";
        for (EntidadFinanciera usuEnt : lstEntUsuario) {
            codigos = codigos.concat("'").concat(usuEnt.getCodEntFinanciera()).concat("',");
        }

        if (lstEntUsuario.isEmpty()) {
            lstEntDisponibles = creditosEJB.findEntidadFinancieraEntities((short) 0);
        } else {
            codigos = codigos.substring(0, codigos.length() - 1);
            lstEntDisponibles = creditosEJB.findEntidadFinancieraEntitiesByCodigo(codigos);
            model = new DualListModel<EntidadFinanciera>(lstEntDisponibles, lstEntUsuario);
        }
    }

    public List<EntidadFinanciera> getLstEntDisponibles() {
        return lstEntDisponibles;
    }

    public void setLstEntDisponibles(List<EntidadFinanciera> lstEntDisponibles) {
        this.lstEntDisponibles = lstEntDisponibles;
    }

    public void setModelEntidadDisponible(DualListModel<EntidadFinanciera> model) {
        this.model = model;
    }

    public DualListModel<EntidadFinanciera> getModelEntidadDisponible() {
        return model;
    }

    public Boolean getMostrarDetalle() {
        return mostrarDetalle;
    }

    public void setMostrarDetalle(Boolean mostrarDetalle) {
        this.mostrarDetalle = mostrarDetalle;
    }

    public String guardar() {
        creditosEJB.deleteEntFinanUsuario(usuario);

        for (EntidadFinanciera object : lstEntUsuario) {

            UsuarioEntidadFinanciera usuEnt = new UsuarioEntidadFinanciera();
            usuEnt.setCodEntFinanciera(utilEJB.find(EntidadFinanciera.class, object.getCodEntFinanciera()));
            usuEnt.setIdUsuario(usuario);

            creditosEJB.create(usuEnt);
        }
        JsfUtil.mensajeInformacion("Operación realizada con exito.");

        return "";
    }

    public EntidadFinanciera getEntEnt() {
        return entEnt;
    }

    public void setEntEnt(EntidadFinanciera entEnt) {
        this.entEnt = entEnt;
    }

    public EntidadFinanciera getEntSal() {
        return entSal;
    }

    public void setEntSal(EntidadFinanciera entSal) {
        this.entSal = entSal;
    }
}
