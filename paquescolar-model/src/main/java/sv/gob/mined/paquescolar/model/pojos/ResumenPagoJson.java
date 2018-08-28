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
public class ResumenPagoJson {
    private String departamento;
    private String formatoRequerimiento;
    private BigDecimal cantidadPlanilla;
    private BigDecimal cantidadCe;
    private BigDecimal montoTotal;
    private BigDecimal montoPagado;
    private BigDecimal montoPendiente;
    private BigDecimal montoReintegro;
    private BigDecimal montoSaldo;

    public String getFormatoRequerimiento() {
        return formatoRequerimiento;
    }

    public void setFormatoRequerimiento(String formatoRequerimiento) {
        this.formatoRequerimiento = formatoRequerimiento;
    }

    public BigDecimal getCantidadPlanilla() {
        return cantidadPlanilla;
    }

    public void setCantidadPlanilla(BigDecimal cantidadPlanilla) {
        this.cantidadPlanilla = cantidadPlanilla;
    }

    public BigDecimal getCantidadCe() {
        return cantidadCe;
    }

    public void setCantidadCe(BigDecimal cantidadCe) {
        this.cantidadCe = cantidadCe;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public BigDecimal getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(BigDecimal montoPendiente) {
        this.montoPendiente = montoPendiente;
    }

    public BigDecimal getMontoReintegro() {
        return montoReintegro;
    }

    public void setMontoReintegro(BigDecimal montoReintegro) {
        this.montoReintegro = montoReintegro;
    }

    public BigDecimal getMontoSaldo() {
        return montoSaldo;
    }

    public void setMontoSaldo(BigDecimal montoSaldo) {
        this.montoSaldo = montoSaldo;
    }

    public ResumenPagoJson() {
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}