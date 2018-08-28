/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
@Table(name = "DETALLE_MATRIZ_PAGO")
@NamedQueries({
    @NamedQuery(name = "DetalleMatrizPago.findAll", query = "SELECT d FROM DetalleMatrizPago d")})
public class DetalleMatrizPago implements Serializable {

    @JoinColumn(name = "ID_MATRIZ", referencedColumnName = "ID_MATRIZ")
    @ManyToOne(fetch = FetchType.EAGER)
    private MatrizPago idMatriz;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_MATRIZ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DET_MATRIZ")
    @SequenceGenerator(name = "SEQ_DET_MATRIZ", sequenceName = "SEQ_DET_MATRIZ", allocationSize = 1, initialValue = 1)
    private BigDecimal idDetalleMatriz;
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @Column(name = "MONTO_CONTRATO")
    private BigDecimal montoContrato;
    @Column(name = "MONTO_DISMINUCION")
    private BigDecimal montoDisminucion;
    @Column(name = "NO_DOC_PAGO")
    private String noDocPago;
    @Column(name = "FECHA_DOC_PAGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocPago;
    @Column(name = "MONTO_RENTA")
    private BigDecimal montoRenta;
    @Column(name = "MONTO_A_PAGAR")
    private BigDecimal montoAPagar;
    @Column(name = "MONTO_A_REINTEGRAR")
    private BigDecimal montoAReintegrar;
    @Column(name = "MONTO_TRANSFERIR_CE")
    private BigDecimal montoTransferirCe;
    @Basic(optional = false)
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Basic(optional = false)
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "USUARIO_MODIFICAICON")
    private String usuarioModificaicon;
    @Column(name = "NO_RES_MODIFICATIVA")
    private String noResModificativa;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Basic(optional = false)
    @Column(name = "ESTADO_ELIMINACION")
    private BigInteger estadoEliminacion;
    @Column(name = "ES_CONTRIBUYENTE")
    private BigInteger esContribuyente;
    @JoinColumn(name = "ID_CONTRATO", referencedColumnName = "ID_CONTRATO")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @OrderBy("idContrato ASC")
    private ContratosOrdenesCompras idContrato;
    @JoinColumn(name = "ID_DETALLE", referencedColumnName = "ID_DETALLE")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetalleCredito idDetalle;
    @JoinColumn(name = "ID_TIPO_DOC_PAGO", referencedColumnName = "ID_TIPO_DOC_PAGO")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoDocPago idTipoDocPago;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDetalleMatriz", fetch = FetchType.LAZY)
    private List<DetallePagos> detallePagosList;
    @Column(name = "MONTO_TOTAL")
    private BigDecimal montoTotal;
    @Column(name = "MONTO_PROV_SIN_CRE")
    private BigDecimal montoProvSinCre;
    @Column(name = "PLANILLA_COMPLETA")
    private BigInteger planillaCompleta;
    @Column(name = "VISIBLE_EN_MATRIZ")
    private BigInteger visibleEnMatriz;
    @Transient
    private String nombreCe;
    @Transient
    private String numeroCuenta;
    @Transient
    private Boolean eliminar;
    @Transient
    private Boolean existeProcesoPago;
    @Transient
    private String razonSocial;
    @Transient
    private BigDecimal idDetallePago;

    public DetalleMatrizPago() {
    }

    public DetalleMatrizPago(BigDecimal idDetalleMatriz) {
        this.idDetalleMatriz = idDetalleMatriz;
    }

