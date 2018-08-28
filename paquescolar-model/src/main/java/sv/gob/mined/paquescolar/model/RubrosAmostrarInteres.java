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
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "RUBROS_AMOSTRAR_INTERES")
@NamedQueries({
    @NamedQuery(name = "RubrosAmostrarInteres.findAll", query = "SELECT r FROM RubrosAmostrarInteres r")})
@SqlResultSetMapping(name = "defaultProcesoCreditoHabilitado",
        entities = @EntityResult(entityClass = RubrosAmostrarInteres.class))
public class RubrosAmostrarInteres implements Serializable {

    @OneToMany(mappedBy = "idRubroAdq", fetch = FetchType.LAZY)
    private List<DetalleProcesoAdq> detalleProcesoAdqList;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RUBRO_INTERES")
    private BigDecimal idRubroInteres;
    @Column(name = "DESCRIPCION_RUBRO")
    private String descripcionRubro;
    @Column(name = "NOMBRE_CORTO")
    private String nombreCorto;
    @Column(name = "ID_RUBRO_UNIFORME")
    private BigDecimal idRubroUniforme;

    public RubrosAmostrarInteres() {
    }

    public RubrosAmostrarInteres(BigDecimal idRubroInteres) {
        this.idRubroInteres = idRubroInteres;
    }

    public BigDecimal getIdRubroInteres() {
        return idRubroInteres;
    }

    public void setIdRubroInteres(BigDecimal idRubroInteres) {
        this.idRubroInteres = idRubroInteres;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRubroInteres != null ? idRubroInteres.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RubrosAmostrarInteres)) {
            return false;
        }
        RubrosAmostrarInteres other = (RubrosAmostrarInteres) object;
        if ((this.idRubroInteres == null && other.idRubroInteres != null) || (this.idRubroInteres != null && !this.idRubroInteres.equals(other.idRubroInteres))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcionRubro;
    }

    public List<DetalleProcesoAdq> getDetalleProcesoAdqList() {
        return detalleProcesoAdqList;
    }

    public void setDetalleProcesoAdqList(List<DetalleProcesoAdq> detalleProcesoAdqList) {
        this.detalleProcesoAdqList = detalleProcesoAdqList;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public BigDecimal getIdRubroUniforme() {
        return idRubroUniforme;
    }

    public void setIdRubroUniforme(BigDecimal idRubroUniforme) {
        this.idRubroUniforme = idRubroUniforme;
    }
}
