/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridadapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sv.gob.mined.seguridadapi.model.Usuario;
import sv.gob.mined.seguridadapi.model.util.Dato;
import sv.gob.mined.seguridadapi.services.UsuarioService;

/**
 *
 * @author misanchez
 */
@RestController
@RequestMapping("/api")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarioByLogin/{login}/{password}")
    public Dato getUsuario(@PathVariable String login, @PathVariable String password) {
        Dato dato = new Dato();

        Usuario usu = usuarioService.getUsuarioByLogin(login, password);
        if (usu == null) {
            dato.setCodigo(2);
            dato.setMsj("Usuario/Contraseña erroneos");
        } else {
            dato.setCodigo(1);
            dato.setData(usu);
        }

        return dato;
    }
}
