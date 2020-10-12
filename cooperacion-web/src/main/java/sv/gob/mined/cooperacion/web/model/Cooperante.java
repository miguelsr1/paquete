/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.web.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "COOPERANTE")
@NamedQueries({
    @NamedQuery(name = "Cooperante.findAll", query = "SELECT c FROM Cooperante c")})
public class Cooperante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_COOPERANTE")
    private Long idCooperante;
    @Column(name = "NOMBRE_COOPERANTE")
    private String nombreCooperante;
    @JoinColumn(name = "ID_TIPO_COOPERANTE", referencedColumnName = "ID_TIPO_COOPERANTE")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoCooperante idTipoCooperante;

    public Cooperante() {
    }

    public Cooperante(Long idCooperante) {
        this.idCooperante = idCooperante;
    }

    public Long getIdCooperante() {
        return idCooperante;
    }

    public void setIdCooperante(Long idCooperante) {
        this.idCooperante = idCooperante;
    }

    public String getNombreCooperante() {
        return nombreCooperante;
    }

    public void setNombreCooperante(String nombreCooperante) {
        this.nombreCooperante = nombreCooperante;
    }

    public TipoCooperante getIdTipoCooperante() {
        return idTipoCooperante;
    }

    public void setIdTipoCooperante(TipoCooperante idTipoCooperante) {
        this.idTipoCooperante = idTipoCooperante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCooperante != null ? idCooperante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cooperante)) {
            return false;
        }
        Cooperante other = (Cooperante) object;
        if ((this.idCooperante == null && other.idCooperante != null) || (this.idCooperante != null && !this.idCooperante.equals(other.idCooperante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.cooperacion.web.model.Cooperante[ idCooperante=" + idCooperante + " ]";
    }
    
}
