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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DET_RUBRO_MUESTRA_INTERES")
@NamedQueries({
    @NamedQuery(name = "DetRubroMuestraInteres.findAll", query = "SELECT d FROM DetRubroMuestraInteres d")})
public class DetRubroMuestraInteres implements Serializable {

    @OneToMany(mappedBy = "idMuestraInteres", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProveedorEmpresa> proveedorEmpresaList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MUESTRA_INTERES")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "det_rubro")
    @SequenceGenerator(name = "det_rubro", sequenceName = "SEQ_DET_RUBRO_MUESTRA_INTERES", allocationSize = 1, initialValue = 1)
    private BigDecimal idMuestraInteres;
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
    @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID_EMPRESA")
    @ManyToOne
    private Empresa idEmpresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMuestraInteres", fetch = FetchType.LAZY)
    private List<DetCapaSegunRubro> detCapaSegunRubroList = new ArrayList<DetCapaSegunRubro>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMuestraInteres", fetch = FetchType.LAZY)
    private List<CapaDistribucionAcre> capaDistribucionAcreList = new ArrayList<CapaDistribucionAcre>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMuestraInteres", fetch = FetchType.LAZY)
    private List<CapaInstPorRubro> capaInstPorRubroList = new ArrayList<CapaInstPorRubro>();
    @JoinColumn(name = "ID_DET_PROCESO_ADQ", referencedColumnName = "ID_DET_PROCESO_ADQ")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetalleProcesoAdq idDetProcesoAdq;

    public DetRubroMuestraInteres() {
    }

    public DetRubroMuestraInteres(BigDecimal idMuestraInteres) {
        this.idMuestraInteres = idMuestraInteres;
    }

    public DetRubroMuestraInteres(BigDecimal idMuestraInteres, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion) {
        this.idMuestraInteres = idMuestraInteres;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdMuestraInteres() {
        return idMuestraInteres;
    }

    public void setIdMuestraInteres(BigDecimal idMuestraInteres) {
        this.idMuestraInteres = idMuestraInteres;
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

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public List<DetCapaSegunRubro> getDetCapaSegunRubroList() {
        return detCapaSegunRubroList;
    }

    public void setDetCapaSegunRubroList(List<DetCapaSegunRubro> detCapaSegunRubroList) {
        this.detCapaSegunRubroList = detCapaSegunRubroList;
    }

    public List<CapaDistribucionAcre> getCapaDistribucionAcreList() {
        return capaDistribucionAcreList;
    }

    public void setCapaDistribucionAcreList(List<CapaDistribucionAcre> capaDistribucionAcreList) {
        this.capaDistribucionAcreList = capaDistribucionAcreList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMuestraInteres != null ? idMuestraInteres.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetRubroMuestraInteres)) {
            return false;
        }
        DetRubroMuestraInteres other = (DetRubroMuestraInteres) object;
        if ((this.idMuestraInteres == null && other.idMuestraInteres != null) || (this.idMuestraInteres != null && !this.idMuestraInteres.equals(other.idMuestraInteres))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetRubroMuestraInteres[ idMuestraInteres=" + idMuestraInteres + " ]";
    }

    public List<CapaInstPorRubro> getCapaInstPorRubroList() {
        return capaInstPorRubroList;
    }

    public void setCapaInstPorRubroList(List<CapaInstPorRubro> capaInstPorRubroList) {
        this.capaInstPorRubroList = capaInstPorRubroList;
    }

    public DetalleProcesoAdq getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(DetalleProcesoAdq idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    public List<ProveedorEmpresa> getProveedorEmpresaList() {
        return proveedorEmpresaList;
    }

    public void setProveedorEmpresaList(List<ProveedorEmpresa> proveedorEmpresaList) {
        this.proveedorEmpresaList = proveedorEmpresaList;
    }
}