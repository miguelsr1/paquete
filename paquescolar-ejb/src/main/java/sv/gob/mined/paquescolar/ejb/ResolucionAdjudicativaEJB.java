/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
import sv.gob.mined.paquescolar.model.DetalleModificativa;
import sv.gob.mined.paquescolar.model.DetalleOfertas;
import sv.gob.mined.paquescolar.model.EstadoReserva;
import sv.gob.mined.paquescolar.model.HistorialCamEstResAdj;
import sv.gob.mined.paquescolar.model.InfoGeneralContratacion;
import sv.gob.mined.paquescolar.model.Liquidacion;
import sv.gob.mined.paquescolar.model.Participantes;
import sv.gob.mined.paquescolar.model.ResolucionesAdjudicativas;
import sv.gob.mined.paquescolar.model.ResolucionesModificativas;
import sv.gob.mined.paquescolar.model.RptDocumentos;
import sv.gob.mined.paquescolar.model.TechoRubroEntEdu;
import sv.gob.mined.paquescolar.model.pojos.contratacion.SaldoProveedorDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ContratoDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.DetalleItemDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ParticipanteConContratoDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ParticipanteDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.VwRptContratoJurCabecera;
import sv.gob.mined.paquescolar.model.pojos.contratacion.VwRptPagare;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.DatosContratoDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.DatosModificativaDto;
import sv.gob.mined.paquescolar.model.pojos.liquidacion.DatosRecepcionDto;

