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
@Table(name = "ESTADISTICA_CENSO")
@NamedQueries({
    @NamedQuery(name = "EstadisticaCenso.findAll", query = "SELECT e FROM EstadisticaCenso e")})
public class EstadisticaCenso implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ESTADISTICA")
    @GeneratedValue(generator = "SEQ_ESTADISTICA_CENSO", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "SEQ_ESTADISTICA_CENSO", name = "SEQ_ESTADISTICA_CENSO", allocationSize = 1, initialValue = 1)
    private BigDecimal idEstadistica;
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @Column(name = "MASCULINO")
    private BigInteger masculino;
    @Column(name = "FEMENIMO")
    private BigInteger femenimo;
    @Basic(optional = false)
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Basic(optional = false)
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;
    @JoinColumn(name = "ID_PROCESO_ADQ", referencedColumnName = "ID_PROCESO_ADQ")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProcesoAdquisicion idProcesoAdq;
    @JoinColumn(name = "ID_NIVEL_EDUCATIVO", referencedColumnName = "ID_NIVEL_EDUCATIVO")
    @ManyToOne(fetch = FetchType.EAGER)
    private NivelEducativo idNivelEducativo;

    public EstadisticaCenso() {
    }

    public EstadisticaCenso(BigDecimal idEstadistica) {
        this.idEstadistica = idEstadistica;
    }

    public EstadisticaCenso(BigDecimal idEstadistica, Date fechaInsercion, String usuarioInsercion) {
        this.idEstadistica = idEstadistica;
        this.fechaInsercion = fechaInsercion;
        this.usuarioInsercion = usuarioInsercion;
    }

    public BigDecimal getIdEstadistica() {
        return idEstadistica;
    }

    public void setIdEstadistica(BigDecimal idEstadistica) {
        this.idEstadistica = idEstadistica;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public BigInteger getMasculino() {
        if(masculino == null){
            return BigInteger.ZERO;
        }
        return masculino;
    }

    public void setMasculino(BigInteger masculino) {
        this.masculino = masculino;
    }

    public BigInteger getFemenimo() {
        if(femenimo == null){
            return BigInteger.ZERO;
        }
        return femenimo;
    }

    public void setFemenimo(BigInteger femenimo) {
        this.femenimo = femenimo;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
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

    public ProcesoAdquisicion getIdProcesoAdq() {
        return idProcesoAdq;
    }

    public void setIdProcesoAdq(ProcesoAdquisicion idProcesoAdq) {
        this.idProcesoAdq = idProcesoAdq;
    }

    public NivelEducativo getIdNivelEducativo() {
        return idNivelEducativo;
    }

    public void setIdNivelEducativo(NivelEducativo idNivelEducativo) {
        this.idNivelEducativo = idNivelEducativo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadistica != null ? idEstadistica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadisticaCenso)) {
            return false;
        }
        EstadisticaCenso other = (EstadisticaCenso) object;
        if ((this.idEstadistica == null && other.idEstadistica != null) || (this.idEstadistica != null && !this.idEstadistica.equals(other.idEstadistica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.EstadisticaCenso[ idEstadistica=" + idEstadistica + " ]";
    }
    
}
