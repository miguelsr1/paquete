/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
import sv.gob.mined.paquescolar.model.DetalleModificativa;
import sv.gob.mined.paquescolar.model.DetalleOfertas;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.DetalleRecepcion;
import sv.gob.mined.paquescolar.model.EstadoSeguimiento;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.RecepcionBienesServicios;
import sv.gob.mined.paquescolar.model.ResolucionesModificativas;
import sv.gob.mined.paquescolar.model.pojos.ReporteGeneralDTO;
import sv.gob.mined.paquescolar.model.pojos.ReporteProveedorDTO;
import sv.gob.mined.paquescolar.model.pojos.recepcion.DetalleItems;
import sv.gob.mined.paquescolar.model.pojos.recepcion.ReportePorDepartamentoDto;
import sv.gob.mined.paquescolar.model.pojos.modificativa.VwBusquedaContratos;
import sv.gob.mined.paquescolar.model.pojos.recepcion.RptEntregasGeneralPorDepartamentoDto;
import sv.gob.mined.paquescolar.model.view.VwBusquedaSeguimientos;
import sv.gob.mined.paquescolar.model.view.VwSeguimientoRptCentroEscolar;
import sv.gob.mined.paquescolar.util.Constantes;

/**
 *
 * @author DesarrolloPc
 */
