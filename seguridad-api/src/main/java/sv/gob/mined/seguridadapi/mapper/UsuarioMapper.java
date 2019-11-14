/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridadapi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sv.gob.mined.seguridadapi.model.Usuario;

/**
 *
 * @author misanchez
 */
public class UsuarioMapper implements RowMapper<Usuario> {

    @Override
    public Usuario mapRow(ResultSet rs, int i) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setActivo(rs.getString("activo"));
        usuario.setApellidos(rs.getString("apellidos"));
        usuario.setCambiarClave(rs.getString("cambiar_clave"));
        usuario.setClaveAcceso(rs.getString("clave_acceso"));
        usuario.setCodigoDepartamento(rs.getString("codigo_departamento"));
        usuario.setCodigoEmpledo(rs.getString("codigo_empleado"));
        usuario.setCodigoEntidad(rs.getString("codigo_entidad"));
        usuario.setCorreoElectronico(rs.getString("correo_electronico"));
        usuario.setDui(rs.getString("dui"));
        usuario.setFechaExpiracion(rs.getDate("fecha_expiracion"));
        usuario.setLogin(rs.getString("login"));
        usuario.setNombres(rs.getString("nombres"));
        usuario.setSexo(rs.getString("sexo"));
        usuario.setTipoUsuario(rs.getString("tipo_usuario"));
        usuario.setUsuarioBloqueado(rs.getString("usuario_bloqueado"));
        return usuario;
    }

}
