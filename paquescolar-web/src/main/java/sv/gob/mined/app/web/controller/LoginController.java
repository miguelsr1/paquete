/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.EMailEJB;
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
    private String usuarioEmp;
    private String claveEmp;
    @EJB
    private LoginEJB loginEJB;
    @EJB
    private EMailEJB eMailEJB;

    private static final ResourceBundle UTIL_CORREO = ResourceBundle.getBundle("Bundle");

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

    public String getUsuarioEmp() {
        return usuarioEmp;
    }

    public void setUsuarioEmp(String usuarioEmp) {
        this.usuarioEmp = usuarioEmp;
    }

    public String getClaveEmp() {
        return claveEmp;
    }

    public void setClaveEmp(String claveEmp) {
        this.claveEmp = claveEmp;
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
        return validar(usuario, clave, false);
    }

    public String isUsuarioProveedorValido() {
        return validar(usuarioEmp, claveEmp, true);
    }

    private String validar(String usuString, String cla, Boolean proveedor) {
        Usuario usu;

        if (proveedor) {
            usu = loginEJB.isUsuarioProveedorValido(usuString, cla);
        } else {
            usu = loginEJB.isUsuarioValido(usuString, cla);
        }

        if (usu == null) {
            JsfUtil.mensajeError("Se estan presentando problemas de comunicación con la base de datos. NOTIFICAR AL ADMINISTRADOR.");
            return "";
        } else if (usu.getIdUsuario() == null) {
            JsfUtil.mensajeAlerta("Clave y/o Usuario incorrectos.");
            return "";
        } else if (usu.getIdTipoUsuario().getIdTipoUsuario().intValue() == 9) {
            if (usu.getActivo() == 0) {
                JsfUtil.mensajeError("Usuario INACTIVO. No posee derechos de acceso");
                return "";
            } else {
                return usuarioOkRedireccionar(usu);
            }
        } else {
            switch (usu.getActivo().intValue()) {
                case 1:
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
                case 2:
                    JsfUtil.mensajeError("Se envió un correo con un link para reestablecer su clave de acceso, por favor realizar este paso antes de iniciar sesión");
                    return "";
                default:
                    JsfUtil.mensajeError("Usuario INACTIVO. No posee derechos de acceso");
                    return "";
            }
        }
    }

    private String usuarioOkRedireccionar(Usuario usu) {
        VarSession.setVariableSession("Usuario", usu.getIdPersona().getUsuario());
        MenuController mc = ((MenuController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "menuController"));

        if (usu.getIdTipoUsuario().getIdTipoUsuario().intValue() == 9) {
            VarSession.setVariableSession("idEmpresa", usu.getIdPersona().getEmpresaList().get(0).getIdEmpresa());
            return "/app/proveedor/regDatosGenerales?faces-redirect=true";
        } else {
            return "/app/inicial?faces-redirect=true";
        }
    }

    public void asignarNuevaClave() {
        HashMap<String, String> valor = loginEJB.solicitarEnlaceNuevaClave(usuario);

        if (valor.isEmpty()) {
            JsfUtil.mensajeAlerta("Este NIT no existe en la base de proveedores. Recuerde que si es un proveedor con personeria juridica el NIT es del Representante Legal");
        } else {
            String titulo = "Paquete Escolar On Line - Cambiar Clave de Acceso";
            String mensaje = UTIL_CORREO.getString("prov.recordarcontrasenha");

            mensaje = MessageFormat.format(mensaje, valor.get("nombreCompleto"), valor.get("token"));

            List<String> to = new ArrayList();
            List<String> cc = new ArrayList();

            to.add(valor.get("correo"));

            eMailEJB.enviarMail("rafael.arias@mined.gob.sv", titulo, mensaje, to, cc, new ArrayList(), new HashMap<>());
        }
    }
}
