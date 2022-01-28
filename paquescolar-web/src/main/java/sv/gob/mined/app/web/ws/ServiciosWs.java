/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.ws;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sv.gob.mined.paquescolar.ws.RestPaquete;

/**
 *
 * @author misanchez
 */
//@Path("servicios")
public class ServiciosWs {
    @EJB
    private RestPaquete restPaquete;
    
    @GET
    @Path("/validarUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validarUsuario(@QueryParam("user") String user, @QueryParam("pass") String pass) {
        Map<String, Object> map = new HashMap();
        map.put("validar", restPaquete.isValidarUsuario(user, pass));
        if ((Boolean) map.get("validar")) {
            map.put("keyToken", "00");
        } else {
            map.put("msj", "Usuario o Clave de acceso no válidas");
        }
        
        return Response.ok(map).header("Access-Control-Allow-Origin", "*").build();
    }
    
    @GET
    @Path("/lstAnho")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLstAnho() {
        Map<String, Object> map = new HashMap();
        map.put("listado", restPaquete.getLstAnhos());
        
        return Response.ok(map).header("Access-Control-Allow-Origin", "*").build();
    }
    
    @GET
    @Path("/lstProcesoAdq")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLstProcesoAdq(@QueryParam("idAnho") BigDecimal idAnho) {
        Map<String, Object> map = new HashMap();
        map.put("listado", restPaquete.getLstProcesoByIdAnho(idAnho));
        
        return Response.ok(map).header("Access-Control-Allow-Origin", "*").build();
    }
    
    @GET
    @Path("/lstRubroAdq")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLstRubroAdq(@QueryParam("idProcesoAdq") Integer idProcesoAdq) {
        Map<String, Object> map = new HashMap();
        map.put("listado", restPaquete.getLstRubroAdqByIdProceso(idProcesoAdq));
        
        return Response.ok(map).header("Access-Control-Allow-Origin", "*").build();
    }
    
    @GET
    @Path("/lstProveedor")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLstRubroAdq(@QueryParam("codigoEntidad") String codigoEntidad, @QueryParam("idProcesoAdq") Integer idProcesoAdq, @QueryParam("idRubro") Integer idRubro) {
        Map<String, Object> map = new HashMap();
        map.put("listado", restPaquete.getLstProveedoresByCodEntAndIdProAndIdRub(codigoEntidad, idProcesoAdq, idRubro));
        
        return Response.ok(map).header("Access-Control-Allow-Origin", "*").build();
    }
}
