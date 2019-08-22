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
public class Constantes {

    public static final String MSJ_ERROR = "msjError";
    public static final String MSJ_WARNING = "msjAlerta";
    public static final String MSJ_INFO = "msjInfo";
    public static final String MSJ_INSERCION = "msjInsercion";
    public static final String ERROR = "error";
    public static final String WARNING = "alerta";

    public static final String QUERY_CONTRATACION_RESUMEN_CONTRATACIONES = "SELECT \n"
            + "                rownum                  as idRow,\n"
            + "                CODIGO_ENTIDAD          as codigoEntidad,\n"
            + "                NOMBRE_CE               as nombreCe,\n"
            + "                NOMBRE_DEPARTAMENTO_CE  as nombreDepartamentoCe,\n"
            + "                NOMBRE_MUNICIPIO_CE     as nombreMunicipioCe,\n"
            + "                DIRECCION_CE            as direccionCe,\n"
            + "                RUBRO                   as rubro,\n"
            + "                NUMERO_NIT              as numeroNit,\n"
            + "                RAZON_SOCIAL            as razonSocial,\n"
            + "                DIRECCION_EMP           as direccionEmp,\n"
            + "                TELEFONO_EMP            as telefonoEmp,\n"
            + "                CELULAR_EMP             as celularEmp,\n"
            + "                NOMBRE_DEPARTAMENTO_EMP as nombreDepartamentoEmp,\n"
            + "                NOMBRE_MUNICIPIO_EMP    as nombreMunicipioEmp,\n"
            + "                CANTIDAD_CONTRATO       as cantidadContrato,\n"
            + "                MONTO_CONTRATO          as montoContrato,\n"
            + "                ID_CONTRATO             as idContrato\n"
            + "            FROM \n"
            + "                VW_RPT_RESUMEN_CONTRATACION \n"
            + "            WHERE \n"
            + "                ID_DET_PROCESO_ADQ = ?1";

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

    public static final String QUERY_PAGOS_BUSQUEDA_PLANILLA = "select rownum                   as idRow, \n"
            + "    FORMATO_REQUERIMIENTO as formatoRequerimiento,\n"
            + "    CODIGO_ENTIDAD        as codigoEntidad,\n"
            + "    NOMBRE_CE             as nombreCe,\n"
            + "    NOMBRE_DEPARTAMENTO   as nombreDepartamento,\n"
            + "    NUMERO_NIT            as numeroNit,\n"
            + "    RAZON_SOCIAL          as razonSocial,\n"
            + "    NOMBRE_CORTO          as descripcionRubro,\n"
            + "    MONTO_ACTUAL          as montoActual,\n"
            + "    CANTIDAD_ACTUAL       as cantidadActual,\n"
            + "    NOMBRE_ENT_FINAN      as nombreEntFinan,\n"
            + "    ID_PLANILLA           as idPlanilla,\n"
            + "    FECHA_INSERCION       as fechaInsercion,\n"
            + "    numero_cheque         as numCheque,\n"
            + "    fecha_cheque          as fechaCheque\n"
            + "from vw_pago_busqueda_planilla ";

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
    public static final String QUERY_CONTRATACION_ANALISIS_ECONOMICO_UNIFORME = "SELECT \n"
            + "    pre.no_item ,\n"
            + "    cat.nombre_producto || ' para ' || nvl.descripcion_nivel descripcion_item, \n"
            + "    case cat.id_producto \n"
            + "    when 30 then case nvl.id_nivel_educativo when 6 then vw.femenimo else vw.femenimo end\n"
            + "    when 44 then case nvl.id_nivel_educativo when 6 then vw.femenimo else vw.femenimo end\n"
            + "    when 32 then case nvl.id_nivel_educativo when 6 then vw.femenimo else vw.femenimo end\n"
            + "    when 29 then case nvl.id_nivel_educativo when 6 then vw.masculino else vw.masculino end\n"
            + "    when 34 then case nvl.id_nivel_educativo when 6 then vw.masculino else vw.masculino end\n"
            + "    when 31 then case nvl.id_nivel_educativo when 6 then vw.masculino else vw.masculino end end num_alumno,\n"
            + "    pre.precio_referencia, \n"
            + "    pre.precio_referencia * (vw.masculino + vw.femenimo) sub_total, \n"
            + "    nvl.id_nivel_educativo, \n"
            + "    vw.codigo_entidad, \n"
            + "    pre.id_det_proceso_adq, \n"
            + "    pre.id_empresa, \n"
            + "    emp.razon_social,\n"
            + "    nvl(det.cantidad,0) adjudicada\n"
            + "FROM \n"
            + "    precios_ref_rubro_emp pre\n"
            + "    INNER JOIN vw_detalle_cotizacion_uniforme vw ON pre.id_nivel_educativo = vw.id_nivel_educativo \n"
            + "    INNER JOIN catalogo_producto cat             ON cat.id_producto = pre.id_producto \n"
            + "    INNER JOIN nivel_educativo nvl               ON pre.id_nivel_educativo = nvl.id_nivel_educativo \n"
            + "    inner join empresa emp                       on pre.id_empresa = emp.id_empresa \n"
            + "    inner join participantes par                 on par.id_empresa = emp.id_empresa \n"
            + "    inner join oferta_bienes_servicios ofe       on par.id_oferta = ofe.id_oferta \n"
            + "                                                    and vw.codigo_entidad = ofe.codigo_entidad \n"
            + "                                                    and pre.id_det_proceso_adq in (%s) \n"
            + "                                                    and ofe.id_det_proceso_adq = vw.id_det_proceso_adq\n"
            + "    left outer join detalle_ofertas det          on par.id_participante = det.id_participante\n"
            + "                                                    and det.id_producto = cat.id_producto\n"
            + "                                                    and nvl.id_nivel_educativo = det.id_nivel_educativo\n"
            + "WHERE \n"
            + "    pre.estado_eliminacion = 0 and \n"
            + "    ofe.estado_eliminacion = 0 and \n"
            + "    par.estado_eliminacion = 0 and \n"
            + "    (det.estado_eliminacion is null or det.estado_eliminacion = 0) and\n"
            + "    ofe.codigo_entidad in (%s) and \n"
            + "    ofe.id_det_proceso_adq = %d \n"
            + "order by \n"
            + "    to_number(pre.no_item), \n"
            + "    pre.id_empresa";

