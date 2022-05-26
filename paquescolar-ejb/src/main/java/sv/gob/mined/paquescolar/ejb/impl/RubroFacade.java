package sv.gob.mined.paquescolar.ejb.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import sv.gob.mined.paquescolar.ejb.AbstractFacade;
import sv.gob.mined.paquescolar.model.RubrosAmostrarInteres;

/**
 *
 * @author misanchez
 */
@Stateless
public class RubroFacade extends AbstractFacade<RubrosAmostrarInteres, BigDecimal> {

    public RubroFacade() {
        super(RubrosAmostrarInteres.class);
    }

    public List<RubrosAmostrarInteres> findByProceso(){
        return new ArrayList();
    }
}
