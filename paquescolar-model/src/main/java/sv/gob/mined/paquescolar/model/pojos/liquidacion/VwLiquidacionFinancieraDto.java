package sv.gob.mined.paquescolar.model.pojos.liquidacion;

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
        name = "defaultVwLiquidacionFinancieraDto",
        query = "SELECT id_contrato idContrato, formato_requerimiento formatoRequerimiento, monto_total montoRequerimiento,nombre_departamento nombreDepartamento,nombre_municipio nombreMunicipio,rubro, codigo_entidad codigoEntidad,nombre,proceso, monto_liquidado montoLiquidado, monto_pendiente montoPendiente, estado_liquidacion estadoLiquidacion,usuario_insercion usuario FROM vw_liquidacion_financiera WHERE id_det_proceso_adq = ?1 and codigo_departamento = ?2",
        resultClass = VwLiquidacionFinancieraDto.class)
public class VwLiquidacionFinancieraDto implements Serializable {

    @Id
    private BigDecimal idContrato;
    private String formatoRequerimiento;
    private BigDecimal montoRequerimiento;
    private String nombreDepartamento;
    private String nombreMunicipio;
    private String rubro;
    private String codigoEntidad;
    private String nombre;
    private String proceso;
    private BigDecimal montoLiquidado;
    private BigDecimal montoPendiente;
    private String estadoLiquidacion;
    private String usuario;

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
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

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getEstadoLiquidacion() {
        return estadoLiquidacion;
    }

    public void setEstadoLiquidacion(String estadoLiquidacion) {
        this.estadoLiquidacion = estadoLiquidacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFormatoRequerimiento() {
        return formatoRequerimiento;
    }

    public void setFormatoRequerimiento(String formatoRequerimiento) {
        this.formatoRequerimiento = formatoRequerimiento;
    }

    public BigDecimal getMontoRequerimiento() {
        return montoRequerimiento;
    }

    public void setMontoRequerimiento(BigDecimal montoRequerimiento) {
        this.montoRequerimiento = montoRequerimiento;
    }

    public BigDecimal getMontoLiquidado() {
        return montoLiquidado;
    }

    public void setMontoLiquidado(BigDecimal montoLiquidado) {
        this.montoLiquidado = montoLiquidado;
    }

    public BigDecimal getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(BigDecimal montoPendiente) {
        this.montoPendiente = montoPendiente;
    }

}
