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
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
import sv.gob.mined.paquescolar.model.EstadoReserva;
import sv.gob.mined.paquescolar.model.HistorialCamEstResAdj;
import sv.gob.mined.paquescolar.model.Participantes;
import sv.gob.mined.paquescolar.model.ResolucionesAdjudicativas;
import sv.gob.mined.paquescolar.model.RptDocumentos;
import sv.gob.mined.paquescolar.model.TechoRubroEntEdu;
import sv.gob.mined.paquescolar.model.pojos.contratacion.SaldoProveedorDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ContratoDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.DetalleItemDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ParticipanteDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.VwRptContratoJurCabecera;
import sv.gob.mined.paquescolar.model.pojos.contratacion.VwRptPagare;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class ResolucionAdjudicativaEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;
    @EJB
    ProveedorEJB proveedorEJB;
    @EJB
    EMailEJB eMailEJB;

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

    public synchronized HashMap<String, Object> aplicarReservaDeFondos(ResolucionesAdjudicativas resAdj,
            BigDecimal estadoReserva, String codigoEntidad, BigInteger cantidad, String comentarioReversion, String usuario) {
        HashMap<String, Object> param = new HashMap();
        //TODO falta derechos de usuarios y manejo de errores
        TechoRubroEntEdu techoCE;

        if (resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 4) {
            param.put("error", "Esta reserva se encuentra anulada y no se puede modificar.");
        } else {
            //recuperar techo presupuestario del centro escolar
            techoCE = findTechoRubroEntEdu(codigoEntidad, resAdj.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq());

            //DIGITADA -> APLICADA  y  REVERTIDAD -> APLICADA
            if (techoCE != null) {
                if ((resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 1 && estadoReserva.intValue() == 2)
                        || (resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 3 && estadoReserva.intValue() == 2)) {
                    //verificar disponibilidad presupuestaria del C.E. y capacidad calificada del proveedor
                    param = existeDiponibilidad(techoCE, resAdj, param);
                    if (usuario.equals("RMINERO") || usuario.equals("RAFAARIAS") || usuario.equals("CVILLEGAS") || !param.containsKey("error")) {
                        param = aplicarSaldos(resAdj, techoCE, estadoReserva, cantidad, comentarioReversion, usuario, param);

                        //Si ya existe un contrato para esta reserva de fondos, se actualizan los datos del resumen de contrataciones
                        if (!resAdj.getContratosOrdenesComprasList().isEmpty() || resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 3) {
                            agregarDatosAResumen(resAdj.getContratosOrdenesComprasList().get(0));
                        }
                    }
                } else //DIGITADA -> ANULADA y REVERTIDA -> ANULADA 
                if ((resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 1 && estadoReserva.intValue() == 4)
                        || (resAdj.getIdResolucionAdj().intValue() == 3 && estadoReserva.intValue() == 4)) {
                    param = aplicarSaldos(resAdj, techoCE, estadoReserva, cantidad, comentarioReversion, usuario, param);
                } else //APLICADA -> REVERTIDA
                if (resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 2 && estadoReserva.intValue() == 3) {
                    param = aplicarSaldos(resAdj, techoCE, estadoReserva, cantidad, comentarioReversion, usuario, param);
                    removerDatosResumen(resAdj);
                }
            } else {
                param.put("error", "Se ha generado un error en la aplicación de fondos.");
            }
        }

        return param;
    }

    public HashMap<String, Object> aplicarSaldos(ResolucionesAdjudicativas res,
            TechoRubroEntEdu techoCE,
            BigDecimal idEstadoReserva,
            BigInteger cantidad,
            String comentarioReversion,
            String usuario,
            HashMap<String, Object> param) {
        try {

            BigDecimal cantidadAdjudicada = new BigDecimal(getCantidadAdjudicadaActual(res.getIdParticipante().getIdParticipante()));
            BigInteger estadoAnterior = res.getIdEstadoReserva().getIdEstadoReserva().toBigInteger();

            //no devuelve nada cuando no hay registros
            Query query;
            if (res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getAnho().equals("2018")) {
                query = em.createQuery("SELECT c FROM CapaInstPorRubro c WHERE c.idMuestraInteres.idEmpresa=:idEmpresa and c.idMuestraInteres.idDetProcesoAdq.idProcesoAdq.idAnho=:idAnho and c.estadoEliminacion = 0 and c.idMuestraInteres.estadoEliminacion=0 and c.idMuestraInteres.idEmpresa.estadoEliminacion=0", CapaInstPorRubro.class);
                query.setParameter("idEmpresa", res.getIdParticipante().getIdEmpresa());
                query.setParameter("idAnho", res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho());
            } else {
                query = em.createQuery("SELECT c FROM CapaInstPorRubro c WHERE c.idMuestraInteres.idEmpresa=:idEmpresa and c.idMuestraInteres.idDetProcesoAdq.idProcesoAdq.idProcesoAdq=:idProcesoAdq and c.estadoEliminacion = 0 and c.idMuestraInteres.estadoEliminacion=0 and c.idMuestraInteres.idEmpresa.estadoEliminacion=0", CapaInstPorRubro.class);
                query.setParameter("idEmpresa", res.getIdParticipante().getIdEmpresa());
                query.setParameter("idProcesoAdq", res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getPadreIdProcesoAdq() == null ? res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdProcesoAdq() : res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getPadreIdProcesoAdq().getIdProcesoAdq());
            }
            List<CapaInstPorRubro> lst = query.getResultList();

            if (!lst.isEmpty()) {
                CapaInstPorRubro capaInst = em.find(CapaInstPorRubro.class, lst.get(0).getIdCapInstRubro());

                switch (idEstadoReserva.intValue()) {
                    case 2: //aplicar
                        techoCE.setMontoAdjudicado(techoCE.getMontoAdjudicado().add(res.getValor()));
                        techoCE.setMontoDisponible(techoCE.getMontoDisponible().add(res.getValor().negate()));

                        capaInst.setCapacidadAdjudicada(getAdjParticipante(res.getIdParticipante()).add(cantidadAdjudicada).toBigInteger());
                        break;
                    case 3: //revertir modificada
                    case 5:
                        techoCE.setMontoAdjudicado(techoCE.getMontoAdjudicado().add(res.getValor().negate()));
                        techoCE.setMontoDisponible(techoCE.getMontoDisponible().add(res.getValor()));

                        capaInst.setCapacidadAdjudicada(getAdjParticipante(res.getIdParticipante()).add(cantidadAdjudicada.negate()).toBigInteger());
                        break;
                    case 4: //anular
                        res.setIdEstadoReserva(new EstadoReserva(idEstadoReserva));
                        break;
                }

                res.setIdEstadoReserva(new EstadoReserva(idEstadoReserva));
                res.setUsuarioModificacion(usuario);
                res.setFechaModificacion(new Date());

                em.merge(techoCE);
                res = em.merge(res);

                em.merge(capaInst);

                historialCambioEstado(res, estadoAnterior, idEstadoReserva.toBigInteger(), comentarioReversion, usuario);
            }
            return param;
        } catch (Exception e) {
            param.put("error", "Se ha generado un error en la aplicación de fondos.");
            Logger.getLogger(ResolucionAdjudicativaEJB.class.getName()).log(Level.SEVERE, null, e);
            return param;
        }
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

    public BigDecimal getAdjParticipante(Participantes par) {
        List lst = getAdjudicacionParticipante(par);

        try {
            return (BigDecimal) lst.get(0);
        } catch (Exception ex) {
            return BigDecimal.ZERO;
        }
    }

    public List getAdjudicacionParticipante(Participantes par) {
        String sql = String.format("SELECT NVL(FN_GET_CANT_ADJ_PROVE(%d, %d), 0) FROM DUAL",
                par.getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq(),
                par.getIdEmpresa().getIdEmpresa().intValue());

        Query q = em.createNativeQuery(sql);
        return q.getResultList();
    }

    private HashMap<String, Object> existeDiponibilidad(TechoRubroEntEdu techoCE, ResolucionesAdjudicativas resAdj, HashMap<String, Object> param) {
        param = existeDisponibilidadProveedor(resAdj, param);
        if (param.containsKey("error")) {
            return param;
        }

        return existeDisponibilidadesCE(techoCE, resAdj.getValor(), param);
    }

    private HashMap<String, Object> existeDisponibilidadesCE(TechoRubroEntEdu techoCE, BigDecimal valorReserva, HashMap<String, Object> param) {

        if (techoCE.getMontoDisponible().compareTo(valorReserva) != -1) {
        } else {
            param.put("error", "El centro escolar " + techoCE.getCodigoEntidad() + " no posee disponibilidad presupuestaria!"
                    + "Monto Disponible: " + techoCE.getMontoDisponible() + " Monto Adjudicado: " + valorReserva);
        }
        return param;
    }

    private HashMap<String, Object> existeDisponibilidadProveedor(ResolucionesAdjudicativas resAdj, HashMap<String, Object> param) {
        List lista;
        BigInteger cantidadAdjudicada = getCantidadAdjudicadaActual(resAdj.getIdParticipante().getIdParticipante());
        BigInteger disponibilidadProveedor;
        lista = getSaldoParticipante(resAdj);
        if (lista != null && !lista.isEmpty()) {
            disponibilidadProveedor = ((BigDecimal) lista.get(0)).toBigInteger();
            if (disponibilidadProveedor.compareTo(cantidadAdjudicada) != -1) {
            } else {
                param.put("error", "El proveedor no posee disponibilidad para este rubro! Cantidad disponible:" + disponibilidadProveedor.intValue()
                        + " Cantidad de Adjudicada: " + cantidadAdjudicada.intValue());
            }
        } else {
            param.put("error", "El proveedor no posee disponibilidad para este rubro!");
        }
        return param;
    }

    public List getSaldoParticipante(ResolucionesAdjudicativas res) {
        String sql = String.format("SELECT NVL(FN_VERIFICAR_SALDO_PROVEEDOR(%d, %d, %d), 0) FROM DUAL",
                res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq(),
                res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getPadreIdProcesoAdq() == null ? res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdProcesoAdq() : res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getPadreIdProcesoAdq().getIdProcesoAdq(),
                res.getIdParticipante().getIdEmpresa().getIdEmpresa().intValue());

        Query q = em.createNativeQuery(sql);
        return q.getResultList();
    }

    public BigInteger getCantidadAdjudicadaActual(BigDecimal idParticipante) {
        BigInteger total;

        Query q = em.createNativeQuery("SELECT nvl(sum(cantidad),0) FROM DETALLE_OFERTAS WHERE id_participante=?1 and estado_eliminacion=0 and id_producto not in (1)");
        q.setParameter(1, idParticipante);
        total = ((BigDecimal) q.getSingleResult()).toBigInteger();

        return total;
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

    public void updateResolucionPorModificativa(BigDecimal idResolucionAdj) {
        Query q = em.createQuery("UPDATE ResolucionesAdjudicativas r SET r.idEstadoReserva=:idEstado WHERE r.idResolucionAdj = :idRes");
        q.setParameter("idEstado", em.find(EstadoReserva.class, new BigDecimal(5)));
        q.setParameter("idRes", idResolucionAdj);
        q.executeUpdate();

        ResolucionesAdjudicativas res = em.find(ResolucionesAdjudicativas.class, idResolucionAdj);
        historialCambioEstado(res, res.getIdEstadoReserva().getIdEstadoReserva().toBigInteger(), new BigInteger("5"), "Modificatoria Inicial", res.getUsuarioInsercion());
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

    private void removerDatosResumen(ResolucionesAdjudicativas resAdj) {
        Query q = em.createNativeQuery("delete RESUMEN_ADJ_EMP where id_det_proceso_adq = ?1 and codigo_entidad =?2 and id_empresa = ?3");
        q.setParameter(1, resAdj.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq());
        q.setParameter(2, resAdj.getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoEntidad());
        q.setParameter(3, resAdj.getIdParticipante().getIdEmpresa().getIdEmpresa());

        q.executeUpdate();

        q = em.createNativeQuery("delete resumen_ce_procesados where id_det_proceso_adq = ?1 and codigo_entidad = ?2");
        q.setParameter(1, resAdj.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq());
        q.setParameter(2, resAdj.getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoEntidad());

        q.executeUpdate();
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
}
