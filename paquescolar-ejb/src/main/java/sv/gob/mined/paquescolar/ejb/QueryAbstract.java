/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.mined.paquescolar.ejb;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author misanchez
 * @param <Entity>
 */
public abstract class QueryAbstract<Entity> {

    @PersistenceContext
    private EntityManager em;
    private Class<Entity> entityClass;

    public QueryAbstract(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }

    public List<?> findByParam(Entity entity, List<Predicate> lstParametros) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entity> cq = cb.createQuery(entityClass);
        Root<Entity> root = cq.from(entityClass);
        
        for (Predicate parametro : lstParametros) {
            cq.select(root).where(parametro);
        }
        
        return em.createQuery(cq).getResultList();
    }
}
