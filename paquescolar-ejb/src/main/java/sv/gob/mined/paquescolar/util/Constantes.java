/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.util;

import java.math.BigDecimal;

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

    public static final String QUERY_CONTRATACION_ESTADO_RESERVA = "select res.id_estado_reserva \n"
            + "                from \n"
            + "                    contratos_ordenes_compras con\n"
            + "                    inner join resoluciones_adjudicativas res on res.id_resolucion_adj = con.id_resolucion_adj\n"
            + "                    inner join participantes par on res.id_participante = par.id_participante\n"
            + "                    inner join oferta_bienes_servicios ofe on par.id_oferta = ofe.id_oferta\n"
            + "                where\n"
            + "                    con.estado_eliminacion = 0 and\n"
            + "                    res.estado_eliminacion = 0 and\n"
            + "                    par.estado_eliminacion = 0 and\n"
            + "                    ofe.estado_eliminacion = 0 and\n"
            + "                    ofe.codigo_entidad = ?1 and\n"
            + "                    ofe.id_det_proceso_adq = ?2";

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
            + "                ID_CONTRATO             as idContrato,\n"
            + "                miembro_firma       as miembroFirma,\n"
            + "                tel_director        as telDirector,\n"
            + "                tel_director2       as telDirector2,\n"
            + "                numero_telefono     as numeroTelefono,\n"
            + "                num_telefono2       as telefonoEmp2,\n"
            + "                num_telefono3       as telefonoEmp3,"
            + "                fecha_emision       as fechaEmision"
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
            + "    DESCRIP_CRED_ACT    as descripCredAct,"
            + "    monto_modificativa  as montoModificativa,"
            + "    ref_prestamo        as refPrestamo"
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
            + " FROM DETALLE_REQUERIMIENTO DR\n"
            + "     LEFT JOIN DETALLE_DOC_PAGO DDP ON DR.ID_DET_REQUERIMIENTO = DDP.ID_DET_REQUERIMIENTO\n"
            + " WHERE DR.ID_REQUERIMIENTO = ?1 \n"
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

    public static final String QUERY_CONTRATACION_MATRICULA = "select \n"
            + "                    rownum              as idRow, \n"
            + "                    codigo_entidad      as codigoEntidad,\n"
            + "                    nombre              as nombreCe,\n"
            + "                    nombre_departamento as nombreDepartamento,\n"
            + "                    nombre_municipio    as nombreMunicipio,\n"
            + "                    inicial_2_fem       as inicial2Fem,\n"
            + "                    inicial_2_mas       as inicial2Mas,\n"
            + "                    inicial_3_fem       as inicial3Fem,\n"
            + "                    inicial_3_mas       as inicial3Mas,\n"
            + "                    par_fem             as parFem,\n"
            + "                    par_mas             as parMas,\n"
            + "                    grado_1_fem         as grado1Fem,\n"
            + "                    grado_1_mas         as grado1Mas,\n"
            + "                    grado_2_fem         as grado2Fem,\n"
            + "                    grado_2_mas         as grado2Mas,\n"
            + "                    grado_3_fem         as grado3Fem,\n"
            + "                    grado_3_mas         as grado3Mas,\n"
            + "                    grado_4_fem         as grado4Fem,\n"
            + "                    grado_4_mas         as grado4Mas,\n"
            + "                    grado_5_fem         as grado5Fem,\n"
            + "                    grado_5_mas         as grado5Mas,\n"
            + "                    grado_6_fem         as grado6Fem,\n"
            + "                    grado_6_mas         as grado6Mas,\n"
            + "                    grado_7_fem         as grado7Fem,\n"
            + "                    grado_7_mas         as grado7Mas,\n"
            + "                    grado_8_fem         as grado8Fem,\n"
            + "                    grado_8_mas         as grado8Mas,\n"
            + "                    grado_9_fem         as grado9Fem,\n"
            + "                    grado_9_mas         as grado9Mas,\n"
            + "                    fle_ciclo_3_fem     as fleCiclo3Fem,\n"
            + "                    fle_ciclo_3_mas     as fleCiclo3Mas,\n"
            + "                    media_1_fem         as media1Fem,\n"
            + "                    media_1_mas         as media1Mas,\n"
            + "                    media_2_fem         as media2Fem,\n"
            + "                    media_2_mas         as media2Mas,\n"
            + "                    media_3_fem         as media3Fem,\n"
            + "                    media_3_mas         as media3Mas,\n"
            + "                    fle_media_fem       as fleMediaFem,\n"
            + "                    fle_media_mas       as fleMediaMas    \n"
            + "                from (select \n"
            + "                            codigo_entidad,\n"
            + "                            nombre,\n"
            + "                            nombre_departamento,\n"
            + "                            nombre_municipio,\n"
            + "                            sum(inicial_2_fem) inicial_2_fem,\n"
            + "                            sum(inicial_2_mas) inicial_2_mas,\n"
            + "                            sum(inicial_3_fem) inicial_3_fem,\n"
            + "                            sum(inicial_3_mas) inicial_3_mas,\n"
            + "                            sum(par_fem) par_fem,\n"
            + "                            sum(par_mas) par_mas,\n"
            + "                            sum(grado_1_fem) grado_1_fem,\n"
            + "                            sum(grado_1_mas) grado_1_mas,\n"
            + "                            sum(grado_2_fem) grado_2_fem,\n"
            + "                            sum(grado_2_mas) grado_2_mas,\n"
            + "                            sum(grado_3_fem) grado_3_fem,\n"
            + "                            sum(grado_3_mas) grado_3_mas,\n"
            + "                            sum(grado_4_fem) grado_4_fem,\n"
            + "                            sum(grado_4_mas) grado_4_mas,\n"
            + "                            sum(grado_5_fem) grado_5_fem,\n"
            + "                            sum(grado_5_mas) grado_5_mas,\n"
            + "                            sum(grado_6_fem) grado_6_fem,\n"
            + "                            sum(grado_6_mas) grado_6_mas,\n"
            + "                            sum(grado_7_fem) grado_7_fem,\n"
            + "                            sum(grado_7_mas) grado_7_mas,\n"
            + "                            sum(grado_8_fem) grado_8_fem,\n"
            + "                            sum(grado_8_mas) grado_8_mas,\n"
            + "                            sum(grado_9_fem) grado_9_fem,\n"
            + "                            sum(grado_9_mas) grado_9_mas,\n"
            + "                            sum(fle_ciclo_3_fem) fle_ciclo_3_fem,\n"
            + "                            sum(fle_ciclo_3_mas) fle_ciclo_3_mas,\n"
            + "                            sum(media_1_fem) media_1_fem,\n"
            + "                            sum(media_1_mas) media_1_mas,\n"
            + "                            sum(media_2_fem) media_2_fem,\n"
            + "                            sum(media_2_mas) media_2_mas,\n"
            + "                            sum(media_3_fem) media_3_fem,\n"
            + "                            sum(media_3_mas) media_3_mas,\n"
            + "                            sum(fle_media_fem) fle_media_fem,\n"
            + "                            sum(fle_media_mas) fle_media_mas\n"
            + "                        from\n"
            + "                            (select \n"
            + "                                est.codigo_entidad,\n"
            + "                                vw.nombre,\n"
            + "                                dep.nombre_Departamento,\n"
            + "                                mun.nombre_municipio,\n"
            + "                                case est.id_nivel_educativo when 25 then nvl(femenimo,0) else 0 end inicial_2_fem,\n"
            + "                                case est.id_nivel_educativo when 25 then nvl(masculino,0) else 0 end inicial_2_mas,\n"
            + "                                case est.id_nivel_educativo when 26 then nvl(femenimo,0) else 0 end inicial_3_fem,\n"
            + "                                case est.id_nivel_educativo when 26 then nvl(masculino,0) else 0 end inicial_3_mas,\n"
            + "                                case est.id_nivel_educativo when 22 then nvl(femenimo,0) else 0 end par_fem,\n"
            + "                                case est.id_nivel_educativo when 22 then nvl(masculino,0) else 0 end par_mas,\n"
            + "                                case est.id_nivel_educativo when 10 then nvl(femenimo,0) else 0 end grado_1_fem,\n"
            + "                                case est.id_nivel_educativo when 10 then nvl(masculino,0) else 0 end grado_1_mas,\n"
            + "                                case est.id_nivel_educativo when 11 then nvl(femenimo,0) else 0 end grado_2_fem,\n"
            + "                                case est.id_nivel_educativo when 11 then nvl(masculino,0) else 0 end grado_2_mas,\n"
            + "                                case est.id_nivel_educativo when 12 then nvl(femenimo,0) else 0 end grado_3_fem,\n"
            + "                                case est.id_nivel_educativo when 12 then nvl(masculino,0) else 0 end grado_3_mas,\n"
            + "                                case est.id_nivel_educativo when 13 then nvl(femenimo,0) else 0 end grado_4_fem,\n"
            + "                                case est.id_nivel_educativo when 13 then nvl(masculino,0) else 0 end grado_4_mas,\n"
            + "                                case est.id_nivel_educativo when 14 then nvl(femenimo,0) else 0 end grado_5_fem,\n"
            + "                                case est.id_nivel_educativo when 14 then nvl(masculino,0) else 0 end grado_5_mas,\n"
            + "                                case est.id_nivel_educativo when 15 then nvl(femenimo,0) else 0 end grado_6_fem,\n"
            + "                                case est.id_nivel_educativo when 15 then nvl(masculino,0) else 0 end grado_6_mas,\n"
            + "                                case est.id_nivel_educativo when 7 then nvl(femenimo,0) else 0 end grado_7_fem,\n"
            + "                                case est.id_nivel_educativo when 7 then nvl(masculino,0) else 0 end grado_7_mas,\n"
            + "                                case est.id_nivel_educativo when 8 then nvl(femenimo,0) else 0 end grado_8_fem,\n"
            + "                                case est.id_nivel_educativo when 8 then nvl(masculino,0) else 0 end grado_8_mas,\n"
            + "                                case est.id_nivel_educativo when 9 then nvl(femenimo,0) else 0 end grado_9_fem,\n"
            + "                                case est.id_nivel_educativo when 9 then nvl(masculino,0) else 0 end grado_9_mas,\n"
            + "                                case est.id_nivel_educativo when 23 then nvl(femenimo,0) else 0 end fle_ciclo_3_fem,\n"
            + "                                case est.id_nivel_educativo when 23 then nvl(masculino,0) else 0 end fle_ciclo_3_mas,\n"
            + "                                case est.id_nivel_educativo when 16 then nvl(femenimo,0) else 0 end media_1_fem,\n"
            + "                                case est.id_nivel_educativo when 16 then nvl(masculino,0) else 0 end media_1_mas,\n"
            + "                                case est.id_nivel_educativo when 17 then nvl(femenimo,0) else 0 end media_2_fem,\n"
            + "                                case est.id_nivel_educativo when 17 then nvl(masculino,0) else 0 end media_2_mas,\n"
            + "                                case est.id_nivel_educativo when 18 then nvl(femenimo,0) else 0 end media_3_fem,\n"
            + "                                case est.id_nivel_educativo when 18 then nvl(masculino,0) else 0 end media_3_mas,\n"
            + "                                case est.id_nivel_educativo when 24 then nvl(femenimo,0) else 0 end fle_media_fem,\n"
            + "                                case est.id_nivel_educativo when 24 then nvl(masculino,0) else 0 end fle_media_mas\n"
            + "                            from \n"
            + "                                estadistica_censo est\n"
            + "                                inner join vw_catalogo_entidad_educativa vw on est.codigo_entidad = vw.codigo_entidad\n"
            + "                                inner join departamento dep on dep.codigo_departamento = vw.codigo_departamento\n"
            + "                                inner join municipio mun on dep.codigo_departamento = mun.codigo_departamento and mun.codigo_municipio = vw.codigo_municipio\n"
            + "                            :CONDICION:)\n"
            + "                        group by \n"
            + "                            codigo_entidad,\n"
            + "                            nombre,\n"
            + "                            nombre_departamento,\n"
            + "                            nombre_municipio\n"
            + "                        order by \n"
            + "                            nombre_departamento, \n"
            + "                            nombre_municipio, \n"
            + "                            codigo_entidad)";

    public static String addCampoToWhere(String cadenaWhere, String nombreCampo, Object objeto) {
        if (objeto != null) {
            if (objeto instanceof String) {
                if (!objeto.toString().isEmpty()) {
                    cadenaWhere = cadenaWhere + (cadenaWhere.contains("WHERE") ? " AND " : " WHERE ") + nombreCampo + " like '%" + ((String) objeto).toUpperCase() + "%'";
                }
            } else if (objeto instanceof BigDecimal) {
                if (((BigDecimal) objeto).intValue() > 0) {
                    cadenaWhere = cadenaWhere + (cadenaWhere.contains("WHERE") ? " AND " : " WHERE ") + nombreCampo + " = " + objeto + "";
                }
            } else {
                cadenaWhere = cadenaWhere + (cadenaWhere.contains("WHERE") ? " AND " : " WHERE ") + nombreCampo + " = " + objeto + "";
            }
        }
        return cadenaWhere;
    }
}
