/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

/**
 *
 * @author oamartinez
 */
public class Bean {

    private Integer cantidadOfertada;
    private Double precioUnitario;
    private String cantidadAdjudicada;

    public Integer getCantidadOfertada() {
        return cantidadOfertada;
    }

    public void setCantidadOfertada(Integer cantidadOfertada) {
        this.cantidadOfertada = cantidadOfertada;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getCantidadAdjudicada() {
        return cantidadAdjudicada;
    }

    public void setCantidadAdjudicada(String cantidadAdjudicada) {
        this.cantidadAdjudicada = cantidadAdjudicada;
    }
}
