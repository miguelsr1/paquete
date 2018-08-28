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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "REQUERIMIENTO_FONDOS")
@NamedQueries({
    @NamedQuery(name = "RequerimientoFondos.findAll", query = "SELECT r FROM RequerimientoFondos r")})
public class RequerimientoFondos implements Serializable {

    @Basic(optional = false)
    @Column(name = "CREDITO")
    private Short credito;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRequerimiento", fetch = FetchType.LAZY)
    private List<ReintegroRequerimiento> reintegroRequerimientoList;

    @OneToMany(mappedBy = "idRequerimiento", fetch = FetchType.LAZY)
    private List<PlanillaPago> planillaPagoList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_REQUERIMIENTO")
    private BigDecimal idRequerimiento;
    @Column(name = "NUMERO_REQUERIMIENTO")
    private Integer numeroRequerimiento;
    @Column(name = "FORMATO_REQUERIMIENTO")
    private String formatoRequerimiento;
    @Column(name = "FUENTE_FINANCIAMIENTO")
    private String fuenteFinanciamiento;
    @Column(name = "MONTO_TOTAL")
    private BigDecimal montoTotal;
    @Column(name = "CONCEPTO")
    private String concepto;
    @Column(name = "COMPONENTE")
    private String componente;
    @Column(name = "LINEA")
    private String linea;
    @Column(name = "FECHA_ELABORACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaElaboracion;
    @Column(name = "ID_BANCO")
    private BigInteger idBanco;
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "USUARIO_ELIMINACION")
    private String usuarioEliminacion;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;
    @Column(name = "CODIGO_DEPARTAMENTO")
    private String codigoDepartamento;
    @Column(name = "ID_NIVEL_EDUCATIVO")
    private BigInteger idNivelEducativo;
    @JoinColumn(name = "ID_DET_PROCESO_ADQ", referencedColumnName = "ID_DET_PROCESO_ADQ")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetalleProcesoAdq idDetProcesoAdq;
    @Column(name = "DIRECTOR_DEPARTAMENTAL")
    private String directorDepartamental;
    @Column(name = "PAGADOR_DEPARTAMENTAL")
    private String pagadorDepartamental;
    @OneToMany(mappedBy = "idRequerimiento", fetch = FetchType.LAZY)
    @OrderBy("codigoEntidad ASC")
    private List<DetalleRequerimiento> detalleRequerimientoList;
    @Column(name = "CORRELATIVO_GENERAL")
    private Integer correlativoGeneral;

    @Transient
    private int numeroDetalle;
    @Transient
    private int numeroPlanillasPago;
    @Transient
    private BigDecimal montoTotalPlanilla;
    @Transient
    private BigDecimal montoTotalReintegrar;

    public RequerimientoFondos() {
    }

    public RequerimientoFondos(BigDecimal idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public BigDecimal getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(BigDecimal idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public Integer getNumeroRequerimiento() {
        return numeroRequerimiento;
    }

    public void setNumeroRequerimiento(Integer numeroRequerimiento) {
        this.numeroRequerimiento = numeroRequerimiento;
    }

    public String getFormatoRequerimiento() {
        return formatoRequerimiento;
    }

    public void setFormatoRequerimiento(String formatoRequerimiento) {
        this.formatoRequerimiento = formatoRequerimiento;
    }

    public String getFuenteFinanciamiento() {
        return fuenteFinanciamiento;
    }

    public void setFuenteFinanciamiento(String fuenteFinanciamiento) {
        this.fuenteFinanciamiento = fuenteFinanciamiento;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public BigInteger getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(BigInteger idBanco) {
        this.idBanco = idBanco;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
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

    public String getUsuarioEliminacion() {
        return usuarioEliminacion;
    }

    public void setUsuarioEliminacion(String usuarioEliminacion) {
        this.usuarioEliminacion = usuarioEliminacion;
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

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public BigInteger getIdNivelEducativo() {
        return idNivelEducativo;
    }

    public void setIdNivelEducativo(BigInteger idNivelEducativo) {
        this.idNivelEducativo = idNivelEducativo;
    }

    public DetalleProcesoAdq getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(DetalleProcesoAdq idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    public String getDirectorDepartamental() {
        return directorDepartamental;
    }

    public void setDirectorDepartamental(String directorDepartamental) {
        this.directorDepartamental = directorDepartamental;
    }

    public String getPagadorDepartamental() {
        return pagadorDepartamental;
    }

    public void setPagadorDepartamental(String pagadorDepartamental) {
        this.pagadorDepartamental = pagadorDepartamental;
    }

    public List<DetalleRequerimiento> getDetalleRequerimientoList() {
        if (detalleRequerimientoList == null) {
            detalleRequerimientoList = new ArrayList();
        }
        return detalleRequerimientoList;
    }

    public void setDetalleRequerimientoList(List<DetalleRequerimiento> detalleRequerimientoList) {
        this.detalleRequerimientoList = detalleRequerimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequerimiento != null ? idRequerimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequerimientoFondos)) {
            return false;
        }
        RequerimientoFondos other = (RequerimientoFondos) object;
        return !((this.idRequerimiento == null && other.idRequerimiento != null) || (this.idRequerimiento != null && !this.idRequerimiento.equals(other.idRequerimiento)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.RequerimientoFondos[ idRequerimiento=" + idRequerimiento + " ]";
    }

    public Integer getCorrelativoGeneral() {
        return correlativoGeneral;
    }

    public void setCorrelativoGeneral(Integer correlativoGeneral) {
        this.correlativoGeneral = correlativoGeneral;
    }

    public List<PlanillaPago> getPlanillaPagoList() {
        if (planillaPagoList == null) {
            planillaPagoList = new ArrayList();
        }
        return planillaPagoList;
    }

    public void setPlanillaPagoList(List<PlanillaPago> planillaPagoList) {
        this.planillaPagoList = planillaPagoList;
    }

    public int getNumeroPlanillasPago() {
        return getPlanillaPagoList().size();
    }

    public BigDecimal getMontoTotalPlanilla() {
        montoTotalPlanilla = BigDecimal.ZERO;
        for (PlanillaPago planillaPago : planillaPagoList) {
            montoTotalPlanilla = montoTotalPlanilla.add(planillaPago.getMontoTotal());
        }
        return montoTotalPlanilla;
    }

    public int getNumeroDetalle() {
        numeroDetalle = 0;
        if (detalleRequerimientoList != null) {
            for (DetalleRequerimiento detalleRequerimiento : detalleRequerimientoList) {
                if (detalleRequerimiento.getActivo().intValue() == 0) {
                    numeroDetalle += 1;
                }
            }
        }
        return numeroDetalle;
    }

    /*public BigDecimal getMontoTotalReintegrar() {
        montoTotalReintegrar = BigDecimal.ZERO;
        for (PlanillaPago planillaPago : getPlanillaPagoList()) {
            montoTotalReintegrar = montoTotalReintegrar.add(planillaPago.getMontoTotalReintegrar());
        }
        return montoTotalReintegrar;
    }*/
    
    public Short getCredito() {
        return credito;
    }

    public void setCredito(Short credito) {
        this.credito = credito;
    }

    public List<ReintegroRequerimiento> getReintegroRequerimientoList() {
        return reintegroRequerimientoList;
    }

    public void setReintegroRequerimientoList(List<ReintegroRequerimiento> reintegroRequerimientoList) {
        this.reintegroRequerimientoList = reintegroRequerimientoList;
    }
}
