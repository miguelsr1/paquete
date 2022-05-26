/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.mined.paquescolar.ejb.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import sv.gob.mined.paquescolar.ejb.AbstractFacade;
import sv.gob.mined.paquescolar.model.OrganizacionEducativa;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;
import sv.gob.mined.paquescolar.util.Filtro;

/**
 *
 * @author misanchez
 */
@Stateless
public class EntidadEducativaFacade extends AbstractFacade<VwCatalogoEntidadEducativa, String> {

    @EJB
    public OrganizacionEducativaFacade organizacionEducativaFacade;

    public EntidadEducativaFacade() {
        super(VwCatalogoEntidadEducativa.class);
    }

    public OrganizacionEducativa findOrgByCargo(String codigoEntidad, String cargo) {
        List<Filtro> lstParametros = new ArrayList();
        lstParametros.add(new Filtro(Filtro.EQUALS, "cargo", cargo));
        lstParametros.add(new Filtro(Filtro.EQUALS, "codigoEntidad", codigoEntidad));

        return organizacionEducativaFacade.findEntityByParam(lstParametros);
    }
}
