/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.Transient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_CREDITO")
@NamedQueries({
    @NamedQuery(name = "DetalleCredito.findAll", query = "SELECT d FROM DetalleCredito d")})
public class DetalleCredito implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_credito")
    @SequenceGenerator(name = "detalle_credito", sequenceName = "SEQ_DET_CREDITO", allocationSize = 1, initialValue = 1)
    private BigDecimal idDetalle;
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @Basic(optional = false)
    @Column(name = "ESTADO_ELIMINACION")
    private BigInteger estadoEliminacion;
    @Basic(optional = false)
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
    @Basic(optional = false)
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @JoinColumn(name = "ID_CREDITO", referencedColumnName = "ID_CREDITO")
    @ManyToOne(fetch = FetchType.EAGER)
    private CreditoBancario idCredito;
    @JoinColumn(name = "ID_CONTRATO", referencedColumnName = "ID_CONTRATO")
    @ManyToOne(fetch = FetchType.EAGER)
    private ContratosOrdenesCompras idContrato;
    @Transient
    private Boolean eliminado = false;

    public DetalleCredito() {
    }

    public DetalleCredito(BigDecimal idDetalle) {
        this.idDetalle = idDetalle;
    }

    public DetalleCredito(BigDecimal idDetalle, BigInteger estadoEliminacion, Date fechaInsercion, String usuarioInsercion) {
        this.idDetalle = idDetalle;
        this.estadoEliminacion = estadoEliminacion;
        this.fechaInsercion = fechaInsercion;
        this.usuarioInsercion = usuarioInsercion;
    }

    public BigDecimal getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(BigDecimal idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public BigInteger getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(BigInteger estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
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

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public CreditoBancario getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(CreditoBancario idCredito) {
        this.idCredito = idCredito;
    }

    public ContratosOrdenesCompras getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratosOrdenesCompras idContrato) {
        this.idContrato = idContrato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalle != null ? idDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleCredito)) {
            return false;
        }
        DetalleCredito other = (DetalleCredito) object;
        if ((this.idDetalle == null && other.idDetalle != null) || (this.idDetalle != null && !this.idDetalle.equals(other.idDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetalleCredito[ idDetalle=" + idDetalle + " ]";
    }

    public Boolean getEliminado() {
        eliminado = estadoEliminacion.compareTo(BigInteger.ONE) == 0;
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
        if (this.eliminado) {
            estadoEliminacion = BigInteger.ONE;
        } else {
            estadoEliminacion = BigInteger.ZERO;
        }
    }
}
