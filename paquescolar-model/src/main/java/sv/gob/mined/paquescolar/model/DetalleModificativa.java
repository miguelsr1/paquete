/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.Transient;

/**
 *
 * @author DesarrolloPc
 */
@Entity
@Table(name = "DETALLE_MODIFICATIVA")
@NamedQueries({
    @NamedQuery(name = "DetalleModificativa.findAll", query = "SELECT d FROM DetalleModificativa d")})
public class DetalleModificativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_MODIF")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DETALLE_MODIF")
    @SequenceGenerator(name = "DETALLE_MODIF", sequenceName = "SEQ_DET_MODIFICATIVA", allocationSize = 1, initialValue = 1)
    private Long idDetalleModif;
    @Column(name = "NO_ITEM")
    private String noItem;
    @Column(name = "CANTIDAD_OLD")
    private Integer cantidadOld;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO_UNITARIO_OLD")
    private BigDecimal precioUnitarioOld;
    @Column(name = "CANTIDAD_NEW")
    private Integer cantidadNew;
    @Column(name = "PRECIO_UNITARIO_NEW")
    private BigDecimal precioUnitarioNew;
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
    private short estadoEliminacion;
    @JoinColumn(name = "ID_RESOLUCION_MODIF", referencedColumnName = "ID_RESOLUCION_MODIF")
    @ManyToOne(fetch = FetchType.EAGER)
    private ResolucionesModificativas idResolucionModif;
    @Column(name = "CONSOLIDADO_ESP_TEC")
    private String consolidadoEspTec;
    @Column(name = "ID_PRODUCTO")
    private BigDecimal idProducto;
    @Column(name = "ID_NIVEL_EDUCATIVO")
    private BigDecimal idNivelEducativo;
    @Transient
    private Boolean modificarItem = false;
    @Transient
    private Boolean eliminar = false;

    public DetalleModificativa() {
    }

    public DetalleModificativa(Long idDetalleModif) {
        this.idDetalleModif = idDetalleModif;
    }

    public DetalleModificativa(Long idDetalleModif, String usuarioInsercion, Date fechaInsercion, short estadoEliminacion) {
        this.idDetalleModif = idDetalleModif;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public String getConsolidadoEspTec() {
        return consolidadoEspTec;
    }

    public void setConsolidadoEspTec(String consolidadoEspTecOld) {
        this.consolidadoEspTec = consolidadoEspTecOld;
    }

    public Long getIdDetalleModif() {
        return idDetalleModif;
    }

    public void setIdDetalleModif(Long idDetalleModif) {
        this.idDetalleModif = idDetalleModif;
    }

    public String getNoItem() {
        return noItem;
    }

    public void setNoItem(String noItemOld) {
        this.noItem = noItemOld;
    }

    public Integer getCantidadOld() {
        return cantidadOld;
    }

    public void setCantidadOld(Integer cantidadOld) {
        this.cantidadOld = cantidadOld;
    }

    public BigDecimal getPrecioUnitarioOld() {
        return precioUnitarioOld;
    }

    public void setPrecioUnitarioOld(BigDecimal precioUnitarioOld) {
        this.precioUnitarioOld = precioUnitarioOld;
    }

    public Integer getCantidadNew() {
        return cantidadNew;
    }

    public void setCantidadNew(Integer cantidadNew) {
        this.cantidadNew = cantidadNew;
    }

    public BigDecimal getPrecioUnitarioNew() {
        return precioUnitarioNew;
    }

    public void setPrecioUnitarioNew(BigDecimal precioUnitarioNew) {
        this.precioUnitarioNew = precioUnitarioNew;
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

    public short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public ResolucionesModificativas getIdResolucionModif() {
        return idResolucionModif;
    }

    public void setIdResolucionModif(ResolucionesModificativas idResolucionModif) {
        this.idResolucionModif = idResolucionModif;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleModif != null ? idDetalleModif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleModificativa)) {
            return false;
        }
        DetalleModificativa other = (DetalleModificativa) object;
        if ((this.idDetalleModif == null && other.idDetalleModif != null) || (this.idDetalleModif != null && !this.idDetalleModif.equals(other.idDetalleModif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetalleModificativa[ idDetalleModif=" + idDetalleModif + " ]";
    }

    public Boolean getModificarItem() {
        return modificarItem;
    }

    public void setModificarItem(Boolean modificarItem) {
        this.modificarItem = modificarItem;
    }

    public BigDecimal getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(BigDecimal idProducto) {
        this.idProducto = idProducto;
    }

    public BigDecimal getIdNivelEducativo() {
        return idNivelEducativo;
    }

    public void setIdNivelEducativo(BigDecimal idNivelEducativo) {
        this.idNivelEducativo = idNivelEducativo;
    }
    
    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
        if (this.eliminar) {
            estadoEliminacion = Short.parseShort("1");
        } else {
            estadoEliminacion = Short.parseShort("0");
        }
    }

    public Boolean getEliminar() {
        return estadoEliminacion == 1;
    }
}