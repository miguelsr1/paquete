/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.Genero;
import sv.gob.mined.paquescolar.model.Persona;
import sv.gob.mined.paquescolar.model.TipoUsuario;
import sv.gob.mined.paquescolar.model.Usuario;
import sv.gob.mined.paquescolar.util.RC4Crypter;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class PersonaEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;

    public List<Persona> buscarUsuarioByUsuarioOrNombre(String valor) {
        Query q = em.createQuery("SELECT u.idPersona FROM Usuario u WHERE UPPER(CONCAT(u.idPersona.primerNombre, u.idPersona.segundoNombre)) like :valor OR UPPER(u.idPersona.usuario) like :valor", Persona.class);
        q.setParameter("valor", "%" + valor.replace(" ", "").trim().toUpperCase() + "%");
        return q.getResultList();
    }

    public List<Persona> buscarNitPersona(String nit) {
        String query = "SELECT p FROM Persona p WHERE p.numeroNit = :nit";
        Query q = em.createQuery(query, Persona.class);
        q.setParameter("nit", nit);

        return q.getResultList();
    }

    public List<Persona> buscarUsuario(String usuario) {
        String query = "SELECT p FROM Persona p WHERE p.usuario = :usuario";
        Query q = em.createQuery(query, Persona.class);
        q.setParameter("usuario", usuario);
        return q.getResultList();
    }

    public void create(Persona persona) {
        em.persist(persona);
    }

    public Persona edit(Persona persona) {
        return em.merge(persona);
    }

    public void setOpcionesUsuario(Usuario usuarioObj, Persona persona) {
        if (usuarioObj.getIdUsuario() == null) {
            em.persist(usuarioObj);
        } else {
            em.merge(usuarioObj);
        }

        switch (usuarioObj.getIdTipoUsuario().getIdTipoUsuario().intValue()) {
            case 1: // ADMINISTRADOR
                //setOpcionesUsuario("21,22,25,26,28,29,30,33", usuarioObj.getIdUsuario());
                break;
            case 2: //DIGITADOR FERIA
                setOpcionesUsuario("31,32,34,39,42,50,55,56,58,59", usuarioObj.getIdUsuario());
                break;
            case 3: //DIGITADOR DEPARTAMENTAL
                setOpcionesUsuario("31,32,34,39,42,43,45,50,61,62,68", usuarioObj.getIdUsuario());
                break;
            case 4: //ENTIDAD FINANCIERA
                setOpcionesUsuario("23,26", usuarioObj.getIdUsuario());
                break;
            case 5: // PAGADOR DEPARTAMENTAL
                setOpcionesUsuario("24,31,32,34,39,42,43,45,50,61,62,67,68", usuarioObj.getIdUsuario());
            case 6: // REFERENTE DEPARTAMENTAL
                setOpcionesUsuario("31,32,34,39,42,43,45,50,61,62,68", usuarioObj.getIdUsuario());
                break;
        }
    }

    private void setOpcionesUsuario(String lstOpciones, BigDecimal idUsuario) {
        int executeUpdate;
        String opciones[] = lstOpciones.split(",");
        String sql = "delete from usuario_opc_menu where ID_USUARIO =?1";

        Query q = em.createNativeQuery(sql);
        q.setParameter(1, idUsuario);
        executeUpdate = q.executeUpdate();

        sql = "insert into usuario_opc_menu (ID_OPC_MENU,ID_USUARIO) values(?1,?2)";
        for (String string : opciones) {
            q = em.createNativeQuery(sql);
            q.setParameter(1, new BigDecimal(string));
            q.setParameter(2, idUsuario);
            executeUpdate = q.executeUpdate();
        }
    }

    public List<TipoUsuario> getLstTipoUsuario() {
        Query q = em.createQuery("SELECT t FROM TipoUsuario t ORDER BY t.idTipoUsuario", TipoUsuario.class);
        return q.getResultList();
    }

    public String encriptarclave(String clave) {
        String claveencriptada;
        RC4Crypter sec = new RC4Crypter();
        claveencriptada = sec.encrypt("ha", clave);
        return claveencriptada;
    }

    public Boolean validarPassword(String clave1, String clave2) {
        if (clave1 != null && !clave1.isEmpty() && clave2 != null && !clave2.isEmpty()) {
            return !clave1.equals(clave2);
        }
        return false;
    }

    public List<Usuario> findUsuarioByNit(String nit) {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.idPersona.numeroNit=:nit and u.estadoEliminacion=0", Usuario.class);
        q.setParameter("nit", nit);

        return q.getResultList();
    }

    public List<Genero> getLstGenero() {
        Query q = em.createQuery("SELECT g FROM Genero g ORDER BY g.idGenero", Genero.class);
        return q.getResultList();
    }
}
