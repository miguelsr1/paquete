/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author misanchez
 */
public class RequerimientoDTO {
    private String numeroRequerimiento;
    private Date fechaGeneracion;
    private String descripcionComponente;
    private String fuenteFinanciamiento;
    private String nombrePagador;
    private String departamento;
    private List<DetalleRequerimientoDTO> lstDetalleRequerimiento = new ArrayList<DetalleRequerimientoDTO>();

    public RequerimientoDTO() {
    }

    public String getNumeroRequerimiento() {
        return numeroRequerimiento;
    }

    public void setNumeroRequerimiento(String numeroRequerimiento) {
        this.numeroRequerimiento = numeroRequerimiento;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getDescripcionComponente() {
        return descripcionComponente;
    }

    public void setDescripcionComponente(String descripcionComponente) {
        this.descripcionComponente = descripcionComponente;
    }

    public String getFuenteFinanciamiento() {
        return fuenteFinanciamiento;
    }

    public void setFuenteFinanciamiento(String fuenteFinanciamiento) {
        this.fuenteFinanciamiento = fuenteFinanciamiento;
    }

    public String getNombrePagador() {
        return nombrePagador;
    }

    public void setNombrePagador(String nombrePagador) {
        this.nombrePagador = nombrePagador;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public List<DetalleRequerimientoDTO> getLstDetalleRequerimiento() {
        return lstDetalleRequerimiento;
    }

    public void setLstDetalleRequerimiento(List<DetalleRequerimientoDTO> lstDetalleRequerimiento) {
        this.lstDetalleRequerimiento = lstDetalleRequerimiento;
    }
}
