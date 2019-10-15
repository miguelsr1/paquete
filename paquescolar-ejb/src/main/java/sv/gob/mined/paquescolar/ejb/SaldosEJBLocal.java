/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import javax.ejb.Local;
import sv.gob.mined.paquescolar.model.ResolucionesAdjudicativas;

/**
 *
 * @author misanchez
 */
@Local
public interface SaldosEJBLocal {

    public HashMap<String, Object> aplicarReservaDeFondos(ResolucionesAdjudicativas resAdj,
            BigDecimal estadoReserva, String codigoEntidad, String comentarioReversion, String usuario);
}