    public DetalleMatrizPago(BigDecimal idDetalleMatriz, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion, BigInteger idContrato) {
        this.idDetalleMatriz = idDetalleMatriz;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdDetalleMatriz() {
        return idDetalleMatriz;
    }

    public void setIdDetalleMatriz(BigDecimal idDetalleMatriz) {
        this.idDetalleMatriz = idDetalleMatriz;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public BigDecimal getMontoContrato() {
        return montoContrato;
    }

    public void setMontoContrato(BigDecimal montoContrato) {
        this.montoContrato = montoContrato;
    }

    public BigDecimal getMontoDisminucion() {
        return montoDisminucion;
    }

    public void setMontoDisminucion(BigDecimal montoDisminucion) {
        this.montoDisminucion = montoDisminucion;
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

    public BigDecimal getMontoRenta() {
        return montoRenta;
    }

    public void setMontoRenta(BigDecimal montoRenta) {
        this.montoRenta = montoRenta;
    }

    public BigDecimal getMontoAPagar() {
        return montoAPagar;
    }

    public void setMontoAPagar(BigDecimal montoAPagar) {
        this.montoAPagar = montoAPagar;
    }

    public BigDecimal getMontoAReintegrar() {
        return montoAReintegrar;
    }

    public void setMontoAReintegrar(BigDecimal montoAReintegrar) {
        this.montoAReintegrar = montoAReintegrar;
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

    public String getUsuarioModificaicon() {
        return usuarioModificaicon;
    }

    public void setUsuarioModificaicon(String usuarioModificaicon) {
        this.usuarioModificaicon = usuarioModificaicon;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleMatriz != null ? idDetalleMatriz.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleMatrizPago)) {
            return false;
        }
        DetalleMatrizPago other = (DetalleMatrizPago) object;
        if ((this.idDetalleMatriz == null && other.idDetalleMatriz != null) || (this.idDetalleMatriz != null && !this.idDetalleMatriz.equals(other.idDetalleMatriz))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.apps.sispaqescolar.entidades.DetalleMatrizPago[ idDetalleMatriz=" + idDetalleMatriz + " ]";
    }

    public TipoDocPago getIdTipoDocPago() {
        return idTipoDocPago;
    }

    public void setIdTipoDocPago(TipoDocPago idTipoDocPago) {
        this.idTipoDocPago = idTipoDocPago;
    }

    public String getNombreCe() {
        return nombreCe;
    }

    public void setNombreCe(String nombreCe) {
        this.nombreCe = nombreCe;
    }

    public String getNoResModificativa() {
        return noResModificativa;
    }

    public void setNoResModificativa(String noResModificativa) {
        this.noResModificativa = noResModificativa;
    }

    public BigInteger getEsContribuyente() {
        return esContribuyente;
    }

    public void setEsContribuyente(BigInteger esContribuyente) {
        this.esContribuyente = esContribuyente;
    }

    public ContratosOrdenesCompras getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratosOrdenesCompras idContrato) {
        this.idContrato = idContrato;
    }

    public DetalleCredito getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(DetalleCredito idDetalle) {
        this.idDetalle = idDetalle;
    }

    public BigDecimal getMontoTransferirCe() {
        return montoTransferirCe;
    }

    public void setMontoTransferirCe(BigDecimal montoTransferirCe) {
        this.montoTransferirCe = montoTransferirCe;
    }

    public List<DetallePagos> getDetallePagosList() {
        if (detallePagosList == null) {
            detallePagosList = new ArrayList<DetallePagos>();
        }
        return detallePagosList;
    }

    public void setDetallePagosList(List<DetallePagos> detallePagosList) {
        this.detallePagosList = detallePagosList;
    }

    public MatrizPago getIdMatriz() {
        return idMatriz;
    }

    public void setIdMatriz(MatrizPago idMatriz) {
        this.idMatriz = idMatriz;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigInteger getPlanillaCompleta() {
        return planillaCompleta;
    }

    public void setPlanillaCompleta(BigInteger planillaCompleta) {
        this.planillaCompleta = planillaCompleta;
    }

    public BigDecimal getMontoProvSinCre() {
        return montoProvSinCre;
    }

    public void setMontoProvSinCre(BigDecimal montoProvSinCre) {
        this.montoProvSinCre = montoProvSinCre;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
        if (this.eliminar) {
            estadoEliminacion = BigInteger.ONE;
        } else {
            estadoEliminacion = BigInteger.ZERO;
        }
    }

    public Boolean getEliminar() {
        return estadoEliminacion.compareTo(BigInteger.ONE) == 0;
    }

    public BigInteger getVisibleEnMatriz() {
        return visibleEnMatriz;
    }

    public void setVisibleEnMatriz(BigInteger visibleEnMatriz) {
        this.visibleEnMatriz = visibleEnMatriz;
    }

    public Boolean getExisteProcesoPago() {
        existeProcesoPago = !getDetallePagosList().isEmpty();

        return existeProcesoPago;
    }

    public void setExisteProcesoPago(Boolean existeProcesoPago) {
        this.existeProcesoPago = existeProcesoPago;
    }

    public String getRazonSocial() {
        return getIdContrato().getIdResolucionAdj().getIdParticipante().getIdEmpresa().getRazonSocial();
    }

    public BigDecimal getIdDetallePago() {
        if (detallePagosList.isEmpty()) {
            return null;
        } else {
            return detallePagosList.get(0).getIdDetallePagos();
        }
    }
}