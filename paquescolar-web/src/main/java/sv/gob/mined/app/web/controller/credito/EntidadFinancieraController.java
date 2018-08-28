/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.credito;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.SelectEvent;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.CreditosEJB;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class EntidadFinancieraController implements Serializable{

    private EntidadFinanciera selected;
    private Boolean deshabilitado = true;
    private List<EntidadFinanciera> listEntFinan = new ArrayList();
    @EJB
    public CreditosEJB creditosEJB;

    /**
     * Creates a new instance of EntidadFinancieraController
     */
    public EntidadFinancieraController() {
    }

    @PostConstruct
    public void init() {
        buscarEntFinan();
    }

    public Boolean getDeshabilitado() {
        return deshabilitado;
    }

    public void setDeshabilitado(Boolean deshabilitado) {
        this.deshabilitado = deshabilitado;
    }

    public void newEntFinanciera() {
        selected = new EntidadFinanciera();

        selected.setEstadoEliminacion(BigInteger.ZERO);
        selected.setFechaInsercion(new Date());
        selected.setUsuarioInsercion(VarSession.getVariableSessionUsuario());

        VarSession.setVariableSessionED("1");
        deshabilitado = false;
    }

    public EntidadFinanciera getSelected() {
        if (selected == null) {
            selected = new EntidadFinanciera();
        }
        return selected;
    }

    public void setSelected(EntidadFinanciera selected) {
        if (selected != null && selected.getCodEntFinanciera() != null) {
            this.selected = selected;
        }
    }

    public void guardar() {
        selected = creditosEJB.guardarEntidadFinanciera(selected);
        deshabilitado = true;
        selected = null;
    }

    public boolean getEENuevo() {
        return VarSession.getVariableSessionED() == 1;
    }

    public boolean getEEModificar() {
        return VarSession.getVariableSessionED() == 2;
    }

    public void onRowSelect(SelectEvent event) {
        selected = (EntidadFinanciera) event.getObject();
        VarSession.setVariableSessionED("2");
        deshabilitado = false;
    }

    public List<EntidadFinanciera> getListEntFinan() {
        return listEntFinan;
    }

    public void setListEntFinan(List<EntidadFinanciera> listEntFinan) {
        this.listEntFinan = listEntFinan;
    }

    private void buscarEntFinan() {
        listEntFinan = creditosEJB.findEntidadFinancieraEntities((short) 0);
    }

    @FacesConverter(forClass = EntidadFinanciera.class)
    public static class EntidadFinancieraControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EntidadFinancieraController controller = (EntidadFinancieraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "entidadFinancieraController");
            return controller.creditosEJB.findEntidadFinanciera(getKey(value));
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
}
