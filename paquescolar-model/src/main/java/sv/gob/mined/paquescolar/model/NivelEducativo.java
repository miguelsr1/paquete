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
@Table(name = "NIVEL_EDUCATIVO")
@NamedQueries({
    @NamedQuery(name = "NivelEducativo.findAll", query = "SELECT n FROM NivelEducativo n")})
public class NivelEducativo implements Serializable {

    @OneToMany(mappedBy = "idNivelEducativo", fetch = FetchType.LAZY)
    private List<DetalleOfertas> detalleOfertasList;
    @OneToMany(mappedBy = "idNivelEducativo", fetch = FetchType.LAZY)
    private List<EstadisticaCenso> estadisticaCensoList;
    @OneToMany(mappedBy = "idNivelEducativo", fetch = FetchType.LAZY)
    private List<PreciosRefRubroEmp> preciosRefRubroEmpList;
    @OneToMany(mappedBy = "idNivelEducativo", fetch = FetchType.LAZY)
    private List<PreciosRefRubro> preciosRefRubroList;
    @OneToMany(mappedBy = "idNivelEducativo", fetch = FetchType.LAZY)
    private List<ResguardoBienes> resguardoBienesList;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_NIVEL_EDUCATIVO")
    private BigDecimal idNivelEducativo;
    @Column(name = "DESCRIPCION_NIVEL")
    private String descripcionNivel;
    @OneToMany(mappedBy = "idNivelEducativo", fetch = FetchType.LAZY)
    private List<DetCapaSegunRubro> detCapaSegunRubroList;

    public NivelEducativo() {
    }

    public NivelEducativo(BigDecimal idNivelEducativo) {
        this.idNivelEducativo = idNivelEducativo;
    }

    public BigDecimal getIdNivelEducativo() {
        return idNivelEducativo;
    }

    public void setIdNivelEducativo(BigDecimal idNivelEducativo) {
        this.idNivelEducativo = idNivelEducativo;
    }

    public String getDescripcionNivel() {
        return descripcionNivel;
    }

    public void setDescripcionNivel(String descripcionNivel) {
        this.descripcionNivel = descripcionNivel;
    }

    public List<DetCapaSegunRubro> getDetCapaSegunRubroList() {
        return detCapaSegunRubroList;
    }

    public void setDetCapaSegunRubroList(List<DetCapaSegunRubro> detCapaSegunRubroList) {
        this.detCapaSegunRubroList = detCapaSegunRubroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNivelEducativo != null ? idNivelEducativo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NivelEducativo)) {
            return false;
        }
        NivelEducativo other = (NivelEducativo) object;
        if ((this.idNivelEducativo == null && other.idNivelEducativo != null) || (this.idNivelEducativo != null && !this.idNivelEducativo.equals(other.idNivelEducativo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcionNivel;
    }

    public List<PreciosRefRubroEmp> getPreciosRefRubroEmpList() {
        return preciosRefRubroEmpList;
    }

    public void setPreciosRefRubroEmpList(List<PreciosRefRubroEmp> preciosRefRubroEmpList) {
        this.preciosRefRubroEmpList = preciosRefRubroEmpList;
    }

    public List<PreciosRefRubro> getPreciosRefRubroList() {
        return preciosRefRubroList;
    }

    public void setPreciosRefRubroList(List<PreciosRefRubro> preciosRefRubroList) {
        this.preciosRefRubroList = preciosRefRubroList;
    }

    public List<EstadisticaCenso> getEstadisticaCensoList() {
        return estadisticaCensoList;
    }

    public void setEstadisticaCensoList(List<EstadisticaCenso> estadisticaCensoList) {
        this.estadisticaCensoList = estadisticaCensoList;
    }

    public List<DetalleOfertas> getDetalleOfertasList() {
        return detalleOfertasList;
    }

    public void setDetalleOfertasList(List<DetalleOfertas> detalleOfertasList) {
        this.detalleOfertasList = detalleOfertasList;
    }

    public List<ResguardoBienes> getResguardoBienesList() {
        return resguardoBienesList;
    }

    public void setResguardoBienesList(List<ResguardoBienes> resguardoBienesList) {
        this.resguardoBienesList = resguardoBienesList;
    }

}
