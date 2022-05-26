/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.mined.paquescolar.ejb.impl;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import sv.gob.mined.paquescolar.ejb.AbstractFacade;
import sv.gob.mined.paquescolar.model.OrganizacionEducativa;

/**
 *
 * @author misanchez
 */
@Stateless
public class OrganizacionEducativaFacade extends AbstractFacade<OrganizacionEducativa, BigDecimal> {

    public OrganizacionEducativaFacade() {
        super(OrganizacionEducativa.class);
    }

}
