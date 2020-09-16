/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.service;

import java.util.List;
import sv.gob.mined.paquete.backend.model.OpcionMenu;
import sv.gob.mined.paquete.backend.model.Usuario;

/**
 *
 * @author MISanchez
 */
public interface LoginService {
 
    public List<OpcionMenu> findAll();
    
    public Usuario isUsuarioValida(String usuario, String clave);
}
