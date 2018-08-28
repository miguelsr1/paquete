/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.pagoprove;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultRentaProveDto",
        entities = @EntityResult(entityClass = DatosProveDto.class))
public class DatosProveDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idRow;
    private String anho;
    private String numeroNit;
    private String numeroDui;
    private String numeroNitEmp;
    private String razonSocial;
    private String nombreDepartamento;
    private String pagadorDepartamental;
    private String numCheque;
    private String docPago;
    private String correoElectronico;
    private String descripcionRubro;
    private String nombreRepresentante;
    private String formatoRequerimiento;
    private String codigoEntidad;
    private String nombreCe;
    private BigDecimal montoActual;
    private BigDecimal montoRetencion;
    private BigDecimal montoRenta;
    private BigDecimal montoReintegro;
    private BigDecimal idDetProcesoAdq;
    private BigDecimal capacidadAcreditada;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCheque;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDocPago;
    @Transient
    private List<DatosProveDto> lstDetalle;

    public DatosProveDto() {
    }

    public String getFormatoRequerimiento() {
        return formatoRequerimiento;
    }

    public void setFormatoRequerimiento(String formatoRequerimiento) {
        this.formatoRequerimiento = formatoRequerimiento;
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getNumeroDui() {
        return numeroDui;
    }

    public void setNumeroDui(String numeroDui) {
        this.numeroDui = numeroDui;
    }

    public String getNumeroNitEmp() {
        return numeroNitEmp;
    }

    public void setNumeroNitEmp(String numeroNitEmp) {
        this.numeroNitEmp = numeroNitEmp;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getPagadorDepartamental() {
        return pagadorDepartamental;
    }

    public void setPagadorDepartamental(String pagadorDepartamental) {
        this.pagadorDepartamental = pagadorDepartamental;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public String getDocPago() {
        return docPago;
    }

    public void setDocPago(String docPago) {
        this.docPago = docPago;
    }

    public BigDecimal getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(BigDecimal montoActual) {
        this.montoActual = montoActual;
    }

    public BigDecimal getMontoRetencion() {
        return montoRetencion;
    }

    public void setMontoRetencion(BigDecimal montoRetencion) {
        this.montoRetencion = montoRetencion;
    }

    public BigDecimal getMontoRenta() {
        return montoRenta;
    }

    public void setMontoRenta(BigDecimal montoRenta) {
        this.montoRenta = montoRenta;
    }

    public Date getFechaCheque() {
        return fechaCheque;
    }

    public void setFechaCheque(Date fechaCheque) {
        this.fechaCheque = fechaCheque;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public BigDecimal getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(BigDecimal idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    public BigDecimal getCapacidadAcreditada() {
        return capacidadAcreditada;
    }

    public void setCapacidadAcreditada(BigDecimal capacidadAcreditada) {
        this.capacidadAcreditada = capacidadAcreditada;
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

    public Date getFechaDocPago() {
        return fechaDocPago;
    }

    public void setFechaDocPago(Date fechaDocPago) {
        this.fechaDocPago = fechaDocPago;
    }

    public List<DatosProveDto> getLstDetalle() {
        return lstDetalle;
    }

    public void setLstDetalle(List<DatosProveDto> lstDetalle) {
        this.lstDetalle = lstDetalle;
    }

    public BigDecimal getMontoReintegro() {
        return montoReintegro;
    }

    public void setMontoReintegro(BigDecimal montoReintegro) {
        this.montoReintegro = montoReintegro;
    }
}