/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.web.view.dialog;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.gob.mined.seguridad.model.OpcionMenu;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class AgregarOpcionMenuView implements Serializable {

    public OpcionMenu opcionMenu = new OpcionMenu();

    public OpcionMenu getOpcionMenu() {
        return opcionMenu;
    }

    public void setOpcionMenu(OpcionMenu opcionMenu) {
        this.opcionMenu = opcionMenu;
    }

}
