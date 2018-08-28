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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "TIPO_EMPRESA")
@NamedQueries({
    @NamedQuery(name = "TipoEmpresa.findAll", query = "SELECT t FROM TipoEmpresa t")})
public class TipoEmpresa implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_EMP")
    private BigDecimal idTipoEmp;
    @Column(name = "DESCRIPCION_TIPO_EMP")
    private String descripcionTipoEmp;
    @OneToMany(mappedBy = "idTipoEmpresa", fetch = FetchType.LAZY)
    private List<Empresa> empresaList;
    @OneToMany(mappedBy = "tipIdTipoEmp", fetch = FetchType.LAZY)
    private List<TipoEmpresa> tipoEmpresaList;
    @JoinColumn(name = "TIP_ID_TIPO_EMP", referencedColumnName = "ID_TIPO_EMP")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoEmpresa tipIdTipoEmp;

    public TipoEmpresa() {
    }

    public TipoEmpresa(BigDecimal idTipoEmp) {
        this.idTipoEmp = idTipoEmp;
    }

    public BigDecimal getIdTipoEmp() {
        return idTipoEmp;
    }

    public void setIdTipoEmp(BigDecimal idTipoEmp) {
        this.idTipoEmp = idTipoEmp;
    }

    public String getDescripcionTipoEmp() {
        return descripcionTipoEmp;
    }

    public void setDescripcionTipoEmp(String descripcionTipoEmp) {
        this.descripcionTipoEmp = descripcionTipoEmp;
    }

    public List<Empresa> getEmpresaList() {
        return empresaList;
    }

    public void setEmpresaList(List<Empresa> empresaList) {
        this.empresaList = empresaList;
    }

    public List<TipoEmpresa> getTipoEmpresaList() {
        return tipoEmpresaList;
    }

    public void setTipoEmpresaList(List<TipoEmpresa> tipoEmpresaList) {
        this.tipoEmpresaList = tipoEmpresaList;
    }

    public TipoEmpresa getTipIdTipoEmp() {
        return tipIdTipoEmp;
    }

    public void setTipIdTipoEmp(TipoEmpresa tipIdTipoEmp) {
        this.tipIdTipoEmp = tipIdTipoEmp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoEmp != null ? idTipoEmp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEmpresa)) {
            return false;
        }
        TipoEmpresa other = (TipoEmpresa) object;
        if ((this.idTipoEmp == null && other.idTipoEmp != null) || (this.idTipoEmp != null && !this.idTipoEmp.equals(other.idTipoEmp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.TipoEmpresa[ idTipoEmp=" + idTipoEmp + " ]";
    }
    
}
