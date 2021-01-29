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
import javax.persistence.Transient;

/**
 *
 * @author MISanchez
 */
@Entity
@Table(name = "LIQUIDACION")
public class Liquidacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LIQUIDACION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLiquidacion")
    @SequenceGenerator(name = "seqLiquidacion", sequenceName = "SEQ_LIQUIDACION", allocationSize = 1, initialValue = 1)
    private BigDecimal idLiquidacion;

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

    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;

    @Column(name = "ACTA_RECEPCION")
    private Short actaRecepcion;
    @Column(name = "ESTADO_LIQUIDACION")
    private Short estadoLiquidacion;
    @Column(name = "COMENTARIO")
    private String comentario;

    @Transient
    private Boolean recepcion;

    public Liquidacion() {
    }

    public BigDecimal getIdLiquidacion() {
        return idLiquidacion;
    }

    public void setIdLiquidacion(BigDecimal idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
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

    public Short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(Short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public Short getActaRecepcion() {
        return actaRecepcion;
    }

    public void setActaRecepcion(Short actaRecepcion) {
        this.actaRecepcion = actaRecepcion;
    }

    public Short getEstadoLiquidacion() {
        return estadoLiquidacion;
    }

    public void setEstadoLiquidacion(Short estadoLiquidacion) {
        this.estadoLiquidacion = estadoLiquidacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setRecepcion(Boolean recepcion) {
        this.recepcion = recepcion;
        actaRecepcion = recepcion ? (short) 1 : (short) 0;
    }

    public Boolean getRecepcion() {
        recepcion = (actaRecepcion == 1);

        return recepcion;
    }
}
