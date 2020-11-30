/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import sv.gob.mined.cooperacion.facade.MantenimientoFacade;
import sv.gob.mined.cooperacion.model.Usuario;
import sv.gob.mined.cooperacion.model.dto.ListadoProyectoDto;

/**
 *
 * @author MISanchez
 */
@Named(value = "listadoView")
@ViewScoped
public class ListadoView implements Serializable {

    private String lblBottonEnviar = "";
    private List<ListadoProyectoDto> lstProyectos = new ArrayList();

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

    public List<ListadoProyectoDto> getLstProyectos() {
        return lstProyectos;
    }

    public void setLstProyectos(List<ListadoProyectoDto> lstProyectos) {
        this.lstProyectos = lstProyectos;
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
}
