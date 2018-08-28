/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_PAGOS")
@NamedQueries({
    @NamedQuery(name = "DetallePagos.findAll", query = "SELECT d FROM DetallePagos d")})
public class DetallePagos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_PAGOS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DETALLE_PAGO")
    @SequenceGenerator(name = "SEQ_DETALLE_PAGO", sequenceName = "SEQ_DETALLE_PAGO", allocationSize = 1, initialValue = 1)
    private BigDecimal idDetallePagos;
    @Column(name = "MONTO_CHEQUE_PROV")
    private BigDecimal montoChequeProv;
    @Column(name = "NUM_CHEQUE_PROV")
    private String numChequeProv;
    @Column(name = "FECHA_CHEQUE_PROV")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaChequeProv;
    @Column(name = "MONTO_CHEQUE_USEFI")
    private BigDecimal montoChequeUsefi;
    @Column(name = "NUM_CHEQUE_USEFI")
    private String numChequeUsefi;
    @Column(name = "FECHA_CHEQUE_USEFI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaChequeUsefi;
    @Column(name = "BANCO")
    private String banco;
    @Basic(optional = false)
    @Column(name = "ID_BANCO")
    private BigInteger idBanco;
    @Column(name = "BANCO_CE")
    private String bancoCe;
    @Basic(optional = false)
    @Column(name = "ID_BANCO_CE")
    private BigInteger idBancoCe;
    @Basic(optional = false)
    @Column(name = "ES_RENTA")
    private BigInteger esRenta;
    @Basic(optional = false)
    @Column(name = "ES_CONTRIBUYENTE")
    private BigInteger esContribuyente;
    @Basic(optional = false)
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Basic(optional = false)
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
    @Basic(optional = false)
    @Column(name = "ESTADO_ELIMINACION")
    private BigInteger estadoEliminacion;
    @JoinColumn(name = "ID_DETALLE_MATRIZ", referencedColumnName = "ID_DETALLE_MATRIZ")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetalleMatrizPago idDetalleMatriz;
    @Column(name = "ID_PLANILLA_PARCIAL")
    private BigInteger idPlanillaParcial;
    @Column(name = "MONTO_CHEQUE_CE")
    private BigDecimal montoChequeCe;
    @Column(name = "NUM_CHEQUE_CE")
    private String numChequeCe;
    @Column(name = "FECHA_CHEQUE_CE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaChequeCe;
    
    public DetallePagos() {
    }

    public DetallePagos(BigDecimal idDetallePagos) {
        this.idDetallePagos = idDetallePagos;
    }

    public DetallePagos(BigDecimal idDetallePagos, BigInteger idBanco, BigInteger esRenta, BigInteger esContribuyente, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion) {
        this.idDetallePagos = idDetallePagos;
        this.idBanco = idBanco;
        this.esRenta = esRenta;
        this.esContribuyente = esContribuyente;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdDetallePagos() {
        return idDetallePagos;
    }

    public void setIdDetallePagos(BigDecimal idDetallePagos) {
        this.idDetallePagos = idDetallePagos;
    }

    public BigDecimal getMontoChequeProv() {
        return montoChequeProv;
    }

    public void setMontoChequeProv(BigDecimal montoChequeProv) {
        this.montoChequeProv = montoChequeProv;
    }

    public String getNumChequeProv() {
        return numChequeProv;
    }

    public void setNumChequeProv(String numChequeProv) {
        this.numChequeProv = numChequeProv;
    }

    public Date getFechaChequeProv() {
        return fechaChequeProv;
    }

    public void setFechaChequeProv(Date fechaChequeProv) {
        this.fechaChequeProv = fechaChequeProv;
    }

    public BigDecimal getMontoChequeUsefi() {
        return montoChequeUsefi;
    }

    public void setMontoChequeUsefi(BigDecimal montoChequeUsefi) {
        this.montoChequeUsefi = montoChequeUsefi;
    }

    public String getNumChequeUsefi() {
        return numChequeUsefi;
    }

    public void setNumChequeUsefi(String numChequeUsefi) {
        this.numChequeUsefi = numChequeUsefi;
    }

    public Date getFechaChequeUsefi() {
        return fechaChequeUsefi;
    }

    public void setFechaChequeUsefi(Date fechaChequeUsefi) {
        this.fechaChequeUsefi = fechaChequeUsefi;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public BigInteger getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(BigInteger idBanco) {
        this.idBanco = idBanco;
    }

    public BigInteger getEsRenta() {
        return esRenta;
    }

    public void setEsRenta(BigInteger esRenta) {
        this.esRenta = esRenta;
    }

    public BigInteger getEsContribuyente() {
        return esContribuyente;
    }

    public void setEsContribuyente(BigInteger esContribuyente) {
        this.esContribuyente = esContribuyente;
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

    public BigInteger getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(BigInteger estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public DetalleMatrizPago getIdDetalleMatriz() {
        return idDetalleMatriz;
    }

    public void setIdDetalleMatriz(DetalleMatrizPago idDetalleMatriz) {
        this.idDetalleMatriz = idDetalleMatriz;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetallePagos != null ? idDetallePagos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallePagos)) {
            return false;
        }
        DetallePagos other = (DetallePagos) object;
        if ((this.idDetallePagos == null && other.idDetallePagos != null) || (this.idDetallePagos != null && !this.idDetallePagos.equals(other.idDetallePagos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.apps.sispaqescolar.entidades.DetallePagos[ idDetallePagos=" + idDetallePagos + " ]";
    }

    public BigInteger getIdPlanillaParcial() {
        return idPlanillaParcial;
    }

    public void setIdPlanillaParcial(BigInteger idPlanillaParcial) {
        this.idPlanillaParcial = idPlanillaParcial;
    }

    public BigDecimal getMontoChequeCe() {
        return montoChequeCe;
    }

    public void setMontoChequeCe(BigDecimal montoChequeCe) {
        this.montoChequeCe = montoChequeCe;
    }

    public String getNumChequeCe() {
        return numChequeCe;
    }

    public void setNumChequeCe(String numChequeCe) {
        this.numChequeCe = numChequeCe;
    }

    public Date getFechaChequeCe() {
        return fechaChequeCe;
    }

    public void setFechaChequeCe(Date fechaChequeCe) {
        this.fechaChequeCe = fechaChequeCe;
    }

    public String getBancoCe() {
        return bancoCe;
    }

    public void setBancoCe(String bancoCe) {
        this.bancoCe = bancoCe;
    }

    public BigInteger getIdBancoCe() {
        return idBancoCe;
    }

    public void setIdBancoCe(BigInteger idBancoCe) {
        this.idBancoCe = idBancoCe;
    }
}