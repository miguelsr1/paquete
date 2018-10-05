/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "RPT_DOCUMENTOS")
@NamedQueries({
    @NamedQuery(name = "RptDocumentos.findAll", query = "SELECT r FROM RptDocumentos r")})
public class RptDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RPT")
    private Integer idRpt;
    @Column(name = "NOMBRE_RPT")
    private String nombreRpt;
    @Column(name = "DS")
    private Short ds;
    @JoinColumn(name = "ID_DET_PROCESO_ADQ", referencedColumnName = "ID_DET_PROCESO_ADQ")
    @ManyToOne
    private DetalleProcesoAdq idDetProcesoAdq;
    @JoinColumn(name = "ID_TIPO_RPT", referencedColumnName = "ID_TIPO_RPT")
    @ManyToOne
    private TipoRpt idTipoRpt;
    @Column(name = "ORDEN")
    private Short orden;

    public RptDocumentos() {
    }

    public Short getOrden() {
        return orden;
    }

    public void setOrden(Short orden) {
        this.orden = orden;
    }

    public RptDocumentos(Integer idRpt) {
        this.idRpt = idRpt;
    }

    public Integer getIdRpt() {
        return idRpt;
    }

    public void setIdRpt(Integer idRpt) {
        this.idRpt = idRpt;
    }

    public String getNombreRpt() {
        return nombreRpt;
    }

    public void setNombreRpt(String nombreRpt) {
        this.nombreRpt = nombreRpt;
    }

    public DetalleProcesoAdq getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(DetalleProcesoAdq idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    public TipoRpt getIdTipoRpt() {
        return idTipoRpt;
    }

    public void setIdTipoRpt(TipoRpt idTipoRpt) {
        this.idTipoRpt = idTipoRpt;
    }

    public Short getDs() {
        return ds;
    }

    public void setDs(Short ds) {
        this.ds = ds;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRpt != null ? idRpt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RptDocumentos)) {
            return false;
        }
        RptDocumentos other = (RptDocumentos) object;
        return !((this.idRpt == null && other.idRpt != null) || (this.idRpt != null && !this.idRpt.equals(other.idRpt)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.RptDocumentos[ idRpt=" + idRpt + " ]";
    }
}