/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "OPCION_MENU")
@NamedQueries({
    @NamedQuery(name = "OpcionMenu.findAll", query = "SELECT o FROM OpcionMenu o")})
public class OpcionMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OPC_MENU")
    private BigDecimal idOpcMenu;
    @Column(name = "NOMBRE_OPCION")
    private String nombreOpcion;
    @Column(name = "NOMBRE_PANEL")
    private String nombrePanel;
    @Column(name = "ICONO")
    private String icono;
    @Column(name = "ORDEN")
    private BigInteger orden;
    @Column(name = "COD_PANTALLA")
    private String codPantalla;
    @JoinTable(name = "USUARIO_OPC_MENU", joinColumns = {
        @JoinColumn(name = "ID_OPC_MENU", referencedColumnName = "ID_OPC_MENU")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Usuario> usuarioList;
    @OneToMany(mappedBy = "opcIdOpcMenu", fetch = FetchType.LAZY)
    private List<OpcionMenu> opcionMenuList;
    @JoinColumn(name = "OPC_ID_OPC_MENU", referencedColumnName = "ID_OPC_MENU")
    @ManyToOne(fetch = FetchType.EAGER)
    private OpcionMenu opcIdOpcMenu;
    @JoinColumn(name = "APP", referencedColumnName = "ID_MODULO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Modulo app;

    public OpcionMenu() {
    }

    public OpcionMenu(BigDecimal idOpcMenu) {
        this.idOpcMenu = idOpcMenu;
    }

    public BigDecimal getIdOpcMenu() {
        return idOpcMenu;
    }

    public void setIdOpcMenu(BigDecimal idOpcMenu) {
        this.idOpcMenu = idOpcMenu;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public String getNombrePanel() {
        return nombrePanel;
    }

    public void setNombrePanel(String nombrePanel) {
        this.nombrePanel = nombrePanel;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public String getCodPantalla() {
        return codPantalla;
    }

    public void setCodPantalla(String codPantalla) {
        this.codPantalla = codPantalla;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public List<OpcionMenu> getOpcionMenuList() {
        return opcionMenuList;
    }

    public void setOpcionMenuList(List<OpcionMenu> opcionMenuList) {
        this.opcionMenuList = opcionMenuList;
    }

    public OpcionMenu getOpcIdOpcMenu() {
        return opcIdOpcMenu;
    }

    public void setOpcIdOpcMenu(OpcionMenu opcIdOpcMenu) {
        this.opcIdOpcMenu = opcIdOpcMenu;
    }

    public Modulo getApp() {
        return app;
    }

    public void setApp(Modulo app) {
        this.app = app;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpcMenu != null ? idOpcMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OpcionMenu)) {
            return false;
        }
        OpcionMenu other = (OpcionMenu) object;
        if ((this.idOpcMenu == null && other.idOpcMenu != null) || (this.idOpcMenu != null && !this.idOpcMenu.equals(other.idOpcMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.OpcionMenu[ idOpcMenu=" + idOpcMenu + " ]";
    }
    
}
