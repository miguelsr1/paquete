/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author DesarrolloPc
 */
public class VwDetalleModificativas {

    private String nombreDepartamento;
    private String nombreCe;
    private String codigoEntidad;
    private String numeroNit;
    private String razonSocial;
    private String numContrato;
    private BigDecimal montoContratoActual;
    private Boolean errorDigitacion = false;
    private Boolean errorAdjudicacion = false;
    private Boolean prorroga = false;
    private Integer cantidadModificada;
    private BigDecimal montoContratoModificado;
    private int numeroRegistro;
    private Boolean tipoExtinsion1 = false; //Imcumplimiento en plazo contractual
    private Boolean tipoExtinsion2 = false; //Por mutuo acuerdo
    private Boolean tipoExtinsion3 = false; //Cambio de proveedor
    private Boolean tipoExtinsion4 = false; //Dimision de contrato
    private Boolean tipoExtinsion5 = false; //Fallecimiento del Proveedor
    private Date fechaResolucion;

    public VwDetalleModificativas() {
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getNombreCe() {
        return nombreCe;
    }

    public void setNombreCe(String nombreCe) {
        this.nombreCe = nombreCe;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
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

    public String getNumContrato() {
        return numContrato;
    }

    public void setNumContrato(String numContrato) {
        this.numContrato = numContrato;
    }

    public BigDecimal getMontoContratoActual() {
        return montoContratoActual;
    }

    public void setMontoContratoActual(BigDecimal montoContratoActual) {
        this.montoContratoActual = montoContratoActual;
    }

    public Boolean getErrorDigitacion() {
        return errorDigitacion;
    }

    public void setErrorDigitacion(Boolean errorDigitacion) {
        this.errorDigitacion = errorDigitacion;
    }

    public Boolean getErrorAdjudicacion() {
        return errorAdjudicacion;
    }

    public void setErrorAdjudicacion(Boolean errorAdjudicacion) {
        this.errorAdjudicacion = errorAdjudicacion;
    }

    public Boolean getProrroga() {
        return prorroga;
    }

    public void setProrroga(Boolean prorroga) {
        this.prorroga = prorroga;
    }

    public Integer getCantidadModificada() {
        return cantidadModificada;
    }

    public void setCantidadModificada(Integer cantidadModificada) {
        this.cantidadModificada = cantidadModificada;
    }

    public BigDecimal getMontoContratoModificado() {
        return montoContratoModificado;
    }

    public void setMontoContratoModificado(BigDecimal montoContratoModificado) {
        this.montoContratoModificado = montoContratoModificado;
    }

    public int getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(int numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public Boolean getTipoExtinsion1() {
        return tipoExtinsion1;
    }

    public void setTipoExtinsion1(Boolean tipoExtinsion1) {
        this.tipoExtinsion1 = tipoExtinsion1;
    }

    public Boolean getTipoExtinsion2() {
        return tipoExtinsion2;
    }

    public void setTipoExtinsion2(Boolean tipoExtinsion2) {
        this.tipoExtinsion2 = tipoExtinsion2;
    }

    public Boolean getTipoExtinsion3() {
        return tipoExtinsion3;
    }

    public void setTipoExtinsion3(Boolean tipoExtinsion3) {
        this.tipoExtinsion3 = tipoExtinsion3;
    }

    public Boolean getTipoExtinsion4() {
        return tipoExtinsion4;
    }

    public void setTipoExtinsion4(Boolean tipoExtinsion4) {
        this.tipoExtinsion4 = tipoExtinsion4;
    }

    public Boolean getTipoExtinsion5() {
        return tipoExtinsion5;
    }

    public void setTipoExtinsion5(Boolean tipoExtinsion5) {
        this.tipoExtinsion5 = tipoExtinsion5;
    }

    public Date getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
}