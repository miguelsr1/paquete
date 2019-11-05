/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
@Table(name = "RESOLUCIONES_ADJUDICATIVAS")
@NamedQueries({
    @NamedQuery(name = "ResolucionesAdjudicativas.findAll", query = "SELECT r FROM ResolucionesAdjudicativas r")})
@AdditionalCriteria("this.estadoEliminacion = 0")
public class ResolucionesAdjudicativas implements Serializable {

    @OneToMany(mappedBy = "idResolucionAdj", fetch = FetchType.LAZY)
    private List<HistorialCamEstResAdj> historialCamEstResAdjList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RESOLUCION_ADJ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resolucion")
    @SequenceGenerator(name = "resolucion", sequenceName = "SEQ_RESOLUCION_ADJ", allocationSize = 1, initialValue = 1)
    private BigDecimal idResolucionAdj;
    @Column(name = "VALOR")
    private BigDecimal valor;
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
    @OneToMany(mappedBy = "idResolucionAdj", fetch = FetchType.LAZY)
    private List<ContratosOrdenesCompras> contratosOrdenesComprasList;
    @JoinColumn(name = "ID_PARTICIPANTE", referencedColumnName = "ID_PARTICIPANTE")
    @ManyToOne(fetch = FetchType.EAGER)
    private Participantes idParticipante;
    @JoinColumn(name = "ID_ESTADO_RESERVA", referencedColumnName = "ID_ESTADO_RESERVA")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoReserva idEstadoReserva;
    
    public ResolucionesAdjudicativas() {
    }

    public ResolucionesAdjudicativas(BigDecimal idResolucionAdj) {
        this.idResolucionAdj = idResolucionAdj;
    }

    public ResolucionesAdjudicativas(BigDecimal idResolucionAdj, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion) {
        this.idResolucionAdj = idResolucionAdj;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdResolucionAdj() {
        return idResolucionAdj;
    }

    public void setIdResolucionAdj(BigDecimal idResolucionAdj) {
        this.idResolucionAdj = idResolucionAdj;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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

    public List<ContratosOrdenesCompras> getContratosOrdenesComprasList() {
        if (contratosOrdenesComprasList == null) {
            return new ArrayList();
        } else {
            return contratosOrdenesComprasList;
        }
    }

    public void setContratosOrdenesComprasList(List<ContratosOrdenesCompras> contratosOrdenesComprasList) {
        this.contratosOrdenesComprasList = contratosOrdenesComprasList;
    }

    public Participantes getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Participantes idParticipante) {
        this.idParticipante = idParticipante;
    }

    public EstadoReserva getIdEstadoReserva() {
        return idEstadoReserva;
    }

    public void setIdEstadoReserva(EstadoReserva idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResolucionAdj != null ? idResolucionAdj.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResolucionesAdjudicativas)) {
            return false;
        }
        ResolucionesAdjudicativas other = (ResolucionesAdjudicativas) object;
        if ((this.idResolucionAdj == null && other.idResolucionAdj != null) || (this.idResolucionAdj != null && !this.idResolucionAdj.equals(other.idResolucionAdj))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.ResolucionesAdjudicativas[ idResolucionAdj=" + idResolucionAdj + " ]";
    }

    public List<HistorialCamEstResAdj> getHistorialCamEstResAdjList() {
        return historialCamEstResAdjList;
    }

    public void setHistorialCamEstResAdjList(List<HistorialCamEstResAdj> historialCamEstResAdjList) {
        this.historialCamEstResAdjList = historialCamEstResAdjList;
    }

}
