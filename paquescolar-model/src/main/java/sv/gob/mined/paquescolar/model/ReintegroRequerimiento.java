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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "REINTEGRO_REQUERIMIENTO")
@NamedQueries({
    @NamedQuery(name = "ReintegroRequerimiento.findAll", query = "SELECT r FROM ReintegroRequerimiento r")})
public class ReintegroRequerimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_REINTEGRO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_reintegro")
    @SequenceGenerator(name = "id_reintegro", sequenceName = "SEQ_ID_REINTEGRO", allocationSize = 1, initialValue = 1)
    private Long idReintegro;
    @Basic(optional = false)
    @Column(name = "NUMERO_CHEQUE")
    private String numeroCheque;
    @Basic(optional = false)
    @Column(name = "FECHA_CHEQUE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCheque;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "MONTO_CHEQUE")
    private BigDecimal montoCheque;
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
    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;
    @JoinColumn(name = "ID_REQUERIMIENTO", referencedColumnName = "ID_REQUERIMIENTO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RequerimientoFondos idRequerimiento;

    public ReintegroRequerimiento() {
    }

    public ReintegroRequerimiento(Long idReintegro) {
        this.idReintegro = idReintegro;
    }

    public ReintegroRequerimiento(Long idReintegro, String numeroCheque, Date fechaCheque, BigDecimal montoCheque, String usuarioInsercion, Date fechaInsercion) {
        this.idReintegro = idReintegro;
        this.numeroCheque = numeroCheque;
        this.fechaCheque = fechaCheque;
        this.montoCheque = montoCheque;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
    }

    public Long getIdReintegro() {
        return idReintegro;
    }

    public void setIdReintegro(Long idReintegro) {
        this.idReintegro = idReintegro;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public Date getFechaCheque() {
        return fechaCheque;
    }

    public void setFechaCheque(Date fechaCheque) {
        this.fechaCheque = fechaCheque;
    }

    public BigDecimal getMontoCheque() {
        return montoCheque;
    }

    public void setMontoCheque(BigDecimal montoCheque) {
        this.montoCheque = montoCheque;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReintegro != null ? idReintegro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReintegroRequerimiento)) {
            return false;
        }
        ReintegroRequerimiento other = (ReintegroRequerimiento) object;
        if ((this.idReintegro == null && other.idReintegro != null) || (this.idReintegro != null && !this.idReintegro.equals(other.idReintegro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.ReintegroRequerimiento[ idReintegro=" + idReintegro + " ]";
    }

}
