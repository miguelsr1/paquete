/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "OPCION_MENU")
@NamedQueries({
    @NamedQuery(name = "OpcionMenu.findAll", query = "SELECT o FROM OpcionMenu o")})
public class OpcionMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OPC_MENU")
    private Long idOpcMenu;
    @Column(name = "CODIGO_OPC_MENU")
    private String codigoOpcMenu;
    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "ICONO")
    private String icono;
    @Column(name = "NOMBRE_OPCION")
    private String nombreOpcion;
    @Column(name = "ORDEN")
    private BigInteger orden;
    @Column(name = "TIPO")
    private Character tipo;
    @Column(name = "URL")
    private String url;
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;
    @Column(name = "ROUTER_LINK")
    private String routerLink;
    @OneToMany(mappedBy = "padreIdOpcMenu", fetch = FetchType.LAZY)
    private List<OpcionMenu> opcionMenuList;
    @JoinColumn(name = "PADRE_ID_OPC_MENU", referencedColumnName = "ID_OPC_MENU")
    @ManyToOne(fetch = FetchType.LAZY)
    private OpcionMenu padreIdOpcMenu;
    @OneToMany(mappedBy = "idOpcMenu", fetch = FetchType.LAZY)
    private List<AppOpcMenu> appOpcMenuList;

    public OpcionMenu() {
    }

    public OpcionMenu(Long idOpcMenu) {
        this.idOpcMenu = idOpcMenu;
    }

    public Long getIdOpcMenu() {
        return idOpcMenu;
    }

    public void setIdOpcMenu(Long idOpcMenu) {
        this.idOpcMenu = idOpcMenu;
    }

    public String getCodigoOpcMenu() {
        return codigoOpcMenu;
    }

    public void setCodigoOpcMenu(String codigoOpcMenu) {
        this.codigoOpcMenu = codigoOpcMenu;
    }

    public Short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(Short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getRouterLink() {
        return routerLink;
    }

    public void setRouterLink(String routerLink) {
        this.routerLink = routerLink;
    }

    public List<OpcionMenu> getOpcionMenuList() {
        return opcionMenuList;
    }

    public void setOpcionMenuList(List<OpcionMenu> opcionMenuList) {
        this.opcionMenuList = opcionMenuList;
    }

    public OpcionMenu getPadreIdOpcMenu() {
        return padreIdOpcMenu;
    }

    public void setPadreIdOpcMenu(OpcionMenu padreIdOpcMenu) {
        this.padreIdOpcMenu = padreIdOpcMenu;
    }

    public List<AppOpcMenu> getAppOpcMenuList() {
        return appOpcMenuList;
    }

    public void setAppOpcMenuList(List<AppOpcMenu> appOpcMenuList) {
        this.appOpcMenuList = appOpcMenuList;
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
        return "sv.gob.mined.seguridad.model.OpcionMenu[ idOpcMenu=" + idOpcMenu + " ]";
    }
    
}
