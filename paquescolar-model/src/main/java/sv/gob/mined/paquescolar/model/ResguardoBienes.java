/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "RESGUARDO_BIENES")
@NamedQueries({
    @NamedQuery(name = "ResguardoBienes.findAll", query = "SELECT r FROM ResguardoBienes r")})
public class ResguardoBienes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_RESGUARDO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_resguardo")
    @SequenceGenerator(name = "seq_resguardo", sequenceName = "SEQ_RESGUARDO", allocationSize = 1, initialValue = 1)
    private Long idResguardo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_ENTIDAD")
    private Character codigoEntidad;
    @Column(name = "CANTIDAD")
    private BigInteger cantidad;
    @Size(max = 25)
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Size(max = 25)
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
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private CatalogoProducto idProducto;
    @JoinColumn(name = "ID_DET_PROCESO_ADQ", referencedColumnName = "ID_DET_PROCESO_ADQ")
    @ManyToOne(fetch = FetchType.LAZY)
    private DetalleProcesoAdq idDetProcesoAdq;
    @JoinColumn(name = "ID_NIVEL_EDUCATIVO", referencedColumnName = "ID_NIVEL_EDUCATIVO")
    @ManyToOne(fetch = FetchType.EAGER)
    private NivelEducativo idNivelEducativo;

    public ResguardoBienes() {
    }

    public ResguardoBienes(Long idResguardo) {
        this.idResguardo = idResguardo;
    }

    public ResguardoBienes(Long idResguardo, Character codigoEntidad) {
        this.idResguardo = idResguardo;
        this.codigoEntidad = codigoEntidad;
    }

    public Long getIdResguardo() {
        return idResguardo;
    }

    public void setIdResguardo(Long idResguardo) {
        this.idResguardo = idResguardo;
    }

    public Character getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(Character codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public BigInteger getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
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

    public CatalogoProducto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(CatalogoProducto idProducto) {
        this.idProducto = idProducto;
    }

    public DetalleProcesoAdq getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(DetalleProcesoAdq idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
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
        hash += (idResguardo != null ? idResguardo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResguardoBienes)) {
            return false;
        }
        ResguardoBienes other = (ResguardoBienes) object;
        if ((this.idResguardo == null && other.idResguardo != null) || (this.idResguardo != null && !this.idResguardo.equals(other.idResguardo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.ReguardoBienes[ idResguardo=" + idResguardo + " ]";
    }
    
}
