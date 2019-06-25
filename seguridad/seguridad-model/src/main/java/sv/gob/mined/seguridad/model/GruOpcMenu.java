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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "GRU_OPC_MENU")
@NamedQueries({
    @NamedQuery(name = "GruOpcMenu.findAll", query = "SELECT a FROM GruOpcMenu a")})
public class GruOpcMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_GRU_OPC_MENU")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GRUOPCMEN")
    @SequenceGenerator(name = "SEQ_GRUOPCMEN", sequenceName = "SEQ_GRUOPCMEN", allocationSize = 1, initialValue = 1)
    private Long idGruOpcMenu;
    @JoinColumn(name = "ID_GRU_APP", referencedColumnName = "ID_GRU_APP")
    @ManyToOne(fetch = FetchType.LAZY)
    private GruApp idGruApp;
    @JoinColumn(name = "ID_OPC_MENU", referencedColumnName = "ID_OPC_MENU")
    @ManyToOne(fetch = FetchType.LAZY)
    private OpcionMenu idOpcMenu;

    public GruOpcMenu() {
    }

    public GruOpcMenu(Long idAppOpcMenu) {
        this.idGruOpcMenu = idAppOpcMenu;
    }

    public Long getIdGruOpcMenu() {
        return idGruOpcMenu;
    }

    public void setIdGruOpcMenu(Long idAppOpcMenu) {
        this.idGruOpcMenu = idAppOpcMenu;
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
        hash += (idGruOpcMenu != null ? idGruOpcMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GruOpcMenu)) {
            return false;
        }
        GruOpcMenu other = (GruOpcMenu) object;
        return !((this.idGruOpcMenu == null && other.idGruOpcMenu != null) || (this.idGruOpcMenu != null && !this.idGruOpcMenu.equals(other.idGruOpcMenu)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.model.GruOpcMenu[ idGruOpcMenu=" + idGruOpcMenu + " ]";
    }
    
}
