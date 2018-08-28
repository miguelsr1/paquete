/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.view;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author
 */
@Entity
@SqlResultSetMapping(name = "vwSeguimientoRptCentroEscolar",
        entities = @EntityResult(entityClass = sv.gob.mined.paquescolar.model.view.VwSeguimientoRptCentroEscolar.class))
public class VwSeguimientoRptCentroEscolar implements Serializable {

    @Id
    private BigDecimal idContrato;
    private String codigoDepartamento;
    private String codigoMunicipio;
    private String codigoEntidad;
    private String nombre;
    private String nombreDepartamento;
    private String nombreMunicipio;
    private String rubro;
    private String numeroNit;
    private String razonSocial;
    private BigDecimal montoContratado;
    private BigDecimal cantidadContratada;
    private Integer idDetProcesoAdq;
    private BigDecimal cantidadEntregada;
    private String estadoEntrega;

    public VwSeguimientoRptCentroEscolar() {
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public BigDecimal getMontoContratado() {
        return montoContratado;
    }

    public void setMontoContratado(BigDecimal montoContratado) {
        this.montoContratado = montoContratado;
    }

    public BigDecimal getCantidadContratada() {
        return cantidadContratada;
    }

    public void setCantidadContratada(BigDecimal cantidadContratada) {
        this.cantidadContratada = cantidadContratada;
    }

    public Integer getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(Integer idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    public BigDecimal getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(BigDecimal cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }
}
