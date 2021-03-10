/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "LIQUIDACION")
@XmlRootElement
public class Liquidacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LIQUIDACION")
    @GeneratedValue(generator = "seqLiquidacion", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seqLiquidacion", sequenceName = "SEQ_LIQUIDACION", allocationSize = 1, initialValue = 1)
    private BigDecimal idLiquidacion;
    @Column(name = "ID_CONTRATO")
    private BigDecimal idContrato;
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;
    @OneToMany(mappedBy = "idLiquidacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DetalleLiquidacion> detalleLiquidacionList;

    public Liquidacion() {
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

    public Short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(Short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    @XmlTransient
    public List<DetalleLiquidacion> getDetalleLiquidacionList() {
        if(detalleLiquidacionList == null){
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
        if ((this.idLiquidacion == null && other.idLiquidacion != null) || (this.idLiquidacion != null && !this.idLiquidacion.equals(other.idLiquidacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.Liquidacion[ idLiquidacion=" + idLiquidacion + " ]";
    }

}
