/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
            } else {
                usu = new Usuario();
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        } finally {
            return usu;
        }
    }

    public Usuario isUsuarioProveedorValido(String usuario, String clave) {
        Usuario usu = null;
        try {
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.idPersona.usuario=:usuario and u.idPersona.claveAcceso=:clave and u.estadoEliminacion=0 and u.idPersona.estadoEliminacion=0", Usuario.class);
            q.setParameter("usuario", usuario);
            q.setParameter("clave", (new RC4Crypter()).encrypt("ha", clave));

            if (!q.getResultList().isEmpty()) {
                usu = (Usuario) q.getResultList().get(0);
            } else {
                usu = new Usuario();
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
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

    public HashMap<String, String> solicitarEnlaceNuevaClave(String numeroNit) {

        HashMap<String, String> mapa = new HashMap<>();

        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.idPersona.numeroNit=:nit", Usuario.class);
        q.setParameter("nit", numeroNit);

        Usuario usu = q.getResultList().isEmpty() ? null : (Usuario) q.getResultList().get(0);

        if (usu != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
            usu.setActivo((short) 0);
            usu.setTokenCambioClave(new RC4Crypter().encrypt("ha", numeroNit.concat(":").concat(sdf.format(new Date()))));

            em.merge(usu);
            mapa.put("correo", usu.getIdPersona().getEmail());
            mapa.put("nombreCompleto", usu.getIdPersona().getNombreCompleto());
            mapa.put("token", usu.getTokenCambioClave());
            usu.getTokenCambioClave();
        }

        return mapa;
    }

    public String getNombreByUsername(String username) {
        Query q = em.createNativeQuery("select PRIMER_NOMBRE || NVL(' ' ||SEGUNDO_NOMBRE, '') || NVL(' ' ||  PRIMER_APELLIDO, '') || NVL(' ' ||SEGUNDO_APELLIDO, '') || NVL(' ' ||ACASADA, '') from persona WHERE usuario= ?1");
        q.setParameter(1, username);
        return (String) q.getResultList().get(0);
    }

    public void actualizarClaveAcceso(String username, String clave) {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.idPersona.usuario=:user", Usuario.class);
        q.setParameter("user", username);
        
        Usuario usuario = (Usuario) q.getSingleResult();
        usuario.setCambiarClave((short) 0);
        
        Persona persona = usuario.getIdPersona();
        persona.setClaveAcceso((new RC4Crypter()).encrypt("ha", clave));

        em.merge(usuario);
        em.merge(persona);
    }
}
