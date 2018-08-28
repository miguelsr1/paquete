/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

/**
 *
 * @author 
 */
public class ReporteGeneralDTO {

    private String CODIGO_ENTIDAD;
    private String NOMBRE;
    private Integer TOTAL_UNIFORME;
    private Integer ENTREGADO_UNIFORME;
    private Integer TOTAL_UTILES;
    private Integer ENTREGADO_UTILES;
    private Integer TOTAL_ZAPATOS;
    private Integer ENTREGADO_ZAPATOS;
    private String CODIGO_DEPARTAMENTO;
    private Integer TOTALCONTRATADO;
    private Integer TOTALENTREGADO;
    private String PORCENTAJEAVAN;

    public ReporteGeneralDTO() {
    }

    public String getCODIGO_ENTIDAD() {
        return CODIGO_ENTIDAD;
    }

    public void setCODIGO_ENTIDAD(String CODIGO_ENTIDAD) {
        this.CODIGO_ENTIDAD = CODIGO_ENTIDAD;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public Integer getTOTAL_UNIFORME() {
        return TOTAL_UNIFORME;
    }

    public void setTOTAL_UNIFORME(Integer TOTAL_UNIFORME) {
        this.TOTAL_UNIFORME = TOTAL_UNIFORME;
    }

    public Integer getENTREGADO_UNIFORME() {
        return ENTREGADO_UNIFORME;
    }

    public void setENTREGADO_UNIFORME(Integer ENTREGADO_UNIFORME) {
        this.ENTREGADO_UNIFORME = ENTREGADO_UNIFORME;
    }

    public Integer getTOTAL_UTILES() {
        return TOTAL_UTILES;
    }

    public void setTOTAL_UTILES(Integer TOTAL_UTILES) {
        this.TOTAL_UTILES = TOTAL_UTILES;
    }

    public Integer getENTREGADO_UTILES() {
        return ENTREGADO_UTILES;
    }

    public void setENTREGADO_UTILES(Integer ENTREGADO_UTILES) {
        this.ENTREGADO_UTILES = ENTREGADO_UTILES;
    }

    public Integer getTOTAL_ZAPATOS() {
        return TOTAL_ZAPATOS;
    }

    public void setTOTAL_ZAPATOS(Integer TOTAL_ZAPATOS) {
        this.TOTAL_ZAPATOS = TOTAL_ZAPATOS;
    }

    public Integer getENTREGADO_ZAPATOS() {
        return ENTREGADO_ZAPATOS;
    }

    public void setENTREGADO_ZAPATOS(Integer ENTREGADO_ZAPATOS) {
        this.ENTREGADO_ZAPATOS = ENTREGADO_ZAPATOS;
    }

    public String getCODIGO_DEPARTAMENTO() {
        return CODIGO_DEPARTAMENTO;
    }

    public void setCODIGO_DEPARTAMENTO(String CODIGO_DEPARTAMENTO) {
        this.CODIGO_DEPARTAMENTO = CODIGO_DEPARTAMENTO;
    }

    public Integer getTOTALCONTRATADO() {
        return TOTALCONTRATADO;
    }

    public void setTOTALCONTRATADO(Integer TOTALCONTRATADO) {
        this.TOTALCONTRATADO = TOTALCONTRATADO;
    }

    public Integer getTOTALENTREGADO() {
        return TOTALENTREGADO;
    }

    public void setTOTALENTREGADO(Integer TOTALENTREGADO) {
        this.TOTALENTREGADO = TOTALENTREGADO;
    }

    public String getPORCENTAJEAVAN() {
        return PORCENTAJEAVAN;
    }

    public void setPORCENTAJEAVAN(String PORCENTAJEAVAN) {
        this.PORCENTAJEAVAN = PORCENTAJEAVAN;
    }
    
        
}
