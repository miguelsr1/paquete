/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.view;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.apache.pdfbox.pdmodel.PDDocument;
import sv.gob.mined.cooperacion.facade.CatalogoFacade;
import sv.gob.mined.cooperacion.facade.EMailFacade;
import sv.gob.mined.cooperacion.facade.MantenimientoFacade;
import sv.gob.mined.cooperacion.facade.paquete.UbicacionFacade;
import sv.gob.mined.cooperacion.model.DatoInfraCe;
import sv.gob.mined.cooperacion.model.Director;
import sv.gob.mined.cooperacion.model.ProyectoCooperacion;
import sv.gob.mined.cooperacion.model.dto.FileInfoDto;
import sv.gob.mined.cooperacion.model.paquete.VwCatalogoEntidadEducativa;
import sv.gob.mined.cooperacion.util.RC4Crypter;
import sv.gob.mined.utils.StringUtils;
import sv.gob.mined.utils.jsf.JsfUtil;

@Named
@ViewScoped
public class InfraView implements Serializable {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("bundle");

    private Boolean codigoBueno = true;
    private String observacion = "";

    private Director directorCe;
    private DatoInfraCe datoInfraCe;
    private ProyectoCooperacion proyecto;
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();
    private List<FileInfoDto> lstArchivos = new ArrayList();

    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private MantenimientoFacade mantenimientoFacade;
    @Inject
    private UbicacionFacade ubicacionFacade;
    @Inject
    private CredencialesView credencialesView;
    @Inject
    private EMailFacade eMailFacade;

    @PostConstruct
    public void init() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("id")) {
            RC4Crypter seguridad = new RC4Crypter();

            String cod = null;

            try {
                //cod = seguridad.decrypt("ha", params.get("id")).split("::");
                cod = params.get("id");
                proyecto = mantenimientoFacade.find(ProyectoCooperacion.class, Long.parseLong(cod));
                datoInfraCe = catalogoFacade.findDatoInfraByCe(proyecto.getCodigoEntidad());

                cargarArchivos();
            } catch (NumberFormatException e) {
                codigoBueno = false;
            }
        } else {
            codigoBueno = false;
        }
    }

    private void cargarArchivos() {
        directorCe = mantenimientoFacade.getDirectorByCodigoEntidad(entidadEducativa.getCodigoEntidad());
        entidadEducativa = ubicacionFacade.findEntidadEducativaByCodigo(proyecto.getCodigoEntidad());

        lstArchivos.clear();

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

    public Boolean getCodigoBueno() {
        return codigoBueno;
    }

    public void setCodigoBueno(Boolean codigoBueno) {
        this.codigoBueno = codigoBueno;
    }

    public ProyectoCooperacion getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoCooperacion proyecto) {
        this.proyecto = proyecto;
    }

    public List<FileInfoDto> getLstArchivos() {
        return lstArchivos;
    }

    public void notificarCe() {
        //Validar fechas
        if (proyecto.getFechaCapacitacionList().isEmpty()) {
            JsfUtil.mensajeAlerta("Debe de confirmar las fechas de las capacitaciones");
        } else {
            try {
                notificarRespuestaCe();
            } catch (AddressException ex) {
                Logger.getLogger(InfodView.class.getName()).log(Level.SEVERE, "", ex);
            }
        }
    }

    private void notificarRespuestaCe() throws AddressException {
        String titulo = RESOURCE_BUNDLE.getString("correo.respuesta.titulo");
        String mensajeNotificacionCabecera = "";
        
        switch(proyecto.getIdEstado()){
            case 2://aprobado
                mensajeNotificacionCabecera = RESOURCE_BUNDLE.getString("correo.respuestaAprobacionInfraestructura.mensaje");
                break;
            case 3://observado
                mensajeNotificacionCabecera = RESOURCE_BUNDLE.getString("correo.respuestaObservacionInfraestructura.mensaje");
                break;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(MessageFormat.format(mensajeNotificacionCabecera,
                StringUtils.getFecha(new Date()), directorCe.getNombreDirector(),
                entidadEducativa.getNombre(), entidadEducativa.getCodigoEntidad(),
                datoInfraCe.getDireccion(), datoInfraCe.getMatricula(),
                (datoInfraCe.getPropiedadInmueble() == 1 ? "MINEDUCYT" : (datoInfraCe.getPropiedadInmueble() == 2 ? "ALCALDIA" : "COMODATO")),
                proyecto.getNombreProyecto(), proyecto.getObjetivos()));

        enviarCorreo(directorCe.getCorreoElectronico(), titulo, sb.toString());
    }

    private void enviarCorreo(String destinatario, String titulo, String mensaje) throws AddressException {
        InternetAddress[] to = new InternetAddress[1];
        to[0] = new InternetAddress(destinatario);

        Session sesion = credencialesView.getMailSessionRemitente();

        eMailFacade.enviarMail(to, null, credencialesView.getRemitenteOficial(), titulo, mensaje, sesion);
    }
}
