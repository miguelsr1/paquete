package sv.gob.mined.app.web.controller.liquidacion;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.RptExcel;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.ejb.LiquidacionFinancieraEJB;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;
import sv.gob.mined.paquescolar.ejb.ResolucionAdjudicativaEJB;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.HistorialLiquiFinan;
import sv.gob.mined.paquescolar.model.LiquidacionFinanciera;
import sv.gob.mined.paquescolar.model.RptDocumentos;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ParticipanteConContratoDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.VwLiqFinanConModifDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.VwLiqFinanConOrigDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.VwLiqFinanConRecepDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.VwLiqFinanDetPlanillaDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.VwLiquidacionFinancieraConsolidadoDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.VwLiquidacionFinancieraDto;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class LiquidacionFinancieraView extends RecuperarProcesoUtil implements Serializable {

    private Boolean isModificativa = true;
    private Boolean isRecepcion = true;
    private Boolean deshabilitar = true;
    private Boolean excel = true;

    private String codigoEntidad;
    private String codigoDepartamento;
    private String observacion;
    private short estadoLiquidacion;
    private Short estadoLiquidacionOld = null;
    private BigDecimal idRubro;
    private BigDecimal idContrato;

    private Date fechaEstadoLiqui;

    private DetalleProcesoAdq detalleProcesoAdq;
    private LiquidacionFinanciera liquidacionFinanciera;
    private HistorialLiquiFinan historialLiquiFinan;

    private VwLiqFinanConOrigDto conOrinDto;
    private VwLiqFinanConModifDto conModifDto;
    private VwLiqFinanConRecepDto conRecepDto;
    private VwLiqFinanDetPlanillaDto conDetPagoDto;
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();

    private List<ParticipanteConContratoDto> lstParticipantes = new ArrayList();

    @EJB
    private LiquidacionFinancieraEJB liquidacionFinancieraEJB;
    @EJB
    private EntidadEducativaEJB entidadEducativaEJB;
    @EJB
    private ResolucionAdjudicativaEJB resolucionAdjudicativaEJB;
    @EJB
    private ReportesEJB reportesEJB;

    @PostConstruct
    public void init() {
        fechaEstadoLiqui = new Date();
    }

    public Boolean getExcel() {
        return excel;
    }

    public void setExcel(Boolean excel) {
        this.excel = excel;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }

    public Date getFechaEstadoLiqui() {
        return fechaEstadoLiqui;
    }

    public void setFechaEstadoLiqui(Date fechaEstadoLiqui) {
        this.fechaEstadoLiqui = fechaEstadoLiqui;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public short getEstadoLiquidacion() {
        return estadoLiquidacion;
    }

    public void setEstadoLiquidacion(short estadoLiquidacion) {
        this.estadoLiquidacion = estadoLiquidacion;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public Boolean getIsModificativa() {
        return isModificativa;
    }

    public Boolean getIsRecepcion() {
        return isRecepcion;
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

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public List<ParticipanteConContratoDto> getLstParticipantes() {
        return lstParticipantes;
    }

    public VwLiqFinanConOrigDto getConOrinDto() {
        return conOrinDto;
    }

    public VwLiqFinanConModifDto getConModifDto() {
        return conModifDto;
    }

    public VwLiqFinanConRecepDto getConRecepDto() {
        return conRecepDto;
    }

    public VwLiqFinanDetPlanillaDto getConDetPagoDto() {
        return conDetPagoDto;
    }

    public void setConDetPagoDto(VwLiqFinanDetPlanillaDto conDetPagoDto) {
        this.conDetPagoDto = conDetPagoDto;
    }

    public void buscarDatos() {
        conOrinDto = liquidacionFinancieraEJB.findContratoOriginal(idContrato);
        conModifDto = liquidacionFinancieraEJB.findContratoModificativa(idContrato);
        conRecepDto = liquidacionFinancieraEJB.findContratoRecepcion(idContrato);
        conDetPagoDto = liquidacionFinancieraEJB.findContratoDetPago(idContrato);

        isModificativa = conModifDto.getIdContrato() != null;
        isRecepcion = conRecepDto.getIdContrato() != null;
        deshabilitar = conRecepDto.getIdContrato() == null;

        liquidacionFinanciera = liquidacionFinancieraEJB.findLiquidacion(idContrato);
        if (liquidacionFinanciera.getIdLiquidacionFinanciera() != null) {
            estadoLiquidacionOld = liquidacionFinanciera.getIdEstadoLiquiFinan();
            estadoLiquidacion = liquidacionFinanciera.getIdEstadoLiquiFinan();
            deshabilitar = liquidacionFinanciera.getIdEstadoLiquiFinan() == 2;
        }
    }

    public void buscarEntidadEducativa() {
        if (codigoEntidad.length() == 5) {
            if (getRecuperarProceso().getProcesoAdquisicion() == null) {
                JsfUtil.mensajeAlerta("Debe de seleccionar un año y proceso de contratación.");
            } else {

                entidadEducativa = entidadEducativaEJB.getEntidadEducativa(codigoEntidad);
                if (entidadEducativa == null) {
                    JsfUtil.mensajeAlerta("No se ha encontrado el centro escolar con código: " + codigoEntidad);
                } else if (idRubro != null) {

                    detalleProcesoAdq = JsfUtil.findDetalle(getRecuperarProceso().getProcesoAdquisicion(), idRubro);
                    lstParticipantes = resolucionAdjudicativaEJB.findParticipantesConContratoByCodEntAndIdDetProcesoAdq(codigoEntidad, detalleProcesoAdq.getIdDetProcesoAdq());
                } else {
                    JsfUtil.mensajeAlerta("Debe de seleccionar un Rubro válido");
                }
            }
        } else {
            entidadEducativa = null;
        }
    }

    public void nuevo() {
        liquidacionFinanciera = new LiquidacionFinanciera();
        historialLiquiFinan = new HistorialLiquiFinan();

        isModificativa = true;
        isRecepcion = true;
        deshabilitar = true;
        codigoEntidad = null;
        idRubro = null;

        fechaEstadoLiqui = null;

        conOrinDto = new VwLiqFinanConOrigDto();
        conModifDto = new VwLiqFinanConModifDto();
        conRecepDto = new VwLiqFinanConRecepDto();
        conDetPagoDto = new VwLiqFinanDetPlanillaDto();
        entidadEducativa = new VwCatalogoEntidadEducativa();

        lstParticipantes.clear();
    }

    public void guardar() {
        boolean continuar = false;
        if (validarCambioDeEstado()) {
            switch (estadoLiquidacion) {
                case 1:
                    if (observacion == null || observacion.isEmpty()) {
                        JsfUtil.mensajeAlerta("Debe de ingresar una observación.");
                    } else {
                        continuar = true;
                    }
                    break;
                case 2:
                    continuar = true;
                    break;
            }
            if (continuar) {
                //guardar Liquidación financiera
                liquidacionFinanciera.setEstadoEliminacion((short) 0);
                liquidacionFinanciera.setFechaInsercion(new Date());
                liquidacionFinanciera.setIdContrato(idContrato);
                liquidacionFinanciera.setIdEstadoLiquiFinan(estadoLiquidacion);
                liquidacionFinanciera.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                liquidacionFinancieraEJB.guardarLiquidacion(liquidacionFinanciera);

                //guardar cambio de estado de liquidación financiera
                historialLiquiFinan = new HistorialLiquiFinan();
                historialLiquiFinan.setFechaInsercion(new Date());
                historialLiquiFinan.setIdContrato(idContrato);
                historialLiquiFinan.setIdEstadoLiquiFinanOld(estadoLiquidacionOld);
                historialLiquiFinan.setIdEstadoLiquiFinanNew(estadoLiquidacion);
                historialLiquiFinan.setJustificacion(observacion);
                historialLiquiFinan.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                liquidacionFinancieraEJB.guardarHistorico(historialLiquiFinan);

                JsfUtil.mensajeInsert();
            }
        } else {
            JsfUtil.mensajeAlerta("Cambio de estado no válido");
        }
    }

    private boolean validarCambioDeEstado() {
        return idContrato != null && ((estadoLiquidacionOld == null && estadoLiquidacion == (short) 1)
                || (estadoLiquidacionOld == null && estadoLiquidacion == (short) 2)
                || (estadoLiquidacionOld == (short) 1 && estadoLiquidacion == (short) 2)
                || (estadoLiquidacionOld == (short) 1 && estadoLiquidacion == (short) 1));
    }

    public void generarReporte() {
        detalleProcesoAdq = JsfUtil.findDetalle(getRecuperarProceso().getProcesoAdquisicion(), idRubro);
        if (excel) {
            imprimirXls();
        } else {
            imprimirPdf();
        }
    }

    private void imprimirXls() {
        List<VwLiquidacionFinancieraDto> lstLiquidacionFinan = liquidacionFinancieraEJB.findAllLiquidacionFinan(detalleProcesoAdq.getIdDetProcesoAdq(), codigoDepartamento);
        List<VwLiquidacionFinancieraConsolidadoDto> lstLiquidacionFinanConsolidado = liquidacionFinancieraEJB.findAllLiquidacionFinanConsolidado(detalleProcesoAdq.getIdDetProcesoAdq(), codigoDepartamento);
        if (lstLiquidacionFinan.isEmpty()) {
            JsfUtil.mensajeInformacion("No se han encontrado datos");
        } else {
            RptExcel.generarRptLiquidacionFinanciera(codigoDepartamento, lstLiquidacionFinan, lstLiquidacionFinanConsolidado);
        }
    }

    private void imprimirPdf() {
        try {
            ServletContext ctx;
            HashMap param = new HashMap();
            List<JasperPrint> lstRptAImprimir = new ArrayList();

            ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

            param.put("ubicacionImagenes", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
            param.put("pCodigoDepartamento", VarSession.getDepartamentoUsuarioSession());
            param.put("pIdDetProcesoAdq", detalleProcesoAdq.getIdDetProcesoAdq());

            lstRptAImprimir.add(reportesEJB.getRpt(param, Reportes.getPathReporte("sv/gob/mined/apps/reportes/liquidacion/rptLiquiFinanConsolidad.jasper")));
            lstRptAImprimir.add(reportesEJB.getRpt(param, Reportes.getPathReporte("sv/gob/mined/apps/reportes/liquidacion/rptLiquiFinanDetallado.jasper")));

            Reportes.generarReporte(lstRptAImprimir, "rptLiquidacionFinanciera_" + codigoDepartamento.replace(" ", ""));
        } catch (IOException | JRException ex) {
            Logger.getLogger(LiquidacionFinancieraView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
