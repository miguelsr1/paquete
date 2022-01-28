/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sv.gob.mined.paquete.backend.model.OpcionMenu;
import sv.gob.mined.paquete.backend.model.Usuario;
import sv.gob.mined.paquete.backend.service.LoginService;

/**
 *
 * @author MISanchez
 */
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class LoginRestController {

    @Autowired
    private LoginService loginService;

    @GetMapping(value = {"/opciones", "/"})
    public List<OpcionMenu> index() {
        return loginService.findAll();
    }

    @GetMapping("/usuario")
    @ResponseBody
    public Usuario index(@RequestParam(name = "usuario") String usuario, @RequestParam(name = "clave") String clave) {
        return loginService.isUsuarioValida(usuario, clave);
    }
}
