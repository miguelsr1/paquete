/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.mined.paquescolar.model.pojos.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author misanchez
 */
@Entity
@SqlResultSetMapping(name = "defaultPorCapaGeoPorPrecioDto",
        entities = @EntityResult(entityClass = PorCapaGeoPorPrecioDto.class))
public class PorCapaGeoPorPrecioDto implements Serializable {

    @Id
    private BigDecimal idMuestraInteres;
    private BigDecimal idEmpresa;
    private String razonSocial;
    private BigInteger distribuidor;
    private String nombreMunicipio;
    private String codigoDepartamento;
    private String nombreDepartamento;
    private String codigoCanton;
    private BigDecimal idMunicipio;
    private BigDecimal precioPromedio;
    private BigDecimal porcentajeCapacidad;
    private BigDecimal porcentajeGeo;

    public PorCapaGeoPorPrecioDto() {
    }

    public BigDecimal getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigDecimal idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public BigDecimal getIdMuestraInteres() {
        return idMuestraInteres;
    }

    public void setIdMuestraInteres(BigDecimal idMuestraInteres) {
        this.idMuestraInteres = idMuestraInteres;
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

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getCodigoCanton() {
        return codigoCanton;
    }

    public void setCodigoCanton(String codigoCanton) {
        this.codigoCanton = codigoCanton;
    }

    public BigDecimal getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(BigDecimal idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public BigDecimal getPrecioPromedio() {
        return precioPromedio;
    }

    public void setPrecioPromedio(BigDecimal precioPromedio) {
        this.precioPromedio = precioPromedio;
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

}
