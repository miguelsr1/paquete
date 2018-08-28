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
@Table(name = "ESTADO_REGISTRO")
@NamedQueries({
    @NamedQuery(name = "EstadoRegistro.findAll", query = "SELECT e FROM EstadoRegistro e")})
public class EstadoRegistro implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ESTADO_REGISTRO")
    private BigDecimal idEstadoRegistro;
    @Column(name = "DESCRIPCION_ESTADO")
    private String descripcionEstado;
    @OneToMany(mappedBy = "idEstadoRegistro", fetch = FetchType.LAZY)
    private List<Empresa> empresaList;

    public EstadoRegistro() {
    }

    public EstadoRegistro(BigDecimal idEstadoRegistro) {
        this.idEstadoRegistro = idEstadoRegistro;
    }

    public BigDecimal getIdEstadoRegistro() {
        return idEstadoRegistro;
    }

    public void setIdEstadoRegistro(BigDecimal idEstadoRegistro) {
        this.idEstadoRegistro = idEstadoRegistro;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public List<Empresa> getEmpresaList() {
        return empresaList;
    }

    public void setEmpresaList(List<Empresa> empresaList) {
        this.empresaList = empresaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoRegistro != null ? idEstadoRegistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoRegistro)) {
            return false;
        }
        EstadoRegistro other = (EstadoRegistro) object;
        if ((this.idEstadoRegistro == null && other.idEstadoRegistro != null) || (this.idEstadoRegistro != null && !this.idEstadoRegistro.equals(other.idEstadoRegistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcionEstado;
    }
    
}
