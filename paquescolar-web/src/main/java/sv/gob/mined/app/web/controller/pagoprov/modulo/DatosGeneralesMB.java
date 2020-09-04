/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov.modulo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.DatosGeograficosEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.Anho;
import sv.gob.mined.paquescolar.model.CapaDistribucionAcre;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.Departamento;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.Municipio;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;

/**
 *
 * @author MISanchez
 */
@ManagedBean
@ViewScoped
public class DatosGeneralesMB implements Serializable {

    private String tapEmpresa;
    private String tapPersona;
    private String fileName = "fotoProveedores/profile.png";
    private String codigoDepartamentoCalificado;
    private String codigoDepartamento = "";

    private BigDecimal idMunicipio = BigDecimal.ZERO;

    //private DetalleProcesoAdq detalleProcesoAdq = new DetalleProcesoAdq();
    private Empresa empresa = new Empresa();
    private CapaInstPorRubro capacidadInst = new CapaInstPorRubro();
    private CapaDistribucionAcre departamentoCalif = new CapaDistribucionAcre();
    
    private ProveedorMB proveedorMB;

    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private UtilEJB utilEJB;
    @EJB
    private DatosGeograficosEJB datosGeograficosEJB;
    

    @PostConstruct
    public void ini() {
        if (VarSession.isVariableSession("idEmpresa")) {
            proveedorMB = ((ProveedorMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "proveedorMB"));

            empresa = proveedorMB.getEmpresa();
            
            cargarDetalleCalificacion();

            idMunicipio = empresa.getIdPersona().getIdMunicipio().getIdMunicipio();
            codigoDepartamento = empresa.getIdPersona().getIdMunicipio().getCodigoDepartamento().getCodigoDepartamento();
        }
    }

    private void cargarDetalleCalificacion() {
        Anho anho = proveedorMB.getAnho();
        ProcesoAdquisicion proceso = anho.getProcesoAdquisicionList().get(0);
        
        if (proceso == null || proceso.getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe seleccionar un proceso de contratación");
        } else {
            if (proceso.getPadreIdProcesoAdq() != null) {
                proceso = proceso.getPadreIdProcesoAdq();
            }
            capacidadInst = proveedorEJB.findDetProveedor(proceso, empresa, CapaInstPorRubro.class);
            if (capacidadInst == null) {
                JsfUtil.mensajeAlerta("No se han cargado los datos de este proveedor para el proceso de contratación del año " + proceso.getIdAnho().getAnho());
            } else {
                //detalleProcesoAdq = capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq();
                departamentoCalif = proveedorEJB.findDetProveedor(proceso, empresa, CapaDistribucionAcre.class);

                if (departamentoCalif == null || departamentoCalif.getCodigoDepartamento() == null) {
                    JsfUtil.mensajeAlerta("Este proveedor no posee departamento de calificación " + proceso.getIdAnho().getAnho());
                } else {
                    codigoDepartamentoCalificado = departamentoCalif.getCodigoDepartamento().getCodigoDepartamento();

                    if (empresa.getIdPersona().getUrlImagen() == null) {
                        fileName = "fotoProveedores/profile.png";
                    } else {
                        fileName = "fotoProveedores/" + empresa.getIdPersona().getUrlImagen();
                    }
                }
            }
        }
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public BigDecimal getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(BigDecimal idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getCodigoDepartamentoCalificado() {
        return codigoDepartamentoCalificado;
    }

    public void setCodigoDepartamentoCalificado(String codigoDepartamentoCalificado) {
        this.codigoDepartamentoCalificado = codigoDepartamentoCalificado;
    }

    public CapaInstPorRubro getCapacidadInst() {
        if (capacidadInst == null) {
            capacidadInst = new CapaInstPorRubro();
        }
        return capacidadInst;
    }

    public void setCapacidadInst(CapaInstPorRubro capacidadInst) {
        this.capacidadInst = capacidadInst;
    }

    public String getTapEmpresa() {
        if (empresa == null) {
            tapEmpresa = "";
        } else if (empresa.getIdPersoneria().getIdPersoneria() == null) {
            tapEmpresa = "";
        } else if (empresa.getIdPersoneria().getIdPersoneria().intValue() == 2) {
            tapEmpresa = "Empresa";
        } else {
            tapEmpresa = "";
        }
        return tapEmpresa;
    }

    public String getTapPersona() {
        if (empresa == null) {
            tapPersona = "";
        } else if (empresa.getIdPersoneria().getIdPersoneria() == null) {
            tapPersona = "";
        } else if (empresa.getIdPersoneria().getIdPersoneria().intValue() == 2) {
            tapPersona = "Representante Legal";
        } else {
            tapPersona = "Proveedor";
        }
        return tapPersona;
    }

    public void guardarDatosGenerales() {
        if (empresa.getIdPersoneria().getIdPersoneria().intValue() == 1) {
            empresa.setRazonSocial(empresa.getIdPersona().getNombreCompletoProveedor());
            empresa.setIdMunicipio(empresa.getIdPersona().getIdMunicipio());
            empresa.setDireccionCompleta(empresa.getIdPersona().getDomicilio());
            empresa.setTelefonos(empresa.getIdPersona().getNumeroTelefono());
            empresa.setNumeroCelular(empresa.getIdPersona().getNumeroCelular());
            empresa.setNumeroNit(empresa.getIdPersona().getNumeroNit());
        }
        if (empresa.getIdPersona().getIdMunicipio().getIdMunicipio().intValue() != idMunicipio.intValue()) {
            empresa.getIdPersona().setIdMunicipio(new Municipio(idMunicipio));
        }

        proveedorEJB.guardar(empresa);

        departamentoCalif.setCodigoDepartamento(utilEJB.find(Departamento.class,
                codigoDepartamentoCalificado));

        if (proveedorEJB.guardarCapaInst(departamentoCalif, capacidadInst)) {
            JsfUtil.mensajeUpdate();
        }
    }
    
    public List<Municipio> getLstMunicipios() {
        return datosGeograficosEJB.getLstMunicipiosByDepartamento(codigoDepartamento);
    }
}
