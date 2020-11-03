package sv.gob.mined.cooperacion.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.cooperacion.model.dto.ListadoProyectoDto;
import sv.gob.mined.cooperacion.util.QueryNativas;

@Stateless
public class MantenimientoFacade {

    @PersistenceContext(unitName = "cooperacionPU")
    private EntityManager emCooperacion;

    public Boolean guardar(Object obj) {
        try {
            emCooperacion.persist(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public <T extends Object> T modificar(T entity) {
        emCooperacion.merge(entity);
        return entity;
    }

    public List<ListadoProyectoDto> findAllProyectos() {
        Query q = emCooperacion.createNativeQuery(QueryNativas.sqlListadoProyectos + " ORDER BY mun.codigo_departamento, mun.codigo_municipio, vw.codigo_entidad", ListadoProyectoDto.class);
        return q.getResultList();
    }
}
