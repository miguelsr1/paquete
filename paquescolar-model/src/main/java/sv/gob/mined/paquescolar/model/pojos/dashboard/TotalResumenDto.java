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
@SqlResultSetMapping(name = "defaultTotalGeneroEmpDto",
        entities = @EntityResult(entityClass = TotalResumenDto.class))
public class TotalResumenDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private BigDecimal idRow;
    private BigDecimal idGenero;
    private BigDecimal idPersoneria;
    private BigDecimal monto;
    private BigDecimal cantidadEmp;
    private Short estadoEliminacion = 0;

    public TotalResumenDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public BigDecimal getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(BigDecimal idGenero) {
        this.idGenero = idGenero;
    }

    public BigDecimal getIdPersoneria() {
        return idPersoneria;
    }

    public void setIdPersoneria(BigDecimal idPersoneria) {
        this.idPersoneria = idPersoneria;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getCantidadEmp() {
        return cantidadEmp;
    }

    public void setCantidadEmp(BigDecimal cantidadEmp) {
        this.cantidadEmp = cantidadEmp;
    }

    public Short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(Short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }
}
