/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class ParametrosMB implements Serializable {

    private Boolean mostrarMsj = false;
    private Boolean usuarioDepartamental = false;
    private Boolean validacionGeneral = true;
    private String codigoDepartamento;
    private String anhoProceso = "";
    private String ubicacion = "";
    private Integer idProcesoAdq;
    private BigDecimal idMunicipio;
    private BigDecimal idAnho = BigDecimal.ZERO;
    private BigDecimal idRubro = BigDecimal.ZERO;

    private Anho anho = new Anho();
    private ProcesoAdquisicion proceso = new ProcesoAdquisicion();
    private Municipio municipio = new Municipio();

    @EJB
    private UtilEJB utilEJB;
    @EJB
    private DatosGeograficosEJB datosGeograficosEJB;
    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;

    @PostConstruct
    public void init() {
        Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();

        if (requestCookieMap.containsKey("anho")) {
            idAnho = new BigDecimal(((Cookie) requestCookieMap.get("anho")).getValue());
            validacionGeneral = false;
        } else {
            idAnho = BigDecimal.ONE;
        }

        if (requestCookieMap.containsKey("proceso")) {
            idProcesoAdq = Integer.parseInt(((Cookie) requestCookieMap.get("proceso")).getValue());
            validacionGeneral = false;
        }

        if (requestCookieMap.containsKey("rubro")) {
            idRubro = new BigDecimal(((Cookie) requestCookieMap.get("rubro")).getValue());
            validacionGeneral = false;
        }

        codigoDepartamento = VarSession.getDepartamentoUsuarioSession();

        if (codigoDepartamento != null) {
            if (VarSession.isCookie("municipio")) {
                idMunicipio = new BigDecimal(VarSession.getCookieValue("municipio"));
                validacionGeneral = false;
            } else {
                idMunicipio = utilEJB.getMunicipioPrimerByDepartamento(codigoDepartamento).getIdMunicipio();
                VarSession.crearCookie("municipio", idMunicipio.toString());
            }
            usuarioDepartamental = true;
        } else if (VarSession.isCookie("departamento")) {
            codigoDepartamento = VarSession.getCookieValue("departamento");
            validacionGeneral = false;
        }
        if (VarSession.isCookie("municipio") && idMunicipio == null) {
            validacionGeneral = false;
            idMunicipio = new BigDecimal(VarSession.getCookieValue("municipio"));
        }

    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">    
    public BigDecimal getIdAnho() {
        return idAnho;
    }

    public void setIdAnho(BigDecimal idAnho) {
        if (idAnho != null) {
            this.idAnho = idAnho;
        }
    }

    public String ubicacion() {
        if (codigoDepartamento != null && idMunicipio != null) {
            return ubicacion;
        } else {
            return "Sin ubicación definida";
        }
    }

    public Integer getIdProcesoAdq() {
        return idProcesoAdq;
    }

    public void setIdProcesoAdq(Integer idProcesoAdq) {
        this.idProcesoAdq = idProcesoAdq;
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
        if (idMunicipio != null) {
            this.idMunicipio = idMunicipio;
        }
    }

    public Boolean getMostrarMsj() {
        return mostrarMsj;
    }

    public void setMostrarMsj(Boolean mostrarMsj) {
        this.mostrarMsj = mostrarMsj;
    }

    public Boolean getUsuarioDepartamental() {
        return usuarioDepartamental;
    }

    public void setUsuarioDepartamental(Boolean usuarioDepartamental) {
        this.usuarioDepartamental = usuarioDepartamental;
    }

    public BigDecimal getRubro() {
        if (VarSession.isCookie("rubro")) {
            idRubro = new BigDecimal((VarSession.getCookieValue("rubro")));
        }
        return idRubro;
    }

    public void setRubro(BigDecimal rubro) {
        this.idRubro = rubro;
    }

    public Boolean getValidacionGeneral() {
        return validacionGeneral;
    }

    public String getUbicacion() {
        if (codigoDepartamento != null && idMunicipio != null) {
            return JsfUtil.getNombreDepartamentoByCodigo(codigoDepartamento) + ", " + municipio.getNombreMunicipio();
        } else {
            return "Sin ubicación definida";
        }
    }

    public String getAnhoProceso() {
        if (idAnho != null && idProcesoAdq != null) {
            return anhoProceso;
        } else {
            return "Año y proceso sin definir";
        }
    }

    public List<Municipio> getLstMunicipios() {
        if (codigoDepartamento == null) {
            return datosGeograficosEJB.getLstMunicipiosByDepartamento("00");
        } else {
            return datosGeograficosEJB.getLstMunicipiosByDepartamento(codigoDepartamento);
        }
    }

    public List<ProcesoAdquisicion> getLstProcesoAdquisicion() {
        return anhoProcesoEJB.getLstProcesoAdquisicionByAnho(idAnho);
    }
    // </editor-fold>

    public String cerrarDlgParametros() throws IOException {
        if (codigoDepartamento != null && idMunicipio != null && idAnho != null && idProcesoAdq != null) {
            validacionGeneral = false;

            VarSession.crearCookie("departamento", codigoDepartamento);
            VarSession.crearCookie("municipio", idMunicipio.toString());
            VarSession.crearCookie("anho", idAnho.toString());
            VarSession.crearCookie("proceso", idProcesoAdq.toString());

            municipio = utilEJB.find(Municipio.class, idMunicipio);
            anho = utilEJB.find(Anho.class, idAnho);
            proceso = utilEJB.find(ProcesoAdquisicion.class, idProcesoAdq);

            anhoProceso = anho.getAnho() + " :: " + proceso.getDescripcionProcesoAdq();
            ubicacion = JsfUtil.getNombreDepartamentoByCodigo(codigoDepartamento) + ", " + municipio.getNombreMunicipio();

            ((AnhoProcesoController) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "anhoProcesoController")).init();
            ((DatosGeograficosController) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "datosGeograficosController")).init();

            return "/app/inicial?faces-redirect=true";
        } else {

            String msj = "Debe Seleccionar: ";
            if (codigoDepartamento == null || idMunicipio == null) {
                if (!msj.replace("Debe Seleccionar: ", "").isEmpty()) {
                    msj += "<br/>";
                }
                msj += "Un departamento y municipio";
            }

            if (idAnho == null || idProcesoAdq == null) {
                msj = "El anho y un proceso";
            }
            if (!msj.replace("Debe Seleccionar: ", "").isEmpty()) {
                JsfUtil.mensajeAlerta(msj);
            }
            return "";
        }
    }

    public void cerrarDlgParamAnhoProceso() throws IOException {
        String msj = "Debe Seleccionar: ";
        if (idAnho == null || idProcesoAdq == null) {
            msj = "El anho y un proceso";
        }
        if (!msj.replace("Debe Seleccionar: ", "").isEmpty()) {
            JsfUtil.mensajeAlerta(msj);
        } else {
            anho = utilEJB.find(Anho.class, idAnho);
            proceso = utilEJB.find(ProcesoAdquisicion.class, idProcesoAdq);

            VarSession.crearCookie("anho", anho.getIdAnho().toString());
            VarSession.crearCookie("proceso", proceso.getIdProcesoAdq().toString());
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
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

            VarSession.crearCookie("departamento", codigoDepartamento);
            VarSession.crearCookie("municipio", idMunicipio.toString());

            PrimeFaces.current().executeScript("PF('dlgParamUbicacion').hide()");
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public void cerrarFiltroUbicacion() {
        if (VarSession.isCookie("departamento")) {
            codigoDepartamento = VarSession.getCookieValue("departamento");
        }
        if (VarSession.isCookie("municipio")) {
            idMunicipio = new BigDecimal(VarSession.getCookieValue("municipio"));
        }
    }

    public void cerrarFiltroAnhoPro() {
        if (VarSession.isCookie("anho")) {
            anho = utilEJB.find(Anho.class, new BigDecimal(VarSession.getCookieValue("anho")));
        }
        if (VarSession.isCookie("proceso")) {
            proceso = utilEJB.find(ProcesoAdquisicion.class, new Integer(VarSession.getCookieValue("proceso")));
        }
    }

}
