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
import sv.gob.mined.votacion.api.util.Constantes;
import sv.gob.mined.votacion.model.votacion.entities.Candidato;
import sv.gob.mined.votacion.model.votacion.dto.DirectorDto;

/**
 *
 * @author DesarrolloPc
 */
@Stateless
public class VotacionFacade implements VotacionFacadeLocal {

    @PersistenceContext(unitName = "votacionUP")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<DirectorDto> getLstDirectores(String dui, String nip, String nombre, String nombreCe, String codigoDepartamento) {
        String sql = Constantes.SQL_DIRECTOR_DTO;
        String where = "";
        int conVar = 1;

        if (dui != null && !dui.isEmpty()) {
            where = " WHERE VW.dui = ?" + conVar;
            conVar++;
        }
        if (nip != null && !nip.isEmpty()) {
            where = (where.isEmpty() ? " WHERE " : " and ") + " VW.nip = ?" + conVar;
            conVar++;
        }
        if (nombre != null && !nombre.isEmpty()) {
            where = (where.isEmpty() ? " WHERE " : " and ") + " VW.nombre LIKE ?" + conVar;
            conVar++;
        }
        if (nombreCe != null && !nombreCe.isEmpty()) {
            where = (where.isEmpty() ? " WHERE " : " and ") + " VW.nombre_ce LIKE ?" + conVar;
            conVar++;
        }
        if (codigoDepartamento != null && !codigoDepartamento.isEmpty()) {
            where = (where.isEmpty() ? " WHERE " : " and ") + " VW.codigo_departamento = ?" + conVar;
            conVar++;
        }

        conVar = 1;
        Query q = em.createNativeQuery(sql + where + " order by VW.codigo_departamento, vw.codigo_entidad", DirectorDto.class);

        if (!where.isEmpty()) {
            if (dui != null && !dui.isEmpty()) {
                q.setParameter(conVar, dui);
                conVar++;
            }
            if (nip != null && !nip.isEmpty()) {
                q.setParameter(conVar, nip);
                conVar++;
            }
            if (nombre != null && !nombre.isEmpty()) {
                q.setParameter(conVar, "%" + nombre.toUpperCase() + "%");
                conVar++;
            }
            if (nombreCe != null && !nombreCe.isEmpty()) {
                q.setParameter(conVar, "%" + nombreCe.toUpperCase() + "%");
                conVar++;
            }
            if (codigoDepartamento != null && !codigoDepartamento.isEmpty()) {
                q.setParameter(conVar, codigoDepartamento);
                conVar++;
            }
        }
        return q.getResultList();
    }

    @Override
    public void guardarCandidato(Candidato candidato) {
        if(candidato.getIdCandidato() == null){
            em.persist(candidato);
        }else{
            em.merge(candidato);
        }
    }

}
