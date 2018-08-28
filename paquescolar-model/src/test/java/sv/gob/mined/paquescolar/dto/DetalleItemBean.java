/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author misanchez
 */
public class DetalleItemBean {
    private static final long serialVersionUID = 1L;
    private String noItem;
    private String descripcion;
    private String razonSocial;
    private String fabricante;
    private BigInteger cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal total;
    private BigDecimal correlativo;

    public DetalleItemBean() {
    }

    public String getNoItem() {
        return noItem;
    }

    public void setNoItem(String noItem) {
        this.noItem = noItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public BigInteger getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(BigDecimal correlativo) {
        this.correlativo = correlativo;
    }
}