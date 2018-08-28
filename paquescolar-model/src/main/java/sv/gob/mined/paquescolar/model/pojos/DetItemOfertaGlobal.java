/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.math.BigDecimal;

/**
 *
 * @author misanchez
 */
public class DetItemOfertaGlobal {
    private String descripcionItem;
    private BigDecimal precioMaxReferencia;
    private BigDecimal precioUnitario;

    public String getDescripcionItem() {
        return descripcionItem;
    }

    public void setDescripcionItem(String descripcionItem) {
        this.descripcionItem = descripcionItem;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioMaxReferencia() {
        return precioMaxReferencia;
    }

    public void setPrecioMaxReferencia(BigDecimal precioMaxReferencia) {
        this.precioMaxReferencia = precioMaxReferencia;
    }
}
