/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "OPCION_MENU", schema = "SEGURIDAD_V2")
@NamedQueries({
    @NamedQuery(name = "OpcionMenu.findAll", query = "SELECT o FROM OpcionMenu o")})
public class OpcionMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OPC_MENU")
    private BigDecimal idOpcMenu;
    @Column(name = "CODIGO_OPC_MENU")
    private String codigoOpcMenu;
    @Column(name = "ESTADO_ELIMINACION")
    private BigInteger estadoEliminacion;
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
    @OneToMany(mappedBy = "idOpcMenuPadre", fetch = FetchType.LAZY)
    private List<OpcionMenu> opcionMenuList;
    @JoinColumn(name = "ID_OPC_MENU_PADRE", referencedColumnName = "ID_OPC_MENU")
    @ManyToOne(fetch = FetchType.LAZY)
    private OpcionMenu idOpcMenuPadre;
    @OneToMany(mappedBy = "idOpcMenu", fetch = FetchType.LAZY)
    private List<AplicacionOpcMenu> aplicacionOpcMenuList;

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

    public String getCodigoOpcMenu() {
        return codigoOpcMenu;
    }

    public void setCodigoOpcMenu(String codigoOpcMenu) {
        this.codigoOpcMenu = codigoOpcMenu;
    }

    public BigInteger getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(BigInteger estadoEliminacion) {
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

    public OpcionMenu getIdOpcMenuPadre() {
        return idOpcMenuPadre;
    }

    public void setIdOpcMenuPadre(OpcionMenu idOpcMenuPadre) {
        this.idOpcMenuPadre = idOpcMenuPadre;
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
