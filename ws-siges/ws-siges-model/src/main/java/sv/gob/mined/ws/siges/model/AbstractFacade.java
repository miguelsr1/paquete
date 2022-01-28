/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.ws.siges.model;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author misanchez
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractFacade.class.getName());
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    protected abstract EntityManager getEntityManager();
    
    public void create(T entity){
        getEntityManager().persist(entity);
    }
    
    public void edit(T entity){
        getEntityManager().merge(entity);
    }
    
    public void remove(T entity){
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    public T find(Object id){
        return getEntityManager().find(entityClass, id);
    }
    
    public List<T> findAll(){
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
}
