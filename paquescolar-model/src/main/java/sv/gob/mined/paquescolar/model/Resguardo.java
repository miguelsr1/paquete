/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "RESGUARDO")
@XmlRootElement
public class Resguardo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RESGUARDO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_resguardo")
    @SequenceGenerator(name = "seq_resguardo", sequenceName = "SEQ_RESGUARDO", allocationSize = 1, initialValue = 1)
    private Long idResguardo;

    @JoinColumn(name = "ID_CONTRATO", referencedColumnName = "ID_CONTRATO")
    @ManyToOne(fetch = FetchType.EAGER)
    private ContratosOrdenesCompras idContrato;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idResguardo", fetch = FetchType.LAZY)
    private List<DetalleResguardo> detalleResguardoList;

    @Transient
    private BigInteger totalCantidad;

    public Resguardo() {
    }

    public Resguardo(Long idResguardo) {
        this.idResguardo = idResguardo;
    }

    public Long getIdResguardo() {
        return idResguardo;
    }

    public void setIdResguardo(Long idResguardo) {
        this.idResguardo = idResguardo;
    }

    public ContratosOrdenesCompras getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratosOrdenesCompras idContrato) {
        this.idContrato = idContrato;
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

    @XmlTransient
    public List<DetalleResguardo> getDetalleResguardoList() {
        if (detalleResguardoList == null) {
            detalleResguardoList = new ArrayList();
        }
        return detalleResguardoList;
    }

    public void setDetalleResguardoList(List<DetalleResguardo> detalleResguardoList) {
        this.detalleResguardoList = detalleResguardoList;
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
        if (!(object instanceof Resguardo)) {
            return false;
        }
        Resguardo other = (Resguardo) object;
        return !((this.idResguardo == null && other.idResguardo != null) || (this.idResguardo != null && !this.idResguardo.equals(other.idResguardo)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.Resguardo[ idResguardo=" + idResguardo + " ]";
    }

    public BigInteger getTotalCantidad() {
        totalCantidad = BigInteger.ZERO;
        if (detalleResguardoList != null) {
            detalleResguardoList.forEach(det -> {
                totalCantidad = totalCantidad.add(det.getCantidad());
            });
        }
        return totalCantidad;
    }

}
