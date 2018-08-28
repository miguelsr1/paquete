/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import sv.gob.mined.paquescolar.model.EstadisticaCenso;


/**
 *
 * @author misanchez
 */
public class VwRptCertificacionPresupuestaria {
    private String departamento;
    private String municipio;
    private String codigoEntidad;
    private String nombreCe;
    private String modalidadDeAdministracion;
    private String usuarioInsercion;
    
    private EstadisticaCenso parvularia;
    private EstadisticaCenso ciclo1;
    private EstadisticaCenso ciclo2;
    private EstadisticaCenso ciclo3;
    private EstadisticaCenso grado7;
    private EstadisticaCenso grado8;
    private EstadisticaCenso grado9;
    private EstadisticaCenso bachillerato;

    public VwRptCertificacionPresupuestaria() {
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getNombreCe() {
        return nombreCe;
    }

    public void setNombreCe(String nombreCe) {
        this.nombreCe = nombreCe;
    }

    public String getModalidadDeAdministracion() {
        return modalidadDeAdministracion;
    }

    public void setModalidadDeAdministracion(String modalidadDeAdministracion) {
        this.modalidadDeAdministracion = modalidadDeAdministracion;
    }

    public EstadisticaCenso getParvularia() {
        return parvularia;
    }

    public void setParvularia(EstadisticaCenso parvularia) {
        this.parvularia = parvularia;
    }

    public EstadisticaCenso getCiclo1() {
        return ciclo1;
    }

    public void setCiclo1(EstadisticaCenso ciclo1) {
        this.ciclo1 = ciclo1;
    }

    public EstadisticaCenso getCiclo2() {
        return ciclo2;
    }

    public void setCiclo2(EstadisticaCenso ciclo2) {
        this.ciclo2 = ciclo2;
    }

    public EstadisticaCenso getCiclo3() {
        return ciclo3;
    }

    public void setCiclo3(EstadisticaCenso ciclo3) {
        this.ciclo3 = ciclo3;
    }

    public EstadisticaCenso getBachillerato() {
        return bachillerato;
    }

    public void setBachillerato(EstadisticaCenso barchillerato) {
        this.bachillerato = barchillerato;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public EstadisticaCenso getGrado7() {
        return grado7;
    }

    public void setGrado7(EstadisticaCenso grado7) {
        this.grado7 = grado7;
    }

    public EstadisticaCenso getGrado8() {
        return grado8;
    }

    public void setGrado8(EstadisticaCenso grado8) {
        this.grado8 = grado8;
    }

    public EstadisticaCenso getGrado9() {
        return grado9;
    }

    public void setGrado9(EstadisticaCenso grado9) {
        this.grado9 = grado9;
    }
}