/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import sv.gob.mined.app.web.controller.ParametrosMB;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.RptExcel;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.CreditosEJB;
import sv.gob.mined.paquescolar.ejb.DatosGeograficosEJB;
import sv.gob.mined.paquescolar.ejb.EMailEJB;
import sv.gob.mined.paquescolar.ejb.PreciosReferenciaEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.Canton;
import sv.gob.mined.paquescolar.model.CapaDistribucionAcre;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.CatalogoProducto;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
import sv.gob.mined.paquescolar.model.Departamento;
import sv.gob.mined.paquescolar.model.DetRubroMuestraInteres;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.DisMunicipioInteres;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.EmpresaCodigoSeg;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;
import sv.gob.mined.paquescolar.model.Municipio;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.PreciosRefRubro;
import sv.gob.mined.paquescolar.model.PreciosRefRubroEmp;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.RubrosAmostrarInteres;
import sv.gob.mined.paquescolar.model.pojos.OfertaGlobal;
import sv.gob.mined.paquescolar.model.pojos.proveedor.DetalleAdjudicacionEmpDto;
import sv.gob.mined.paquescolar.model.pojos.proveedor.MunicipioDto;
import sv.gob.mined.paquescolar.model.pojos.proveedor.NotificacionOfertaProvDto;
import sv.gob.mined.paquescolar.util.RC4Crypter;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class ProveedorController extends RecuperarProcesoUtil implements Serializable {

    private int idRow;

    private Boolean resetUsuario = false;
    private Boolean resetAceptacion = false;
    private Boolean deshabiliar = true;
    private Boolean showFoto = true;
    private Boolean showCapacidadAdjudicada = false;
    private Boolean showUpdateEmpresa = false;
    private Boolean mismaDireccion = false;
    private Boolean personaNatural = false;
    private Boolean rubroUniforme = false;
    private Boolean inscritoIva = false;
    private Boolean deseaInscribirseIva = false;

    private String nit;

    private String numeroNit;
    private String rowEdit;
    private String fotoProveedor;
    private String estiloZapato;
    private String tapEmpresa;
    private String tapPersona;
    private String codigoDepartamentoCalificado;
    private String fileName = "fotoProveedores/profile.png";
    private String codEntFinanciera;
    private String url;
    private String msjError = "";
    private String codigoDepartamento = "";
    private String codigoDepartamentoLocal = "";
    private String idCanton;
    private String idCantonLocal;

    private BigDecimal idAnho = BigDecimal.ZERO;
    private BigDecimal rubro = BigDecimal.ZERO;
    private BigDecimal idMunicipio = BigDecimal.ZERO;
    private BigDecimal idMunicipioLocal = BigDecimal.ZERO;
    private BigDecimal totalItems = BigDecimal.ZERO;
    private BigDecimal totalMonto = BigDecimal.ZERO;
    private File carpetaNfs = new File("/imagenes/PaqueteEscolar/Fotos_Zapatos/");

    private Municipio municipio = new Municipio();
    private DetalleProcesoAdq detalleProcesoAdq = new DetalleProcesoAdq();
    private RubrosAmostrarInteres rubrosAmostrarInteres = new RubrosAmostrarInteres();
    private Empresa empresa = new Empresa();
    private CapaDistribucionAcre departamentoCalif = new CapaDistribucionAcre();
    private CapaInstPorRubro capacidadInst = new CapaInstPorRubro();
    private PreciosRefRubroEmp precioRef = new PreciosRefRubroEmp();
    private PreciosRefRubro preMaxRefPar = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefCi = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefCii = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefCiii = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefCiiiMf = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefBac = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefBacMf = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG1 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG2 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG3 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG4 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG5 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG6 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG7 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG8 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG9 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefB1 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefB2 = new PreciosRefRubro();

    private List<String> images = new ArrayList();

    private List<MunicipioDto> lstMunSource = new ArrayList();
    private List<MunicipioDto> lstMunTarget = new ArrayList();
    private List<CatalogoProducto> lstItem = new ArrayList();
    private List<PreciosRefRubroEmp> lstPreciosReferencia = new ArrayList();
    private List<DetalleAdjudicacionEmpDto> lstResumenAdj = new ArrayList();
    private List<DetalleAdjudicacionEmpDto> lstDetalleAdj = new ArrayList();
    private DualListModel<MunicipioDto> lstMunicipiosInteres = new DualListModel();
    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private DatosGeograficosEJB datosGeograficosEJB;
    @EJB
    private UtilEJB utilEJB;
    @EJB
    private ReportesEJB reportesEJB;
    @EJB
    private CreditosEJB creditosEJB;
    @EJB
    private PreciosReferenciaEJB preciosReferenciaEJB;
    @EJB
    private EMailEJB eMailEJB;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("Bundle");

    /**
     * Creates a new instance of ProveedorController
     */
    public ProveedorController() {
    }

    @PostConstruct
    public void ini() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = req.getRequestURL().toString();

        if (VarSession.isVariableSession("idEmpresa")) {
            empresa = proveedorEJB.findEmpresaByPk((BigDecimal) VarSession.getVariableSession("idEmpresa"));

            //showUpdateEmpresa = ((Integer) VarSession.getVariableSession("idTipoUsuario") == 1 || (Integer) VarSession.getVariableSession("idTipoUsuario") == 2);
            cargarDetalleCalificacion();
            if (url.contains("DatosGenerales")) {
                idMunicipio = empresa.getIdPersona().getIdMunicipio().getIdMunicipio();
                codigoDepartamento = empresa.getIdPersona().getIdMunicipio().getCodigoDepartamento().getCodigoDepartamento();
            } else if (url.contains("MunicipiosInteres")) {
                cargarMunInteres();
            } else if (url.contains("PreciosReferencia") && getRecuperarProceso().getProcesoAdquisicion() != null && getRecuperarProceso().getProcesoAdquisicion().getIdProcesoAdq() != null) {
                cargarPrecioRef();
            } else if (url.contains("FotografiaMuestras") && getRecuperarProceso().getProcesoAdquisicion() != null && getRecuperarProceso().getProcesoAdquisicion().getIdProcesoAdq() != null) {
            }
        }

        if (VarSession.getVariableSessionUsuario().equals("MSANCHEZ")) {
            showCapacidadAdjudicada = true;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">
    public Boolean getInscritoIva() {
        return inscritoIva;
    }

    public void setInscritoIva(Boolean inscritoIva) {
        this.inscritoIva = inscritoIva;
    }

    public Boolean getDeseaInscribirseIva() {
        return deseaInscribirseIva;
    }

    public void setDeseaInscribirseIva(Boolean deseaInscribirseIva) {
        this.deseaInscribirseIva = deseaInscribirseIva;
    }

    public Boolean getMismaDireccion() {
        return mismaDireccion;
    }

    public void setMismaDireccion(Boolean mismaDireccion) {
        this.mismaDireccion = mismaDireccion;
    }

    public Boolean getRubroUniforme() {
        return rubroUniforme;
    }

    public void setRubroUniforme(Boolean rubroUniforme) {
        this.rubroUniforme = rubroUniforme;
    }

    public Boolean getPersonaNatural() {
        return personaNatural;
    }

    public void setPersonaNatural(Boolean personaNatural) {
        this.personaNatural = personaNatural;
    }

    public String getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(String idCanton) {
        this.idCanton = idCanton;
    }

    public String getIdCantonLocal() {
        return idCantonLocal;
    }

    public void setIdCantonLocal(String idCantonLocal) {
        if (idCantonLocal != null) {
            this.idCantonLocal = idCantonLocal;
        }
    }

    public Boolean getResetUsuario() {
        return resetUsuario;
    }

    public void setResetUsuario(Boolean resetUsuario) {
        this.resetUsuario = resetUsuario;
    }

    public Boolean getResetAceptacion() {
        return resetAceptacion;
    }

    public void setResetAceptacion(Boolean resetAceptacion) {
        this.resetAceptacion = resetAceptacion;
    }

    public String getCodigoDepartamentoLocal() {
        return codigoDepartamentoLocal;
    }

    public void setCodigoDepartamentoLocal(String codigoDepartamentoLocal) {
        this.codigoDepartamentoLocal = codigoDepartamentoLocal;
    }

    public BigDecimal getIdMunicipioLocal() {
        return idMunicipioLocal;
    }

    public void setIdMunicipioLocal(BigDecimal idMunicipioLocal) {
        this.idMunicipioLocal = idMunicipioLocal;
    }

    public BigDecimal getRubro() {
        return rubro;
    }

    public void setRubro(BigDecimal rubro) {
        this.rubro = rubro;
    }

    public List<DetalleAdjudicacionEmpDto> getLstDetalleAdj() {
        return lstDetalleAdj;
    }

    public CapaDistribucionAcre getDepartamentoCalif() {
        return departamentoCalif;
    }

    public void setDepartamentoCalif(CapaDistribucionAcre departamentoCalif) {
        this.departamentoCalif = departamentoCalif;
    }

    public String getCodigoDepartamentoCalificado() {
        return codigoDepartamentoCalificado;
    }

    public void setCodigoDepartamentoCalificado(String codigoDepartamentoCalificado) {
        this.codigoDepartamentoCalificado = codigoDepartamentoCalificado;
    }

    public String getCodEntFinanciera() {
        return codEntFinanciera;
    }

    public void setCodEntFinanciera(String entidadFinanciera) {
        this.codEntFinanciera = entidadFinanciera;
    }

    public List<EntidadFinanciera> getLstEntidades() {
        return creditosEJB.findEntidadFinancieraEntities((short) 1);
    }

    public String getEstiloZapato() {
        return estiloZapato;
    }

    public void setEstiloZapato(String estiloZapato) {
        this.estiloZapato = estiloZapato;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Boolean getShowCapacidadAdjudicada() {
        return showCapacidadAdjudicada;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setShowCapacidadAdjudicada(Boolean showCapacidadAdjudicada) {
        this.showCapacidadAdjudicada = showCapacidadAdjudicada;
    }

    public String getFotoProveedor() {
        return fotoProveedor;
    }

    public void setFotoProveedor(String fotoProveedor) {
        this.fotoProveedor = fotoProveedor;
    }

    public Boolean getShowFoto() {
        return showFoto;
    }

    public void setShowFoto(Boolean showFoto) {
        this.showFoto = showFoto;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public BigDecimal getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(BigDecimal idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Boolean getDeshabiliar() {
        return deshabiliar;
    }

    public void setDeshabiliar(Boolean deshabiliar) {
        this.deshabiliar = deshabiliar;
    }

    public CapaInstPorRubro getCapacidadInst() {
        if (capacidadInst == null) {
            capacidadInst = new CapaInstPorRubro();
        }
        return capacidadInst;
    }

    public void setCapacidadInst(CapaInstPorRubro capacidadInst) {
        this.capacidadInst = capacidadInst;
    }

    public List<Departamento> getLstDepartamentos() {
        return datosGeograficosEJB.getLstDepartamentos();
    }

    public DualListModel<MunicipioDto> getLstMunicipiosInteres() {
        return lstMunicipiosInteres;
    }

    public void setLstMunicipiosInteres(DualListModel<MunicipioDto> lstMunicipiosInteres) {
        if (!(lstMunicipiosInteres.getSource().isEmpty() && lstMunicipiosInteres.getTarget().isEmpty())) {
            this.lstMunicipiosInteres = lstMunicipiosInteres;
        }
    }

    public DetalleProcesoAdq getDetalleProcesoAdq() {
        return detalleProcesoAdq;
    }

    public void setDetalleProcesoAdq(DetalleProcesoAdq detalleProcesoAdq) {
        this.detalleProcesoAdq = detalleProcesoAdq;
    }

    public RubrosAmostrarInteres getRubrosAmostrarInteres() {
        return rubrosAmostrarInteres;
    }

    public void setRubrosAmostrarInteres(RubrosAmostrarInteres rubrosAmostrarInteres) {
        this.rubrosAmostrarInteres = rubrosAmostrarInteres;
    }

    public List<PreciosRefRubroEmp> getLstPreciosReferencia() {
        return lstPreciosReferencia;
    }

    public void setLstPreciosReferencia(List<PreciosRefRubroEmp> lstPreciosReferencia) {
        this.lstPreciosReferencia = lstPreciosReferencia;
    }

    public PreciosRefRubroEmp getPrecioRef() {
        return precioRef;
    }

    public void setPrecioRef(PreciosRefRubroEmp preciosRef) {
        this.precioRef = preciosRef;
    }

    public int getIdRow() {
        return idRow;
    }

    public void setIdRow(int idRow) {
        this.idRow = idRow;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String filename) {
        this.fileName = filename;
    }

    public List<CatalogoProducto> getLstItem() {
        return lstItem;
    }

    public void setLstItem(List<CatalogoProducto> lstItem) {
        this.lstItem = lstItem;
    }

    public List<DetalleAdjudicacionEmpDto> getLstResumenAdj() {
        return lstResumenAdj;
    }

    public String getTotalItems() {
        return JsfUtil.getFormatoNum(totalItems, true);
    }

    public String getTotalMonto() {
        return JsfUtil.getFormatoNum(totalMonto, false);
    }

    public List<Municipio> getLstMunicipios() {
        return datosGeograficosEJB.getLstMunicipiosByDepartamento(codigoDepartamento);
    }

    public List<Municipio> getLstMunicipiosLocal() {
        return datosGeograficosEJB.getLstMunicipiosByDepartamento(codigoDepartamentoLocal);
    }

    public List<Canton> getLstCantones() {
        return datosGeograficosEJB.getLstCantonByMunicipio(idMunicipio);
    }

    public List<Canton> getLstCantonesLocal() {
        return datosGeograficosEJB.getLstCantonByMunicipio(idMunicipioLocal);
    }

    public Boolean getShowUpdateEmpresa() {
        switch ((Integer) VarSession.getVariableSession("idTipoUsuario")) {
            case 1:
            case 2:
                showUpdateEmpresa = true;
                break;
            default:
                showUpdateEmpresa = false;
                break;
        }
        return showUpdateEmpresa;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getTapEmpresa() {
        if (empresa == null) {
            tapEmpresa = "";
        } else if (empresa.getIdPersoneria().getIdPersoneria() == null) {
            tapEmpresa = "";
        } else if (empresa.getIdPersoneria().getIdPersoneria().intValue() == 2) {
            tapEmpresa = "Empresa";
        } else {
            tapEmpresa = "";
        }
        return tapEmpresa;
    }

    public String getTapPersona() {
        if (empresa == null) {
            tapPersona = "";
        } else if (empresa.getIdPersoneria().getIdPersoneria() == null) {
            tapPersona = "";
        } else if (empresa.getIdPersoneria().getIdPersoneria().intValue() == 2) {
            tapPersona = "Representante Legal";
        } else {
            tapPersona = "Proveedor";
        }
        return tapPersona;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }
    // </editor-fold>  

    public void cargarFotografias() {
        carpetaNfs = new File("/imagenes/PaqueteEscolar/Fotos_Zapatos/" + empresa.getNumeroNit() + File.separator);
        if (!carpetaNfs.exists()) {
            System.out.println("Se creo carpeta: " + empresa.getNumeroNit() + " - " + carpetaNfs.mkdir());
        }
        carpetaNfs = new File("/imagenes/PaqueteEscolar/Fotos_Zapatos/" + empresa.getNumeroNit() + File.separator + estiloZapato + File.separator);
        if (!carpetaNfs.exists()) {
            System.out.println("Se creo carpeta: " + empresa.getNumeroNit() + File.separator + estiloZapato + File.separator + " - " + carpetaNfs.mkdir());
        }

        carpetaNfs = new File("/imagenes/PaqueteEscolar/Fotos_Zapatos/" + empresa.getNumeroNit() + File.separator + estiloZapato + File.separator);
        images = new ArrayList();

        if (carpetaNfs.list() != null) {
            for (String string : carpetaNfs.list()) {
                images.add("Fotos_Zapatos/" + empresa.getNumeroNit() + "/" + estiloZapato + "/" + string);
            }
        }
    }

    public void filtroProveedores() {
        empresa = new Empresa();
        capacidadInst = new CapaInstPorRubro();
        lstPreciosReferencia.clear();
        lstMunSource.clear();
        lstMunTarget.clear();
        fileName = "fotoProveedores/profile.png";
        showFoto = true;
        deshabiliar = true;
        images.clear();
        Map<String, Object> options = new HashMap();
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", false);
        options.put("contentHeight", 500);
        options.put("contentWidth", 750);

        PrimeFaces.current().dialog().openDynamic("/app/comunes/dialogos/proveedor/filtroProveedor", options, null);
    }

    public void dlgFotografia() {
        if (empresa == null || empresa.getIdEmpresa() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un empresa");
        } else {
            deshabiliar = true;
            Map<String, Object> options = new HashMap();
            options.put("modal", true);
            options.put("draggable", false);
            options.put("resizable", false);
            options.put("contentHeight", 400);
            options.put("contentWidth", 554);
            VarSession.setVariableSession("nitEmpresa", empresa.getNumeroNit());
            PrimeFaces.current().dialog().openDynamic("/app/comunes/filtroFotoProveedor", options, null);
        }
    }

    public void dlgFotografiaZapato() {
        if (empresa == null || empresa.getIdEmpresa() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un empresa");
        } else {
            deshabiliar = true;
            Map<String, Object> options = new HashMap();
            options.put("modal", true);
            options.put("draggable", true);
            options.put("position", "center center");
            options.put("resizable", false);
            options.put("contentHeight", 600);
            options.put("contentWidth", 660);
            VarSession.setVariableSession("nitEmpresa", empresa.getNumeroNit());
            VarSession.setVariableSession("carpetaFoto", carpetaNfs.getAbsolutePath());
            PrimeFaces.current().dialog().openDynamic("/app/control/oferentes/fotografia", options, null);
        }
    }

    private void cargarDetalleCalificacion() {
        ProcesoAdquisicion proceso = ((ParametrosMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "parametrosMB")).getProceso();
        idAnho = ((ParametrosMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "parametrosMB")).getAnho().getIdAnho();
        if (proceso == null || proceso.getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe seleccionar un proceso de contratación");
        } else {
            if (proceso.getPadreIdProcesoAdq() != null) {
                proceso = proceso.getPadreIdProcesoAdq();
            }
            DetRubroMuestraInteres detRubro = proveedorEJB.findDetRubroByAnhoAndRubro(idAnho, empresa.getIdEmpresa());
            if (detRubro == null) {
                JsfUtil.mensajeAlerta("No se han cargado los datos de este proveedor para el proceso de contratación del año " + proceso.getIdAnho().getAnho());
            } else {
                capacidadInst = proveedorEJB.findDetProveedor(detRubro.getIdRubroInteres().getIdRubroInteres(), idAnho, empresa, CapaInstPorRubro.class);
                if (capacidadInst == null) {
                    JsfUtil.mensajeAlerta("No se han cargado los datos de este proveedor para el proceso de contratación del año " + proceso.getIdAnho().getAnho());
                } else {
                    detalleProcesoAdq = JsfUtil.findDetalleByRubroAndAnho(proceso,
                            capacidadInst.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres(),
                            capacidadInst.getIdMuestraInteres().getIdAnho().getIdAnho());
                    departamentoCalif = proveedorEJB.findDetProveedor(detRubro.getIdRubroInteres().getIdRubroInteres(), idAnho, empresa, CapaDistribucionAcre.class);
                    personaNatural = (empresa.getIdPersoneria().getIdPersoneria().intValue() == 1);
                    if (personaNatural) {
                        mismaDireccion = (empresa.getIdMunicipio().getIdMunicipio().intValue() == empresa.getIdPersona().getIdMunicipio().getIdMunicipio().intValue()
                                && empresa.getDireccionCompleta().equals(empresa.getIdPersona().getDomicilio()));
                    }

                    /**
                     * Fecha: 30/08/2018 Comentario: Adición de validación de
                     * departamento calificado para proveedor
                     */
                    if (departamentoCalif == null || departamentoCalif.getCodigoDepartamento() == null) {
                        JsfUtil.mensajeAlerta("Este proveedor no posee departamento de calificación " + proceso.getIdAnho().getAnho());
                    } else {
                        codigoDepartamentoCalificado = departamentoCalif.getCodigoDepartamento().getCodigoDepartamento();

                        idMunicipioLocal = empresa.getIdMunicipio().getIdMunicipio();
                        codigoDepartamentoLocal = empresa.getIdMunicipio().getCodigoDepartamento().getCodigoDepartamento();

                        rubroUniforme = (departamentoCalif.getIdMuestraInteres().getIdRubroInteres().getIdRubroUniforme().intValue() == 1);

                        if (rubroUniforme) {
                            idCanton = empresa.getIdPersona().getCodigoCanton();
                            idCantonLocal = empresa.getCodigoCanton();
                        }

                        deshabiliar = false;
                        if (empresa.getIdPersona().getUrlImagen() == null) {
                            fileName = "fotoProveedores/profile.png";
                        } else {
                            fileName = "fotoProveedores/" + empresa.getIdPersona().getUrlImagen();
                        }
                    }
                }
            }
        }
    }

    public void empresaSeleccionada(SelectEvent event) {
        if (event.getObject() != null) {
            if (event.getObject() instanceof Empresa) {
                empresa = (Empresa) event.getObject();

                if (url.contains("DatosGenerales")) {
                    idMunicipio = empresa.getIdPersona().getIdMunicipio().getIdMunicipio();
                    codigoDepartamento = empresa.getIdPersona().getIdMunicipio().getCodigoDepartamento().getCodigoDepartamento();
                }

                if (empresa.getIdMunicipio() == null) {
                    empresa.setIdMunicipio(utilEJB.find(Municipio.class,
                            BigDecimal.ONE));

                    if (empresa.getIdPersona().getIdMunicipio() == null) {
                        empresa.getIdPersona().setIdMunicipio(utilEJB.find(Municipio.class,
                                BigDecimal.ONE));
                    }
                }
                VarSession.setVariableSession("idEmpresa", empresa.getIdEmpresa());
                cargarDetalleCalificacion();
                showUpdateEmpresa = ((Integer) VarSession.getVariableSession("idTipoUsuario") == 1);
            }
        } else {
            deshabiliar = false;
            JsfUtil.mensajeAlerta("Debe de seleccionar una empresa");
        }
    }

    public void empSelecMuniInteres(SelectEvent event) {
        if (event.getObject() != null) {
            if (event.getObject() instanceof Empresa) {
                empresa = (Empresa) event.getObject();
                VarSession.setVariableSession("idEmpresa", empresa.getIdEmpresa());
                cargarMunInteres();
            } else {
                Logger.getLogger(ProveedorController.class
                        .getName()).log(Level.INFO, "No se pudo convertir el objeto a la clase Empresa{0}", event.getObject());
            }
        } else {
            deshabiliar = false;
            JsfUtil.mensajeAlerta("Debe de seleccionar una empresa");
        }
    }

    private void cargarMunInteres() {
        cargarDetalleCalificacion();
        if (capacidadInst != null && capacidadInst.getIdCapInstRubro() != null) {
            if (departamentoCalif != null && departamentoCalif.getCodigoDepartamento() != null) {
                lstMunSource = datosGeograficosEJB.getLstMunicipiosDisponiblesDeInteres(departamentoCalif.getIdCapaDistribucion(), departamentoCalif.getCodigoDepartamento().getCodigoDepartamento());
                lstMunTarget = datosGeograficosEJB.getLstMunicipiosDeInteres(departamentoCalif.getIdCapaDistribucion());
                lstMunicipiosInteres = new DualListModel(lstMunSource, lstMunTarget);
            }
        }
    }

    public void empSelecPrecioRef(SelectEvent event) {
        if (event.getObject() != null) {
            if (event.getObject() instanceof Empresa) {
                empresa = (Empresa) event.getObject();
                fileName = "fotoProveedores/" + empresa.getIdPersona().getFoto();
                VarSession.setVariableSession("idEmpresa", empresa.getIdEmpresa());
                cargarDetalleCalificacion();
                if (capacidadInst != null && capacidadInst.getIdCapInstRubro() != null) {
                    cargarPrecioRef();
                }
            } else {
                Logger.getLogger(ProveedorController.class
                        .getName()).log(Level.WARNING, "No se pudo convertir el objeto a la clase Empresa{0}", event.getObject());
            }
        }
        estiloZapato = "0";
    }

    private void cargarPrecioRef() {
        if (capacidadInst != null && capacidadInst.getIdCapInstRubro() != null) {
            rubrosAmostrarInteres = capacidadInst.getIdMuestraInteres().getIdRubroInteres();
            lstItem = proveedorEJB.findItemProveedor(empresa, detalleProcesoAdq);
            lstPreciosReferencia = proveedorEJB.findPreciosRefRubroEmpRubro(getEmpresa(),
                    getDetalleProcesoAdq().getIdRubroAdq().getIdRubroInteres(),
                    getDetalleProcesoAdq().getIdProcesoAdq().getIdAnho().getIdAnho());
            switch (detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho().intValue()) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    preMaxRefPar = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(BigDecimal.ONE, detalleProcesoAdq);
                    preMaxRefCi = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(3), detalleProcesoAdq);
                    preMaxRefCii = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(4), detalleProcesoAdq);
                    preMaxRefCiii = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(5), detalleProcesoAdq);
                    preMaxRefBac = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(6), detalleProcesoAdq);
                    if (detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho().intValue() > 6
                            && detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue() == 2) {
                        //procesos de contratación mayores a 2018 y rubro de utiles
                        preMaxRefG1 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(10), detalleProcesoAdq);
                        preMaxRefG2 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(11), detalleProcesoAdq);
                        preMaxRefG3 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(12), detalleProcesoAdq);
                        preMaxRefG4 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(13), detalleProcesoAdq);
                        preMaxRefG5 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(14), detalleProcesoAdq);
                        preMaxRefG6 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(15), detalleProcesoAdq);
                        preMaxRefG7 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(7), detalleProcesoAdq);
                        preMaxRefG8 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(8), detalleProcesoAdq);
                        preMaxRefG9 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(9), detalleProcesoAdq);
                        preMaxRefB1 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(16), detalleProcesoAdq);
                        preMaxRefB2 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(17), detalleProcesoAdq);
                    }
                    break;
                default: //año 2021 año 2022
                    if (detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue() != 1) { //utiles y zapatos
                        preMaxRefPar = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(22), detalleProcesoAdq);
                    }
                    preMaxRefCi = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(3), detalleProcesoAdq);
                    preMaxRefCii = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(4), detalleProcesoAdq);
                    preMaxRefCiii = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(5), detalleProcesoAdq);
                    preMaxRefBac = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(6), detalleProcesoAdq);

                    if (detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue() == 2) { //utiles
                        preMaxRefCiiiMf = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(23), detalleProcesoAdq);
                        preMaxRefBacMf = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(24), detalleProcesoAdq);
                    }
                    break;
            }
        }
        PrimeFaces.current().ajax().update("frmPrincipal");
    }

    public void guardarDatosGenerales() {
        if (showUpdateEmpresa) {
            if (empresa.getIdPersoneria().getIdPersoneria().intValue() == 1) {
                empresa.setRazonSocial(empresa.getIdPersona().getNombreCompletoProveedor());
                empresa.setTelefonos(empresa.getIdPersona().getNumeroTelefono());
                empresa.setNumeroCelular(empresa.getIdPersona().getNumeroCelular());
                empresa.setNumeroNit(empresa.getIdPersona().getNumeroNit());

                if (mismaDireccion) {
                    idMunicipioLocal = idMunicipio;
                    idCantonLocal = idCanton;
                    empresa.setDireccionCompleta(empresa.getIdPersona().getDomicilio());

                    empresa.getIdPersona().setIdMunicipio(new Municipio(idMunicipio));
                    empresa.setIdMunicipio(new Municipio(idMunicipioLocal));
                    if (rubroUniforme) {
                        empresa.setCodigoCanton(idCantonLocal);
                        empresa.getIdPersona().setCodigoCanton(idCanton);
                    } else {
                        empresa.setCodigoCanton(null);
                        empresa.getIdPersona().setCodigoCanton(null);
                    }
                } else {
                    empresa.getIdPersona().setIdMunicipio(new Municipio(idMunicipio));
                    empresa.getIdPersona().setCodigoCanton(idCanton);
                }

            } else if (rubroUniforme) {
                empresa.setCodigoCanton(idCantonLocal);
                empresa.setIdMunicipio(new Municipio(idMunicipioLocal));
                empresa.getIdPersona().setIdMunicipio(new Municipio(idMunicipio));
                empresa.getIdPersona().setCodigoCanton(idCanton);
            }

            if (rubroUniforme) {
                empresa.setEsContribuyente(inscritoIva ? (short) 1 : 0);
                empresa.setDeseaInscribirse(deseaInscribirseIva ? (short) 1 : 0);
            }

            proveedorEJB.guardar(empresa);

            departamentoCalif.setCodigoDepartamento(utilEJB.find(Departamento.class,
                    codigoDepartamentoCalificado));

            if (proveedorEJB.guardarCapaInst(departamentoCalif, capacidadInst)) {
                JsfUtil.mensajeUpdate();
            }

        }

        departamentoCalif.setCodigoDepartamento(utilEJB.find(Departamento.class,
                codigoDepartamentoCalificado));

        if (proveedorEJB.guardarCapaInst(departamentoCalif, capacidadInst)) {
            JsfUtil.mensajeUpdate();
        }
    }

    public void guardarMunicipioInteres() {
        List<DisMunicipioInteres> lstMunicipioIntereses = proveedorEJB.findMunicipiosInteres(departamentoCalif);

        lstMunicipioIntereses.forEach((disMunicipioInteres) -> {
            disMunicipioInteres.setEstadoEliminacion(BigInteger.ONE);
        });

        if (!(lstMunicipiosInteres.getSource().isEmpty() && lstMunicipiosInteres.getTarget().isEmpty())) {
            for (MunicipioDto mun : getLstMunicipiosInteres().getTarget()) {
                Boolean existe = false;
                for (DisMunicipioInteres disMunicipioInteres : lstMunicipioIntereses) {
                    if (disMunicipioInteres.getIdMunicipio().getIdMunicipio().compareTo(mun.getIdMunicipio()) == 0) {
                        disMunicipioInteres.setEstadoEliminacion(BigInteger.ZERO);
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    DisMunicipioInteres disMunicipio = new DisMunicipioInteres();
                    disMunicipio.setEstadoEliminacion(BigInteger.ZERO);
                    disMunicipio.setFechaInsercion(new Date());
                    disMunicipio.setIdCapaDistribucion(departamentoCalif);
                    disMunicipio.setIdMunicipio(new Municipio(mun.getIdMunicipio()));
                    disMunicipio.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                    lstMunicipioIntereses.add(disMunicipio);
                }
            }

            Boolean val = false;
            for (DisMunicipioInteres disMunicipioInteres : lstMunicipioIntereses) {
                val = proveedorEJB.guardar(disMunicipioInteres);
                if (val == false) {
                    break;
                }
            }

            if (val) {
                JsfUtil.mensajeUpdate();
            } else {
                JsfUtil.mensajeError("Ha ocurrido un error en el registro de los datos.<br/>Reportar por favor al adminsitrador del sistema");
            }
        }
    }

    public void guardarPreciosRef() {
        if (detalleProcesoAdq.getIdDetProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de contratación");
        } else {
            String msj;
            Boolean preciosValidos = true;
            for (PreciosRefRubroEmp precio : lstPreciosReferencia) {
                if (precio.getNoItem() != null && !precio.getNoItem().isEmpty() && precio.getIdNivelEducativo() != null && precio.getIdProducto() != null && precio.getPrecioReferencia() != null && precio.getPrecioReferencia().compareTo(BigDecimal.ZERO) == 1) {
                } else {
                    preciosValidos = false;
                    break;
                }
            }

            if (preciosValidos) {
                lstPreciosReferencia.forEach((precio) -> {
                    proveedorEJB.guardar(precio);
                });
                lstPreciosReferencia = proveedorEJB.findPreciosRefRubroEmpRubro(getEmpresa(),
                        getDetalleProcesoAdq().getIdRubroAdq().getIdRubroInteres(),
                        getDetalleProcesoAdq().getIdProcesoAdq().getIdAnho().getIdAnho());

                msj = "Actualización exitosa";

                if (detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho().intValue() > 8) { // anho mayor de 2020
                    //validación de ingreso de todos los item para el rubro de uniforme
                    if (detalleProcesoAdq.getIdRubroAdq().getIdRubroUniforme().intValue() == 1) {
                        if (lstPreciosReferencia.size() < 12) {
                            msj = "Se han guardado los precios, pero es necesario que registre todos los item disponibles.";
                        }
                    }
                }

                JsfUtil.mensajeInformacion(msj);

            } else {
                JsfUtil.mensajeInformacion("Los precios de referencia no han sido guardados debido a que existen datos incompletos o erroneos.");
            }
        }
    }

    public void onCellEdit(CellEditEvent event) {
        msjError = "";
        //DataTable tbl = (DataTable) event.getSource();
        FacesContext context = FacesContext.getCurrentInstance();
        precioRef = context.getApplication().evaluateExpressionGet(context, "#{precio}", PreciosRefRubroEmp.class);
        boolean valido = true;
        if (!valido) {
            precioRef.setIdProducto(null);
            precioRef.setIdNivelEducativo(null);
        } else {
            this.rowEdit = String.valueOf(event.getRowIndex());
            if (event.getColumn().getColumnKey().contains("item")) {
                String numItem = event.getNewValue().toString();
                editarNumeroDeItem(event.getRowIndex(), numItem);
            } else if (event.getColumn().getColumnKey().contains("precio")) {
                agregarPrecio();
            }
        }
    }

    public void updateFilaDetalle() {
        if (rowEdit != null) {
            PrimeFaces.current().ajax().update("tblDetallePrecio:" + rowEdit + ":descripcionItem");
            PrimeFaces.current().ajax().update("tblDetallePrecio:" + rowEdit + ":nivelEducativo");
            PrimeFaces.current().ajax().update("tblDetallePrecio:" + rowEdit + ":precio");
            PrimeFaces.current().ajax().update("tblDetallePrecio:" + rowEdit + ":precio2");
        }
        if (!msjError.isEmpty()) {
            JsfUtil.mensajeAlerta(msjError);
        }
    }

    public void agregarPrecio() {
        if (precioRef != null) {
            BigDecimal preRef = BigDecimal.ZERO;

            if (precioRef.getIdNivelEducativo() != null) {
                switch (detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                    case 1://UNIFORMES
                    case 4:
                    case 5:
                        preRef = getPrecioRefUniforme();
                        break;
                    case 2:
                        preRef = getPrecioRefUtiles();
                        break;
                    case 3:
                        if (precioRef.getIdNivelEducativo().getIdNivelEducativo().compareTo(new BigDecimal("6")) == 0) {
                            preRef = new BigDecimal("16.00");
                        } else {
                            preRef = new BigDecimal("14.60");
                        }
                        break;
                }
            }

            if (precioRef.getPrecioReferencia() != null && precioRef.getPrecioReferencia().compareTo(preRef) == 1) {
                precioRef.setPrecioReferencia(BigDecimal.ZERO);
                switch (detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                    case 1:
                    case 5:
                        msjError = "Precio Máximo de Referencia para: <br/>"
                                + "1)<strong> Parvularia</strong>: - Blusa, Falda y Camisa: $ 4.25 y Pantalon Corto $ 4.00<br />"
                                + "2)<strong> Básica y Bachillerato</strong>: - Blusa, Falda y Camisa: $ 4.50 y Pantalon Corto y Pantalon: $ 6.00<br/>";
                        break;
                    case 2:
                        if (detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho().intValue() == 9) {
                            switch (detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                                case 4:
                                case 5:
                                    msjError = "Precio Máximo de Referencia para: <br/>"
                                            + "1)<strong> Parvularia</strong>: $ " + preMaxRefPar.getPrecioMaxMas() + "<br/>"
                                            + "2)<strong> Primer Ciclo</strong>: $ " + preMaxRefCi.getPrecioMaxMas() + "<br/>"
                                            + "3)<strong> Segundo Ciclo</strong>: $ " + preMaxRefCii.getPrecioMaxMas() + "<br/>"
                                            + "4)<strong> Tercer Ciclo</strong>: $ " + preMaxRefCiii.getPrecioMaxMas() + "<br/>"
                                            + "5)<strong> Bachillerato: $ " + preMaxRefBac.getPrecioMaxMas() + "</strong>";
                                    break;
                                case 2:
                                    msjError = "Precio Máximo de Referencia para: <br/>"
                                            + "1)<strong> Inicial y Parvularia</strong>: $ " + preMaxRefPar.getPrecioMaxMas() + "<br/>"
                                            + "2)<strong> Primer Ciclo</strong>: $ " + preMaxRefCi.getPrecioMaxMas() + "<br/>"
                                            + "3)<strong> Segundo Ciclo</strong>: $ " + preMaxRefCii.getPrecioMaxMas() + "<br/>"
                                            + "4)<strong> Tercer Ciclo</strong>: $ " + preMaxRefCiii.getPrecioMaxMas() + "<br/>"
                                            + "4)<strong> Tercer Ciclo - Mod.Flexible</strong>: $ " + preMaxRefCiiiMf.getPrecioMaxMas() + "<br/>"
                                            + "5)<strong> Bachillerato: </strong>$ " + preMaxRefBac.getPrecioMaxMas() + "<br/>"
                                            + "5)<strong> Bachillerato - Mod.Flexible: </strong>$ " + preMaxRefBacMf.getPrecioMaxMas();
                                    break;
                                case 3:
                                    msjError = "Precio Máximo de Referencia para: <br/>"
                                            + "1)<strong> Inicial y Parvularia</strong>: $ " + preMaxRefPar.getPrecioMaxMas() + "<br/>"
                                            + "2)<strong> Primer Ciclo</strong>: $ " + preMaxRefCi.getPrecioMaxMas() + "<br/>"
                                            + "3)<strong> Segundo Ciclo</strong>: $ " + preMaxRefCii.getPrecioMaxMas() + "<br/>"
                                            + "4)<strong> Tercer Ciclo</strong>: $ " + preMaxRefCiii.getPrecioMaxMas() + "<br/>"
                                            + "5)<strong> Bachillerato: </strong>$ " + preMaxRefBac.getPrecioMaxMas();
                                    break;
                            }
                        } else {
                            msjError = "Precio Máximo de Referencia para: <br/>"
                                    + "1)<strong> Parvularia</strong>: $ " + preMaxRefPar.getPrecioMaxMas() + "<br/>"
                                    + "2)<strong> Primer Ciclo</strong>: $ " + preMaxRefCi.getPrecioMaxMas() + "<br/>"
                                    + "3)<strong> Segundo Ciclo</strong>: $ " + preMaxRefCii.getPrecioMaxMas() + "<br/>"
                                    + "4)<strong> Tercer Ciclo</strong>: $ " + preMaxRefCiii.getPrecioMaxMas() + "<br/>"
                                    + "5)<strong> Bachillerato: $ " + preMaxRefBac.getPrecioMaxMas() + "</strong>";
                        }
                        break;
                    case 3:
                        msjError = "Precio Máximo de Referencia para Zapatos escolares de:<br/> "
                                + "<strong>Parvularia y Básica</strong>: $ 14.60 <br/>"
                                + "<strong>Bachillerato</strong>: $16.00";
                        break;
                }
            }
        }
    }

    private BigDecimal getPrecioRefUniforme() {
        BigDecimal preRef = BigDecimal.ZERO;

        switch (precioRef.getIdProducto().getIdProducto().intValue()) {
            case 29:
            case 30:
            case 44:
                switch (precioRef.getIdNivelEducativo().getIdNivelEducativo().intValue()) {
                    case 1:
                        preRef = new BigDecimal("4.25");
                        break;
                    case 2:
                    case 6:
                        preRef = new BigDecimal("4.50");
                        break;
                }
                break;
            case 31:
                switch (precioRef.getIdNivelEducativo().getIdNivelEducativo().intValue()) {
                    case 1:
                        preRef = new BigDecimal("4.00");
                        break;
                }
                break;
            case 34:
                switch (precioRef.getIdNivelEducativo().getIdNivelEducativo().intValue()) {
                    case 1:
                        preRef = new BigDecimal("6.00");
                        break;
                    case 2:
                    case 6:
                        preRef = new BigDecimal("6.00");
                        break;
                }
                break;
        }

        return preRef;
    }

    private BigDecimal getPrecioRefUtiles() {
        BigDecimal preRef = BigDecimal.ZERO;

        switch (precioRef.getIdNivelEducativo().getIdNivelEducativo().intValue()) {
            case 1: //parvularia
                preRef = preMaxRefPar.getPrecioMaxMas();
                break;
            case 22: //incial y parvularia
                preRef = preMaxRefPar.getPrecioMaxMas();
                break;
            case 3: //ciclo I
                preRef = preMaxRefCi.getPrecioMaxMas();
                break;
            case 4: //ciclo II
                preRef = preMaxRefCii.getPrecioMaxMas();
                break;
            case 5://ciclo III
                preRef = preMaxRefCiii.getPrecioMaxMas();
                break;
            case 23://modalidad flexible y ciclo III
                preRef = preMaxRefCiii.getPrecioMaxMas();
                break;
            case 6: //Bachillerato
                preRef = preMaxRefBac.getPrecioMaxMas();
                break;
            case 24: //modalidad flexible y Bachillerato
                preRef = preMaxRefBac.getPrecioMaxMas();
                break;
        }

        return preRef;
    }

    private void editarNumeroDeItem(int rowEdit, String numItem) {
        Boolean itemRepetido = false;
        BigDecimal precioLibro = BigDecimal.ZERO;
        for (int i = 0; i < lstPreciosReferencia.size(); i++) {
            if (i != rowEdit) {
                if (lstPreciosReferencia.get(i).getNoItem() != null
                        && lstPreciosReferencia.get(i).getNoItem().equals(numItem)) {
                    itemRepetido = true;
                    break;
                }
            }
        }

        if (itemRepetido) {
            precioRef.setNoItem("");
            msjError = "Este Item ya fue ingresado.";
        } else {
            CatalogoProducto item = null;
            NivelEducativo nivel = null;
            if (numItem != null && !numItem.isEmpty()) {
                switch (detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                    case 1: //UNIFORMES
                    case 4:
                        switch (Integer.parseInt(numItem)) {
                            case 0:
                                break;
                            case 1:
                            case 6:
                            case 10:
                                item = proveedorEJB.findProducto("30");
                                switch (Integer.parseInt(numItem)) {
                                    case 1:
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case 6:
                                        nivel = proveedorEJB.findNivelEducativo("2");
                                        break;
                                    default:
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                }
                                break;
                            case 2:
                            case 7:
                            case 11:
                                item = proveedorEJB.findProducto("44");
                                switch (Integer.parseInt(numItem)) {
                                    case 2:
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case 7:
                                        nivel = proveedorEJB.findNivelEducativo("2");
                                        break;
                                    default:
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                }
                                break;
                            case 3:
                            case 8:
                            case 12:
                                item = proveedorEJB.findProducto("29");
                                switch (Integer.parseInt(numItem)) {
                                    case 3:
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case 8:
                                        nivel = proveedorEJB.findNivelEducativo("2");
                                        break;
                                    default:
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                }
                                break;
                            case 4:
                                item = proveedorEJB.findProducto("31");
                                nivel = proveedorEJB.findNivelEducativo("1");
                                break;
                            case 5:
                            case 9:
                            case 13:
                                item = proveedorEJB.findProducto("34");
                                switch (Integer.parseInt(numItem)) {
                                    case 5:
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case 9:
                                        nivel = proveedorEJB.findNivelEducativo("2");
                                        break;
                                    default:
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                }
                                break;
                            default:
                                item = null;
                                nivel = null;
                                msjError = "El item ingresado no es válido.";
                                break;
                        }
                        break;
                    case 2: //UTILES
                        switch (detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho().intValue()) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                //procesos antes de la contratacion de 2019
                                switch (Integer.parseInt(numItem)) {
                                    case 1:
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case 2:
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("3");
                                        break;
                                    case 3:
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("4");
                                        break;
                                    case 4:
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("5");
                                        break;
                                    case 5:
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                    default:
                                        item = null;
                                        nivel = null;
                                        msjError = "El item ingresado no es válido.";
                                        break;
                                }
                                break;
                            case 7:
                            case 8:
                                //procesos mayor o igual a la contratacion de 2019
                                switch (numItem) {
                                    case "1":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case "2":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("3");
                                        break;
                                    case "3":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("4");
                                        break;
                                    case "4":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("5");
                                        break;
                                    case "5":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                    case "2.1": //grado 1
                                        item = proveedorEJB.findProducto("1");
                                        nivel = proveedorEJB.findNivelEducativo("10");
                                        precioLibro = new BigDecimal("4.10");
                                        break;
                                    case "2.2": //grado 2
                                        item = proveedorEJB.findProducto("1");
                                        nivel = proveedorEJB.findNivelEducativo("11");
                                        precioLibro = new BigDecimal("4.10");
                                        break;
                                    case "2.3": //grado 3
                                        item = proveedorEJB.findProducto("1");
                                        nivel = proveedorEJB.findNivelEducativo("12");
                                        precioLibro = new BigDecimal("3.62");
                                        break;
                                    case "3.1": //grado 4
                                        item = proveedorEJB.findProducto("1");
                                        nivel = proveedorEJB.findNivelEducativo("13");
                                        precioLibro = new BigDecimal("3.62");
                                        break;
                                    case "3.2": //grado 5
                                        item = proveedorEJB.findProducto("1");
                                        nivel = proveedorEJB.findNivelEducativo("14");
                                        precioLibro = new BigDecimal("3.62");
                                        break;
                                    case "3.3": //grado 6
                                        item = proveedorEJB.findProducto("1");
                                        nivel = proveedorEJB.findNivelEducativo("15");
                                        precioLibro = new BigDecimal("3.62");
                                        break;
                                    case "4.1": //grado 7
                                        item = proveedorEJB.findProducto("1");
                                        nivel = proveedorEJB.findNivelEducativo("7");
                                        precioLibro = new BigDecimal("3.62");
                                        break;
                                    case "4.2": //grado 8
                                        item = proveedorEJB.findProducto("1");
                                        nivel = proveedorEJB.findNivelEducativo("8");
                                        precioLibro = new BigDecimal("3.62");
                                        break;
                                    case "4.3": //grado 9
                                        item = proveedorEJB.findProducto("1");
                                        nivel = proveedorEJB.findNivelEducativo("9");
                                        precioLibro = new BigDecimal("3.62");
                                        break;
                                    case "5.1": //1er año bachillerato
                                        item = proveedorEJB.findProducto("1");
                                        nivel = proveedorEJB.findNivelEducativo("16");
                                        precioLibro = new BigDecimal("2.05");
                                        break;
                                    case "5.2": //2do año bachillerato
                                        item = proveedorEJB.findProducto("1");
                                        nivel = proveedorEJB.findNivelEducativo("17");
                                        precioLibro = new BigDecimal("2.05");
                                        break;
                                    default:
                                        item = null;
                                        nivel = null;
                                        msjError = "El item ingresado no es válido.";
                                        break;
                                }
                                break;
                            default:
                                //procesos mayor o igual a la contratacion de 2021
                                switch (numItem) {
                                    case "1":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("22");
                                        break;
                                    case "2":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("3");
                                        break;
                                    case "3":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("4");
                                        break;
                                    case "4":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("5");
                                        break;
                                    case "4.4":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("23");
                                        break;
                                    case "5":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                    case "5.1":
                                        item = proveedorEJB.findProducto("54");
                                        nivel = proveedorEJB.findNivelEducativo("24");
                                        break;

                                    default:
                                        item = null;
                                        nivel = null;
                                        msjError = "El item ingresado no es válido.";
                                        break;
                                }
                                break;
                        }
                        break;
                    case 3: //ZAPATOS
                        switch (detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho().intValue()) {
                            case 9: //año 2021
                                switch (Integer.parseInt(numItem)) {
                                    case 1:
                                        item = proveedorEJB.findProducto("21");
                                        nivel = proveedorEJB.findNivelEducativo("22");
                                        break;
                                    case 2:
                                        item = proveedorEJB.findProducto("43");
                                        nivel = proveedorEJB.findNivelEducativo("22");
                                        break;
                                    case 3:
                                        item = proveedorEJB.findProducto("21");
                                        nivel = proveedorEJB.findNivelEducativo("3");
                                        break;
                                    case 4:
                                        item = proveedorEJB.findProducto("43");
                                        nivel = proveedorEJB.findNivelEducativo("3");
                                        break;
                                    case 5:
                                        item = proveedorEJB.findProducto("21");
                                        nivel = proveedorEJB.findNivelEducativo("4");
                                        break;
                                    case 6:
                                        item = proveedorEJB.findProducto("43");
                                        nivel = proveedorEJB.findNivelEducativo("4");
                                        break;
                                    case 7:
                                        item = proveedorEJB.findProducto("21");
                                        nivel = proveedorEJB.findNivelEducativo("5");
                                        break;
                                    case 8:
                                        item = proveedorEJB.findProducto("43");
                                        nivel = proveedorEJB.findNivelEducativo("5");
                                        break;
                                    case 9:
                                        item = proveedorEJB.findProducto("21");
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                    case 10:
                                        item = proveedorEJB.findProducto("43");
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                    default:
                                        item = null;
                                        nivel = null;
                                        msjError = "El item ingresado no es válido.";
                                        break;
                                }
                                break;
                            default:
                                switch (Integer.parseInt(numItem)) {
                                    case 1:
                                        item = proveedorEJB.findProducto("21");
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case 2:
                                        item = proveedorEJB.findProducto("43");
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case 3:
                                        item = proveedorEJB.findProducto("21");
                                        nivel = proveedorEJB.findNivelEducativo("3");
                                        break;
                                    case 4:
                                        item = proveedorEJB.findProducto("43");
                                        nivel = proveedorEJB.findNivelEducativo("3");
                                        break;
                                    case 5:
                                        item = proveedorEJB.findProducto("21");
                                        nivel = proveedorEJB.findNivelEducativo("4");
                                        break;
                                    case 6:
                                        item = proveedorEJB.findProducto("43");
                                        nivel = proveedorEJB.findNivelEducativo("4");
                                        break;
                                    case 7:
                                        item = proveedorEJB.findProducto("21");
                                        nivel = proveedorEJB.findNivelEducativo("5");
                                        break;
                                    case 8:
                                        item = proveedorEJB.findProducto("43");
                                        nivel = proveedorEJB.findNivelEducativo("5");
                                        break;
                                    case 9:
                                        item = proveedorEJB.findProducto("21");
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                    case 10:
                                        item = proveedorEJB.findProducto("43");
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                    default:
                                        item = null;
                                        nivel = null;
                                        msjError = "El item ingresado no es válido.";
                                        break;
                                }
                                break;
                        }
                        break;
                }

                if (item == null && nivel == null) {
                } else if (isProductoIsValid(item.getIdProducto())) {
                    precioRef.setIdProducto(item);
                    precioRef.setIdNivelEducativo(nivel);
                    if (precioLibro.intValue() > 0) {
                        precioRef.setPrecioReferencia(precioLibro);
                    }
                } else {
                    precioRef.setNoItem("");
                    msjError = "El proveedore NO ESTA CALIFICADO para ofertar este ITEM";
                }

            }
        }
    }

    private boolean isProductoIsValid(BigDecimal idProducto) {
        if (lstItem.stream().anyMatch(producto -> (producto.getIdProducto().intValue() == idProducto.intValue()))) {
            return true;
        }
        JsfUtil.mensajeError("El proveedore NO ESTA CALIFICADO para ofertar este ITEM");
        return false;
    }

    public void eliminarDetalle() {
        if (precioRef != null) {
            if (precioRef.getEstadoEliminacion().compareTo(BigInteger.ZERO) == 0) {
                if (precioRef.getIdPrecioRefEmp() != null) {
                    precioRef.setEstadoEliminacion(BigInteger.ONE);
                } else {
                    lstPreciosReferencia.remove(idRow);
                }
            } else {
                precioRef.setEstadoEliminacion(BigInteger.ZERO);
            }

            precioRef = null;
        } else {
            JsfUtil.mensajeAlerta("Debe seleccionar un precio para poder eliminarlo.");
        }
        idRow = -1;
    }

    public void agregarNewPrecio() {
        if (detalleProcesoAdq.getIdDetProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de contratación");
        } else {
            if (detalleProcesoAdq.getIdDetProcesoAdq() != null) {
                PreciosRefRubroEmp current = new PreciosRefRubroEmp();
                current.setEstadoEliminacion(BigInteger.ZERO);
                current.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                current.setFechaInsercion(new Date());
                current.setIdEmpresa(empresa);
                current.setIdMuestraInteres(capacidadInst.getIdMuestraInteres());
                lstPreciosReferencia.add(current);
            } else {
                JsfUtil.mensajeInformacion("Debe de seleccionar un proceso de contratación.");
            }
        }
    }

    public void updateFrm(SelectEvent event) {
        fotoProveedor = (String) event.getObject();
        if (!fotoProveedor.contains("/")) {
            showFoto = false;
        } else {
            empresa.getIdPersona().setUrlImagen(fotoProveedor);
        }
    }

    public void updateFrmFotoZapato(SelectEvent event) {
        cargarFotografias();
    }

    public void impOfertaGlobal() {
        try {
            String idGestion = proveedorEJB.datosConfirmados(capacidadInst.getIdMuestraInteres().getIdMuestraInteres(),
                    empresa.getIdEmpresa(),
                    VarSession.getVariableSessionUsuario());

            String lugar = empresa.getIdMunicipio().getNombreMunicipio().concat(", ").concat(empresa.getIdMunicipio().getCodigoDepartamento().getNombreDepartamento());
            if (idGestion.isEmpty()) {
                idGestion = proveedorEJB.datosConfirmados(capacidadInst.getIdMuestraInteres().getIdMuestraInteres(),
                        empresa.getIdEmpresa(),
                        VarSession.getVariableSessionUsuario());
            }

            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            HashMap param = new HashMap();
            param.put("ubicacionImagenes", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
            param.put("pEscudo", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
            param.put("usuarioInsercion", VarSession.getVariableSessionUsuario());
            param.put("pLugar", lugar);
            param.put("pRubro", JsfUtil.getNombreRubroById(capacidadInst.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres()));
            param.put("pIdRubro", capacidadInst.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres().intValue());
            param.put("pCorreoPersona", capacidadInst.getIdMuestraInteres().getIdEmpresa().getIdPersona().getEmail());
            param.put("pIdGestion", idGestion);

            List<OfertaGlobal> lstDatos = reportesEJB.getLstOfertaGlobal(empresa.getNumeroNit(), detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres(), detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho());
            lstDatos.get(0).setRubro(JsfUtil.getNombreRubroById(capacidadInst.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres()));
            if (lstDatos.get(0).getDepartamento().contains("TODO EL PAIS")) {
                param.put("productor", Boolean.TRUE);
            } else {
                param.put("productor", Boolean.FALSE);
            }

            List<JasperPrint> jasperPrintList = new ArrayList();

            jasperPrintList.add(JasperFillManager.fillReport(
                    Reportes.getPathReporte("sv/gob/mined/apps/reportes/oferta" + File.separator + "rptOfertaGlobalProv" + detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getAnho() + ".jasper"),
                    param, new JRBeanCollectionDataSource(lstDatos)));

            String muni = VarSession.getNombreMunicipioSession();

            param.put("pLugar", empresa.getIdMunicipio().getCodigoDepartamento().getNombreDepartamento());

            if (empresa.getIdPersoneria().getIdPersoneria().intValue() == 1) {
                jasperPrintList.add(Reportes.getReporteAImprimir("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerProvNat" + detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getAnho(), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(empresa, detalleProcesoAdq, muni))));

            } else {
                jasperPrintList.add(Reportes.getReporteAImprimir("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerProvJur" + detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getAnho(), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(empresa, detalleProcesoAdq, muni))));

            }

            /*jasperPrintList.addAll(Reportes.getReporteOfertaDeProveedor(capacidadInst, empresa, detalleProcesoAdq,
                    reportesEJB.getLstOfertaGlobal(empresa.getNumeroNit(), detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres(), detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho()),
                    reportesEJB.getDeclaracionJurada(empresa, detalleProcesoAdq, VarSession.getNombreMunicipioSession())));*/
            if (!jasperPrintList.isEmpty()) {
                Reportes.generarReporte(jasperPrintList, "oferta_global_" + getEmpresa().getNumeroNit());
            }
        } catch (JRException | IOException ex) {
            Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscarAdjudicacionesProveedor(SelectEvent event) {
        if (event.getObject() != null) {
            if (event.getObject() instanceof Empresa) {
                empresa = (Empresa) event.getObject();
                cargarDetalleCalificacion();
                /**
                 * Fecha: 05/09/2018 Comentario: Validación para la capacidad
                 * instalada del proveedor seleccionado
                 */
                if (capacidadInst != null && capacidadInst.getIdCapInstRubro() != null) {
                    detalleProcesoAdq = JsfUtil.findDetalleByRubroAndAnho(getRecuperarProceso().getProcesoAdquisicion(),
                            capacidadInst.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres(),
                            capacidadInst.getIdMuestraInteres().getIdAnho().getIdAnho());

                    lstResumenAdj = proveedorEJB.resumenAdjProveedor(empresa.getNumeroNit(), detalleProcesoAdq.getIdDetProcesoAdq());
                    if (lstResumenAdj.isEmpty()) {
                        JsfUtil.mensajeInformacion("No se encontrarón adjudicaciones para este proveedor.");
                    } else {
                        totalItems = BigDecimal.ZERO;
                        totalMonto = BigDecimal.ZERO;
                        lstResumenAdj.forEach(resumen -> {
                            totalItems = totalItems.add(resumen.getCantidad());
                            totalMonto = totalMonto.add(resumen.getMonto());
                        });
                        lstDetalleAdj = proveedorEJB.detalleAdjProveedor(empresa.getNumeroNit(), detalleProcesoAdq.getIdDetProcesoAdq());
                    }
                }
            }
        } else {
            deshabiliar = false;
            JsfUtil.mensajeAlerta("Debe de seleccionar una empresa");

        }
    }

    @FacesConverter(forClass = MunicipioDto.class, value = "muniConverter")
    public static class MunicipioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProveedorController controller = (ProveedorController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "proveedorController");
            MunicipioDto mun = new MunicipioDto();
            try {
                BeanUtils.copyProperties(mun, controller.utilEJB.find(Municipio.class, getKey(value)));
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return mun;
        }

        java.math.BigDecimal getKey(String value) {
            java.math.BigDecimal key;
            key = new java.math.BigDecimal(value);
            return key;
        }

        String getStringKey(java.math.BigDecimal value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MunicipioDto) {
                MunicipioDto o = (MunicipioDto) object;
                return getStringKey(o.getIdMunicipio());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Municipio.class.getName());
            }
        }
    }

    public void resumenAdjudicacionesXls(Object document) {
        int[] numEnt = {0, 4};
        int[] numDec = {5};
        RptExcel.generarRptExcelGenerico((HSSFWorkbook) document, numEnt, numDec);
    }

    public String getNombreRubroProveedor() {
        if (capacidadInst != null && capacidadInst.getIdMuestraInteres() != null) {
            return JsfUtil.getNombreRubroById(capacidadInst.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres());
        } else {
            return "";
        }
    }

    public void calcularNoItems() {
        if (getRecuperarProceso().getProcesoAdquisicion() != null) {
            proveedorEJB.calcularNoItems(rubro, getRecuperarProceso().getProcesoAdquisicion().getIdAnho().getIdAnho());
        }
    }

    public void calcularNoItemByNit() {
        if (getRecuperarProceso().getProcesoAdquisicion() != null) {
            proveedorEJB.calcularNoItems(rubro, getRecuperarProceso().getProcesoAdquisicion().getIdAnho().getIdAnho(), numeroNit, null);
            proveedorEJB.calcularPreRefByNit(rubro, getRecuperarProceso().getProcesoAdquisicion().getIdAnho().getIdAnho(), numeroNit);
        }
    }

    public void calcularPreciosEmp() {
        if (getRecuperarProceso().getProcesoAdquisicion() != null) {
            proveedorEJB.calcularPreRef(rubro, getRecuperarProceso().getProcesoAdquisicion().getIdAnho().getIdAnho());
        }
    }

    public void imprimirDeclaraciones() {
        try {
            String nombreRpt;
            JasperPrint rptTemp;
            List<JasperPrint> lstRptAImprimir = new ArrayList();
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

            for (ContratosOrdenesCompras contratosOrdenesCompras : proveedorEJB.getLstContratosByNitAndAnho(empresa.getNumeroNit(), getRecuperarProceso().getProcesoAdquisicion().getIdAnho().getAnho())) {
                HashMap param = new HashMap();
                param.put("idContrato", contratosOrdenesCompras.getIdContrato());
                param.put("ubicacionImagenes", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
                param.put("pAnho", detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getAnho());

                nombreRpt = "sv/gob/mined/apps/reportes/declaracion/rptDeclaracionAdjudicatorio".concat(contratosOrdenesCompras.getIdResolucionAdj().getIdParticipante().getIdEmpresa().getIdPersoneria().getIdPersoneria().intValue() == 1 ? "PerNat" : "PerJur").concat(param.get("pAnho").toString());
                rptTemp = reportesEJB.getRpt(param, Reportes.getPathReporte(nombreRpt + ".jasper"));
                lstRptAImprimir.add(rptTemp);
            }

            Reportes.generarReporte(lstRptAImprimir, "documentos_prov_" + empresa.getNumeroNit());
        } catch (IOException | JRException ex) {
            Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resetDatosProveedor() {
        Boolean continuar = false;
        if (numeroNit == null || numeroNit.isEmpty()) {
            JsfUtil.mensajeAlerta("Por favor digite un NIT");
        } else {
            Empresa emp = proveedorEJB.findEmpresaByNit(numeroNit, false);
            if (emp == null) {
                JsfUtil.mensajeAlerta("No existe ningún Proveedor o Representante Legal con el NIT ingresado");
            } else {
                continuar = true;
            }
        }
        if (rubro != null) {
            if (resetAceptacion && continuar) {
                proveedorEJB.resetAceptacion(numeroNit, getRecuperarProceso().getProcesoAdquisicion().getIdAnho().getIdAnho());
            }
            if (resetUsuario && continuar) {
                proveedorEJB.resetActivacion(numeroNit);
            }
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar un Rubro de Adquisición");
        }
    }

    public void generarCodigoSeguridad() {
        proveedorEJB.generarCodigoSeguridad(JsfUtil.findDetalle(getRecuperarProceso().getProcesoAdquisicion(), rubro).getIdDetProcesoAdq());
    }

    public void generarCodigo() {
        String nits = "0308-080792-101-5,0315-240453-002-8,0614-080552-008-0,1412-260958-001-1,0101-220786-103-2,0609-110777-102-3,0311-111067-101-3,1010-060566-101-0,0708-280476-102-2,0210-200559-006-4,0210-180478-104-6,0427-091191-101-0,0308-300393-101-7,0207-150396-101-8,0614-241068-119-7,0316-080692-101-5,1404-291058-101-2,0610-030881-101-4,1217-180481-103-0,1309-280689-101-1,0210-180692-102-3,0614-291264-112-1,0103-091175-102-0,0619-270790-102-9,0502-160991-103-0,0802-020778-102-9,0111-010197-101-9,0213-040275-102-7,0207-231078-101-9,0105-180563-101-2,1324-120172-102-0,1123-240766-101-0,1405-051176-101-8,0821-120866-101-9,0612-180658-001-0,0614-010567-120-0,1215-160177-101-6,0201-110890-101-1,0204-011069-101-6,0608-140974-102-0,0210-160778-101-8,0805-240674-102-7,0509-100990-103-9,0520-220477-103-0,0614-070673-114-6,1416-110588-102-5,1109-021055-101-9,0610-200782-101-7,0821-071285-105-1,1404-250582-102-2,1217-110380-102-2,0210-250576-118-3,0520-030884-102-0,0819-220383-101-9,0101-060464-104-6,1405-040289-101-7,1213-141085-101-9,0608-050384-101-6,1206-071283-101-2,0405-031056-101-2,0809-130284-101-7,0819-061278-101-2,0821-080301-103-0,0203-250579-101-7,0816-160381-102-2,1408-090862-101-4,0307-021284-102-0,0106-030785-101-2,1212-140878-102-4,0203-020772-102-0,0614-300371-124-0,0614-080686-116-0,1416-181076-101-2,0702-190565-001-5,1405-030182-102-6,1010-200184-103-9,0617-301186-104-4,0610-230686-101-7,0210-280275-111-1,1208-280477-102-8,0302-150468-103-1,0422-260880-101-1,1102-221247-001-5,1010-160993-103-4,1319-140285-101-4,0614-150970-004-5,1212-070296-101-8,0101-080673-105-5,0203-300857-101-8,0103-251174-102-8,0312-240782-102-4,1303-071080-101-6,0313-270277-101-8,1319-141096-101-5,1105-010276-101-3,0207-290595-101-5,0309-020466-101-4,0614-050785-142-6,1209-281088-105-1,0422-200461-101-0,0203-220148-101-1,1010-240466-102-2,0610-291165-102-4,1217-180366-104-3,0819-180779-101-9,0315-151091-106-6,0308-050678-101-3,1312-200273-103-4,0210-050678-116-9,0805-110456-101-5,0213-250370-102-3,0402-240652-101-8,0610-140485-102-3,1319-260776-101-4,1107-230180-101-0,1418-191182-101-7,0614-300170-115-8,0802-040966-101-7,0602-121293-105-4,1123-211194-105-0,0618-071184-101-6,0716-230960-101-2,0511-220388-104-6,0204-110981-101-2,0901-121269-101-6,0306-150878-105-9,0702-110959-101-0,0605-080769-102-2,0617-150762-101-6,0412-300888-102-1,0614-080689-132-9,0422-230694-101-9,0111-010380-104-8,0210-121077-110-5,0816-190499-101-8,0202-130673-101-1,0210-040573-104-7,0210-130864-101-3,1010-230951-001-8,0703-050373-101-2,0302-080577-102-1,0416-020992-101-6,0306-070882-101-6,0210-070267-101-3,0821-250880-102-1,0614-170260-007-7,0101-070967-101-3,0821-300877-104-1,0210-180775-101-6,0308-241290-102-6,0430-230170-101-3,0906-180380-103-9,0503-260468-101-1,0402-110877-101-3,0203-060267-103-1,0614-160174-118-5,0111-230993-102-1,0614-190471-138-6,1311-260777-102-4,0203-140181-102-8,0905-110991-101-8,1217-081077-111-7,0301-051174-102-1,0816-041280-101-2";
        RC4Crypter rc4 = new RC4Crypter();

        for (String nit : nits.split(",")) {
            Empresa emp = proveedorEJB.findEmpresaByNit(nit, true);
            EmpresaCodigoSeg ecs = new EmpresaCodigoSeg();

            ecs.setIdEmpresa(emp.getIdEmpresa());
            ecs.setCodigoSeguridad(rc4.encrypt("ha", emp.getIdEmpresa().toString().concat(emp.getNumeroNit())).substring(0, 10));
            ecs.setUsuarioActivado((short) 0);

            proveedorEJB.generarCodigoSeguridad(ecs);
        }
    }

    public void generarNotificacionUniformes() {
        StringBuilder sb = new StringBuilder();
        String nombreCanton = "";

        for (Canton canton : getLstCantones()) {
            if (canton.getCodigoCanton().equals(idCantonLocal) && canton.getIdMunicipio().intValue() == empresa.getIdMunicipio().getIdMunicipio().intValue()) {
                nombreCanton = canton.getNombreCanton();
                break;
            }
        }

        NotificacionOfertaProvDto nopd = proveedorEJB.getNotificacionOfertaProv(empresa.getIdEmpresa(), idAnho, detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres());

        sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("prov_notif_inscripcion_uniforme.email.mensaje"),
                nopd.getRazonSocial(),
                nopd.getNumeroNit(),
                nopd.getDescripcionRubro(),
                nopd.getPrograma(),
                rubroUniforme ? nombreCanton.concat(",").concat(nopd.getUbicacionPer()) : nopd.getUbicacionPer(),
                nopd.getDomicilio(),
                nopd.getTelefonoPer(),
                nopd.getDireccionCompleta(),
                nopd.getTelefonoLocal(),
                rubroUniforme ? "Cantón, Municipio y Departamento" : "Municipio y Departamento"));

        sb.append(RESOURCE_BUNDLE.getString("prov_notif_inscripcion_uniforme.email.mensaje.tbl_precios.header"));
        nopd.getLstDetItemOfertaGlobal().forEach((detalle) -> {
            sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("prov_notif_inscripcion_uniforme.email.mensaje.tbl_precios.detalle"), detalle.getDescripcionItem(), detalle.getPrecioMaxReferencia(), detalle.getPrecioUnitario()));
        });
        sb.append(RESOURCE_BUNDLE.getString("prov_notif_inscripcion_uniforme.email.mensaje.tbl_precios.fin"));

        sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("prov_notif_inscripcion_uniforme.email.mensaje.middle"), departamentoCalif.getCodigoDepartamento().getNombreDepartamento()));

        sb.append(RESOURCE_BUNDLE.getString("prov_notif_inscripcion_uniforme.email.mensaje.tbl_municipio.header"));
        nopd.getLstMunIntOfertaGlobal().forEach((detalle) -> {
            sb.append(MessageFormat.format(RESOURCE_BUNDLE.getString("prov_notif_inscripcion_uniforme.email.mensaje.tbl_municipio.detalle"), detalle.getNombreDepartamento(), detalle.getNombreMunicipio()));
        });
        sb.append(RESOURCE_BUNDLE.getString("prov_notif_inscripcion_uniforme.email.mensaje.tbl_municipio.fin"));

        List<String> to = new ArrayList();
        List<String> cc = new ArrayList();
        List<String> bcc = new ArrayList();

        to.add(empresa.getIdPersona().getEmail());

        cc.add(VarSession.getUsuarioSession().getIdPersona().getEmail());
        cc.add("rafael.jose.arias@admin.mined.edu.sv");
        cc.add("carlos.enrique.villegas@admin.mined.edu.sv");

        //bcc.add("miguelsr1@gmail.com");

        eMailEJB.enviarMail("Notificación de Recepción de Oferta Global " + detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getAnho(),
                sb.toString(),
                to,
                cc,
                bcc,
                JsfUtil.getSessionMailG("2"));
    }
}
