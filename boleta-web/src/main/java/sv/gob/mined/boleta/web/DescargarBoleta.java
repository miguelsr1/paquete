/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.gob.mined.boleta.ejb.ObtenerArchivoFacade;

/**
 *
 * @author misanchez
 */
public class DescargarBoleta extends HttpServlet {

    private final int ARBITARY_SIZE = 1048;
    @EJB
    private ObtenerArchivoFacade obtenerArchivoFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get image file.
        String codigoGenerado = request.getParameter("codigoGenerado");

        try {
            File boleta = obtenerArchivoFacade.obtenerArchivo(codigoGenerado);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename="+codigoGenerado+".pdf");
            

            try (InputStream in = new BufferedInputStream(new FileInputStream(boleta));
                    OutputStream out = response.getOutputStream()) {

                byte[] buffer = new byte[ARBITARY_SIZE];

                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DescargarBoleta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
