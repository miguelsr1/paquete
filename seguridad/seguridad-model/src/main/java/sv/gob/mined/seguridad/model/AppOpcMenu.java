/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model;

import java.io.Serializable;
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
@Table(name = "APP_OPC_MENU")
@NamedQueries({
    @NamedQuery(name = "AppOpcMenu.findAll", query = "SELECT a FROM AppOpcMenu a")})
public class AppOpcMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_APP_OPC_MENU")
    private Long idAppOpcMenu;
    @JoinColumn(name = "ID_GRU_APP", referencedColumnName = "ID_GRU_APP")
    @ManyToOne(fetch = FetchType.LAZY)
    private GruApp idGruApp;
    @JoinColumn(name = "ID_OPC_MENU", referencedColumnName = "ID_OPC_MENU")
    @ManyToOne(fetch = FetchType.LAZY)
    private OpcionMenu idOpcMenu;

    public AppOpcMenu() {
    }

    public AppOpcMenu(Long idAppOpcMenu) {
        this.idAppOpcMenu = idAppOpcMenu;
    }

    public Long getIdAppOpcMenu() {
        return idAppOpcMenu;
    }

    public void setIdAppOpcMenu(Long idAppOpcMenu) {
        this.idAppOpcMenu = idAppOpcMenu;
    }

    public GruApp getIdGruApp() {
        return idGruApp;
    }

    public void setIdGruApp(GruApp idGruApp) {
        this.idGruApp = idGruApp;
    }

    public OpcionMenu getIdOpcMenu() {
        return idOpcMenu;
    }

    public void setIdOpcMenu(OpcionMenu idOpcMenu) {
        this.idOpcMenu = idOpcMenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAppOpcMenu != null ? idAppOpcMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppOpcMenu)) {
            return false;
        }
        AppOpcMenu other = (AppOpcMenu) object;
        if ((this.idAppOpcMenu == null && other.idAppOpcMenu != null) || (this.idAppOpcMenu != null && !this.idAppOpcMenu.equals(other.idAppOpcMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.model.AppOpcMenu[ idAppOpcMenu=" + idAppOpcMenu + " ]";
    }
    
}
