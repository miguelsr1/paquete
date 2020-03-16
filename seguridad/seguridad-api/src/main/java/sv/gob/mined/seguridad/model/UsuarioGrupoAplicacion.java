/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "USUARIO_GRUPO_APLICACION", schema = "SEGURIDAD_V2")
@NamedQueries({
    @NamedQuery(name = "UsuarioGrupoAplicacion.findAll", query = "SELECT u FROM UsuarioGrupoAplicacion u")})
public class UsuarioGrupoAplicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USU_GRUPO_APP")
    private BigDecimal idUsuGrupoApp;
    @Column(name = "ACTIVO_GRUPO_APP")
    private Character activoGrupoApp;
    @Column(name = "USU_GRUPO_APP")
    private Character usuGrupoApp;
    @JoinColumn(name = "ID_GRUPO_APP", referencedColumnName = "ID_GRUPO_APP")
    @ManyToOne(fetch = FetchType.LAZY)
    private GrupoAplicacion idGrupoApp;
    @JoinColumn(name = "ID_USU_APP", referencedColumnName = "ID_USU_APP")
    @ManyToOne(fetch = FetchType.LAZY)
    private UsuarioAplicacion idUsuApp;

    public UsuarioGrupoAplicacion() {
    }

    public UsuarioGrupoAplicacion(BigDecimal idUsuGrupoApp) {
        this.idUsuGrupoApp = idUsuGrupoApp;
    }

    public BigDecimal getIdUsuGrupoApp() {
        return idUsuGrupoApp;
    }

    public void setIdUsuGrupoApp(BigDecimal idUsuGrupoApp) {
        this.idUsuGrupoApp = idUsuGrupoApp;
    }

    public Character getActivoGrupoApp() {
        return activoGrupoApp;
    }

    public void setActivoGrupoApp(Character activoGrupoApp) {
        this.activoGrupoApp = activoGrupoApp;
    }

    public Character getUsuGrupoApp() {
        return usuGrupoApp;
    }

    public void setUsuGrupoApp(Character usuGrupoApp) {
        this.usuGrupoApp = usuGrupoApp;
    }

    public GrupoAplicacion getIdGrupoApp() {
        return idGrupoApp;
    }

    public void setIdGrupoApp(GrupoAplicacion idGrupoApp) {
        this.idGrupoApp = idGrupoApp;
    }

    public UsuarioAplicacion getIdUsuApp() {
        return idUsuApp;
    }

    public void setIdUsuApp(UsuarioAplicacion idUsuApp) {
        this.idUsuApp = idUsuApp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuGrupoApp != null ? idUsuGrupoApp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioGrupoAplicacion)) {
            return false;
        }
        UsuarioGrupoAplicacion other = (UsuarioGrupoAplicacion) object;
        if ((this.idUsuGrupoApp == null && other.idUsuGrupoApp != null) || (this.idUsuGrupoApp != null && !this.idUsuGrupoApp.equals(other.idUsuGrupoApp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.model.UsuarioGrupoAplicacion[ idUsuGrupoApp=" + idUsuGrupoApp + " ]";
    }
    
}
