/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.util.Date;

/**
 *
 * @author misanchez
 */
public class DeclaracionJurada {
    private String rubro;
    private String anho;
    private String ciudad;
    private Date fecha;
    private String razonSocial;
    private String direccionEmpresa;
    private String nitEmpresa;
    private String representanteLegal;
    private String direccionRepre;
    private String nitRepre;
    private String duiRepre;

    public DeclaracionJurada() {
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getDireccionRepre() {
        return direccionRepre;
    }

    public void setDireccionRepre(String direccionRepre) {
        this.direccionRepre = direccionRepre;
    }

    public String getNitRepre() {
        return nitRepre;
    }

    public void setNitRepre(String nitRepre) {
        this.nitRepre = nitRepre;
    }

    public String getDuiRepre() {
        return duiRepre;
    }

    public void setDuiRepre(String duiRepre) {
        this.duiRepre = duiRepre;
    }
}