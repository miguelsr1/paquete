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

/**
 *
 * @author misanchez
 */
@FacesConverter(value = "anhoConverter")
public class AnhoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Class clase = ((java.util.Vector) ((UISelectItems) component.getChildren().get(1)).getValue()).get(0).getClass();
        MenuController controller = (MenuController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "menuController");
        return controller.utilEJB.find(clase, JsfUtil.newInstanceValuePK(clase, value));
    }

    /*java.math.BigDecimal getKey(String value) {
     java.math.BigDecimal key;
     key = new java.math.BigDecimal(value);
     return key;
     }*/

    /*String getStringKey(java.math.BigDecimal value) {
     StringBuilder sb = new StringBuilder();
     sb.append(value);
     return sb.toString();
     }*/
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        } 
        //if (object instanceof Anho) {
        //Anho o = (Anho) object;
        if (JsfUtil.getValuePK(object) == null) {
            return null;
        } else {
            return JsfUtil.getValuePK(object).toString();
        }
        /*} else {
         throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Anho.class.getName());
         }*/
    }
}
