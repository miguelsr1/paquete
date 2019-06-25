/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.web.view.dialog;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.gob.mined.seguridad.model.Modulo;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class AgregarModuloView implements Serializable{
    
    public Modulo modulo;
    
    @PostConstruct
    public void init(){
        modulo = new Modulo();
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }
    
    
}
