/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.web.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DIRECTOR_CE")
@NamedQueries({
    @NamedQuery(name = "DirectorCe.findAll", query = "SELECT d FROM DirectorCe d")})
public class DirectorCe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DIRECTOR")
    private Long idDirector;
    @Basic(optional = false)
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @Basic(optional = false)
    @Column(name = "CORREO_DIRECTOR")
    private String correoDirector;
    @Column(name = "NIP_DIRECTOR")
    private String nipDirector;
    @Column(name = "NOMBRE_DIRECTOR")
    private String nombreDirector;
    @Column(name = "APELLIDOS_DIRECTOR")
    private String apellidosDirector;
    @Column(name = "ACTIVO")
    private Short activo;
    @Column(name = "FECHA_INACTIVO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInactivo;

    public DirectorCe() {
    }

    public DirectorCe(Long idDirector) {
        this.idDirector = idDirector;
    }

    public DirectorCe(Long idDirector, String codigoEntidad, String correoDirector) {
        this.idDirector = idDirector;
        this.codigoEntidad = codigoEntidad;
        this.correoDirector = correoDirector;
    }

    public Long getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(Long idDirector) {
        this.idDirector = idDirector;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getCorreoDirector() {
        return correoDirector;
    }

    public void setCorreoDirector(String correoDirector) {
        this.correoDirector = correoDirector;
    }

    public String getNipDirector() {
        return nipDirector;
    }

    public void setNipDirector(String nipDirector) {
        this.nipDirector = nipDirector;
    }

    public String getNombreDirector() {
        return nombreDirector;
    }

    public void setNombreDirector(String nombreDirector) {
        this.nombreDirector = nombreDirector;
    }

    public String getApellidosDirector() {
        return apellidosDirector;
    }

    public void setApellidosDirector(String apellidosDirector) {
        this.apellidosDirector = apellidosDirector;
    }

    public Short getActivo() {
        return activo;
    }

    public void setActivo(Short activo) {
        this.activo = activo;
    }

    public Date getFechaInactivo() {
        return fechaInactivo;
    }

    public void setFechaInactivo(Date fechaInactivo) {
        this.fechaInactivo = fechaInactivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDirector != null ? idDirector.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DirectorCe)) {
            return false;
        }
        DirectorCe other = (DirectorCe) object;
        if ((this.idDirector == null && other.idDirector != null) || (this.idDirector != null && !this.idDirector.equals(other.idDirector))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.cooperacion.web.model.DirectorCe[ idDirector=" + idDirector + " ]";
    }
    
}
