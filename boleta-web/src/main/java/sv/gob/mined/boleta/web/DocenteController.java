/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.gob.mined.boleta.model.CorreoDocente;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class DocenteController implements Serializable {

    private CorreoDocente correoDocente = new CorreoDocente();

    public DocenteController() {
    }

    public CorreoDocente getCorreoDocente() {
        return correoDocente;
    }

    public void setCorreoDocente(CorreoDocente correoDocente) {
        this.correoDocente = correoDocente;
    }

}
