/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author MISanchez
 */
public class StringUtils {
    private static final DateFormat FORMAT_DATE_RPT = new SimpleDateFormat("ddMMMyy_HHmmss");
    
    /**
     * Devuelve la fecha y hora actual en formato ddMMyy_HHmmss
     * @return 
     */
    public static String getFechaActual() {
        return FORMAT_DATE_RPT.format(new Date());
    }
}
