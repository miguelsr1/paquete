/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridadapi.dao;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sv.gob.mined.seguridadapi.mapper.UsuarioMapper;
import sv.gob.mined.seguridadapi.model.Usuario;

@Repository(value = "usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao {

    @Autowired
    private DataSource dataSource;

    JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Usuario getUsuarioByLogin(String login, String password) {
        String passwordEncrypted = DigestUtils.md5Hex(password).toUpperCase();

        List<Usuario> lstUsuario = jdbcTemplate.query("SELECT * FROM Usuario WHERE login=? and clave_acceso=?", new Object[]{login, passwordEncrypted}, new UsuarioMapper());
        if (lstUsuario.isEmpty()) {
            return null;
        } else {
            return lstUsuario.get(0);
        }
    }

}
