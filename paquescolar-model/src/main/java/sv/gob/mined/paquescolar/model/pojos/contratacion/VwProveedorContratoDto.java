/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.mined.paquescolar.model.pojos.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

/**
 *
 * @author misanchez
 */
@Entity
@NamedNativeQuery(
        name = "defaultVwProveedorContratoDto",
        query = "select nombre_departamento as nombreDepartamento, codigo_entidad as codigoEntidad, nombre, monto, razon_social as razonSocial, numero_nit as numeroNit from VW_PROVEEDOR_CONTRATO where codigo_entidad = ?1 and id_proceso_adq = ?2 and id_rubro_adq = ?3",
        resultClass = VwProveedorContratoDto.class)
public class VwProveedorContratoDto implements Serializable {

    @Id
    private String numeroNit;
    private String nombreDepartamento;
    private String codigoEntidad;
    private String nombre;
    private BigDecimal monto;
    private String razonSocial;

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

}
