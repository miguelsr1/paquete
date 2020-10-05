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
    public static void main(String[] args) throws IOException {
        leerXls();
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
}
