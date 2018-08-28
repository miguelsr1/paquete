/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_PLANILLA")
@NamedQueries({
    @NamedQuery(name = "DetallePlanilla.findAll", query = "SELECT d FROM DetallePlanilla d")})
@AdditionalCriteria("this.estadoEliminacion = 0")
public class DetallePlanilla implements Serializable {

    @JoinColumn(name = "ID_DETALLE_DOC_PAGO", referencedColumnName = "ID_DETALLE_DOC_PAGO")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetalleDocPago idDetalleDocPago;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_PLANILLA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "det_planilla")
    @SequenceGenerator(name = "det_planilla", sequenceName = "SEQ_DET_PLANILLA", allocationSize = 1, initialValue = 1)
    private BigDecimal idDetallePlanilla;
    @JoinColumn(name = "CODIGO_ENTIDAD", referencedColumnName = "CODIGO_ENTIDAD")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private VwCatalogoEntidadEducativa codigoEntidad;
    @Column(name = "MONTO_ORIGINAL")
    private BigDecimal montoOriginal;
    @Column(name = "CANTIDAD_ORIGINAL")
    private BigInteger cantidadOriginal;
    @Column(name = "MONTO_ACTUAL")
    private BigDecimal montoActual;
    @Column(name = "CANTIDAD_ACTUAL")
    private BigInteger cantidadActual;

    @JoinColumn(name = "ID_PLANILLA", referencedColumnName = "ID_PLANILLA")
    @ManyToOne(fetch = FetchType.EAGER)
    private PlanillaPago idPlanilla;
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
    private Short estadoEliminacion;

    @Column(name = "CHEQUE")
    private Short cheque;
    @Column(name = "NUM_CHEQUE")
    private String numCheque;
    @Column(name = "MONTO_CHEQUE")
    private BigDecimal montoCheque;
    @Column(name = "FECHA_CHEQUE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCheque;
    @JoinColumn(name = "ID_BANCO", referencedColumnName = "ID_BANCO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Bancos idBanco;
    
    @Transient
    private Boolean eliminar = false;
    @Transient
    private Boolean detalleDocPago = false;

    public DetallePlanilla() {
    }

    public DetallePlanilla(BigDecimal idDetallePlanilla) {
        this.idDetallePlanilla = idDetallePlanilla;
    }

    public BigDecimal getIdDetallePlanilla() {
        return idDetallePlanilla;
    }

    public void setIdDetallePlanilla(BigDecimal idDetallePlanilla) {
        this.idDetallePlanilla = idDetallePlanilla;
    }

    public VwCatalogoEntidadEducativa getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(VwCatalogoEntidadEducativa codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public BigDecimal getMontoOriginal() {
        return montoOriginal;
    }

    public void setMontoOriginal(BigDecimal montoOriginal) {
        this.montoOriginal = montoOriginal;
    }

    public BigInteger getCantidadOriginal() {
        return cantidadOriginal;
    }

    public void setCantidadOriginal(BigInteger cantidadOriginal) {
        this.cantidadOriginal = cantidadOriginal;
    }

    public BigDecimal getMontoActual() {
        if (montoActual == null) {
            montoActual = BigDecimal.ZERO;
        }
        return montoActual;
    }

    public void setMontoActual(BigDecimal montoActual) {
        this.montoActual = montoActual;
    }

    public BigInteger getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(BigInteger cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public PlanillaPago getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(PlanillaPago idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetallePlanilla != null ? idDetallePlanilla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallePlanilla)) {
            return false;
        }
        DetallePlanilla other = (DetallePlanilla) object;
        if ((this.idDetallePlanilla == null && other.idDetallePlanilla != null) || (this.idDetallePlanilla != null && !this.idDetallePlanilla.equals(other.idDetallePlanilla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetallePlanilla[ idDetallePlanilla=" + idDetallePlanilla + " ]";
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
        if (this.eliminar) {
            estadoEliminacion = (short) 1;
        } else {
            estadoEliminacion = (short) 0;
        }
    }

    public Boolean getEliminar() {
        return estadoEliminacion.intValue() == 1;
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

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public Short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(Short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Short getCheque() {
        return cheque;
    }

    public void setCheque(Short cheque) {
        this.cheque = cheque;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public BigDecimal getMontoCheque() {
        return montoCheque;
    }

    public void setMontoCheque(BigDecimal montoCheque) {
        this.montoCheque = montoCheque;
    }

    public Date getFechaCheque() {
        return fechaCheque;
    }

    public void setFechaCheque(Date fechaCheque) {
        this.fechaCheque = fechaCheque;
    }

    public Bancos getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Bancos idBanco) {
        this.idBanco = idBanco;
    }

    public DetalleDocPago getIdDetalleDocPago() {
        if (idDetalleDocPago == null) {
            idDetalleDocPago = new DetalleDocPago();
        }
        return idDetalleDocPago;
    }

    public void setIdDetalleDocPago(DetalleDocPago idDetalleDocPago) {
        this.idDetalleDocPago = idDetalleDocPago;
    }

    public Boolean getDetalleDocPago() {
        return getIdDetalleDocPago().getIdDetalleDocPago() != null;
    }

    public void setDetalleDocPago(Boolean detalleDocPago) {
        this.detalleDocPago = detalleDocPago;
    }

    /*public BigDecimal getMontoRenta() {
        return montoRenta;
    }

    public void setMontoRenta(BigDecimal montoRenta) {
        this.montoRenta = montoRenta;
    }*/
}
