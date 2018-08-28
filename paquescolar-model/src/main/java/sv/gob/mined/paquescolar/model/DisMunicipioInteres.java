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
@Table(name = "DIS_MUNICIPIO_INTERES")
@NamedQueries({
    @NamedQuery(name = "DisMunicipioInteres.findAll", query = "SELECT d FROM DisMunicipioInteres d")})
public class DisMunicipioInteres implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MUNICIPIO_INTERES")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIS_MUNICIPIO" )
    @SequenceGenerator(name = "SEQ_DIS_MUNICIPIO", sequenceName = "SEQ_DIS_MUNICIPIO", allocationSize = 1, initialValue = 1)
    private BigDecimal idMunicipioInteres;
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
    @JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID_MUNICIPIO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Municipio idMunicipio;
    @JoinColumn(name = "ID_CAPA_DISTRIBUCION", referencedColumnName = "ID_CAPA_DISTRIBUCION")
    @ManyToOne(fetch = FetchType.EAGER)
    private CapaDistribucionAcre idCapaDistribucion;

    public DisMunicipioInteres() {
    }

    public DisMunicipioInteres(BigDecimal idMunicipioInteres) {
        this.idMunicipioInteres = idMunicipioInteres;
    }

    public DisMunicipioInteres(BigDecimal idMunicipioInteres, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion) {
        this.idMunicipioInteres = idMunicipioInteres;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdMunicipioInteres() {
        return idMunicipioInteres;
    }

    public void setIdMunicipioInteres(BigDecimal idMunicipioInteres) {
        this.idMunicipioInteres = idMunicipioInteres;
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

    public Municipio getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Municipio idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public CapaDistribucionAcre getIdCapaDistribucion() {
        return idCapaDistribucion;
    }

    public void setIdCapaDistribucion(CapaDistribucionAcre idCapaDistribucion) {
        this.idCapaDistribucion = idCapaDistribucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMunicipioInteres != null ? idMunicipioInteres.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisMunicipioInteres)) {
            return false;
        }
        DisMunicipioInteres other = (DisMunicipioInteres) object;
        if ((this.idMunicipioInteres == null && other.idMunicipioInteres != null) || (this.idMunicipioInteres != null && !this.idMunicipioInteres.equals(other.idMunicipioInteres))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DisMunicipioInteres[ idMunicipioInteres=" + idMunicipioInteres + " ]";
    }
    
}
