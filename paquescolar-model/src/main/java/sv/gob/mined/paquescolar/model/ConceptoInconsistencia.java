/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "CONCEPTO_INCONSISTENCIA")
@NamedQueries({
    @NamedQuery(name = "ConceptoInconsistencia.findAll", query = "SELECT c FROM ConceptoInconsistencia c")})
public class ConceptoInconsistencia implements Serializable {

    @JoinColumn(name = "ID_ANHO", referencedColumnName = "ID_ANHO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Anho idAnho;
    @OneToMany(mappedBy = "idConcepto", fetch = FetchType.LAZY)
    private List<DetalleLiquidacionInc> detalleLiquidacionIncList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CONCEPTO")
    private Integer idConcepto;
    @Size(max = 250)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public ConceptoInconsistencia() {
    }

    public ConceptoInconsistencia(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConcepto != null ? idConcepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptoInconsistencia)) {
            return false;
        }
        ConceptoInconsistencia other = (ConceptoInconsistencia) object;
        if ((this.idConcepto == null && other.idConcepto != null) || (this.idConcepto != null && !this.idConcepto.equals(other.idConcepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.ConceptoInconsistencia[ idConcepto=" + idConcepto + " ]";
    }

    public Anho getIdAnho() {
        return idAnho;
    }

    public void setIdAnho(Anho idAnho) {
        this.idAnho = idAnho;
    }

    public List<DetalleLiquidacionInc> getDetalleLiquidacionIncList() {
        return detalleLiquidacionIncList;
    }

    public void setDetalleLiquidacionIncList(List<DetalleLiquidacionInc> detalleLiquidacionIncList) {
        this.detalleLiquidacionIncList = detalleLiquidacionIncList;
    }
    
}
