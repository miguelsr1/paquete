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
import sv.gob.mined.paquescolar.model.DetalleCredito;

/**
 *
 * @author misanchez
 */
@FacesConverter(value = "detalleCreditoConverter", forClass = DetalleCredito.class)
public class DetalleCreditoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        MenuController controller = (MenuController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "menuController");
        return controller.utilEJB.find(DetalleCredito.class, JsfUtil.newInstanceValuePK(DetalleCredito.class, value));
    }
    
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        } 
        if (JsfUtil.getValuePK(object) == null) {
            return null;
        } else {
            return JsfUtil.getValuePK(object).toString();
        }
    }
}
