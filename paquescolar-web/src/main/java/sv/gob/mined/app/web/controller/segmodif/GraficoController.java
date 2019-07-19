/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.segmodif;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.model.chart.PieChartModel;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProceso;
import sv.gob.mined.app.web.util.RptExcel;
import sv.gob.mined.app.web.util.UtilFile;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.ejb.RecepcionEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.pojos.DetalleContratacionDto;
import sv.gob.mined.paquescolar.model.pojos.GraficoTipoEmpresaDTO;
import sv.gob.mined.paquescolar.model.pojos.ReporteGeneralDTO;
import sv.gob.mined.paquescolar.model.pojos.ReporteProveedorDTO;
import sv.gob.mined.paquescolar.model.pojos.recepcion.ReportePorDepartamentoDto;
import sv.gob.mined.paquescolar.model.view.VwSeguimientoRptCentroEscolar;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class GraficoController implements Serializable {

    @EJB
    public ProveedorEJB proveedorEJB;
    @EJB
    public EntidadEducativaEJB entidadEducativaEJB;
    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;
    @EJB
    private RecepcionEJB recepcionEJB;

    private Boolean mostrarGrafico = false;

    private float totalProceso = 0;
    private float totalFinalizados = 0;
    private float totalPendientes = 0;
    private float totalPendientesProveedor = 0;
    private float totalProcesoProveedor = 0;
    private float totalFinalizadosProveedor = 0;
    private float totalPendientesCentroEdu = 0;
    private float totalProcesoCentroEdu = 0;
    private float totalFinalizadosCentroEdu = 0;
    private Integer totalmunicipios = 0;
    private Integer uniformes = 0;
    private Integer zapatos = 0;
    private String porcentajeAvance;
    private String tipoEmp1 = "";
    private String totaltipoEmp1 = "";
    private String tipoEmp2 = "";
    private String totaltipoEm2 = "";
    private String tipoEmp3 = "";
    private String totaltipoEmp3 = "";
    private String tipoEmp4 = "";
    private String totaltipoEmp4 = "";
    private String total = "";
    private String totalTipoEmp = "";

    private String codigoDepartamento;

    private List<DetalleContratacionDto> detalleContratacionesbydep = new ArrayList(0);
    private List<DetalleContratacionDto> detalleCotizaciones = new ArrayList(0);
    private List<GraficoTipoEmpresaDTO> listaCapacidad = new ArrayList();

    private PieChartModel pieModelSeguimiento;
    private PieChartModel pieModelSeguimientoProveedor;
    private PieChartModel pieModelSeguimientoCentroEdu;
    private DetalleProcesoAdq detalleProcesoSeg = new DetalleProcesoAdq();
    private BigDecimal rubroSeg = BigDecimal.ZERO;
    private BigDecimal parametroMayor = new BigDecimal(0);
    private String tipoReporte;
    private HSSFSheet s1;
    private HSSFWorkbook wb1;
    private Boolean mostrarGraficoCentroEducativo;
    private static DataFormat FORMATO_DATA;

    @ManagedProperty("#{recuperarProceso}")
    private RecuperarProceso recuperarProceso;

    public GraficoController() {
    }

    @PostConstruct
    public void ini() {
        mostrarGrafico = false;
    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">    
    public RecuperarProceso getRecuperarProceso() {
        return recuperarProceso;
    }

    public void setRecuperarProceso(RecuperarProceso recuperarProceso) {
        this.recuperarProceso = recuperarProceso;
    }

    public Boolean getMostrarGraficoCentroEducativo() {
        return mostrarGraficoCentroEducativo;
    }

    public void setMostrarGraficoCentroEducativo(Boolean mostrarGraficoCentroEducativo) {
        this.mostrarGraficoCentroEducativo = mostrarGraficoCentroEducativo;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public BigDecimal getRubroSeg() {
        return rubroSeg;
    }

    public void setRubroSeg(BigDecimal rubroSeg) {
        this.rubroSeg = rubroSeg;
    }

    public Boolean getMostrarGrafico() {
        return mostrarGrafico;
    }

    public void setMostrarGrafico(Boolean mostrarGrafico) {
        this.mostrarGrafico = mostrarGrafico;
    }

    public BigDecimal getParametroMayor() {
        return parametroMayor;
    }

    public void setParametroMayor(BigDecimal parametroMayor) {
        this.parametroMayor = parametroMayor;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(String porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    public Integer getTotalmunicipios() {
        return totalmunicipios;
    }

    public void setTotalmunicipios(Integer totalmunicipios) {
        this.totalmunicipios = totalmunicipios;
    }

    public String getTipoEmp1() {
        return tipoEmp1;
    }

    public void setTipoEmp1(String tipoEmp1) {
        this.tipoEmp1 = tipoEmp1;
    }

    public String getTipoEmp2() {
        return tipoEmp2;
    }

    public void setTipoEmp2(String tipoEmp2) {
        this.tipoEmp2 = tipoEmp2;
    }

    public String getTipoEmp3() {
        return tipoEmp3;
    }

    public void setTipoEmp3(String tipoEmp3) {
        this.tipoEmp3 = tipoEmp3;
    }

    public String getTotaltipoEmp1() {
        return totaltipoEmp1;
    }

    public void setTotaltipoEmp1(String totaltipoEmp1) {
        this.totaltipoEmp1 = totaltipoEmp1;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotaltipoEm2() {
        return totaltipoEm2;
    }

    public void setTotaltipoEm2(String totaltipoEm2) {
        this.totaltipoEm2 = totaltipoEm2;
    }

    public String getTotaltipoEmp3() {
        return totaltipoEmp3;
    }

    public void setTotaltipoEmp3(String totaltipoEmp3) {
        this.totaltipoEmp3 = totaltipoEmp3;
    }

    public String getTipoEmp4() {
        return tipoEmp4;
    }

    public void setTipoEmp4(String tipoEmp4) {
        this.tipoEmp4 = tipoEmp4;
    }

    public String getTotaltipoEmp4() {
        return totaltipoEmp4;
    }

    public void setTotaltipoEmp4(String totaltipoEmp4) {
        this.totaltipoEmp4 = totaltipoEmp4;
    }

    public String getTotalTipoEmp() {
        return totalTipoEmp;
    }

    public void setTotalTipoEmp(String totalTipoEmp) {
        this.totalTipoEmp = totalTipoEmp;
    }

    public String getDepa() {
        return codigoDepartamento;
    }

    public void setDepa(String departamento) {
        if (departamento != null) {
            this.codigoDepartamento = departamento;
        }
    }

    public List<DetalleContratacionDto> getDetalleContratacionesbydep() {
        return detalleContratacionesbydep;
    }

    public void setDetalleContratacionesbydep(List<DetalleContratacionDto> detalleContratacionesbydep) {
        this.detalleContratacionesbydep = detalleContratacionesbydep;
    }

    public List<DetalleContratacionDto> getDetalleCotizaciones() {
        return detalleCotizaciones;
    }

    public void setDetalleCotizaciones(List<DetalleContratacionDto> detalleCotizaciones) {
        this.detalleCotizaciones = detalleCotizaciones;
    }

    public List<GraficoTipoEmpresaDTO> getListaCapacidad() {
        return listaCapacidad;
    }

    public void setListaCapacidad(List<GraficoTipoEmpresaDTO> listaCapacidad) {
        this.listaCapacidad = listaCapacidad;
    }

    // </editor-fold>
    public PieChartModel getPieModelSeguimientoProveedor() {
        createPieModelSeguimientoProveedor();
        return pieModelSeguimientoProveedor;
    }

    private void createPieModelSeguimientoProveedor() {
        pieModelSeguimientoProveedor = new PieChartModel();
        float tot = totalPendientesProveedor;
        float totalp = totalProcesoProveedor = 400;
        float totalf = totalFinalizadosProveedor = 300;
        float totalPend = totalPendientesProveedor - (totalProcesoProveedor + totalFinalizadosProveedor);

        pieModelSeguimientoProveedor.set("Proveedores En Proceso " + (int) totalProcesoProveedor, (100 * totalp) / tot);
        pieModelSeguimientoProveedor.set("Proveedores Pendientes " + (int) totalPend, (100 * totalPend) / tot);
        pieModelSeguimientoProveedor.set("Proveedores Finalizados " + (int) totalFinalizadosProveedor, (100 * totalf) / tot);
        pieModelSeguimientoProveedor.setLegendPosition("e");
        pieModelSeguimientoProveedor.setFill(false);
        pieModelSeguimientoProveedor.setShowDataLabels(true);
        pieModelSeguimientoProveedor.setDiameter(150);

    }

    private void createPieModelSeguimientoCentroEdu() {
        pieModelSeguimientoCentroEdu = new PieChartModel();
        float tot = totalPendientesCentroEdu;
        float totalp = totalProcesoCentroEdu = 400;
        float totalf = totalFinalizadosCentroEdu = 300;
        float totalPend = totalPendientesCentroEdu - (totalProcesoCentroEdu + totalFinalizadosCentroEdu);

        pieModelSeguimientoCentroEdu.set("C.E. en proceso " + (int) totalProcesoCentroEdu, (100 * totalp) / tot);
        pieModelSeguimientoCentroEdu.set("C.E. Pendientes " + (int) totalPend, (100 * totalPend) / tot);
        pieModelSeguimientoCentroEdu.set("C.E. Finalizados " + (int) totalFinalizadosCentroEdu, (100 * totalf) / tot);
        pieModelSeguimientoCentroEdu.setLegendPosition("e");
        pieModelSeguimientoCentroEdu.setFill(false);
        pieModelSeguimientoCentroEdu.setShowDataLabels(true);
        pieModelSeguimientoCentroEdu.setDiameter(150);

    }

    public void buscarProcesoSeg() {
        detalleProcesoSeg = anhoProcesoEJB.getDetProcesoAdq(recuperarProceso.getProcesoAdquisicion(), rubroSeg);
    }

    public void generarReportesExcel() throws FileNotFoundException, IOException, InvalidFormatException, URISyntaxException {
        if (recuperarProceso.getProcesoAdquisicion().getIdProcesoAdq() != null) {
            List<ReporteProveedorDTO> lista = recepcionEJB.getLstReporteProveedores(recuperarProceso.getProcesoAdquisicion(), codigoDepartamento, rubroSeg);
            dowloadProveedoresFile(lista);
        }
    }

    public void dowloadProveedoresFile(List<ReporteProveedorDTO> lista) throws FileNotFoundException, IOException, InvalidFormatException, URISyntaxException {
        CellStyle style;
        try (InputStream ins = GraficoController.class.getClassLoader().getResourceAsStream("sv/gob/mined/apps/sispaqescolar/reporte/cuadro_seguimiento_proveedor.xls")) {
            wb1 = (HSSFWorkbook) WorkbookFactory.create(ins);
            style = wb1.createCellStyle();
            style.setWrapText(true);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
        }
        s1 = wb1.getSheetAt(0);   //sheet by index
        Integer B = 1;
        Integer C = 2;
        Integer D = 3;
        Integer E = 4;
        Integer F = 5;
        Integer i = 7;
        escribirValor(JsfUtil.getNombreRubroById(rubroSeg), 3, D, style);
        for (ReporteProveedorDTO repdet : lista) {
            escribirValor(repdet.getRAZON_SOCIAL(), i, B, style);
            escribirValor(repdet.getNIT(), i, C, style);
            escribirValor(repdet.getTOTAL_UNIFORMES().toString(), i, D, style);
            escribirValor(repdet.getTOTAL_ENTREGADOS().toString(), i, E, style);
            escribirValor(String.valueOf(repdet.getAVANCE()), i, F, style);
            i++;
        }
        generarArchivo(wb1, "REPORTE_PROVEEDORES");
    }

    private void escribirValor(String text, Integer row, Integer col, CellStyle style) {
        HSSFRichTextString texto = new HSSFRichTextString(text);
        if (s1.getRow(row) == null) {
            Row row1 = s1.createRow(row);
            Cell c = row1.createCell(col);
            c.setCellValue(texto);
            c.setCellStyle(style);
        } else {
            Row row1 = s1.getRow(row);
            Cell c = row1.createCell(col);
            c.setCellValue(texto);
            c.setCellStyle(style);
        }
    }

    private void generarArchivo(Workbook wb, String nombreRpt) throws IOException {
        String patron = "dd-MM-yyyy";
        SimpleDateFormat formato = new SimpleDateFormat(patron);
        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        wb.write(outByteStream);

        UtilFile.downloadFileBytes(outByteStream.toByteArray(), nombreRpt, UtilFile.CONTENIDO_XLS, UtilFile.EXTENSION_XLS);
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
            HSSFRow header2 = sheet.getRow(j);
            for (int i = 0; i < header2.getPhysicalNumberOfCells(); i++) {
                HSSFCell cell = header2.getCell(i);
                switch (i) {
                    case 5:
                        if (cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()) {
                            String valor = cell.getStringCellValue();
                            cell.setCellValue(Integer.parseInt(valor));
                            cell.setCellType(CellType.NUMERIC);
                        }
                        break;
                    case 6:
                        if (cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()) {
                            String valor = cell.getStringCellValue();
                            cell.setCellValue(Double.parseDouble(valor));
                            cell.setCellType(CellType.NUMERIC);
                        }
                        break;
                }
            }
        }
    }

    public void dowloadDepartamentoFile(List<ReportePorDepartamentoDto> lista) {
        HSSFCellStyle style;
        try (InputStream ins = GraficoController.class.getClassLoader().getResourceAsStream("sv/gob/mined/apps/sispaqescolar/reporte/cuadro_seguimiento_departamento.xls")) {
            wb1 = (HSSFWorkbook) WorkbookFactory.create(ins);
            FORMATO_DATA = wb1.createDataFormat();
            style = wb1.createCellStyle();
            style.setWrapText(true);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);

            s1 = wb1.getSheetAt(0);   //sheet by index
            Integer B = 1;
            Integer C = 2;
            Integer D = 3;
            Integer E = 4;
            Integer F = 5;
            Integer G = 6;
            Integer H = 7;
            Integer I = 8;
            Integer J = 9;
            Integer K = 10;
            Integer i = 6;
            for (ReportePorDepartamentoDto row : lista) {
                escribirValor(row.getNombreDepartamento(), i, B, style);
                escribirValor(row.getTotalContratosUtiles().toString(), i, C, style);
                escribirValor(row.getTotalEntregasUtiles().toString(), i, D, style);
                escribirValor(row.getTotalContratosZapatos().toString(), i, E, style);
                escribirValor(row.getTotalEntregasZapatos().toString(), i, F, style);
                escribirValor(row.getTotalContratosUniforme().toString(), i, G, style);
                escribirValor(row.getTotalEntregasUniforme().toString(), i, H, style);
                escribirValor(row.getTotalContratos().toString(), i, I, style);
                escribirValor(row.getTotalEntregas().toString(), i, J, style);

                escribirNumero(row.getPorcentajeDeAvance(), i, K, style, false);
                i++;
            }
            generarArchivo(wb1, "REPORTE_DEPARTAMENTO");
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(GraficoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void escribirNumero(String text, Integer row, Integer col, CellStyle style, Boolean entero) {
        HSSFRow hrow = s1.getRow(row);
        HSSFCell cell = hrow.getCell(col);
        if (cell == null) {
            hrow.createCell(col);
            cell = hrow.getCell(col);
        }
        cell.setCellType(CellType.NUMERIC);
        style.setDataFormat(entero ? FORMATO_DATA.getFormat("#,##0") : FORMATO_DATA.getFormat("#,##0.00"));
        cell.setCellStyle(style);
        cell.setCellValue(entero ? Integer.parseInt(text) : Double.parseDouble(text));
    }

    public void dowloadFile(List<ReporteGeneralDTO> lista) throws FileNotFoundException, IOException, InvalidFormatException, URISyntaxException {
        CellStyle style;
        try (InputStream ins = GraficoController.class.getClassLoader().getResourceAsStream("sv/gob/mined/apps/sispaqescolar/reporte/cuadro_seguimiento_CE.xls")) {
            wb1 = (HSSFWorkbook) WorkbookFactory.create(ins);
            style = wb1.createCellStyle();
            style.setWrapText(true);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
        }
        s1 = wb1.getSheetAt(0);   //sheet by index
        Integer C = 2;
        Integer D = 3;
        Integer E = 4;
        Integer F = 5;
        Integer G = 6;
        Integer H = 7;
        Integer I = 8;
        Integer J = 9;
        Integer K = 10;
        Integer L = 11;
        Integer i = 7;

        for (ReporteGeneralDTO repdet : lista) {
            escribirValor(repdet.getNOMBRE(), i, C, style);
            escribirValor(repdet.getTOTAL_UTILES().toString(), i, D, style);
            escribirValor(repdet.getENTREGADO_UTILES().toString(), i, E, style);
            escribirValor(repdet.getTOTAL_ZAPATOS().toString(), i, F, style);
            escribirValor(repdet.getENTREGADO_ZAPATOS().toString(), i, G, style);
            escribirValor(repdet.getTOTAL_UNIFORME().toString(), i, H, style);
            escribirValor(repdet.getENTREGADO_UNIFORME().toString(), i, I, style);
            escribirValor(repdet.getTOTALCONTRATADO().toString(), i, J, style);
            escribirValor(repdet.getTOTALENTREGADO().toString(), i, K, style);
            escribirValor(String.valueOf(repdet.getPORCENTAJEAVAN()), i, L, style);
            i++;
        }

        generarArchivo(wb1, "REPORTE_GENERAL");
    }

    public void buscarAvanceSegProcesador() {
        buscarProcesoSeg();
        mostrarGrafico = true;
        if (codigoDepartamento != null) {

            List<Object> lista = proveedorEJB.findAvanceSeguimientos(codigoDepartamento, detalleProcesoSeg);
            totalPendientes = 0;
            totalProceso = 0;
            totalFinalizados = 0;

            if (!lista.isEmpty()) {
                for (Object object : lista) {
                    Object[] datos = (Object[]) object;
                    totalPendientes = (Float.parseFloat(datos[0].toString()));
                    totalProceso = (Float.parseFloat(datos[1].toString()));
                    totalFinalizados = (Float.parseFloat(datos[2].toString()));
                }
            } else {
                JsfUtil.mensajeInformacion("No se encontraron registros.");
            }
        }
    }

    public void buscarAvanceSegProcesadorProveedor() {
        buscarProcesoSeg();
        if (codigoDepartamento != null) {

            List<Object> lista = proveedorEJB.findAvanceSeguimientosPorProveedor(codigoDepartamento, detalleProcesoSeg);
            totalPendientesProveedor = 0;
            totalProcesoProveedor = 0;
            totalFinalizadosProveedor = 0;

            if (!lista.isEmpty()) {
                for (Object object : lista) {
                    Object[] datos = (Object[]) object;
                    totalPendientesProveedor = (Float.parseFloat(datos[0].toString()));
                    totalProcesoProveedor = (Float.parseFloat(datos[1].toString()));
                    totalFinalizadosProveedor = (Float.parseFloat(datos[2].toString()));
                }
            } else {
                JsfUtil.mensajeInformacion("No se encontraron registros.");
            }
        }
    }

    public void buscarAvanceSegProcesadorCentroEducativo() {
        buscarProcesoSeg();
        mostrarGraficoCentroEducativo = true;
        if (codigoDepartamento != null) {

            List<Object> lista = proveedorEJB.findAvanceSeguimientosPorCentroEducativo(codigoDepartamento, detalleProcesoSeg);
            totalPendientesCentroEdu = 0;
            totalProcesoCentroEdu = 0;
            totalFinalizadosCentroEdu = 0;

            if (!lista.isEmpty()) {
                for (Object object : lista) {
                    Object[] datos = (Object[]) object;
                    totalPendientesCentroEdu = (Float.parseFloat(datos[0].toString()));
                    totalProcesoCentroEdu = (Float.parseFloat(datos[1].toString()));
                    totalFinalizadosCentroEdu = (Float.parseFloat(datos[2].toString()));
                }
            } else {
                JsfUtil.mensajeInformacion("No se encontraron registros.");
            }
        }
    }

    public PieChartModel getPieModelSeguimiento() {
        createPieModelSeguimiento();
        return pieModelSeguimiento;
    }

    private void createPieModelSeguimiento() {
        pieModelSeguimiento = new PieChartModel();
        float tot = totalPendientes;
        float totalp = totalProceso;
        float totalf = totalFinalizados;
        float totalPend = totalPendientes - (totalProceso + totalFinalizados);

        pieModelSeguimiento.set("Contratos En proceso " + (int) totalProceso, (100 * totalp) / tot);
        pieModelSeguimiento.set("Contratos Pendientes " + (int) totalPend, (100 * totalPend) / tot);
        pieModelSeguimiento.set("Contratos Finalizados " + (int) totalFinalizados, (100 * totalf) / tot);
        pieModelSeguimiento.setLegendPosition("e");
        pieModelSeguimiento.setFill(false);
        pieModelSeguimiento.setShowDataLabels(true);
        pieModelSeguimiento.setDiameter(150);
    }

    public void buscarAvanceSeguimiento() {
        this.porcentajeAvance = "";
        buscarAvanceSegProcesador();
        buscarAvanceSeguimientoProveedor();
        buscarAvanceSeguimientoCentroEducativo();
    }

    public void buscarAvanceSeguimientoProveedor() {
        buscarAvanceSegProcesadorProveedor();
    }

    public void buscarAvanceSeguimientoCentroEducativo() {
        buscarAvanceSegProcesadorCentroEducativo();
    }

    public PieChartModel getPieModelSeguimientoCentroEdu() {
        createPieModelSeguimientoCentroEdu();
        return pieModelSeguimientoCentroEdu;
    }

    public void updateDetProcesoAdq() {
        detalleProcesoSeg = anhoProcesoEJB.getDetProcesoAdq(recuperarProceso.getProcesoAdquisicion(), rubroSeg);
    }

    public void generarReportesDepartamentoExcel() {
        if (recuperarProceso.getProcesoAdquisicion().getIdProcesoAdq() != null) {
            List<ReportePorDepartamentoDto> lista = recepcionEJB.getLstReporteGeneralDepartamento(recuperarProceso.getProcesoAdquisicion().getDetalleProcesoAdqList().get(0).getIdDetProcesoAdq(), recuperarProceso.getProcesoAdquisicion().getDetalleProcesoAdqList().get(1).getIdDetProcesoAdq(), recuperarProceso.getProcesoAdquisicion().getDetalleProcesoAdqList().get(2).getIdDetProcesoAdq());
            dowloadDepartamentoFile(lista);
        }
    }

    public void generarReportesProveedoresExcel() throws FileNotFoundException, IOException, InvalidFormatException, URISyntaxException {
        if (recuperarProceso.getProcesoAdquisicion().getIdProcesoAdq() != null) {
            List<ReporteGeneralDTO> lista = recepcionEJB.getLstReporteGeneral(recuperarProceso.getProcesoAdquisicion(), codigoDepartamento);
            dowloadFile(lista);
        }
    }

    public void generarReportesCEExcel() throws FileNotFoundException, IOException, InvalidFormatException, URISyntaxException {
        if (recuperarProceso.getProcesoAdquisicion().getIdProcesoAdq() != null) {
            List<VwSeguimientoRptCentroEscolar> lista = recepcionEJB.getLstSeguimientoRptCE(detalleProcesoSeg.getIdDetProcesoAdq(), codigoDepartamento);

            HSSFCellStyle style;
            try (InputStream ins = GraficoController.class.getClassLoader().getResourceAsStream("sv/gob/mined/apps/sispaqescolar/reporte/cuadro_seguimiento_CE2.xls")) {
                wb1 = (HSSFWorkbook) WorkbookFactory.create(ins);
                style = wb1.createCellStyle();
                style.setWrapText(true);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                HSSFFont txtFont = (HSSFFont) wb1.createFont();
                txtFont.setFontName("Arial");
                txtFont.setFontHeightInPoints((short) 10.0);
                style.setFont(txtFont);
            }
            HSSFSheet hoja = wb1.getSheetAt(0);
            Integer A = 0;
            Integer B = 1;
            Integer C = 2;
            Integer D = 3;
            Integer E = 4;
            Integer F = 5;
            Integer G = 6;
            Integer H = 7;
            Integer I = 8;
            Integer J = 9;
            Integer K = 10;
            Integer i = 1;
            for (VwSeguimientoRptCentroEscolar repdet : lista) {
                escribirTexto(hoja, repdet.getCodigoEntidad(), i, A, style);
                escribirTexto(hoja, repdet.getNombre(), i, B, style);
                escribirTexto(hoja, repdet.getNombreDepartamento(), i, C, style);
                escribirTexto(hoja, repdet.getNombreMunicipio(), i, D, style);
                escribirTexto(hoja, repdet.getRubro(), i, E, style);
                escribirTexto(hoja, repdet.getNumeroNit(), i, F, style);
                escribirTexto(hoja, repdet.getRazonSocial(), i, G, style);
                escribirNumero(hoja, repdet.getMontoContratado().toString(), i, H, style, false);
                escribirNumero(hoja, repdet.getCantidadContratada().toString(), i, I, style, true);
                escribirNumero(hoja, repdet.getCantidadEntregada().toString(), i, J, style, true);
                escribirTexto(hoja, repdet.getEstadoEntrega(), i, K, style);
                i++;
            }
            generarArchivo(wb1, "REPORTE_CENTRO");
        }
    }

    private void escribirTexto(HSSFSheet hoja, String text, Integer row, Integer col, CellStyle style) {
        HSSFRichTextString texto = new HSSFRichTextString(text);
        if (hoja.getRow(row) == null) {
            Row row1 = hoja.createRow(row);
            Cell c = row1.createCell(col);
            c.setCellValue(texto);
            c.setCellStyle(style);
        } else {
            Row row1 = hoja.getRow(row);
            Cell c = row1.createCell(col);
            c.setCellValue(texto);
            c.setCellStyle(style);
        }
    }

    private void escribirNumero(HSSFSheet hoja, String text, Integer row, Integer col, CellStyle style, Boolean entero) {
        HSSFRow hrow = hoja.getRow(row);
        HSSFCell cell = hrow.getCell(col);
        if (cell == null) {
            hrow.createCell(col);
            cell = hrow.getCell(col);
        }
        cell.setCellValue(entero ? Integer.parseInt(text) : Double.parseDouble(text));
        cell.setCellType(CellType.NUMERIC);
        cell.setCellStyle(style);
    }

    public void buscarTotal() {
        obtenerDatos();
    }

    public void obtenerDatos() {
        detalleProcesoSeg = anhoProcesoEJB.getDetProcesoAdq(recuperarProceso.getProcesoAdquisicion(), rubroSeg);
        List<Object> lista = proveedorEJB.findAvanceContratacionByDepartamento(detalleProcesoSeg, codigoDepartamento);
        listaCapacidad = new ArrayList(0);
        BigDecimal total1 = BigDecimal.ZERO;
        parametroMayor = BigDecimal.ZERO;
        if (!lista.isEmpty()) {
            for (Object object : lista) {
                GraficoTipoEmpresaDTO result = new GraficoTipoEmpresaDTO();
                Object[] datos = (Object[]) object;
                result.setNombretipoEmpresa(datos[0].toString());
                result.setTotal(new BigDecimal(datos[1].toString()));
                if (result.getTotal().compareTo(total1) == 1) {
                    total1 = result.getTotal();
                }
                result.setTotalFormatedo(JsfUtil.getFormatoNum(result.getTotal(), true));
                result.setTotaltipoEmp(new BigDecimal(datos[2].toString()));
                listaCapacidad.add(result);
            }
            parametroMayor = total1;
        } else {
            mostrarGrafico = false;
            JsfUtil.mensajeInformacion("No se encontraron registros ");
        }
    }

    public void generarProveedoreHacienda() {
        RptExcel.generarRptProveedoresHacienda(proveedorEJB.getLstProveedoresHacienda(detalleProcesoSeg.getIdDetProcesoAdq(), codigoDepartamento));
    }
}
