/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sv.gob.mined.paquete.backend.model.Roles;
import sv.gob.mined.paquete.backend.model.Usuario;
import sv.gob.mined.paquete.backend.util.RC4Crypter;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Usuario isUsuarioValido(String usuario, String clave) {
        try {
            return jdbcTemplate.queryForObject(
                    "select usu.*, per.usuario, per.clave_acceso from usuario usu inner join persona per on usu.id_persona = per.id_persona where per.usuario = ? and per.clave_acceso = ?",
                    new Object[]{usuario, (new RC4Crypter()).encrypt("ha", clave)},
                    (rs, rowNum)
                    -> new Usuario(
                            rs.getBigDecimal("id_usuario"),
                            rs.getBigDecimal("id_persona"),
                            rs.getBigDecimal("id_tipo_usuario"),
                            rs.getString("usuario_insercion"),
                            rs.getDate("fecha_insercion"),
                            rs.getString("usuario_modificacion"),
                            rs.getDate("fecha_modificacion"),
                            rs.getDate("fecha_eliminacion"),
                            rs.getBigDecimal("estado_eliminacion"),
                            rs.getString("codigo_departamento"),
                            rs.getDate("fecha_vencimiento_clave"),
                            rs.getDate("fecha_inicio_login"),
                            rs.getDate("fecha_fin_login"),
                            rs.getShort("rango_fecha_login"),
                            rs.getShort("activo"),
                            rs.getString("usuario"),
                            rs.getString("clave_acceso")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Usuario getUsuario(String usuario) {
        try {
            return jdbcTemplate.queryForObject(
                    "select usu.*, per.usuario, per.clave_acceso from usuario usu inner join persona per on usu.id_persona = per.id_persona where per.usuario = ?",
                    new Object[]{usuario},
                    (rs, rowNum)
                    -> new Usuario(
                            rs.getBigDecimal("id_usuario"),
                            rs.getBigDecimal("id_persona"),
                            rs.getBigDecimal("id_tipo_usuario"),
                            rs.getString("usuario_insercion"),
                            rs.getDate("fecha_insercion"),
                            rs.getString("usuario_modificacion"),
                            rs.getDate("fecha_modificacion"),
                            rs.getDate("fecha_eliminacion"),
                            rs.getBigDecimal("estado_eliminacion"),
                            rs.getString("codigo_departamento"),
                            rs.getDate("fecha_vencimiento_clave"),
                            rs.getDate("fecha_inicio_login"),
                            rs.getDate("fecha_fin_login"),
                            rs.getShort("rango_fecha_login"),
                            rs.getShort("activo"),
                            rs.getString("usuario"),
                            rs.getString("clave_acceso")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Roles> getRolesByUsuario(String usuario) {
        try {
            return jdbcTemplate.query(
                    "select usu.id_usuario, tu.descripcion\n"
                    + "from \n"
                    + "    persona per\n"
                    + "    inner join usuario usu on per.id_persona = usu.id_persona\n"
                    + "    inner join usuario_roles ur on usu.id_usuario = ur.id_usuario\n"
                    + "    inner join tipo_usuario tu on ur.id_tipo_usuario = tu.id_tipo_usuario\n"
                    + "where \n"
                    + "    per.usuario = ?",
                    new Object[]{usuario},
                    (rs, rowNum)
                    -> new Roles(rs.getBigDecimal("id_usuario"),
                            rs.getString("descripcion"))
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
