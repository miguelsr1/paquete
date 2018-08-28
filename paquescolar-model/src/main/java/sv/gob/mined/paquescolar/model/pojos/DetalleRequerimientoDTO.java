/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.math.BigDecimal;

/**
 *
 * @author misanchez
 */
public class DetalleRequerimientoDTO {
    private String codigoEntidad;
    private String nombreCe;
    private BigDecimal montoRequerimiento;
    private String numeroNit;
    private String nombreEmpresa;
    private BigDecimal montoContrato;

    public DetalleRequerimientoDTO() {
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

    public BigDecimal getMontoRequerimiento() {
        return montoRequerimiento;
    }

    public void setMontoRequerimiento(BigDecimal montoRequerimiento) {
        this.montoRequerimiento = montoRequerimiento;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public BigDecimal getMontoContrato() {
        return montoContrato;
    }

    public void setMontoContrato(BigDecimal montoContrato) {
        this.montoContrato = montoContrato;
    }
}
