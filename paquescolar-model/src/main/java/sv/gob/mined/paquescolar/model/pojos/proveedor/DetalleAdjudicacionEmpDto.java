/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.proveedor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez comentario: Antes de 05/09/2018 esta clase se llamó
 * VwDetalleAdjudicacionEmpDto
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultDetAdjudicaciones",
        entities = @EntityResult(entityClass = DetalleAdjudicacionEmpDto.class))
public class DetalleAdjudicacionEmpDto implements Serializable {

    @Id
    private BigDecimal idRow;
    private String nombreDepartamento;
    private String nombreMunicipio;
    private String codigoEntidad;
    private String nombre;
    private String descripcionNivel;
    private String rubro;
    private String nombreProducto;
    private BigDecimal cantidad;
    private BigDecimal monto;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaApertura;
    @Transient
    private short estadoEliminacion = 0;

    public DetalleAdjudicacionEmpDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
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

    public String getDescripcionNivel() {
        return descripcionNivel;
    }

    public void setDescripcionNivel(String descripcionNivel) {
        this.descripcionNivel = descripcionNivel;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }
}
