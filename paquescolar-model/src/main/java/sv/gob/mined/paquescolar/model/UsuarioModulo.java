/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "USUARIO_MODULO")
@NamedQueries({
    @NamedQuery(name = "UsuarioModulo.findAll", query = "SELECT u FROM UsuarioModulo u")})
public class UsuarioModulo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USU_MOD")
    private BigDecimal idUsuMod;
    @Column(name = "ACTIVO")
    private BigInteger activo;
    @Column(name = "TIPO_ACCESO")
    private Character tipoAcceso;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario idUsuario;
    @JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Modulo idModulo;

    public UsuarioModulo() {
    }

    public UsuarioModulo(BigDecimal idUsuMod) {
        this.idUsuMod = idUsuMod;
    }

    public BigDecimal getIdUsuMod() {
        return idUsuMod;
    }

    public void setIdUsuMod(BigDecimal idUsuMod) {
        this.idUsuMod = idUsuMod;
    }

    public BigInteger getActivo() {
        return activo;
    }

    public void setActivo(BigInteger activo) {
        this.activo = activo;
    }

    public Character getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(Character tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Modulo getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Modulo idModulo) {
        this.idModulo = idModulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuMod != null ? idUsuMod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioModulo)) {
            return false;
        }
        UsuarioModulo other = (UsuarioModulo) object;
        if ((this.idUsuMod == null && other.idUsuMod != null) || (this.idUsuMod != null && !this.idUsuMod.equals(other.idUsuMod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.UsuarioModulo[ idUsuMod=" + idUsuMod + " ]";
    }
    
}
