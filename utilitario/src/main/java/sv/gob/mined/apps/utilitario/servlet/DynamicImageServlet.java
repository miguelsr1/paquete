/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.apps.utilitario.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author misanchez
 */
public class DynamicImageServlet extends HttpServlet {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("Bundle");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cargarImagen(response, request.getParameter("file"));
    }

    private void cargarImagen(HttpServletResponse response, String file) {
        try {
            byte[] bytes;
            // Get image contents.
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(RESOURCE_BUNDLE.getString("path_images") + File.separator + file))) {
                // Get image contents.
                bytes = new byte[in.available()];
                in.read(bytes);
            }

            // Write image contents to response.
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            cargarImagen(response, "sin_foto.png");
        }
    }
}
