package sv.gob.mined.boleta.ejb;

import com.sun.mail.handlers.message_rfc822;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Session;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

@Stateless
@LocalBean
public class LeerBoletasEJB {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("parametros");

    @EJB
    private EMailEJB eMailEJB;

    public void leerArchivosPendientes(Session mailSession, String codDepa, String usuario) {
        List<String> pathArchivosProcesados = new ArrayList();
        Properties info = chargeEmailsProperties("emails0212");

        File carpetaRoot = new File(RESOURCE_BUNDLE.getString("path_archivo"));
        for (File carpetaDepa : carpetaRoot.listFiles()) {
            if (carpetaDepa.isDirectory()) {
                for (File carpetaPorFecha : carpetaDepa.listFiles()) {
                    if (carpetaPorFecha.isDirectory()) {
                        for (File archivoBoleta : carpetaPorFecha.listFiles()) {
                            if (archivoBoleta.isFile() && (archivoBoleta.getName().toUpperCase().contains("PDF"))) {
                                splitPages(archivoBoleta, codDepa, mailSession, info, usuario);
                                
                                pathArchivosProcesados.add(archivoBoleta.getAbsolutePath() + "::" + archivoBoleta.getName());
                                
                                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, archivoBoleta.getName());
                            }
                        }
                    }
                }
            }
        }
    }

    public void leerArchivosPendientesByUltimoProcesado(Session mailSession, String codDepa, String usuario, String codigo, String mesAnho) {
        List<String> pathArchivosProcesados = new ArrayList();
        Properties info = chargeEmailsProperties("emails0212");

        File carpetaRoot = new File(RESOURCE_BUNDLE.getString("path_archivo"));
        for (File carpetaDepa : carpetaRoot.listFiles()) {
            if (carpetaDepa.isDirectory() && carpetaDepa.getName().contains(codDepa)) {
                for (File carpetaPorFecha : carpetaDepa.listFiles()) {
                    if (carpetaPorFecha.isDirectory()) {
                        for (File archivoBoleta : carpetaPorFecha.listFiles()) {
                            if (archivoBoleta.isFile() && (archivoBoleta.getName().toUpperCase().contains("PDF"))) {
                                splitPagesByCodigo(archivoBoleta, codDepa, mailSession, info, usuario, codigo, mesAnho, RESOURCE_BUNDLE.getString("path_archivo"));
                                
                                pathArchivosProcesados.add(archivoBoleta.getAbsolutePath() + "::" + archivoBoleta.getName());

                                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, archivoBoleta.getName());
                            }
                        }
                    }
                }
            }
        }

        for (String pathArchivo : pathArchivosProcesados) {
            try {
                File folderProcesado = new File(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + "12" + File.separator + mesAnho + File.separator + "procesado" + File.separator);
                if (!folderProcesado.exists()) {
                    folderProcesado.mkdir();
                }
                //mover archivo a carpeta de procesados
                Path temp = Files.copy(Paths.get(pathArchivo.split("::")[0]),
                        Paths.get(folderProcesado.getAbsolutePath() + File.separator + pathArchivo.split("::")[1]), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void continuarEnvio(Session mailSession, String codDepa, String usuario, String ultimoCodigoProcesado, String mesAnho) {
        leerArchivosPendientesByUltimoProcesado(mailSession, codDepa, usuario, ultimoCodigoProcesado, mesAnho);
    }

    public void splitPagesByCodigo(File file, String codDepa, Session mailSession, Properties info, String usuario, String ultimoCodigoProcesado, String mesAnho, String path) {
        StringBuilder sb = new StringBuilder("");
        PDDocument document = null;
        Boolean ultimo = false;
        Boolean continuar = false;
        int interacion = 0;
        int siguienteInteracion = 0;
        int contadorDeCortes = 0;

        int boletasEnviadas = 0;
        int docenteNoEncontrados = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        try {
            sb = sb.append("Se han enviado boletas de pago del archivo : ").append(file.getName()).append("<br/>")
                    .append("Hora de inicio: ").append(sdf.format(new Date())).append("<br/>");
            document = PDDocument.load(file);

            Splitter splitter = new Splitter();

            if (document.getNumberOfPages() > 1000) {
                siguienteInteracion = 1000;
                splitter.setStartPage(1);
                splitter.setEndPage(siguienteInteracion);
                contadorDeCortes = siguienteInteracion;

                interacion = ((int) (document.getNumberOfPages() / 1000)) + 1;
            }

            do {
                interacion--;
                for (PDDocument pd : splitter.split(document)) {
                    String code = getCodigoDocente(pd, "         )", 0, 15).substring(8);

                    if (!ultimo) {
                        ultimo = code.equals(ultimoCodigoProcesado);
                    }

                    if (ultimo && continuar) {
                        if (info.containsKey(code)) {
                            String email = info.getProperty(code);
                            //Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, email);
                            //eMailEJB.enviarMail(code, email, usuario, RESOURCE_BUNDLE.getString("mail.message"), pd, mailSession);
                            boletasEnviadas++;
                        } else {
                            eMailEJB.escribirEmpleadoNoEncontrado(codDepa, mesAnho, path, code);
                            docenteNoEncontrados++;
                            //Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.WARNING, "No existe este empleado: {0}", code);
                        }
                    }

                    if (ultimo && !continuar) {
                        continuar = true;
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

            sb = sb.append("Hora de fin: ").append(sdf.format(new Date())).append("<br/>");
            sb = sb.append("Número de boletas enviadas: ").append(boletasEnviadas).append("<br/>");
            sb = sb.append("Número de docente no encontrados: ").append(docenteNoEncontrados).append("<br/>");

            eMailEJB.enviarMailDeConfirmacion("Envio de boletas de pago", sb.toString(), usuario, mailSession);
        } catch (IOException ex) {

            sb = new StringBuilder("");
            sb = sb.append("Ha ocurrido un error en el envio de las boletas de pago del archivo : ").append(file.getName()).append("<br/>")
                    .append("Se ha enviado una notificación del error al administrador.");

            eMailEJB.enviarMailDeError("Error en envio de boletas de pago", sb.toString(), usuario, mailSession);
            try {
                if (document != null) {
                    document.close();
                }

            } catch (IOException ex1) {
                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex1);
            }

            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void splitPages(File file, String codDepa, Session mailSession, Properties info, String usuario) {
        StringBuilder sb = new StringBuilder("");
        PDDocument document = null;
        Boolean ultimo = false;
        Boolean continuar = false;
        int interacion = 0;
        int siguienteInteracion = 0;
        int contadorDeCortes = 0;

        int boletasEnviadas = 0;
        int docenteNoEncontrados = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        
        
        try {
            document = PDDocument.load(file);

            Splitter splitter = new Splitter();

            for (PDDocument pd : splitter.split(document)) {
                String code = getCodigoDocente(pd, "         )", 0, 15).substring(8);
                if (info.containsKey(code)) {
                    String email = info.getProperty(code);

                    eMailEJB.enviarMail(code, email, usuario, RESOURCE_BUNDLE.getString("mail.message"), pd, mailSession);
                }
            }

            document.close();
        } catch (IOException ex) {
            try {
                if (document != null) {
                    document.close();
                }

            } catch (IOException ex1) {
                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex1);
            }

            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Properties chargeEmailsProperties(String nombre) {
        Properties info = null;
        try {
            info = new Properties();
            try (InputStream input = LeerBoletasEJB.class.getClassLoader().getResourceAsStream(nombre + ".properties")) {
                info.load(input);
            }

        } catch (IOException ex) {
            Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;
    }

    public static String getCodigoDocente(PDDocument pDDocument, String strEndIdentifier, int offSet, int back) {
        String returnString;
        try {
            PDFTextStripper tStripper = new PDFTextStripper();
            tStripper.setStartPage(1);
            tStripper.setEndPage(1);
            String pdfFileInText = tStripper.getText(pDDocument);
            String strEnd = strEndIdentifier;
            int endInddex = pdfFileInText.indexOf(strEnd) + offSet;
            int startInddex = endInddex - back;
            returnString = pdfFileInText.substring(startInddex, endInddex); // + strEnd;
        } catch (IOException ex) {
            returnString = "No ParaGraph Found";
        }
        return returnString;
    }

}
