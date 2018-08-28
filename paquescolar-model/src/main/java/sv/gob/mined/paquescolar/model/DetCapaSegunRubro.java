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
@Table(name = "DET_CAPA_SEGUN_RUBRO")
@NamedQueries({
    @NamedQuery(name = "DetCapaSegunRubro.findAll", query = "SELECT d FROM DetCapaSegunRubro d")})
public class DetCapaSegunRubro implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_RUBRO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "det_capa_rubro")
    @SequenceGenerator(name = "det_capa_rubro", sequenceName = "SEQ_DET_CAPA_SEGUN_RUBRO", allocationSize = 1, initialValue = 1)
    private BigDecimal idDetalleRubro;
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
    @JoinColumn(name = "ID_NIVEL_EDUCATIVO", referencedColumnName = "ID_NIVEL_EDUCATIVO")
    @ManyToOne(fetch = FetchType.EAGER)
    private NivelEducativo idNivelEducativo;
    @JoinColumn(name = "ID_MUESTRA_INTERES", referencedColumnName = "ID_MUESTRA_INTERES")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetRubroMuestraInteres idMuestraInteres;
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoProducto idProducto;

    public DetCapaSegunRubro() {
    }

    public DetCapaSegunRubro(BigDecimal idDetalleRubro) {
        this.idDetalleRubro = idDetalleRubro;
    }

    public DetCapaSegunRubro(BigDecimal idDetalleRubro, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion) {
        this.idDetalleRubro = idDetalleRubro;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdDetalleRubro() {
        return idDetalleRubro;
    }

    public void setIdDetalleRubro(BigDecimal idDetalleRubro) {
        this.idDetalleRubro = idDetalleRubro;
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

    public NivelEducativo getIdNivelEducativo() {
        return idNivelEducativo;
    }

    public void setIdNivelEducativo(NivelEducativo idNivelEducativo) {
        this.idNivelEducativo = idNivelEducativo;
    }

    public DetRubroMuestraInteres getIdMuestraInteres() {
        return idMuestraInteres;
    }

    public void setIdMuestraInteres(DetRubroMuestraInteres idMuestraInteres) {
        this.idMuestraInteres = idMuestraInteres;
    }

    public CatalogoProducto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(CatalogoProducto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleRubro != null ? idDetalleRubro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetCapaSegunRubro)) {
            return false;
        }
        DetCapaSegunRubro other = (DetCapaSegunRubro) object;
        if ((this.idDetalleRubro == null && other.idDetalleRubro != null) || (this.idDetalleRubro != null && !this.idDetalleRubro.equals(other.idDetalleRubro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetCapaSegunRubro[ idDetalleRubro=" + idDetalleRubro + " ]";
    }
}
