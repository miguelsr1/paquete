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
public class DetPlaParChequesDTO {
    private BigDecimal idBanco;
    private BigDecimal montoCheque;

    public DetPlaParChequesDTO() {
    }

    public BigDecimal getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(BigDecimal idBanco) {
        this.idBanco = idBanco;
    }

    public BigDecimal getMontoCheque() {
        return montoCheque;
    }

    public void setMontoCheque(BigDecimal montoCheque) {
        this.montoCheque = montoCheque;
    }

   
    
    
}
