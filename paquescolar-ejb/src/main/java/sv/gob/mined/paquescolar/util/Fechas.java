/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author misanchez
 */
public class Fechas {

    public static Date getStringToDate(String fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return sdf.parse(fecha);
        } catch (Exception e) {
            return new Date();
        }
    }
    /*public static String getDateToString(Date fecha) {
     try {
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
     return sdf.parse(fecha);
     } catch (Exception e) {
     return new Date();
     }*/
}
