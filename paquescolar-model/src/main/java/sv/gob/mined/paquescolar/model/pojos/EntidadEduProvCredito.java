/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author misanchez
 */
public class EntidadEduProvCredito {
    private String codigoEntidad;
    private String nombre;
    private BigInteger numeroProveedor;
    private BigDecimal montoContratado;
    private BigDecimal IdContratado;

    public EntidadEduProvCredito() {
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getNumeroProveedor() {
        return numeroProveedor;
    }

    public void setNumeroProveedor(BigInteger numeroProveedor) {
        this.numeroProveedor = numeroProveedor;
    }

    public BigDecimal getMontoContratado() {
        return montoContratado;
    }

    public void setMontoContratado(BigDecimal montoContratado) {
        this.montoContratado = montoContratado;
    }

    public BigDecimal getIdContratado() {
        return IdContratado;
    }

    public void setIdContratado(BigDecimal IdContratado) {
        this.IdContratado = IdContratado;
    }
}