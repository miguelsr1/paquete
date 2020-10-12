/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.web.model;

import java.io.Serializable;
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
@Table(name = "MODALIDAD_EJECUCION")
@NamedQueries({
    @NamedQuery(name = "ModalidadEjecucion.findAll", query = "SELECT m FROM ModalidadEjecucion m")})
public class ModalidadEjecucion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MOD_EJECUCION")
    private Long idModEjecucion;
    @Column(name = "DESCRIPCION_MODALIDAD")
    private String descripcionModalidad;

    public ModalidadEjecucion() {
    }

    public ModalidadEjecucion(Long idModEjecucion) {
        this.idModEjecucion = idModEjecucion;
    }

    public Long getIdModEjecucion() {
        return idModEjecucion;
    }

    public void setIdModEjecucion(Long idModEjecucion) {
        this.idModEjecucion = idModEjecucion;
    }

    public String getDescripcionModalidad() {
        return descripcionModalidad;
    }

    public void setDescripcionModalidad(String descripcionModalidad) {
        this.descripcionModalidad = descripcionModalidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModEjecucion != null ? idModEjecucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModalidadEjecucion)) {
            return false;
        }
        ModalidadEjecucion other = (ModalidadEjecucion) object;
        if ((this.idModEjecucion == null && other.idModEjecucion != null) || (this.idModEjecucion != null && !this.idModEjecucion.equals(other.idModEjecucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.cooperacion.web.model.ModalidadEjecucion[ idModEjecucion=" + idModEjecucion + " ]";
    }
    
}
