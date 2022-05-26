package sv.gob.mined.paquescolar.model.pojos.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import sv.gob.mined.paquescolar.model.LiquidacionDetalleDonacion;

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

    private Long totalDonacion = 0l;

    private List<LiquidacionDetalleDonacion> detDonacion = new ArrayList();

    public DatosLiquidacionDto() {
    }

    public List<LiquidacionDetalleDonacion> getDetDonacion() {
        if (detDonacion == null) {
            detDonacion = new ArrayList();
        }
        return detDonacion;
    }

    public void setDetDonacion(List<LiquidacionDetalleDonacion> detDonacion) {
        this.detDonacion = detDonacion;
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
        if (cantidadRecepcion == null) {
            return BigDecimal.ZERO;
        }
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

    public Long getTotalDonacion() {
        totalDonacion = 0l;
        for (LiquidacionDetalleDonacion liquidacionDetalleDonacion : getDetDonacion()) {
            totalDonacion += liquidacionDetalleDonacion.getCantidad();
        }
        return totalDonacion;
    }

    public void setTotalDonacion(Long totalDonacion) {
        this.totalDonacion = totalDonacion;
    }

    public void setCantidadResguardo(BigDecimal cantidadResguardo) {
        this.cantidadResguardo = cantidadResguardo;
    }
}
