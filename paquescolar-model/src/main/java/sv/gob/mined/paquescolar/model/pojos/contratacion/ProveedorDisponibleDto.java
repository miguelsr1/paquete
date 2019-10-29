/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    private BigInteger distribuidor;
    private BigDecimal capacidadAcreditada;
    private BigDecimal capacidadAdjudicada;
    private String nombreMunicipio;
    private String nombreDepartamento;
    private BigDecimal puAvg;
    private BigDecimal porcentajePrecio;
    private BigDecimal porcentajeGeo;
    private BigDecimal porcentajeCapacidad;
    private BigDecimal porcentajeCapacidadItem;

    public ProveedorDisponibleDto() {
    }

    public BigDecimal getPorcentajeCapacidadItem() {
        return porcentajeCapacidadItem;
    }

    public void setPorcentajeCapacidadItem(BigDecimal porcentajeCapacidadItem) {
        this.porcentajeCapacidadItem = porcentajeCapacidadItem;
    }

    public BigDecimal getPorcentajeCapacidad() {
        return porcentajeCapacidad;
    }

    public void setPorcentajeCapacidad(BigDecimal porcentajeCapacidad) {
        this.porcentajeCapacidad = porcentajeCapacidad;
    }

    public BigDecimal getPorcentajeGeo() {
        return porcentajeGeo;
    }

    public void setPorcentajeGeo(BigDecimal porcentajeGeo) {
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

    public BigInteger getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(BigInteger distribuidor) {
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

    public BigDecimal getPuAvg() {
        return puAvg;
    }

    public void setPuAvg(BigDecimal puAvg) {
        this.puAvg = puAvg;
    }

    public BigDecimal getPorcentajePrecio() {
        return porcentajePrecio;
    }

    public void setPorcentajePrecio(BigDecimal porcentajePrecio) {
        this.porcentajePrecio = porcentajePrecio;
    }

}
