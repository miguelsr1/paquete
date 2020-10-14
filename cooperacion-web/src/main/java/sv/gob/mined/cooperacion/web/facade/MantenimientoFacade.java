package sv.gob.mined.cooperacion.web.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MantenimientoFacade {

    @PersistenceContext(unitName = "cooperacionPU")
    private EntityManager emCooperacion;

    public void guardar(Object obj) {
        emCooperacion.persist(obj);
    }

    public <T extends Object> T modificar(T entity) {
        emCooperacion.merge(entity);
        return entity;
    }
}
