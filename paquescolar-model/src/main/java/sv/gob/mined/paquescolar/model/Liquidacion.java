/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author MISanchez
 */
@Entity
@Table(name = "LIQUIDACION")
public class Liquidacion implements Serializable {

    @OneToMany(mappedBy = "idLiquidacion", fetch = FetchType.LAZY)
    private List<DetalleLiquidacion> detalleLiquidacionList;

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

    public List<DetalleLiquidacion> getDetalleLiquidacionList() {
        return detalleLiquidacionList;
    }

    public void setDetalleLiquidacionList(List<DetalleLiquidacion> detalleLiquidacionList) {
        this.detalleLiquidacionList = detalleLiquidacionList;
    }
}
