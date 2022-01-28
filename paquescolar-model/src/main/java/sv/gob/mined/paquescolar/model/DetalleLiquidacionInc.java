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
import org.eclipse.persistence.annotations.AdditionalCriteria;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_LIQUIDACION_INC")
@NamedQueries({
    @NamedQuery(name = "DetalleLiquidacionInc.findAll", query = "SELECT d FROM DetalleLiquidacionInc d")})
@AdditionalCriteria("this.historico = 0")
public class DetalleLiquidacionInc implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DET_LIQ_INC") //
    @GeneratedValue(generator = "seqDetLiqInc", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seqDetLiqInc", sequenceName = "SEQ_DET_LIQ_INC", allocationSize = 1, initialValue = 1)
    private BigDecimal idDetLiqInc;
    @Column(name = "VALOR")
    private Short valor;
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "HISTORICO")
    private Short historico;
    @JoinColumn(name = "ID_CONCEPTO", referencedColumnName = "ID_CONCEPTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private ConceptoInconsistencia idConcepto;
    @JoinColumn(name = "ID_LIQUIDACION", referencedColumnName = "ID_LIQUIDACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private Liquidacion idLiquidacion;

    public DetalleLiquidacionInc() {
    }

    public DetalleLiquidacionInc(BigDecimal idDetLiqInc) {
        this.idDetLiqInc = idDetLiqInc;
    }

    public BigDecimal getIdDetLiqInc() {
        return idDetLiqInc;
    }

    public void setIdDetLiqInc(BigDecimal idDetLiqInc) {
        this.idDetLiqInc = idDetLiqInc;
    }

    public Short getValor() {
        return valor;
    }

    public void setValor(Short valor) {
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

    public Short getHistorico() {
        return historico;
    }

    public void setHistorico(Short historico) {
        this.historico = historico;
    }

    public ConceptoInconsistencia getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(ConceptoInconsistencia idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Liquidacion getIdLiquidacion() {
        return idLiquidacion;
    }

    public void setIdLiquidacion(Liquidacion idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetLiqInc != null ? idDetLiqInc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleLiquidacionInc)) {
            return false;
        }
        DetalleLiquidacionInc other = (DetalleLiquidacionInc) object;
        return !((this.idDetLiqInc == null && other.idDetLiqInc != null) || (this.idDetLiqInc != null && !this.idDetLiqInc.equals(other.idDetLiqInc)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetalleLiquidacionInc[ idDetLiqInc=" + idDetLiqInc + " ]";
    }
    
}
