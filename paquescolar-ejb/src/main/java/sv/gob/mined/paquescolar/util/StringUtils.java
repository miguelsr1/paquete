/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.util;

/**
 *
 * @author misanchez
 */
public class StringUtils {

    public static final String QUERY_RPT_PROVEEDOR_ENT_FINAN = "SELECT "
            + "    rownum              as idRow, "
            + "    ID_DETALLE          as idDetalle, "
            + "    NUMERO_NIT          as numeroNit,"
            + "    RAZON_SOCIAL        as razonSocial,"
            + "    NOMBRE_ENT_FINAN    as nombreEntFinan,"
            + "    COD_ENT_FINANCIERA  as codEntFinanciera,"
            + "    NOMBRE_DEPARTAMENTO as nombreDepartamento,"
            + "    CODIGO_DEPARTAMENTO as codigoDepartamento,"
            + "    CODIGO_ENTIDAD      as codigoEntidad,"
            + "    nombre              as nombre,"
            + "    MONTO_CREDITO       as montoCredito,"
            + "    CREDITO_ACTIVO      as creditoActivo,"
            + "    DESCRIPCION_RUBRO   as descripcionRubro,"
            + "    ID_RUBRO_INTERES    as idRubroInteres,"
            + "    ID_DET_PROCESO_ADQ  as idProceso,"
            + "    MONTO_CONTRATO      as montoContrato,"
            + "    DESCRIP_CRED_ACT    as descripCredAct"
            + " FROM vw_datos_proveedores_finan ";

    public static final String QUERY_RECEPCION_FIND_CONTRATOS_FISICOS = "select "
            + "    CODIGO_DEPARTAMENTO as codigoDepartamento,"
            + "    NOMBRE_DEPARTAMENTO as nombreDepartamento,"
            + "    CODIGO_ENTIDAD      as codigoEntidad,"
            + "    NOMBRE              as nombreCe,"
            + "    cantidad            as cantidad,"
            + "    monto               as monto,"
            + "    id_contrato         as idContrato,"
            + "    NUMERO_CONTRATO     as numeroContrato,"
            + "    razon_social        as razonSocial,"
            + "    numero_nit          as numeroNit,"
            + "    anho                as anho,"
            + "    ID_RUBRO_ADQ        as idRubroAdq"
            + " from vw_busqueda_contratos";

    public static final String QUERY_PAGOS_BUSQUEDA_PLANILLA = "select \n"
            + "     rownum                   as idRow, "
            + "     rf.FORMATO_REQUERIMIENTO as formatoRequerimiento,\n"
            + "     dr.CODIGO_ENTIDAD        as codigoEntidad,\n"
            + "     dr.NOMBRE_CE             as nombreCe,\n"
            + "     dr.NOMBRE_DEPARTAMENTO   as nombreDepartamento,\n"
            + "     dr.NUMERO_NIT            as numeroNit,\n"
            + "     dr.RAZON_SOCIAL          as razonSocial,\n"
            + "     RB.NOMBRE_CORTO          as descripcionRubro,\n"
            + "     dp.MONTO_ACTUAL          as montoActual,\n"
            + "     dp.CANTIDAD_ACTUAL       as cantidadActual,\n"
            + "     dr.NOMBRE_ENT_FINAN      as nombreEntFinan,\n"
            + "     pp.ID_PLANILLA           as idPlanilla,\n"
            + "     pp.FECHA_INSERCION       as fechaInsercion\n"
            + " from planilla_pago pp\n"
            + "     inner join detalle_planilla dp         on pp.ID_PLANILLA = dp.id_planilla\n"
            + "     inner join DETALLE_DOC_PAGO ddp        on dp.ID_DETALLE_DOC_PAGO = ddp.ID_DETALLE_DOC_PAGO\n"
            + "     inner join DETALLE_REQUERIMIENTO dr    on dr.ID_DET_REQUERIMIENTO = ddp.ID_DET_REQUERIMIENTO\n"
            + "     inner join REQUERIMIENTO_FONDOS rf     on rf.ID_REQUERIMIENTO = dr.ID_REQUERIMIENTO\n"
            + "     inner join DETALLE_PROCESO_ADQ dpa     on rf.id_det_proceso_adq = dpa.ID_DET_PROCESO_ADQ\n"
            + "     inner join RUBROS_AMOSTRAR_INTERES rb  on rb.ID_RUBRO_INTERES = dpa.ID_RUBRO_ADQ\n"
            + "     inner join PROCESO_ADQUISICION pa      on pa.id_proceso_adq = dpa.ID_PROCESO_ADQ";

