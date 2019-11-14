/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridadapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.gob.mined.seguridadapi.dao.UsuarioDao;
import sv.gob.mined.seguridadapi.model.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;
    
    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioByLogin(String login, String password) {
        return usuarioDao.getUsuarioByLogin(login, password);
    }

}
