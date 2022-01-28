/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
@Table(name = "PARTICIPANTES")
@NamedQueries({
    @NamedQuery(name = "Participantes.findAll", query = "SELECT p FROM Participantes p")})
@AdditionalCriteria("this.estadoEliminacion = 0")
public class Participantes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PARTICIPANTE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participantes")
    @SequenceGenerator(name = "participantes", sequenceName = "SEQ_PARTICIPANTES", allocationSize = 1, initialValue = 1)
    private BigDecimal idParticipante;
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
    @Basic(optional = false)
    @Column(name = "MODIFICATIVA")
    private BigInteger modificativa;
    @OneToMany(mappedBy = "idParticipante", fetch = FetchType.LAZY)
    private List<ResolucionesAdjudicativas> resolucionesAdjudicativasList;
    @JoinColumn(name = "ID_OFERTA", referencedColumnName = "ID_OFERTA")
    @ManyToOne(fetch = FetchType.EAGER)
    private OfertaBienesServicios idOferta;
    @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID_EMPRESA")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Empresa idEmpresa;

    @Column(name = "PORCENTAJE_PRECIO")
    private BigDecimal porcentajePrecio;
    @Column(name = "PORCENTAJE_GEO")
    private BigDecimal porcentajeGeo;
    @Column(name = "PORCENTAJE_CAPACIDAD")
    private BigDecimal porcentajeCapacidad;

    @OneToMany(mappedBy = "idParticipante", fetch = FetchType.LAZY)
    @OrderBy("noItem ASC")
    private List<DetalleOfertas> detalleOfertasList;

    @Transient
    private Boolean eliminar = false;
    @Transient
    private BigInteger cantidad = BigInteger.ZERO;
    @Transient
    private BigDecimal monto = BigDecimal.ZERO;

    public Participantes() {
    }

    public Participantes(BigDecimal idParticipante) {
        this.idParticipante = idParticipante;
    }

    public Participantes(BigDecimal idParticipante, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion, BigInteger modificativa) {
        this.idParticipante = idParticipante;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
        this.modificativa = modificativa;
    }

    public BigDecimal getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(BigDecimal idParticipante) {
        this.idParticipante = idParticipante;
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

    public List<ResolucionesAdjudicativas> getResolucionesAdjudicativasList() {
        return resolucionesAdjudicativasList;
    }

    public void setResolucionesAdjudicativasList(List<ResolucionesAdjudicativas> resolucionesAdjudicativasList) {
        this.resolucionesAdjudicativasList = resolucionesAdjudicativasList;
    }

    public OfertaBienesServicios getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(OfertaBienesServicios idOferta) {
        this.idOferta = idOferta;
    }

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParticipante != null ? idParticipante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participantes)) {
            return false;
        }
        Participantes other = (Participantes) object;
        return !((this.idParticipante == null && other.idParticipante != null) || (this.idParticipante != null && !this.idParticipante.equals(other.idParticipante)));
    }

    @Override
    public String toString() {
        return (idEmpresa == null) ? "" : idEmpresa.toString();
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

    public List<DetalleOfertas> getDetalleOfertasList() {
        return detalleOfertasList;
    }

    public void setDetalleOfertasList(List<DetalleOfertas> detalleOfertasList) {
        this.detalleOfertasList = detalleOfertasList;
    }

    public BigInteger getCantidad() {
        cantidad = BigInteger.ZERO;
        for (DetalleOfertas detalleOfertas : detalleOfertasList) {
            if (detalleOfertas.getEstadoEliminacion().compareTo(BigInteger.ZERO) == 0) {
                cantidad = cantidad.add(detalleOfertas.getCantidad());
            }
        }
        return cantidad;
    }

    public BigDecimal getMonto() {
        monto = BigDecimal.ZERO;
        for (DetalleOfertas detalleOfertas : detalleOfertasList) {
            if (detalleOfertas.getEstadoEliminacion().compareTo(BigInteger.ZERO) == 0) {
                monto = monto.add(detalleOfertas.getPrecioUnitario().multiply(new BigDecimal(detalleOfertas.getCantidad())));
            }
        }
        return monto;
    }

    public BigDecimal getPorcentajePrecio() {
        return porcentajePrecio;
    }

    public void setPorcentajePrecio(BigDecimal porcentajePrecio) {
        this.porcentajePrecio = porcentajePrecio;
    }
    

    public BigDecimal getPorcentajeGeo() {
        return porcentajeGeo;
    }

    public void setPorcentajeGeo(BigDecimal porcentajeGeo) {
        this.porcentajeGeo = porcentajeGeo;
    }

    public BigDecimal getPorcentajeCapacidad() {
        return porcentajeCapacidad;
    }

    public void setPorcentajeCapacidad(BigDecimal porcentajeCapacidad) {
        this.porcentajeCapacidad = porcentajeCapacidad;
    }
}
