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
import sv.gob.mined.app.web.controller.ParametrosMB;
import sv.gob.mined.app.web.controller.pagoprov.modulo.ProveedorMB;
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
        if (VarSession.isVariableSession("idEmpresa")) {
            ProveedorMB proveedorMB = ((ProveedorMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                    getValue(FacesContext.getCurrentInstance().getELContext(), null, "proveedorMB"));
            procesoAdquisicion = proveedorMB.getAnho().getProcesoAdquisicionList().get(0);
            ((ParametrosMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                    getValue(FacesContext.getCurrentInstance().getELContext(), null, "parametrosMB")).setProceso(procesoAdquisicion);
            
        } else {
            procesoAdquisicion = ((ParametrosMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                    getValue(FacesContext.getCurrentInstance().getELContext(), null, "parametrosMB")).getProceso();
        }
        
        
        if (procesoAdquisicion == null || procesoAdquisicion.getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de adquisición.");
        }

        departamento = ((ParametrosMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "parametrosMB")).getCodigoDepartamento();
    }

    public ProcesoAdquisicion getProcesoAdquisicion() {
        procesoAdquisicion = ((ParametrosMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "parametrosMB")).getProceso();
        return procesoAdquisicion;
    }

    public String getDepartamento() {
        departamento = ((ParametrosMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "parametrosMB")).getCodigoDepartamento();
        return departamento;
    }
}
