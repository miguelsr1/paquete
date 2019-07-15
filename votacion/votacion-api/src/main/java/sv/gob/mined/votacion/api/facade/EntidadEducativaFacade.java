/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.votacion.api.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.votacion.model.paquete.entities.Departamento;
import sv.gob.mined.votacion.model.paquete.entities.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@Stateless
public class EntidadEducativaFacade implements EntidadEducativaFacadeLocal {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    @Override
    public List<Departamento> getLstDepartamentos() {
        Query q = em.createQuery("SELECT d FROM Departamento d where D.codigoDepartamento NOT IN ('00') ORDER BY d.codigoDepartamento", Departamento.class);
        return q.getResultList();
    }

    @Override
    public VwCatalogoEntidadEducativa findEntidadByCodigo(String codigoEntidad) {
        Query q = em.createQuery("SELECT v FROM VwCatalogoEntidadEducativa v WHERE v.codigoEntidad=:codigo", VwCatalogoEntidadEducativa.class);
        q.setParameter("codigo", codigoEntidad);
        if (q.getResultList().isEmpty()) {
            return (VwCatalogoEntidadEducativa) q.getSingleResult();
        } else {
            return null;
        }
    }
}
