/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.LoginEJB;
import sv.gob.mined.paquescolar.model.Usuario;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class LoginController implements Serializable {

    private String usuario;
    private String clave;
    @EJB
    private LoginEJB loginEJBLocal;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }

    @PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        VarSession.setVariableSession("host", request.getServerName());
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void dialogReasignarClave() {
        Map<String, Object> options = new HashMap();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 240);
        options.put("contentWidth", 339);

        PrimeFaces.current().dialog().openDynamic("app/comunes/reasignarClave", options, null);
    }

    public String isUsuarioValido() {
        Usuario usu = loginEJBLocal.isUsuarioValido(usuario, clave);

        if (usu == null) {
            JsfUtil.mensajeError("Clave y/o Usuario incorrectos.");
            return "";
        } else {
            if (usu.getActivo().intValue() == 1) {
                if (usu.getRangoFechaLogin().intValue() == 0) {
                    return usuarioOkRedireccionar(usu);
                } else {
                    if (!((new Date()).after(usu.getFechaInicioLogin()) && (new Date()).before(usu.getFechaFinLogin()))) {
                        JsfUtil.mensajeError("Ha concluido su periodo de actividad en el sistema. No posee derechos de acceso");
                        return "";
                    } else {
                        return usuarioOkRedireccionar(usu);
                    }
                }
            } else {
                JsfUtil.mensajeError("Usuario INACTIVO. No posee derechos de acceso");
                return "";
            }
        }
    }

    private String usuarioOkRedireccionar(Usuario usu) {
        VarSession.setVariableSession("Usuario", usu.getIdPersona().getUsuario());
        MenuController mc = ((MenuController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "menuController"));
        return "/app/inicial?faces-redirect=true";
    }
}
