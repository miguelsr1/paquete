/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author misanchez
 */
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Herramientas {

    private static final String[] meses = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
    private static final String[] dias = {"domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"};
    private static final DecimalFormat formatter = new DecimalFormat("$ #,##0.00");
    private static final DecimalFormat formatter2 = new DecimalFormat("#,##0.00");

    public static String aLetras(BigDecimal paramBigDecimal) {
        String str6 = "";

        String str8 = "";

        paramBigDecimal = paramBigDecimal.setScale(2, 4);

        String str1 = paramBigDecimal.toString();

        for (int i = 0; i <= 11 - str1.length(); i++) {
            str6 = str6 + "0";
        }
        str1 = str6 + str1;
        String str2 = str1.substring(10);
        String str3 = str1.substring(6, 9);
        String str4 = str1.substring(3, 6);
        String str5 = str1.substring(0, 3);

        if (!str5.equals("000")) {
            String str7 = analizaCadena(str5);

            if (Integer.valueOf(str5).intValue() != 1) {
                str8 = str7.trim() + " MILLONES ";
            } else {
                str8 = str7.trim() + " MILLON ";
            }
            if (Integer.valueOf(str4).intValue() != 0) {
                str7 = analizaCadena(str4);
                str8 = str8 + " " + str7.trim() + " MIL";
            }

            if (Integer.valueOf(str3).intValue() != 0) {
                str7 = analizaCadena(str3);
                str8 = str8 + " " + str7.trim();
            }

            str8 = str8 + " " + str2 + "/100";
            return str8;
        }

        if (!str4.equals("000")) {
            String str7 = analizaCadena(str4);
            str8 = str8 + " " + str7.trim() + " MIL";

            if (Integer.valueOf(str3).intValue() != 0) {
                str7 = analizaCadena(str3);
                str8 = str8 + " " + str7.trim();
            }

            str8 = str8 + " " + str2 + "/100";
            return str8;
        }

        if (!str3.equals("000")) {
            String str7 = analizaCadena(str3);
            str8 = str8 + " " + str7.trim();
            str8 = str8 + " " + str2 + "/100";
            return str8;
        }

        return str8;
    }

    public static String bLetras(BigDecimal paramBigDecimal) {
        String str6 = "";

        String str8 = "";

        paramBigDecimal = paramBigDecimal.setScale(2, 4);

        String str1 = paramBigDecimal.toString();

        for (int i = 0; i <= 11 - str1.length(); i++) {
            str6 = str6 + "0";
        }
        str1 = str6 + str1;

        String str3 = str1.substring(6, 9);
        String str4 = str1.substring(3, 6);
        String str5 = str1.substring(0, 3);

        if (!str5.equals("000")) {
            String str7 = analizaCadena(str5);

            if (Integer.valueOf(str5).intValue() != 1) {
                str8 = str7.trim() + " MILLONES ";
            } else {
                str8 = str7.trim() + " MILLON ";
            }
            if (Integer.valueOf(str4).intValue() != 0) {
                str7 = analizaCadena(str4);
                str8 = str8 + " " + str7.trim() + " MIL";
            }

            if (Integer.valueOf(str3).intValue() != 0) {
                str7 = analizaCadena(str3);
                str8 = str8 + " " + str7.trim();
            }

            return str8;
        }

        if (!str4.equals("000")) {
            String str7 = analizaCadena(str4);
            str8 = str8 + " " + str7.trim() + " MIL";

            if (Integer.valueOf(str3).intValue() != 0) {
                str7 = analizaCadena(str3);
                str8 = str8 + " " + str7.trim();
            }

            return str8;
        }

        if (!str3.equals("000")) {
            String str7 = analizaCadena(str3);
            str8 = str8 + " " + str7.trim();

            return str8;
        }

        return str8;
    }

    public static String analizaCadena(String paramString) {
        String str3 = "";

        String[] arrayOfString1 = {"", "UN ", "DOS ", "TRES ", "CUATRO ", "CINCO ", "SEIS ", "SIETE ", "OCHO ", "NUEVE "};
        String[] arrayOfString2 = {"", "ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", "DIECISEIS ", "DIECISIETE ", "DIECIOCHO ", "DIECINUEVE "};
        String[] arrayOfString3 = {"", "", "", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"};
        String[] arrayOfString4 = {"", "CIENTO ", "DOSCIENTOS ", "TRESCIENTOS ", "CUATROCIENTOS ", "QUINIENTOS ", "SEISCIENTOS ", "SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS "};

        if (!paramString.substring(0, 1).equals("0")) {
            if (paramString.equals("100")) {
                String str4 = "CIEN";
                return str4;
            }

            str3 = arrayOfString4[Integer.valueOf(paramString.substring(0, 1)).intValue()];

            if (paramString.substring(1).equals("00")) {
                String str4 = str3.trim();
                return str4;
            }

        }

        int i = paramString.charAt(1);
        String str2;
        String str4;
        switch (i) {
            case 48:
                str2 = arrayOfString1[Integer.valueOf(paramString.substring(2)).intValue()];
                str4 = str3 + str2;
                break;
            case 49:
                if (paramString.substring(2).equals("0")) {
                    str2 = "DIEZ";
                } else {
                    str2 = arrayOfString2[Integer.valueOf(paramString.substring(2)).intValue()];
                }
                str4 = str3 + str2;
                break;
            case 50:
                if (paramString.substring(2).equals("0")) {
                    str4 = str3 + " VEINTE";
                } else {
                    str2 = arrayOfString1[Integer.valueOf(paramString.substring(2)).intValue()];
                    str4 = str3 + " VEINTI" + str2;
                }
                break;
            default:
                str2 = arrayOfString3[Integer.valueOf(paramString.substring(1, 2)).intValue()];
                if (!paramString.substring(2).equals("0")) {
                    str2 = str2 + " Y " + arrayOfString1[Integer.valueOf(paramString.substring(2)).intValue()];
                }
                str4 = str3 + " " + str2;
        }
        return str4;
    }

    public static String getNumDia(String fecha) {
        Calendar c = Calendar.getInstance();
        try {
            SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
            c.setTime(sm.parse(fecha));
            return String.valueOf(c.get(5));
        } catch (ParseException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return "";
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

    public static String getNomMes(String fecha) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(fecha));
            return meses[c.get(2)];
        } catch (ParseException ex) {
        }
        return "";
    }

    public static String getNomMes(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        return meses[c.get(2)];
    }

    public static String getNumAnyo(String fecha) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(fecha));

            return String.valueOf(c.get(1));
        } catch (ParseException ex) {
        }
        return "";
    }

    public static String getNumAnyo(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);

        return String.valueOf(c.get(1));
    }

    public static String getHora(Date fecha) {
        return String.valueOf(new SimpleDateFormat("HH:mm").format(fecha));
    }

    public static String getFormatoMonto(BigDecimal monto) {
        return formatter.format(monto);
    }

    public static String getFormatoMonto2(BigDecimal monto) {
        return formatter2.format(monto);
    }

    public static String getFormatoLetrasHoraYFecha(Date fecha) {
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MMMM/yyyy");

        String[] hora = sdfHora.format(fecha).split(":");
        String[] fechaStr = sdfFecha.format(fecha).split("/");

        StringBuilder cadena;

        cadena = triTexto(Integer.parseInt(hora[0])).append("horas con ").append(triTexto(Integer.parseInt(hora[1]))).append("minutos del día ").
                append(triTexto(Integer.parseInt(fechaStr[0]))).append("de ").append(fechaStr[1]).append(" de ").append(triTexto(Integer.parseInt(fechaStr[2])));

        return cadena.toString();
    }

    private static StringBuilder triTexto(int n) {
        StringBuilder result = new StringBuilder();
        int centenas = n / 100;
        int decenas = (n % 100) / 10;
        int unidades = (n % 10);

        switch (centenas) {
            case 0:
                break;
            case 1:
                if (decenas == 0 && unidades == 0) {
                    result.append("cien ");
                    return result;
                } else {
                    result.append("ciento ");
                }
                break;
            case 2:
                result.append("doscientos ");
                break;
            case 3:
                result.append("trescientos ");
                break;
            case 4:
                result.append("cuatrocientos ");
                break;
            case 5:
                result.append("quinientos ");
                break;
            case 6:
                result.append("seiscientos ");
                break;
            case 7:
                result.append("setecientos ");
                break;
            case 8:
                result.append("ochocientos ");
                break;
            case 9:
                result.append("novecientos ");
                break;
            case 20:
                result.append("dos mil ");
                break;
        }

        switch (decenas) {
            case 0:
                break;
            case 1:
                if (unidades == 0) {
                    result.append("diez ");
                    return result;
                } else if (unidades == 1) {
                    result.append("once ");
                    return result;
                } else if (unidades == 2) {
                    result.append("doce ");
                    return result;
                } else if (unidades == 3) {
                    result.append("trece ");
                    return result;
                } else if (unidades == 4) {
                    result.append("catorce ");
                    return result;
                } else if (unidades == 5) {
                    result.append("quince ");
                    return result;
                } else {
                    result.append("dieci");
                }
                break;
            case 2:
                if (unidades == 0) {
                    result.append("veinte ");
                    return result;
                } else {
                    result.append("veinti");
                }
                break;
            case 3:
                result.append("treinta ");
                break;
            case 4:
                result.append("cuarenta ");
                break;
            case 5:
                result.append("cincuenta ");
                break;
            case 6:
                result.append("sesenta ");
                break;
            case 7:
                result.append("setenta ");
                break;
            case 8:
                result.append("ochenta ");
                break;
            case 9:
                result.append("noventa ");
                break;
        }

        if (decenas > 2 && unidades > 0) {
            result.append("y ");
        }

        switch (unidades) {
            case 0:
                break;
            case 1:
                result.append("un ");
                break;
            case 2:
                result.append("dos ");
                break;
            case 3:
                result.append("tres ");
                break;
            case 4:
                result.append("cuatro ");
                break;
            case 5:
                result.append("cinco ");
                break;
            case 6:
                result.append("seis ");
                break;
            case 7:
                result.append("siete ");
                break;
            case 8:
                result.append("ocho ");
                break;
            case 9:
                result.append("nueve ");
                break;
        }

        return result;
    }
}
