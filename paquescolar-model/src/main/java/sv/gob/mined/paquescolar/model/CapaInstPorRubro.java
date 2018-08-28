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

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "CAPA_INST_POR_RUBRO")
@NamedQueries({
    @NamedQuery(name = "CapaInstPorRubro.findAll", query = "SELECT c FROM CapaInstPorRubro c")})
public class CapaInstPorRubro implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CAP_INST_RUBRO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inst_rubro")
    @SequenceGenerator(name = "inst_rubro", sequenceName = "SEQ_CAPA_INST_RUBRO", allocationSize = 1, initialValue = 1)
    private BigDecimal idCapInstRubro;
    @Column(name = "CAPACIDAD_ACREDITADA")
    private BigInteger capacidadAcreditada;
    @Column(name = "CAPACIDAD_ADJUDICADA")
    private BigInteger capacidadAdjudicada;
    @Basic(optional = false)
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
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
    @Column(name = "ESTADO_ELIMINACION")
    private BigInteger estadoEliminacion;
    @JoinColumn(name = "ID_MUESTRA_INTERES", referencedColumnName = "ID_MUESTRA_INTERES")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetRubroMuestraInteres idMuestraInteres;

    public CapaInstPorRubro() {
    }

    public CapaInstPorRubro(BigDecimal idCapInstRubro) {
        this.idCapInstRubro = idCapInstRubro;
    }

    public CapaInstPorRubro(BigDecimal idCapInstRubro, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion) {
        this.idCapInstRubro = idCapInstRubro;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdCapInstRubro() {
        return idCapInstRubro;
    }

    public void setIdCapInstRubro(BigDecimal idCapInstRubro) {
        this.idCapInstRubro = idCapInstRubro;
    }

    public BigInteger getCapacidadAcreditada() {
        return capacidadAcreditada;
    }

    public void setCapacidadAcreditada(BigInteger capacidadAcreditada) {
        this.capacidadAcreditada = capacidadAcreditada;
    }

    public BigInteger getCapacidadAdjudicada() {
        return capacidadAdjudicada;
    }

    public void setCapacidadAdjudicada(BigInteger capacidadAdjudicada) {
        this.capacidadAdjudicada = capacidadAdjudicada;
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

    public BigInteger getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(BigInteger estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public DetRubroMuestraInteres getIdMuestraInteres() {
        return idMuestraInteres;
    }

    public void setIdMuestraInteres(DetRubroMuestraInteres idMuestraInteres) {
        this.idMuestraInteres = idMuestraInteres;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCapInstRubro != null ? idCapInstRubro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CapaInstPorRubro)) {
            return false;
        }
        CapaInstPorRubro other = (CapaInstPorRubro) object;
        if ((this.idCapInstRubro == null && other.idCapInstRubro != null) || (this.idCapInstRubro != null && !this.idCapInstRubro.equals(other.idCapInstRubro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.CapaInstPorRubro[ idCapInstRubro=" + idCapInstRubro + " ]";
    }
}
