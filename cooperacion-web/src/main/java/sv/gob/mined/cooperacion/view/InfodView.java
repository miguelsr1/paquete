/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.view;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import sv.gob.mined.cooperacion.facade.EMailFacade;
import sv.gob.mined.cooperacion.facade.MantenimientoFacade;
import sv.gob.mined.cooperacion.facade.paquete.UbicacionFacade;
import sv.gob.mined.cooperacion.model.Director;
import sv.gob.mined.cooperacion.model.FechaCapacitacion;
import sv.gob.mined.cooperacion.model.ProyectoCooperacion;
import sv.gob.mined.cooperacion.model.paquete.VwCatalogoEntidadEducativa;
import sv.gob.mined.cooperacion.util.RC4Crypter;
import sv.gob.mined.utils.StringUtils;

/**
 *
 * @author MISanchez
 */
@Named
@ViewScoped
public class InfodView implements Serializable {

    private ProyectoCooperacion proyecto = new ProyectoCooperacion();

    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();

    private boolean showWeekends = true;
    private boolean tooltip = true;
    private boolean allDaySlot = true;
    private boolean disable = false;

    private Boolean codigoBueno = true;

    private Director directorCe;
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();
    private List<FechaCapacitacion> lstFechas = new ArrayList();
    private List<FechaCapacitacion> lstFechasProyecto = new ArrayList();
    private List<Integer> lstFechasEliminadas = new ArrayList();

    @Inject
    private MantenimientoFacade mantenimientoFacade;
    @Inject
    private UbicacionFacade ubicacionFacade;
    @Inject
    private EMailFacade eMailFacade;
    
