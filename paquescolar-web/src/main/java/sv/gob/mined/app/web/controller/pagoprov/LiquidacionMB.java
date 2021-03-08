/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.ejb.OfertaBienesServiciosEJB;
import sv.gob.mined.paquescolar.ejb.RecepcionEJB;
import sv.gob.mined.paquescolar.ejb.ResolucionAdjudicativaEJB;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.Liquidacion;
import sv.gob.mined.paquescolar.model.OfertaBienesServicios;
import sv.gob.mined.paquescolar.model.RecepcionBienesServicios;
import sv.gob.mined.paquescolar.model.ResolucionesModificativas;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.DatosContratoDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.DatosModificativaDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.DatosRecepcionDto;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author MISanchez
 */
@ManagedBean
@ViewScoped
public class LiquidacionMB extends RecuperarProcesoUtil implements Serializable {

    private Boolean modificativa = false;
    private String codigoEntidad;
    private String numeroContrato;
    private BigDecimal cantidadOriginal;
    private BigDecimal montoOriginal;
    private BigDecimal cantidadModificativa;
    private BigDecimal montoModificativa;
    private BigDecimal cantidadRecepcion;
    private BigDecimal idRubro;
    private BigDecimal idParticipante = BigDecimal.ZERO;

    private ContratosOrdenesCompras contrato = new ContratosOrdenesCompras();
    private DetalleProcesoAdq detalleProceso = new DetalleProcesoAdq();
    private Liquidacion liquidacion = new Liquidacion();
    private OfertaBienesServicios oferta = new OfertaBienesServicios();
    private RecepcionBienesServicios recepcion = new RecepcionBienesServicios();
    private ResolucionesModificativas resModificativa;

    private List<DatosContratoDto> datosContratoDto;
    private List<DatosModificativaDto> datosModificativaDto;
    private List<DatosRecepcionDto> datosRecepcionDto;

    private List<Liquidacion> lstLiquidaciones = new ArrayList();

    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();

    @EJB
    private EntidadEducativaEJB entidadEducativaEJB;
    @EJB
    private OfertaBienesServiciosEJB ofertaBienesServiciosEJB;
    @EJB
    private ResolucionAdjudicativaEJB resolucionAdjudicativaEJB;
    @EJB
    private RecepcionEJB recepcionEJB;

