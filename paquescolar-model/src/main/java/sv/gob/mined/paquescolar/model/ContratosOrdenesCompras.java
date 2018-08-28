/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.annotations.AdditionalCriteria;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "CONTRATOS_ORDENES_COMPRAS")
@NamedQueries({
    @NamedQuery(name = "ContratosOrdenesCompras.findAll", query = "SELECT c FROM ContratosOrdenesCompras c")})
@AdditionalCriteria("this.estadoEliminacion = 0")
public class ContratosOrdenesCompras implements Serializable {
    @Column(name = "ID_CONTRATO_PADRE")
    private BigInteger idContratoPadre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idContrato", fetch = FetchType.LAZY)
    private List<RecepcionBienesServicios> recepcionBienesServiciosList;
    @Basic(optional = false)
    @Column(name = "MODIFICATIVA")
    private short modificativa;
    
    @OneToMany(mappedBy = "idContrato", fetch = FetchType.LAZY)
    private List<DetalleCredito> detalleCreditoList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CONTRATO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contrato")
    @SequenceGenerator(name="contrato", sequenceName = "SEQ_CONTRATO", allocationSize=1, initialValue=1)
    private BigDecimal idContrato;
    @Column(name = "ACTIVO")
    private BigInteger activo;
    @Column(name = "NUMERO_CONTRATO")
    private String numeroContrato;
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "TIENE_ANTICIPO")
    private BigInteger tieneAnticipo;
    @Column(name = "TIENE_RETENCION")
    private BigInteger tieneRetencion;
    @Column(name = "PLAZO_PREVISTO_ENTREGA")
    private BigInteger plazoPrevistoEntrega;
    @Column(name = "CIUDAD_FIRMA")
    private String ciudadFirma;
    @Column(name = "FECHA_EMISION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;
    @Column(name = "ANHO_CONTRATO")
    private String anhoContrato;
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
    @Column(name = "FECHA_ORDEN_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOrdenInicio;
    @Column(name = "MIEMBRO_FIRMA")
    private String miembroFirma;
    @JoinColumn(name = "ID_RESOLUCION_ADJ", referencedColumnName = "ID_RESOLUCION_ADJ")
    @ManyToOne(fetch = FetchType.EAGER)
    private ResolucionesAdjudicativas idResolucionAdj;
    /*@OneToMany(mappedBy = "idContratoPadre", fetch = FetchType.LAZY)
    private List<ContratosOrdenesCompras> contratosOrdenesComprasList;*/
    /*@JoinColumn(name = "ID_CONTRATO_PADRE", referencedColumnName = "ID_CONTRATO")
    @ManyToOne(fetch = FetchType.EAGER)
    private ContratosOrdenesCompras idContratoPadre;*/
    @Basic(optional = false)
    @Column(name = "PRIMERO")
    private Short primero;

    public ContratosOrdenesCompras() {
    }

    public ContratosOrdenesCompras(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public ContratosOrdenesCompras(BigDecimal idContrato, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion) {
        this.idContrato = idContrato;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }

    public BigInteger getActivo() {
        return activo;
    }

    public void setActivo(BigInteger activo) {
        this.activo = activo;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigInteger getTieneAnticipo() {
        return tieneAnticipo;
    }

    public void setTieneAnticipo(BigInteger tieneAnticipo) {
        this.tieneAnticipo = tieneAnticipo;
    }

    public BigInteger getTieneRetencion() {
        return tieneRetencion;
    }

    public void setTieneRetencion(BigInteger tieneRetencion) {
        this.tieneRetencion = tieneRetencion;
    }

    public BigInteger getPlazoPrevistoEntrega() {
        return plazoPrevistoEntrega;
    }

    public void setPlazoPrevistoEntrega(BigInteger plazoPrevistoEntrega) {
        this.plazoPrevistoEntrega = plazoPrevistoEntrega;
    }

    public String getCiudadFirma() {
        return ciudadFirma;
    }

    public void setCiudadFirma(String ciudadFirma) {
        this.ciudadFirma = ciudadFirma;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getAnhoContrato() {
        return anhoContrato;
    }

    public void setAnhoContrato(String anhoContrato) {
        this.anhoContrato = anhoContrato;
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

    public Date getFechaOrdenInicio() {
        return fechaOrdenInicio;
    }

    public void setFechaOrdenInicio(Date fechaOrdenInicio) {
        this.fechaOrdenInicio = fechaOrdenInicio;
    }

    public String getMiembroFirma() {
        return miembroFirma;
    }

    public void setMiembroFirma(String miembroFirma) {
        this.miembroFirma = miembroFirma;
    }

    public ResolucionesAdjudicativas getIdResolucionAdj() {
        return idResolucionAdj;
    }

    public void setIdResolucionAdj(ResolucionesAdjudicativas idResolucionAdj) {
        this.idResolucionAdj = idResolucionAdj;
    }

    /*public List<ContratosOrdenesCompras> getContratosOrdenesComprasList() {
        return contratosOrdenesComprasList;
    }

    public void setContratosOrdenesComprasList(List<ContratosOrdenesCompras> contratosOrdenesComprasList) {
        this.contratosOrdenesComprasList = contratosOrdenesComprasList;
    }*/
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContrato != null ? idContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContratosOrdenesCompras)) {
            return false;
        }
        ContratosOrdenesCompras other = (ContratosOrdenesCompras) object;
        if ((this.idContrato == null && other.idContrato != null) || (this.idContrato != null && !this.idContrato.equals(other.idContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.ContratosOrdenesCompras[ idContrato=" + idContrato + " ]";
    }

    public List<DetalleCredito> getDetalleCreditoList() {
        return detalleCreditoList;
    }

    public void setDetalleCreditoList(List<DetalleCredito> detalleCreditoList) {
        this.detalleCreditoList = detalleCreditoList;
    }

    public BigInteger getIdContratoPadre() {
        return idContratoPadre;
    }

    public void setIdContratoPadre(BigInteger idContratoPadre) {
        this.idContratoPadre = idContratoPadre;
    }

    public List<RecepcionBienesServicios> getRecepcionBienesServiciosList() {
        return recepcionBienesServiciosList;
    }

    public void setRecepcionBienesServiciosList(List<RecepcionBienesServicios> recepcionBienesServiciosList) {
        this.recepcionBienesServiciosList = recepcionBienesServiciosList;
    }

    public short getModificativa() {
        return modificativa;
    }

    public void setModificativa(short modificativa) {
        this.modificativa = modificativa;
    }

    public Short getPrimero() {
        return primero;
    }

    public void setPrimero(Short primero) {
        this.primero = primero;
    }
}
