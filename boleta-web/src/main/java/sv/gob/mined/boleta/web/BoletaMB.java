/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.primefaces.model.UploadedFile;
import sv.gob.mined.boleta.Dto.DatosDto;

/**
 *
 * @author misanchez
 */
@ManagedBean
@SessionScoped
public class BoletaMB implements Serializable {

    private List<DatosDto> lstDatos = new ArrayList();

    private UploadedFile file;
    private PDDocument document;
    private List<PDDocument> lstPages;
    private Iterator<PDDocument> iterator;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<DatosDto> getLstDatos() {
        return lstDatos;
    }

    public void upload() {
        if (file != null) {
            splitPages();
        }
    }

    public void splitPages() {
        List<PDDocument> Pages2;
        try {
            document = PDDocument.load(file.getInputstream(), MemoryUsageSetting.setupTempFileOnly());

            //Instantiating Splitter class
            Splitter splitter = new Splitter();

            if (document.getNumberOfPages() > 1000) {
                splitter.setSplitAtPage(1000); //<----- separar el documento cada 1000 paginas
            }

            //splitting the pages of a PDF document
            lstPages = splitter.split(document);

            //Creating an iterator 
            iterator = lstPages.listIterator();

            /*jpb_progreso.setValue(0);
                    jpb_progreso.setMinimum(1);
                    jpb_progreso.setStringPainted(true);*/
            final Properties info = chargeEmailsProperties2();

            while (iterator.hasNext()) {
                DatosDto rowData = new DatosDto();

                PDDocument pd = iterator.next();

                if (pd.getNumberOfPages() > 1) {
                    //System.out.println("Entro a sub bloque");
                    try {
                        Splitter splitter2 = new Splitter();
                        Pages2 = splitter2.split(pd);
                        Iterator<PDDocument> iterator2 = Pages2.listIterator();
                        while (iterator2.hasNext()) {
                            PDDocument pd2 = iterator2.next();
                            String code = getCode2(pd2, "         )", 0, 15).substring(8);
                            if (info.containsKey(code)) {
                                String email = info.getProperty(code);
                                rowData.setCodigo(code);
                                rowData.setCorreoElectronico(email);

                                lstDatos.add(rowData);
                            } else {
                            }

                            pd2.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Utilizado con las 4763 boletas reales de La Libertad
                    String code = getCode2(pd, "         )", 0, 15).substring(8);

                    if (info.containsKey(code)) {
                        String email = info.getProperty(code);
                        rowData.setCodigo(code);
                        rowData.setCorreoElectronico(email);
                        lstDatos.add(rowData);

                        //Para enviar correo
                        //sendEmail.sendEmailWithAttachment(pd, email, code); //<---- descomentar
                    } else {
                        //System.out.println("Codigo obtenido del PDF con error " + code); 
                    }
                }
                pd.close();
            }

            document.close();
        } catch (IOException ex) {
            try {
                document.close();
            } catch (IOException ex1) {
                Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, null, ex1);
            }

            Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Properties chargeEmailsProperties2() {
        Properties info = null;
        try {
            info = new Properties();
            InputStream input = BoletaMB.class.getClassLoader().getResourceAsStream("emails.properties");
            //fin            
            info.load(input);
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;
    }

    public static String getCode2(PDDocument pDDocument, String strEndIdentifier, int offSet, int back) {
        String returnString = "";
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
            ex.printStackTrace();
        }
        return returnString;
    }
}
