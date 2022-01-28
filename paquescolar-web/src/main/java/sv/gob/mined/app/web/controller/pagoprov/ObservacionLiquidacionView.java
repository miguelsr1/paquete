/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.ejb.ResolucionAdjudicativaEJB;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ParticipanteConContratoDto;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

@ManagedBean
@ViewScoped
public class ObservacionLiquidacionView extends RecuperarProcesoUtil implements Serializable {

    private String codigoEntidad;

    private BigDecimal idRubro;
    private BigDecimal idParticipante = BigDecimal.ZERO;

    private DetalleProcesoAdq detalleProceso = new DetalleProcesoAdq();
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();

    private List<ParticipanteConContratoDto> lstParticipantes = new ArrayList();

    @EJB
    private EntidadEducativaEJB entidadEducativaEJB;
    @EJB
    private ResolucionAdjudicativaEJB resolucionAdjudicativaEJB;

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public BigDecimal getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(BigDecimal idRubro) {
        this.idRubro = idRubro;
    }

    public BigDecimal getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(BigDecimal idParticipante) {
        this.idParticipante = idParticipante;
    }

    public List<ParticipanteConContratoDto> getLstParticipantes() {
        return lstParticipantes;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
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

                entidadEducativa = entidadEducativaEJB.getEntidadEducativa(codigoEntidad);
                if (entidadEducativa == null) {
                    JsfUtil.mensajeAlerta("No se ha encontrado el centro escolar con código: " + codigoEntidad);
                } else {
                    detalleProceso = JsfUtil.findDetalle(getRecuperarProceso().getProcesoAdquisicion(), idRubro);
                    lstParticipantes = resolucionAdjudicativaEJB.findParticipantesConContratoByCodEntAndIdDetProcesoAdq(codigoEntidad, detalleProceso.getIdDetProcesoAdq());
                }
            }
        } else {
            entidadEducativa = null;
        }
    }
}
