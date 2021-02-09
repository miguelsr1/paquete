/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DATO_INFRA_CE")
@NamedQueries({
    @NamedQuery(name = "DatoInfraCe.findAll", query = "SELECT d FROM DatoInfraCe d")})
public class DatoInfraCe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DATO_INFRA")
    private Long idDatoInfra;
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "MATRICULA")
    private String matricula;
    @Column(name = "PROPIEDAD_INMUEBLE")
    private Short propiedadInmueble;

    public DatoInfraCe() {
    }

    public DatoInfraCe(Long idDatoInfra) {
        this.idDatoInfra = idDatoInfra;
    }

    public Long getIdDatoInfra() {
        return idDatoInfra;
    }

    public void setIdDatoInfra(Long idDatoInfra) {
        this.idDatoInfra = idDatoInfra;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Short getPropiedadInmueble() {
        return propiedadInmueble;
    }

    public void setPropiedadInmueble(Short propiedadInmueble) {
        this.propiedadInmueble = propiedadInmueble;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDatoInfra != null ? idDatoInfra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatoInfraCe)) {
            return false;
        }
        DatoInfraCe other = (DatoInfraCe) object;
        if ((this.idDatoInfra == null && other.idDatoInfra != null) || (this.idDatoInfra != null && !this.idDatoInfra.equals(other.idDatoInfra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.cooperacion.model.DatoInfraCe[ idDatoInfra=" + idDatoInfra + " ]";
    }
    
}
