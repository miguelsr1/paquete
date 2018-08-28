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

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "MATRIZ_PAGO")
@NamedQueries({
    @NamedQuery(name = "MatrizPago.findAll", query = "SELECT m FROM MatrizPago m")})
public class MatrizPago implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MATRIZ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MATRIZ")
    @SequenceGenerator(name = "SEQ_MATRIZ", sequenceName = "SEQ_MATRIZ", allocationSize = 1, initialValue = 1)
    private BigDecimal idMatriz;
    @Column(name = "NO_REQUERIMIENTO")
    private String noRequerimiento;
    @Column(name = "CODIGO_PLANILLA")
    private String codigoPlanilla;
    @Column(name = "CODIGO_DEPARTAMENTO")
    private String codigoDepartamento;
    @Column(name = "COMPONENTE")
    private String componente;
    @Column(name = "NO_CARGA")
    private BigInteger noCarga;
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
    @Column(name = "ESTADO_ELIMINACION")
    private BigInteger estadoEliminacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMatriz", fetch = FetchType.LAZY)
    @OrderBy("codigoEntidad ASC")
    private List<DetalleMatrizPago> detalleMatrizPagoList;
    @JoinColumn(name = "ID_PROCESO", referencedColumnName = "ID_PROCESO")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Proceso idProceso;
    
    @Column(name = "NOMBRE_BONO")
    private String nombreBono;
    @Column(name = "NOMBRE_FUENTE_FINAN")
    private String nombreFuenteFinan;
    @Column(name = "BANCO_PAGADURIA")
    private String bancoPagaduria;
    @Column(name = "CONCEPTO")
    private String concepto;

    public MatrizPago() {
    }

    public MatrizPago(BigDecimal idMatriz) {
        this.idMatriz = idMatriz;
    }

    public MatrizPago(BigDecimal idMatriz, String usuarioInsercion, Date fechaInsercion) {
        this.idMatriz = idMatriz;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
    }

    public BigDecimal getIdMatriz() {
        return idMatriz;
    }

    public void setIdMatriz(BigDecimal idMatriz) {
        this.idMatriz = idMatriz;
    }

    public String getNoRequerimiento() {
        return noRequerimiento;
    }

    public void setNoRequerimiento(String noRequerimiento) {
        this.noRequerimiento = noRequerimiento;
    }

    public String getCodigoPlanilla() {
        return codigoPlanilla;
    }

    public void setCodigoPlanilla(String codigoPlanilla) {
        this.codigoPlanilla = codigoPlanilla;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public BigInteger getNoCarga() {
        return noCarga;
    }

    public void setNoCarga(BigInteger noCarga) {
        this.noCarga = noCarga;
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

    public List<DetalleMatrizPago> getDetalleMatrizPagoList() {
        if(detalleMatrizPagoList == null){
            detalleMatrizPagoList = new ArrayList<DetalleMatrizPago>();
        }
        return detalleMatrizPagoList;
    }

    public void setDetalleMatrizPagoList(List<DetalleMatrizPago> detalleMatrizPagoList) {
        this.detalleMatrizPagoList = detalleMatrizPagoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMatriz != null ? idMatriz.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatrizPago)) {
            return false;
        }
        MatrizPago other = (MatrizPago) object;
        if ((this.idMatriz == null && other.idMatriz != null) || (this.idMatriz != null && !this.idMatriz.equals(other.idMatriz))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.apps.sispaqescolar.entidades.MatrizPago[ idMatriz=" + idMatriz + " ]";
    }

    public Proceso getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Proceso idProceso) {
        this.idProceso = idProceso;
    }

    public String getNombreBono() {
        return nombreBono;
    }

    public void setNombreBono(String nombreBono) {
        this.nombreBono = nombreBono;
    }

    public String getNombreFuenteFinan() {
        return nombreFuenteFinan;
    }

    public void setNombreFuenteFinan(String nombreFuenteFinan) {
        this.nombreFuenteFinan = nombreFuenteFinan;
    }

    public String getBancoPagaduria() {
        return bancoPagaduria;
    }

    public void setBancoPagaduria(String bancoPagaduria) {
        this.bancoPagaduria = bancoPagaduria;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}