/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultProveedorDisponibleDto",
        entities = @EntityResult(entityClass = ProveedorDisponibleDto.class))
public class ProveedorDisponibleDto implements Serializable {

    @Id
    private BigDecimal idRow;
    private BigDecimal idEmpresa;
    private String razonSocial;
    private Integer distribuidor;
    private BigDecimal capacidadAcreditada;
    private BigDecimal capacidadAdjudicada;
    private String nombreMunicipio;
    private String nombreDepartamento;
    private Double puAvg;
    private Double porcentajePrecio;
    private Double porcentajeGeo;
    private Double porcentajeCapacidad;
    private Double porcentajeCapacidadItem;
    private Double porcentajeEvaluacion;
    private Double porcentajeAdjudicacion;

    public ProveedorDisponibleDto() {
    }

    public Double getPorcentajeCapacidadItem() {
        return porcentajeCapacidadItem;
    }

    public void setPorcentajeCapacidadItem(Double porcentajeCapacidadItem) {
        this.porcentajeCapacidadItem = porcentajeCapacidadItem;
    }

    public Double getPorcentajeCapacidad() {
        return porcentajeCapacidad;
    }

    public void setPorcentajeCapacidad(Double porcentajeCapacidad) {
        this.porcentajeCapacidad = porcentajeCapacidad;
    }

    public Double getPorcentajeGeo() {
        return porcentajeGeo;
    }

    public void setPorcentajeGeo(Double porcentajeGeo) {
        this.porcentajeGeo = porcentajeGeo;
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public BigDecimal getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigDecimal idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Integer getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Integer distribuidor) {
        this.distribuidor = distribuidor;
    }

    public BigDecimal getCapacidadAcreditada() {
        return capacidadAcreditada;
    }

    public void setCapacidadAcreditada(BigDecimal capacidadAcreditada) {
        this.capacidadAcreditada = capacidadAcreditada;
    }

    public BigDecimal getCapacidadAdjudicada() {
        return capacidadAdjudicada;
    }

    public void setCapacidadAdjudicada(BigDecimal capacidadAdjudicada) {
        this.capacidadAdjudicada = capacidadAdjudicada;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public Double getPuAvg() {
        return puAvg;
    }

    public void setPuAvg(Double puAvg) {
        this.puAvg = puAvg;
    }

    public Double getPorcentajePrecio() {
        return porcentajePrecio;
    }

    public void setPorcentajePrecio(Double porcentajePrecio) {
        this.porcentajePrecio = porcentajePrecio;
    }

    public Double getPorcentajeEvaluacion() {
        return porcentajeEvaluacion;
    }

    public void setPorcentajeEvaluacion(Double porcentajeEvaluacion) {
        this.porcentajeEvaluacion = porcentajeEvaluacion;
    }

    public Double getPorcentajeAdjudicacion() {
        return porcentajeAdjudicacion;
    }

    public void setPorcentajeAdjudicacion(Double porcentajeAdjudicacion) {
        this.porcentajeAdjudicacion = porcentajeAdjudicacion;
    }
}
