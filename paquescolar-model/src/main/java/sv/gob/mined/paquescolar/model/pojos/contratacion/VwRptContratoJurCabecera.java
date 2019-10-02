/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultVwRptContratoJurCabecera",
        entities = @EntityResult(entityClass = VwRptContratoJurCabecera.class))
public class VwRptContratoJurCabecera implements Serializable {

    @Id
    private BigDecimal idRow;
    private BigDecimal valor;
    private BigDecimal total;
    private BigInteger cantidad;
    private BigDecimal totalBachillerato;
    private BigDecimal totalParBasica;
    private String numeroContrato;
    private String razonSocial;
    private String descripcionRubro;
    private String nombreMiembro;
    private String inicialesModalidad;
    private String nombre;
    private BigInteger plazoPrevistoEntrega;
    private String ciudadFirma;
    private String direccionCe;
    private String telefonoCe;
    private String faxCe;
    private String direccionEmp;
    private String telefonoEmp;
    private String celularEmp;
    private String faxEmp;
    private String numeroNit;
    private String usuarioInsercion;
    private String anhoContrato;
    private String fechaEmision;
    private String nombrePresentante;
    private String codigoEntidad;
    private BigInteger idContrato;
    private BigInteger distribuidor;
    private BigInteger mostrarLeyenda;
    private Boolean isBachillerato = false;
    private Boolean isParBasica = false;
    private String nombreDepartamento;
    private String nombreMunicipio;
    private String numeroDui;
    private String nitRepresentante;

    @Transient
    private List<DetalleItemDto> lstDetalleItems;
    @Transient
    private List<DetalleItemDto> lstDetalleItemsBac;

    public VwRptContratoJurCabecera() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public String getNombreMiembro() {
        return nombreMiembro;
    }

    public void setNombreMiembro(String nombreMiembro) {
        this.nombreMiembro = nombreMiembro;
    }

    public String getInicialesModalidad() {
        return inicialesModalidad;
    }

    public void setInicialesModalidad(String inicialesModalidad) {
        this.inicialesModalidad = inicialesModalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getPlazoPrevistoEntrega() {
        return plazoPrevistoEntrega;
    }

    public void setPlazoPrevistoEntrega(BigInteger plazoPrevistoEntrega) {
        this.plazoPrevistoEntrega = plazoPrevistoEntrega;
    }

    public String getCiudadFirma() {
        return ciudadFirma;
    }

    public void setCiudadFirma(String ciudadFirma) {
        this.ciudadFirma = ciudadFirma;
    }

    public String getDireccionCe() {
        return direccionCe;
    }

    public void setDireccionCe(String direccionCe) {
        this.direccionCe = direccionCe;
    }

    public String getTelefonoCe() {
        return telefonoCe;
    }

    public void setTelefonoCe(String telefonoCe) {
        this.telefonoCe = telefonoCe;
    }

    public String getFaxCe() {
        return faxCe;
    }

    public void setFaxCe(String faxCe) {
        this.faxCe = faxCe;
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

    public String getFaxEmp() {
        return faxEmp;
    }

    public void setFaxEmp(String faxEmp) {
        this.faxEmp = faxEmp;
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

    public String getAnhoContrato() {
        return anhoContrato;
    }

    public void setAnhoContrato(String anhoContrato) {
        this.anhoContrato = anhoContrato;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNombrePresentante() {
        return nombrePresentante;
    }

    public void setNombrePresentante(String nombrePresentante) {
        this.nombrePresentante = nombrePresentante;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public BigInteger getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigInteger idContrato) {
        this.idContrato = idContrato;
    }

    public BigInteger getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(BigInteger distribuidor) {
        this.distribuidor = distribuidor;
    }

    public BigInteger getMostrarLeyenda() {
        return mostrarLeyenda;
    }

    public void setMostrarLeyenda(BigInteger mostrarLeyenda) {
        this.mostrarLeyenda = mostrarLeyenda;
    }

    public List<DetalleItemDto> getLstDetalleItems() {
        if (lstDetalleItems == null) {
            lstDetalleItems = new ArrayList();
        }
        return lstDetalleItems;
    }

    public void setLstDetalleItems(List<DetalleItemDto> lstDetalleItems) {
        this.lstDetalleItems = lstDetalleItems;
    }

    public List<DetalleItemDto> getLstDetalleItemsBac() {
        if (lstDetalleItemsBac == null) {
            lstDetalleItemsBac = new ArrayList();
        }
        return lstDetalleItemsBac;
    }

    public void setLstDetalleItemsBac(List<DetalleItemDto> lstDetalleItemsBac) {
        this.lstDetalleItemsBac = lstDetalleItemsBac;
    }

    public BigDecimal getTotal() {
        total = BigDecimal.ZERO;
        for (DetalleItemDto vwRptDetItems : getLstDetalleItems()) {
            total = total.add(vwRptDetItems.getSubTotal());
        }
        for (DetalleItemDto vwRptDetItems : getLstDetalleItemsBac()) {
            total = total.add(vwRptDetItems.getSubTotal());
        }

        return total;
    }

    public BigInteger getCantidad() {
        cantidad = BigInteger.ZERO;
        for (DetalleItemDto vwRptDetItems : getLstDetalleItems()) {
            cantidad = cantidad.add(vwRptDetItems.getCantidad());
        }
        for (DetalleItemDto vwRptDetItems : getLstDetalleItemsBac()) {
            cantidad = cantidad.add(vwRptDetItems.getCantidad());
        }

        return cantidad;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalBachillerato() {
        totalBachillerato = BigDecimal.ZERO;
        for (DetalleItemDto vwRptDetItems : getLstDetalleItemsBac()) {
            totalBachillerato = totalBachillerato.add(vwRptDetItems.getSubTotal());
        }
        isBachillerato = totalBachillerato.compareTo(BigDecimal.ZERO) != 0;
        return totalBachillerato;
    }

    public void setTotalBachillerato(BigDecimal totalBachillerato) {
        this.totalBachillerato = totalBachillerato;
    }

    public BigDecimal getTotalParBasica() {
        totalParBasica = BigDecimal.ZERO;
        for (DetalleItemDto vwRptDetItems : getLstDetalleItems()) {
            totalParBasica = totalParBasica.add(vwRptDetItems.getSubTotal());
        }
        isParBasica = totalParBasica.compareTo(BigDecimal.ZERO) != 0;
        return totalParBasica;
    }

    public void setTotalParBasica(BigDecimal totalParBasica) {
        this.totalParBasica = totalParBasica;
    }

    public Boolean getIsBachillerato() {
        return isBachillerato;
    }

    public void setIsBachillerato(Boolean isBachillerato) {
        this.isBachillerato = isBachillerato;
    }

    public Boolean getIsParBasica() {
        return isParBasica;
    }

    public void setIsParBasica(Boolean isParBasica) {
        this.isParBasica = isParBasica;
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

    public String getNumeroDui() {
        return numeroDui;
    }

    public void setNumeroDui(String numeroDui) {
        this.numeroDui = numeroDui;
    }

    public String getNitRepresentante() {
        return nitRepresentante;
    }

    public void setNitRepresentante(String nitRepresentante) {
        this.nitRepresentante = nitRepresentante;
    }
}
