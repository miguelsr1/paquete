package sv.gob.mined.app.web.controller.proveedor;

/**
 *
 * @author misanchez
 */
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import sv.gob.mined.app.web.controller.pagoprov.ProveedorController;
import sv.gob.mined.app.web.controller.pagoprov.modulo.PreciosReferenciaMB;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.PreciosReferenciaEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;
import sv.gob.mined.paquescolar.model.CatalogoProducto;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.PreciosRefRubro;
import sv.gob.mined.paquescolar.model.PreciosRefRubroEmp;
import sv.gob.mined.paquescolar.model.pojos.OfertaGlobal;

@ManagedBean
@ViewScoped
public class PreciosReferenciaView implements Serializable {

    private int idRow;
    
    private Boolean deshabiliar = true;
    
    private String msjError;
    private String rowEdit;
        
    private DetalleProcesoAdq detalleProcesoAdq;
    private PreciosRefRubroEmp precioRef = new PreciosRefRubroEmp();
    private PreciosRefRubro preMaxRefPar = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefCi = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefCii = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefCiii = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefCiiiMf = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefBac = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefBacMf = new PreciosRefRubro();
        
    private List<CatalogoProducto> lstItem = new ArrayList();
    private List<PreciosRefRubroEmp> lstPreciosReferencia = new ArrayList();

    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private PreciosReferenciaEJB preciosReferenciaEJB;
    @EJB
    private ReportesEJB reportesEJB;

    @ManagedProperty(value = "#{cargaGeneralView}")
    private CargaGeneralView cargaGeneralView;
    
    @ManagedProperty(value = "#{preciosReferenciaMB}")
    private PreciosReferenciaMB preciosReferenciaMB;
    
    @PostConstruct
    public void init(){
        if(cargaGeneralView.getEmpresa() != null && cargaGeneralView.getEmpresa().getIdEmpresa() !=null){
            cargaInicialDeDatos();
        }
    }

    public CargaGeneralView getCargaGeneralView() {
        return cargaGeneralView;
    }

    public void setCargaGeneralView(CargaGeneralView cargaGeneralView) {
        this.cargaGeneralView = cargaGeneralView;
    }

    public PreciosReferenciaMB getPreciosReferenciaMB() {
        return preciosReferenciaMB;
    }

    public void setPreciosReferenciaMB(PreciosReferenciaMB preciosReferenciaMB) {
        this.preciosReferenciaMB = preciosReferenciaMB;
    }

    public Boolean getDeshabiliar() {
        return deshabiliar;
    }

