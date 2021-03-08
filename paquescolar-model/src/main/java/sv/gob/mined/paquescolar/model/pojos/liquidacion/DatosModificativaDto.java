/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.liquidacion;

import java.math.BigDecimal;
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
@SqlResultSetMapping(name = "defaultDatosModificativaDto",
        entities = @EntityResult(entityClass = DatosModificativaDto.class))
public class DatosModificativaDto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private java.math.BigDecimal idContrato;
    private String noItem;
    private java.math.BigDecimal cantidadNew;
    private java.math.BigDecimal precioUnitarioNew;
    private java.math.BigDecimal idEstadoReserva;

    public DatosModificativaDto() {
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

    public BigDecimal getCantidadNew() {
        return cantidadNew;
    }

    public void setCantidadNew(BigDecimal cantidadNew) {
        this.cantidadNew = cantidadNew;
    }

    public BigDecimal getPrecioUnitarioNew() {
        return precioUnitarioNew;
    }

    public void setPrecioUnitarioNew(BigDecimal precioUnitarioNew) {
        this.precioUnitarioNew = precioUnitarioNew;
    }

    public BigDecimal getIdEstadoReserva() {
        return idEstadoReserva;
    }

    public void setIdEstadoReserva(BigDecimal idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }

}
