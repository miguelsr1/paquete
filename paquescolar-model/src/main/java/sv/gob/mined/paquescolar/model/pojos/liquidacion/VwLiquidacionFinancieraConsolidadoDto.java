/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.mined.paquescolar.model.pojos.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

/**
 *
 * @author misanchez
 */
@Entity
@NamedNativeQuery(
        name = "defaultVwLiquidacionFinancieraConsolidadoDto",
        query = "select id_requerimiento as idRequerimiento,PAGADOR_DEPARTAMENTAL as pagadorDepartamental,numero_cuenta as numeroCuenta,formato_requerimiento as formatoRequerimiento,monto_total as montoRequerimiento,rubro,proceso,sum(nvl(monto_liquidado,0)) montoLiquidado,sum(nvl(monto_pendiente,0)) montoPendiente from vw_liquidacion_finan_con where id_det_proceso_adq = ?1 and codigo_departamento=?2 group by id_requerimiento,pagador_departamental,numero_cuenta,formato_requerimiento,monto_total,rubro,proceso,id_det_proceso_adq,codigo_departamento",
        resultClass = VwLiquidacionFinancieraConsolidadoDto.class)
public class VwLiquidacionFinancieraConsolidadoDto implements Serializable {

    @Id
    private BigDecimal idRequerimiento;
    private String pagadorDepartamental;
    private String numeroCuenta;
    private String formatoRequerimiento;
    private BigDecimal montoRequerimiento;
    private String rubro;
    private String proceso;
    private BigDecimal montoLiquidado;
    private BigDecimal montoPendiente;

    public BigDecimal getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(BigDecimal idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getPagadorDepartamental() {
        return pagadorDepartamental;
    }

    public void setPagadorDepartamental(String pagadorDepartamental) {
        this.pagadorDepartamental = pagadorDepartamental;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getFormatoRequerimiento() {
        return formatoRequerimiento;
    }

    public void setFormatoRequerimiento(String formatoRequerimiento) {
        this.formatoRequerimiento = formatoRequerimiento;
    }

    public BigDecimal getMontoRequerimiento() {
        return montoRequerimiento;
    }

    public void setMontoRequerimiento(BigDecimal montoRequerimiento) {
        this.montoRequerimiento = montoRequerimiento;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public BigDecimal getMontoLiquidado() {
        return montoLiquidado;
    }

    public void setMontoLiquidado(BigDecimal montoLiquidado) {
        this.montoLiquidado = montoLiquidado;
    }

    public BigDecimal getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(BigDecimal montoPendiente) {
        this.montoPendiente = montoPendiente;
    }

}
