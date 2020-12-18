/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.envio.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jboss.ejb3.annotation.TransactionTimeout;
import sv.gob.mined.envio.model.Destinatarios;
import sv.gob.mined.envio.model.DetalleEnvio;
import sv.gob.mined.envio.model.EnvioMasivo;
import sv.gob.mined.envio.web.EnvioView;

/**
 *
 * @author MISanchez
 */
@Stateless
public class LeerArchivoFacade {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("parametros");

    @Inject
    private PersistenciaFacade preFacade;

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @TransactionTimeout(unit = TimeUnit.MINUTES, value = 120)
    public BigDecimal guardarRegistros(String pathArchivo, String remitente, String titulo, String mensaje) {
        InputStream input = null;
        BigDecimal idEnvio = BigDecimal.ZERO;
        EnvioMasivo eMasivo = new EnvioMasivo();
        List<byte[]> lstImagenes = new ArrayList();
        List<String> lstExtencionImagenes = new ArrayList();

        try {
            File fTmp = new File(pathArchivo);
            input = new FileInputStream(fTmp);

            String correo = "";
            String valores;
            String titulos = "";

            Workbook wb = WorkbookFactory.create(input);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            eMasivo.setArchivo(pathArchivo);
            eMasivo.setCorreRemitente(remitente);
            eMasivo.setFechaEnvio(new Date());

            //buscar imagenes dentro del mensaje para extraer el base64 para porterior almacenarlo como imagen en el servidor
            int count = 1;
            String regex = "<img src=\"data:image/(gif|jpe?g|png);base64,([^\"]*\")[^<>]*>";
            Matcher m = Pattern.compile(regex).matcher(mensaje);
            while (m.find()) {
                String imageFileType = m.group(1);
                String imageDataString = m.group(2);
                byte[] imageData = Base64.decodeBase64(imageDataString);

                lstImagenes.add(imageData);
                lstExtencionImagenes.add(imageFileType);

                mensaje = mensaje.replace(m.group(), "<img src=\"cid:imagen" + count + "\">");
                count++;

            }

            eMasivo.setMensaje(mensaje);
            eMasivo.setTitulo(titulo);

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (row.getRowNum() == 0) {
                    for (int i = 1; i < row.getPhysicalNumberOfCells(); i++) {
                        titulos = titulos.isEmpty() ? row.getCell(i).getStringCellValue() : (titulos.concat(",").concat(row.getCell(i).getStringCellValue()));
                    }
                } else {
                    if (row.getCell(0) != null) {
                        switch (row.getCell(0).getCellType()) {
                            case STRING:
                                correo = row.getCell(0).getStringCellValue();
                                break;
                        }

                        valores = "";
                        for (int i = 1; i <= titulos.split(",").length; i++) {
                            valores = valores.isEmpty() ? getValueOfCell(row.getCell(i)) : (valores.concat(",").concat(getValueOfCell(row.getCell(i))));
                        }

                        String valorFinal = "";
                        for (int i = 0; i < titulos.split(",").length; i++) {
                            valorFinal = valorFinal.isEmpty() ? titulos.split(",")[i].concat("::").concat(valores.split(",")[i])
                                    : (valorFinal.concat("&&").concat(titulos.split(",")[i].concat("::").concat(valores.split(",")[i])));
                        }

                        DetalleEnvio de = new DetalleEnvio();
                        de.setCorreoDestinatario(correo);
                        de.setIdEnvio(eMasivo);
                        de.setNip(valorFinal);
                        de.setEnviado((short) 0);

                        eMasivo.getDetalleEnvioList().add(de);
                    }
                }
            }
            idEnvio = preFacade.guardarEnvio(eMasivo);

            //almacenar imagenes del mensaje
            if (!lstImagenes.isEmpty()) {
                File folder = new File("/opt/soporte/envio_masivo/envio" + idEnvio.intValue() + "/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                count = 1;
                for (byte[] imgBytes : lstImagenes) {
                    FileUtils.writeByteArrayToFile(new File("/opt/soporte/envio_masivo/envio" + idEnvio.intValue() + "/imagen" + count + "." + lstExtencionImagenes.get(count - 1)), imgBytes);
                    count++;
                }
            }

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

    private String getValueOfCell(Cell cell) {
        String valor;
        switch (cell.getCellType()) {
            case STRING:
                valor = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (cell.getNumericCellValue() % 1 == 0) {
                    valor = String.format("%d", (long) cell.getNumericCellValue());
                } else {
                    valor = String.format("%.2f", cell.getNumericCellValue());
                }
                break;
            default:
                valor = "";
                break;
        }
        return valor;
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @TransactionTimeout(unit = TimeUnit.MINUTES, value = 120)
    public void splitPages(String codDepa) {
        PDDocument document = null;

        int interacion;
        int indexPosicion;
        int contadorDeCortes;
        int siguienteInteracion = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");

        String nie = "";
        String cadenaDeBusqueda;
        String pathArchivo;

        //List<Destinatarios> lstCorreo = preFacade.getLstDestinatarioByCodigoDepartamento(codDepa);
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            pathArchivo = RESOURCE_BUNDLE.getString("path_archivo_windows");
        } else {
            pathArchivo = RESOURCE_BUNDLE.getString("path_archivo_linux");
        }

        try {
            document = PDDocument.load(new File(pathArchivo + File.separator + "notas" + File.separator + codDepa.concat(".pdf")));
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

            cadenaDeBusqueda = "NIE:";
            indexPosicion = 15;

            do {
                interacion--;

                for (PDDocument pd : splitter.split(document)) {
                    String codTemp = "";

                    try {
                        codTemp = getNieEstudiante(pd, cadenaDeBusqueda, nie);

                        //obtener codigo del empleado de la boleta
                        String codigo = codTemp.trim();

                        /*File carpetaCodigo = new File(pathArchivo + File.separator + "notas" + File.separator + codigo);
                        if (!carpetaCodigo.exists()) {
                            carpetaCodigo.mkdir();
                        }*/

                        //crear archivo pdf
                        pd.save(pathArchivo + File.separator + "notas" + File.separator + codigo + ".pdf");

                    } catch (StringIndexOutOfBoundsException e) {
                        Logger.getLogger(LeerArchivoFacade.class.getName()).log(Level.SEVERE, "DEPA {0} - Error obteniendo el nip del docente{1}", new Object[]{codDepa, codTemp});
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
                Logger.getLogger(LeerArchivoFacade.class.getName()).log(Level.SEVERE, "DEPA : {0} Error en el archivo:", new Object[]{codDepa});
                Logger.getLogger(LeerArchivoFacade.class.getName()).log(Level.SEVERE, "Muy probablemente, este archivo no es de boletas de pagos");
                Logger.getLogger(LeerArchivoFacade.class.getName()).log(Level.SEVERE, "========== ERROR ==========", ex);
            }
        }
    }

    private String getNieEstudiante(PDDocument pDDocument, String strEndIdentifier, String nipOld) throws IOException {
        String returnString;
        PDFTextStripper tStripper = new PDFTextStripper();
        tStripper.setStartPage(1);
        tStripper.setEndPage(1);
        String pdfFileInText = tStripper.getText(pDDocument);

        String strEnd = strEndIdentifier;
        int endInddex = pdfFileInText.indexOf(strEnd) + 4;
        if (endInddex != -1) {
            int posEnd = pdfFileInText.indexOf("Centro Educativo"); //503
            returnString = pdfFileInText.substring(endInddex, posEnd);
            System.out.println("NIE: " + returnString);
            if (returnString.contains("_______")) {
                returnString = "";
            }
        } else {
            returnString = nipOld;
        }

        return returnString;
    }
}
