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
        name = "defaultVwLiqFinanConModifDto",
        query = "select id_contrato as idContrato, numero_contrato as numeroContrato, fecha_modificacion as fechaModificacion, cantidad_contrato as cantidadContrato, monto_contrato as montoContrato from vw_liq_finan_con_modif where id_contrato = ?1",
        resultClass = VwLiqFinanConModifDto.class)
public class VwLiqFinanConModifDto implements Serializable {

    @Id
    private BigDecimal idContrato;
    private String numeroContrato;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaModificacion;
    private BigDecimal cantidadContrato;
    private BigDecimal montoContrato;

    public VwLiqFinanConModifDto() {
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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
