/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.api;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.seguridad.model.Usuario;
import sv.gob.mined.utils.seguridad.Seguridad;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class UsuarioFacade  {

    @PersistenceContext(unitName = "sv.gob.mined_seguridad-model_jar_1.0PU")
    public EntityManager em;

    public Usuario findUsuarioByLogin(String login, String password) {
       Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.login=:login and u.claveAcceso=:pass", Usuario.class);
        q.setParameter("login", login);
        q.setParameter("pass", Seguridad.encriptar(password));
        List<Usuario> lst = q.getResultList();
        if (lst.isEmpty()) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
