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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import sv.gob.mined.cooperacion.facade.MantenimientoFacade;
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
        lstProyectos = mantenimientoFacade.findAllProyectos();
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

    public void enviar(){
        
    }
    
    public void nuevoProyecto() {
        
    }
    
    public void eliminarProyecto(){
        
    }
}
