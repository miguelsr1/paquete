/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.ws.siges.model;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sv.gob.mined.ws.siges.api.dto.CredencialJwtDto;

/**
 *
 * @author misanchez
 */
@Stateless
public class ServicioFacade {

    @EJB
    private ParametrosGeneralesFacade pgFacade;

    public String getTokenWSSgies(String nombreMetodo) {
        String userGetToken = pgFacade.getValorByNombreParametro("WS_USER_GET_TOKEN");
        String passGetToken = pgFacade.getValorByNombreParametro("WS_PASS_GET_TOKEN");
        String pathUrlWSSiges = pgFacade.getValorByNombreParametro("PATH_WS_SIGES");

        CredencialJwtDto cre = new CredencialJwtDto();

        cre.setUsername(userGetToken);
        cre.setPassword(passGetToken);
        cre.setAddress("192.168.22.133");
        cre.setTokenExpirationMinutes(120);
        cre.setCategoriasOperacion(new Integer[]{1});

        Gson gson = new Gson();

        Client client = Client.create();
        WebResource webResource = client.resource(pathUrlWSSiges + "ss/seg/auth");
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, gson.toJson(cre));

        if (response.getStatus() == 200) {
            return response.getEntity(String.class);
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
    }
}
