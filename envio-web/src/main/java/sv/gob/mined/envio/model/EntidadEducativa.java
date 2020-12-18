/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.envio.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author MISanchez
 */
@Entity
@Table(name = "ENTIDAD_EDUCATIVA")
@NamedQueries({
    @NamedQuery(name = "EntidadEducativa.findAll", query = "SELECT e FROM EntidadEducativa e")})
public class EntidadEducativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @Column(name = "CODIGO_DEPARTAMENTO")
    private String codigoDepartamento;

    public EntidadEducativa() {
    }

    public EntidadEducativa(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoEntidad != null ? codigoEntidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadEducativa)) {
            return false;
        }
        EntidadEducativa other = (EntidadEducativa) object;
        if ((this.codigoEntidad == null && other.codigoEntidad != null) || (this.codigoEntidad != null && !this.codigoEntidad.equals(other.codigoEntidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.envio.model.EntidadEducativa[ codigoEntidad=" + codigoEntidad + " ]";
    }
    
}
