/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author misanchez
 */
@Entity
@SqlResultSetMapping(name = "vwRptProveedoresContratadosDto",
        entities = @EntityResult(entityClass = VwRptProveedoresContratadosDto.class))
public class VwRptProveedoresContratadosDto implements Serializable {

    @Id
    private BigDecimal idRow;
    private BigDecimal idContrato;
    private String nombreDepartamentoCe;
    private String nombreMunicipioCe;
    private String codigoEntidad;
    private String nombreCe;
    private String direccionCe;
    private String rubro;
    private BigDecimal cantidadContrato;
    private BigDecimal montoContrato;
    private String nombreDepartamentoEmp;
    private String nombreMunicipioEmp;
    private String razonSocial;
    private String direccionEmp;
    private String telefonoEmp;
    private String celularEmp;
    private String numeroNit;

    public VwRptProveedoresContratadosDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public BigDecimal getCantidadContrato() {
        return cantidadContrato;
    }

    public void setCantidadContrato(BigDecimal cantidadContrato) {
        this.cantidadContrato = cantidadContrato;
    }

    public String getNombreDepartamentoCe() {
        return nombreDepartamentoCe;
    }

    public void setNombreDepartamentoCe(String nombreDepartamentoCe) {
        this.nombreDepartamentoCe = nombreDepartamentoCe;
    }

    public String getNombreDepartamentoEmp() {
        return nombreDepartamentoEmp;
    }

    public void setNombreDepartamentoEmp(String nombreDepartamentoEmp) {
        this.nombreDepartamentoEmp = nombreDepartamentoEmp;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public BigDecimal getMontoContrato() {
        return montoContrato;
    }

    public void setMontoContrato(BigDecimal montoContrato) {
        this.montoContrato = montoContrato;
    }

    public String getNombreMunicipioCe() {
        return nombreMunicipioCe;
    }

    public void setNombreMunicipioCe(String nombreMunicipioCe) {
        this.nombreMunicipioCe = nombreMunicipioCe;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getNombreCe() {
        return nombreCe;
    }

    public void setNombreCe(String nombreCe) {
        this.nombreCe = nombreCe;
    }

    public String getDireccionCe() {
        return direccionCe;
    }

    public void setDireccionCe(String direccionCe) {
        this.direccionCe = direccionCe;
    }

    public String getNombreMunicipioEmp() {
        return nombreMunicipioEmp;
    }

    public void setNombreMunicipioEmp(String nombreMunicipioEmp) {
        this.nombreMunicipioEmp = nombreMunicipioEmp;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccionEmp() {
        return direccionEmp;
    }

    public void setDireccionEmp(String direccionEmp) {
        this.direccionEmp = direccionEmp;
    }

    public String getTelefonoEmp() {
        return telefonoEmp;
    }

    public void setTelefonoEmp(String telefonoEmp) {
        this.telefonoEmp = telefonoEmp;
    }

    public String getCelularEmp() {
        return celularEmp;
    }

    public void setCelularEmp(String celularEmp) {
        this.celularEmp = celularEmp;
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
    }
}
