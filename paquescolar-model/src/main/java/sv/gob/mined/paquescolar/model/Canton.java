/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "CANTON")
@NamedQueries({
    @NamedQuery(name = "Canton.findAll", query = "SELECT c FROM Canton c")})
public class Canton implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CANTON")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "canton")
    @SequenceGenerator(name="canton", sequenceName = "SEQ_CANTON", allocationSize=1, initialValue=1)
    private Long idCanton;
    @Column(name = "CODIGO_CANTON")
    private String codigoCanton;
    @Column(name = "NOMBRE_CANTON")
    private String nombreCanton;
    @Column(name = "ID_MUNICIPIO")
    private BigInteger idMunicipio;

    public Canton() {
    }

    public Canton(Long idCanton) {
        this.idCanton = idCanton;
    }

    public Long getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(Long idCanton) {
        this.idCanton = idCanton;
    }

    public String getCodigoCanton() {
        return codigoCanton;
    }

    public void setCodigoCanton(String codigoCanton) {
        this.codigoCanton = codigoCanton;
    }

    public String getNombreCanton() {
        return nombreCanton;
    }

    public void setNombreCanton(String nombreCanton) {
        this.nombreCanton = nombreCanton;
    }

    public BigInteger getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(BigInteger idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCanton != null ? idCanton.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Canton)) {
            return false;
        }
        Canton other = (Canton) object;
        if ((this.idCanton == null && other.idCanton != null) || (this.idCanton != null && !this.idCanton.equals(other.idCanton))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.Canton[ idCanton=" + idCanton + " ]";
    }
    
}
