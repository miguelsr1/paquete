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
import javax.persistence.CascadeType;
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
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "OFERTA_BIENES_SERVICIOS")
@NamedQueries({
    @NamedQuery(name = "OfertaBienesServicios.findAll", query = "SELECT o FROM OfertaBienesServicios o")})
public class OfertaBienesServicios implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OFERTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oferta")
    @SequenceGenerator(name="oferta", sequenceName = "SEQ_OFERTA_BS", allocationSize=1, initialValue=1)
    private BigDecimal idOferta;
    
    @JoinColumn(name = "CODIGO_ENTIDAD", referencedColumnName = "CODIGO_ENTIDAD")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private VwCatalogoEntidadEducativa codigoEntidad;
    
    @Column(name = "FECHA_APERTURA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaApertura;
    @Column(name = "HORA_APERTURA")
    private BigInteger horaApertura;
    @Column(name = "MINUTO_APERTURA")
    private BigInteger minutoApertura;
    @Column(name = "JUSTIFICACION")
    private String justificacion;
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
    @JoinColumn(name = "ID_DET_PROCESO_ADQ", referencedColumnName = "ID_DET_PROCESO_ADQ")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private DetalleProcesoAdq idDetProcesoAdq;
    @OneToMany(mappedBy = "idOferta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("idParticipante ASC")
    private List<Participantes> participantesList;
    

    public OfertaBienesServicios() {
    }

    public OfertaBienesServicios(BigDecimal idOferta) {
        this.idOferta = idOferta;
    }

    public BigDecimal getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(BigDecimal idOferta) {
        this.idOferta = idOferta;
    }

    public VwCatalogoEntidadEducativa getCodigoEntidad() {
        if(codigoEntidad == null){
            codigoEntidad = new VwCatalogoEntidadEducativa();
        }
        return codigoEntidad;
    }

    public void setCodigoEntidad(VwCatalogoEntidadEducativa codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public BigInteger getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(BigInteger horaApertura) {
        this.horaApertura = horaApertura;
    }

    public BigInteger getMinutoApertura() {
        return minutoApertura;
    }

    public void setMinutoApertura(BigInteger minutoApertura) {
        this.minutoApertura = minutoApertura;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
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

    public DetalleProcesoAdq getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(DetalleProcesoAdq idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    public List<Participantes> getParticipantesList() {
        return participantesList;
    }

    public void setParticipantesList(List<Participantes> participantesList) {
        this.participantesList = participantesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOferta != null ? idOferta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OfertaBienesServicios)) {
            return false;
        }
        OfertaBienesServicios other = (OfertaBienesServicios) object;
        if ((this.idOferta == null && other.idOferta != null) || (this.idOferta != null && !this.idOferta.equals(other.idOferta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.OfertaBienesServicios[ idOferta=" + idOferta + " ]";
    }
}
