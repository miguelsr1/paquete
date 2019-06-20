/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "USU_GRU_APP")
@NamedQueries({
    @NamedQuery(name = "UsuGruApp.findAll", query = "SELECT u FROM UsuGruApp u")})
public class UsuGruApp implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USU_GRU_APP") 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUGRUAPP")
    @SequenceGenerator(name = "SEQ_USUGRUAPP", sequenceName = "SEQ_USUGRUAPP", allocationSize = 1, initialValue = 1)
    private BigDecimal idUsuGruApp;
    @Column(name = "ACTIVO")
    private Character activo;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "ID_GRU_APP", referencedColumnName = "ID_GRU_APP")
    @ManyToOne(fetch = FetchType.LAZY)
    private GruApp idGruApp;
    @JoinColumn(name = "LOGIN", referencedColumnName = "LOGIN")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario login;

    public UsuGruApp() {
    }

    public UsuGruApp(BigDecimal idUsuGruApp) {
        this.idUsuGruApp = idUsuGruApp;
    }

    public BigDecimal getIdUsuGruApp() {
        return idUsuGruApp;
    }

    public void setIdUsuGruApp(BigDecimal idUsuGruApp) {
        this.idUsuGruApp = idUsuGruApp;
    }

    public Character getActivo() {
        return activo;
    }

    public void setActivo(Character activo) {
        this.activo = activo;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public GruApp getIdGruApp() {
        return idGruApp;
    }

    public void setIdGruApp(GruApp idGruApp) {
        this.idGruApp = idGruApp;
    }

    public Usuario getLogin() {
        return login;
    }

    public void setLogin(Usuario login) {
        this.login = login;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuGruApp != null ? idUsuGruApp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuGruApp)) {
            return false;
        }
        UsuGruApp other = (UsuGruApp) object;
        if ((this.idUsuGruApp == null && other.idUsuGruApp != null) || (this.idUsuGruApp != null && !this.idUsuGruApp.equals(other.idUsuGruApp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.model.UsuGruApp[ idUsuGruApp=" + idUsuGruApp + " ]";
    }
    
}
