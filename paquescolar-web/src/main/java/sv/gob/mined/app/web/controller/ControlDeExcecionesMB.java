/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.application.exceptionhandler.ExceptionInfo;
import sv.gob.mined.paquescolar.model.pojos.Descriptor;

/**
 *
 * @author misanchez
 */
@ManagedBean
@SessionScoped
public class ControlDeExcecionesMB {

    private Boolean mostrarPanelErrores = false;
    private String mensaje = "";
    private List<Descriptor> lstErrores = new ArrayList();

    /**
     * Creates a new instance of ControlDeExcecionesMB
     */
    public ControlDeExcecionesMB() {
    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getNumeroDeErrores() {
        return lstErrores.size();
    }

    public List<Descriptor> getLstErrores() {
        return lstErrores;
    }

    public void setLstErrores(List<Descriptor> lstErrores) {
        this.lstErrores = lstErrores;
    }

    public Boolean getMostrarPanelErrores() {
        return mostrarPanelErrores;
    }

    public void setMostrarPanelErrores(Boolean mostrarPanelErrores) {
        this.mostrarPanelErrores = mostrarPanelErrores;
    }
    // </editor-fold>

    public void limpiarPanelErrores() {
        mensaje = "";
        mostrarPanelErrores = false;
        lstErrores.clear();
    }

    public void agregarErrorAPanel(ExceptionInfo detalleError) {
        if (detalleError != null) {
            mensaje = "Número de errores: ";
            lstErrores.add(new Descriptor(detalleError.getType(), detalleError.getFormattedStackTrace()));
            System.out.println("AGREGO EL ERROR: " +lstErrores.size());
        }
    }
}
