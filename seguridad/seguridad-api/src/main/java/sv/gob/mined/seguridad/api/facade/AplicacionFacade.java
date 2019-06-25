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
import sv.gob.mined.seguridad.model.GruOpcMenu;
import sv.gob.mined.seguridad.model.Grupo;
import sv.gob.mined.seguridad.model.OpcionMenu;
import sv.gob.mined.seguridad.model.UsuGruApp;
import sv.gob.mined.seguridad.model.Usuario;
import sv.gob.mined.seguridad.model.dto.OpcionMenuDto;

/**
 *
 * @author martin
 */
@Stateless
@LocalBean
public class AplicacionFacade {

    @PersistenceContext(unitName = "seguridadv2-UP")
    private EntityManager em;

    public List<Aplicacion> getLstAplicaciones() {
        Query q = em.createQuery("SELECT a FROM Aplicacion a", Aplicacion.class);
        return q.getResultList();
    }

    public List<Grupo> getLstGruposNotInIdApp(Long idApp) {
        Query q = em.createQuery("SELECT g.idGrupo FROM GruApp g WHERE g.idAplicacion.idAplicacion = :idApp ORDER BY g.idGrupo.idGrupo", Grupo.class);
        q.setParameter("idApp", idApp);
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

    public List<OpcionMenuDto> getLstOpcionMenuNotInIdApp(Long idApp) {
        Query q = em.createNamedQuery("Seguridad.OpcMenuNotInIdApp", OpcionMenuDto.class);
        q.setParameter(1, idApp);
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

    public GruApp getGruAppByIdAppAndIdGru(Long idApp, Long idGru, String login) {
        Query q = em.createQuery("SELECT g FROM GruApp g WHERE g.idGrupo.idGrupo=:idGru and g.idAplicacion.idAplicacion=:idApp", GruApp.class);
        q.setParameter("idGru", idGru);
        q.setParameter("idApp", idApp);

        if (q.getResultList().isEmpty()) {
            GruApp gruApp = new GruApp();
            gruApp.setActivo('1');
            gruApp.setFechaCreacion(new Date());
            gruApp.setIdAplicacion(em.find(Aplicacion.class, idApp));
            gruApp.setIdGrupo(em.find(Grupo.class, idGru));
            gruApp.setUsuarioCreacion(login);

            em.persist(gruApp);

            return gruApp;
        } else {
            return (GruApp) q.getSingleResult();
        }
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

    /**
     * Asociar los grupos, de una aplicación en particular, a un usuario
     * especifico
     *
     * @param lstGruApp
     * @param login
     */
    public void guardarUsuGruApp(List<GruApp> lstGruApp, Usuario login) {
        for (GruApp gruApp : lstGruApp) {
            Query q = em.createQuery("SELECT u FROM UsuGruApp u WHERE u.login=:login and u.idGruApp=:idGruApp", UsuGruApp.class);
            q.setParameter("login", login);
            q.setParameter("idGruApp", gruApp);
            if (q.getResultList().isEmpty()) {
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

    public void guardarOpcMenuToGruApp(List<Long> lstIdsOpcMenu, Long idGruApp) {
        for (Long idOpcMenu : lstIdsOpcMenu) {
            Query q = em.createQuery("SELECT g FROM GruOpcMenu g WHERE g.idGruApp.idGruApp=:idGruApp and g.idOpcMenu.idOpcMenu=:idOpcMenu", GruOpcMenu.class);
            q.setParameter("idGruApp", idGruApp);
            q.setParameter("idOpcMenu", idOpcMenu);

            if (q.getResultList().isEmpty()) {
                GruOpcMenu gom = new GruOpcMenu();
                gom.setIdGruApp(em.find(GruApp.class, idGruApp));
                gom.setIdOpcMenu(em.find(OpcionMenu.class, idOpcMenu));

                em.persist(gom);
            }
        }
    }

    //public void guardarOpcMenuToUsuGruApp
    /**
     * Método que devuelve true si la opción, que se recibe como parametro, ya
     * la posee el GruApp
     *
     * @param padreIdOpcMenu
     * @param idGruApp
     * @return TRUE si la opción existe para el usuario
     */
    public Boolean isExisteOpcionPadreToGruApp(Long padreIdOpcMenu, Long idGruApp) {
        Query q = em.createQuery("SELECT g FROM GruOpcMenu g WHERE g.idGruApp.idGruApp=:idGruApp and g.idOpcMenu.idOpcMenu=:idOpcMenu", GruOpcMenu.class);
        q.setParameter(1, padreIdOpcMenu);
        q.setParameter(2, idGruApp);
        if (q.getResultList().isEmpty()) {

        } else {
            GruOpcMenu gom = (GruOpcMenu) q.getSingleResult();

        }
        return q.getResultList().isEmpty();
    }

    public List<Long> validarOpcPadreToGruApp(Long idOpcMenu, Long idGruApp, List<Long> lst) {
        Query q = em.createQuery("SELECT g FROM GruOpcMenu g WHERE g.idGruApp.idGruApp=:idGruApp and g.idOpcMenu.idOpcMenu=:idOpcMenu", GruOpcMenu.class);
        q.setParameter("idOpcMenu", idOpcMenu);
        q.setParameter("idGruApp", idGruApp);

        if (q.getResultList().isEmpty()) {
            lst.add(idOpcMenu);
            OpcionMenu opcionMenu = em.find(OpcionMenu.class, idOpcMenu);
            if(opcionMenu.getPadreIdOpcMenu()!=null){
                validarOpcPadreToGruApp(opcionMenu.getPadreIdOpcMenu().getIdOpcMenu(), idGruApp, lst);
            }
        } else {
            GruOpcMenu gom = (GruOpcMenu) q.getSingleResult();
            if (gom.getIdOpcMenu().getPadreIdOpcMenu() != null) {
                validarOpcPadreToGruApp(gom.getIdOpcMenu().getPadreIdOpcMenu().getIdOpcMenu(), idGruApp, lst);
            }
        }

        return lst;
    }

}
