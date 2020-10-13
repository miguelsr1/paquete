package sv.gob.mined.cooperacion.web.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import sv.gob.mined.cooperacion.web.facade.CatalogoFacade;
import sv.gob.mined.cooperacion.web.facade.paquete.UbicacionFacade;
import sv.gob.mined.cooperacion.web.model.Director;
import sv.gob.mined.cooperacion.web.model.GeoEntidadEducativa;
import sv.gob.mined.cooperacion.web.model.ModalidadEjecucion;
import sv.gob.mined.cooperacion.web.model.TipoCooperacion;
import sv.gob.mined.cooperacion.web.model.TipoCooperante;
import sv.gob.mined.cooperacion.web.model.TipoInstrumento;
import sv.gob.mined.cooperacion.web.model.paquete.Departamento;
import sv.gob.mined.cooperacion.web.model.paquete.Municipio;
import sv.gob.mined.cooperacion.web.model.paquete.VwCatalogoEntidadEducativa;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class RegistrarCooperacionView implements Serializable {

    private String posicionInicial = "13.749655, -88.822362";
    private String codigoEntidad;
    private String codigoDepartamento;

    private Director directorCe;
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();
    private GeoEntidadEducativa geoEntidadEducativa = new GeoEntidadEducativa();
    private List<Departamento> lstDepartamentos = new ArrayList();
    private List<Municipio> lstMunicipio = new ArrayList();

    private List<TipoCooperante> lstTipoCooperante = new ArrayList();
    private List<TipoCooperacion> lstTipoCooperacion = new ArrayList();
    private List<ModalidadEjecucion> lstModalidadEjecucion = new ArrayList();
    private List<TipoInstrumento> lstTipoInstrumento = new ArrayList();

    private MapModel simpleModel;

    @Inject
    private UbicacionFacade ubicacionFacade;
    @Inject
    private CatalogoFacade catalogoFacade;

    @PostConstruct
    public void init() {
        lstDepartamentos = ubicacionFacade.getLstDepartamentos();
        lstModalidadEjecucion = catalogoFacade.findModalidadEjecucion();
        lstTipoCooperacion = catalogoFacade.findTipoCooperacion();
        lstTipoCooperante = catalogoFacade.findTipoCooperante();
        lstTipoInstrumento = catalogoFacade.findTipoInstrumento();
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
    }

    public GeoEntidadEducativa getGeoEntidadEducativa() {
        return geoEntidadEducativa;
    }

    public List<Departamento> getLstDepartamentos() {
        return lstDepartamentos;
    }

    public List<Municipio> getLstMunicipio() {
        return lstMunicipio;
    }

    public List<TipoCooperante> getLstTipoCooperante() {
        return lstTipoCooperante;
    }

    public List<TipoCooperacion> getLstTipoCooperacion() {
        return lstTipoCooperacion;
    }

    public List<ModalidadEjecucion> getLstModalidadEjecucion() {
        return lstModalidadEjecucion;
    }

    public List<TipoInstrumento> getLstTipoInstrumento() {
        return lstTipoInstrumento;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getPosicionInicial() {
        return posicionInicial;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public Director getDirectorCe() {
        return directorCe;
    }

    public void recuperarMunicipios() {
        lstMunicipio = ubicacionFacade.getLstMunicipio(codigoDepartamento);
    }

    public void buscarEntidadEducativa() {
        entidadEducativa = ubicacionFacade.findEntidadEducativaByCodigo(codigoEntidad);
        if (entidadEducativa != null) {
            directorCe = catalogoFacade.findDirectorByCodigoEntidad(codigoEntidad);
            if (directorCe != null) {
                geoEntidadEducativa = catalogoFacade.findGeoEntidadEducativaByCodigo(codigoEntidad);
                codigoDepartamento = entidadEducativa.getCodigoDepartamento();
                posicionInicial = geoEntidadEducativa.getGeoreferenciaY().toString() + "," + geoEntidadEducativa.getGeoreferenciaX().toString();
                agregarGpsCe();
                recuperarMunicipios();
            } else {
                JsfUtil.mensajeAlerta("No se cuenta con el director del Centro Educativo: " + codigoEntidad + " - " + entidadEducativa.getNombre());
            }
        } else {
            JsfUtil.mensajeAlerta("El código ingresado no existe.");
        }
    }

    public void agregarGpsCe() {
        simpleModel = new DefaultMapModel();

        if (geoEntidadEducativa.getGeoreferenciaX() != null && geoEntidadEducativa.getGeoreferenciaX().intValue() != 0
                && geoEntidadEducativa.getGeoreferenciaY() != null && geoEntidadEducativa.getGeoreferenciaY().intValue() != 0) {
            LatLng coor = new LatLng(geoEntidadEducativa.getGeoreferenciaY().doubleValue(), geoEntidadEducativa.getGeoreferenciaX().doubleValue());
            simpleModel.addOverlay(new Marker(coor, "CE: " + geoEntidadEducativa.getCodigoEntidad()));
        }
    }
}
