package sv.gob.mined.app.web.controller.proveedor;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import sv.gob.mined.app.web.controller.pagoprov.ProveedorController;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.DatosGeograficosEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;
import sv.gob.mined.paquescolar.model.CapaDistribucionAcre;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.DisMunicipioInteres;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.Municipio;
import sv.gob.mined.paquescolar.model.pojos.OfertaGlobal;
import sv.gob.mined.paquescolar.model.pojos.proveedor.MunicipioDto;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class MunicipioInteresView implements Serializable {
    
    private Boolean deshabiliar = false;

    private CapaInstPorRubro capacidadInst = new CapaInstPorRubro();
    private CapaDistribucionAcre departamentoCalif = new CapaDistribucionAcre();

    private List<MunicipioDto> lstMunSource = new ArrayList();
    private List<MunicipioDto> lstMunTarget = new ArrayList();
    private DualListModel<MunicipioDto> lstMunicipiosInteres = new DualListModel();

    @EJB
    private ReportesEJB reportesEJB;
    @EJB
    private DatosGeograficosEJB datosGeograficosEJB;
    @EJB
    public ProveedorEJB proveedorEJB;

    @ManagedProperty(value = "#{cargaGeneralView}")
    private CargaGeneralView cargaGeneralView;

    @PostConstruct
    public void init() {
        if (cargaGeneralView.getEmpresa().getIdEmpresa() != null) {
            if (cargaGeneralView.getUrl().contains("DatosGenerales")) {

            } else if (cargaGeneralView.getUrl().contains("MunicipioInteres")) {
                cargarMunInteres();
            } else if (cargaGeneralView.getUrl().contains("PreciosReferencia")) {

            }
        }
    }

    public Boolean getDeshabiliar() {
        return deshabiliar;
    }

    public void setDeshabiliar(Boolean deshabiliar) {
        this.deshabiliar = deshabiliar;
    }

    public CargaGeneralView getCargaGeneralView() {
        return cargaGeneralView;
    }

    public void setCargaGeneralView(CargaGeneralView cargaGeneralView) {
        this.cargaGeneralView = cargaGeneralView;
    }

    public DualListModel<MunicipioDto> getLstMunicipiosInteres() {
        return lstMunicipiosInteres;
    }

    public void setLstMunicipiosInteres(DualListModel<MunicipioDto> lstMunicipiosInteres) {
        this.lstMunicipiosInteres = lstMunicipiosInteres;
    }

    public List<MunicipioDto> getLstMunSource() {
        return lstMunSource;
    }

    public void setLstMunSource(List<MunicipioDto> lstMunSource) {
        this.lstMunSource = lstMunSource;
    }

    public List<MunicipioDto> getLstMunTarget() {
        return lstMunTarget;
    }

    public void setLstMunTarget(List<MunicipioDto> lstMunTarget) {
        this.lstMunTarget = lstMunTarget;
    }

    public CapaDistribucionAcre getDepartamentoCalif() {
        return departamentoCalif;
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

    public void impOfertaGlobal() {
        try {
            String idGestion = "";
            String lugar = cargaGeneralView.getEmpresa().getIdMunicipio().getNombreMunicipio().concat(", ").concat(cargaGeneralView.getEmpresa().getIdMunicipio().getCodigoDepartamento().getNombreDepartamento());

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

            List<OfertaGlobal> lstDatos = reportesEJB.getLstOfertaGlobal(cargaGeneralView.getEmpresa().getNumeroNit(), 
                    cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroInteres(), cargaGeneralView.getProcesoAdquisicion().getIdAnho().getIdAnho());
            lstDatos.get(0).setRubro(JsfUtil.getNombreRubroById(capacidadInst.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres()));
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
                jasperPrintList.add(Reportes.getReporteAImprimir("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerProvNat" + cargaGeneralView.getProcesoAdquisicion().getIdAnho().getAnho(), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(cargaGeneralView.getEmpresa(), cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroInteres(), cargaGeneralView.getProcesoAdquisicion().getIdAnho().getIdAnho(), muni))));

            } else {
                jasperPrintList.add(Reportes.getReporteAImprimir("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerProvJur" + cargaGeneralView.getProcesoAdquisicion().getIdAnho().getAnho(), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(cargaGeneralView.getEmpresa(), cargaGeneralView.getDetRubroMuestraInteres().getIdRubroInteres().getIdRubroInteres(), cargaGeneralView.getProcesoAdquisicion().getIdAnho().getIdAnho(), muni))));

            }

            if (!jasperPrintList.isEmpty()) {
                Reportes.generarReporte(jasperPrintList, "oferta_global_" + cargaGeneralView.getEmpresa().getNumeroNit());
            }
        } catch (JRException | IOException ex) {
            Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void filtroProveedores() {
        cargaGeneralView.setEmpresa(new Empresa());
        capacidadInst = new CapaInstPorRubro();

        lstMunSource.clear();
        lstMunTarget.clear();
        cargaGeneralView.setFileName("fotoProveedores/profile.png");

        Map<String, Object> options = new HashMap();
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", false);
        options.put("contentHeight", 500);
        options.put("contentWidth", 750);

        PrimeFaces.current().dialog().openDynamic("/app/comunes/dialogos/proveedor/filtroProveedor", options, null);
    }

    public void empSelecMuniInteres(SelectEvent event) {
        if (event.getObject() != null) {
            if (event.getObject() instanceof Empresa) {
                cargaGeneralView.setEmpresa((Empresa) event.getObject());
                VarSession.setVariableSession("idEmpresa", cargaGeneralView.getEmpresa().getIdEmpresa());
                cargaGeneralView.cargarDetalleCalificacion();
                cargarMunInteres();
            } else {
                Logger.getLogger(ProveedorController.class
                        .getName()).log(Level.INFO, "No se pudo convertir el objeto a la clase Empresa{0}", event.getObject());
            }
        } else {
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

    private void cargarDetalleCalificacion() {
        departamentoCalif = cargaGeneralView.getCapaDistribucionAcre();
        capacidadInst = cargaGeneralView.getCapacidadInstPorRubro();

        if (departamentoCalif == null || departamentoCalif.getCodigoDepartamento() == null) {
            JsfUtil.mensajeAlerta("Este proveedor no posee departamento de calificación " + cargaGeneralView.getProcesoAdquisicion().getIdAnho().getAnho());
        } else {
            if (cargaGeneralView.getEmpresa().getIdPersona().getUrlImagen() == null) {
                cargaGeneralView.setFileName("fotoProveedores/profile.png");
            } else {
                cargaGeneralView.setFileName("fotoProveedores/" + cargaGeneralView.getEmpresa().getIdPersona().getUrlImagen());
            }
        }
    }
}
