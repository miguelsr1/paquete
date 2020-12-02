package sv.gob.mined.cooperacion.facade;

import java.math.BigDecimal;
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

    public <T extends Object> T find(Class<T> entityClass, Object primaryKey) {
        return emCooperacion.find(entityClass, primaryKey);
    }

    public <T extends Object> T modificar(T entity) {
        emCooperacion.merge(entity);
        return entity;
    }

    public List<ListadoProyectoDto> findAllProyectos() {
        Query q = emCooperacion.createNativeQuery(QueryNativas.sqlListadoProyectos + " ORDER BY mun.codigo_departamento, mun.codigo_municipio, vw.codigo_entidad", ListadoProyectoDto.class);
        return q.getResultList();
    }

    public List<ListadoProyectoDto> findProyectosByDepartamento(String codigoDepartamento) {
        Query q = emCooperacion.createNativeQuery(QueryNativas.sqlListadoProyectos + " and mun.codigo_departamento='" + codigoDepartamento + "' ORDER BY mun.codigo_departamento, mun.codigo_municipio, vw.codigo_entidad", ListadoProyectoDto.class);
        return q.getResultList();
    }

    public List<ListadoProyectoDto> findProyectosByMunicipio(BigDecimal idMunicipio) {
        Query q = emCooperacion.createNativeQuery(QueryNativas.sqlListadoProyectos + " and mun.id_municipio=" + idMunicipio + " ORDER BY mun.codigo_departamento, mun.codigo_municipio, vw.codigo_entidad", ListadoProyectoDto.class);
        return q.getResultList();
    }

    public List<ListadoProyectoDto> findProyectosByEstado(Short idEstado, String codigoDepartamento, BigDecimal idMunicipio) {
        String where = "";
        if (codigoDepartamento != null) {
            where = " mun.codigo_departamento='" + codigoDepartamento + "'";
        }
        if (idMunicipio != null) {
            if (where.isEmpty()) {
                where = " mun.id_municipio=" + idMunicipio;
            } else {
                where = where + " and mun.id_municipio=" + idMunicipio;
            }
        }

        if (!where.isEmpty()) {
            where = where + " and pro.id_estado =" + idEstado;
        }

        Query q = emCooperacion.createNativeQuery(QueryNativas.sqlListadoProyectos + where + " ORDER BY mun.codigo_departamento, mun.codigo_municipio, vw.codigo_entidad", ListadoProyectoDto.class);
        return q.getResultList();
    }

    public List<ListadoProyectoDto> findProyectosByCodigoEntidad(String codigoEntidad) {
        Query q = emCooperacion.createNativeQuery(QueryNativas.sqlListadoProyectos + " and vw.codigo_entidad = '" + codigoEntidad + "' ORDER BY mun.codigo_departamento, mun.codigo_municipio, vw.codigo_entidad", ListadoProyectoDto.class);
        return q.getResultList();
    }

    public List<ListadoProyectoDto> findProyectosByUT(String codigoEntidad) {
        Query q = emCooperacion.createNativeQuery(QueryNativas.sqlListadoProyectos + " and vw.codigo_entidad = '" + codigoEntidad + "' ORDER BY mun.codigo_departamento, mun.codigo_municipio, vw.codigo_entidad", ListadoProyectoDto.class);
        return q.getResultList();
    }
}
