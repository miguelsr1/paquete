/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.math.BigDecimal;

/**
 *
 * @author reynaldo
 */
public class MontosEntidadSmaelDto {
    private String codigoEntidad;
    //private String numeroCuenta;
    private BigDecimal monto;

    public MontosEntidadSmaelDto() {
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigo_entidad) {
        this.codigoEntidad = codigo_entidad;
    }

    /*public String getNumero_Cuenta() {
        return numero_Cuenta;
    }

    public void setNumero_Cuenta(String numero_Cuenta) {
        this.numero_Cuenta = numero_Cuenta;
    }*/

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
    
}
