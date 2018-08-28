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
@Table(name = "TIPO_MODIF_CONTRATO")
@NamedQueries({
    @NamedQuery(name = "TipoModifContrato.findAll", query = "SELECT t FROM TipoModifContrato t")})
public class TipoModifContrato implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MODIF_CONTRATO")
    private BigDecimal idModifContrato;
    @Column(name = "DESCRIPCION_MODIFICATIVA")
    private String descripcionModificativa;
    @Column(name = "EXTINSION")
    private Short extinsion;
    @OneToMany(mappedBy = "idModifContrato")
    private List<ResolucionesModificativas> resolucionesModificativasList;

    public TipoModifContrato() {
    }

    public TipoModifContrato(BigDecimal idModifContrato) {
        this.idModifContrato = idModifContrato;
    }

    public BigDecimal getIdModifContrato() {
        return idModifContrato;
    }

    public void setIdModifContrato(BigDecimal idModifContrato) {
        this.idModifContrato = idModifContrato;
    }

    public String getDescripcionModificativa() {
        return descripcionModificativa;
    }

    public void setDescripcionModificativa(String descripcionModificativa) {
        this.descripcionModificativa = descripcionModificativa;
    }

    public Short getExtinsion() {
        return extinsion;
    }

    public void setExtinsion(Short extinsion) {
        this.extinsion = extinsion;
    }

    public List<ResolucionesModificativas> getResolucionesModificativasList() {
        return resolucionesModificativasList;
    }

    public void setResolucionesModificativasList(List<ResolucionesModificativas> resolucionesModificativasList) {
        this.resolucionesModificativasList = resolucionesModificativasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModifContrato != null ? idModifContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoModifContrato)) {
            return false;
        }
        TipoModifContrato other = (TipoModifContrato) object;
        if ((this.idModifContrato == null && other.idModifContrato != null) || (this.idModifContrato != null && !this.idModifContrato.equals(other.idModifContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idModifContrato + " - " + descripcionModificativa;
    }
    
}
