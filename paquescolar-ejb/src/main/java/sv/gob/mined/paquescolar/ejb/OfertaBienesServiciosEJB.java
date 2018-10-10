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
import java.util.ArrayList;
import java.util.Date;
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
import sv.gob.mined.paquescolar.model.ResolucionesAdjudicativas;
import sv.gob.mined.paquescolar.model.pojos.Bean;
import sv.gob.mined.paquescolar.model.pojos.ReportPOIBean;
import sv.gob.mined.paquescolar.model.view.VwCotizacion;
import sv.gob.mined.paquescolar.util.StringUtils;

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

    public void create(OfertaBienesServicios ofertaBienesServicios) {
        em.persist(ofertaBienesServicios);
    }

    public OfertaBienesServicios edit(OfertaBienesServicios ofertaBienesServicios) {
        return em.merge(ofertaBienesServicios);
    }

    public OfertaBienesServicios getOfertaByProcesoCodigoEntidad(String codigoEntidad, DetalleProcesoAdq proceso) {
        Query q = em.createQuery("SELECT o FROM OfertaBienesServicios o WHERE o.codigoEntidad.codigoEntidad=:codigoEntidad and o.idDetProcesoAdq=:proceso and o.estadoEliminacion = 0", OfertaBienesServicios.class);
        q.setParameter("codigoEntidad", codigoEntidad);
        q.setParameter("proceso", proceso);
        if (q.getResultList() != null && !q.getResultList().isEmpty()) {
            OfertaBienesServicios oferta = (OfertaBienesServicios) q.getSingleResult();
            for (int i = oferta.getParticipantesList().size() - 1; i >= 0; i--) {
                if (oferta.getParticipantesList().get(i).getEstadoEliminacion().compareTo(BigInteger.ONE) == 0) {
                    oferta.getParticipantesList().remove(oferta.getParticipantesList().get(i));
                }
            }

            for (Participantes par : oferta.getParticipantesList()) {
                for (int i = par.getDetalleOfertasList().size() - 1; i >= 0; i--) {
                    if (par.getDetalleOfertasList().get(i).getEstadoEliminacion().compareTo(BigInteger.ONE) == 0) {
                        par.getDetalleOfertasList().remove(par.getDetalleOfertasList().get(i));
                    }
                }
            }
            return oferta;
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
                    String idProcesos = "";
                    for (DetalleProcesoAdq detalleProcesoAdq : idDetProceso.getIdProcesoAdq().getDetalleProcesoAdqList()) {
                        if(detalleProcesoAdq.getIdRubroAdq().getIdRubroUniforme().intValue() == 1)
                        if (idProcesos.isEmpty()) {
                            idProcesos = detalleProcesoAdq.getIdDetProcesoAdq() + "";
                        } else {
                            idProcesos += "," + detalleProcesoAdq.getIdDetProcesoAdq();
                        }
                    }

                    query = String.format(StringUtils.QUERY_CONTRATACION_ANALISIS_ECONOMICO_UNIFORME, idProcesos, codigoEntidad, idDetProceso.getIdDetProcesoAdq());
                    break;
                case 2:
                    query = String.format(StringUtils.QUERY_CONTRATACION_ANALISIS_ECONOMICO_UTILES, codigoEntidad, idDetProceso.getIdDetProcesoAdq());
                    break;
                case 3:
                    query = String.format(StringUtils.QUERY_CONTRATACION_ANALISIS_ECONOMICO_ZAPATOS, codigoEntidad, idDetProceso.getIdDetProcesoAdq());
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
        } finally {
            return listadoAExportar;
        }
    }

    public List<VwCotizacion> getLstCotizacion(String municipio, String codigoEntidad, DetalleProcesoAdq idProceso, Participantes participante) {
        List<VwCotizacion> lstCotizacion = new ArrayList<>();
        Query q = em.createNativeQuery("select * from vw_cabecera_cotizacion where codigo_entidad=?1 and ID_DET_PROCESO_ADQ=?2 and id_participante=?3");
        q.setParameter(1, codigoEntidad);
        q.setParameter(2, idProceso.getIdDetProcesoAdq());
        q.setParameter(3, participante.getIdParticipante());

        List lst = q.getResultList();

        for (Object object : lst) {
            Object[] datos = (Object[]) object;
            VwCotizacion v = new VwCotizacion();
            v.setLstDetalleOferta(new ArrayList<DetalleOfertas>());
            v.setLstDetalleOfertaLibros(new ArrayList<DetalleOfertas>());
            v.setFechaApertura(datos[0].toString());
            v.setRazonSocial(datos[1].toString());
            v.setModalidadAdministrativa(datos[2].toString());
            v.setNombreCe(datos[3].toString());
            v.setCodigoEntidad(datos[4].toString());
            v.setDireccionCe(datos[5].toString());
            v.setNombreRespresenanteEmp(datos[6].toString());
            v.setNombreRepresentanteCe(datos[7].toString());
            v.setLugarFecha(municipio.concat(",").concat(v.getFechaApertura()));
            v.setUsuarioInsercion(datos[10].toString());

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
            for (Object object1 : lst2) {
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
            }

            lstCotizacion.add(v);
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
}
