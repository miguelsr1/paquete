/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import sv.gob.mined.paquescolar.model.pojos.Bean;

/**
 *
 * @author oamartinez
 */
public class Bean2Excel {

    private HSSFWorkbook workbook;
    private HSSFFont boldFont;
    private HSSFDataFormat format;
    private String rubro = "";
    private String nombreCentroEducativo = "";
    private String codigoCentroEducativo = "";
    private String fuenteFinanciamiento = "";
    private String fechaElaboracion = "";
    private String digitador = "";
    private List listado;

    public Bean2Excel() {
        workbook = new HSSFWorkbook();
        boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        format = workbook.createDataFormat();
    }

    public Bean2Excel(List listadoProv, String rubroCE, String nombreCE, String codigoCE, String fuenteFinCE, String fechaElabCE, String digitadorCE) {
        workbook = new HSSFWorkbook();
        boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        format = workbook.createDataFormat();

        rubro = rubroCE.toUpperCase();
        nombreCentroEducativo = nombreCE.toUpperCase();
        codigoCentroEducativo = codigoCE.toUpperCase();
        fuenteFinanciamiento = fuenteFinCE.toUpperCase();
        fechaElaboracion = fechaElabCE.toUpperCase();
        digitador = digitadorCE.toUpperCase();
        listado = listadoProv;
    }

