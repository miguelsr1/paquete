/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.Anho;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.RubrosAmostrarInteres;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class AnhoProcesoEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public List<Anho> getLstAnhos() {
        Query q = em.createQuery("SELECT a FROM Anho a ORDER BY a.idAnho DESC", Anho.class);
        return q.getResultList();
    }

    public List<Anho> getLstAnhosDesde(String idAnho) {
        Query q = em.createQuery("SELECT a FROM Anho a WHERE a.idAnho > " + idAnho + " ORDER BY a.idAnho DESC", Anho.class);
        return q.getResultList();
    }

    public List<ProcesoAdquisicion> getLstProcesoAdquisicionByAnho(BigDecimal idAnho) {
        Query q = em.createQuery("SELECT p FROM ProcesoAdquisicion p WHERE p.idAnho.idAnho=:idAnho ORDER BY p.idProcesoAdq", ProcesoAdquisicion.class);
        q.setParameter("idAnho", idAnho);
        return q.getResultList();
    }

    public List<RubrosAmostrarInteres> getLstRubros() {
        Query q = em.createQuery("SELECT r FROM RubrosAmostrarInteres r ORDER BY r.idRubroInteres", RubrosAmostrarInteres.class);
        return q.getResultList();
    }

    public List<RubrosAmostrarInteres> getLstRubrosByAnho(String anho) {
        Query q = em.createQuery("SELECT DISTINCT d.idRubroAdq FROM DetalleProcesoAdq d WHERE d.idProcesoAdq.idAnho.anho=:anho ORDER BY d.idRubroAdq.idRubroInteres", RubrosAmostrarInteres.class);
        q.setParameter("anho", anho);
        return q.getResultList();
    }

    public List<RubrosAmostrarInteres> getLstRubros(ProcesoAdquisicion proceso) {
        Query q = em.createQuery("SELECT d.idRubroAdq FROM DetalleProcesoAdq d WHERE d.idProcesoAdq=:proceso ORDER BY d.idRubroAdq.idRubroInteres", RubrosAmostrarInteres.class);
        q.setParameter("proceso", proceso);
        return q.getResultList();
    }

    public List<RubrosAmostrarInteres> getLstRubrosResguardo(ProcesoAdquisicion proceso) {
        Query q = em.createQuery("SELECT d.idRubroAdq FROM DetalleProcesoAdq d WHERE d.idProcesoAdq=:proceso and d.idRubroAdq.idRubroInteres not in (5) ORDER BY d.idRubroAdq.idRubroInteres", RubrosAmostrarInteres.class);
        q.setParameter("proceso", proceso);
        return q.getResultList();
    }

    public List<RubrosAmostrarInteres> getLstRubrosByRubro(BigDecimal... params) {
        List lst = new ArrayList();
        for (BigDecimal param : params) {
            lst.add(param);
        }
        Query q = em.createQuery("SELECT r FROM RubrosAmostrarInteres r WHERE r.idRubroInteres in :lstRubros ORDER BY r.idRubroInteres", RubrosAmostrarInteres.class);
        q.setParameter("lstRubros", lst);
        return q.getResultList();
    }

    public DetalleProcesoAdq getDetProcesoAdq(Integer idProcesoAdquisicion, BigDecimal rubrosAmostrarInteres) {
        Query q = em.createQuery("SELECT d FROM DetalleProcesoAdq d WHERE d.idProcesoAdq.idProcesoAdq =:idProceso and d.idRubroAdq.idRubroInteres =:idRubro ORDER BY d.idDetProcesoAdq", DetalleProcesoAdq.class);
        q.setParameter("idProceso", idProcesoAdquisicion);
        q.setParameter("idRubro", rubrosAmostrarInteres);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (DetalleProcesoAdq) q.getSingleResult();
        }
    }

    public DetalleProcesoAdq getDetProcesoAdq(String anho, BigDecimal rubrosAmostrarInteres) {
        Query q = em.createQuery("SELECT d FROM DetalleProcesoAdq d WHERE d.idProcesoAdq.idAnho.anho=:anho and d.idRubroAdq.idRubroInteres=:idRubro and d.idProcesoAdq.padreIdProcesoAdq is null ORDER BY d.idDetProcesoAdq", DetalleProcesoAdq.class);
        q.setParameter("anho", anho);
        q.setParameter("idRubro", rubrosAmostrarInteres);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (DetalleProcesoAdq) q.getSingleResult();
        }
    }

    public ProcesoAdquisicion getProcesoAdquisicionByIdAnho(String idAnho) {
        Query q = em.createQuery("SELECT p FROM ProcesoAdquisicion p WHERE p.idAnho.idAnho=:idAnho and p.padreIdProcesoAdq is null", ProcesoAdquisicion.class);
        q.setParameter("idAnho", new BigDecimal(idAnho));
        return (ProcesoAdquisicion) q.getResultList().get(0);
    }
}
