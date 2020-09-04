/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.util.RC4Crypter;

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
    private BigDecimal idEmpresa;
    private Boolean showPanel = false;
    private String pass1;

    private static final ResourceBundle UTIL_CORREO = ResourceBundle.getBundle("Bundle");

    @EJB
    public ProveedorEJB proveedorEJB;

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("codigo")) {
            String idEmpresaStr = (new RC4Crypter()).decrypt("ha", params.get("codigo")).split(":")[0];
            
            idEmpresa = new BigDecimal(idEmpresaStr);
        }
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

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
        String tituloEmail = UTIL_CORREO.getString("prov.email.titulo");
        String cuerpoEmail = UTIL_CORREO.getString("prov.email.mensaje");

        Boolean validar = proveedorEJB.validarCodigoSegEmpresa(codigo, nit, dui, tituloEmail, cuerpoEmail);
        if (validar) {
            JsfUtil.mensajeInformacion("Se le ha enviado un correo para activar su usuario de acceso. Esta es la única vez que podrá realizar este paso.");
        } else {
            JsfUtil.mensajeError("Los valores ingresados no coinciden con ningún proveedor");
        }

        showPanel = validar;
    }

    public String guardarPasswordProv() {
        proveedorEJB.guardarPasswordProv(idEmpresa, pass1);
        
        return "index.mined";
    }
}
