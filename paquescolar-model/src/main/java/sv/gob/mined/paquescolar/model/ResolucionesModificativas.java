/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;

/**
 *
 * @author DesarrolloPc
 */
@Entity
@Table(name = "RESOLUCIONES_MODIFICATIVAS")
@NamedQueries({
    @NamedQuery(name = "ResolucionesModificativas.findAll", query = "SELECT r FROM ResolucionesModificativas r")})
public class ResolucionesModificativas implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idResModifPadre", fetch = FetchType.LAZY)
    private List<ResolucionesModificativas> resolucionesModificativasList;
    @JoinColumn(name = "ID_RES_MODIF_PADRE", referencedColumnName = "ID_RESOLUCION_MODIF")
    @ManyToOne(fetch = FetchType.EAGER)
    private ResolucionesModificativas idResModifPadre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "JUSTIFICACION_MODIFICATIVA")
    private String justificacionModificativa;
    @JoinColumn(name = "ID_MODIF_CONTRATO", referencedColumnName = "ID_MODIF_CONTRATO")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoModifContrato idModifContrato;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RESOLUCION_MODIF")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resolucion_modif")
    @SequenceGenerator(name = "resolucion_modif", sequenceName = "SEQ_RESOLUCION_MODIF", allocationSize = 1, initialValue = 1)
    private BigDecimal idResolucionModif;
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
    @OneToMany(mappedBy = "idResolucionModif", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("noItem ASC")
    private List<DetalleModificativa> detalleModificativaList;
    @Column(name = "ID_ESTADO_RESERVA")
    private BigDecimal idEstadoReserva;
    @JoinColumn(name = "ID_CONTRATO", referencedColumnName = "ID_CONTRATO")
    @ManyToOne(fetch = FetchType.EAGER)
    private ContratosOrdenesCompras idContrato;
    @Transient
    private BigDecimal montoContratoContratoOld;
    @Column(name = "FECHA_SOLICITUD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    @Column(name = "FECHA_NOTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNota;
    @Column(name = "FECHA_RESOLUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaResolucion;
    @Column(name = "DIAS_PRORROGA")
    private Integer diasProrroga;

    public ResolucionesModificativas() {
    }

    public ResolucionesModificativas(BigDecimal idResolucionModif) {
        this.idResolucionModif = idResolucionModif;
    }

    public ResolucionesModificativas(BigDecimal idResolucionModif, BigDecimal valor, String usuarioInsercion, Date fechaInsercion, short estadoEliminacion) {
        this.idResolucionModif = idResolucionModif;
        this.valor = valor;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdResolucionModif() {
        return idResolucionModif;
    }

    public void setIdResolucionModif(BigDecimal idResolucionModif) {
        this.idResolucionModif = idResolucionModif;
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

    public List<DetalleModificativa> getDetalleModificativaList() {
        return detalleModificativaList;
    }

    public void setDetalleModificativaList(List<DetalleModificativa> detalleModificativaList) {
        this.detalleModificativaList = detalleModificativaList;
    }

    public BigDecimal getIdEstadoReserva() {
        return idEstadoReserva;
    }

    public void setIdEstadoReserva(BigDecimal idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }

    public ContratosOrdenesCompras getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratosOrdenesCompras idContrato) {
        this.idContrato = idContrato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResolucionModif != null ? idResolucionModif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResolucionesModificativas)) {
            return false;
        }
        ResolucionesModificativas other = (ResolucionesModificativas) object;
        if ((this.idResolucionModif == null && other.idResolucionModif != null) || (this.idResolucionModif != null && !this.idResolucionModif.equals(other.idResolucionModif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.ResolucionesModificativas[ idResolucionModif=" + idResolucionModif + " ]";
    }

    public String getJustificacionModificativa() {
        return justificacionModificativa;
    }

    public void setJustificacionModificativa(String justificacionModificativa) {
        this.justificacionModificativa = justificacionModificativa;
    }

    public TipoModifContrato getIdModifContrato() {
        return idModifContrato;
    }

    public void setIdModifContrato(TipoModifContrato idModifContrato) {
        this.idModifContrato = idModifContrato;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public List<ResolucionesModificativas> getResolucionesModificativasList() {
        return resolucionesModificativasList;
    }

    public void setResolucionesModificativasList(List<ResolucionesModificativas> resolucionesModificativasList) {
        this.resolucionesModificativasList = resolucionesModificativasList;
    }

    public ResolucionesModificativas getIdResModifPadre() {
        return idResModifPadre;
    }

    public void setIdResModifPadre(ResolucionesModificativas idResModifPadre) {
        this.idResModifPadre = idResModifPadre;
    }

    public BigDecimal getMontoContratoContratoOld() {
        montoContratoContratoOld = BigDecimal.ZERO;
        for (DetalleModificativa detalle : getDetalleModificativaList()) {
            montoContratoContratoOld = montoContratoContratoOld.add(detalle.getPrecioUnitarioOld().multiply(new BigDecimal(detalle.getCantidadOld())));
        }
        return montoContratoContratoOld;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaNota() {
        return fechaNota;
    }

    public void setFechaNota(Date fechaNota) {
        this.fechaNota = fechaNota;
    }

    public Date getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public Integer getDiasProrroga() {
        return diasProrroga;
    }

    public void setDiasProrroga(Integer diasProrroga) {
        this.diasProrroga = diasProrroga;
    }
}