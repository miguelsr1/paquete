/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.Departamento;
import sv.gob.mined.paquescolar.model.OpcionMenu;
import sv.gob.mined.paquescolar.model.Usuario;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class MenuEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Deprecated
    public Character getTipoAcceso(Integer idPersona, Integer appModulo) {
        Query query = em.createQuery("SELECT u.tipoAcceso FROM UsuarioModulo u WHERE u.idUsuario.idPersona.idPersona=:idPersona and u.activo=1 and u.idModulo.idModulo=:appModulo");
        query.setParameter("idPersona", idPersona);
        query.setParameter("appModulo", appModulo);
        if (query.getResultList().isEmpty()) {
            return null;
        } else {
            return (Character) query.getSingleResult();
        }
    }

    @Deprecated
    public List getListaOpcionesByIdPersonaAndModulo(Integer idPersona, Integer appModulo) {
        try {
            String sql = "SELECT OPCION_MENU.* "
                    + "FROM OPCION_MENU "
                    + "INNER JOIN USUARIO_OPC_MENU ON USUARIO_OPC_MENU.ID_OPC_MENU = OPCION_MENU.ID_OPC_MENU "
                    + "INNER JOIN USUARIO ON USUARIO_OPC_MENU.ID_USUARIO = USUARIO.ID_USUARIO "
                    + "INNER JOIN PERSONA ON USUARIO.ID_PERSONA = PERSONA.ID_PERSONA "
                    + "WHERE PERSONA.ID_PERSONA = ?1 and OPCION_MENU.APP = ?2 "
                    + " ORDER BY OPCION_MENU.ORDEN";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, idPersona);
            query.setParameter(2, appModulo);

            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(MenuEJB.class.getName()).log(Level.WARNING, "Error en generacion de Menu. idPersona {0} y appModulo {1}", new Object[]{idPersona, appModulo});
            return new ArrayList();
        }
    }

    public Usuario getUsuarioByUsuario(String usuario) {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.idPersona.usuario=:usu and u.estadoEliminacion=0", Usuario.class);
        q.setParameter("usu", usuario);
        return (Usuario) q.getSingleResult();
    }

    public Departamento getCodigoDepartamento(BigDecimal idPersona) {
        try {
            Query query = em.createQuery("SELECT u.codigoDepartamento FROM Usuario u WHERE u.idPersona.idPersona=:idPersona");
            query.setParameter("idPersona", idPersona);
            if (query.getResultList().isEmpty()) {
                return null;
            } else {
                String codDepa = query.getSingleResult().toString();
                query = em.createQuery("SELECT d FROM Departamento d WHERE d.codigoDepartamento=:codDepa");
                query.setParameter("codDepa", codDepa);
                return (Departamento) query.getSingleResult();
            }
        } finally {
        }
    }

    @Deprecated
    public List getOpcionesPadre(Integer idPersona, Integer appModulo, Integer idOpcMenu) {
        String sql;

        if (appModulo == null) {
            sql = "SELECT OPCION_MENU.* "
                    + "FROM OPCION_MENU "
                    + "INNER JOIN USUARIO_OPC_MENU ON USUARIO_OPC_MENU.ID_OPC_MENU = OPCION_MENU.ID_OPC_MENU "
                    + "INNER JOIN USUARIO ON USUARIO_OPC_MENU.ID_USUARIO = USUARIO.ID_USUARIO "
                    + "INNER JOIN PERSONA ON USUARIO.ID_PERSONA = PERSONA.ID_PERSONA "
                    + "WHERE PERSONA.ID_PERSONA = ?1 and OPCION_MENU.OPC_ID_OPC_MENU is null " + (idOpcMenu != null ? "and opcion_menu.id_opc_menu = ?2" : "")
                    + " ORDER BY OPCION_MENU.app, OPCION_MENU.ORDEN";
        } else {
            sql = "SELECT OPCION_MENU.* "
                    + "FROM OPCION_MENU "
                    + "INNER JOIN USUARIO_OPC_MENU ON USUARIO_OPC_MENU.ID_OPC_MENU = OPCION_MENU.ID_OPC_MENU "
                    + "INNER JOIN USUARIO ON USUARIO_OPC_MENU.ID_USUARIO = USUARIO.ID_USUARIO "
                    + "INNER JOIN PERSONA ON USUARIO.ID_PERSONA = PERSONA.ID_PERSONA "
                    + "WHERE PERSONA.ID_PERSONA = ?1 and OPCION_MENU.APP = ?2 and OPCION_MENU.OPC_ID_OPC_MENU is not null "
                    + " ORDER BY OPCION_MENU.app, OPCION_MENU.ORDEN";
        }
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, idPersona);
        if (appModulo != null) {
            query.setParameter(2, appModulo);
        } else if (idOpcMenu != null) {
            query.setParameter(2, idOpcMenu);
        }

        return query.getResultList();
    }

    public List<OpcionMenu> getOpciones(Integer idUsuario, Integer idOpcMenu, Boolean isPadre) {
        Query q = em.createNativeQuery("select om.* \n"
                + "from \n"
                + "    OPCION_MENU om\n"
                + "    inner join USUARIO_OPC_MENU uom on uom.id_opc_menu = OM.ID_OPC_MENU\n"
                + "where \n"
                + "    id_usuario = " + idUsuario + " and om.opc_id_opc_menu " + (isPadre ? " = " + idOpcMenu : " is null ") + "\n"
                + "order by\n"
                + "    app, orden");
        return q.getResultList();
    }
}
