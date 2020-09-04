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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import sv.gob.mined.app.web.controller.ParametrosMB;
import sv.gob.mined.app.web.controller.pagoprov.ProveedorController;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.UtilFile;
import sv.gob.mined.app.web.util.VarSession;
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

    private Empresa empresa = new Empresa();
    private DetalleProcesoAdq detalleProcesoAdq = new DetalleProcesoAdq();
    private CapaInstPorRubro capacidadInst = new CapaInstPorRubro();

    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private ReportesEJB reportesEJB;

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

                aceptarCondiciones = (capacidadInst.getIdMuestraInteres().getDatosVerificados() == 1);
            }
        }
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
        proveedorEJB.datosConfirmados(capacidadInst.getIdMuestraInteres().getIdMuestraInteres());
    }

    public void impOfertaGlobal() {
        try {
            String lugar = ((ParametrosMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                    getValue(FacesContext.getCurrentInstance().getELContext(), null, "parametrosMB")).getUbicacion();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            HashMap param = new HashMap();
            param.put("ubicacionImagenes", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
            param.put("pEscudo", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
            param.put("usuarioInsercion", VarSession.getVariableSessionUsuario());
            param.put("pLugar", lugar + ", " + sdf.format(new Date()));
            param.put("pRubro", JsfUtil.getNombreRubroById(capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres()));
            param.put("pIdRubro", capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres().intValue());

            List<OfertaGlobal> lstDatos = reportesEJB.getLstOfertaGlobal(empresa.getNumeroNit(), detalleProcesoAdq.getIdDetProcesoAdq(), detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue());
            lstDatos.get(0).setRubro(JsfUtil.getNombreRubroById(capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres()));
            if (lstDatos.get(0).getDepartamento().contains("TODO EL PAIS")) {
                param.put("productor", Boolean.TRUE);
            } else {
                param.put("productor", Boolean.FALSE);
            }

            String tmp = "";
            List<JasperPrint> jasperPrintList = new ArrayList();
            if (detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho().intValue() > 6
                    && detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue() == 2) {
                tmp = "2019";

            }

            if (detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho().intValue() >= 8) {
                jasperPrintList.add(JasperFillManager.fillReport(Reportes.class
                        .getClassLoader().getResourceAsStream("sv/gob/mined/apps/reportes/oferta" + File.separator + "rptOfertaGlobalProv" + detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getAnho() + ".jasper"), param, new JRBeanCollectionDataSource(lstDatos)));

            } else {
                jasperPrintList.add(JasperFillManager.fillReport(Reportes.class
                        .getClassLoader().getResourceAsStream("sv/gob/mined/apps/reportes/oferta" + File.separator + "rptOfertaGlobal" + tmp + ".jasper"), param, new JRBeanCollectionDataSource(lstDatos)));
            }

            String muni = VarSession.getNombreMunicipioSession();

            if (detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho().intValue() >= 8) {
                if (empresa.getIdPersoneria().getIdPersoneria().intValue() == 1) {
                    jasperPrintList.add(JasperFillManager.fillReport(ProveedorController.class
                            .getClassLoader().getResourceAsStream("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerProvNat" + detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getAnho() + ".jasper"), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(empresa, detalleProcesoAdq, muni))));

                } else {
                    jasperPrintList.add(JasperFillManager.fillReport(ProveedorController.class
                            .getClassLoader().getResourceAsStream("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerProvJur" + detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getAnho() + ".jasper"), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(empresa, detalleProcesoAdq, muni))));

                }
            } /*else if (empresa.getIdPersoneria().getIdPersoneria().intValue() == 1) {
                jasperPrintList.add(JasperFillManager.fillReport(ProveedorController.class
                        .getClassLoader().getResourceAsStream("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurCumplimientoPerNat.jasper"), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(empresa, detalleProcesoAdq, muni))));
                jasperPrintList
                        .add(JasperFillManager.fillReport(ProveedorController.class
                                .getClassLoader().getResourceAsStream("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerNat2.jasper"), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(empresa, detalleProcesoAdq, muni))));

            } else {
                jasperPrintList.add(JasperFillManager.fillReport(ProveedorController.class
                        .getClassLoader().getResourceAsStream("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurCumplimientoPerJur.jasper"), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(empresa, detalleProcesoAdq, muni))));
                jasperPrintList
                        .add(JasperFillManager.fillReport(ProveedorController.class
                                .getClassLoader().getResourceAsStream("sv/gob/mined/apps/reportes/declaracion" + File.separator + "rptDeclaracionJurAceptacionPerJur2.jasper"), param, new JRBeanCollectionDataSource(reportesEJB.getDeclaracionJurada(empresa, detalleProcesoAdq, muni))));
            }*/

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
}
