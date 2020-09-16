/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author MISanchez
 */
public class Persona {

    private BigDecimal idPersona;
    private BigDecimal idGenero;
    private BigDecimal idDocLegal;
    private BigDecimal idMunicipio;
    private String primerApellido;
    private String segundoApellido;
    private String primerNombre;
    private String segundoNombre;
    private Date fechaNacimiento;
    private String numeroDui;
    private String numeroNit;
    private String numeroTelefono;
    private String numeroCelular;
    private String domicilio;
    private String profesion;
    private String acasada;
    private String email;
    private BigDecimal inactivo;
    private String usuarioCreacion;
    private String usuario;
    private String claveAcceso;
    private Date fechaInsercion;
    private String usuarioModificacion;
    private Date fechaModificacion;
    private Date fechaEliminacion;
    private BigDecimal estadoEliminacion;
    private String numTelefono3;
    private String numTelefono2;

    public Persona(BigDecimal idPersona, BigDecimal idGenero, BigDecimal idDocLegal, BigDecimal idMunicipio, String primerApellido, String segundoApellido, String primerNombre, String segundoNombre, Date fechaNacimiento, String numeroDui, String numeroNit, String numeroTelefono, String numeroCelular, String domicilio, String profesion, String acasada, String email, BigDecimal inactivo, String usuarioCreacion, String usuario, String claveAcceso, Date fechaInsercion, String usuarioModificacion, Date fechaModificacion, Date fechaEliminacion, BigDecimal estadoEliminacion, String numTelefono3, String numTelefono2) {
        this.idPersona = idPersona;
        this.idGenero = idGenero;
        this.idDocLegal = idDocLegal;
        this.idMunicipio = idMunicipio;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroDui = numeroDui;
        this.numeroNit = numeroNit;
        this.numeroTelefono = numeroTelefono;
        this.numeroCelular = numeroCelular;
        this.domicilio = domicilio;
        this.profesion = profesion;
        this.acasada = acasada;
        this.email = email;
        this.inactivo = inactivo;
        this.usuarioCreacion = usuarioCreacion;
        this.usuario = usuario;
        this.claveAcceso = claveAcceso;
        this.fechaInsercion = fechaInsercion;
        this.usuarioModificacion = usuarioModificacion;
        this.fechaModificacion = fechaModificacion;
        this.fechaEliminacion = fechaEliminacion;
        this.estadoEliminacion = estadoEliminacion;
        this.numTelefono3 = numTelefono3;
        this.numTelefono2 = numTelefono2;
    }

    public BigDecimal getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(BigDecimal idPersona) {
        this.idPersona = idPersona;
    }

    public BigDecimal getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(BigDecimal idGenero) {
        this.idGenero = idGenero;
    }

    public BigDecimal getIdDocLegal() {
        return idDocLegal;
    }

    public void setIdDocLegal(BigDecimal idDocLegal) {
        this.idDocLegal = idDocLegal;
    }

    public BigDecimal getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(BigDecimal idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getAcasada() {
        return acasada;
    }

    public void setAcasada(String acasada) {
        this.acasada = acasada;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getInactivo() {
        return inactivo;
    }

    public void setInactivo(BigDecimal inactivo) {
        this.inactivo = inactivo;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public BigDecimal getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(BigDecimal estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public String getNumTelefono3() {
        return numTelefono3;
    }

    public void setNumTelefono3(String numTelefono3) {
        this.numTelefono3 = numTelefono3;
    }

    public String getNumTelefono2() {
        return numTelefono2;
    }

    public void setNumTelefono2(String numTelefono2) {
        this.numTelefono2 = numTelefono2;
    }

}
