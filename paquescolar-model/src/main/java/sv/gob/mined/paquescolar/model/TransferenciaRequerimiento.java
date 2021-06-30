/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "TRANSFERENCIA_REQUERIMIENTO")
@NamedQueries({
    @NamedQuery(name = "TransferenciaRequerimiento.findAll", query = "SELECT t FROM TransferenciaRequerimiento t")})
public class TransferenciaRequerimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TRANSFERENCIA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANSFERENCIA")
    @SequenceGenerator(name = "SEQ_TRANSFERENCIA", sequenceName = "SEQ_TRANSFERENCIA", allocationSize = 1, initialValue = 1)
    private BigDecimal idTransferencia;
    @Column(name = "MONTO_TRANSFERIDO")
    private BigDecimal montoTransferido;
    @Column(name = "FECHA_TRANSFERENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTransferencia;
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;
    @JoinColumn(name = "ID_REQUERIMIENTO", referencedColumnName = "ID_REQUERIMIENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private RequerimientoFondos idRequerimiento;

    public TransferenciaRequerimiento() {
    }

    public TransferenciaRequerimiento(BigDecimal idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public BigDecimal getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(BigDecimal idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public BigDecimal getMontoTransferido() {
        return montoTransferido;
    }

    public void setMontoTransferido(BigDecimal montoTransferido) {
        this.montoTransferido = montoTransferido;
    }

    public Date getFechaTransferencia() {
        return fechaTransferencia;
    }

    public void setFechaTransferencia(Date fechaTransferencia) {
        this.fechaTransferencia = fechaTransferencia;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public Short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(Short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public RequerimientoFondos getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(RequerimientoFondos idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransferencia != null ? idTransferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransferenciaRequerimiento)) {
            return false;
        }
        TransferenciaRequerimiento other = (TransferenciaRequerimiento) object;
        return !((this.idTransferencia == null && other.idTransferencia != null) || (this.idTransferencia != null && !this.idTransferencia.equals(other.idTransferencia)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.TransferenciaRequerimiento[ idTransferencia=" + idTransferencia + " ]";
    }

}
