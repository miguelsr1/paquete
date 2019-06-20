/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "BITACORA_ACCESO")
@NamedQueries({
    @NamedQuery(name = "BitacoraAcceso.findAll", query = "SELECT b FROM BitacoraAcceso b")})
public class BitacoraAcceso implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_BITACORA_ACCESO")
    private BigDecimal idBitacoraAcceso;
    @Column(name = "FECHA_ACCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAcceso;
    @Column(name = "FECHA_SALIDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalida;
    @Column(name = "IP_ACCESO")
    private String ipAcceso;
    @Column(name = "ID_USU_GRUPO")
    private BigInteger idUsuGrupo;

    public BitacoraAcceso() {
    }

    public BitacoraAcceso(BigDecimal idBitacoraAcceso) {
        this.idBitacoraAcceso = idBitacoraAcceso;
    }

    public BigDecimal getIdBitacoraAcceso() {
        return idBitacoraAcceso;
    }

    public void setIdBitacoraAcceso(BigDecimal idBitacoraAcceso) {
        this.idBitacoraAcceso = idBitacoraAcceso;
    }

    public Date getFechaAcceso() {
        return fechaAcceso;
    }

    public void setFechaAcceso(Date fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getIpAcceso() {
        return ipAcceso;
    }

    public void setIpAcceso(String ipAcceso) {
        this.ipAcceso = ipAcceso;
    }

    public BigInteger getIdUsuGrupo() {
        return idUsuGrupo;
    }

    public void setIdUsuGrupo(BigInteger idUsuGrupo) {
        this.idUsuGrupo = idUsuGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBitacoraAcceso != null ? idBitacoraAcceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BitacoraAcceso)) {
            return false;
        }
        BitacoraAcceso other = (BitacoraAcceso) object;
        if ((this.idBitacoraAcceso == null && other.idBitacoraAcceso != null) || (this.idBitacoraAcceso != null && !this.idBitacoraAcceso.equals(other.idBitacoraAcceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.seguridad.model.BitacoraAcceso[ idBitacoraAcceso=" + idBitacoraAcceso + " ]";
    }
    
}
