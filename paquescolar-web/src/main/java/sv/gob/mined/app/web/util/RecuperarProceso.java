/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.util;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sv.gob.mined.app.web.controller.AnhoProcesoController;
import sv.gob.mined.app.web.controller.DatosGeograficosController;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;

/**
 *
 * @author misanchez
 */
@ManagedBean(name = "recuperarProceso")
@SessionScoped
public class RecuperarProceso implements Serializable {

    private ProcesoAdquisicion procesoAdquisicion = new ProcesoAdquisicion();
    private String departamento;

    @PostConstruct
    public void init1() {
        VarSession.setVariableSessionED("0");
        recuperarProcesoAdq();
    }

    public void recuperarProcesoAdq() {
        procesoAdquisicion = ((AnhoProcesoController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "anhoProcesoController")).getProceso();
        if (procesoAdquisicion == null || procesoAdquisicion.getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de adquisición.");
        }

        departamento = ((DatosGeograficosController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "datosGeograficosController")).getCodigoDepartamento();
    }

    public ProcesoAdquisicion getProcesoAdquisicion() {
        return procesoAdquisicion;
    }

    public void setProcesoAdquisicion(ProcesoAdquisicion procesoAdquisicion) {
        this.procesoAdquisicion = procesoAdquisicion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
