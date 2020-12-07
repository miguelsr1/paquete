/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import sv.gob.mined.cooperacion.facade.MantenimientoFacade;
import sv.gob.mined.cooperacion.facade.paquete.UbicacionFacade;
import sv.gob.mined.cooperacion.model.ProyectoCooperacion;
import sv.gob.mined.cooperacion.model.Usuario;
import sv.gob.mined.cooperacion.model.dto.ListadoProyectoDto;
import sv.gob.mined.cooperacion.model.paquete.Municipio;

/**
 *
 * @author MISanchez
 */
@Named(value = "listadoView")
@ViewScoped
public class ListadoView implements Serializable {

    private Short idEstado;
    private BigDecimal idMunicipio;
    private String lblBottonEnviar = "";
    private String codigoDepartamento;
    private String where;
    private final String posicionInicial = "13.749655, -88.822362";
    private ProyectoCooperacion proyecto;
    private Long idProyecto;
    private List<ListadoProyectoDto> lstProyectos = new ArrayList();
    private List<Municipio> lstMunicipio = new ArrayList();

    private MapModel simpleModel;

    @Inject
    private UbicacionFacade ubicacionFacade;

    @Inject
    private MantenimientoFacade mantenimientoFacade;

    /**
     * Creates a new instance of ListadoView
     */
    public ListadoView() {
    }

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();

        if (fc.getExternalContext().getSessionMap().containsKey("usuario")) {
            Usuario usu = (Usuario) fc.getExternalContext().getSessionMap().get("usuario");

            switch (usu.getIdPerfil()) {
                case 1:
                    lstProyectos = mantenimientoFacade.findAllProyectos();
                    break;
                case 2:
                    switch (usu.getUsuarioOrgList().get(0).getDescripcion()) {
                        case "INFRA":
                            where = " and pro.id_tipo_cooperacion in (1,4,6) ";
                            break;
                        case "INFOD":
                            where = " and pro.id_tipo_cooperacion in (3,15) ";
                            break;
                        case "DPSP":
                            where = " and pro.id_tipo_cooperacion in (5) ";
                            break;
                        case "GTIC":
                            where = " and pro.id_tipo_cooperacion in (9) ";
                            break;
                        case "JURIDICO":
                            where = " and pro.id_tipo_cooperacion in (12,13) ";
                            break;
                        case "ASISTENCIA":
                            where = " and pro.id_tipo_cooperacion in (14) ";
                            break;
                    }

                    lstProyectos = mantenimientoFacade.findProyectosByWhereCustom(where);
                    break;
                default:
                    where = " and vw.codigo_entidad = '" + usu.getDirector().getCodigoEntidad() + "'";
                    lstProyectos = mantenimientoFacade.findProyectosByWhereCustom(where);
                    break;
            }
        }

        simpleModel = new DefaultMapModel();
    }

    public String getPosicionInicial() {
        return posicionInicial;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public Short getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Short idEstado) {
        this.idEstado = idEstado;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public ProyectoCooperacion getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoCooperacion proyecto) {
        this.proyecto = proyecto;
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

    public List<ListadoProyectoDto> getLstProyectos() {
        return lstProyectos;
    }

    public void setLstProyectos(List<ListadoProyectoDto> lstProyectos) {
        this.lstProyectos = lstProyectos;
    }

    public List<Municipio> getLstMunicipio() {
        return lstMunicipio;
    }

    public String getLblBottonEnviar() {
        return lblBottonEnviar;
    }

    public void setLblBottonEnviar(String lblBottonEnviar) {
        this.lblBottonEnviar = lblBottonEnviar;
    }

    public void enviar() {

    }

    public void nuevoProyecto() {

    }

    public void eliminarProyecto() {

    }

    public void recuperarProyecto() {
        proyecto = mantenimientoFacade.find(ProyectoCooperacion.class, idProyecto);
    }

    public void recuperarMunicipios() {
        lstMunicipio = ubicacionFacade.getLstMunicipio(codigoDepartamento);
        recuperarLstProyectos();
    }

    public void recuperarLstProyectos() {
        String whereTmp = "";
        if (codigoDepartamento != null) {
            whereTmp = " and mun.codigo_departamento = '" + codigoDepartamento + "'";
        }
        if (codigoDepartamento != null && idMunicipio != null) {
            whereTmp += " and mun.id_municipio=" + idMunicipio + "";
        }
        if (idEstado != null) {
            whereTmp += " and pro.id_estado = " + idEstado;
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        switch (fc.getExternalContext().getSessionMap().get("role").toString()) {
            case "ADMIN":
                break;
            default:
                whereTmp = whereTmp + where;
                break;
        }

        lstProyectos = mantenimientoFacade.findProyectosByWhereCustom(whereTmp);
    }

    public void agregarPuntos() {
        lstProyectos.stream().filter((pro) -> (pro.getGeoPx() != null && pro.getGeoPy() != null)).forEachOrdered((pro) -> {
            LatLng coor = new LatLng(pro.getGeoPy().doubleValue(), pro.getGeoPx().doubleValue());
            simpleModel.addOverlay(new Marker(coor, "CE: " + pro.getCodigoEntidad()));
        });
    }
}
