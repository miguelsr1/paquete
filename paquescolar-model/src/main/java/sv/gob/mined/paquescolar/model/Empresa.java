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
@Table(name = "EMPRESA")
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")})
public class Empresa implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpresa", fetch = FetchType.LAZY)
    private List<Participantes> participantesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpresa", fetch = FetchType.LAZY)
    private List<PreciosRefRubroEmp> preciosRefRubroEmpList = new ArrayList<PreciosRefRubroEmp>();
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EMPRESA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empresa")
    @SequenceGenerator(name = "empresa", sequenceName = "SEQ_EMPRESA", allocationSize = 1, initialValue = 1)
    private BigDecimal idEmpresa;
    @Column(name = "NOMBRE_COMERCIAL")
    private String nombreComercial;
    @Basic(optional = false)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Basic(optional = false)
    @Column(name = "NUMERO_NIT")
    private String numeroNit;
    @Basic(optional = false)
    @Column(name = "DIRECCION_COMPLETA")
    private String direccionCompleta;
    @Column(name = "NUMERO_IVA")
    private String numeroIva;
    @Column(name = "NUMERO_REG_COMERCIO")
    private String numeroRegComercio;
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @Column(name = "TELEFONOS")
    private String telefonos;
    @Column(name = "NUMERO_CELULAR")
    private String numeroCelular;
    @Column(name = "FAX")
    private String fax;
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
    @Column(name = "DISTRIBUIDOR")
    private BigInteger distribuidor;
    @Column(name = "MOSTRAR_LEYENDA")
    private BigInteger mostrarLeyenda;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpresa", fetch = FetchType.LAZY)
    private List<DetRubroMuestraInteres> detRubroMuestraInteresList = new ArrayList<DetRubroMuestraInteres>();
    @JoinColumn(name = "ID_PERSONERIA", referencedColumnName = "ID_PERSONERIA")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoPersoneria idPersoneria;
    @JoinColumn(name = "ID_TIPO_EMPRESA", referencedColumnName = "ID_TIPO_EMP")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoEmpresa idTipoEmpresa;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Persona idPersona;
    @JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID_MUNICIPIO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Municipio idMunicipio;
    @JoinColumn(name = "ID_ESTADO_REGISTRO", referencedColumnName = "ID_ESTADO_REGISTRO")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoRegistro idEstadoRegistro;
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    @Column(name = "CODIGO_CANTON")
    private String codigoCanton;

    public Empresa() {
    }

    public Empresa(BigDecimal idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Empresa(BigDecimal idEmpresa, String razonSocial, String numeroNit, String direccionCompleta, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion) {
        this.idEmpresa = idEmpresa;
        this.razonSocial = razonSocial;
        this.numeroNit = numeroNit;
        this.direccionCompleta = direccionCompleta;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigDecimal idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }

    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }

    public String getNumeroIva() {
        return numeroIva;
    }

    public void setNumeroIva(String numeroIva) {
        this.numeroIva = numeroIva;
    }

    public String getNumeroRegComercio() {
        return numeroRegComercio;
    }

    public void setNumeroRegComercio(String numeroRegComercio) {
        this.numeroRegComercio = numeroRegComercio;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public BigInteger getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(BigInteger distribuidor) {
        this.distribuidor = distribuidor;
    }

    public BigInteger getMostrarLeyenda() {
        return mostrarLeyenda;
    }

    public void setMostrarLeyenda(BigInteger mostrarLeyenda) {
        this.mostrarLeyenda = mostrarLeyenda;
    }

    public List<DetRubroMuestraInteres> getDetRubroMuestraInteresList() {
        return detRubroMuestraInteresList;
    }

    public void setDetRubroMuestraInteresList(List<DetRubroMuestraInteres> detRubroMuestraInteresList) {
        this.detRubroMuestraInteresList = detRubroMuestraInteresList;
    }

    public TipoPersoneria getIdPersoneria() {
        if (idPersoneria == null) {
            idPersoneria = new TipoPersoneria();
        }
        return idPersoneria;
    }

    public void setIdPersoneria(TipoPersoneria idPersoneria) {
        this.idPersoneria = idPersoneria;
    }

    public TipoEmpresa getIdTipoEmpresa() {
        return idTipoEmpresa;
    }

    public void setIdTipoEmpresa(TipoEmpresa idTipoEmpresa) {
        this.idTipoEmpresa = idTipoEmpresa;
    }

    public Persona getIdPersona() {
        if (idPersona == null) {
            idPersona = new Persona();
        }
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public Municipio getIdMunicipio() {
        if (idMunicipio == null) {
            idMunicipio = new Municipio();
        }
        return idMunicipio;
    }

    public void setIdMunicipio(Municipio idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public EstadoRegistro getIdEstadoRegistro() {
        return idEstadoRegistro;
    }

    public void setIdEstadoRegistro(EstadoRegistro idEstadoRegistro) {
        this.idEstadoRegistro = idEstadoRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        return !((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa)));
    }

    @Override
    public String toString() {
        return razonSocial;
    }

    public List<PreciosRefRubroEmp> getPreciosRefRubroEmpList() {
        return preciosRefRubroEmpList;
    }

    public void setPreciosRefRubroEmpList(List<PreciosRefRubroEmp> preciosRefRubroEmpList) {
        this.preciosRefRubroEmpList = preciosRefRubroEmpList;
    }

    public List<Participantes> getParticipantesList() {
        return participantesList;
    }

    public void setParticipantesList(List<Participantes> participantesList) {
        this.participantesList = participantesList;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getCodigoCanton() {
        return codigoCanton;
    }

    public void setCodigoCanton(String codigoCanton) {
        this.codigoCanton = codigoCanton;
    }
}
