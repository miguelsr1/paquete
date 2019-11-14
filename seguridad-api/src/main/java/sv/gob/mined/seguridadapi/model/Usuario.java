/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridadapi.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author misanchez
 */
public class Usuario implements Serializable {

    private String login;
    private String activo;
    private String apellidos;
    private String cambiarClave;
    private String claveAcceso;
    private String correoElectronico;
    private String dui;
    private Date fechaExpiracion;
    private String nombres;
    private String sexo;
    private String usuarioBloqueado;
    private String tipoUsuario;
    private String codigoEmpledo;
    private String codigoEntidad;
    private String codigoDepartamento;

    public Usuario() {
    }

    public Usuario(String login) {
        this.login = login;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCambiarClave() {
        return cambiarClave;
    }

    public void setCambiarClave(String cambiarClave) {
        this.cambiarClave = cambiarClave;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getUsuarioBloqueado() {
        return usuarioBloqueado;
    }

    public void setUsuarioBloqueado(String usuarioBloqueado) {
        this.usuarioBloqueado = usuarioBloqueado;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCodigoEmpledo() {
        return codigoEmpledo;
    }

    public void setCodigoEmpledo(String codigoEmpledo) {
        this.codigoEmpledo = codigoEmpledo;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    @Override
    public String toString() {
        return "Usuario{" + "login=" + login + '}';
    }

}
