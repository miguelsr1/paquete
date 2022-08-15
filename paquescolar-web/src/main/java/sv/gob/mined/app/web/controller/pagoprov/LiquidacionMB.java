/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.CloseEvent;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.RptExcel;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.ejb.LoginEJB;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;
import sv.gob.mined.paquescolar.ejb.ResolucionAdjudicativaEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.ConceptoInconsistencia;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
import sv.gob.mined.paquescolar.model.DetalleLiquidacion;
import sv.gob.mined.paquescolar.model.DetalleLiquidacionInc;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.Liquidacion;
import sv.gob.mined.paquescolar.model.LiquidacionDetalleDonacion;
import sv.gob.mined.paquescolar.model.OrganizacionEducativa;
import sv.gob.mined.paquescolar.model.RecepcionBienesServicios;
import sv.gob.mined.paquescolar.model.ResolucionesModificativas;
import sv.gob.mined.paquescolar.model.pojos.contratacion.DetalleItemDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ParticipanteConContratoDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.DatosContratoDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.DatosLiquidacionDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.DatosModificativaDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.DatosRecepcionAndResguardoDto;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author MISanchez
 */
@ManagedBean
@ViewScoped
public class LiquidacionMB extends RecuperarProcesoUtil implements Serializable {

    private int rowEdit = 0;
    private int rowEditLiq = 0;
    private Boolean existe = false;
    private Boolean deshabilitar = true;
    private Boolean deshabilitarAgregar = true;
    private Boolean modificativa = false;
    private Boolean tipoDonacion = true; //true recibe; false entrega
    private String codigoEntidad;
    private String ceDonante;
    private String codigoDepartamento;
    private String numeroContrato;
    private String observacion;
    private String nombreProveedor;
    private Integer idConcepto;
    private Long cantidadDonada;
    private BigDecimal cantidadOriginal;
    private BigDecimal montoOriginal;
    private BigDecimal cantidadModificativa;
    private BigDecimal montoModificativa;
    private BigDecimal cantidadRecepcion;
    private BigDecimal idRubro;
    private BigDecimal idParticipante = BigDecimal.ZERO;
    private BigDecimal idParticipanteDonante = BigDecimal.ZERO;

    private Date fechaDonacion;

    private ContratosOrdenesCompras contrato = new ContratosOrdenesCompras();
    private DetalleProcesoAdq detalleProceso = new DetalleProcesoAdq();
    private Liquidacion liquidacion = new Liquidacion();
    private LiquidacionDetalleDonacion liqDetDonacion = new LiquidacionDetalleDonacion();
    private DetalleLiquidacionInc detalleLiquidacionInc = new DetalleLiquidacionInc();
    private RecepcionBienesServicios recepcion = new RecepcionBienesServicios();
    private ResolucionesModificativas resModificativa;
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();
    private VwCatalogoEntidadEducativa entidadEducativaDonante = new VwCatalogoEntidadEducativa();
    private OrganizacionEducativa organizacion = new OrganizacionEducativa();
    private DatosLiquidacionDto datosLiquidacionDto = new DatosLiquidacionDto();
    private DetalleItemDto detalleItemDto;

    private List<DatosContratoDto> datosContratoDto;
    private List<DatosModificativaDto> datosModificativaDto;
    private List<DatosRecepcionAndResguardoDto> datosRecepcionAndResguardoDto;
    private List<DatosLiquidacionDto> datosLiquidacionDtos;
    private List<Liquidacion> lstLiquidaciones = new ArrayList();
    private List<LiquidacionDetalleDonacion> lstLiqCeDonacion = new ArrayList();
    private List<ParticipanteConContratoDto> lstParticipantes = new ArrayList();
    private List<ParticipanteConContratoDto> lstParticipantesDonante = new ArrayList();
    private List<ConceptoInconsistencia> lstConcepto = new ArrayList();
    private List<DetalleLiquidacionInc> lstDetalleLiquidacionIncs = new ArrayList();

