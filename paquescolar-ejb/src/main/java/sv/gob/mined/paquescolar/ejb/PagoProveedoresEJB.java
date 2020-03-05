/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.DetalleDocPago;
import sv.gob.mined.paquescolar.model.DetallePlanilla;
import sv.gob.mined.paquescolar.model.DetallePreCarga;
import sv.gob.mined.paquescolar.model.DetalleRequerimiento;
import sv.gob.mined.paquescolar.model.ListaNotificacionPago;
import sv.gob.mined.paquescolar.model.PlanillaPago;
import sv.gob.mined.paquescolar.model.PlanillaPagoCheque;
import sv.gob.mined.paquescolar.model.PreCarga;
import sv.gob.mined.paquescolar.model.ReintegroRequerimiento;
import sv.gob.mined.paquescolar.model.ResolucionesModificativas;
import sv.gob.mined.paquescolar.model.TipoDocPago;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.DatosBusquedaPlanillaDto;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.DatosProveDto;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.PreCargaDto;
import sv.gob.mined.paquescolar.util.Constantes;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class PagoProveedoresEJB {

    @EJB
    private EMailEJB eMailEJB;

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    public List<DatosProveDto> getLstDatosProveDto(String anhoPago, String numeroNit, String codigoDepartamento, String usuario) {
        try {
            Query q = em.createNamedQuery("PagoProve.ConstanciaRentencionByAnhoAndNitEmp", DatosProveDto.class);
            q.setParameter(1, Integer.parseInt(anhoPago));
            q.setParameter(2, numeroNit);
            q.setParameter(3, codigoDepartamento);
            return q.getResultList();
        } catch (NumberFormatException e) {
            eMailEJB.enviarMailDeError("Paquete Escolar - Error - Modulo de pago",
                    "Error en generacion de constancia de renta.\n"
                    + "Anho " + anhoPago + "; NIT " + numeroNit + "; codigoDepartamento " + codigoDepartamento + "; usuario " + usuario,
                    e);
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, "Error en generacion de constancia de renta.");
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, "Anho {0} NIT {1} codigoDepartamento {2}", new Object[]{anhoPago, numeroNit, codigoDepartamento});
            return new ArrayList();
        }
    }

    public List<DatosProveDto> getDatosF910(String codigoDepartamento, Integer anho) {
        Query q = em.createNamedQuery("PagoProve.FileF910", DatosProveDto.class);
        q.setParameter(1, codigoDepartamento);
        q.setParameter(2, anho);
        return q.getResultList();
    }

    public List<DatosProveDto> getDatosRptRentaMensual(String codigoDepartamento, Integer idMesPago, Integer anhoPago, String usuario) {
        try {
            Query q = em.createNamedQuery("PagoProve.ReporteRentaMensual", DatosProveDto.class);
            q.setParameter(1, codigoDepartamento);
            q.setParameter(2, idMesPago);
            q.setParameter(3, anhoPago);
            return q.getResultList();
        } catch (Exception e) {
            eMailEJB.enviarMailDeError("Paquete Escolar - Error - Modulo de pago",
                    "Error en generacion de constancia de renta mensual.\n"
                    + "Anho " + anhoPago + "; idMesPago " + idMesPago + "; codigoDepartamento " + codigoDepartamento + "; usuario " + usuario,
                    e);
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, String.format("Error generando el reporte de renta mensual: %s - %d - %d", codigoDepartamento, idMesPago, anhoPago));

            return new ArrayList();
        }
    }

    public List<DatosProveDto> getDatosRptRentaMensualByRequerimiento(String codigoDepartamento, Integer idMesPago, Integer anhoPago, String formatoRequerimiento, String usuario) {
        try {
            Query q = em.createNamedQuery("PagoProve.ReporteRentaMensualByRequerimiento", DatosProveDto.class);
            q.setParameter(1, codigoDepartamento);
            q.setParameter(2, idMesPago);
            q.setParameter(3, anhoPago);
            q.setParameter(4, formatoRequerimiento);
            return q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, String.format("Error generando el reporte de renta mensual por requerimiento: %s - %d - %d", codigoDepartamento, idMesPago, anhoPago));

            return new ArrayList();
        }
    }

    public List<DatosProveDto> getDatosRptLiquidacion(String codigoDepartamento, String anho, Integer idDetProcesoAdq, String codigoEntidad) {
        Query q = em.createNamedQuery("PagoProve.RptLiquidacionDePagos", DatosProveDto.class);
        q.setParameter(1, codigoDepartamento);
        q.setParameter(2, Integer.parseInt("20" + anho));
        q.setParameter(3, idDetProcesoAdq);
        q.setParameter(4, codigoEntidad);
        return q.getResultList();
    }

    public List<DatosProveDto> getDatosRptReintegroByIdReq(BigDecimal idRequerimiento) {
        Query q = em.createNamedQuery("PagoProve.RptReintegroByIdReq", DatosProveDto.class);
        q.setParameter(1, idRequerimiento);
        return q.getResultList();
    }

    /**
     * Este metodo se dejara de utilizar a partir de 05/12/2018
     *
     * @param planillaPago
     * @return
     * @deprecated
     */
    @Deprecated
    public PlanillaPago guardarPlanillaPago(PlanillaPago planillaPago) {
        try {
            if (planillaPago.getIdPlanilla() == null) {
                em.persist(planillaPago);
            } else {
                planillaPago = em.merge(planillaPago);
            }
            return planillaPago;
        } catch (Exception e) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, null, e);
            return new PlanillaPago();
        }
    }

    public HashMap<String, Object> guardarPlanillaPago(PlanillaPago planillaPago, Boolean tipoPagoEntFinanciera,
            Boolean showChequeEntProv, Boolean showChequeRenta, Boolean showChequeUsefi,
            PlanillaPagoCheque chequeFinanProve, PlanillaPagoCheque chequeUsefi, PlanillaPagoCheque chequeRenta, String usuario) {

        boolean isRubroUniforme = planillaPago.getIdRequerimiento().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroUniforme().intValue() == 1;
        boolean guardarNuevo = (planillaPago.getIdPlanilla() == null);
        boolean showPnlCheques = false;
        int idTipoPlanilla = planillaPago.getIdTipoPlanilla();

        HashMap<String, Object> mapDeRetorno = new HashMap();

        //Verificar que los montos (actual y original) sean diferentes para ingreso de los datos del cheque USEFI
        //Esta validacion se realiza unicamente cuando la planilla se esta creando
        if (!showChequeUsefi) {
            for (DetallePlanilla detPla : planillaPago.getDetallePlanillaList()) {
                if (detPla.getIdDetalleDocPago().getMontoActual() != null && detPla.getIdDetalleDocPago().getContratoModif() == 1) {
                    showChequeUsefi = (detPla.getIdDetalleDocPago().getMontoActual().compareTo(detPla.getMontoOriginal()) == -1);
                    if (showChequeUsefi) {
                        break;
                    }
                }
            }
        }

        //guardar datos de fecha y numero de cheque en el detalle de planilla
        if (isRubroUniforme && chequeFinanProve.getFechaCheque() != null && chequeFinanProve.getNumeroCheque() != null) {
            for (DetallePlanilla detPla : planillaPago.getDetallePlanillaList()) {
                detPla.setFechaCheque(chequeFinanProve.getFechaCheque());
                detPla.setNumCheque(chequeFinanProve.getNumeroCheque());
            }
        }

        if (guardarNuevo) {
            planillaPago.setFechaInsercion(new Date());
            planillaPago.setUsuarioInsercion(usuario);
            planillaPago.setEstadoEliminacion((short) 0);

            //Habilitar visibilidad de cheques
            showChequeRenta = isRubroUniforme;
            showPnlCheques = (showChequeEntProv || showChequeRenta || showChequeUsefi);
        } else {
            planillaPago.setFechaModificacion(new Date());
            planillaPago.setUsuarioModificacion(usuario);
        }

        mapDeRetorno.put("showChequeEntProv", showChequeEntProv);
        mapDeRetorno.put("showChequeRenta", showChequeRenta);
        mapDeRetorno.put("showChequeUsefi", showChequeUsefi);
        mapDeRetorno.put("showPnlCheques", showPnlCheques);

        if (guardarNuevo) {
            em.persist(planillaPago);
        } else {
            em.merge(planillaPago);
        }

        if (planillaPago.getIdPlanilla() != null) {
            //mensaje
            mapDeRetorno.put(Constantes.MSJ_INFO, "Datos almacenados satisfactoriamente.");

            //Persistiendo los datos de los cheques
            BigDecimal mRenta = BigDecimal.ZERO;
            BigDecimal montoTotalActual;

            if (isRubroUniforme) {
                for (DetallePlanilla detPla : planillaPago.getDetallePlanillaList()) {
                    if (detPla.getIdDetalleDocPago().getMontoRenta() != null) {
                        mRenta = mRenta.add(detPla.getIdDetalleDocPago().getMontoRenta());
                    }
                }
                montoTotalActual = getMontoActualTotal(planillaPago).add(mRenta.negate());
            } else {
                montoTotalActual = getMontoActualTotal(planillaPago);
            }

            if (showChequeEntProv) {
                chequeFinanProve.setMontoCheque(montoTotalActual);
                if (chequeFinanProve.getIdPlanillaCheque() == null) {
                    chequeFinanProve.setaFavorDe((short) (idTipoPlanilla == 1 ? 3 : 0));
                    chequeFinanProve.setUsuarioInsercion(usuario);
                    chequeFinanProve.setFechaInsercion(new Date());
                    chequeFinanProve.setEstadoEliminacion((short) 0);
                    chequeFinanProve.setTransferencia((short) 0);
                    chequeFinanProve.setIdPlanilla(planillaPago);
                } else {
                    chequeFinanProve.setTransferencia(tipoPagoEntFinanciera ? 1 : (short) 0);
                    chequeFinanProve.setUsuarioModificacion(usuario);
                    chequeFinanProve.setFechaModificacion(new Date());
                }

                mapDeRetorno.put("chequeFinanProve", guardarPlanillaPagoCheque(chequeFinanProve));
            }

            if (showChequeUsefi) {
                chequeUsefi.setMontoCheque(getMontoOriginalTotal(planillaPago).add(montoTotalActual.negate()).add(mRenta.negate()));
                if (chequeUsefi.getIdPlanillaCheque() == null) {
                    chequeUsefi.setaFavorDe((short) 1);
                    chequeUsefi.setUsuarioInsercion(usuario);
                    chequeUsefi.setFechaInsercion(new Date());
                    chequeUsefi.setEstadoEliminacion((short) 0);
                    chequeUsefi.setIdPlanilla(planillaPago);
                } else {
                    chequeUsefi.setUsuarioModificacion(usuario);
                    chequeUsefi.setFechaModificacion(new Date());
                }
                mapDeRetorno.put("chequeUsefi", guardarPlanillaPagoCheque(chequeUsefi));
            }

            if (showChequeRenta) {
                if (chequeRenta.getIdPlanillaCheque() == null) {
                    chequeRenta.setaFavorDe((short) 2);
                    chequeRenta.setUsuarioInsercion(usuario);
                    chequeRenta.setFechaInsercion(new Date());
                    chequeRenta.setEstadoEliminacion((short) 0);
                    chequeRenta.setIdPlanilla(planillaPago);
                } else {
                    chequeRenta.setUsuarioModificacion(usuario);
                    chequeRenta.setFechaModificacion(new Date());
                }

                chequeRenta.setMontoCheque(mRenta);
                mapDeRetorno.put("chequeRenta", guardarPlanillaPagoCheque(chequeRenta));
            }
            mapDeRetorno.put(Constantes.ERROR, false);
        } else {
            //mensaje
            mapDeRetorno.clear();
            mapDeRetorno.put(Constantes.ERROR, true);
            mapDeRetorno.put(Constantes.MSJ_ERROR, "Ocurrio un error en la operación. Comuniquese con el administrador del sistema");
        }

        return mapDeRetorno;
    }

    private BigDecimal getMontoActualTotal(PlanillaPago planillaPago) {
        BigDecimal total = BigDecimal.ZERO;
        for (DetallePlanilla detPla : planillaPago.getDetallePlanillaList()) {
            if (detPla.getCantidadActual() != null && detPla.getEstadoEliminacion() == 0) {
                total = total.add(detPla.getMontoActual());
            }
        }
        return total;
    }

    private BigDecimal getMontoOriginalTotal(PlanillaPago planillaPago) {
        BigDecimal total = BigDecimal.ZERO;
        for (DetallePlanilla detPla : planillaPago.getDetallePlanillaList()) {
            if (detPla.getCantidadOriginal() != null) {
                total = total.add(detPla.getMontoOriginal());
            }
        }
        return total;
    }

    public ReintegroRequerimiento guardarReintegro(ReintegroRequerimiento reintegro) {
        try {
            if (reintegro.getIdReintegro() == null) {
                em.persist(reintegro);
            } else {
                reintegro = em.merge(reintegro);
            }
            return reintegro;
        } catch (Exception e) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, null, e);
            return new ReintegroRequerimiento();
        }
    }

    public DetallePlanilla guardarDetallePlanilla(DetallePlanilla detallePlanilla) {
        try {
            if (detallePlanilla.getIdPlanilla() == null) {
                em.persist(detallePlanilla);
            } else {
                detallePlanilla = em.merge(detallePlanilla);
            }
            return detallePlanilla;
        } catch (Exception e) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, null, e);
            return new DetallePlanilla();
        }
    }

    public PlanillaPagoCheque guardarPlanillaPagoCheque(PlanillaPagoCheque planillaPagoCheque) {
        try {
            if (planillaPagoCheque.getIdPlanillaCheque() == null) {
                em.persist(planillaPagoCheque);
            } else {
                planillaPagoCheque = em.merge(planillaPagoCheque);
            }
            return planillaPagoCheque;
        } catch (Exception e) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, null, e);
            return new PlanillaPagoCheque();
        }
    }

    public List<DatosProveDto> getProveedoresPorIdRequerimiento(BigDecimal idRequerimiento) {
        Query q = em.createNamedQuery("PagoProve.ProveedoresByIdRequerimiento", DatosProveDto.class);
        q.setParameter(1, idRequerimiento);
        return q.getResultList();
    }

    public List<DetalleRequerimiento> getLstProveedorByIdRequerimiento(BigDecimal idRequerimiento, String numeroNit) {
        Query q = em.createQuery("select d from DetalleRequerimiento d where d.idRequerimiento.idRequerimiento =:idReq and d.numeroNit=:numeroNit and d.idDetRequerimiento not in(select p.idDetalleDocPago.idDetRequerimiento.idDetRequerimiento from DetallePlanilla p where p.estadoEliminacion = 0 and p.idPlanilla.idRequerimiento.idRequerimiento=:idReq) order by d.razonSocial, d.codigoEntidad", DetalleRequerimiento.class);
        q.setParameter("idReq", idRequerimiento);
        q.setParameter("numeroNit", numeroNit);
        return q.getResultList();
    }

    public List<DetalleRequerimiento> getDetRequerimientoPendienteByEntFinan(BigDecimal idRequerimiento, String nomEntFinanciera) {
        List<DetalleRequerimiento> lst;
        Query q = em.createQuery("select d from DetalleRequerimiento d where d.activo=0 and d.idRequerimiento.idRequerimiento =:idReq and d.nombreEntFinan=:nomEntFinanciera and d.idDetRequerimiento not in(select p.idDetalleDocPago.idDetRequerimiento.idDetRequerimiento from DetallePlanilla p where p.estadoEliminacion = 0 and p.idPlanilla.idRequerimiento.idRequerimiento=:idReq1 ) order by d.razonSocial, d.codigoEntidad", DetalleRequerimiento.class);
        q.setParameter("idReq", idRequerimiento);
        q.setParameter("idReq1", idRequerimiento);
        q.setParameter("nomEntFinanciera", nomEntFinanciera);
        lst = q.getResultList();

        return lst;
    }

    public List<DetalleRequerimiento> getDetRequerimientoPendiente(BigDecimal idRequerimiento) {
        List<DetalleRequerimiento> lst;
        Query q = em.createQuery("select d from DetalleRequerimiento d where d.activo=0 and d.idRequerimiento.idRequerimiento =:idReq and d.idDetRequerimiento not in(select p.idDetalleDocPago.idDetRequerimiento.idDetRequerimiento from DetallePlanilla p where p.estadoEliminacion = 0 and p.idPlanilla.idRequerimiento.idRequerimiento=:idReq1 ) order by d.razonSocial, d.codigoEntidad", DetalleRequerimiento.class);
        q.setParameter("idReq", idRequerimiento);
        q.setParameter("idReq1", idRequerimiento);
        lst = q.getResultList();

        return lst;
    }

    public Boolean isPlanillaConReintegro(BigDecimal idPlanilla) {
        Query q = em.createNamedQuery("PagoProve.IsPlanillaConReintegro", DatosProveDto.class);
        q.setParameter(1, idPlanilla);
        return ((DatosProveDto) q.getSingleResult()).getIdRow().intValue() != 0;
    }

    /**
     * Devuelve todos los NIT y RazonSocial de los contratos asociados a una
     * Planilla de Pago
     *
     * @param idPlanilla
     * @return
     */
    public List<DatosProveDto> getLstNitProveeByIdPlanilla(BigDecimal idPlanilla) {
        Query q = em.createNamedQuery("PagoProve.NitProveedorByIdPlanilla", DatosProveDto.class);
        q.setParameter(1, idPlanilla);
        return q.getResultList();
    }

    /**
     * Verifica que el monto a modificar no sobrepase el monto total solicitado
     * para el centro escolar.
     *
     * @param codigoEntidad
     * @param IdRequerimiento
     * @param idDetRequerimiento
     * @param montoNuevo
     * @return true montos validos
     */
    synchronized public Boolean validarMontoRequerido(String codigoEntidad, BigDecimal IdRequerimiento, BigDecimal idDetRequerimiento, BigDecimal montoNuevo) {
        BigDecimal montoReqCodEntOriginal;
        BigDecimal montoReqCodEntNuevo;

        Query q = em.createQuery("SELECT SUM(d.montoTotal) FROM DetalleRequerimiento d WHERE d.codigoEntidad=:codEnt and d.idRequerimiento.idRequerimiento=:idReq");
        q.setParameter("codEnt", codigoEntidad);
        q.setParameter("idReq", IdRequerimiento);
        montoReqCodEntOriginal = (BigDecimal) q.getSingleResult();

        q = em.createNativeQuery("SELECT NVL(SUM(DDP.MONTO_ACTUAL),0) FROM DETALLE_DOC_PAGO DDP, DETALLE_REQUERIMIENTO DR WHERE DR.CODIGO_ENTIDAD = ?1 AND DR.ID_REQUERIMIENTO = ?2 AND DDP.ID_DET_REQUERIMIENTO <> ?3 AND DR.ID_DET_REQUERIMIENTO = DDP.ID_DET_REQUERIMIENTO");
        q.setParameter(1, codigoEntidad);
        q.setParameter(2, IdRequerimiento);
        q.setParameter(3, idDetRequerimiento);
        montoReqCodEntNuevo = (q.getSingleResult() == null) ? BigDecimal.ZERO : ((BigDecimal) q.getSingleResult());

        try {
            return montoReqCodEntOriginal.compareTo(montoReqCodEntNuevo.add(montoNuevo)) != -1;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(PagoProveedoresEJB.class
                    .getName()).log(Level.WARNING, "Ah ocurrido un error en la validaci\u00f3n de los montos. Centro escolar{0}, idRequerimiento: {1}, idDetRequerimiento: {2}, montoNuevo: {3}", new Object[]{codigoEntidad, IdRequerimiento, idDetRequerimiento, montoNuevo});
            return false;
        }
    }

    public PlanillaPagoCheque getPlanillaPagoCheque(PlanillaPago planillaPago, Short aFavorDe) {
        Query q = em.createQuery("SELECT p FROM PlanillaPagoCheque p WHERE p.idPlanilla=:idPlanilla AND p.aFavorDe=:aFavorDe AND p.estadoEliminacion=0", PlanillaPagoCheque.class);
        q.setParameter("idPlanilla", planillaPago);
        q.setParameter("aFavorDe", aFavorDe);
        return q.getResultList().isEmpty() ? new PlanillaPagoCheque() : (PlanillaPagoCheque) q.getSingleResult();
    }

    public PlanillaPago getPlanillaPago(BigDecimal idPlanilla) {
        return em.find(PlanillaPago.class, idPlanilla);
    }

    public ReintegroRequerimiento getReintegroByIdReq(BigDecimal idReq) {
        Query q = em.createQuery("SELECT r FROM ReintegroRequerimiento r WHERE r.idRequerimiento.idRequerimiento =:idReq", ReintegroRequerimiento.class);
        q.setParameter("idReq", idReq);

        if (q.getResultList().isEmpty()) {
            return new ReintegroRequerimiento();
        } else {
            return (ReintegroRequerimiento) q.getSingleResult();
        }
    }

    public void guardarDetalleDocPago(DetalleDocPago detalleDocPago) {
        if (detalleDocPago.getIdDetalleDocPago() == null) {
            em.persist(detalleDocPago);
        } else {
            detalleDocPago = em.merge(detalleDocPago);

            if (!detalleDocPago.getDetallePlanillaList().isEmpty()) {

                DetallePlanilla detPla = detalleDocPago.getDetallePlanillaList().get(0);

                if (detalleDocPago.getContratoModif() == 1) {
                    detPla.setCantidadActual(detalleDocPago.getCantidadActual());
                    detPla.setMontoActual(detalleDocPago.getMontoActual());
                } else {
                    detPla.setCantidadActual(detPla.getCantidadOriginal());
                    detPla.setMontoActual(detPla.getMontoOriginal());
                }

                em.merge(detPla);
            }
        }
    }

    public String getEMailProveedorByNit(String numeroNit) {
        Query q = em.createQuery("SELECT e.correoElectronico FROM Empresa e where e.numeroNit = :numeroNit");
        q.setParameter("numeroNit", numeroNit);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (String) q.getSingleResult();
        }
    }

    public String getEMailEntidadFinancieraById(String codEntidadFinan) {
        Query q = em.createQuery("SELECT e.eMail FROM EntidadFinanciera e where e.codEntFinanciera=:codEnt");
        q.setParameter("codEnt", codEntidadFinan);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (String) q.getSingleResult();
        }
    }

    public List<DetallePlanilla> getLstDetallePlanillaByIdPlanillaAndNit(BigDecimal idPlanilla, String nit) {
        Query q = em.createQuery("SELECT d FROM DetallePlanilla d WHERE d.idPlanilla.idPlanilla=:idPlanilla and d.idDetalleDocPago.idDetRequerimiento.numeroNit=:nit", DetallePlanilla.class);
        q.setParameter("idPlanilla", idPlanilla);
        q.setParameter("nit", nit);
        return q.getResultList();
    }

    public Boolean isDetalleRequerimeintoEnPlanilla(BigDecimal idDetRequerimiento) {
        Query q = em.createQuery("SELECT d FROM DetallePlanilla d WHERE d.idDetalleDocPago.idDetRequerimiento.idDetRequerimiento=:idDet and d.estadoEliminacion=0 and d.idDetalleDocPago.estadoEliminacion=0");
        q.setParameter("idDet", idDetRequerimiento);
        return !q.getResultList().isEmpty();
    }

    public List<TipoDocPago> findTipoDocPagoEntities() {
        Query q = em.createQuery("SELECT t FROM TipoDocPago t", TipoDocPago.class);
        return q.getResultList();
    }

    public void eliminarPlanilla(BigDecimal idPlanilla, String usuario) {
        Query q = em.createNativeQuery("UPDATE planilla_pago_cheque SET estado_eliminacion=1, fecha_eliminacion=sysdate, usuario_modificacion=?1 WHERE id_planilla=?2");
        q.setParameter(1, usuario);
        q.setParameter(2, idPlanilla);
        q.executeUpdate();

        q = em.createNativeQuery("Delete Detalle_Planilla WHERE id_Planilla=?1");
        q.setParameter(1, idPlanilla);
        q.executeUpdate();

        q = em.createNativeQuery("UPDATE Planilla_Pago SET estado_Eliminacion=1, fecha_Eliminacion=sysdate, usuario_Modificacion=?1 WHERE id_Planilla=?2");
        q.setParameter(1, usuario);
        q.setParameter(2, idPlanilla);
        q.executeUpdate();
    }

    public List<DatosBusquedaPlanillaDto> buscarPlanillas(BigDecimal idPlanilla, BigDecimal monto, String numeroNit, String nombreEntFinan, Integer idProcesoAdq, String numeroCheque, Date fechaCheque) {
        String strWhere = Constantes.addCampoToWhere("", "ID_PLANILLA", idPlanilla);
        strWhere = Constantes.addCampoToWhere(strWhere, "MONTO_ACTUAL", monto);
        strWhere = Constantes.addCampoToWhere(strWhere, "NUMERO_NIT", numeroNit);
        strWhere = Constantes.addCampoToWhere(strWhere, "upper(NOMBRE_ENT_FINAN)", nombreEntFinan.toUpperCase());
        strWhere = Constantes.addCampoToWhere(strWhere, "id_proceso_adq", idProcesoAdq);
        strWhere = Constantes.addCampoToWhere(strWhere, "numero_cheque", numeroCheque);
        strWhere = Constantes.addCampoToWhere(strWhere, "fecha_cheque", fechaCheque);
        //System.out.println("QUERY\n=========================" + Constantes.QUERY_PAGOS_BUSQUEDA_PLANILLA + strWhere + " ORDER BY ID_PLANILLA\n================");
        Query q = em.createNativeQuery(Constantes.QUERY_PAGOS_BUSQUEDA_PLANILLA + strWhere + " ORDER BY ID_PLANILLA", DatosBusquedaPlanillaDto.class);
        return q.getResultList();
    }

    public boolean isContratoConModificativa(BigDecimal idContrato) {
        Query q = em.createQuery("SELECT c.modificativa FROM ContratosOrdenesCompras c Where c.idContrato=:idCon and c.estadoEliminacion = 0");
        q.setParameter("idCon", idContrato);
        return ((Short) q.getSingleResult() == 1);
    }

    public ResolucionesModificativas getUltimaModificativa(BigDecimal idContrato) {
        Query q = em.createQuery("SELECT r FROM ResolucionesModificativas r WHERE r.idContrato.idContrato=:idCon and r.idEstadoReserva=2", ResolucionesModificativas.class);
        q.setParameter("idCon", idContrato);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (ResolucionesModificativas) q.getSingleResult();
        }
    }

    public String getNombrePagadorByCodDepa(String codDepa) {
        Query q = em.createNativeQuery("SELECT PAGADOR_DEPARTAMENTAL FROM DATOS_DEPA_PAGADURIA WHERE estado_eliminacion = 0 and codigo_departamento=?1");
        q.setParameter(1, codDepa);
        return (String) q.getSingleResult();
    }

    public List<PreCargaDto> getLstPreCargaByIdDetProcesoAdq(Integer idDetProcesoAdq) {
        Query q = em.createNamedQuery("PagoProve.RptPreCargaByIdDetProcesoAdq");
        q.setParameter(1, idDetProcesoAdq);
        return q.getResultList();
    }

    public List<PreCarga> findPreCargaByIdDetProcesoAdq(Integer idDetProcesoAdq) {
        Query q = em.createQuery("SELECT p FROM PreCarga p WHERE p.idDetProcesoAdq.idDetProcesoAdq = :idDetProcesoAdq ORDER BY p.idPrecarga, p.idDetProcesoAdq", PreCarga.class);
        q.setParameter("idDetProcesoAdq", idDetProcesoAdq);
        return q.getResultList();
    }

    public void guardarPreCarga(PreCarga preCarga) {
        if (preCarga.getIdPrecarga() == null) {
            em.persist(preCarga);
        } else {
            em.merge(preCarga);
        }
    }

    public List<DetallePreCarga> getLstDetallePreCargaByIdPreCarga(BigDecimal idPrecarga) {
        Query q = em.createQuery("SELECT d FROM DetallePreCarga d WHERE d.idPrecarga.idPrecarga=:idPrecarga ORDER BY d.codigoDepartamento, d.codigoMunicipio", DetallePreCarga.class);
        q.setParameter("idPrecarga", idPrecarga);
        return q.getResultList();
    }

    public int guardarNotificacionPago(ListaNotificacionPago listaNotificacionPago) {
        try {
            if (listaNotificacionPago.getIdLista() == null) {
                em.persist(listaNotificacionPago);
                return 1;
            } else {
                em.merge(listaNotificacionPago);
                return 2;
            }
        } catch (Exception e) {
            return 3;
        }
    }

    public List<ListaNotificacionPago> getLstNotificacionPagosByCodDepa(String codigoDepartamento) {
        Query q = em.createQuery("SELECT l FROM ListaNotificacionPago l WHERE l.codigoDepartamento=:codDepa ORDER BY l.idLista", ListaNotificacionPago.class);
        q.setParameter("codDepa", codigoDepartamento);
        return q.getResultList();
    }
}
