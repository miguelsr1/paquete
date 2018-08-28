/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

/**
 *
 * @author reynaldo
 */
public class SmaelEncabezadoDTO {
    private String TIPO_CONCEPTO_BONO;
    private String NOMBRE_BONO;
    private String NOMBRE;
    private String NO_REQUERIMIENTO; 
    private String CODIGO_PLANILLA; 
    private String ID_PLANILLA_PARCIAL; 
    private String NOMBRE_BANCO;
    private String ID_PRE_CARGA;
    private String NOMBRE_EST_PLANILLA;
    private String MONTO;

    public SmaelEncabezadoDTO() {
    }

    public String getTIPO_CONCEPTO_BONO() {
        return TIPO_CONCEPTO_BONO;
    }

    public void setTIPO_CONCEPTO_BONO(String TIPO_CONCEPTO_BONO) {
        this.TIPO_CONCEPTO_BONO = TIPO_CONCEPTO_BONO;
    }

    public String getNOMBRE_BONO() {
        return NOMBRE_BONO;
    }

    public void setNOMBRE_BONO(String NOMBRE_BONO) {
        this.NOMBRE_BONO = NOMBRE_BONO;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getNO_REQUERIMIENTO() {
        return NO_REQUERIMIENTO;
    }

    public void setNO_REQUERIMIENTO(String NO_REQUERIMIENTO) {
        this.NO_REQUERIMIENTO = NO_REQUERIMIENTO;
    }

    public String getCODIGO_PLANILLA() {
        return CODIGO_PLANILLA;
    }

    public void setCODIGO_PLANILLA(String CODIGO_PLANILLA) {
        this.CODIGO_PLANILLA = CODIGO_PLANILLA;
    }

    public String getNOMBRE_BANCO() {
        return NOMBRE_BANCO;
    }

    public void setNOMBRE_BANCO(String NOMBRE_BANCO) {
        this.NOMBRE_BANCO = NOMBRE_BANCO;
    }

    public String getID_PLANILLA_PARCIAL() {
        return ID_PLANILLA_PARCIAL;
    }

    public void setID_PLANILLA_PARCIAL(String ID_PLANILLA_PARCIAL) {
        this.ID_PLANILLA_PARCIAL = ID_PLANILLA_PARCIAL;
    }

    public String getID_PRE_CARGA() {
        return ID_PRE_CARGA;
    }

    public void setID_PRE_CARGA(String ID_PRE_CARGA) {
        this.ID_PRE_CARGA = ID_PRE_CARGA;
    }

    public String getNOMBRE_EST_PLANILLA() {
        return NOMBRE_EST_PLANILLA;
    }

    public void setNOMBRE_EST_PLANILLA(String NOMBRE_EST_PLANILLA) {
        this.NOMBRE_EST_PLANILLA = NOMBRE_EST_PLANILLA;
    }

    public String getMONTO() {
        return MONTO;
    }

    public void setMONTO(String MONTO) {
        this.MONTO = MONTO;
    }
}