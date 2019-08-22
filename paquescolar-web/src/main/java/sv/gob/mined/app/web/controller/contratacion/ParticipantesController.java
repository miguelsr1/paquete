/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.contratacion;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.commons.lang3.math.NumberUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.ResolucionAdjudicativaEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.CatalogoProducto;
import sv.gob.mined.paquescolar.model.DetalleOfertas;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.EstadoReserva;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.Participantes;
import sv.gob.mined.paquescolar.model.PreciosRefRubroEmp;
import sv.gob.mined.paquescolar.model.ResolucionesAdjudicativas;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class ParticipantesController implements Serializable {

    private int rowEdit = 0;
    private int estadoEdicion = 0;
    private String url;
    private String numItem;
    private String codigoEntidad;
    private String msjError = "";
    private Boolean showEstilo = false;
    private Boolean deshabilitado = true;
    private Boolean mostrarMsj = false;
    private Boolean modifDesac = true;
    private Boolean mostraTblLibros = false;
    private BigDecimal idParticipante = BigDecimal.ZERO;
    private CatalogoProducto item;
    private DetalleProcesoAdq detalleProceso = new DetalleProcesoAdq();
    private NivelEducativo nivel;
    private Participantes current = new Participantes();
    private DetalleOfertas detalleSeleccionado;
    private SelectItem[] lstEstilos = new SelectItem[0];
    private List<DetalleOfertas> lstDetalleOferta = new ArrayList();
    private List<DetalleOfertas> lstDetalleOfertaLibros = new ArrayList();
    private List<PreciosRefRubroEmp> lstPreciosEmp = new ArrayList<>();
    private List<BigDecimal> lstNiveles = new ArrayList();
    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private ResolucionAdjudicativaEJB resolucionAdjudicativaEJB;
    @EJB
    private UtilEJB utilEJB;
    @EJB
    private EntidadEducativaEJB entidadEducativaEJB;

    /**
     * Creates a new instance of ParticipantesController
     */
    public ParticipantesController() {
    }

    @PostConstruct
    public void init() {
        url = "";

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("txtCodigoEntidad")) {
            if (params.containsKey("idParticipante")) {
                VarSession.setVariableSessionED("2");
                deshabilitado = false;
                if (current.getIdParticipante() == null) {
                    current = utilEJB.find(Participantes.class, new BigDecimal(params.get("idParticipante")));
                }
                idParticipante = current.getIdParticipante();
                codigoEntidad = current.getIdOferta().getCodigoEntidad().getCodigoEntidad();
                buscarItemsProveedor();
            }
        } else {
            VarSession.setVariableSessionED("1");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getter-setter">
    public Boolean getMostraTblLibros() {
        return mostraTblLibros;
    }

    public void setMostraTblLibros(Boolean mostraTblLibros) {
        this.mostraTblLibros = mostraTblLibros;
    }

    public BigDecimal getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(BigDecimal idParticipante) {
        this.idParticipante = idParticipante;
    }

    public int getRowEdit() {
        return rowEdit;
    }

    public void setRowEdit(int rowEdit) {
        this.rowEdit = rowEdit;
    }

    public int getEstadoEdicion() {
        return estadoEdicion;
    }

    public void setEstadoEdicion(int estadoEdicion) {
        this.estadoEdicion = estadoEdicion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumItem() {
        return numItem;
    }

    public void setNumItem(String numItem) {
        this.numItem = numItem;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public Boolean getDeshabilitado() {
        return deshabilitado;
    }

    public void setDeshabilitado(Boolean deshabilitado) {
        this.deshabilitado = deshabilitado;
    }

    public Boolean getMostrarMsj() {
        return mostrarMsj;
    }

    public void setMostrarMsj(Boolean mostrarMsj) {
        this.mostrarMsj = mostrarMsj;
    }

    public boolean isModifDesac() {
        return modifDesac;
    }

    public void setModifDesac(boolean modifDesac) {
        this.modifDesac = modifDesac;
    }

    public SelectItem[] getLstEstilos() {
        return lstEstilos;
    }

    public void setSelected(Participantes participante) {
        if (participante != null && current != null) {
            if (current.equals(participante)) {
            } else {
                current = participante;
            }
        }
    }

    public Participantes getSelected() {
        if (current == null) {
            current = new Participantes();
        }
        return current;
    }

    public DetalleOfertas getDetalleSeleccionado() {
        return detalleSeleccionado;
    }

    public void setDetalleSeleccionado(DetalleOfertas detalleSeleccionado) {
        this.detalleSeleccionado = detalleSeleccionado;
    }

    public CatalogoProducto getItem() {
        return item;
    }

    public void setItem(CatalogoProducto item) {
        this.item = item;
    }

    public NivelEducativo getNivel() {
        return nivel;
    }

    public void setNivel(NivelEducativo nivel) {
        this.nivel = nivel;
    }

    public List<DetalleOfertas> getLstDetalleOferta() {
        return lstDetalleOferta;
    }

    public void setLstDetalleOferta(List<DetalleOfertas> lstDetalleOferta) {
        this.lstDetalleOferta = lstDetalleOferta;
    }

    public List<DetalleOfertas> getLstDetalleOfertaLibros() {
        return lstDetalleOfertaLibros;
    }

    public void setLstDetalleOfertaLibros(List<DetalleOfertas> lstDetalleOfertaLibros) {
        this.lstDetalleOfertaLibros = lstDetalleOfertaLibros;
    }

    public Boolean getShowEstilo() {
        return showEstilo;
    }

    // </editor-fold>
    public String prepareEdit() {
        setDeshabilitado(false);
        VarSession.removeVariableSession("codigoEntidad");
        VarSession.setVariableSessionED("2");
        current = new Participantes();
        estadoEdicion = 2;
        mostrarMsj = false;
        mostraTblLibros = false;
        lstDetalleOferta.clear();
        lstDetalleOfertaLibros.clear();
        return "";
    }

    public String update() {
        String urlRed = "reg02ReservaFondos.mined?includeViewParams=true&codigoEntidad=" + codigoEntidad + "&idParticipante=" + idParticipante;
        Boolean isError, isUtiles;
        try {
            isUtiles = (detalleProceso.getIdRubroAdq().getIdRubroInteres().intValue() == 2);
            if (isUtiles) {
                isError = lstDetalleOferta.isEmpty() && lstDetalleOfertaLibros.isEmpty();
            } else {
                isError = (lstDetalleOferta == null || lstDetalleOfertaLibros == null);
            }

            if (isError) {
                JsfUtil.mensajeAlerta("Debe de agregar al menos un detalle a la oferta."
                        + (isUtiles ? "\nDebe de agregar al menos un detalle a la oferta de libros" : ""));
                return "";
            }

            for (DetalleOfertas det : lstDetalleOferta) {
                if (det.getEstadoEliminacion().compareTo(BigInteger.ONE) == 0) {
                    det.setUsuarioModificacion(VarSession.getVariableSessionUsuario());
                    det.setFechaEliminacion(new Date());
                } else {
                    if (det.getCantidad().compareTo(BigInteger.ZERO) == 0) {
                        JsfUtil.mensajeAlerta("Al menos un detalle de la oferta tiene cantidad de ITEMS con valor de CERO.");
                        return "";
                    }

                    if (det.getPrecioUnitario().compareTo(BigDecimal.ZERO) == 0) {
                        JsfUtil.mensajeAlerta("Al menos un detalle de la oferta tiene precio unitario de con valor de CERO.");
                        return "";
                    }
                }
            }
            for (DetalleOfertas det : lstDetalleOfertaLibros) {
                if (det.getEstadoEliminacion().compareTo(BigInteger.ONE) == 0) {
                    det.setUsuarioModificacion(VarSession.getVariableSessionUsuario());
                    det.setFechaEliminacion(new Date());
                } else {
                    if (det.getCantidad().compareTo(BigInteger.ZERO) == 0) {
                        JsfUtil.mensajeAlerta("Al menos un libro de la oferta tiene cantidad de ITEMS con valor de CERO.");
                        return "";
                    }

                    if (det.getPrecioUnitario().compareTo(BigDecimal.ZERO) == 0) {
                        JsfUtil.mensajeAlerta("Al menos un libro de la oferta tiene precio unitario de con valor de CERO.");
                        return "";
                    }
                }
            }

            ResolucionesAdjudicativas res = resolucionAdjudicativaEJB.findResolucionesAdjudicativasByIdParticipante(idParticipante);
            if (res == null) {
                res = new ResolucionesAdjudicativas();
                res.setIdEstadoReserva(utilEJB.find(EstadoReserva.class, BigDecimal.ONE));
                res.setIdParticipante(current);
                res.setValor(getMontoAdjudicado(false).add(getMontoAdjudicado(true)));
                res.setUsuarioInsercion(VarSession.getVariableSessionUsuario());

                resolucionAdjudicativaEJB.create(res);
            } else {
                switch (res.getIdEstadoReserva().getIdEstadoReserva().intValue()) {
                    case 1://digitacion
                    case 3://revertida
                        res.setValor(getMontoAdjudicado(false).add(getMontoAdjudicado(true)));
                        resolucionAdjudicativaEJB.edit(res);
                        break;
                    case 2:
                        JsfUtil.mensajeError("Reserva de fondos APLICADA. Primero debe revertir la reserva para aplicar estos cambios.");
                        urlRed = null;
                        break;
                    case 4:
                    case 5:
                        JsfUtil.mensajeError("La reserva de fondos se encuentra ANULADA/MODIFICADA, No se pueden aplicar cambios.");
                        urlRed = null;
                        break;
                }
            }

            if (urlRed != null) {
                proveedorEJB.guardarDetalleOferta(lstDetalleOferta);
                if (!lstDetalleOfertaLibros.isEmpty()) {
                    proveedorEJB.guardarDetalleOferta(lstDetalleOfertaLibros);
                }
                return urlRed;
            }

            return "";
        } catch (Exception e) {
            JsfUtil.mensajeError("Error en el registro del detalle de la oferta.");
            return "";
        }
    }

    private BigDecimal getMontoAdjudicado(Boolean libros) {
        BigDecimal total = BigDecimal.ZERO;
        List<DetalleOfertas> tmplista = libros ? getLstDetalleOfertaLibros() : getLstDetalleOferta();
        if (tmplista != null) {
            for (DetalleOfertas detalle : tmplista) {
                if (detalle.getEstadoEliminacion().compareTo(BigInteger.ZERO) == 0) {
                    if (detalle.getCantidad() != null && detalle.getPrecioUnitario() != null) {
                        total = total.add(new BigDecimal(detalle.getCantidad()).multiply(detalle.getPrecioUnitario()));
                    }
                }
            }
        }
        return total;
    }

    private BigInteger getCantidadAdjudicado(Boolean libros) {
        Boolean error = false;
        List<DetalleOfertas> tmplista = libros ? getLstDetalleOfertaLibros() : getLstDetalleOferta();
        BigInteger total = BigInteger.ZERO;
        if (tmplista != null) {
            for (DetalleOfertas detalle : tmplista) {
                if (detalle.getEstadoEliminacion().compareTo(BigInteger.ZERO) == 0) {
                    if (detalle.getCantidad() != null) {
                        if (detalle.getIdProducto() != null && detalle.getIdProducto().getIdProducto().intValue() != 1) {
                            total = total.add(detalle.getCantidad());
                        } else {
                            total = total.add(detalle.getCantidad());
                        }
                    } else {
                        error = true;
                    }
                }
            }
        }
        if (error) {
            JsfUtil.mensajeAlerta("Exite un detalle de oferta con cantidad no valida!");
        }
        return total;
    }

    public void buscarItemsProveedor() {
        mostrarMsj = false;
        showEstilo = false;
        mostraTblLibros = false;
        lstEstilos = new SelectItem[0];
        lstDetalleOferta.clear();
        if (idParticipante != null && idParticipante.compareTo(BigDecimal.ZERO) != 0) {
            current = utilEJB.find(Participantes.class, idParticipante);
            detalleProceso = current.getIdOferta().getIdDetProcesoAdq();

            //verificar si el proveedor seleccionado posee precios de referencia
            if (proveedorEJB.isPrecioRef(current.getIdEmpresa(), current.getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getAnho())) {
                //cargar estilos, unicamente si el rubro es zapatos
                if (detalleProceso.getIdRubroAdq().getIdRubroInteres().compareTo(new BigDecimal(3)) == 0) {
                    File carpetaNfs = new File("/imagenes/PaqueteEscolar/Fotos_Zapatos/" + current.getIdEmpresa().getNumeroNit() + "/");

                    if (carpetaNfs.list() != null) {
                        showEstilo = true;
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

                //cargar detalle de contratación
                lstDetalleOferta = proveedorEJB.findDetalleOfertas(current, false);

                if (detalleProceso.getIdRubroAdq().getIdRubroInteres().intValue() == 2) {
                    if (detalleProceso.getIdProcesoAdq().getIdAnho().getIdAnho().intValue() > 5) {
                        mostraTblLibros = true;
                        lstDetalleOfertaLibros = proveedorEJB.findDetalleOfertas(current, true);
                    }
                }

                //verificar el estado de la resersolucion adjudicativa
                ResolucionesAdjudicativas res = resolucionAdjudicativaEJB.findResolucionesAdjudicativasByIdParticipante(idParticipante);
                int idResolucion;

                if (res == null) {
                    idResolucion = 1;
                } else {
                    idResolucion = res.getIdEstadoReserva().getIdEstadoReserva().intValue();
                }

                switch (idResolucion) {
                    case 1://digitacion
                    case 3://revertida
                        lstPreciosEmp = proveedorEJB.findPreciosRefRubroEmpRubro(current.getIdEmpresa(), detalleProceso);
                        lstNiveles = entidadEducativaEJB.getLstNivelesConMatriculaReportadaByIdProcesoAdqAndCodigoEntidad(detalleProceso.getIdProcesoAdq().getIdProcesoAdq(), current.getIdOferta().getCodigoEntidad().getCodigoEntidad());

                        //en el momento de creación del detalle de oferta, se agregaran todos los items calificados del proveedor
                        //seleccionado con el objetivo de facilitar el ingreso de esta información
                        if (lstDetalleOferta.isEmpty()) {
                            for (PreciosRefRubroEmp preRefEmp : lstPreciosEmp) {
                                if (preRefEmp.getIdProducto().getIdProducto().intValue() != 1) {
                                    for (BigDecimal idNivel : lstNiveles) {
                                        BigDecimal temIdNivel = BigDecimal.ZERO;
                                        if(detalleProceso.getIdRubroAdq().getIdRubroUniforme().intValue() == 1){ //rubro uniforme
                                            switch (idNivel.intValue()) {
                                            case 1:
                                            case 6:
                                            case 16:
                                            case 18:
                                                temIdNivel = idNivel;
                                                break;
                                            case 3://primer ciclo
                                            case 4://segundo ciclo
                                            case 5://tercer ciclo
                                            case 7://7o grado
                                            case 8://8o grado
                                            case 9://9o grado
                                            case 10://1o grado
                                            case 11://2o grado
                                            case 12://3o grado
                                            case 13://4o grado
                                            case 14://5o grado
                                            case 15://6o grado
                                                temIdNivel = new BigDecimal(2);
                                                break;
                                        }
                                        }else{//rubro de utiles o zapatos
                                            temIdNivel = idNivel;
                                        }

                                        if (preRefEmp.getIdNivelEducativo().getIdNivelEducativo().compareTo(temIdNivel) == 0) {
                                            DetalleOfertas det = new DetalleOfertas();
                                            det.setNoItem(preRefEmp.getNoItem());
                                            det.setIdNivelEducativo(preRefEmp.getIdNivelEducativo());
                                            det.setIdProducto(preRefEmp.getIdProducto());
                                            det.setConsolidadoEspTec(preRefEmp.getIdProducto().toString() + ", " + preRefEmp.getIdNivelEducativo().toString());
                                            det.setCantidad(BigInteger.ZERO);
                                            det.setPrecioUnitario(preRefEmp.getPrecioReferencia());
                                            det.setEstadoEliminacion(BigInteger.ZERO);
                                            det.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                                            det.setFechaInsercion(new Date());
                                            det.setModificativa(BigInteger.ZERO);
                                            det.setIdParticipante(current);

                                            lstDetalleOferta.add(det);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (lstDetalleOfertaLibros.isEmpty()) {
                            for (PreciosRefRubroEmp preRefEmp : lstPreciosEmp) {
                                if (preRefEmp.getIdProducto().getIdProducto().intValue() == 1) {
                                    for (BigDecimal idNivel : lstNiveles) {
                                        if (preRefEmp.getIdNivelEducativo().getIdNivelEducativo().compareTo(idNivel) == 0) {
                                            DetalleOfertas det = new DetalleOfertas();
                                            det.setNoItem(preRefEmp.getNoItem());
                                            det.setIdNivelEducativo(preRefEmp.getIdNivelEducativo());
                                            det.setIdProducto(preRefEmp.getIdProducto());
                                            det.setConsolidadoEspTec(preRefEmp.getIdProducto().toString() + ", " + preRefEmp.getIdNivelEducativo().toString());
                                            det.setCantidad(BigInteger.ZERO);
                                            det.setPrecioUnitario(preRefEmp.getPrecioReferencia());
                                            det.setEstadoEliminacion(BigInteger.ZERO);
                                            det.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                                            det.setFechaInsercion(new Date());
                                            det.setModificativa(BigInteger.ZERO);
                                            det.setIdParticipante(current);

                                            lstDetalleOfertaLibros.add(det);
                                        }
                                    }
                                }
                            }
                        }

                        modifDesac = false;
                        break;
                    case 2:
                        JsfUtil.mensajeInformacion("Reserva de fondos APLICADA. Primero debe REVERTIR la reserva para realizar cambios.");
                        url = null;
                        modifDesac = true;
                        break;
                    case 4:
                    case 5:
                        JsfUtil.mensajeInformacion("La reserva de fondos se encuentra ANULADA/MODIFICADA, No se pueden realizar cambios.");
                        url = null;
                        modifDesac = true;
                        break;
                }
            } else {
                //bandera para monstrar mensaje que el proveedor no tiene precios de referencia ingresados
                mostrarMsj = true;
            }
        } else {
            lstDetalleOferta.clear();
        }
    }

    public void prepareNewDetalle() {
        if (current != null) {
            DetalleOfertas det = new DetalleOfertas();
            det.setConsolidadoEspTec("");
            det.setCantidad(BigInteger.ZERO);
            det.setPrecioUnitario(BigDecimal.ZERO);
            det.setEstadoEliminacion(BigInteger.ZERO);
            det.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
            det.setFechaInsercion(new Date());
            det.setIdParticipante(current);
            det.setModificativa(BigInteger.ZERO);

            lstDetalleOferta.add(det);
        } else {
            JsfUtil.mensajeAlerta("Debe seleccionar un proveedor.");
        }
    }

    public void prepareNewDetalleLibro() {
        if (current != null) {
            DetalleOfertas det = new DetalleOfertas();
            det.setConsolidadoEspTec("");
            det.setCantidad(BigInteger.ZERO);
            det.setPrecioUnitario(BigDecimal.ZERO);
            det.setEstadoEliminacion(BigInteger.ZERO);
            det.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
            det.setFechaInsercion(new Date());
            det.setIdParticipante(current);
            det.setModificativa(BigInteger.ZERO);

            lstDetalleOfertaLibros.add(det);
        } else {
            JsfUtil.mensajeAlerta("Debe seleccionar un proveedor.");
        }
    }

    public void eliminarDetalle(Boolean libro) {
        if (detalleSeleccionado != null) {
            if (detalleSeleccionado.getEstadoEliminacion().compareTo(BigInteger.ZERO) == 0) {
                if (detalleSeleccionado.getIdDetalleOfe() != null) {
                    detalleSeleccionado.setEstadoEliminacion(BigInteger.ONE);
                } else {
                    if (libro) {
                        lstDetalleOfertaLibros.remove(rowEdit);
                    } else {
                        lstDetalleOferta.remove(rowEdit);
                    }
                }
            } else {
                detalleSeleccionado.setEstadoEliminacion(BigInteger.ZERO);
            }

            detalleSeleccionado = null;
        } else {
            JsfUtil.mensajeAlerta("Debe seleccionar un detalle para poder eliminarlo.");
        }
    }

    public void onCellEdit(CellEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        DetalleOfertas det = context.getApplication().evaluateExpressionGet(context, "#{detalle}", DetalleOfertas.class);
        if (det != null) {
            edicionCellItem(det, event, false);
        }
    }

    public void onCellEditLibros(CellEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        DetalleOfertas det = context.getApplication().evaluateExpressionGet(context, "#{detalle}", DetalleOfertas.class);
        edicionCellItem(det, event, true);
    }

    private void edicionCellItem(DetalleOfertas det, CellEditEvent event, Boolean libros) {
        if (event.getNewValue() != null) {
            numItem = event.getNewValue().toString();
            if (NumberUtils.isNumber(numItem.trim())) {
                nivel = null;
                item = null;

                if (det.getEstadoEliminacion().compareTo(BigInteger.ONE) == 0) {
                    JsfUtil.mensajeError("El detalle seleccionado no se puede modificar.");
                } else {
                    if (event.getColumn().getColumnKey().contains("item")) {
                        numItem = event.getNewValue().toString();
                        rowEdit = event.getRowIndex();
                        editarNumeroDeItem(det, event.getRowIndex(), libros);
                        det.setUsuarioModificacion(VarSession.getVariableSessionUsuario());
                        det.setFechaModificacion(new Date());
                    }
                }
            } else {
                limpiarDetalleOferta(det);
                msjError = "Debe de ingresar un número válido.";
            }
        } else {
            limpiarDetalleOferta(det);
            msjError = "Debe de ingresar un número válido.";
        }
    }

    public String getMontoActual() {
        String montoActual;
        montoActual = JsfUtil.getFormatoNum(getMontoAdjudicado(false), false);
        return montoActual;
    }

    public String getMontoActualLibros() {
        String montoActual;
        montoActual = JsfUtil.getFormatoNum(getMontoAdjudicado(true), false);
        return montoActual;
    }

    public String getCantidadActual() {
        String montoActual;
        montoActual = JsfUtil.getFormatoNum(getCantidadAdjudicado(false), true);
        return montoActual;
    }

    public String getCantidadActualLibros() {
        String montoActual;
        montoActual = JsfUtil.getFormatoNum(getCantidadAdjudicado(true), true);
        return montoActual;
    }

    public void updateFilaDetalle(String nombreTabla) {
        int numRow = nombreTabla.equals("tblDetalleOferta") ? lstDetalleOferta.size() : lstDetalleOfertaLibros.size();
        if (numRow > 1) {
            numRow--;
        }
        PrimeFaces.current().ajax().update(nombreTabla + ":" + rowEdit + ":descripcionItem");
        PrimeFaces.current().ajax().update(nombreTabla + ":" + rowEdit + ":precioUnitario");
        PrimeFaces.current().ajax().update(nombreTabla + ":" + rowEdit + ":subTotal");
        PrimeFaces.current().ajax().update(nombreTabla + ":" + numRow + ":totalCantidad");
        PrimeFaces.current().ajax().update(nombreTabla + ":" + numRow + ":total");
        if (!msjError.isEmpty()) {
            JsfUtil.mensajeAlerta(msjError);
        }
    }

    private void editarNumeroDeItem(DetalleOfertas det, int rowEdit, Boolean libros) {
        Boolean error = true;
        Boolean isNivel = true;
        msjError = "";

        for (PreciosRefRubroEmp precio : lstPreciosEmp) {
            if (precio.getNoItem().equals(numItem)) {
                item = precio.getIdProducto();
                nivel = precio.getIdNivelEducativo();

                for (BigDecimal idNivel : lstNiveles) {
                    if (nivel.getIdNivelEducativo().compareTo(idNivel) == 0) {
                        isNivel = false;
                        break;
                    }
                }

                error = isNivel;
                break;
            }
        }

        if (error) {
            msjError = "El item ingresado no es válido.";
            if (isNivel) {
                msjError += "<br/>No se ha ingresado estadisticas en este nivel educativo.";
            }
            det.setConsolidadoEspTec("");
        } else {
            if (item != null && nivel != null && !validarItemDuplicado(det, rowEdit, libros)) {
                det.setConsolidadoEspTec(item.toString() + ", " + nivel.toString());
                det.setIdProducto(item);
                det.setIdNivelEducativo(nivel);
                PreciosRefRubroEmp precio = proveedorEJB.getPrecioRef(current.getIdEmpresa(), nivel.getIdNivelEducativo(), item.getIdProducto(), current.getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getAnho());
                if (precio != null) {
                    det.setPrecioUnitario(precio.getPrecioReferencia());
                } else {
                    msjError = "El proveedor seleccionado no posee precios de referencia para el producto y nivel educativo seleccionado.";
                    limpiarDetalleOferta(det);
                }
            } else {
                limpiarDetalleOferta(det);
            }
        }
    }

    private void limpiarDetalleOferta(DetalleOfertas det) {
        det.setNoItem("");
        det.setConsolidadoEspTec("");
        det.setPrecioUnitario(BigDecimal.ZERO);
        det.setCantidad(BigInteger.ZERO);
    }

    private boolean validarItemDuplicado(DetalleOfertas detalleNew, int rowEdit, Boolean libros) {
        List<DetalleOfertas> tmplista = libros ? getLstDetalleOfertaLibros() : getLstDetalleOferta();

        for (int i = 0; i < tmplista.size(); i++) {
            if (detalleNew.getIdDetalleOfe() != null && tmplista.get(i).getIdDetalleOfe() != null && tmplista.get(i).getIdDetalleOfe().compareTo(detalleNew.getIdDetalleOfe()) == 0 && i != rowEdit) {
            } else if (tmplista.get(i).getNoItem() != null && tmplista.get(i).getNoItem().equals(detalleNew.getNoItem()) && i != rowEdit) {
                msjError = "Este item ya fue agregado!";
                return true;
            }
        }
        return false;
    }
}
