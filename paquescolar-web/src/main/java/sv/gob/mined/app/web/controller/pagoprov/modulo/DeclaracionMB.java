/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov.modulo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import sv.gob.mined.app.web.controller.pagoprov.ProveedorController;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.UtilFile;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.apps.utilitario.Herramientas;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;
import sv.gob.mined.paquescolar.model.Anho;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.pojos.OfertaGlobal;

/**
 *
 * @author MISanchez
 */
@ManagedBean
@ViewScoped
public class DeclaracionMB implements Serializable {

    private Boolean aceptarCondiciones = false;
    private Boolean aceptarDeclaracion = false;
    private String idGestion = "";
    private String cabecera;
    private String detalle;
    private Empresa empresa = new Empresa();
    private DetalleProcesoAdq detalleProcesoAdq = new DetalleProcesoAdq();
    private CapaInstPorRubro capacidadInst = new CapaInstPorRubro();

    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private ReportesEJB reportesEJB;

    private static final ResourceBundle UTIL_CORREO = ResourceBundle.getBundle("Bundle");

    @PostConstruct
    public void ini() {
        if (VarSession.isVariableSession("idEmpresa")) {
            ProveedorMB proveedorMB = ((ProveedorMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                    getValue(FacesContext.getCurrentInstance().getELContext(), null, "proveedorMB"));
            empresa = proveedorMB.getEmpresa();
            Anho anho = proveedorMB.getAnho();
            ProcesoAdquisicion proceso = anho.getProcesoAdquisicionList().get(0);

            if (proceso == null || proceso.getIdProcesoAdq() == null) {
                JsfUtil.mensajeAlerta("Debe seleccionar un proceso de contratación");
            } else {
                if (proceso.getPadreIdProcesoAdq() != null) {
                    proceso = proceso.getPadreIdProcesoAdq();
                }
                capacidadInst = proveedorEJB.findDetProveedor(proceso, empresa, CapaInstPorRubro.class);
                detalleProcesoAdq = capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq();
                aceptarCondiciones = (capacidadInst.getIdMuestraInteres().getDatosVerificados() == 1);
                aceptarDeclaracion = (capacidadInst.getIdMuestraInteres().getAceptacionTerminos() == 1);

                cabecera = MessageFormat.format(UTIL_CORREO.getString("prov.declaracion.cabecera"),
                        capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq().getIdRubroAdq().getDescripcionRubro(),
                        anho.getAnho());
                detalle = MessageFormat.format(UTIL_CORREO.getString("prov.declaracion.detalle"),
                        capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq().getIdRubroAdq().getDescripcionRubro(),
                        empresa.getIdPersona().getEmail(),
                        empresa.getDireccionCompleta().concat(", ").concat(empresa.getIdMunicipio().getNombreMunicipio()).concat(", ").concat(empresa.getIdMunicipio().getCodigoDepartamento().getNombreDepartamento()),
                        capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getAnho());
            }
        }
    }

    public String getCabecera() {
        return cabecera;
    }

    public String getDetalle() {
        return detalle;
    }

    public Boolean getAceptarDeclaracion() {
        return aceptarDeclaracion;
    }

    public void setAceptarDeclaracion(Boolean aceptarDeclaracion) {
        this.aceptarDeclaracion = aceptarDeclaracion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Boolean getAceptarCondiciones() {
        return aceptarCondiciones;
    }

    public void setAceptarCondiciones(Boolean aceptarCondiciones) {
        this.aceptarCondiciones = aceptarCondiciones;
    }

    public void guardarAceptarCondiciones() {
        if (aceptarCondiciones && aceptarDeclaracion) {
            idGestion = proveedorEJB.datosConfirmados(capacidadInst.getIdMuestraInteres().getIdMuestraInteres(), 
                    empresa.getIdEmpresa(),
                    VarSession.getVariableSessionUsuario());
            enviarNotificacionModProv();
        }else{
            JsfUtil.mensajeAlerta("Debe de aceptar la Declaració Jurada y Aceptación de Presentación de Oferta.");
        }
    }

    public void impOfertaGlobal() {
        try {
            String lugar = empresa.getIdMunicipio().getNombreMunicipio().concat(", ").concat(empresa.getIdMunicipio().getCodigoDepartamento().getNombreDepartamento());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            HashMap param = new HashMap();
            param.put("ubicacionImagenes", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
            param.put("pEscudo", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
            param.put("usuarioInsercion", VarSession.getVariableSessionUsuario());
            param.put("pLugar", lugar + ", " + sdf.format(new Date()));
            param.put("pRubro", JsfUtil.getNombreRubroById(capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres()));
            param.put("pIdRubro", capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres().intValue());
            param.put("pCorreoPersona", capacidadInst.getIdMuestraInteres().getIdEmpresa().getIdPersona().getEmail());
            param.put("pIdGestion", idGestion);

            List<OfertaGlobal> lstDatos = reportesEJB.getLstOfertaGlobal(empresa.getNumeroNit(), detalleProcesoAdq.getIdDetProcesoAdq(), detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue());
            lstDatos.get(0).setRubro(JsfUtil.getNombreRubroById(capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres()));
            if (lstDatos.get(0).getDepartamento().contains("TODO EL PAIS")) {
                param.put("productor", Boolean.TRUE);
            } else {
                param.put("productor", Boolean.FALSE);
            }

            List<JasperPrint> jasperPrintList = new ArrayList();

            jasperPrintList.add(JasperFillManager.fillReport(Reportes.class
                    .getClassLoader().getResourceAsStream("sv/gob/mined/apps/reportes/oferta" + File.separator + "rptOfertaGlobalProv" + detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getAnho() + ".jasper"), param, new JRBeanCollectionDataSource(lstDatos)));

            String muni = VarSession.getNombreMunicipioSession();

            param.put("pLugar", empresa.getIdMunicipio().getCodigoDepartamento().getNombreDepartamento());

            if (empresa.getIdPersoneria().getIdPersoneria().intValue() == 1) {
                jasperPrintList.add(JasperFillManager.fillReport(ProveedorController.class
                        .getClassLoader().getResourceAsStream("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerProvNat" + detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getAnho() + ".jasper"), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(empresa, detalleProcesoAdq, muni))));

            } else {
                jasperPrintList.add(JasperFillManager.fillReport(ProveedorController.class
                        .getClassLoader().getResourceAsStream("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerProvJur" + detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getAnho() + ".jasper"), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(empresa, detalleProcesoAdq, muni))));

            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.FALSE);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();

            UtilFile.downloadFileBytes(baos.toByteArray(), "oferta_global_" + getEmpresa().getNumeroNit(), UtilFile.CONTENIDO_PDF, UtilFile.EXTENSION_PDF);

        } catch (IOException | JRException ex) {
            Logger.getLogger(DeclaracionMB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviarNotificacionModProv() {
        String titulo;
        String mensaje;
        Date fecha = new Date();
        List<String> to = new ArrayList();
        List<String> cc = new ArrayList();
        List<String> bcc = new ArrayList();

        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

        cc.add("carlos.villegas@mined.gob.sv");
        cc.add("rene.brizuela@mined.gob.sv");

        bcc.add("rafael.arias@mined.gob.sv");
        to.add(empresa.getIdPersona().getEmail());

        titulo = MessageFormat.format(UTIL_CORREO.getString("prov_notif_inscripcion.email.titulo"), capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getAnho());
        mensaje = MessageFormat.format(UTIL_CORREO.getString("prov_notif_inscripcion.email.mensaje"),
                sdfHora.format(fecha).split(":")[0], sdfHora.format(fecha).split(":")[1],
                Herramientas.getNumDia(fecha), Herramientas.getNomMes(fecha), Herramientas.getNumAnyo(fecha),
                empresa.getIdPersona().getEmail(), empresa.getIdPersona().getNombreCompleto(), empresa.getIdPersona().getNumeroDui(), idGestion,
                sdfHora.format(fecha).split(":")[0], sdfHora.format(fecha).split(":")[1],
                Herramientas.getNumDia(fecha), Herramientas.getNomMes(fecha), Herramientas.getNumAnyo(fecha));

        proveedorEJB.enviarNotificacionModProv("carlos.villegas@mined.gob.sv", titulo, mensaje, to, cc, bcc);
    }
}