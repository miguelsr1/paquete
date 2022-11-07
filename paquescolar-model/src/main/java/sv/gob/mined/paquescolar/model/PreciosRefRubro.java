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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.annotations.AdditionalCriteria;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PRECIOS_REF_RUBRO")
@NamedQueries({
    @NamedQuery(name = "PreciosRefRubro.findAll", query = "SELECT p FROM PreciosRefRubro p")})
@AdditionalCriteria("this.estadoEliminacion = 0")
public class PreciosRefRubro implements Serializable {

    @JoinColumn(name = "ID_ANHO", referencedColumnName = "ID_ANHO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Anho idAnho;
    @JoinColumn(name = "ID_RUBRO_INTERES", referencedColumnName = "ID_RUBRO_INTERES")
    @ManyToOne(fetch = FetchType.LAZY)
    private RubrosAmostrarInteres idRubroInteres;
    
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PRECIO_SEGUN_RUBRO")
    private BigDecimal idPrecioSegunRubro;
    @Column(name = "PRECIO_MAX_MAS")
    private BigDecimal precioMaxMas;
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
    @Column(name = "PRECIO_MAX_FEM")
    private BigDecimal precioMaxFem;
    @JoinColumn(name = "ID_NIVEL_EDUCATIVO", referencedColumnName = "ID_NIVEL_EDUCATIVO")
    @ManyToOne(fetch = FetchType.EAGER)
    private NivelEducativo idNivelEducativo;
    @Column(name = "CLIMA_FRIO")
    private Short climaFrio;

    public PreciosRefRubro() {
    }

    public PreciosRefRubro(BigDecimal idPrecioSegunRubro) {
        this.idPrecioSegunRubro = idPrecioSegunRubro;
    }

    public BigDecimal getIdPrecioSegunRubro() {
        return idPrecioSegunRubro;
    }

    public void setIdPrecioSegunRubro(BigDecimal idPrecioSegunRubro) {
        this.idPrecioSegunRubro = idPrecioSegunRubro;
    }

    public BigDecimal getPrecioMaxMas() {
        if(precioMaxMas == null){
            return BigDecimal.ZERO;
        }
        return precioMaxMas;
    }

    public void setPrecioMaxMas(BigDecimal precioMaxMas) {
        this.precioMaxMas = precioMaxMas;
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

    public BigDecimal getPrecioMaxFem() {
        if(precioMaxFem == null){
            return BigDecimal.ZERO;
        }
        return precioMaxFem;
    }

    public void setPrecioMaxFem(BigDecimal precioMaxFem) {
        this.precioMaxFem = precioMaxFem;
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
        hash += (idPrecioSegunRubro != null ? idPrecioSegunRubro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreciosRefRubro)) {
            return false;
        }
        PreciosRefRubro other = (PreciosRefRubro) object;
        return !((this.idPrecioSegunRubro == null && other.idPrecioSegunRubro != null) || (this.idPrecioSegunRubro != null && !this.idPrecioSegunRubro.equals(other.idPrecioSegunRubro)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.PreciosRefRubro[ idPrecioSegunRubro=" + idPrecioSegunRubro + " ]";
    }

    public Anho getIdAnho() {
        return idAnho;
    }

    public void setIdAnho(Anho idAnho) {
        this.idAnho = idAnho;
    }

    public RubrosAmostrarInteres getIdRubroInteres() {
        return idRubroInteres;
    }

    public void setIdRubroInteres(RubrosAmostrarInteres idRubroInteres) {
        this.idRubroInteres = idRubroInteres;
    }

    public Short getClimaFrio() {
        return climaFrio;
    }

    public void setClimaFrio(Short climaFrio) {
        this.climaFrio = climaFrio;
    }
}