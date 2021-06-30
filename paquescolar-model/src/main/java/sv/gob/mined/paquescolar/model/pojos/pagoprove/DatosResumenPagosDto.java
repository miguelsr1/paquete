/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.pagoprove;

import java.io.Serializable;
import java.math.BigDecimal;
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
@SqlResultSetMapping(name = "defaultDatoResumenPagosDto",
        entities = @EntityResult(entityClass = DatosResumenPagosDto.class))
public class DatosResumenPagosDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idRow;
    private String formatoRequerimiento;
    private String codigoDepartamento;
    private BigDecimal cantidadPlanilla;
    private BigDecimal cantidadCe;
    private BigDecimal montoTotal;
    private BigDecimal montoTransferido;
    private BigDecimal montoPagado;
    private BigDecimal montoPendiente;
    private BigDecimal montoReintegro;
    private BigDecimal montoSaldo;

    public DatosResumenPagosDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getFormatoRequerimiento() {
        return formatoRequerimiento;
    }

    public void setFormatoRequerimiento(String formatoRequerimiento) {
        this.formatoRequerimiento = formatoRequerimiento;
    }

    public BigDecimal getCantidadPlanilla() {
        return cantidadPlanilla;
    }

    public void setCantidadPlanilla(BigDecimal cantidadPlanilla) {
        this.cantidadPlanilla = cantidadPlanilla;
    }

    public BigDecimal getCantidadCe() {
        return cantidadCe;
    }

    public void setCantidadCe(BigDecimal cantidadCe) {
        this.cantidadCe = cantidadCe;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public BigDecimal getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(BigDecimal montoPendiente) {
        this.montoPendiente = montoPendiente;
    }

    public BigDecimal getMontoReintegro() {
        return montoReintegro;
    }

    public void setMontoReintegro(BigDecimal montoReintegro) {
        this.montoReintegro = montoReintegro;
    }

    public BigDecimal getMontoTransferido() {
        return montoTransferido;
    }

    public void setMontoTransferido(BigDecimal montoTransferido) {
        this.montoTransferido = montoTransferido;
    }

    public BigDecimal getMontoSaldo() {
        return montoSaldo;
    }

    public void setMontoSaldo(BigDecimal montoSaldo) {
        this.montoSaldo = montoSaldo;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }
}