/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.web.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.cooperacion.web.model.Director;
import sv.gob.mined.cooperacion.web.model.EeGeoDepartamento;
import sv.gob.mined.cooperacion.web.model.EeGeoMunicipio;
import sv.gob.mined.cooperacion.web.model.GeoEntidadEducativa;
import sv.gob.mined.cooperacion.web.model.ModalidadEjecucion;
import sv.gob.mined.cooperacion.web.model.TipoCooperacion;
import sv.gob.mined.cooperacion.web.model.TipoCooperante;
import sv.gob.mined.cooperacion.web.model.TipoInstrumento;

/**
 *
 * @author misanchez
 */
@Stateless
public class CatalogoFacade {

    @PersistenceContext(unitName = "cooperacionPU")
    private EntityManager emCooperacion;

    public List<TipoCooperante> findTipoCooperante() {
        Query q = emCooperacion.createQuery("SELECT t FROM TipoCooperante t", TipoCooperante.class);
        return q.getResultList();
    }

    public List<TipoCooperacion> findTipoCooperacion() {
        Query q = emCooperacion.createQuery("SELECT t FROM TipoCooperacion t", TipoCooperacion.class);
        return q.getResultList();
    }

    public List<ModalidadEjecucion> findModalidadEjecucion() {
        Query q = emCooperacion.createQuery("SELECT m FROM ModalidadEjecucion m", ModalidadEjecucion.class);
        return q.getResultList();
    }

    public List<TipoInstrumento> findTipoInstrumento() {
        Query q = emCooperacion.createQuery("SELECT t FROM TipoInstrumento t", TipoInstrumento.class);
        return q.getResultList();
    }

    public GeoEntidadEducativa findGeoEntidadEducativaByCodigo(String codigoEntidad) {
        Query q = emCooperacion.createQuery("SELECT g FROM GeoEntidadEducativa g WHERE g.codigoEntidad = :codigoEntidad", GeoEntidadEducativa.class);
        q.setParameter("codigoEntidad", codigoEntidad);
        return q.getResultList().isEmpty() ? null : (GeoEntidadEducativa) q.getSingleResult();
    }

    public EeGeoDepartamento getGeoDepartamentoByCodDepa(String codigoDepartamento) {
        Query q = emCooperacion.createQuery("SELECT e FROM EeGeoDepartamento e WHERE e.codigoDepartamento=:codDepa", EeGeoDepartamento.class);
        q.setParameter("codDepa", codigoDepartamento);
        return q.getResultList().isEmpty() ? null : (EeGeoDepartamento) q.getSingleResult();
    }

    public EeGeoMunicipio getGeoMunicipioByCodMuniAndDepa(String codigoDepartamento, String codigoMunicipio) {
        Query q = emCooperacion.createQuery("SELECT e FROM EeGeoMunicipio e WHERE e.codigoDepartamento=:codDepa and e.codigoMunicipio=:codMuni", EeGeoMunicipio.class);
        q.setParameter("codDepa", codigoDepartamento);
        q.setParameter("codMuni", codigoMunicipio);
        return q.getResultList().isEmpty() ? null : (EeGeoMunicipio) q.getSingleResult();
    }

    public Director findDirectorByCodigoEntidad(String codigoEntidad) {
        Query q = emCooperacion.createQuery("SELECT d FROM Director d WHERE d.codigoEntidad=:codEnt AND d.activo=1", Director.class);
        q.setParameter("codEnt", codigoEntidad);
        return q.getResultList().isEmpty() ? null : (Director) q.getSingleResult();
    }
}
