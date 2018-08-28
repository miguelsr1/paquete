/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.converter;

/**
 *
 * @author misanchez
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
@FacesConverter(value = "genericConverter")
public class GenericConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || value.toString().contains("Seleccione")) {
            return null;
        }
        Class clase = ((java.util.Vector) ((UISelectItems) component.getChildren().get(1)).getValue()).get(0).getClass();
        MenuController controller = (MenuController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "menuController");
        return controller.utilEJB.find(clase, JsfUtil.newInstanceValuePK(clase, value));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null || JsfUtil.getValuePK(object) == null) {
            return null;
        }
        return JsfUtil.getValuePK(object).toString();
    }
}
