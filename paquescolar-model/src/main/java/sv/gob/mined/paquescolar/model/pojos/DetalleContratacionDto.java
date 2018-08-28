/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

/**
 *
 * @author reynaldo
 */
public class DetalleContratacionDto {

    private String codigo_entidad;
    private String educativa_nombre;
    private String municipio;
    private String empresa_numero_nit;
    private String razon_social;
    Integer cantidad;
    Double sub_total;

    public DetalleContratacionDto() {
    }

    public String getCodigo_entidad() {
        return codigo_entidad;
    }

    public void setCodigo_entidad(String codigo_entidad) {
        this.codigo_entidad = codigo_entidad;
    }

    public String getEducativa_nombre() {
        return educativa_nombre;
    }

    public void setEducativa_nombre(String educativa_nombre) {
        this.educativa_nombre = educativa_nombre;
    }

    public String getEmpresa_numero_nit() {
        return empresa_numero_nit;
    }

    public void setEmpresa_numero_nit(String empresa_numero_nit) {
        this.empresa_numero_nit = empresa_numero_nit;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSub_total() {
        return sub_total;
    }

    public void setSub_total(Double sub_total) {
        this.sub_total = sub_total;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}
