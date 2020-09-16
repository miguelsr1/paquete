/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.model;

import java.math.BigDecimal;

/**
 *
 * @author misanchez
 */
public class Roles {
    
    private BigDecimal id_usuario;
    private String rol;

    public Roles() {
    }

    public Roles(BigDecimal id_usuario, String rol) {
        this.id_usuario = id_usuario;
        this.rol = rol;
    }

    public BigDecimal getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(BigDecimal id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
