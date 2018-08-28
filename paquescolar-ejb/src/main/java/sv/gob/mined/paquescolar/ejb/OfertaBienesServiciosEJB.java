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
                    Integer idDetEstadisticas;

                    if (idDetProceso.getIdRubroAdq().getIdRubroInteres().intValue() == 5) {
                        Query q = em.createQuery("SELECT d FROM DetalleProcesoAdq d WHERE d.idRubroAdq.idRubroInteres=:idRubro and d.idProcesoAdq.idProcesoAdq=:idProcesoAdq", DetalleProcesoAdq.class);
                        q.setParameter("idRubro", 4);
                        q.setParameter("idProcesoAdq", idDetProceso.getIdProcesoAdq().getIdProcesoAdq());
                        idDetEstadisticas = ((DetalleProcesoAdq) q.getSingleResult()).getIdDetProcesoAdq();
                    } else {
                        idDetEstadisticas = idDetProceso.getIdDetProcesoAdq();
                    }

                    query = "SELECT precios_ref_rubro_emp.no_item ,\n"
                            + "      catalogo_producto.nombre_producto || ' para ' || nivel_educativo.DESCRIPCION_NIVEL descripcion_item, \n"
                            + "                      case catalogo_producto.id_producto \n"
                            + "                        when 30 then \n"
                            + "                            case nivel_educativo.ID_NIVEL_EDUCATIVO\n"
                            + "                            when 6 then VW_DETALLE_COTIZACION_UNIFORME.FEMENIMO\n"
                            + "                            else VW_DETALLE_COTIZACION_UNIFORME.FEMENIMO\n"
                            + "                            end\n"
                            + "                        when 44 then \n"
                            + "                            case nivel_educativo.ID_NIVEL_EDUCATIVO\n"
                            + "                            when 6 then VW_DETALLE_COTIZACION_UNIFORME.FEMENIMO\n"
                            + "                            else VW_DETALLE_COTIZACION_UNIFORME.FEMENIMO\n"
                            + "                            end\n"
                            + "                        when 32 then \n"
                            + "                            case nivel_educativo.ID_NIVEL_EDUCATIVO\n"
                            + "                            when 6 then VW_DETALLE_COTIZACION_UNIFORME.FEMENIMO\n"
                            + "                            else VW_DETALLE_COTIZACION_UNIFORME.FEMENIMO\n"
                            + "                            end\n"
                            + "                        when 29 then \n"
                            + "                            case nivel_educativo.ID_NIVEL_EDUCATIVO\n"
                            + "                            when 6 then VW_DETALLE_COTIZACION_UNIFORME.MASCULINO\n"
                            + "                            else VW_DETALLE_COTIZACION_UNIFORME.MASCULINO\n"
                            + "                            end\n"
                            + "                        when 34 then \n"
                            + "                            case nivel_educativo.ID_NIVEL_EDUCATIVO\n"
                            + "                            when 6 then VW_DETALLE_COTIZACION_UNIFORME.MASCULINO\n"
                            + "                            else VW_DETALLE_COTIZACION_UNIFORME.MASCULINO\n"
                            + "                            end\n"
                            + "                        when 31 then \n"
                            + "                            case nivel_educativo.ID_NIVEL_EDUCATIVO\n"
                            + "                            when 6 then VW_DETALLE_COTIZACION_UNIFORME.MASCULINO\n"
                            + "                            else VW_DETALLE_COTIZACION_UNIFORME.MASCULINO\n"
                            + "                            end\n"
                            + "                      end num_alumno,\n"
                            + "                      precios_ref_rubro_emp.precio_referencia, \n"
                            + "                      precios_ref_rubro_emp.precio_referencia*(VW_DETALLE_COTIZACION_UNIFORME.MASCULINO + VW_DETALLE_COTIZACION_UNIFORME.FEMENIMO) sub_total, \n"
                            + "                      nivel_educativo.ID_NIVEL_EDUCATIVO, \n"
                            + "                      VW_DETALLE_COTIZACION_UNIFORME.CODIGO_ENTIDAD, \n"
                            + "                      precios_ref_rubro_emp.ID_DET_PROCESO_ADQ, \n"
                            + "                      precios_ref_rubro_emp.id_empresa, \n"
                            + "                      empresa.razon_social \n"
                            + "FROM precios_ref_rubro_emp \n"
                            + "  INNER JOIN VW_DETALLE_COTIZACION_UNIFORME ON precios_ref_rubro_emp.ID_NIVEL_EDUCATIVO = VW_DETALLE_COTIZACION_UNIFORME.ID_NIVEL_EDUCATIVO \n"
                            + "  INNER JOIN catalogo_producto ON catalogo_producto.ID_PRODUCTO = precios_ref_rubro_emp.ID_PRODUCTO \n"
                            + "  INNER JOIN nivel_educativo ON precios_ref_rubro_emp.ID_NIVEL_EDUCATIVO = nivel_educativo.ID_NIVEL_EDUCATIVO \n"
                            + "  inner join empresa on precios_ref_rubro_emp.id_empresa = empresa.id_empresa \n"
                            + "  inner join participantes on participantes.id_empresa = empresa.id_empresa \n"
                            + "  inner join oferta_bienes_servicios on participantes.id_oferta = oferta_bienes_servicios.id_oferta \n"
                            + "    and VW_DETALLE_COTIZACION_UNIFORME.codigo_entidad = oferta_bienes_servicios.codigo_entidad \n"
                            + "WHERE VW_DETALLE_COTIZACION_UNIFORME.ID_DET_PROCESO_ADQ = " + idDetEstadisticas + " and  \n"
                            + "  precios_ref_rubro_emp.estado_eliminacion = 0 and \n"
                            + "  oferta_bienes_servicios.ID_DET_PROCESO_ADQ = " + idDetProceso.getIdDetProcesoAdq() + " and \n"
                            + "  oferta_bienes_servicios.codigo_entidad in (" + codigoEntidad + ") and \n"
                            + "  oferta_bienes_servicios.estado_eliminacion = 0 and "
                            + "  participantes.estado_eliminacion = 0  and "
                            + idDetEstadisticas + "  = precios_ref_rubro_emp.ID_DET_PROCESO_ADQ \n"
                            + "order by to_number(precios_ref_rubro_emp.no_item), precios_ref_rubro_emp.id_empresa";
                    break;
                case 2:

                    query = "SELECT precios_ref_rubro_emp.no_item,"
                            + "  catalogo_producto.nombre_producto || ' para ' || nivel_educativo.DESCRIPCION_NIVEL descripcion_item, "
                            + "  estadistica_censo.MASCULINO + estadistica_censo.FEMENIMO num_alumno, "
                            + "  precios_ref_rubro_emp.precio_referencia, "
                            + "  precios_ref_rubro_emp.precio_referencia*(estadistica_censo.MASCULINO + estadistica_censo.FEMENIMO) sub_total, "
                            + "  nivel_educativo.ID_NIVEL_EDUCATIVO, "
                            + "  estadistica_censo.CODIGO_ENTIDAD, "
                            + "  precios_ref_rubro_emp.ID_DET_PROCESO_ADQ, "
                            + "  precios_ref_rubro_emp.id_empresa, "
                            + "  empresa.razon_social "
                            + "FROM precios_ref_rubro_emp "
                            + "  INNER JOIN estadistica_censo ON precios_ref_rubro_emp.ID_NIVEL_EDUCATIVO = estadistica_censo.ID_NIVEL_EDUCATIVO "
                            + "  INNER JOIN catalogo_producto ON catalogo_producto.ID_PRODUCTO = precios_ref_rubro_emp.ID_PRODUCTO "
                            + "  INNER JOIN nivel_educativo ON precios_ref_rubro_emp.ID_NIVEL_EDUCATIVO = nivel_educativo.ID_NIVEL_EDUCATIVO "
                            + "  inner join empresa on precios_ref_rubro_emp.id_empresa = empresa.id_empresa "
                            + "  inner join participantes on participantes.id_empresa = empresa.id_empresa "
                            + "  inner join oferta_bienes_servicios on participantes.id_oferta = oferta_bienes_servicios.id_oferta "
                            + "    and estadistica_censo.codigo_entidad = oferta_bienes_servicios.codigo_entidad "
                            + "  inner join detalle_proceso_adq on detalle_proceso_adq.ID_PROCESO_ADQ = estadistica_censo.ID_PROCESO_ADQ\n"
                            + "    and oferta_bienes_servicios.id_det_proceso_adq = detalle_proceso_adq.id_det_proceso_adq\n"
                            + "WHERE "
                            + "  precios_ref_rubro_emp.estado_eliminacion = 0 and \n"
                            + "  oferta_bienes_servicios.ID_DET_PROCESO_ADQ = " + idDetProceso.getIdDetProcesoAdq() + " and "
                            + "  oferta_bienes_servicios.codigo_entidad in (" + codigoEntidad + ") and "
                            + "  oferta_bienes_servicios.estado_eliminacion = 0 and"
                            + "  participantes.estado_eliminacion = 0  and "
                            + "  oferta_bienes_servicios.ID_DET_PROCESO_ADQ = precios_ref_rubro_emp.ID_DET_PROCESO_ADQ "
                            + "order by  to_number(precios_ref_rubro_emp.no_item), precios_ref_rubro_emp.id_empresa";
                    break;
                case 3:
                    query = "SELECT \n"
                            + "  precios_ref_rubro_emp.no_item,\n"
                            + "  catalogo_producto.nombre_producto || ' para ' || nivel_educativo.DESCRIPCION_NIVEL descripcion_item,   \n"
                            + "  CASE catalogo_producto.id_producto\n"
                            + "    when 43 then estadistica_censo.MASCULINO\n"
                            + "    when 21 then estadistica_censo.FEMENIMO \n"
                            + "    end num_alumno,\n"
                            + "  precios_ref_rubro_emp.precio_referencia,   \n"
                            + "  precios_ref_rubro_emp.precio_referencia*(estadistica_censo.MASCULINO + estadistica_censo.FEMENIMO) sub_total,   \n"
                            + "  nivel_educativo.ID_NIVEL_EDUCATIVO,   \n"
                            + "  estadistica_censo.CODIGO_ENTIDAD,   \n"
                            + "  precios_ref_rubro_emp.ID_DET_PROCESO_ADQ,   \n"
                            + "  precios_ref_rubro_emp.id_empresa,   \n"
                            + "  empresa.razon_social \n"
                            + "FROM precios_ref_rubro_emp   \n"
                            + "  INNER JOIN estadistica_censo ON precios_ref_rubro_emp.ID_NIVEL_EDUCATIVO = estadistica_censo.ID_NIVEL_EDUCATIVO   \n"
                            + "  INNER JOIN catalogo_producto ON catalogo_producto.ID_PRODUCTO = precios_ref_rubro_emp.ID_PRODUCTO   \n"
                            + "  INNER JOIN nivel_educativo ON precios_ref_rubro_emp.ID_NIVEL_EDUCATIVO = nivel_educativo.ID_NIVEL_EDUCATIVO   \n"
                            + "  inner join empresa on precios_ref_rubro_emp.id_empresa = empresa.id_empresa   \n"
                            + "  inner join participantes on participantes.id_empresa = empresa.id_empresa   \n"
                            + "  inner join oferta_bienes_servicios on participantes.id_oferta = oferta_bienes_servicios.id_oferta    \n"
                            + "    and estadistica_censo.codigo_entidad = oferta_bienes_servicios.codigo_entidad \n"
                            + "  inner join detalle_proceso_adq on detalle_proceso_adq.ID_PROCESO_ADQ = estadistica_censo.ID_PROCESO_ADQ\n"
                            + "    and oferta_bienes_servicios.id_det_proceso_adq = detalle_proceso_adq.id_det_proceso_adq\n"
                            + "WHERE "
                            + "  precios_ref_rubro_emp.estado_eliminacion = 0 and \n"
                            + "  oferta_bienes_servicios.ID_DET_PROCESO_ADQ = " + idDetProceso.getIdDetProcesoAdq() + " and \n"
                            + "  oferta_bienes_servicios.codigo_entidad in (" + codigoEntidad + ") \n"
                            + "  and oferta_bienes_servicios.estado_eliminacion = 0 and"
                            + "  participantes.estado_eliminacion = 0  and"
                            + "  oferta_bienes_servicios.ID_DET_PROCESO_ADQ = precios_ref_rubro_emp.ID_DET_PROCESO_ADQ  \n"
                            + "order by to_number(precios_ref_rubro_emp.no_item), \n"
                            + "precios_ref_rubro_emp.id_empresa";
                    break;
            }
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);

            LinkedHashMap<String, Integer> mapaItems = new LinkedHashMap<>();
            LinkedHashMap<String, Integer> mapaItemsIndex = new LinkedHashMap<>();
            LinkedHashMap<String, Integer> mapaRazonSocial = new LinkedHashMap<>();
            LinkedHashMap<String, Integer> mapaCantidades = new LinkedHashMap<>();

            Integer itemIndex = 0;
            Integer razonSocialIndex = 0;
            while (rs.next()) {
                if (!mapaItems.containsKey(rs.getString("descripcion_item"))) {
                    mapaItems.put(rs.getString("descripcion_item"), itemIndex);
                    mapaItemsIndex.put(rs.getString("descripcion_item"), rs.getInt("no_item"));
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
                bean.setCantidadAdjudicada("");
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
                v.getLstDetalleOferta().add(det);
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
