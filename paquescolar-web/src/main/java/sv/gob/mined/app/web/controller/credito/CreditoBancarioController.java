/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.credito;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.controller.AnhoProcesoController;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProceso;
import sv.gob.mined.app.web.util.RptExcel;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.CreditosEJB;
import sv.gob.mined.paquescolar.ejb.LoginEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.Anho;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
import sv.gob.mined.paquescolar.model.CreditoBancario;
import sv.gob.mined.paquescolar.model.DetalleCredito;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.EntFinanDetProAdq;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;
import sv.gob.mined.paquescolar.model.RubrosAmostrarInteres;
import sv.gob.mined.paquescolar.model.pojos.credito.CreditoProveedorDto;
import sv.gob.mined.paquescolar.model.pojos.credito.ResumenCreditosDto;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;
import sv.gob.mined.paquescolar.model.pojos.credito.DatosProveedoresFinanDto;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class CreditoBancarioController extends RecuperarProceso implements Serializable {

    private int rowEdit = 0;
    private String numeroNit = "";
    private Boolean usuarioEntidadFinanciera = false;
    private Boolean deshabilitado = true;
    private Boolean visibleLista = false;
    private Boolean visibleDatosGen = false;
    private Boolean visibleEdicion = false;
    private Boolean visibleEstadoCreditoActivo = false;
    private Boolean visibleEstadoCreditoCancelado = false;
    private Boolean visibleDlgEntidades = false;
    private BigInteger estadoCredito;
    private BigDecimal totalDeCreditos = BigDecimal.ZERO;
    private BigDecimal totalDeContratos = BigDecimal.ZERO;
    private BigDecimal anho = BigDecimal.ZERO;
    private String codigoDepartamento;
    private Empresa empresa;
    private DetalleProcesoAdq detalleProceso;
    private CreditoBancario credito;
    private CreditoBancario creditoConsultado;
    private EntidadFinanciera entidadSeleccionado;
    private BigDecimal idRubro = BigDecimal.ZERO;
    private ContratosOrdenesCompras contrato;
    private DetalleCredito detalleSeleccionado;
    private List<VwCatalogoEntidadEducativa> lstDetalleCeCredito;
    private List<EntidadFinanciera> lstEntidadFinanciera = new ArrayList();
    private List<EntidadFinanciera> lstEntFinanHabilitadoCredito = new ArrayList();
    private List<EntFinanDetProAdq> lstEntFinanDetProAdq = new ArrayList();
    private List<DetalleProcesoAdq> lstDetalleProcesoCredito = new ArrayList();
    private List<RubrosAmostrarInteres> lstRubros = new ArrayList();
    private List<SelectItem> selectEstadoCredito = new ArrayList();
    private List<ResumenCreditosDto> listaResumenGen = new ArrayList();
    private List<DetalleCredito> lstDetalleCredito = new ArrayList();
    private List<CreditoBancario> lstCreditoActivos = new ArrayList();
    private List<EntidadFinanciera> lstEntidades = new ArrayList();
    private List<CreditoProveedorDto> lstCreditosProveedor = new ArrayList();
    private List<ContratosOrdenesCompras> lstContratosDisponibles = new ArrayList();
    private List<ContratosOrdenesCompras> lstContratos = new ArrayList();
    private List<DatosProveedoresFinanDto> listaProvGral = new ArrayList();
    @EJB
    public CreditosEJB creditosEJB;
    @EJB
    public LoginEJB loginEJB;
    @EJB
    public ProveedorEJB proveedorEJB;
    @EJB
    public AnhoProcesoEJB anhoProcesoEJB;
    @EJB
    private UtilEJB utilEJB;

    /**
     * Creates a new instance of CreditoBancarioController
     */
    public CreditoBancarioController() {
    }

    @PostConstruct
    public void ini() {
        anho = ((AnhoProcesoController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "anhoProcesoController")).getAnho().getIdAnho();

        lstRubros = anhoProcesoEJB.getLstRubros(super.getProcesoAdquisicion());
        /*if (VarSession.getVariableSessionUsuario().equals("MSANCHEZ")) {
        }*/

        SelectItem selectActivo = new SelectItem();
        selectActivo.setLabel("Credido Activo");
        selectActivo.setValue(1);
        SelectItem selectActivo2 = new SelectItem();
        selectActivo2.setLabel("Credido Inactivo");
        selectActivo2.setValue(0);
        selectEstadoCredito.add(selectActivo);
        selectEstadoCredito.add(selectActivo2);
    }

    public void agregarEntidades() {
        for (EntidadFinanciera entFinan : lstEntidadFinanciera) {
            EntFinanDetProAdq ent = new EntFinanDetProAdq();
            ent.setCodEntFinanciera(entFinan);
            ent.setEstadoEliminacion((short) 0);
            ent.setFechaInsercion(new Date());
            ent.setIdDetProcesoAdq(new BigInteger(detalleProceso.getIdDetProcesoAdq().toString()));
            ent.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
            lstEntFinanDetProAdq.add(ent);
        }
        visibleDlgEntidades = false;
    }

    public Boolean getUsuarioEntidadFinanciera() {
        return usuarioEntidadFinanciera;
    }

    public List<VwCatalogoEntidadEducativa> getLstDetalleCeCredito() {
        return lstDetalleCeCredito;
    }

    public Boolean getVisibleDlgEntidades() {
        return visibleDlgEntidades;
    }

    public void setVisibleDlgEntidades(Boolean visibleDlgEntidades) {
        this.visibleDlgEntidades = visibleDlgEntidades;
    }

    public DetalleProcesoAdq getDetalleProceso() {
        return detalleProceso;
    }

    public void setDetalleProceso(DetalleProcesoAdq detalleProceso) {
        this.detalleProceso = detalleProceso;
    }

    public List<DetalleProcesoAdq> getLstDetalleProcesoCredito() {
        lstDetalleProcesoCredito = creditosEJB.getLstDetalleProcesoCredito(super.getProcesoAdquisicion().getIdProcesoAdq());
        return lstDetalleProcesoCredito;
    }

    public void setLstDetalleProcesoCredito(List<DetalleProcesoAdq> lstDetalleProcesoCredito) {
        this.lstDetalleProcesoCredito = lstDetalleProcesoCredito;
    }

    /*public void updateRubro() {
        lstRubros = creditosEJB.getLstRubrosHabilitados(super.getProcesoAdquisicion().getIdProcesoAdq(), entidadSeleccionado.getCodEntFinanciera());
    }*/
    public void updateEntidadHabilitarCredito() {
        lstEntFinanDetProAdq = creditosEJB.getLstEntidadesCredito(detalleProceso.getIdDetProcesoAdq());
    }

    public void guardar() {
        //Guardar activacios de procesos
        for (DetalleProcesoAdq detProceso : lstDetalleProcesoCredito) {
            utilEJB.updateEntity(detProceso);
        }

        //Guardar detalle de entidades habilitadas por proceso
        for (EntFinanDetProAdq entFinanDetProAdq : lstEntFinanDetProAdq) {
            if (entFinanDetProAdq.getEstadoEliminacion() == 0) {
                if (entFinanDetProAdq.getIdEntFinan() == null) {
                    creditosEJB.createEntFinanHabilitado(entFinanDetProAdq);
                }
            } else {
                if (entFinanDetProAdq.getIdEntFinan() != null) {
                    creditosEJB.deleteEntFinanHabilitado(entFinanDetProAdq.getIdEntFinan());
                }
            }
        }
        updateEntidadHabilitarCredito();
        JsfUtil.mensajeInsert();
    }

    public List<EntFinanDetProAdq> getLstEntFinanDetProAdq() {
        return lstEntFinanDetProAdq;
    }

    public void setLstEntFinanDetProAdq(List<EntFinanDetProAdq> lstEntFinanDetProAdq) {
        this.lstEntFinanDetProAdq = lstEntFinanDetProAdq;
    }

    public List<RubrosAmostrarInteres> getLstRubros() {
        return lstRubros;
    }

    public List<EntidadFinanciera> getLstEntidadFinanciera() {
        return lstEntidadFinanciera;
    }

    public void setLstEntidadFinanciera(List<EntidadFinanciera> lstEntidadFinanciera) {
        this.lstEntidadFinanciera = lstEntidadFinanciera;
    }

    public BigInteger getEstadoCredito() {
        return estadoCredito;
    }

    public void setEstadoCredito(BigInteger estadoCredito) {
        this.estadoCredito = estadoCredito;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public BigDecimal getAnho() {
        return anho;
    }

    public void setAnho(BigDecimal anho) {
        this.anho = anho;
    }

    public ContratosOrdenesCompras getContrato() {
        if (contrato == null) {
            contrato = new ContratosOrdenesCompras();
        }
        return contrato;
    }

    public void setContrato(ContratosOrdenesCompras contrato) {
        this.contrato = contrato;
    }

    public CreditoBancario getCredito() {
        if (credito == null) {
            credito = new CreditoBancario();
        }
        return credito;
    }

    public void setCredito(CreditoBancario credito) {
        if (credito != null) {
            this.credito = credito;
        }
    }

    public Empresa getEmpresa() {
        if (empresa == null) {
            empresa = new Empresa();
        }
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Boolean getVisibleLista() {
        return visibleLista;
    }

    public void setVisibleLista(Boolean visibleLista) {
        this.visibleLista = visibleLista;
    }

    public void buscarDetProceso() {
        detalleProceso = anhoProcesoEJB.getDetProcesoAdq(super.getProcesoAdquisicion(), idRubro);
        credito.setIdDetProcesoAdq(detalleProceso);
        credito.setCodEntFinanciera(entidadSeleccionado);

        buscarEntFinanUsu();

        usuarioEntidadFinanciera = lstEntidades.isEmpty();

        if (lstEntidades.isEmpty()) {
        }
        /*else {
            lstRubros = creditosEJB.getLstRubrosHabilitados(super.getProcesoAdquisicion().getIdProcesoAdq(), lstEntidades.get(0).getCodEntFinanciera());
        }*/
    }

    public List<DetalleCredito> getLstDetalleCredito() {
        if (detalleSeleccionado == null) {
            detalleSeleccionado = new DetalleCredito();
        }
        return lstDetalleCredito;
    }

    public DetalleCredito getDetalleSeleccionado() {
        return detalleSeleccionado;
    }

    public void setDetalleSeleccionado(DetalleCredito detalleSeleccionado) {
        this.detalleSeleccionado = detalleSeleccionado;
    }

    public boolean getEENuevo() {
        return VarSession.getVariableSessionED() == 1;
    }

    public boolean getEEModificar() {
        return VarSession.getVariableSessionED() == 2;
    }

    /**
     * Método que recupera el listado de Entidades Financieras
     */
    private void buscarEntFinanUsu() {
        BigInteger u = VarSession.getUsuarioSession().getIdTipoUsuario().getIdTipoUsuario().toBigInteger();
        if (detalleProceso == null) {
            lstEntidades.clear();
        } else if (u.compareTo(BigInteger.ONE) == 0) {
            //Usuario del tipo Administrador
            lstEntidades = creditosEJB.findEntidadFinancieraByIdDetProcesoAdq(detalleProceso.getIdDetProcesoAdq());
        } else {
            //Usuario del tipo Entidad Financiera. Una unica entidad por usuario
            lstEntidades = creditosEJB.getlstEntFinanUsuario(VarSession.getVariableSessionUsuario(), detalleProceso.getIdDetProcesoAdq());
        }
    }

    public BigDecimal getRubro() {
        return idRubro;
    }

    public void setRubro(BigDecimal rubro) {
        this.idRubro = rubro;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public EntidadFinanciera getEntidadSeleccionado() {
        return entidadSeleccionado;
    }

    public void setEntidadSeleccionado(EntidadFinanciera entidadSeleccionado) {
        this.entidadSeleccionado = entidadSeleccionado;
    }

    public Boolean getDeshabilitado() {
        return deshabilitado;
    }

    public void setDeshabilitado(Boolean deshabilitado) {
        this.deshabilitado = deshabilitado;
    }

    public Boolean getVisibleDatosGen() {
        return visibleDatosGen;
    }

    public void setVisibleDatosGen(Boolean visibleDatosGen) {
        this.visibleDatosGen = visibleDatosGen;
    }

    public Boolean getVisibleEdicion() {
        return visibleEdicion;
    }

    public void setVisibleEdicion(Boolean visibleEdicion) {
        this.visibleEdicion = visibleEdicion;
    }

    public String newCredito() {
        if (super.getProcesoAdquisicion().getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de contratación");
        } else {
            credito = new CreditoBancario();
            credito.setCreditoActivo(BigInteger.ONE);
            credito.setDetalleCreditoList(new ArrayList<DetalleCredito>());
            lstCreditoActivos.clear();
            lstDetalleCredito.clear();
            deshabilitado = false;
            setVisibleStatusCredito(false);
            VarSession.setVariableSessionED("1");
            operacionComun();
        }
        return "";
    }

    public void editCredito() {
        if (super.getProcesoAdquisicion().getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de contratación");
        } else {
            credito = new CreditoBancario();
            deshabilitado = false;
            VarSession.setVariableSessionED("2");
            operacionComun();
        }
    }

    public void setVisibleStatusCredito(Boolean panelEdicion) {
        if (panelEdicion) {
            this.visibleEstadoCreditoActivo = false;
            this.visibleEstadoCreditoCancelado = true;
        }
        if (!panelEdicion) {
            this.visibleEstadoCreditoCancelado = false;
            this.visibleEstadoCreditoActivo = true;
        }
    }

    private void operacionComun() {
        numeroNit = "";
    }

    public List<EntidadFinanciera> getLstEntidades() {
        return lstEntidades;
    }

    public void buscarEmpresa() {
        if (super.getProcesoAdquisicion().getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de contratación");
        } else if (validacionFiltro()) {
            empresa = proveedorEJB.findEmpresaByNit(numeroNit);
            if (empresa != null) {
                List lst = proveedorEJB.findDetRubroMuestraInteresEntitiesByRubroAndEmpresa(detalleProceso, empresa);

                if (lst.isEmpty()) {
                    JsfUtil.mensajeAlerta("Este proveedor no participo en el rubro seleccionado.");
                } else {
                    if (credito == null) {
                        credito = new CreditoBancario();
                    }
                    credito.setNumeroNit(empresa.getNumeroNit());

                    lstContratosDisponibles = creditosEJB.getLstContratosDisponiblesCreditos(empresa, detalleProceso);
                    switch (VarSession.getVariableSessionED()) {
                        case 1:
                            visibleDatosGen = true;
                            visibleEdicion = true;
                            break;
                        case 2:
                            visibleDatosGen = true;
                            visibleLista = true;
                            lstCreditoActivos = creditosEJB.findCreditoBancarioByEmpresa(empresa.getNumeroNit(), detalleProceso);
                            break;
                    }
                }
            } else {
                JsfUtil.mensajeAlerta("No se encontrarón el provedor con NIT: " + numeroNit);
            }
        }
    }

    public List<CreditoBancario> getLstCreditoActivos() {
        return lstCreditoActivos;
    }

    public void mostarPanelEdicion() {
        if (((Integer) VarSession.getVariableSession("idTipoUsuario") == 1)
                || entidadSeleccionado.getCodEntFinanciera().equals(credito.getCodEntFinanciera().getCodEntFinanciera())) {
            setVisibleLista(false);
            setVisibleEdicion(true);
            //quitarEliminadosDetalleCredito();
            visibleEstadoCreditoActivo = true;
        } else {
            JsfUtil.mensajeAlerta("No posee derechos de ver registros de otras entidades financieras");
        }
    }

    public Boolean getVisibleEstadoCreditoActivo() {
        return visibleEstadoCreditoActivo;
    }

    public Boolean getVisibleEstadoCreditoCancelado() {
        return visibleEstadoCreditoCancelado;
    }

    public void cancelacionDeCredito() {
        Boolean bandera = false;

        if (credito.getCreditoActivo().equals(BigInteger.ZERO)) {
            credito.setCreditoActivo(BigInteger.ONE);
            setVisibleStatusCredito(false);
            bandera = true;
        }
        if (credito.getCreditoActivo().equals(BigInteger.ONE) && bandera == false) {
            credito.setCreditoActivo(BigInteger.ZERO);
            setVisibleStatusCredito(true);
        }

        try {
            creditosEJB.editCreditoBancario(credito, VarSession.getVariableSessionUsuario());
            JsfUtil.mensajeInformacion("La operación se completo con éxito.");
        } catch (Exception ex) {
            JsfUtil.mensajeError("Ocurrio un error en la operación de cancelación del crédito.\n" + ex.getMessage());
        } finally {
            deshabilitado = true;
            detalleSeleccionado = null;
        }
    }

    private boolean validacionFiltro() {
        if (entidadSeleccionado == null || entidadSeleccionado.getCodEntFinanciera().equals("00000")) {
            JsfUtil.mensajeAlerta("Debe de seleccionar una entidad financiera válida.");
            return false;
        } else if (detalleProceso == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un rubro de adquisición y proceso de contratación.");
            return false;
        } else {
            return !numeroNit.replace("-", "").trim().equals("");
        }
    }

    public void guardarCredito() {
        if (credito.getCreditoActivo() == null) {
            credito.setCreditoActivo(BigInteger.ZERO);
        }

        if (VarSession.getVariableSessionED() == 1) {
            credito.setCodEntFinanciera(entidadSeleccionado);
        }
        creditosEJB.guardarCredito(credito, VarSession.getVariableSessionUsuario());
        JsfUtil.mensajeInformacion("Operación realizada satisfactoriamente");
    }

    public void eliminarDetalleCredito() {
        if (detalleSeleccionado != null) {
            if (!detalleSeleccionado.getEliminado()) {
                if (detalleSeleccionado.getIdDetalle() != null) {
                    detalleSeleccionado.setEliminado(true);
                } else {
                    credito.getDetalleCreditoList().remove(rowEdit);
                    lstContratosDisponibles.add(detalleSeleccionado.getIdContrato());
                }
            } else {
                detalleSeleccionado.setEliminado(false);
            }
        } else {
            JsfUtil.mensajeAlerta("Debe seleccionar un detalle para poder eliminarlo.");
        }
    }

    public void agregarNuevoContrato() {
        PrimeFaces.current().ajax().update("dlgCeDisponibles");
        PrimeFaces.current().executeScript("PF('dlgCeDisponibles').show()");
    }

    public List<ContratosOrdenesCompras> getLstContratosDisponibles() {
        return lstContratosDisponibles;
    }

    /**
     * Este metodo recupera los centros escolares que le contrataron bienes o
     * servicios a un proveedor y para un proceso en particular. Estos centros
     * escolares estan disponibles para realizarle creditos a este provedo
     *
     */
    public void buscarCreditosActivos() {
        lstCreditoActivos = creditosEJB.findCreditoBancarioByEmpresa(empresa.getNumeroNit(), detalleProceso);
    }

    public void cerrar() {
        PrimeFaces.current().executeScript("PF('dlgCeDisponibles').hide()");
    }

    public void onContratoSeleccionado() {
        PrimeFaces.current().executeScript("PF('dlgCeDisponibles').hide()");

        for (ContratosOrdenesCompras contratoSeleccionado : lstContratos) {
            detalleSeleccionado = new DetalleCredito();
            detalleSeleccionado.setCodigoEntidad(contratoSeleccionado.getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoEntidad());
            detalleSeleccionado.setEstadoEliminacion(BigInteger.ZERO);
            detalleSeleccionado.setFechaInsercion(new Date());
            detalleSeleccionado.setIdContrato(contratoSeleccionado);
            detalleSeleccionado.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
            detalleSeleccionado.setIdCredito(credito);

            credito.getDetalleCreditoList().add(detalleSeleccionado);

            lstContratosDisponibles.remove(contratoSeleccionado);
        }
    }

    public List<ContratosOrdenesCompras> getLstContratos() {
        return lstContratos;
    }

    public void setLstContratos(List<ContratosOrdenesCompras> lstContratos) {
        this.lstContratos = lstContratos;
    }

    public int getRowEdit() {
        return rowEdit;
    }

    public void setRowEdit(int rowEdit) {
        this.rowEdit = rowEdit;
    }

    public void generarResumen() {
        this.listaResumenGen = creditosEJB.generarResumen(anho);
    }

    public List<ResumenCreditosDto> getListaResumenGen() {
        return listaResumenGen;
    }

    public void setListaResumenGen(List<ResumenCreditosDto> listaResumenGen) {
        this.listaResumenGen = listaResumenGen;
    }

    public List<CreditoProveedorDto> getLstCreditosProveedor() {
        return lstCreditosProveedor;
    }

    public void setLstCreditosProveedor(List<CreditoProveedorDto> lstCreditosProveedor) {
        this.lstCreditosProveedor = lstCreditosProveedor;
    }

    public void consultaCreditosActivosPorProveedor() {
        if (super.getProcesoAdquisicion().getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de contratación");
        } else {
            lstCreditosProveedor = creditosEJB.getCreditosActivosPorProveedor(codigoDepartamento, anhoProcesoEJB.getDetProcesoAdq(super.getProcesoAdquisicion(), idRubro));
            totalDeCreditos = BigDecimal.ZERO;
            totalDeContratos = BigDecimal.ZERO;
            for (CreditoProveedorDto cre : lstCreditosProveedor) {
                totalDeCreditos = totalDeCreditos.add(cre.getMontoCredito() == null ? BigDecimal.ZERO : cre.getMontoCredito());
                totalDeContratos = totalDeContratos.add(cre.getMontoContrato() == null ? BigDecimal.ZERO : cre.getMontoContrato());
            }
        }
    }

    public String getTotalDeCreditos() {
        return JsfUtil.getFormatoNum(totalDeCreditos, false);
    }

    public String getTotalDeContratos() {
        return JsfUtil.getFormatoNum(totalDeContratos, false);
    }

    public void postProcessXLS(Object document) {
        if (lstCreditosProveedor.isEmpty()) {
            JsfUtil.mensajeInformacion("No hay datos para exportar");
        } else {
            HSSFWorkbook wb = (HSSFWorkbook) document;
            HSSFSheet sheet = wb.getSheetAt(0);

            for (int j = 0; j < sheet.getLastRowNum(); j++) {
                HSSFRow row = sheet.getRow(j);
                if (j != 0) {
                    for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                        if (i == 4 || i == 8 || i == 6) {
                            String valor = row.getCell(i).getRichStringCellValue().getString();
                            if (!valor.isEmpty()) {
                                HSSFCell celda = row.createCell(i);
                                celda.setCellType(CellType.NUMERIC);
                                celda.setCellValue(new Double(valor.replace(",", "")));
                            }
                        }
                    }
                }
            }
        }
    }

    public void preProcessXLS() {
        if (listaResumenGen.isEmpty()) {
            JsfUtil.mensajeInformacion("No hay datos para exportar");
        } else {
            RptExcel.generarRptResumenGeneralCreditos(listaResumenGen, utilEJB.find(Anho.class, anho).getAnho());
        }
    }

    public void resetNit() {
        numeroNit = "";
        detalleProceso = anhoProcesoEJB.getDetProcesoAdq(super.getProcesoAdquisicion(), idRubro);
    }

    public List<DatosProveedoresFinanDto> getListaProvGral() {
        return listaProvGral;
    }

    public Integer getNumeroDeContratosEnCredito() {
        return listaProvGral.size();
    }

    public BigDecimal getMontoTotalEnCreditos() {
        BigDecimal monto = BigDecimal.ZERO;
        for (DatosProveedoresFinanDto vwDatosProveedoresFinan : listaProvGral) {
            monto = monto.add(vwDatosProveedoresFinan.getMontoCredito());
        }
        return monto;
    }

    public BigDecimal getMontoTotalEnContratos() {
        BigDecimal monto = BigDecimal.ZERO;
        for (DatosProveedoresFinanDto vwDatosProveedoresFinan : listaProvGral) {
            monto = monto.add(vwDatosProveedoresFinan.getMontoContrato());
        }
        return monto;
    }

    public void buscarListadoProveedor() {
        listaProvGral.clear();
        listaProvGral = creditosEJB.buscarListadoProveedor(idRubro, entidadSeleccionado, detalleProceso, estadoCredito);
    }

    public List<SelectItem> getSelectEstadoCredito() {
        return selectEstadoCredito;
    }

    public List<EntidadFinanciera> getLstEntFinanHabilitadoCredito() {
        return lstEntFinanHabilitadoCredito;
    }

    public void openDialogEntidades() {
        if (detalleProceso == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso");
        } else {
            lstEntFinanHabilitadoCredito = creditosEJB.getLstEntFinanHabilitadoCredito(detalleProceso.getIdDetProcesoAdq());
            visibleDlgEntidades = true;
        }
    }

    public void preProcessDatosProveedorXLS(Object document) {
        if (listaProvGral.isEmpty()) {
            JsfUtil.mensajeInformacion("No hay datos para exportar");
        } else {
            RptExcel.generarRptResumenPorRubroYFinanciera(listaProvGral, utilEJB.find(Anho.class, anho).getAnho());
        }
    }

    public CreditoBancario getCreditoConsultado() {
        return creditoConsultado;
    }

    public void setCreditoConsultado(CreditoBancario creditoConsultado) {
        this.creditoConsultado = creditoConsultado;
    }

    public void buscarCENoDisponibleCredito() {
        if (creditoConsultado != null) {
            lstDetalleCeCredito = new ArrayList();
            String parametros = "";

            List<DetalleCredito> lstCredito = creditosEJB.findDetalleCreditoEntitiesByCredito(creditoConsultado);

            for (DetalleCredito detalleCredito : lstCredito) {
                parametros = parametros.concat("'").concat(detalleCredito.getCodigoEntidad()).concat("',");
            }
            parametros = parametros.substring(0, parametros.length() - 1);
            lstDetalleCeCredito = creditosEJB.findCENoDisponibleCredito(parametros);
        }
    }
}
