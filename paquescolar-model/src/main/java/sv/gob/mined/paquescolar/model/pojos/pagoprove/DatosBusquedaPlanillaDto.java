/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.pagoprove;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultDatosBusquedaPlanillaDto",
        entities = @EntityResult(entityClass = DatosBusquedaPlanillaDto.class))
public class DatosBusquedaPlanillaDto implements Serializable{
    @Id
    private BigDecimal idRow;
    private String formatoRequerimiento;
    private String codigoEntidad;
    private String nombreCe;
    private String nombreDepartamento;
    private String numeroNit;
    private String razonSocial;
    private String descripcionRubro;
    private BigDecimal montoActual;
    private BigInteger cantidadActual;
    private String nombreEntFinan;
    private BigDecimal idPlanilla;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInsercion;

    public DatosBusquedaPlanillaDto() {
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getFormatoRequerimiento() {
        return formatoRequerimiento;
    }

    public void setFormatoRequerimiento(String formatoRequerimiento) {
        this.formatoRequerimiento = formatoRequerimiento;
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

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
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

    public BigDecimal getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(BigDecimal montoActual) {
        this.montoActual = montoActual;
    }

    public BigInteger getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(BigInteger cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public String getNombreEntFinan() {
        return nombreEntFinan;
    }

    public void setNombreEntFinan(String nombreEntFinan) {
        this.nombreEntFinan = nombreEntFinan;
    }

    public BigDecimal getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(BigDecimal idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }
}
