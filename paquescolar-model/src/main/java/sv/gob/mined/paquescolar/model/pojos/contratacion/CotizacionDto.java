/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.contratacion;

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
@SqlResultSetMapping(name = "defaultCotizacionDto",
        entities = @EntityResult(entityClass = CotizacionDto.class))
public class CotizacionDto implements Serializable {

    @Id
    private BigDecimal idRow;
    private Integer noItemInt;
    private String nombreProducto;
    private BigInteger cantidadSolicitada;
    private BigInteger cantidadResguardo;

    public CotizacionDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public Integer getNoItemInt() {
        return noItemInt;
    }

    public void setNoItemInt(Integer noItemInt) {
        this.noItemInt = noItemInt;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigInteger getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(BigInteger cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public BigInteger getCantidadResguardo() {
        return cantidadResguardo;
    }

    public void setCantidadResguardo(BigInteger cantidadResguardo) {
        this.cantidadResguardo = cantidadResguardo;
    }

}
