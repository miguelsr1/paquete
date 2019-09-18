/*
 * To change this template, choose Tools | Templates
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.controller.AnhoProcesoController;
import sv.gob.mined.app.web.util.Bean2Excel;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.apps.utilitario.Herramientas;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.ejb.OfertaBienesServiciosEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;
import sv.gob.mined.paquescolar.ejb.ResolucionAdjudicativaEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
import sv.gob.mined.paquescolar.model.DetalleOfertas;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.HistorialCamEstResAdj;
import sv.gob.mined.paquescolar.model.OfertaBienesServicios;
import sv.gob.mined.paquescolar.model.OrganizacionEducativa;
import sv.gob.mined.paquescolar.model.Participantes;
import sv.gob.mined.paquescolar.model.ResolucionesAdjudicativas;
import sv.gob.mined.paquescolar.model.RptDocumentos;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class ContratosOrdenesComprasController extends RecuperarProcesoUtil implements Serializable {

    private int estadoEdicion = 0;
    private int tipoRpt = 1;
    private Boolean soloLectura = false;
    private Boolean showFechaOrdenInicio = true;
    private Boolean deshabilitado = true;
    private Boolean continuar = true;
    private Boolean noEditableRepCe = true;
    private Boolean filtroCE = true;
    private Boolean cambiarRepreCe = false;
    private Boolean cambiarCiudadFirma = false;
    private Boolean crearRepresentante = false;
    private Boolean actaAdj = false;
    private Boolean notaAdj = false;
    private Boolean contrato = false;
    private Boolean garantiaContrato = false;
    private Boolean garantiaAnticipo = false;
    private Boolean garantiaUsoTela = false;
    private Boolean showGarantiaUsoTela = false;
    private Boolean analisisTecEco = false;
    private String codigoEntidad;
    private String razonSocial;
    private String representanteLegal;
    private BigDecimal rubro = BigDecimal.ZERO;
    private BigDecimal idParticipante = BigDecimal.ZERO;
    private BigDecimal idMunicipio;
    private DetalleProcesoAdq detalleProceso = new DetalleProcesoAdq();
    private OfertaBienesServicios oferta;
    private Participantes participante;
    private OrganizacionEducativa representanteCe;
    private ContratosOrdenesCompras current = new ContratosOrdenesCompras();
    private ResolucionesAdjudicativas resolucionAdj = new ResolucionesAdjudicativas();
    private VwCatalogoEntidadEducativa entidadEducativa = new VwCatalogoEntidadEducativa();
    private List<Integer> lstSelectDocumentosImp = new ArrayList();
    private List<SelectItem> lstDocumentosImp = new ArrayList();
    private List<HistorialCamEstResAdj> lstHistorialCambios = new ArrayList();
    @EJB
    private OfertaBienesServiciosEJB ofertaBienesServiciosEJB;
    @EJB
    private EntidadEducativaEJB entidadEducativaEJB;
    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private ResolucionAdjudicativaEJB resolucionAdjudicativaEJB;
    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;
    @EJB
    private ReportesEJB reportesEJB;
    @EJB
    private UtilEJB utilEJB;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("Bundle");

    /**
     * Creates a new instance of ContratosOrdenesComprasController
     */
    public ContratosOrdenesComprasController() {
    }

    @PostConstruct
    public void ini() {
        VarSession.setVariableSessionED("0");
        rubro = ((AnhoProcesoController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "anhoProcesoController")).getRubro();

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        detalleProceso = anhoProcesoEJB.getDetProcesoAdq(getRecuperarProceso().getProcesoAdquisicion(), rubro);

        if (VarSession.getIdMunicipioSession() != null) {
            idMunicipio = VarSession.getIdMunicipioSession();
        }

        if (params.containsKey("txtCodigoEntidad")) {
            VarSession.setVariableSessionED("2");
            if (detalleProceso != null) {
                cargaInicialDeDatos(params);
                lstDocumentosImp = utilEJB.getLstDocumentosImp(rubro.intValueExact() == 1 || rubro.intValueExact() == 4 || rubro.intValueExact() == 5);
            }
        } else {
            VarSession.setVariableSessionED("0");
        }
    }

    public List<HistorialCamEstResAdj> getLstHistorialCambios() {
        return lstHistorialCambios;
    }

    public void setLstHistorialCambios(List<HistorialCamEstResAdj> lstHistorialCambios) {
        this.lstHistorialCambios = lstHistorialCambios;
    }

    public Boolean getAnalisisTecEco() {
        return analisisTecEco;
    }

    public void setAnalisisTecEco(Boolean analisisTecEco) {
        this.analisisTecEco = analisisTecEco;
    }

    public Boolean getSoloLectura() {
        return soloLectura;
    }

    public BigDecimal getRubro() {
        return rubro;
    }

    public void setRubro(BigDecimal rubro) {
        this.rubro = rubro;
    }

    public Boolean getShowFechaOrdenInicio() {
        return showFechaOrdenInicio;
    }

    public void setShowFechaOrdenInicio(Boolean showFechaOrdenInicio) {
        this.showFechaOrdenInicio = showFechaOrdenInicio;
    }

    public ContratosOrdenesCompras getSelected() {
        return current;
    }

    public Boolean getNoEditableRepCe() {
        return noEditableRepCe;
    }

    public void setNoEditableRepCe(Boolean noEditableRepCe) {
        this.noEditableRepCe = noEditableRepCe;
    }

    public Boolean getCrearRepresentante() {
        return crearRepresentante;
    }

    public void setCrearRepresentante(Boolean crearRepresentante) {
        this.crearRepresentante = crearRepresentante;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public OfertaBienesServicios getOferta() {
        return oferta;
    }

    public void setOferta(OfertaBienesServicios oferta) {
        this.oferta = oferta;
    }

    public Participantes getParticipante() {
        return participante;
    }

    public void setParticipante(Participantes participante) {
        this.participante = participante;
    }

    public BigDecimal getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(BigDecimal idParticipante) {
        this.idParticipante = idParticipante;
    }

    public OrganizacionEducativa getRepresentanteCe() {
        return representanteCe;
    }

    public void setRepresentanteCe(OrganizacionEducativa representanteCe) {
        this.representanteCe = representanteCe;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
    }

    public void setEntidadEducativa(VwCatalogoEntidadEducativa entidadEducativa) {
        this.entidadEducativa = entidadEducativa;
    }

    private void cargaInicialDeDatos(Map<String, String> params) {
        if (params.containsKey("txtCodigoEntidad")) {
            codigoEntidad = params.get("txtCodigoEntidad");
            entidadEducativa = entidadEducativaEJB.getEntidadEducativa(codigoEntidad);
            resolucionAdj = resolucionAdjudicativaEJB.findResolucionesAdjudicativasByPk((BigDecimal) VarSession.getVariableSession("idRes"));
            continuar = false;
            deshabilitado = false;
            if (resolucionAdj.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdRubroAdq().getDescripcionRubro().contains("UNIFORME")) {
                showGarantiaUsoTela = true;
                showFechaOrdenInicio = false;
            }
            cargarDocumentoLegal();
        } else {
            JsfUtil.mensajeAlerta("Se ha perdido el valor del codigo del centro escolar. Por favor inicie nuevamente su proceso");
            Logger.getLogger(ContratosOrdenesCompras.class.getName()).log(Level.INFO, null, "=============================================================");
            Logger.getLogger(ContratosOrdenesCompras.class.getName()).log(Level.INFO, null, "Error: Se ha perdido el valor de la variable codigoEntidad");
            Logger.getLogger(ContratosOrdenesCompras.class.getName()).log(Level.INFO, null, "ContratosOrdenesComprasController.cargaInicialDeDatos()");
            Logger.getLogger(ContratosOrdenesCompras.class.getName()).log(Level.INFO, null, "=============================================================");
        }

    }

    private void cargarDocumentoLegal() {
        List<ContratosOrdenesCompras> lst = resolucionAdjudicativaEJB.findContratoByResolucion(resolucionAdj);

        if (lst.size() > 1) {
            JsfUtil.mensajeError("Existe un problema con el contrato seleccionado, por favor reportarlo al administrador del sistema.");
            resolucionAdjudicativaEJB.enviarCorreoDeError(resolucionAdj.getIdResolucionAdj());
        } else {
            ContratosOrdenesCompras contratoOrd = null;

            if (lst.size() == 1) {
                contratoOrd = lst.get(0);
            }

            razonSocial = resolucionAdj.getIdParticipante().getIdEmpresa().getRazonSocial();
            representanteLegal = proveedorEJB.getRespresentanteLegalEmp(resolucionAdj.getIdParticipante().getIdEmpresa().getIdPersona().getIdPersona());
            representanteCe = entidadEducativaEJB.getPresidenteOrganismoEscolar(codigoEntidad);

            if (contratoOrd == null) { //CREAR NUEVA INSTACIA DE UN CONTRATO
                current = new ContratosOrdenesCompras();

                if (idMunicipio != null) {
                    current.setCiudadFirma(VarSession.getNombreMunicipioSession());
                    current.setAnhoContrato(resolucionAdj.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getAnho());

                    setPlazoPrevistoEntrega();

                    if (representanteCe != null) {
                        current.setMiembroFirma(representanteCe.getNombreMiembro());
                        noEditableRepCe = false;
                    }
                }
            } else { //CARGAR CONTRATO EXISTENTE
                current = contratoOrd;
                /**
                 * Fecha: 05/09/2018 Comentario: Validar que el contrato
                 * seleccionado tenga asignado el plazo previsto de entrega
                 */
                if (current.getPlazoPrevistoEntrega() == null) {
                    setPlazoPrevistoEntrega();
                    resolucionAdjudicativaEJB.editContrato(current);
                }
            }
        }
    }

    public boolean getEENuevo() {
        return VarSession.getVariableSessionED() == 1;
    }

    public boolean getEEModificar() {
        return VarSession.getVariableSessionED() == 2;
    }

    public String prepareCreate() {
        if (idMunicipio != null) {
            limpiarCampos();
            codigoEntidad = "";
            showGarantiaUsoTela = false;
            current = new ContratosOrdenesCompras();
            current.setCiudadFirma(VarSession.getNombreMunicipioSession());
            oferta = null;
            deshabilitado = false;
            estadoEdicion = 1;
            continuar = true;
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar un departamento y municipio.");
        }
        return null;
    }

    public void limpiarCampos() {
        oferta = null;
        entidadEducativa = null;
        participante = null;
        continuar = true;
        idParticipante = BigDecimal.ZERO;
        soloLectura = false;
        lstSelectDocumentosImp.clear();
    }

    public Boolean getDeshabilitado() {
        return deshabilitado;
    }

    public void setDeshabilitado(Boolean deshabilitado) {
        this.deshabilitado = deshabilitado;
    }

    public Boolean getContinuar() {
        return continuar;
    }

    public void setContinuar(Boolean continuar) {
        this.continuar = continuar;
    }

    public void guardar() {
        if (current != null) {
            if (resolucionAdj != null && resolucionAdj.getIdResolucionAdj() != null) {
                if (current.getCiudadFirma() == null || current.getCiudadFirma().isEmpty()) {
                    JsfUtil.mensajeError("Ocurrio un error inesperado. Debe de reasignar el municipio.");
                } else {
                    if (current.getIdContrato() == null) {
                        create();
                    } else {
                        update();
                    }
                }
            } else {
                JsfUtil.mensajeError("Ocurrio un error inesperado. Por favor iniciar nuevamente el proceso de inserción/modificación del contrato.");
            }
        }
    }

    public void create() {
        try {
            if (current.getAnhoContrato() == null) {
            } else if (current.getFechaEmision() == null) {
            } else {
                if (!noEditableRepCe) {
                    if (crearRepresentante) {
                        representanteCe = new OrganizacionEducativa();
                        representanteCe.setCargo("Presidente Propietario, Director");
                        representanteCe.setCodigoEntidad(codigoEntidad);
                        representanteCe.setEstadoEliminacion(BigInteger.ZERO);
                        representanteCe.setFechaInsercion(new Date());
                        representanteCe.setFirmaContrato(BigInteger.ONE);
                        representanteCe.setNombreMiembro(current.getMiembroFirma());
                        representanteCe.setUsuarioInsercion(VarSession.getVariableSessionUsuario());

                        entidadEducativaEJB.create(representanteCe);
                    }
                }
                current.setModificativa((short) 0);
                current.setMiembroFirma(representanteCe.getNombreMiembro());
                current.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                current.setFechaInsercion(new Date());
                current.setEstadoEliminacion(BigInteger.ZERO);
                current.setIdResolucionAdj(resolucionAdj);

                current = resolucionAdjudicativaEJB.createContrato(current);
                JsfUtil.mensajeInsert();
            }
        } catch (Exception e) {
            JsfUtil.mensajeError("Error en el registro del contrato del C.E.");
        }
    }

    public String prepareEdit() {
        if (idMunicipio != null) {
            //actualizar anho y detalle proceso adquisicion
            limpiarCampos();
            showGarantiaUsoTela = false;
            codigoEntidad = "";
            current = new ContratosOrdenesCompras();
            oferta = null;
            deshabilitado = false;
            estadoEdicion = 2;
            continuar = true;
            garantiaAnticipo = false;
            garantiaContrato = false;
            garantiaUsoTela = false;
            actaAdj = false;
            notaAdj = false;
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar un departamento y municipio.");
        }

        return null;
    }

    public void update() {
        try {
            if (noEditableRepCe) {
                representanteCe.setNombreMiembro(current.getMiembroFirma());
                representanteCe.setFechaModificacion(new Date());
                representanteCe.setUsuarioModificacion(VarSession.getVariableSessionUsuario());

                entidadEducativaEJB.edit(representanteCe);
            }

            current.setUsuarioModificacion(VarSession.getVariableSessionUsuario());
            current.setFechaModificacion(new Date());
            current = resolucionAdjudicativaEJB.editContrato(current);

            JsfUtil.mensajeUpdate();
        } catch (Exception e) {
            Logger.getLogger(ContratosOrdenesCompras.class.getName()).log(Level.SEVERE, null, e);
            JsfUtil.mensajeError("Error en la actualización del contrato del C.E.");
        }
    }

    public void buscarEntidadEducativa() {
        limpiarCampos();
        if (codigoEntidad.length() == 5) {
            /**
             * Fecha: 30/08/2018 Comentario: Validación de seleccion del año y
             * proceso de adquisición
             */
            if (getRecuperarProceso().getProcesoAdquisicion() == null) {
                JsfUtil.mensajeAlerta("Debe de seleccionar un año y proceso de contratación.");
            } else {
                detalleProceso = anhoProcesoEJB.getDetProcesoAdq(getRecuperarProceso().getProcesoAdquisicion(), rubro);
                entidadEducativa = entidadEducativaEJB.getEntidadEducativa(codigoEntidad);

                if (entidadEducativa == null) {
                    JsfUtil.mensajeAlerta("No se ha encontrado el centro escolar con código: " + codigoEntidad);
                } else {

                    if (VarSession.getDepartamentoUsuarioSession() != null) {
                        String dep = getRecuperarProceso().getDepartamento();
                        if (entidadEducativa.getCodigoDepartamento().getCodigoDepartamento().equals(dep) || (int) VarSession.getVariableSession("idTipoUsuario") == 1) {
                            oferta = ofertaBienesServiciosEJB.getOfertaByProcesoCodigoEntidad(codigoEntidad, detalleProceso);

                            if (oferta == null) {
                                JsfUtil.mensajeError("No existe un proceso de contratación para este centro escolar.");
                            } else {
                                List<Participantes> lst = oferta.getParticipantesList();

                                for (int i = lst.size() - 1; i >= 0; i--) {
                                    if (lst.get(i).getEstadoEliminacion().compareTo(BigInteger.ONE) == 0) {
                                        oferta.getParticipantesList().remove(lst.get(i));
                                    }
                                }

                                //BUSCAR REPRESENTANTE DEL ORGANISMO DE ADMINISTRACION ESCOLAR
                                representanteCe = entidadEducativaEJB.getPresidenteOrganismoEscolar(codigoEntidad);
                                if (representanteCe == null) {
                                    JsfUtil.mensajeInformacion("No esta registrado el representante del organismo de administración escolar, pero lo puede registrar aqui.");
                                    crearRepresentante = true;
                                    noEditableRepCe = false;
                                } else {
                                    crearRepresentante = false;
                                }

                                showFechaOrdenInicio = !detalleProceso.getIdRubroAdq().getDescripcionRubro().contains("UNIFORME");
                            }
                        } else {
                            JsfUtil.mensajeAlerta("El codigo del centro escolar no pertenece al departamento " + JsfUtil.getNombreDepartamentoByCodigo(dep) + "<br/>"
                                    + "Departamento del CE: " + entidadEducativa.getCodigoEntidad() + " es " + entidadEducativa.getCodigoDepartamento().getNombreDepartamento());
                        }
                    }
                }
            }
        } else {
            entidadEducativa = null;
        }
    }

    public void buscarDocumentoLegal() {
        if (idParticipante != null && idParticipante.compareTo(BigDecimal.ZERO) != 0) {
            continuar = true;
            participante = proveedorEJB.findParticipantesById(idParticipante);
            razonSocial = participante.getIdEmpresa().getRazonSocial();
            representanteLegal = proveedorEJB.getRespresentanteLegalEmp(participante.getIdEmpresa().getIdPersona().getIdPersona());
            resolucionAdj = resolucionAdjudicativaEJB.findResolucionesAdjudicativasByIdParticipante(idParticipante);

            if (resolucionAdj == null) {
                JsfUtil.mensajeAlerta("Este proveedor no posee adjudicaciones para este centro escolar.");
            } else {
                List<ContratosOrdenesCompras> lst = resolucionAdjudicativaEJB.findContratoByResolucion(resolucionAdj);

                if (lst.size() > 1) {
                    JsfUtil.mensajeError("Existe un problema con el contrato seleccionado, por favor reportarlo al administrador del sistema.");
                    resolucionAdjudicativaEJB.enviarCorreoDeError(resolucionAdj.getIdResolucionAdj());
                } else {
                    ContratosOrdenesCompras contratoOrd = null;

                    if (lst.size() == 1) {
                        contratoOrd = lst.get(0);
                    }

                    showGarantiaUsoTela = (rubro.intValue() == 1 || rubro.intValue() == 4 || rubro.intValue() == 5);
                    lstDocumentosImp = utilEJB.getLstDocumentosImp(showGarantiaUsoTela);
                    showFechaOrdenInicio = !showGarantiaUsoTela;

                    switch (estadoEdicion) {
                        case 1: //BUSCAR RESOLUCION ADJUDICATIVA
                            if (contratoOrd == null) {
                                if (resolucionAdj.getIdEstadoReserva().getIdEstadoReserva().compareTo(new BigDecimal("2")) == 0) {
                                    continuar = false;
                                    if (current != null) {
                                        if (representanteCe != null) {
                                            current.setAnhoContrato(resolucionAdj.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getAnho());
                                            setPlazoPrevistoEntrega();

                                            current.setMiembroFirma(representanteCe.getNombreMiembro());
                                        }
                                    }
                                } else {
                                    limpiarCampos();
                                    JsfUtil.mensajeAlerta("Esta reserva de fondo se encuentra en estado de " + resolucionAdj.getIdEstadoReserva().getDescripcionReserva()
                                            + ".<br/>Ver historico de cambios. <a onclick=\"PF('dlgHistorialCambiosReserva').show();\">Ver</a>");
                                    resolucionAdj = null;
                                }
                            } else {
                                JsfUtil.mensajeAlerta("Ya existe el contrato para el proveedor y centro escolar seleccionados.");
                            }
                            break;
                        case 2: //BUSCAR CONTRATO
                            if (contratoOrd == null) {
                                JsfUtil.mensajeAlerta("No hay contrato para el proveedor y centro escolar seleccionados.");
                                resolucionAdj = null;

                            } else {
                                if (resolucionAdj.getIdEstadoReserva().getIdEstadoReserva().compareTo(new BigDecimal("2")) == 0) {
                                    current = contratoOrd;
                                    continuar = false;
                                    if (current.getPlazoPrevistoEntrega() == null) {
                                        setPlazoPrevistoEntrega();
                                    }
                                } else {
                                    buscarHistorialCambios();
                                    PrimeFaces.current().ajax().update("dlgHistorialCambiosReserva");
                                    JsfUtil.mensajeAlerta("Esta reserva de fondo se encuentra en estado de " + resolucionAdj.getIdEstadoReserva().getDescripcionReserva()
                                            + ".<br/>Ver historico de cambios. <a onclick=\"PF('dlgHistorialCambiosReserva').show();\">Ver</a>");
                                    current = contratoOrd;
                                    continuar = true;
                                    deshabilitado = true;
                                }
                            }
                            break;
                    }
                }
            }
        }
    }

    private void setPlazoPrevistoEntrega() {
        switch (detalleProceso.getIdRubroAdq().getIdRubroInteres().intValue()) {
            case 1:
            case 4:
            case 5:
                current.setPlazoPrevistoEntrega(new BigInteger("60"));
                break;
            case 2:
                if (detalleProceso.getIdProcesoAdq().getDescripcionProcesoAdq().contains("MODALIDADES FLEXIBLES")) {
                    current.setPlazoPrevistoEntrega(new BigInteger("15"));

                } else {
                    current.setPlazoPrevistoEntrega(new BigInteger("30"));
                }
                break;
            case 3:
                current.setPlazoPrevistoEntrega(new BigInteger("60"));
                break;
        }
    }

    public List<JasperPrint> imprimirDesdeModificativa(List<RptDocumentos> lstRptDocumentos, Boolean perNatural, ContratosOrdenesCompras resolucionAdj, String codigoEnt) {
        representanteCe = entidadEducativaEJB.getPresidenteOrganismoEscolar(codigoEnt);
        current = resolucionAdj;
        this.resolucionAdj = resolucionAdj.getIdResolucionAdj();
        return imprimir(lstRptDocumentos, perNatural);
    }

    public List<JasperPrint> imprimir(List<RptDocumentos> lstRptDocumentos, Boolean perNatural) {
        String nombreRpt;
        Boolean sobredemanda;
        ServletContext ctx;
        JasperPrint rptTemp;
        HashMap param = new HashMap();
        List<JasperPrint> lstRptAImprimir = new ArrayList();

        ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        param.put("ubicacionImagenes", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
        param.put("pMiembro", current.getMiembroFirma());
        sobredemanda = detalleProceso.getIdProcesoAdq().getDescripcionProcesoAdq().contains("SOBREDEMANDA");

        for (RptDocumentos rptDoc : lstRptDocumentos) {

            param = JsfUtil.getNombreRubroRpt(detalleProceso.getIdRubroAdq().getIdRubroInteres().intValue(), param, sobredemanda);

            switch (rptDoc.getDs()) {
                case 0://JRBeanColletions
                    switch (rptDoc.getIdTipoRpt().getIdTipoRpt()) {
                        case 3://Acta Adjudicacion
                            if (detalleProceso.getIdProcesoAdq().getDescripcionProcesoAdq().contains("MODALIDADES FLEXIBLES")) {
                                param.put("descripcionRubro", "SUMINISTRO DE LIBROS DE MATEMÁTICA");
                            }

                            param.put("SUBREPORT_DIR", ctx.getRealPath(Reportes.PATH_REPORTES + "notasactas") + File.separator);
                            rptTemp = reportesEJB.getRpt(param, ContratosOrdenesComprasController.class.getClassLoader().getResourceAsStream(rptDoc.getNombreRpt() + ".jasper"), resolucionAdjudicativaEJB.generarRptActaAdjudicacion(current.getIdResolucionAdj().getIdResolucionAdj()));

                            lstRptAImprimir.add(rptTemp);
                            break;
                        case 4://Nota Adjudicacion
                            if (detalleProceso.getIdProcesoAdq().getDescripcionProcesoAdq().contains("MODALIDADES FLEXIBLES")) {
                                param.put("descripcionRubro", "SUMINISTRO DE LIBROS DE MATEMÁTICA");
                            }

                            param.put("SUBREPORT_DIR", ctx.getRealPath(Reportes.PATH_REPORTES + "notasactas") + File.separator);
                            rptTemp = reportesEJB.getRpt(param, ContratosOrdenesComprasController.class.getClassLoader().getResourceAsStream(rptDoc.getNombreRpt() + ".jasper"), resolucionAdjudicativaEJB.generarRptNotaAdjudicacion(current.getIdResolucionAdj().getIdResolucionAdj()));

                            lstRptAImprimir.add(rptTemp);
                            lstRptAImprimir.add(rptTemp);
                            break;
                        case 5://Garantia Cumplimiento
                        case 6://Garantia Uso Tela
                            if (detalleProceso.getIdProcesoAdq().getDescripcionProcesoAdq().contains("MODALIDADES FLEXIBLES")) {
                                param.put("descripcionRubro", "SUMINISTRO DE LIBROS DE MATEMÁTICA");
                            }

                            lstRptAImprimir.add(reportesEJB.getRpt(param, ContratosOrdenesComprasController.class.getClassLoader().getResourceAsStream(rptDoc.getNombreRpt() + ".jasper"), resolucionAdjudicativaEJB.generarRptGarantia(current.getIdResolucionAdj().getIdResolucionAdj(), current.getIdContrato())));
                            break;
                        case 7://Contrato
                            if (detalleProceso.getIdProcesoAdq().getDescripcionProcesoAdq().contains("MODALIDADES FLEXIBLES")) {
                                param.put("descripcionRubro", "SUMINISTRO DE LIBROS DE MATEMÁTICA");
                            }

                            param.put("SUBREPORT_DIR", ctx.getRealPath(Reportes.PATH_REPORTES + "contratos") + File.separator);
                            param.put("idContrato", current.getIdContrato());
                            param.put("ubicacionImagenes", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
                            param.put("telDirector", (representanteCe.getTelDirector() == null ? "" : representanteCe.getTelDirector()));
                            if (!getSelected().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdRubroAdq().getDescripcionRubro().contains("UNIFORME")) {
                                if (getSelected().getFechaOrdenInicio() != null) {
                                    param.put("P_FECHA_INICIO", Herramientas.getNumDia(getSelected().getFechaOrdenInicio()) + " días del mes de " + Herramientas.getNomMes(getSelected().getFechaOrdenInicio()) + " del año  " + Herramientas.getNumAnyo(getSelected().getFechaOrdenInicio()));
                                } else {
                                    param.put("P_FECHA_INICIO", "SIN DEFINIR");
                                }
                            }

                            nombreRpt = rptDoc.getNombreRpt().concat(perNatural ? "Nat" : "Jur");
                            rptTemp = reportesEJB.getRpt(param, ContratosOrdenesComprasController.class.getClassLoader().getResourceAsStream(nombreRpt + ".jasper"), resolucionAdjudicativaEJB.generarContrato(current.getIdContrato(), current.getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres()));
                            lstRptAImprimir.add(rptTemp);
                            lstRptAImprimir.add(rptTemp);
                            break;
                    }
                    break;
                case 1://DSConnection SQL
                    switch (rptDoc.getIdTipoRpt().getIdTipoRpt()) {
                        case 7://Contrato
                            Boolean bachillerato = false;
                            Boolean libros = false;

                            if (detalleProceso.getIdRubroAdq().getDescripcionRubro().contains("UNIFORME")) {
                                for (DetalleOfertas detalleOfertas : resolucionAdj.getIdParticipante().getDetalleOfertasList()) {
                                    if (detalleOfertas.getEstadoEliminacion().intValue() == 0) {
                                        if (detalleOfertas.getConsolidadoEspTec().contains("BACHILLERATO")) {
                                            bachillerato = true;
                                            break;
                                        }
                                    }
                                }
                            } else if (detalleProceso.getIdRubroAdq().getIdRubroInteres().intValue() == 2) {
                                for (DetalleOfertas detalleOfertas : resolucionAdj.getIdParticipante().getDetalleOfertasList()) {
                                    if (detalleOfertas.getEstadoEliminacion().intValue() == 0) {
                                        if (detalleOfertas.getConsolidadoEspTec().contains("Libro")) {
                                            libros = true;
                                            break;
                                        }
                                    }
                                }
                            }

                            //adición de aclaracion al contrato de 2do uniforme para el año 2019 
                            if (detalleProceso.getIdProcesoAdq().getIdAnho().getAnho().equals("2019")) {
                                if (detalleProceso.getIdDetProcesoAdq() == 41) {
                                    param.put("pAclaracion", RESOURCE_BUNDLE.getString("aclaracionContrato2019") + " ");
                                } else {
                                    param.put("pAclaracion", ", conforme a las cláusulas que a continuación se especifican. ");
                                }
                            }

                            param.put("SUBREPORT_DIR", ctx.getRealPath(Reportes.PATH_REPORTES + "contratos") + File.separator);
                            param.put("idContrato", current.getIdContrato());
                            param.put("ubicacionImagenes", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
                            param.put("telDirector", (representanteCe.getTelDirector() == null ? "" : representanteCe.getTelDirector()));
                            param.put("bachillerato", bachillerato);
                            param.put("libros", libros);
                            param.put("idResolucion", resolucionAdj.getIdResolucionAdj());
                            if (!getSelected().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdRubroAdq().getDescripcionRubro().contains("UNIFORME")) {
                                if (getSelected().getFechaOrdenInicio() != null) {
                                    param.put("P_FECHA_INICIO", Herramientas.getNumDia(getSelected().getFechaOrdenInicio()) + " días del mes de " + Herramientas.getNomMes(getSelected().getFechaOrdenInicio()) + " del año  " + Herramientas.getNumAnyo(getSelected().getFechaOrdenInicio()));
                                } else {
                                    param.put("P_FECHA_INICIO", "SIN DEFINIR");
                                }
                            }
                            if (detalleProceso.getIdProcesoAdq().getDescripcionProcesoAdq().contains("MODALIDADES FLEXIBLES")) {
                                param.put("descripcionRubro", "SUMINISTRO DE LIBROS DE MATEMÁTICA");
                            }
                            nombreRpt = rptDoc.getNombreRpt().concat(perNatural ? "Nat" : "Jur");
                            rptTemp = reportesEJB.getRpt(param, ContratosOrdenesComprasController.class.getClassLoader().getResourceAsStream(nombreRpt + ".jasper"));

                            lstRptAImprimir.add(rptTemp);
                            lstRptAImprimir.add(rptTemp);
                            break;
                    }
                    break;
            }
        }

        return lstRptAImprimir;
    }

    public void impDocumentos() {
        //List<Integer> lstRpt = new ArrayList();
        List<RptDocumentos> lstRptDocumentos;
        Boolean isPersonaNat;

        if (getSelected() != null && getSelected().getIdContrato() != null) {
            if (lstSelectDocumentosImp.isEmpty()) {
                JsfUtil.mensajeAlerta("Debe de seleccionar un documento para poder ser impreso.");
            } else {
                lstRptDocumentos = resolucionAdjudicativaEJB.getDocumentosAImprimir(detalleProceso.getIdDetProcesoAdq(), lstSelectDocumentosImp);

                if (lstRptDocumentos.isEmpty()) {
                    JsfUtil.mensajeAlerta("No se han definidos los documentos a imprimir para este proceso.");
                } else {
                    try {
                        isPersonaNat = (current.getIdResolucionAdj().getIdParticipante().getIdEmpresa().getIdPersoneria().getIdPersoneria().intValue() == 1);
                        Reportes.generarReporte(imprimir(lstRptDocumentos, isPersonaNat), "documentos_" + codigoEntidad);
                    } catch (Exception ex) {
                        Logger.getLogger(ContratosOrdenesCompras.class.getName()).log(Level.SEVERE, "id_resolucion: " + current, ex);
                    }
                }
            }
        } else {
            JsfUtil.mensajeAlerta("Primero debe de guardar el contrato antes de imprimirlo");
        }
    }

    public void imprimirAnalisisEconomico() {
        if (current.getIdContrato() != null) {
            OfertaBienesServicios ofe = getSelected().getIdResolucionAdj().getIdParticipante().getIdOferta();
            if (ofe == null) {
                JsfUtil.mensajeAlerta("Primero debe de guardar la oferta!!!");
            } else {
                SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
                List lst = ofertaBienesServiciosEJB.getDatosRptAnalisisEconomico(ofe.getCodigoEntidad().getCodigoEntidad(), ofe.getIdDetProcesoAdq());
                Bean2Excel oReport = new Bean2Excel(lst, detalleProceso.getIdRubroAdq().getDescripcionRubro(), entidadEducativa.getNombre(), entidadEducativa.getCodigoEntidad(), "", sd.format(ofe.getFechaApertura()), getSelected().getUsuarioInsercion());
                oReport.createFile(ofe.getCodigoEntidad().getCodigoEntidad());
            }
        } else {
            JsfUtil.mensajeAlerta("Primero debe de guardar la oferta!!!");
        }
    }

    public Boolean getFiltroCE() {
        return filtroCE;
    }

    public void setFiltroCE(Boolean filtroCE) {
        this.filtroCE = filtroCE;
    }

    public Boolean getCambiarCiudadFirma() {
        return cambiarCiudadFirma;
    }

    public void setCambiarCiudadFirma(Boolean cambiarCiudadFirma) {
        this.cambiarCiudadFirma = cambiarCiudadFirma;
    }

    public Boolean getCambiarRepreCe() {
        return cambiarRepreCe;
    }

    public void setCambiarRepreCe(Boolean cambiarRepreCe) {
        this.cambiarRepreCe = cambiarRepreCe;
    }

    public Boolean getActaAdj() {
        return actaAdj;
    }

    public void setActaAdj(Boolean actaAdj) {
        this.actaAdj = actaAdj;
    }

    public Boolean getNotaAdj() {
        return notaAdj;
    }

    public void setNotaAdj(Boolean notaAdj) {
        this.notaAdj = notaAdj;
    }

    public Boolean getContrato() {
        return contrato;
    }

    public void setContrato(Boolean contrato) {
        this.contrato = contrato;
    }

    public Boolean getGarantiaContrato() {
        return garantiaContrato;
    }

    public void setGarantiaContrato(Boolean garantiaContrato) {
        this.garantiaContrato = garantiaContrato;
    }

    public Boolean getGarantiaAnticipo() {
        return garantiaAnticipo;
    }

    public void setGarantiaAnticipo(Boolean garantiaAnticipo) {
        this.garantiaAnticipo = garantiaAnticipo;
    }

    public Boolean getGarantiaUsoTela() {
        return garantiaUsoTela;
    }

    public void setGarantiaUsoTela(Boolean garantiaUsoTela) {
        this.garantiaUsoTela = garantiaUsoTela;
    }

    public DetalleProcesoAdq getDetalleProceso() {
        return detalleProceso;
    }

    public void setDetalleProceso(DetalleProcesoAdq detalleProceso) {
        this.detalleProceso = detalleProceso;
    }

    public int getTipoRpt() {
        return tipoRpt;
    }

    public void setTipoRpt(int tipoRpt) {
        this.tipoRpt = tipoRpt;
        VarSession.setVariableSession("tipoRpt", tipoRpt);
    }

    public Boolean getShowGarantiaUsoTela() {
        return showGarantiaUsoTela;
    }

    public void setShowGarantiaUsoTela(Boolean showGarantiaUsoTela) {
        this.showGarantiaUsoTela = showGarantiaUsoTela;
    }

    public List<SelectItem> getLstDocumentosImp() {
        return lstDocumentosImp;
    }

    public List<Integer> getLstSelectDocumentosImp() {
        return lstSelectDocumentosImp;
    }

    public void setLstSelectDocumentosImp(List<Integer> lstSelectDocumentosImp) {
        this.lstSelectDocumentosImp = lstSelectDocumentosImp;
    }

    public void buscarHistorialCambios() {
        lstHistorialCambios = resolucionAdjudicativaEJB.findHistorialByIdResolucionAdj(resolucionAdj.getIdResolucionAdj());
    }
}
