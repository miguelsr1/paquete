/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author misanchez
 */
//@Path("/graficos")
public class GraficosWS {

    //@Path("/list")
    //@GET
    //@Produces(MediaType.TEXT_PLAIN)
    public String getGrafico() {

        return "datos";
    }
}
