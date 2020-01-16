/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.gob.mined.boleta.ejb.PersistenciaFacade;
import sv.gob.mined.boleta.model.CorreoDocente;
import sv.gob.mined.boleta.model.DominiosCorreo;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class DocenteController implements Serializable {

    private String criterioBusqueda;
    private Integer idDominio;
    private CorreoDocente correoDocente = new CorreoDocente();
    
    @EJB
    private PersistenciaFacade persistenciaFacade;

    public DocenteController() {
    }

    public CorreoDocente getCorreoDocente() {
        return correoDocente;
    }

    public void setCorreoDocente(CorreoDocente correoDocente) {
        this.correoDocente = correoDocente;
    }

    public Integer getIdDominio() {
        return idDominio;
    }

    public void setIdDominio(Integer idDominio) {
        this.idDominio = idDominio;
    }

    public List<DominiosCorreo> getLstDominiosCorreo(){
        return persistenciaFacade.getLstDominiosCorreo();
    }
    
    public void guardarDocente(){
        
    }
    
    public List<CorreoDocente> getLstCorreoDocente(){
        return persistenciaFacade.getLstCorreoDocentes();
    }
}
