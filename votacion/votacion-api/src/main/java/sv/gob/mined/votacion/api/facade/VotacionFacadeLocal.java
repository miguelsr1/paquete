/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.votacion.api.facade;

import java.util.List;
import javax.ejb.Local;
import sv.gob.mined.votacion.model.votacion.dto.DirectorDto;

/**
 *
 * @author DesarrolloPc
 */
@Local
public interface VotacionFacadeLocal {
    
    public List<DirectorDto> getLstDirectores(String dui, String nip, String nombre, String nombreCe, String codigoDepartamento);
}
