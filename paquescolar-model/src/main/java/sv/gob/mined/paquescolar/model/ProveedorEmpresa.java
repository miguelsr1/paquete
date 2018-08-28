/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PROVEEDOR_EMPRESA")
@NamedQueries({
    @NamedQuery(name = "ProveedorEmpresa.findAll", query = "SELECT p FROM ProveedorEmpresa p")})
public class ProveedorEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROVEEDOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROVEEDOR_EMPRESA")
    @SequenceGenerator(name = "SEQ_PROVEEDOR_EMPRESA", sequenceName = "SEQ_PROVEEDOR_EMPRESA", allocationSize = 1, initialValue = 1)
    private BigDecimal idProveedor;
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "ESTADO_ELIMINACION")
    private BigInteger estadoEliminacion;
    @JoinColumn(name = "ID_PRODUCTOR", referencedColumnName = "ID_PRODUCTOR")
    @ManyToOne(fetch = FetchType.EAGER)
    private Productor idProductor;
    @JoinColumn(name = "ID_MUESTRA_INTERES", referencedColumnName = "ID_MUESTRA_INTERES")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetRubroMuestraInteres idMuestraInteres;

    public ProveedorEmpresa() {
    }

    public ProveedorEmpresa(BigDecimal idProveedor) {
        this.idProveedor = idProveedor;
    }

    public BigDecimal getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(BigDecimal idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public BigInteger getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(BigInteger estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public Productor getIdProductor() {
        return idProductor;
    }

    public void setIdProductor(Productor idProductor) {
        this.idProductor = idProductor;
    }

    public DetRubroMuestraInteres getIdMuestraInteres() {
        return idMuestraInteres;
    }

    public void setIdMuestraInteres(DetRubroMuestraInteres idMuestraInteres) {
        this.idMuestraInteres = idMuestraInteres;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedor != null ? idProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProveedorEmpresa)) {
            return false;
        }
        ProveedorEmpresa other = (ProveedorEmpresa) object;
        if ((this.idProveedor == null && other.idProveedor != null) || (this.idProveedor != null && !this.idProveedor.equals(other.idProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.ProveedorEmpresa[ idProveedor=" + idProveedor + " ]";
    }
}
