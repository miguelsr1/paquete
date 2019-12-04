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

    public void leerArchivosPendientes(Session mailSession, String codDepa) {
        Properties info = chargeEmailsProperties("emails0212");

        File carpetaRoot = new File(RESOURCE_BUNDLE.getString("path_archivo"));
        for (File carpetaDepa : carpetaRoot.listFiles()) {
            if (carpetaDepa.isDirectory()) {
                for (File carpetaPorFecha : carpetaDepa.listFiles()) {
                    if (carpetaPorFecha.isDirectory()) {
                        for (File archivoBoleta : carpetaPorFecha.listFiles()) {
                            if (archivoBoleta.isFile()) {
                                splitPages(archivoBoleta, codDepa, mailSession, info);
                                Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, archivoBoleta.getName());
                            }
                        }
                    }
                }
            }
        }
    }

    public void splitPages(File file, String codDepa, Session mailSession, Properties info) {
        PDDocument document = null;
        try {
            //try (InputStream is = new FileInputStream(file)) {
            document = PDDocument.load(file);

            Splitter splitter = new Splitter();

            List<PDDocument> lstPages = splitter.split(document);

            int i = 1;
            for (PDDocument pd : splitter.split(document)) {
                String code = getCodigoDocente(pd, "         )", 0, 15).substring(8);
                if (info.containsKey(code)) {
                    String email = info.getProperty(code);

                    //enviarMail(code, email, pd);
                    //Logger.getLogger(LeerBoletasEJB.class.getName()).log(Level.INFO, "Envio numero: {0} - codigo departamento {1} - codigo docente {2}", new Object[]{i, codDepa, code});
                    
                    //i++;
                }
            }


            //is.close();
            document.close();
            // }
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
