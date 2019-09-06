/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.ws.paquete;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sv.gob.mined.paquescolar.ejb.ServiciosJsonEJB;

/**
 * REST Web Service
 *
 * @author misanchez
 */
@Path("generic")
public class GenericResource {

    @EJB
    private ServiciosJsonEJB serviciosJsonEJB;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of
     * sv.gob.mined.ws.paquete.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        //TODO return proper representation object
        Map<String, Object> map = new HashMap();
        map.put("listado", serviciosJsonEJB.getResumenPagoJsonByDepaAndDetProcesoAdq("01", 35));

        return Response.ok(map).build();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @GET
    @Path("/validarUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getValidarUsuario(@QueryParam("user") String user, @QueryParam("pass") String pass) {
        Map<String, Object> map = new HashMap();
        map.put("respuesta", serviciosJsonEJB.isUsuarioValido(user, pass));
        if ((Boolean) map.get("validar")) {
            map.put("keyToken", "00");
        } else {
            map.put("msj", "Usuario o Clave de acceso no válidas");
        }

        return Response.ok(map).header("Access-Control-Allow-Origin", "*").build();
    }
}
