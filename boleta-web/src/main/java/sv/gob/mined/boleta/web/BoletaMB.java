/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import sv.gob.mined.boleta.dto.DatosDto;
import sv.gob.mined.boleta.ejb.PersistenciaFacade;
import sv.gob.mined.boleta.model.CodigoGenerado;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@ManagedBean
@SessionScoped
public class BoletaMB implements Serializable {

    private Boolean showUploadBoletas = true;
    private Boolean deshabilitar = true;
    private String usuario;
    private String clave;
    private String codDepa;
    private String mes;
    private String anho;
    private String mesAnho;
    private String nombreArchivo;
    private Date fecha;
    private Boolean aceptar = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM_yyyy");

    private CodigoGenerado codigoGenerado = new CodigoGenerado();
    private List<DatosDto> lstPendientes = new ArrayList();
    private List<DatosDto> lstArchivosOriginales = new ArrayList();

    private UploadedFile file;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("parametros");

    @EJB
    private PersistenciaFacade persistenciaFacade;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getExternalContext().getSessionMap().containsKey("usuario")) {
            try {
                usuario = context.getExternalContext().getSessionMap().get("usuario").toString();
                clave = context.getExternalContext().getSessionMap().get("clave").toString();
                codDepa = usuario.substring(7, 9);
                
                mes = sdf.format(new Date()).split("_")[0];
                anho = sdf.format(new Date()).split("_")[1];
                
                System.out.println(mes + " - " + anho);
                
                cargarArchivos();
            } catch (IOException ex) {
                Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                JsfUtil.limpiarVariableSession();
                context.getExternalContext().getSessionMap().clear();
                ExternalContext externalContext = context.getExternalContext();
                externalContext.redirect(((ServletContext) externalContext.getContext()).getContextPath() + "/index.mined");
                System.gc();
            } catch (IOException ex) {
                Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, "Error haciendo logout", ex);
            }
        }
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    public Boolean getShowUploadBoletas() {
        return showUploadBoletas;
    }

    public void setShowUploadBoletas(Boolean showUploadBoletas) {
        this.showUploadBoletas = showUploadBoletas;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public CodigoGenerado getCodigoGenerado() {
        return codigoGenerado;
    }

    public void setCodigoGenerado(CodigoGenerado codigoGenerado) {
        this.codigoGenerado = codigoGenerado;
    }

    public void cargarArchivos() throws IOException {
        lstArchivosOriginales.clear();
        lstPendientes.clear();
        mesAnho = mes.concat("_").concat(anho);

        codigoGenerado = persistenciaFacade.getCodigoGenerado(codDepa, mesAnho);
        showUploadBoletas = codigoGenerado.getIdCodigo() != null;

        File carpetaPendientes = new File(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + codDepa + File.separator + mesAnho);
        cargarDatosDeArchivos(lstPendientes, carpetaPendientes);

        File carpetaProcesados = new File(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + codDepa + File.separator + mesAnho + File.separator + "archivo_original" + File.separator);
        cargarDatosDeArchivos(lstArchivosOriginales, carpetaProcesados);

        if (!lstPendientes.isEmpty()) {
            JsfUtil.mensajeInformacion("Existen archivos pendientes de envío, estos estan en cola de espera para ser procesados.");
        }
    }

    private void cargarDatosDeArchivos(List<DatosDto> lst, File carpeta) throws IOException {
        if (carpeta.exists()) {
            for (File listFile : carpeta.listFiles()) {
                if (listFile.isFile()) {
                    DatosDto dato = new DatosDto();

                    Path pathFile = listFile.toPath();
                    FileTime fTime = (FileTime) Files.getAttribute(pathFile, "creationTime");
                    PDDocument pdc = PDDocument.load(listFile);

                    dato.setFechaCreado(new Date(fTime.toMillis()));
                    dato.setNombreArchivo(listFile.getName());
                    dato.setNumeroPaginas(pdc.getNumberOfPages());

                    lst.add(dato);

                    pdc.close();
                }
            }
        } else {
            carpeta.mkdir();
        }

        deshabilitar = false;
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(FileUploadEvent file) throws FileNotFoundException, IOException {
        if (mesAnho != "") {
            this.file = file.getFile();

            File carpeta = new File(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + codDepa + File.separator + mesAnho + File.separator);
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

            Path folder = Paths.get(RESOURCE_BUNDLE.getString("path_archivo") + File.separator + codDepa + File.separator + mesAnho + File.separator + file.getFile().getFileName());
            Path arc = Files.createFile(folder);

            try (InputStream input = file.getFile().getInputstream()) {
                Files.copy(input, arc, StandardCopyOption.REPLACE_EXISTING);
            }
            
            cargarArchivos();
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar un mes y un año de PAGO");
        }
    }

    public List<DatosDto> getLstPendientes() {
        return lstPendientes;
    }

    public List<DatosDto> getLstArchivoOriginales() {
        return lstArchivosOriginales;
    }

    public Integer getCantidadDeBoletasEnviadas() {
        return persistenciaFacade.getCantidadDeBoletasEnviadas(codDepa);
    }
}