    public static final String QUERY_CONTRATACION_ANALISIS_ECONOMICO_UTILES = "SELECT \n"
            + "    pre.no_item,\n"
            + "    cat.nombre_producto || ' para ' || nvl.descripcion_nivel descripcion_item, \n"
            + "    est.masculino + est.femenimo num_alumno, \n"
            + "    pre.precio_referencia, \n"
            + "    pre.precio_referencia * (est.masculino + est.femenimo) sub_total, \n"
            + "    nvl.id_nivel_educativo, \n"
            + "    est.codigo_entidad, \n"
            + "    pre.id_det_proceso_adq, \n"
            + "    pre.id_empresa, \n"
            + "    emp.razon_social,\n"
            + "    nvl(det.cantidad,0) adjudicada\n"
            + "FROM precios_ref_rubro_emp pre\n"
            + "    INNER JOIN estadistica_censo est        ON pre.id_nivel_educativo = est.id_nivel_educativo \n"
            + "    INNER JOIN catalogo_producto cat        ON cat.id_producto = pre.id_producto \n"
            + "    INNER JOIN nivel_educativo nvl          ON pre.id_nivel_educativo = nvl.id_nivel_educativo \n"
            + "    INNER JOIN empresa emp                  ON pre.id_empresa = emp.id_empresa \n"
            + "    INNER JOIN participantes par            ON par.id_empresa = emp.id_empresa \n"
            + "    INNER JOIN oferta_bienes_servicios ofe  ON par.id_oferta = ofe.id_oferta \n"
            + "                                                AND est.codigo_entidad = ofe.codigo_entidad \n"
            + "                                                and pre.id_det_proceso_adq = ofe.id_det_proceso_adq\n"
            + "    left outer join detalle_ofertas det          on par.id_participante = det.id_participante\n"
            + "                                                and det.id_producto = cat.id_producto\n"
            + "                                                and est.id_nivel_educativo = det.id_nivel_educativo\n"
            + "    INNER JOIN detalle_proceso_adq detp     ON detp.id_proceso_adq = est.id_proceso_adq\n"
            + "                                                AND ofe.id_det_proceso_adq = detp.id_det_proceso_adq\n"
            + "WHERE \n"
            + "    pre.estado_eliminacion = 0 AND \n"
            + "    ofe.estado_eliminacion = 0 AND\n"
            + "    par.estado_eliminacion = 0  AND \n"
            + "    (det.estado_eliminacion is null or det.estado_eliminacion = 0) and\n"
            + "    ofe.codigo_entidad IN (%s) AND\n"
            + "    ofe.id_det_proceso_adq = %d \n"
            + "ORDER BY \n"
            + "    to_number(pre.no_item), \n"
            + "    pre.id_empresa";