/**
 *
 * @author misanchez
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ResolucionAdjudicativaEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;
    @EJB
    ProveedorEJB proveedorEJB;
    @EJB
    EMailEJB eMailEJB;
    @EJB
    SaldosEJB saldosEJB;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public ResolucionesAdjudicativas findResolucionesAdjudicativasByIdParticipante(BigDecimal participante) {
        Query q = em.createQuery("SELECT r FROM ResolucionesAdjudicativas r WHERE r.idParticipante.idParticipante=:participante and r.estadoEliminacion=0", ResolucionesAdjudicativas.class);
        q.setParameter("participante", participante);
        List<ResolucionesAdjudicativas> lst = q.getResultList();
        if (lst.isEmpty()) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    public ResolucionesAdjudicativas findResolucionesAdjudicativasByPk(BigDecimal idResolucion) {
        Query q = em.createQuery("SELECT r FROM ResolucionesAdjudicativas r WHERE r.idParticipante.estadoEliminacion=0 and r.idResolucionAdj=:idResolucion", ResolucionesAdjudicativas.class);
        q.setParameter("idResolucion", idResolucion);
        List<ResolucionesAdjudicativas> lst = q.getResultList();
        if (lst.isEmpty()) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    public void create(ResolucionesAdjudicativas resolucionesAdjudicativas) {
        if (resolucionesAdjudicativas.getContratosOrdenesComprasList() == null) {
            resolucionesAdjudicativas.setContratosOrdenesComprasList(new ArrayList());
        }
        try {
            resolucionesAdjudicativas.setEstadoEliminacion(BigInteger.ZERO);
            resolucionesAdjudicativas.setFechaInsercion(new Date());
            em.persist(resolucionesAdjudicativas);
        } catch (Exception ex) {
            Logger.getLogger(ResolucionAdjudicativaEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResolucionesAdjudicativas edit(ResolucionesAdjudicativas resolucionesAdjudicativas) {
        try {
            resolucionesAdjudicativas.setFechaModificacion(new Date());
            resolucionesAdjudicativas = em.merge(resolucionesAdjudicativas);
            return resolucionesAdjudicativas;
        } catch (Exception ex) {
            Logger.getLogger(ResolucionAdjudicativaEJB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Boolean validarCambioEstado(ResolucionesAdjudicativas resAdj, BigDecimal estadoReserva) {
        Boolean resultado = false;
        if ((resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 1 && estadoReserva.intValue() == 2) || (resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 3 && estadoReserva.intValue() == 2)) {
            resultado = true;
        } else //DIGITADA -> ANULADA y REVERTIDA -> ANULADA 
        if ((resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 1 && estadoReserva.intValue() == 4) || (resAdj.getIdResolucionAdj().intValue() == 3 && estadoReserva.intValue() == 4)) {
            resultado = true;
        } else //APLICADA -> REVERTIDA
        if (resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 2 && estadoReserva.intValue() == 3) {
            resultado = true;
        }
        return resultado;
    }

    public List<EstadoReserva> findEstadoReservaEntities() {
        Query q = em.createQuery("SELECT e FROM EstadoReserva e", EstadoReserva.class);
        return q.getResultList();
    }

    public List<ContratosOrdenesCompras> findContratoByResolucion(ResolucionesAdjudicativas resolucion) {
        Query q = em.createQuery("SELECT c FROM ContratosOrdenesCompras c WHERE c.estadoEliminacion = 0 and c.idResolucionAdj=:resolucion", ContratosOrdenesCompras.class);
        q.setParameter("resolucion", resolucion);
        return q.getResultList();
    }

    public ContratosOrdenesCompras createContrato(ContratosOrdenesCompras contratosOrdenesCompras) {
        String numeroContrato = getNumContrato(contratosOrdenesCompras.getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoEntidad(), contratosOrdenesCompras.getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getIdAnho().intValue());
        contratosOrdenesCompras.setNumeroContrato(numeroContrato.length() == 1 ? "0" + numeroContrato : numeroContrato);
        em.persist(contratosOrdenesCompras);

        InfoGeneralContratacion info = new InfoGeneralContratacion();

        info.setCantidadContrato(contratosOrdenesCompras.getIdResolucionAdj().getIdParticipante().getCantidad().longValue());
        info.setCodigoDepartamento(contratosOrdenesCompras.getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoDepartamento().getCodigoDepartamento());
        info.setCodigoEntidad(contratosOrdenesCompras.getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoEntidad());
        info.setCodigoMunicipio(contratosOrdenesCompras.getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoMunicipio());
        info.setFechaInsercion(contratosOrdenesCompras.getFechaInsercion());
        info.setIdContrato(contratosOrdenesCompras.getIdContrato().toBigInteger());
        info.setIdDetProcesoAdq(contratosOrdenesCompras.getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq().longValue());
        info.setIdEmpresa(contratosOrdenesCompras.getIdResolucionAdj().getIdParticipante().getIdEmpresa().getIdEmpresa().toBigInteger());
        info.setMontoContrato(contratosOrdenesCompras.getIdResolucionAdj().getIdParticipante().getMonto());
        info.setNombreDepartamento(contratosOrdenesCompras.getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoDepartamento().getNombreDepartamento());

        em.persist(info);

        agregarDatosAResumen(contratosOrdenesCompras);

        return contratosOrdenesCompras;
    }

    /**
     * Obtiene el numero del contrato y es incremental en base al proceso de
     * adquisición y codigo del centro educativo
     *
     * @param codigoEntidad
     * @param idDetProcesoAdq
     * @return
     */
    private synchronized String getNumContrato(String codigoEntidad, Integer idAnho) {
        Query q = em.createQuery("SELECT COUNT(c.idContrato)+1 FROM ContratosOrdenesCompras c WHERE c.idResolucionAdj.idParticipante.idOferta.codigoEntidad.codigoEntidad=:codigoEntidad AND c.idResolucionAdj.idParticipante.idOferta.idDetProcesoAdq.idProcesoAdq.idAnho.idAnho=:idAnho AND c.estadoEliminacion=0 AND c.idResolucionAdj.estadoEliminacion=0 AND c.idResolucionAdj.idParticipante.estadoEliminacion=0 AND c.idResolucionAdj.idParticipante.idOferta.estadoEliminacion=0");
        q.setParameter("codigoEntidad", codigoEntidad);
        q.setParameter("idAnho", idAnho);
        return ((Long) q.getSingleResult()).toString();
    }

    public ContratosOrdenesCompras editContrato(ContratosOrdenesCompras contratosOrdenesCompras) {
        return em.merge(contratosOrdenesCompras);
    }

    public List<EstadoReserva> getLstEstadoReservaModif() {
        Query q = em.createQuery("SELECT e FROM EstadoReserva e WHERE e.idEstadoReserva IN (1, 2) ORDER BY e.idEstadoReserva", EstadoReserva.class);
        return q.getResultList();
    }

    public List<ContratoDto> generarRptActaAdjudicacion(BigDecimal idResolucion) {
        List<ContratoDto> lst;

        ResolucionesAdjudicativas res = findResolucionesAdjudicativasByPk(idResolucion);

        Query query = em.createNamedQuery("Contratacion.RptActaAdjudicacion", ContratoDto.class);
        query.setParameter(1, res.getIdParticipante().getIdOferta().getIdOferta());
        lst = query.getResultList();
        if (lst.isEmpty()) {
            return new ArrayList();
        } else {
            query = em.createNamedQuery("Contratacion.RptActaAdjudicacionParticipantes", ParticipanteDto.class);
            query.setParameter(1, res.getIdParticipante().getIdOferta().getIdOferta());
            lst.get(0).setLstParticipantes(query.getResultList());

            query = em.createNamedQuery("Contratacion.RptActaAdjudicacionItems", DetalleItemDto.class);
            query.setParameter(1, res.getIdParticipante().getIdOferta().getIdOferta());
            lst.get(0).setLstDetalleItem(query.getResultList());
        }
        return lst;
    }

    public List<ContratoDto> generarRptActaRecomendacion(BigDecimal idResolucion) {
        List<ContratoDto> lst;

        ResolucionesAdjudicativas res = findResolucionesAdjudicativasByPk(idResolucion);

        Query query = em.createNamedQuery("Contratacion.RptActaAdjudicacion", ContratoDto.class);
        query.setParameter(1, res.getIdParticipante().getIdOferta().getIdOferta());
        lst = query.getResultList();
        if (lst.isEmpty()) {
            return new ArrayList();
        } else {
            query = em.createNamedQuery("Contratacion.RptActaAdjudicacionParticipantes", ParticipanteDto.class);
            query.setParameter(1, res.getIdParticipante().getIdOferta().getIdOferta());
            lst.get(0).setLstParticipantes(query.getResultList());

            query = em.createNamedQuery("Contratacion.RptActaAdjudicacionItems", DetalleItemDto.class);
            query.setParameter(1, res.getIdParticipante().getIdOferta().getIdOferta());
            lst.get(0).setLstDetalleItem(query.getResultList());

            lst.get(0).setLstPorcentajeEval(proveedorEJB.getLstProveedorPorcentajeEval(res.getIdParticipante().getIdOferta().getIdOferta()));
        }
        return lst;
    }

    public List<ContratoDto> generarRptNotaAdjudicacion(BigDecimal idResolucion) {
        Query query = em.createNamedQuery("Contratacion.RptNotaAdjudicacionBean", ContratoDto.class);
        query.setParameter(1, idResolucion);

        List<ContratoDto> lstNotaAdj = query.getResultList();
        if (!lstNotaAdj.isEmpty()) {
            query = em.createQuery("SELECT r.idParticipante.idEmpresa.idPersoneria.idPersoneria, r.idParticipante.idEmpresa.distribuidor FROM ResolucionesAdjudicativas r WHERE r.idResolucionAdj=:idResolucion");
            query.setParameter("idResolucion", idResolucion);

            Object idPersoneria = query.getSingleResult();
            Object lstD[] = (Object[]) idPersoneria;

            if (lstD[1].toString().equals("0")) {
                //FABRICANTES
                query = em.createNamedQuery("Contratacion.RptNotaAdjudicacionBeanDetalleItemFab", DetalleItemDto.class);
            } else {
                //DISTRIBUIDORES
                query = em.createNamedQuery("Contratacion.RptNotaAdjudicacionBeanDetalleItemDist", DetalleItemDto.class);
            }
            query.setParameter(1, idResolucion);
            lstNotaAdj.get(0).setLstDetalleItem(query.getResultList());
        }

        return lstNotaAdj;
    }

    public List<VwRptPagare> generarRptGarantia(BigDecimal idResolucion, BigDecimal idContrato) {
        try {
            Query query = em.createNamedQuery("Contratacion.VwRptPagare", VwRptPagare.class);
            query.setParameter(1, idResolucion);
            query.setParameter(2, idContrato);

            return query.getResultList();
        } finally {
        }
    }

    public List<VwRptContratoJurCabecera> generarContrato(ContratosOrdenesCompras idContrato, BigDecimal idRubro) {
        List<VwRptContratoJurCabecera> lstContrato;
        List<DetalleItemDto> lst;
        try {
            Query query = em.createNamedQuery("Contratacion.VwRptContratoJurCabecera", VwRptContratoJurCabecera.class);
            query.setParameter(1, idContrato.getIdContrato());

            lstContrato = query.getResultList();

            if (!lstContrato.isEmpty()) {
                query = em.createQuery("SELECT DISTINCT c.idResolucionAdj.idParticipante.idEmpresa.idPersoneria.idPersoneria, c.idResolucionAdj.idParticipante.idEmpresa.distribuidor FROM ContratosOrdenesCompras c WHERE c.idResolucionAdj=:idResolucion and c.estadoEliminacion=0");
                query.setParameter("idResolucion", idContrato.getIdResolucionAdj());

                Object idPersoneria = query.getSingleResult();
                Object[] datos = (Object[]) idPersoneria;

                if (datos[1].toString().equals("1")) {
                    //DISTRIBUIDOR
                    query = em.createNamedQuery("Contratacion.RptNotaAdjudicacionBeanDetalleItemDist", DetalleItemDto.class);
                } else {
                    //FABRICANTE
                    query = em.createNamedQuery("Contratacion.RptNotaAdjudicacionBeanDetalleItemFab", DetalleItemDto.class);
                }

                query.setParameter(1, idContrato.getIdResolucionAdj().getIdResolucionAdj());
                lst = query.getResultList();

                List<DetalleItemDto> lstDetalle = new ArrayList();
                List<DetalleItemDto> lstDetalleBac = new ArrayList();

                lstDetalleBac.addAll(lst.stream().filter(d -> idRubro.compareTo(BigDecimal.ONE) == 0 && d.getConsolidadoEspTec().contains("BACHILLERATO")).collect(Collectors.toList()));
                lstDetalle.addAll(lst.stream().filter(d -> idRubro.compareTo(BigDecimal.ONE) != 0 || !d.getConsolidadoEspTec().contains("BACHILLERATO")).collect(Collectors.toList()));

                lstContrato.get(0).setLstDetalleItems(lstDetalle);
                lstContrato.get(0).setLstDetalleItemsBac(lstDetalleBac);
            }

            return lstContrato;
        } finally {
        }
    }

    public SaldoProveedorDto getSaldoProveedor(ResolucionesAdjudicativas res) {
        SaldoProveedorDto saldo = new SaldoProveedorDto();
        saldo.setRubro(res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdRubroAdq().getDescripcionRubro());
        saldo.setCapacidadCalificada(proveedorEJB.findCapaInstPorRubro(res.getIdParticipante()).get(0).getCapacidadAcreditada());
        saldo.setCapacidadAdjudicada(((BigDecimal) getAdjudicacionParticipante(res.getIdParticipante()).get(0)).toBigInteger());
        saldo.setAdjudicadaActual(getCantidadAdjudicadaActual(res.getIdParticipante().getIdParticipante()));
        saldo.setSaldoCapacidad(BigInteger.ZERO);
        return saldo;
    }

    public BigInteger getCantidadAdjudicadaActual(BigDecimal idParticipante) {
        BigInteger total;

        Query q = em.createNativeQuery("SELECT nvl(sum(cantidad),0) FROM DETALLE_OFERTAS WHERE id_participante=?1 and estado_eliminacion=0 and id_producto not in (1)");
        q.setParameter(1, idParticipante);
        total = ((BigDecimal) q.getSingleResult()).toBigInteger();

        return total;
    }

    public List getAdjudicacionParticipante(Participantes par) {
        String sql = String.format("SELECT NVL(FN_GET_CANT_ADJ_PROVE(%d, %d), 0) FROM DUAL",
                par.getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq(),
                par.getIdEmpresa().getIdEmpresa().intValue());

        Query q = em.createNativeQuery(sql);
        return q.getResultList();
    }

    public void updateResolucionPorModificativa(BigDecimal idResolucionAdj) {
        Query q = em.createQuery("UPDATE ResolucionesAdjudicativas r SET r.idEstadoReserva=:idEstado WHERE r.idResolucionAdj = :idRes");
        q.setParameter("idEstado", em.find(EstadoReserva.class, new BigDecimal(5)));
        q.setParameter("idRes", idResolucionAdj);
        q.executeUpdate();

        ResolucionesAdjudicativas res = em.find(ResolucionesAdjudicativas.class, idResolucionAdj);
        historialCambioEstado(res, res.getIdEstadoReserva().getIdEstadoReserva().toBigInteger(), new BigInteger("5"), "Modificatoria Inicial", res.getUsuarioInsercion());
    }

    private void historialCambioEstado(ResolucionesAdjudicativas res,
            BigInteger estadoAnterior,
            BigInteger estadoNuevo,
            String comentarioReversion, String usuario) {
        HistorialCamEstResAdj historial = new HistorialCamEstResAdj();
        if (estadoAnterior.compareTo(BigInteger.ONE) == 0 && estadoNuevo.compareTo(new BigInteger("2")) == 0) {
            historial.setComentarioHistorial("Primera aplicación de reserva de fondos");
        } else if (estadoAnterior.compareTo(new BigInteger("2")) == 0 && estadoNuevo.compareTo(new BigInteger("3")) == 0) {
            historial.setComentarioHistorial(comentarioReversion);
        }
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(estadoNuevo);
        historial.setFechaCambioEstado(new Date());
        historial.setIdResolucionAdj(res);
        historial.setUsuario(usuario);

        em.persist(historial);
    }

    public List<RptDocumentos> getDocumentosAImprimir(Integer idDetProcesoAdq, List<Integer> lstNumDoc) {
        Query q = em.createQuery("SELECT r FROM RptDocumentos r WHERE r.idDetProcesoAdq.idDetProcesoAdq=:idDet and r.idTipoRpt.idTipoRpt in :lst ORDER BY r.orden", RptDocumentos.class);
        q.setParameter("idDet", idDetProcesoAdq);
        q.setParameter("lst", lstNumDoc);

        return q.getResultList();
    }

    public void enviarCorreoDeError(BigDecimal idResolucionAdj) {
        eMailEJB.enviarMailDeError("Contratos Múltiple", "Duplicidad de contratos para idResolucionAdj: " + idResolucionAdj, null);
    }

    private void agregarDatosAResumen(ContratosOrdenesCompras contrato) {
        Query q = em.createNamedQuery("SP_ADD_RESUMEN_CE_PROCESADO");
        q.setParameter("P_ID_DET_PROCESO_ADQ", contrato.getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq());
        q.setParameter("P_CODIGO_ENTIDAD", contrato.getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoEntidad());
        q.setParameter("P_CODIGO_DEPARTAMENTO", contrato.getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoDepartamento().getCodigoDepartamento());
        q.setParameter("P_CODIGO_MUNICIPIO", contrato.getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoMunicipio());
        q.getResultList();

        q = em.createNamedQuery("SP_ADD_RESUMEN_ADJ_EMP");
        q.setParameter("P_ID_DET_PROCESO_ADQ", contrato.getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq());
        q.setParameter("P_ID_EMPRESA", contrato.getIdResolucionAdj().getIdParticipante().getIdEmpresa().getIdEmpresa());
        q.setParameter("P_ID_TIPO_EMPRESA", contrato.getIdResolucionAdj().getIdParticipante().getIdEmpresa().getIdTipoEmpresa().getIdTipoEmp());
        q.setParameter("P_CODIGO_DEPARTAMENTO", contrato.getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoDepartamento().getCodigoDepartamento());
        q.setParameter("P_CODIGO_ENTIDAD", contrato.getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoEntidad());
        q.setParameter("P_SUBTOTAL", contrato.getIdResolucionAdj().getIdParticipante().getMonto());
        q.setParameter("P_CANTIDAD", contrato.getIdResolucionAdj().getIdParticipante().getCantidad());
        q.getResultList();
    }

    public List<HistorialCamEstResAdj> findHistorialByIdResolucionAdj(BigDecimal idResolucionAdj) {
        Query q = em.createQuery("SELECT h FROM HistorialCamEstResAdj h WHERE h.idResolucionAdj.idResolucionAdj=:idResolucionAdj ORDER BY h.idHistorialCam", HistorialCamEstResAdj.class);
        q.setParameter("idResolucionAdj", idResolucionAdj);
        return q.getResultList();
    }

    public TechoRubroEntEdu findTechoRubroEntEdu(String codigoEntidad, Integer idDetProcesoAdq) {
        Query query = em.createQuery("SELECT t FROM TechoRubroEntEdu t WHERE t.codigoEntidad=:codigoEntidad and t.idDetProcesoAdq.idDetProcesoAdq=:idProceso and t.estadoEliminacion=0", TechoRubroEntEdu.class);
        query.setParameter("codigoEntidad", codigoEntidad);
        query.setParameter("idProceso", idDetProcesoAdq);

        if (query.getResultList().isEmpty()) {
            return null;
        } else {
            return (TechoRubroEntEdu) query.getSingleResult();
        }
    }

    @Lock(LockType.WRITE)
    public HashMap<String, Object> aplicarReservaDeFondos(ResolucionesAdjudicativas resAdj,
            BigDecimal estadoReserva, String codigoEntidad, String comentarioReversion, String usuario) {
        Logger.getLogger(ResolucionAdjudicativaEJB.class.getName()).log(Level.INFO, "Usuario que aplica reserva: {0} del CE: {1}", new Object[]{usuario, codigoEntidad});
        HashMap<String, Object> param = new HashMap();
        try {
            param = saldosEJB.aplicarReservaDeFondos(resAdj, estadoReserva, codigoEntidad, comentarioReversion, usuario);
        } catch (Exception e) {
            Logger.getLogger(ResolucionAdjudicativaEJB.class.getName()).log(Level.WARNING, "Es necesario volver aplicar la reserva: {0} del CE: {1}", new Object[]{usuario, codigoEntidad});
            param.put("error", "Por favor, intenten nuevamente APLICAR la reserva.");
        }
        return param;
    }

    public void reversionMasiva() {
        Query q = em.createNativeQuery("SELECT id_resolucion_adj, id_det_proceso_adq, codigo_entidad FROM TEMP");
        List lst = q.getResultList();
        lst.forEach((object) -> {
            Object[] datos1 = (Object[]) object;
            ResolucionesAdjudicativas res = em.find(ResolucionesAdjudicativas.class, datos1[0]);
            HashMap<String, Object> param = aplicarReservaDeFondos(res, new BigDecimal(3), datos1[2].toString(), "error capacidad CONAMYPE", "RAFAARIAS");
            if (param.containsKey("error")) {
                Logger.getLogger(ResolucionAdjudicativaEJB.class.getName()).log(Level.SEVERE, param.get("error").toString());
            } else {

            }
        });

    }

    public List<DetalleOfertas> findDetalleOfertas(Participantes participante) {
        return proveedorEJB.findDetalleOfertas(participante, false);
    }

    public List<DetalleModificativa> findDetalleModificativa(BigDecimal idResModif) {
        Query q = em.createQuery("SELECT d FROM DetalleModificativa d WHERE d.idResolucionModif.idResolucionModif = :idRes ORDER BY FUNC('TO_NUMBER', d.noItem)", DetalleModificativa.class);
        q.setParameter("idRes", idResModif);

        return q.getResultList();
    }

    public ContratosOrdenesCompras findContratoByCodEntAndIdDetProceso(String codigoEntidad, BigDecimal idDetProcesoAdq) {
        Query q = em.createQuery("SELECT c FROM ContratosOrdenesCompras c WHERE c.idResolucionAdj.idParticipante.idOferta.codigoEntidad.codigoEntidad=:codEnt AND c.idResolucionAdj.idParticipante.idOferta.idDetProcesoAdq.idDetProcesoAdq=:idDetProcesoAdq AND c.idResolucionAdj.idParticipante.idOferta.estadoEliminacion=0", ContratosOrdenesCompras.class);
        q.setParameter("codEnt", codigoEntidad);
        q.setParameter("idDetProcesoAdq", idDetProcesoAdq);
        return q.getResultList().isEmpty() ? null : (ContratosOrdenesCompras) q.getResultList().get(0);
    }

    public ContratosOrdenesCompras findContratoByIdParticipante(BigDecimal idParticipante) {
        Query q = em.createQuery("SELECT c FROM ContratosOrdenesCompras c WHERE c.idResolucionAdj.idParticipante.idParticipante=:idPar AND c.idResolucionAdj.idParticipante.idOferta.estadoEliminacion=0", ContratosOrdenesCompras.class);
        q.setParameter("idPar", idParticipante);
        return q.getResultList().isEmpty() ? null : (ContratosOrdenesCompras) q.getResultList().get(0);
    }

    public ResolucionesModificativas findModificativaByIdContrato(BigDecimal idContrato) {
        Query q = em.createQuery("SELECT r FROM ResolucionesModificativas r WHERE r.idContrato.idContrato=idContrato and r.idEstadoReserva.idEstadoReserva=2", ResolucionesModificativas.class);
        q.setParameter("idContrato", idContrato);
        return q.getResultList().isEmpty() ? null : (ResolucionesModificativas) q.getResultList().get(0);
    }

    public void guardarLiquidacion(Liquidacion liquidacion) {
        if (liquidacion.getIdLiquidacion() == null) {
            em.persist(liquidacion);
        }
    }

    public List<Liquidacion> getLstLiquidacionByCodigoEntAndIdDetProcesoAdqAndIdParticipante(String codigoEnt, Integer idDetProcesoAdq, BigDecimal idParticipante) {
        Query q = em.createQuery("SELECT c FROM ContratosOrdenesCompras c WHERE c.idResolucionAdj.idParticipante.idOferta.codigoEntidad.codigoEntidad=:codEnt and c.idResolucionAdj.idParticipante.idOferta.idDetProcesoAdq.idDetProcesoAdq=:idDetPro and c.idResolucionAdj.idParticipante.idParticipante=:idPar", ContratosOrdenesCompras.class);
        q.setParameter("codEnt", codigoEnt);
        q.setParameter("idDetPro", idDetProcesoAdq);
        q.setParameter("idPar", idParticipante);
        BigDecimal idContrato = ((ContratosOrdenesCompras)q.getResultList().get(0)).getIdContrato();
        
        q = em.createQuery("SELECT l FROM Liquidacion l where l.idContrato.idContrato=:idCon ORDER BY l.idLiquidacion", ContratosOrdenesCompras.class);
        q.setParameter("idCon", idContrato);
        return q.getResultList();
    }

    public List<DatosContratoDto> getDatosContratoDto(String codigoEntidad, Integer idDetProcesoAdq) {
        Query q = em.createNamedQuery("Liquidacion.DatosContratoDto", DatosContratoDto.class);
        q.setParameter(1, codigoEntidad);
        q.setParameter(2, idDetProcesoAdq);
        return q.getResultList();
    }
    
    public List<DatosModificativaDto> getDatosModificativaDto(BigDecimal idContrato){
        Query q = em.createNamedQuery("Liquidacion.DatosModificativaDto", DatosModificativaDto.class);
        q.setParameter(1, idContrato);
        return q.getResultList();
    }
    
    public List<DatosRecepcionDto> getDatosRecepcionDto(BigDecimal idContrato){
        Query q = em.createNamedQuery("Liquidacion.DatosRecepcionDto", DatosRecepcionDto.class);
        q.setParameter(1, idContrato);
        return q.getResultList();
    }
    
    public ContratosOrdenesCompras findContratoByPk(BigDecimal idContrato){
        return em.find(ContratosOrdenesCompras.class, idContrato);
    }
    
    public List<ParticipanteConContratoDto> findParticipantesConContratoByCodEntAndIdDetProcesoAdq(String codigoEntidad, Integer idDetProcesoAdq){
        Query q = em.createNamedQuery("Proveedor.ContratoActivo", ParticipanteConContratoDto.class);
        q.setParameter(1, codigoEntidad);
        q.setParameter(2, idDetProcesoAdq);
        return q.getResultList();
    }
}
