/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.contratacion;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import sv.gob.mined.app.web.controller.AnhoProcesoController;
import sv.gob.mined.app.web.util.Bean2Excel;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.DatosGeograficosEJB;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.ejb.OfertaBienesServiciosEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.OfertaBienesServicios;
import sv.gob.mined.paquescolar.model.Participantes;
import sv.gob.mined.paquescolar.model.ResolucionesAdjudicativas;
import sv.gob.mined.paquescolar.model.pojos.VwRptCertificacionPresupuestaria;
import sv.gob.mined.paquescolar.model.pojos.contratacion.PrecioReferenciaEmpresaDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.VwCotizacion;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;
import sv.gob.mined.paquescolar.util.Constantes;

/**
 *
 * @author misanchez
 */
@ManagedBean(name = "ofertaMB")
@ViewScoped
public class OfertaMB extends RecuperarProcesoUtil implements Serializable {

    private int rowEdit = 0;
    private String estiloSeleccionado = "-";
    private String municipioCe;
    private String codigoEntidad;
    private String valorDeBusqueda;
    private String tipoDocumentoImp = "";
    private String nombreEmp = "";
    private String nombreEmpOtros = "";
    private Boolean showProductos = false;
    private Boolean deshabilitar = true;
    private Boolean modifDesac = false;
    private Boolean abrirDialogCe = false;
    private Boolean pageResolucion = false;
    private BigDecimal rubro = BigDecimal.ZERO;
    private BigDecimal cantidadAlumnos = BigDecimal.ZERO;

    private Empresa empresaSeleccionada;
    private Empresa tempEmpresaSeleccionada;
    private Participantes participanteSeleccionado;
    private DetalleProcesoAdq detalleProceso = new DetalleProcesoAdq();
    private CapaInstPorRubro capaInstSeleccionada;
    private OfertaBienesServicios current = new OfertaBienesServicios();
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();
    private SelectItem[] lstEstilos = new SelectItem[0];
    private List<String> images = new ArrayList();
    private List<CapaInstPorRubro> lstCapaEmpresas = new ArrayList();
    private List<CapaInstPorRubro> lstCapaEmpresasOtros = new ArrayList();
    private List<CapaInstPorRubro> lstEmpresas = new ArrayList();
    private List<CapaInstPorRubro> lstEmpresasOtros = new ArrayList();
    private List<PrecioReferenciaEmpresaDto> lstPrecios = new ArrayList();

    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private OfertaBienesServiciosEJB ofertaBienesServiciosEJB;
    @EJB
    private EntidadEducativaEJB entidadEducativaEJB;
    @EJB
    private DatosGeograficosEJB datosGeograficosEJB;
    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;
    @EJB
    private ReportesEJB reportesEJB;

    /**
     * Creates a new instance of OfertaMB
     */
    public OfertaMB() {
    }