    @Inject
    private CredencialesView credencialesView;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("bundle");

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("id")) {
            RC4Crypter seguridad = new RC4Crypter();

            String[] cod = null;

            try {
                cod = seguridad.decrypt("ha", params.get("id")).split("::");
                proyecto = mantenimientoFacade.find(ProyectoCooperacion.class, Long.parseLong(cod[0]));
                entidadEducativa = ubicacionFacade.findEntidadEducativaByCodigo(proyecto.getCodigoEntidad());
                directorCe = mantenimientoFacade.getDirectorByCodigoEntidad(entidadEducativa.getCodigoEntidad());

                lstFechasProyecto = mantenimientoFacade.findCapacitacionesByProyecto(proyecto.getIdProyecto());
                cargarDatosIniciales();
            } catch (Exception e) {
                codigoBueno = false;
            }
        } else {
            codigoBueno = false;
        }
    }

    public Boolean getCodigoBueno() {
        return codigoBueno;
    }

    public boolean getDisable() {
        return disable;
    }

    public List<FechaCapacitacion> getLstFechasProyecto() {
        return lstFechasProyecto;
    }

    public void setLstFechasProyecto(List<FechaCapacitacion> lstFechasProyecto) {
        this.lstFechasProyecto = lstFechasProyecto;
    }

    public List<FechaCapacitacion> getLstFechas() {
        return lstFechas;
    }

    public ProyectoCooperacion getProyecto() {
        return proyecto;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public boolean isShowWeekends() {
        return showWeekends;
    }

    public boolean isTooltip() {
        return tooltip;
    }

    public boolean isAllDaySlot() {
        return allDaySlot;
    }

    public void onEventSelect(SelectEvent<ScheduleEvent> selectEvent) {
        event = selectEvent.getObject();
        if (event.getData() != null) {
            disable = (((FechaCapacitacion) event.getData()).getIdProyecto().getIdProyecto().compareTo(proyecto.getIdProyecto()) != 0);
        }
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        event = DefaultScheduleEvent.builder().startDate(selectEvent.getObject()).endDate(selectEvent.getObject().plusHours(1)).build();
        disable = false;
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Delta:" + event.getDeltaAsDuration());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Start-Delta:" + event.getDeltaStartAsDuration() + ", End-Delta: " + event.getDeltaEndAsDuration());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void agregarEvento() {
        if (event.isAllDay()) {
            if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
                event.setEndDate(event.getEndDate().plusDays(1));
            }
        }

        if (event.getId() == null) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }

        FechaCapacitacion fechaTmp = new FechaCapacitacion();

        fechaTmp.setTitulo(event.getTitle());
        fechaTmp.setFechaInicio(java.sql.Timestamp.valueOf(event.getStartDate()));
        fechaTmp.setFechaFin(java.sql.Timestamp.valueOf(event.getEndDate()));
        fechaTmp.setEstadoEliminacion((short) 0);
        fechaTmp.setIdProyecto(proyecto);
        fechaTmp.setLugar(event.getDescription());

        lstFechasProyecto.add(fechaTmp);

        event = new DefaultScheduleEvent();
    }

    public void eliminarEvento() {
        eventModel.deleteEvent(event);
        lstFechasProyecto.stream().filter((fecha) -> (fecha.getIdFecha().equals(((FechaCapacitacion) event.getData()).getIdFecha()))).forEachOrdered((fecha) -> {
            fecha.setEstadoEliminacion((short) 1);
        });
    }

    public void guardar() {

        lstFechasProyecto.forEach((fechaCapacitacion) -> {
            if (fechaCapacitacion.getIdFecha() == null) {
                mantenimientoFacade.guardar(fechaCapacitacion);
            } else {
                mantenimientoFacade.modificar(fechaCapacitacion);
            }
        });

        lstFechasProyecto = mantenimientoFacade.findCapacitacionesByProyecto(proyecto.getIdProyecto());
    }

    public void cargarDatosIniciales() {
        lstFechas = mantenimientoFacade.findAllCapacitaciones();

        lstFechas.stream().map((fecha) -> DefaultScheduleEvent.builder()
                .title(fecha.getTitulo())
                .startDate(fecha.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .endDate(fecha.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .data(fecha)
                .description(fecha.getLugar())
                .build()).forEachOrdered((eventTemp) -> {
            eventModel.addEvent(eventTemp);
        });
    }

    public void notificarCe() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfHora = new SimpleDateFormat("hh:mm a");
            
            String titulo = RESOURCE_BUNDLE.getString("correo.respuesta.titulo");
            String mensajeNotificacionCabecera = RESOURCE_BUNDLE.getString("correo.respuestaSolicitudCapacitacion.mensaje");
            String tblInicio = RESOURCE_BUNDLE.getString("correo.respuestaSolicitudCapacitacion.mensaje.tablaDetalle.header");
            String tblDetalle = RESOURCE_BUNDLE.getString("correo.respuestaSolicitudCapacitacion.mensaje.tablaDetalle.detalle");
            String tblFin = RESOURCE_BUNDLE.getString("correo.respuestaSolicitudCapacitacion.mensaje.tablaDetalle.fin");
            String mensajeNotificacionFin = RESOURCE_BUNDLE.getString("correo.respuestaSolicitudCapacitacion.mensaje.fin");
            
            StringBuilder sb = new StringBuilder();
            
            sb.append(MessageFormat.format(mensajeNotificacionCabecera,
                    StringUtils.getFecha(new Date()), directorCe.getNombreDirector(),
                    entidadEducativa.getNombre(), entidadEducativa.getCodigoEntidad(),
                    proyecto.getNombreProyecto(), proyecto.getObjetivos()
            ));
            
            sb.append(tblInicio);
            lstFechasProyecto.forEach((fechaCapa) -> {
                sb.append(MessageFormat.format(tblDetalle,
                        sdf.format(fechaCapa.getFechaInicio()), sdfHora.format(fechaCapa.getFechaInicio()),
                        sdfHora.format(fechaCapa.getFechaFin()), fechaCapa.getLugar()));
            });
            sb.append(tblFin);
            
            sb.append(mensajeNotificacionFin);
            
            InternetAddress[] to = new InternetAddress[1];
            to[0] = new InternetAddress(directorCe.getCorreoElectronico());
            
            credencialesView.setDominio("2");
            credencialesView.setCorreoRemitente("miguel.sanchez");
            credencialesView.setPassword("miguelsr15.");
            credencialesView.validarCredencial();
            
            eMailFacade.enviarMail(to, null, credencialesView.getRemitente(), titulo, sb.toString(), credencialesView.getMailSession());
            
        } catch (AddressException ex) {
            Logger.getLogger(InfodView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
