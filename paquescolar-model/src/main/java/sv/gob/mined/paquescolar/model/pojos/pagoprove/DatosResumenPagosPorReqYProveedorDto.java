/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.pagoprove;

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
@SqlResultSetMapping(name = "defaultDatosResumenPagosPorReqYProveedorDto",
        entities = @EntityResult(entityClass = DatosResumenPagosPorReqYProveedorDto.class))
public class DatosResumenPagosPorReqYProveedorDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idRow;
    private String formatoRequerimiento;
    private String codigoDepartamento;
    private String descripcionRubro;
    private String numeroNit;
    private String razonSocial;
    private BigDecimal cantidadTotalContratos;
    private BigDecimal montoTotalContratado;
    private BigDecimal montoTotalPagado;
    private BigDecimal montoTotalPendiente;
    private BigDecimal montoTotalReintegrar;

    public DatosResumenPagosPorReqYProveedorDto() {
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

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
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

    public BigDecimal getCantidadTotalContratos() {
        return cantidadTotalContratos;
    }

    public void setCantidadTotalContratos(BigDecimal cantidadTotalContratos) {
        this.cantidadTotalContratos = cantidadTotalContratos;
    }

    public BigDecimal getMontoTotalContratado() {
        return montoTotalContratado;
    }

    public void setMontoTotalContratado(BigDecimal montoTotalContratado) {
        this.montoTotalContratado = montoTotalContratado;
    }

    public BigDecimal getMontoTotalPagado() {
        return montoTotalPagado;
    }

    public void setMontoTotalPagado(BigDecimal montoTotalPagado) {
        this.montoTotalPagado = montoTotalPagado;
    }

    public BigDecimal getMontoTotalPendiente() {
        return montoTotalPendiente;
    }

    public void setMontoTotalPendiente(BigDecimal montoTotalPendiente) {
        this.montoTotalPendiente = montoTotalPendiente;
    }

    public BigDecimal getMontoTotalReintegrar() {
        return montoTotalReintegrar;
    }

    public void setMontoTotalReintegrar(BigDecimal montoTotalReintegrar) {
        this.montoTotalReintegrar = montoTotalReintegrar;
    }
    
}