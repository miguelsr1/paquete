/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.votacion.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import sv.gob.mined.votacion.model.facade.EntidadEducativaFacadeLocal;
import sv.gob.mined.votacion.model.paquete.entities.Departamento;

/**
 *
 * @author misanchez
 */
@Named(value = "votacionView")
@RequestScoped
public class VotacionView implements Serializable {

    @EJB
    private EntidadEducativaFacadeLocal eefl;

    /**
     * Creates a new instance of VotacionView
     */
    public VotacionView() {
    }

    public List<Departamento> getLstDepartamentos() {
        return eefl.getLstDepartamentos();
    }
}