    @EJB
    private EntidadEducativaEJB entidadEducativaEJB;
    @EJB
    private ResolucionAdjudicativaEJB resolucionAdjudicativaEJB;
    @EJB
    private LoginEJB loginEJB;
    @EJB
    private ReportesEJB reportesEJB;
    @EJB
    private UtilEJB utilEJB;

    public LiquidacionMB() {
    }

    public DetalleItemDto getDetalleItemDto() {
        return detalleItemDto;
    }

    public BigDecimal getIdParticipanteDonante() {
        return idParticipanteDonante;
    }

    public void setIdParticipanteDonante(BigDecimal idParticipanteDonante) {
        this.idParticipanteDonante = idParticipanteDonante;
    }

    public Boolean getTipoDonacion() {
        return tipoDonacion;
    }

    public void setTipoDonacion(Boolean tipoDonacion) {
        this.tipoDonacion = tipoDonacion;
    }

    public LiquidacionDetalleDonacion getLiqDetDonacion() {
        return liqDetDonacion;
    }

    public void setLiqDetDonacion(LiquidacionDetalleDonacion liqDetDonacion) {
        this.liqDetDonacion = liqDetDonacion;
    }

    public List<LiquidacionDetalleDonacion> getLstLiqCeDonacion() {
        return lstLiqCeDonacion;
    }

    public void setLstLiqCeDonacion(List<LiquidacionDetalleDonacion> lstLiqCeDonacion) {
        this.lstLiqCeDonacion = lstLiqCeDonacion;
    }

    public DatosLiquidacionDto getDatosLiquidacionDto() {
        return datosLiquidacionDto;
    }

    public void setDatosLiquidacionDto(DatosLiquidacionDto datosLiquidacionDto) {
        this.tipoDonacion = true;
        this.datosLiquidacionDto = datosLiquidacionDto;
    }

    public Date getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(Date fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }

    public Long getCantidadDonada() {
        return cantidadDonada;
    }

    public void setCantidadDonada(Long cantidadDonada) {
        this.cantidadDonada = cantidadDonada;
    }

    public String getCeDonante() {
        return ceDonante;
    }

