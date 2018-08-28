/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PROCESO_ADQUISICION")
@NamedQueries({
    @NamedQuery(name = "ProcesoAdquisicion.findAll", query = "SELECT p FROM ProcesoAdquisicion p")})
public class ProcesoAdquisicion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROCESO_ADQ")
    private Integer idProcesoAdq;
    @Column(name = "DESCRIPCION_PROCESO_ADQ")
    private String descripcionProcesoAdq;
    @OneToMany(mappedBy = "padreIdProcesoAdq")
    private List<ProcesoAdquisicion> procesoAdquisicionList;
    @JoinColumn(name = "PADRE_ID_PROCESO_ADQ", referencedColumnName = "ID_PROCESO_ADQ")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProcesoAdquisicion padreIdProcesoAdq;
    @JoinColumn(name = "ID_ANHO", referencedColumnName = "ID_ANHO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Anho idAnho;
    @OneToMany(mappedBy = "idProcesoAdq", fetch = FetchType.LAZY)
    @OrderBy("idDetProcesoAdq ASC")
    private List<DetalleProcesoAdq> detalleProcesoAdqList;
    @OneToMany(mappedBy = "idProcesoAdq", fetch = FetchType.LAZY)
    private List<EstadisticaCenso> estadisticaCensoList;

    public ProcesoAdquisicion() {
    }

    public ProcesoAdquisicion(Integer idProcesoAdq) {
        this.idProcesoAdq = idProcesoAdq;
    }

    public Integer getIdProcesoAdq() {
        return idProcesoAdq;
    }

    public void setIdProcesoAdq(Integer idProcesoAdq) {
        this.idProcesoAdq = idProcesoAdq;
    }

    public String getDescripcionProcesoAdq() {
        return descripcionProcesoAdq;
    }

    public void setDescripcionProcesoAdq(String descripcionProcesoAdq) {
        this.descripcionProcesoAdq = descripcionProcesoAdq;
    }

    public List<ProcesoAdquisicion> getProcesoAdquisicionList() {
        return procesoAdquisicionList;
    }

    public void setProcesoAdquisicionList(List<ProcesoAdquisicion> procesoAdquisicionList) {
        this.procesoAdquisicionList = procesoAdquisicionList;
    }

    public ProcesoAdquisicion getPadreIdProcesoAdq() {
        return padreIdProcesoAdq;
    }

    public void setPadreIdProcesoAdq(ProcesoAdquisicion padreIdProcesoAdq) {
        this.padreIdProcesoAdq = padreIdProcesoAdq;
    }

    public Anho getIdAnho() {
        return idAnho;
    }

    public void setIdAnho(Anho idAnho) {
        this.idAnho = idAnho;
    }

    public List<DetalleProcesoAdq> getDetalleProcesoAdqList() {
        return detalleProcesoAdqList;
    }

    public void setDetalleProcesoAdqList(List<DetalleProcesoAdq> detalleProcesoAdqList) {
        this.detalleProcesoAdqList = detalleProcesoAdqList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProcesoAdq != null ? idProcesoAdq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcesoAdquisicion)) {
            return false;
        }
        ProcesoAdquisicion other = (ProcesoAdquisicion) object;
        return !((this.idProcesoAdq == null && other.idProcesoAdq != null) || (this.idProcesoAdq != null && !this.idProcesoAdq.equals(other.idProcesoAdq)));
    }

    @Override
    public String toString() {
        return descripcionProcesoAdq;
    }

    public List<EstadisticaCenso> getEstadisticaCensoList() {
        return estadisticaCensoList;
    }

    public void setEstadisticaCensoList(List<EstadisticaCenso> estadisticaCensoList) {
        this.estadisticaCensoList = estadisticaCensoList;
    }
}
