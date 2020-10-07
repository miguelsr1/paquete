/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.utils.jsf;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author MISanchez
 */
public class JsfUtil {

    private static FacesMessage msg;

    private static final DecimalFormat FORMAT_DECIMAL = new DecimalFormat("#,##0.00");

    private static final DecimalFormat FORMAT_ENTERO = new DecimalFormat("#,##0");

    public static final DateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yyyy");

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? (entities.size() + 1) : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static String getRequestParameter(String key) {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static void mensajeUpdate() {
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "<big>Información</big", "<big>Actualización exitosa.</big>");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void mensajeInsert() {
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "<big>Información</big>", "Registro almacenado satisfactoriamente</big>");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void mensajeAlerta(String mensaje) {
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "<big>Alerta</big>", "<big>" + mensaje + "</big>");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void mensajeError(String mensaje) {
        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "<big>Error</big>", "<big>" + mensaje + "</big>");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void mensajeInformacion(String mensaje) {
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "<big>Información</big>", "<big> " + mensaje + "</big>");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public static void redireccionar(String url) {
        ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        configurableNavigationHandler.performNavigation(url);
    }

    public static String getNombreDepartamentoByCodigo(String codigo) {
        String nombre = "";
        switch (codigo) {
            case "00":
                nombre = "TODO EL PAIS";
                break;
            case "01":
                nombre = "AHUACHAPAN";
                break;
            case "02":
                nombre = "SANTA ANA";
                break;
            case "03":
                nombre = "SONSONATE";
                break;
            case "04":
                nombre = "CHALATENANGO";
                break;
            case "05":
                nombre = "LA LIBERTAD";
                break;
            case "06":
                nombre = "SAN SALVADOR";
                break;
            case "07":
                nombre = "CUSCATLAN";
                break;
            case "08":
                nombre = "LA PAZ";
                break;
            case "09":
                nombre = "CABAÑAS";
                break;
            case "10":
                nombre = "SAN VICENTE";
                break;
            case "11":
                nombre = "USULUTAN";
                break;
            case "12":
                nombre = "SAN MIGUEL";
                break;
            case "13":
                nombre = "MORAZAN";
                break;
            case "14":
                nombre = "LA UNION";
                break;
        }
        return nombre;
    }

    public static String getNombreRubroById(BigDecimal idRubro) {
        switch (idRubro.intValue()) {
            case 1:
            case 4:
            case 5:
                return "SERVICIOS DE CONFECCION DE UNIFORMES";
            case 2:
                return "SUMINISTRO DE PAQUETES DE UTILES ESCOLARES";
        }
        return "PRODUCCION DE ZAPATOS";
    }

    public static HashMap getNombreRubroRpt(int idRubro, HashMap<String, String> param, Boolean sobredemanda) {
        switch (idRubro) {
            case 1:
                param.put("descripcionRubro", (sobredemanda ? "SOBREDEMANDA DE " : "") + "SERVICIOS DE CONFECCION DE UNIFORMES");
                break;
            case 4:
                param.put("descripcionRubro", (sobredemanda ? "SOBREDEMANDA DE " : "") + "SERVICIOS DE CONFECCION DE PRIMER UNIFORME");
                break;
            case 5:
                param.put("descripcionRubro", (sobredemanda ? "SOBREDEMANDA DE " : "") + "SERVICIOS DE CONFECCION DE SEGUNDO UNIFORME");
                break;
            case 2:
                param.put("descripcionRubro", (sobredemanda ? "SOBREDEMANDA DE " : "") + "SUMINISTRO DE PAQUETES DE UTILES ESCOLARES");
                break;
            case 3:
                param.put("descripcionRubro", (sobredemanda ? "SOBREDEMANDA DE " : "") + "PRODUCCION DE ZAPATOS");
                break;
        }
        return param;
    }

    public static String getFormatoNum(Object obj, Boolean entero) {
        if (entero) {
            if (obj == null) {
                return "0";
            }
            return FORMAT_ENTERO.format(obj);
        }
        if (obj == null) {
            return "0.00";
        }
        return FORMAT_DECIMAL.format(obj);
    }

    public static String formatearNumero(int posisiones, String valor, Boolean numInt) {
        valor = valor.replaceAll("[.]", "");
        Formatter fmt = new Formatter();
        if (numInt) {
            fmt.format("%0" + posisiones + "d", new Object[]{Integer.parseInt(valor)});
        } else {
            fmt.format("%-" + posisiones + "s", new Object[]{valor});
        }
        return fmt.toString();
    }

    public static String formatoNumeroF910(int posisiones, BigDecimal valor, Boolean numInt) {
        String formato = FORMAT_DECIMAL.format(valor.doubleValue()).replaceAll("[.]", "").replaceAll("[,]", "");
        Formatter fmt = new Formatter();
        Formatter format;
        if (numInt) {
            format = fmt.format("%0" + posisiones + "d", new Object[]{Integer.parseInt(formato)});
        } else {
            fmt.format("%-" + posisiones + "s", new Object[]{valor});
        }
        return fmt.toString();
    }

    public static String getFechaString(Date date) {
        return FORMAT_DATE.format(date);
    }

    public static Boolean isExisteParametroUrl(String nombreParamentro) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return params.containsKey(nombreParamentro);
    }

    public static String getParametroUrl(String nombreParamentro) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return params.get(nombreParamentro);
    }

    public static void limpiarVariableSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().clear();
    }

    public static String getNombreMesYAnhoByParam(String mesAnho) {
        String valor;
        String[] param = mesAnho.split("_");
        switch (param[0]) {
            case "01":
                valor = "enero";
                valor = valor + " de " + param[1];
                return valor;
            case "02":
                valor = "febrero";
                valor = valor + " de " + param[1];
                return valor;
            case "03":
                valor = "marzo";
                valor = valor + " de " + param[1];
                return valor;
            case "04":
                valor = "abril";
                valor = valor + " de " + param[1];
                return valor;
            case "05":
                valor = "mayo";
                valor = valor + " de " + param[1];
                return valor;
            case "06":
                valor = "junio";
                valor = valor + " de " + param[1];
                return valor;
            case "07":
                valor = "julio";
                valor = valor + " de " + param[1];
                return valor;
            case "08":
                valor = "agosto";
                valor = valor + " de " + param[1];
                return valor;
            case "09":
                valor = "septiembre";
                valor = valor + " de " + param[1];
                return valor;
            case "10":
                valor = "octubre";
                valor = valor + " de " + param[1];
                return valor;
            case "11":
                valor = "noviembre";
                valor = valor + " de " + param[1];
                return valor;
        }
        valor = "diciembre";
        valor = valor + " de " + param[1];
        return valor;
    }
}
