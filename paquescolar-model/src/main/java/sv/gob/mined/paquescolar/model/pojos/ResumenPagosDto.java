/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author misanchez
 */
public class ResumenPagosDto implements Serializable {

    private BigDecimal idRequerimiento;
    private String codigoEntidad;
    private String codigoDepartamento;
    private String nombreDepartamento;
    private String formatoRequerimiento;
    private BigDecimal montoTotal = BigDecimal.ZERO;
    private BigInteger numeroCe;
    private BigInteger cantidadPlanilla;
    private BigDecimal montoTotalPlanilla = BigDecimal.ZERO;
    private BigDecimal montoPendientePago = BigDecimal.ZERO;
    private BigDecimal montoReintegrar = BigDecimal.ZERO;
    private BigDecimal saldoRequerimiento = BigDecimal.ZERO;

    public ResumenPagosDto() {
    }

    public BigDecimal getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(BigDecimal idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
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

    public String getFormatoRequerimiento() {
        return formatoRequerimiento;
    }

    public void setFormatoRequerimiento(String formatoRequerimiento) {
        this.formatoRequerimiento = formatoRequerimiento;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigInteger getNumeroCe() {
        return numeroCe;
    }

    public void setNumeroCe(BigInteger numeroCe) {
        this.numeroCe = numeroCe;
    }

    public BigInteger getCantidadPlanilla() {
        return cantidadPlanilla;
    }

    public void setCantidadPlanilla(BigInteger cantidadPlanilla) {
        this.cantidadPlanilla = cantidadPlanilla;
    }

    public BigDecimal getMontoTotalPlanilla() {
        return montoTotalPlanilla;
    }

    public void setMontoTotalPlanilla(BigDecimal montoTotalPlanilla) {
        this.montoTotalPlanilla = montoTotalPlanilla;
    }

    public BigDecimal getMontoPendientePago() {
        return montoPendientePago;
    }

    public void setMontoPendientePago(BigDecimal montoPendientePago) {
        this.montoPendientePago = montoPendientePago;
    }

    public BigDecimal getMontoReintegrar() {
        return montoReintegrar;
    }

    public void setMontoReintegrar(BigDecimal montoReintegrar) {
        this.montoReintegrar = montoReintegrar;
    }

    public BigDecimal getSaldoRequerimiento() {
        return saldoRequerimiento;
    }

    public void setSaldoRequerimiento(BigDecimal saldoRequerimiento) {
        this.saldoRequerimiento = saldoRequerimiento;
    }
}
