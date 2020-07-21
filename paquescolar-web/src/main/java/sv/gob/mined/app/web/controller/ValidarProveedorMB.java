/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;

/**
 *
 * @author MISanchez
 */
@ManagedBean
@ViewScoped
public class ValidarProveedorMB implements Serializable {

    private String codigo;
    private String nit;
    private String dui;
    private Boolean showPanel = false;

    @EJB
    public ProveedorEJB proveedorEJB;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public Boolean getShowPanel() {
        return showPanel;
    }

    public void setShowPanel(Boolean showPanel) {
        this.showPanel = showPanel;
    }

    public void validarProveedor() {
        Boolean validar = proveedorEJB.validarCodigoSegEmpresa(codigo, nit, dui);
        if (validar) {

        } else {
            JsfUtil.mensajeError("Los valores ingresados no coinciden con ningún proveedor");
        }

        showPanel = validar;
    }
}
