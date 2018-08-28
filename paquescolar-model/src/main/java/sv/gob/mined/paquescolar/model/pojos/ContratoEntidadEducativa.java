/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.math.BigDecimal;

/**
 *
 * @author misanchez
 */
public class ContratoEntidadEducativa {
    private String numeroNit;
    private String razonSocial;
    private BigDecimal montoContrato;
    private BigDecimal idDetalle;;
    private Boolean aplicaRenta;
    private BigDecimal idContrato;
    private Boolean esCredito;

    public ContratoEntidadEducativa() {
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

    public BigDecimal getMontoContrato() {
        return montoContrato;
    }

    public void setMontoContrato(BigDecimal montoContrato) {
        this.montoContrato = montoContrato;
    }

    public Boolean getEsCredito() {
        return esCredito;
    }

    public void setEsCredito(Boolean esCredito) {
        this.esCredito = esCredito;
    }

    public Boolean getAplicaRenta() {
        return aplicaRenta;
    }

    public void setAplicaRenta(Boolean aplicaRenta) {
        this.aplicaRenta = aplicaRenta;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public BigDecimal getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(BigDecimal idDetalle) {
        this.idDetalle = idDetalle;
    }
}