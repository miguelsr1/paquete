/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.ejb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author DesarrolloPc
 */
@Stateless
@LocalBean
public class BitacoraDeProcesoEJB {

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void escribirEmpleadoNoEncontrado(String codDepa, String mesAnho, String path, String codigoEmpleado) {
        try {
            File file = new File(path + File.separator + codDepa + File.separator + mesAnho + File.separator + "no_encontrado.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            Files.write(Paths.get(file.getAbsolutePath()), codigoEmpleado.concat("\n").getBytes(), StandardOpenOption.APPEND);

        } catch (IOException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void correoNoEnviadoPorErrorGenerado(String codDepa, String mesAnho, String path, String codigoEmpleado) {
        try {
            File carpetaError = new File(path + File.separator + codDepa + File.separator + mesAnho + File.separator + "errores");
            if (!carpetaError.exists()) {
                carpetaError.mkdir();
            }

            File file = new File(path + File.separator + codDepa + File.separator + mesAnho + File.separator + "errores" + File.separator + "correo_no_enviado.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            Files.write(Paths.get(file.getAbsolutePath()), codigoEmpleado.concat("\n").getBytes(), StandardOpenOption.APPEND);

        } catch (IOException ex) {
            Logger.getLogger(EMailEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
