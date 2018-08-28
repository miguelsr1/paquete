/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

/**
 *
 * @author 
 */
public class ReporteProveedorDTO {
    private String NOMBRE_DEPARTAMENTO;
    private String RAZON_SOCIAL;
    private String NIT;
    private Integer TOTAL_UNIFORMES;
    private Integer TOTAL_ENTREGADOS;
    private String AVANCE;

    public ReporteProveedorDTO() {
    }

    public String getNOMBRE_DEPARTAMENTO() {
        return NOMBRE_DEPARTAMENTO;
    }

    public void setNOMBRE_DEPARTAMENTO(String NOMBRE_DEPARTAMENTO) {
        this.NOMBRE_DEPARTAMENTO = NOMBRE_DEPARTAMENTO;
    }

    public String getRAZON_SOCIAL() {
        return RAZON_SOCIAL;
    }

    public void setRAZON_SOCIAL(String RAZON_SOCIAL) {
        this.RAZON_SOCIAL = RAZON_SOCIAL;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public Integer getTOTAL_UNIFORMES() {
        return TOTAL_UNIFORMES;
    }

    public void setTOTAL_UNIFORMES(Integer TOTAL_UNIFORMES) {
        this.TOTAL_UNIFORMES = TOTAL_UNIFORMES;
    }

    public Integer getTOTAL_ENTREGADOS() {
        return TOTAL_ENTREGADOS;
    }

    public void setTOTAL_ENTREGADOS(Integer TOTAL_ENTREGADOS) {
        this.TOTAL_ENTREGADOS = TOTAL_ENTREGADOS;
    }

    public String getAVANCE() {
        return AVANCE;
    }

    public void setAVANCE(String AVANCE) {
        this.AVANCE = AVANCE;
    }
    
    
}
