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
@SqlResultSetMapping(name = "defaultTotalTipoEmpDto",
        entities = @EntityResult(entityClass = TotalTipoEmpDto.class))
public class TotalTipoEmpDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idTipoEmp;
    private String descripcionTipoEmp;
    private BigDecimal cantidadEmp;
    private BigDecimal monto;
    private Short estadoEliminacion = 0;

    public TotalTipoEmpDto() {
    }

    public BigDecimal getIdTipoEmp() {
        return idTipoEmp;
    }

    public void setIdTipoEmp(BigDecimal idTipoEmp) {
        this.idTipoEmp = idTipoEmp;
    }

    public String getDescripcionTipoEmp() {
        return descripcionTipoEmp;
    }

    public void setDescripcionTipoEmp(String descripcionTipoEmp) {
        this.descripcionTipoEmp = descripcionTipoEmp;
    }

    public BigDecimal getCantidadEmp() {
        return cantidadEmp;
    }

    public void setCantidadEmp(BigDecimal cantidadEmp) {
        this.cantidadEmp = cantidadEmp;
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
}
