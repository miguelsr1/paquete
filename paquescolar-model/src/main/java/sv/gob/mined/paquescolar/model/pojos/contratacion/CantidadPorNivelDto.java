/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
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
@SqlResultSetMapping(name = "defaultCantidadPorNivelDto",
        entities = @EntityResult(entityClass = CantidadPorNivelDto.class))
public class CantidadPorNivelDto implements Serializable {

    @Id
    private BigDecimal idRow;
    private String codigoEntidad;
    private String nombreCe;
    private String nombreDepartamento;
    private String nombreMunicipio;

    private BigDecimal inicial2Fem;
    private BigDecimal inicial2Mas;
    private BigDecimal inicial3Fem;
    private BigDecimal inicial3Mas;

    private BigDecimal parFem;
    private BigDecimal parMas;
    private BigDecimal grado1Fem;
    private BigDecimal grado1Mas;
    private BigDecimal grado2Fem;
    private BigDecimal grado2Mas;
    private BigDecimal grado3Fem;
    private BigDecimal grado3Mas;
    private BigDecimal grado4Fem;
    private BigDecimal grado4Mas;
    private BigDecimal grado5Fem;
    private BigDecimal grado5Mas;
    private BigDecimal grado6Fem;
    private BigDecimal grado6Mas;
    private BigDecimal grado7Fem;
    private BigDecimal grado7Mas;
    private BigDecimal grado8Fem;
    private BigDecimal grado8Mas;
    private BigDecimal grado9Fem;
    private BigDecimal grado9Mas;

    private BigDecimal fleCiclo3Fem;
    private BigDecimal fleCiclo3Mas;

    private BigDecimal media1Fem;
    private BigDecimal media1Mas;
    private BigDecimal media2Fem;
    private BigDecimal media2Mas;
    private BigDecimal media3Fem;
    private BigDecimal media3Mas;

    private BigDecimal fleMediaFem;
    private BigDecimal fleMediaMas;

    public CantidadPorNivelDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public BigDecimal getParFem() {
        return parFem;
    }

    public void setParFem(BigDecimal parFem) {
        this.parFem = parFem;
    }

    public BigDecimal getParMas() {
        return parMas;
    }

    public void setParMas(BigDecimal parMas) {
        this.parMas = parMas;
    }

    public BigDecimal getGrado1Fem() {
        return grado1Fem;
    }

    public void setGrado1Fem(BigDecimal grado1Fem) {
        this.grado1Fem = grado1Fem;
    }

    public BigDecimal getGrado1Mas() {
        return grado1Mas;
    }

    public void setGrado1Mas(BigDecimal grado1Mas) {
        this.grado1Mas = grado1Mas;
    }

    public BigDecimal getGrado2Fem() {
        return grado2Fem;
    }

    public void setGrado2Fem(BigDecimal grado2Fem) {
        this.grado2Fem = grado2Fem;
    }

    public BigDecimal getGrado2Mas() {
        return grado2Mas;
    }

    public void setGrado2Mas(BigDecimal grado2Mas) {
        this.grado2Mas = grado2Mas;
    }

    public BigDecimal getGrado3Fem() {
        return grado3Fem;
    }

    public void setGrado3Fem(BigDecimal grado3Fem) {
        this.grado3Fem = grado3Fem;
    }

    public BigDecimal getGrado3Mas() {
        return grado3Mas;
    }

    public void setGrado3Mas(BigDecimal grado3Mas) {
        this.grado3Mas = grado3Mas;
    }

    public BigDecimal getGrado4Fem() {
        return grado4Fem;
    }

    public void setGrado4Fem(BigDecimal grado4Fem) {
        this.grado4Fem = grado4Fem;
    }

    public BigDecimal getGrado4Mas() {
        return grado4Mas;
    }

    public void setGrado4Mas(BigDecimal grado4Mas) {
        this.grado4Mas = grado4Mas;
    }

    public BigDecimal getGrado5Fem() {
        return grado5Fem;
    }

    public void setGrado5Fem(BigDecimal grado5Fem) {
        this.grado5Fem = grado5Fem;
    }

    public BigDecimal getGrado5Mas() {
        return grado5Mas;
    }

    public void setGrado5Mas(BigDecimal grado5Mas) {
        this.grado5Mas = grado5Mas;
    }

    public BigDecimal getGrado6Fem() {
        return grado6Fem;
    }

    public void setGrado6Fem(BigDecimal grado6Fem) {
        this.grado6Fem = grado6Fem;
    }

