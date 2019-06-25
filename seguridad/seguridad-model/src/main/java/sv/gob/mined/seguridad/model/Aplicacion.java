/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "APLICACION")
@NamedQueries({
    @NamedQuery(name = "Aplicacion.findAll", query = "SELECT a FROM Aplicacion a")})
public class Aplicacion implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAplicacion", fetch = FetchType.LAZY)
    private List<Modulo> moduloList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_APLICACION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APP")
    @SequenceGenerator(name = "SEQ_APP", sequenceName = "SEQ_APP", allocationSize = 1, initialValue = 1)
    private Long idAplicacion;
    @Column(name = "ADMINISTRADOR_APLICACION")
    private String administradorAplicacion;
    @Column(name = "ESTADO_APLICACION")
    private Character estadoAplicacion;
    @Column(name = "FECHA_INICIO_PRODUCCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioProduccion;
    @Column(name = "NOMBRE_APLICACION")
    private String nombreAplicacion;
    @Column(name = "UNIDAD_DUENA")
    private String unidadDuena;
    @OneToMany(mappedBy = "idAplicacion", fetch = FetchType.LAZY)
    private List<GruApp> gruAppList;

    public Aplicacion() {
    }

    public Aplicacion(Long idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public Long getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(Long idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public String getAdministradorAplicacion() {
        return administradorAplicacion;
    }

    public void setAdministradorAplicacion(String administradorAplicacion) {
        this.administradorAplicacion = administradorAplicacion;
    }

    public Character getEstadoAplicacion() {
        return estadoAplicacion;
    }

    public void setEstadoAplicacion(Character estadoAplicacion) {
        this.estadoAplicacion = estadoAplicacion;
    }

    public Date getFechaInicioProduccion() {
        return fechaInicioProduccion;
    }

    public void setFechaInicioProduccion(Date fechaInicioProduccion) {
        this.fechaInicioProduccion = fechaInicioProduccion;
    }

    public String getNombreAplicacion() {
        return nombreAplicacion;
    }

    public void setNombreAplicacion(String nombreAplicacion) {
        this.nombreAplicacion = nombreAplicacion;
    }

    public String getUnidadDuena() {
        return unidadDuena;
    }

    public void setUnidadDuena(String unidadDuena) {
        this.unidadDuena = unidadDuena;
    }

    public List<GruApp> getGruAppList() {
        return gruAppList;
    }

    public void setGruAppList(List<GruApp> gruAppList) {
        this.gruAppList = gruAppList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAplicacion != null ? idAplicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aplicacion)) {
            return false;
        }
        Aplicacion other = (Aplicacion) object;
        if ((this.idAplicacion == null && other.idAplicacion != null) || (this.idAplicacion != null && !this.idAplicacion.equals(other.idAplicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.model.Aplicacion[ idAplicacion=" + idAplicacion + " ]";
    }

    public List<Modulo> getModuloList() {
        return moduloList;
    }

    public void setModuloList(List<Modulo> moduloList) {
        this.moduloList = moduloList;
    }
    
}