@Stateless
@LocalBean
public class RecepcionEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;
    
    public ContratosOrdenesCompras getContratoOrdenCompra(BigDecimal idContrato) {
        if (idContrato != null) {
            return em.find(ContratosOrdenesCompras.class, idContrato);
        } else {
            return new ContratosOrdenesCompras();
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
    
    public List<VwBusquedaContratos> getLstBusquedaContratosFisico(DetalleProcesoAdq proceso, String codigoEntidad, String codigoDepartamento, String numeroNit, String numeroContrato, String nombreProveedor) {
        String cadenaWhere = Constantes.addCampoToWhere("", "ID_DET_PROCESO_ADQ", proceso.getIdDetProcesoAdq());
        cadenaWhere = Constantes.addCampoToWhere(cadenaWhere, "codigo_entidad", codigoEntidad);
        if (codigoDepartamento != null && !codigoDepartamento.equals("00")) {
            cadenaWhere = Constantes.addCampoToWhere(cadenaWhere, "codigo_departamento", codigoDepartamento);
        }
        cadenaWhere = Constantes.addCampoToWhere(cadenaWhere, "numero_nit", numeroNit);
        
        if (!nombreProveedor.isEmpty() && nombreProveedor.length() > 0) {
            nombreProveedor = nombreProveedor.toUpperCase();
            cadenaWhere += (cadenaWhere.isEmpty() ? "" : " AND ").concat(" razon_social like'%" + nombreProveedor + "%'");
        }
        cadenaWhere = Constantes.addCampoToWhere(cadenaWhere, "numero_contrato", numeroContrato);
        
        Query query = em.createNativeQuery(Constantes.QUERY_RECEPCION_FIND_CONTRATOS_FISICOS + cadenaWhere, VwBusquedaContratos.class);
        return query.getResultList();
    }
    
    public List<ReporteProveedorDTO> getLstReporteProveedores(ProcesoAdquisicion proceso, String codigoDepartamento, BigDecimal rubroSeg) {
        DetalleProcesoAdq procesos = getDepProcesosAdqProveedor(proceso, rubroSeg);
        List<ReporteProveedorDTO> lstContratos = new ArrayList();
        String sql = "SELECT * "
                + "FROM "
                + "  (SELECT TBL1.CODIGO_DEPARTAMENTO, "
                + "    TBL1.NOMBRE_DEPARTAMENTO, "
                + "    TBL1.RAZON_SOCIAL, "
                + "    TBL1.NUMERO_NIT, "
                + "    TBL1.TOTAL_UNIFORNES, "
                + "    TBL2.TOTAL_ENTREGADOS, "
                + "    NVL(TOTAL_ENTREGADOS,0)/NVL(TOTAL_UNIFORNES,0)*100 AVANCE "
                + "  FROM "
                + "    (SELECT DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      EMPRESA.RAZON_SOCIAL, "
                + "      EMPRESA.NUMERO_NIT, "
                + "      COUNT(DISTINCT CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO) TOTAL_UNIFORNES "
                + "    FROM CONTRATOS_ORDENES_COMPRAS "
                + "    INNER JOIN RESOLUCIONES_ADJUDICATIVAS "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_RESOLUCION_ADJ = CONTRATOS_ORDENES_COMPRAS.ID_RESOLUCION_ADJ "
                + "    INNER JOIN ESTADO_RESERVA "
                + "    ON ESTADO_RESERVA.ID_ESTADO_RESERVA = RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA "
                + "    INNER JOIN PARTICIPANTES "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_PARTICIPANTE = PARTICIPANTES.ID_PARTICIPANTE "
                + "    INNER JOIN DETALLE_OFERTAS "
                + "    ON PARTICIPANTES.ID_PARTICIPANTE = DETALLE_OFERTAS.ID_PARTICIPANTE "
                + "    INNER JOIN EMPRESA "
                + "    ON PARTICIPANTES.ID_EMPRESA = EMPRESA.ID_EMPRESA "
                + "    INNER JOIN MUNICIPIO "
                + "    ON EMPRESA.ID_MUNICIPIO = MUNICIPIO.ID_MUNICIPIO "
                + "    INNER JOIN DEPARTAMENTO "
                + "    ON municipio.CODIGO_DEPARTAMENTO = DEPARTAMENTO.CODIGO_DEPARTAMENTO "
                + "    INNER JOIN OFERTA_BIENES_SERVICIOS "
                + "    ON PARTICIPANTES.ID_OFERTA = OFERTA_BIENES_SERVICIOS.ID_OFERTA "
                + "    INNER JOIN DETALLE_PROCESO_ADQ "
                + "    ON OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ = DETALLE_PROCESO_ADQ.ID_DET_PROCESO_ADQ "
                + "    INNER JOIN VW_CATALOGO_ENTIDAD_EDUCATIVA "
                + "    ON OFERTA_BIENES_SERVICIOS.CODIGO_ENTIDAD          = VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD "
                + "    WHERE CONTRATOS_ORDENES_COMPRAS.ESTADO_ELIMINACION = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ESTADO_ELIMINACION     = 0 "
                + "    AND PARTICIPANTES.ESTADO_ELIMINACION               = 0 "
                + "    AND DETALLE_OFERTAS.ESTADO_ELIMINACION             = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ESTADO_ELIMINACION  = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA  IN (2, 4, 5) "
                + "    AND CONTRATOS_ORDENES_COMPRAS.MODIFICATIVA         = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ    IN (" + procesos.getIdDetProcesoAdq() + ") "
                + "    GROUP BY DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      EMPRESA.RAZON_SOCIAL, "
                + "      EMPRESA.NUMERO_NIT "
                + "    ) TBL1 "
                + "  LEFT OUTER JOIN "
                + "    (SELECT DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      EMPRESA.RAZON_SOCIAL, "
                + "      EMPRESA.NUMERO_NIT, "
                + "      COUNT(DISTINCT CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO) TOTAL_ENTREGADOS "
                + "    FROM RECEPCION_BIENES_SERVICIOS "
                + "    INNER JOIN CONTRATOS_ORDENES_COMPRAS "
                + "    ON RECEPCION_BIENES_SERVICIOS.ID_CONTRATO = CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO "
                + "    INNER JOIN RESOLUCIONES_ADJUDICATIVAS "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_RESOLUCION_ADJ = CONTRATOS_ORDENES_COMPRAS.ID_RESOLUCION_ADJ "
                + "    INNER JOIN ESTADO_RESERVA "
                + "    ON ESTADO_RESERVA.ID_ESTADO_RESERVA = RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA "
                + "    INNER JOIN PARTICIPANTES "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_PARTICIPANTE = PARTICIPANTES.ID_PARTICIPANTE "
                + "    INNER JOIN DETALLE_OFERTAS "
                + "    ON PARTICIPANTES.ID_PARTICIPANTE = DETALLE_OFERTAS.ID_PARTICIPANTE "
                + "    INNER JOIN EMPRESA "
                + "    ON PARTICIPANTES.ID_EMPRESA = EMPRESA.ID_EMPRESA "
                + "    INNER JOIN MUNICIPIO "
                + "    ON EMPRESA.ID_MUNICIPIO = MUNICIPIO.ID_MUNICIPIO "
                + "    INNER JOIN DEPARTAMENTO "
                + "    ON municipio.CODIGO_DEPARTAMENTO = DEPARTAMENTO.CODIGO_DEPARTAMENTO "
                + "    INNER JOIN OFERTA_BIENES_SERVICIOS "
                + "    ON PARTICIPANTES.ID_OFERTA = OFERTA_BIENES_SERVICIOS.ID_OFERTA "
                + "    INNER JOIN DETALLE_PROCESO_ADQ "
                + "    ON OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ = DETALLE_PROCESO_ADQ.ID_DET_PROCESO_ADQ "
                + "    INNER JOIN VW_CATALOGO_ENTIDAD_EDUCATIVA "
                + "    ON OFERTA_BIENES_SERVICIOS.CODIGO_ENTIDAD            = VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD "
                + "    WHERE CONTRATOS_ORDENES_COMPRAS.ESTADO_ELIMINACION   = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ESTADO_ELIMINACION       = 0 "
                + "    AND PARTICIPANTES.ESTADO_ELIMINACION                 = 0 "
                + "    AND DETALLE_OFERTAS.ESTADO_ELIMINACION               = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ESTADO_ELIMINACION    = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA    IN (2, 4, 5) "
                + "    AND CONTRATOS_ORDENES_COMPRAS.MODIFICATIVA           = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ      IN (" + procesos.getIdDetProcesoAdq() + ") "
                + "    AND RECEPCION_BIENES_SERVICIOS.ID_ESTADO_SEGUIMIENTO = 2 "
                + "    AND RECEPCION_BIENES_SERVICIOS.estado_eliminacion    = 0 "
                + "    GROUP BY DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      EMPRESA.RAZON_SOCIAL, "
                + "      EMPRESA.NUMERO_NIT "
                + "    ) TBL2 ON TBL1.CODIGO_DEPARTAMENTO = TBL2.CODIGO_DEPARTAMENTO "
                + "  AND TBL1.numero_nit                  = TBL2.numero_nit "
                + "  ) ";
        
        String where = "";
        if (codigoDepartamento != null && !codigoDepartamento.equals("00")) {
            where += " where codigo_departamento='" + codigoDepartamento + "'";
        }
        
        try {
            Query query = em.createNativeQuery(sql + where);
            List lst = query.getResultList();
            for (Object object : lst) {
                Object[] datos = (Object[]) object;
                ReporteProveedorDTO contrato = new ReporteProveedorDTO();
                contrato.setNOMBRE_DEPARTAMENTO(datos[1].toString());
                contrato.setRAZON_SOCIAL(datos[2].toString());
                contrato.setNIT(datos[3].toString());
                if (datos[4] == null) {
                    contrato.setTOTAL_UNIFORMES(0);
                } else {
                    contrato.setTOTAL_UNIFORMES(Integer.parseInt(datos[4].toString()));
                }
                
                if (datos[5] == null) {
                    contrato.setTOTAL_ENTREGADOS(0);
                } else {
                    contrato.setTOTAL_ENTREGADOS(Integer.parseInt(datos[5].toString()));
                }
                if (datos[6] == null) {
                    contrato.setAVANCE("0");
                } else {
                    Float num = Float.parseFloat(datos[6].toString());
                    String valor = String.format("%.2f", num);
                    contrato.setAVANCE(valor + "%");
                }
                
                lstContratos.add(contrato);
            }
        } finally {
            return lstContratos;
        }
    }
    
    private List<DetalleProcesoAdq> getDepProcesosAdq(ProcesoAdquisicion proceso) {
        Query q = em.createQuery("SELECT t FROM DetalleProcesoAdq t where t.idProcesoAdq.idProcesoAdq=:idProcesoAdq ORDER BY t.idRubroAdq.idRubroInteres", DetalleProcesoAdq.class);
        q.setParameter("idProcesoAdq", proceso.getIdProcesoAdq());
        return q.getResultList();
    }
    
    private DetalleProcesoAdq getDepProcesosAdqProveedor(ProcesoAdquisicion proceso, BigDecimal rubro) {
        Query q = em.createQuery("SELECT t FROM DetalleProcesoAdq t where t.idProcesoAdq.idProcesoAdq=:idProcesoAdq and t.idRubroAdq.idRubroInteres =:idRubroInteres", DetalleProcesoAdq.class);
        q.setParameter("idProcesoAdq", proceso.getIdProcesoAdq());
        q.setParameter("idRubroInteres", rubro);
        return (DetalleProcesoAdq) q.getSingleResult();
    }
    
    public List<VwSeguimientoRptCentroEscolar> getLstSeguimientoRptCE(Integer proceso, String codigoDepartamento) {
        List<VwSeguimientoRptCentroEscolar> lst = new ArrayList();
        List datos;
        Query q = em.createNativeQuery("SELECT \n"
                + "            VW_CANTIDAD_CONTRATADA.ID_CONTRATO,\n"
                + "            VW_CANTIDAD_CONTRATADA.CODIGO_DEPARTAMENTO,\n"
                + "            VW_CANTIDAD_CONTRATADA.CODIGO_MUNICIPIO,\n"
                + "            VW_CANTIDAD_CONTRATADA.CODIGO_ENTIDAD,\n"
                + "            VW_CANTIDAD_CONTRATADA.NOMBRE,\n"
                + "            VW_CANTIDAD_CONTRATADA.NOMBRE_DEPARTAMENTO,\n"
                + "            VW_CANTIDAD_CONTRATADA.NOMBRE_MUNICIPIO,\n"
                + "            VW_CANTIDAD_CONTRATADA.RUBRO,\n"
                + "            VW_CANTIDAD_CONTRATADA.NUMERO_NIT,\n"
                + "            VW_CANTIDAD_CONTRATADA.RAZON_SOCIAL,\n"
                + "            VW_CANTIDAD_CONTRATADA.MONTO_CONTRATADO,\n"
                + "            VW_CANTIDAD_CONTRATADA.CANTIDAD_CONTRATADA,\n"
                + "            VW_CANTIDAD_CONTRATADA.ID_DET_PROCESO_ADQ,\n"
                + "            VW_CANTIDAD_ENTREGADA.CANTIDAD_ENTREGADA,\n"
                + "            CASE VW_CANTIDAD_CONTRATADA.CANTIDAD_CONTRATADA when VW_CANTIDAD_ENTREGADA.CANTIDAD_ENTREGADA THEN 'COMPLETO' WHEN 0 THEN 'PENDIENTE' ELSE 'PARCIAL' end ESTADO_ENTREGA\n"
                + "            FROM VW_CANTIDAD_CONTRATADA\n"
                + "            INNER JOIN VW_CANTIDAD_ENTREGADA ON VW_CANTIDAD_ENTREGADA.ID_CONTRATO = VW_CANTIDAD_CONTRATADA.ID_CONTRATO\n"
                + "            WHERE VW_CANTIDAD_CONTRATADA.ID_DET_PROCESO_ADQ = ?1 AND\n"
                + "            VW_CANTIDAD_CONTRATADA.CODIGO_DEPARTAMENTO = ?2").
                setParameter(1, proceso).
                setParameter(2, codigoDepartamento);
        datos = q.getResultList();
        
        for (Object dato : datos) {
            Object[] objDato = (Object[]) dato;
            VwSeguimientoRptCentroEscolar vw = new VwSeguimientoRptCentroEscolar();
            vw.setIdContrato(new BigDecimal(objDato[0].toString()));
            vw.setCodigoDepartamento(objDato[1].toString());
            vw.setCodigoMunicipio(objDato[2].toString());
            vw.setCodigoEntidad(objDato[3].toString());
            vw.setNombre(objDato[4].toString());
            vw.setNombreDepartamento(objDato[5].toString());
            vw.setNombreMunicipio(objDato[6].toString());
            vw.setRubro(objDato[7].toString());
            vw.setNumeroNit(objDato[8].toString());
            vw.setRazonSocial(objDato[9].toString());
            vw.setMontoContratado(new BigDecimal(objDato[10].toString()));
            vw.setCantidadContratada(new BigDecimal(objDato[11].toString()));
            vw.setIdDetProcesoAdq(new Integer(objDato[12].toString()));
            vw.setCantidadEntregada(new BigDecimal(objDato[13].toString()));
            vw.setEstadoEntrega(objDato[14].toString());
            lst.add(vw);
        }
        return lst;
    }
    
    public List<ReportePorDepartamentoDto> getLstReporteGeneralDepartamento(Integer idDetProcesoUniformes, Integer idDetProcesoUtiles, Integer idDetProcesoZapatos) {
        Query query = em.createNamedQuery("Recepcion.RptGeneralDeEntregas", ReportePorDepartamentoDto.class);
        query.setParameter(1, idDetProcesoUniformes);
        query.setParameter(2, idDetProcesoUtiles);
        query.setParameter(3, idDetProcesoZapatos);
        return query.getResultList();
    }
    
    public List<RptEntregasGeneralPorDepartamentoDto> getLstRpteGeneral(Integer idDetProceso) {
        Query query = em.createNamedQuery("Recepcion.RptGeneral", RptEntregasGeneralPorDepartamentoDto.class);
        query.setParameter(1, idDetProceso);
        return query.getResultList();
    }
    
    public List<RptEntregasGeneralPorDepartamentoDto> getLstRpteGeneralDepartamento(Integer idDetProceso, String codigoDepartamento) {
        Query query = em.createNamedQuery("Recepcion.RptGeneralPorDepartamento", RptEntregasGeneralPorDepartamentoDto.class);
        query.setParameter(1, idDetProceso);
        query.setParameter(2, codigoDepartamento);
        return query.getResultList();
    }
    
    public List<ReporteGeneralDTO> getLstReporteGeneral(ProcesoAdquisicion proceso, String codigoDepartamento) {
        List<DetalleProcesoAdq> procesos = getDepProcesosAdq(proceso);
        List<ReporteGeneralDTO> lstContratos = new ArrayList<>();
        String sql = "select * from(SELECT TB.CODIGO_ENTIDAD, "
                + "  TB.NOMBRE, "
                + "  TB.TOTAL_UNIFORME, "
                + "  TB.ENTREGADO_UNIFORME , "
                + "  TB.TOTAL_UTILES, "
                + "  TB.ENTREGADO_UTILES, "
                + "  TB.TOTAL_ZAPATOS, "
                + "  TB.ENTREGADO_ZAPATOS, "
                + "  TB.CODIGO_DEPARTAMENTO, "
                + "nvl(TB.TOTAL_UNIFORME,0) + nvl(TB.TOTAL_UTILES,0) + nvl(TB.TOTAL_ZAPATOS,0) totalContratos,  "
                + "nvl(TB.ENTREGADO_UNIFORME,0) + nvl(TB.ENTREGADO_UTILES,0) +  nvl(TB.ENTREGADO_ZAPATOS,0) totalEntregado,  "
                + "(nvl(TB.ENTREGADO_UNIFORME,0) + nvl(TB.ENTREGADO_UTILES,0) +  nvl(TB.ENTREGADO_ZAPATOS,0))/(nvl(TB.TOTAL_UNIFORME,0) + nvl(TB.TOTAL_UTILES,0) + nvl(TB.TOTAL_ZAPATOS,0))*100 porcentajeAvan "
                + "FROM "
                + "  (SELECT TBL.*, "
                + "    TBL1.TOTAL_UNIFORME, "
                + "    TBL2.ENTREGADO_UNIFORME , "
                + "    TBL3.TOTAL_UTILES, "
                + "    TBL4.ENTREGADO_UTILES, "
                + "    TBL5.TOTAL_ZAPATOS, "
                + "    TBL6.ENTREGADO_ZAPATOS "
                + "  FROM "
                + "    (SELECT DISTINCT DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE "
                + "    FROM CONTRATOS_ORDENES_COMPRAS "
                + "    INNER JOIN RESOLUCIONES_ADJUDICATIVAS "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_RESOLUCION_ADJ = CONTRATOS_ORDENES_COMPRAS.ID_RESOLUCION_ADJ "
                + "    INNER JOIN ESTADO_RESERVA "
                + "    ON ESTADO_RESERVA.ID_ESTADO_RESERVA = RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA "
                + "    INNER JOIN PARTICIPANTES "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_PARTICIPANTE = PARTICIPANTES.ID_PARTICIPANTE "
                + "    INNER JOIN DETALLE_OFERTAS "
                + "    ON PARTICIPANTES.ID_PARTICIPANTE = DETALLE_OFERTAS.ID_PARTICIPANTE "
                + "    INNER JOIN EMPRESA "
                + "    ON PARTICIPANTES.ID_EMPRESA = EMPRESA.ID_EMPRESA "
                + "    INNER JOIN OFERTA_BIENES_SERVICIOS "
                + "    ON PARTICIPANTES.ID_OFERTA = OFERTA_BIENES_SERVICIOS.ID_OFERTA "
                + "    INNER JOIN DETALLE_PROCESO_ADQ "
                + "    ON OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ = DETALLE_PROCESO_ADQ.ID_DET_PROCESO_ADQ "
                + "    INNER JOIN VW_CATALOGO_ENTIDAD_EDUCATIVA "
                + "    ON OFERTA_BIENES_SERVICIOS.CODIGO_ENTIDAD = VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD "
                + "    INNER JOIN DEPARTAMENTO "
                + "    ON VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_DEPARTAMENTO = DEPARTAMENTO.CODIGO_DEPARTAMENTO "
                + "    WHERE CONTRATOS_ORDENES_COMPRAS.ESTADO_ELIMINACION   = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ESTADO_ELIMINACION       = 0 "
                + "    AND PARTICIPANTES.ESTADO_ELIMINACION                 = 0 "
                + "    AND DETALLE_OFERTAS.ESTADO_ELIMINACION               = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ESTADO_ELIMINACION    = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA    IN (2, 4, 5) "
                + "    AND CONTRATOS_ORDENES_COMPRAS.MODIFICATIVA           = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ      IN (" + procesos.get(0).getIdDetProcesoAdq() + "," + procesos.get(1).getIdDetProcesoAdq() + "," + procesos.get(2).getIdDetProcesoAdq() + ")"
                + "    GROUP BY DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE "
                + "    ) TBL "
                + "  LEFT OUTER JOIN "
                + "    (SELECT DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE, "
                + "      COUNT(DISTINCT CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO) total_uniforme "
                + "    FROM CONTRATOS_ORDENES_COMPRAS "
                + "    INNER JOIN RESOLUCIONES_ADJUDICATIVAS "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_RESOLUCION_ADJ = CONTRATOS_ORDENES_COMPRAS.ID_RESOLUCION_ADJ "
                + "    INNER JOIN ESTADO_RESERVA "
                + "    ON ESTADO_RESERVA.ID_ESTADO_RESERVA = RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA "
                + "    INNER JOIN PARTICIPANTES "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_PARTICIPANTE = PARTICIPANTES.ID_PARTICIPANTE "
                + "    INNER JOIN DETALLE_OFERTAS "
                + "    ON PARTICIPANTES.ID_PARTICIPANTE = DETALLE_OFERTAS.ID_PARTICIPANTE "
                + "    INNER JOIN EMPRESA "
                + "    ON PARTICIPANTES.ID_EMPRESA = EMPRESA.ID_EMPRESA "
                + "    INNER JOIN OFERTA_BIENES_SERVICIOS "
                + "    ON PARTICIPANTES.ID_OFERTA = OFERTA_BIENES_SERVICIOS.ID_OFERTA "
                + "    INNER JOIN DETALLE_PROCESO_ADQ "
                + "    ON OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ = DETALLE_PROCESO_ADQ.ID_DET_PROCESO_ADQ "
                + "    INNER JOIN VW_CATALOGO_ENTIDAD_EDUCATIVA "
                + "    ON OFERTA_BIENES_SERVICIOS.CODIGO_ENTIDAD = VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD "
                + "    INNER JOIN DEPARTAMENTO "
                + "    ON VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_DEPARTAMENTO = DEPARTAMENTO.CODIGO_DEPARTAMENTO "
                + "    WHERE CONTRATOS_ORDENES_COMPRAS.ESTADO_ELIMINACION   = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ESTADO_ELIMINACION       = 0 "
                + "    AND PARTICIPANTES.ESTADO_ELIMINACION                 = 0 "
                + "    AND DETALLE_OFERTAS.ESTADO_ELIMINACION               = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ESTADO_ELIMINACION    = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA    IN (2, 4, 5) "
                + "    AND CONTRATOS_ORDENES_COMPRAS.MODIFICATIVA           = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ      IN (" + procesos.get(0).getIdDetProcesoAdq() + ") "
                + "    GROUP BY DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE "
                + "    ) TBL1 ON TBL.CODIGO_DEPARTAMENTO = TBL1.CODIGO_DEPARTAMENTO "
                + "  AND TBL.CODIGO_ENTIDAD              = TBL1.CODIGO_ENTIDAD "
                + "  LEFT OUTER JOIN "
                + "    (SELECT DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE, "
                + "      COUNT(DISTINCT CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO) entregado_uniforme "
                + "    FROM RECEPCION_BIENES_SERVICIOS "
                + "    INNER JOIN CONTRATOS_ORDENES_COMPRAS "
                + "    ON RECEPCION_BIENES_SERVICIOS.ID_CONTRATO = CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO "
                + "    INNER JOIN RESOLUCIONES_ADJUDICATIVAS "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_RESOLUCION_ADJ = CONTRATOS_ORDENES_COMPRAS.ID_RESOLUCION_ADJ "
                + "    INNER JOIN ESTADO_RESERVA "
                + "    ON ESTADO_RESERVA.ID_ESTADO_RESERVA = RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA "
                + "    INNER JOIN PARTICIPANTES "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_PARTICIPANTE = PARTICIPANTES.ID_PARTICIPANTE "
                + "    INNER JOIN DETALLE_OFERTAS "
                + "    ON PARTICIPANTES.ID_PARTICIPANTE = DETALLE_OFERTAS.ID_PARTICIPANTE "
                + "    INNER JOIN EMPRESA "
                + "    ON PARTICIPANTES.ID_EMPRESA = EMPRESA.ID_EMPRESA "
                + "    INNER JOIN OFERTA_BIENES_SERVICIOS "
                + "    ON PARTICIPANTES.ID_OFERTA = OFERTA_BIENES_SERVICIOS.ID_OFERTA "
                + "    INNER JOIN DETALLE_PROCESO_ADQ "
                + "    ON OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ = DETALLE_PROCESO_ADQ.ID_DET_PROCESO_ADQ "
                + "    INNER JOIN VW_CATALOGO_ENTIDAD_EDUCATIVA "
                + "    ON OFERTA_BIENES_SERVICIOS.CODIGO_ENTIDAD = VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD "
                + "    INNER JOIN DEPARTAMENTO "
                + "    ON VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_DEPARTAMENTO = DEPARTAMENTO.CODIGO_DEPARTAMENTO "
                + "    WHERE CONTRATOS_ORDENES_COMPRAS.ESTADO_ELIMINACION   = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ESTADO_ELIMINACION       = 0 "
                + "    AND PARTICIPANTES.ESTADO_ELIMINACION                 = 0 "
                + "    AND DETALLE_OFERTAS.ESTADO_ELIMINACION               = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ESTADO_ELIMINACION    = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA    IN (2, 4, 5) "
                + "    AND CONTRATOS_ORDENES_COMPRAS.MODIFICATIVA           = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ      IN (" + procesos.get(0).getIdDetProcesoAdq() + ") "
                + "    AND RECEPCION_BIENES_SERVICIOS.ID_ESTADO_SEGUIMIENTO = 2 "
                + "    AND RECEPCION_BIENES_SERVICIOS.estado_eliminacion    = 0 "
                + "    GROUP BY DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE "
                + "    ) TBL2 ON TBL1.CODIGO_DEPARTAMENTO = TBL2.CODIGO_DEPARTAMENTO "
                + "  AND TBL1.CODIGO_ENTIDAD              = TBL2.CODIGO_ENTIDAD "
                + "  LEFT OUTER JOIN "
                + "    (SELECT DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE, "
                + "      COUNT(DISTINCT CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO) total_utiles "
                + "    FROM CONTRATOS_ORDENES_COMPRAS "
                + "    INNER JOIN RESOLUCIONES_ADJUDICATIVAS "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_RESOLUCION_ADJ = CONTRATOS_ORDENES_COMPRAS.ID_RESOLUCION_ADJ "
                + "    INNER JOIN ESTADO_RESERVA "
                + "    ON ESTADO_RESERVA.ID_ESTADO_RESERVA = RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA "
                + "    INNER JOIN PARTICIPANTES "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_PARTICIPANTE = PARTICIPANTES.ID_PARTICIPANTE "
                + "    INNER JOIN DETALLE_OFERTAS "
                + "    ON PARTICIPANTES.ID_PARTICIPANTE = DETALLE_OFERTAS.ID_PARTICIPANTE "
                + "    INNER JOIN EMPRESA "
                + "    ON PARTICIPANTES.ID_EMPRESA = EMPRESA.ID_EMPRESA "
                + "    INNER JOIN OFERTA_BIENES_SERVICIOS "
                + "    ON PARTICIPANTES.ID_OFERTA = OFERTA_BIENES_SERVICIOS.ID_OFERTA "
                + "    INNER JOIN DETALLE_PROCESO_ADQ "
                + "    ON OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ = DETALLE_PROCESO_ADQ.ID_DET_PROCESO_ADQ "
                + "    INNER JOIN VW_CATALOGO_ENTIDAD_EDUCATIVA "
                + "    ON OFERTA_BIENES_SERVICIOS.CODIGO_ENTIDAD = VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD "
                + "    INNER JOIN DEPARTAMENTO "
                + "    ON VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_DEPARTAMENTO = DEPARTAMENTO.CODIGO_DEPARTAMENTO "
                + "    WHERE CONTRATOS_ORDENES_COMPRAS.ESTADO_ELIMINACION   = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ESTADO_ELIMINACION       = 0 "
                + "    AND PARTICIPANTES.ESTADO_ELIMINACION                 = 0 "
                + "    AND DETALLE_OFERTAS.ESTADO_ELIMINACION               = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ESTADO_ELIMINACION    = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA    IN (2, 4, 5) "
                + "    AND CONTRATOS_ORDENES_COMPRAS.MODIFICATIVA           = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ      IN (" + procesos.get(1).getIdDetProcesoAdq() + ") "
                + "    GROUP BY DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE "
                + "    ) TBL3 ON TBL.CODIGO_DEPARTAMENTO = TBL3.CODIGO_DEPARTAMENTO "
                + "  AND TBL.CODIGO_ENTIDAD              = TBL3.CODIGO_ENTIDAD "
                + "  LEFT OUTER JOIN "
                + "    (SELECT DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE, "
                + "      COUNT(DISTINCT CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO) entregado_utiles "
                + "    FROM RECEPCION_BIENES_SERVICIOS "
                + "    INNER JOIN CONTRATOS_ORDENES_COMPRAS "
                + "    ON RECEPCION_BIENES_SERVICIOS.ID_CONTRATO = CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO "
                + "    INNER JOIN RESOLUCIONES_ADJUDICATIVAS "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_RESOLUCION_ADJ = CONTRATOS_ORDENES_COMPRAS.ID_RESOLUCION_ADJ "
                + "    INNER JOIN ESTADO_RESERVA "
                + "    ON ESTADO_RESERVA.ID_ESTADO_RESERVA = RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA "
                + "    INNER JOIN PARTICIPANTES "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_PARTICIPANTE = PARTICIPANTES.ID_PARTICIPANTE "
                + "    INNER JOIN DETALLE_OFERTAS "
                + "    ON PARTICIPANTES.ID_PARTICIPANTE = DETALLE_OFERTAS.ID_PARTICIPANTE "
                + "    INNER JOIN EMPRESA "
                + "    ON PARTICIPANTES.ID_EMPRESA = EMPRESA.ID_EMPRESA "
                + "    INNER JOIN OFERTA_BIENES_SERVICIOS "
                + "    ON PARTICIPANTES.ID_OFERTA = OFERTA_BIENES_SERVICIOS.ID_OFERTA "
                + "    INNER JOIN DETALLE_PROCESO_ADQ "
                + "    ON OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ = DETALLE_PROCESO_ADQ.ID_DET_PROCESO_ADQ "
                + "    INNER JOIN VW_CATALOGO_ENTIDAD_EDUCATIVA "
                + "    ON OFERTA_BIENES_SERVICIOS.CODIGO_ENTIDAD = VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD "
                + "    INNER JOIN DEPARTAMENTO "
                + "    ON VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_DEPARTAMENTO = DEPARTAMENTO.CODIGO_DEPARTAMENTO "
                + "    WHERE CONTRATOS_ORDENES_COMPRAS.ESTADO_ELIMINACION   = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ESTADO_ELIMINACION       = 0 "
                + "    AND PARTICIPANTES.ESTADO_ELIMINACION                 = 0 "
                + "    AND DETALLE_OFERTAS.ESTADO_ELIMINACION               = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ESTADO_ELIMINACION    = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA    IN (2, 4, 5) "
                + "    AND CONTRATOS_ORDENES_COMPRAS.MODIFICATIVA           = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ      IN (" + procesos.get(1).getIdDetProcesoAdq() + ") "
                + "    AND RECEPCION_BIENES_SERVICIOS.ID_ESTADO_SEGUIMIENTO = 2 "
                + "    AND RECEPCION_BIENES_SERVICIOS.estado_eliminacion    = 0 "
                + "    GROUP BY DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE "
                + "    ) TBL4 ON TBL3.CODIGO_DEPARTAMENTO = TBL4.CODIGO_DEPARTAMENTO "
                + "  AND TBL3.CODIGO_ENTIDAD              = TBL4.CODIGO_ENTIDAD "
                + "  LEFT OUTER JOIN "
                + "    (SELECT DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE, "
                + "      COUNT(DISTINCT CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO) total_zapatos "
                + "    FROM CONTRATOS_ORDENES_COMPRAS "
                + "    INNER JOIN RESOLUCIONES_ADJUDICATIVAS "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_RESOLUCION_ADJ = CONTRATOS_ORDENES_COMPRAS.ID_RESOLUCION_ADJ "
                + "    INNER JOIN ESTADO_RESERVA "
                + "    ON ESTADO_RESERVA.ID_ESTADO_RESERVA = RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA "
                + "    INNER JOIN PARTICIPANTES "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_PARTICIPANTE = PARTICIPANTES.ID_PARTICIPANTE "
                + "    INNER JOIN DETALLE_OFERTAS "
                + "    ON PARTICIPANTES.ID_PARTICIPANTE = DETALLE_OFERTAS.ID_PARTICIPANTE "
                + "    INNER JOIN EMPRESA "
                + "    ON PARTICIPANTES.ID_EMPRESA = EMPRESA.ID_EMPRESA "
                + "    INNER JOIN OFERTA_BIENES_SERVICIOS "
                + "    ON PARTICIPANTES.ID_OFERTA = OFERTA_BIENES_SERVICIOS.ID_OFERTA "
                + "    INNER JOIN DETALLE_PROCESO_ADQ "
                + "    ON OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ = DETALLE_PROCESO_ADQ.ID_DET_PROCESO_ADQ "
                + "    INNER JOIN VW_CATALOGO_ENTIDAD_EDUCATIVA "
                + "    ON OFERTA_BIENES_SERVICIOS.CODIGO_ENTIDAD = VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD "
                + "    INNER JOIN DEPARTAMENTO "
                + "    ON VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_DEPARTAMENTO = DEPARTAMENTO.CODIGO_DEPARTAMENTO "
                + "    WHERE CONTRATOS_ORDENES_COMPRAS.ESTADO_ELIMINACION   = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ESTADO_ELIMINACION       = 0 "
                + "    AND PARTICIPANTES.ESTADO_ELIMINACION                 = 0 "
                + "    AND DETALLE_OFERTAS.ESTADO_ELIMINACION               = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ESTADO_ELIMINACION    = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA    IN (2, 4, 5) "
                + "    AND CONTRATOS_ORDENES_COMPRAS.MODIFICATIVA           = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ      IN (" + procesos.get(2).getIdDetProcesoAdq() + ") "
                + "    GROUP BY DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE "
                + "    ) TBL5 ON TBL.CODIGO_DEPARTAMENTO = TBL5.CODIGO_DEPARTAMENTO "
                + "  AND TBL.CODIGO_ENTIDAD              = TBL5.CODIGO_ENTIDAD "
                + "  LEFT OUTER JOIN "
                + "    (SELECT DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE, "
                + "      COUNT(DISTINCT CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO) entregado_zapatos "
                + "    FROM RECEPCION_BIENES_SERVICIOS "
                + "    INNER JOIN CONTRATOS_ORDENES_COMPRAS "
                + "    ON RECEPCION_BIENES_SERVICIOS.ID_CONTRATO = CONTRATOS_ORDENES_COMPRAS.ID_CONTRATO "
                + "    INNER JOIN RESOLUCIONES_ADJUDICATIVAS "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_RESOLUCION_ADJ = CONTRATOS_ORDENES_COMPRAS.ID_RESOLUCION_ADJ "
                + "    INNER JOIN ESTADO_RESERVA "
                + "    ON ESTADO_RESERVA.ID_ESTADO_RESERVA = RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA "
                + "    INNER JOIN PARTICIPANTES "
                + "    ON RESOLUCIONES_ADJUDICATIVAS.ID_PARTICIPANTE = PARTICIPANTES.ID_PARTICIPANTE "
                + "    INNER JOIN DETALLE_OFERTAS "
                + "    ON PARTICIPANTES.ID_PARTICIPANTE = DETALLE_OFERTAS.ID_PARTICIPANTE "
                + "    INNER JOIN EMPRESA "
                + "    ON PARTICIPANTES.ID_EMPRESA = EMPRESA.ID_EMPRESA "
                + "    INNER JOIN OFERTA_BIENES_SERVICIOS "
                + "    ON PARTICIPANTES.ID_OFERTA = OFERTA_BIENES_SERVICIOS.ID_OFERTA "
                + "    INNER JOIN DETALLE_PROCESO_ADQ "
                + "    ON OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ = DETALLE_PROCESO_ADQ.ID_DET_PROCESO_ADQ "
                + "    INNER JOIN VW_CATALOGO_ENTIDAD_EDUCATIVA "
                + "    ON OFERTA_BIENES_SERVICIOS.CODIGO_ENTIDAD = VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD "
                + "    INNER JOIN DEPARTAMENTO "
                + "    ON VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_DEPARTAMENTO = DEPARTAMENTO.CODIGO_DEPARTAMENTO "
                + "    WHERE CONTRATOS_ORDENES_COMPRAS.ESTADO_ELIMINACION   = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ESTADO_ELIMINACION       = 0 "
                + "    AND PARTICIPANTES.ESTADO_ELIMINACION                 = 0 "
                + "    AND DETALLE_OFERTAS.ESTADO_ELIMINACION               = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ESTADO_ELIMINACION    = 0 "
                + "    AND RESOLUCIONES_ADJUDICATIVAS.ID_ESTADO_RESERVA    IN (2, 4, 5) "
                + "    AND CONTRATOS_ORDENES_COMPRAS.MODIFICATIVA           = 0 "
                + "    AND OFERTA_BIENES_SERVICIOS.ID_DET_PROCESO_ADQ      IN (" + procesos.get(2).getIdDetProcesoAdq() + ") "
                + "    AND RECEPCION_BIENES_SERVICIOS.ID_ESTADO_SEGUIMIENTO = 2 "
                + "    AND RECEPCION_BIENES_SERVICIOS.estado_eliminacion    = 0 "
                + "    GROUP BY DEPARTAMENTO.CODIGO_DEPARTAMENTO, "
                + "      DEPARTAMENTO.NOMBRE_DEPARTAMENTO, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.CODIGO_ENTIDAD, "
                + "      VW_CATALOGO_ENTIDAD_EDUCATIVA.NOMBRE "
                + "    ) TBL6 ON TBL5.CODIGO_DEPARTAMENTO = TBL6.CODIGO_DEPARTAMENTO "
                + "  AND TBL5.CODIGO_ENTIDAD              = TBL6.CODIGO_ENTIDAD "
                + "  ) tb)";
        
        String where = "";
        if (codigoDepartamento != null && !codigoDepartamento.equals("00")) {
            where += " where CODIGO_DEPARTAMENTO='" + codigoDepartamento + "'";
        }
        
        try {
            Query query = em.createNativeQuery(sql + where);
            List lst = query.getResultList();
            for (Object object : lst) {
                Object[] datos = (Object[]) object;
                ReporteGeneralDTO contrato = new ReporteGeneralDTO();
                contrato.setCODIGO_ENTIDAD(datos[0].toString());
                contrato.setNOMBRE(datos[1].toString());
                if (datos[2] == null) {
                    contrato.setTOTAL_UNIFORME(0);
                } else {
                    contrato.setTOTAL_UNIFORME(Integer.parseInt(datos[2].toString()));
                }
                
                if (datos[3] == null) {
                    contrato.setENTREGADO_UNIFORME(0);
                } else {
                    contrato.setENTREGADO_UNIFORME(Integer.parseInt(datos[3].toString()));
                }
                if (datos[4] == null) {
                    contrato.setTOTAL_UTILES(0);
                } else {
                    contrato.setTOTAL_UTILES(Integer.parseInt(datos[4].toString()));
                }
                if (datos[5] == null) {
                    contrato.setENTREGADO_UTILES(0);
                } else {
                    contrato.setENTREGADO_UTILES(Integer.parseInt(datos[5].toString()));
                }
                if (datos[6] == null) {
                    contrato.setTOTAL_ZAPATOS(0);
                } else {
                    contrato.setTOTAL_ZAPATOS(Integer.parseInt(datos[6].toString()));
                }
                if (datos[7] == null) {
                    contrato.setENTREGADO_ZAPATOS(0);
                } else {
                    contrato.setENTREGADO_ZAPATOS(Integer.parseInt(datos[7].toString()));
                }
                contrato.setCODIGO_DEPARTAMENTO(datos[8].toString());
                if (datos[9] == null) {
                    contrato.setTOTALCONTRATADO(0);
                } else {
                    contrato.setTOTALCONTRATADO(Integer.parseInt(datos[9].toString()));
                }
                if (datos[10] == null) {
                    contrato.setTOTALENTREGADO(0);
                } else {
                    contrato.setTOTALENTREGADO(Integer.parseInt(datos[10].toString()));
                }
                if (datos[11] == null) {
                    contrato.setPORCENTAJEAVAN("0");
                } else {
                    Float num = Float.parseFloat(datos[11].toString());
                    String valor = String.format("%.2f", num);
                    contrato.setPORCENTAJEAVAN(valor + "%");
                }
                
                lstContratos.add(contrato);
            }
        } finally {
            return lstContratos;
        }
    }
    
    public DetalleRecepcion getDetalleRecepcionById(BigDecimal idDetalleRecepcion) {
        DetalleRecepcion detalle = new DetalleRecepcion();
        Query q = em.createQuery("SELECT d FROM DetalleRecepcion d WHERE d.idDetalleRecepcion=:idDetalleRecepcion", DetalleRecepcion.class);
        q.setParameter("idDetalleRecepcion", idDetalleRecepcion);
        if (!q.getResultList().isEmpty()) {
            detalle = (DetalleRecepcion) q.getResultList().get(0);
        }
        
        return detalle;
    }
    
    public List<DetalleRecepcion> getLstDetalleRecepcionByFk(BigDecimal idRecepcion) {
        Query q = em.createQuery("SELECT d FROM DetalleRecepcion d WHERE d.idRecepcion.idRecepcion=:idRecep and d.estadoEliminacion=0 ORDER BY d.fechaRecepcion, FUNC('TO_NUMBER',d.noItem) ASC", DetalleRecepcion.class);
        q.setParameter("idRecep", idRecepcion);
        return q.getResultList();
    }
    
    public RecepcionBienesServicios getRecepcion(BigDecimal idcontrato) {
        Query q = em.createQuery("SELECT r FROM RecepcionBienesServicios r WHERE r.idContrato.idContrato=:idContrato and r.estadoEliminacion=0", RecepcionBienesServicios.class);
        q.setParameter("idContrato", idcontrato);
        if (!q.getResultList().isEmpty()) {
            return (RecepcionBienesServicios) q.getResultList().get(0);
        } else {
            RecepcionBienesServicios recepcion = new RecepcionBienesServicios();
            recepcion.setEstadoEliminacion(BigInteger.ZERO);
            recepcion.setFechaInsercion(new Date());
            recepcion.setIdEstadoSeguimiento(em.find(EstadoSeguimiento.class, 1));
            recepcion.setIdContrato(em.find(ContratosOrdenesCompras.class, idcontrato));
            return recepcion;
        }
    }
    
    public boolean ifExisteRecepcion(BigDecimal idcontrato) {
        return (getRecepcion(idcontrato).getIdRecepcion() != null);
    }
    
    public List<VwBusquedaSeguimientos> getLstBusquedaSeguimientos(Integer idDetProcesoAdq, String codigoEntidad, String codigoDepartamento, String numeroNit, String numeroContrato, String nombreProveedor) {
        List<VwBusquedaSeguimientos> lstSeguimientos = new ArrayList();
        String where = "";
        if (idDetProcesoAdq != null) {
            where += " ID_DET_PROCESO_ADQ=" + idDetProcesoAdq;
        }
        if (codigoEntidad != null && !codigoEntidad.isEmpty()) {
            where += (where.isEmpty() ? "" : " AND ").concat(" codigo_entidad='" + codigoEntidad + "'");
        }
        if (codigoDepartamento != null && !codigoDepartamento.equals("00")) {
            where += (where.isEmpty() ? "" : " AND ").concat(" codigo_departamento='" + codigoDepartamento + "'");
        }
        if (numeroNit != null && !numeroNit.isEmpty()) {
            where += (where.isEmpty() ? "" : " AND ").concat(" numero_nit='" + numeroNit + "'");
        }
        if (nombreProveedor != null && !nombreProveedor.isEmpty()) {
            nombreProveedor = nombreProveedor.toUpperCase();
            where += (where.isEmpty() ? "" : " AND ").concat(" razon_social like'%" + nombreProveedor + "%'");
        }
        
        if (numeroContrato != null && !numeroContrato.isEmpty()) {
            where += (where.isEmpty() ? "" : " AND ").concat(" numero_contrato='" + numeroContrato + "'");
        }
        Query query = em.createNativeQuery("SELECT * FROM vw_busqueda_seguimientos WHERE " + where + " union SELECT * FROM vw_busqueda_seguimientos_modif WHERE " + where);
        List lst = query.getResultList();
        for (Object object : lst) {
            Object[] datos = (Object[]) object;
            VwBusquedaSeguimientos contrato = new VwBusquedaSeguimientos();
            contrato.setCodigoDepartamento(datos[0].toString());
            contrato.setNombreDepartamento(datos[1].toString());
            contrato.setCodigoEntidad(datos[2].toString());
            contrato.setNombreCe(datos[3].toString());
            contrato.setCantidad(new BigDecimal(datos[4].toString()));
            contrato.setMonto(new BigDecimal(datos[5].toString()));
            contrato.setIdContrato(new BigDecimal(datos[6].toString()));
            contrato.setNumeroContrato(datos[7].toString());
            contrato.setRazonSocial(datos[8].toString());
            contrato.setNumeroNit(datos[9].toString());
            contrato.setEstado(datos[12].toString());
            contrato.setEstadoEliminacion(false);
            contrato.setIdRecepcion(new BigDecimal(datos[13].toString()));
            
            lstSeguimientos.add(contrato);
        }
        return lstSeguimientos;
    }
    
    public void guardarRecepcion(RecepcionBienesServicios recepcion) {
        if (recepcion.getIdRecepcion() == null) {
            em.persist(recepcion);
        } else {
            em.merge(recepcion);
        }
    }
    
    public void guardarDetalleRecepcion(DetalleRecepcion detalle, Boolean cambioEstado) {
        if (cambioEstado) {
            Query q = em.createNativeQuery("update recepcion_bienes_servicios set id_estado_seguimiento=2 where id_recepcion=" + detalle.getIdRecepcion().getIdRecepcion());
            q.executeUpdate();
        } else {
            Query q = em.createNativeQuery("update recepcion_bienes_servicios set id_estado_seguimiento=1 where id_recepcion=" + detalle.getIdRecepcion().getIdRecepcion());
            q.executeUpdate();
        }
        if (detalle.getIdDetalleRecepcion() == null) {
            em.persist(detalle);
        } else {
            em.merge(detalle);
        }
    }
    
    public void updateDetalleRecepcion(DetalleRecepcion detalle) {
        Query q = em.createQuery("UPDATE DetalleRecepcion det SET DET.observaciones=:observaciones,DET.cantidadEntregada=:cantidadEntregada WHERE DET.idDetalleRecepcion=:idDetalleRecepcion", DetalleRecepcion.class);
        q.setParameter("observaciones", detalle.getObservaciones());
        q.setParameter("cantidadEntregada", detalle.getCantidadEntregada());
        q.setParameter("idDetalleRecepcion", detalle.getIdDetalleRecepcion());
        int valor = q.executeUpdate();
    }
    
    public List<DetalleItems> getLstItemsPendienteEntrega(BigDecimal idContrato) {
        Query q = em.createNamedQuery("Recepcion.ItemsPendienteDeEntrega", DetalleItems.class);
        q.setParameter(1, idContrato);
        return q.getResultList();
    }
    
    public List<DetalleItems> getLstItemsPendienteEntrega(BigDecimal idContrato, String noItem) {
        Query q = em.createNamedQuery("Recepcion.ItemsPendienteDeEntregaByNoItem", DetalleItems.class);
        q.setParameter(1, idContrato);
        q.setParameter(2, noItem);
        return q.getResultList();
    }
    
    public void eliminarRecepcion(BigDecimal idRecepcion, String usuario) {
        Query q = em.createQuery("UPDATE RecepcionBienesServicios r SET r.estadoEliminacion=1, r.fechaEliminacion=:fecha, r.usuarioModificacion=:usu WHERE r.idRecepcion=:idRecep");
        q.setParameter("fecha", new Date());
        q.setParameter("usu", usuario);
        q.setParameter("idRecep", idRecepcion);
        q.executeUpdate();
    }
    
    public List<DetalleOfertas> getItemDeContratoVigente(BigDecimal idContrato) {
        List<DetalleOfertas> lstDetalleOferta = new ArrayList<>();
        ContratosOrdenesCompras con = em.find(ContratosOrdenesCompras.class, idContrato);
        if (con.getModificativa() == 1) {
            Query q = em.createNativeQuery("SELECT * FROM resoluciones_modificativas WHERE id_contrato = ?1 and id_estado_reserva = 2 and estado_eliminacion = 0", ResolucionesModificativas.class);
            q.setParameter(1, idContrato);
            ResolucionesModificativas res = (ResolucionesModificativas) q.getSingleResult();
            for (DetalleModificativa detModif : res.getDetalleModificativaList()) {
                DetalleOfertas det = new DetalleOfertas();
                
                det.setIdDetalleOfe(new BigDecimal(detModif.getIdDetalleModif()));
                det.setCantidad(new BigInteger(detModif.getCantidadNew().toString()));
                det.setConsolidadoEspTec(detModif.getConsolidadoEspTec());
                det.setNoItem(detModif.getNoItem());
                
                lstDetalleOferta.add(det);
            }
            return lstDetalleOferta;
        } else {
            return con.getIdResolucionAdj().getIdParticipante().getDetalleOfertasList();
        }
    }
    
    public Boolean existeModificativaByIdContrato(BigDecimal idContrato) {
        Query q = em.createQuery("SELECT r FROM ResolucionesModificativas r WHERE r.estadoEliminacion = 0 and r.idEstadoReserva = 1 and r.idResModifPadre is null and r.idContrato.idContrato=:idContrato", ResolucionesModificativas.class);
        q.setParameter("idContrato", idContrato);
        return q.getResultList().size() > 0;
    }
    
    public RecepcionBienesServicios findRecepcionByIdContrato(BigDecimal idContrato) {
        Query q = em.createQuery("SELECT r FROM RecepcionBienesServicios r WHERE r.idContrato.idContrato=:idContrato and r.estadoEliminacion = 0", RecepcionBienesServicios.class);
        q.setParameter("idContrato", idContrato);
        
        return q.getResultList().isEmpty() ? null : (RecepcionBienesServicios) q.getResultList().get(0);
    }
}
