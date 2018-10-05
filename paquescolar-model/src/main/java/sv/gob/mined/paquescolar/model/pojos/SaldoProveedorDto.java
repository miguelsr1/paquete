/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultRptSaldoProveedor",
        entities = @EntityResult(entityClass = SaldoProveedorDto.class))
public class SaldoProveedorDto implements Serializable {

    @Id
    private BigDecimal idRow;
    private String rubro;
    private BigInteger capacidadCalificada = BigInteger.ZERO;
    private BigInteger capacidadAdjudicada = BigInteger.ZERO;
    private BigInteger adjudicadaActual = BigInteger.ZERO;
    private BigInteger saldoCapacidad = BigInteger.ZERO;

    private String numeroNit;
    private String razonSocial;
    private String codigoDepartamento;

    public SaldoProveedorDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
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

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
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
