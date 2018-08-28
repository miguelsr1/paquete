/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author RCeron
 */
public class GraficoTipoEmpresaDTO implements Serializable {

    private BigDecimal total;
    private String nombretipoEmpresa;
    private BigDecimal totaltipoEmp;
    private String totalFormatedo;

    public GraficoTipoEmpresaDTO() {
    }

    public String getNombretipoEmpresa() {
        return nombretipoEmpresa;
    }

    public void setNombretipoEmpresa(String nombretipoEmpresa) {
        this.nombretipoEmpresa = nombretipoEmpresa;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotaltipoEmp() {
        return totaltipoEmp;
    }

    public void setTotaltipoEmp(BigDecimal totaltipoEmp) {
        this.totaltipoEmp = totaltipoEmp;
    }

    public String getTotalFormatedo() {
        return totalFormatedo;
    }

    public void setTotalFormatedo(String totalFormatedo) {
        this.totalFormatedo = totalFormatedo;
    }

    @Override
    public String toString() {
        return "GraficoTipoEmpresa{" + "total=" + total + ", tipoEmpresa=" + nombretipoEmpresa + '}';
    }
}
