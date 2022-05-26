package sv.gob.mined.paquescolar.ejb;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sv.gob.mined.paquescolar.util.Filtro;

/**
 *
 * @author misanchez
 * @param <Entity> Entidad
 * @param <PrimaryKey>
 */
public abstract class AbstractFacade<Entity, PrimaryKey> {

    @PersistenceContext
    public EntityManager em;

    private final Class<Entity> entityClass;

    public AbstractFacade(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(Entity ent) {
        em.persist(ent);
    }

    public void update(Entity ent) {
        em.merge(ent);
    }

    public List<Entity> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entity> cq = cb.createQuery(entityClass);

        return em.createQuery(cq).getResultList();
    }

    public Entity findByPk(PrimaryKey pk) {
        return em.find(entityClass, pk);
    }

    public List<?> findLstByParam(Map<String, Object> parametros) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entity> cq = cb.createQuery(entityClass);
        Root<Entity> root = cq.from(entityClass);

        for (Map.Entry<String, Object> entry : parametros.entrySet()) {
            cq.select(root).where(cb.equal(root.get(entry.getKey()), entry.getValue()));
        }

        return em.createQuery(cq).getResultList();
    }

    public Entity findEntityByParam(List<Filtro> parametros) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entity> cq = cb.createQuery(entityClass);
        Root<Entity> root = cq.from(entityClass);
        for (Filtro parametro : parametros) {
            switch (parametro.getTipoOperacion()) {
                case 1://EQUALS
                    cq.select(root).where(cb.equal(root.get(parametro.getClave()), parametro.getValor()));
                    break;
                case 2://LIKE
                    cq.select(root).where(cb.like(root.get(parametro.getClave()), "%" + parametro.getValor() + "%"));
                    break;
            }
        }

        return em.createQuery(cq).getResultList().isEmpty() ? null : em.createQuery(cq).getResultList().get(0);
    }
}
