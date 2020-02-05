/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.ejb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author DesarrolloPc
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class SeparacionBoletasFacade extends Exception {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("parametros");

    @Asynchronous
    public void separacion(String mesAnho, String codDepa) {
        File carpetaRoot = new File(RESOURCE_BUNDLE.getString("path_archivo"));
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");

        for (File carpetaDepa : carpetaRoot.listFiles()) {
            if (carpetaDepa.isDirectory() && carpetaDepa.getName().equals(codDepa)) {
                Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.INFO, "SEPARACION - Hora de inicio + {0}", new Date());
                for (File carpetaPorFecha : carpetaDepa.listFiles()) {
                    if (carpetaPorFecha.isDirectory() && carpetaPorFecha.getName().equals(mesAnho)) {
                        try {
                            for (File archivoBoleta : carpetaPorFecha.listFiles()) {
                                if (archivoBoleta.isFile() && (archivoBoleta.getName().toUpperCase().contains("PDF"))) {

                                    //consolidar boletas por docente
                                    splitPages(archivoBoleta, codDepa, mesAnho, RESOURCE_BUNDLE.getString("path_archivo"), sdf);

                                    File folderArchivoOriginal = new File(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + codDepa + File.separator + mesAnho + File.separator + "archivo_original" + File.separator);
                                    if (!folderArchivoOriginal.exists()) {
                                        folderArchivoOriginal.mkdir();
                                    }
                                    Path temp = Files.move(Paths.get(archivoBoleta.getAbsolutePath()),
                                            Paths.get(folderArchivoOriginal.getAbsolutePath() + File.separator + archivoBoleta.getName()), StandardCopyOption.REPLACE_EXISTING);
                                }
                            }
                            Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.INFO, "Unificación de boletas - Hora de fin + {0}", new Date());
                            //unificar boletas en un solo archivo por docente
                            unirBoletasUnSolaArchivo(carpetaPorFecha);
                        } catch (IOException ex) {
                            Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.SEVERE, "Mensaje: {0}", ex.getMessage());
                            Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.SEVERE, "Causa: {0}", ex.getCause());
                            Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.SEVERE, "Clase: {0}", ex.getClass().getName());
                        }
                    }
                }
                Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.INFO, "SEPARACION - Hora de fin + {0}", new Date());
            }
        }
    }

    @Asynchronous
    public void separacionTotal(String mesAnho) {
        File carpetaRoot = new File(RESOURCE_BUNDLE.getString("path_archivo"));
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmsszzz");

        for (File carpetaDepa : carpetaRoot.listFiles()) {
            Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.INFO, "SEPARACION TOTAL " + carpetaDepa.getName() + " - Hora de inicio + {0}", new Date());
            if (carpetaDepa.isDirectory()) {
                for (File carpetaPorFecha : carpetaDepa.listFiles()) {
                    if (carpetaPorFecha.isDirectory() && carpetaPorFecha.getName().equals(mesAnho)) {
                        try {
                            for (File archivoBoleta : carpetaPorFecha.listFiles()) {
                                if (archivoBoleta.isFile() && (archivoBoleta.getName().toUpperCase().contains("PDF"))) {
                                    //consolidar boletas por docente
                                    splitPages(archivoBoleta, carpetaDepa.getName(), mesAnho, RESOURCE_BUNDLE.getString("path_archivo"), sdf);
                                }
                            }
                            Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.INFO, "Unificación de boletas - Hora de fin + {0}", new Date());
                            //unificar boletas en un solo archivo por docente
                            unirBoletasUnSolaArchivo(carpetaPorFecha);
                        } catch (IOException ex) {
                            Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.SEVERE, "Mensaje: {0}", ex.getMessage());
                            Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.SEVERE, "Causa: {0}", ex.getCause());
                            Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.SEVERE, "Clase: {0}", ex.getClass().getName());
                        }
                    }
                }
            }
            Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.INFO, "SEPARACION TOTAL " + carpetaDepa.getName() + " - Hora de fin + {0}", new Date());
        }
    }

    private void unirBoletasUnSolaArchivo(File carpetaPorFecha) throws FileNotFoundException, IOException {
        File[] lstPDf = carpetaPorFecha.listFiles();
        Arrays.sort(lstPDf) ;
        for (File carpetaDocente : lstPDf) {
            if (carpetaDocente.isDirectory() && !carpetaDocente.getName().equals("procesado") && !carpetaDocente.getName().equals("archivo_original")) {
                PDFMergerUtility PDFmerger = new PDFMergerUtility();
                PDFmerger.setDestinationFileName(carpetaPorFecha.getPath() + File.separator + carpetaDocente.getName() + ".pdf");

                for (File boleta : carpetaDocente.listFiles()) {
                    PDFmerger.addSource(boleta);
                }
                PDFmerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

                for (File boleta : carpetaDocente.listFiles()) {
                    boleta.delete();
                }
                carpetaDocente.delete();
            }
        }

    }

    private void splitPages(File file, String codDepa, String mesAnho, String path, SimpleDateFormat sdf) {
        PDDocument document = null;
        int interacion;
        int contadorDeCortes;
        int siguienteInteracion = 0;

        try {
            document = PDDocument.load(file);

            Splitter splitter = new Splitter();
            splitter.setStartPage(1);

            if (document.getNumberOfPages() > 1000) {
                siguienteInteracion = 1000;
                splitter.setEndPage(siguienteInteracion);

                interacion = ((int) (document.getNumberOfPages() / 1000)) + 1;
            } else {
                splitter.setEndPage(document.getNumberOfPages());
                interacion = 1;
            }

            do {
                interacion--;
                for (PDDocument pd : splitter.split(document)) {
                    String codTemp = "";
                    try {
                        codTemp = getCodigoDocente(pd, "         )", 0, 15, file.getName());
                        //obtener codigo del empleado de la boleta
                        String codigo = codTemp.substring(8);

                        if (!codigo.isEmpty()) {
                            //crear archivo con el codigo
                            File carpetaCodigo = new File(path + File.separator + codDepa + File.separator + mesAnho + File.separator + codigo);
                            if (!carpetaCodigo.exists()) {
                                carpetaCodigo.mkdir();
                            }

                            //crear archivo pdf
                            pd.save(path + File.separator + codDepa + File.separator + mesAnho + File.separator + codigo + File.separator + sdf.format(new Date()) + ".pdf");
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.SEVERE, "DEPA {0} - Error obteniendo el nip del docente{1}", new Object[]{codDepa, codTemp});
                    }

                    pd.close();
                }
                if (interacion > 0) {
                    contadorDeCortes = siguienteInteracion + 1;
                    siguienteInteracion = siguienteInteracion + 1000;
                    splitter = new Splitter();
                    splitter.setStartPage(contadorDeCortes);
                    if (document.getNumberOfPages() > siguienteInteracion) {
                        splitter.setEndPage(siguienteInteracion);
                    } else {
                        splitter.setEndPage(document.getNumberOfPages());
                    }
                }
            } while (interacion != 0);

            document.close();

        } catch (IOException ex) {
            try {
                if (document != null) {
                    document.close();
                }
            } catch (IOException ex1) {
                Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.SEVERE, "DEPA : {0} Error en el archivo: {1}", new Object[]{codDepa, file.getName()});
                Logger.getLogger(SeparacionBoletasFacade.class.getName()).log(Level.SEVERE, "Muy probablemente, este archivo no es de boletas de pagos");
            }

            ex.printStackTrace();
        }
    }

    private String getCodigoDocente(PDDocument pDDocument, String strEndIdentifier, int offSet, int back, String nombreArchivo) throws IOException {
        String returnString = "";
        PDFTextStripper tStripper = new PDFTextStripper();
        tStripper.setStartPage(1);
        tStripper.setEndPage(1);
        String pdfFileInText = tStripper.getText(pDDocument);
        String strEnd = strEndIdentifier;
        int endInddex = pdfFileInText.indexOf(strEnd) + offSet;
        int startInddex = endInddex - back;
        returnString = pdfFileInText.substring(startInddex, endInddex);

        return returnString;
    }
}
