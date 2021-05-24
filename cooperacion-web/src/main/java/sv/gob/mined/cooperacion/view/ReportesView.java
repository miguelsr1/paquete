/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import sv.gob.mined.cooperacion.facade.CatalogoFacade;
import sv.gob.mined.cooperacion.model.dto.MatrizProyectoDto;

@Named
@ViewScoped
public class ReportesView implements Serializable {
    
    private HSSFWorkbook wb;
    private DataFormat FORMATO_DATA;
    private final DateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yyyy");
    private final String EXTENSION_XLS = "xls";
    private final String CONTENIDO_XLS = "application/ms-excel";
    private final DateFormat FORMAT_DATE_RPT = new SimpleDateFormat("ddMMMyy_HHmmss");
    
    @Inject
    private CatalogoFacade catalogoFacade;
    
    public void generarMatrizProyecto() {
        List<MatrizProyectoDto> lstDatos = catalogoFacade.getMatrizProyectosByAnho("2020");
        HSSFCellStyle style;
        int row = 1;
        try (InputStream ins = ReportesView.class.getClassLoader().getResourceAsStream("sv/gob/mined/rpt/excel/matrizProyecto.xls")) {
            wb = (HSSFWorkbook) WorkbookFactory.create(ins);
            FORMATO_DATA = wb.createDataFormat();
            style = wb.createCellStyle();
            
            HSSFSheet s1 = wb.getSheetAt(0);   //sheet by index

            for (MatrizProyectoDto dato : lstDatos) {
                escribirNumero(dato.getIdProyecto().toString(), row, 0, style, true, s1);
                escribirTexto(dato.getNombreProyecto(), row, 1, style, s1);
                escribirTexto(dato.getDescripcion(), row, 2, style, s1);
                escribirTexto(dato.getInstitucion(), row, 3, style, s1);
                escribirTexto(dato.getSectorIntervencion(), row, 4, style, s1);
                escribirTexto(dato.getTmCooperacion(), row, 5, style, s1);
                escribirTexto(dato.getDescripcionObjetivo(), row, 6, style, s1);
                escribirTexto(dato.getDescripcionMeta(), row, 7, style, s1);
                escribirTexto(dato.getFechaEstimadaInicio(), row, 8, style, s1);
                escribirTexto(dato.getFechaEstimadaFin(), row, 9, style, s1);
                escribirTexto(dato.getNombreCooperante(), row, 10, style, s1);
                
                escribirNumero(dato.getMontoInversion().toString(), row, 11, style, false, s1);
                escribirTexto("", row, 12, style, s1);
                escribirTexto("", row, 13, style, s1);
                escribirTexto("", row, 14, style, s1);
                escribirTexto("", row, 15, style, s1);
                row++;
            }
            
            generarArchivo(wb, "rptMatrizProyecto");
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void escribirTexto(String text, Integer row, Integer col, CellStyle style, HSSFSheet hoja) {
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
    
    private void escribirFecha(Date fecha, Integer row, Integer col, CellStyle style, HSSFSheet hoja) {
        HSSFRow row1 = hoja.getRow(row);
        HSSFCell cell = row1.getCell(col);
        if (cell == null) {
            cell = row1.createCell(col);
        }
        if (fecha != null) {
            cell.setCellValue(FORMAT_DATE.format(fecha));
        }
        cell.setCellStyle(style);
    }
    
    private void escribirNumero(String text, Integer row, Integer col, CellStyle style, Boolean entero, HSSFSheet hoja) {
        text = text.replace(",", "");
        HSSFRow hrow = hoja.getRow(row);
        HSSFCell cell = hrow.getCell(col);
        if (cell == null) {
            hrow.createCell(col);
            cell = hrow.getCell(col);
        }
        style.setDataFormat(entero ? FORMATO_DATA.getFormat("#,##0") : FORMATO_DATA.getFormat("#,##0.00"));
        cell.setCellStyle(style);
        if (text != null && !text.isEmpty()) {
            cell.setCellValue(entero ? Integer.parseInt(text) : Double.parseDouble(text));
        }
    }
    
    private void generarArchivo(Workbook wb, String nombreFile) {
        try (ByteArrayOutputStream outByteStream = new ByteArrayOutputStream()) {
            wb.write(outByteStream);
            byte[] outArray = outByteStream.toByteArray();
            downloadFileBytes(outArray, nombreFile, CONTENIDO_XLS, EXTENSION_XLS);
        } catch (IOException ex) {
            Logger.getLogger(ReportesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void downloadFileBytes(byte[] outArray, String nombreFile, String tipoDeContenido, String extension) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        
        response.setContentType(tipoDeContenido);
        response.setContentLength(outArray.length);
        response.setHeader("Content-disposition", "attachment; filename=" + nombreFile + "-" + getFechaGeneracionReporte() + "." + extension);
        OutputStream outStream = response.getOutputStream();
        outStream.write(outArray);
        outStream.flush();
        outStream.close();
        facesContext.responseComplete();
        facesContext.renderResponse();
    }
    
    public String getFechaGeneracionReporte() {
        return FORMAT_DATE_RPT.format(new Date());
    }
    
    public String formatearNumero(int posisiones, String valor, Boolean numInt) {
        valor = valor.replaceAll("[.]", "");
        Formatter fmt = new Formatter();
        if (numInt) {
            fmt.format("%0" + posisiones + "d", Integer.parseInt(valor));
        } else {
            fmt.format("%-" + posisiones + "s", valor);
        }
        return fmt.toString();
    }
}
