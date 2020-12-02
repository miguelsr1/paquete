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
    private ProyectoCooperacion proyecto;
    private Long idProyecto;
    private List<ListadoProyectoDto> lstProyectos = new ArrayList();
    private List<Municipio> lstMunicipio = new ArrayList();

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

                    break;
                default:
                    lstProyectos = mantenimientoFacade.findProyectosByCodigoEntidad(usu.getDirector().getCodigoEntidad());
                    break;
            }
        }

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

    public void recuperarMunicipios() {
        lstMunicipio = ubicacionFacade.getLstMunicipio(codigoDepartamento);
        lstProyectos = mantenimientoFacade.findProyectosByDepartamento(codigoDepartamento);
    }

    public void recuperarLstProyectosByMunicipio() {
        lstProyectos = mantenimientoFacade.findProyectosByMunicipio(idMunicipio);
    }
    
    public void recuperarLstProyectosByEstado() {
        lstProyectos = mantenimientoFacade.findProyectosByMunicipio(idMunicipio);
    }

    public void recuperarProyecto() {
        proyecto = mantenimientoFacade.find(ProyectoCooperacion.class, idProyecto);
    }
}
