/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.credito;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import sv.gob.mined.paquescolar.model.DetalleCredito;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultCreditoProveedorDto",
        entities = @EntityResult(entityClass = CreditoProveedorDto.class))
public class CreditoProveedorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idRow;
    private String numeroNit;
    private String razonSocial;
    private String nombreEntFinan;
    private String codigoEntidad;
    private String nombreCE;
    private String nombreDepartamento;
    private String codigoDepartamento;
    private String codigoDepaEmp;
    private String nombreDepartamentoPro;
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    private BigDecimal idCredito;
    private BigDecimal idProceso;
    private BigDecimal montoCredito;
    private BigInteger creditoActivo;
    private BigDecimal montoContrato;
    private BigDecimal idDetalle;
    private Boolean cancelado = false;

    @Transient
    private DetalleCredito detalleCredito;

    public CreditoProveedorDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public BigDecimal getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(BigDecimal idCredito) {
        this.idCredito = idCredito;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public BigDecimal getMontoCredito() {
        return montoCredito;
    }

    public void setMontoCredito(BigDecimal montoCredito) {
        this.montoCredito = montoCredito;
    }

    public String getNombreEntFinan() {
        return nombreEntFinan;
    }

    public void setNombreEntFinan(String nombreEntFinan) {
        this.nombreEntFinan = nombreEntFinan;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getNombreCE() {
        return nombreCE;
    }

    public void setNombreCE(String nombreCE) {
        this.nombreCE = nombreCE;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public BigDecimal getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(BigDecimal idProceso) {
        this.idProceso = idProceso;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getCodigoDepaEmp() {
        return codigoDepaEmp;
    }

    public void setCodigoDepaEmp(String codigoDepaEmp) {
        this.codigoDepaEmp = codigoDepaEmp;
    }

    public BigInteger getCreditoActivo() {
        return creditoActivo;
    }

    public void setCreditoActivo(BigInteger creditoActivo) {
        this.creditoActivo = creditoActivo;
    }

    public BigDecimal getMontoContrato() {
        return montoContrato;
    }

    public void setMontoContrato(BigDecimal montoContrato) {
        this.montoContrato = montoContrato;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNombreDepartamentoPro() {
        return nombreDepartamentoPro;
    }

    public void setNombreDepartamentoPro(String nombreDepartamentoPro) {
        this.nombreDepartamentoPro = nombreDepartamentoPro;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public BigDecimal getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(BigDecimal idDetalle) {
        this.idDetalle = idDetalle;
    }

    public DetalleCredito getDetalleCredito() {
        return detalleCredito;
    }

    public void setDetalleCredito(DetalleCredito detalleCredito) {
        this.detalleCredito = detalleCredito;
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }
}
