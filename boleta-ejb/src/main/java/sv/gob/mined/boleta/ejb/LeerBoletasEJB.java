package sv.gob.mined.boleta.ejb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        Properties info = chargeEmailsProperties("emails0212");

        File carpetaRoot = new File(RESOURCE_BUNDLE.getString("path_archivo"));
        for (File carpetaDepa : carpetaRoot.listFiles()) {
            if (carpetaDepa.isDirectory()) {
                for (File carpetaPorFecha : carpetaDepa.listFiles()) {
                    if (carpetaPorFecha.isDirectory()) {
                        for (File archivoBoleta : carpetaPorFecha.listFiles()) {
                            if (archivoBoleta.isFile()) {
                                splitPages(archivoBoleta, codDepa, mailSession, info, usuario);
                                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, archivoBoleta.getName());
                            }
                        }
                    }
                }
            }
        }
    } //

    public void leerArchivosPendientesByUltimoProcesado(Session mailSession, String codDepa, String usuario, String codigo) {
        Properties info = chargeEmailsProperties("emails0212");

        File carpetaRoot = new File(RESOURCE_BUNDLE.getString("path_archivo"));
        for (File carpetaDepa : carpetaRoot.listFiles()) {
            if (carpetaDepa.isDirectory()) {
                for (File carpetaPorFecha : carpetaDepa.listFiles()) {
                    if (carpetaPorFecha.isDirectory()) {
                        for (File archivoBoleta : carpetaPorFecha.listFiles()) {
                            if (archivoBoleta.isFile()) {
                                splitPagesByCodigo(archivoBoleta, codDepa, mailSession, info, usuario, codigo);
                                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, archivoBoleta.getName());
                            }
                        }
                    }
                }
            }
        }
    }

    public void continuarEnvio(Session mailSession, String codDepa, String usuario, String ultimoCodigoProcesado) {
        leerArchivosPendientesByUltimoProcesado(mailSession, codDepa, usuario, ultimoCodigoProcesado);
    }

    public void splitPagesByCodigo(File file, String codDepa, Session mailSession, Properties info, String usuario, String ultimoCodigoProcesado) {
        PDDocument document = null;
        Boolean ultimo = false;
        Boolean continuar = false;
        int numPage = 0;
        int interacion = 0;
        try {
            document = PDDocument.load(file);

            numPage = document.getNumberOfPages();

            Splitter splitter = new Splitter();
            
            if (document.getNumberOfPages() > 1000) {
                splitter.setSplitAtPage(1000); //<----- separar el documento cada 1000 paginas
            }

            for (PDDocument pd : splitter.split(document)) {
                String code = getCodigoDocente(pd, "         )", 0, 15).substring(8);

                ultimo = code.equals(ultimoCodigoProcesado);

                if (ultimo && continuar) {
                    if (info.containsKey(code)) {
                        String email = info.getProperty(code);
                        Logger.getGlobal().log(Level.INFO, email);
                        //eMailEJB.enviarMail(code, email, usuario, RESOURCE_BUNDLE.getString("mail.message"), pd, mailSession);
                    }
                }

                if (ultimo && !continuar) {
                    continuar = true;
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

    public void splitPages(File file, String codDepa, Session mailSession, Properties info, String usuario) {
        PDDocument document = null;
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
