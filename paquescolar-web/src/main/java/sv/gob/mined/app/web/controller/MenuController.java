/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.MenuEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.Usuario;

/**
 *
 * @author misanchez
 */
@ManagedBean
@SessionScoped
public class MenuController {

    private Boolean usuarioSoloLectura = false;
    private String app;
    private String codigoPantalla;
    private String codigoDepartamento;
    private String query;
    private String llavePrimaria;
    private String tipoUsuario;

    private DefaultMenuModel model;
    private Usuario usuario;
    private List lstOpciones;
    @EJB
    public UtilEJB utilEJB;
    @EJB
    private MenuEJB menuEJB;

    /**
     * Creates a new instance of MenuController
     */
    public MenuController() {
    }

    @PostConstruct
    public void init() {
        if (VarSession.isVariableSession("Usuario")) {
            usuario = menuEJB.getUsuarioByUsuario(VarSession.getVariableSessionUsuario());
            tipoUsuario = usuario.getIdTipoUsuario().getDescripcion();
            usuarioSoloLectura = (usuario.getIdTipoUsuario().getIdTipoUsuario().intValue() == 8);

            VarSession.setVariableSession("Usuario", usuario.getIdPersona().getUsuario());
            VarSession.setVariableSession("idTipoUsuario", usuario.getIdTipoUsuario().getIdTipoUsuario().intValue());
            if (!usuario.getCodigoDepartamento().equals("00")) {
                codigoDepartamento = usuario.getCodigoDepartamento();
            }
            armarMenu();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">    
    public Boolean getUsuarioSoloLectura() {
        return usuarioSoloLectura;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getDepartamentoUsuario() {
        return codigoDepartamento;
    }

    public void setDepartamentoUsuario(String departamentoUsuario) {
        this.codigoDepartamento = departamentoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public DefaultMenuModel getModel() {
        return model;
    }

    public void setModel(DefaultMenuModel model) {
        this.model = model;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getCodigoPantalla() {
        return codigoPantalla;
    }

    public void setCodigoPantalla(String codigoPantalla) {
        this.codigoPantalla = codigoPantalla;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLlavePrimaria() {
        return llavePrimaria;
    }

    public void setLlavePrimaria(String llavePrimaria) {
        this.llavePrimaria = llavePrimaria;
    }
    // </editor-fold>    

    public void armarMenu() {
        lstOpciones = menuEJB.getOpciones(usuario.getIdUsuario().intValue(), null, false);
        crearArbolMenu();
    }

    private void crearArbolMenu() {
        DefaultMenuModel menu = new DefaultMenuModel();

        try {
            DefaultMenuItem itemMenu = new DefaultMenuItem();
            itemMenu.setValue(" Slim Menu");
            itemMenu.setIcon("fa fa-thumb-tack");
            itemMenu.setCommand("#{guestPreferences.setSlimMenu(true)}");
            itemMenu.setAjax(false);

            menu.addElement(itemMenu);

            for (Object opc : lstOpciones) {
                Object[] datos = (Object[]) opc;
                DefaultSubMenu subMenu = new DefaultSubMenu();
                subMenu.setIcon(datos[4] != null ? datos[4].toString() : null);
                subMenu.setLabel(datos[2].toString());
                subMenu.setId("sub" + datos[0].toString());
                getHijo(subMenu, (BigDecimal) datos[0]);
                menu.addElement(subMenu);
            }
            model = menu;

        } catch (Exception ex) {
            JsfUtil.mensajeError("Ocurrio una excepción en el proceso de creación del arbol del menu. Contactese con el administrador del sistema.");
            Logger.getLogger(MenuController.class.getName()).
                    log(Level.SEVERE, "\n\n===============================================\n"
                            + "Ocurrio un error inesperado en el proceso de creaci\u00f3n del arbol del menu para el usuario: {0}\n"
                            + "Clase: {1}\n"
                            + "Metodo: crearArbolMenu() \n",
                            new Object[]{usuario.getIdPersona().getUsuario(), MenuController.class.getSimpleName()});
        }
    }

    private void getHijo(DefaultSubMenu opPadre, BigDecimal resultado) {
        List lst = menuEJB.getOpciones(usuario.getIdUsuario().intValue(), resultado.intValue(), true);

        for (Object object : lst) {
            Object[] datos = (Object[]) object;
            List lstTemp = menuEJB.getOpciones(usuario.getIdUsuario().intValue(), ((BigDecimal) datos[0]).intValue(), true);

            if (lstTemp.isEmpty()) {
                DefaultMenuItem itemMenu = new DefaultMenuItem();

                itemMenu.setValue(" " + datos[2]);
                itemMenu.setOutcome(datos[3].toString());
                itemMenu.setIcon(datos[4] != null ? datos[4].toString() : null);
                itemMenu.setAjax(false);
                itemMenu.setId("item" + datos[0].toString());
                opPadre.addElement(itemMenu);
            } else {
                DefaultSubMenu subMenu = new DefaultSubMenu();
                subMenu.setIcon(datos[4] != null ? datos[4].toString() : null);
                subMenu.setLabel(datos[2].toString());
                subMenu.setId("sub" + datos[0].toString());
                getHijo(subMenu, (BigDecimal) datos[0]);
                opPadre.addElement(subMenu);
            }
        }
    }

    @Deprecated
    public String selectOpcion() {
        String url = "";
        if (VarSession.getUsuarioSession() == null) {
            ((PersonaController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                    getValue(FacesContext.getCurrentInstance().getELContext(), null, "personaController")).logout();
        } else {
            int idPersona = usuario.getIdPersona().getIdPersona().intValue();
            int appModulo = Integer.parseInt(app);
            lstOpciones = menuEJB.getListaOpcionesByIdPersonaAndModulo(idPersona, appModulo);

            if (lstOpciones.isEmpty()) {
                JsfUtil.mensajeError("No tiene derechos para ingresar a este modulo");
            } else {
                Character tipoAcceso = menuEJB.getTipoAcceso(idPersona, appModulo);
                if (tipoAcceso != null) {
                    VarSession.setVariableSession("tipoAcceso", tipoAcceso);
                }
                url = ((Object[]) lstOpciones.get(0))[3].toString() + "?faces-redirect=true";
                armarMenu();
            }
        }
        return url;
    }

    public void refreshEntidad() {
        utilEJB.executeSql(query);
        JsfUtil.mensajeInformacion("Entidad refrescada");
    }

    public String verResumenGeneralContrataciones() {
        if ((Integer) VarSession.getVariableSession("idTipoUsuario") == 1) {
            return "dashboard";
        } else {
            JsfUtil.mensajeAlerta("No posee los privilegios para ver esta información");
        }
        return "";
    }
}
