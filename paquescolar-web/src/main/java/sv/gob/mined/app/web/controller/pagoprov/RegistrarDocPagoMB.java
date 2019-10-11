/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.PagoProveedoresEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.DetalleDocPago;
import sv.gob.mined.paquescolar.model.DetalleModificativa;
import sv.gob.mined.paquescolar.model.DetallePlanilla;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.DetalleRequerimiento;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;
import sv.gob.mined.paquescolar.model.PlanillaPago;
import sv.gob.mined.paquescolar.model.PlanillaPagoCheque;
import sv.gob.mined.paquescolar.model.RequerimientoFondos;
import sv.gob.mined.paquescolar.model.ResolucionesModificativas;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.DatosProveDto;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class RegistrarDocPagoMB extends RecuperarProcesoUtil implements Serializable {

    private int ajusteRenta = 0;
    private int rowEdit = 0;
    private int idTipoPlanilla = 0;

    private Boolean contratoModificado = false;
    private Boolean contratoExtinguido = false;
    private Boolean renderMontoRenta = false;
    private Boolean isRubroUniforme = false;
    private Boolean dlgEdtDetDocPago = false;
    private Boolean showPnlCheques = false;
    private Boolean showChequeEntProv = false;
    private Boolean showChequeUsefi = false;
    private Boolean showChequeRenta = false;
    private Boolean isPlanillaLectura = false;
    private Boolean seleccionRequerimiento = false;
    private Boolean seleccionPlanilla = false;
    private Boolean planilla = false;
    private Boolean dlgShowSeleccionProveedor = false;
    private Boolean detallePlanilla = false;
    private Boolean tipoPagoEntFinanciera = false;

    private Boolean filtro = true;

    private String codigoEntidad;
    private String numeroRequerimiento = "";
    private String nombreEntFinanciera = "";

    private BigDecimal idPlanilla;
    private BigDecimal idReq = BigDecimal.ZERO;
    private BigDecimal idRubro = BigDecimal.ZERO;

    private Empresa empresa = new Empresa();
    private EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
    private DetalleDocPago detalleDocPago = new DetalleDocPago();
    private DetalleRequerimiento detalleRequerimiento = new DetalleRequerimiento();
    private DetalleProcesoAdq detalleProcesoAdq = new DetalleProcesoAdq();
    private PlanillaPago planillaPago = new PlanillaPago();
    private PlanillaPagoCheque chequeFinanProve = new PlanillaPagoCheque();
    private PlanillaPagoCheque chequeUsefi = new PlanillaPagoCheque();
    private PlanillaPagoCheque chequeRenta = new PlanillaPagoCheque();
    private RequerimientoFondos requerimientoFondos = new RequerimientoFondos();
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();

    private List<DetalleRequerimiento> lstDetalleRequerimiento = new ArrayList();
    private List<DetallePlanilla> lstDetallePlanilla = new ArrayList();
    private List<DatosProveDto> lstProveedores = new ArrayList();

    @EJB
    private PagoProveedoresEJB pagoProveedoresEJB;
    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private UtilEJB utilEJB;

    public RegistrarDocPagoMB() {
    }

    public Boolean getUsuarioAdministrador() {
        return VarSession.getUsuarioSession().getIdTipoUsuario().getAdministrador().intValue() == 1;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getNumeroRequerimiento() {
        return numeroRequerimiento;
    }

    public void setNumeroRequerimiento(String numeroRequerimiento) {
        this.numeroRequerimiento = numeroRequerimiento;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
    }

    public void setEntidadEducativa(VwCatalogoEntidadEducativa entidadEducativa) {
        this.entidadEducativa = entidadEducativa;
    }

    public List<DetalleRequerimiento> getLstDetalleRequerimiento() {
        return lstDetalleRequerimiento;
    }

    public void setLstDetalleRequerimiento(List<DetalleRequerimiento> lstDetalleRequerimiento) {
        this.lstDetalleRequerimiento = lstDetalleRequerimiento;
    }

    public DetalleRequerimiento getDetalleRequerimiento() {
        return detalleRequerimiento;
    }

    public void setDetalleRequerimiento(DetalleRequerimiento detalleRequerimiento) {
        this.detalleRequerimiento = detalleRequerimiento;
    }

    public int getRowEdit() {
        return rowEdit;
    }

    public void setRowEdit(int rowEdit) {
        this.rowEdit = rowEdit;
    }

    public void buscarRequerimiento() {
        if (!numeroRequerimiento.trim().isEmpty()) {
            requerimientoFondos = proveedorEJB.getRequerimientoByNumero(numeroRequerimiento, (VarSession.isVariableSession("departamentoUsuario") ? getRecuperarProceso().getDepartamento() : null), getRecuperarProceso().getProcesoAdquisicion().getIdProcesoAdq());
            if (requerimientoFondos == null) {
                JsfUtil.mensajeInformacion("No se encontro el requerimiento con número: " + numeroRequerimiento);
            } else if (!codigoEntidad.trim().isEmpty()) {
                lstDetalleRequerimiento = proveedorEJB.getLstDetalleReqByCodEntidadAndProceso(codigoEntidad, getRecuperarProceso().getProcesoAdquisicion(),
                        VarSession.isVariableSession("departamentoUsuario") ? getRecuperarProceso().getDepartamento() : null,
                        numeroRequerimiento);
            } else {
                lstDetalleRequerimiento = requerimientoFondos.getDetalleRequerimientoList();
            }
        } else if (!codigoEntidad.trim().isEmpty()) {
            lstDetalleRequerimiento = proveedorEJB.getLstDetalleReqByCodEntidadAndProceso(codigoEntidad, getRecuperarProceso().getProcesoAdquisicion(), VarSession.isVariableSession("departamentoUsuario") ? getRecuperarProceso().getDepartamento() : null, null);
        }

        if (lstDetalleRequerimiento.isEmpty()) {
            JsfUtil.mensajeAlerta("No se encontrarón resultados.");
        }
    }

    public void showDlgEdtDetDocPago() {
        ajusteRenta = 0;
        contratoModificado = false;
        contratoExtinguido = (detalleRequerimiento.getActivo() == 1);
        renderMontoRenta = false;

        detalleDocPago = proveedorEJB.getDetalleDocPago(detalleRequerimiento);

        if (detalleDocPago.getIdDetalleDocPago() == null) {
            detalleDocPago.setIdDetRequerimiento(detalleRequerimiento);
            detalleDocPago.setIdTipoDocPago(1);
        } else {
            contratoModificado = (detalleDocPago.getContratoModif() == (short) 1);
        }
        isRubroUniforme = (detalleRequerimiento.getIdRequerimiento().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres().intValue() == 1)
                || (detalleRequerimiento.getIdRequerimiento().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres().intValue() == 4)
                || (detalleRequerimiento.getIdRequerimiento().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres().intValue() == 5);

        //verificación del tipo de rubro y personeria natural para determinar si aplica o no el calculo de renta
        renderMontoRenta = (isRubroUniforme
                && proveedorEJB.isPersonaNatural(detalleDocPago.getIdDetRequerimiento().getNumeroNit()));
        //Si aplica, se realiza el calculo del monto de renta
        calculoDeRenta();

        //verificar si ha existido modificativa al contrato original
        if (!contratoModificado) {
            contratoModificado = pagoProveedoresEJB.isContratoConModificativa(new BigDecimal(detalleRequerimiento.getIdContrato()));
        }
        if (contratoModificado) {
            ResolucionesModificativas resModif = pagoProveedoresEJB.getUltimaModificativa(new BigDecimal(detalleRequerimiento.getIdContrato()));
            if (resModif != null) {
                if (resModif.getIdEstadoReserva().intValue() == 1) {
                    JsfUtil.mensajeAlerta("Este contrato tiene una modificativa en estado de DIGITACIÓN");
                } else {
                    detalleDocPago.setFechaModificativa(resModif.getFechaResolucion());
                    detalleDocPago.setCantidadActual(BigInteger.ZERO);
                    detalleDocPago.setMontoActual(BigDecimal.ZERO);
                    for (DetalleModificativa detalle : resModif.getDetalleModificativaList()) {
                        if (detalle.getEstadoEliminacion() == 0) {
                            if (detalle.getIdProducto().intValue() != 1) {
                                detalleDocPago.setCantidadActual(detalleDocPago.getCantidadActual().add(new BigInteger(detalle.getCantidadNew().toString())));
                            }
                            detalleDocPago.setMontoActual(detalleDocPago.getMontoActual().add(detalle.getPrecioUnitarioNew().multiply(new BigDecimal(detalle.getCantidadNew()))));
                        }
                    }
                }
            }
        }

        dlgEdtDetDocPago = true;
    }

    public void calculoDeRenta() {
        if (renderMontoRenta) {
            BigDecimal montoTotalContrato = BigDecimal.ZERO;
            BigDecimal mRenta = BigDecimal.ZERO;

            //CALCULA DE LA RENTA
            if (contratoModificado) {
                if (detalleDocPago.getMontoActual() != null) {
                    montoTotalContrato = detalleDocPago.getMontoActual();
                }
            } else {
                detalleDocPago.setMontoActual(null);
                montoTotalContrato = detalleDocPago.getIdDetRequerimiento().getMontoTotal();
            }
            switch (detalleDocPago.getIdTipoDocPago()) {
                case 1://FACTURA :: RENTA = (MONTO_TOTAL/1.13) * 0.10
                    mRenta = (montoTotalContrato.divide(new BigDecimal(1.13), 2, RoundingMode.HALF_DOWN)).multiply(new BigDecimal(0.10)).setScale(2, RoundingMode.HALF_DOWN);
                    break;
                case 2://RECIBO :: RENTA = MONTO_TOTAL * 0.10
                    mRenta = montoTotalContrato.multiply(new BigDecimal(0.1)).setScale(2, RoundingMode.HALF_DOWN);
                    break;
            }

            switch (ajusteRenta) {
                case 0:
                    break;
                case 1:
                    mRenta = mRenta.add(new BigDecimal(0.01).negate());
                    break;
                case 2:
                    mRenta = mRenta.add(new BigDecimal(0.01));
                    break;
            }

            detalleDocPago.setMontoRenta(mRenta);
        } else {
            detalleDocPago.setMontoRenta(BigDecimal.ZERO);
        }
    }

    public void verPlanillaPago() {
        planillaPago = utilEJB.find(PlanillaPago.class,
                idPlanilla);
        idRubro = planillaPago.getIdRequerimiento().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres();

        reiniciarVisibilidadCheques();
        isRubroUniforme = ((idRubro.intValue() == 1) || (idRubro.intValue() == 4) || (idRubro.intValue() == 5));
        detalleProcesoAdq = planillaPago.getIdRequerimiento().getIdDetProcesoAdq();

        selectPlanilla();
        isPlanillaLectura = true;
        PrimeFaces.current().ajax().update("dlgPlanillaPago");
    }

    public void reiniciarVisibilidadCheques() {
        showChequeEntProv = false;
        showChequeRenta = false;
        showChequeUsefi = false;
        showPnlCheques = false;
    }

    public void selectPlanilla() {
        seleccionPlanilla = false;
        seleccionRequerimiento = false;
        planilla = true;
        detallePlanilla = true;
        filtro = false;
        requerimientoFondos = planillaPago.getIdRequerimiento();
        idTipoPlanilla = planillaPago.getIdTipoPlanilla().intValue();
        lstDetallePlanilla = planillaPago.getDetallePlanillaList();

        switch (planillaPago.getIdTipoPlanilla()) {
            case 1:
                showChequeEntProv = true;
                if (lstDetallePlanilla.isEmpty()) {
                    showDlgSeleccionProveedor();
                    PrimeFaces.current().ajax().update("dlgSeleccionProveedor");
                } else {
                    empresa = proveedorEJB.findEmpresaByNit(lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getNumeroNit());
                    recuperarContratosByProveedor(requerimientoFondos.getIdRequerimiento(), lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getNumeroNit());
                }
                chequeFinanProve = cheque(3); //recuperar cheque para proveedor

                //Si la planilla seleccionada fue creada en el proceso anterior, se debe de recuperar el dato del cheque
                if (chequeFinanProve.getIdPlanillaCheque() == null) {
                    chequeFinanProve.setMontoCheque(BigDecimal.ZERO);
                    for (DetallePlanilla detPla : lstDetallePlanilla) {
                        chequeFinanProve.setNumeroCheque(detPla.getNumCheque());
                        chequeFinanProve.setFechaCheque(detPla.getFechaCheque());
                        if (detPla.getMontoCheque() != null) {
                            chequeFinanProve.setMontoCheque(chequeFinanProve.getMontoCheque().add(detPla.getMontoCheque()));
                        }
                    }
                }
                tipoPagoEntFinanciera = (chequeFinanProve.getTransferencia() == 1);
                break;
            case 2:
                recuperarContratosByIdRequerimiento(requerimientoFondos.getIdRequerimiento());
                break;
            case 3:
                showChequeEntProv = (requerimientoFondos.getCredito() == 1);
                if (!lstDetallePlanilla.isEmpty()) {
                    nombreEntFinanciera = lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getNombreEntFinan();
                    entidadFinanciera = proveedorEJB.getEntidadByNombre(nombreEntFinanciera);
                    recuperarContratosByEntidadFinanciera(requerimientoFondos.getIdRequerimiento(), nombreEntFinanciera);
                }
                chequeFinanProve = cheque(0); //recuperar cheque para financiera
                break;
        }

        //verificación de reintegros en la planilla seleccionada
        showChequeUsefi = pagoProveedoresEJB.isPlanillaConReintegro(planillaPago.getIdPlanilla());
        if (showChequeUsefi) {
            chequeUsefi = cheque(1);
        }

        if (isRubroUniforme) {
            for (DetallePlanilla detPla : lstDetallePlanilla) {
                showChequeRenta = proveedorEJB.isPersonaNatural(detPla.getIdDetalleDocPago().getIdDetRequerimiento().getNumeroNit());
                if (showChequeRenta) {
                    chequeRenta = cheque(2);
                    break;
                }
            }
        }

        showPnlCheques = !(showChequeEntProv == false && showChequeRenta == false && showChequeUsefi == false);
    }

    private void recuperarContratosByEntidadFinanciera(BigDecimal idRequerimiento, String nombreEntFinan) {
        lstDetalleRequerimiento = pagoProveedoresEJB.getDetRequerimientoPendienteByEntFinan(idRequerimiento, nombreEntFinan);
    }

    private void recuperarContratosByProveedor(BigDecimal idRequerimiento, String numeroNit) {
        lstDetalleRequerimiento = pagoProveedoresEJB.getLstProveedorByIdRequerimiento(idRequerimiento, numeroNit);
    }

    private void recuperarContratosByIdRequerimiento(BigDecimal idRequerimiento) {
        lstDetalleRequerimiento = pagoProveedoresEJB.getDetRequerimientoPendiente(idRequerimiento);
    }
    
    /**
     * Metodo que crea el cheque para la financiera, pagaduría USEFI, Renta o
     * Proveedor
     *
     * @param isFinanciera si es 1 devuelve el cheque de la financiera, si es 2
     * el cheque de USEFI, si es 3 cheque de Renta y si es 4 el cheque pera el
     * Proveedor(Planilla de tipo 1)
     */
    private PlanillaPagoCheque cheque(int tipoCheque) {
        PlanillaPagoCheque pagoCheque = pagoProveedoresEJB.getPlanillaPagoCheque(planillaPago, (short) tipoCheque);
        if (pagoCheque.getIdPlanillaCheque() == null) {
            pagoCheque.setEstadoEliminacion((short) 0);
            pagoCheque.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
            pagoCheque.setFechaInsercion(new Date());
            pagoCheque.setaFavorDe((short) tipoCheque);
        }
        return pagoCheque;
    }

    private void showDlgSeleccionProveedor() {
        showChequeEntProv = true;
        dlgShowSeleccionProveedor = true;
        //Se recuperar el listado de proveedores que conforman el requerimiento seleccionado y que estan pendientes de 
        //ser asociados a una planilla de pago
        lstProveedores = pagoProveedoresEJB.getProveedoresPorIdRequerimiento(idReq);
    }
}
