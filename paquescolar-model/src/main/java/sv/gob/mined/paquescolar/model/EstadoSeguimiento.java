/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
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
@Table(name = "ESTADO_SEGUIMIENTO")
@NamedQueries({
    @NamedQuery(name = "EstadoSeguimiento.findAll", query = "SELECT e FROM EstadoSeguimiento e")})
public class EstadoSeguimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ESTADO_SEGUIMIENTO")
    private Integer idEstadoSeguimiento;
    @Column(name = "DESCRIPCION_ESTADO")
    private String descripcionEstado;
    @OneToMany(mappedBy = "idEstadoSeguimiento")
    private List<RecepcionBienesServicios> recepcionBienesServiciosList;

    public EstadoSeguimiento() {
    }

    public EstadoSeguimiento(Integer idEstadoSeguimiento) {
        this.idEstadoSeguimiento = idEstadoSeguimiento;
    }

    public Integer getIdEstadoSeguimiento() {
        return idEstadoSeguimiento;
    }

    public void setIdEstadoSeguimiento(Integer idEstadoSeguimiento) {
        this.idEstadoSeguimiento = idEstadoSeguimiento;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public List<RecepcionBienesServicios> getRecepcionBienesServiciosList() {
        return recepcionBienesServiciosList;
    }

    public void setRecepcionBienesServiciosList(List<RecepcionBienesServicios> recepcionBienesServiciosList) {
        this.recepcionBienesServiciosList = recepcionBienesServiciosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoSeguimiento != null ? idEstadoSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoSeguimiento)) {
            return false;
        }
        EstadoSeguimiento other = (EstadoSeguimiento) object;
        if ((this.idEstadoSeguimiento == null && other.idEstadoSeguimiento != null) || (this.idEstadoSeguimiento != null && !this.idEstadoSeguimiento.equals(other.idEstadoSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.EstadoSeguimiento[ idEstadoSeguimiento=" + idEstadoSeguimiento + " ]";
    }
    
}
