/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.votacion.api.facade;

import java.util.List;
import javax.ejb.Local;
import sv.gob.mined.votacion.model.paquete.entities.Departamento;
import sv.gob.mined.votacion.model.paquete.entities.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@Local
public interface EntidadEducativaFacadeLocal {

    public List<Departamento> getLstDepartamentos();

    public VwCatalogoEntidadEducativa findEntidadByCodigo(String codigoEntidad);
}
