/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.boleta.model.CodigoGenerado;
import sv.gob.mined.boleta.model.DetalleCodigo;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class PersistenciaFacade {

    @PersistenceContext(unitName = "boletaPU")
    private EntityManager em;


    public void guardarCodigoGeneradoPorNip(Long idCodigo, String nip, String codigoGenerado) {
        DetalleCodigo detalleCodigo = new DetalleCodigo();
        detalleCodigo.setCodigoGenerado(codigoGenerado);
        detalleCodigo.setIdCodigo(idCodigo);
        detalleCodigo.setNip(nip);
        
        em.persist(detalleCodigo);
    }

    public Long getPkCodigoGeneradoByCodDepaAndMesAnho(String codDepa, String mesAnho) {
        CodigoGenerado codigoGenerado;

        Query q = em.createQuery("SELECT c FROM CodigoGenerado c WHERE c.codigoDepartamento=:codDepa AND c.mesAnho=:mesAnho", CodigoGenerado.class);
        q.setParameter("codDepa", codDepa);
        q.setParameter("mesAnho", mesAnho);
        if (q.getResultList().isEmpty()) {
            codigoGenerado = new CodigoGenerado();
            codigoGenerado.setMesAnho(mesAnho);
            codigoGenerado.setCodigoDepartamento(codDepa);

            em.persist(codigoGenerado);
        } else {
            codigoGenerado = (CodigoGenerado) q.getResultList().get(0);
        }
        return codigoGenerado.getIdCodigo();
    }
}
