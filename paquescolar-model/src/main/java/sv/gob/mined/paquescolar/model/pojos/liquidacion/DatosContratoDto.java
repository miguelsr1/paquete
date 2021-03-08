/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultDatosContratoDto",
        entities = @EntityResult(entityClass = DatosContratoDto.class))
public class DatosContratoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idOferta;
    private String noItem;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal idContrato;
    private BigDecimal idEstadoReserva;
    private String codigoEntidad;
    private Integer idDetProcesoAdq;
    
    public DatosContratoDto() {
    }

    public BigDecimal getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(BigDecimal idOferta) {
        this.idOferta = idOferta;
    }

    public String getNoItem() {
        return noItem;
    }

    public void setNoItem(String noItem) {
        this.noItem = noItem;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public BigDecimal getIdEstadoReserva() {
        return idEstadoReserva;
    }

    public void setIdEstadoReserva(BigDecimal idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public Integer getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(Integer idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

}
