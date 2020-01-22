/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.DetalleModificativa;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.EstadoReserva;
import sv.gob.mined.paquescolar.model.HistorialCamEstResAdj;
import sv.gob.mined.paquescolar.model.Participantes;
import sv.gob.mined.paquescolar.model.Persona;
import sv.gob.mined.paquescolar.model.ResolucionesAdjudicativas;
import sv.gob.mined.paquescolar.model.ResolucionesModificativas;
import sv.gob.mined.paquescolar.model.TechoRubroEntEdu;
import sv.gob.mined.paquescolar.model.TipoModifContrato;
import sv.gob.mined.paquescolar.model.pojos.VwDepartamentoModificativas;
import sv.gob.mined.paquescolar.model.pojos.VwDetalleModificativas;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ContratoDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.DetalleItemDto;
import sv.gob.mined.paquescolar.model.pojos.modificativa.VwBusquedaContratos;
import sv.gob.mined.paquescolar.model.pojos.modificativa.VwContratoModificatoria;
import sv.gob.mined.paquescolar.model.pojos.contratacion.VwRptContratoJurCabecera;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class ModificativaEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;
    @EJB
    ResolucionAdjudicativaEJB resolucionAdjudicativaEJB;

    public List<VwBusquedaContratos> getLstBusquedaContrato(DetalleProcesoAdq proceso, String codigoEntidad, String codigoDepartamento, String numeroNit, Date fecha1, Date fecha2, String numeroContrato, int op) {
        List<VwBusquedaContratos> lstContratos = new ArrayList();
        String where = "";
        if (proceso != null) {
            where += " ID_DET_PROCESO_ADQ=" + proceso.getIdDetProcesoAdq();
        }
        if (!codigoEntidad.isEmpty()) {
            where += (where.isEmpty() ? "" : " AND ").concat(" codigo_entidad='" + codigoEntidad + "'");
        }
        if (codigoDepartamento != null && !codigoDepartamento.equals("00")) {
            where += (where.isEmpty() ? "" : " AND ").concat(" codigo_departamento='" + codigoDepartamento + "'");
        }
        if (!numeroNit.isEmpty()) {
            where += (where.isEmpty() ? "" : " AND ").concat(" numero_nit='" + numeroNit + "'");
        }
        if (fecha1 != null) {
            where += (where.isEmpty() ? "" : " AND ").concat(" fecha_insercion > ?");
        }
        if (fecha2 != null) {
            where += (where.isEmpty() ? "" : " AND ").concat(" fecha_insercion < ?");
        }
        if (!numeroContrato.isEmpty()) {
            where += (where.isEmpty() ? "" : " AND ").concat(" numero_contrato='" + numeroContrato + "'");
        }

        switch (op) {
            case 1:
            case 2:
                where += (where.isEmpty() ? "" : " AND ").concat(" ID_ESTADO_RESERVA in (2,5)");
                break;
            case 3:
                where += (where.isEmpty() ? "" : " AND ").concat(" ID_ESTADO_RESERVA IN (2,4)");
                break;
        }

        try {
            Logger.getLogger(ModificativaEJB.class.getName()).log(Level.INFO, "SELECT * FROM VW_BUSQUEDA_CONTRATO WHERE {0}", where);
            Query q = em.createNativeQuery("SELECT * FROM VW_BUSQUEDA_CONTRATO WHERE " + where);
            if (fecha1 != null && fecha2 == null) {
                q.setParameter(1, fecha1);
            } else if (fecha1 == null && fecha2 != null) {
                q.setParameter(1, fecha2);
            } else if (fecha1 != null && fecha2 != null) {
                q.setParameter(1, fecha1);
                q.setParameter(2, fecha2);
            }

            List lst = q.getResultList();
            for (Object object : lst) {
                Object[] datos = (Object[]) object;
                VwBusquedaContratos contrato = new VwBusquedaContratos();
                contrato.setCodigoDepartamento(datos[0].toString());
                contrato.setNombreDepartamento(datos[1].toString());
                contrato.setCodigoEntidad(datos[2].toString());
                contrato.setNombreCe(datos[3].toString());
                contrato.setIdContrato(new BigDecimal(datos[4].toString()));
                contrato.setNumeroContrato(datos[5].toString());
                contrato.setRazonSocial(datos[6].toString());
                contrato.setNumeroNit(datos[7].toString());
                contrato.setDescripcionReserva(datos[10].toString());
                contrato.setIdResolucionAdj(new BigDecimal(datos[11].toString()));
                lstContratos.add(contrato);
            }
            return lstContratos;
        } catch (Exception ex) {
            Logger.getLogger(ModificativaEJB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public synchronized Boolean guardarModificatoria(ResolucionesModificativas modificativa, String usuario) {
        try {
            if (modificativa.getIdResolucionModif() == null) {
                modificativa.setIdEstadoReserva(BigDecimal.ONE);
                modificativa.setEstadoEliminacion(Short.parseShort("0"));
                modificativa.setUsuarioInsercion(usuario);
                modificativa.setFechaInsercion(new Date());
                em.persist(modificativa);
            } else {
                modificativa.setUsuarioModificacion(usuario);
                modificativa.setFechaModificacion(new Date());
                modificativa = em.merge(modificativa);
            }

            if (modificativa.getIdResModifPadre() == null) {
                updateResolucionPorModificativa(modificativa.getIdContrato().getIdResolucionAdj().getIdResolucionAdj());
            } else {
                modificativa.getIdResModifPadre().setIdEstadoReserva(new BigDecimal(5));
                modificativa.getIdResModifPadre().setFechaModificacion(new Date());
                modificativa.getIdResModifPadre().setUsuarioModificacion(usuario);
                ResolucionesModificativas resPadre = modificativa.getIdResModifPadre();
                guardarModificatoria(resPadre);
            }

            return false;
        } catch (NumberFormatException ex) {
            Logger.getLogger(ModificativaEJB.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ModificativaEJB.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
    }

    public void updateResolucionPorModificativa(BigDecimal idResolucionAdj) {
        //Cambiar el estado de la reserva de fondos de APLICADA A MODIFICADA
        Query q = em.createQuery("UPDATE ResolucionesAdjudicativas r SET r.idEstadoReserva=:idEstado WHERE r.idResolucionAdj = :idRes");
        q.setParameter("idEstado", em.find(EstadoReserva.class, new BigDecimal(5)));
        q.setParameter("idRes", idResolucionAdj);
        q.executeUpdate();

        //Cambiar el estado MODIFICATIVA del Contrato asociado a la Reserva de Fondos
        q = em.createQuery("UPDATE ContratosOrdenesCompras c SET c.modificativa=1 WHERE c.idResolucionAdj.idResolucionAdj = :idRes");
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

    public ResolucionesModificativas guardarModificatoria(ResolucionesModificativas modificativa) {
        try {
            if (modificativa.getIdResolucionModif() == null) {
                em.contains(modificativa);
            } else {
                modificativa = em.merge(modificativa);
            }
            return modificativa;
        } catch (Exception ex) {
            Logger.getLogger(RecepcionEJB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public VwBusquedaContratos getContratosById(BigDecimal idContrato) {
        Query query = em.createNamedQuery("Modificativa.BusquedaContratoById", VwBusquedaContratos.class);
        query.setParameter(1, idContrato);
        if (query.getResultList().isEmpty()) {
            return new VwBusquedaContratos();
        } else {
            return (VwBusquedaContratos) query.getResultList().get(0);
        }
    }

    /**
     * Obtiene el monto original de la modificativa que recibe como parametro.
     *
     * @param idResModif
     * @return
     */
    public BigDecimal getMontoOldContrato(BigDecimal idResModif) {
        Query query = em.createNativeQuery("SELECT FN_MO_GET_MONTO_CONTRATO_OLD(?1) FROM DUAL");
        query.setParameter(1, idResModif);
        if (query.getResultList().isEmpty()) {
            return BigDecimal.ZERO;
        } else {
            return (BigDecimal) query.getSingleResult();
        }
    }

    public List<TipoModifContrato> getLstTipoModifContrato(Short extision) {
        Query q = em.createQuery("SELECT t FROM TipoModifContrato t WHERE t.idModifContrato not in (7) and t.extinsion=:extinsion", TipoModifContrato.class);
        q.setParameter("extinsion", extision);
        return q.getResultList();
    }

    public void guardarExt(ResolucionesModificativas res) {
        try {
            em.persist(res);
        } catch (Exception ex) {
            Logger.getLogger(RecepcionEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarExtision(ResolucionesModificativas contratoEstado) {
        if (contratoEstado.getIdResolucionModif() == null) {
            em.persist(contratoEstado);
        } else {
            em.merge(contratoEstado);
        }
    }

    public List<VwContratoModificatoria> getContratoModificatoria(BigDecimal idContrato) {
        Query q = em.createNamedQuery("Modificativa.BusquedaContratoOriginal", VwContratoModificatoria.class);
        q.setParameter(1, idContrato);
        return q.getResultList();
    }

    public Boolean validarCambioEstado(ResolucionesModificativas resAdj, BigDecimal idEstadoReserva) {
        Boolean resultado = false;
        if ((resAdj.getIdEstadoReserva().intValue() == 1 && idEstadoReserva.intValue() == 2) || (resAdj.getIdEstadoReserva().intValue() == 3 && idEstadoReserva.intValue() == 2)) {
            resultado = true;
        } else //DIGITADA -> ANULADA y REVERTIDA -> ANULADA 
        if ((resAdj.getIdEstadoReserva().intValue() == 1 && idEstadoReserva.intValue() == 4) || (resAdj.getIdEstadoReserva().intValue() == 3 && idEstadoReserva.intValue() == 4)) {
            resultado = true;
        } else //APLICADA -> REVERTIDA
        if (resAdj.getIdEstadoReserva().intValue() == 2 && idEstadoReserva.intValue() == 3) {
            resultado = true;
        } else //APLICADA -> MODIFICADA
        if (resAdj.getIdEstadoReserva().intValue() == 2 && idEstadoReserva.intValue() == 5) {
            resultado = true;
        }
        return resultado;
    }

    public HashMap<String, Object> aplicarSaldos(ResolucionesModificativas res,
            TechoRubroEntEdu techoCE,
            BigDecimal idEstadoReserva,
            String usuario,
            HashMap<String, Object> param) {
        try {
            BigDecimal cantidadAdjudicada = new BigDecimal(getCantidadAdjudicadaActual(res));

            //no devuelve nada cuando no hay registros            
            Query query;
            if (res.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getAnho().equals("2018")) {
                query = em.createQuery("SELECT c FROM CapaInstPorRubro c WHERE c.idMuestraInteres.idEmpresa=:idEmpresa and c.idMuestraInteres.idDetProcesoAdq.idProcesoAdq.idAnho=:idAnho and c.estadoEliminacion = 0 and c.idMuestraInteres.estadoEliminacion=0 and c.idMuestraInteres.idEmpresa.estadoEliminacion=0", CapaInstPorRubro.class);
                query.setParameter("idEmpresa", res.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdEmpresa());
                query.setParameter("idAnho", res.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho());
            } else {
                query = em.createQuery("SELECT c FROM CapaInstPorRubro c WHERE c.idMuestraInteres.idEmpresa=:idEmpresa and c.idMuestraInteres.idDetProcesoAdq.idProcesoAdq.idProcesoAdq=:idProcesoAdq and c.estadoEliminacion = 0 and c.idMuestraInteres.estadoEliminacion=0 and c.idMuestraInteres.idEmpresa.estadoEliminacion=0", CapaInstPorRubro.class);
                query.setParameter("idEmpresa", res.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdEmpresa());
                query.setParameter("idProcesoAdq", res.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getPadreIdProcesoAdq() == null ? res.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdProcesoAdq() : res.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getPadreIdProcesoAdq().getIdProcesoAdq());
            }

            List<CapaInstPorRubro> lst = query.getResultList();

            if (!lst.isEmpty()) {
                CapaInstPorRubro capaInst = em.find(CapaInstPorRubro.class, lst.get(0).getIdCapInstRubro());

                switch (idEstadoReserva.intValue()) {
                    case 2: //aplicar
                        techoCE.setMontoAdjudicado(techoCE.getMontoAdjudicado().add(res.getValor().negate()));
                        techoCE.setMontoDisponible(techoCE.getMontoDisponible().add(res.getValor()));
                        capaInst.setCapacidadAdjudicada(getAdjParticipante(res.getIdContrato().getIdResolucionAdj().getIdParticipante()).add(cantidadAdjudicada).toBigInteger());
                        break;
                    case 5: //modificada
                        if (res.getIdEstadoReserva().intValue() == 5) {
                            techoCE.setMontoAdjudicado(techoCE.getMontoAdjudicado().add(res.getValor()));
                            techoCE.setMontoDisponible(techoCE.getMontoDisponible().add(res.getValor().negate()));
                            capaInst.setCapacidadAdjudicada(getAdjParticipante(res.getIdContrato().getIdResolucionAdj().getIdParticipante()).add(cantidadAdjudicada).toBigInteger());
                        }
                        break;
                    case 3: //revertir
                        techoCE.setMontoAdjudicado(techoCE.getMontoAdjudicado().add(res.getValor()));
                        techoCE.setMontoDisponible(techoCE.getMontoDisponible().add(res.getValor().negate()));

                        capaInst.setCapacidadAdjudicada(getAdjParticipante(res.getIdContrato().getIdResolucionAdj().getIdParticipante()).add(cantidadAdjudicada.negate()).toBigInteger());
                        break;
                    case 4: //anular
                        res.setIdEstadoReserva(idEstadoReserva);
                        break;
                }

                res.setIdEstadoReserva(idEstadoReserva);
                res.setUsuarioModificacion(usuario);
                res.setFechaModificacion(new Date());

                em.merge(techoCE);
                em.merge(res);
            }
            return param;
        } catch (Exception e) {
            Logger.getLogger(RecepcionEJB.class.getName()).log(Level.SEVERE, null, e);
            param.put("error", "Se ha generado un error en la aplicación de fondos.");
            return param;
        }
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
        String sql = String.format("SELECT NVL(FN_GETADJPROVEEDOR(%d, %d), 0) FROM EMPRESA WHERE id_empresa=%d",
                par.getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq(),
                par.getIdEmpresa().getIdEmpresa().intValue(),
                par.getIdEmpresa().getIdEmpresa().intValue());

        Query q = em.createNativeQuery(sql);
        return q.getResultList();
    }

    public synchronized HashMap<String, Object> aplicarReservaDeFondos(ResolucionesModificativas resModif, BigDecimal idEstadoReserva, String usuario) {
        HashMap<String, Object> param = new HashMap<>();
        //TODO falta derechos de usuarios y manejo de errores
        TechoRubroEntEdu techoCE;

        if (resModif.getIdEstadoReserva().intValue() == 4) {
            param.put("error", "Esta reserva se encuentra anulada y no se puede modificar.");
        } else {
            techoCE = resolucionAdjudicativaEJB.findTechoRubroEntEdu(resModif.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoEntidad(),
                    resModif.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq());

            //DIGITADA -> APLICADA  
            //REVERTIDAD -> APLICADA
            //MODIFICADA -> MODIFICADA
            if (techoCE != null) {
                if ((resModif.getIdEstadoReserva().intValue() == 1 && idEstadoReserva.intValue() == 2)
                        || (resModif.getIdEstadoReserva().intValue() == 3 && idEstadoReserva.intValue() == 2)
                        || (resModif.getIdEstadoReserva().intValue() == 5 && idEstadoReserva.intValue() == 5)) {
                    param = existeDiponibilidad(techoCE, resModif, param);
                    if (usuario.equals("RMINERO") || !param.containsKey("error")) {
                        param = aplicarSaldos(resModif, techoCE, idEstadoReserva, usuario, param);
                    }
                } else //DIGITADA -> ANULADA y REVERTIDA -> ANULADA 
                if ((resModif.getIdEstadoReserva().intValue() == 1 && idEstadoReserva.intValue() == 4)
                        || (resModif.getIdEstadoReserva().intValue() == 3 && idEstadoReserva.intValue() == 4)) {
                    param = aplicarSaldos(resModif, techoCE, idEstadoReserva, usuario, param);
                } else //APLICADA -> REVERTIDA
                if (resModif.getIdEstadoReserva().intValue() == 2 && idEstadoReserva.intValue() == 3) {
                    param = aplicarSaldos(resModif, techoCE, idEstadoReserva, usuario, param);
                }
            } else {
                param.put("error", "Se ha generado un error en la aplicación de fondos.");
            }
        }

        return param;
    }

    public HashMap<String, Object> existeDiponibilidad(TechoRubroEntEdu techoCE, ResolucionesModificativas resModif, HashMap<String, Object> param) {
        param = existeDisponibilidadProveedor(resModif, param);
        if (param.containsKey("error")) {
            return param;
        }

        return existeDisponibilidadesCE(techoCE, resModif.getValor(), param);
    }

    private HashMap<String, Object> existeDisponibilidadesCE(TechoRubroEntEdu techoCE, BigDecimal valorReserva, HashMap<String, Object> param) {

        if (techoCE.getMontoDisponible().intValue() - valorReserva.intValue() < 0) {
        } else {
            param.put("error", "El centro escolar " + techoCE.getCodigoEntidad() + " no posee disponibilidad presupuestaria!"
                    + "Monto Disponible: " + techoCE.getMontoDisponible() + " Monto Adjudicado: " + valorReserva);
        }
        return param;
    }

    private HashMap<String, Object> existeDisponibilidadProveedor(ResolucionesModificativas resModif, HashMap<String, Object> param) {
        List lista;
        BigInteger cantidadAdjudicada = getCantidadAdjudicadaActual(resModif);
        BigInteger disponibilidadProveedor;
        lista = getSaldoParticipante(resModif);
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

    public List getSaldoParticipante(ResolucionesModificativas resModif) {
        String sql = String.format("SELECT NVL(FN_GETSALDOPROVEEDOR(%d, %d, %d), 0) FROM DUAL",
                resModif.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdDetProcesoAdq(),
                resModif.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getPadreIdProcesoAdq() == null ? resModif.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdProcesoAdq() : resModif.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getPadreIdProcesoAdq().getIdProcesoAdq(),
                resModif.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdEmpresa().getIdEmpresa().intValue());

        Query q = em.createNativeQuery(sql);
        return q.getResultList();
    }

    /**
     * Cantidad variante en el contrato, esta cantidad puede subir o disminuir
     * dependiendo del tipo de modificativa
     *
     * @param resModif
     * @return
     */
    private BigInteger getCantidadAdjudicadaActual(ResolucionesModificativas resModif) {
        Long total;
        Query q = em.createQuery("SELECT sum(d.cantidadNew-d.cantidadOld) FROM DetalleModificativa d WHERE d.idResolucionModif=:idResModif and d.estadoEliminacion=0");
        q.setParameter("idResModif", resModif);
        total = (Long) q.getSingleResult();
        return new BigInteger(total.toString());
    }

    public List<VwRptContratoJurCabecera> generarContrato(ResolucionesModificativas resModif) {
        List<VwRptContratoJurCabecera> lstContrato;
        Query query = em.createNamedQuery("sv.gob.mined.paquescolar.model.pojos.contratacion.VwRptContratoJurCabecera", VwRptContratoJurCabecera.class);
        query.setParameter(1, resModif.getIdContrato().getIdContrato());
        lstContrato = query.getResultList();

        VwRptContratoJurCabecera cabeceraContrato = lstContrato.get(0);

        List<DetalleItemDto> lstDetalle = new ArrayList();
        List<DetalleItemDto> lstDetalleBac = new ArrayList();

        for (DetalleModificativa detalle : resModif.getDetalleModificativaList()) {
            if (detalle.getCantidadNew() > 0) {
                DetalleItemDto detalleContrato = new DetalleItemDto();
                detalleContrato.setNoItem(detalle.getNoItem());
                detalleContrato.setConsolidadoEspTec(detalle.getConsolidadoEspTec());
                detalleContrato.setCantidad(BigInteger.valueOf(detalle.getCantidadNew()));
                detalleContrato.setPrecioUnitario(detalle.getPrecioUnitarioNew());
                detalleContrato.setSubTotal(detalle.getPrecioUnitarioNew().multiply(BigDecimal.valueOf(detalle.getCantidadNew().longValue())));
                detalleContrato.setIdContrato(resModif.getIdContrato().getIdContrato().toBigInteger());

                if (resModif.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres().compareTo(BigDecimal.ONE) == 0 && detalleContrato.getConsolidadoEspTec().contains("BACHILLERATO")) {
                    lstDetalleBac.add(detalleContrato);
                } else {
                    lstDetalle.add(detalleContrato);
                }
            }
        }

        cabeceraContrato.setLstDetalleItems(lstDetalle);
        cabeceraContrato.setLstDetalleItemsBac(lstDetalleBac);

        return lstContrato;
    }

    public List<ContratoDto> generarResolucionModificativa(ResolucionesModificativas resolucionesModificativas) {
        List<ContratoDto> lstModificacionContratos = new ArrayList();
        ContratoDto rptModificacionContrato = new ContratoDto();
        Persona persona = resolucionesModificativas.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdEmpresa().getIdPersona();
        String nombreRepresentante = persona.getPrimerNombre().
                concat(persona.getSegundoNombre() == null ? " " : " " + persona.getSegundoNombre() + " ").
                concat(persona.getPrimerApellido()).concat(persona.getSegundoApellido() == null ? " " : " " + persona.getSegundoApellido() + " ").
                concat(persona.getAcasada() == null ? " " : " " + persona.getAcasada() + " ");

        rptModificacionContrato.setDescripcionRubro(resolucionesModificativas.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdRubroAdq().getDescripcionRubro());
        rptModificacionContrato.setFechaCreacionModif(resolucionesModificativas.getFechaInsercion());
        rptModificacionContrato.setNumeroContrato(resolucionesModificativas.getIdContrato().getNumeroContrato());
        rptModificacionContrato.setCodigoEntidad(resolucionesModificativas.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getCodigoEntidad());
        rptModificacionContrato.setNombreCe(resolucionesModificativas.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getNombre());
        rptModificacionContrato.setModalidadAdministrativa(resolucionesModificativas.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getCodigoEntidad().getInicialesModalidad());
        if (resolucionesModificativas.getIdResModifPadre() == null) {
            rptModificacionContrato.setFechaContratoModif(resolucionesModificativas.getIdContrato().getFechaEmision());
        } else {
            rptModificacionContrato.setFechaContratoModif(resolucionesModificativas.getIdResModifPadre().getFechaInsercion());
        }
        rptModificacionContrato.setFechaNota(resolucionesModificativas.getFechaNota());
        rptModificacionContrato.setFechaResolucion(resolucionesModificativas.getFechaResolucion());
        rptModificacionContrato.setFechaSolicitud(resolucionesModificativas.getFechaSolicitud());
        rptModificacionContrato.setMontoContratoModif(resolucionesModificativas.getMontoContratoContratoOld());
        rptModificacionContrato.setPlazoContrato(resolucionesModificativas.getIdContrato().getPlazoPrevistoEntrega().toString());
        rptModificacionContrato.setFechaOrdenInicio(resolucionesModificativas.getIdContrato().getFechaOrdenInicio());
        rptModificacionContrato.setNombreMiembro(resolucionesModificativas.getIdContrato().getMiembroFirma());
        rptModificacionContrato.setNombreRepresentante(nombreRepresentante);
        rptModificacionContrato.setRazonSocial(resolucionesModificativas.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdEmpresa().getRazonSocial());
        rptModificacionContrato.setAnhoContrato(resolucionesModificativas.getIdContrato().getAnhoContrato());
        rptModificacionContrato.setNitRepresentante(resolucionesModificativas.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdEmpresa().getIdPersona().getNumeroNit());
        rptModificacionContrato.setCiudadFirma(resolucionesModificativas.getIdContrato().getCiudadFirma());
        rptModificacionContrato.setHoraRegistro(resolucionesModificativas.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getHoraApertura().toString());
        rptModificacionContrato.setMinutoRegistro(resolucionesModificativas.getIdContrato().getIdResolucionAdj().getIdParticipante().getIdOferta().getHoraApertura().toString());

        for (DetalleModificativa detalle : resolucionesModificativas.getDetalleModificativaList()) {
            DetalleItemDto itemOld = new DetalleItemDto();
            DetalleItemDto itemNew = new DetalleItemDto();

            itemOld.setNoItem(detalle.getNoItem());
            itemNew.setNoItem(detalle.getNoItem());
            itemOld.setConsolidadoEspTec(detalle.getConsolidadoEspTec());
            itemNew.setConsolidadoEspTec(detalle.getConsolidadoEspTec());
            itemOld.setCantidad(BigInteger.valueOf(detalle.getCantidadOld().longValue()));
            itemNew.setCantidad(BigInteger.valueOf(detalle.getCantidadNew().longValue()));
            itemOld.setPrecioUnitario(detalle.getPrecioUnitarioOld());
            itemNew.setPrecioUnitario(detalle.getPrecioUnitarioNew());
            itemOld.setSubTotal(detalle.getPrecioUnitarioOld().multiply(BigDecimal.valueOf(detalle.getCantidadOld().longValue())));
            itemNew.setSubTotal(detalle.getPrecioUnitarioNew().multiply(BigDecimal.valueOf(detalle.getCantidadNew().longValue())));

            rptModificacionContrato.getLstDetalleOld().add(itemOld);
            rptModificacionContrato.getLstDetalleNew().add(itemNew);
        }

        lstModificacionContratos.add(rptModificacionContrato);

        return lstModificacionContratos;
    }

    public List<VwDepartamentoModificativas> lstDetalleModificativasOrderDepa(Integer idDetProcesoAdq) {
        List<VwDepartamentoModificativas> lst = new ArrayList();
        VwDepartamentoModificativas bandera = new VwDepartamentoModificativas();
        int var = 0;
        int numeroRegistro = 1;

        Query query = em.createNativeQuery("SELECT * FROM VW_DETALLE_MODIFICATIVAS where ID_DET_PROCESO_ADQ=" + idDetProcesoAdq + " order by codigo_departamento,CODIGO_ENTIDAD,razon_social,ID_MODIF_CONTRATO");
        List lstDatos = query.getResultList();

        for (Object object : lstDatos) {
            VwDetalleModificativas detalleModif = new VwDetalleModificativas();
            Object[] datos = (Object[]) object;
            if (lst.isEmpty()) {
                VwDepartamentoModificativas depa = new VwDepartamentoModificativas();
                depa.setNombreDepartamento(datos[0].toString());
                lst.add(var, depa);
                bandera = lst.get(var);
                var += 1;
            } else {
                if (bandera.getNombreDepartamento().equals(datos[0].toString())) {
                    numeroRegistro += 1;
                } else {
                    VwDepartamentoModificativas depa = new VwDepartamentoModificativas();
                    depa.setNombreDepartamento(datos[0].toString());
                    lst.add(var, depa);
                    bandera = lst.get(var);
                    var += 1;
                    numeroRegistro = 1;
                }
            }
            detalleModif.setNumeroRegistro(numeroRegistro);
            detalleModif.setNombreCe(datos[1].toString());
            detalleModif.setCodigoEntidad(datos[2].toString());
            detalleModif.setNumeroNit(datos[3].toString());
            detalleModif.setRazonSocial(datos[4].toString());
            detalleModif.setNumContrato(datos[5].toString());
            detalleModif.setCantidadModificada(Integer.parseInt(datos[6].toString()));
            detalleModif.setMontoContratoModificado(new BigDecimal(datos[7].toString()));
            detalleModif.setMontoContratoActual(new BigDecimal(datos[8].toString()));
            switch ((new BigDecimal(datos[9].toString())).intValue()) {
                case 2:
                    detalleModif.setErrorDigitacion(Boolean.TRUE);
                    break;
                case 3:
                    detalleModif.setErrorAdjudicacion(Boolean.TRUE);
                    break;
                case 4:
                    detalleModif.setProrroga(Boolean.TRUE);
                    break;
            }
            bandera.getLstDetalleModificativas().add(detalleModif);
        }
        return lst;
    }

    public List<VwDetalleModificativas> lstDetalleModificativasOrderProveedor(Integer idDetProcesoAdq) {
        List<VwDetalleModificativas> lst = new ArrayList();
        Query query = em.createNativeQuery("SELECT * FROM VW_DETALLE_MODIFICATIVAS where ID_DET_PROCESO_ADQ=" + idDetProcesoAdq + " order by razon_social,CODIGO_ENTIDAD,ID_MODIF_CONTRATO");
        List lstDatos = query.getResultList();

        for (Object object : lstDatos) {
            VwDetalleModificativas detalleModif = new VwDetalleModificativas();
            Object[] datos = (Object[]) object;
            detalleModif.setNombreDepartamento(datos[0].toString());
            detalleModif.setNombreCe(datos[1].toString());
            detalleModif.setCodigoEntidad(datos[2].toString());
            detalleModif.setNumeroNit(datos[3].toString());
            detalleModif.setRazonSocial(datos[4].toString());
            detalleModif.setNumContrato(datos[5].toString());
            detalleModif.setCantidadModificada(Integer.parseInt(datos[6].toString()));
            detalleModif.setMontoContratoModificado(new BigDecimal(datos[7].toString()));
            detalleModif.setMontoContratoActual(new BigDecimal(datos[8].toString()));
            switch ((new BigDecimal(datos[9].toString())).intValue()) {
                case 2:
                    detalleModif.setErrorDigitacion(Boolean.TRUE);
                    break;
                case 3:
                    detalleModif.setErrorAdjudicacion(Boolean.TRUE);
                    break;
                case 4:
                    detalleModif.setProrroga(Boolean.TRUE);
                    break;
            }
            lst.add(detalleModif);
        }
        return lst;
    }

    public List<VwDetalleModificativas> lstTerminacionContratos(Integer idDetProcesoAdq) {
        List<VwDetalleModificativas> lst = new ArrayList();
        Query query = em.createNativeQuery("SELECT * FROM VW_TERMINACION_CONTRATOS where ID_DET_PROCESO_ADQ=" + idDetProcesoAdq + " order by NOMBRE,CODIGO_DEPARTAMENTO,ID_TIPO_EXTINSION");
        List lstDatos = query.getResultList();

        for (Object object : lstDatos) {
            VwDetalleModificativas detalleModif = new VwDetalleModificativas();
            Object[] datos = (Object[]) object;
            detalleModif.setNumeroNit(datos[0].toString());
            detalleModif.setRazonSocial(datos[1].toString());
            detalleModif.setNombreCe(datos[2].toString());
            detalleModif.setCodigoEntidad(datos[3].toString());
            detalleModif.setNombreDepartamento(datos[4].toString());
            detalleModif.setNumContrato(datos[5].toString());
            detalleModif.setMontoContratoActual(new BigDecimal(datos[6].toString()));
            switch ((new BigDecimal(datos[7].toString())).intValue()) {
                case 1:
                    detalleModif.setTipoExtinsion1(Boolean.TRUE);
                    break;
                case 2:
                    detalleModif.setTipoExtinsion2(Boolean.TRUE);
                    break;
                case 3:
                    detalleModif.setTipoExtinsion3(Boolean.TRUE);
                    break;
                case 4:
                    detalleModif.setTipoExtinsion4(Boolean.TRUE);
                    break;
                case 5:
                    detalleModif.setTipoExtinsion5(Boolean.TRUE);
                    break;
            }
            lst.add(detalleModif);
        }
        return lst;
    }

    /**
     * Devuelve verdadero si existe un registro de RecepcionBienesServicios para
     * el contrato que se recibe como parametro
     *
     * @param idContrato
     * @return
     */
    public Boolean existeRecepcionDeContrato(BigDecimal idContrato) {
        Query q = em.createNativeQuery("SELECT count(*) FROM RECEPCION_BIENES_SERVICIOS where id_contrato = ?1 and ESTADO_ELIMINACION=0");
        q.setParameter(1, idContrato);
        return (((BigDecimal) q.getSingleResult()).intValue() > 0);
    }
    
    
}
