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
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProceso;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.CreditosEJB;
import sv.gob.mined.paquescolar.ejb.PagoProveedoresEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.DetallePlanilla;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;
import sv.gob.mined.paquescolar.model.PlanillaPago;
import sv.gob.mined.paquescolar.model.RequerimientoFondos;
import sv.gob.mined.paquescolar.model.pojos.ResumenRequerimientoDto;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.DatosProveDto;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class PlanillaPagoLstMB extends RecuperarProceso implements Serializable {

    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;
    @EJB
    private PagoProveedoresEJB pagoProveedoresEJB;
    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private UtilEJB utilEJB;
    @EJB
    private CreditosEJB creditosEJB;

    private int idDetProceso;
    private int idTipoPlanilla = 0;

    private Boolean dlgShowEntidadesFinancieras = false;
    private Boolean dlgShowTipoPlanilla = false;
    private Boolean dlgShowSeleccionProveedor = false;
    private Boolean seleccionRequerimiento = false;
    private Boolean seleccionPlanilla = false;

    private String codigoDepartamento = "";
    private String nombreEntFinanciera = "";

    private BigDecimal idReq = BigDecimal.ZERO;
    private BigDecimal idRubro = BigDecimal.ZERO;

    private DatosProveDto proveedor = new DatosProveDto();
    private EntidadFinanciera entidadFinanciera = new EntidadFinanciera();
    private PlanillaPago planillaPago = new PlanillaPago();

    private List<ResumenRequerimientoDto> lstResumenRequerimiento = new ArrayList();
    private List<EntidadFinanciera> lstEntFinRequerimiento = new ArrayList();
    private List<RequerimientoFondos> lstRequerimientoFondos = new ArrayList();
    private List<PlanillaPago> lstPlanillas = new ArrayList();
    private List<DetallePlanilla> lstDetallePlanilla = new ArrayList();
    private List<DatosProveDto> lstProveedores = new ArrayList();

    /**
     * Creates a new instance of PlanillaPagoMB
     */
    public PlanillaPagoLstMB() {
    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">
    public Boolean getSeleccionPlanilla() {
        return seleccionPlanilla;
    }

    public void setSeleccionPlanilla(Boolean seleccionPlanilla) {
        this.seleccionPlanilla = seleccionPlanilla;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public BigDecimal getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(BigDecimal idRubro) {
        this.idRubro = idRubro;
    }

    public List<RequerimientoFondos> getLstRequerimientoFondos() {
        return lstRequerimientoFondos;
    }

    public void setLstRequerimientoFondos(List<RequerimientoFondos> lstRequerimientoFondos) {
        this.lstRequerimientoFondos = lstRequerimientoFondos;
    }

    public Boolean getSeleccionRequerimiento() {
        return seleccionRequerimiento;
    }

    public void setSeleccionRequerimiento(Boolean seleccionRequerimiento) {
        this.seleccionRequerimiento = seleccionRequerimiento;
    }

    public BigDecimal getIdReq() {
        return idReq;
    }

    public void setIdReq(BigDecimal idReq) {
        this.idReq = idReq;
    }

    public List<ResumenRequerimientoDto> getLstResumenRequerimiento() {
        return lstResumenRequerimiento;
    }

    public void setLstResumenRequerimiento(List<ResumenRequerimientoDto> lstResumenRequerimiento) {
        this.lstResumenRequerimiento = lstResumenRequerimiento;
    }

    public PlanillaPago getPlanillaPago() {
        return planillaPago;
    }

    public void setPlanillaPago(PlanillaPago planillaPago) {
        this.planillaPago = planillaPago;
    }

    public List<PlanillaPago> getLstPlanillas() {
        return lstPlanillas;
    }

    public void setLstPlanillas(List<PlanillaPago> lstPlanillas) {
        this.lstPlanillas = lstPlanillas;
    }

    public Boolean getDlgShowEntidadesFinancieras() {
        return dlgShowEntidadesFinancieras;
    }

    public void setDlgShowEntidadesFinancieras(Boolean dlgShowEntidadesFinancieras) {
        this.dlgShowEntidadesFinancieras = dlgShowEntidadesFinancieras;
    }

    public Boolean getDlgShowTipoPlanilla() {
        return dlgShowTipoPlanilla;
    }

    public void setDlgShowTipoPlanilla(Boolean dlgShowTipoPlanilla) {
        this.dlgShowTipoPlanilla = dlgShowTipoPlanilla;
    }

    public Boolean getDlgShowSeleccionProveedor() {
        return dlgShowSeleccionProveedor;
    }

    public void setDlgShowSeleccionProveedor(Boolean dlgShowSeleccionProveedor) {
        this.dlgShowSeleccionProveedor = dlgShowSeleccionProveedor;
    }

    public EntidadFinanciera getEntidadFinanciera() {
        return entidadFinanciera;
    }

    public void setEntidadFinanciera(EntidadFinanciera entidadFinanciera) {
        this.entidadFinanciera = entidadFinanciera;
    }

    public List<EntidadFinanciera> getLstEntFinRequerimiento() {
        return lstEntFinRequerimiento;
    }

    public void setLstEntFinRequerimiento(List<EntidadFinanciera> lstEntFinRequerimiento) {
        this.lstEntFinRequerimiento = lstEntFinRequerimiento;
    }

    public List<DatosProveDto> getLstProveedores() {
        return lstProveedores;
    }

    public void setLstProveedores(List<DatosProveDto> lstProveedores) {
        this.lstProveedores = lstProveedores;
    }

    public DatosProveDto getProveedor() {
        return proveedor;
    }

    public void setProveedor(DatosProveDto proveedor) {
        this.proveedor = proveedor;
    }

    public int getIdTipoPlanilla() {
        return idTipoPlanilla;
    }

    public void setIdTipoPlanilla(int idTipoPlanilla) {
        this.idTipoPlanilla = idTipoPlanilla;
    }

    //</editor-fold>
    public void nuevoPlanilla() {
        inicializacionVariables(true);
    }

    private void inicializacionVariables(Boolean valor) {
        seleccionRequerimiento = valor;
        seleccionPlanilla = !valor;

        idTipoPlanilla = 0;
        entidadFinanciera = new EntidadFinanciera();
        proveedor = new DatosProveDto();

        idRubro = BigDecimal.ZERO;
        idReq = BigDecimal.ZERO;
        lstPlanillas.clear();
        lstProveedores.clear();
        lstRequerimientoFondos.clear();
        lstEntFinRequerimiento.clear();
    }

    public void editarPlanilla() {
        inicializacionVariables(false);
    }

    public void clearRubroAndRequerimiento() {
        idRubro = BigDecimal.ZERO;
        idReq = BigDecimal.ZERO;
    }

    public void recuperarRequerimientos() {
        idDetProceso = anhoProcesoEJB.getDetProcesoAdq(super.getProcesoAdquisicion(), idRubro).getIdDetProcesoAdq();
        lstRequerimientoFondos = proveedorEJB.getLstRequerimientos(codigoDepartamento, idDetProceso);
    }

    public void buscarRequerimientos() {
        if (idRubro != null && idRubro.intValue() > 0) {
            buscarRequerimientoqOrPlanilla();
            lstResumenRequerimiento = proveedorEJB.getLstResumenRequerimiento(codigoDepartamento, idDetProceso);
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar un rubro de adquisición.");
        }
    }

    private void buscarRequerimientoqOrPlanilla() {
        idDetProceso = anhoProcesoEJB.getDetProcesoAdq(super.getProcesoAdquisicion(), idRubro).getIdDetProcesoAdq();
    }

    public void buscarPlanillas() {
        if (idRubro != null) {
            if (idReq != null) {
                buscarRequerimientoqOrPlanilla();
                lstPlanillas = proveedorEJB.getLstPlanillaPagos(idReq);
                if (lstPlanillas.isEmpty()) {
                    JsfUtil.mensajeInformacion("El requerimiento seleccionado no tienen planillas registradas");
                }
            } else {
                JsfUtil.mensajeInformacion("Debe de seleccionar un requerimiento.");
            }
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar un rubro de adquisición.");
        }
    }

    /**
     * Método que se ejecuta al momento de seleccionar un requerimiento para la
     * creación de una planilla de pago
     */
    public void selectRequerimiento() {
        RequerimientoFondos requerimientoFondos = utilEJB.find(RequerimientoFondos.class, idReq); //recuperacion del requerimiento de fondos

        dlgShowTipoPlanilla = false;
        dlgShowEntidadesFinancieras = false;
        lstEntFinRequerimiento.clear(); //limpiando listado de entidades financieras del requerimiento previamente seleccionado
        lstProveedores.clear(); //limpiando listado de proveedores del requerimiento previamente seleccionado

        if (requerimientoFondos.getCredito().intValue() == 1) {
            idTipoPlanilla = 3;
            List<String> lstNombre = proveedorEJB.getEntidadesPorRequerimiento(requerimientoFondos.getIdRequerimiento());

            if (!lstNombre.isEmpty()) {
                //si el requerimiento es de credito, se desplegan las entidades financieras asociados a dicho requerimiento
                lstEntFinRequerimiento = creditosEJB.findEntidadFinancieraEntitiesByName(proveedorEJB.getEntidadesPorRequerimiento(requerimientoFondos.getIdRequerimiento()));
                dlgShowEntidadesFinancieras = true;
            } else {
                JsfUtil.mensajeInformacion("Ya se ha completado el pago de este requerimiento.");
            }
        } else {
            dlgShowTipoPlanilla = true;
        }
    }

    /**
     * Método que se ejecuta al seleccionar la planilla a editar.
     */
    public String selectPlanilla() {
        Boolean redireccionar = false;
        idTipoPlanilla = planillaPago.getIdTipoPlanilla().intValue();
        lstDetallePlanilla = planillaPago.getDetallePlanillaList();

        switch (planillaPago.getIdTipoPlanilla()) {
            case 1:
                if (lstDetallePlanilla.isEmpty()) {
                    showDlgSeleccionProveedor();
                    PrimeFaces.current().ajax().update("dlgSeleccionProveedor");
                } else {
                    redireccionar = true;
                }

                break;
            case 2:
                redireccionar = true;
                break;
            case 3:
                if (lstDetallePlanilla.isEmpty()) {
                    //si el requerimiento es de credito, se desplegan las entidades financieras asociados a dicho requerimiento
                    List<String> lstNombre = proveedorEJB.getEntidadesPorRequerimiento(planillaPago.getIdRequerimiento().getIdRequerimiento());
                    if (lstNombre.isEmpty()) {
                        JsfUtil.mensajeAlerta("El requerimiento seleccionado ya no tienen contratos con crédito");
                    } else {
                        lstEntFinRequerimiento = creditosEJB.findEntidadFinancieraEntitiesByName(proveedorEJB.getEntidadesPorRequerimiento(planillaPago.getIdRequerimiento().getIdRequerimiento()));
                        dlgShowEntidadesFinancieras = true;
                    }
                } else {
                    nombreEntFinanciera = lstDetallePlanilla.get(0).getIdDetalleDocPago().getIdDetRequerimiento().getNombreEntFinan();
                    entidadFinanciera = proveedorEJB.getEntidadByNombre(nombreEntFinanciera);
                    redireccionar = true;
                }
                break;
        }

        if (redireccionar) {
            return "planillaPagoEdt.mined";
        } else {
            return "";
        }
    }

    private void showDlgSeleccionProveedor() {
        dlgShowSeleccionProveedor = true;
        //Se recuperar el listado de proveedores que conforman el requerimiento seleccionado y que estan pendientes de 
        //ser asociados a una planilla de pago
        lstProveedores = pagoProveedoresEJB.getProveedoresPorIdRequerimiento(idReq);
    }

    public void cerrarDlgTipoPlanilla() {
        PrimeFaces.current().executeScript("PF('dlgTipoPlanilla').hide()");
        switch (idTipoPlanilla) {
            case 1: //Planilla con un solo proveedor
                showDlgSeleccionProveedor();
                break;
            case 2: //Planilla con más de 2 proveedores
                dlgShowSeleccionProveedor = false;
                crearPlanillaDePago();
                break;
        }
    }

    public void crearPlanillaDePago() {
        //validacion de requerimiento con credito y seleccion de entidad financiera
        if (entidadFinanciera.getCodEntFinanciera() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar una entidad financiera");
        } else {
//            mostrarEditDePlanilla();
//            if (showChequeEntProv) {
//                recuperarContratosByEntidadFinanciera(requerimientoFondos.getIdRequerimiento(), entidadFinanciera.getNombreEntFinan());
//            } else {
//                recuperarContratosByIdRequerimiento(requerimientoFondos.getIdRequerimiento());
//            }
            PrimeFaces.current().executeScript("PF('dlgEntidadesFinancieras').hide()");
        }
    }

    public void cerrarDlgSeleccioneProveedor() {
        if (proveedor == null || proveedor.getNumeroNit() == null || proveedor.getNumeroNit().isEmpty()) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proveedor");
        }
    }

    public void eliminarPlanilla() {
        pagoProveedoresEJB.eliminarPlanilla(planillaPago.getIdPlanilla(), VarSession.getVariableSessionUsuario());
        buscarPlanillas();
    }
}
