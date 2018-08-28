/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "USUARIO_ENTIDAD_FINANCIERA")
@NamedQueries({
    @NamedQuery(name = "UsuarioEntidadFinanciera.findAll", query = "SELECT u FROM UsuarioEntidadFinanciera u")})
public class UsuarioEntidadFinanciera implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USU_ENT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usu_ent")
    @SequenceGenerator(name = "usu_ent", sequenceName = "SEQ_USU_ENT", allocationSize = 1, initialValue = 1)
    private BigDecimal idUsuEnt;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario idUsuario;
    @JoinColumn(name = "COD_ENT_FINANCIERA", referencedColumnName = "COD_ENT_FINANCIERA")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private EntidadFinanciera codEntFinanciera;

    public UsuarioEntidadFinanciera() {
    }

    public UsuarioEntidadFinanciera(BigDecimal idUsuEnt) {
        this.idUsuEnt = idUsuEnt;
    }

    public BigDecimal getIdUsuEnt() {
        return idUsuEnt;
    }

    public void setIdUsuEnt(BigDecimal idUsuEnt) {
        this.idUsuEnt = idUsuEnt;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public EntidadFinanciera getCodEntFinanciera() {
        return codEntFinanciera;
    }

    public void setCodEntFinanciera(EntidadFinanciera codEntFinanciera) {
        this.codEntFinanciera = codEntFinanciera;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuEnt != null ? idUsuEnt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioEntidadFinanciera)) {
            return false;
        }
        UsuarioEntidadFinanciera other = (UsuarioEntidadFinanciera) object;
        if ((this.idUsuEnt == null && other.idUsuEnt != null) || (this.idUsuEnt != null && !this.idUsuEnt.equals(other.idUsuEnt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.UsuarioEntidadFinanciera[ idUsuEnt=" + idUsuEnt + " ]";
    }
    
}
