/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.contratacion;

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
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultVwRptPagare",
        entities = @EntityResult(entityClass = VwRptPagare.class))
public class VwRptPagare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idRow;
    private String ciudadFirma;
    private String fechaEmision;
    private BigInteger idContrato;
    private String inicialesModalidad;
    private BigDecimal valor;
    private String descripcionRubro;
    private String numeroContrato;
    private String nombre;
    private String nombreRepresentante;
    private String domicilioRepresentante;
    private String numeroTelefono;
    private String numeroDui;
    private String numeroNit;
    private String usuarioInsercion;
    private BigInteger idResolucionAdj;
    private String razonSocial;
    private String anhoContrato;

    public VwRptPagare() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getCiudadFirma() {
        return ciudadFirma;
    }

    public void setCiudadFirma(String ciudadFirma) {
        this.ciudadFirma = ciudadFirma;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigInteger getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigInteger idContrato) {
        this.idContrato = idContrato;
    }

    public String getInicialesModalidad() {
        return inicialesModalidad;
    }

    public void setInicialesModalidad(String inicialesModalidad) {
        this.inicialesModalidad = inicialesModalidad;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getDomicilioRepresentante() {
        return domicilioRepresentante;
    }

    public void setDomicilioRepresentante(String domicilioRepresentante) {
        this.domicilioRepresentante = domicilioRepresentante;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getNumeroDui() {
        return numeroDui;
    }

    public void setNumeroDui(String numeroDui) {
        this.numeroDui = numeroDui;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public BigInteger getIdResolucionAdj() {
        return idResolucionAdj;
    }

    public void setIdResolucionAdj(BigInteger idResolucionAdj) {
        this.idResolucionAdj = idResolucionAdj;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getAnhoContrato() {
        return anhoContrato;
    }

    public void setAnhoContrato(String anhoContrato) {
        this.anhoContrato = anhoContrato;
    }

}
