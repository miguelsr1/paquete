/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.recepcion;

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
@SqlResultSetMapping(name = "defaultDetalleItems",
        entities = @EntityResult(entityClass = DetalleItems.class))
public class DetalleItems implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private BigDecimal idRow;
    private BigInteger cantidad;
    private String consolidadoEspTec;
    private String noItem;

    public DetalleItems() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public BigInteger getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    public String getConsolidadoEspTec() {
        return consolidadoEspTec;
    }

    public void setConsolidadoEspTec(String consolidadoEspTec) {
        this.consolidadoEspTec = consolidadoEspTec;
    }

    public String getNoItem() {
        return noItem;
    }

    public void setNoItem(String noItem) {
        this.noItem = noItem;
    }

}
