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
@Table(name = "APLICACION_OPC_MENU", schema = "SEGURIDAD_V2")
@NamedQueries({
    @NamedQuery(name = "AplicacionOpcMenu.findAll", query = "SELECT a FROM AplicacionOpcMenu a")})
public class AplicacionOpcMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_APP_OPC_MENU")
    private BigDecimal idAppOpcMenu;
    @JoinColumn(name = "ID_GRUPO_APP", referencedColumnName = "ID_GRUPO_APP")
    @ManyToOne(fetch = FetchType.LAZY)
    private GrupoAplicacion idGrupoApp;
    @JoinColumn(name = "ID_OPC_MENU", referencedColumnName = "ID_OPC_MENU")
    @ManyToOne(fetch = FetchType.LAZY)
    private OpcionMenu idOpcMenu;

    public AplicacionOpcMenu() {
    }

    public AplicacionOpcMenu(BigDecimal idAppOpcMenu) {
        this.idAppOpcMenu = idAppOpcMenu;
    }

    public BigDecimal getIdAppOpcMenu() {
        return idAppOpcMenu;
    }

    public void setIdAppOpcMenu(BigDecimal idAppOpcMenu) {
        this.idAppOpcMenu = idAppOpcMenu;
    }

    public GrupoAplicacion getIdGrupoApp() {
        return idGrupoApp;
    }

    public void setIdGrupoApp(GrupoAplicacion idGrupoApp) {
        this.idGrupoApp = idGrupoApp;
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
        if (!(object instanceof AplicacionOpcMenu)) {
            return false;
        }
        AplicacionOpcMenu other = (AplicacionOpcMenu) object;
        if ((this.idAppOpcMenu == null && other.idAppOpcMenu != null) || (this.idAppOpcMenu != null && !this.idAppOpcMenu.equals(other.idAppOpcMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.model.AplicacionOpcMenu[ idAppOpcMenu=" + idAppOpcMenu + " ]";
    }
    
}
