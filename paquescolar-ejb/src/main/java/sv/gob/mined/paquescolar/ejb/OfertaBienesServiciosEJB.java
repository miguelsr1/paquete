/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.DetalleOfertas;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.OfertaBienesServicios;
import sv.gob.mined.paquescolar.model.Participantes;
import sv.gob.mined.paquescolar.model.ResguardoBienes;
import sv.gob.mined.paquescolar.model.ResolucionesAdjudicativas;
import sv.gob.mined.paquescolar.model.pojos.Bean;
import sv.gob.mined.paquescolar.model.pojos.ReportPOIBean;
import sv.gob.mined.paquescolar.model.pojos.contratacion.DetalleContratadoPorComponenteDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.VwCotizacion;
import sv.gob.mined.paquescolar.util.Constantes;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class OfertaBienesServiciosEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;

    public HashMap<String, Object> create(OfertaBienesServicios current, String usuario) {
        HashMap<String, Object> mapDeRetorno = new HashMap();

        try {
            OfertaBienesServicios oferta = getOfertaByProcesoCodigoEntidad(current.getCodigoEntidad().getCodigoEntidad(), current.getIdDetProcesoAdq());
            if (oferta != null) {
                if (oferta.getUsuarioInsercion().equals(usuario)) {
                } else {
                    mapDeRetorno.put(Constantes.ERROR, true);
                    mapDeRetorno.put(Constantes.MSJ_ERROR, "Otro usuario ya creo una oferta para este centro escolar y proceso de contratación.");
                }
            } else {
                if (current.getParticipantesList() == null || current.getParticipantesList().isEmpty()) {
                    mapDeRetorno.put(Constantes.WARNING, true);
                    mapDeRetorno.put(Constantes.MSJ_WARNING, "Primero debe de agregar por lo menos un proveedor a esta oferta.");
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh");

                    current.setEstadoEliminacion(BigInteger.ZERO);
                    current.setFechaInsercion(new Date());
                    current.setUsuarioInsercion(usuario);
                    current.setHoraApertura(new BigInteger(sdf.format(new Date())));

                    sdf = new SimpleDateFormat("mm");
                    current.setMinutoApertura(new BigInteger(sdf.format(new Date())));

                    em.persist(current);

                    mapDeRetorno.put(Constantes.ERROR, false);
                    mapDeRetorno.put(Constantes.WARNING, false);
                }
            }
        } catch (Exception e) {
            mapDeRetorno.put(Constantes.ERROR, true);
            mapDeRetorno.put(Constantes.MSJ_ERROR, "Error en el registro de la oferta. Comunicarse con el Administrador del Sistema.");
            Logger.getLogger(OfertaBienesServiciosEJB.class.getName()).log(Level.WARNING, "Error al intentar registrar una oferta para el CE: " + current.getCodigoEntidad().getCodigoEntidad(), e);
        }
        return mapDeRetorno;
    }

    public OfertaBienesServicios edit(OfertaBienesServicios ofertaBienesServicios) {
        return em.merge(ofertaBienesServicios);
    }

    public OfertaBienesServicios getOfertaByProcesoCodigoEntidad(String codigoEntidad, DetalleProcesoAdq proceso) {
        Query q = em.createQuery("SELECT o FROM OfertaBienesServicios o WHERE o.codigoEntidad.codigoEntidad=:codigoEntidad and o.idDetProcesoAdq=:proceso and o.estadoEliminacion = 0", OfertaBienesServicios.class);
        q.setParameter("codigoEntidad", codigoEntidad);
        q.setParameter("proceso", proceso);
        if (!q.getResultList().isEmpty()) {
            return (OfertaBienesServicios) q.getSingleResult();
        } else {
            return null;
        }
    }

    public Boolean isOfertaRubro(String codigoEntidad, DetalleProcesoAdq idDetProceso) {
        Query query = em.createQuery("SELECT o FROM OfertaBienesServicios o WHERE o.codigoEntidad.codigoEntidad=:codigoEntidad and o.idDetProcesoAdq=:proceso and o.estadoEliminacion = 0");
        query.setParameter("codigoEntidad", codigoEntidad);
        query.setParameter("proceso", idDetProceso);

        return !query.getResultList().isEmpty();
    }

    private String getIdDetalleProcesoPadre(DetalleProcesoAdq idDetProceso) {
        String ids = "";
        Query q = em.createNativeQuery("select det.id_det_proceso_adq \n"
                + "from detalle_proceso_Adq det\n"
                + "    inner join proceso_adquisicion pro on pro.id_proceso_adq = det.id_proceso_adq\n"
                + "    inner join anho     on pro.id_anho = anho.id_anho\n"
                + "where anho.anho = ?1 and det.id_rubro_adq = ?2");
        q.setParameter(1, idDetProceso.getIdProcesoAdq().getIdAnho().getAnho());
        q.setParameter(2, idDetProceso.getIdRubroAdq().getIdRubroInteres());
        List<BigDecimal> lst = q.getResultList();
        for (BigDecimal id : lst) {
            if (ids.isEmpty()) {
                ids = id.toString();
            } else {
                ids = ids + "," + id.toString();
            }
        }

        return ids;
    }

    public List<Object> getDatosRptAnalisisEconomico(String codigoEntidad, DetalleProcesoAdq idDetProceso) {
        String query = "";
        ReportPOIBean reportPOIBean = null;
        List<Object> listadoAExportar = new LinkedList<>();
        try {
            Connection conn = em.unwrap(java.sql.Connection.class);

            switch (idDetProceso.getIdRubroAdq().getIdRubroInteres().intValue()) {
                case 1:
                case 4:
                case 5:
                    String idDetProcesos = getIdDetalleProcesoPadre(idDetProceso);

                    query = String.format(Constantes.QUERY_CONTRATACION_ANALISIS_ECONOMICO_UNIFORME, idDetProcesos, codigoEntidad, idDetProceso.getIdDetProcesoAdq());
                    break;
                case 2:
                    query = String.format(Constantes.QUERY_CONTRATACION_ANALISIS_ECONOMICO_UTILES, codigoEntidad, idDetProceso.getIdDetProcesoAdq());
                    break;
                case 3:
                    query = String.format(Constantes.QUERY_CONTRATACION_ANALISIS_ECONOMICO_ZAPATOS, codigoEntidad, idDetProceso.getIdDetProcesoAdq());
                    break;
            }
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery(query);

            LinkedHashMap<String, Integer> mapaItems = new LinkedHashMap<>();
            LinkedHashMap<String, String> mapaItemsIndex = new LinkedHashMap<>();
            LinkedHashMap<String, Integer> mapaRazonSocial = new LinkedHashMap<>();
            LinkedHashMap<String, Integer> mapaCantidades = new LinkedHashMap<>();

            Integer itemIndex = 0;
            Integer razonSocialIndex = 0;
            while (rs.next()) {
                if (!mapaItems.containsKey(rs.getString("descripcion_item"))) {
                    mapaItems.put(rs.getString("descripcion_item"), itemIndex);
                    mapaItemsIndex.put(rs.getString("descripcion_item"), rs.getString("no_item"));
                    mapaCantidades.put(rs.getString("descripcion_item"), rs.getInt("num_alumno"));
                    itemIndex++;
                }
                if (!mapaRazonSocial.containsKey(rs.getString("razon_social"))) {
                    mapaRazonSocial.put(rs.getString("razon_social"), razonSocialIndex);
                    razonSocialIndex++;
                }
            }
            Bean[][] datos = new Bean[mapaItems.size()][mapaRazonSocial.size()];
            rs.beforeFirst();
            while (rs.next()) {
                int x = mapaItems.get(rs.getString("descripcion_item"));
                int y = mapaRazonSocial.get(rs.getString("razon_social"));
                Bean bean = new Bean();
                bean.setCantidadOfertada(rs.getInt("num_alumno"));
                bean.setPrecioUnitario(rs.getDouble("precio_referencia"));
                bean.setCantidadAdjudicada(rs.getInt("adjudicada"));
                datos[x][y] = bean;
            }
            listadoAExportar.add(mapaItems);
            listadoAExportar.add(mapaRazonSocial);
            listadoAExportar.add(datos);
            listadoAExportar.add(mapaItemsIndex);
            listadoAExportar.add(mapaCantidades);

            listadoAExportar.add(reportPOIBean);

            return listadoAExportar;
        } catch (SQLException ex) {
            Logger.getLogger(OfertaBienesServiciosEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listadoAExportar;
    }

    public List<VwCotizacion> getLstCotizacion(String municipio, String codigoEntidad, DetalleProcesoAdq idProceso, Participantes participante) {
        List<VwCotizacion> lstCotizacion= new ArrayList();
        try {
            Query q = em.createNamedQuery("Contratacion.VwCotizacion", VwCotizacion.class);
            q.setParameter(1, codigoEntidad);
            q.setParameter(2, idProceso.getIdDetProcesoAdq());
            q.setParameter(3, participante.getIdParticipante());

            lstCotizacion = q.getResultList();

            VwCotizacion v = lstCotizacion.get(0);

            v.setLstDetalleOferta(new ArrayList());
            v.setLstDetalleOfertaLibros(new ArrayList());
            v.setLugarFecha(municipio.concat(",").concat(v.getFechaApertura()));

            String nombreVista = "";

            switch (idProceso.getIdRubroAdq().getIdRubroInteres().intValue()) {
                case 1:
                case 4:
                case 5:
                    nombreVista = "VW_COTIZACION_UNIFORME";
                    break;
                case 2:
                    nombreVista = "VW_COTIZACION_UTILES";
                    break;
                case 3:
                    nombreVista = "VW_COTIZACION_ZAPATOS";
                    break;
            }

            q = em.createNativeQuery("select distinct * FROM " + nombreVista + " WHERE id_empresa=?1 and codigo_entidad=?2 and id_proceso_estadistica=?3 and (id_proceso_precio=?4 or id_proceso_precio=?5) order by to_number(no_item), id_nivel_educativo");
            q.setParameter(1, participante.getIdEmpresa().getIdEmpresa());
            q.setParameter(2, codigoEntidad);
            q.setParameter(3, idProceso.getIdProcesoAdq().getIdProcesoAdq());
            q.setParameter(4, idProceso.getIdProcesoAdq().getIdProcesoAdq());
            q.setParameter(5, idProceso.getIdProcesoAdq().getPadreIdProcesoAdq() == null ? null : idProceso.getIdProcesoAdq().getPadreIdProcesoAdq().getIdProcesoAdq());

            List lst2 = q.getResultList();

            lst2.forEach((object1) -> {
                Object[] datos1 = (Object[]) object1;
                DetalleOfertas det = new DetalleOfertas();
                det.setNoItem(datos1[9].toString());
                det.setConsolidadoEspTec(datos1[0].toString().concat(", ").concat(datos1[1].toString()));
                det.setCantidad(new BigInteger(datos1[2].toString()));
                det.setPrecioUnitario(new BigDecimal(datos1[3].toString()));

                if (det.getConsolidadoEspTec().contains("Libro")) {
                    v.getLstDetalleOfertaLibros().add(det);
                } else {
                    v.getLstDetalleOferta().add(det);
                }
            });
        } catch (Exception e) {
            Logger.getLogger(OfertaBienesServiciosEJB.class.getName()).log(Level.WARNING, "Error en la generacion de la cotizacion {0} {1} {2}", new Object[]{codigoEntidad, participante, idProceso});
        }

        return lstCotizacion;
    }

    public ResolucionesAdjudicativas findResolucionesAdjudicativas(Participantes participante) {
        Query query = em.createQuery("SELECT r FROM ResolucionesAdjudicativas r WHERE r.idParticipante=:participante and r.estadoEliminacion=0 and r.idEstadoReserva.idEstadoReserva not in (4)", ResolucionesAdjudicativas.class);
        query.setParameter("participante", participante);
        List<ResolucionesAdjudicativas> lstResoluciones = query.getResultList();

        if (lstResoluciones.isEmpty()) {
            return null;
        } else {
            return lstResoluciones.get(0);
        }
    }
    
    public ResolucionesAdjudicativas editResolucion(ResolucionesAdjudicativas resolucionesAdjudicativas, String usuarioModif) {
        resolucionesAdjudicativas.setFechaModificacion(new Date());
        resolucionesAdjudicativas.setUsuarioModificacion(usuarioModif);

        return em.merge(resolucionesAdjudicativas);
    }

    public List<ResguardoBienes> getLstResguardoBienesByCodEntAndIdDetPro(String codigoEntidad, Integer idDetProcesoAdq) {
        Query q = em.createQuery("SELECT r FROM ResguardoBienes r WHERE r.codigoEntidad=:codEnt and r.idDetProcesoAdq.idDetProcesoAdq=:idDet and r.estadoEliminacion = 0 ORDER BY r.idNivelEducativo.idNivelEducativo, r.idProducto.idProducto", ResguardoBienes.class);
        q.setParameter("codEnt", codigoEntidad);
        q.setParameter("idDet", idDetProcesoAdq);
        return q.getResultList();
    }

    public List<DetalleContratadoPorComponenteDto> getLstDetalleContratado(String codigoEntidad, Integer idDetProcesoAdq) {
        Query q = em.createNamedQuery("Contratacion.DetalleContratadoPorComponenteDto", DetalleContratadoPorComponenteDto.class);
        q.setParameter(1, codigoEntidad);
        q.setParameter(2, idDetProcesoAdq);
        return q.getResultList();
    }

    public void guardarResguardo(ResguardoBienes resguardoBienes, String usuario) {
        if (resguardoBienes.getIdResguardo() == null) {
            resguardoBienes.setFechaInsercion(new Date());
            resguardoBienes.setUsuarioInsercion(usuario);
            em.persist(resguardoBienes);
        } else {
            resguardoBienes.setFechaModificacion(new Date());
            resguardoBienes.setUsuarioModificacion(usuario);
            em.merge(resguardoBienes);
        }
    }
}
