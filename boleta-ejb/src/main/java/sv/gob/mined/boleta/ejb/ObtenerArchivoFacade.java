/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.ejb;

import java.io.File;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class ObtenerArchivoFacade {

    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("parametros");

    @EJB
    private PersistenciaFacade persistenciaFacade;

    public File obtenerArchivo(String codigoGenerado) {
        String pathBoleta = persistenciaFacade.getDetalleCodigo(codigoGenerado);
        return new File(RESOURCE.getString("path_archivo") + File.separator + pathBoleta);
    }
}
