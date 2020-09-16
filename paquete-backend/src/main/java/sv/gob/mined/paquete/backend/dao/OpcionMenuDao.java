/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquete.backend.dao;

import java.util.List;
import sv.gob.mined.paquete.backend.model.OpcionMenu;

/**
 *
 * @author MISanchez
 */
public interface OpcionMenuDao {

    public List<OpcionMenu> findAll();
}
