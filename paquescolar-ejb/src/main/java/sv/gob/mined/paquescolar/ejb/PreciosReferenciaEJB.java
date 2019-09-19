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
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.PreciosRefRubro;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class PreciosReferenciaEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public PreciosRefRubro findPreciosRefRubroByNivelEduAndRubro(BigDecimal nivelEdu, DetalleProcesoAdq rubro) {
        PreciosRefRubro pr = new PreciosRefRubro();
        NivelEducativo n = em.find(NivelEducativo.class, nivelEdu);
        pr.setIdNivelEducativo(n);
        pr.setPrecioMaxFem(BigDecimal.ZERO);
        pr.setPrecioMaxMas(BigDecimal.ZERO);

        if (rubro == null || nivelEdu == null) {
            return pr;
        }
        Query q = em.createQuery("SELECT p FROM PreciosRefRubro p WHERE p.idNivelEducativo.idNivelEducativo=:nivelEdu and p.idDetProcesoAdq.idProcesoAdq.idAnho.anho=:anho and p.idDetProcesoAdq.idRubroAdq.idRubroInteres=:idRubro", PreciosRefRubro.class);
        q.setParameter("nivelEdu", nivelEdu);
        q.setParameter("anho", rubro.getIdProcesoAdq().getIdAnho().getAnho());
        q.setParameter("idRubro", rubro.getIdRubroAdq().getIdRubroInteres());

        if (q.getResultList().isEmpty()) {
            return pr;
        } else {
            return (PreciosRefRubro) q.getSingleResult();
        }
    }
    
    public List<PreciosRefRubro> getLstPreciosRefRubroByRubro(DetalleProcesoAdq rubro) {
        Query q = em.createQuery("SELECT p FROM PreciosRefRubro p WHERE p.idDetProcesoAdq.idProcesoAdq.idAnho.anho=:anho and p.idDetProcesoAdq.idRubroAdq.idRubroInteres=:idRubro", PreciosRefRubro.class);
        q.setParameter("anho", rubro.getIdProcesoAdq().getIdAnho().getAnho());
        q.setParameter("idRubro", rubro.getIdRubroAdq().getIdRubroInteres());

        return q.getResultList();
    }
}
