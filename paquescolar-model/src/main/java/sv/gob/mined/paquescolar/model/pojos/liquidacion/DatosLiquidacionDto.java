package sv.gob.mined.paquescolar.model.pojos.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author misanchez
 */
public class DatosLiquidacionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idContrato;
    private String noItem;
    private BigDecimal cantidadContrato;
    private BigDecimal cantidadModificativa;
    private BigDecimal cantidadRecepcion;
    private BigDecimal precioUnitarioContrato;
    private BigDecimal precioUnitarioModificativa;
    private BigDecimal cantidadResguardo;

    public DatosLiquidacionDto() {
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

    public BigDecimal getCantidadContrato() {
        return cantidadContrato;
    }

    public void setCantidadContrato(BigDecimal cantidadContrato) {
        this.cantidadContrato = cantidadContrato;
    }

    public BigDecimal getCantidadModificativa() {
        return cantidadModificativa;
    }

    public void setCantidadModificativa(BigDecimal cantidadModificativa) {
        this.cantidadModificativa = cantidadModificativa;
    }

    public BigDecimal getCantidadRecepcion() {
        return cantidadRecepcion;
    }

    public void setCantidadRecepcion(BigDecimal cantidadRecepcion) {
        this.cantidadRecepcion = cantidadRecepcion;
    }

    public BigDecimal getPrecioUnitarioContrato() {
        return precioUnitarioContrato;
    }

    public void setPrecioUnitarioContrato(BigDecimal precioUnitarioContrato) {
        this.precioUnitarioContrato = precioUnitarioContrato;
    }

    public BigDecimal getPrecioUnitarioModificativa() {
        return precioUnitarioModificativa;
    }

    public void setPrecioUnitarioModificativa(BigDecimal precioUnitarioModificativa) {
        this.precioUnitarioModificativa = precioUnitarioModificativa;
    }

    public BigDecimal getCantidadResguardo() {
        return cantidadResguardo;
    }

    public void setCantidadResguardo(BigDecimal cantidadResguardo) {
        this.cantidadResguardo = cantidadResguardo;
    }
}
