/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "GRU_APP")
@NamedQueries({
    @NamedQuery(name = "GruApp.findAll", query = "SELECT g FROM GruApp g")})
public class GruApp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_GRU_APP")
    private Long idGruApp;
    @Column(name = "ACTIVO")
    private Character activo;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @OneToMany(mappedBy = "idGruApp", fetch = FetchType.LAZY)
    private List<AppOpcMenu> appOpcMenuList;
    @JoinColumn(name = "ID_APLICACION", referencedColumnName = "ID_APLICACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private Aplicacion idAplicacion;
    @JoinColumn(name = "ID_GRUPO", referencedColumnName = "ID_GRUPO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Grupo idGrupo;
    @OneToMany(mappedBy = "idGruApp", fetch = FetchType.LAZY)
    private List<UsuGruApp> usuGruAppList;

    public GruApp() {
    }

    public GruApp(Long idGruApp) {
        this.idGruApp = idGruApp;
    }

    public Long getIdGruApp() {
        return idGruApp;
    }

    public void setIdGruApp(Long idGruApp) {
        this.idGruApp = idGruApp;
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

    public List<AppOpcMenu> getAppOpcMenuList() {
        return appOpcMenuList;
    }

    public void setAppOpcMenuList(List<AppOpcMenu> appOpcMenuList) {
        this.appOpcMenuList = appOpcMenuList;
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

    public List<UsuGruApp> getUsuGruAppList() {
        return usuGruAppList;
    }

    public void setUsuGruAppList(List<UsuGruApp> usuGruAppList) {
        this.usuGruAppList = usuGruAppList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGruApp != null ? idGruApp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GruApp)) {
            return false;
        }
        GruApp other = (GruApp) object;
        if ((this.idGruApp == null && other.idGruApp != null) || (this.idGruApp != null && !this.idGruApp.equals(other.idGruApp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idAplicacion.getNombreAplicacion() + " - " + idGrupo.getNombreGrupo();
    }
    
}
