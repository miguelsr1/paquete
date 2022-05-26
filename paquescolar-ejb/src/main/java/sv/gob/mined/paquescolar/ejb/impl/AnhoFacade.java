package sv.gob.mined.paquescolar.ejb.impl;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import sv.gob.mined.paquescolar.ejb.AbstractFacade;
import sv.gob.mined.paquescolar.model.Anho;

/**
 *
 * @author misanchez
 */
@Stateless
public class AnhoFacade extends AbstractFacade<Anho, BigDecimal> {

    public AnhoFacade() {
        super(Anho.class);
    }

}
