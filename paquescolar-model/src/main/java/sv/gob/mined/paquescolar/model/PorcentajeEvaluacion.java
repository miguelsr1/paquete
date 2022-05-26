/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PORCENTAJE_EVALUACION")
@NamedQueries({
    @NamedQuery(name = "PorcentajeEvaluacion.findAll", query = "SELECT p FROM PorcentajeEvaluacion p")})
public class PorcentajeEvaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PORCENTAJE")
    private Long idPorcentaje;
    @Column(name = "ID_RUBRO_INTERES")
    private Long idRubroInteres;
    @Column(name = "ID_ANHO")
    private Long idAnho;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PORCENTAJE")
    private BigDecimal porcentaje;
    @Column(name = "ID_CRITERIO")
    private Long idCriterio;
    @Column(name = "PADRE_ID_PORCENTAJE")
    private Long padreIdPorcentaje;

    public PorcentajeEvaluacion() {
    }

    public PorcentajeEvaluacion(Long idPorcentaje) {
        this.idPorcentaje = idPorcentaje;
    }

    public PorcentajeEvaluacion(Long idPorcentaje, BigDecimal porcentaje) {
        this.idPorcentaje = idPorcentaje;
        this.porcentaje = porcentaje;
    }

    public Long getIdPorcentaje() {
        return idPorcentaje;
    }

    public void setIdPorcentaje(Long idPorcentaje) {
        this.idPorcentaje = idPorcentaje;
    }

    public Long getIdRubroInteres() {
        return idRubroInteres;
    }

    public void setIdRubroInteres(Long idRubroInteres) {
        this.idRubroInteres = idRubroInteres;
    }

    public Long getIdAnho() {
        return idAnho;
    }

    public void setIdAnho(Long idAnho) {
        this.idAnho = idAnho;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Long getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(Long idCriterio) {
        this.idCriterio = idCriterio;
    }

    public Long getPadreIdPorcentaje() {
        return padreIdPorcentaje;
    }

    public void setPadreIdPorcentaje(Long padreIdPorcentaje) {
        this.padreIdPorcentaje = padreIdPorcentaje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPorcentaje != null ? idPorcentaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PorcentajeEvaluacion)) {
            return false;
        }
        PorcentajeEvaluacion other = (PorcentajeEvaluacion) object;
        return !((this.idPorcentaje == null && other.idPorcentaje != null) || (this.idPorcentaje != null && !this.idPorcentaje.equals(other.idPorcentaje)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.PorcentajeEvaluacion[ idPorcentaje=" + idPorcentaje + " ]";
    }

}
