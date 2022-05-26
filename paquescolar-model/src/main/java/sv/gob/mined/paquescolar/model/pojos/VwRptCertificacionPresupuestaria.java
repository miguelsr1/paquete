/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Transient;
import sv.gob.mined.paquescolar.model.EstadisticaCenso;

/**
 *
 * @author misanchez
 */
@Entity
@NamedNativeQueries({
@NamedNativeQuery(
        name = "cabeceraCertificacionPre",
        query = "SELECT nombre_departamento as nombreDepartamento, nombre_municipio as nombreMunicipio, codigo_entidad as codigoEntidad, nombre, iniciales_modalidad as modalidadDeAdministracion FROM vw_cabecera_certificacion_pre WHERE codigo_entidad = ?1",
        resultClass = VwRptCertificacionPresupuestaria.class)
})

public class VwRptCertificacionPresupuestaria implements Serializable {

    @Id
    private String codigoEntidad;
    private String departamento;
    private String municipio;
    private String nombreCe;
    private String modalidadDeAdministracion;
    private String usuarioInsercion;

    @Transient
    private EstadisticaCenso parvularia;
    @Transient
    private EstadisticaCenso ciclo1;
    @Transient
    private EstadisticaCenso ciclo2;
    @Transient
    private EstadisticaCenso ciclo3;
    @Transient
    private EstadisticaCenso grado1;
    @Transient
    private EstadisticaCenso grado2;
    @Transient
    private EstadisticaCenso grado3;
    @Transient
    private EstadisticaCenso grado4;
    @Transient
    private EstadisticaCenso grado5;
    @Transient
    private EstadisticaCenso grado6;
    @Transient
    private EstadisticaCenso grado7;
    @Transient
    private EstadisticaCenso grado8;
    @Transient
    private EstadisticaCenso grado9;
    @Transient
    private EstadisticaCenso gradob1;
    @Transient
    private EstadisticaCenso gradob2;
    @Transient
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

    public EstadisticaCenso getGrado1() {
        return grado1;
    }

    public void setGrado1(EstadisticaCenso grado1) {
        this.grado1 = grado1;
    }

    public EstadisticaCenso getGrado2() {
        return grado2;
    }

    public void setGrado2(EstadisticaCenso grado2) {
        this.grado2 = grado2;
    }

    public EstadisticaCenso getGrado3() {
        return grado3;
    }

    public void setGrado3(EstadisticaCenso grado3) {
        this.grado3 = grado3;
    }

    public EstadisticaCenso getGrado4() {
        return grado4;
    }

    public void setGrado4(EstadisticaCenso grado4) {
        this.grado4 = grado4;
    }

    public EstadisticaCenso getGrado5() {
        return grado5;
    }

    public void setGrado5(EstadisticaCenso grado5) {
        this.grado5 = grado5;
    }

    public EstadisticaCenso getGrado6() {
        return grado6;
    }

    public void setGrado6(EstadisticaCenso grado6) {
        this.grado6 = grado6;
    }

    public EstadisticaCenso getGradob1() {
        return gradob1;
    }

    public void setGradob1(EstadisticaCenso gradob1) {
        this.gradob1 = gradob1;
    }

    public EstadisticaCenso getGradob2() {
        return gradob2;
    }

    public void setGradob2(EstadisticaCenso gradob2) {
        this.gradob2 = gradob2;
    }
}
