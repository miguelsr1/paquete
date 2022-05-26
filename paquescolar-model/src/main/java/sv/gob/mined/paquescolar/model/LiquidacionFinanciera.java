/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "LIQUIDACION_FINANCIERA")
@AdditionalCriteria("this.estadoEliminacion = 0")
public class LiquidacionFinanciera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID_LIQUIDACION_FINANCIERA")
    @GeneratedValue(generator = "seqLiqFinan", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seqLiqFinan", sequenceName = "SEQ_LIQ_FINAN", allocationSize = 1, initialValue = 1)
    private BigDecimal idLiquidacionFinanciera;
    @Basic(optional = false)
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "USUARIO_MODIFICATIVA")
    private String usuarioModificativa;
    @Column(name = "FECHA_MODIFICATIVA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificativa;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;
    @Column(name = "ID_ESTADO_LIQUI_FINAN")
    private Short idEstadoLiquiFinan;
    @Column(name = "ID_CONTRATO")
    private BigDecimal idContrato;

    public LiquidacionFinanciera() {
    }

    public Short getIdEstadoLiquiFinan() {
        return idEstadoLiquiFinan;
    }

    public void setIdEstadoLiquiFinan(Short idEstadoLiquiFinan) {
        this.idEstadoLiquiFinan = idEstadoLiquiFinan;
    }

    public LiquidacionFinanciera(BigDecimal idLiquidacionFinanciera) {
        this.idLiquidacionFinanciera = idLiquidacionFinanciera;
    }

    public LiquidacionFinanciera(BigDecimal idLiquidacionFinanciera, String usuarioInsercion) {
        this.idLiquidacionFinanciera = idLiquidacionFinanciera;
        this.usuarioInsercion = usuarioInsercion;
    }

    public BigDecimal getIdLiquidacionFinanciera() {
        return idLiquidacionFinanciera;
    }

    public void setIdLiquidacionFinanciera(BigDecimal idLiquidacionFinanciera) {
        this.idLiquidacionFinanciera = idLiquidacionFinanciera;
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

    public String getUsuarioModificativa() {
        return usuarioModificativa;
    }

    public void setUsuarioModificativa(String usuarioModificativa) {
        this.usuarioModificativa = usuarioModificativa;
    }

    public Date getFechaModificativa() {
        return fechaModificativa;
    }

    public void setFechaModificativa(Date fechaModificativa) {
        this.fechaModificativa = fechaModificativa;
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

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLiquidacionFinanciera != null ? idLiquidacionFinanciera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LiquidacionFinanciera)) {
            return false;
        }
        LiquidacionFinanciera other = (LiquidacionFinanciera) object;
        if ((this.idLiquidacionFinanciera == null && other.idLiquidacionFinanciera != null) || (this.idLiquidacionFinanciera != null && !this.idLiquidacionFinanciera.equals(other.idLiquidacionFinanciera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.LiquidacionFinanciera[ idLiquidacionFinanciera=" + idLiquidacionFinanciera + " ]";
    }

}
