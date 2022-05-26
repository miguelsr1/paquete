package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.HistorialLiquiFinan;
import sv.gob.mined.paquescolar.model.LiquidacionFinanciera;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.VwLiqFinanConModifDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.VwLiqFinanConOrigDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.VwLiqFinanConRecepDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.VwLiqFinanDetPlanillaDto;

/**
 *
 * @author misanchez
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LiquidacionFinancieraEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    public VwLiqFinanConOrigDto findContratoOriginal(BigDecimal idContrato) {
        Query q = em.createNamedQuery("defaultVwLiqFinanConOrigDto", VwLiqFinanConOrigDto.class);
        q.setParameter(1, idContrato);
        return q.getResultList().isEmpty() ? new VwLiqFinanConOrigDto() : (VwLiqFinanConOrigDto) q.getSingleResult();
    }

    public VwLiqFinanConModifDto findContratoModificativa(BigDecimal idContrato) {
        Query q = em.createNamedQuery("defaultVwLiqFinanConModifDto", VwLiqFinanConModifDto.class);
        q.setParameter(1, idContrato);
        return q.getResultList().isEmpty() ? new VwLiqFinanConModifDto() : (VwLiqFinanConModifDto) q.getSingleResult();
    }

    public VwLiqFinanConRecepDto findContratoRecepcion(BigDecimal idContrato) {
        Query q = em.createNamedQuery("defaultVwLiqFinanConRecepDto", VwLiqFinanConRecepDto.class);
        q.setParameter(1, idContrato);
        return q.getResultList().isEmpty() ? new VwLiqFinanConRecepDto() : (VwLiqFinanConRecepDto) q.getSingleResult();
    }
    
    public VwLiqFinanDetPlanillaDto findContratoDetPago(BigDecimal idContrato) {
        Query q = em.createNamedQuery("defaultVwLiqFinanDetPlanillaDto", VwLiqFinanDetPlanillaDto.class);
        q.setParameter(1, idContrato);
        return q.getResultList().isEmpty() ? new VwLiqFinanDetPlanillaDto() : (VwLiqFinanDetPlanillaDto) q.getResultList().get(0);
    }

    public LiquidacionFinanciera findLiquidacion(BigDecimal idContrato) {
        Query q = em.createQuery("SELECT l FROM LiquidacionFinanciera l WHERE l.idContrato = :pIdContrato", LiquidacionFinanciera.class);
        q.setParameter("pIdContrato", idContrato);
        return q.getResultList().isEmpty() ? new LiquidacionFinanciera() : (LiquidacionFinanciera) q.getResultList().get(0);
    }

    public void guardarLiquidacion(LiquidacionFinanciera liquidacionFinan) {
        if (liquidacionFinan.getIdLiquidacionFinanciera() == null) {
            em.persist(liquidacionFinan);
        } else {
            em.merge(liquidacionFinan);
        }
    }

    public void guardarHistorico(HistorialLiquiFinan historico) {
        em.persist(historico);
    }
}
