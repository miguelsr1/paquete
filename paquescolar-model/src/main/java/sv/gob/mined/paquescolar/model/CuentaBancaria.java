/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

/**
 *
 * @author misanchez
 */
/*@NamedStoredProcedureQuery(
        name = "SP_GENERAR_REQUERIMIENTO",
        procedureName = "SP_GENERAR_REQUERIMIENTO",
        returnsResultSet = false,
        parameters = {
            @StoredProcedureParameter(queryParameter = "param1", name = "ANHO", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param2", name = "COD_DEPA", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param3", name = "COD_ENT", direction = Direction.IN, type = Integer.class),
            @StoredProcedureParameter(queryParameter = "param4", name = "NUM_CUENTA", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param5", name = "CONCEP", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param6", name = "COMPON", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param7", name = "LINE", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param8", name = "USU", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param9", name = "TIENE_CRE", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param10", name = "MONTO_TOT", direction = Direction.IN, type = BigDecimal.class),
            @StoredProcedureParameter(queryParameter = "param11", name = "ID_NIVEL", direction = Direction.IN, type = Integer.class),
            @StoredProcedureParameter(queryParameter = "param12", name = "ID_DEP_PRO", direction = Direction.IN, type = Integer.class)
        }
)*/
@NamedStoredProcedureQuery(
        name = "SP_GENERAR_REQ_PRE_CARGA",
        procedureName = "SP_GENERAR_REQ_PRE_CARGA",
        returnsResultSet = false,
        parameters = {
            @StoredProcedureParameter(queryParameter = "param1", name = "ANHO", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param2", name = "COD_DEPA", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param3", name = "COD_ENT", direction = Direction.IN, type = Integer.class),
            @StoredProcedureParameter(queryParameter = "param4", name = "NUM_CUENTA", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param5", name = "CONCEP", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param6", name = "COMPON", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param7", name = "LINE", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param8", name = "USU", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param9", name = "TIENE_CRE", direction = Direction.IN, type = String.class),
            @StoredProcedureParameter(queryParameter = "param10", name = "MONTO_TOT", direction = Direction.IN, type = BigDecimal.class),
            @StoredProcedureParameter(queryParameter = "param11", name = "ID_NIVEL", direction = Direction.IN, type = Integer.class),
            @StoredProcedureParameter(queryParameter = "param12", name = "ID_DEP_PRO", direction = Direction.IN, type = Integer.class)
        }
)

@Entity
@Table(name = "CUENTA_BANCARIA")
@XmlRootElement
public class CuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CUENTA")
    private Integer idCuenta;
    @Column(name = "CODIGO_DEPARTAMENTO")
    private String codigoDepartamento;
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    @JoinColumn(name = "ID_BANCO", referencedColumnName = "ID_BANCO")
    @ManyToOne
    private Bancos idBanco;
    @Basic(optional = false)
    @Column(name = "ACTIVO")
    private short activo;

    public CuentaBancaria() {
    }

    public CuentaBancaria(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numerCuenta) {
        this.numeroCuenta = numerCuenta;
    }

    public Bancos getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Bancos idBanco) {
        this.idBanco = idBanco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuenta != null ? idCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuentaBancaria)) {
            return false;
        }
        CuentaBancaria other = (CuentaBancaria) object;
        return !((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta)));
    }

    @Override
    public String toString() {
        return numeroCuenta;
    }

    public short getActivo() {
        return activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }
}
