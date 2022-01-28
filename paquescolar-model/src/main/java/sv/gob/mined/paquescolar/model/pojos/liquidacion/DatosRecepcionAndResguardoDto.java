/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.liquidacion;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultDatosRecepcionDto",
        entities = @EntityResult(entityClass = DatosRecepcionAndResguardoDto.class))
public class DatosRecepcionAndResguardoDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    private BigDecimal idRow;
    private java.math.BigDecimal idContrato;
    private String noItem;
    private BigDecimal cantidadEntregada;
    private BigDecimal cantidadResguardo;

    public DatosRecepcionAndResguardoDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public String getNoItem() {
        return noItem;
    }

    public void setNoItem(String noItem) {
        this.noItem = noItem;
    }

    public BigDecimal getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(BigDecimal cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public BigDecimal getCantidadResguardo() {
        return cantidadResguardo;
    }

    public void setCantidadResguardo(BigDecimal cantidadResguardo) {
        this.cantidadResguardo = cantidadResguardo;
    }

}
