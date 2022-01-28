/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paqueupdatetecho;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ws.ConamypeEJB;
import ws.ConamypeEJBService;

/**
 *
 * @author MISanchez
 */
public class Start {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            test();
        } catch (IOException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void enviarDatosProveedor() {
        try {
            JSONParser parser = new JSONParser();
            String nombre = "0614-020400-148-9.txt"; //json7, json8, json9, json10

            Object obj = parser.parse(new FileReader("C:\\Users\\MISanchez\\Documents\\MINED\\paquete\\Paquete 2021\\" + nombre));

            String jsonString = ((JSONObject) obj).toJSONString();

            ConamypeEJBService service = new ConamypeEJBService();
            ConamypeEJB port = service.getConamypeEJBPort();

            port.setDatosProveedor(jsonString, "PRUEBAS_MINED");
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void actualizarCorreoProve() {
        FileInputStream file = null;
        ConamypeEJBService service = new ConamypeEJBService();
        ConamypeEJB port = service.getConamypeEJBPort();

        try {
            file = new FileInputStream(new File("//home//misanchez//Documentos//Feria Escolar//Paquete 2021//correos_zapatos2.xls"));
            // Crear el objeto que tendra el libro de Excel
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            /*
            * Obtenemos la primera pestaña a la que se quiera procesar indicando el indice.
            * Una vez obtenida la hoja excel con las filas que se quieren leer obtenemos el iterator
            * que nos permite recorrer cada una de las filas que contiene.
             */
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            // Recorremos todas las filas para mostrar el contenido de cada celda
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                String numeroNit;
                String correo;

                if (row.getRowNum() != 0) {
                    numeroNit = row.getCell(0).getStringCellValue();
                    correo = row.getCell(1).getStringCellValue();

                    port.updCorreoElectronicoByNit(numeroNit, correo);

                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void actualizarCapacidad() {
        FileInputStream file = null;

        try {
            file = new FileInputStream(new File("C:\\Users\\MISanchez\\Documents\\MINED\\paquete\\Paquete 2021\\CAPACIDADES 2021.xls"));
            // Crear el objeto que tendra el libro de Excel
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;

            ConamypeEJBService service = new ConamypeEJBService();
            ConamypeEJB port = service.getConamypeEJBPort();
            // Recorremos todas las filas para mostrar el contenido de cada celda
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                String numeroNit;
                Integer idDetProcesoAdq;
                BigInteger capacidad;

                if (row.getRowNum() != 0) {
                    numeroNit = row.getCell(0).getStringCellValue();
                    capacidad = new BigInteger(String.valueOf((int) row.getCell(1).getNumericCellValue()));
                    if (row.getCell(5).getStringCellValue().toUpperCase().contains("UTILES")) {
                        idDetProcesoAdq = 58;
                    } else {
                        idDetProcesoAdq = 59;
                    }

                    System.out.println(numeroNit + "-" + capacidad + "-" + idDetProcesoAdq);

                    //port.updCapacidadByNitAndIdDet(numeroNit, idDetProcesoAdq, capacidad);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void test() throws FileNotFoundException, IOException {
        File fTmp = new File("C:\\Users\\MISanchez\\Documents\\eliminar.xls");
        InputStream input = new FileInputStream(fTmp);
        DecimalFormat df = new DecimalFormat("#0");
        /*String nip;
            String nombre;*/
        String correo = "";
        String valores = "";
        String titulos = "";

        Workbook wb = WorkbookFactory.create(input);
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (row.getRowNum() == 0) {
                for (int i = 1; i < row.getPhysicalNumberOfCells(); i++) {
                    titulos = titulos.isEmpty() ? row.getCell(i).getStringCellValue() : (titulos.concat(",").concat(row.getCell(i).getStringCellValue()));
                }

                System.out.println("titulo: " + titulos);
            } else {
                valores = "";

                if (row.getCell(0) != null) {
                    switch (row.getCell(0).getCellType()) {
                        case STRING:
                            correo = row.getCell(0).getStringCellValue();
                            break;
                    }
                }

                for (int i = 1; i <= titulos.split(",").length; i++) {
                    valores = valores.isEmpty() ? getValueOfCell(row.getCell(i)): (valores.concat(",").concat(getValueOfCell(row.getCell(i))));
                }
                String string = "";
                for (int i = 0; i < titulos.split(",").length; i++) {
                    string = string.isEmpty() ? titulos.split(",")[i].concat("::").concat(valores.split(",")[i])
                            : (string.concat("||").concat(titulos.split(",")[i].concat("::").concat(valores.split(",")[i])));
                }

                System.out.println("valores: " + string);
            }
        }
    }

    public static String getValueOfCell(Cell cell) {
        String valor;
        switch (cell.getCellType()) {
            case STRING:
                valor = cell.getStringCellValue();
                break;
            case NUMERIC:
                valor = String.valueOf(cell.getNumericCellValue());
                break;
            default:
                valor = "";
                break;
        }
        return valor;
    }
}
