/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_LIQUIDACION")
@NamedQueries({
    @NamedQuery(name = "DetalleLiquidacion.findAll", query = "SELECT d FROM DetalleLiquidacion d")})
public class DetalleLiquidacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DET_LIQUIDACION")
    private Long idDetLiquidacion;
    @Column(name = "NO_ITEM")
    private String noItem;
    @Column(name = "CANTIDAD")
    private Long cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;
    @Column(name = "CANTIDAD_ENTREGADA")
    private Long cantidadEntregada;
    @Column(name = "CANTIDAD__RESGUARDO")
    private Long cantidadResguardo;
    @JoinColumn(name = "ID_LIQUIDACION", referencedColumnName = "ID_LIQUIDACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private Liquidacion idLiquidacion;

    public DetalleLiquidacion() {
    }

    public DetalleLiquidacion(Long idDetLiquidacion) {
        this.idDetLiquidacion = idDetLiquidacion;
    }

    public Long getIdDetLiquidacion() {
        return idDetLiquidacion;
    }

    public void setIdDetLiquidacion(Long idDetLiquidacion) {
        this.idDetLiquidacion = idDetLiquidacion;
    }

    public String getNoItem() {
        return noItem;
    }

    public void setNoItem(String noItem) {
        this.noItem = noItem;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Long getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Long cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public Long getCantidadResguardo() {
        return cantidadResguardo;
    }

    public void setCantidadResguardo(Long cantidadResguardo) {
        this.cantidadResguardo = cantidadResguardo;
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
        hash += (idDetLiquidacion != null ? idDetLiquidacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleLiquidacion)) {
            return false;
        }
        DetalleLiquidacion other = (DetalleLiquidacion) object;
        if ((this.idDetLiquidacion == null && other.idDetLiquidacion != null) || (this.idDetLiquidacion != null && !this.idDetLiquidacion.equals(other.idDetLiquidacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetalleLiquidacion[ idDetLiquidacion=" + idDetLiquidacion + " ]";
    }
    
}
