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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "LIQUIDACION_DETALLE_DONACION")
@NamedQueries({
    @NamedQuery(name = "LiquidacionDetalleDonacion.findAll", query = "SELECT l FROM LiquidacionDetalleDonacion l")})
public class LiquidacionDetalleDonacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DET_DONACION")
    @GeneratedValue(generator = "seqLiqDonacion", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seqLiqDonacion", sequenceName = "SEQ_LIQ_DONACION", allocationSize = 1, initialValue = 1)
    private Long idDetDonacion;
    @Column(name = "CANTIDAD")
    private Long cantidad;
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @Column(name = "FECHA_DONACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDonacion;
    @JoinColumn(name = "ID_DET_LIQUIDACION", referencedColumnName = "ID_DET_LIQUIDACION")
    @ManyToOne
    private DetalleLiquidacion idDetLiquidacion;
    @Column(name = "TIPO_DONACION")
    private Short tipoDonacion;
    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;

    @Transient
    private Boolean eliminar = false;

    public LiquidacionDetalleDonacion() {
    }

    public LiquidacionDetalleDonacion(Long idDetDonacion) {
        this.idDetDonacion = idDetDonacion;
    }

    public Long getIdDetDonacion() {
        return idDetDonacion;
    }

    public void setIdDetDonacion(Long idDetDonacion) {
        this.idDetDonacion = idDetDonacion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public DetalleLiquidacion getIdDetLiquidacion() {
        return idDetLiquidacion;
    }

    public void setIdDetLiquidacion(DetalleLiquidacion idDetLiquidacion) {
        this.idDetLiquidacion = idDetLiquidacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetDonacion != null ? idDetDonacion.hashCode() : 0);
        return hash;
    }

    public Short getTipoDonacion() {
        return tipoDonacion;
    }

    public void setTipoDonacion(Short tipoDonacion) {
        this.tipoDonacion = tipoDonacion;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LiquidacionDetalleDonacion)) {
            return false;
        }
        LiquidacionDetalleDonacion other = (LiquidacionDetalleDonacion) object;
        return !((this.idDetDonacion == null && other.idDetDonacion != null) || (this.idDetDonacion != null && !this.idDetDonacion.equals(other.idDetDonacion)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.LiquidacionDetalleDonacion[ idDetDonacion=" + idDetDonacion + " ]";
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public Date getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(Date fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }

    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

}
