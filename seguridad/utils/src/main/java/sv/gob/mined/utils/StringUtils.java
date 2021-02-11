/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author MISanchez
 */
public class StringUtils {

    private static final String[] meses = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
    private static final String[] dias = {"domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"};
    private static final DateFormat FORMAT_DATE_RPT = new SimpleDateFormat("ddMMMyy_HHmmss");
    private static final DateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Devuelve la fecha y hora actual en formato ddMMyy_HHmmss
     *
     * @return
     */
    public static String getFechaActual() {
        return FORMAT_DATE_RPT.format(new Date());
    }

    public static String getFechaFormat(Date fecha){
        return FORMAT_DATE.format(fecha);
    }
    
    public static String getFecha(Date fecha) {
        String fechaStr = "%s del mes de %s de %s";

        Calendar c = Calendar.getInstance();
        c.setTime(fecha);

        Date tmp = c.getTime();

        fechaStr = String.format(fechaStr, getNumDia(tmp), getNomMes(tmp), getNumAnyo(tmp));

        return fechaStr;
    }

    public static String getNumDia(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        return String.valueOf(c.get(5));
    }

    public static String getNomDia(String fecha) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(fecha));
            return dias[(c.get(7) - 1)];
        } catch (ParseException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return "";
    }

    public static String getNomMes(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        return meses[c.get(2)];
    }

    public static String getNumAnyo(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);

        return String.valueOf(c.get(1));
    }
}
