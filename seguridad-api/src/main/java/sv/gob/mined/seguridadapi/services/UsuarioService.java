/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridadapi.services;

import sv.gob.mined.seguridadapi.model.Usuario;

/**
 *
 * @author misanchez
 */
public interface UsuarioService {

    /**
     *
     * @param login
     * @param password
     * @return
     */
    public Usuario getUsuarioByLogin(String login, String password);
}
