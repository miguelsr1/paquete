/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.primefaces.PrimeFaces;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.util.LangUtils;
import sv.gob.mined.cooperacion.facade.MantenimientoFacade;
import sv.gob.mined.cooperacion.facade.paquete.UbicacionFacade;
import sv.gob.mined.cooperacion.model.Cooperante;
import sv.gob.mined.cooperacion.model.ProyectoCooperacion;
import sv.gob.mined.cooperacion.model.TipoCooperacion;
import sv.gob.mined.cooperacion.model.Usuario;
import sv.gob.mined.cooperacion.model.dto.AtributoValorDto;
import sv.gob.mined.cooperacion.model.dto.ListadoProyectoDto;
import sv.gob.mined.cooperacion.model.dto.FileInfoDto;
import sv.gob.mined.cooperacion.model.paquete.Municipio;
import sv.gob.mined.cooperacion.model.paquete.VwCatalogoEntidadEducativa;

/**
 *
 * @author MISanchez
 */
@Named(value = "listadoView")
@ViewScoped
public class ListadoView implements Serializable {

    private Boolean dlgShowInfoCe = false;

    private Short idEstado;

    private String nombreArchivo = "";
    private String lblBottonEnviar = "";
    private String codigoDepartamento;
    private String where;
    private String nombreProyecto;
    private String anho;
    private final String posicionInicial = "13.749655, -88.822362";

    private Long idProyecto;
    private Long idCooperante;
    private Long idTipoCooperacion;
    private BigDecimal idMunicipio;

    private ProyectoCooperacion proyecto;
    private ListadoProyectoDto proyectoDto;
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();
    private List<FileInfoDto> lstArchivos = new ArrayList();
    private List<ListadoProyectoDto> lstProyectos = new ArrayList();
    private List<ListadoProyectoDto> lstProyectosFiltrados = new ArrayList();
    private List<Municipio> lstMunicipio = new ArrayList();
    private List<ProyectoCooperacion> lstProyectoCooperacion = new ArrayList();
    private List<Cooperante> lstCooperantes = new ArrayList();
    private List<TipoCooperacion> lstTipoCooperaciones = new ArrayList();

    private MapModel simpleModel;

    @Inject
    private UbicacionFacade ubicacionFacade;

    @Inject
    private MantenimientoFacade mantenimientoFacade;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("bundle");

    /**
     * Creates a new instance of ListadoView
     */
    public ListadoView() {
    }

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();

        if (fc.getExternalContext().getSessionMap().containsKey("usuario")) {
            Usuario usu = (Usuario) fc.getExternalContext().getSessionMap().get("usuario");

            switch (usu.getIdPerfil()) {
                case 1://admin
                    lstProyectos = mantenimientoFacade.findAllProyectos();
                    break;
                case 2:// u.t.
                    switch (usu.getUsuarioOrgList().get(0).getDescripcion()) {
                        case "INFRA":
                            where = " and pro.id_tipo_cooperacion in (1,4,6) ";
                            break;
                        case "INFOD":
                            where = " and pro.id_tipo_cooperacion in (3,15) ";
                            break;
                        case "DPSP":
                            where = " and pro.id_tipo_cooperacion in (5) ";
                            break;
                        case "GTIC":
                            where = " and pro.id_tipo_cooperacion in (9) ";
                            break;
                        case "JURIDICO":
                            where = " and pro.id_tipo_cooperacion in (12,13) ";
                            break;
                    }

                    lstProyectos = mantenimientoFacade.findProyectosByWhereCustom(where);
                    break;
                default:
                    where = " and vw.codigo_entidad = '" + usu.getDirector().getCodigoEntidad() + "'";
                    lstProyectos = mantenimientoFacade.findProyectosByWhereCustom(where);
                    break;
            }
        }

        simpleModel = new DefaultMapModel();
        lstCooperantes = mantenimientoFacade.findAllCooperantes();
        lstTipoCooperaciones = mantenimientoFacade.findAllTipoCoopeciones();
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    public List<ListadoProyectoDto> getLstProyectosFiltrados() {
        return lstProyectosFiltrados;
    }

