/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.api;

import javax.ejb.Remote;
import sv.gob.mined.seguridad.model.Usuario;

/**
 *
 * @author misanchez
 */
@Remote
public interface NewSessionBeanRemote {

    public Usuario findUsuarioByLogin(String login);
}
