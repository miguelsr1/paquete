package sv.gob.mined.app.web.controller.proveedor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.CreditosEJB;
import sv.gob.mined.paquescolar.ejb.DatosGeograficosEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.Canton;
import sv.gob.mined.paquescolar.model.CapaDistribucionAcre;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.Departamento;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;
import sv.gob.mined.paquescolar.model.Municipio;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class DatosGeneralesView implements Serializable {

    private Boolean deshabiliar = false;
    private Boolean showUpdateEmpresa;
    private Boolean personaNatural;
    private Boolean mismaDireccion;
    private Boolean rubroUniforme;
    private Boolean inscritoIva = false;
    private Boolean deseaInscribirseIva = false;
    private Boolean showFoto = false;

    private String fotoProveedor;
    private String idCanton;
    private String codEntFinanciera;
    private String idCantonLocal;
    private String tapEmpresa;
    private String tapPersona;
    private String fileName = "fotoProveedores/profile.png";
    private String codigoDepartamento;
    private String codigoDepartamentoLocal;
    private String codigoDepartamentoCalificado;

    private BigDecimal idMunicipio;
    private BigDecimal idMunicipioLocal;

    private DetalleProcesoAdq detalleProcesoAdq;
    private CapaDistribucionAcre departamentoCalif;
    private CapaInstPorRubro capacidadInst = new CapaInstPorRubro();

    @EJB
    private DatosGeograficosEJB datosGeograficosEJB;
    @EJB
    private UtilEJB utilEJB;
    @EJB
    private CreditosEJB creditosEJB;
    @EJB
    public ProveedorEJB proveedorEJB;

    @ManagedProperty(value = "#{cargaGeneralView}")
    private CargaGeneralView cargaGeneralView;

    @PostConstruct
    public void init() {
        cargarDetalleCalificacion();
    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">
    
    public String getTapEmpresa() {
        return tapEmpresa;
    }

    public String getTapPersona() {
        return tapPersona;
    }
    
    public CargaGeneralView getCargaGeneralView() {
        return cargaGeneralView;
    }

    public void setCargaGeneralView(CargaGeneralView cargaGeneralView) {
        this.cargaGeneralView = cargaGeneralView;
    }

    public Boolean getShowUpdateEmpresa() {
        return showUpdateEmpresa;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String filename) {
        this.fileName = filename;
    }

    public Boolean getMismaDireccion() {
        return mismaDireccion;
    }

    public void setMismaDireccion(Boolean mismaDireccion) {
        this.mismaDireccion = mismaDireccion;
    }

    public List<Municipio> getLstMunicipios() {
        return datosGeograficosEJB.getLstMunicipiosByDepartamento(codigoDepartamento);
    }

    public List<Municipio> getLstMunicipiosLocal() {
        return datosGeograficosEJB.getLstMunicipiosByDepartamento(codigoDepartamentoLocal);
    }

    public List<EntidadFinanciera> getLstEntidades() {
        return creditosEJB.findEntidadFinancieraEntities((short) 1);
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getCodigoDepartamentoLocal() {
        return codigoDepartamentoLocal;
    }

    public void setCodigoDepartamentoLocal(String codigoDepartamentoLocal) {
        this.codigoDepartamentoLocal = codigoDepartamentoLocal;
    }

    public String getCodigoDepartamentoCalificado() {
        return codigoDepartamentoCalificado;
    }

    public void setCodigoDepartamentoCalificado(String codigoDepartamentoCalificado) {
        this.codigoDepartamentoCalificado = codigoDepartamentoCalificado;
    }

    public String getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(String idCanton) {
        this.idCanton = idCanton;
    }

    public BigDecimal getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(BigDecimal idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public List<Canton> getLstCantones() {
        return datosGeograficosEJB.getLstCantonByMunicipio(idMunicipio);
    }

    public List<Canton> getLstCantonesLocal() {
        return datosGeograficosEJB.getLstCantonByMunicipio(idMunicipioLocal);
    }

    public String getCodEntFinanciera() {
        return codEntFinanciera;
    }

    public void setCodEntFinanciera(String entidadFinanciera) {
        this.codEntFinanciera = entidadFinanciera;
    }

    public Boolean getDeshabiliar() {
        return deshabiliar;
    }

    public CapaInstPorRubro getCapacidadInst() {
        if (capacidadInst == null) {
            capacidadInst = new CapaInstPorRubro();
        }
        return capacidadInst;
    }

    public Boolean getShowFoto() {
        return showFoto;
    }

    public void setShowFoto(Boolean showFoto) {
        this.showFoto = showFoto;
    }

    public Boolean getInscritoIva() {
        return inscritoIva;
    }

    public void setInscritoIva(Boolean inscritoIva) {
        this.inscritoIva = inscritoIva;
    }

    public Boolean getRubroUniforme() {
        return rubroUniforme;
    }

    public void setRubroUniforme(Boolean rubroUniforme) {
        this.rubroUniforme = rubroUniforme;
    }

    public Boolean getDeseaInscribirseIva() {
        return deseaInscribirseIva;
    }

    public void setDeseaInscribirseIva(Boolean deseaInscribirseIva) {
        this.deseaInscribirseIva = deseaInscribirseIva;
    }

    public DetalleProcesoAdq getDetalleProcesoAdq() {
        return detalleProcesoAdq;
    }

    public void setDetalleProcesoAdq(DetalleProcesoAdq detalleProcesoAdq) {
        this.detalleProcesoAdq = detalleProcesoAdq;
    }

    public String getIdCantonLocal() {
        return idCantonLocal;
    }

    public void setIdCantonLocal(String idCantonLocal) {
        if (idCantonLocal != null) {
            this.idCantonLocal = idCantonLocal;
        }
    }

    public BigDecimal getIdMunicipioLocal() {
        return idMunicipioLocal;
    }

    public void setIdMunicipioLocal(BigDecimal idMunicipioLocal) {
        this.idMunicipioLocal = idMunicipioLocal;
    }

    public Boolean getPersonaNatural() {
        return personaNatural;
    }

    public void setPersonaNatural(Boolean personaNatural) {
        this.personaNatural = personaNatural;
    }

    // </editor-fold>
    public void empresaSeleccionada(SelectEvent event) {
        if (event.getObject() != null) {
            if (event.getObject() instanceof Empresa) {
                cargaGeneralView.setEmpresa((Empresa) event.getObject());

                if (cargaGeneralView.getUrl().contains("DatosGenerales")) {
                    idMunicipio = cargaGeneralView.getEmpresa().getIdPersona().getIdMunicipio().getIdMunicipio();
                    codigoDepartamento = cargaGeneralView.getEmpresa().getIdPersona().getIdMunicipio().getCodigoDepartamento().getCodigoDepartamento();
                }

                if (cargaGeneralView.getEmpresa().getIdMunicipio() == null) {
                    cargaGeneralView.getEmpresa().setIdMunicipio(utilEJB.find(Municipio.class, BigDecimal.ONE));

                    if (cargaGeneralView.getEmpresa().getIdPersona().getIdMunicipio() == null) {
                        cargaGeneralView.getEmpresa().getIdPersona().setIdMunicipio(utilEJB.find(Municipio.class, BigDecimal.ONE));
                    }
                }
                VarSession.setVariableSession("idEmpresa", cargaGeneralView.getEmpresa().getIdEmpresa());
                cargaGeneralView.cargarDetalleCalificacion();
                cargarDetalleCalificacion();
                showUpdateEmpresa = ((Integer) VarSession.getVariableSession("idTipoUsuario") == 1);
            }
        } else {
            deshabiliar = false;
            JsfUtil.mensajeAlerta("Debe de seleccionar una empresa");
        }
    }

    private void cargarDetalleCalificacion() {
        capacidadInst = cargaGeneralView.getCapacidadInstPorRubro();
        if (capacidadInst == null) {
            JsfUtil.mensajeAlerta("No se han cargado los datos de este proveedor para el proceso de contratación del año " + cargaGeneralView.getProcesoAdquisicion().getIdAnho());
        } else {
            detalleProcesoAdq = JsfUtil.findDetalleByRubroAndAnho(cargaGeneralView.getProcesoAdquisicion(),
                    capacidadInst.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres(),
                    capacidadInst.getIdMuestraInteres().getIdAnho().getIdAnho());
            departamentoCalif = proveedorEJB.findDetProveedor(capacidadInst.getIdMuestraInteres(), null, CapaDistribucionAcre.class);
            personaNatural = (cargaGeneralView.getEmpresa().getIdPersoneria().getIdPersoneria().intValue() == 1);
            if (personaNatural) {
                mismaDireccion = (cargaGeneralView.getEmpresa().getIdMunicipio().getIdMunicipio().intValue() == cargaGeneralView.getEmpresa().getIdPersona().getIdMunicipio().getIdMunicipio().intValue());
            }

            if (departamentoCalif == null || departamentoCalif.getCodigoDepartamento() == null) {
                JsfUtil.mensajeAlerta("Este proveedor no posee departamento de calificación " + cargaGeneralView.getProcesoAdquisicion().getIdAnho().getAnho());
            } else {
                codigoDepartamentoCalificado = departamentoCalif.getCodigoDepartamento().getCodigoDepartamento();

                idMunicipioLocal = cargaGeneralView.getEmpresa().getIdMunicipio().getIdMunicipio();
                codigoDepartamentoLocal = cargaGeneralView.getEmpresa().getIdMunicipio().getCodigoDepartamento().getCodigoDepartamento();

                rubroUniforme = (departamentoCalif.getIdMuestraInteres().getIdRubroInteres().getIdRubroUniforme().intValue() == 1);

                if (rubroUniforme) {
                    idCanton = cargaGeneralView.getEmpresa().getIdPersona().getCodigoCanton();
                    idCantonLocal = cargaGeneralView.getEmpresa().getCodigoCanton();
                }

                deshabiliar = false;
                if (cargaGeneralView.getEmpresa().getIdPersona().getUrlImagen() == null) {
                    fileName = "fotoProveedores/profile.png";
                } else {
                    fileName = "fotoProveedores/" + cargaGeneralView.getEmpresa().getIdPersona().getUrlImagen();
                }
            }
        }
        /*}
        }*/

        if (cargaGeneralView.getEmpresa() == null) {
            tapEmpresa = "";
            tapPersona = "";
        } else if (cargaGeneralView.getEmpresa().getIdPersoneria().getIdPersoneria() == null) {
            tapPersona = "";
            tapEmpresa = "";
        } else if (cargaGeneralView.getEmpresa().getIdPersoneria().getIdPersoneria().intValue() == 2) {
            tapEmpresa = "Empresa";
            tapPersona = "Representante Legal";
        } else {
            tapEmpresa = "";
            tapPersona = "Proveedor";
        }
    }

    public void guardarDatosGenerales() {
        if (showUpdateEmpresa) {
            if (cargaGeneralView.getEmpresa().getIdPersoneria().getIdPersoneria().intValue() == 1) {
                cargaGeneralView.getEmpresa().setRazonSocial(cargaGeneralView.getEmpresa().getIdPersona().getNombreCompletoProveedor());
                cargaGeneralView.getEmpresa().setTelefonos(cargaGeneralView.getEmpresa().getIdPersona().getNumeroTelefono());
                cargaGeneralView.getEmpresa().setNumeroCelular(cargaGeneralView.getEmpresa().getIdPersona().getNumeroCelular());
                cargaGeneralView.getEmpresa().setNumeroNit(cargaGeneralView.getEmpresa().getIdPersona().getNumeroNit());

                if (mismaDireccion) {
                    idMunicipioLocal = idMunicipio;
                    idCantonLocal = idCanton;
                    cargaGeneralView.getEmpresa().setDireccionCompleta(cargaGeneralView.getEmpresa().getIdPersona().getDomicilio());

                    cargaGeneralView.getEmpresa().getIdPersona().setIdMunicipio(new Municipio(idMunicipio));
                    cargaGeneralView.getEmpresa().setIdMunicipio(new Municipio(idMunicipioLocal));
                    if (rubroUniforme) {
                        cargaGeneralView.getEmpresa().setCodigoCanton(idCantonLocal);
                        cargaGeneralView.getEmpresa().getIdPersona().setCodigoCanton(idCanton);
                    } else {
                        cargaGeneralView.getEmpresa().setCodigoCanton(null);
                        cargaGeneralView.getEmpresa().getIdPersona().setCodigoCanton(null);
                    }
                } else {
                    cargaGeneralView.getEmpresa().getIdPersona().setIdMunicipio(new Municipio(idMunicipio));
                    cargaGeneralView.getEmpresa().getIdPersona().setCodigoCanton(idCanton);
                }

            } else if (rubroUniforme) {
                cargaGeneralView.getEmpresa().setCodigoCanton(idCantonLocal);
                cargaGeneralView.getEmpresa().setIdMunicipio(new Municipio(idMunicipioLocal));
                cargaGeneralView.getEmpresa().getIdPersona().setIdMunicipio(new Municipio(idMunicipio));
                cargaGeneralView.getEmpresa().getIdPersona().setCodigoCanton(idCanton);
            }

            if (rubroUniforme) {
                cargaGeneralView.getEmpresa().setEsContribuyente(inscritoIva ? (short) 1 : 0);
                cargaGeneralView.getEmpresa().setDeseaInscribirse(deseaInscribirseIva ? (short) 1 : 0);
            }

            proveedorEJB.guardar(cargaGeneralView.getEmpresa());

            departamentoCalif.setCodigoDepartamento(utilEJB.find(Departamento.class, codigoDepartamentoCalificado));

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

    public void filtroProveedores() {
        cargaGeneralView.setEmpresa(new Empresa());
        capacidadInst = new CapaInstPorRubro();

        fileName = "fotoProveedores/profile.png";
        showFoto = true;
        deshabiliar = true;

        Map<String, Object> options = new HashMap();
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", false);
        options.put("contentHeight", 500);
        options.put("contentWidth", 750);
        options.put("hideEffect", "fade");
        options.put("showEffect", "fade");

        PrimeFaces.current().dialog().openDynamic("/app/comunes/dialogos/proveedor/filtroProveedor", options, null);
    }

    public String getNombreRubroProveedor() {
        if (capacidadInst != null && capacidadInst.getIdMuestraInteres() != null) {
            return JsfUtil.getNombreRubroById(capacidadInst.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres());
        } else {
            return "";
        }
    }

    public void dlgFotografia() {
        if (cargaGeneralView.getEmpresa() == null || cargaGeneralView.getEmpresa().getIdEmpresa() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un empresa");
        } else {
            deshabiliar = true;
            Map<String, Object> options = new HashMap();
            options.put("modal", true);
            options.put("draggable", false);
            options.put("resizable", false);
            options.put("contentHeight", 400);
            options.put("contentWidth", 554);
            VarSession.setVariableSession("nitEmpresa", cargaGeneralView.getEmpresa().getNumeroNit());
            PrimeFaces.current().dialog().openDynamic("/app/comunes/filtroFotoProveedor", options, null);
        }
    }

    public void updateFrm(SelectEvent event) {
        fotoProveedor = (String) event.getObject();
        if (!fotoProveedor.contains("/")) {
            showFoto = false;
        } else {
            cargaGeneralView.getEmpresa().getIdPersona().setUrlImagen(fotoProveedor);
        }
    }
}
