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
import org.eclipse.persistence.annotations.AdditionalCriteria;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PRECIOS_REF_RUBRO_EMP")
@NamedQueries({
    @NamedQuery(name = "PreciosRefRubroEmp.findAll", query = "SELECT p FROM PreciosRefRubroEmp p")})
@AdditionalCriteria("this.estadoEliminacion = 0")
public class PreciosRefRubroEmp implements Serializable {

    @JoinColumn(name = "ID_MUESTRA_INTERES", referencedColumnName = "ID_MUESTRA_INTERES")
    @ManyToOne(fetch = FetchType.LAZY)
    private DetRubroMuestraInteres idMuestraInteres;

    @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID_EMPRESA")
    @ManyToOne(fetch = FetchType.EAGER)
    private Empresa idEmpresa;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PRECIO_REF_EMP")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "precioRef")
    @SequenceGenerator(name = "precioRef", sequenceName = "SEQ_PRE_REF_RUBRO_EMP", allocationSize = 1, initialValue = 1)
    private BigDecimal idPrecioRefEmp;
    @Column(name = "PRECIO_REFERENCIA")
    private BigDecimal precioReferencia;
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
    @Column(name = "NO_ITEM")
    private String noItem;
    @JoinColumn(name = "ID_NIVEL_EDUCATIVO", referencedColumnName = "ID_NIVEL_EDUCATIVO")
    @ManyToOne(fetch = FetchType.EAGER)
    private NivelEducativo idNivelEducativo;
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoProducto idProducto;

    public PreciosRefRubroEmp() {
    }

    public PreciosRefRubroEmp(BigDecimal idPrecioRefEmp) {
        this.idPrecioRefEmp = idPrecioRefEmp;
    }

    public BigDecimal getIdPrecioRefEmp() {
        return idPrecioRefEmp;
    }

    public void setIdPrecioRefEmp(BigDecimal idPrecioRefEmp) {
        this.idPrecioRefEmp = idPrecioRefEmp;
    }

    public BigDecimal getPrecioReferencia() {
        return precioReferencia;
    }

    public void setPrecioReferencia(BigDecimal precioReferencia) {
        this.precioReferencia = precioReferencia;
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

    public String getNoItem() {
        return noItem;
    }

    public void setNoItem(String noItem) {
        this.noItem = noItem;
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
        hash += (idPrecioRefEmp != null ? idPrecioRefEmp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreciosRefRubroEmp)) {
            return false;
        }
        PreciosRefRubroEmp other = (PreciosRefRubroEmp) object;
        return !((this.idPrecioRefEmp == null && other.idPrecioRefEmp != null) || (this.idPrecioRefEmp != null && !this.idPrecioRefEmp.equals(other.idPrecioRefEmp)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.PreciosRefRubroEmp[ idPrecioRefEmp=" + idPrecioRefEmp + " ]";
    }

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public DetRubroMuestraInteres getIdMuestraInteres() {
        return idMuestraInteres;
    }

    public void setIdMuestraInteres(DetRubroMuestraInteres idMuestraInteres) {
        this.idMuestraInteres = idMuestraInteres;
    }
}
