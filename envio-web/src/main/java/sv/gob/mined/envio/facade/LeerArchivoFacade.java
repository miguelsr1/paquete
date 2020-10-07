/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.envio.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jboss.ejb3.annotation.TransactionTimeout;
import sv.gob.mined.envio.model.DetalleEnvio;
import sv.gob.mined.envio.model.EnvioMasivo;
import sv.gob.mined.envio.web.EnvioView;

/**
 *
 * @author MISanchez
 */
@Stateless
public class LeerArchivoFacade {

    @Inject
    private PersistenciaFacade preFacade;

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @TransactionTimeout(unit = TimeUnit.MINUTES, value = 120)
    public BigDecimal guardarRegistros(String pathArchivo, String remitente, String titulo, String mensaje) {
        InputStream input = null;
        BigDecimal idEnvio = BigDecimal.ZERO;
        EnvioMasivo eMasivo = new EnvioMasivo();

        try {
            File fTmp = new File(pathArchivo);
            input = new FileInputStream(fTmp);
            DecimalFormat df = new DecimalFormat("#0");
            String nip;
            String nombre;
            String correo;
            
            Workbook wb = WorkbookFactory.create(input);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            eMasivo.setArchivo(pathArchivo);
            eMasivo.setCorreRemitente(remitente);
            eMasivo.setFechaEnvio(new Date());
            eMasivo.setMensaje(mensaje);
            eMasivo.setTitulo(titulo);

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (row.getRowNum() != 0) {
                    if (row.getCell(0) != null) {
                        switch (row.getCell(0).getCellType()) {
                            case STRING:
                                nip = row.getCell(0).getStringCellValue();
                                break;
                            case NUMERIC:
                                nip = String.valueOf(df.format(row.getCell(0).getNumericCellValue()));
                                break;
                            default:
                                nip = "";
                                break;
                        }
                    } else {
                        nip = "";
                    }

                    if (row.getCell(1) != null && row.getCell(2) != null) {
                        nombre = row.getCell(1).getStringCellValue();
                        correo = row.getCell(2).getStringCellValue();

                        DetalleEnvio de = new DetalleEnvio();
                        de.setCorreoDestinatario(correo);
                        de.setIdEnvio(eMasivo);
                        de.setNip(nip);
                        de.setNombreDestinatario(nombre);
                        de.setEnviado((short) 0);

                        eMasivo.getDetalleEnvioList().add(de);
                    }
                }
            }

            idEnvio = preFacade.guardarEnvio(eMasivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(EnvioView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
            }
        }

        return idEnvio;
    }
}
