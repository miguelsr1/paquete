/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PLANILLA_PAGO")
@NamedQueries({
    @NamedQuery(name = "PlanillaPago.findAll", query = "SELECT p FROM PlanillaPago p")})
public class PlanillaPago implements Serializable {

    @OneToMany(mappedBy = "idPlanilla", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlanillaPagoCheque> planillaPagoChequeList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PLANILLA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planilla")
    @SequenceGenerator(name = "planilla", sequenceName = "SEQ_PLANILLA", allocationSize = 1, initialValue = 1)
    private BigDecimal idPlanilla;
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
    @JoinColumn(name = "ID_REQUERIMIENTO", referencedColumnName = "ID_REQUERIMIENTO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RequerimientoFondos idRequerimiento;
    @OneToMany(mappedBy = "idPlanilla", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DetallePlanilla> detallePlanillaList;
    @Column(name = "ID_TIPO_PLANILLA")
    private Short idTipoPlanilla;
    @Column(name = "ID_ESTADO_PLANILLA")
    private Short idEstadoPlanilla;
    @Column(name = "NOTIFICACION")
    private Short notificacion;

    @Transient
    private BigDecimal montoTotal;
    @Transient
    private BigDecimal montoTotalReintegrar;
    @Transient
    private Boolean eliminar = false;

    public PlanillaPago() {
    }

    public BigDecimal getMontoTotal() {
        montoTotal = BigDecimal.ZERO;
        for (DetallePlanilla detallePlanilla : getDetallePlanillaList()) {
            if (detallePlanilla.getEstadoEliminacion() == 0) {
                montoTotal = montoTotal.add(detallePlanilla.getMontoActual());
            }
        }
        return montoTotal;
    }

    public Short getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Short notificacion) {
        this.notificacion = notificacion;
    }

    public PlanillaPago(BigDecimal idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public Short getIdEstadoPlanilla() {
        return idEstadoPlanilla;
    }

    public void setIdEstadoPlanilla(Short idEstadoPlanilla) {
        this.idEstadoPlanilla = idEstadoPlanilla;
    }

    public BigDecimal getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(BigDecimal idPlanilla) {
        this.idPlanilla = idPlanilla;
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

    public Short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(Short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public RequerimientoFondos getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(RequerimientoFondos idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public List<DetallePlanilla> getDetallePlanillaList() {
        if (detallePlanillaList == null) {
            detallePlanillaList = new ArrayList();
        }
        return detallePlanillaList;
    }

    public void setDetallePlanillaList(List<DetallePlanilla> detallePlanillaList) {
        this.detallePlanillaList = detallePlanillaList;
    }

    public Short getIdTipoPlanilla() {
        return idTipoPlanilla;
    }

    public void setIdTipoPlanilla(Short idTipoPlanilla) {
        this.idTipoPlanilla = idTipoPlanilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlanilla != null ? idPlanilla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaPago)) {
            return false;
        }
        PlanillaPago other = (PlanillaPago) object;
        return !((this.idPlanilla == null && other.idPlanilla != null) || (this.idPlanilla != null && !this.idPlanilla.equals(other.idPlanilla)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.PlanillaPago[ idPlanilla=" + idPlanilla + " ]";
    }

    @XmlTransient
    public List<PlanillaPagoCheque> getPlanillaPagoChequeList() {
        if (planillaPagoChequeList == null) {
            planillaPagoChequeList = new ArrayList();
        }
        return planillaPagoChequeList;
    }

    public void setPlanillaPagoChequeList(List<PlanillaPagoCheque> planillaPagoChequeList) {
        this.planillaPagoChequeList = planillaPagoChequeList;
    }

    public BigDecimal getMontoTotalReintegrar() {
        montoTotalReintegrar = BigDecimal.ZERO;
        for (DetallePlanilla detPla : getDetallePlanillaList()) {
            if (detPla.getEstadoEliminacion() == 0) {
                if (detPla.getIdDetalleDocPago().getContratoModif() == 1) {
                    montoTotalReintegrar = montoTotalReintegrar.add(detPla.getMontoOriginal().add(detPla.getMontoActual().negate()));
                }
            }
        }
        return montoTotalReintegrar;
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
}
