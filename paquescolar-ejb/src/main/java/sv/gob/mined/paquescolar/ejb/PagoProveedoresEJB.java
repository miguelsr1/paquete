/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.DetalleDocPago;
import sv.gob.mined.paquescolar.model.DetallePlanilla;
import sv.gob.mined.paquescolar.model.DetalleRequerimiento;
import sv.gob.mined.paquescolar.model.PlanillaPago;
import sv.gob.mined.paquescolar.model.PlanillaPagoCheque;
import sv.gob.mined.paquescolar.model.ReintegroRequerimiento;
import sv.gob.mined.paquescolar.model.ResolucionesModificativas;
import sv.gob.mined.paquescolar.model.TipoDocPago;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.DatosBusquedaPlanillaDto;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.DatosProveDto;
import sv.gob.mined.paquescolar.util.StringUtils;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class PagoProveedoresEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    public List<DatosProveDto> getLstDatosProveDto(String anhoPago, String numeroNit, String codigoDepartamento) {
        Query q = em.createNamedQuery("PagoProve.ConstanciaRentencionByAnhoAndNitEmp", DatosProveDto.class);
        q.setParameter(1, Integer.parseInt(anhoPago));
        q.setParameter(2, numeroNit);
        q.setParameter(3, codigoDepartamento);
        return q.getResultList();
    }

    public List<DatosProveDto> getDatosF910(String codigoDepartamento, Integer anho) {
        Query q = em.createNamedQuery("PagoProve.FileF910", DatosProveDto.class);
        q.setParameter(1, codigoDepartamento);
        q.setParameter(1, anho);
        return q.getResultList();
    }

    public List<DatosProveDto> getDatosRptRentaMensual(String codigoDepartamento, Integer idMesPago, Integer anhoPago) {
        Query q = em.createNamedQuery("PagoProve.ReporteRentaMensual", DatosProveDto.class);
        q.setParameter(1, codigoDepartamento);
        q.setParameter(2, idMesPago);
        q.setParameter(3, anhoPago);
        return q.getResultList();
    }

    public List<DatosProveDto> getDatosRptRentaMensualByRequerimiento(String codigoDepartamento, Integer idMesPago, Integer anhoPago, String formatoRequerimiento) {
        Query q = em.createNamedQuery("PagoProve.ReporteRentaMensualByRequerimiento", DatosProveDto.class);
        q.setParameter(1, codigoDepartamento);
        q.setParameter(2, idMesPago);
        q.setParameter(3, anhoPago);
        q.setParameter(4, formatoRequerimiento);
        return q.getResultList();
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

        return montoReqCodEntOriginal.compareTo(montoReqCodEntNuevo.add(montoNuevo)) != -1;
    }

    public PlanillaPagoCheque getPlanillaPagoCheque(PlanillaPago planillaPago, Short aFavorDe) {
        Query q = em.createQuery("SELECT p FROM PlanillaPagoCheque p WHERE p.idPlanilla=:idPlanilla AND p.aFavorDe=:aFavorDe AND p.estadoEliminacion=0", PlanillaPagoCheque.class);
        q.setParameter("idPlanilla", planillaPago);
        q.setParameter("aFavorDe", aFavorDe);
        return q.getResultList().isEmpty() ? new PlanillaPagoCheque() : (PlanillaPagoCheque) q.getSingleResult();
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
        Query q = em.createQuery("UPDATE PlanillaPagoCheque p SET p.estadoEliminacion=1, p.fechaEliminacion=sysdate, p.usuarioModificacion=:usu WHERE p.idPlanilla.idPlanilla=:idPla");
        q.setParameter("fecha", new Date());
        q.setParameter("usu", usuario);
        q.setParameter("idPla", idPlanilla);
        q.executeUpdate();

        q = em.createQuery("Delete DetallePlanilla WHERE d.idPlanilla.idPlanilla=:idPla");
        q.setParameter("idPla", idPlanilla);
        q.executeUpdate();

        q = em.createQuery("UPDATE PlanillaPago p SET p.estadoEliminacion=1, p.fechaEliminacion=:fecha, p.usuarioModificacion=:usu WHERE p.idPlanilla=:idPla");
        q.setParameter("fecha", new Date());
        q.setParameter("usu", usuario);
        q.setParameter("idPla", idPlanilla);
        q.executeUpdate();
    }

    public List<DatosBusquedaPlanillaDto> buscarPlanillas(BigDecimal idPlanilla, BigDecimal monto, String numeroNit, String nombreEntFinan, Integer idProcesoAdq) {
        String strWhere = StringUtils.addCampoToWhere("", "pp.ID_PLANILLA", idPlanilla);
        strWhere = StringUtils.addCampoToWhere(strWhere, "dp.MONTO_ACTUAL", monto);
        strWhere = StringUtils.addCampoToWhere(strWhere, "dr.NUMERO_NIT", numeroNit);
        strWhere = StringUtils.addCampoToWhere(strWhere, "dr.NOMBRE_ENT_FINAN", nombreEntFinan);
        strWhere = StringUtils.addCampoToWhere(strWhere, "pa.id_proceso_adq", idProcesoAdq);
        Query q = em.createNativeQuery(StringUtils.QUERY_PAGOS_BUSQUEDA_PLANILLA + strWhere + " ORDER BY pp.ID_PLANILLA", DatosBusquedaPlanillaDto.class);
        return q.getResultList();
    }

    public boolean isContratoConModificativa(BigDecimal idContrato) {
        Query q = em.createQuery("SELECT c.modificativa FROM ContratosOrdenesCompras c Where c.idContrato=:idCon and c.estadoEliminacion = 0");
        q.setParameter("idCon", idContrato);
        return ((Short) q.getSingleResult() == 1);
    }

    public ResolucionesModificativas getUltimaModificativa(BigDecimal idContrato) {
        Query q = em.createQuery("SELECT r FROM ResolucionesModificativas r WHERE r.idContrato.idContrato=:idCon and r.idResModifPadre is null ", ResolucionesModificativas.class);
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
}
