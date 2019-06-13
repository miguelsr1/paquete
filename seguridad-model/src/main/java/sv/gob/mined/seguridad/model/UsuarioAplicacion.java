/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "USUARIO_APLICACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioAplicacion.findAll", query = "SELECT u FROM UsuarioAplicacion u")})
public class UsuarioAplicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USU_APP")
    private BigDecimal idUsuApp;
    @Column(name = "CODIGO_DEPARTAMENTO")
    private String codigoDepartamento;
    @Column(name = "USU_GRUPO_ACTIVO")
    private Character usuGrupoActivo;
    @OneToMany(mappedBy = "idUsuGrupo", fetch = FetchType.LAZY)
    private List<BitacoraAcceso> bitacoraAccesoList;
    @JoinColumn(name = "ID_APLICACION", referencedColumnName = "ID_APLICACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private Aplicacion idAplicacion;
    @JoinColumn(name = "LOGIN", referencedColumnName = "LOGIN")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario login;
    @OneToMany(mappedBy = "idUsuApp", fetch = FetchType.LAZY)
    private List<UsuarioGrupoAplicacion> usuarioGrupoAplicacionList;

    public UsuarioAplicacion() {
    }

    public UsuarioAplicacion(BigDecimal idUsuApp) {
        this.idUsuApp = idUsuApp;
    }

    public BigDecimal getIdUsuApp() {
        return idUsuApp;
    }

    public void setIdUsuApp(BigDecimal idUsuApp) {
        this.idUsuApp = idUsuApp;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public Character getUsuGrupoActivo() {
        return usuGrupoActivo;
    }

    public void setUsuGrupoActivo(Character usuGrupoActivo) {
        this.usuGrupoActivo = usuGrupoActivo;
    }

    @XmlTransient
    public List<BitacoraAcceso> getBitacoraAccesoList() {
        return bitacoraAccesoList;
    }

    public void setBitacoraAccesoList(List<BitacoraAcceso> bitacoraAccesoList) {
        this.bitacoraAccesoList = bitacoraAccesoList;
    }

    public Aplicacion getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(Aplicacion idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public Usuario getLogin() {
        return login;
    }

    public void setLogin(Usuario login) {
        this.login = login;
    }

    @XmlTransient
    public List<UsuarioGrupoAplicacion> getUsuarioGrupoAplicacionList() {
        return usuarioGrupoAplicacionList;
    }

    public void setUsuarioGrupoAplicacionList(List<UsuarioGrupoAplicacion> usuarioGrupoAplicacionList) {
        this.usuarioGrupoAplicacionList = usuarioGrupoAplicacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuApp != null ? idUsuApp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioAplicacion)) {
            return false;
        }
        UsuarioAplicacion other = (UsuarioAplicacion) object;
        if ((this.idUsuApp == null && other.idUsuApp != null) || (this.idUsuApp != null && !this.idUsuApp.equals(other.idUsuApp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.seguridad.api.model.UsuarioAplicacion[ idUsuApp=" + idUsuApp + " ]";
    }
    
}