    public void createFile(String codigoEntidad) {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            ReportColumn[] reportColumns = new ReportColumn[]{
                new ReportColumn("numItem", "ITEM", FormatType.TEXT),
                new ReportColumn("item", "DESCRIPCION DEL ITEM", FormatType.TEXT),
                new ReportColumn("cantidadRequerida", "CANTIDAD REQUERIDA", FormatType.INTEGER),
                new ReportColumn("listadoProveedores", "NOMBRES DE LAS PERSONAS PROVEEDORAS", FormatType.OBJECT)
            };

            ReportColumn[] reportColumns1 = new ReportColumn[]{
                new ReportColumn("nombreProveedor", "Nombre Proveedor", FormatType.TEXT),
                new ReportColumn("listadoDatos", "", FormatType.OBJECT)
            };

            ReportColumn[] reportColumns2 = new ReportColumn[]{
                new ReportColumn("cantidadOfertada", "Cantidad Ofertada", FormatType.TEXT),
                new ReportColumn("precioUnitario", "Precio Unitario", FormatType.TEXT),
                new ReportColumn("cantidadAdjudicada", "Cantidad Adjudicada", FormatType.TEXT)
            };

            this.addSheet(listado, reportColumns, reportColumns1, reportColumns2, "economico");
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            FacesContext fc = FacesContext.getCurrentInstance();
            workbook.write(outByteStream);
            byte[] outArray = outByteStream.toByteArray();
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(outArray.length);
            response.setHeader("Content-disposition", "attachment; filename=analisis_" + codigoEntidad + ".xls");
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);
            outStream.flush();
            fc.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSheet(List<?> listado, ReportColumn[] columns, ReportColumn[] columns1, ReportColumn[] columns2, String sheetName) {

        HSSFSheet sheet = workbook.createSheet(sheetName);
        int numCols = columns.length;
        int numCols1 = columns1.length;
        int numCols2 = columns2.length;
        int currentRow = 0;
        HSSFRow row;

        try {

            LinkedHashMap<String, Integer> mapaItems = (LinkedHashMap) listado.get(0);
            LinkedHashMap<String, Integer> mapaRazonSocial = (LinkedHashMap) listado.get(1);
            Bean[][] data = (Bean[][]) listado.get(2);
            LinkedHashMap<String, String> mapaItemsIndex = (LinkedHashMap) listado.get(3);
            LinkedHashMap<String, Integer> mapaCantidadItems = (LinkedHashMap) listado.get(4);

            HSSFFont hSSFFont = workbook.createFont();
            hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
            hSSFFont.setFontHeightInPoints((short) 8);
            hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            sheet.getPrintSetup().setLandscape(true);
            sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.LETTER_PAPERSIZE);
            sheet.setMargin(HSSFSheet.BottomMargin, 0.75);
            sheet.setMargin(HSSFSheet.TopMargin, 0.75);
            sheet.setMargin(HSSFSheet.LeftMargin, 0.75);
            sheet.setMargin(HSSFSheet.RightMargin, 0.75);

            HSSFCellStyle myBoldStyle = workbook.createCellStyle();
            myBoldStyle.setFont(this.boldFont);

            HSSFCellStyle myNoParticipateStyle = workbook.createCellStyle();
            myNoParticipateStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            myNoParticipateStyle.setBorderBottom(BorderStyle.THIN);
            myNoParticipateStyle.setBorderTop(BorderStyle.THIN);
            myNoParticipateStyle.setBorderRight(BorderStyle.THIN);
            myNoParticipateStyle.setBorderLeft(BorderStyle.THIN);
            myNoParticipateStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
            myNoParticipateStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

            HSSFCellStyle myNormalStyle = workbook.createCellStyle();
            myNormalStyle.setFont(hSSFFont);
            myNormalStyle.setWrapText(true);
            myNormalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            myNormalStyle.setBorderBottom(BorderStyle.THIN);
            myNormalStyle.setBorderTop(BorderStyle.THIN);
            myNormalStyle.setBorderRight(BorderStyle.THIN);
            myNormalStyle.setBorderLeft(BorderStyle.THIN);

            HSSFCellStyle myParticularStyle = workbook.createCellStyle();
            myParticularStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            myParticularStyle.setBorderBottom(BorderStyle.NONE);
            myParticularStyle.setBorderTop(BorderStyle.NONE);
            myParticularStyle.setBorderRight(BorderStyle.NONE);
            myParticularStyle.setBorderLeft(BorderStyle.NONE);

            HSSFCellStyle myStyle = workbook.createCellStyle();
            myStyle.setRotation(new Short("90"));
            myStyle.setFont(this.boldFont);
            myStyle.setBorderBottom(BorderStyle.THIN);
            myStyle.setBorderTop(BorderStyle.THIN);
            myStyle.setBorderRight(BorderStyle.THIN);
            myStyle.setBorderLeft(BorderStyle.THIN);

            HSSFCellStyle myDataStyle = workbook.createCellStyle();
            myDataStyle.setBorderBottom(BorderStyle.THIN);
            myDataStyle.setBorderTop(BorderStyle.THIN);
            myDataStyle.setBorderRight(BorderStyle.THIN);
            myDataStyle.setBorderLeft(BorderStyle.THIN);

            String[] proveedoresAMostrar = new String[mapaRazonSocial.size()];
            String[] itemsAMostrar = new String[mapaItems.size()];

            mapaRazonSocial.keySet().toArray(proveedoresAMostrar);
            mapaItems.keySet().toArray(itemsAMostrar);

            currentRow++;
            currentRow++;
            currentRow++;
            // Create the report header at row 0
            row = sheet.createRow(currentRow);
            // Loop over all the column beans and populate the report headers
            for (int i = 0; i < numCols; i++) {
                // Get the header text from the bean and write it to the cell
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(myNormalStyle);
                cell.setCellValue(columns[i].getHeader());
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            }
            sheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow, 3, 2 + (proveedoresAMostrar.length * numCols2)));

            currentRow++; // increment the spreadsheet row before we step into the data
            // Create the report header at row 0
            row = sheet.createRow(currentRow);
            // Loop over all the column beans and populate the report headers
            for (int l = 0; l < proveedoresAMostrar.length; l++) {
                for (int m = 0; m < numCols1; m++) {
                    // Get the header text from the bean and write it to the cell
                    HSSFCell cell = row.createCell(m + 3 + (l * 3));
                    cell.setCellStyle(myNormalStyle);
                    cell.setCellValue(proveedoresAMostrar[l]);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                }
                sheet.addMergedRegion(new CellRangeAddress(currentRow, currentRow, 3 + (l * 3), 5 + (l * 3)));
            }

            currentRow++; // increment the spreadsheet row before we step into the data
            // Create the report header at row 0
            row = sheet.createRow(currentRow);
            row.setHeight(new Short("2100"));
            // Loop over all the column beans and populate the report headers           
            for (int l = 0; l < proveedoresAMostrar.length; l++) {
                for (int m = 0; m < numCols2; m++) {
                    // Get the header text from the bean and write it to the cell
                    HSSFCell cell = row.createCell(m + 3 + (l * 3));
                    cell.setCellStyle(myStyle);
                    cell.setCellValue(columns2[m].getHeader());
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                }
            }

            currentRow++; // increment the spreadsheet row before we step into the data
            for (String itemsAMostrar1 : itemsAMostrar) {
                row = sheet.createRow(currentRow);
                HSSFCell cell = row.createCell(0);
                cell.setCellStyle(myDataStyle);
                cell.setCellValue(mapaItemsIndex.get(itemsAMostrar1));
                cell = row.createCell(1);
                cell.setCellStyle(myDataStyle);
                cell.setCellValue(itemsAMostrar1);
                cell = row.createCell(2);
                cell.setCellStyle(myDataStyle);
                cell.setCellValue(mapaCantidadItems.get(itemsAMostrar1));
                currentRow++;
            }

            for (int j = 0; j < itemsAMostrar.length; j++) {
                HSSFCell cell;
                row = sheet.getRow(j + 6);
                for (int k = 0; k < proveedoresAMostrar.length; k++) {
                    Bean bean = (Bean) data[j][k];
                    if (bean != null) {
                        cell = row.createCell((k * 3) + 3);
                        cell.setCellStyle(myDataStyle);
                        cell.setCellValue(bean.getCantidadOfertada());
                        cell = row.createCell((k * 3) + 4);
                        cell.setCellStyle(myDataStyle);
                        cell.setCellValue(bean.getPrecioUnitario());
                        cell = row.createCell((k * 3) + 5);
                        cell.setCellStyle(myDataStyle);
                        cell.setCellValue(bean.getCantidadAdjudicada());
                    } else {
                        cell = row.createCell((k * 3) + 3);
                        cell.setCellStyle(myNoParticipateStyle);
                        cell.setCellValue("");
                        cell = row.createCell((k * 3) + 4);
                        cell.setCellStyle(myNoParticipateStyle);
                        cell.setCellValue("");
                        cell = row.createCell((k * 3) + 5);
                        cell.setCellStyle(myNoParticipateStyle);
                        cell.setCellValue("");
                    }
                }
            }

            sheet.getRow(4).setHeight(new Short("1000"));
            row = sheet.getRow(5);
            for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                sheet.autoSizeColumn(colNum);
            }

            currentRow++;
            currentRow++;
            row = sheet.createRow(currentRow);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("RAZONAMIENTO:");

            currentRow++;
            currentRow++;
            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue("F._____________________________________");
            currentRow++;
            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue("Representante Legal (Presidente del Organismo de Administración Escolar(a)");
            currentRow++;
            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue("CDE, CECE o CDI");

            row = sheet.getRow(3);
            cell = row.getCell(0);
            cell.setCellValue("");
            cell.setCellStyle(myParticularStyle);
            cell = row.getCell(1);
            cell.setCellValue("");
            cell.setCellStyle(myParticularStyle);
            cell = row.getCell(2);
            cell.setCellValue("");
            cell.setCellStyle(myParticularStyle);

            row = sheet.getRow(5);
            cell = row.createCell(0);
            cell.setCellStyle(myNormalStyle);
            cell.setCellValue("ITEM");
            sheet.setColumnWidth(cell.getColumnIndex(), 1500);
            cell = row.createCell(1);
            cell.setCellStyle(myNormalStyle);
            cell.setCellValue("DESCRIPCIÓN DEL ITEM");
            cell = row.createCell(2);
            cell.setCellStyle(myNormalStyle);
            cell.setCellValue("CANTIDAD REQUERIDA");
            sheet.setColumnWidth(cell.getColumnIndex(), 3000);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2 + (proveedoresAMostrar.length * numCols2)));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2 + (proveedoresAMostrar.length * numCols2)));
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2 + (proveedoresAMostrar.length * numCols2)));

            row = sheet.createRow(0);
            HSSFCell cell1 = row.createCell(0);
            cell1.setCellStyle(myBoldStyle);
            cell1.setCellValue("ANALISIS ECONOMICO RUBRO " + rubro);
            currentRow++;
            row = sheet.createRow(1);
            cell1 = row.createCell(0);
            cell1.setCellStyle(myBoldStyle);
            cell1.setCellValue("NOMBRE DEL CENTRO EDUCATIVO : " + nombreCentroEducativo + " CODIGO : " + codigoCentroEducativo);
            currentRow++;
            row = sheet.createRow(2);
            cell1 = row.createCell(0);
            cell1.setCellStyle(myBoldStyle);
            cell1.setCellValue("FUENTE DE FINANCIAMIENTO : " + fuenteFinanciamiento + " FECHA DE ELABORACIÓN : " + fechaElaboracion);
            currentRow++;

            String leyenda = HSSFHeader.font("Arial", "Bold") + HSSFHeader.fontSize((short) 8) + "ANALISIS TÉCNICO RUBRO : " + rubro + ", CENTRO EDUCATIVO : " + nombreCentroEducativo + " CODIGO : " + codigoCentroEducativo;
            sheet.getHeader().setCenter(leyenda);
            sheet.getFooter().setLeft(digitador);
            sheet.getFooter().setRight("Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages());

            sheet.setAutobreaks(false);
            sheet.setRowBreak(sheet.getLastRowNum());
            sheet.setColumnBreak(20);
            generateTechnicalAnalisisSheet("ANALISIS_TECNICO", proveedoresAMostrar, rubro,
                    "PRESENTACIÓN DEL DOCUMENTO DE LA DECLARACION JURADA GLOBAL DE CUMPLIMIENTO DEL TÉRMINO DE REFERENCIA PLAZO Y LUGAR DE ENTREGA.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void generateTechnicalAnalisisSheet(String pSheetName, String[] pProvAMostrar, String descripcionRubro, String medicion) {
        HSSFSheet sheet = workbook.createSheet(pSheetName);
        int currentRow = 0;
        HSSFRow row;

        HSSFFont hSSFFont = workbook.createFont();
        hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFont.setFontHeightInPoints((short) 8);
        hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        HSSFCellStyle myBoldTitleStyle = workbook.createCellStyle();
        myBoldTitleStyle.setFont(this.boldFont);

        HSSFCellStyle myBoldStyle = workbook.createCellStyle();
        myBoldStyle.setFont(this.boldFont);
        myBoldStyle.setWrapText(true);
        myBoldStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        myBoldStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

        HSSFCellStyle myNormalStyle = workbook.createCellStyle();
        myNormalStyle.setFont(hSSFFont);
        myNormalStyle.setWrapText(true);
        myNormalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        myNormalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        myNormalStyle.setBorderBottom(BorderStyle.THIN);
        myNormalStyle.setBorderTop(BorderStyle.THIN);
        myNormalStyle.setBorderRight(BorderStyle.THIN);
        myNormalStyle.setBorderLeft(BorderStyle.THIN);

        HSSFCellStyle myParticularStyle = workbook.createCellStyle();
        myParticularStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        myParticularStyle.setBorderBottom(BorderStyle.NONE);
        myParticularStyle.setBorderTop(BorderStyle.NONE);
        myParticularStyle.setBorderRight(BorderStyle.NONE);
        myParticularStyle.setBorderLeft(BorderStyle.NONE);

        HSSFCellStyle myStyle = workbook.createCellStyle();
        myStyle.setRotation(new Short("90"));
        myStyle.setFont(this.boldFont);
        myStyle.setBorderBottom(BorderStyle.THIN);
        myStyle.setBorderTop(BorderStyle.THIN);
        myStyle.setBorderRight(BorderStyle.THIN);
        myStyle.setBorderLeft(BorderStyle.THIN);

        HSSFCellStyle myDataStyle = workbook.createCellStyle();
        myDataStyle.setBorderBottom(BorderStyle.THIN);
        myDataStyle.setBorderTop(BorderStyle.THIN);
        myDataStyle.setBorderRight(BorderStyle.THIN);
        myDataStyle.setBorderLeft(BorderStyle.THIN);

        try {
            Map<String, String> proveedores = new LinkedHashMap<>();
            for (String provee : pProvAMostrar) {
                if (!proveedores.containsKey(provee)) {
                    proveedores.put(provee, provee);
                }
            }
            pProvAMostrar = proveedores.keySet().toArray(new String[0]);
            sheet.getPrintSetup().setLandscape(true);
            sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.LETTER_PAPERSIZE);
            sheet.setMargin(HSSFSheet.BottomMargin, 0.75);
            sheet.setMargin(HSSFSheet.TopMargin, 0.75);
            sheet.setMargin(HSSFSheet.LeftMargin, 0.75);
            sheet.setMargin(HSSFSheet.RightMargin, 0.75);

            row = sheet.createRow(0);
            HSSFCell cell1 = row.createCell(0);
            cell1.setCellStyle(myBoldTitleStyle);
            cell1.setCellValue(
                    "MODELO DE FORMATO PARA ANALISIS TÉCNICO RUBRO " + rubro);
            currentRow++;
            row = sheet.createRow(1);
            cell1 = row.createCell(0);
            cell1.setCellStyle(myBoldTitleStyle);
            cell1.setCellValue("NOMBRE DEL CENTRO EDUCATIVO : "
                    + nombreCentroEducativo + " CODIGO : " + codigoCentroEducativo);
            currentRow++;
            row = sheet.createRow(2);
            cell1 = row.createCell(0);
            cell1.setCellStyle(myBoldTitleStyle);
            cell1.setCellValue("FUENTE DE FINANCIAMIENTO : "
                    + fuenteFinanciamiento + " FECHA DE ELABORACIÓN : " + fechaElaboracion);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1
                    + (pProvAMostrar.length * 2)));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1
                    + (pProvAMostrar.length * 2)));
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1
                    + (pProvAMostrar.length * 2)));

            currentRow++;
            //Fila para el titulo de los nombres de proveedores
            row = sheet.createRow(currentRow);
            cell1 = row.createCell(2);
            cell1.setCellStyle(myNormalStyle);
            cell1.setCellValue("NOMBRES DE LOS PROVEEDORES");
            sheet.addMergedRegion(new CellRangeAddress(currentRow,
                    currentRow, 2, 1 + (pProvAMostrar.length * 2)));

            currentRow++;
            //Fila para ingresar en la hoja el nombre de los proveedores
            row = sheet.createRow(currentRow);
            int cellIndex = 2;
            for (String prov : pProvAMostrar) {
                cell1 = row.createCell(cellIndex);
                cell1.setCellStyle(myNormalStyle);
                cell1.setCellValue(prov);
                sheet.addMergedRegion(new CellRangeAddress(currentRow,
                        currentRow, cellIndex, cellIndex + 1));
                cellIndex += 2;
            }
            currentRow++;
            //Fila para ingresar leyendas de cumple y no cumple
            row = sheet.createRow(currentRow);

            cell1 = row.createCell(0);
            cell1.setCellStyle(myBoldStyle);
            cell1.setCellValue("DESCRIPCIÓN DEL RUBRO");
            cell1 = row.createCell(1);
            cell1.setCellStyle(myBoldStyle);
            cell1.setCellValue("MEDICIÓN");

            int cellIndexLabel = 2;
            for (String prov : pProvAMostrar) {
                cell1 = row.createCell(cellIndexLabel);
                cell1.setCellStyle(myNormalStyle);
                cell1.setCellValue("Cumple");

                cell1 = row.createCell(cellIndexLabel + 1);
                cell1.setCellStyle(myNormalStyle);
                cell1.setCellValue("No Cumple");
                cellIndexLabel += 2;
            }
            currentRow++;
            //Fila para ingreso de usuario
            row = sheet.createRow(currentRow);
            cell1 = row.createCell(0);
            cell1.setCellStyle(myNormalStyle);
            cell1.setCellValue(descripcionRubro);
            cell1 = row.createCell(1);
            cell1.setCellStyle(myNormalStyle);
            cell1.setCellValue(medicion);

            for (int i = 2; i < (pProvAMostrar.length * 2) + 2; i++) {
                cell1 = row.createCell(i);
                cell1.setCellStyle(myNormalStyle);
            }

            currentRow++;
            currentRow++;
            currentRow++;
            row = sheet.createRow(currentRow);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("RAZONAMIENTO:");

            currentRow++;
            currentRow++;
            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue("F._____________________________________");
            currentRow++;
            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue(
                    "Representante Legal (Presidente del Organismo de Administración Escolar (a)");
            currentRow++;
            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue("CDE, CECE o CDI");

            String leyenda = HSSFHeader.font("Arial", "Bold")
                    + HSSFHeader.fontSize((short) 8) + "ANALISIS TÉCNICO RUBRO : " + rubro
                    + ", CENTRO EDUCATIVO : " + nombreCentroEducativo + " CODIGO : "
                    + codigoCentroEducativo;
            sheet.getHeader().setCenter(leyenda);
            sheet.getFooter().setLeft(digitador);
            sheet.getFooter().setRight("Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages());

            sheet.getRow(4).setHeight(new Short("1000"));
            sheet.getRow(5).setHeight(new Short("500"));
            sheet.getRow(6).setHeight(new Short("2000"));
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 6000);
            row = sheet.getRow(5);
            for (int colNum = 2; colNum < row.getLastCellNum();
                    colNum++) {
                sheet.setColumnWidth(colNum, 2500);
            }

            sheet.setAutobreaks(false);
            sheet.setRowBreak(sheet.getLastRowNum());
            sheet.setColumnBreak(7);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    public HSSFFont boldFont() {
        return boldFont;
    }

    public void write(OutputStream outputStream) throws Exception {
        workbook.write(outputStream);
    }

    private void writeCell(HSSFRow row, int col, Object value, FormatType formatType, Short bgColor, HSSFFont font)
            throws Exception {

        HSSFCell cell = HSSFCellUtil.createCell(row, col, null);

        if (value == null) {
            return;
        }

        if (font != null) {
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFont(font);
            cell.setCellStyle(style);
        }

        switch (formatType) {
            case TEXT:
                cell.setCellValue(value.toString());
                break;
            case INTEGER:
                cell.setCellValue(((Number) value).intValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("#,##0")));
                break;
            case FLOAT:
                cell.setCellValue(((Number) value).doubleValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("#,##0.00")));
                break;
            case DOUBLE:
                cell.setCellValue(((Double) value));
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("$#,##0.00;$#,##0.00")));
                break;
            case DATE:
                cell.setCellValue((Date) value);
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("m/d/yy")));
                break;
            case MONEY:
                cell.setCellValue(((Number) value).intValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        format.getFormat("$#,##0.00;$#,##0.00"));
                break;
            case PERCENTAGE:
                cell.setCellValue(((Number) value).doubleValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat("0.00%"));
            case OBJECT:
                cell.setCellValue(value.toString());
                break;
        }
        if (bgColor != null) {
            HSSFCellUtil.setCellStyleProperty(cell, workbook,
                    CellUtil.FILL_FOREGROUND_COLOR, bgColor);
            HSSFCellUtil.setCellStyleProperty(cell, workbook,
                    CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
        }

    }

    public enum FormatType {
        TEXT, INTEGER, FLOAT, DATE, MONEY, PERCENTAGE, OBJECT, DOUBLE
    }
}
