/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.credito;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RCeron
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultDatosProveedoresFinanDto",
        entities = @EntityResult(entityClass = DatosProveedoresFinanDto.class))
public class DatosProveedoresFinanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idRow;
    private BigInteger idDetalle;
    private String descripCredAct;
    private String numeroNit;
    private String razonSocial;
    private String nombreEntFinan;
    private String codEntFinanciera;
    private String nombreDepartamento;
    private String codigoDepartamento;
    private String codigoEntidad;
    private String nombre;
    private String refPrestamo;
    private BigDecimal montoCredito;
    private BigInteger creditoActivo;
    private String descripcionRubro;
    private BigInteger idRubroInteres;
    private BigInteger idProceso;
    private BigDecimal montoContrato;
    private BigDecimal montoModificativa;

    public DatosProveedoresFinanDto() {
    }

    public BigDecimal getMontoModificativa() {
        return montoModificativa;
    }

    public void setMontoModificativa(BigDecimal montoModificativa) {
        this.montoModificativa = montoModificativa;
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreEntFinan() {
        return nombreEntFinan;
    }

    public void setNombreEntFinan(String nombreEntFinan) {
        this.nombreEntFinan = nombreEntFinan;
    }

    public String getCodEntFinanciera() {
        return codEntFinanciera;
    }

    public void setCodEntFinanciera(String codEntFinanciera) {
        this.codEntFinanciera = codEntFinanciera;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRefPrestamo() {
        return refPrestamo;
    }

    public void setRefPrestamo(String refPrestamo) {
        this.refPrestamo = refPrestamo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getMontoCredito() {
        return montoCredito;
    }

    public void setMontoCredito(BigDecimal montoCredito) {
        this.montoCredito = montoCredito;
    }

    public BigInteger getCreditoActivo() {
        return creditoActivo;
    }

    public void setCreditoActivo(BigInteger creditoActivo) {
        this.creditoActivo = creditoActivo;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public BigInteger getIdRubroInteres() {
        return idRubroInteres;
    }

    public void setIdRubroInteres(BigInteger idRubroInteres) {
        this.idRubroInteres = idRubroInteres;
    }

    public BigInteger getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(BigInteger idProceso) {
        this.idProceso = idProceso;
    }

    public BigDecimal getMontoContrato() {
        return montoContrato;
    }

    public void setMontoContrato(BigDecimal montoContrato) {
        this.montoContrato = montoContrato;
    }

    public String getDescripCredAct() {
        return descripCredAct;
    }

    public void setDescripCredAct(String descripCredAct) {
        this.descripCredAct = descripCredAct;
    }

    public BigInteger getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(BigInteger idDetalle) {
        this.idDetalle = idDetalle;
    }
}
