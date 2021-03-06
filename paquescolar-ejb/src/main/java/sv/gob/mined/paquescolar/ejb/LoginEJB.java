/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.Persona;
import sv.gob.mined.paquescolar.model.Usuario;
import sv.gob.mined.paquescolar.util.RC4Crypter;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class LoginEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    public Usuario isUsuarioValido(String usuario, String clave) {
        Usuario usu = null;
        try {
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.idPersona.usuario=:usuario and u.idPersona.claveAcceso=:clave and u.estadoEliminacion=0", Usuario.class);
            q.setParameter("usuario", usuario);
            q.setParameter("clave", (new RC4Crypter()).encrypt("ha", clave));

            if (!q.getResultList().isEmpty()) {
                usu = (Usuario) q.getResultList().get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return usu;
        }
    }

    public Persona findPersona(BigDecimal id) {
        return em.find(Persona.class, id);
    }

    public List<Usuario> findUsuarios() {
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.estadoEliminacion=0 AND U.idTipoUsuario.idTipoUsuario=4", Usuario.class);
        return query.getResultList();
    }

    public BigInteger findTipoUsuarioByIdPersona(BigDecimal idPersona) {
        Query q = em.createNativeQuery("SELECT id_tipo_usuario FROM usuario WHERE id_persona = ?1");
        q.setParameter(1, idPersona);
        Object obj = q.getSingleResult();
        return new BigInteger(obj.toString());
    }
}
