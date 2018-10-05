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
import sv.gob.mined.paquescolar.model.pojos.SaldoProveedorDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ContratoDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.DetalleItemDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ParticipanteDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.VwRptContratoJurCabecera;
import sv.gob.mined.paquescolar.model.view.VwRptPagare;

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
            resolucionesAdjudicativas.setContratosOrdenesComprasList(new ArrayList<ContratosOrdenesCompras>());
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
                    }
                } else //DIGITADA -> ANULADA y REVERTIDA -> ANULADA 
                if ((resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 1 && estadoReserva.intValue() == 4)
                        || (resAdj.getIdResolucionAdj().intValue() == 3 && estadoReserva.intValue() == 4)) {
                    param = aplicarSaldos(resAdj, techoCE, estadoReserva, cantidad, comentarioReversion, usuario, param);
                } else //APLICADA -> REVERTIDA
                if (resAdj.getIdEstadoReserva().getIdEstadoReserva().intValue() == 2 && estadoReserva.intValue() == 3) {
                    param = aplicarSaldos(resAdj, techoCE, estadoReserva, cantidad, comentarioReversion, usuario, param);
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
            Query query = em.createQuery("SELECT c FROM CapaInstPorRubro c WHERE c.idMuestraInteres.idEmpresa=:idEmpresa and c.idMuestraInteres.idDetProcesoAdq.idProcesoAdq.idAnho=:idAnho and c.estadoEliminacion = 0 and c.idMuestraInteres.estadoEliminacion=0 and c.idMuestraInteres.idEmpresa.estadoEliminacion=0", CapaInstPorRubro.class);
            query.setParameter("idEmpresa", res.getIdParticipante().getIdEmpresa());
            query.setParameter("idAnho", res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho());

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
        } catch (Exception e) {
            param.put("error", "Se ha generado un error en la aplicación de fondos.");
            Logger.getLogger(ResolucionAdjudicativaEJB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
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
        //String sql = String.format("SELECT NVL(FN_GETADJPROVEEDOR(%d, %d), 0) FROM EMPRESA WHERE id_empresa=%d",
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
        //String sql = String.format("SELECT NVL(FN_GETSALDOPROVEEDOR(%d, %d, %d), 0) FROM EMPRESA WHERE id_empresa=%d",
        String sql = String.format("SELECT NVL(FN_VERIFICAR_SALDO_PROVEEDOR(%d, %d), 0) FROM DUAL",
                res.getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq(),
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
        List<VwRptPagare> lstPagare = new ArrayList();
        try {
            Query query = em.createNativeQuery("SELECT * FROM vw_rpt_pagare WHERE id_resolucion_adj=?1 and id_contrato=?2");
            query.setParameter(1, idResolucion);
            query.setParameter(2, idContrato);

            List lst = query.getResultList();

            for (Object object : lst) {
                Object[] datos = (Object[]) object;
                VwRptPagare v = new VwRptPagare();
                v.setCiudadFirma(datos[0] != null ? datos[0].toString() : null);
                v.setFechaEmision(datos[1] != null ? datos[1].toString() : null);
                v.setIdContrato(datos[2] != null ? new BigInteger(datos[2].toString()) : null);
                v.setInicialesModalidad(datos[3] != null ? datos[3].toString() : null);
                v.setValor(datos[4] != null ? new BigDecimal(datos[4].toString()) : null);
                v.setDescripcionRubro(datos[5] != null ? datos[5].toString() : null);
                v.setNumeroContrato(datos[6] != null ? datos[6].toString() : null);
                v.setNombre(datos[7] != null ? datos[7].toString() : null);
                v.setNombreRepresentante(datos[8] != null ? datos[8].toString() : null);
                v.setDomicilioRepresentante(datos[9] != null ? datos[9].toString() : null);
                v.setNumeroTelefono(datos[10] != null ? datos[10].toString() : null);
                v.setNumeroDui(datos[11] != null ? datos[11].toString() : null);
                v.setNumeroNit(datos[12] != null ? datos[12].toString() : null);
                v.setUsuarioInsercion(datos[13] != null ? datos[13].toString() : null);
                v.setIdResolucionAdj(datos[14] != null ? new BigInteger(datos[14].toString()) : null);
                v.setRazonSocial(datos[15] != null ? datos[15].toString() : null);
                v.setAnhoContrato(datos[16] != null ? datos[16].toString() : null);

                lstPagare.add(v);
            }

            return lstPagare;
        } finally {
        }
    }

    public List<VwRptPagare> generarRptGarantiaByIdContrato(BigDecimal idContrato) {
        List<VwRptPagare> lstPagare = new ArrayList();
        try {
            Query query = em.createNativeQuery("SELECT * FROM vw_rpt_pagare WHERE id_contrato=?1");
            query.setParameter(1, idContrato);

            List lst = query.getResultList();

            for (Object object : lst) {
                Object[] datos = (Object[]) object;
                VwRptPagare v = new VwRptPagare();
                v.setCiudadFirma(datos[0] != null ? datos[0].toString() : null);
                v.setFechaEmision(datos[1] != null ? datos[1].toString() : null);
                v.setIdContrato(datos[2] != null ? new BigInteger(datos[2].toString()) : null);
                v.setInicialesModalidad(datos[3] != null ? datos[3].toString() : null);
                v.setValor(datos[4] != null ? new BigDecimal(datos[4].toString()) : null);
                v.setDescripcionRubro(datos[5] != null ? datos[5].toString() : null);
                v.setNumeroContrato(datos[6] != null ? datos[6].toString() : null);
                v.setNombre(datos[7] != null ? datos[7].toString() : null);
                v.setNombreRepresentante(datos[8] != null ? datos[8].toString() : null);
                v.setDomicilioRepresentante(datos[9] != null ? datos[9].toString() : null);
                v.setNumeroTelefono(datos[10] != null ? datos[10].toString() : null);
                v.setNumeroDui(datos[11] != null ? datos[11].toString() : null);
                v.setNumeroNit(datos[12] != null ? datos[12].toString() : null);
                v.setUsuarioInsercion(datos[13] != null ? datos[13].toString() : null);
                v.setIdResolucionAdj(datos[14] != null ? new BigInteger(datos[14].toString()) : null);
                v.setRazonSocial(datos[15] != null ? datos[15].toString() : null);
                v.setAnhoContrato(datos[16] != null ? datos[16].toString() : null);

                lstPagare.add(v);
            }

            return lstPagare;
        } finally {
        }
    }

    public List<VwRptContratoJurCabecera> generarContrato(BigDecimal idContrato, BigDecimal idRubro) {
        List<VwRptContratoJurCabecera> lstContrato = new ArrayList();
        try {
            ContratosOrdenesCompras contratoEntity = em.find(ContratosOrdenesCompras.class, idContrato);

            Query query = em.createNativeQuery("SELECT * FROM VW_RPT_CONTRATO_JUR_CABECERA  WHERE ID_CONTRATO=?1");
            query.setParameter(1, idContrato);

            List lst = query.getResultList();
            VwRptContratoJurCabecera cabeceraContrato = new VwRptContratoJurCabecera();

            for (Object object : lst) {
                Object[] datos = (Object[]) object;

                cabeceraContrato.setValor(new BigDecimal(datos[0] != null ? datos[0].toString() : null));
                cabeceraContrato.setNumeroContrato(datos[1] != null ? datos[1].toString() : null);
                cabeceraContrato.setRazonSocial(datos[2] != null ? datos[2].toString() : null);
                cabeceraContrato.setDescripcionRubro(datos[3] != null ? datos[3].toString() : null);
                cabeceraContrato.setNombreMiembro(datos[4] != null ? datos[4].toString() : null);
                cabeceraContrato.setInicialesModalidad(datos[5] != null ? datos[5].toString() : null);
                cabeceraContrato.setNombre(datos[6] != null ? datos[6].toString() : null);
                cabeceraContrato.setPlazoPrevistoEntrega(new BigInteger(datos[7] != null ? datos[7].toString() : null));
                cabeceraContrato.setCiudadFirma(datos[8] != null ? datos[8].toString() : null);
                cabeceraContrato.setDireccionCe(datos[9] != null ? datos[9].toString() : null);
                cabeceraContrato.setTelefonoCe(datos[10] != null ? datos[10].toString() : null);
                cabeceraContrato.setFaxCe(datos[11] != null ? datos[11].toString() : null);
                cabeceraContrato.setDireccionEmp(datos[12] != null ? datos[12].toString() : null);
                cabeceraContrato.setTelefonoEmp(datos[13] != null ? datos[13].toString() : null);
                cabeceraContrato.setCelularEmp(datos[14] != null ? datos[14].toString() : null);
                cabeceraContrato.setFaxEmp(datos[15] != null ? datos[15].toString() : null);
                cabeceraContrato.setNumeroNit(datos[16] != null ? datos[16].toString() : null);
                cabeceraContrato.setUsuarioInsercion(datos[17] != null ? datos[17].toString() : null);
                cabeceraContrato.setAnhoContrato(datos[18] != null ? datos[18].toString() : null);
                cabeceraContrato.setFechaEmision(datos[19] != null ? datos[19].toString() : null);
                cabeceraContrato.setNombrePresentante(datos[20] != null ? datos[20].toString() : null);
                cabeceraContrato.setCodigoEntidad(datos[21] != null ? datos[21].toString() : null);
                cabeceraContrato.setIdContrato(new BigInteger(datos[22] != null ? datos[22].toString() : null));
                cabeceraContrato.setDistribuidor(new BigInteger(datos[24] != null ? datos[24].toString() : null));
                cabeceraContrato.setMostrarLeyenda(new BigInteger(datos[25] != null ? datos[25].toString() : null));
                cabeceraContrato.setNombreDepartamento(datos[26] != null ? datos[26].toString() : null);
                cabeceraContrato.setNombreMunicipio(datos[27] != null ? datos[27].toString() : null);
                cabeceraContrato.setNumeroDui(datos[28] != null ? datos[28].toString() : null);
                cabeceraContrato.setNitRepresentante(datos[29] != null ? datos[29].toString() : null);

                lstContrato.add(cabeceraContrato);
            }

            if (!lstContrato.isEmpty()) {
                query = em.createQuery("SELECT DISTINCT c.idResolucionAdj.idParticipante.idEmpresa.idPersoneria.idPersoneria, c.idResolucionAdj.idParticipante.idEmpresa.distribuidor FROM ContratosOrdenesCompras c WHERE c.idResolucionAdj=:idResolucion and c.estadoEliminacion=0");
                query.setParameter("idResolucion", contratoEntity.getIdResolucionAdj());

                Object idPersoneria = query.getSingleResult();
                Object[] datos = (Object[]) idPersoneria;

                if (datos[1].toString().equals("1")) {
                    //DISTRIBUIDOR
                    query = em.createNativeQuery("SELECT * FROM VW_RPT_DET_ITEMS_DIST WHERE ID_RESOLUCION_ADJ=?1 ORDER BY TO_NUMBER(NO_ITEM)");
                } else {
                    //FABRICANTE
                    query = em.createNativeQuery("SELECT * FROM VW_RPT_DET_ITEMS_FAB WHERE ID_RESOLUCION_ADJ=?1 ORDER BY TO_NUMBER(NO_ITEM)");
                }

                query.setParameter(1, contratoEntity.getIdResolucionAdj().getIdResolucionAdj());
                lst = query.getResultList();

                List<DetalleItemDto> lstDetalle = new ArrayList();
                List<DetalleItemDto> lstDetalleBac = new ArrayList();

                for (Object object : lst) {
                    datos = (Object[]) object;
                    DetalleItemDto detalleContrato = new DetalleItemDto();
                    detalleContrato.setNoItem(datos[0] != null ? datos[0].toString() : null);
                    detalleContrato.setConsolidadoEspTec(datos[1] != null ? datos[1].toString() : null);
                    detalleContrato.setNombreProveedor(datos[2] != null ? datos[2].toString() : null);
                    detalleContrato.setCantidad(new BigInteger(datos[3] != null ? datos[3].toString() : "0"));
                    detalleContrato.setPrecioUnitario(new BigDecimal(datos[4] != null ? datos[4].toString() : "0"));
                    detalleContrato.setSubTotal(new BigDecimal(datos[5] != null ? datos[5].toString() : "0"));
                    detalleContrato.setIdContrato(new BigInteger(datos[6] != null ? datos[6].toString() : "0"));
                    detalleContrato.setIdResolucionAdj(new BigInteger(datos[7] != null ? datos[7].toString() : "0"));

                    if (idRubro.compareTo(BigDecimal.ONE) == 0 && detalleContrato.getConsolidadoEspTec().contains("BACHILLERATO")) {
                        lstDetalleBac.add(detalleContrato);
                    } else {
                        lstDetalle.add(detalleContrato);
                    }
                }

                cabeceraContrato.setLstDetalleItems(lstDetalle);
                cabeceraContrato.setLstDetalleItemsBac(lstDetalleBac);
            }

            return lstContrato;
        } finally {
        }
    }

    public List<VwRptContratoJurCabecera> generarContrato(BigDecimal idContrato, BigDecimal idRubro, Boolean primero) {
        List<VwRptContratoJurCabecera> lstContrato = new ArrayList();
        try {
            ContratosOrdenesCompras contratoEntity = em.find(ContratosOrdenesCompras.class, idContrato);

            Query query = em.createNativeQuery("SELECT * FROM VW_RPT_CONTRATO_JUR_CABECERA  WHERE ID_CONTRATO=?1 and primero=?2");
            query.setParameter(1, idContrato);
            query.setParameter(2, primero ? (short) 1 : (short) 2);

            List lst = query.getResultList();
            VwRptContratoJurCabecera cabeceraContrato = new VwRptContratoJurCabecera();

            for (Object object : lst) {
                Object[] datos = (Object[]) object;

                cabeceraContrato.setValor(new BigDecimal(datos[0] != null ? datos[0].toString() : null));
                cabeceraContrato.setNumeroContrato(datos[1] != null ? datos[1].toString() : null);
                cabeceraContrato.setRazonSocial(datos[2] != null ? datos[2].toString() : null);
                cabeceraContrato.setDescripcionRubro(datos[3] != null ? datos[3].toString() : null);
                cabeceraContrato.setNombreMiembro(datos[4] != null ? datos[4].toString() : null);
                cabeceraContrato.setInicialesModalidad(datos[5] != null ? datos[5].toString() : null);
                cabeceraContrato.setNombre(datos[6] != null ? datos[6].toString() : null);
                cabeceraContrato.setPlazoPrevistoEntrega(new BigInteger(datos[7] != null ? datos[7].toString() : null));
                cabeceraContrato.setCiudadFirma(datos[8] != null ? datos[8].toString() : null);
                cabeceraContrato.setDireccionCe(datos[9] != null ? datos[9].toString() : null);
                cabeceraContrato.setTelefonoCe(datos[10] != null ? datos[10].toString() : null);
                cabeceraContrato.setFaxCe(datos[11] != null ? datos[11].toString() : null);
                cabeceraContrato.setDireccionEmp(datos[12] != null ? datos[12].toString() : null);
                cabeceraContrato.setTelefonoEmp(datos[13] != null ? datos[13].toString() : null);
                cabeceraContrato.setCelularEmp(datos[14] != null ? datos[14].toString() : null);
                cabeceraContrato.setFaxEmp(datos[15] != null ? datos[15].toString() : null);
                cabeceraContrato.setNumeroNit(datos[16] != null ? datos[16].toString() : null);
                cabeceraContrato.setUsuarioInsercion(datos[17] != null ? datos[17].toString() : null);
                cabeceraContrato.setAnhoContrato(datos[18] != null ? datos[18].toString() : null);
                cabeceraContrato.setFechaEmision(datos[19] != null ? datos[19].toString() : null);
                cabeceraContrato.setNombrePresentante(datos[20] != null ? datos[20].toString() : null);
                cabeceraContrato.setCodigoEntidad(datos[21] != null ? datos[21].toString() : null);
                cabeceraContrato.setIdContrato(new BigInteger(datos[22] != null ? datos[22].toString() : null));
                cabeceraContrato.setDistribuidor(new BigInteger(datos[24] != null ? datos[24].toString() : null));
                cabeceraContrato.setMostrarLeyenda(new BigInteger(datos[25] != null ? datos[25].toString() : null));
                cabeceraContrato.setNombreDepartamento(datos[26] != null ? datos[26].toString() : null);
                cabeceraContrato.setNombreMunicipio(datos[27] != null ? datos[27].toString() : null);
                cabeceraContrato.setNumeroDui(datos[28] != null ? datos[28].toString() : null);
                cabeceraContrato.setNitRepresentante(datos[29] != null ? datos[29].toString() : null);

                lstContrato.add(cabeceraContrato);
            }

            if (!lstContrato.isEmpty()) {
                query = em.createQuery("SELECT DISTINCT c.idResolucionAdj.idParticipante.idEmpresa.idPersoneria.idPersoneria, c.idResolucionAdj.idParticipante.idEmpresa.distribuidor FROM ContratosOrdenesCompras c WHERE c.idResolucionAdj=:idResolucion and c.estadoEliminacion=0");
                query.setParameter("idResolucion", contratoEntity.getIdResolucionAdj());

                Object idPersoneria = query.getSingleResult();
                Object[] datos = (Object[]) idPersoneria;

                if (datos[1].toString().equals("1")) {
                    //DISTRIBUIDOR
                    query = em.createNativeQuery("SELECT * FROM VW_RPT_DET_ITEMS_DIST WHERE ID_RESOLUCION_ADJ=?1 ORDER BY TO_NUMBER(NO_ITEM)");
                } else {
                    //FABRICANTE
                    query = em.createNativeQuery("SELECT * FROM VW_RPT_DET_ITEMS_FAB WHERE ID_RESOLUCION_ADJ=?1 ORDER BY TO_NUMBER(NO_ITEM)");
                }

                query.setParameter(1, contratoEntity.getIdResolucionAdj().getIdResolucionAdj());
                lst = query.getResultList();

                List<DetalleItemDto> lstDetalle = new ArrayList();
                List<DetalleItemDto> lstDetalleBac = new ArrayList();

                for (Object object : lst) {
                    datos = (Object[]) object;
                    DetalleItemDto detalleContrato = new DetalleItemDto();
                    detalleContrato.setNoItem(datos[0] != null ? datos[0].toString() : null);
                    detalleContrato.setConsolidadoEspTec(datos[1] != null ? datos[1].toString() : null);
                    detalleContrato.setNombreProveedor(datos[2] != null ? datos[2].toString() : null);
                    detalleContrato.setCantidad(new BigInteger(datos[3] != null ? datos[3].toString() : "0"));
                    detalleContrato.setPrecioUnitario(new BigDecimal(datos[4] != null ? datos[4].toString() : "0"));
                    detalleContrato.setSubTotal(new BigDecimal(datos[5] != null ? datos[5].toString() : "0"));
                    detalleContrato.setIdContrato(new BigInteger(datos[6] != null ? datos[6].toString() : "0"));
                    detalleContrato.setIdResolucionAdj(new BigInteger(datos[7] != null ? datos[7].toString() : "0"));

                    if (idRubro.compareTo(BigDecimal.ONE) == 0 && detalleContrato.getConsolidadoEspTec().contains("BACHILLERATO")) {
                        lstDetalleBac.add(detalleContrato);
                    } else {
                        lstDetalle.add(detalleContrato);
                    }
                }

                cabeceraContrato.setLstDetalleItems(lstDetalle);
                cabeceraContrato.setLstDetalleItemsBac(lstDetalleBac);
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
        //Query q = em.createQuery("SELECT e FROM EstadoReserva e WHERE e.idEstadoReserva=5", EstadoReserva.class);
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
        eMailEJB.enviarMailDeError("Contratos Múltiple", "miguel.sanchez@mined.gob.sv", "Duplicidad de contratos para idResolucionAdj: " + idResolucionAdj);
    }
}
