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

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "GRUPO_APLICACION", schema = "SEGURIDAD_V2")
@NamedQueries({
    @NamedQuery(name = "GrupoAplicacion.findAll", query = "SELECT g FROM GrupoAplicacion g")})
public class GrupoAplicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_GRUPO_APP")
    private BigDecimal idGrupoApp;
    @OneToMany(mappedBy = "idGrupoApp", fetch = FetchType.LAZY)
    private List<UsuarioGrupoAplicacion> usuarioGrupoAplicacionList;
    @JoinColumn(name = "ID_APLICACION", referencedColumnName = "ID_APLICACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private Aplicacion idAplicacion;
    @JoinColumn(name = "ID_GRUPO", referencedColumnName = "ID_GRUPO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Grupo idGrupo;
    @OneToMany(mappedBy = "idGrupoApp", fetch = FetchType.LAZY)
    private List<AplicacionOpcMenu> aplicacionOpcMenuList;

    public GrupoAplicacion() {
    }

    public GrupoAplicacion(BigDecimal idGrupoApp) {
        this.idGrupoApp = idGrupoApp;
    }

    public BigDecimal getIdGrupoApp() {
        return idGrupoApp;
    }

    public void setIdGrupoApp(BigDecimal idGrupoApp) {
        this.idGrupoApp = idGrupoApp;
    }

    public List<UsuarioGrupoAplicacion> getUsuarioGrupoAplicacionList() {
        return usuarioGrupoAplicacionList;
    }

    public void setUsuarioGrupoAplicacionList(List<UsuarioGrupoAplicacion> usuarioGrupoAplicacionList) {
        this.usuarioGrupoAplicacionList = usuarioGrupoAplicacionList;
    }

    public Aplicacion getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(Aplicacion idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public Grupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Grupo idGrupo) {
        this.idGrupo = idGrupo;
    }

    public List<AplicacionOpcMenu> getAplicacionOpcMenuList() {
        return aplicacionOpcMenuList;
    }

    public void setAplicacionOpcMenuList(List<AplicacionOpcMenu> aplicacionOpcMenuList) {
        this.aplicacionOpcMenuList = aplicacionOpcMenuList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupoApp != null ? idGrupoApp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoAplicacion)) {
            return false;
        }
        GrupoAplicacion other = (GrupoAplicacion) object;
        if ((this.idGrupoApp == null && other.idGrupoApp != null) || (this.idGrupoApp != null && !this.idGrupoApp.equals(other.idGrupoApp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.model.GrupoAplicacion[ idGrupoApp=" + idGrupoApp + " ]";
    }
    
}
