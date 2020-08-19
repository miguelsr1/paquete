/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author MISanchez
 */
@ManagedBean
@ViewScoped
public class LiquidacionMB implements Serializable {
    
    
    private String codigoEntidad;
    private BigDecimal idRubro;

    public LiquidacionMB() {
    }
    
    
}
