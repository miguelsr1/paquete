/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_RESGUARDO")
@XmlRootElement
public class DetalleResguardo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DET_RESGUARDO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_det_resguardo")
    @SequenceGenerator(name = "seq_det_resguardo", sequenceName = "SEQ_DET_RESGUARDO", allocationSize = 1, initialValue = 1)
    private BigDecimal idDetResguardo;
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private BigInteger cantidad;
    @Basic(optional = false)
    @Column(name = "NO_ITEM")
    private String noItem;
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
    @Basic(optional = false)
    @Column(name = "ESTADO_ELIMINACION")
    private short estadoEliminacion;
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CatalogoProducto idProducto;
    @JoinColumn(name = "ID_RESGUARDO", referencedColumnName = "ID_RESGUARDO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Resguardo idResguardo;

    @JoinColumn(name = "ID_NIVEL_EDUCATIVO", referencedColumnName = "ID_NIVEL_EDUCATIVO")
    @ManyToOne(fetch = FetchType.EAGER)
    private NivelEducativo idNivelEducativo;

    public DetalleResguardo() {
    }

    public DetalleResguardo(BigDecimal idDetResguardo) {
        this.idDetResguardo = idDetResguardo;
    }

    public DetalleResguardo(BigDecimal idDetResguardo, BigInteger cantidad, String usuarioInsercion, Date fechaInsercion, short estadoEliminacion) {
        this.idDetResguardo = idDetResguardo;
        this.cantidad = cantidad;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public String getNoItem() {
        return noItem;
    }

    public void setNoItem(String noItem) {
        this.noItem = noItem;
    }

    public BigDecimal getIdDetResguardo() {
        return idDetResguardo;
    }

    public void setIdDetResguardo(BigDecimal idDetResguardo) {
        this.idDetResguardo = idDetResguardo;
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

    public short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public CatalogoProducto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(CatalogoProducto idProducto) {
        this.idProducto = idProducto;
    }

    public Resguardo getIdResguardo() {
        return idResguardo;
    }

    public void setIdResguardo(Resguardo idResguardo) {
        this.idResguardo = idResguardo;
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
        hash += (idDetResguardo != null ? idDetResguardo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleResguardo)) {
            return false;
        }
        DetalleResguardo other = (DetalleResguardo) object;
        if ((this.idDetResguardo == null && other.idDetResguardo != null) || (this.idDetResguardo != null && !this.idDetResguardo.equals(other.idDetResguardo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetalleResguardo[ idDetResguardo=" + idDetResguardo + " ]";
    }

}
