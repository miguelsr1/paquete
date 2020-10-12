/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.web.facade.paquete;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.cooperacion.web.model.paquete.Departamento;
import sv.gob.mined.cooperacion.web.model.paquete.Municipio;

/**
 *
 * @author misanchez
 */
@EJB
@Stateless
public class UbicacionFacade {

    @PersistenceContext(name = "paqueteUP")
    private EntityManager emPaquete;

    public List<Departamento> getLstDepartamentos() {
        Query q = emPaquete.createQuery("SELECT d FROM Departamento d ORDER BY d.codigoDepartamento", Departamento.class);
        return q.getResultList();
    }

    public List<Municipio> getLstMunicipio(String codigoDepa) {
        Query q = emPaquete.createQuery("SELECT m FROM Municipio m WHERE m.codigoDepartamento.codigoDepartamento=:codDepa ORDER BY m.codigoMunicipio", Departamento.class);
        q.setParameter("codDepa", codigoDepa);
        return q.getResultList();
    }
}
