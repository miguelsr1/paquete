/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
@SqlResultSetMapping(name = "defaultDetalleContratacionPorItemDto",
        entities = @EntityResult(entityClass = DetalleContratacionPorItemDto.class))
public class DetalleContratacionPorItemDto implements Serializable {

    @Id
    private BigDecimal idContrato;
    private String codigoEntidad;
    private String nombre;
    private String codigoDepartamento;
    private String nombreDepartamento;
    private String codigoMunicipio;
    private String nombreMunicipio;
    private BigInteger cantidadContratada;
    private BigDecimal montoContratado;
    private String numeroNit;
    private String razonSocial;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
    private String item6;
    private String item7;
    private String item8;
    private String item9;
    private String item10;
    private String item11;
    private String item12;
    private String item13;
    private String item21;
    private String item22;
    private String item23;
    private String item31;
    private String item32;
    private String item33;
    private String item41;
    private String item42;
    private String item43;
    private String item51;
    private String item52;

    public DetalleContratacionPorItemDto() {
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

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public BigInteger getCantidadContratada() {
        return cantidadContratada;
    }

    public void setCantidadContratada(BigInteger cantidadContratada) {
        this.cantidadContratada = cantidadContratada;
    }

    public BigDecimal getMontoContratado() {
        return montoContratado;
    }

    public void setMontoContratado(BigDecimal montoContratado) {
        this.montoContratado = montoContratado;
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

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getItem5() {
        return item5;
    }

    public void setItem5(String item5) {
        this.item5 = item5;
    }

    public String getItem6() {
        return item6;
    }

    public void setItem6(String item6) {
        this.item6 = item6;
    }

    public String getItem7() {
        return item7;
    }

    public void setItem7(String item7) {
        this.item7 = item7;
    }

    public String getItem8() {
        return item8;
    }

    public void setItem8(String item8) {
        this.item8 = item8;
    }

    public String getItem9() {
        return item9;
    }

    public void setItem9(String item9) {
        this.item9 = item9;
    }

    public String getItem10() {
        return item10;
    }

    public void setItem10(String item10) {
        this.item10 = item10;
    }

    public String getItem11() {
        return item11;
    }

    public void setItem11(String item11) {
        this.item11 = item11;
    }

    public String getItem12() {
        return item12;
    }

    public void setItem12(String item12) {
        this.item12 = item12;
    }

    public String getItem13() {
        return item13;
    }

    public void setItem13(String item13) {
        this.item13 = item13;
    }

    public String getItem21() {
        return item21;
    }

    public void setItem21(String item21) {
        this.item21 = item21;
    }

    public String getItem22() {
        return item22;
    }

    public void setItem22(String item22) {
        this.item22 = item22;
    }

    public String getItem23() {
        return item23;
    }

    public void setItem23(String item23) {
        this.item23 = item23;
    }

    public String getItem31() {
        return item31;
    }

    public void setItem31(String item31) {
        this.item31 = item31;
    }

    public String getItem32() {
        return item32;
    }

    public void setItem32(String item32) {
        this.item32 = item32;
    }

    public String getItem33() {
        return item33;
    }

    public void setItem33(String item33) {
        this.item33 = item33;
    }

    public String getItem41() {
        return item41;
    }

    public void setItem41(String item41) {
        this.item41 = item41;
    }

    public String getItem42() {
        return item42;
    }

    public void setItem42(String item42) {
        this.item42 = item42;
    }

    public String getItem43() {
        return item43;
    }

    public void setItem43(String item43) {
        this.item43 = item43;
    }

    public String getItem51() {
        return item51;
    }

    public void setItem51(String item51) {
        this.item51 = item51;
    }

    public String getItem52() {
        return item52;
    }

    public void setItem52(String item52) {
        this.item52 = item52;
    }
}