/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "ENTIDAD_FINANCIERA")
@NamedQueries({
    @NamedQuery(name = "EntidadFinanciera.findAll", query = "SELECT e FROM EntidadFinanciera e")})
@SqlResultSetMapping(name = "defaultEntidadCreditoHabilitado",
        entities = @EntityResult(entityClass = EntidadFinanciera.class))
public class EntidadFinanciera implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEntFinanciera", fetch = FetchType.LAZY)
    private List<UsuarioEntidadFinanciera> usuarioEntidadFinancieraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEntFinanciera", fetch = FetchType.LAZY)
    private List<EntFinanDetProAdq> entFinanDetProAdqList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_ENT_FINANCIERA")
    private String codEntFinanciera;
    @Basic(optional = false)
    @Column(name = "NOMBRE_ENT_FINAN")
    private String nombreEntFinan;
    @Basic(optional = false)
    @Column(name = "E_MAIL")
    private String eMail;
    @Basic(optional = false)
    @Column(name = "TELEFONO_ENT")
    private String telefonoEnt;
    @Basic(optional = false)
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Basic(optional = false)
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Basic(optional = false)
    @Column(name = "ESTADO_ELIMINACION")
    private BigInteger estadoEliminacion;
    @Column(name = "NOMBRE_CONTACTO")
    private String nombreContacto;
    @OneToMany(mappedBy = "codEntFinanciera", fetch = FetchType.LAZY)
    private List<CreditoBancario> creditoBancarioList;
    @Column(name = "BANDERA")
    private Short bandera;
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;

    public EntidadFinanciera() {
    }

    public EntidadFinanciera(String codEntFinanciera) {
        this.codEntFinanciera = codEntFinanciera;
    }

    public EntidadFinanciera(String codEntFinanciera, String nombreEntFinan, String eMail, String telefonoEnt, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion) {
        this.codEntFinanciera = codEntFinanciera;
        this.nombreEntFinan = nombreEntFinan;
        this.eMail = eMail;
        this.telefonoEnt = telefonoEnt;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public String getCodEntFinanciera() {
        return codEntFinanciera;
    }

    public void setCodEntFinanciera(String codEntFinanciera) {
        this.codEntFinanciera = codEntFinanciera;
    }

    public String getNombreEntFinan() {
        return nombreEntFinan;
    }

    public void setNombreEntFinan(String nombreEntFinan) {
        this.nombreEntFinan = nombreEntFinan;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getTelefonoEnt() {
        return telefonoEnt;
    }

    public void setTelefonoEnt(String telefonoEnt) {
        this.telefonoEnt = telefonoEnt;
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

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public BigInteger getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(BigInteger estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public List<CreditoBancario> getCreditoBancarioList() {
        return creditoBancarioList;
    }

    public void setCreditoBancarioList(List<CreditoBancario> creditoBancarioList) {
        this.creditoBancarioList = creditoBancarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEntFinanciera != null ? codEntFinanciera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadFinanciera)) {
            return false;
        }
        EntidadFinanciera other = (EntidadFinanciera) object;
        if ((this.codEntFinanciera == null && other.codEntFinanciera != null) || (this.codEntFinanciera != null && !this.codEntFinanciera.equals(other.codEntFinanciera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreEntFinan;
    }

    public List<UsuarioEntidadFinanciera> getUsuarioEntidadFinancieraList() {
        return usuarioEntidadFinancieraList;
    }

    public void setUsuarioEntidadFinancieraList(List<UsuarioEntidadFinanciera> usuarioEntidadFinancieraList) {
        this.usuarioEntidadFinancieraList = usuarioEntidadFinancieraList;
    }

    public List<EntFinanDetProAdq> getEntFinanDetProAdqList() {
        return entFinanDetProAdqList;
    }

    public void setEntFinanDetProAdqList(List<EntFinanDetProAdq> entFinanDetProAdqList) {
        this.entFinanDetProAdqList = entFinanDetProAdqList;
    }

    public Short getBandera() {
        return bandera;
    }

    public void setBandera(Short bandera) {
        this.bandera = bandera;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}
