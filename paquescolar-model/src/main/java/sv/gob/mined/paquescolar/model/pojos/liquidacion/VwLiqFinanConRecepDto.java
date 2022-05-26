package sv.gob.mined.paquescolar.model.pojos.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Temporal;

/**
 *
 * @author misanchez
 */
@Entity
@NamedNativeQuery(
        name = "defaultVwLiqFinanConRecepDto",
        query = "select cantidad_total as cantidadContrato, monto_total as montoContrato, estado_reserva as estadoReserva, estado_recepcion as estadoRecepcion, id_contrato as idContrato, fecha_recepcion as fechaRecepcion from VW_LIQ_FINAN_CON_RECEP where id_contrato = ?1",
        resultClass = VwLiqFinanConRecepDto.class)
public class VwLiqFinanConRecepDto implements Serializable {
    @Id
    private BigDecimal idContrato;
    private String estadoReserva;
    private String estadoRecepcion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRecepcion;
    private BigDecimal cantidadContrato;
    private BigDecimal montoContrato;

    public VwLiqFinanConRecepDto() {
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public String getEstadoRecepcion() {
        return estadoRecepcion;
    }

    public void setEstadoRecepcion(String estadoRecepcion) {
        this.estadoRecepcion = estadoRecepcion;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public BigDecimal getCantidadContrato() {
        return cantidadContrato;
    }

    public void setCantidadContrato(BigDecimal cantidadContrato) {
        this.cantidadContrato = cantidadContrato;
    }

    public BigDecimal getMontoContrato() {
        return montoContrato;
    }

    public void setMontoContrato(BigDecimal montoContrato) {
        this.montoContrato = montoContrato;
    }
}
