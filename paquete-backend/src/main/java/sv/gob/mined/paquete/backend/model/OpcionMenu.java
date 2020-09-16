/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author MISanchez
 */
public class OpcionMenu implements Serializable {

    private BigDecimal idOpcMenu;
    private BigDecimal opcIdOpcMenu;
    private String nombreOpcion;
    private String icono;
    private BigDecimal orden;
    private String codPantalla;
    private BigDecimal app;

    public OpcionMenu() {
    }

    public OpcionMenu(BigDecimal idOpcMenu, BigDecimal opcIdOpcMenu, String nombreOpcion, String icono, BigDecimal orden, String codPantalla, BigDecimal app) {
        this.idOpcMenu = idOpcMenu;
        this.opcIdOpcMenu = opcIdOpcMenu;
        this.nombreOpcion = nombreOpcion;
        this.icono = icono;
        this.orden = orden;
        this.codPantalla = codPantalla;
        this.app = app;
    }

    public BigDecimal getIdOpcMenu() {
        return idOpcMenu;
    }

    public void setIdOpcMenu(BigDecimal idOpcMenu) {
        this.idOpcMenu = idOpcMenu;
    }

    public BigDecimal getOpcIdOpcMenu() {
        return opcIdOpcMenu;
    }

    public void setOpcIdOpcMenu(BigDecimal opcIdOpcMenu) {
        this.opcIdOpcMenu = opcIdOpcMenu;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public BigDecimal getOrden() {
        return orden;
    }

    public void setOrden(BigDecimal orden) {
        this.orden = orden;
    }

    public String getCodPantalla() {
        return codPantalla;
    }

    public void setCodPantalla(String codPantalla) {
        this.codPantalla = codPantalla;
    }

    public BigDecimal getApp() {
        return app;
    }

    public void setApp(BigDecimal app) {
        this.app = app;
    }
    
}