    public static final String QUERY_CONTRATACION_ANALISIS_ECONOMICO_ZAPATOS = "select \n"
            + "    pre.no_item,\n"
            + "    cat.nombre_producto || ' para ' || nvl.descripcion_nivel descripcion_item,\n"
            + "    case cat.id_producto when 43 then est.masculino when 21 then est.femenimo  end num_alumno,\n"
            + "    pre.precio_referencia, \n"
            + "    pre.precio_referencia*(est.masculino + est.femenimo) sub_total, \n"
            + "    nvl.id_nivel_educativo, \n"
            + "    est.codigo_entidad, \n"
            + "    pre.id_det_proceso_adq, \n"
            + "    pre.id_empresa, \n"
            + "    emp.razon_social,\n"
            + "    nvl(det.cantidad,0) adjudicada\n"
            + "from precios_ref_rubro_emp pre\n"
            + "    inner join estadistica_censo est        on pre.id_nivel_educativo = est.id_nivel_educativo \n"
            + "    inner join catalogo_producto cat        on cat.id_producto = pre.id_producto \n"
            + "    inner join nivel_educativo nvl          on pre.id_nivel_educativo = nvl.id_nivel_educativo \n"
            + "    inner join empresa emp                  on pre.id_empresa = emp.id_empresa \n"
            + "    inner join participantes par            on par.id_empresa = emp.id_empresa \n"
            + "    inner join oferta_bienes_servicios ofe  on par.id_oferta = ofe.id_oferta \n"
            + "                                                and est.codigo_entidad = ofe.codigo_entidad \n"
            + "                                                and ofe.id_det_proceso_adq = pre.id_det_proceso_adq\n"
            + "    left outer join detalle_ofertas det          on par.id_participante = det.id_participante\n"
            + "                                                and det.id_producto = cat.id_producto\n"
            + "                                                and est.id_nivel_educativo = det.id_nivel_educativo\n"
            + "    inner join detalle_proceso_adq detp     on detp.id_proceso_adq = est.id_proceso_adq\n"
            + "                                                and ofe.id_det_proceso_adq = detp.id_det_proceso_adq\n"
            + "where\n"
            + "    pre.estado_eliminacion = 0 and \n"
            + "    ofe.estado_eliminacion = 0 and\n"
            + "    par.estado_eliminacion = 0 and\n"
            + "    ofe.codigo_entidad in (%s) and\n"
            + "    ofe.id_det_proceso_adq = %d \n"
            + "order by \n"
            + "    to_number(pre.no_item),\n"
            + "    pre.id_empresa";

    public static final String QUERY_PAGOS_ENTIDADES_POR_REQUERIMIENTO = "SELECT distinct NOMBRE_ENT_FINAN \n"
            + " FROM DETALLE_REQUERIMIENTO\n"
            + " WHERE \n"
            + "     ID_DET_REQUERIMIENTO not in("
            + "         SELECT DR.ID_DET_REQUERIMIENTO \n"
            + "         FROM DETALLE_PLANILLA DP\n"
            + "             INNER JOIN DETALLE_DOC_PAGO DDP ON DP.ID_DETALLE_DOC_PAGO = DDP.ID_DETALLE_DOC_PAGO\n"
            + "             INNER JOIN DETALLE_REQUERIMIENTO DR ON DR.ID_DET_REQUERIMIENTO = DDP.ID_DET_REQUERIMIENTO\n"
            + "         WHERE DP.ESTADO_ELIMINACION = 0\n"
            + "             AND DDP.ESTADO_ELIMINACION = 0\n"
            + "             AND DR.ID_REQUERIMIENTO = ?1) AND\n"
            + "     ID_REQUERIMIENTO = ?1\n"
            + " ORDER BY NOMBRE_ENT_FINAN";

