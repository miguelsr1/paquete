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
import javax.persistence.Transient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PERSONA")
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")})
public class Persona implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersona", fetch = FetchType.LAZY)
    private List<Empresa> empresaList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PERSONA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona")
    @SequenceGenerator(name = "persona", sequenceName = "SEQ_PERSONA", allocationSize = 1, initialValue = 1)
    private BigDecimal idPersona;
    @Column(name = "PRIMER_APELLIDO")
    private String primerApellido;
    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;
    @Column(name = "PRIMER_NOMBRE")
    private String primerNombre;
    @Column(name = "SEGUNDO_NOMBRE")
    private String segundoNombre;
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "NUMERO_DUI")
    private String numeroDui;
    @Column(name = "NUMERO_NIT")
    private String numeroNit;
    @Column(name = "NUMERO_TELEFONO")
    private String numeroTelefono;
    @Column(name = "NUMERO_CELULAR")
    private String numeroCelular;
    @Column(name = "DOMICILIO")
    private String domicilio;
    @Column(name = "PROFESION")
    private String profesion;
    @Column(name = "ACASADA")
    private String acasada;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "INACTIVO")
    private BigInteger inactivo;
    @Column(name = "LECTURA_FIRMA")
    private String lecturaFirma;
    @Column(name = "NUMERO_DOCUMENTO_LEGAL")
    private String numeroDocumentoLegal;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "CLAVE_ACCESO")
    private String claveAcceso;
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
    @Column(name = "URL_IMAGEN")
    private String urlImagen;
    @Column(name = "URL_DUI")
    private String urlDui;
    @Column(name = "URL_NIT")
    private String urlNit;
    @Column(name = "URL_DECLARACION")
    private String urlDeclaracion;
    @JoinColumn(name = "ID_DOC_LEGAL", referencedColumnName = "ID_DOC_LEGAL")
    @ManyToOne(fetch = FetchType.EAGER)
    private TiposDocLegal idDocLegal;
    @JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID_MUNICIPIO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Municipio idMunicipio;
    @JoinColumn(name = "ID_GENERO", referencedColumnName = "ID_GENERO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Genero idGenero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersona", fetch = FetchType.LAZY)
    private List<Usuario> usuarioList;

    @Column(name = "NUM_TELEFONO2")
    private String numTelefono2;
    @Column(name = "NUM_TELEFONO3")
    private String numTelefono3;
    @Transient
    private String foto;
    @Transient
    private String nombreCompleto;

    public Persona() {
    }

    public Persona(BigDecimal idPersona) {
        this.idPersona = idPersona;
    }

    public Persona(BigDecimal idPersona, Date fechaInsercion, BigInteger estadoEliminacion) {
        this.idPersona = idPersona;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public String getNumTelefono2() {
        return numTelefono2;
    }

    public void setNumTelefono2(String numTelefono2) {
        this.numTelefono2 = numTelefono2;
    }

    public String getNumTelefono3() {
        return numTelefono3;
    }

    public void setNumTelefono3(String numTelefono3) {
        this.numTelefono3 = numTelefono3;
    }

    public BigDecimal getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(BigDecimal idPersona) {
        this.idPersona = idPersona;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroDui() {
        return numeroDui;
    }

    public void setNumeroDui(String numeroDui) {
        this.numeroDui = numeroDui;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getAcasada() {
        return acasada;
    }

    public void setAcasada(String acasada) {
        this.acasada = acasada;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getInactivo() {
        return inactivo;
    }

    public void setInactivo(BigInteger inactivo) {
        this.inactivo = inactivo;
    }

    public String getLecturaFirma() {
        return lecturaFirma;
    }

    public void setLecturaFirma(String lecturaFirma) {
        this.lecturaFirma = lecturaFirma;
    }

    public String getNumeroDocumentoLegal() {
        return numeroDocumentoLegal;
    }

    public void setNumeroDocumentoLegal(String numeroDocumentoLegal) {
        this.numeroDocumentoLegal = numeroDocumentoLegal;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuario() {
        if (usuario == null) {
            return "";
        }
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
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

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getUrlDui() {
        return urlDui;
    }

    public void setUrlDui(String urlDui) {
        this.urlDui = urlDui;
    }

    public String getUrlNit() {
        return urlNit;
    }

    public void setUrlNit(String urlNit) {
        this.urlNit = urlNit;
    }

    public String getUrlDeclaracion() {
        return urlDeclaracion;
    }

    public void setUrlDeclaracion(String urlDeclaracion) {
        this.urlDeclaracion = urlDeclaracion;
    }

    public TiposDocLegal getIdDocLegal() {
        return idDocLegal;
    }

    public void setIdDocLegal(TiposDocLegal idDocLegal) {
        this.idDocLegal = idDocLegal;
    }

    public Municipio getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Municipio idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Genero getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Genero idGenero) {
        this.idGenero = idGenero;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        return !((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.Persona[ idPersona=" + idPersona + " ]";
    }

    public List<Empresa> getEmpresaList() {
        return empresaList;
    }

    public void setEmpresaList(List<Empresa> empresaList) {
        this.empresaList = empresaList;
    }

    public String getFoto() {
        if (urlImagen == null) {
            foto = "sin_foto.png";
        } else {
            foto = urlImagen;
        }
        return foto;
    }

    private String getApellidos() {
        if (acasada == null) {
            if (segundoApellido == null) {
                if (primerApellido == null) {
                    return "";
                } else {
                    return primerApellido;
                }
            } else {
                return primerApellido + " " + segundoApellido;
            }
        } else {
            return primerApellido + " " + acasada;
        }
    }

    private String getNombres() {
        if (segundoNombre == null) {
            if (primerNombre == null) {
                return "";
            } else {
                return primerNombre;
            }
        } else {
            return primerNombre + " " + segundoNombre;
        }
    }

    public String getNombreCompletoProveedor() {
        return getNombres() + " " + getApellidos();
    }

    public String getNombreCompleto() {
        if (getUsuario().isEmpty()) {
            return "";
        } else {
            return getNombres() + " " + getApellidos() + " (" + getUsuario() + ")";
        }
    }
}
