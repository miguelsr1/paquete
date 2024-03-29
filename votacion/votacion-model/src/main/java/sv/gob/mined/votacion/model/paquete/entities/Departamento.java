/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.votacion.model.paquete.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "DEPARTAMENTO", schema = "PAQUETES")
@NamedQueries({
    @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d")})
public class Departamento implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO_DEPARTAMENTO")
    private String codigoDepartamento;
    @Column(name = "NOMBRE_DEPARTAMENTO")
    private String nombreDepartamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoDepartamento")
    private List<VwCatalogoEntidadEducativa> catalogoEntidadEducativaList;

    public Departamento() {
    }

    public Departamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoDepartamento != null ? codigoDepartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.codigoDepartamento == null && other.codigoDepartamento != null) || (this.codigoDepartamento != null && !this.codigoDepartamento.equals(other.codigoDepartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codigoDepartamento + " - " + nombreDepartamento;
    }

    public List<VwCatalogoEntidadEducativa> getCatalogoEntidadEducativaList() {
        return catalogoEntidadEducativaList;
    }

    public void setCatalogoEntidadEducativaList(List<VwCatalogoEntidadEducativa> catalogoEntidadEducativaList) {
        this.catalogoEntidadEducativaList = catalogoEntidadEducativaList;
    }
    
}
