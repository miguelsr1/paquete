package sv.gob.mined.paquescolar.model.pojos.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
        name = "defaultVwLiqFinanDetPlanillaDto",
        query = "select id_contrato as idContrato, transferencia, id_planilla as idPlanilla, tipo_pago as tipoPago, numero_doc_pago as numeroDocPago, fecha_cheque as fechaCheque, tipo_doc as tipoDoc, monto_actual as montoActual, cantidad_actual as cantidadActual, no_doc_pago as noDocPago, fecha_doc_pago as fechaDocPago from vw_liq_finan_det_planilla where id_contrato = ?1",
        resultClass = VwLiqFinanDetPlanillaDto.class)
public class VwLiqFinanDetPlanillaDto implements Serializable {

    @Id
    private BigDecimal idContrato;
    private BigDecimal idPlanilla;
    private String tipoPago;
    private String numeroDocPago;
    private String noDocPago;
    private Short transferencia;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCheque;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDocPago;
    private String tipoDoc;
    private BigDecimal montoActual;
    private BigInteger cantidadActual;

    public VwLiqFinanDetPlanillaDto() {
    }

    public Short getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Short transferencia) {
        this.transferencia = transferencia;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public BigDecimal getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(BigDecimal idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getNumeroDocPago() {
        return numeroDocPago;
    }

    public void setNumeroDocPago(String numeroDocPago) {
        this.numeroDocPago = numeroDocPago;
    }

    public Date getFechaCheque() {
        return fechaCheque;
    }

    public void setFechaCheque(Date fechaCheque) {
        this.fechaCheque = fechaCheque;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public BigDecimal getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(BigDecimal montoActual) {
        this.montoActual = montoActual;
    }

    public BigInteger getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(BigInteger cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public String getNoDocPago() {
        return noDocPago;
    }

    public void setNoDocPago(String noDocPago) {
        this.noDocPago = noDocPago;
    }

    public Date getFechaDocPago() {
        return fechaDocPago;
    }

    public void setFechaDocPago(Date fechaDocPago) {
        this.fechaDocPago = fechaDocPago;
    }

}
