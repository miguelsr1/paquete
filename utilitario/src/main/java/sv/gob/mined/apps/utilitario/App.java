package sv.gob.mined.apps.utilitario;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Timestamp ts = new Timestamp((new Date()).getTime());
        
        Date fecha = ts;
        
        System.out.println(Herramientas.getFormatoLetrasHoraYFecha(fecha));
        
    }
}
