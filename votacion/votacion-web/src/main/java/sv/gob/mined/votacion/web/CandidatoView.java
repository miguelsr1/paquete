/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.votacion.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import sv.gob.mined.votacion.api.facade.VotacionFacadeLocal;
import sv.gob.mined.votacion.model.votacion.entities.Candidato;

/**
 *
 * @author misanchez
 */
@Named(value = "candidatoView")
@RequestScoped
public class CandidatoView implements Serializable{

    private Candidato candidato = new Candidato();
    @EJB
    private VotacionFacadeLocal vfl;
    /**
     * Creates a new instance of CandidatoView
     */
    public CandidatoView() {
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }
    
    public void guardar(){
        vfl.guardarCandidato(candidato);
    }
}
