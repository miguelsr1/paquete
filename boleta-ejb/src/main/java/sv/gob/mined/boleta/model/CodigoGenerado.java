/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "CODIGO_GENERADO")
@NamedQueries({
    @NamedQuery(name = "CodigoGenerado.findAll", query = "SELECT c FROM CodigoGenerado c")})
public class CodigoGenerado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CODIGO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codigoGenerado")
    @SequenceGenerator(name = "codigoGenerado", sequenceName = "SEQ_CODIGO_GENERADO", allocationSize = 1, initialValue = 1)
    private Long idCodigo;
    @Column(name = "CODIGO_DEPARTAMENTO")
    private String codigoDepartamento;
    @Column(name = "MES_ANHO")
    private String mesAnho;

    public CodigoGenerado() {
    }

    public CodigoGenerado(Long idCodigo) {
        this.idCodigo = idCodigo;
    }

    public Long getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Long idCodigo) {
        this.idCodigo = idCodigo;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getMesAnho() {
        return mesAnho;
    }

    public void setMesAnho(String mesAnho) {
        this.mesAnho = mesAnho;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCodigo != null ? idCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodigoGenerado)) {
            return false;
        }
        CodigoGenerado other = (CodigoGenerado) object;
        if ((this.idCodigo == null && other.idCodigo != null) || (this.idCodigo != null && !this.idCodigo.equals(other.idCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.boleta.model.CodigoGenerado[ idCodigo=" + idCodigo + " ]";
    }

}
