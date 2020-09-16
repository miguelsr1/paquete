/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.gob.mined.paquete.backend.dao.UsuarioDao;
import sv.gob.mined.paquete.backend.model.Usuario;

/**
 *
 * @author MISanchez
 */
@Service("jdbcUserDetailsService")
public class JdbcUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

    private Logger logger = LoggerFactory.getLogger(JdbcUserDetailsService.class);

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.getUsuario(username);

        if (usuario == null) {
            logger.error("Error login: no existe el usuario '" + username + "'");
            throw new UsernameNotFoundException("Username '" + username + "' no existe en el sistema!");
        }

        List<GrantedAuthority> authorities = new ArrayList();

        usuarioDao.getRolesByUsuario(username).forEach(roles -> {
            authorities.add(new SimpleGrantedAuthority(roles.getRol()));
        });

        if (authorities.isEmpty()) {
            logger.error("Error login: usuario '"+username+"' no tiene roles asignados!");
            throw new UsernameNotFoundException("Username '" + username + "' no tiene roles asignados!");
        }

        return new User(usuario.getUsuario(),
                usuario.getPassword(),
                (usuario.getActivo() == 1),
                true, true, true,
                authorities);
    }

}
