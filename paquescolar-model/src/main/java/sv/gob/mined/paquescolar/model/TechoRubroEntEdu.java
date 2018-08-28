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
@Table(name = "TECHO_RUBRO_ENT_EDU")
@NamedQueries({
    @NamedQuery(name = "TechoRubroEntEdu.findAll", query = "SELECT t FROM TechoRubroEntEdu t")})
public class TechoRubroEntEdu implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RUBRO_TECHO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "techo")
    @SequenceGenerator(name="techo", sequenceName = "SEQ_TECHO_RUBRO_ENT_EDU", allocationSize=1, initialValue=1)
    private BigDecimal idRubroTecho;
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @Column(name = "MONTO_PRESUPUESTADO")
    private BigDecimal montoPresupuestado;
    @Column(name = "MONTO_ADJUDICADO")
    private BigDecimal montoAdjudicado;
    @Column(name = "MONTO_DISPONIBLE")
    private BigDecimal montoDisponible;
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
    private BigInteger estadoEliminacion;
    @JoinColumn(name = "ID_DET_PROCESO_ADQ", referencedColumnName = "ID_DET_PROCESO_ADQ")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetalleProcesoAdq idDetProcesoAdq;

    public TechoRubroEntEdu() {
    }

    public TechoRubroEntEdu(BigDecimal idRubroTecho) {
        this.idRubroTecho = idRubroTecho;
    }

    public BigDecimal getIdRubroTecho() {
        return idRubroTecho;
    }

    public void setIdRubroTecho(BigDecimal idRubroTecho) {
        this.idRubroTecho = idRubroTecho;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public BigDecimal getMontoPresupuestado() {
        return montoPresupuestado;
    }

    public void setMontoPresupuestado(BigDecimal montoPresupuestado) {
        this.montoPresupuestado = montoPresupuestado;
    }

    public BigDecimal getMontoAdjudicado() {
        return montoAdjudicado;
    }

    public void setMontoAdjudicado(BigDecimal montoAdjudicado) {
        this.montoAdjudicado = montoAdjudicado;
    }

    public BigDecimal getMontoDisponible() {
        return montoDisponible;
    }

    public void setMontoDisponible(BigDecimal montoDisponible) {
        this.montoDisponible = montoDisponible;
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

    public DetalleProcesoAdq getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(DetalleProcesoAdq idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRubroTecho != null ? idRubroTecho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TechoRubroEntEdu)) {
            return false;
        }
        TechoRubroEntEdu other = (TechoRubroEntEdu) object;
        if ((this.idRubroTecho == null && other.idRubroTecho != null) || (this.idRubroTecho != null && !this.idRubroTecho.equals(other.idRubroTecho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.TechoRubroEntEdu[ idRubroTecho=" + idRubroTecho + " ]";
    }
    
}
