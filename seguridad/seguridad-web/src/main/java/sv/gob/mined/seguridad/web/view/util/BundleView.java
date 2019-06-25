/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.web.view.util;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author misanchez
 */
public class BundleView implements Serializable {

    @ManagedProperty("#{bundle}")
    private ResourceBundle bundle;

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }
}