    public static final String QUERY_PROVEEDOR_MUNICIPIOS_DISPONIBLES_DE_INTERES = "select \n"
            + "                rownum                  as idRow,\n"
            + "                mun.id_municipio        as idMunicipio,\n"
            + "                mun.codigo_departamento as codigoDepartamento,\n"
            + "                depa.nombre_departamento || ' : ' ||mun.codigo_municipio|| ' - ' || mun.nombre_municipio    as nombreMunicipio,\n"
            + "                mun.codigo_municipio    as codigoMunicipio\n"
            + "            from \n"
            + "                municipio mun\n"
            + "                inner join departamento depa              on depa.codigo_departamento = mun.codigo_departamento\n"
            + "                left outer join DIS_MUNICIPIO_INTERES dis on mun.id_municipio = dis.id_municipio\n"
            + "                                                            and dis.ID_CAPA_DISTRIBUCION = ?1 \n"
            + "                                                            and dis.estado_eliminacion = 0 \n"
            + "            where \n"
            + "                mun.id_municipio is not null and "
            + "                id_municipio_interes is null \n"
            + "                COMODIN_DEPARTAMENTO\n"
            + "            order by\n"
            + "                mun.id_municipio";

    public static final String QUERY_PROVEEDOR_RESUMEN_ADJ_EMP = "SELECT \n"
            + "    rownum          as idRow,\n"
            + "    codigo_entidad  as codigoEntidad,\n"
            + "    nombre          as nombre,\n"
            + "    rubro           as rubro,\n"
            + "    cantidad        as cantidad,\n"
            + "    monto           as monto\n"
            + "FROM \n"
            + "    VW_RPT_PROVEEDORES_CONTRATADOS \n"
            + "WHERE \n"
            + "    NUMERO_NIT = ?1 AND \n"
            + "    ID_DET_PROCESO_ADQ = ?2";
    public static final String QUERY_PROVEEDOR_DETALLE_ADJ_EMP = "SELECT \n"
            + "    rownum              as idRow,\n"
            + "    nombre_departamento as nombreDepartamento,\n"
            + "    nombre_municipio    as nombreMunicipio,\n"
            + "    codigo_entidad      as codigoEntidad,\n"
            + "    nombre,\n"
            + "    descripcion_nivel   as descripcionNivel,\n"
            + "    nombre_corto        as rubro,\n"
            + "    nombre_producto     as nombreProducto,\n"
            + "    cantidad,\n"
            + "    monto,\n"
            + "    fecha_apertura      as fechaApertura\n"
            + "FROM \n"
            + "     vw_detalle_adjudicacion_emp WHERE NUMERO_NIT=?1 AND ID_DET_PROCESO_ADQ=?2";

    public static String addCampoToWhere(String cadenaWhere, String nombreCampo, Object objeto) {
        if (objeto != null) {
            if (objeto instanceof String) {
                if (!objeto.toString().isEmpty()) {
                    cadenaWhere = cadenaWhere + (cadenaWhere.contains("WHERE") ? " AND " : " WHERE ") + nombreCampo + " like '%" + ((String) objeto).toUpperCase() + "%'";
                }
            } else {
                cadenaWhere = cadenaWhere + (cadenaWhere.contains("WHERE") ? " AND " : " WHERE ") + nombreCampo + " = " + objeto + "";
            }
        } else {
            //cadenaWhere = cadenaWhere + (cadenaWhere.contains("WHERE") ? " AND " : " WHERE ") + nombreCampo + " = " + objeto;
        }
        return cadenaWhere;
    }
}
