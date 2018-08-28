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
@Table(name = "HISTORIAL_CAM_EST_RES_ADJ")
@NamedQueries({
    @NamedQuery(name = "HistorialCamEstResAdj.findAll", query = "SELECT h FROM HistorialCamEstResAdj h")})
public class HistorialCamEstResAdj implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_HISTORIAL_CAM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historial")
    @SequenceGenerator(name="historial", sequenceName = "SEQ_HISTORIAL_CAM", allocationSize=1, initialValue=1)
    private BigDecimal idHistorialCam;
    @Column(name = "ESTADO_ANTERIOR")
    private BigInteger estadoAnterior;
    @Column(name = "ESTADO_NUEVO")
    private BigInteger estadoNuevo;
    @Column(name = "COMENTARIO_HISTORIAL")
    private String comentarioHistorial;
    @Column(name = "ARCHIVO_RESPALDO")
    private String archivoRespaldo;
    @Column(name = "FECHA_CAMBIO_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCambioEstado;
    @Column(name = "USUARIO")
    private String usuario;
    @JoinColumn(name = "ID_RESOLUCION_ADJ", referencedColumnName = "ID_RESOLUCION_ADJ")
    @ManyToOne(fetch = FetchType.EAGER)
    private ResolucionesAdjudicativas idResolucionAdj;

    public HistorialCamEstResAdj() {
    }

    public HistorialCamEstResAdj(BigDecimal idHistorialCam) {
        this.idHistorialCam = idHistorialCam;
    }

    public BigDecimal getIdHistorialCam() {
        return idHistorialCam;
    }

    public void setIdHistorialCam(BigDecimal idHistorialCam) {
        this.idHistorialCam = idHistorialCam;
    }

    public BigInteger getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(BigInteger estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public BigInteger getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(BigInteger estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public String getComentarioHistorial() {
        return comentarioHistorial;
    }

    public void setComentarioHistorial(String comentarioHistorial) {
        this.comentarioHistorial = comentarioHistorial;
    }

    public String getArchivoRespaldo() {
        return archivoRespaldo;
    }

    public void setArchivoRespaldo(String archivoRespaldo) {
        this.archivoRespaldo = archivoRespaldo;
    }

    public Date getFechaCambioEstado() {
        return fechaCambioEstado;
    }

    public void setFechaCambioEstado(Date fechaCambioEstado) {
        this.fechaCambioEstado = fechaCambioEstado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public ResolucionesAdjudicativas getIdResolucionAdj() {
        return idResolucionAdj;
    }

    public void setIdResolucionAdj(ResolucionesAdjudicativas idResolucionAdj) {
        this.idResolucionAdj = idResolucionAdj;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistorialCam != null ? idHistorialCam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialCamEstResAdj)) {
            return false;
        }
        HistorialCamEstResAdj other = (HistorialCamEstResAdj) object;
        if ((this.idHistorialCam == null && other.idHistorialCam != null) || (this.idHistorialCam != null && !this.idHistorialCam.equals(other.idHistorialCam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.HistorialCamEstResAdj[ idHistorialCam=" + idHistorialCam + " ]";
    }
    
}
