/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.api.facade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.seguridad.model.Aplicacion;
import sv.gob.mined.seguridad.model.GruApp;
import sv.gob.mined.seguridad.model.Grupo;
import sv.gob.mined.seguridad.model.OpcionMenu;
import sv.gob.mined.seguridad.model.UsuGruApp;
import sv.gob.mined.seguridad.model.Usuario;

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
        Query q = em.createQuery("SELECT a FROM Aplicacion a", Aplicacion.class);
        return q.getResultList();
    }

    public List<Grupo> getLstGrupos() {
        Query q = em.createQuery("SELECT g FROM Grupo g", Grupo.class);
        return q.getResultList();
    }

    public Usuario getUsuarioByLogin(String login) {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.login=:login", Usuario.class);
        q.setParameter("login", login);
        return (q.getResultList().isEmpty() ? new Usuario() : (Usuario) q.getSingleResult());
    }

    public List<GruApp> getGrupoAplicacionByApp(BigDecimal idAplicacion) {
        Query q = em.createQuery("SELECT g FROM GruApp g WHERE g.idAplicacion.idAplicacion=:idApp", GruApp.class);
        q.setParameter("idApp", idAplicacion);
        return q.getResultList();
    }

    public List<UsuGruApp> getLstUsuGrupoAppByUsuAndApp(String login, BigDecimal idAplicacion) {
        Query q = em.createQuery("SELECT u FROM UsuGruApp u WHERE u.idUsuApp.login.login=:login AND u.idGrupoApp.idAplicacion.idAplicacion=:idApp", UsuGruApp.class);
        q.setParameter("login", login);
        q.setParameter("idApp", idAplicacion);
        return q.getResultList();
    }

    public List<OpcionMenu> getLstOpcionMenuByUsuAndApp(String login, BigDecimal idAplicacion) {
        Query q = em.createQuery("SELECT a.idOpcMenu FROM AplicacionOpcMenu a WHERE a.idGrupoApp", OpcionMenu.class);
        return q.getResultList();
    }

    public void guardarGrupo(Grupo grp) {
        if (grp.getIdGrupo() == null) {
            em.persist(grp);
        } else {
            em.merge(grp);
        }
    }

    public GruApp getGruAppById(Long id) {
        return em.find(GruApp.class, id);
    }

    public List<GruApp> getLstAppGrp() {
        Query q = em.createQuery("SELECT g FROM GruApp g", GruApp.class);
        return q.getResultList();
    }
    
    public List<GruApp> getLstAppGrpByUsu(Usuario login) {
        Query q = em.createQuery("SELECT u.idGruApp FROM UsuGruApp u WHERE u.login=:login", GruApp.class);
        q.setParameter("login", login);
        return q.getResultList();
    }

    public void guardarUsuario(Usuario usu) {
        Usuario u = em.find(Usuario.class, usu.getLogin());

        if (u == null) {
            em.persist(usu);
        } else {
            em.merge(usu);
        }
    }

    public void guardarUsuGruApp(List<GruApp> lstGruApp, Usuario login) {
        for (GruApp gruApp : lstGruApp) {
            Query q = em.createQuery("SELECT u FROM UsuGruApp u WHERE u.login=:login and u.idGruApp=:idGruApp", UsuGruApp.class);
            q.setParameter("login", login);
            q.setParameter("idGruApp", gruApp);
            if(q.getResultList().isEmpty()){
                UsuGruApp usuGruApp = new UsuGruApp();
                usuGruApp.setActivo('A');
                usuGruApp.setFechaCreacion(new Date());
                usuGruApp.setIdGruApp(gruApp);
                usuGruApp.setLogin(login);
                usuGruApp.setUsuarioCreacion("ROOT");
                
                em.persist(usuGruApp);
            }
        }
    }
}
