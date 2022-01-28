/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sv.gob.mined.paquete.backend.model.OpcionMenu;

@Repository
public class OpcionMenuDaoImpl implements OpcionMenuDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<OpcionMenu> findAll() {
        return jdbcTemplate.query(
                "select id_opc_menu,opc_id_opc_menu,nombre_opcion,icono,orden,cod_pantalla,app from opcion_menu",
                (rs, rowNum)
                -> new OpcionMenu(
                        rs.getBigDecimal("id_opc_menu"),
                        rs.getBigDecimal("opc_id_opc_menu"),
                        rs.getString("nombre_opcion"),
                        rs.getString("icono"),
                        rs.getBigDecimal("orden"),
                        rs.getString("cod_pantalla"),
                        rs.getBigDecimal("app")
                )
        );
    }

}
