/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PROCESO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proceso.findAll", query = "SELECT p FROM Proceso p"),
    @NamedQuery(name = "Proceso.findByIdProceso", query = "SELECT p FROM Proceso p WHERE p.idProceso = :idProceso"),
    @NamedQuery(name = "Proceso.findByNombreProceso", query = "SELECT p FROM Proceso p WHERE p.nombreProceso = :nombreProceso")})
public class Proceso implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROCESO")
    private BigDecimal idProceso;
    @Basic(optional = false)
    @Column(name = "NOMBRE_PROCESO")
    private String nombreProceso;
    @JoinColumn(name = "ID_RUBRO_INTERES", referencedColumnName = "ID_RUBRO_INTERES")
    @ManyToOne(fetch = FetchType.EAGER)
    private RubrosAmostrarInteres idRubroInteres;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProceso", fetch = FetchType.LAZY)
    private List<MatrizPago> matrizPagoList;

    public Proceso() {
    }

    public Proceso(BigDecimal idProceso) {
        this.idProceso = idProceso;
    }

    public Proceso(BigDecimal idProceso, String nombreProceso) {
        this.idProceso = idProceso;
        this.nombreProceso = nombreProceso;
    }

    public BigDecimal getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(BigDecimal idProceso) {
        this.idProceso = idProceso;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public RubrosAmostrarInteres getIdRubroInteres() {
        return idRubroInteres;
    }

    public void setIdRubroInteres(RubrosAmostrarInteres idRubroInteres) {
        this.idRubroInteres = idRubroInteres;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProceso != null ? idProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proceso)) {
            return false;
        }
        Proceso other = (Proceso) object;
        if ((this.idProceso == null && other.idProceso != null) || (this.idProceso != null && !this.idProceso.equals(other.idProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idProceso + " - " + nombreProceso;
    }

    public List<MatrizPago> getMatrizPagoList() {
        return matrizPagoList;
    }

    public void setMatrizPagoList(List<MatrizPago> matrizPagoList) {
        this.matrizPagoList = matrizPagoList;
    }
}