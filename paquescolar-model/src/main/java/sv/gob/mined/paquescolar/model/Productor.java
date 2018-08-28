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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PRODUCTOR")
@NamedQueries({
    @NamedQuery(name = "Productor.findAll", query = "SELECT p FROM Productor p")})
public class Productor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PRODUCTOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCTOR")
    @SequenceGenerator(name = "SEQ_PRODUCTOR", sequenceName = "SEQ_PRODUCTOR", allocationSize = 1, initialValue = 1)
    private Integer idProductor;
    @Column(name = "NOMBRE_PRODUCTO")
    private String nombreProducto;
    @Column(name = "NUMERO_NIT")
    private String numeroNit;
    @Column(name = "DIRECCION")
    private String direccion;
    @OneToMany(mappedBy = "idProductor", fetch = FetchType.LAZY)
    private List<ProveedorEmpresa> proveedorEmpresaList;

    public Productor() {
    }

    public Productor(Integer idProductor) {
        this.idProductor = idProductor;
    }

    public Integer getIdProductor() {
        return idProductor;
    }

    public void setIdProductor(Integer idProductor) {
        this.idProductor = idProductor;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<ProveedorEmpresa> getProveedorEmpresaList() {
        return proveedorEmpresaList;
    }

    public void setProveedorEmpresaList(List<ProveedorEmpresa> proveedorEmpresaList) {
        this.proveedorEmpresaList = proveedorEmpresaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProductor != null ? idProductor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productor)) {
            return false;
        }
        Productor other = (Productor) object;
        if ((this.idProductor == null && other.idProductor != null) || (this.idProductor != null && !this.idProductor.equals(other.idProductor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.Productor[ idProductor=" + idProductor + " ]";
    }
    
}
