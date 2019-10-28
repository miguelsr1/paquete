/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

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
@Table(name = "MUNICIPIO_ALEDANHO")
@NamedQueries({
    @NamedQuery(name = "MunicipioAledanho.findAll", query = "SELECT m FROM MunicipioAledanho m")})
public class MunicipioAledanho implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "CODIGOS_MUNICIPIOS")
    private String codigosMunicipios;
    @Column(name = "ID_MUNICIPIO")
    private BigDecimal idMunicipio;
    @Column(name = "ID_MUNICIPIOS")
    private String idMunicipios;

    public MunicipioAledanho() {
    }

    public MunicipioAledanho(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigosMunicipios() {
        return codigosMunicipios;
    }

    public void setCodigosMunicipios(String codigosMunicipios) {
        this.codigosMunicipios = codigosMunicipios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MunicipioAledanho)) {
            return false;
        }
        MunicipioAledanho other = (MunicipioAledanho) object;
        return !((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo)));
    }

    public BigDecimal getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(BigDecimal idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getIdMunicipios() {
        return idMunicipios;
    }

    public void setIdMunicipios(String idMunicipios) {
        this.idMunicipios = idMunicipios;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.MunicipioAledanho[ codigo=" + codigo + " ]";
    }

}
