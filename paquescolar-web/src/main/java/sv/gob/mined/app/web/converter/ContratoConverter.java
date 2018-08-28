/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import sv.gob.mined.app.web.controller.MenuController;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;

/**
 *
 * @author misanchez
 */
@FacesConverter(value = "contratoConverter", forClass = ContratosOrdenesCompras.class)
public class ContratoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        MenuController controller = (MenuController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "menuController");
        return controller.utilEJB.find(ContratosOrdenesCompras.class, JsfUtil.newInstanceValuePK(ContratosOrdenesCompras.class, value));
    }

    java.lang.String getKey(String value) {
        java.lang.String key;
        key = value;
        return key;
    }

    String getStringKey(java.lang.String value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof EntidadFinanciera) {
            ContratosOrdenesCompras o = (ContratosOrdenesCompras) object;
            return o.getIdContrato().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EntidadFinanciera.class.getName());
        }
    }
}