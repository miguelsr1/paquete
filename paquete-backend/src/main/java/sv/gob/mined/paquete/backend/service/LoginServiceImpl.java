/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.gob.mined.paquete.backend.dao.OpcionMenuDao;
import sv.gob.mined.paquete.backend.dao.UsuarioDao;
import sv.gob.mined.paquete.backend.model.OpcionMenu;
import sv.gob.mined.paquete.backend.model.Usuario;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private OpcionMenuDao opcionMenuDao;
    
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public List<OpcionMenu> findAll() {
        return opcionMenuDao.findAll();
    }

    @Override
    public Usuario isUsuarioValida(String usuario, String clave) {
        return usuarioDao.isUsuarioValido(usuario, clave);
    }
    
    
}
