/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "MODULO")
@NamedQueries({
    @NamedQuery(name = "Modulo.findAll", query = "SELECT m FROM Modulo m")})
public class Modulo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MODULO")
    private BigDecimal idModulo;
    @Column(name = "NOMBRE_MODULO")
    private String nombreModulo;
    @OneToMany(mappedBy = "idModulo", fetch = FetchType.LAZY)
    private List<UsuarioModulo> usuarioModuloList;
    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
    private List<OpcionMenu> opcionMenuList;

    public Modulo() {
    }

    public Modulo(BigDecimal idModulo) {
        this.idModulo = idModulo;
    }

    public BigDecimal getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(BigDecimal idModulo) {
        this.idModulo = idModulo;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public List<UsuarioModulo> getUsuarioModuloList() {
        return usuarioModuloList;
    }

    public void setUsuarioModuloList(List<UsuarioModulo> usuarioModuloList) {
        this.usuarioModuloList = usuarioModuloList;
    }

    public List<OpcionMenu> getOpcionMenuList() {
        return opcionMenuList;
    }

    public void setOpcionMenuList(List<OpcionMenu> opcionMenuList) {
        this.opcionMenuList = opcionMenuList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModulo != null ? idModulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modulo)) {
            return false;
        }
        Modulo other = (Modulo) object;
        if ((this.idModulo == null && other.idModulo != null) || (this.idModulo != null && !this.idModulo.equals(other.idModulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.Modulo[ idModulo=" + idModulo + " ]";
    }
    
}