    public void setCeDonante(String ceDonante) {
        this.ceDonante = ceDonante;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativaDonante() {
        return entidadEducativaDonante;
    }

    public int getRowEdit() {
        return rowEdit;
    }

    public void setRowEdit(int rowEdit) {
        this.rowEdit = rowEdit;
    }

    public int getRowEditLiq() {
        return rowEditLiq;
    }

    public void setRowEditLiq(int rowEditLiq) {
        this.rowEditLiq = rowEditLiq;
    }

    public DetalleLiquidacionInc getDetalleLiquidacionInc() {
        return detalleLiquidacionInc;
    }

    public void setDetalleLiquidacionInc(DetalleLiquidacionInc detalleLiquidacionInc) {
        this.detalleLiquidacionInc = detalleLiquidacionInc;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public List<DetalleLiquidacionInc> getLstDetalleLiquidacionIncs() {
        return lstDetalleLiquidacionIncs;
    }

    public List<ConceptoInconsistencia> getLstConcepto() {
        return lstConcepto;
    }

    public List<ParticipanteConContratoDto> getLstParticipantes() {
        return lstParticipantes;
    }

    public List<ParticipanteConContratoDto> getLstParticipantesDonante() {
        return lstParticipantesDonante;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public List<DatosLiquidacionDto> getDatosLiquidacionDtos() {
        return datosLiquidacionDtos;
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(Boolean deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    public Boolean getDeshabilitarAgregar() {
        return deshabilitarAgregar;
    }

    public void setDeshabilitarAgregar(Boolean deshabilitarAgregar) {
        this.deshabilitarAgregar = deshabilitarAgregar;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public void nuevo() {
        liquidacion = new Liquidacion();
        entidadEducativa = new VwCatalogoEntidadEducativa();
        entidadEducativaDonante = new VwCatalogoEntidadEducativa();
        lstParticipantes.clear();
        lstParticipantesDonante.clear();
        lstLiquidaciones.clear();
        deshabilitar = false;
        tipoDonacion = true;
        codigoEntidad = "";
        ceDonante = "";
    }

    public void buscarEntidadEducativa() {
        lstParticipantes.clear();
        lstLiquidaciones.clear();
        lstDetalleLiquidacionIncs.clear();
        idParticipante = BigDecimal.ZERO;
        idParticipanteDonante = BigDecimal.ZERO;
        ceDonante = "";

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
                    organizacion = entidadEducativaEJB.getPresidenteOrganismoEscolar(entidadEducativa.getCodigoEntidad());
                    detalleProceso = JsfUtil.findDetalle(getRecuperarProceso().getProcesoAdquisicion(), idRubro);
                    lstParticipantes = resolucionAdjudicativaEJB.findParticipantesConContratoByCodEntAndIdDetProcesoAdq(codigoEntidad, detalleProceso.getIdDetProcesoAdq());
                }
            }
        } else {
            entidadEducativa = null;
        }
    }

    public void cancelarLiquidacion() {
        liquidacion = new Liquidacion();
        datosLiquidacionDtos.clear();
        lstLiquidaciones = resolucionAdjudicativaEJB.getLstLiquidacionByCodigoEntAndIdDetProcesoAdqAndIdParticipante(codigoEntidad, detalleProceso.getIdDetProcesoAdq(), idParticipante);
    }

    public void agregarLista() {
        liquidacion.setObservacion(observacion == null ? "" : observacion);
        liquidacion.setEstadoLiquidacion("E");
        liquidacion.setFechaInsercion(new Date());
        liquidacion.setEstadoEliminacion((short) 0);
        liquidacion.setIdContrato(resolucionAdjudicativaEJB.findContratoByPk(datosContratoDto.get(0).getIdContrato()));
        liquidacion.setUsuarioInsercion(VarSession.getVariableSessionUsuario());

        /**
         * faltaria detalle de diferencia de items contratados por modificativa
         * a contratos
         */
        datosLiquidacionDtos.forEach(dato -> {
            DetalleLiquidacion det = new DetalleLiquidacion();

            det.setNoItem(dato.getNoItem());
            det.setCantidad(dato.getCantidadContrato() == null ? 0l : dato.getCantidadContrato().longValue());
            det.setPrecioUnitario(dato.getPrecioUnitarioContrato() == null ? BigDecimal.ZERO : dato.getPrecioUnitarioContrato());

            if (modificativa) {
                det.setCantidadModificativa(dato.getCantidadModificativa().longValue());
                det.setPrecioUnitarioModif(dato.getPrecioUnitarioModificativa());
            }

            det.setCantidadEntregada(dato.getCantidadRecepcion().longValue());
            if (dato.getCantidadResguardo() != null && dato.getCantidadResguardo().intValue() > 0) {
                det.setCantidadResguardo(dato.getCantidadResguardo().longValue());
            } else {
                det.setCantidadResguardo(0l);
            }
            det.setIdLiquidacion(liquidacion);

            dato.getDetDonacion().forEach(liqCeDonante -> {
                liqCeDonante.setIdDetLiquidacion(det);
                det.getLiquidacionDetalleDonacionList().add(liqCeDonante);
            });

            liquidacion.getDetalleLiquidacionList().add(det);
        });

        resolucionAdjudicativaEJB.guardarLiquidacion(liquidacion);

        JsfUtil.mensajeInsert();

        liquidacion = new Liquidacion();

        lstLiquidaciones = resolucionAdjudicativaEJB.getLstLiquidacionByCodigoEntAndIdDetProcesoAdqAndIdParticipante(codigoEntidad, detalleProceso.getIdDetProcesoAdq(), idParticipante);
    }

    public void recuperarLstLiquidacionByCodEntAndIdDetPro() {
        lstLiquidaciones.clear();
        lstDetalleLiquidacionIncs.clear();
        if (idParticipante != null && idParticipante.compareTo(BigDecimal.ZERO) > 0) {
            lstLiquidaciones = resolucionAdjudicativaEJB.getLstLiquidacionByCodigoEntAndIdDetProcesoAdqAndIdParticipante(codigoEntidad, detalleProceso.getIdDetProcesoAdq(), idParticipante);
            if (!lstLiquidaciones.isEmpty()) {
                deshabilitarAgregar = lstLiquidaciones.get(lstLiquidaciones.size() - 1).getEstadoLiquidacion().equals("L");
            } else {
                deshabilitarAgregar = false;
            }
        }
    }

    public void recuperarDatos() {
        datosLiquidacionDtos = new ArrayList();

        datosContratoDto = resolucionAdjudicativaEJB.getDatosContratoDto(codigoEntidad, detalleProceso.getIdDetProcesoAdq(), idParticipante);
        if (datosContratoDto.get(0).getIdEstadoReserva().intValue() == 5) {
            datosModificativaDto = resolucionAdjudicativaEJB.getDatosModificativaDto(datosContratoDto.get(0).getIdContrato());
            modificativa = true;
        }

        datosRecepcionAndResguardoDto = resolucionAdjudicativaEJB.getDatosRecepcionAndResguardoDto(datosContratoDto.get(0).getIdContrato());

        switch (detalleProceso.getIdRubroAdq().getIdRubroInteres().intValue()) {
            case 1:
            case 4:
            case 5:
                crearTablaDeCompracion(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"));
                break;
            case 2:
                crearTablaDeCompracion(Arrays.asList("1", "2", "3", "4", "5"));
                break;
            case 3:
                crearTablaDeCompracion(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
                break;
        }
    }

    private void crearTablaDeCompracion(List<String> listado) {
        listado.forEach(noItem -> {
            DatosLiquidacionDto datoLiquidacion = new DatosLiquidacionDto();
            datoLiquidacion = subCrearTabla(noItem, datoLiquidacion);
            if (datoLiquidacion != null) {
                datosLiquidacionDtos.add(datoLiquidacion);
            }
        });
    }

    private DatosLiquidacionDto subCrearTabla(String noItem, DatosLiquidacionDto datoLiquidacion) {
        Boolean noEstaItem = true;
        for (DatosContratoDto dato : datosContratoDto) {
            if (dato.getNoItem().equals(noItem)) {
                datoLiquidacion.setIdContrato(dato.getIdContrato());
                datoLiquidacion.setNoItem(noItem);
                datoLiquidacion.setCantidadContrato(dato.getCantidad());
                datoLiquidacion.setPrecioUnitarioContrato(dato.getPrecioUnitario());
                noEstaItem = false;
                break;
            }
        }

        if (modificativa) {
            for (DatosModificativaDto dato : datosModificativaDto) {
                if (dato.getNoItem().equals(noItem)) {
                    datoLiquidacion.setIdContrato(dato.getIdContrato());
                    datoLiquidacion.setNoItem(noItem);
                    datoLiquidacion.setCantidadModificativa(dato.getCantidadNew());
                    datoLiquidacion.setPrecioUnitarioModificativa(dato.getPrecioUnitarioNew());
                    noEstaItem = false;
                    break;
                }
            }
        }

        for (DatosRecepcionAndResguardoDto dato : datosRecepcionAndResguardoDto) {
            if (dato.getNoItem().equals(noItem)) {
                datoLiquidacion.setIdContrato(dato.getIdContrato());
                datoLiquidacion.setNoItem(noItem);
                datoLiquidacion.setCantidadRecepcion(dato.getCantidadEntregada());
                datoLiquidacion.setCantidadResguardo(dato.getCantidadResguardo());
                noEstaItem = false;
                break;
            }
        }

        if (noEstaItem) {
            return null;
        } else {
            if (datoLiquidacion.getCantidadModificativa() == null) {
                datoLiquidacion.setCantidadModificativa(BigDecimal.ZERO);
            }
            if (datoLiquidacion.getPrecioUnitarioModificativa() == null) {
                datoLiquidacion.setPrecioUnitarioModificativa(BigDecimal.ZERO);
            }
            if (datoLiquidacion.getCantidadResguardo() == null) {
                datoLiquidacion.setCantidadResguardo(BigDecimal.ZERO);
            }

            return datoLiquidacion;
        }
    }

    public void imprimirReporteLiq() {
        String nombreUsuario = loginEJB.getNombreByUsername(liquidacion.getUsuarioInsercion());
        Long cantidadDonacionRecibida = 0l;
        Long cantidadDonacionEntregada = 0l;
        BigDecimal montoDonacionRecibida = BigDecimal.ZERO;
        BigDecimal montoDonacionEntregada = BigDecimal.ZERO;

        HashMap param = new HashMap();
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        param.put("pEscudo", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
        param.put("p_id_liquidacion", liquidacion.getIdLiquidacion());
        param.put("p_id_contrato", liquidacion.getIdContrato().getIdContrato());
        param.put("p_nombre_canton", entidadEducativa.getNombreCanton());
        param.put("p_nombre_cacerio", "");
        param.put("p_nombre_usuario", nombreUsuario);
        param.put("pTelDirector", organizacion.getTelDirector());

        param.put("pCodigo", codigoEntidad);
        param.put("pIdParticipante", liquidacion.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdParticipante());
        param.put("idLiquidacion", liquidacion.getIdLiquidacion());

        List<LiquidacionDetalleDonacion> lstDetalleDonacion = resolucionAdjudicativaEJB.findDetalleDonacionByIdLiquidacion(liquidacion.getIdLiquidacion());

        for (LiquidacionDetalleDonacion liquidacionDetalleDonacion : lstDetalleDonacion) {
            if (liquidacionDetalleDonacion.getTipoDonacion() == 1) {
                cantidadDonacionRecibida += liquidacionDetalleDonacion.getCantidad();
                montoDonacionRecibida = montoDonacionRecibida.add(liquidacionDetalleDonacion.getPrecioUnitario().multiply(new BigDecimal(liquidacionDetalleDonacion.getCantidad())));
            } else {
                cantidadDonacionEntregada += liquidacionDetalleDonacion.getCantidad();
                montoDonacionEntregada = montoDonacionEntregada.add(liquidacionDetalleDonacion.getPrecioUnitario().multiply(new BigDecimal(liquidacionDetalleDonacion.getCantidad())));
            }
        }

        param.put("cantidadDonacionRecibida", cantidadDonacionRecibida);
        param.put("montoDonacionRecibida", montoDonacionRecibida);
        param.put("cantidadDonacionEntregada", cantidadDonacionEntregada);
        param.put("montoDonacionEntregada", montoDonacionEntregada);

        Reportes.generarRptSQLConnection(reportesEJB, param, "sv/gob/mined/apps/reportes/pagoproveedor/", "rptLiquidacion", "rptLiquidacion");
    }

    public void eliminarLiquidacion() {
        resolucionAdjudicativaEJB.eliminarLiquidacion(liquidacion.getIdLiquidacion(), VarSession.getVariableSessionUsuario());
        JsfUtil.mensajeInformacion("Se ha eliminado satisfactoriamente el registro seleccionado.");
        lstLiquidaciones = resolucionAdjudicativaEJB.getLstLiquidacionByCodigoEntAndIdDetProcesoAdqAndIdParticipante(codigoEntidad, detalleProceso.getIdDetProcesoAdq(), idParticipante);
    }

    public void imprimirReporteInc() {
        HashMap param = new HashMap();
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        param.put("pEscudo", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
        param.put("pCodigo", codigoEntidad);
        param.put("pIdParticipante", liquidacion.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdParticipante());
        param.put("idLiquidacion", liquidacion.getIdLiquidacion());

        Reportes.generarRptSQLConnection(reportesEJB, param, "sv/gob/mined/apps/reportes/pagoproveedor/", "rptLiqObservacion", "rptLiqObservacion");
    }

    public void recuperarConceptosInconsistencia(Liquidacion liq) {
        lstConcepto = resolucionAdjudicativaEJB.getLstConceptosInconsistencia(getRecuperarProceso().getProcesoAdquisicion().getIdAnho().getIdAnho());
        liquidacion = liq;
        lstDetalleLiquidacionIncs = liq.getDetalleLiquidacionIncList();
    }

    public void carmbiarEstadoLiquidacion(Liquidacion liq) {
        liquidacion = liq;
    }

    public void guardarCambioEstado() {
        liquidacion.setUsuarioModificacion(VarSession.getVariableSessionUsuario());
        liquidacion.setFechaModificacion(new Date());
        resolucionAdjudicativaEJB.guardarLiquidacion(liquidacion);
        JsfUtil.mensajeUpdate();
    }

    public void agregarConceptoInc() {
        existe = false;
        lstDetalleLiquidacionIncs.forEach(det -> {
            if (det.getIdConcepto().getIdConcepto().compareTo(idConcepto) == 0) {
                existe = true;
            }
        });

        if (existe) {
            JsfUtil.mensajeAlerta("Este concepto ya fue agregado");
        } else {
            DetalleLiquidacionInc detLiq = new DetalleLiquidacionInc();
            detLiq.setFechaInsercion(new Date());
            detLiq.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
            detLiq.setIdLiquidacion(liquidacion);
            detLiq.setValor((short) 1);
            detLiq.setIdConcepto(utilEJB.find(ConceptoInconsistencia.class, idConcepto));

            lstDetalleLiquidacionIncs.add(detLiq);
        }
    }

    public void guardarDetalleInconsistencias() {
        resolucionAdjudicativaEJB.guardarDetalleLiquidacionInc(lstDetalleLiquidacionIncs);
        JsfUtil.mensajeInsert();
    }

    public void eliminarObservacion() {
        if (detalleLiquidacionInc != null) {
            if (detalleLiquidacionInc.getHistorico().compareTo((short) 0) == 0) {
                if (detalleLiquidacionInc.getIdDetLiqInc() != null) {
                    detalleLiquidacionInc.setHistorico((short) 1);
                } else {
                    lstDetalleLiquidacionIncs.remove(rowEdit);
                }
            } else {
                detalleLiquidacionInc.setHistorico((short) 1);
            }

            detalleLiquidacionInc = null;
        } else {
            JsfUtil.mensajeAlerta("Debe seleccionar un detalle para poder eliminarlo.");
        }
    }

    public void generarRptLiquidacion() {
        detalleProceso = JsfUtil.findDetalle(getRecuperarProceso().getProcesoAdquisicion(), idRubro);
        RptExcel.generarRptLiquidacion(codigoDepartamento, detalleProceso.getIdDetProcesoAdq(), resolucionAdjudicativaEJB.getRptLiquidacion(codigoDepartamento, detalleProceso.getIdDetProcesoAdq()));
    }

    public void buscarEntidadEducativaDonante() {
        if (ceDonante.length() == 5) {
            if (getRecuperarProceso().getProcesoAdquisicion() == null) {
                JsfUtil.mensajeAlerta("Debe de seleccionar un año y proceso de contratación.");
            } else {

                entidadEducativaDonante = entidadEducativaEJB.getEntidadEducativa(ceDonante);
                if (entidadEducativaDonante == null) {
                    JsfUtil.mensajeAlerta("No se ha encontrado el centro escolar con código: " + ceDonante);
                } else {
                    if (tipoDonacion) {
                        lstParticipantesDonante = resolucionAdjudicativaEJB.findParticipantesConContratoByCodEntAndIdDetProcesoAdq(ceDonante, detalleProceso.getIdDetProcesoAdq());
                    }
                }
            }
        } else {
            entidadEducativaDonante = null;
        }
    }

    public void agregarLiqCeDonante() {
        String msj = "";
        if (ceDonante.isEmpty()) {
            msj = "Debe de ingresar el Código de Entidad.";
        }
        if (tipoDonacion) {
            if (idParticipanteDonante == null || idParticipanteDonante.intValue() == 0) {
                msj = (msj.isEmpty() ? "" : msj + "<br/>").concat("Debe de seleccionar un proveedor.");
            }
        }
        if (cantidadDonada != null && cantidadDonada < 1) {
            msj = (msj.isEmpty() ? "" : msj + "<br/>").concat("Debe de ingresar una cantidad válida.");
        }
        if (fechaDonacion == null) {
            msj = (msj.isEmpty() ? "" : msj + "<br/>").concat("Debe de seleccionar una fecha.");
        }

        if (!msj.isEmpty()) {
            JsfUtil.mensajeAlerta(msj);
        } else {

            LiquidacionDetalleDonacion detDonacion = new LiquidacionDetalleDonacion();
            detDonacion.setCantidad(cantidadDonada);
            detDonacion.setCodigoEntidad(ceDonante);
            detDonacion.setFechaDonacion(fechaDonacion);

            if (tipoDonacion) {
                detDonacion.setTipoDonacion((short) 1);
                detDonacion.setPrecioUnitario(detalleItemDto.getPrecioUnitario());
            } else {
                detDonacion.setTipoDonacion((short) 0);
            }

            lstLiqCeDonacion.add(detDonacion);

            limpiarDlgDetalleCeDonante();
        }
    }

    public void guardarDetalleCeDonante() {
        for (LiquidacionDetalleDonacion detDonacion : lstLiqCeDonacion) {
            datosLiquidacionDto.getDetDonacion().add(detDonacion);
        }

        limpiarDlgDetalleCeDonante();
        lstLiqCeDonacion.clear();
    }

    public void limpiarDlgDetalleCeDonante() {
        ceDonante = "";
        cantidadDonada = 0l;
        fechaDonacion = null;
        tipoDonacion = true;
        entidadEducativaDonante = new VwCatalogoEntidadEducativa();
        detalleItemDto = new DetalleItemDto();
        idParticipanteDonante = null;
    }

    public void cerrarDlgDetalleCedonante() {
        limpiarDlgDetalleCeDonante();
        lstLiqCeDonacion.clear();
    }

    public void eliminarLiqCeDonante() {
        lstLiqCeDonacion.remove(rowEditLiq);
    }

    public void obtenerProveedor() {
        Optional<ParticipanteConContratoDto> participanteDto = lstParticipantes.stream().parallel().filter(par -> par.getIdParticipante().compareTo(idParticipante) == 0).findAny();
        if (participanteDto.isPresent()) {
            nombreProveedor = participanteDto.get().getRazonSocial();
        }
    }

    public String getEspecificacionItem() {
        if (datosLiquidacionDto != null && datosLiquidacionDto.getNoItem() != null && !datosLiquidacionDto.getNoItem().isEmpty()) {
            return JsfUtil.getNombreItem(datosLiquidacionDto.getNoItem(), idRubro);
        } else {
            return "";
        }
    }

    public String especificacion(String noItem) {
        if (noItem != null && !noItem.isEmpty()) {
            return JsfUtil.getNombreItem(noItem, idRubro);
        } else {
            return "";
        }
    }

    public void recuperarPrecioUnitarioItemByProveedorDonante() {
        detalleItemDto = resolucionAdjudicativaEJB.findDetalleByParticipanteAndNoItem(idParticipanteDonante, datosLiquidacionDto.getNoItem());
    }

    public void closeDlgDetalleDonacion(CloseEvent event) {
        cerrarDlgDetalleCedonante();
    }

    public void limpiarDlgDonacion() {
        cerrarDlgDetalleCedonante();
    }
}
