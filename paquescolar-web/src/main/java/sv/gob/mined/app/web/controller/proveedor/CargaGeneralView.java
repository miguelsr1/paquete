package sv.gob.mined.app.web.controller.proveedor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.model.CapaDistribucionAcre;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.DetCapaSegunRubro;
import sv.gob.mined.paquescolar.model.DetRubroMuestraInteres;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;

/**
 *
 * @author misanchez
 */
@ManagedBean
@SessionScoped
public class CargaGeneralView extends RecuperarProcesoUtil implements Serializable {

    private Boolean deshabiliar = false;
    private Boolean showFoto;

    public String url;
    public String fileName;
    public String rubroProveedor = "";

    private BigDecimal idAnho;

    private Empresa empresa = new Empresa();
    private ProcesoAdquisicion proceso;
    private DetRubroMuestraInteres detRubroMuestraInteres;

    @EJB
    public ProveedorEJB proveedorEJB;

    @PostConstruct
    public void ini() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        url = req.getRequestURL().toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BigDecimal getIdAnho() {
        return idAnho;
    }

    public CapaInstPorRubro getCapacidadInstPorRubro() {
        return detRubroMuestraInteres != null ? proveedorEJB.findDetProveedor(detRubroMuestraInteres, proceso.getIdProcesoAdq(), CapaInstPorRubro.class) : null;
    }

    public CapaDistribucionAcre getCapaDistribucionAcre() {
        return detRubroMuestraInteres != null ? proveedorEJB.findDetProveedor(detRubroMuestraInteres, proceso.getIdProcesoAdq(), CapaDistribucionAcre.class) : null;
    }

    public DetCapaSegunRubro getDetCapaSegunRubro() {
        return detRubroMuestraInteres != null ? proveedorEJB.findDetProveedor(detRubroMuestraInteres, proceso.getIdProcesoAdq(), DetCapaSegunRubro.class) : null;
    }

    public DetRubroMuestraInteres getDetRubroMuestraInteres() {
        return detRubroMuestraInteres;
    }

    public Boolean getDeshabiliar() {
        return deshabiliar;
    }

    public void setDeshabiliar(Boolean deshabiliar) {
        this.deshabiliar = deshabiliar;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Boolean getShowFoto() {
        return showFoto;
    }

    public void setShowFoto(Boolean showFoto) {
        this.showFoto = showFoto;
    }

    public String getNombreRubroProveedor() {
        return rubroProveedor;
    }

    public ProcesoAdquisicion getProcesoAdquisicion() {
        return super.getRecuperarProceso().getProcesoAdquisicion();
    }

    public void dlgFotografia() {
        if (getEmpresa() == null || getEmpresa().getIdEmpresa() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un empresa");
        } else {
            deshabiliar = true;
            Map<String, Object> options = new HashMap();
            options.put("modal", true);
            options.put("draggable", false);
            options.put("resizable", false);
            options.put("contentHeight", 400);
            options.put("contentWidth", 554);
            VarSession.setVariableSession("nitEmpresa", getEmpresa().getNumeroNit());
            PrimeFaces.current().dialog().openDynamic("/app/comunes/filtroFotoProveedor", options, null);
        }
    }

    public void cargarDetalleCalificacion() {
        this.proceso = super.getRecuperarProceso().getProcesoAdquisicion();
        idAnho = proceso.getIdAnho().getIdAnho();
        if (proceso.getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe seleccionar un proceso de contratación");
        } else {
            detRubroMuestraInteres = proveedorEJB.findDetRubroByAnhoAndRubro(idAnho, getEmpresa().getIdEmpresa());
            if (detRubroMuestraInteres == null) {
                JsfUtil.mensajeAlerta("No se han cargado los datos de este proveedor para el proceso de contratación del año " + proceso.getIdAnho().getAnho());
            } else {
                rubroProveedor = JsfUtil.getNombreRubroById(detRubroMuestraInteres.getIdRubroInteres().getIdRubroInteres());
            }
        }
    }
}