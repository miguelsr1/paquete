/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.mined.paquescolar.ws;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author MISanchez
 */
@RequestScoped
@Path("/v1/seguridad")
public class SeguridadRest {
    
    @POST
    @Path("validarProvedor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validadProveedor(@PathParam("codigoProveedor") String codigoProveedor, @PathParam("nit") String nit, @PathParam("dui") String dui){
        return null;
    }
}
