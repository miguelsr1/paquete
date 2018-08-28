/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
@Table(name = "TIPO_RPT")
@NamedQueries({
    @NamedQuery(name = "TipoRpt.findAll", query = "SELECT t FROM TipoRpt t")})
public class TipoRpt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_RPT")
    private Short idTipoRpt;
    @Column(name = "NOMBRE_TIPO")
    private String nombreTipo;
    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;
    @OneToMany(mappedBy = "idTipoRpt")
    private List<RptDocumentos> rptDocumentosList;

    public TipoRpt() {
    }

    public TipoRpt(Short idTipoRpt) {
        this.idTipoRpt = idTipoRpt;
    }

    public Short getIdTipoRpt() {
        return idTipoRpt;
    }

    public void setIdTipoRpt(Short idTipoRpt) {
        this.idTipoRpt = idTipoRpt;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public Short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(Short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public List<RptDocumentos> getRptDocumentosList() {
        return rptDocumentosList;
    }

    public void setRptDocumentosList(List<RptDocumentos> rptDocumentosList) {
        this.rptDocumentosList = rptDocumentosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoRpt != null ? idTipoRpt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoRpt)) {
            return false;
        }
        TipoRpt other = (TipoRpt) object;
        if ((this.idTipoRpt == null && other.idTipoRpt != null) || (this.idTipoRpt != null && !this.idTipoRpt.equals(other.idTipoRpt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.TipoRpt[ idTipoRpt=" + idTipoRpt + " ]";
    }
    
}
