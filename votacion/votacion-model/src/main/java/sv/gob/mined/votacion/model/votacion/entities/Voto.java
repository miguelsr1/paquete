/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.votacion.model.votacion.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "VOTO", schema = "VOTACION")
@NamedQueries({
    @NamedQuery(name = "Voto.findAll", query = "SELECT v FROM Voto v")})
public class Voto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_VOTO")
    private BigDecimal idVoto;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Column(name = "ESTADO_ELIMINACION")
    private BigInteger estadoEliminacion;
    @JoinColumn(name = "ID_CANDIDATO", referencedColumnName = "ID_CANDIDATO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Candidato idCandidato;
    @JoinColumn(name = "ID_DIRECTOR", referencedColumnName = "ID_DIRECTOR")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Director idDirector;

    public Voto() {
    }

    public Voto(BigDecimal idVoto) {
        this.idVoto = idVoto;
    }

    public BigDecimal getIdVoto() {
        return idVoto;
    }

    public void setIdVoto(BigDecimal idVoto) {
        this.idVoto = idVoto;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public BigInteger getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(BigInteger estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public Candidato getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Candidato idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Director getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(Director idDirector) {
        this.idDirector = idDirector;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVoto != null ? idVoto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Voto)) {
            return false;
        }
        Voto other = (Voto) object;
        if ((this.idVoto == null && other.idVoto != null) || (this.idVoto != null && !this.idVoto.equals(other.idVoto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.votacion.api.votacion.entities.Voto[ idVoto=" + idVoto + " ]";
    }

}
