/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DEPARTAMENTO")
@NamedQueries({
    @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d")})
public class Departamento implements Serializable {
    @OneToMany(mappedBy = "codigoDepartamento", fetch = FetchType.LAZY)
    private List<CapaDistribucionAcre> capaDistribucionAcreList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO_DEPARTAMENTO")
    private String codigoDepartamento;
    @Column(name = "NOMBRE_DEPARTAMENTO")
    private String nombreDepartamento;
    @OneToMany(mappedBy = "codigoDepartamento", fetch = FetchType.LAZY)
    @OrderBy("codigoMunicipio ASC")
    private List<Municipio> municipioList;
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

    public List<Municipio> getMunicipioList() {
        return municipioList;
    }

    public void setMunicipioList(List<Municipio> municipioList) {
        this.municipioList = municipioList;
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

    public List<CapaDistribucionAcre> getCapaDistribucionAcreList() {
        return capaDistribucionAcreList;
    }

    public void setCapaDistribucionAcreList(List<CapaDistribucionAcre> capaDistribucionAcreList) {
        this.capaDistribucionAcreList = capaDistribucionAcreList;
    }

    public List<VwCatalogoEntidadEducativa> getCatalogoEntidadEducativaList() {
        return catalogoEntidadEducativaList;
    }

    public void setCatalogoEntidadEducativaList(List<VwCatalogoEntidadEducativa> catalogoEntidadEducativaList) {
        this.catalogoEntidadEducativaList = catalogoEntidadEducativaList;
    }
    
}
