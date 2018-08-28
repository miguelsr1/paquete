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

/**
 *
 * @author DesarrolloPc
 */
@Entity
@Table(name = "RECEPCION_BIENES_SERVICIOS")
@NamedQueries({
    @NamedQuery(name = "RecepcionBienesServicios.findAll", query = "SELECT r FROM RecepcionBienesServicios r")})
public class RecepcionBienesServicios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RECEPCION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recepcion")
    @SequenceGenerator(name = "recepcion", sequenceName = "SEQ_RECEPCION_BIENES", allocationSize = 1, initialValue = 1)
    private BigDecimal idRecepcion;
    @Column(name = "FECHA_ORDEN_INICIO_ENTREGA1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOrdenInicioEntrega1;
    @Column(name = "FECHA_ORDEN_INICIO_ENTREGA2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOrdenInicioEntrega2;
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
    @JoinColumn(name = "ID_ESTADO_SEGUIMIENTO", referencedColumnName = "ID_ESTADO_SEGUIMIENTO")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoSeguimiento idEstadoSeguimiento;
    @JoinColumn(name = "ID_CONTRATO", referencedColumnName = "ID_CONTRATO")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ContratosOrdenesCompras idContrato;
    
    @OneToMany(mappedBy = "idRecepcion", fetch = FetchType.EAGER)
    @OrderBy("fechaRecepcion ASC")
    private List<DetalleRecepcion> detalleRecepcionList;

    public RecepcionBienesServicios() {
    }

    public List<DetalleRecepcion> getDetalleRecepcionList() {
        return detalleRecepcionList;
    }

    public void setDetalleRecepcionList(List<DetalleRecepcion> detalleRecepcionList) {
        this.detalleRecepcionList = detalleRecepcionList;
    }

    public BigDecimal getIdRecepcion() {
        return idRecepcion;
    }

    public void setIdRecepcion(BigDecimal idRecepcion) {
        this.idRecepcion = idRecepcion;
    }

    public Date getFechaOrdenInicioEntrega1() {
        return fechaOrdenInicioEntrega1;
    }

    public void setFechaOrdenInicioEntrega1(Date fechaOrdenInicioEntrega1) {
        this.fechaOrdenInicioEntrega1 = fechaOrdenInicioEntrega1;
    }

    public Date getFechaOrdenInicioEntrega2() {
        return fechaOrdenInicioEntrega2;
    }

    public void setFechaOrdenInicioEntrega2(Date fechaOrdenInicioEntrega2) {
        this.fechaOrdenInicioEntrega2 = fechaOrdenInicioEntrega2;
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

    public EstadoSeguimiento getIdEstadoSeguimiento() {
        return idEstadoSeguimiento;
    }

    public void setIdEstadoSeguimiento(EstadoSeguimiento idEstadoSeguimiento) {
        this.idEstadoSeguimiento = idEstadoSeguimiento;
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
        hash += (idRecepcion != null ? idRecepcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecepcionBienesServicios)) {
            return false;
        }
        RecepcionBienesServicios other = (RecepcionBienesServicios) object;
        if ((this.idRecepcion == null && other.idRecepcion != null) || (this.idRecepcion != null && !this.idRecepcion.equals(other.idRecepcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.RecepcionBienesServicios[ idRecepcion=" + idRecepcion + " ]";
    }

}
