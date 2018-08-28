/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.dashboard;

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
@SqlResultSetMapping(name = "defaultContratacionDto",
        entities = @EntityResult(entityClass = TotalContratadoDto.class))
public class TotalContratadoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String codDepCe;
    private String nomDepCe;
    private BigDecimal cantidad;
    private BigDecimal cantidadNino;
    private BigDecimal cantidadNina;
    private BigDecimal monto;
    private Short estadoEliminacion = 0;

    public TotalContratadoDto() {
    }

    public String getCodDepCe() {
        return codDepCe;
    }

    public void setCodDepCe(String codDepCe) {
        this.codDepCe = codDepCe;
    }

    public String getNomDepCe() {
        return nomDepCe;
    }

    public void setNomDepCe(String nomDepCe) {
        this.nomDepCe = nomDepCe;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Short getEstadoEliminacion() {
        if (estadoEliminacion == null) {
            return Short.parseShort("0");
        }
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(Short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getCantidadNino() {
        return cantidadNino;
    }

    public void setCantidadNino(BigDecimal cantidadNino) {
        this.cantidadNino = cantidadNino;
    }

    public BigDecimal getCantidadNina() {
        return cantidadNina;
    }

    public void setCantidadNina(BigDecimal cantidadNina) {
        this.cantidadNina = cantidadNina;
    }
}
