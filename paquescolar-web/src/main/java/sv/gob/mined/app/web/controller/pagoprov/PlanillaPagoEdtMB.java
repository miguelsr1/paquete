/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProceso;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.UtilFile;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.EMailEJB;
import sv.gob.mined.paquescolar.ejb.PagoProveedoresEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.DetalleDocPago;
import sv.gob.mined.paquescolar.model.DetallePlanilla;
import sv.gob.mined.paquescolar.model.DetalleRequerimiento;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;
import sv.gob.mined.paquescolar.model.PlanillaPago;
import sv.gob.mined.paquescolar.model.PlanillaPagoCheque;
import sv.gob.mined.paquescolar.model.RequerimientoFondos;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.DatosProveDto;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 *
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class PlanillaPagoEdtMB extends RecuperarProceso implements Serializable {

    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;
    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private PagoProveedoresEJB pagoProveedoresEJB;
    @EJB
    private EMailEJB eMailEJB;
    @EJB
    private UtilEJB utilEJB;
    @EJB
    private ReportesEJB reportesEJB;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("Bundle");

    private int indexTab = 0;
    private int rowEdit = 0;
    private int idTipoPlanilla = 0;
    private int idDetProceso;

    private Integer[] tipoDocumentoImp;

    private Boolean cheque = false;
    private Boolean isPlanillaLectura = false;
    private Boolean dlgDetallePlanilla = false;
    private Boolean showPnlCheques = false;
    private Boolean showChequeEntProv = false;
    private Boolean showChequeUsefi = false;
    private Boolean showChequeRenta = false;
    private Boolean dlgEdtDetPlanilla = false;
    private Boolean contratoModificado = false;
    private Boolean tipoPagoEntFinanciera = false;
    private Boolean isRubroUniforme = false;

    private String anho;
    private String emailUnico;
    private String nombreRubro = "";
    private String nombreEntFinanciera = "";
    private String codigoDepartamento = "";

    private BigDecimal idReq = BigDecimal.ZERO;
    private BigDecimal idRubro = BigDecimal.ZERO;
    private BigDecimal idPlanilla = BigDecimal.ZERO;

    private Empresa empresa = new Empresa();
    private EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
    private DetallePlanilla detPlanilla = new DetallePlanilla();
    private PlanillaPago planillaPago = new PlanillaPago();
    private PlanillaPagoCheque chequeFinanProve = new PlanillaPagoCheque();
    private PlanillaPagoCheque chequeUsefi = new PlanillaPagoCheque();
    private PlanillaPagoCheque chequeRenta = new PlanillaPagoCheque();
    private RequerimientoFondos requerimientoFondos = new RequerimientoFondos();

    private List<DatosProveDto> lstEmailProveeCredito = new ArrayList();
    private List<DetallePlanilla> lstDetallePlanilla = new ArrayList();
    private List<PlanillaPago> lstPlanillas = new ArrayList();
    private List<DetalleRequerimiento> lstDetalleRequerimiento = new ArrayList();
    private List<DetalleRequerimiento> lstDetalleRequerimientoSeleccionado = new ArrayList();

    private List<SelectItem> lstTipoDocImp = new ArrayList();

    /**
     * Creates a new instance of PlanillaPagoEdtMB
     */
    public PlanillaPagoEdtMB() {
    }

    @PostConstruct
    public void ini() {
        if (JsfUtil.isExisteParametroUrl("idPlanilla")) {
            //editando planilla
            planillaPago = utilEJB.find(PlanillaPago.class, new BigDecimal(JsfUtil.getParametroUrl("idPlanilla")));
            cargarPlanilla();
        } else {
            planillaPago = new PlanillaPago();
            planillaPago.setIdTipoPlanilla(Short.parseShort(JsfUtil.getParametroUrl("idTipoPlanilla")));
            planillaPago.setIdRequerimiento(utilEJB.find(RequerimientoFondos.class, new BigDecimal(JsfUtil.getParametroUrl("idReq"))));
            planillaPago.setIdEstadoPlanilla((short) 1);

            switch (planillaPago.getIdTipoPlanilla()) {
                case 1:
                    lstDetalleRequerimiento = pagoProveedoresEJB.getLstProveedorByIdRequerimiento(planillaPago.getIdRequerimiento().getIdRequerimiento(), JsfUtil.getParametroUrl("nit"));
                    break;
                case 2:
                    lstDetalleRequerimiento = pagoProveedoresEJB.getDetRequerimientoPendiente(planillaPago.getIdRequerimiento().getIdRequerimiento());
                    break;
                case 3:
                    lstDetalleRequerimiento = pagoProveedoresEJB.getDetRequerimientoPendienteByEntFinan(planillaPago.getIdRequerimiento().getIdRequerimiento(), JsfUtil.getParametroUrl("nombreEntFinan"));
                    break;
            }
        }
        codigoDepartamento = super.getDepartamento();
        documentosAImprimir();
    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">
    public Boolean getCheque() {
        return cheque;
    }

    public void setCheque(Boolean cheque) {
        this.cheque = cheque;
    }

    public List<DetalleRequerimiento> getLstDetalleRequerimiento() {
        return lstDetalleRequerimiento;
    }

    public void setLstDetalleRequerimiento(List<DetalleRequerimiento> lstDetalleRequerimiento) {
        this.lstDetalleRequerimiento = lstDetalleRequerimiento;
    }

    public String getNombreEntFinanciera() {
        return nombreEntFinanciera;
    }

    public void setNombreEntFinanciera(String nombreEntFinanciera) {
        this.nombreEntFinanciera = nombreEntFinanciera;
    }

    public Boolean getShowChequeCredito() {
        return showChequeEntProv;
    }

    public EntidadFinanciera getEntidadFinanciera() {
        return entidadFinanciera;
    }

    public RequerimientoFondos getRequerimientoFondos() {
        return requerimientoFondos;
    }

    public void setRequerimientoFondos(RequerimientoFondos requerimientoFondos) {
        this.requerimientoFondos = requerimientoFondos;
    }

    public int getNumeroDetalle() {
        return lstDetalleRequerimiento.size();
    }

    public int getCantidadCeSeleccionados() {
        return lstDetalleRequerimientoSeleccionado.size();
    }

    public BigDecimal getMontoCeSeleccionados() {
        BigDecimal monto = BigDecimal.ZERO;
        for (DetalleRequerimiento detalleReq : lstDetalleRequerimientoSeleccionado) {
            monto = monto.add(detalleReq.getMontoTotal());
        }

        return monto;
    }

    public int getIndexTab() {
        return indexTab;
    }

    public void setIndexTab(int indexTab) {
        this.indexTab = indexTab;
    }

    public PlanillaPago getPlanillaPago() {
        return planillaPago;
    }

    public void setPlanillaPago(PlanillaPago planillaPago) {
        this.planillaPago = planillaPago;
    }

    public int getRowEdit() {
        return rowEdit;
    }

    public void setRowEdit(int rowEdit) {
        this.rowEdit = rowEdit;
    }

    public Boolean getIsPlanillaLectura() {
        return isPlanillaLectura;
    }

    public void setIsPlanillaLectura(Boolean isPlanillaLectura) {
        this.isPlanillaLectura = isPlanillaLectura;
    }

    public Boolean getDlgDetallePlanilla() {
        return dlgDetallePlanilla;
    }

    public void setDlgDetallePlanilla(Boolean dlgDetallePlanilla) {
        this.dlgDetallePlanilla = dlgDetallePlanilla;
    }

    public Boolean getShowPnlCheques() {
        return showPnlCheques;
    }

    public void setShowPnlCheques(Boolean showPnlCheques) {
        this.showPnlCheques = showPnlCheques;
    }

    public Boolean getShowChequeEntProv() {
        return showChequeEntProv;
    }

    public void setShowChequeEntProv(Boolean showChequeEntProv) {
        this.showChequeEntProv = showChequeEntProv;
    }

    public Boolean getShowChequeUsefi() {
        return showChequeUsefi;
    }

    public void setShowChequeUsefi(Boolean showChequeUsefi) {
        this.showChequeUsefi = showChequeUsefi;
    }

    public Boolean getShowChequeRenta() {
        return showChequeRenta;
    }

    public void setShowChequeRenta(Boolean showChequeRenta) {
        this.showChequeRenta = showChequeRenta;
    }

    public Boolean getDlgEdtDetPlanilla() {
        return dlgEdtDetPlanilla;
    }

    public void setDlgEdtDetPlanilla(Boolean dlgEdtDetPlanilla) {
        this.dlgEdtDetPlanilla = dlgEdtDetPlanilla;
    }

    public Boolean getContratoModificado() {
        return contratoModificado;
    }

    public void setContratoModificado(Boolean contratoModificado) {
        this.contratoModificado = contratoModificado;
    }

    public String getEmailUnico() {
        return emailUnico;
    }

    public void setEmailUnico(String emailUnico) {
        this.emailUnico = emailUnico;
    }

    public String getNombreRubro() {
        return nombreRubro;
    }

    public void setNombreRubro(String nombreRubro) {
        this.nombreRubro = nombreRubro;
    }

    public BigDecimal getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(BigDecimal idRubro) {
        this.idRubro = idRubro;
    }

    public DetallePlanilla getDetPlanilla() {
        return detPlanilla;
    }

    public void setDetPlanilla(DetallePlanilla detPlanilla) {
        this.detPlanilla = detPlanilla;
    }

    public List<DatosProveDto> getLstEmailProveeCredito() {
        return lstEmailProveeCredito;
    }

    public void setLstEmailProveeCredito(List<DatosProveDto> lstEmailProveeCredito) {
        this.lstEmailProveeCredito = lstEmailProveeCredito;
    }

    public List<DetallePlanilla> getLstDetallePlanilla() {
        return lstDetallePlanilla;
    }

    public void setLstDetallePlanilla(List<DetallePlanilla> lstDetallePlanilla) {
        this.lstDetallePlanilla = lstDetallePlanilla;
    }

    public int getIdTipoPlanilla() {
        return idTipoPlanilla;
    }

    public void setIdTipoPlanilla(int idTipoPlanilla) {
        this.idTipoPlanilla = idTipoPlanilla;
    }

    public Boolean getTipoPagoEntFinanciera() {
        return tipoPagoEntFinanciera;
    }

    public void setTipoPagoEntFinanciera(Boolean tipoPagoEntFinanciera) {
        this.tipoPagoEntFinanciera = tipoPagoEntFinanciera;
    }

    public void setEntidadFinanciera(EntidadFinanciera entidadFinanciera) {
        this.entidadFinanciera = entidadFinanciera;
    }

    public PlanillaPagoCheque getChequeFinanProve() {
        return chequeFinanProve;
    }

    public void setChequeFinanProve(PlanillaPagoCheque chequeFinanProve) {
        this.chequeFinanProve = chequeFinanProve;
    }

    public PlanillaPagoCheque getChequeUsefi() {
        return chequeUsefi;
    }

    public void setChequeUsefi(PlanillaPagoCheque chequeUsefi) {
        this.chequeUsefi = chequeUsefi;
    }

    public PlanillaPagoCheque getChequeRenta() {
        return chequeRenta;
    }

    public void setChequeRenta(PlanillaPagoCheque chequeRenta) {
        this.chequeRenta = chequeRenta;
    }

    public int getIdDetProceso() {
        return idDetProceso;
    }

    public void setIdDetProceso(int idDetProceso) {
        this.idDetProceso = idDetProceso;
    }

    public Integer[] getTipoDocumentoImp() {
        return tipoDocumentoImp;
    }

    public void setTipoDocumentoImp(Integer[] tipoDocumentoImp) {
        this.tipoDocumentoImp = tipoDocumentoImp;
    }

    public Boolean getIsRubroUniforme() {
        return isRubroUniforme;
    }

    public void setIsRubroUniforme(Boolean isRubroUniforme) {
        this.isRubroUniforme = isRubroUniforme;
    }

    public BigDecimal getIdReq() {
        return idReq;
    }

    public void setIdReq(BigDecimal idReq) {
        this.idReq = idReq;
    }

    public BigDecimal getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(BigDecimal idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<PlanillaPago> getLstPlanillas() {
        return lstPlanillas;
    }

    public void setLstPlanillas(List<PlanillaPago> lstPlanillas) {
        this.lstPlanillas = lstPlanillas;
    }

    public List<SelectItem> getLstTipoDocImp() {
        return lstTipoDocImp;
    }

    public void setLstTipoDocImp(List<SelectItem> lstTipoDocImp) {
        this.lstTipoDocImp = lstTipoDocImp;
    }

    public List<DetalleRequerimiento> getLstDetalleRequerimientoSeleccionado() {
        return lstDetalleRequerimientoSeleccionado;
    }

    public void setLstDetalleRequerimientoSeleccionado(List<DetalleRequerimiento> lstDetalleRequerimientoSeleccionado) {
        this.lstDetalleRequerimientoSeleccionado = lstDetalleRequerimientoSeleccionado;
    }

    //</editor-fold>
    public void notificacion() {
        if (planillaPago.getIdTipoPlanilla() == 3) {
            emailUnico = pagoProveedoresEJB.getEMailEntidadFinancieraById(lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getCodEntFinanciera());
            lstEmailProveeCredito = pagoProveedoresEJB.getLstNitProveeByIdPlanilla(planillaPago.getIdPlanilla());

            Logger.getLogger(PagoProveedoresController.class.getName()).log(Level.INFO, "Email Entidad {0}", emailUnico);
            for (DatosProveDto datosProveDto : lstEmailProveeCredito) {
                Logger.getLogger(PagoProveedoresController.class.getName()).log(Level.INFO, "Email {0} Proveedor {1} {2}", new String[]{datosProveDto.getCorreoElectronico(), datosProveDto.getRazonSocial(), datosProveDto.getNumeroNit()});
            }
        } else if (planillaPago.getIdTipoPlanilla() == 1) {
            emailUnico = pagoProveedoresEJB.getEMailProveedorByNit(lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getNumeroNit());
            Logger.getLogger(PagoProveedoresController.class.getName()).log(Level.INFO, "Email Entidad {0}", emailUnico);
        }

        if (emailUnico == null || emailUnico.isEmpty()) {
            JsfUtil.mensajeAlerta("No se puede enviar la notificación debido a que el proveedor/entidad financiera no posee registro de correo electrónico");
        } else {
            PrimeFaces.current().ajax().update("dlgEMailNotificacion");
        }
    }

    public void showDlgDetallePlanilla() {
        dlgDetallePlanilla = true;
        switch (idRubro.intValue()) {
            case 1:
                nombreRubro = "SERVICIOS DE CONFECCION DE UNIFORMES";
                break;
            case 2:
                nombreRubro = "SUMINISTRO DE PAQUETES DE UTILES ESCOLARES";
                break;
            case 3:
                nombreRubro = "PRODUCCION DE ZAPATOS";
                break;
            case 4:
                nombreRubro = "SERVICIOS DE CONFECCION DEL 1er UNIFORMES";
                break;
            case 5:
                nombreRubro = "SERVICIOS DE CONFECCION DEL 2do UNIFORMES";
                break;
        }
        PrimeFaces.current().ajax().update("dlgDetallePlanilla");
    }

    public void showDlgEdtDetPlanilla() {
        dlgEdtDetPlanilla = true;
        contratoModificado = false;

        if (detPlanilla.getIdDetalleDocPago().getIdDetalleDocPago() != null) {
            contratoModificado = detPlanilla.getIdDetalleDocPago().getContratoModif() == 1;
            detPlanilla.setMontoCheque(detPlanilla.getIdDetalleDocPago().getMontoActual());
            if (!contratoModificado && (detPlanilla.getCheque() != null && detPlanilla.getCheque() == 1)) {
                detPlanilla.setMontoCheque(detPlanilla.getMontoOriginal());
            } else if (contratoModificado && (detPlanilla.getCheque() != null && detPlanilla.getCheque() == 1)) {
                detPlanilla.setMontoCheque(detPlanilla.getIdDetalleDocPago().getMontoActual());
            }
            cheque = (detPlanilla.getCheque() != null && detPlanilla.getCheque() == 1);
            PrimeFaces.current().ajax().update("dlgEdtDetPlanilla");
            PrimeFaces.current().ajax().update("pngEdit");
        }
    }

    public void eliminarDetalle() {
        if (detPlanilla != null) {
            if (detPlanilla.getEstadoEliminacion().intValue() == 0) {
                if (detPlanilla.getIdDetallePlanilla() != null) {
                    detPlanilla.setEstadoEliminacion((short) 1);
                } else {
                    lstDetallePlanilla.remove(rowEdit);
                }
            } else {
                detPlanilla.setEstadoEliminacion((short) 0);
            }

            detPlanilla = new DetallePlanilla();
        } else {
            JsfUtil.mensajeAlerta("Debe seleccionar un detalle para poder eliminarlo.");
        }
    }

    public BigInteger getCantidadOriginalTotal() {
        BigInteger total = BigInteger.ZERO;
        for (DetallePlanilla detallePlanilla1 : lstDetallePlanilla) {
            if (detallePlanilla1.getCantidadOriginal() != null) {
                total = total.add(detallePlanilla1.getCantidadOriginal());
            }
        }
        return total;
    }

    public BigDecimal getMontoOriginalTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetallePlanilla detPla : lstDetallePlanilla) {
            if (detPla.getCantidadOriginal() != null) {
                total = total.add(detPla.getMontoOriginal());
            }
        }
        return total;
    }

    public BigInteger getCantidadActualTotal() {
        BigInteger total = BigInteger.ZERO;
        for (DetallePlanilla detPla : lstDetallePlanilla) {
            if (detPla.getCantidadActual() != null) {
                total = total.add(detPla.getCantidadActual());
            }
        }
        return total;
    }

    public BigDecimal getMontoActualTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetallePlanilla detPla : lstDetallePlanilla) {
            if (detPla.getCantidadActual() != null && detPla.getEstadoEliminacion() == 0) {
                total = total.add(detPla.getMontoActual());
            }
        }
        return total;
    }

    public BigDecimal getMontoRentaTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetallePlanilla detPla : lstDetallePlanilla) {
            if (detPla.getCantidadActual() != null && detPla.getIdDetalleDocPago().getMontoRenta() != null) {
                total = total.add(detPla.getIdDetalleDocPago().getMontoRenta());
            }
        }
        return total;
    }

    public void generarArchivoDePagoFinaciera() {
        if (showChequeEntProv) {
            StringBuilder sb = new StringBuilder();
            Boolean esCorrecto = true;
            chequeFinanProve.setTransferencia((short) 1);
            pagoProveedoresEJB.guardarPlanillaPagoCheque(chequeFinanProve);

            switch (planillaPago.getIdTipoPlanilla()) {
                case 1://un solo proveedor
                    if (empresa.getNumeroCuenta() == null || empresa.getNumeroCuenta().trim().isEmpty()) {
                        JsfUtil.mensajeError("El Proveedor/La Financiera, no tiene registrada la cuenta bancaria para efectuar la transferencia electrónica.");
                        esCorrecto = false;
                    }
                    break;
                case 3://credito
                    if (entidadFinanciera.getNumeroCuenta() == null || entidadFinanciera.getNumeroCuenta().trim().isEmpty()) {
                        JsfUtil.mensajeError("El Proveedor/La Financiera, no tiene registrada la cuenta bancaria para efectuar la transferencia electrónica.");
                        esCorrecto = false;
                    }
                    break;
            }

            if (esCorrecto) {
                try {
                    switch (idTipoPlanilla) {
                        case 1: //un solo proveedor
                            sb.append(empresa.getNumeroCuenta()).append(",").
                                    append(empresa.getRazonSocial()).append(",").
                                    append(" ").append(",").
                                    append(chequeFinanProve.getMontoCheque()).append(",").
                                    append("1").append(",").
                                    append("Abono por pago de contrato de paquete escolar");
                            break;
                        case 3: //entidad financiera
                            sb.append(entidadFinanciera.getNumeroCuenta()).append(";").
                                    append(entidadFinanciera.getNombreEntFinan()).append(";").
                                    append(" ").append(";").
                                    append(chequeFinanProve.getMontoCheque()).append(";").
                                    append("1").append(";").
                                    append("Abono por pago de contrato(s) de paquete(s) escolar(es) asociado(s) a un crédito otorgado");
                            break;
                    }
                    notificacion();
                    UtilFile.downloadFileTextoPlano(sb.toString(), "transferencia-" + planillaPago.getIdPlanilla(), "csv");
                    Logger.getLogger(PagoProveedoresController.class
                            .getName()).log(Level.INFO, "Archivo: Genera: {0}\n======================================\n{1}", new Object[]{VarSession.getVariableSessionUsuario(), sb.toString()});
                } catch (IOException ex) {
                    Logger.getLogger(PlanillaPagoEdtMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void eliminarPlanilla() {
        pagoProveedoresEJB.eliminarPlanilla(idPlanilla, VarSession.getVariableSessionUsuario());
        buscarPlanillas();
    }

    public void buscarPlanillas() {
        if (idRubro != null) {
            if (idReq != null) {
                buscarRequerimientoqOrPlanilla();
                lstPlanillas = proveedorEJB.getLstPlanillaPagos(idReq);
                if (lstPlanillas.isEmpty()) {
                    JsfUtil.mensajeInformacion("El requerimiento seleccionado no tienen planillas registradas");
                }
                documentosAImprimir();
            } else {
                JsfUtil.mensajeInformacion("Debe de seleccionar un requerimiento.");
            }
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar un rubro de adquisición.");
        }
    }

    private void buscarRequerimientoqOrPlanilla() {
        showChequeEntProv = false;
        showChequeRenta = false;
        showChequeUsefi = false;
        showPnlCheques = false;

        isRubroUniforme = ((idRubro.intValue() == 1) || (idRubro.intValue() == 4) || (idRubro.intValue() == 5));
        idDetProceso = anhoProcesoEJB.getDetProcesoAdq(super.getProcesoAdquisicion(), idRubro).getIdDetProcesoAdq();
    }

    private void documentosAImprimir() {
        lstTipoDocImp.clear();
        lstTipoDocImp.add(new SelectItem(1, "Planilla de Pago"));
        lstTipoDocImp.add(new SelectItem(2, "Matriz de Pago"));
        lstTipoDocImp.add(new SelectItem(3, "Formato de Entrega Cheque"));
        lstTipoDocImp.add(new SelectItem(4, "Planilla de Reintegro"));
        switch (idRubro.intValue()) {
            case 1:
            case 4:
            case 5:
                lstTipoDocImp.add(new SelectItem(5, "Planilla de Renta"));
                break;
        }
    }

    public void cargarPlanilla() {
        idTipoPlanilla = planillaPago.getIdTipoPlanilla().intValue();
        lstDetallePlanilla = planillaPago.getDetallePlanillaList();

        switch (planillaPago.getIdTipoPlanilla()) {
            case 1:
                showChequeEntProv = true;

                empresa = proveedorEJB.findEmpresaByNit(lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getNumeroNit());
                recuperarContratosByProveedor(planillaPago.getIdRequerimiento().getIdRequerimiento(), lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getNumeroNit());
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
                recuperarContratosByIdRequerimiento(planillaPago.getIdRequerimiento().getIdRequerimiento());
                break;
            case 3:
                showChequeEntProv = (planillaPago.getIdRequerimiento().getCredito() == 1);
                if (!lstDetallePlanilla.isEmpty()) {
                    nombreEntFinanciera = lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getNombreEntFinan();
                    entidadFinanciera = proveedorEJB.getEntidadByNombre(nombreEntFinanciera);
                    recuperarContratosByEntidadFinanciera(planillaPago.getIdRequerimiento().getIdRequerimiento(), nombreEntFinanciera);
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

    private void recuperarContratosByProveedor(BigDecimal idRequerimiento, String numeroNit) {
        lstDetalleRequerimiento = pagoProveedoresEJB.getLstProveedorByIdRequerimiento(idRequerimiento, numeroNit);
    }

    private void recuperarContratosByIdRequerimiento(BigDecimal idRequerimiento) {
        lstDetalleRequerimiento = pagoProveedoresEJB.getDetRequerimientoPendiente(idRequerimiento);
    }

    private void recuperarContratosByEntidadFinanciera(BigDecimal idRequerimiento, String nombreEntFinan) {
        lstDetalleRequerimiento = pagoProveedoresEJB.getDetRequerimientoPendienteByEntFinan(idRequerimiento, nombreEntFinan);
    }

    /**
     * @return
     */
    public String redirecionPlanillaLst() {
        return "reg07PlanillaPagoLst.mined";
    }

    public void guardarPlanilla() {
        boolean guardarNuevo = (planillaPago.getIdPlanilla() == null);

        //Verificar que los montos (actual y original) sean diferentes para ingreso de los datos del cheque USEFI
        //Esta validacion se realiza unicamente cuando la planilla se esta creando
        if (!showChequeUsefi) {
            for (DetallePlanilla detPla : lstDetallePlanilla) {
                if (detPla.getIdDetalleDocPago().getMontoActual() != null && detPla.getIdDetalleDocPago().getContratoModif() == 1) {
                    showChequeUsefi = (detPla.getIdDetalleDocPago().getMontoActual().compareTo(detPla.getMontoOriginal()) == -1);
                    if (showChequeUsefi) {
                        break;
                    }
                }
            }
        }

        //guardar datos de fecha y numero de cheque en el detalle de planilla
        if (isRubroUniforme && chequeFinanProve.getFechaCheque() != null && chequeFinanProve.getNumeroCheque() != null) {
            for (DetallePlanilla detPla : lstDetallePlanilla) {
                detPla.setFechaCheque(chequeFinanProve.getFechaCheque());
                detPla.setNumCheque(chequeFinanProve.getNumeroCheque());
            }
        }

        if (planillaPago.getIdPlanilla() == null) {
            planillaPago.setFechaInsercion(new Date());
            planillaPago.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
            planillaPago.setEstadoEliminacion((short) 0);
        } else {
            planillaPago.setFechaModificacion(new Date());
            planillaPago.setUsuarioModificacion(VarSession.getVariableSessionUsuario());
        }

        //Habilitar visibilidad de cheques
        if (guardarNuevo) {
            showChequeRenta = isRubroUniforme;
            showPnlCheques = (showChequeEntProv || showChequeRenta || showChequeUsefi);
        }

        planillaPago = pagoProveedoresEJB.guardarPlanillaPago(planillaPago);
        for (DetallePlanilla detPla : lstDetallePlanilla) {
            pagoProveedoresEJB.guardarDetallePlanilla(detPla);
        }
        planillaPago = utilEJB.find(PlanillaPago.class, planillaPago.getIdPlanilla());
        lstDetallePlanilla = planillaPago.getDetallePlanillaList();

        if (planillaPago.getIdPlanilla() != null) {
            JsfUtil.mensajeInformacion("Datos almacenados satisfactoriamente.");

            if (guardarNuevo) {
                indexTab = 1;
                JsfUtil.mensajeAlerta("Debe de completar el registro de la información de los Cheques.");
                PrimeFaces.current().ajax().update("tabView");
            }

            //Persistiendo los datos de los cheques
            guardarCheques();
            PrimeFaces.current().ajax().update("tbDetallePlanilla");
        } else {
            JsfUtil.mensajeError("Ocurrio un error en la operación.");
        }
    }

    /**
     * Almacenar datos de los cheques necesarios
     */
    private void guardarCheques() {
        planillaPago = utilEJB.find(PlanillaPago.class, planillaPago.getIdPlanilla());
        BigDecimal mRenta = BigDecimal.ZERO;
        BigDecimal montoTotalActual;

        if (isRubroUniforme) {
            for (DetallePlanilla detPla : lstDetallePlanilla) {
                if (detPla.getIdDetalleDocPago().getMontoRenta() != null) {
                    mRenta = mRenta.add(detPla.getIdDetalleDocPago().getMontoRenta());
                }
            }
            montoTotalActual = getMontoActualTotal().add(mRenta.negate());
        } else {
            montoTotalActual = getMontoActualTotal();
        }

        if (showChequeEntProv) {
            chequeFinanProve.setMontoCheque(montoTotalActual);
            if (chequeFinanProve.getIdPlanillaCheque() == null) {
                chequeFinanProve.setaFavorDe((short) (idTipoPlanilla == 1 ? 3 : 0));
                chequeFinanProve.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                chequeFinanProve.setFechaInsercion(new Date());
                chequeFinanProve.setEstadoEliminacion((short) 0);
                chequeFinanProve.setTransferencia((short) 0);
                chequeFinanProve.setIdPlanilla(planillaPago);
            } else {
                chequeFinanProve.setTransferencia(tipoPagoEntFinanciera ? 1 : (short) 0);
                chequeFinanProve.setUsuarioModificacion(VarSession.getVariableSessionUsuario());
                chequeFinanProve.setFechaModificacion(new Date());
            }

            chequeFinanProve = pagoProveedoresEJB.guardarPlanillaPagoCheque(chequeFinanProve);
        }

        if (showChequeUsefi) {
            chequeUsefi.setMontoCheque(getMontoOriginalTotal().add(montoTotalActual.negate()).add(mRenta.negate()));
            if (chequeUsefi.getIdPlanillaCheque() == null) {
                chequeUsefi.setaFavorDe((short) 1);
                chequeUsefi.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                chequeUsefi.setFechaInsercion(new Date());
                chequeUsefi.setEstadoEliminacion((short) 0);
                chequeUsefi.setIdPlanilla(planillaPago);
            } else {
                chequeUsefi.setUsuarioModificacion(VarSession.getVariableSessionUsuario());
                chequeUsefi.setFechaModificacion(new Date());
            }
            chequeUsefi = pagoProveedoresEJB.guardarPlanillaPagoCheque(chequeUsefi);
        }

        if (showChequeRenta) {
            if (chequeRenta.getIdPlanillaCheque() == null) {
                chequeRenta.setaFavorDe((short) 2);
                chequeRenta.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                chequeRenta.setFechaInsercion(new Date());
                chequeRenta.setEstadoEliminacion((short) 0);
                chequeRenta.setIdPlanilla(planillaPago);
            } else {
                chequeRenta.setUsuarioModificacion(VarSession.getVariableSessionUsuario());
                chequeRenta.setFechaModificacion(new Date());
            }

            chequeRenta.setMontoCheque(mRenta);
            chequeRenta = pagoProveedoresEJB.guardarPlanillaPagoCheque(chequeRenta);
        }
    }

    public void enviarCorreos() {
        if (null == planillaPago.getIdTipoPlanilla()) {
        } else {
            switch (planillaPago.getIdTipoPlanilla()) {
                case 1:
                case 3:
                    //envio de notificacion a Entidad/Proveedor
                    //eMailEJB.enviarMail("Paquete Escolar - Notificación de Pago ", "miguel.sanchez@mined.gob.sv", getMensajeDeNotificacion(planillaPago, planillaPago.getDetallePlanillaList(), false));
                    eMailEJB.enviarMail("Paquete Escolar - Notificación de Pago ", emailUnico, getMensajeDeNotificacion(planillaPago, lstDetallePlanilla, false));

                    //Si es planilla tipo credito, enviar notificacion a proveedores incluidos en la planilla de pago
                    if (planillaPago.getIdTipoPlanilla() == 3) {
                        for (DatosProveDto datosProveDto : lstEmailProveeCredito) {
                            if (datosProveDto.getCorreoElectronico() != null && !datosProveDto.getCorreoElectronico().isEmpty()) {
                                //eMailEJB.enviarMail("Paquete Escolar - Notificación de Pago ", "miguel.sanchez@mined.gob.sv", getMensajeDeNotificacion(planillaPago, pagoProveedoresEJB.getLstDetallePlanillaByIdPlanillaAndNit(planillaPago.getIdPlanilla(), datosProveDto.getNumeroNit()), true));
                                eMailEJB.enviarMail("Paquete Escolar - Notificación de Pago ", datosProveDto.getCorreoElectronico(), getMensajeDeNotificacion(planillaPago, pagoProveedoresEJB.getLstDetallePlanillaByIdPlanillaAndNit(planillaPago.getIdPlanilla(), datosProveDto.getNumeroNit()), true));
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Método que devuelve el mensage a enviar a la entidad financiera o al
     * proveedor
     *
     * @param plaPago
     * @return
     */
    private String getMensajeDeNotificacion(PlanillaPago plaPago, List<DetallePlanilla> lstDetallePlanilla, boolean isProveedores) {
        StringBuilder sb = new StringBuilder("");
        if (isProveedores) {
            sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("pagoprov.email.cabeceraNotificacion"), lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getRazonSocial()));
        } else {
            if (plaPago.getIdTipoPlanilla() == 3) {
                sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("pagoprov.email.cabeceraNotificacion"), lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getNombreEntFinan()));
            } else if (plaPago.getIdTipoPlanilla() == 1) {
                sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("pagoprov.email.cabeceraNotificacion"), lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getRazonSocial()));
            }
        }

        if (isProveedores) {
            sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("pagoprov.email.cabeceraMensajeProv"), nombreEntFinanciera));
        } else {
            sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("pagoprov.email.cabeceraMensaje"), planillaPago.getIdRequerimiento().getFormatoRequerimiento(), JsfUtil.getFormatoNum(planillaPago.getMontoTotal(), false)));
        }
        sb.append(RESOURCE_BUNDLE.getString("pagoprov.email.tablaDetalle.header"));
        for (DetallePlanilla detalle : lstDetallePlanilla) {
            sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("pagoprov.email.tablaDetalle.detalle"), detalle.getCodigoEntidad().getCodigoEntidad(), detalle.getCodigoEntidad().getNombre(), detalle.getIdDetalleDocPago().getIdDetRequerimiento().getNumeroNit(), detalle.getIdDetalleDocPago().getIdDetRequerimiento().getRazonSocial(), JsfUtil.getFormatoNum(detalle.getMontoActual(), false)));
        }
        //agregando fila de totales (unicamente si la planilla de pago contiene más de 1 contrato)
        if (lstDetallePlanilla.size() > 1) {
            sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("pagoprov.email.tablaDetalle.footer"),
                    JsfUtil.getFormatoNum(getMontoActualTotal(), false)));
        }
        sb.append(RESOURCE_BUNDLE.getString("pagoprov.email.tablaDetalle.fin"));
        sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("pagoprov.email.finNotificacion"), JsfUtil.getFechaString(planillaPago.getFechaInsercion()), JsfUtil.getNombreDepartamentoByCodigo(super.getDepartamento())));

        return sb.toString();
    }

    public void imprimirDocumentos() {
        boolean tempChequeEntPro;
        String rpt = "";
        String pNombreCheque;

        List<JasperPrint> jasperPrintList = new ArrayList();
        //artificio para impresion de planillas creadas previo a la tipificación de planillas
        if (planillaPago.getIdEstadoPlanilla() == 0) {
            tempChequeEntPro = planillaPago.getIdRequerimiento().getCredito() == 1;
            pNombreCheque = nombreEntFinanciera;
        } else {
            tempChequeEntPro = showChequeEntProv;
            switch (planillaPago.getIdTipoPlanilla()) {
                case 1:
                    pNombreCheque = lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getRazonSocial();
                    break;
                case 3:
                    pNombreCheque = nombreEntFinanciera;
                    break;
                default:
                    pNombreCheque = "";
                    break;
            }
        }

        for (Integer idRpt : tipoDocumentoImp) {
            switch (idRpt) {
                case 1: //Planilla de Pago
                    rpt = tempChequeEntPro ? "rptTransferenciaCrediCheque" : "rptTransferenciaCheque";
                    break;
                case 2: //Matriz de Pago
                    rpt = tempChequeEntPro ? "rptMatrizPagoCredito" : "rptMatrizPago";
                    break;
                case 3: //Formato Entrega de Cheques
                    rpt = tempChequeEntPro ? "rptFormatoEntregaChequeCredito" : "rptFormatoEntregaCheque";
                    break;
                case 4: //Planilla de Reintegro
                    rpt = "rptFormatoReintegro";
                    break;
                case 5: //Planilla Renta
                    rpt = "rptTransferenciaRenta";
                    break;
            }
            jasperPrintList.add(imprimirRptPlanilla(rpt, pNombreCheque));
        }
        Reportes.generarReporte(jasperPrintList, "rptsPlanilla-" + planillaPago.getIdPlanilla());
    }

    public JasperPrint imprimirRptPlanilla(String rpt, String pNombreCheque) {
        JasperPrint jp;
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        HashMap param = new HashMap();
        param.put("pEscudo", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
        param.put("pDepartamento", JsfUtil.getNombreDepartamentoByCodigo(codigoDepartamento));
        param.put("pIdPlanilla", planillaPago.getIdPlanilla().intValue());
        param.put("pAFavorDe", (short) (planillaPago.getIdTipoPlanilla() == 1 ? 3 : 0));
        param.put("pNombreCheque", pNombreCheque);
        jp = reportesEJB.getRpt(param, PagoProveedoresController.class.getClassLoader().getResourceAsStream(("sv/gob/mined/apps/reportes/pagoproveedor" + File.separator + rpt + ".jasper")));
        return jp;
    }

    public JasperPrint imprimirRpt(RequerimientoFondos req, String nombreDepartamento, String nombreRpt, String nombrePdf) {
        JasperPrint jp;
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        HashMap param = new HashMap();
        param.put("pEscudo", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
        param.put("pDepartamental", nombreDepartamento);
        param.put("pUniforme", idDetProceso == 25 ? 1 : 0);
        param.put("pIdRequerimiento", req.getIdRequerimiento().intValue());
        param.put("pAnho", "20" + anho);
        jp = reportesEJB.getRpt(param, PagoProveedoresController.class.getClassLoader().getResourceAsStream(("sv/gob/mined/apps/reportes/pagoproveedor" + File.separator + nombreRpt)));
        return jp;
    }

    public JasperPrint imprimirRptPagoProve(String nombreDepartamento) {
        JasperPrint jp;
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        HashMap param = new HashMap();
        param.put("pEscudo", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
        param.put("pNombreDepartamento", nombreDepartamento);
        param.put("pAnho", "20" + anho);
        param.put("pUsuario", VarSession.getVariableSessionUsuario());
        param.put("pPagadorDepartamental", pagoProveedoresEJB.getNombrePagadorByCodDepa(super.getDepartamento()));
        DatosProveDto datos = new DatosProveDto();
        datos.setLstDetalle(lstEmailProveeCredito);
        List<DatosProveDto> lst = new ArrayList();
        lst.add(datos);
        jp = reportesEJB.getRpt(param, PagoProveedoresController.class.getClassLoader().getResourceAsStream(("sv/gob/mined/apps/reportes/pagoproveedor/rptPagoProvee.jasper")), lst);
        return jp;
    }

    public void editEdtDetPlanilla() {
        Boolean montoValidado = true;
        detPlanilla.setIdBanco(proveedorEJB.getLstBancos().get(0));
        detPlanilla.setCheque(cheque ? (short) 1 : 0);
        detPlanilla.getIdDetalleDocPago().setContratoModif((short) (contratoModificado ? 1 : 0));
        if (contratoModificado) {
            montoValidado = (detPlanilla.getMontoActual().compareTo(detPlanilla.getIdDetalleDocPago().getMontoActual()) != -1);
            if (montoValidado) {
                detPlanilla.setCantidadActual(detPlanilla.getIdDetalleDocPago().getCantidadActual());
                detPlanilla.setMontoActual(detPlanilla.getIdDetalleDocPago().getMontoActual());
            } else {
                JsfUtil.mensajeAlerta("El monto actual no puede superar al monto original!");
            }
        } else {
            detPlanilla.setCantidadActual(detPlanilla.getCantidadOriginal());
            detPlanilla.setMontoActual(detPlanilla.getMontoOriginal());

        }

        if (!montoValidado) {
            if (showChequeEntProv) {
                detPlanilla.setCheque((short) 1);
                detPlanilla.setNumCheque(chequeFinanProve.getNumeroCheque());
                detPlanilla.setMontoCheque(chequeFinanProve.getMontoCheque());
                detPlanilla.setFechaCheque(chequeFinanProve.getFechaCheque());
            }
            detPlanilla.setCheque(cheque ? (short) 1 : 0);
            contratoModificado = false;
            cheque = false;
            dlgEdtDetPlanilla = false;
        }
    }

    public void closeDlgEdtDetPlanilla() {
        contratoModificado = false;
        cheque = false;
        dlgEdtDetPlanilla = false;
    }

    public void addCeSeleccionadosADetallePlanilla() {
        for (DetalleRequerimiento detalleReq : lstDetalleRequerimientoSeleccionado) {
            boolean isRepetido = false;

            for (DetallePlanilla detPla : lstDetallePlanilla) {
                if (detPla.getIdDetalleDocPago().getIdDetRequerimiento().getIdContrato().intValue()
                        == detalleReq.getIdContrato().intValue()) {
                    isRepetido = true;
                    break;
                }
            }
            if (!isRepetido) {
                addDetPlanilla(detalleReq);
            }
        }
        closeDlgDetallePlanilla();
    }

    private void addDetPlanilla(DetalleRequerimiento detalleReq) {
        DetallePlanilla detPla = new DetallePlanilla();
        detPla.setCantidadOriginal(new BigInteger(detalleReq.getCantidadTotal().toString()));
        detPla.setMontoOriginal(detalleReq.getMontoTotal());
        detPla.setCantidadActual(BigInteger.ZERO);
        detPla.setMontoActual(BigDecimal.ZERO);
        detPla.setCodigoEntidad(utilEJB.find(VwCatalogoEntidadEducativa.class, detalleReq.getCodigoEntidad()));
        detPla.setIdPlanilla(planillaPago);
        detPla.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
        detPla.setFechaInsercion(new Date());
        detPla.setEstadoEliminacion((short) 0);
        lstDetallePlanilla.add(detPla);

        DetalleDocPago detDocPago = detalleReq.getRegCompleto() ? detalleReq.getDetalleDocPagoList().get(0) : null;
        if (detDocPago != null && detDocPago.getIdDetalleDocPago() != null) {

            if (detDocPago.getContratoModif() == 1) {
                detPla.setCantidadActual(detDocPago.getCantidadActual());
                detPla.setMontoActual(detDocPago.getMontoActual());
            } else {
                detPla.setCantidadActual(detPla.getCantidadOriginal());
                detPla.setMontoActual(detPla.getMontoOriginal());
            }

            detPla.setIdDetalleDocPago(detDocPago);
        }
    }

    public void closeDlgDetallePlanilla() {
        cheque = false;
        contratoModificado = false;
        dlgDetallePlanilla = false;
        lstDetalleRequerimientoSeleccionado.clear();
    }

    public void validarDetalleRequerimiento(SelectEvent event) {
        if (!((DetalleRequerimiento) event.getObject()).getRegCompleto()) {
            lstDetalleRequerimientoSeleccionado.remove((DetalleRequerimiento) event.getObject());
            JsfUtil.mensajeAlerta("Este contrato no tiene ingresada la información del documento de pago. Por favor ingresarlo");
        }
    }

    public void pagarCheque() {
        if (!contratoModificado && cheque) {
            detPlanilla.setMontoCheque(detPlanilla.getMontoOriginal());
        } else if (contratoModificado && cheque) {
            detPlanilla.setMontoCheque(detPlanilla.getIdDetalleDocPago().getMontoActual());
        }
    }
}
