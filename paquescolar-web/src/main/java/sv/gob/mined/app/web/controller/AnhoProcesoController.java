/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.Anho;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.RubrosAmostrarInteres;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@ManagedBean
@SessionScoped
public class AnhoProcesoController implements Serializable {

    private String codigoEntidad = "";
    private Anho anho = new Anho();
    private ProcesoAdquisicion proceso = new ProcesoAdquisicion();
    private BigDecimal rubro = BigDecimal.ZERO;
    private DetalleProcesoAdq detalleProcesoAdq = new DetalleProcesoAdq();
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();
    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;
    @EJB
    private UtilEJB utilEJB;

    /**
     * Creates a new instance of AnhoProcesoController
     */
    public AnhoProcesoController() {
    }

    @PostConstruct
    public void init() {
        Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        if (requestCookieMap.containsKey("anho")) {
            anho = utilEJB.find(Anho.class, new BigDecimal(((Cookie) requestCookieMap.get("anho")).getValue()));
        } else {
            anho = new Anho(BigDecimal.ONE);
        }

        if (requestCookieMap.containsKey("proceso")) {
            proceso = utilEJB.find(ProcesoAdquisicion.class, Integer.parseInt(((Cookie) requestCookieMap.get("proceso")).getValue()));
        }

        if (requestCookieMap.containsKey("rubro")) {
            rubro = new BigDecimal(((Cookie) requestCookieMap.get("rubro")).getValue());
        }

        if ((proceso != null || proceso.getIdProcesoAdq() != null) && (rubro != null || rubro != BigDecimal.ZERO)) {
            findDetalleProcesoAdq();
        }
    }

    public String getAnhoProceso() {
        if (anho != null && proceso != null && anho.getIdAnho() != null && proceso.getIdProcesoAdq() != null) {
            return anho.getAnho() + " :: " + proceso.getDescripcionProcesoAdq();
        } else {
            return "Año y proceso sin definir";
        }
    }

    public List<ProcesoAdquisicion> getLstProcesoAdquisicion() {
        if (anho == null || anho.getIdAnho() == null) {
            return anhoProcesoEJB.getLstProcesoAdquisicionByAnho(BigDecimal.ONE);
        } else {
            return anhoProcesoEJB.getLstProcesoAdquisicionByAnho(anho.getIdAnho());
        }
    }

    public List<RubrosAmostrarInteres> getLstRubros() {
        if (proceso == null || proceso.getIdProcesoAdq() == null) {
            return anhoProcesoEJB.getLstRubros();
        } else {
            return anhoProcesoEJB.getLstRubros(proceso);
        }
    }

    public Anho getAnho() {
        return anho;
    }

    public void setAnho(Anho anho) {
        this.anho = anho;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public ProcesoAdquisicion getProceso() {
        return proceso;
    }

    public void setProceso(ProcesoAdquisicion proceso) {
        this.proceso = proceso;
    }

    public BigDecimal getRubro() {
        if (VarSession.isCookie("rubro")) {
            rubro = new BigDecimal((VarSession.getCookieValue("rubro")));
        }

        return rubro;
    }

    public void setRubro(BigDecimal rubro) {
        this.rubro = rubro;
    }

    public DetalleProcesoAdq getDetalleProcesoAdq() {
        return detalleProcesoAdq;
    }

    public void setDetalleProcesoAdq(DetalleProcesoAdq detalleProcesoAdq) {
        this.detalleProcesoAdq = detalleProcesoAdq;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
    }

    public void setEntidadEducativa(VwCatalogoEntidadEducativa entidadEducativa) {
        this.entidadEducativa = entidadEducativa;
    }

    public void findDetalleProcesoAdq() {
        detalleProcesoAdq = anhoProcesoEJB.getDetProcesoAdq(proceso, rubro);
    }

    public String limpiarVariables() {
        VarSession.removeVariableSession("idEmpresa");
        return "inicial";
    }

    public void cerrarDlgParamAnhoProceso() throws IOException {
        anho = getAnho();
        proceso = getProceso();
        String msj = "Debe Seleccionar: ";
        if (anho == null || anho.getIdAnho() == null || proceso == null || proceso.getIdProcesoAdq() == null) {
            msj = "El anho y un proceso";
        }
        if (!msj.replace("Debe Seleccionar: ", "").isEmpty()) {
            JsfUtil.mensajeAlerta(msj);
        } else {
            crearCookies();
            cerrarFiltroPro(true);
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());

        //recuperarProceso.recuperarProcesoAdq();
    }

    private void crearCookies() {
        VarSession.crearCookie("anho", anho.getIdAnho().toString());
        VarSession.crearCookie("proceso", proceso.getIdProcesoAdq().toString());
    }

    public void cerrarFiltroPro() {
        cerrarFiltroPro(false);
    }

    private void cerrarFiltroPro(Boolean exito) {
        if (!exito) {
            if (VarSession.isCookie("anho")) {
                anho = utilEJB.find(Anho.class, new BigDecimal(VarSession.getCookieValue("anho")));
            }
            if (VarSession.isCookie("proceso")) {
                proceso = utilEJB.find(ProcesoAdquisicion.class, new Integer(VarSession.getCookieValue("proceso")));
            }
            setAnho(anho);
            setProceso(proceso);
        }
    }

    public void filtroParametros() {
        Map<String, Object> options = new HashMap();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 136);
        options.put("contentWidth", 510);
        options.put("closable", false);

        PrimeFaces.current().dialog().openDynamic("/app/comunes/filtroParamAnhoProceso", options, null);
    }
}
