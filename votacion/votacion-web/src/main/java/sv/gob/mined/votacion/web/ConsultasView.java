/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.votacion.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import sv.gob.mined.votacion.api.facade.VotacionFacadeLocal;
import sv.gob.mined.votacion.model.votacion.dto.DirectorDto;

/**
 *
 * @author DesarrolloPc
 */
@Named(value = "consultasView")
@RequestScoped
public class ConsultasView implements Serializable {

    private String dui;
    private String nip;
    private String nombre;
    private String nombreCe;
    private String codigoDepartamento;

    private List<DirectorDto> lstDirectores = new ArrayList();

    @EJB
    private VotacionFacadeLocal vfl;

    /**
     * Creates a new instance of ConsultasView
     */
    public ConsultasView() {
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCe() {
        return nombreCe;
    }

    public void setNombreCe(String nombreCe) {
        this.nombreCe = nombreCe;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String nombreDepartamento) {
        this.codigoDepartamento = nombreDepartamento;
    }

    public List<DirectorDto> getLstDirectores() {
        return lstDirectores;
    }

    public void setLstDirectores(List<DirectorDto> lstDirectores) {
        this.lstDirectores = lstDirectores;
    }

    public void buscarDirectores() {
        lstDirectores = vfl.getLstDirectores(dui, nip, nombre, nombreCe, codigoDepartamento);
    }
}
