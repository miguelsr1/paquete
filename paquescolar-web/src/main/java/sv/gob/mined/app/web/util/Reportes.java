/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;

/**
 *
 * @author misanchez
 */
public class Reportes {

    public static final String PATH_REPORTES = File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "sv" + File.separator + "gob" + File.separator + "mined" + File.separator + "apps" + File.separator + "reportes" + File.separator;
    public static final String PATH_REPORTES_PAGO_PROVEEDORES = File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "sv" + File.separator + "gob" + File.separator + "mined" + File.separator + "apps" + File.separator + "reportes" + File.separator + "pagoproveedor" + File.separator;
    public static final String PATH_IMAGENES = File.separator + "resources" + File.separator + "images" + File.separator;

    /**
     *
     * @param lst
     * @param param
     * @param paqueteRpt
     * @param nombreRpt
     * @param nombrePdfGenerado
     */
    public static void generarRptBeanConnection(List lst, HashMap param, String paqueteRpt, String nombreRpt, String nombrePdfGenerado) {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            param.put("ubicacionImagenes", ctx.getRealPath(PATH_IMAGENES));
            JasperPrint jasperPrint = JasperFillManager.fillReport(Reportes.class.getClassLoader().getResourceAsStream(paqueteRpt + File.separator + nombreRpt), param, new JRBeanCollectionDataSource(lst));
            responseRptPdf(jasperPrint, nombrePdfGenerado);
        } catch (IOException | JRException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param reportesEJB
     * @param param
     * @param paqueteRpt
     * @param nombreRpt
     * @param nombrePdfGenerado
     */
    public static void generarRptSQLConnection(ReportesEJB reportesEJB, HashMap param, String paqueteRpt, String nombreRpt, String nombrePdfGenerado) {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            param.put("ubicacionImagenes", ctx.getRealPath(PATH_IMAGENES));
            JasperPrint jasperPrint = reportesEJB.getRpt(param, Reportes.class.getClassLoader().getResourceAsStream((paqueteRpt + File.separator + nombreRpt + ".jasper")));
            responseRptPdf(jasperPrint, nombrePdfGenerado);
        } catch (IOException | JRException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param jp
     * @param nombrePdfGenerado
     * @throws JRException
     * @throws IOException
     */
    private static void responseRptPdf(JasperPrint jp, String nombrePdfGenerado) throws JRException, IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        byte[] content = JasperExportManager.exportReportToPdf(jp);
        response.setHeader("Content-disposition", "attachment;filename=" + nombrePdfGenerado + ".pdf");
        response.setContentType("application/pdf");
        response.setContentLength(content == null ? 0 : content.length);
        response.getOutputStream().write(content, 0, content.length);
        response.getOutputStream().flush();
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();

    }

    /**
     *
     * @param lst
     * @param param
     * @param codigoEntidad
     * @param sobredemanda
     * @param reportesEJB
     * @param anho
     * @param lstRpts
     */
    public static void generarRptsContractuales(List<?> lst, HashMap param, String codigoEntidad, Boolean sobredemanda, ReportesEJB reportesEJB, String anho, String... lstRpts) {
        try {
            List<JasperPrint> jasperPrintList = new ArrayList();
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            param.put("ubicacionImagenes", ctx.getRealPath(PATH_IMAGENES));
            for (String rpt : lstRpts) {
                if (rpt.contains("Uni")) {
                    param.put("descripcionRubro", (sobredemanda ? "SOBREDEMANDA DE " : "") + "SERVICIOS DE CONFECCION DE UNIFORMES");
                } else if (rpt.contains("Uti")) {
                    param.put("descripcionRubro", (sobredemanda ? "SOBREDEMANDA DE " : "") + "SUMINISTRO DE PAQUETES DE UTILES ESCOLARES");
                } else {
                    param.put("descripcionRubro", (sobredemanda ? "SOBREDEMANDA DE " : "") + "PRODUCCION DE ZAPATOS");
                }

                if (rpt.contains("rptCertUti_2017")) {
                    jasperPrintList.add(reportesEJB.getRpt(param, Reportes.class.getClassLoader().getResourceAsStream("sv/gob/mined/apps/sispaqescolar/reporte" + File.separator + rpt)));
                } else {
                    if (rpt.contains("rptCertUni")) {
                        if (Integer.parseInt(anho) > 2017) {
                            param.put("descripcionRubro", "SERVICIOS DE CONFECCION DEL PRIMER UNIFORME");
                            jasperPrintList.add(JasperFillManager.fillReport(Reportes.class.getClassLoader().getResourceAsStream("sv/gob/mined/apps/sispaqescolar/reporte" + File.separator + rpt), param, new JRBeanCollectionDataSource(lst)));
                            param.put("descripcionRubro", "SERVICIOS DE CONFECCION DEL SEGUNDO UNIFORME");
                            jasperPrintList.add(JasperFillManager.fillReport(Reportes.class.getClassLoader().getResourceAsStream("sv/gob/mined/apps/sispaqescolar/reporte" + File.separator + rpt), param, new JRBeanCollectionDataSource(lst)));
                        } else {
                            jasperPrintList.add(JasperFillManager.fillReport(Reportes.class.getClassLoader().getResourceAsStream("sv/gob/mined/apps/sispaqescolar/reporte" + File.separator + rpt), param, new JRBeanCollectionDataSource(lst)));
                        }
                    } else {
                        jasperPrintList.add(JasperFillManager.fillReport(Reportes.class.getClassLoader().getResourceAsStream("sv/gob/mined/apps/sispaqescolar/reporte" + File.separator + rpt), param, new JRBeanCollectionDataSource(lst)));
                    }
                }
            }
            generarReporte(jasperPrintList, codigoEntidad);
        } catch (JRException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param jasperPrintList
     * @param nombreRpt
     */
    public static void generarReporte(List<JasperPrint> jasperPrintList, String nombreRpt) {
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.FALSE);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment;filename=" + nombreRpt + ".pdf");
            response.setContentLength(baos == null ? 0 : baos.toByteArray().length);
            response.getOutputStream().write(baos.toByteArray());

            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException | JRException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
