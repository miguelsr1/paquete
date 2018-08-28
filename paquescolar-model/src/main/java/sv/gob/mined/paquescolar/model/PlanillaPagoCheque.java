/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PLANILLA_PAGO_CHEQUE")
@XmlRootElement
public class PlanillaPagoCheque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PLANILLA_CHEQUE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHEQUE")
    @SequenceGenerator(name = "SEQ_CHEQUE", sequenceName = "SEQ_PLANILLA_PAGO_CHEQUE", allocationSize = 1, initialValue = 1)
    private Integer idPlanillaCheque;
    @Column(name = "NUMERO_CHEQUE")
    private String numeroCheque;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MONTO_CHEQUE")
    private BigDecimal montoCheque;
    @Column(name = "FECHA_CHEQUE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCheque;
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
    @JoinColumn(name = "ID_PLANILLA", referencedColumnName = "ID_PLANILLA")
    @ManyToOne(fetch = FetchType.EAGER)
    private PlanillaPago idPlanilla;
    @Column(name = "A_FAVOR_DE")
    private Short aFavorDe;
    @Column(name = "TRANSFERENCIA")
    private Short transferencia = 0;
    @Column(name = "NUMERO_REFERENCIA_TRANS")
    private String numeroReferenciaTrans;

    public PlanillaPagoCheque() {
    }

    public PlanillaPagoCheque(Integer idPlanillaCheque) {
        this.idPlanillaCheque = idPlanillaCheque;
    }

    public Integer getIdPlanillaCheque() {
        return idPlanillaCheque;
    }

    public void setIdPlanillaCheque(Integer idPlanillaCheque) {
        this.idPlanillaCheque = idPlanillaCheque;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
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

    public PlanillaPago getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(PlanillaPago idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlanillaCheque != null ? idPlanillaCheque.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaPagoCheque)) {
            return false;
        }
        PlanillaPagoCheque other = (PlanillaPagoCheque) object;
        if ((this.idPlanillaCheque == null && other.idPlanillaCheque != null) || (this.idPlanillaCheque != null && !this.idPlanillaCheque.equals(other.idPlanillaCheque))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.PlanillaPagoCheque[ idPlanillaCheque=" + idPlanillaCheque + " ]";
    }

    /**
     * Método que especifica el destinario del cheque
     *
     * @return 0: Financiera; 1: Pagaduría USEFI
     */
    public Short getaFavorDe() {
        return aFavorDe;
    }

    public void setaFavorDe(Short aFavorDe) {
        this.aFavorDe = aFavorDe;
    }

    /**
     * Método que especifica si el pago se realizará por cheque o transferencia
     *
     * @return 0: Financiera; 1: Pagaduría USEFI
     */
    public Short getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Short transferencia) {
        this.transferencia = transferencia;
    }

    public String getNumeroReferenciaTrans() {
        return numeroReferenciaTrans;
    }

    public void setNumeroReferenciaTrans(String numeroReferenciaTrans) {
        this.numeroReferenciaTrans = numeroReferenciaTrans;
    }
}