    public void setDeshabiliar(Boolean deshabiliar) {
        this.deshabiliar = deshabiliar;
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
    
    public void guardarPreciosRef() {
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
            lstPreciosReferencia = proveedorEJB.findPreciosRefRubroEmpRubro(cargaGeneralView.getEmpresa(),
                    cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroInteres(),
                    cargaGeneralView.getIdAnho());

            msj = "Actualización exitosa";

            if (cargaGeneralView.getIdAnho().intValue() > 8) { // anho mayor de 2020
                //validación de ingreso de todos los item para el rubro de uniforme
                if (cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroUniforme().intValue() == 1) {
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

    public void impOfertaGlobal() {
        try {
            String idGestion = ""; //proveedorEJB.datosConfirmados(capacidadInst.getIdMuestraInteres().getIdMuestraInteres(),empresa.getIdEmpresa(),VarSession.getVariableSessionUsuario());

            String lugar = cargaGeneralView.getEmpresa().getIdMunicipio().getNombreMunicipio().concat(", ").concat(cargaGeneralView.getEmpresa().getIdMunicipio().getCodigoDepartamento().getNombreDepartamento());

            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            HashMap param = new HashMap();
            param.put("ubicacionImagenes", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
            param.put("pEscudo", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
            param.put("usuarioInsercion", VarSession.getVariableSessionUsuario());
            param.put("pLugar", lugar);
            param.put("pRubro", JsfUtil.getNombreRubroById(cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroInteres()));
            param.put("pIdRubro", cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroInteres().intValue());
            param.put("pCorreoPersona", cargaGeneralView.getEmpresa().getIdPersona().getEmail());
            param.put("pIdGestion", idGestion);

            List<OfertaGlobal> lstDatos = reportesEJB.getLstOfertaGlobal(cargaGeneralView.getEmpresa().getNumeroNit(), cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroInteres(), cargaGeneralView.getProcesoAdquisicion().getIdAnho().getIdAnho());
            lstDatos.get(0).setRubro(JsfUtil.getNombreRubroById(cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroInteres()));
            if (lstDatos.get(0).getDepartamento().contains("TODO EL PAIS")) {
                param.put("productor", Boolean.TRUE);
            } else {
                param.put("productor", Boolean.FALSE);
            }

            List<JasperPrint> jasperPrintList = new ArrayList();

            jasperPrintList.add(JasperFillManager.fillReport(
                    Reportes.getPathReporte("sv/gob/mined/apps/reportes/oferta" + File.separator + "rptOfertaGlobalProv" + cargaGeneralView.getProcesoAdquisicion().getIdAnho().getAnho() + ".jasper"),
                    param, new JRBeanCollectionDataSource(lstDatos)));

            String muni = VarSession.getNombreMunicipioSession();

            param.put("pLugar", cargaGeneralView.getEmpresa().getIdMunicipio().getCodigoDepartamento().getNombreDepartamento());

            if (cargaGeneralView.getEmpresa().getIdPersoneria().getIdPersoneria().intValue() == 1) {
                jasperPrintList.add(Reportes.getReporteAImprimir("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerProvNat" + cargaGeneralView.getProcesoAdquisicion().getIdAnho().getAnho(), param,
                        new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(cargaGeneralView.getEmpresa(), cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroInteres(), cargaGeneralView.getDetRubroMuestraInteres().getIdAnho().getIdAnho(), muni))));

            } else {
                jasperPrintList.add(Reportes.getReporteAImprimir("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerProvJur" + cargaGeneralView.getProcesoAdquisicion().getIdAnho().getAnho(), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(cargaGeneralView.getEmpresa(), cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroInteres(), cargaGeneralView.getDetRubroMuestraInteres().getIdAnho().getIdAnho(), muni))));

            }

            if (!jasperPrintList.isEmpty()) {
                Reportes.generarReporte(jasperPrintList, "oferta_global_" + cargaGeneralView.getEmpresa().getNumeroNit());
            }
        } catch (JRException | IOException ex) {
            Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void filtroProveedores() {
        lstPreciosReferencia.clear();
        deshabiliar = true;

        Map<String, Object> options = new HashMap();
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", false);
        options.put("contentHeight", 500);
        options.put("contentWidth", 750);

        PrimeFaces.current().dialog().openDynamic("/app/comunes/dialogos/proveedor/filtroProveedor", options, null);
    }
    
    private void cargaInicialDeDatos(){
        detalleProcesoAdq = JsfUtil.findDetalle(cargaGeneralView.getProcesoAdquisicion(), cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroInteres());
                
        preciosReferenciaMB.setEmpresa(cargaGeneralView.getEmpresa());
        preciosReferenciaMB.setDetalleProcesoAdq(detalleProcesoAdq);
        preciosReferenciaMB.setCapacidadInst(cargaGeneralView.getCapacidadInstPorRubro());
        preciosReferenciaMB.cargarDetalleCalificacion();
        
        cargarPrecioRef();
    }

    public void empSelecPrecioRef(SelectEvent event) {
        if (event.getObject() != null) {
            if (event.getObject() instanceof Empresa) {
                cargaGeneralView.setEmpresa((Empresa) event.getObject());
                cargaGeneralView.cargarDetalleCalificacion();
                
                cargaInicialDeDatos();
                
                if (cargaGeneralView.getCapacidadInstPorRubro() != null && cargaGeneralView.getCapacidadInstPorRubro().getIdCapInstRubro() != null) {
                    cargarPrecioRef();
                }
            } else {
                Logger.getLogger(ProveedorController.class
                        .getName()).log(Level.WARNING, "No se pudo convertir el objeto a la clase Empresa{0}", event.getObject());
            }
        }
    } 
    
    private void cargarPrecioRef() {
            lstItem = proveedorEJB.findItemProveedor(cargaGeneralView.getEmpresa(), detalleProcesoAdq);
            lstPreciosReferencia = proveedorEJB.findPreciosRefRubroEmpRubro(cargaGeneralView.getEmpresa(),
                    detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres(),
                    detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho());
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
                        /*preMaxRefG1 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(10), detalleProcesoAdq);
                        preMaxRefG2 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(11), detalleProcesoAdq);
                        preMaxRefG3 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(12), detalleProcesoAdq);
                        preMaxRefG4 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(13), detalleProcesoAdq);
                        preMaxRefG5 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(14), detalleProcesoAdq);
                        preMaxRefG6 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(15), detalleProcesoAdq);
                        preMaxRefG7 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(7), detalleProcesoAdq);
                        preMaxRefG8 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(8), detalleProcesoAdq);
                        preMaxRefG9 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(9), detalleProcesoAdq);
                        preMaxRefB1 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(16), detalleProcesoAdq);
                        preMaxRefB2 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(17), detalleProcesoAdq);*/
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
        PrimeFaces.current().ajax().update("frmPrincipal");
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
                current.setIdEmpresa(cargaGeneralView.getEmpresa());
                current.setIdMuestraInteres(cargaGeneralView.getDetRubroMuestraInteres());
                lstPreciosReferencia.add(current);
            } else {
                JsfUtil.mensajeInformacion("Debe de seleccionar un proceso de contratación.");
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
}
