/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.view;

import java.math.BigDecimal;

/**
 *
 * @author DesarrolloPc
 */
public class VwBusquedaSeguimientos {

    private String codigoDepartamento;
    private String nombreDepartamento;
    private String codigoEntidad;
    private String nombreCe;
    private BigDecimal cantidad;
    private BigDecimal monto;
    private BigDecimal idContrato;
    private BigDecimal idRecepcion;
    private String numeroContrato;
    private String razonSocial;
    private String numeroNit;
    private String estado;
    private Boolean estadoEliminacion;

    public VwBusquedaSeguimientos() {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(Boolean estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdRecepcion() {
        return idRecepcion;
    }

    public void setIdRecepcion(BigDecimal idRecepcion) {
        this.idRecepcion = idRecepcion;
    }
}
