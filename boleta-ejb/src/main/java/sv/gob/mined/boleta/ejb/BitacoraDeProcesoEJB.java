/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author DesarrolloPc
 */
@Stateless
@LocalBean
public class BitacoraDeProcesoEJB {

    /*@Asynchronous
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
    }*/

    /*@Asynchronous
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
    }*/
}
