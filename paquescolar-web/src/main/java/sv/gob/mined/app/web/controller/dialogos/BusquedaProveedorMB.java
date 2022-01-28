/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.dialogos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.model.Empresa;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class BusquedaProveedorMB implements Serializable {

    private String valor;

    private Empresa empresa = new Empresa();
    private List<Empresa> lstEmpresas = new ArrayList();

    @EJB
    private ProveedorEJB proveedorEJB;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Empresa> getLstEmpresas() {
        return lstEmpresas;
    }

    public void setLstEmpresas(List<Empresa> lstEmpresas) {
        this.lstEmpresas = lstEmpresas;
    }

    public void buscarEmpresas() {
        if (valor != null && !valor.isEmpty()) {
            lstEmpresas = proveedorEJB.findEmpresaByValorBusqueda(valor.toUpperCase());
            if (lstEmpresas.isEmpty()) {
                JsfUtil.mensajeAlerta("No se encontrarón coincidencias con los valores de busqueda ingresados.");
            }
        } else {
            JsfUtil.mensajeAlerta("Debe de ingresar un valor para realizar la busqueda.");
        }
    }

    public void cerrarFiltro() {
        if (empresa == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar una empresa");
        } else {
            PrimeFaces.current().dialog().closeDynamic(empresa);
        }
    }

    public void cerrarFiltroPro() {
        PrimeFaces.current().dialog().closeDynamic("/app/comunes/dialogos/proveedor/filtroProveedor");
    }
}
