/*
 * To change this template, choose Tools | Templates
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
public class DetalleCreditoBean implements Serializable{
    
    private BigDecimal idDetalle;
    private String codigoEntidad;
    private String nombreCE;
    private String departamentoCE;
    private BigInteger cantidad;
    private BigDecimal monto;
    private Boolean eliminado;

    public DetalleCreditoBean() {
    }

    public BigDecimal getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(BigDecimal idDetalle) {
        this.idDetalle = idDetalle;
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

    public String getDepartamentoCE() {
        return departamentoCE;
    }

    public void setDepartamentoCE(String departamentoCE) {
        this.departamentoCE = departamentoCE;
    }

    public BigInteger getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
}
