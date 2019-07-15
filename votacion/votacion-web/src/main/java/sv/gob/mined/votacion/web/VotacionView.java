/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.votacion.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import sv.gob.mined.votacion.api.facade.EntidadEducativaFacadeLocal;
import sv.gob.mined.votacion.model.paquete.entities.Departamento;
import sv.gob.mined.votacion.model.votacion.entities.Candidato;

/**
 *
 * @author misanchez
 */
@Named(value = "votacionView")
@RequestScoped
public class VotacionView implements Serializable {

    private Boolean showListadado = false;

    private List<Candidato> lstCandidato = new ArrayList();
    @EJB
    private EntidadEducativaFacadeLocal eefl;

    /**
     * Creates a new instance of VotacionView
     */
    public VotacionView() {
    }

    @PostConstruct
    public void init() {
        Candidato c1 = new Candidato(new BigDecimal(1));
        c1.setNombres("Nombre 1");
        c1.setApellidos("Apellido 1");
        c1.setCodigoDepartamento("01");
        c1.setDui("00000000-0");
        c1.setResumenCv("Este es el resumen de la hoja de vida del candidato 1");
        lstCandidato.add(c1);

        Candidato c2 = new Candidato(new BigDecimal(2));
        c2.setNombres("Nombre 2");
        c2.setApellidos("Apellido 2");
        c2.setCodigoDepartamento("01");
        c2.setDui("11111111-0");
        c2.setResumenCv("Este es el resumen de la hoja de vida del candidato 2");
        lstCandidato.add(c2);

        Candidato c3 = new Candidato(new BigDecimal(3));
        c3.setNombres("Nombre 3");
        c3.setApellidos("Apellido 3");
        c3.setCodigoDepartamento("01");
        c3.setDui("22222222-0");
        c3.setResumenCv("Este es el resumen de la hoja de vida del candidato 3");
        lstCandidato.add(c3);
    }

    public List<Candidato> getLstCandidato() {
        return lstCandidato;
    }

    public void setLstCandidato(List<Candidato> lstCandidato) {
        this.lstCandidato = lstCandidato;
    }

    public List<Departamento> getLstDepartamentos() {
        return eefl.getLstDepartamentos();
    }

    public Boolean getShowListadado() {
        return showListadado;
    }

    public void validarVotante() {
        showListadado = true;
    }

    public void confirmarVoto() {
        Map<String, Object> opt = new HashMap();
        opt.put("modal", true);
        opt.put("draggable", true);
        opt.put("resizable", false);
        opt.put("contentHeight", 210);
        opt.put("contentWidth", 355);
        PrimeFaces.current().dialog().openDynamic("/app/dialogos/dlgConfirmacionVoto.xhtml", opt, null);
    }
}
