/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "PARAMETROS_GENERALES", schema = "SEGURIDAD_V2")
@NamedQueries({
    @NamedQuery(name = "ParametrosGenerales.findAll", query = "SELECT p FROM ParametrosGenerales p")})
public class ParametrosGenerales implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PARAMETRO")
    private BigDecimal idParametro;
    @Column(name = "ACTIVO_PARAMETRO")
    private Character activoParametro;
    @Column(name = "NUM_INTENTOS_CLAVE")
    private Integer numIntentosClave;
    @Column(name = "PERIODICIDAD_CAMBIO_CLAVE")
    private Integer periodicidadCambioClave;

    public ParametrosGenerales() {
    }

    public ParametrosGenerales(BigDecimal idParametro) {
        this.idParametro = idParametro;
    }

    public BigDecimal getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(BigDecimal idParametro) {
        this.idParametro = idParametro;
    }

    public Character getActivoParametro() {
        return activoParametro;
    }

    public void setActivoParametro(Character activoParametro) {
        this.activoParametro = activoParametro;
    }

    public Integer getNumIntentosClave() {
        return numIntentosClave;
    }

    public void setNumIntentosClave(Integer numIntentosClave) {
        this.numIntentosClave = numIntentosClave;
    }

    public Integer getPeriodicidadCambioClave() {
        return periodicidadCambioClave;
    }

    public void setPeriodicidadCambioClave(Integer periodicidadCambioClave) {
        this.periodicidadCambioClave = periodicidadCambioClave;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametrosGenerales)) {
            return false;
        }
        ParametrosGenerales other = (ParametrosGenerales) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.model.ParametrosGenerales[ idParametro=" + idParametro + " ]";
    }
    
}
