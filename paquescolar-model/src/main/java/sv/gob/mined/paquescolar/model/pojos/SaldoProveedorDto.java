/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author misanchez
 */
public class SaldoProveedorDto implements Serializable {

    private String rubro;
    private BigInteger capacidadCalificada = BigInteger.ZERO;
    private BigInteger capacidadAdjudicada = BigInteger.ZERO;
    private BigInteger adjudicadaActual = BigInteger.ZERO;
    private BigInteger saldoCapacidad = BigInteger.ZERO;

    public SaldoProveedorDto() {
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public BigInteger getCapacidadCalificada() {
        return capacidadCalificada;
    }

    public void setCapacidadCalificada(BigInteger capacidadCalificada) {
        this.capacidadCalificada = capacidadCalificada;
    }

    public BigInteger getCapacidadAdjudicada() {
        return capacidadAdjudicada;
    }

    public void setCapacidadAdjudicada(BigInteger capacidadAdjudicada) {
        this.capacidadAdjudicada = capacidadAdjudicada;
    }

    public BigInteger getAdjudicadaActual() {
        return adjudicadaActual;
    }

    public void setAdjudicadaActual(BigInteger adjudicadaActual) {
        this.adjudicadaActual = adjudicadaActual;
    }

    public BigInteger getSaldoCapacidad() {
        return saldoCapacidad;
    }

    public void setSaldoCapacidad(BigInteger saldoCapacidad) {
        this.saldoCapacidad = saldoCapacidad;
    }
}
