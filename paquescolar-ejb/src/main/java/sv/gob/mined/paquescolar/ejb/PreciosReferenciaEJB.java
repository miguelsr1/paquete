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
        Query q = em.createQuery("SELECT p FROM PreciosRefRubro p WHERE p.idNivelEducativo.idNivelEducativo=:nivelEdu and p.idRubroInteres.idRubroInteres=:pIdRubro and p.idAnho.idAnho=:pIdAnho", PreciosRefRubro.class);
        q.setParameter("nivelEdu", nivelEdu);
        q.setParameter("pIdRubro", rubro.getIdRubroAdq().getIdRubroInteres());
        q.setParameter("pIdAnho", rubro.getIdProcesoAdq().getIdAnho().getIdAnho());

        if (q.getResultList().isEmpty()) {
            return pr;
        } else {
            return (PreciosRefRubro) q.getSingleResult();
        }
    }

    public List<PreciosRefRubro> getLstPreciosRefRubroByRubro(DetalleProcesoAdq rubro) {
        Query q = em.createNativeQuery("select prr.* \n"
                + "from PRECIOS_REF_RUBRO prr \n"
                + "    inner join NIVEL_EDUCATIVO niv on niv.ID_NIVEL_EDUCATIVO = prr.ID_NIVEL_EDUCATIVO \n"
                + "    inner join detalle_proceso_adq dpa on dpa.id_det_proceso_adq = prr.id_det_proceso_adq\n"
                + "    inner join proceso_adquisicion pa on dpa.id_proceso_adq = pa.id_proceso_adq\n"
                + "    inner join anho on pa.id_anho = anho.id_anho\n"
                + "where anho.id_anho = ?1 and dpa.id_rubro_adq = ?2\n"
                + "order by niv.ORDEN2", PreciosRefRubro.class);
        q.setParameter(1, rubro.getIdProcesoAdq().getIdAnho().getIdAnho());
        q.setParameter(2, rubro.getIdRubroAdq().getIdRubroInteres());

        return q.getResultList();
    }
    
    public List<PreciosRefRubro> getLstPreciosRefRubroByRubro(DetalleProcesoAdq rubro, Boolean ceClimaFrio) {
        Query q;

        if (rubro.getIdRubroAdq().getIdRubroUniforme().intValue() == 1) {
            if (ceClimaFrio) {
                q = em.createQuery("SELECT p FROM PreciosRefRubro p WHERE p.idAnho.idAnho = :pIdAnho and p.idRubroInteres.idRubroInteres = :pIdRubro and ((p.idNivelEducativo.idNivelEducativo = 22 and p.climaFrio=1) or (p.idNivelEducativo.idNivelEducativo in (2,6) and p.climaFrio=0)) ORDER BY p.idNivelEducativo.orden2", PreciosRefRubro.class);
            } else {
                q = em.createQuery("SELECT p FROM PreciosRefRubro p WHERE p.idAnho.idAnho = :pIdAnho and p.idRubroInteres.idRubroInteres = :pIdRubro and p.idNivelEducativo.idNivelEducativo in (22, 2, 6) and p.climaFrio=0 ORDER BY p.idNivelEducativo.orden2", PreciosRefRubro.class);
            }
        } else {
            q = em.createQuery("SELECT p FROM PreciosRefRubro p WHERE p.idAnho.idAnho = :pIdAnho and p.idRubroInteres.idRubroInteres = :pIdRubro ORDER BY p.idNivelEducativo.orden2", PreciosRefRubro.class);
        }

        q.setParameter("pIdAnho", rubro.getIdProcesoAdq().getIdAnho().getIdAnho());
        q.setParameter("pIdRubro", rubro.getIdRubroAdq().getIdRubroInteres());

        return q.getResultList();
    }
}
