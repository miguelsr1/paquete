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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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
        actualizarCorreoProve();
    }

    public static void leerXls() throws FileNotFoundException, IOException {

        enviarDatosProveedor();
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
}
