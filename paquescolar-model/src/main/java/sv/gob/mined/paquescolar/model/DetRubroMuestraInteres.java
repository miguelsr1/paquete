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
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DET_RUBRO_MUESTRA_INTERES")
@NamedQueries({
    @NamedQuery(name = "DetRubroMuestraInteres.findAll", query = "SELECT d FROM DetRubroMuestraInteres d")})
@NamedStoredProcedureQuery(
        name = "SP_GET_ID_GESTION",
        procedureName = "SP_GET_ID_GESTION",
        returnsResultSet = false,
        parameters = {
            @StoredProcedureParameter(mode =  ParameterMode.IN, type = BigDecimal.class, name = "P_ID_EMPRESA", queryParameter = "P_ID_EMPRESA"),
            @StoredProcedureParameter(mode =  ParameterMode.IN, type = Integer.class, name = "P_ID_MUESTRA_INTERES", queryParameter = "P_ID_MUESTRA_INTERES"),
            @StoredProcedureParameter(mode =  ParameterMode.OUT, type = String.class, name = "P_ID_GESTION", queryParameter = "P_ID_GESTION")
        }
)
public class DetRubroMuestraInteres implements Serializable {

    @OneToMany(mappedBy = "idMuestraInteres", fetch = FetchType.LAZY)
    private List<PreciosRefRubroEmp> preciosRefRubroEmpList;
    @JoinColumn(name = "ID_ANHO", referencedColumnName = "ID_ANHO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Anho idAnho;
    @JoinColumn(name = "ID_RUBRO_INTERES", referencedColumnName = "ID_RUBRO_INTERES")
    @ManyToOne(fetch = FetchType.LAZY)
    private RubrosAmostrarInteres idRubroInteres;

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
    
    @Column(name = "DATOS_VERIFICADOS")
    private Short datosVerificados;
    @Column(name = "ACEPTACION_TERMINOS")
    private Short aceptacionTerminos;
    @Column(name = "ID_GESTION")
    private String idGestion;

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

    public Short getAceptacionTerminos() {
        return aceptacionTerminos;
    }

    public void setAceptacionTerminos(Short aceptacionTerminos) {
        this.aceptacionTerminos = aceptacionTerminos;
    }

    public String getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(String idGestion) {
        this.idGestion = idGestion;
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
        return !((this.idMuestraInteres == null && other.idMuestraInteres != null) || (this.idMuestraInteres != null && !this.idMuestraInteres.equals(other.idMuestraInteres)));
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

    public List<ProveedorEmpresa> getProveedorEmpresaList() {
        return proveedorEmpresaList;
    }

    public void setProveedorEmpresaList(List<ProveedorEmpresa> proveedorEmpresaList) {
        this.proveedorEmpresaList = proveedorEmpresaList;
    }

    public Short getDatosVerificados() {
        return datosVerificados;
    }

    public void setDatosVerificados(Short datosVerificados) {
        this.datosVerificados = datosVerificados;
    }

    public List<PreciosRefRubroEmp> getPreciosRefRubroEmpList() {
        return preciosRefRubroEmpList;
    }

    public void setPreciosRefRubroEmpList(List<PreciosRefRubroEmp> preciosRefRubroEmpList) {
        this.preciosRefRubroEmpList = preciosRefRubroEmpList;
    }

    public Anho getIdAnho() {
        return idAnho;
    }

    public void setIdAnho(Anho idAnho) {
        this.idAnho = idAnho;
    }

    public RubrosAmostrarInteres getIdRubroInteres() {
        return idRubroInteres;
    }

    public void setIdRubroInteres(RubrosAmostrarInteres idRubroInteres) {
        this.idRubroInteres = idRubroInteres;
    }
}