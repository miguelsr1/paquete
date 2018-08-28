/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.modificativa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DesarrolloPc
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultBusquedaContratos",
        entities = @EntityResult(entityClass = VwBusquedaContratos.class))
public class VwBusquedaContratos implements Serializable{

    @Id
    private BigDecimal idContrato;
    private String codigoDepartamento;
    private String nombreDepartamento;
    private String codigoEntidad;
    private String nombreCe;
    private String anhoContrato;
    private BigDecimal cantidad;
    private BigDecimal monto;
    private String numeroContrato;
    private String razonSocial;
    private String numeroNit;
    private String descripcionReserva;
    private BigDecimal idResolucionAdj;
    private BigDecimal idRubroAdq;
    private BigDecimal idDetProcesoAdq;
    private BigDecimal idEstadoReserva;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInsercion; 
    
    public VwBusquedaContratos() {
    }

    public BigDecimal getIdResolucionAdj() {
        return idResolucionAdj;
    }

    public void setIdResolucionAdj(BigDecimal idResolucionAdj) {
        this.idResolucionAdj = idResolucionAdj;
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

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getDescripcionReserva() {
        return descripcionReserva;
    }

    public void setDescripcionReserva(String descripcionReserva) {
        this.descripcionReserva = descripcionReserva;
    }

    public String getAnhoContrato() {
        return anhoContrato;
    }

    public void setAnhoContrato(String anhoContrato) {
        this.anhoContrato = anhoContrato;
    }

    public BigDecimal getIdRubroAdq() {
        return idRubroAdq;
    }

    public void setIdRubroAdq(BigDecimal idRubroAdq) {
        this.idRubroAdq = idRubroAdq;
    }

    public BigDecimal getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(BigDecimal idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    public BigDecimal getIdEstadoReserva() {
        return idEstadoReserva;
    }

    public void setIdEstadoReserva(BigDecimal idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }
}