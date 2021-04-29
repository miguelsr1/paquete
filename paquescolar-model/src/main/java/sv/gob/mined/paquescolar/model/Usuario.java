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
import javax.persistence.ManyToMany;
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
@Table(name = "USUARIO")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private List<UsuarioEntidadFinanciera> usuarioEntidadFinancieraList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
    @SequenceGenerator(name = "usuario", sequenceName = "SEQ_USUARIO", allocationSize = 1, initialValue = 1)
    private BigDecimal idUsuario;
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
    private BigInteger estadoEliminacion;
    @Basic(optional = false)
    @Column(name = "CODIGO_DEPARTAMENTO")
    private String codigoDepartamento;
    @Column(name = "TOKEN_CAMBIO_CLAVE")
    private String tokenCambioClave;
    @ManyToMany(mappedBy = "usuarioList")
    private List<OpcionMenu> opcionMenuList;
    @JoinColumn(name = "ID_TIPO_USUARIO", referencedColumnName = "ID_TIPO_USUARIO")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoUsuario idTipoUsuario;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Persona idPersona;
    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private List<UsuarioModulo> usuarioModuloList;
    @Column(name = "FECHA_VENCIMIENTO_CLAVE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimientoClave;
    @Column(name = "FECHA_INICIO_LOGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioLogin;
    @Column(name = "FECHA_FIN_LOGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinLogin;
    @Column(name = "RANGO_FECHA_LOGIN")
    private Short rangoFechaLogin;
    @Column(name = "ACTIVO")
    private Short activo;
    @Column(name = "CAMBIAR_CLAVE")
    private Short cambiarClave;

    @Transient
    private String msj;

    public Usuario() {
    }

    public String getTokenCambioClave() {
        return tokenCambioClave;
    }

    public void setTokenCambioClave(String tokenCambioClave) {
        this.tokenCambioClave = tokenCambioClave;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public Usuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(BigDecimal idUsuario, String codigoDepartamento) {
        this.idUsuario = idUsuario;
        this.codigoDepartamento = codigoDepartamento;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public List<OpcionMenu> getOpcionMenuList() {
        return opcionMenuList;
    }

    public void setOpcionMenuList(List<OpcionMenu> opcionMenuList) {
        this.opcionMenuList = opcionMenuList;
    }

    public TipoUsuario getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(TipoUsuario idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public List<UsuarioModulo> getUsuarioModuloList() {
        return usuarioModuloList;
    }

    public void setUsuarioModuloList(List<UsuarioModulo> usuarioModuloList) {
        this.usuarioModuloList = usuarioModuloList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        return !((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.Usuario[ idUsuario=" + idUsuario + " ]";
    }

    public List<UsuarioEntidadFinanciera> getUsuarioEntidadFinancieraList() {
        return usuarioEntidadFinancieraList;
    }

    public void setUsuarioEntidadFinancieraList(List<UsuarioEntidadFinanciera> usuarioEntidadFinancieraList) {
        this.usuarioEntidadFinancieraList = usuarioEntidadFinancieraList;
    }

    public Date getFechaVencimientoClave() {
        return fechaVencimientoClave;
    }

    public void setFechaVencimientoClave(Date fechaVencimientoClave) {
        this.fechaVencimientoClave = fechaVencimientoClave;
    }

    public Date getFechaInicioLogin() {
        return fechaInicioLogin;
    }

    public void setFechaInicioLogin(Date fechaInicioLogin) {
        this.fechaInicioLogin = fechaInicioLogin;
    }

    public Date getFechaFinLogin() {
        return fechaFinLogin;
    }

    public void setFechaFinLogin(Date fechaFinLogin) {
        this.fechaFinLogin = fechaFinLogin;
    }

    public Short getRangoFechaLogin() {
        return rangoFechaLogin;
    }

    public void setRangoFechaLogin(Short rangoFechaLogin) {
        this.rangoFechaLogin = rangoFechaLogin;
    }

    public Short getActivo() {
        return activo;
    }

    public void setActivo(Short activo) {
        this.activo = activo;
    }

    public Short getCambiarClave() {
        return cambiarClave;
    }

    public void setCambiarClave(Short cambiarClave) {
        this.cambiarClave = cambiarClave;
    }
}
