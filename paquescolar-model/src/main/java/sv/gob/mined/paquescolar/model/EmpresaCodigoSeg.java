/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "EMPRESA_CODIGO_SEG")
@NamedQueries({
    @NamedQuery(name = "EmpresaCodigoSeg.findAll", query = "SELECT e FROM EmpresaCodigoSeg e")})

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name = "SP_RESET_ACEPTACION",
            procedureName = "SP_RESET_ACEPTACION",
            returnsResultSet = false,
            parameters = {
                @StoredProcedureParameter(queryParameter = "P_NUMERO_NIT", name = "P_NUMERO_NIT", direction = Direction.IN, type = String.class),
                @StoredProcedureParameter(queryParameter = "P_ID_ANHO", name = "P_ID_ANHO", direction = Direction.IN, type = BigDecimal.class)
            }
    ),
    @NamedStoredProcedureQuery(
            name = "SP_RESET_ACTIVACION_USER",
            procedureName = "SP_RESET_ACTIVACION_USER",
            returnsResultSet = false,
            parameters = {
                @StoredProcedureParameter(queryParameter = "P_NUMERO_NIT", name = "P_NUMERO_NIT", direction = Direction.IN, type = String.class)
            }
    )
})
public class EmpresaCodigoSeg implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EMP_COD_SEG")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMP_COD_SEG")
    @SequenceGenerator(name = "SEQ_EMP_COD_SEG", sequenceName = "SEQ_EMP_COD_SEG", allocationSize = 1, initialValue = 1)
    private Long idEmpCodSeg;
    @Column(name = "ID_EMPRESA")
    private BigDecimal idEmpresa;

    @Basic(optional = false)
    @Column(name = "CODIGO_SEGURIDAD")
    private String codigoSeguridad;
    @Basic(optional = false)
    @Column(name = "USUARIO_ACTIVADO")
    private short usuarioActivado;
    @Column(name = "FECHA_ACTIVACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActivacion;
    @Column(name = "TOKEN_ACTIVACION")
    private String tokenActivacion;

    public EmpresaCodigoSeg() {
    }

    public EmpresaCodigoSeg(Long idEmpCodSeg) {
        this.idEmpCodSeg = idEmpCodSeg;
    }

    public BigDecimal getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigDecimal idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public EmpresaCodigoSeg(Long idEmpCodSeg, String codigoSeguridad, short usuarioActivado) {
        this.idEmpCodSeg = idEmpCodSeg;
        this.codigoSeguridad = codigoSeguridad;
        this.usuarioActivado = usuarioActivado;
    }

    public Long getIdEmpCodSeg() {
        return idEmpCodSeg;
    }

    public void setIdEmpCodSeg(Long idEmpCodSeg) {
        this.idEmpCodSeg = idEmpCodSeg;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public short getUsuarioActivado() {
        return usuarioActivado;
    }

    public void setUsuarioActivado(short usuarioActivado) {
        this.usuarioActivado = usuarioActivado;
    }

    public Date getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(Date fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public String getTokenActivacion() {
        return tokenActivacion;
    }

    public void setTokenActivacion(String tokenActivacion) {
        this.tokenActivacion = tokenActivacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpCodSeg != null ? idEmpCodSeg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaCodigoSeg)) {
            return false;
        }
        EmpresaCodigoSeg other = (EmpresaCodigoSeg) object;
        if ((this.idEmpCodSeg == null && other.idEmpCodSeg != null) || (this.idEmpCodSeg != null && !this.idEmpCodSeg.equals(other.idEmpCodSeg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.EmpresaCodigoSeg[ idEmpCodSeg=" + idEmpCodSeg + " ]";
    }

}
