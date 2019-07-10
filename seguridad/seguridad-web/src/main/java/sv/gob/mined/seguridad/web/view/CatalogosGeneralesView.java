/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.web.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author desarrllopc2
 */
@ManagedBean
@ApplicationScoped
public class CatalogosGeneralesView implements Serializable {

    private String version;

    
    
    public CatalogosGeneralesView() {
    }

    @PostConstruct
    public void init() {
        Manifest manifest;
        try {
            InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/META-INF/MANIFEST.MF");
            manifest = new Manifest();
            manifest.read(is);

            Attributes atts = manifest.getMainAttributes();

            version = atts.getValue("Implementation-Build");
            version = version + "." + atts.getValue("build-time");

        } catch (IOException ex) {
            Logger.getLogger(CatalogosGeneralesView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    public String getVersion() {
        return version;
    }
}
