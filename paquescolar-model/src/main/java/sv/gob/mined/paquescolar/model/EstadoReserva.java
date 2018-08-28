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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "ESTADO_RESERVA")
@NamedQueries({
    @NamedQuery(name = "EstadoReserva.findAll", query = "SELECT e FROM EstadoReserva e")})
public class EstadoReserva implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ESTADO_RESERVA")
    private BigDecimal idEstadoReserva;
    @Column(name = "DESCRIPCION_RESERVA")
    private String descripcionReserva;
    @OneToMany(mappedBy = "idEstadoReserva", fetch = FetchType.LAZY)
    private List<ResolucionesAdjudicativas> resolucionesAdjudicativasList;

    public EstadoReserva() {
    }

    public EstadoReserva(BigDecimal idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }

    public BigDecimal getIdEstadoReserva() {
        return idEstadoReserva;
    }

    public void setIdEstadoReserva(BigDecimal idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }

    public String getDescripcionReserva() {
        return descripcionReserva;
    }

    public void setDescripcionReserva(String descripcionReserva) {
        this.descripcionReserva = descripcionReserva;
    }

    public List<ResolucionesAdjudicativas> getResolucionesAdjudicativasList() {
        return resolucionesAdjudicativasList;
    }

    public void setResolucionesAdjudicativasList(List<ResolucionesAdjudicativas> resolucionesAdjudicativasList) {
        this.resolucionesAdjudicativasList = resolucionesAdjudicativasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoReserva != null ? idEstadoReserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoReserva)) {
            return false;
        }
        EstadoReserva other = (EstadoReserva) object;
        if ((this.idEstadoReserva == null && other.idEstadoReserva != null) || (this.idEstadoReserva != null && !this.idEstadoReserva.equals(other.idEstadoReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcionReserva;
    }
    
}
