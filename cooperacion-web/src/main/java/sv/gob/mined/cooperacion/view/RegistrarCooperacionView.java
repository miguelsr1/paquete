package sv.gob.mined.cooperacion.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import sv.gob.mined.cooperacion.facade.CatalogoFacade;
import sv.gob.mined.cooperacion.facade.EMailFacade;
import sv.gob.mined.cooperacion.facade.MantenimientoFacade;
import sv.gob.mined.cooperacion.facade.paquete.UbicacionFacade;
import sv.gob.mined.cooperacion.model.Cooperante;
import sv.gob.mined.cooperacion.model.Director;
import sv.gob.mined.cooperacion.model.GeoEntidadEducativa;
import sv.gob.mined.cooperacion.model.HisCambioEstadoPro;
import sv.gob.mined.cooperacion.model.ModalidadEjecucion;
import sv.gob.mined.cooperacion.model.Notificacion;
import sv.gob.mined.cooperacion.model.ProyectoCooperacion;
import sv.gob.mined.cooperacion.model.TipoCooperacion;
import sv.gob.mined.cooperacion.model.TipoCooperante;
import sv.gob.mined.cooperacion.model.TipoInstrumento;
import sv.gob.mined.cooperacion.model.Usuario;
import sv.gob.mined.cooperacion.model.paquete.Departamento;
import sv.gob.mined.cooperacion.model.paquete.Municipio;
import sv.gob.mined.cooperacion.model.paquete.VwCatalogoEntidadEducativa;
import sv.gob.mined.utils.StringUtils;
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

    private String[] nivelI;
    private String[] nivelII;
    private String[] nivelIII;

    private Director directorCe;
    private ProyectoCooperacion proyectoCooperacion = new ProyectoCooperacion();
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();
    private GeoEntidadEducativa geoEntidadEducativa = new GeoEntidadEducativa();
    private List<Municipio> lstMunicipio = new ArrayList();

    private MapModel simpleModel;

    @Inject
    private UbicacionFacade ubicacionFacade;
    @Inject
    private MantenimientoFacade mantenimientoFacade;
    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private EMailFacade eMailFacade;

    @Inject
    private CredencialesView credencialesView;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("bundle");

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();

        if (fc.getExternalContext().getSessionMap().containsKey("usuario")) {
            Usuario usu = (Usuario) fc.getExternalContext().getSessionMap().get("usuario");
            if (usu.getDirector() != null) {
                directorCe = usu.getDirector();
                codigoEntidad = directorCe.getCodigoEntidad();
                buscarEntidadEducativa();
            }
        }
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
        return ubicacionFacade.getLstDepartamentos();
    }

    public List<Municipio> getLstMunicipio() {
        return lstMunicipio;
    }

    public List<Cooperante> getLstCooperantes() {
        return catalogoFacade.findCooperante();
    }

    public List<TipoCooperante> getLstTipoCooperante() {
        return catalogoFacade.findTipoCooperante();
    }

    public List<TipoCooperacion> getLstTipoCooperacion() {
        return catalogoFacade.findTipoCooperacion();
    }

    public List<ModalidadEjecucion> getLstModalidadEjecucion() {
        return catalogoFacade.findModalidadEjecucion();
    }

    public List<TipoInstrumento> getLstTipoInstrumento() {
        return catalogoFacade.findTipoInstrumento();
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

    public ProyectoCooperacion getProyectoCooperacion() {
        return proyectoCooperacion;
    }

    public void setProyectoCooperacion(ProyectoCooperacion proyectoCooperacion) {
        this.proyectoCooperacion = proyectoCooperacion;
    }

    public void recuperarMunicipios() {
        lstMunicipio = ubicacionFacade.getLstMunicipio(codigoDepartamento);
    }

    public String[] getNivelI() {
        return nivelI;
    }

    public void setNivelI(String[] nivelI) {
        this.nivelI = nivelI;
    }

    public String[] getNivelII() {
        return nivelII;
    }

    public void setNivelII(String[] nivelII) {
        this.nivelII = nivelII;
    }

    public String[] getNivelIII() {
        return nivelIII;
    }

    public void setNivelIII(String[] nivelIII) {
        this.nivelIII = nivelIII;
    }

    public void buscarEntidadEducativa() {
        entidadEducativa = ubicacionFacade.findEntidadEducativaByCodigo(codigoEntidad);
        if (entidadEducativa != null) {
            directorCe = catalogoFacade.findDirectorByCodigoEntidad(codigoEntidad);
            if (directorCe != null) {
                geoEntidadEducativa = catalogoFacade.findGeoEntidadEducativaByCodigo(codigoEntidad);
                codigoDepartamento = entidadEducativa.getCodigoDepartamento();
                if (geoEntidadEducativa != null) {
                    posicionInicial = geoEntidadEducativa.getGeoreferenciaY().toString() + "," + geoEntidadEducativa.getGeoreferenciaX().toString();
                    agregarGpsCe();
                }
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

    public void guardar() {
        Date tmpFecha;
        String titulo = RESOURCE_BUNDLE.getString("correo.respuesta.titulo");
        String mensajeParaCe = "";
        String mensajeParaUt = "";

        for (String nivel : nivelI) {
            switch (nivel) {
                case "1":
                    proyectoCooperacion.setInicial((short) 1);
                    break;
                case "2":
                    proyectoCooperacion.setParvularia((short) 2);
                    break;
            }
        }
        for (String nivel : nivelII) {
            switch (nivel) {
                case "3":
                    proyectoCooperacion.setBasicaCi((short) 3);
                    break;
                case "4":
                    proyectoCooperacion.setBasicaCii((short) 4);
                    break;
                case "5":
                    proyectoCooperacion.setBasicaCiii((short) 5);
                    break;
            }
        }
        for (String nivel : nivelIII) {
            switch (nivel) {
                case "6":
                    proyectoCooperacion.setMedia((short) 6);
                    break;
                case "7":
                    proyectoCooperacion.setDocente((short) 7);
                    break;
                case "8":
                    proyectoCooperacion.setOtros((short) 8);
                    break;
            }
        }

        if (proyectoCooperacion.getIdProyecto() == null) {
            InternetAddress[] to;
            InternetAddress[] cc;

            tmpFecha = new Date();
            proyectoCooperacion.setFechaInsercion(tmpFecha);
            proyectoCooperacion.setUsuarioInsercion(directorCe.getIdDirector());
            proyectoCooperacion.setCodigoEntidad(codigoEntidad);
            proyectoCooperacion.setEstadoEliminacion((short) 0);

            switch (proyectoCooperacion.getIdTipoCooperacion().getIdTipoCooperacion().intValue()) {
                //RESPUESTA DE APROBACIÓN AUTOMATICA
                case 2:
                case 7:
                case 8:
                case 10:
                case 11:
                case 14:
                    proyectoCooperacion.setIdEstado((short) 2);

                    mensajeParaCe = MessageFormat.format(RESOURCE_BUNDLE.getString("correo.respuestaAprobacionAutomatica.mensaje"),
                            StringUtils.getFecha(new Date()), directorCe.getNombreDirector(),
                            entidadEducativa.getNombre(), entidadEducativa.getCodigoEntidad(),
                            proyectoCooperacion.getNombreProyecto(), proyectoCooperacion.getObjetivos());
                    break;

                //RESPUESTA DEPENDERA DEL MONTO DEL PROYECTO
                case 1:
                case 4:
                case 5:
                case 6:
                    proyectoCooperacion.setIdEstado((short) 1);

                    if (proyectoCooperacion.getIdTipoCooperacion().getIdTipoCooperacion().intValue() != 5) {
                        mensajeParaCe = MessageFormat.format(RESOURCE_BUNDLE.getString("correo.respuestaAprobacionVoBo.mensaje"),
                                StringUtils.getFecha(new Date()), directorCe.getNombreDirector(),
                                entidadEducativa.getNombre(), entidadEducativa.getCodigoEntidad(),
                                proyectoCooperacion.getNombreProyecto(), proyectoCooperacion.getObjetivos());

                        
                        
                    } else if (proyectoCooperacion.getMontoInversion().compareTo(new BigDecimal(5000)) == 1) {
                        //cooperaciones mayores a 5,000.00

                    } else {
                        // cooperacion inferior a 5,000.00
                        mensajeParaCe = MessageFormat.format(RESOURCE_BUNDLE.getString("correo.respuestaAprobacionAutomatica.mensaje"),
                                StringUtils.getFecha(new Date()), directorCe.getNombreDirector(),
                                entidadEducativa.getNombre(), entidadEducativa.getCodigoEntidad(),
                                proyectoCooperacion.getNombreProyecto(), proyectoCooperacion.getObjetivos());
                    }
                    break;
                default:
            }

            if (mantenimientoFacade.guardar(proyectoCooperacion)) {
                JsfUtil.mensajeInsert();
                guardarHistoricoCambioEstado(tmpFecha, null, (short) 1);
                List<Notificacion> lstNotificacion = catalogoFacade.findNotificacionByTipoCooperacion(proyectoCooperacion.getIdTipoCooperacion().getIdTipoCooperacion());

                String emailsTo;
                String emailsCc = "";

                emailsTo = directorCe.getCorreoElectronico();

                for (Notificacion notificacion : lstNotificacion) {
                    if (notificacion.getTipoDestinatario() == 1) {
                        emailsTo = emailsTo.concat(",").concat(notificacion.getCorreo());
                    } else {
                        if (emailsCc.isEmpty()) {
                            emailsCc = notificacion.getCorreo();
                        } else {
                            emailsCc = emailsCc.concat(",").concat(notificacion.getCorreo());
                        }
                    }
                }

                to = new InternetAddress[emailsTo.split(",").length];
                cc = new InternetAddress[emailsCc.split(",").length];
                try {
                    for (int i = 0; i < emailsTo.split(",").length; i++) {
                        to[i] = new InternetAddress(emailsTo.split(",")[i]);
                    }
                    for (int i = 0; i < emailsCc.split(",").length; i++) {
                        cc[i] = new InternetAddress(emailsCc.split(",")[i]);
                    }

                } catch (AddressException ex) {
                    Logger.getLogger(RegistrarCooperacionView.class.getName()).log(Level.SEVERE, null, ex);
                }

                Session session = credencialesView.getMailSession();

                eMailFacade.enviarMail(to, cc,
                        directorCe.getCorreoElectronico(), titulo, mensajeParaCe,
                        session);
            } else {
                JsfUtil.mensajeError("Ah ocurrido un error en la operación de guardar.");
            }
        } else {
            mantenimientoFacade.modificar(proyectoCooperacion);
        }
    }

    private void guardarHistoricoCambioEstado(Date tmpFecha, Short cambioAnt, Short cambioNew) {
        HisCambioEstadoPro historico = new HisCambioEstadoPro();
        historico.setFechaCambio(tmpFecha);
        historico.setIdEstadoAnt(cambioAnt);
        historico.setIdEstadoNew(cambioNew);
        historico.setIdProyecto(proyectoCooperacion.getIdProyecto());
        historico.setUsuarioCambio(directorCe.getIdDirector());

        mantenimientoFacade.guardar(historico);
    }
}
