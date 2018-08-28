/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.modificativa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DesarrolloPc
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultContratoModificatio",
        entities = @EntityResult(entityClass = VwContratoModificatoria.class))
public class VwContratoModificatoria implements Serializable{

    @Id
    private BigDecimal idRow;
    private BigDecimal idResolucionModif;
    private BigDecimal idEstadoReserva;
    private String descripcionReserva;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInsercion;
    private Integer cantidad;
    private BigDecimal monto;
    private BigDecimal idContrato;
    private BigDecimal idResModifPadre;
    private BigDecimal idResolucionAdj;

    public VwContratoModificatoria() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public BigDecimal getIdResolucionModif() {
        return idResolucionModif;
    }

    public void setIdResolucionModif(BigDecimal idResolucionModif) {
        this.idResolucionModif = idResolucionModif;
    }

    public BigDecimal getIdEstadoReserva() {
        return idEstadoReserva;
    }

    public void setIdEstadoReserva(BigDecimal idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }

    public String getDescripcionReserva() {
        return descripcionReserva;
    }

    public void setDescripcionReserva(String descripcionReserva) {
        this.descripcionReserva = descripcionReserva;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public BigDecimal getIdResModifPadre() {
        return idResModifPadre;
    }

    public void setIdResModifPadre(BigDecimal idResModifPadre) {
        this.idResModifPadre = idResModifPadre;
    }

    public BigDecimal getIdResolucionAdj() {
        return idResolucionAdj;
    }

    public void setIdResolucionAdj(BigDecimal idResolucionAdj) {
        this.idResolucionAdj = idResolucionAdj;
    }
}
