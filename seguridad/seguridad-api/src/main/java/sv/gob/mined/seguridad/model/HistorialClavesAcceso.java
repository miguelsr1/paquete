/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "HISTORIAL_CLAVES_ACCESO", schema = "SEGURIDAD_V2")
@NamedQueries({
    @NamedQuery(name = "HistorialClavesAcceso.findAll", query = "SELECT h FROM HistorialClavesAcceso h")})
public class HistorialClavesAcceso implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_HISTORIAL_CLAVES")
    private BigDecimal idHistorialClaves;
    @Column(name = "CLAVE_ACCESO")
    private String claveAcceso;
    @Column(name = "FECHA_CADUCIDAD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCaducidad;
    @JoinColumn(name = "LOGIN", referencedColumnName = "LOGIN")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario login;

    public HistorialClavesAcceso() {
    }

    public HistorialClavesAcceso(BigDecimal idHistorialClaves) {
        this.idHistorialClaves = idHistorialClaves;
    }

    public BigDecimal getIdHistorialClaves() {
        return idHistorialClaves;
    }

    public void setIdHistorialClaves(BigDecimal idHistorialClaves) {
        this.idHistorialClaves = idHistorialClaves;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Usuario getLogin() {
        return login;
    }

    public void setLogin(Usuario login) {
        this.login = login;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistorialClaves != null ? idHistorialClaves.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialClavesAcceso)) {
            return false;
        }
        HistorialClavesAcceso other = (HistorialClavesAcceso) object;
        if ((this.idHistorialClaves == null && other.idHistorialClaves != null) || (this.idHistorialClaves != null && !this.idHistorialClaves.equals(other.idHistorialClaves))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.model.HistorialClavesAcceso[ idHistorialClaves=" + idHistorialClaves + " ]";
    }
    
}