    @PostConstruct
    public void init() {
        rubro = ((AnhoProcesoController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "anhoProcesoController")).getRubro();

        detalleProceso = anhoProcesoEJB.getDetProcesoAdq(getRecuperarProceso().getProcesoAdquisicion(), rubro);

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if (params.containsKey("txtCodigoEntidad")) {
            current = ofertaBienesServiciosEJB.getOfertaByProcesoCodigoEntidad(params.get("txtCodigoEntidad"), detalleProceso);

            codigoEntidad = current.getCodigoEntidad().getCodigoEntidad();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">
    public BigDecimal getCantidadAlumnos() {
        return cantidadAlumnos;
    }

    public String getNombreEmpOtros() {
        return nombreEmpOtros;
    }

    public void setNombreEmpOtros(String nombreEmpOtros) {
        this.nombreEmpOtros = nombreEmpOtros;
    }

    public String getNombreEmp() {
        return nombreEmp;
    }

    public void setNombreEmp(String nombreEmp) {
        this.nombreEmp = nombreEmp;
    }

    public Boolean getPageResolucion() {
        return pageResolucion;
    }

    public void setPageResolucion(Boolean pageResolucion) {
        this.pageResolucion = pageResolucion;
    }

    public BigDecimal getRubro() {
        return rubro;
    }

    public void setRubro(BigDecimal rubro) {
        if (rubro != null) {
            VarSession.crearCookie("rubro", rubro.toString());
            AnhoProcesoController controller = (AnhoProcesoController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                    getValue(FacesContext.getCurrentInstance().getELContext(), null, "anhoProcesoController");
            controller.setRubro(rubro);
            controller.findDetalleProcesoAdq();

            this.rubro = rubro;
        }
    }

    public Boolean getAbrirDialogCe() {
        return abrirDialogCe;
    }

    public void setAbrirDialogCe(Boolean abrirDialogCe) {
        this.abrirDialogCe = abrirDialogCe;
    }

    public Participantes getParticipanteSeleccionado() {
        return participanteSeleccionado;
    }

    public void setParticipanteSeleccionado(Participantes participanteSeleccionado) {
        this.participanteSeleccionado = participanteSeleccionado;
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(Boolean deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    public Boolean getModifDesac() {
        return modifDesac;
    }

    public void setModifDesac(Boolean modifDesac) {
        this.modifDesac = modifDesac;
    }

    public void setSelected(OfertaBienesServicios oferta) {
        if (oferta == null) {
            this.current = oferta;
        }
    }

    public OfertaBienesServicios getSelected() {
        if (current == null) {
            current = new OfertaBienesServicios();
        }
        return current;
    }

    public DetalleProcesoAdq getDetalleProceso() {
        return detalleProceso;
    }

    public void setDetalleProceso(DetalleProcesoAdq detalleProceso) {
        this.detalleProceso = detalleProceso;
    }

    public Empresa getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public String getMunicipioCe() {
        return municipioCe;
    }

    public void setMunicipioCe(String municipioCe) {
        this.municipioCe = municipioCe;
    }

    public Boolean getShowProductos() {
        return showProductos;
    }

    public void setShowProductos(Boolean showProductos) {
        this.showProductos = showProductos;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public CapaInstPorRubro getCapaInstSeleccionada() {
        return capaInstSeleccionada;
    }

    public void setCapaInstSeleccionada(CapaInstPorRubro capaInstSeleccionada) {
        if (capaInstSeleccionada != null) {
            this.capaInstSeleccionada = capaInstSeleccionada;
        }
    }

    public List<CapaInstPorRubro> getLstCapaEmpresas() {
        return lstCapaEmpresas;
    }

    public void setLstCapaEmpresas(List<CapaInstPorRubro> lstCapaEmpresas) {
        this.lstCapaEmpresas = lstCapaEmpresas;
    }

    public List<CapaInstPorRubro> getLstCapaEmpresasOtros() {
        return lstCapaEmpresasOtros;
    }

    public void setLstCapaEmpresasOtros(List<CapaInstPorRubro> lstCapaEmpresasOtros) {
        this.lstCapaEmpresasOtros = lstCapaEmpresasOtros;
    }

    public List<PrecioReferenciaEmpresaDto> getLstPrecios() {
        return lstPrecios;
    }

    public void setLstPrecios(List<PrecioReferenciaEmpresaDto> lstPrecios) {
        this.lstPrecios = lstPrecios;
    }

    /*public List<PreciosRefRubroEmp> getLstPreciosReferencia() {
        return lstPreciosReferencia;
    }

    public void setLstPreciosReferencia(List<PreciosRefRubroEmp> lstPreciosReferencia) {
        this.lstPreciosReferencia = lstPreciosReferencia;
    }*/
    public SelectItem[] getLstEstilos() {
        return lstEstilos;
    }

    public String getEstiloSeleccionado() {
        return estiloSeleccionado;
    }

    public void setEstiloSeleccionado(String estiloSeleccionado) {
        this.estiloSeleccionado = estiloSeleccionado;
    }

    public boolean getEENuevo() {
        return VarSession.getVariableSessionED() == 1;
    }

    public boolean getEEModificar() {
        return VarSession.getVariableSessionED() == 2;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
    }

    public void setEntidadEducativa(VwCatalogoEntidadEducativa entidadEducativa) {
        this.entidadEducativa = entidadEducativa;
    }

    public int getRowEdit() {
        return rowEdit;
    }

    public void setRowEdit(int rowEdit) {
        this.rowEdit = rowEdit;
    }

    public String getTipoDocumentoImp() {
        return tipoDocumentoImp;
    }

    public void setTipoDocumentoImp(String tipoDocumentoImp) {
        this.tipoDocumentoImp = tipoDocumentoImp;
    }

    // </editor-fold>
    public void mostrarFiltroCE() {
        if (detalleProceso == null || detalleProceso.getIdDetProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de contratación");
        } else {
            Map<String, Object> opt = new HashMap();
            opt.put("modal", true);
            opt.put("draggable", true);
            opt.put("resizable", false);
            opt.put("contentHeight", 380);
            opt.put("contentWidth", 615);
            PrimeFaces.current().dialog().openDynamic("/app/comunes/filtroCentroEscolar.mined", opt, null);
        }
    }

    public void cargarFotosPorEstilo() {
        if (!estiloSeleccionado.equals("-")) {
            File carpetaNfs = new File("/imagenes/PaqueteEscolar/Fotos_Zapatos/" + tempEmpresaSeleccionada.getNumeroNit() + "/" + estiloSeleccionado + "/");
            images = new ArrayList();

            if (carpetaNfs.list() != null) {
                for (String string : carpetaNfs.list()) {
                    images.add("Fotos_Zapatos/" + tempEmpresaSeleccionada.getNumeroNit() + "/" + estiloSeleccionado + "/" + string);
                }
            }
        }
    }

    public String getFotoPrincipal(String nit) {
        String imagen = "Fotos_Zapatos/sin_foto.png";
        File carpetaNfs = new File("/imagenes/PaqueteEscolar/Fotos_Zapatos/" + nit + "/");
        if (carpetaNfs.list() != null) {
            for (String nombreCarpeta : carpetaNfs.list()) {
                File carpetaImagenes = new File("/imagenes/PaqueteEscolar/Fotos_Zapatos/" + nit + "/" + nombreCarpeta + "/");
                if (carpetaImagenes.list() != null) {
                    for (String imagenes : carpetaImagenes.list()) {
                        return "Fotos_Zapatos/" + nit + "/" + nombreCarpeta + "/" + imagenes;
                    }
                }
            }
        }

        return imagen;
    }

    public void limpiarFiltros() {
        if (current != null) {
            current.getCodigoEntidad().setCodigoEntidad("");
        }
        lstCapaEmpresas = new ArrayList();
        lstCapaEmpresasOtros = new ArrayList();
        limpiarCampos();
        detalleProceso = anhoProcesoEJB.getDetProcesoAdq(getRecuperarProceso().getProcesoAdquisicion(), rubro);
    }

    public void onSelect(CapaInstPorRubro capa) {
        capaInstSeleccionada = capa;
        try {
            if (capaInstSeleccionada == null) {
                JsfUtil.mensajeAlerta("Debe de seleccionar un proveedor");
            } else {
                empresaSeleccionada = capaInstSeleccionada.getIdMuestraInteres().getIdEmpresa();
                lstPrecios = proveedorEJB.getLstPreciosByIdEmpresaAndIdProcesoAdq(empresaSeleccionada.getIdEmpresa(), detalleProceso.getIdProcesoAdq().getIdProcesoAdq());
                if (lstPrecios.isEmpty()) {
                    JsfUtil.mensajeAlerta("Este proveedor no posee precios de referencia. No se puede ingresar a la oferta.");
                } else {
                    if (VarSession.getDepartamentoUsuarioSession() != null) {
                        if (proveedorEJB.isDepaCalificado(empresaSeleccionada, entidadEducativa.getCodigoDepartamento().getCodigoDepartamento(), detalleProceso)) {
                            if (!findParticipanteEnOferta(empresaSeleccionada)) {
                                Participantes participante = new Participantes();

                                participante.setEstadoEliminacion(BigInteger.ZERO);
                                participante.setFechaInsercion(new Date());
                                participante.setIdEmpresa(empresaSeleccionada);
                                participante.setIdOferta(current);
                                participante.setModificativa(BigInteger.ZERO);
                                participante.setUsuarioInsercion(VarSession.getVariableSession("Usuario").toString());

                                current.getParticipantesList().add(participante);

                                capaInstSeleccionada = null;
                            }
                        } else {
                            JsfUtil.mensajeAlerta("Este proveedor no esta calificado para este departamento.");
                        }
                    } else {
                        JsfUtil.mensajeAlerta("Debe de seleccionar un departamento y municipio.");
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(OfertaMB.class.getName()).log(Level.INFO, null, "Error OfertaBienesServiciosController.onSelect()");
            Logger.getLogger(OfertaMB.class.getName()).log(Level.INFO, null, "Codigo Entidad " + current.getCodigoEntidad().getCodigoEntidad());
            Logger.getLogger(OfertaMB.class.getName()).log(Level.INFO, null, "Proveedor " + empresaSeleccionada.getNumeroNit());
            Logger.getLogger(OfertaMB.class.getName()).log(Level.INFO, null, "Error: " + e.getMessage());
        }
    }

    private boolean findParticipanteEnOferta(Empresa empresa) {
        if (current.getParticipantesList() == null) {
            current.setParticipantesList(new ArrayList(0));
        }

        for (Participantes par : current.getParticipantesList()) {
            if (par.getIdEmpresa().getNumeroNit().equals(empresa.getNumeroNit()) && par.getEstadoEliminacion().compareTo(BigInteger.ZERO) == 0) {
                JsfUtil.mensajeError("El proveedor seleccionado ya existe en la oferta actual.");
                return true;
            }
        }
        return false;
    }

    public void prepareCreate() {
        current = new OfertaBienesServicios();
        deshabilitar = false;
        VarSession.setVariableSessionED("1");
        limpiarCampos();
    }

    public void limpiarCampos() {
        current = new OfertaBienesServicios();
        codigoEntidad = "";
        entidadEducativa = new VwCatalogoEntidadEducativa();
    }

    public void create() {
        HashMap<String, Object> parametros = ofertaBienesServiciosEJB.create(current, VarSession.getVariableSessionUsuario());

        if ((Boolean) parametros.get(Constantes.ERROR)) {
            JsfUtil.mensajeError(parametros.get(Constantes.MSJ_ERROR).toString());
        } else if ((Boolean) parametros.get(Constantes.WARNING)) {
            JsfUtil.mensajeError(parametros.get(Constantes.MSJ_WARNING).toString());
        } else {
            JsfUtil.mensajeInsert();
            VarSession.setVariableSessionED("2");
        }
    }

    public void guardar() {
        if (current.getCodigoEntidad() == null || current.getCodigoEntidad().getCodigoEntidad().isEmpty()) {
            JsfUtil.mensajeAlerta("Debe de agregar el centro escolar y el detalle de proveedores.");
        } else if (current.getFechaApertura() == null) {
            JsfUtil.mensajeAlerta("Debe de agregar la fecha de registro de la oferta.");
        } else if (current.getParticipantesList() == null) {
            JsfUtil.mensajeAlerta("Debe de agregar el detalle de provedores");
        } else if (current != null && current.getIdDetProcesoAdq() != null) {
            if (current.getIdOferta() != null) {
                update();
            } else {
                create();
                VarSession.setVariableSessionED("2");
            }
        }
    }

    public void prepareEdit() {
        VarSession.setVariableSessionED("2");
        entidadEducativa = null;
        deshabilitar = false;
        limpiarCampos();
    }

    public void update() {
        try {
            current = ofertaBienesServiciosEJB.edit(current);
            JsfUtil.mensajeUpdate();
        } catch (Exception e) {
            JsfUtil.mensajeError("Error en la actulización de la oferta.");
        }
    }

    public void imprimir() throws Exception {
        if (tipoDocumentoImp.equals("1")) {
            imprimirAnalisisEconomico();
        } else if (tipoDocumentoImp.equals("2")) {
            imprimirCertificacion();
        }
    }

    public void imprimirAnalisisEconomico() throws Exception {
        if (getSelected().getIdOferta() == null) {
            JsfUtil.mensajeAlerta("Primero debe de guardar la oferta!!!");
        } else {
            SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
            List lst = ofertaBienesServiciosEJB.getDatosRptAnalisisEconomico(getSelected().getCodigoEntidad().getCodigoEntidad(), getSelected().getIdDetProcesoAdq());
            Bean2Excel oReport = new Bean2Excel(lst, detalleProceso.getIdRubroAdq().getDescripcionRubro(), entidadEducativa.getNombre(), entidadEducativa.getCodigoEntidad(), "", sd.format(getSelected().getFechaApertura()), getSelected().getUsuarioInsercion());
            oReport.createFile(getSelected().getCodigoEntidad().getCodigoEntidad());
        }
    }

    public void imprimirCertificacion() {
        List<VwRptCertificacionPresupuestaria> lst = new ArrayList();
        HashMap param = new HashMap();

        if (detalleProceso != null) {
            VwRptCertificacionPresupuestaria vw = entidadEducativaEJB.getCertificacion(codigoEntidad, getRecuperarProceso().getProcesoAdquisicion(), (getRecuperarProceso().getProcesoAdquisicion().getIdProcesoAdq() >= 12));

            if (vw != null) {
                vw.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                lst.add(vw);
                String nombreRpt = "";

                switch (detalleProceso.getIdRubroAdq().getIdRubroInteres().intValue()) {
                    case 1:
                        nombreRpt = "rptCertUni.jasper";
                        break;
                    case 2:
                        nombreRpt = "rptCertUti.jasper";
                        break;
                    case 3:
                        nombreRpt = "rptCertZap.jasper";
                        break;
                }
                Reportes.generarRptsContractuales(lst, param, codigoEntidad, detalleProceso.getIdProcesoAdq().getDescripcionProcesoAdq().contains("SOBREDEMANDA"), null, nombreRpt);
            } else {
                JsfUtil.mensajeAlerta("Error obteniendo las estadisticas de censo rápido para el centro escolar: " + codigoEntidad);
            }
        }
    }

    public void buscarEntidadEducativa() {
        if (codigoEntidad.length() == 5) {
            /**
             * Fecha: 30/08/2018 Comentario: Validación de seleccion del año y
             * proceso de adquisición
             */
            if (getRecuperarProceso().getProcesoAdquisicion() == null) {
                JsfUtil.mensajeAlerta("Debe de seleccionar un año y proceso de contratación.");
            } else {
                detalleProceso = anhoProcesoEJB.getDetProcesoAdq(getRecuperarProceso().getProcesoAdquisicion(), rubro);
                current.setIdDetProcesoAdq(detalleProceso);
                abrirDialogCe = false;
                entidadEducativa = entidadEducativaEJB.getEntidadEducativa(codigoEntidad);
                getSelected().setCodigoEntidad(entidadEducativa);
                if (entidadEducativa == null) {
                    JsfUtil.mensajeAlerta("No se ha encontrado el centro escolar con código: " + current.getCodigoEntidad().getCodigoEntidad());
                } else {
                    if (VarSession.getDepartamentoUsuarioSession() != null) {
                        String dep = getRecuperarProceso().getDepartamento();
                        if (entidadEducativa.getCodigoDepartamento().getCodigoDepartamento().equals(dep) || (Integer) VarSession.getVariableSession("idTipoUsuario") == 1) {
                            if (VarSession.getVariableSessionED() == 1) {
                                if (ofertaBienesServiciosEJB.isOfertaRubro(current.getCodigoEntidad().getCodigoEntidad(), current.getIdDetProcesoAdq())) {
                                    JsfUtil.mensajeError("Ya existe un proceso de contratación para este centro escolar.");
                                } else {
                                    deshabilitar = false;
                                }
                            } else if (VarSession.getVariableSessionED() == 2) {
                                current = ofertaBienesServiciosEJB.getOfertaByProcesoCodigoEntidad(current.getCodigoEntidad().getCodigoEntidad(), current.getIdDetProcesoAdq());

                                if (current == null) {
                                    JsfUtil.mensajeError("No existe un proceso de contratación para este centro escolar.");
                                } else {
                                    deshabilitar = false;
                                }
                            }
                        } else {
                            JsfUtil.mensajeAlerta("El codigo del centro escolar no pertenece al departamento " + JsfUtil.getNombreDepartamentoByCodigo(dep) + "<br/>"
                                    + "Departamento del CE: " + entidadEducativa.getCodigoEntidad() + " es " + entidadEducativa.getCodigoDepartamento().getNombreDepartamento());
                        }
                        abrirDialogCe = true;
                    } else {
                        JsfUtil.mensajeAlerta("Debe de seleccionar un departamento y municipio.");
                    }
                }
            }
        } else {
            if (current != null && current.getParticipantesList() != null) {
                current.getParticipantesList().clear();
            }
            entidadEducativa = null;
        }
    }

    private void ejecutarCalculo(String cod) {
        int ed = VarSession.getVariableSessionED();
        ResolucionesAdjudicativasController controller = (ResolucionesAdjudicativasController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "resolucionesAdjudicativasController");
        controller.setCodigoEntidad(cod);
        controller.buscarSaldoPresupuestoCE(detalleProceso.getIdDetProcesoAdq());
        VarSession.setVariableSessionED(String.valueOf(ed));
    }

    public void abrirDialogProveedor() {
        if (current != null && current.getCodigoEntidad() != null && current.getCodigoEntidad().getCodigoEntidad().length() == 5) {
            if (current.getIdDetProcesoAdq() == null) {
                current.setIdDetProcesoAdq(detalleProceso);
            }
            consultarEmpresa();
            PrimeFaces.current().executeScript("PF('dlgProveedor').show();");
        } else {
            JsfUtil.mensajeAlerta("Debe de ingresar un código de infraestructura válido");
        }
    }

    public void consultarEmpresa() {
        BigInteger cantidad;
        municipioCe = datosGeograficosEJB.findNombreMunicipioCe(current.getCodigoEntidad().getCodigoEntidad());
        cantidadAlumnos = entidadEducativaEJB.getCantidadTotalByCodEntAndIdProcesoAdq(codigoEntidad, current.getIdDetProcesoAdq().getIdProcesoAdq().getIdProcesoAdq());

        if (detalleProceso.getIdRubroAdq().getIdRubroUniforme().intValue() == 1) {
            cantidad = cantidadAlumnos.multiply(new BigDecimal(2)).toBigInteger();
        } else {
            cantidad = cantidadAlumnos.toBigInteger();
        }

        lstEmpresas = proveedorEJB.getLstCapaEmpPorNitOrRazonSocialAndRubroAndMunicipioCe(current.getIdDetProcesoAdq(), current.getCodigoEntidad().getCodigoEntidad(), true, true, cantidad);
        lstEmpresasOtros = proveedorEJB.getLstCapaEmpPorNitOrRazonSocialAndRubroAndMunicipioCe(current.getIdDetProcesoAdq(), current.getCodigoEntidad().getCodigoEntidad(), false, false, BigInteger.ZERO);

        lstCapaEmpresas.clear();
        lstCapaEmpresas.addAll(lstEmpresas);
        lstCapaEmpresasOtros.clear();
        lstCapaEmpresasOtros.addAll(lstEmpresasOtros);

        if (lstEmpresas.isEmpty()) {
            JsfUtil.mensajeInformacion("No se encontró ninguna coincidencia con el valor: <strong>" + valorDeBusqueda + "</strong>");
        }
    }

    public void eliminarDetalle() {
        if (participanteSeleccionado != null) {
            if (participanteSeleccionado.getEstadoEliminacion().compareTo(BigInteger.ZERO) == 0) {
                if (participanteSeleccionado.getIdParticipante() != null) {
                    participanteSeleccionado.setEstadoEliminacion(BigInteger.ONE);
                } else {
                    getSelected().getParticipantesList().remove(rowEdit);
                }
            } else {
                participanteSeleccionado.setEstadoEliminacion(BigInteger.ZERO);
            }

            participanteSeleccionado = null;
        } else {
            JsfUtil.mensajeAlerta("Debe seleccionar un participante para poder eliminarlo.");
        }
    }

    public void rptCotizacion() {
        String anho = "";
        String nombreRpt = "";
        HashMap param = new HashMap();
        List<VwCotizacion> lst = ofertaBienesServiciosEJB.getLstCotizacion(VarSession.getNombreMunicipioSession(), getSelected().getCodigoEntidad().getCodigoEntidad(), getSelected().getIdDetProcesoAdq(), participanteSeleccionado);
        Boolean sobredemanda = getRecuperarProceso().getProcesoAdquisicion().getDescripcionProcesoAdq().contains("SOBREDEMANDA");

        //Para contratos antes de 2016, se tomara los formatos de rpt que no incluyen el año en el nombre del archivo jasper
        if (Integer.parseInt(getSelected().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getAnho()) > 2016) {
            anho = getSelected().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getAnho();
        }

        switch (getSelected().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres().toBigInteger().intValue()) {
            case 1:
            case 4:
            case 5:
                nombreRpt = "rptCotizacionUni" + anho + ".jasper";
                break;
            case 2:
                nombreRpt = "rptCotizacionUti" + anho + ".jasper";
                break;
            case 3:
                if (getRecuperarProceso().getProcesoAdquisicion().getDescripcionProcesoAdq().contains("MINI")) {
                    nombreRpt = "rptCotizacionZap" + anho + "_mini.jasper";
                } else {
                    nombreRpt = "rptCotizacionZap" + anho + ".jasper";
                }
        }
        param = JsfUtil.getNombreRubroRpt(detalleProceso.getIdRubroAdq().getIdRubroInteres().toBigInteger().intValue(), param, sobredemanda);
        param.put("ubicacionImagenes", OfertaBienesServicios.class.getClassLoader().getResource(("sv/gob/mined/apps/reportes/cotizacion" + File.separator + nombreRpt)).getPath().replace(nombreRpt, ""));

        Reportes.generarRptBeanConnection(lst, param, "sv/gob/mined/apps/reportes/cotizacion", nombreRpt, "cotización" + codigoEntidad);
    }

    public String editarOfertaParticipante() {
        String r = "";
        boolean modificaciones = false;

        for (Participantes participante : current.getParticipantesList()) {
            if (participante.getIdParticipante() == null) {
                modificaciones = true;
                break;
            }
        }

        if (modificaciones == false) {
            switch (VarSession.getVariableSessionED()) {
                case 2:
                    r = "reg02DetalleOferta";
                    break;
            }
        } else {
            JsfUtil.mensajeAlerta("Primero debe de guardar los cambios realizados.");
        }

        return r;
    }

    public void deleteParticipante() {
        if (participanteSeleccionado.getIdParticipante() == null) {
            current.getParticipantesList().remove(participanteSeleccionado);
        } else {
            ResolucionesAdjudicativas res = ofertaBienesServiciosEJB.findResolucionesAdjudicativas(participanteSeleccionado);
            if (res != null) {
                //El proveedor seleccionado tiene un reserva de fondos
                switch (res.getIdEstadoReserva().getIdEstadoReserva().intValue()) {
                    case 1:
                    case 3:
                        res.setEstadoEliminacion(BigInteger.ONE);
                        try {
                            ofertaBienesServiciosEJB.editResolucion(res, VarSession.getVariableSessionUsuario());
                            participanteSeleccionado.setEstadoEliminacion(BigInteger.ONE);
                            participanteSeleccionado.setFechaEliminacion(new Date());
                            participanteSeleccionado.setUsuarioModificacion(VarSession.getVariableSessionUsuario());

                        } catch (Exception ex) {
                            JsfUtil.mensajeError("Ocurrio un error en la operación.\n" + ex.getMessage());
                        }
                        break;
                    case 2:
                    case 4:
                    case 5:
                        JsfUtil.mensajeAlerta("No se puede eliminar este participante, "
                                + "porque se encuentra en una reserva de fondos con estado: " + res.getIdEstadoReserva().getDescripcionReserva());
                        break;
                }
            } else {
                //El proveedor seleccionado NO tiene reserva de fondos
                try {
                    participanteSeleccionado.setEstadoEliminacion(BigInteger.ONE);
                    participanteSeleccionado.setFechaEliminacion(new Date());
                    participanteSeleccionado.setUsuarioModificacion(VarSession.getVariableSessionUsuario());
                    ofertaBienesServiciosEJB.edit(current);
                    buscarEntidadEducativa();
                } catch (Exception ex) {
                    JsfUtil.mensajeError("Ocurrio un error en la eliminación del participante.");
                }
            }
        }
    }

    public void onRowSelect(SelectEvent event) {
        capaInstSeleccionada = (CapaInstPorRubro) event.getObject();
    }

    /**
     * Este metodo se ejecuta en el filtro de centros escolares en la pagina
     * regReservaFondos.xhtml
     */
    public void buscarEntidadEducativaRes() {
        if (codigoEntidad.length() == 5) {
            /**
             * Fecha: 30/08/2018 Comentario: Validación de seleccion del año y
             * proceso de adquisición
             */
            if (getRecuperarProceso().getProcesoAdquisicion() == null) {
                JsfUtil.mensajeAlerta("Debe de seleccionar un año y proceso de contratación.");
            } else {
                detalleProceso = anhoProcesoEJB.getDetProcesoAdq(getRecuperarProceso().getProcesoAdquisicion(), rubro);
                current.setIdDetProcesoAdq(detalleProceso);
                abrirDialogCe = false;

                current = ofertaBienesServiciosEJB.getOfertaByProcesoCodigoEntidad(codigoEntidad, detalleProceso);

                if (current == null) {
                    entidadEducativa = entidadEducativaEJB.getEntidadEducativa(codigoEntidad);
                    if (entidadEducativa == null) {
                        JsfUtil.mensajeAlerta("No se ha encontrado el centro escolar con código: " + current.getCodigoEntidad());
                    } else {
                        JsfUtil.mensajeError("No existe un proceso de contratación para este centro escolar.");
                    }
                } else {
                    entidadEducativa = current.getCodigoEntidad();
                    ejecutarCalculo(codigoEntidad);
                    cargarOferta();
                }
            }
        } else {
            if (current != null && current.getParticipantesList() != null) {
                current.getParticipantesList().clear();
            }
            entidadEducativa = null;
        }
    }

    private void cargarOferta() {
        //getSelected().setCodigoEntidad(entidadEducativa);
        if (VarSession.getDepartamentoUsuarioSession() != null) {
            String dep = getRecuperarProceso().getDepartamento();
            if (entidadEducativa.getCodigoDepartamento().getCodigoDepartamento().equals(dep)
                    || (Integer) VarSession.getVariableSession("idTipoUsuario") == 1) {
                if (VarSession.getVariableSessionED() == 1) {
                    if (current != null) {
                        JsfUtil.mensajeError("Ya existe un proceso de contratación para este centro escolar.");
                    }
                } else if (VarSession.getVariableSessionED() == 2) {
                    //current = ofertaBienesServiciosEJB.getOfertaByProcesoCodigoEntidad(current.getCodigoEntidad().getCodigoEntidad(), current.getIdDetProcesoAdq());

                    if (current == null) {
                        JsfUtil.mensajeError("No existe un proceso de contratación para este centro escolar.");
                    }
                }
            } else {
                JsfUtil.mensajeAlerta("El codigo del centro escolar no pertenece al departamento " + JsfUtil.getNombreDepartamentoByCodigo(dep) + "<br/>"
                        + "Departamento del CE: " + entidadEducativa.getCodigoEntidad() + " es " + entidadEducativa.getCodigoDepartamento().getNombreDepartamento());
            }
            abrirDialogCe = true;
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar un departamento y municipio.");
        }
    }

    public void cargarDetalleProveedor(CapaInstPorRubro capa) {
        capaInstSeleccionada = capa;
        tempEmpresaSeleccionada = capaInstSeleccionada.getIdMuestraInteres().getIdEmpresa();
        File carpetaNfs = new File("/imagenes/PaqueteEscolar/Fotos_Zapatos/" + tempEmpresaSeleccionada.getNumeroNit() + "/");
        //lstPreciosReferencia = proveedorEJB.findPreciosRefRubroEmpRubro(capaInstSeleccionada.getIdMuestraInteres().getIdEmpresa(), detalleProceso);
        lstPrecios = proveedorEJB.getLstPreciosByIdEmpresaAndIdProcesoAdq(capaInstSeleccionada.getIdMuestraInteres().getIdEmpresa().getIdEmpresa(), detalleProceso.getIdProcesoAdq().getIdProcesoAdq());

        if (carpetaNfs.list() != null) {
            lstEstilos = new SelectItem[carpetaNfs.list().length + 1];
            int i = 0;
            lstEstilos[i] = new SelectItem("-", "Seleccione");
            i++;
            for (String string : carpetaNfs.list()) {
                lstEstilos[i] = new SelectItem(string, string);
                i++;
            }
        }
    }

    public void filterProveedores() {
        lstCapaEmpresas = JsfUtil.getListFilterByStream(lstEmpresas, nombreEmp);
    }

    public void filterProveedoresOtros() {
        lstCapaEmpresasOtros = JsfUtil.getListFilterByStream(lstEmpresasOtros, nombreEmpOtros);
    }

    public void filterReiniciarProveedores() {
        lstCapaEmpresas.clear();
        lstCapaEmpresas.addAll(lstEmpresas);
        lstCapaEmpresasOtros.clear();
        lstCapaEmpresasOtros.addAll(lstEmpresasOtros);
    }

    public void imprimirDetalleAContratar() {
        HashMap param = new HashMap();
        param.put("pRubro", detalleProceso.getIdRubroAdq().getDescripcionRubro());
        param.put("pAnho", detalleProceso.getIdProcesoAdq().getIdAnho().getAnho());
        param.put("pCodigoEntidad", codigoEntidad);
        param.put("pHoraYFecha", entidadEducativa.getCodigoDepartamento().getNombreDepartamento() + ", " + JsfUtil.getFechaString(new Date()));

        Reportes.generarRptSQLConnection(reportesEJB, param, "sv/gob/mined/apps/reportes/contratos/", "rptDetalleDeBienesUniforme", "rptDetalleDeBienesUniforme_");
    }
}