    public static final String QUERY_CREDITO_ACTIVOS_POR_PROVEEDOR = "SELECT\n"
            + "                rownum              as idRow,\n"
            + "                id_credito          as idCredito,\n"
            + "                numero_nit          as numeroNit,\n"
            + "                monto_credito       as montoCredito,\n"
            + "                nombre_ent_finan    as nombreEntFinan,\n"
            + "                fecha_vencimiento   as fechaVencimiento,\n"
            + "                codigo_entidad      as codigoEntidad,\n"
            + "                nombre              as nombreCe,\n"
            + "                nombre_departamento as nombreDepartamento,\n"
            + "                id_det_proceso_adq  as idProceso,\n"
            + "                codigo_departamento as codigoDepartamento,\n"
            + "                depa_emp            as codigoDepaEmp,\n"
            + "                credito_activo      as creditoActivo,\n"
            + "                monto               as montoContrato,\n"
            + "                nom_depa_emp        as nombreDepartamentoPro,\n"
            + "                razon_social        as razonSocial,\n"
            + "                cod_depa_emp        as codigoDepaEmp\n"
            + "            FROM\n"
            + "                vw_creditos_proveedores ";

    public static final String QUERY_PAGOS_SEGUIMIENTO_BY_DET_PROCESO_ADQ = "select \n"
            + "    codigo_departamento,\n"
            + "    nombre_departamento,\n"
            + "    descripcion_rubro,\n"
            + "    numero_nit,\n"
            + "    razon_social,\n"
            + "    count(total_contratos) numero_contratos,\n"
            + "    sum(monto_original) monto_total_contratado,\n"
            + "    sum(contrato_pagado) contratos_pagados,\n"
            + "    sum(monto_pagado) monto_pagado,\n"
            + "    sum(monto_reintegro) monto_reintegro,\n"
            + "    sum(monto_original - monto_reintegro - monto_pagado) monto_pendiente_pago\n"
            + "from     \n"
            + "    (select \n"
            + "        dr.codigo_departamento,\n"
            + "        dr.nombre_departamento,\n"
            + "        dr.codigo_municipio,\n"
            + "        dr.nombre_municipio,\n"
            + "        dr.descripcion_rubro,\n"
            + "        dr.numero_nit,\n"
            + "        dr.razon_social,\n"
            + "        dr.id_contrato total_contratos,\n"
            + "        case when vw.id_contrato is null then 0 else 1 end contrato_pagado,\n"
            + "        dr.monto_total monto_original,\n"
            + "        nvl(vw.monto_actual,0) monto_pagado,\n"
            + "        case vw.contrato_modif when 1 then dr.monto_total - nvl(vw.monto_actual,0) else 0 end monto_reintegro\n"
            + "    from \n"
            + "        requerimiento_fondos rf     \n"
            + "        inner join detalle_proceso_Adq dpa          on rf.id_det_proceso_adq = dpa.id_det_proceso_adq\n"
            + "        inner join rubros_amostrar_interes rub      on dpa.id_rubro_adq = rub.id_rubro_interes\n"
            + "        inner join detalle_requerimiento dr         on rf.id_requerimiento = dr.id_requerimiento\n"
            + "        left outer join vw_sub_pago_det_doc_pago vw on vw.id_det_requerimiento = dr.id_det_requerimiento\n"
            + "    where \n"
            + "        rf.id_det_proceso_adq = ?1 and\n"
            + "        rf.estado_eliminacion = 0)\n"
            + "group by \n"
            + "    codigo_departamento,\n"
            + "    nombre_departamento,\n"
            + "    descripcion_rubro,\n"
            + "    numero_nit,\n"
            + "    razon_social\n"
            + "order by \n"
            + "    codigo_departamento,\n"
            + "    razon_social";

    public static String addCampoToWhere(String cadenaWhere, String nombreCampo, Object objeto) {
        if (objeto != null) {
            if (objeto instanceof String) {
                if (!objeto.toString().isEmpty()) {
                    cadenaWhere = cadenaWhere + (cadenaWhere.contains("WHERE") ? " AND " : " WHERE ") + nombreCampo + " like '%" + ((String) objeto).toUpperCase() + "%'";
                }
            } else {
                cadenaWhere = cadenaWhere + (cadenaWhere.contains("WHERE") ? " AND " : " WHERE ") + nombreCampo + " = " + objeto + "";
            }
        }
        return cadenaWhere;
    }
}
