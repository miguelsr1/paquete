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
@Table(name = "TIPO_DOC_PAGO")
@NamedQueries({
    @NamedQuery(name = "TipoDocPago.findAll", query = "SELECT t FROM TipoDocPago t")})
public class TipoDocPago implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_DOC_PAGO", nullable = false, precision = 0, scale = -127)
    private BigDecimal idTipoDocPago;
    @Column(name = "DESCRIPCION_DOC_PAGO", length = 20)
    private String descripcionDocPago;
    @OneToMany(mappedBy = "idTipoDocPago", fetch = FetchType.LAZY)
    private List<DetalleMatrizPago> detalleMatrizPagoList;

    public TipoDocPago() {
    }

    public TipoDocPago(BigDecimal idTipoDocPago) {
        this.idTipoDocPago = idTipoDocPago;
    }

    public BigDecimal getIdTipoDocPago() {
        return idTipoDocPago;
    }

    public void setIdTipoDocPago(BigDecimal idTipoDocPago) {
        this.idTipoDocPago = idTipoDocPago;
    }

    public String getDescripcionDocPago() {
        return descripcionDocPago;
    }

    public void setDescripcionDocPago(String descripcionDocPago) {
        this.descripcionDocPago = descripcionDocPago;
    }

    public List<DetalleMatrizPago> getDetalleMatrizPagoList() {
        return detalleMatrizPagoList;
    }

    public void setDetalleMatrizPagoList(List<DetalleMatrizPago> detalleMatrizPagoList) {
        this.detalleMatrizPagoList = detalleMatrizPagoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoDocPago != null ? idTipoDocPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDocPago)) {
            return false;
        }
        TipoDocPago other = (TipoDocPago) object;
        if ((this.idTipoDocPago == null && other.idTipoDocPago != null) || (this.idTipoDocPago != null && !this.idTipoDocPago.equals(other.idTipoDocPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcionDocPago;
    }
    
}
