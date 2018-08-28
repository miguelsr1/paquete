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
@Table(name = "TIPOS_DOC_LEGAL")
@NamedQueries({
    @NamedQuery(name = "TiposDocLegal.findAll", query = "SELECT t FROM TiposDocLegal t")})
public class TiposDocLegal implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DOC_LEGAL")
    private BigDecimal idDocLegal;
    @Column(name = "DESCRIPCION_DOC_LEGAL")
    private String descripcionDocLegal;
    @OneToMany(mappedBy = "idDocLegal", fetch = FetchType.LAZY)
    private List<Persona> personaList;

    public TiposDocLegal() {
    }

    public TiposDocLegal(BigDecimal idDocLegal) {
        this.idDocLegal = idDocLegal;
    }

    public BigDecimal getIdDocLegal() {
        return idDocLegal;
    }

    public void setIdDocLegal(BigDecimal idDocLegal) {
        this.idDocLegal = idDocLegal;
    }

    public String getDescripcionDocLegal() {
        return descripcionDocLegal;
    }

    public void setDescripcionDocLegal(String descripcionDocLegal) {
        this.descripcionDocLegal = descripcionDocLegal;
    }

    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocLegal != null ? idDocLegal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposDocLegal)) {
            return false;
        }
        TiposDocLegal other = (TiposDocLegal) object;
        if ((this.idDocLegal == null && other.idDocLegal != null) || (this.idDocLegal != null && !this.idDocLegal.equals(other.idDocLegal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.TiposDocLegal[ idDocLegal=" + idDocLegal + " ]";
    }
    
}
