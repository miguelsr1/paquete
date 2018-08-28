/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author misanchez
 */
public class VwDetCotizacion {
    private String descripcionItem;
    private BigInteger cantidadAlumnos;
    private BigDecimal precioUnitario;
    private BigDecimal montoTotal;

    public VwDetCotizacion() {
    }

    public String getDescripcionItem() {
        return descripcionItem;
    }

    public void setDescripcionItem(String descripcionItem) {
        this.descripcionItem = descripcionItem;
    }

    public BigInteger getCantidadAlumnos() {
        return cantidadAlumnos;
    }

    public void setCantidadAlumnos(BigInteger cantidadAlumnos) {
        this.cantidadAlumnos = cantidadAlumnos;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }
    
}
