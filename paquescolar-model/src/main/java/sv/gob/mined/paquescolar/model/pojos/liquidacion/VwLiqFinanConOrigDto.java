package sv.gob.mined.paquescolar.model.pojos.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Temporal;

/**
 *
 * @author misanchez
 */
@Entity
@NamedNativeQuery(
        name = "defaultVwLiqFinanConOrigDto",
        query = "select id_det_proceso_adq as idDetProcesoAdq, id_contrato as idContrato, codigo_entidad as codigoEntidad, nombre, nombre_departamento as nombreDepartamento, nombre_municipio as nombreMunicipio, numero_nit as numeroNit, razon_social as razonSocial, numero_contrato as numeroContrato, fecha_emision as fechaEmision, modificativa, cantidad_contrato as cantidadContrato, monto_contrato as montoContrato from vw_liq_finan_con_orig where id_contrato = ?1",
        resultClass = VwLiqFinanConOrigDto.class)
public class VwLiqFinanConOrigDto implements Serializable {

    @Id
    private BigDecimal idContrato;
    private Integer idDetProcesoAdq;

    public Integer getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(Integer idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }
    private String codigoEntidad;
    private String nombre;
    private String nombreDepartamento;
    private String nombreMunicipio;
    private String numeroNit;
    private String razonSocial;
    private String numeroContrato;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEmision;
    private Short modificativa;
    private BigDecimal cantidadContrato;
    private BigDecimal montoContrato;

    public VwLiqFinanConOrigDto() {
    }

    public BigDecimal getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigDecimal idContrato) {
        this.idContrato = idContrato;
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

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
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

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Short getModificativa() {
        return modificativa;
    }

    public void setModificativa(Short modificativa) {
        this.modificativa = modificativa;
    }

    public BigDecimal getCantidadContrato() {
        return cantidadContrato;
    }

    public void setCantidadContrato(BigDecimal cantidadContrato) {
        this.cantidadContrato = cantidadContrato;
    }

    public BigDecimal getMontoContrato() {
        return montoContrato;
    }

    public void setMontoContrato(BigDecimal montoContrato) {
        this.montoContrato = montoContrato;
    }

}
