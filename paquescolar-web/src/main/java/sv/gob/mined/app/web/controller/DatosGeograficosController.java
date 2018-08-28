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
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.DatosGeograficosEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.Anho;
import sv.gob.mined.paquescolar.model.Municipio;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;

/**
 *
 * @author misanchez
 */
@ManagedBean
@SessionScoped
public class DatosGeograficosController implements Serializable {

    private String codigoDepartamento;
    private BigDecimal idMunicipio;
    private Boolean mostrarMsj = false;
    private Boolean usuarioDepartamental = false;
    private Anho anho = new Anho();
    private ProcesoAdquisicion proceso = new ProcesoAdquisicion();
    private Municipio municipio = new Municipio();
    @EJB
    private DatosGeograficosEJB datosGeograficosEJB;
    @EJB
    private UtilEJB utilEJB;

    /**
     * Creates a new instance of DatosGeograficosController
     */
    public DatosGeograficosController() {
    }

    public Boolean getUsuarioDepartamental() {
        return usuarioDepartamental;
    }

    @PostConstruct
    public void init() {
        codigoDepartamento = VarSession.getDepartamentoUsuarioSession();
        if (codigoDepartamento != null) {
            if (VarSession.isCookie("municipio")) {
                idMunicipio = new BigDecimal(VarSession.getCookieValue("municipio"));
            } else {
                idMunicipio = utilEJB.getMunicipioPrimerByDepartamento(codigoDepartamento).getIdMunicipio();
                VarSession.crearCookie("municipio", idMunicipio.toString());
            }
            usuarioDepartamental = true;
        } else if (VarSession.isCookie("departamento")) {
            codigoDepartamento = VarSession.getCookieValue("departamento");
        }
        if (VarSession.isCookie("municipio") && idMunicipio == null) {
            idMunicipio = new BigDecimal(VarSession.getCookieValue("municipio"));
        }

        if (codigoDepartamento != null) {
            ((MenuController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                    getValue(FacesContext.getCurrentInstance().getELContext(), null, "menuController")).setDepartamentoUsuario(codigoDepartamento);
        }

        if (idMunicipio != null) {
            municipio = utilEJB.find(Municipio.class, idMunicipio);
        }
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

    public Boolean getMostrarMsj() {
        return mostrarMsj;
    }

    public String getUbicacion() {
        if (codigoDepartamento != null && idMunicipio != null) {
            return JsfUtil.getNombreDepartamentoByCodigo(codigoDepartamento) + ", " + municipio.getNombreMunicipio();
        } else {
            return "Sin ubicación definida";
        }
    }

    public void setMostrarMsj(Boolean mostrarMsj) {
        this.mostrarMsj = mostrarMsj;
    }

    public String getDepartamento() {
        return codigoDepartamento;
    }

    public void setDepartamento(String departamento) {
        if (departamento != null) {
            this.codigoDepartamento = departamento;
        }
    }

    public String getNombreMunicipio() {
        return municipio.getNombreMunicipio();
    }

    public BigDecimal getMunicipio() {
        return idMunicipio;
    }

    public void setMunicipio(BigDecimal municipio) {
        if (municipio != null) {
            this.idMunicipio = municipio;
        }
    }

    private void crearCookies() {
        VarSession.crearCookie("departamento", codigoDepartamento);
        VarSession.crearCookie("municipio", idMunicipio.toString());
    }

    public List<Municipio> getLstMunicipios() {
        if (codigoDepartamento == null) {
            return datosGeograficosEJB.getLstMunicipiosByDepartamento("00");
        } else {
            return datosGeograficosEJB.getLstMunicipiosByDepartamento(codigoDepartamento);
        }
    }

    public void filtroParametros() {
        mostrarMsj = false;
        Map<String, Object> options = new HashMap();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 136);
        options.put("contentWidth", 510);
        options.put("closable", false);

        PrimeFaces.current().dialog().openDynamic("/app/comunes/filtroParamUbicacion", options, null);
    }

    public void cerrarFiltroPro() {
        cerrarFiltroPro(false);
    }

    private void cerrarFiltroPro(Boolean exito) {
        if (!exito) {
            if (VarSession.isCookie("departamento")) {
                codigoDepartamento = VarSession.getCookieValue("departamento");
            }
            if (VarSession.isCookie("municipio")) {
                idMunicipio = new BigDecimal(VarSession.getCookieValue("municipio"));
            }
        }
    }

    public void cerrarDlgParamUbicacion() throws IOException {
        String msj = "Debe Seleccionar: ";
        if (codigoDepartamento == null || idMunicipio == null) {
            if (!msj.replace("Debe Seleccionar: ", "").isEmpty()) {
                msj += "<br/>";
            }
            msj += "Un departamento y municipio";
        }

        if (!msj.replace("Debe Seleccionar: ", "").isEmpty()) {
            JsfUtil.mensajeAlerta(msj);
        } else {
            municipio = utilEJB.find(Municipio.class, idMunicipio);
            crearCookies();
            cerrarFiltroPro(true);
            PrimeFaces.current().executeScript("PF('dlgParamUbicacion').hide()");
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public void updateMunicipio() {
        idMunicipio = null;
    }
}