    public LiquidacionMB() {
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public List<Liquidacion> getLstLiquidaciones() {
        return lstLiquidaciones;
    }

    public void setLstLiquidaciones(List<Liquidacion> lstLiquidaciones) {
        this.lstLiquidaciones = lstLiquidaciones;
    }

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

    public OfertaBienesServicios getOferta() {
        return oferta;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
    }

    public ContratosOrdenesCompras getContrato() {
        return contrato;
    }

    public RecepcionBienesServicios getRecepcion() {
        return recepcion;
    }

    public Liquidacion getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(Liquidacion liquidacion) {
        this.liquidacion = liquidacion;
    }

    public Boolean getModificativa() {
        return modificativa;
    }

    public void setModificativa(Boolean modificativa) {
        this.modificativa = modificativa;
    }

    public BigDecimal getMontoOriginal() {
        return montoOriginal;
    }

    public void setMontoOriginal(BigDecimal montoOriginal) {
        this.montoOriginal = montoOriginal;
    }

    public BigDecimal getMontoModificativa() {
        return montoModificativa;
    }

    public void setMontoModificativa(BigDecimal montoModificativa) {
        this.montoModificativa = montoModificativa;
    }

    public ResolucionesModificativas getResModificativa() {
        return resModificativa;
    }

    public void setResModificativa(ResolucionesModificativas resModificativa) {
        this.resModificativa = resModificativa;
    }

    public BigDecimal getCantidadOriginal() {
        return cantidadOriginal;
    }

    public void setCantidadOriginal(BigDecimal cantidadOriginal) {
        this.cantidadOriginal = cantidadOriginal;
    }

    public BigDecimal getCantidadModificativa() {
        return cantidadModificativa;
    }

    public void setCantidadModificativa(BigDecimal cantidadModificativa) {
        this.cantidadModificativa = cantidadModificativa;
    }

    public BigDecimal getCantidadRecepcion() {
        return cantidadRecepcion;
    }

    public void setCantidadRecepcion(BigDecimal cantidadRecepcion) {
        this.cantidadRecepcion = cantidadRecepcion;
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
                    oferta = ofertaBienesServiciosEJB.getOfertaByProcesoCodigoEntidad(codigoEntidad, detalleProceso);
                }
            }
        } else {
            entidadEducativa = null;
        }
    }

    public void agregarContrato() {
        contrato = resolucionAdjudicativaEJB.findContratoByIdParticipante(idParticipante);

        if (contrato != null) {
            modificativa = (contrato.getModificativa() == 1);
            montoOriginal = contrato.getIdResolucionAdj().getIdParticipante().getMonto();
            montoModificativa = BigDecimal.ZERO;

            if (modificativa) {
                resModificativa = new ResolucionesModificativas();
                resModificativa = resolucionAdjudicativaEJB.findModificativaByIdContrato(contrato.getIdContrato());
                resModificativa.getDetalleModificativaList().forEach((detalleModificativa) -> {
                    montoModificativa = montoModificativa.add(detalleModificativa.getPrecioUnitarioNew().multiply(new BigDecimal(detalleModificativa.getCantidadNew())));
                });
            }

            numeroContrato = "ME-" + contrato.getNumeroContrato() + "/" + contrato.getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getAnho();
            recepcion = recepcionEJB.findRecepcionByIdContrato(contrato.getIdContrato());

            liquidacion = new Liquidacion();

            liquidacion.setEstadoEliminacion((short) 0);
            liquidacion.setFechaInsercion(new Date());
            liquidacion.setIdContrato(contrato);
            liquidacion.setUsuarioInsercion(VarSession.getVariableSessionUsuario());

        }
    }

    public void agregarLista() {
        lstLiquidaciones.add(liquidacion);

        resolucionAdjudicativaEJB.guardarLiquidacion(liquidacion);

        JsfUtil.mensajeInsert();

        liquidacion = new Liquidacion();
    }

    public void recuperarLstLiquidacionByCodEntAndIdDetPro() {
        lstLiquidaciones = resolucionAdjudicativaEJB.getLstLiquidacionByCodigoEntAndIdDetProcesoAdq(codigoEntidad, detalleProceso.getIdDetProcesoAdq());
    }

    public void recuperarDatos() {
        datosContratoDto = resolucionAdjudicativaEJB.getDatosContratoDto(codigoEntidad, detalleProceso.getIdDetProcesoAdq());
        if (datosContratoDto.get(0).getIdEstadoReserva().intValue() == 5) {
            datosModificativaDto = resolucionAdjudicativaEJB.getDatosModificativaDto(datosContratoDto.get(0).getIdContrato());
            modificativa = true;
        }

        datosRecepcionDto = resolucionAdjudicativaEJB.getDatosRecepcionDto(datosContratoDto.get(0).getIdContrato());

        cantidadOriginal = BigDecimal.ZERO;
        montoOriginal = BigDecimal.ZERO;
        datosContratoDto.stream().forEachOrdered(datoContrato -> {
            cantidadOriginal = cantidadOriginal.add(datoContrato.getCantidad());
            montoOriginal = montoOriginal.add(datoContrato.getCantidad().multiply(datoContrato.getPrecioUnitario()));
        });
        
        cantidadModificativa = BigDecimal.ZERO;
        montoModificativa = BigDecimal.ZERO;
        datosModificativaDto.stream().forEachOrdered(datoContrato -> {
            cantidadModificativa = cantidadModificativa.add(datoContrato.getCantidadNew());
            montoModificativa = montoModificativa.add(datoContrato.getCantidadNew().multiply(datoContrato.getPrecioUnitarioNew()));
        });
        
        cantidadRecepcion = BigDecimal.ZERO;
        datosRecepcionDto.stream().forEachOrdered(datoContrato -> {
            cantidadRecepcion = cantidadRecepcion.add(datoContrato.getCantidadEntregada());
        });
        
    }
}
