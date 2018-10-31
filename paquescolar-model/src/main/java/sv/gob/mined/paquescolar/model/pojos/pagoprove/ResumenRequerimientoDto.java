/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.pagoprove;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultResumenRequerimientoDto",
        entities = @EntityResult(entityClass = ResumenRequerimientoDto.class))
public class ResumenRequerimientoDto implements Serializable{
    @Id
    private BigDecimal idRow;
    private BigDecimal idRequerimiento;
    private String concepto;
    private String formatoRequerimiento;
    private BigDecimal montoTotal;
    private BigInteger cantidadPlanilla;
    private BigDecimal montoTotalPlanilla;
    private BigDecimal montoPendientePago;
    private BigDecimal montoReintegrar;
    private BigDecimal saldoRequerimiento;

    public ResumenRequerimientoDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public BigDecimal getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(BigDecimal idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFormatoRequerimiento() {
        return formatoRequerimiento;
    }

    public void setFormatoRequerimiento(String formatoRequerimiento) {
        this.formatoRequerimiento = formatoRequerimiento;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigInteger getCantidadPlanilla() {
        return cantidadPlanilla;
    }

    public void setCantidadPlanilla(BigInteger cantidadPlanilla) {
        this.cantidadPlanilla = cantidadPlanilla;
    }

    public BigDecimal getMontoTotalPlanilla() {
        return montoTotalPlanilla;
    }

    public void setMontoTotalPlanilla(BigDecimal montoTotalPlanilla) {
        this.montoTotalPlanilla = montoTotalPlanilla;
    }

    public BigDecimal getMontoPendientePago() {
        return montoPendientePago;
    }

    public void setMontoPendientePago(BigDecimal montoPendientePago) {
        this.montoPendientePago = montoPendientePago;
    }

    public BigDecimal getMontoReintegrar() {
        return montoReintegrar;
    }

    public void setMontoReintegrar(BigDecimal montoReintegrar) {
        this.montoReintegrar = montoReintegrar;
    }

    public BigDecimal getSaldoRequerimiento() {
        return saldoRequerimiento;
    }

    public void setSaldoRequerimiento(BigDecimal saldoRequerimiento) {
        this.saldoRequerimiento = saldoRequerimiento;
    }
}
