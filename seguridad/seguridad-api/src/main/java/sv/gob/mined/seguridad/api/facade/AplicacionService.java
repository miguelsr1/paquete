/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.api.facade;

import java.util.List;
import javax.ejb.Local;
import sv.gob.mined.seguridad.model.Aplicacion;
import sv.gob.mined.seguridad.model.Usuario;

/**
 *
 * @author misanchez
 */
@Local
public interface AplicacionService {

    public <T extends Object> T findEntity(Class<T> clase, Object id);

    public List<Aplicacion> getLstAplicaciones();
    
    public Usuario getUsuarioByLogin(String login) ;
}