    public void setLstProyectosFiltrados(List<ListadoProyectoDto> lstProyectosFiltrados) {
        this.lstProyectosFiltrados = lstProyectosFiltrados;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public List<TipoCooperacion> getLstTipoCooperaciones() {
        return lstTipoCooperaciones;
    }

    public Long getIdCooperante() {
        return idCooperante;
    }

    public void setIdCooperante(Long idCooperante) {
        this.idCooperante = idCooperante;
    }

    public Long getIdTipoCooperacion() {
        return idTipoCooperacion;
    }

    public void setIdTipoCooperacion(Long idTipoCooperacion) {
        this.idTipoCooperacion = idTipoCooperacion;
    }

    public List<Cooperante> getLstCooperantes() {
        return lstCooperantes;
    }

    public void setLstCooperantes(List<Cooperante> lstCooperantes) {
        this.lstCooperantes = lstCooperantes;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
    }

    public List<ProyectoCooperacion> getLstProyectoCooperacion() {
        return lstProyectoCooperacion;
    }

    public Boolean getDlgShowInfoCe() {
        return dlgShowInfoCe;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public List<FileInfoDto> getLstArchivos() {
        return lstArchivos;
    }

    public ListadoProyectoDto getProyectoDto() {
        return proyectoDto;
    }

    public void setProyectoDto(ListadoProyectoDto proyectoDto) {
        this.proyectoDto = proyectoDto;
    }

    public String getPosicionInicial() {
        return posicionInicial;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public Short getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Short idEstado) {
        this.idEstado = idEstado;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public ProyectoCooperacion getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoCooperacion proyecto) {
        this.proyecto = proyecto;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public BigDecimal getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(BigDecimal idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public List<ListadoProyectoDto> getLstProyectos() {
        return lstProyectos;
    }

    public void setLstProyectos(List<ListadoProyectoDto> lstProyectos) {
        this.lstProyectos = lstProyectos;
    }

    public List<Municipio> getLstMunicipio() {
        return lstMunicipio;
    }

    public String getLblBottonEnviar() {
        return lblBottonEnviar;
    }

    public void setLblBottonEnviar(String lblBottonEnviar) {
        this.lblBottonEnviar = lblBottonEnviar;
    }

    public List<AtributoValorDto> getLstAnhosProyectos() {
        return mantenimientoFacade.findAnhosDeProyecto();
    }

    public void enviar() {

    }

    public void nuevoProyecto() {

    }

    public void eliminarProyecto() {

    }

    public void recuperarProyecto() {
        proyecto = mantenimientoFacade.find(ProyectoCooperacion.class, idProyecto);
    }

    public void recuperarMunicipios() {
        idMunicipio = null;
        lstMunicipio = ubicacionFacade.getLstMunicipio(codigoDepartamento);
        recuperarLstProyectos();
    }

    public void recuperarLstProyectos() {
        String whereTmp = "";
        if (codigoDepartamento != null && !codigoDepartamento.equals("0")) {
            whereTmp = " and mun.codigo_departamento = '" + codigoDepartamento + "'";
        }
        if (codigoDepartamento != null && !codigoDepartamento.equals("0") && idMunicipio != null && idMunicipio != BigDecimal.ZERO) {
            whereTmp += " and mun.id_municipio=" + idMunicipio + "";
        }
        if (idEstado != null && idEstado != 0) {
            whereTmp += " and pro.id_estado = " + idEstado;
        }
        if (idCooperante != null) {
            whereTmp += " and pro.id_cooperante = " + idCooperante;
        }
        if (idTipoCooperacion != null) {
            whereTmp += " and pro.id_tipo_cooperacion = " + idTipoCooperacion;
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        switch (fc.getExternalContext().getSessionMap().get("role").toString()) {
            case "ADMIN":
                break;
            default:
                whereTmp = whereTmp + where;
                break;
        }

        lstProyectos = mantenimientoFacade.findProyectosByWhereCustom(whereTmp);
    }

    public void recuperarLstProyectosPorCooperante() {
        String whereTmp = "";
        if (idCooperante == null || idCooperante == 0) {
            
        } else {
            whereTmp = " and pro.id_cooperante = " + idCooperante;

        }
        if (anho != null && !anho.isEmpty() & !anho.equals("00")) {
            if (whereTmp.isEmpty()) {
                whereTmp = " and pro.anho = '" + anho + "'";
            } else {
                whereTmp += " and pro.anho = '" + anho + "'";
            }
        }

        if (!whereTmp.isEmpty()) {
            lstProyectos = mantenimientoFacade.findProyectosByWhereCustom(whereTmp);
        }else{
            lstProyectos = mantenimientoFacade.findProyectosByWhereCustom("");
        }

        simpleModel = new DefaultMapModel();
        StringBuilder ruta = new StringBuilder();
        ruta.append(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
        ruta.append("/resources/images/");
        String urlIcono;

        for (ListadoProyectoDto pro : lstProyectos) {

            if (pro.getGeoPy() != null) {
                LatLng coor = new LatLng(pro.getGeoPy().doubleValue(), pro.getGeoPx().doubleValue());
                urlIcono = ruta + File.separator + "gps_" + (169 - pro.getIdCooperante().intValue()) + ".png";

                simpleModel.addOverlay(new Marker(coor, "CE: " + pro.getCodigoEntidad(), "", urlIcono));
            }
        }

        PrimeFaces.current().executeScript("initialize()");
    }

    public void agregarPuntos() {
        simpleModel = new DefaultMapModel();

        if (!lstProyectosFiltrados.isEmpty()) {
            cargarPunto(lstProyectosFiltrados);
        } else {
            cargarPunto(lstProyectos);
        }
    }

    private void cargarPunto(List<ListadoProyectoDto> listado) {
        StringBuilder ruta = new StringBuilder();
        ruta.append(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
        ruta.append("/resources/images/");
        String urlIcono;

        for (ListadoProyectoDto pro : listado) {
            if (pro.getGeoPy() != null) {
                LatLng coor = new LatLng(pro.getGeoPy().doubleValue(), pro.getGeoPx().doubleValue());
                urlIcono = ruta + File.separator + "gps_" + (169 - pro.getIdCooperante().intValue()) + ".png";

                simpleModel.addOverlay(new Marker(coor, "CE: " + pro.getCodigoEntidad(), "", urlIcono));
            }
        }

        PrimeFaces.current().executeScript("initialize()");
    }

    public void buscarProyecto() {
        lstArchivos.clear();
        if (proyectoDto != null && proyectoDto.getIdProyecto() != null) {
            idProyecto = proyectoDto.getIdProyecto();
        } else {
            idProyecto = proyecto.getIdProyecto();
        }
        proyecto = mantenimientoFacade.find(ProyectoCooperacion.class, idProyecto);

        File folder = new File(RESOURCE_BUNDLE.getString("path_folder") + File.separator + proyecto.getIdProyecto());

        if (folder.exists()) {
            for (File archivo : folder.listFiles()) {
                try {
                    FileTime fTime = (FileTime) Files.getAttribute(archivo.toPath(), "creationTime");
                    FileInfoDto file = new FileInfoDto();

                    try (PDDocument pdc = PDDocument.load(archivo)) {
                        file.setFechaCreado(new Date(fTime.toMillis()));
                        file.setNombreArchivo(archivo.getName());
                        file.setNumeroPaginas(pdc.getNumberOfPages());

                        lstArchivos.add(file);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ListadoView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public StreamedContent getFile() throws FileNotFoundException {
        File filePdf = new File(RESOURCE_BUNDLE.getString("path_folder") + File.separator + proyecto.getIdProyecto() + File.separator + nombreArchivo);
        FileInputStream fis = new FileInputStream(filePdf);
        StreamedContent file = DefaultStreamedContent.builder()
                .name(nombreArchivo)
                .contentType("application/pdf")
                .stream(() -> fis)
                .build();

        return file;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        Marker marker = (Marker) event.getOverlay();
        dlgShowInfoCe = true;
        String codigoEntidad = marker.getTitle().replace("CE: ", "");
        entidadEducativa = ubicacionFacade.findEntidadEducativaByCodigo(codigoEntidad);
        lstMunicipio = ubicacionFacade.getLstMunicipio(entidadEducativa.getCodigoDepartamento());
        lstProyectoCooperacion = mantenimientoFacade.findProyectosByCodEnt(codigoEntidad);
    }

    public List<Cooperante> getLstCooperantePorProyecto() {
        return mantenimientoFacade.findCooperantesDeProyecto();
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }

        ListadoProyectoDto customer = (ListadoProyectoDto) value;
        return customer.getNombreProyecto().toLowerCase().contains(filterText);
    }

}
