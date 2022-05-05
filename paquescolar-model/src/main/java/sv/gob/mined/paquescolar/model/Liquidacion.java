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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.AdditionalCriteria;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "LIQUIDACION")
@XmlRootElement
@AdditionalCriteria("this.estadoEliminacion = 0")
public class Liquidacion implements Serializable {

    @Size(max = 500)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Size(max = 1)
    @Column(name = "ESTADO_LIQUIDACION")
    private String estadoLiquidacion;
    @OneToMany(mappedBy = "idLiquidacion", fetch = FetchType.LAZY)
    private List<DetalleLiquidacionInc> detalleLiquidacionIncList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LIQUIDACION")
    @GeneratedValue(generator = "seqLiquidacion", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seqLiquidacion", sequenceName = "SEQ_LIQUIDACION", allocationSize = 1, initialValue = 1)
    private BigDecimal idLiquidacion;
    @JoinColumn(name = "ID_CONTRATO", referencedColumnName = "ID_CONTRATO")
    @ManyToOne(fetch = FetchType.EAGER)
    private ContratosOrdenesCompras idContrato;
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "FECHA_LIQUIDACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLiquidacion;
    @Column(name = "FECHA_RECEPCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecepcion;
    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;
    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;

    @OneToMany(mappedBy = "idLiquidacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DetalleLiquidacion> detalleLiquidacionList;

    @Transient
    private BigDecimal montoRecepcion;
    @Transient
    private BigDecimal montoContratado;
    @Transient
    private BigDecimal montoModificativa;
    @Transient
    private BigDecimal montoResguardo;
    @Transient
    private Long totalDonacionRecibe;
    @Transient
    private Long totalDonacionEntrega;

    public Liquidacion() {
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Liquidacion(BigDecimal idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
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

    @XmlTransient
    public List<DetalleLiquidacion> getDetalleLiquidacionList() {
        if (detalleLiquidacionList == null) {
            detalleLiquidacionList = new ArrayList();
        }
        return detalleLiquidacionList;
    }

    public void setDetalleLiquidacionList(List<DetalleLiquidacion> detalleLiquidacionList) {
        this.detalleLiquidacionList = detalleLiquidacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLiquidacion != null ? idLiquidacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liquidacion)) {
            return false;
        }
        Liquidacion other = (Liquidacion) object;
        return !((this.idLiquidacion == null && other.idLiquidacion != null) || (this.idLiquidacion != null && !this.idLiquidacion.equals(other.idLiquidacion)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.Liquidacion[ idLiquidacion=" + idLiquidacion + " ]";
    }

    public BigDecimal getMontoRecepcion() {
        montoRecepcion = BigDecimal.ZERO;

        detalleLiquidacionList.forEach(detalleLiquidacion -> {
            if (detalleLiquidacion.getPrecioUnitarioModif() == null) {
                montoRecepcion = montoRecepcion.add(detalleLiquidacion.getPrecioUnitario().multiply(new BigDecimal(detalleLiquidacion.getCantidadEntregada())));
            } else {
                montoRecepcion = montoRecepcion.add(detalleLiquidacion.getPrecioUnitarioModif().multiply(new BigDecimal(detalleLiquidacion.getCantidadEntregada())));
            }
        });

        return montoRecepcion;
    }

    public void setMontoRecepcion(BigDecimal montoRecepcion) {
        this.montoRecepcion = montoRecepcion;
    }

    public BigDecimal getMontoContratado() {
        montoContratado = BigDecimal.ZERO;

        detalleLiquidacionList.forEach(detalleLiquidacion -> {
            montoContratado = montoContratado.add(detalleLiquidacion.getPrecioUnitario().multiply(new BigDecimal(detalleLiquidacion.getCantidad())));
        });

        return montoContratado;
    }

    public void setMontoContratado(BigDecimal montoContratado) {
        this.montoContratado = montoContratado;
    }

    public BigDecimal getMontoModificativa() {

        montoModificativa = BigDecimal.ZERO;

        detalleLiquidacionList.forEach(detalleLiquidacion -> {
            if (detalleLiquidacion.getCantidadModificativa() != null) {
                montoModificativa = montoModificativa.add(detalleLiquidacion.getPrecioUnitarioModif().multiply(new BigDecimal(detalleLiquidacion.getCantidadModificativa())));
            }
        });

        return montoModificativa;
    }

    public void setMontoModificativa(BigDecimal montoModificativa) {
        this.montoModificativa = montoModificativa;
    }

    public BigDecimal getMontoResguardo() {
        montoResguardo = BigDecimal.ZERO;

        detalleLiquidacionList.forEach(detalleLiquidacion -> {
            if (detalleLiquidacion.getPrecioUnitarioModif() == null) {
                montoResguardo = montoResguardo.add(detalleLiquidacion.getPrecioUnitario().multiply(new BigDecimal(detalleLiquidacion.getCantidadResguardo())));
            } else {
                montoResguardo = montoResguardo.add(detalleLiquidacion.getPrecioUnitarioModif().multiply(new BigDecimal(detalleLiquidacion.getCantidadResguardo())));
            }
        });

        return montoResguardo;
    }

    public Long getTotalDonacionRecibe() {
        totalDonacionRecibe = 0l;

        detalleLiquidacionList.forEach(detalleLiquidacion -> {
            for (LiquidacionDetalleDonacion det : detalleLiquidacion.getLiquidacionDetalleDonacionList()) {
                if (det.getTipoDonacion() == (short) 1) {
                    totalDonacionRecibe += det.getCantidad();
                }
            }
        });

        return totalDonacionRecibe;
    }

    public Long getTotalDonacionEntrega() {
        totalDonacionEntrega = 0l;

        detalleLiquidacionList.forEach(detalleLiquidacion -> {
            for (LiquidacionDetalleDonacion det : detalleLiquidacion.getLiquidacionDetalleDonacionList()) {
                if (det.getTipoDonacion() == (short) 0) {
                    totalDonacionEntrega += det.getCantidad();
                }
            }
        });

        return totalDonacionEntrega;
    }

    public void setMontoResguardo(BigDecimal montoResguardo) {
        this.montoResguardo = montoResguardo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstadoLiquidacion() {
        return estadoLiquidacion;
    }

    public void setEstadoLiquidacion(String estadoLiquidacion) {
        this.estadoLiquidacion = estadoLiquidacion;
    }

    public List<DetalleLiquidacionInc> getDetalleLiquidacionIncList() {
        if (detalleLiquidacionIncList == null) {
            detalleLiquidacionIncList = new ArrayList();
        }
        return detalleLiquidacionIncList;
    }

    public void setDetalleLiquidacionIncList(List<DetalleLiquidacionInc> detalleLiquidacionIncList) {
        this.detalleLiquidacionIncList = detalleLiquidacionIncList;
    }

}
