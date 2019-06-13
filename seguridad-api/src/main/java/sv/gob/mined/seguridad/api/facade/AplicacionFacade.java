/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.api.facade;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.seguridad.model.Aplicacion;
import sv.gob.mined.seguridad.model.Grupo;
import sv.gob.mined.seguridad.model.GrupoAplicacion;
import sv.gob.mined.seguridad.model.OpcionMenu;
import sv.gob.mined.seguridad.model.Usuario;
import sv.gob.mined.seguridad.model.UsuarioGrupoAplicacion;

/**
 *
 * @author martin
 */
@Stateless
@LocalBean
public class AplicacionFacade {

    @PersistenceContext(unitName = "seguridadv2-UP")
    private EntityManager em;

    public void businessMethod() {
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public List<Aplicacion> getLstAplicaciones() {
        Query q = em.createQuery("SELECT a FROM Aplicacion", Aplicacion.class);
        return q.getResultList();
    }

    public List<Grupo> getLstGrupos() {
        Query q = em.createQuery("SELECT g FROM Grupo", Grupo.class);
        return q.getResultList();
    }

    public Usuario getUsuarioByLogin(String login) {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.login=:login", Usuario.class);
        q.setParameter("login", login);
        return (q.getResultList().isEmpty() ? new Usuario() : (Usuario) q.getSingleResult());
    }
    
    public List<GrupoAplicacion> getGrupoAplicacionByApp(BigDecimal idAplicacion){
        Query q = em.createQuery("SELECT g FROM GrupoAplicacion g WHERE g.idAplicacion.idAplicacion=:idApp", GrupoAplicacion.class);
        q.setParameter("idApp", idAplicacion);
        return q.getResultList();
    }
    
    public List<UsuarioGrupoAplicacion> getLstUsuGrupoAppByUsuAndApp(String login, BigDecimal idAplicacion){
        Query q = em.createQuery("SELECT u FROM UsuarioGrupoAplicacion u WHERE u.idUsuApp.login.login=:login AND u.idGrupoApp.idAplicacion.idAplicacion=:idApp", UsuarioGrupoAplicacion.class);
        q.setParameter("login", login);
        q.setParameter("idApp", idAplicacion);
        return q.getResultList();
    }
    
    public List<OpcionMenu> getLstOpcionMenuByUsuAndApp(String login, BigDecimal idAplicacion){
        Query q = em.createQuery("SELECT a.idOpcMenu FROM AplicacionOpcMenu a WHERE a.idGrupoApp", OpcionMenu.class);
        return q.getResultList();
    }
}
