/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import sv.gob.mined.app.web.controller.MenuController;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;

/**
 *
 * @author misanchez
 */
@FacesConverter(value = "entidadFinancieraConverter")
public class EntidadFinancieraConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Class clase = ((java.util.Vector) ((UISelectItems) component.getChildren().get(0)).getValue()).get(0).getClass();
        MenuController controller = (MenuController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "menuController");
        return controller.utilEJB.find(clase, JsfUtil.newInstanceValuePK(clase, value));
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
            EntidadFinanciera o = (EntidadFinanciera) object;
            return getStringKey(o.getCodEntFinanciera());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EntidadFinanciera.class.getName());
        }
    }
}