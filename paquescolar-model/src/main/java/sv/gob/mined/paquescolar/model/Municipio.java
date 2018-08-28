/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "MUNICIPIO")
@NamedQueries({
    @NamedQuery(name = "Municipio.findAll", query = "SELECT m FROM Municipio m")})
public class Municipio implements Serializable {

    @OneToMany(mappedBy = "idMunicipio", fetch = FetchType.LAZY)
    private List<Empresa> empresaList;
    @OneToMany(mappedBy = "idMunicipio", fetch = FetchType.LAZY)
    private List<DisMunicipioInteres> disMunicipioInteresList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MUNICIPIO")
    private BigDecimal idMunicipio;
    @Column(name = "NOMBRE_MUNICIPIO")
    private String nombreMunicipio;
    @Column(name = "CODIGO_MUNICIPIO")
    private String codigoMunicipio;
    @OneToMany(mappedBy = "idMunicipio", fetch = FetchType.LAZY)
    private List<Persona> personaList;
    @JoinColumn(name = "CODIGO_DEPARTAMENTO", referencedColumnName = "CODIGO_DEPARTAMENTO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Departamento codigoDepartamento;

    public Municipio() {
    }

    public Municipio(BigDecimal idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public BigDecimal getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(BigDecimal idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    public Departamento getCodigoDepartamento() {
        if (codigoDepartamento == null) {
            codigoDepartamento = new Departamento();
        }
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(Departamento codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMunicipio != null ? idMunicipio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) object;
        return !((this.idMunicipio == null && other.idMunicipio != null) || (this.idMunicipio != null && !this.idMunicipio.equals(other.idMunicipio)));
    }

    @Override
    public String toString() {
        return codigoMunicipio + " - " + nombreMunicipio;
    }

    public List<Empresa> getEmpresaList() {
        return empresaList;
    }

    public void setEmpresaList(List<Empresa> empresaList) {
        this.empresaList = empresaList;
    }

    public List<DisMunicipioInteres> getDisMunicipioInteresList() {
        return disMunicipioInteresList;
    }

    public void setDisMunicipioInteresList(List<DisMunicipioInteres> disMunicipioInteresList) {
        this.disMunicipioInteresList = disMunicipioInteresList;
    }

}
