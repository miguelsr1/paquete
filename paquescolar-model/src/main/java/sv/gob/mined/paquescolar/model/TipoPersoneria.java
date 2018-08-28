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
@Table(name = "TIPO_PERSONERIA")
@NamedQueries({
    @NamedQuery(name = "TipoPersoneria.findAll", query = "SELECT t FROM TipoPersoneria t")})
public class TipoPersoneria implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PERSONERIA")
    private BigDecimal idPersoneria;
    @Column(name = "DESCRIPCION_PERSONERIA")
    private String descripcionPersoneria;
    @OneToMany(mappedBy = "idPersoneria", fetch = FetchType.LAZY)
    private List<Empresa> empresaList;

    public TipoPersoneria() {
    }

    public TipoPersoneria(BigDecimal idPersoneria) {
        this.idPersoneria = idPersoneria;
    }

    public BigDecimal getIdPersoneria() {
        return idPersoneria;
    }

    public void setIdPersoneria(BigDecimal idPersoneria) {
        this.idPersoneria = idPersoneria;
    }

    public String getDescripcionPersoneria() {
        return descripcionPersoneria;
    }

    public void setDescripcionPersoneria(String descripcionPersoneria) {
        this.descripcionPersoneria = descripcionPersoneria;
    }

    public List<Empresa> getEmpresaList() {
        return empresaList;
    }

    public void setEmpresaList(List<Empresa> empresaList) {
        this.empresaList = empresaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersoneria != null ? idPersoneria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPersoneria)) {
            return false;
        }
        TipoPersoneria other = (TipoPersoneria) object;
        if ((this.idPersoneria == null && other.idPersoneria != null) || (this.idPersoneria != null && !this.idPersoneria.equals(other.idPersoneria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.TipoPersoneria[ idPersoneria=" + idPersoneria + " ]";
    }
    
}
