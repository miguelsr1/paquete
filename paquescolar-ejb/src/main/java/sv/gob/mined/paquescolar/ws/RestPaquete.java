/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ws;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.QueryParam;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.ServiciosJsonEJB;
import sv.gob.mined.paquescolar.model.Anho;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.RubrosAmostrarInteres;
import sv.gob.mined.paquescolar.model.pojos.contratacion.VwProveedorContratoDto;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class RestPaquete {
    @EJB
    private ServiciosJsonEJB serviciosJsonEJB;
    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;
    
    public Boolean isValidarUsuario(String user, String pass) {
        return serviciosJsonEJB.isUsuarioValido(user, pass);
    }
    
    public List<Anho> getLstAnhos(){
        return anhoProcesoEJB.getLstAnhos();
    }
    
    public List<ProcesoAdquisicion> getLstProcesoByIdAnho(@QueryParam("idAnho") BigDecimal idAnho){
        return anhoProcesoEJB.getLstProcesoAdquisicionByAnho(idAnho);
    }
    
    public List<RubrosAmostrarInteres> getLstRubroAdqByIdProceso(@QueryParam("idProceso") Integer idProceso){
        return anhoProcesoEJB.getLstRubros(new ProcesoAdquisicion(idProceso));
    }
    
    public List<VwProveedorContratoDto> getLstProveedoresByCodEntAndIdProAndIdRub(String codigoEntidad, Integer idProcesoAdq, Integer idRubro){
        return serviciosJsonEJB.getLstProveedoresByCodEntAndIdProAndIdRub(codigoEntidad, idProcesoAdq, idRubro);
    }
}
