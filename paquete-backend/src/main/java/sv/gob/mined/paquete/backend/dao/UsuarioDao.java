/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.dao;

import java.util.List;
import sv.gob.mined.paquete.backend.model.Roles;
import sv.gob.mined.paquete.backend.model.Usuario;

/**
 *
 * @author MISanchez
 */
public interface UsuarioDao {

    public Usuario isUsuarioValido(String usuario, String clave);

    public Usuario getUsuario(String usuario);
    
    public List<Roles> getRolesByUsuario(String usuario);
}