    public BigDecimal getGrado6Mas() {
        return grado6Mas;
    }

    public void setGrado6Mas(BigDecimal grado6Mas) {
        this.grado6Mas = grado6Mas;
    }

    public BigDecimal getGrado7Fem() {
        return grado7Fem;
    }

    public void setGrado7Fem(BigDecimal grado7Fem) {
        this.grado7Fem = grado7Fem;
    }

    public BigDecimal getGrado7Mas() {
        return grado7Mas;
    }

    public void setGrado7Mas(BigDecimal grado7Mas) {
        this.grado7Mas = grado7Mas;
    }

    public BigDecimal getGrado8Fem() {
        return grado8Fem;
    }

    public void setGrado8Fem(BigDecimal grado8Fem) {
        this.grado8Fem = grado8Fem;
    }

    public BigDecimal getGrado8Mas() {
        return grado8Mas;
    }

    public void setGrado8Mas(BigDecimal grado8Mas) {
        this.grado8Mas = grado8Mas;
    }

    public BigDecimal getGrado9Fem() {
        return grado9Fem;
    }

    public void setGrado9Fem(BigDecimal grado9Fem) {
        this.grado9Fem = grado9Fem;
    }

    public BigDecimal getGrado9Mas() {
        return grado9Mas;
    }

    public void setGrado9Mas(BigDecimal grado9Mas) {
        this.grado9Mas = grado9Mas;
    }

    public BigDecimal getMedia1Fem() {
        return media1Fem;
    }

    public void setMedia1Fem(BigDecimal media1Fem) {
        this.media1Fem = media1Fem;
    }

    public BigDecimal getMedia1Mas() {
        return media1Mas;
    }

    public void setMedia1Mas(BigDecimal media1Mas) {
        this.media1Mas = media1Mas;
    }

    public BigDecimal getMedia2Fem() {
        return media2Fem;
    }

    public void setMedia2Fem(BigDecimal media2Fem) {
        this.media2Fem = media2Fem;
    }

    public BigDecimal getMedia2Mas() {
        return media2Mas;
    }

    public void setMedia2Mas(BigDecimal media2Mas) {
        this.media2Mas = media2Mas;
    }

    public BigDecimal getMedia3Fem() {
        return media3Fem;
    }

    public void setMedia3Fem(BigDecimal media3Fem) {
        this.media3Fem = media3Fem;
    }

    public BigDecimal getMedia3Mas() {
        return media3Mas;
    }

    public void setMedia3Mas(BigDecimal media3Mas) {
        this.media3Mas = media3Mas;
    }

    public String getNombreCe() {
        return nombreCe;
    }

    public void setNombreCe(String nombreCe) {
        this.nombreCe = nombreCe;
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

    public BigDecimal getInicial2Fem() {
        return inicial2Fem;
    }

    public void setInicial2Fem(BigDecimal inicial2Fem) {
        this.inicial2Fem = inicial2Fem;
    }

    public BigDecimal getInicial2Mas() {
        return inicial2Mas;
    }

    public void setInicial2Mas(BigDecimal inicial2Mas) {
        this.inicial2Mas = inicial2Mas;
    }

    public BigDecimal getInicial3Fem() {
        return inicial3Fem;
    }

    public void setInicial3Fem(BigDecimal inicial3Fem) {
        this.inicial3Fem = inicial3Fem;
    }

    public BigDecimal getInicial3Mas() {
        return inicial3Mas;
    }

    public void setInicial3Mas(BigDecimal inicial3Mas) {
        this.inicial3Mas = inicial3Mas;
    }

    public BigDecimal getFleCiclo3Fem() {
        return fleCiclo3Fem;
    }

    public void setFleCiclo3Fem(BigDecimal fleCiclo3Fem) {
        this.fleCiclo3Fem = fleCiclo3Fem;
    }

    public BigDecimal getFleCiclo3Mas() {
        return fleCiclo3Mas;
    }

    public void setFleCiclo3Mas(BigDecimal fleCiclo3Mas) {
        this.fleCiclo3Mas = fleCiclo3Mas;
    }

    public BigDecimal getFleMediaFem() {
        return fleMediaFem;
    }

    public void setFleMediaFem(BigDecimal fleMediaFem) {
        this.fleMediaFem = fleMediaFem;
    }

    public BigDecimal getFleMediaMas() {
        return fleMediaMas;
    }

    public void setFleMediaMas(BigDecimal fleMediaMas) {
        this.fleMediaMas = fleMediaMas;
    }

}
