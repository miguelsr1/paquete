/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "HISTORIAL_LIQUI_FINAN")
public class HistorialLiquiFinan implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_HISTORICO_LIQUI_FINAN")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historicoLiquiFinan")
    @SequenceGenerator(name = "historicoLiquiFinan", sequenceName = "SEQ_HISTORIAL_LIQUI_FINAN", allocationSize = 1, initialValue = 1)
    private BigDecimal idHistoricoLiquiFinan;
    @Column(name = "ID_CONTRATO")   
    private BigDecimal idContrato;
    @Basic(optional = false)
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "ID_ESTADO_LIQUI_FINAN_OLD")
    private Short idEstadoLiquiFinanOld;
    @Column(name = "ID_ESTADO_LIQUI_FINAN_New")
    private Short idEstadoLiquiFinanNew;
    @Column(name = "JUSTIFICACION")
    private String justificacion;

    public HistorialLiquiFinan() {
    }

    public BigDecimal getIdHistoricoLiquiFinan() {
        return idHistoricoLiquiFinan;
    }

    public void setIdHistoricoLiquiFinan(BigDecimal idHistoricoLiquiFinan) {
        this.idHistoricoLiquiFinan = idHistoricoLiquiFinan;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
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

    public Short getIdEstadoLiquiFinanOld() {
        return idEstadoLiquiFinanOld;
    }

    public void setIdEstadoLiquiFinanOld(Short idEstadoLiquiFinanOld) {
        this.idEstadoLiquiFinanOld = idEstadoLiquiFinanOld;
    }

    public Short getIdEstadoLiquiFinanNew() {
        return idEstadoLiquiFinanNew;
    }

    public void setIdEstadoLiquiFinanNew(Short idEstadoLiquiFinanNew) {
        this.idEstadoLiquiFinanNew = idEstadoLiquiFinanNew;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

}
