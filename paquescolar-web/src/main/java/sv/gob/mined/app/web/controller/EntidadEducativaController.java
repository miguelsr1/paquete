/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class EntidadEducativaController implements Serializable{

    private String valorDeBusqueda;
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();
    private List<VwCatalogoEntidadEducativa> lstEntidadEducativa;
    @EJB
    EntidadEducativaEJB entidadEducativaEJB;

    /**
     * Creates a new instance of EntidadEducativaController
     */
    public EntidadEducativaController() {
    }

    public String getValorDeBusqueda() {
        return valorDeBusqueda;
    }

    public void setValorDeBusqueda(String valorDeBusqueda) {
        this.valorDeBusqueda = valorDeBusqueda;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
    }

    public void setEntidadEducativa(VwCatalogoEntidadEducativa entidadEducativa) {
        this.entidadEducativa = entidadEducativa;
    }

    public List<VwCatalogoEntidadEducativa> getLstEntidadEducativa() {
        return lstEntidadEducativa;
    }

    public void setLstEntidadEducativa(List<VwCatalogoEntidadEducativa> lstEntidadEducativa) {
        this.lstEntidadEducativa = lstEntidadEducativa;
    }
    
    public void buscarEntidadEducativaByNombre() {
        if (valorDeBusqueda.isEmpty()) {
            JsfUtil.mensajeInformacion("Debe de digitar parte del nombre del centro escolar para relizar la busqueda");
        } else {
            lstEntidadEducativa = entidadEducativaEJB.getLstEntidadEducativaByNombreOrCodigoEntidad(valorDeBusqueda.toUpperCase().trim());
        }
    }
    
    public void seleccionarEntidadEducativa(){
        PrimeFaces.current().dialog().closeDynamic(entidadEducativa);
    }
}
