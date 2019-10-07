/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.EstadisticaCenso;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.OrganizacionEducativa;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.TechoRubroEntEdu;
import sv.gob.mined.paquescolar.model.pojos.VwRptCertificacionPresupuestaria;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class EntidadEducativaEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public VwCatalogoEntidadEducativa getEntidadEducativa(String codigoEntidad) {
        Query q = em.createQuery("SELECT v FROM VwCatalogoEntidadEducativa v WHERE v.codigoEntidad=:codigoEntidad", VwCatalogoEntidadEducativa.class);
        q.setParameter("codigoEntidad", codigoEntidad);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (VwCatalogoEntidadEducativa) q.getSingleResult();
        }
    }

    public TechoRubroEntEdu findTechoRubroEntEdu(String codigoEntidad, DetalleProcesoAdq idProceso) {
        Query query = em.createQuery("SELECT t FROM TechoRubroEntEdu t WHERE t.codigoEntidad=:codigoEntidad and t.idDetProcesoAdq=:idProceso and t.estadoEliminacion=0", TechoRubroEntEdu.class);
        query.setParameter("codigoEntidad", codigoEntidad);
        query.setParameter("idProceso", idProceso);
        List<TechoRubroEntEdu> lstTechos = query.getResultList();

        if (lstTechos.isEmpty()) {
            return null;
        } else {
            return lstTechos.get(0);
        }
    }

    public List<VwCatalogoEntidadEducativa> getLstEntidadEducativaByNombreOrCodigoEntidad(String nombre) {
        List<VwCatalogoEntidadEducativa> lstEntidadEducativa;

        Query query = em.createQuery("SELECT v FROM VwCatalogoEntidadEducativa v WHERE v.nombre like :nombreCe or v.codigoEntidad like :codigoEntidad ORDER BY v.codigoDepartamento.codigoDepartamento", VwCatalogoEntidadEducativa.class);
        query.setParameter("nombreCe", "%" + nombre + "%");
        query.setParameter("codigoEntidad", "%" + nombre + "%");
        lstEntidadEducativa = query.getResultList();

        return lstEntidadEducativa;
    }

    public List<EstadisticaCenso> getLstEstadisticaByCodEntAndProceso(String codigoEntidad, Integer idProcesoAdq) {
        Query q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codEnt and e.idProcesoAdq.idProcesoAdq=:idPro", EstadisticaCenso.class);
        q.setParameter("codEnt", codigoEntidad);
        q.setParameter("idPro", idProcesoAdq);
        return q.getResultList();
    }

    public EstadisticaCenso getEstadisticaByCodEntAndNivelAndProceso(String codEntidad, BigDecimal nivel, ProcesoAdquisicion procesoAdq) {
        Query q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codEnt and e.idNivelEducativo.idNivelEducativo=:nivel and e.idProcesoAdq=:procesoAdq", EstadisticaCenso.class);
        q.setParameter("codEnt", codEntidad);
        q.setParameter("nivel", nivel);
        q.setParameter("procesoAdq", procesoAdq);

        if (q.getResultList().isEmpty()) {
            EstadisticaCenso est = new EstadisticaCenso();
            est.setEstadoEliminacion(Short.parseShort("0"));
            est.setIdNivelEducativo(em.find(NivelEducativo.class, nivel));
            est.setIdProcesoAdq(procesoAdq);
            est.setCodigoEntidad(codEntidad);
            est.setMasculino(BigInteger.ZERO);
            est.setFemenimo(BigInteger.ZERO);
            return est;
        } else {
            return (EstadisticaCenso) q.getSingleResult();
        }
    }

    public Boolean guardarEstadistica(EstadisticaCenso estadistica, String usuario) {
        Boolean error = true;
        try {
            if (estadistica.getMasculino() == null) {
                estadistica.setMasculino(BigInteger.ZERO);
            }
            if (estadistica.getFemenimo() == null) {
                estadistica.setFemenimo(BigInteger.ZERO);
            }
            if (estadistica.getIdEstadistica() == null) {
                estadistica.setFechaInsercion(new Date());
                estadistica.setUsuarioInsercion(usuario);
                em.persist(estadistica);
            } else {
                estadistica.setFechaModificacion(new Date());
                estadistica.setUsuarioModificacion(usuario);
                em.merge(estadistica);
            }

            error = false;
        } catch (Exception e) {
            Logger.getLogger(EntidadEducativaEJB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            return error;
        }
    }

    public Boolean guardarPresupuesto(String usuario, TechoRubroEntEdu... lstTecho) {
        Boolean error = true;
        try {
            for (TechoRubroEntEdu techo : lstTecho) {
                if (techo.getIdRubroTecho() == null) {
                    em.persist(techo);
                } else {
                    techo.setFechaModificacion(new Date());
                    techo.setUsuarioModificacion(usuario);
                    em.merge(techo);
                }
            }
            error = false;
        } catch (Exception e) {
            Logger.getLogger(EntidadEducativaEJB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            return error;
        }
    }

    public List<TechoRubroEntEdu> getLstTechosByProceso(Integer idProcesoAdq, String codigoEntidad) {
        Query q = em.createQuery("SELECT t FROM TechoRubroEntEdu t WHERE t.idDetProcesoAdq.idProcesoAdq.idProcesoAdq=:idPro and t.codigoEntidad=:codEnt and t.estadoEliminacion = 0", TechoRubroEntEdu.class);
        q.setParameter("idPro", idProcesoAdq);
        q.setParameter("codEnt", codigoEntidad);
        return q.getResultList();
    }

    public TechoRubroEntEdu findTechoByProceso(DetalleProcesoAdq detProAdq, String codigoEntidad, String usuario) {
        Query q = em.createQuery("SELECT t FROM TechoRubroEntEdu t WHERE t.idDetProcesoAdq=:detProAdq and t.codigoEntidad=:codEnt", TechoRubroEntEdu.class);
        q.setParameter("detProAdq", detProAdq);
        q.setParameter("codEnt", codigoEntidad);

        if (q.getResultList().isEmpty()) {
            TechoRubroEntEdu techo = new TechoRubroEntEdu();

            techo.setCodigoEntidad(codigoEntidad);
            techo.setEstadoEliminacion(BigInteger.ZERO);
            techo.setFechaInsercion(new Date());
            techo.setIdDetProcesoAdq(detProAdq);
            techo.setMontoAdjudicado(BigDecimal.ZERO);
            techo.setMontoDisponible(BigDecimal.ZERO);
            techo.setMontoPresupuestado(BigDecimal.ZERO);
            techo.setUsuarioInsercion(usuario);

            return techo;
        } else {
            return (TechoRubroEntEdu) q.getSingleResult();
        }
    }

    public VwRptCertificacionPresupuestaria getCertificacion(String codigoEntidad, ProcesoAdquisicion proceso, Boolean utiles) {
        VwRptCertificacionPresupuestaria cabecera = new VwRptCertificacionPresupuestaria();
        Query q = em.createNativeQuery("SELECT * FROM vw_cabecera_certificacion_pre WHERE codigo_entidad = '" + codigoEntidad + "'");
        List lst = q.getResultList();
        for (Object object : lst) {
            Object[] datos = (Object[]) object;

            cabecera.setDepartamento(datos[0].toString());
            cabecera.setMunicipio(datos[1].toString());
            cabecera.setCodigoEntidad(datos[2].toString());
            cabecera.setNombreCe(datos[3].toString());
            cabecera.setModalidadDeAdministracion(datos[4].toString());
        }
        try {
            q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=1 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
            q.setParameter("codigoEntidad", codigoEntidad);
            q.setParameter("proceso", proceso);
            cabecera.setParvularia((EstadisticaCenso) q.getSingleResult());

            q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=3 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
            q.setParameter("codigoEntidad", codigoEntidad);
            q.setParameter("proceso", proceso);
            cabecera.setCiclo1((EstadisticaCenso) q.getSingleResult());

            q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=4 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
            q.setParameter("codigoEntidad", codigoEntidad);
            q.setParameter("proceso", proceso);
            cabecera.setCiclo2((EstadisticaCenso) q.getSingleResult());

            q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=5 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
            q.setParameter("codigoEntidad", codigoEntidad);
            q.setParameter("proceso", proceso);
            cabecera.setCiclo3((EstadisticaCenso) q.getSingleResult());

            q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=6 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
            q.setParameter("codigoEntidad", codigoEntidad);
            q.setParameter("proceso", proceso);
            cabecera.setBachillerato((EstadisticaCenso) q.getSingleResult());

            if (utiles) {
                q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=10 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
                q.setParameter("codigoEntidad", codigoEntidad);
                q.setParameter("proceso", proceso);
                cabecera.setGrado1((EstadisticaCenso) q.getSingleResult());

                q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=11 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
                q.setParameter("codigoEntidad", codigoEntidad);
                q.setParameter("proceso", proceso);
                cabecera.setGrado2((EstadisticaCenso) q.getSingleResult());

                q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=12 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
                q.setParameter("codigoEntidad", codigoEntidad);
                q.setParameter("proceso", proceso);
                cabecera.setGrado3((EstadisticaCenso) q.getSingleResult());

                q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=13 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
                q.setParameter("codigoEntidad", codigoEntidad);
                q.setParameter("proceso", proceso);
                cabecera.setGrado4((EstadisticaCenso) q.getSingleResult());

                q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=14 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
                q.setParameter("codigoEntidad", codigoEntidad);
                q.setParameter("proceso", proceso);
                cabecera.setGrado5((EstadisticaCenso) q.getSingleResult());

                q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=15 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
                q.setParameter("codigoEntidad", codigoEntidad);
                q.setParameter("proceso", proceso);
                cabecera.setGrado6((EstadisticaCenso) q.getSingleResult());

                q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=7 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
                q.setParameter("codigoEntidad", codigoEntidad);
                q.setParameter("proceso", proceso);
                cabecera.setGrado7((EstadisticaCenso) q.getSingleResult());

                q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=8 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
                q.setParameter("codigoEntidad", codigoEntidad);
                q.setParameter("proceso", proceso);
                cabecera.setGrado8((EstadisticaCenso) q.getSingleResult());

                q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=9 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
                q.setParameter("codigoEntidad", codigoEntidad);
                q.setParameter("proceso", proceso);
                cabecera.setGrado9((EstadisticaCenso) q.getSingleResult());

                q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=16 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
                q.setParameter("codigoEntidad", codigoEntidad);
                q.setParameter("proceso", proceso);
                cabecera.setGradob1((EstadisticaCenso) q.getSingleResult());

                q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=17 and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
                q.setParameter("codigoEntidad", codigoEntidad);
                q.setParameter("proceso", proceso);
                cabecera.setGradob2((EstadisticaCenso) q.getSingleResult());
            }

            return cabecera;
        } catch (Exception ex) {
            Logger.getLogger(EntidadEducativaEJB.class.getName()).log(Level.WARNING, null, "Error en obteniendo las estadisticas de censo rápido");
            Logger.getLogger(EntidadEducativaEJB.class.getName()).log(Level.WARNING, null, "Codigo Entidad: " + codigoEntidad);
            Logger.getLogger(EntidadEducativaEJB.class.getName()).log(Level.WARNING, null, "Proceso de compra: " + proceso.getIdAnho().getAnho());
            return null;
        } finally {
        }
    }

    public OrganizacionEducativa getPresidenteOrganismoEscolar(String codigoEntidad) {
        Query query = em.createNativeQuery("SELECT * FROM organizacion_educativa  WHERE codigo_entidad=?1 and firma_contrato=1", OrganizacionEducativa.class);
        query.setParameter(1, codigoEntidad);
        List<OrganizacionEducativa> lst = query.getResultList();
        if (lst.isEmpty()) {
            return new OrganizacionEducativa();
        } else {
            return (OrganizacionEducativa) query.getResultList().get(0);
        }
    }

    public void create(OrganizacionEducativa organizacionEducativa) {
        em.persist(organizacionEducativa);

    }

    public OrganizacionEducativa edit(OrganizacionEducativa organizacionEducativa) {
        return em.merge(organizacionEducativa);
    }

    public EstadisticaCenso asignarTechoCe(EstadisticaCenso estadisticaCenso, Integer idProceso) {
        estadisticaCenso.setEstadoEliminacion(Short.parseShort("0"));

        if (estadisticaCenso.getIdEstadistica() == null) {
            estadisticaCenso.setFechaInsercion(new Date());
            estadisticaCenso.setUsuarioInsercion("ADMIN");
            em.persist(estadisticaCenso);
        } else {
            estadisticaCenso.setFechaModificacion(new Date());
            estadisticaCenso.setUsuarioModificacion("ADMIN");
            em.merge(estadisticaCenso);
        }

        return estadisticaCenso;
    }

    public List<BigDecimal> getLstNivelesConMatriculaReportadaByIdProcesoAdqAndCodigoEntidad(Integer idProcesoAdq, String codigoEntidad) {
        Query q = em.createQuery("SELECT DISTINCT e.idNivelEducativo.idNivelEducativo FROM EstadisticaCenso e WHERE e.idProcesoAdq.idProcesoAdq=:idProAdq AND e.codigoEntidad=:codEnt AND (e.masculino >0 OR e.femenimo > 0) order by e.idNivelEducativo.idNivelEducativo");
        q.setParameter("idProAdq", idProcesoAdq);
        q.setParameter("codEnt", codigoEntidad);
        return q.getResultList();
    }

    public BigDecimal getCantidadTotalByCodEntAndIdProcesoAdq(String codigoEntidad, Integer idProcesoAdq) {
        Query q = em.createNativeQuery("select sum(nvl(masculino,0)+nvl(femenimo,0)) from estadistica_censo \n"
                + "where codigo_entidad = ?1 and id_proceso_adq = ?2 and estado_eliminacion = 0 and id_nivel_educativo in (1,3,4,5,6)");
        q.setParameter(1, codigoEntidad);
        q.setParameter(2, idProcesoAdq);
        return q.getResultList().isEmpty() ? BigDecimal.ZERO : (BigDecimal) q.getSingleResult();
    }

    /*public String getNivelesDeEntidadEducativa(String codigoEntidad, Integer idProcesoAdq) {
        String idNiveles = "";
        Query q = em.createNativeQuery("select id_nivel\n"
                + "from\n"
                + "    (select codigo_entidad, case when id_nivel_educativo in (1) then 1 when id_nivel_educativo in (3,4,5) then 2 when id_nivel_educativo in (16,17,18) then 6 end id_nivel, masculino, femenimo\n"
                + "    from estadistica_censo\n"
                + "    where (masculino <> 0 or femenimo <> 0) and codigo_entidad = ?1 and id_proceso_adq = ?2 and estado_eliminacion = 0 and id_nivel_Educativo not in (2,6,7,8,9,10,11,12,13,14,15))\n"
                + "group by codigo_entidad,id_nivel\n"
                + "order by codigo_entidad,id_nivel");
        q.setParameter(1, codigoEntidad);
        q.setParameter(2, idProcesoAdq);

        List lst = q.getResultList();

        for (Object object : lst) {
            Object[] datos = (Object[]) object;
            if (idNiveles.isEmpty()) {
                idNiveles = datos[0].toString();
            } else {
                idNiveles = "," + datos[0].toString();
            }
        }
        return idNiveles;
    }*/
    
    public HashMap<String, String> getNoItemsByCodigoEntidadAndIdProcesoAdq(String codigoEntidad, DetalleProcesoAdq detProcesoAdq, boolean isUniforme) {
        String noItemSeparados = "";
        String noItems = "";
        String idNivelesCe = "";
        String sql;
        HashMap<String, String> mapItems = new HashMap();
        List lst;
        BigDecimal mas;
        BigDecimal fem;

        if (isUniforme) {
            sql = "select id_nivel, sum(masculino) mas, sum(femenimo) fem\n"
                    + "from (select case when id_nivel_educativo = 1 then 1 when id_nivel_educativo in (3,4,5) then 2 when id_nivel_educativo = 6 then 6 end id_nivel,\n"
                    + "        masculino, femenimo\n"
                    + "    from estadistica_censo \n"
                    + "    where id_proceso_adq = ?1 and id_nivel_educativo in (1,3,4,5,6) and codigo_entidad = ?2 and (masculino <> 0 or femenimo <> 0))\n"
                    + "group by id_nivel";
        } else {
            sql = "select id_nivel_educativo, masculino, femenimo from estadistica_censo \n"
                    + "where id_proceso_adq = ?1 and \n"
                    + "    id_nivel_educativo in (1,3,4,5,6) and \n"
                    + "    codigo_entidad = ?2 and \n"
                    + "    (masculino <> 0 or femenimo <> 0)";
        }

        Query q = em.createNativeQuery(sql);
        q.setParameter(1, detProcesoAdq.getIdProcesoAdq().getIdProcesoAdq());
        q.setParameter(2, codigoEntidad);

        lst = q.getResultList();

        //item_8 = '8' and item_9 = '9'
        //'8','9'
        for (Object object : lst) {
            Object datos[] = (Object[]) object;

            mas = (BigDecimal) datos[1];
            fem = (BigDecimal) datos[2];

            switch (((BigDecimal) datos[0]).intValue()) {
                case 1: //PARVULARIA
                    idNivelesCe += idNivelesCe.isEmpty() ? "1" : ",1";
                    switch (detProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                        case 1:
                        case 4:
                        case 5://uniformes
                            if (fem.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_1 = '1' and item_2 = '2'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'1','2'";
                            }
                            if (mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_3 = '3' and item_4 = '4' and item_5 = '5'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'3','4','5'";
                            }
                            break;
                        case 2://utiles
                            if (fem.intValue() > 0 || mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_1 = '1'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'1'";
                            }
                            break;
                        case 3://zapatos
                            if (fem.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_1 = '1'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'1'";
                            }
                            if (mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_2 = '2'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'2'";
                            }
                            break;
                    }

                    break;
                case 2: //BASICA - UNICAMENTE APLICA PARA UNIFORME
                    idNivelesCe += idNivelesCe.isEmpty() ? "2" : ",2";
                    if (fem.intValue() > 0) {
                        noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_6 = '6' and item_7 = '7'";
                        noItems += (noItems.isEmpty() ? "" : " , ") + "'6','7'";
                    }
                    if (mas.intValue() > 0) {
                        noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_8 = '8' and item_9 = '9'";
                        noItems += (noItems.isEmpty() ? "" : " , ") + "'8','9'";
                    }
                    break;
                case 3: //PRIMER CICLO
                    idNivelesCe += idNivelesCe.isEmpty() ? "3" : ",3";
                    switch (detProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                        case 2://utiles
                            if (fem.intValue() > 0 || mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_2 = '2'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'2'";
                            }
                            break;
                        case 3://zapatos
                            if (fem.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_3 = '3'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'3'";
                            }
                            if (mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_4 = '4'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'4'";
                            }
                            break;
                    }
                    break;
                case 4: //SEGUNDO CICLO
                    idNivelesCe += idNivelesCe.isEmpty() ? "4" : ",4";
                    switch (detProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                        case 2://utiles
                            if (fem.intValue() > 0 || mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_3 = '3'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'3'";
                            }
                            break;
                        case 3://zapatos
                            if (fem.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_5 = '5'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'5'";
                            }
                            if (mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_6 = '6'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'6'";
                            }
                            break;
                    }
                    break;
                case 5: //TERCER CICLO
                    idNivelesCe += idNivelesCe.isEmpty() ? "5" : ",5";
                    switch (detProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                        case 2://utiles
                            if (fem.intValue() > 0 || mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_4 = '4'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'4'";
                            }
                            break;
                        case 3://zapatos
                            if (fem.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_7 = '7'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'7'";
                            }
                            if (mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_8 = '8'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'8'";
                            }
                            break;
                    }
                    break;
                case 6: //MEDIA
                    idNivelesCe += idNivelesCe.isEmpty() ? "6" : ",6";
                    switch (detProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                        case 1:
                        case 4:
                        case 5://uniformes
                            if (fem.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_10 = '10' and item_11 = '11'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'10','11'";
                            }
                            if (mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_12 = '12' and item_13 = '13'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'13','13'";
                            }
                            break;
                        case 2://utiles
                            if (fem.intValue() > 0 || mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_5 = '5'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'5'";
                            }
                            break;
                        case 3://zapatos
                            if (fem.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_9 = '9'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'9'";
                            }
                            if (mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_10 = '10'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'10'";
                            }
                            break;
                    }
                    break;
            }
        }

        mapItems.put("noItemSeparados", noItemSeparados);
        mapItems.put("noItems", noItems);
        mapItems.put("idNivelesCe", idNivelesCe);

        return mapItems;
    }
}
