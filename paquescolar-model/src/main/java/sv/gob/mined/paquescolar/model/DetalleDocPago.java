/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_DOC_PAGO")
@NamedQueries({
    @NamedQuery(name = "DetalleDocPago.findAll", query = "SELECT d FROM DetalleDocPago d")})
public class DetalleDocPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_DOC_PAGO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DET_DOC_PAGO")
    @SequenceGenerator(name = "SEQ_DET_DOC_PAGO", sequenceName = "SEQ_DET_DOC_PAGO", allocationSize = 1, initialValue = 1)
    private Integer idDetalleDocPago;
    @Column(name = "ID_TIPO_DOC_PAGO")
    private Integer idTipoDocPago;
    @Column(name = "NO_DOC_PAGO")
    private String noDocPago;
    @Column(name = "FECHA_DOC_PAGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocPago;
    @Column(name = "CONTRATO_MODIF")
    private Short contratoModif;
    @Column(name = "NO_RES_MODIFICATIVA")
    private String noResModificativa;
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;
    @JoinColumn(name = "ID_DET_REQUERIMIENTO", referencedColumnName = "ID_DET_REQUERIMIENTO")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetalleRequerimiento idDetRequerimiento;
    @OneToMany(mappedBy = "idDetalleDocPago", fetch = FetchType.LAZY)
    private List<DetallePlanilla> detallePlanillaList;
    @Column(name = "FECHA_MODIFICATIVA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificativa;
    @Column(name = "MONTO_ACTUAL")
    private BigDecimal montoActual;
    @Column(name = "CANTIDAD_ACTUAL")
    private BigInteger cantidadActual;
    @Column(name = "MONTO_RENTA")
    private BigDecimal montoRenta;

    public DetalleDocPago() {
    }

    public Date getFechaModificativa() {
        return fechaModificativa;
    }

    public void setFechaModificativa(Date fechaModificativa) {
        this.fechaModificativa = fechaModificativa;
    }

    public BigDecimal getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(BigDecimal montoActual) {
        this.montoActual = montoActual;
    }

    public BigInteger getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(BigInteger cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public DetalleDocPago(Integer idDetalleDocPago) {
        this.idDetalleDocPago = idDetalleDocPago;
    }

    public Integer getIdDetalleDocPago() {
        return idDetalleDocPago;
    }

    public void setIdDetalleDocPago(Integer idDetalleDocPago) {
        this.idDetalleDocPago = idDetalleDocPago;
    }

    public Integer getIdTipoDocPago() {
        return idTipoDocPago;
    }

    public void setIdTipoDocPago(Integer idTipoDocPago) {
        this.idTipoDocPago = idTipoDocPago;
    }

    public String getNoDocPago() {
        return noDocPago;
    }

    public void setNoDocPago(String noDocPago) {
        this.noDocPago = noDocPago;
    }

    public Date getFechaDocPago() {
        return fechaDocPago;
    }

    public void setFechaDocPago(Date fechaDocPago) {
        this.fechaDocPago = fechaDocPago;
    }

    public Short getContratoModif() {
        return contratoModif;
    }

    public void setContratoModif(Short contratoModif) {
        this.contratoModif = contratoModif;
    }

    public String getNoResModificativa() {
        return noResModificativa;
    }

    public void setNoResModificativa(String noResModificativa) {
        this.noResModificativa = noResModificativa;
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

    public DetalleRequerimiento getIdDetRequerimiento() {
        return idDetRequerimiento;
    }

    public void setIdDetRequerimiento(DetalleRequerimiento idDetRequerimiento) {
        this.idDetRequerimiento = idDetRequerimiento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleDocPago != null ? idDetalleDocPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleDocPago)) {
            return false;
        }
        DetalleDocPago other = (DetalleDocPago) object;
        if ((this.idDetalleDocPago == null && other.idDetalleDocPago != null) || (this.idDetalleDocPago != null && !this.idDetalleDocPago.equals(other.idDetalleDocPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetalleDocPago[ idDetalleDocPago=" + idDetalleDocPago + " ]";
    }

    public List<DetallePlanilla> getDetallePlanillaList() {
        return detallePlanillaList;
    }

    public void setDetallePlanillaList(List<DetallePlanilla> detallePlanillaList) {
        this.detallePlanillaList = detallePlanillaList;
    }

    public BigDecimal getMontoRenta() {
        return montoRenta;
    }

    public void setMontoRenta(BigDecimal montoRenta) {
        this.montoRenta = montoRenta;
    }
}
