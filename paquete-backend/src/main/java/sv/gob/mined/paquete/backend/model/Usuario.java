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
public class Usuario {

    private BigDecimal idUsuario;
    private BigDecimal idPersona;
    private BigDecimal idTipoUsuario;
    private String usuarioInsercion;
    private Date fechaInsercion;
    private String usuario_modificacion;
    private Date fechaModificacion;
    private Date fechaEliminacion;
    private BigDecimal estadoEliminacion;
    private String codigoDepartamento;
    private Date fechaVencimientoClave;
    private Date fechaInicioLogin;
    private Date fechaFinLogin;
    private Short rangoFechaLogin;
    private Short activo;

    public Usuario() {
    }

    public Usuario(BigDecimal idUsuario, BigDecimal idPersona, BigDecimal idTipoUsuario, String usuarioInsercion, Date fechaInsercion, String usuario_modificacion, Date fechaModificacion, Date fechaEliminacion, BigDecimal estadoEliminacion, String codigoDepartamento, Date fechaVencimientoClave, Date fechaInicioLogin, Date fechaFinLogin, Short rangoFechaLogin, Short activo) {
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
        this.idTipoUsuario = idTipoUsuario;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.usuario_modificacion = usuario_modificacion;
        this.fechaModificacion = fechaModificacion;
        this.fechaEliminacion = fechaEliminacion;
        this.estadoEliminacion = estadoEliminacion;
        this.codigoDepartamento = codigoDepartamento;
        this.fechaVencimientoClave = fechaVencimientoClave;
        this.fechaInicioLogin = fechaInicioLogin;
        this.fechaFinLogin = fechaFinLogin;
        this.rangoFechaLogin = rangoFechaLogin;
        this.activo = activo;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(BigDecimal idPersona) {
        this.idPersona = idPersona;
    }

    public BigDecimal getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(BigDecimal idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getUsuario_modificacion() {
        return usuario_modificacion;
    }

    public void setUsuario_modificacion(String usuario_modificacion) {
        this.usuario_modificacion = usuario_modificacion;
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

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public Date getFechaVencimientoClave() {
        return fechaVencimientoClave;
    }

    public void setFechaVencimientoClave(Date fechaVencimientoClave) {
        this.fechaVencimientoClave = fechaVencimientoClave;
    }

    public Date getFechaInicioLogin() {
        return fechaInicioLogin;
    }

    public void setFechaInicioLogin(Date fechaInicioLogin) {
        this.fechaInicioLogin = fechaInicioLogin;
    }

    public Date getFechaFinLogin() {
        return fechaFinLogin;
    }

    public void setFechaFinLogin(Date fechaFinLogin) {
        this.fechaFinLogin = fechaFinLogin;
    }

    public Short getRangoFechaLogin() {
        return rangoFechaLogin;
    }

    public void setRangoFechaLogin(Short rangoFechaLogin) {
        this.rangoFechaLogin = rangoFechaLogin;
    }

    public Short getActivo() {
        return activo;
    }

    public void setActivo(Short activo) {
        this.activo = activo;
    }
    
}
