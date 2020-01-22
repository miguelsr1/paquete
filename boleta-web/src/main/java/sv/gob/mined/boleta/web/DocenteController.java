/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sv.gob.mined.boleta.ejb.PersistenciaFacade;
import sv.gob.mined.boleta.model.CorreoDocente;
import sv.gob.mined.boleta.model.DominiosCorreo;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class DocenteController implements Serializable {

    public String getMensaje() {
        return mensaje;
    }

    private String mensaje;
    private String criterioBusqueda;
    private String correoSinDominio;
    private Integer idDominio;
    private CorreoDocente correoDocente = new CorreoDocente();
    private CorreoDocente docenteSeleccionado = new CorreoDocente();
    private List<CorreoDocente> lstCorreos = new ArrayList();

    @EJB
    private PersistenciaFacade persistenciaFacade;

    public DocenteController() {
    }

    public String getCorreoSinDominio() {
        return correoSinDominio;
    }

    public void setCorreoSinDominio(String correoSinDominio) {
        this.correoSinDominio = correoSinDominio;
    }

    public String getCriterioBusqueda() {
        return criterioBusqueda;
    }

    public void setCriterioBusqueda(String criterioBusqueda) {
        this.criterioBusqueda = criterioBusqueda;
    }

    public CorreoDocente getCorreoDocente() {
        return correoDocente;
    }

    public void setCorreoDocente(CorreoDocente correoDocente) {
        this.correoDocente = correoDocente;
    }

    public CorreoDocente getDocenteSeleccionado() {
        return docenteSeleccionado;
    }

    public void setDocenteSeleccionado(CorreoDocente docenteSeleccionado) {
        this.docenteSeleccionado = docenteSeleccionado;
    }

    public Integer getIdDominio() {
        return idDominio;
    }

    public void setIdDominio(Integer idDominio) {
        this.idDominio = idDominio;
    }

    public List<DominiosCorreo> getLstDominiosCorreo() {
        return persistenciaFacade.getLstDominiosCorreo();
    }

    public List<CorreoDocente> getLstCorreoDocente() {
        return lstCorreos;
    }

    public void buscar() {
        lstCorreos = persistenciaFacade.getLstCorreoDocenteByCriterio(criterioBusqueda);
        if (lstCorreos.isEmpty()) {
            mensaje = "No se han encontrado coincidencias";
        } else {
            mensaje = "";
        }

    }

    public void cerrarDlg() {
        correoDocente = docenteSeleccionado;
        correoSinDominio = correoDocente.getCorreoElectronico().split("@")[0];
        switch (correoDocente.getCorreoElectronico().split("@")[1]) {
            case "@docentes.edu.sv":
                idDominio = 1;
                break;
            case "@docentes.mined.edu.sv":
                idDominio = 2;
                break;
            case "@admin.mined.edu.sv":
                idDominio = 3;
                break;
        }
    }

    public void guardarDocente() {
        FacesContext context = FacesContext.getCurrentInstance();
        correoDocente.setPrimerNombre(correoDocente.getPrimerNombre().toUpperCase());
        correoDocente.setPrimerApellido(correoDocente.getPrimerApellido().toUpperCase());
        correoDocente.setCorreoElectronico(correoSinDominio.concat(getDominioCorreo(idDominio)).toLowerCase());
        int valor = persistenciaFacade.guardarDocente(correoDocente, context.getExternalContext().getSessionMap().get("usuario").toString());
        if (valor == 1) {
            JsfUtil.mensajeUpdate();
        } else {
            JsfUtil.mensajeAlerta("Ya existe un usuario con el NIP o correo electronico ingresado");
        }
    }

    private String getDominioCorreo(Integer idDominio) {
        switch (idDominio) {
            case 1:
                return "@docentes.edu.sv";
            case 2:
                return "@docentes.mined.edu.sv";
            case 3:
                return "@admin.mined.edu.sv";
            default:
                return "";
        }
    }

    public void nuevoDocente() {
        correoDocente = new CorreoDocente();
    }

    public void modificarDocente() {
        criterioBusqueda = "";
        lstCorreos.clear();
    }
}
