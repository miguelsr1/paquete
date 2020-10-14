package sv.gob.mined.cooperacion.web.view;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.cooperacion.web.facade.CatalogoFacade;
import sv.gob.mined.cooperacion.web.facade.MantenimientoFacade;
import sv.gob.mined.cooperacion.web.model.Cooperante;
import sv.gob.mined.cooperacion.web.model.TipoCooperante;
import sv.gob.mined.utils.jsf.JsfUtil;

@Named
@ViewScoped
public class CooperanteView implements Serializable{
    
    private Long idTipoCooperante;
    private Cooperante cooperante = new Cooperante();
    
    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject 
    private MantenimientoFacade manttoFacade;
    
    public List<TipoCooperante> getLstTipoCooperante(){
        return catalogoFacade.findTipoCooperante();
    }

    public Cooperante getCooperante() {
        return cooperante;
    }

    public void setCooperante(Cooperante cooperante) {
        this.cooperante = cooperante;
    }

    public Long getIdTipoCooperante() {
        return idTipoCooperante;
    }

    public void setIdTipoCooperante(Long idTipoCooperante) {
        this.idTipoCooperante = idTipoCooperante;
    }
    
    
    public void guardarCooperante(){
        cooperante.setIdTipoCooperante(new TipoCooperante(idTipoCooperante));
        
        if(cooperante.getIdCooperante() == null){
            manttoFacade.guardar(cooperante);
        }else{
            manttoFacade.modificar(cooperante);
        }
        
        JsfUtil.mensajeInformacion("Datos almacenados satisfactoriamente");
    }
    
}
