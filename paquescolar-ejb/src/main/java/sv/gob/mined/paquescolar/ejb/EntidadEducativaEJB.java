/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.EstadisticaCenso;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.OrganizacionEducativa;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.TechoRubroEntEdu;
import sv.gob.mined.paquescolar.model.pojos.VwRptCertificacionPresupuestaria;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ResguardoItemDto;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;
import sv.gob.mined.paquescolar.ws.TechoCE;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class EntidadEducativaEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;
    @EJB
    private UtilEJB utilEJB;

    public VwCatalogoEntidadEducativa getEntidadEducativa(String codigoEntidad) {
        Query q = em.createQuery("SELECT v FROM VwCatalogoEntidadEducativa v WHERE v.codigoEntidad=:codigoEntidad", VwCatalogoEntidadEducativa.class);
        q.setParameter("codigoEntidad", codigoEntidad);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (VwCatalogoEntidadEducativa) q.getSingleResult();
        }
    }

    public VwCatalogoEntidadEducativa getEntidadEducativaEstadistica(String codigoEntidad) {
        StoredProcedureQuery sp = em.createNamedStoredProcedureQuery("SP_ACTUALIZAR_EST2021");
        sp.setParameter("V_CODIGO_ENTIDAD", codigoEntidad);
        sp.execute();

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
//        Query q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codEnt and e.idNivelEducativo.idNivelEducativo=:nivel and e.idProcesoAdq=:procesoAdq", EstadisticaCenso.class);
//        q.setParameter("codEnt", codEntidad);
//        q.setParameter("nivel", nivel);
//        q.setParameter("procesoAdq", procesoAdq);
//
//        if (q.getResultList().isEmpty()) {
        EstadisticaCenso est = new EstadisticaCenso();
        est.setEstadoEliminacion(Short.parseShort("0"));
        est.setIdNivelEducativo(em.find(NivelEducativo.class, nivel));
        est.setIdProcesoAdq(procesoAdq);
        est.setCodigoEntidad(codEntidad);
        est.setMasculino(BigInteger.ZERO);
        est.setFemenimo(BigInteger.ZERO);
        return est;
//        } else {
//            return (EstadisticaCenso) q.getSingleResult();
//        }
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
                if (techo != null) {
                    if (techo.getIdRubroTecho() == null) {
                        em.persist(techo);
                    } else {
                        techo.setFechaModificacion(new Date());
                        techo.setUsuarioModificacion(usuario);
                        em.merge(techo);
                    }
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
            q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo=" + (proceso.getIdAnho().getIdAnho().intValue() > 8 ? "22" : "1") + " and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
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

            q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo in (5) and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
            q.setParameter("codigoEntidad", codigoEntidad);
            q.setParameter("proceso", proceso);
            cabecera.setCiclo3((EstadisticaCenso) q.getSingleResult());

            q = em.createQuery("SELECT e FROM EstadisticaCenso e WHERE e.codigoEntidad=:codigoEntidad and e.idNivelEducativo.idNivelEducativo in (6) and e.idProcesoAdq=:proceso", EstadisticaCenso.class);
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

    public OrganizacionEducativa getMiembro(String codigoEntidad, String cargo) {
        Query query = em.createNativeQuery("SELECT * FROM organizacion_educativa  WHERE codigo_entidad=?1 and firma_contrato=0 and cargo='" + cargo + "'", OrganizacionEducativa.class);
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

    public void guardar(OrganizacionEducativa org) {
        if (org.getIdOrganizacionEducativa() == null) {
            create(org);
        } else {
            edit(org);
        }
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
        String niveles = "";
        switch (idProcesoAdq) {
            case 19:
                niveles = "(22,3,4,5,6,23,24)";
                break;
            default:
                niveles = "(1,3,4,5,6)";
                break;
        }

        Query q = em.createNativeQuery("select sum(nvl(masculino,0)+nvl(femenimo,0)) from estadistica_censo \n"
                + "where codigo_entidad = ?1 and id_proceso_adq = ?2 and estado_eliminacion = 0 and id_nivel_educativo in " + niveles);
        q.setParameter(1, codigoEntidad);
        q.setParameter(2, idProcesoAdq);
        return q.getResultList().isEmpty() ? BigDecimal.ZERO : (BigDecimal) q.getSingleResult();
    }

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
            sql = "select "
                    + "id_nivel_educativo, "
                    + "masculino, femenimo from estadistica_censo \n"
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
                case 22:
                case 1: //PARVULARIA
                    idNivelesCe += idNivelesCe.isEmpty() ? "" + ((BigDecimal) datos[0]).intValue() : "," + ((BigDecimal) datos[0]).intValue() + "";
                    switch (detProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                        case 1:
                        case 4:
                        case 5://uniformes
                            if (fem.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_" + ((BigDecimal) datos[0]).intValue() + " = '" + ((BigDecimal) datos[0]).intValue() + "' and item_2 = '2'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'" + ((BigDecimal) datos[0]).intValue() + "','2'";
                            }
                            if (mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_3 = '3' and item_4 = '4'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'3','4'";
                            }
                            break;
                        case 2://utiles
                            //case 6://mascarillas
                            if (fem.intValue() > 0 || mas.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_" + ((BigDecimal) datos[0]).intValue() + " = '" + ((BigDecimal) datos[0]).intValue() + "'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'" + ((BigDecimal) datos[0]).intValue() + "'";
                            }
                            break;
                        case 3://zapatos
                            if (fem.intValue() > 0) {
                                noItemSeparados += (noItemSeparados.isEmpty() ? "" : " and ") + "item_" + ((BigDecimal) datos[0]).intValue() + " = '" + ((BigDecimal) datos[0]).intValue() + "'";
                                noItems += (noItems.isEmpty() ? "" : " , ") + "'" + ((BigDecimal) datos[0]).intValue() + "'";
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
                        case 6://mascarilla
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
                        case 6://mascarilla
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
                        case 6://mascarilla
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
                        case 6://mascarilla
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

    public void updateNombreDirectorContratoYPago(String codigoEntidad, Integer idAnho, String nombreDirector) {
        Query q = em.createNamedQuery("SP_UPD_NOMBRE_DIRECTOR");
        q.setParameter("P_COD_ENT", codigoEntidad);
        q.setParameter("P_ID_ANHO", idAnho);
        q.setParameter("P_NOMBRE", nombreDirector);

        q.getResultList();
    }

    public List<OrganizacionEducativa> lstCorreosDirectores() {
        Query q = em.createQuery("SELECT o FROM OrganizacionEducativa o WHERE o.firmaContrato=1 and o.correoElectronico is not null", OrganizacionEducativa.class);
        return q.getResultList();
    }

    public void cargarMatricula(String codigoEntidad, int idProceso,
            int parMas, int parFem,
            int grado1mas, int grado1fem, int grado2mas, int grado2fem, int grado3mas, @WebParam(name = "grado3fem") int grado3fem,
            int grado4mas, int grado4fem, int grado5mas, int grado5fem,
            int grado6mas, int grado6fem, int grado7mas, int grado7fem,
            int grado8mas, int grado8fem, int grado9mas, int grado9fem,
            int b1mas, int b1fem, int b2mas, int b2fem,
            int b3mas, int b3fem) {

        FileInputStream file = null;
        try {
            int ini2Fem = 0;
            int ini2Mas = 0;
            int ini3Fem = 0;
            int ini3Mas = 0;

            file = new FileInputStream(new File("C:\\Users\\Desarrollo\\Documents\\MINED\\paquete\\sigesmatpaqesc-2022.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            /*
            * Obtenemos la primera pestaña a la que se quiera procesar indicando el indice.
            * Una vez obtenida la hoja excel con las filas que se quieren leer obtenemos el iterator
            * que nos permite recorrer cada una de las filas que contiene.
             */
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            // Recorremos todas las filas para mostrar el contenido de cada celda
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                if (row.getRowNum() != 0) {

                    codigoEntidad = String.valueOf((int) row.getCell(0).getNumericCellValue());
                    ini2Fem = (int) row.getCell(1).getNumericCellValue();
                    ini2Mas = (int) row.getCell(2).getNumericCellValue();
                    ini3Fem = (int) row.getCell(3).getNumericCellValue();
                    ini3Mas = (int) row.getCell(4).getNumericCellValue();
                    parFem = (int) row.getCell(5).getNumericCellValue();
                    parMas = (int) row.getCell(6).getNumericCellValue();
                    grado1fem = (int) row.getCell(7).getNumericCellValue();
                    grado1mas = (int) row.getCell(8).getNumericCellValue();
                    grado2fem = (int) row.getCell(9).getNumericCellValue();
                    grado2mas = (int) row.getCell(10).getNumericCellValue();
                    grado3fem = (int) row.getCell(11).getNumericCellValue();
                    grado3mas = (int) row.getCell(12).getNumericCellValue();
                    grado4fem = (int) row.getCell(13).getNumericCellValue();
                    grado4mas = (int) row.getCell(14).getNumericCellValue();
                    grado5fem = (int) row.getCell(15).getNumericCellValue();
                    grado5mas = (int) row.getCell(16).getNumericCellValue();
                    grado6fem = (int) row.getCell(17).getNumericCellValue();
                    grado6mas = (int) row.getCell(18).getNumericCellValue();
                    grado7fem = (int) row.getCell(19).getNumericCellValue();
                    grado7mas = (int) row.getCell(20).getNumericCellValue();
                    grado8fem = (int) row.getCell(21).getNumericCellValue();
                    grado8mas = (int) row.getCell(22).getNumericCellValue();
                    grado9fem = (int) row.getCell(23).getNumericCellValue();
                    grado9mas = (int) row.getCell(24).getNumericCellValue();
                    b1fem = (int) row.getCell(25).getNumericCellValue();
                    b1mas = (int) row.getCell(26).getNumericCellValue();
                    b2fem = (int) row.getCell(27).getNumericCellValue();
                    b2mas = (int) row.getCell(28).getNumericCellValue();
                    b3fem = (int) row.getCell(29).getNumericCellValue();
                    b3mas = (int) row.getCell(30).getNumericCellValue();

                    DetalleProcesoAdq detProAdqUni;
                    DetalleProcesoAdq detProAdqUni2;
                    DetalleProcesoAdq detProAdqUti;
                    DetalleProcesoAdq detProAdqZap;
                    EstadisticaCenso estIni2;
                    EstadisticaCenso estIni3;
                    EstadisticaCenso estPar;
                    EstadisticaCenso estCiclo1;
                    EstadisticaCenso estCiclo2;
                    EstadisticaCenso estCiclo3;
                    EstadisticaCenso estGrado1;
                    EstadisticaCenso estGrado2;
                    EstadisticaCenso estGrado3;
                    EstadisticaCenso estGrado4;
                    EstadisticaCenso estGrado5;
                    EstadisticaCenso estGrado6;
                    EstadisticaCenso estGrado7;
                    EstadisticaCenso estGrado8;
                    EstadisticaCenso estGrado9;
                    EstadisticaCenso estB1;
                    EstadisticaCenso estB2;
                    EstadisticaCenso estB3;
                    EstadisticaCenso estBac;
                    TechoRubroEntEdu techoUni;
                    TechoRubroEntEdu techoUni2 = new TechoRubroEntEdu();
                    TechoRubroEntEdu techoUti;
                    TechoRubroEntEdu techoZap;

                    ProcesoAdquisicion procesoAdquisicion = utilEJB.find(ProcesoAdquisicion.class, 21);

                    detProAdqUni = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(4));
                    detProAdqUni2 = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(5));
                    detProAdqUti = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(2));
                    detProAdqZap = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(3));

                    estIni2 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("25"), procesoAdquisicion);
                    estIni3 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("26"), procesoAdquisicion);
                    estPar = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("22"), procesoAdquisicion);
                    estCiclo1 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("3"), procesoAdquisicion);
                    estCiclo2 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("4"), procesoAdquisicion);
                    estCiclo3 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("5"), procesoAdquisicion);
                    estBac = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("6"), procesoAdquisicion);
                    estGrado7 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("7"), procesoAdquisicion);
                    estGrado8 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("8"), procesoAdquisicion);
                    estGrado9 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("9"), procesoAdquisicion);
                    estGrado1 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("10"), procesoAdquisicion);
                    estGrado2 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("11"), procesoAdquisicion);
                    estGrado3 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("12"), procesoAdquisicion);
                    estGrado4 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("13"), procesoAdquisicion);
                    estGrado5 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("14"), procesoAdquisicion);
                    estGrado6 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("15"), procesoAdquisicion);
                    estB1 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("16"), procesoAdquisicion);
                    estB2 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("17"), procesoAdquisicion);
                    estB3 = getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("18"), procesoAdquisicion);

                    if (estPar.getIdEstadistica() == null) {
                        //try {
                        estIni2.setCodigoEntidad(codigoEntidad);
                        estIni2.setFemenimo(new BigInteger(String.valueOf(ini2Fem)));
                        estIni2.setMasculino(new BigInteger(String.valueOf(ini2Mas)));
                        estIni2.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("25")));
                        estIni2.setIdProcesoAdq(procesoAdquisicion);

                        estIni3.setCodigoEntidad(codigoEntidad);
                        estIni3.setFemenimo(new BigInteger(String.valueOf(ini3Fem)));
                        estIni3.setMasculino(new BigInteger(String.valueOf(ini3Mas)));
                        estIni3.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("26")));
                        estIni3.setIdProcesoAdq(procesoAdquisicion);

                        estPar.setCodigoEntidad(codigoEntidad);
                        estPar.setFemenimo(new BigInteger(String.valueOf(parFem)));
                        estPar.setMasculino(new BigInteger(String.valueOf(parMas)));
                        estPar.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("22")));
                        estPar.setIdProcesoAdq(procesoAdquisicion);

                        estCiclo1.setCodigoEntidad(codigoEntidad);
                        estCiclo1.setFemenimo(new BigInteger(String.valueOf(grado1fem + grado2fem + grado3fem)));
                        estCiclo1.setMasculino(new BigInteger(String.valueOf(grado1mas + grado2mas + grado3mas)));
                        estCiclo1.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("3")));
                        estCiclo1.setIdProcesoAdq(procesoAdquisicion);

                        estCiclo2.setCodigoEntidad(codigoEntidad);
                        estCiclo2.setFemenimo(new BigInteger(String.valueOf(grado4fem + grado5fem + grado6fem)));
                        estCiclo2.setMasculino(new BigInteger(String.valueOf(grado4mas + grado5mas + grado6mas)));
                        estCiclo2.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("4")));
                        estCiclo2.setIdProcesoAdq(procesoAdquisicion);

                        estCiclo3.setCodigoEntidad(codigoEntidad);
                        estCiclo3.setFemenimo(new BigInteger(String.valueOf(grado7fem + grado8fem + grado9fem)));
                        estCiclo3.setMasculino(new BigInteger(String.valueOf(grado7fem + grado8fem + grado9fem)));
                        estCiclo3.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("5")));
                        estCiclo3.setIdProcesoAdq(procesoAdquisicion);

                        estBac.setCodigoEntidad(codigoEntidad);
                        estBac.setFemenimo(new BigInteger(String.valueOf(b1fem + b2fem + b3fem)));
                        estBac.setMasculino(new BigInteger(String.valueOf(b1mas + b2mas + b3mas)));
                        estBac.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("6")));
                        estBac.setIdProcesoAdq(procesoAdquisicion);

                        estGrado7.setCodigoEntidad(codigoEntidad);
                        estGrado7.setFemenimo(new BigInteger(String.valueOf(grado7fem)));
                        estGrado7.setMasculino(new BigInteger(String.valueOf(grado7mas)));
                        estGrado7.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("7")));
                        estGrado7.setIdProcesoAdq(procesoAdquisicion);

                        estGrado8.setCodigoEntidad(codigoEntidad);
                        estGrado8.setFemenimo(new BigInteger(String.valueOf(grado8fem)));
                        estGrado8.setMasculino(new BigInteger(String.valueOf(grado8mas)));
                        estGrado8.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("8")));
                        estGrado8.setIdProcesoAdq(procesoAdquisicion);

                        estGrado9.setCodigoEntidad(codigoEntidad);
                        estGrado9.setFemenimo(new BigInteger(String.valueOf(grado9fem)));
                        estGrado9.setMasculino(new BigInteger(String.valueOf(grado9mas)));
                        estGrado9.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("9")));
                        estGrado9.setIdProcesoAdq(procesoAdquisicion);

                        estGrado1.setCodigoEntidad(codigoEntidad);
                        estGrado1.setFemenimo(new BigInteger(String.valueOf(grado1fem)));
                        estGrado1.setMasculino(new BigInteger(String.valueOf(grado1mas)));
                        estGrado1.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("10")));
                        estGrado1.setIdProcesoAdq(procesoAdquisicion);

                        estGrado2.setCodigoEntidad(codigoEntidad);
                        estGrado2.setFemenimo(new BigInteger(String.valueOf(grado2fem)));
                        estGrado2.setMasculino(new BigInteger(String.valueOf(grado2mas)));
                        estGrado2.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("11")));
                        estGrado2.setIdProcesoAdq(procesoAdquisicion);

                        estGrado3.setCodigoEntidad(codigoEntidad);
                        estGrado3.setFemenimo(new BigInteger(String.valueOf(grado3fem)));
                        estGrado3.setMasculino(new BigInteger(String.valueOf(grado3mas)));
                        estGrado3.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("12")));
                        estGrado3.setIdProcesoAdq(procesoAdquisicion);

                        estGrado4.setCodigoEntidad(codigoEntidad);
                        estGrado4.setFemenimo(new BigInteger(String.valueOf(grado4fem)));
                        estGrado4.setMasculino(new BigInteger(String.valueOf(grado4mas)));
                        estGrado4.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("13")));
                        estGrado4.setIdProcesoAdq(procesoAdquisicion);

                        estGrado5.setCodigoEntidad(codigoEntidad);
                        estGrado5.setFemenimo(new BigInteger(String.valueOf(grado5fem)));
                        estGrado5.setMasculino(new BigInteger(String.valueOf(grado5mas)));
                        estGrado5.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("14")));
                        estGrado5.setIdProcesoAdq(procesoAdquisicion);

                        estGrado6.setCodigoEntidad(codigoEntidad);
                        estGrado6.setFemenimo(new BigInteger(String.valueOf(grado6fem)));
                        estGrado6.setMasculino(new BigInteger(String.valueOf(grado6mas)));
                        estGrado6.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("15")));
                        estGrado6.setIdProcesoAdq(procesoAdquisicion);

                        estB1.setCodigoEntidad(codigoEntidad);
                        estB1.setFemenimo(new BigInteger(String.valueOf(b1fem)));
                        estB1.setMasculino(new BigInteger(String.valueOf(b1mas)));
                        estB1.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("16")));
                        estB1.setIdProcesoAdq(procesoAdquisicion);

                        estB2.setCodigoEntidad(codigoEntidad);
                        estB2.setFemenimo(new BigInteger(String.valueOf(b2fem)));
                        estB2.setMasculino(new BigInteger(String.valueOf(b2mas)));
                        estB2.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("17")));
                        estB2.setIdProcesoAdq(procesoAdquisicion);

                        estB3.setCodigoEntidad(codigoEntidad);
                        estB3.setFemenimo(new BigInteger(String.valueOf(b3fem)));
                        estB3.setMasculino(new BigInteger(String.valueOf(b3mas)));
                        estB3.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("18")));
                        estB3.setIdProcesoAdq(procesoAdquisicion);

                        asignarTechoCe(estIni2, idProceso);
                        asignarTechoCe(estIni3, idProceso);
                        asignarTechoCe(estPar, idProceso);
                        asignarTechoCe(estCiclo1, idProceso);
                        asignarTechoCe(estCiclo2, idProceso);
                        asignarTechoCe(estCiclo3, idProceso);
                        asignarTechoCe(estBac, idProceso);
                        asignarTechoCe(estGrado7, idProceso);
                        asignarTechoCe(estGrado8, idProceso);
                        asignarTechoCe(estGrado9, idProceso);
                        asignarTechoCe(estGrado1, idProceso);
                        asignarTechoCe(estGrado2, idProceso);
                        asignarTechoCe(estGrado3, idProceso);
                        asignarTechoCe(estGrado4, idProceso);
                        asignarTechoCe(estGrado5, idProceso);
                        asignarTechoCe(estGrado6, idProceso);
                        asignarTechoCe(estB1, idProceso);
                        asignarTechoCe(estB2, idProceso);
                        asignarTechoCe(estB3, idProceso);

                        Logger.getLogger(TechoCE.class.getName()).log(Level.INFO, "codigo:{0}", codigoEntidad);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TechoCE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TechoCE.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(TechoCE.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public List<ResguardoItemDto> getLstResguardoADisminuir(String codigoEntidad, Integer idProcesoAdq, Integer idProcesoAdqAnt, BigDecimal idRubroInteres) {
        String queryName;

        switch (idRubroInteres.intValue()) {
            case 2:
                queryName = "Contratacion.resguardoUtiles";
                break;
            case 3:
                queryName = "Contratacion.resguardoZapatos";
                break;
            default:
                queryName = "Contratacion.resguardoUniformes";
                break;
        }

        Query q = em.createNamedQuery(queryName, ResguardoItemDto.class);
        q.setParameter(1, codigoEntidad);
        q.setParameter(2, idProcesoAdq);
        q.setParameter(3, idProcesoAdqAnt);
        return q.getResultList();
    }
}
