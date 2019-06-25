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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "MOD_OPC_MENU")
@NamedQueries({
    @NamedQuery(name = "ModOpcMenu.findAll", query = "SELECT m FROM ModOpcMenu m")})
public class ModOpcMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MOD_OPC_MENU")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MODOPCMENU")
    @SequenceGenerator(name = "SEQ_MODOPCMENU", sequenceName = "SEQ_MODOPCMENU", allocationSize = 1, initialValue = 1)
    private Long idModOpcMenu;
    @Column(name = "ID_MODULO")
    private Long idModulo;
    @Column(name = "ID_OPC_MENU")
    private Long idOpcMenu;

    public ModOpcMenu() {
    }

    public ModOpcMenu(Long idModOpcMenu) {
        this.idModOpcMenu = idModOpcMenu;
    }

    public Long getIdModOpcMenu() {
        return idModOpcMenu;
    }

    public void setIdModOpcMenu(Long idModOpcMenu) {
        this.idModOpcMenu = idModOpcMenu;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }

    public Long getIdOpcMenu() {
        return idOpcMenu;
    }

    public void setIdOpcMenu(Long idOpcMenu) {
        this.idOpcMenu = idOpcMenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModOpcMenu != null ? idModOpcMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModOpcMenu)) {
            return false;
        }
        ModOpcMenu other = (ModOpcMenu) object;
        if ((this.idModOpcMenu == null && other.idModOpcMenu != null) || (this.idModOpcMenu != null && !this.idModOpcMenu.equals(other.idModOpcMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.model.ModOpcMenu[ idModOpcMenu=" + idModOpcMenu + " ]";
    }
    
}
