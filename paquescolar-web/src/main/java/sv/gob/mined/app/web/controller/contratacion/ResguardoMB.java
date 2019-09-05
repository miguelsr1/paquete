/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.ejb.OfertaBienesServiciosEJB;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.OfertaBienesServicios;
import sv.gob.mined.paquescolar.model.ResguardoBienes;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@ManagedBean(name = "resguardoMB")
@ViewScoped
public class ResguardoMB extends RecuperarProcesoUtil implements Serializable {

    private String codigoEntidad;
    private BigDecimal rubro = BigDecimal.ZERO;

    private List<ResguardoBienes> lstResguardoBienes = new ArrayList();
    private DetalleProcesoAdq detalleProceso = new DetalleProcesoAdq();
    private OfertaBienesServicios current = new OfertaBienesServicios();
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();

    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;
    @EJB
    private EntidadEducativaEJB entidadEducativaEJB;
    @EJB
    private OfertaBienesServiciosEJB ofertaBienesServiciosEJB;

    public BigDecimal getRubro() {
        return rubro;
    }

    public void setRubro(BigDecimal rubro) {
        this.rubro = rubro;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
    }

    public void setEntidadEducativa(VwCatalogoEntidadEducativa entidadEducativa) {
        this.entidadEducativa = entidadEducativa;
    }

    public List<ResguardoBienes> getLstResguardoBienes() {
        return lstResguardoBienes;
    }

    public void setLstResguardoBienes(List<ResguardoBienes> lstResguardoBienes) {
        this.lstResguardoBienes = lstResguardoBienes;
    }

    public void limpiarFiltros() {

    }

    public void buscarEntidadEducativa() {
        if (codigoEntidad.length() == 5) {
            /**
             * Fecha: 30/08/2018 Comentario: Validación de seleccion del año y
             * proceso de adquisición
             */
            if (getRecuperarProceso().getProcesoAdquisicion() == null) {
                JsfUtil.mensajeAlerta("Debe de seleccionar un año y proceso de contratación.");
            } else {
                detalleProceso = anhoProcesoEJB.getDetProcesoAdq(getRecuperarProceso().getProcesoAdquisicion(), rubro);

                entidadEducativa = entidadEducativaEJB.getEntidadEducativa(codigoEntidad);

                if (entidadEducativa == null) {
                    JsfUtil.mensajeAlerta("No se ha encontrado el centro escolar con código: " + codigoEntidad);
                } else {
                    if (VarSession.getDepartamentoUsuarioSession() != null) {
                        String dep = getRecuperarProceso().getDepartamento();
                        if (entidadEducativa.getCodigoDepartamento().getCodigoDepartamento().equals(dep) || (Integer) VarSession.getVariableSession("idTipoUsuario") == 1) {
                            if (VarSession.getVariableSessionED() == 1) {
                                if (ofertaBienesServiciosEJB.isOfertaRubro(codigoEntidad, detalleProceso)) {
                                    
                                } else {
                                    JsfUtil.mensajeError("No existe un proceso de contratación para este centro escolar.");
                                }
                            } else if (VarSession.getVariableSessionED() == 2) {
                                current = ofertaBienesServiciosEJB.getOfertaByProcesoCodigoEntidad(current.getCodigoEntidad().getCodigoEntidad(), current.getIdDetProcesoAdq());

                                if (current == null) {
                                    JsfUtil.mensajeError("No existe un proceso de contratación para este centro escolar.");
                                } else {

                                }
                            }
                        } else {
                            JsfUtil.mensajeAlerta("El codigo del centro escolar no pertenece al departamento " + JsfUtil.getNombreDepartamentoByCodigo(dep) + "<br/>"
                                    + "Departamento del CE: " + entidadEducativa.getCodigoEntidad() + " es " + entidadEducativa.getCodigoDepartamento().getNombreDepartamento());
                        }
                    } else {
                        JsfUtil.mensajeAlerta("Debe de seleccionar un departamento y municipio.");
                    }
                }
            }
        } else {
            if (current != null && current.getParticipantesList() != null) {
                current.getParticipantesList().clear();
            }
            entidadEducativa = null;
        }
    }
}
