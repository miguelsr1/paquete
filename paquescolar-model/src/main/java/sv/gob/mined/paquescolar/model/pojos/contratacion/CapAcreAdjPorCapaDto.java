/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.mined.paquescolar.model.pojos.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author misanchez
 */
@Entity
@SqlResultSetMapping(name = "defaultCapAcreAdjPorCapaDto",
        entities = @EntityResult(entityClass = CapAcreAdjPorCapaDto.class))
public class CapAcreAdjPorCapaDto implements Serializable {

    @Id
    private BigDecimal idMuestraInteres;
    private BigInteger capacidadAcreditada;
    private BigInteger capacidadAdjudicada;
    private BigDecimal porcentajeCapacidad;

    public CapAcreAdjPorCapaDto() {
    }

    public BigDecimal getIdMuestraInteres() {
        return idMuestraInteres;
    }

    public void setIdMuestraInteres(BigDecimal idMuestraInteres) {
        this.idMuestraInteres = idMuestraInteres;
    }

    public BigInteger getCapacidadAcreditada() {
        return capacidadAcreditada;
    }

    public void setCapacidadAcreditada(BigInteger capacidadAcreditada) {
        this.capacidadAcreditada = capacidadAcreditada;
    }

    public BigInteger getCapacidadAdjudicada() {
        return capacidadAdjudicada;
    }

    public void setCapacidadAdjudicada(BigInteger capacidadAdjudicada) {
        this.capacidadAdjudicada = capacidadAdjudicada;
    }

    public BigDecimal getPorcentajeCapacidad() {
        return porcentajeCapacidad;
    }

    public void setPorcentajeCapacidad(BigDecimal porcentajeCapacidad) {
        this.porcentajeCapacidad = porcentajeCapacidad;
    }

}
