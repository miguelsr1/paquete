/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProceso;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.EMailEJB;
import sv.gob.mined.paquescolar.ejb.PagoProveedoresEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.DetallePlanilla;
import sv.gob.mined.paquescolar.model.DetalleRequerimiento;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;
import sv.gob.mined.paquescolar.model.PlanillaPago;
import sv.gob.mined.paquescolar.model.PlanillaPagoCheque;
import sv.gob.mined.paquescolar.model.RequerimientoFondos;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.DatosProveDto;

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

    private String emailUnico;
    private String nombreRubro = "";
    private String nombreEntFinanciera = "";

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

    private List<DatosProveDto> lstEmailProveeCredito = new ArrayList();
    private List<DetallePlanilla> lstDetallePlanilla = new ArrayList();
    private List<PlanillaPago> lstPlanillas = new ArrayList();
    private List<DetalleRequerimiento> lstDetalleRequerimiento = new ArrayList();

    private List<SelectItem> lstTipoDocImp = new ArrayList();

    /**
     * Creates a new instance of PlanillaPagoEdtMB
     */
    public PlanillaPagoEdtMB() {
    }

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("idPlanilla")) {
            //editando planilla
            planillaPago = utilEJB.find(PlanillaPago.class, new BigDecimal(params.get("idPlanilla")));
            cargarPlanilla();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">
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

    public Boolean getCheque() {
        return cheque;
    }

    public void setCheque(Boolean cheque) {
        this.cheque = cheque;
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

    public EntidadFinanciera getEntidadFinanciera() {
        return entidadFinanciera;
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
            cheque = (detPlanilla.getCheque() != null && detPlanilla.getCheque() == 1);
            pagarCheque();
            PrimeFaces.current().ajax().update("dlgEdtDetPlanilla");
            PrimeFaces.current().ajax().update("pngEdit");
        }
    }

    public void pagarCheque() {
        if (!contratoModificado && cheque) {
            detPlanilla.setMontoCheque(detPlanilla.getMontoOriginal());
        } else if (contratoModificado && cheque) {
            detPlanilla.setMontoCheque(detPlanilla.getIdDetalleDocPago().getMontoActual());
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
                JsfUtil.downloadFile(sb.toString(), "transferencia-" + planillaPago.getIdPlanilla(), "csv");
                Logger.getLogger(PagoProveedoresController.class
                        .getName()).log(Level.INFO, "Archivo: Genera: {0}\n======================================\n{1}", new Object[]{VarSession.getVariableSessionUsuario(), sb.toString()});
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
        reiniciarVisibilidadCheques();
        isRubroUniforme = ((idRubro.intValue() == 1) || (idRubro.intValue() == 4) || (idRubro.intValue() == 5));
        idDetProceso = anhoProcesoEJB.getDetProcesoAdq(super.getProcesoAdquisicion(), idRubro).getIdDetProcesoAdq();
    }

    public void reiniciarVisibilidadCheques() {
        showChequeEntProv = false;
        showChequeRenta = false;
        showChequeUsefi = false;
        showPnlCheques = false;
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
        lstDetalleRequerimiento = proveedorEJB.getDetRequerimientoPendiente(idRequerimiento);
    }
    
    private void recuperarContratosByEntidadFinanciera(BigDecimal idRequerimiento, String nombreEntFinan) {
        lstDetalleRequerimiento = proveedorEJB.getDetRequerimientoPendienteByEntFinan(idRequerimiento, nombreEntFinan);
    }
}
