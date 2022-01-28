/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.ws.siges.model;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.ws.siges.api.model.ParametrosGenerales;

/**
 *
 * @author misanchez
 */
@Singleton
public class ParametrosGeneralesFacade extends AbstractFacade<ParametrosGenerales> {

    @PersistenceContext(unitName = "ws-siges-api-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public ParametrosGeneralesFacade() {
        super(ParametrosGenerales.class);
    }

    public String getValorByNombreParametro(String nombreParametro) {
        Query q = em.createQuery("SELECT p.valor FROM ParametrosGenerales p WHERE p.activo='1' AND p.nombre=:nombre");
        q.setParameter("nombre", nombreParametro);
        return (String) q.getSingleResult();
    }
}
