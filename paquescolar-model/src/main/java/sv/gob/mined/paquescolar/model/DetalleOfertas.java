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
import org.eclipse.persistence.annotations.AdditionalCriteria;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_OFERTAS")
@NamedQueries({
    @NamedQuery(name = "DetalleOfertas.findAll", query = "SELECT d FROM DetalleOfertas d")})
@AdditionalCriteria("this.estadoEliminacion = 0")
public class DetalleOfertas implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_OFE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_oferta")
    @SequenceGenerator(name = "detalle_oferta", sequenceName = "SEQ_DET_OFERTA", allocationSize = 1, initialValue = 1)
    private BigDecimal idDetalleOfe;
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private BigInteger cantidad;
    @Basic(optional = false)
    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;
    @Basic(optional = false)
    @Column(name = "CONSOLIDADO_ESP_TEC")
    private String consolidadoEspTec;
    @Basic(optional = false)
    @Column(name = "NO_ITEM")
    private String noItem;
    @Basic(optional = false)
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "ESTILO_ZAPATO")
    private String estiloZapato;
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
    @Basic(optional = false)
    @Column(name = "MODIFICATIVA")
    private BigInteger modificativa;
    @Column(name = "CANTIDAD_OFERTADA")
    private BigInteger cantidadOfertada;
    @JoinColumn(name = "ID_PARTICIPANTE", referencedColumnName = "ID_PARTICIPANTE")
    @ManyToOne(fetch = FetchType.EAGER)
    private Participantes idParticipante;
    @JoinColumn(name = "ID_NIVEL_EDUCATIVO", referencedColumnName = "ID_NIVEL_EDUCATIVO")
    @ManyToOne(fetch = FetchType.EAGER)
    private NivelEducativo idNivelEducativo;
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoProducto idProducto;
    @Transient
    private Boolean eliminar = false;
    @Transient
    private Integer noItemInt;

    public DetalleOfertas() {
    }

    public DetalleOfertas(BigDecimal idDetalleOfe) {
        this.idDetalleOfe = idDetalleOfe;
    }

    public String getEstiloZapato() {
        return estiloZapato;
    }

    public void setEstiloZapato(String estiloZapato) {
        this.estiloZapato = estiloZapato;
    }

    public DetalleOfertas(BigDecimal idDetalleOfe, BigInteger cantidad, BigDecimal precioUnitario, String consolidadoEspTec, String noItem, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion, BigInteger modificativa) {
        this.idDetalleOfe = idDetalleOfe;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.consolidadoEspTec = consolidadoEspTec;
        this.noItem = noItem;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
        this.modificativa = modificativa;
    }

    public BigDecimal getIdDetalleOfe() {
        return idDetalleOfe;
    }

    public void setIdDetalleOfe(BigDecimal idDetalleOfe) {
        this.idDetalleOfe = idDetalleOfe;
    }

    public BigInteger getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getConsolidadoEspTec() {
        return consolidadoEspTec;
    }

    public void setConsolidadoEspTec(String consolidadoEspTec) {
        this.consolidadoEspTec = consolidadoEspTec;
    }

    public String getNoItem() {
        return noItem;
    }

    public void setNoItem(String noItem) {
        this.noItem = noItem;
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

    public BigInteger getModificativa() {
        return modificativa;
    }

    public void setModificativa(BigInteger modificativa) {
        this.modificativa = modificativa;
    }

    public BigInteger getCantidadOfertada() {
        return cantidadOfertada;
    }

    public void setCantidadOfertada(BigInteger cantidadOfertada) {
        this.cantidadOfertada = cantidadOfertada;
    }

    public Participantes getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Participantes idParticipante) {
        this.idParticipante = idParticipante;
    }

    public NivelEducativo getIdNivelEducativo() {
        return idNivelEducativo;
    }

    public void setIdNivelEducativo(NivelEducativo idNivelEducativo) {
        this.idNivelEducativo = idNivelEducativo;
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
        hash += (idDetalleOfe != null ? idDetalleOfe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleOfertas)) {
            return false;
        }
        DetalleOfertas other = (DetalleOfertas) object;
        return !((this.idDetalleOfe == null && other.idDetalleOfe != null) || (this.idDetalleOfe != null && !this.idDetalleOfe.equals(other.idDetalleOfe)));
    }

    @Override
    public String toString() {
        return noItem + " - " + consolidadoEspTec;
    }

    public Integer getNoItemInt() {
        if (noItem == null) {
            return 0;
        }
        return Integer.parseInt(noItem);
    }

    public void setNoItemInt(Integer noItemInt) {
        this.noItemInt = noItemInt;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
        if (this.eliminar) {
            estadoEliminacion = BigInteger.ONE;
        } else {
            estadoEliminacion = BigInteger.ZERO;
        }
    }

    public Boolean getEliminar() {
        return estadoEliminacion.compareTo(BigInteger.ONE) == 0;
    }
}
