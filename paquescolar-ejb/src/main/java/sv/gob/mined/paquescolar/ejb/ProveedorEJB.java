/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
import sv.gob.mined.paquescolar.model.Bancos;
import sv.gob.mined.paquescolar.model.CapaDistribucionAcre;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.CatalogoProducto;
import sv.gob.mined.paquescolar.model.CuentaBancaria;
import sv.gob.mined.paquescolar.model.Departamento;
import sv.gob.mined.paquescolar.model.DetRubroMuestraInteres;
import sv.gob.mined.paquescolar.model.DetalleDocPago;
import sv.gob.mined.paquescolar.model.DetalleOfertas;
import sv.gob.mined.paquescolar.model.DetallePlanilla;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.DetalleRequerimiento;
import sv.gob.mined.paquescolar.model.DisMunicipioInteres;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.Participantes;
import sv.gob.mined.paquescolar.model.Persona;
import sv.gob.mined.paquescolar.model.PlanillaPago;
import sv.gob.mined.paquescolar.model.PlanillaPagoCheque;
import sv.gob.mined.paquescolar.model.PreciosRefRubroEmp;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.RequerimientoFondos;
import sv.gob.mined.paquescolar.model.TipoPersoneria;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.ResumenRequerimientoDto;
import sv.gob.mined.paquescolar.model.pojos.proveedor.DetalleAdjudicacionEmpDto;
import sv.gob.mined.paquescolar.model.pojos.VwRptProveedoresContratadosDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.DetalleContratacionPorItemDto;
import sv.gob.mined.paquescolar.model.view.DatosPreliminarRequerimiento;
import sv.gob.mined.paquescolar.util.Constantes;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class ProveedorEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public Participantes findParticipantesById(BigDecimal id) {
        Query q = em.createQuery("SELECT p FROM Participantes p WHERE p.estadoEliminacion = 0 and p.idParticipante=:id", Participantes.class);
        q.setParameter("id", id);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            Participantes participante = (Participantes) q.getSingleResult();
            for (int i = participante.getDetalleOfertasList().size() - 1; i >= 0; i--) {
                if (participante.getDetalleOfertasList().get(i).getEstadoEliminacion().compareTo(BigInteger.ONE) == 0) {
                    participante.getDetalleOfertasList().remove(participante.getDetalleOfertasList().get(i));
                }
            }
            return participante;
        }
    }

    public List<DetalleRequerimiento> getLstDetalleReqByCodEntidadAndProceso(String codigoEntidad,
            ProcesoAdquisicion procesoAdq,
            String codigoDepartamento,
            String formatoRequerimiento) {

        String jpql = "SELECT d FROM DetalleRequerimiento d WHERE ";
        if (formatoRequerimiento != null && codigoEntidad != null) {
            jpql += "d.codigoEntidad=:codigoEntidad AND d.idRequerimiento.formatoRequerimiento=:formatoReq ";
        } else if (formatoRequerimiento == null && codigoEntidad != null) {
            jpql += "d.codigoEntidad=:codigoEntidad ";
        } else {
            jpql += "d.idRequerimiento.formatoRequerimiento=:formatoReq ";
        }

        jpql += "and d.idRequerimiento.idDetProcesoAdq.idProcesoAdq=:idProcesoAdq ";

        if (codigoDepartamento != null) {
            jpql += "and d.idRequerimiento.codigoDepartamento=:codDepa ";
        }

        Query q = em.createQuery(jpql + (jpql.contains("WHERE") ? " and " : " WHERE ") + " d.idRequerimiento.estadoEliminacion=0 ", DetalleRequerimiento.class);

        if (formatoRequerimiento != null && codigoEntidad != null) {
            q.setParameter("formatoReq", formatoRequerimiento);
            q.setParameter("codigoEntidad", codigoEntidad);
        } else if (formatoRequerimiento == null && codigoEntidad != null) {
            q.setParameter("codigoEntidad", codigoEntidad);
        } else {
            q.setParameter("formatoReq", formatoRequerimiento);
        }

        if (codigoDepartamento != null) {
            q.setParameter("codDepa", codigoDepartamento);
        }

        q.setParameter("idProcesoAdq", procesoAdq);

        return q.getResultList();
    }

    public DetalleDocPago getDetalleDocPago(DetalleRequerimiento detalleRequerimiento) {
        Query q = em.createQuery("SELECT d FROM DetalleDocPago d WHERE d.idDetRequerimiento=:idReq ", DetalleDocPago.class);
        q.setParameter("idReq", detalleRequerimiento);
        List<DetalleDocPago> lst = q.getResultList();
        if (lst.isEmpty()) {
            return new DetalleDocPago();
        } else {
            return lst.get(0);
        }
    }

    public List<DatosPreliminarRequerimiento> getLstPreRequerimiento(Integer idDetProcesoAdq, String codigoDepartamento, int credito, int idNivelEducativo) {
        String sqlString;
        String where = "";
        List<DatosPreliminarRequerimiento> lst = new ArrayList<>();

        switch (idNivelEducativo) {
            case 1:
                where = " AND UPPER(trim(VW_FORMATO_PRE_CARGA.NOMBRE)) LIKE '%PARVULARIA%' ";
                break;
            case 2:
                where = " AND VW_FORMATO_PRE_CARGA.CODIGO_ENTIDAD NOT IN (SELECT VW_FORMATO_PRE_CARGA.codigo_entidad FROM VW_FORMATO_PRE_CARGA WHERE ID_DET_PROCESO_ADQ = " + idDetProcesoAdq + " AND TIENE_CREDITO = " + (credito == 1 ? "'NO'" : "'SI'") + " AND (UPPER(trim(NOMBRE)) like '%PARVULARIA%' or UPPER(trim(NOMBRE)) like 'INSTITUTO%')) ";
                break;
            case 6:
                where = " AND UPPER(trim(VW_FORMATO_PRE_CARGA.NOMBRE)) LIKE 'INSTITUTO%' ";
                break;
        }

        switch (credito) {
            case 1:
                where += " AND TIENE_CREDITO = 'NO' ";
                break;
            default:
                where += " AND TIENE_CREDITO = 'SI' ";
                break;
        }

        sqlString = "SELECT \n"
                + "  VW_FORMATO_PRE_CARGA.CODIGO_DEPARTAMENTO,\n"
                + "  VW_FORMATO_PRE_CARGA.CODIGO_MUNICIPIO,\n"
                + "  VW_FORMATO_PRE_CARGA.NOMBRE_DEPARTAMENTO,\n"
                + "  VW_FORMATO_PRE_CARGA.NOMBRE_MUNICIPIO,\n"
                + "  VW_FORMATO_PRE_CARGA.CODIGO_ENTIDAD,\n"
                + "  UPPER(trim(VW_FORMATO_PRE_CARGA.NOMBRE)),\n"
                + "  VW_FORMATO_PRE_CARGA.INICIALES_MODALIDAD,\n"
                + "  VW_FORMATO_PRE_CARGA.DIRECCION_CE,\n"
                + "  VW_FORMATO_PRE_CARGA.TELEFONOS,\n"
                + "  VW_FORMATO_PRE_CARGA.MIEMBRO_FIRMA,\n"
                + "  VW_FORMATO_PRE_CARGA.TEL_DIRECTOR,\n"
                + "  VW_FORMATO_PRE_CARGA.DESCRIPCION_RUBRO,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.PAR_CANTIDAD) PAR_CANTIDAD,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.PAR_MONTO) PAR_MONTO,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.CI_CANTIDAD) CI_CANTIDAD,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.CI_MONTO) CI_MONTO,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.CII_CANTIDAD) CII_CANTIDAD,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.CII_MONTO) CII_MONTO,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.CIII_CANTIDAD) CIII_CANTIDAD,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.CIII_MONTO) CIII_MONTO,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.BAS_CANTIDAD) BAS_CANTIDAD,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.BAS_MONTO) BAS_MONTO,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.BAC_CANTIDAD) BAC_CANTIDAD,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.BAC_MONTO) BAC_MONTO,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.MONTO_TOTAL) MONTO_TOTAL,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.TOTAL_NINA) TOTAL_NINA,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.TOTAL_NINO) TOTAL_NINO,\n"
                + "  VW_FORMATO_PRE_CARGA.TIENE_CREDITO,\n"
                + "  VW_FORMATO_PRE_CARGA.ID_DET_PROCESO_ADQ,\n"
                + "  SUM(VW_FORMATO_PRE_CARGA.CANTIDAD_TOTAL) CANTIDAD_TOTAL\n"
                + "FROM VW_FORMATO_PRE_CARGA \n"
                + "   LEFT OUTER JOIN DETALLE_REQUERIMIENTO ON VW_FORMATO_PRE_CARGA.ID_CONTRATO = DETALLE_REQUERIMIENTO.ID_CONTRATO \n"
                + "WHERE VW_FORMATO_PRE_CARGA.ID_DET_PROCESO_ADQ = ?1 AND "
                + "  VW_FORMATO_PRE_CARGA.CODIGO_DEPARTAMENTO = ?2 AND "
                + "  VW_FORMATO_PRE_CARGA.TIENE_CREDITO = ?3 AND "
                + "  DETALLE_REQUERIMIENTO.ID_DET_REQUERIMIENTO IS NULL " + where + "  \n"
                + "GROUP BY \n"
                + "  VW_FORMATO_PRE_CARGA.CODIGO_DEPARTAMENTO,\n"
                + "  VW_FORMATO_PRE_CARGA.CODIGO_MUNICIPIO,\n"
                + "  VW_FORMATO_PRE_CARGA.NOMBRE_DEPARTAMENTO,\n"
                + "  VW_FORMATO_PRE_CARGA.NOMBRE_MUNICIPIO,\n"
                + "  VW_FORMATO_PRE_CARGA.CODIGO_ENTIDAD,\n"
                + "  VW_FORMATO_PRE_CARGA.NOMBRE,\n"
                + "  VW_FORMATO_PRE_CARGA.INICIALES_MODALIDAD,\n"
                + "  VW_FORMATO_PRE_CARGA.DIRECCION_CE,\n"
                + "  VW_FORMATO_PRE_CARGA.TELEFONOS,\n"
                + "  VW_FORMATO_PRE_CARGA.MIEMBRO_FIRMA,\n"
                + "  VW_FORMATO_PRE_CARGA.TEL_DIRECTOR,\n"
                + "  VW_FORMATO_PRE_CARGA.DESCRIPCION_RUBRO,\n"
                + "  VW_FORMATO_PRE_CARGA.TIENE_CREDITO,\n"
                + "  VW_FORMATO_PRE_CARGA.ID_DET_PROCESO_ADQ,\n"
                + "  VW_FORMATO_PRE_CARGA.ID_CONTRATO \n"
                + "ORDER BY VW_FORMATO_PRE_CARGA.ID_CONTRATO, VW_FORMATO_PRE_CARGA.CODIGO_ENTIDAD ASC";

        Query q = em.createNativeQuery(sqlString);
        q.setParameter(1, idDetProcesoAdq);
        q.setParameter(2, codigoDepartamento);
        q.setParameter(3, credito == 1 ? "NO" : "SI");

        for (Object object : q.getResultList()) {
            DatosPreliminarRequerimiento dato = new DatosPreliminarRequerimiento();
            Object[] datos = (Object[]) object;

            dato.setCodigoDepartamento(datos[0].toString());
            dato.setCodigoMunicipio(datos[1].toString());
            dato.setNombreDepartamento(datos[2].toString());
            dato.setNombreMunicipio(datos[3].toString());
            dato.setCodigoEntidad(datos[4].toString());
            dato.setNombre(datos[5].toString());
            dato.setInicialesModalidad(datos[6].toString());
            dato.setDireccionCe(datos[7] == null ? null : datos[7].toString());
            dato.setTelefonos(datos[8].toString());
            dato.setMiembroFirma(datos[9].toString());
            dato.setTelDirector(datos[10] == null ? null : datos[10].toString());
            dato.setDescripcionRubro(datos[11].toString());
            dato.setParCantidad((BigDecimal) datos[12]);
            dato.setParMonto((BigDecimal) datos[13]);
            dato.setCiCantidad((BigDecimal) datos[14]);
            dato.setCiMonto((BigDecimal) datos[15]);
            dato.setCiiCantidad((BigDecimal) datos[16]);
            dato.setCiiMonto((BigDecimal) datos[17]);
            dato.setCiiiCantidad((BigDecimal) datos[18]);
            dato.setCiiiMonto((BigDecimal) datos[19]);
            dato.setBasCantidad((BigDecimal) datos[20]);
            dato.setBasMonto((BigDecimal) datos[21]);
            dato.setBacCantidad((BigDecimal) datos[22]);
            dato.setBacMonto((BigDecimal) datos[23]);
            dato.setMontoTotal((BigDecimal) datos[24]);
            dato.setTotalNina((BigDecimal) datos[25]);
            dato.setTotalNino((BigDecimal) datos[26]);
            dato.setTieneCredito(datos[27].toString());
            dato.setIdDetProcesoAdq(((BigDecimal) datos[28]).intValue());
            dato.setCantidadTotal(((BigDecimal) datos[29]));

            lst.add(dato);
        }
        return lst;
    }

    public Boolean getDatosPreRequerimiento(Integer idDetProcesoAdq, String codigoDepartamento, int credito, int idNivelEducativo) {
        String sqlString;
        String where = "";

        switch (idNivelEducativo) {
            case 1:
                where = " AND UPPER(trim(VW_FORMATO_PRE_CARGA.NOMBRE)) LIKE '%PARVULARIA%' ";
                break;
            case 2:
                where = " AND VW_FORMATO_PRE_CARGA.CODIGO_ENTIDAD NOT IN (SELECT VW_FORMATO_PRE_CARGA.codigo_entidad FROM VW_FORMATO_PRE_CARGA WHERE ID_DET_PROCESO_ADQ = " + idDetProcesoAdq + " AND TIENE_CREDITO = " + (credito == 1 ? "'NO'" : "'SI'") + " AND (UPPER(trim(NOMBRE)) like '%PARVULARIA%' or UPPER(trim(NOMBRE)) like 'INSTITUTO%')) ";
                break;
            case 6:
                where = " AND UPPER(trim(VW_FORMATO_PRE_CARGA.NOMBRE)) LIKE 'INSTITUTO%' ";
                break;
        }

        switch (credito) {
            case 1:
                where += " AND TIENE_CREDITO = 'NO' ";
                break;
            default:
                where += " AND TIENE_CREDITO = 'SI' ";
                break;
        }

        sqlString = "SELECT \n"
                + "  count(*)\n"
                + "FROM VW_FORMATO_PRE_CARGA \n"
                + "   LEFT OUTER JOIN DETALLE_REQUERIMIENTO ON VW_FORMATO_PRE_CARGA.ID_CONTRATO = DETALLE_REQUERIMIENTO.ID_CONTRATO \n"
                + "WHERE VW_FORMATO_PRE_CARGA.ID_DET_PROCESO_ADQ = ?1 AND "
                + "  VW_FORMATO_PRE_CARGA.CODIGO_DEPARTAMENTO = ?2 AND "
                + "  VW_FORMATO_PRE_CARGA.TIENE_CREDITO = ?3 AND "
                + "  DETALLE_REQUERIMIENTO.ID_DET_REQUERIMIENTO IS NULL " + where + "  \n"
                + "ORDER BY VW_FORMATO_PRE_CARGA.ID_CONTRATO, VW_FORMATO_PRE_CARGA.CODIGO_ENTIDAD ASC";

        Query q = em.createNativeQuery(sqlString);
        q.setParameter(1, idDetProcesoAdq);
        q.setParameter(2, codigoDepartamento);
        q.setParameter(3, credito == 1 ? "NO" : "SI");

        BigDecimal cantidad = (BigDecimal) q.getSingleResult();
        return (cantidad.intValue() == 1);
    }

    public Empresa findEmpresaByNit(String nit) {
        Query q = em.createQuery("SELECT e FROM Empresa e WHERE e.numeroNit=:nit and e.estadoEliminacion=0", Empresa.class);
        q.setParameter("nit", nit);

        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (Empresa) q.getSingleResult();
        }
    }

    public Empresa findEmpresaByPk(BigDecimal pk) {
        Query q = em.createQuery("SELECT e FROM Empresa e WHERE e.idEmpresa=:idEmpresa", Empresa.class);
        q.setParameter("idEmpresa", pk);

        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (Empresa) q.getSingleResult();
        }
    }

    public List<Empresa> findEmpresaByValorBusqueda(String valor) {
        Query q = em.createQuery("SELECT e FROM Empresa e WHERE e.estadoEliminacion=0 and e.numeroNit like :nit OR FUNC('REGEXP_REPLACE', e.razonSocial,' ','') like :razonSocial", Empresa.class);
        q.setParameter("nit", "%" + valor + "%");
        q.setParameter("razonSocial", "%" + valor.replace(" ", "") + "%");
        return q.getResultList();
    }

    public DetRubroMuestraInteres findRubroByProveedor(BigDecimal idEmpresa) {
        Query q = em.createQuery("SELECT d FROM DetRubroMuestraInteres d WHERE d.idEmpresa.idEmpresa=:idEmpresa and d.estadoEliminacion=0", DetRubroMuestraInteres.class);
        q.setParameter("idEmpresa", idEmpresa);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (DetRubroMuestraInteres) q.getSingleResult();
        }
    }

    public <T extends Object> T findDetProveedor(ProcesoAdquisicion proceso, Empresa idEmpresa, Class clase) {
        Query q = em.createQuery("SELECT d FROM " + clase.getSimpleName() + " d WHERE d.idMuestraInteres.idDetProcesoAdq.idProcesoAdq=:proceso and d.idMuestraInteres.idEmpresa=:idEmpresa and d.estadoEliminacion=0 and d.idMuestraInteres.estadoEliminacion=0", clase);
        q.setParameter("proceso", proceso);
        q.setParameter("idEmpresa", idEmpresa);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (T) q.getResultList().get(0);
        }
    }

    public boolean updateCuentaEmpresa(String numeroNit, String numeroCuenta) {
        Query q = em.createQuery("SELECT e FROM Empresa e WHERE e.numeroNit=:numeroNit", Empresa.class);
        q.setParameter("numeroNit", numeroNit);
        if (q.getResultList().isEmpty()) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.INFO, "El proveedor {0} no fue encontrado", numeroNit);
            return false;
        } else {
            Empresa emp = (Empresa) q.getResultList().get(0);
            emp.setNumeroCuenta(numeroCuenta);
            em.merge(emp);
            return true;
        }
    }

    public Boolean guardar(Object... entidades) {
        try {
            for (Object entidad : entidades) {
                if (isNewRegistro(entidad)) {
                    em.persist(entidad);
                } else {
                    entidad = em.merge(entidad);
                }
            }
            return true;
        } catch (Exception e) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public Boolean guardarCapaInst(CapaDistribucionAcre capaDistribucionAcre, CapaInstPorRubro capaInstPorRubro) {
        try {
            Query query = em.createQuery("UPDATE CapaInstPorRubro c SET c.capacidadAcreditada = :capaCalificada WHERE c.idMuestraInteres.idEmpresa.idEmpresa = :idEmpresa and c.idMuestraInteres.idDetProcesoAdq.idProcesoAdq.idAnho.idAnho = :idAnho ");
            query.setParameter("capaCalificada", capaInstPorRubro.getCapacidadAcreditada());
            query.setParameter("idEmpresa", capaInstPorRubro.getIdMuestraInteres().getIdEmpresa().getIdEmpresa());
            query.setParameter("idAnho", capaInstPorRubro.getIdMuestraInteres().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getIdAnho());

            query.executeUpdate();

            query = em.createQuery("UPDATE CapaDistribucionAcre c SET c.codigoDepartamento = :codigoDepartamento WHERE c.idMuestraInteres.idEmpresa.idEmpresa = :idEmpresa and c.idMuestraInteres.idDetProcesoAdq.idProcesoAdq.idAnho.idAnho = :idAnho ");
            query.setParameter("codigoDepartamento", capaDistribucionAcre.getCodigoDepartamento());
            query.setParameter("idEmpresa", capaInstPorRubro.getIdMuestraInteres().getIdEmpresa().getIdEmpresa());
            query.setParameter("idAnho", capaInstPorRubro.getIdMuestraInteres().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getIdAnho());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public PlanillaPagoCheque guardar(PlanillaPagoCheque entidad) {
        try {
            if (isNewRegistro(entidad)) {
                em.persist(entidad);
            } else {
                entidad = em.merge(entidad);
            }

            return entidad;
        } catch (Exception e) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public void guardarDetDocPago(DetalleDocPago detalleDocPago, String usuario) {
        if (detalleDocPago.getIdDetalleDocPago() == null) {
            detalleDocPago.setFechaInsercion(new Date());
            detalleDocPago.setEstadoEliminacion((short) 0);
            detalleDocPago.setUsuarioInsercion(usuario);
            em.persist(detalleDocPago);
        } else {
            detalleDocPago.setFechaModificacion(new Date());
            detalleDocPago.setUsuarioModificacion(usuario);
            em.merge(detalleDocPago);
        }
    }

    public void guardarDetalleRequerimiento(DetalleRequerimiento detalleRequerimiento) {
        em.merge(detalleRequerimiento);
    }

    /**
     * Método que verifica si una empresa es del tipo de personeria natural
     *
     * @param numeroNit
     * @return true para Personeria Natural
     */
    public Boolean isPersonaNatural(String numeroNit) {
        Query q = em.createQuery("SELECT e.idPersoneria FROM Empresa e WHERE e.numeroNit=:numeroNit", TipoPersoneria.class);
        q.setParameter("numeroNit", numeroNit);
        return ((TipoPersoneria) q.getSingleResult()).getIdPersoneria().intValue() == 1;
    }

    public synchronized void guardarDetDocPago(DetalleDocPago detalleDocPago) {
        if (detalleDocPago.getIdDetalleDocPago() != null) {
            em.merge(detalleDocPago);
        } else {
            em.persist(detalleDocPago);
        }
    }

    public Boolean guardarDetalleOferta(List<DetalleOfertas> lstDetalleOfertas) {
        try {
            for (DetalleOfertas detalleOfertas : lstDetalleOfertas) {
                if (detalleOfertas.getIdDetalleOfe() == null) {
                    em.persist(detalleOfertas);
                } else {
                    detalleOfertas = em.merge(detalleOfertas);
                }
            }
            return true;
        } catch (Exception e) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

    }

    private Boolean isNewRegistro(Object t) {
        Boolean valor = false;
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(javax.persistence.Id.class) != null) {
                try {
                    Class[] sinArgumentos = new Class[0];
                    Object[] sinParametros = new Object[0];
                    Method getter = new PropertyDescriptor(field.getName(), t.getClass()).getReadMethod();
                    Object value = t.getClass().getMethod(getter.getName(), sinArgumentos).invoke(t, sinParametros);
                    valor = value == null;
                    break;
                } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                    Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return valor;
    }

    public List<DisMunicipioInteres> findMunicipiosInteres(CapaDistribucionAcre depa) {
        Query query = em.createQuery("SELECT d FROM DisMunicipioInteres d WHERE d.idCapaDistribucion =:capa ORDER BY d.idMunicipio.idMunicipio, d.idMunicipio.codigoDepartamento.codigoDepartamento", DisMunicipioInteres.class);
        query.setParameter("capa", depa);

        return query.getResultList();
    }

    public List<PreciosRefRubroEmp> findPreciosRefRubroEmpRubro(Empresa idEmpresa, DetalleProcesoAdq idDetProceso) {
        Query q;
        if (idDetProceso.getIdRubroAdq().getDescripcionRubro().contains("2do") || idDetProceso.getIdRubroAdq().getDescripcionRubro().contains("1er")) {
            q = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.idEmpresa=:idEmpresa and (p.idDetProcesoAdq.idRubroAdq.idRubroInteres=:idRubro AND p.idDetProcesoAdq.idProcesoAdq =:idProceso) and p.estadoEliminacion=0 ORDER BY FUNC('TO_NUMBER', p.noItem)", PreciosRefRubroEmp.class);
            q.setParameter("idEmpresa", idEmpresa);
            q.setParameter("idRubro", new BigDecimal(4));

            if (idDetProceso.getIdProcesoAdq().getPadreIdProcesoAdq() == null) {
                q.setParameter("idProceso", idDetProceso.getIdProcesoAdq());
            } else {
                q.setParameter("idProceso", idDetProceso.getIdProcesoAdq().getPadreIdProcesoAdq());
            }
        } else {
            q = em.createQuery("SELECT p FROM PreciosRefRubroEmp p  WHERE p.idEmpresa=:idEmpresa and (p.idDetProcesoAdq=:idDetProceso or p.idDetProcesoAdq.idProcesoAdq =:idDetProceso1) and p.estadoEliminacion=0 ORDER BY FUNC('TO_NUMBER', p.noItem)", PreciosRefRubroEmp.class);
            q.setParameter("idEmpresa", idEmpresa);
            q.setParameter("idDetProceso", idDetProceso);
            q.setParameter("idDetProceso1", idDetProceso.getIdProcesoAdq().getPadreIdProcesoAdq());
        }
        return q.getResultList();
    }

    public Boolean isDepaCalificado(Empresa empresa, String codigoDepartamento, DetalleProcesoAdq detalleProceso) {
        Boolean valor = false;
        Query q;
        q = em.createQuery("SELECT c.codigoDepartamento FROM CapaDistribucionAcre c WHERE c.idMuestraInteres.idEmpresa=:idEmpresa and (c.idMuestraInteres.idDetProcesoAdq.idRubroAdq.idRubroInteres=:idRubro and c.idMuestraInteres.idDetProcesoAdq.idProcesoAdq.idAnho.idAnho=:idAnho) and c.estadoEliminacion=0");
        q.setParameter("idEmpresa", empresa);
        if (detalleProceso.getIdRubroAdq().getDescripcionRubro().contains("2do")) {
            q.setParameter("idRubro", new BigDecimal(4));
        } else {
            q.setParameter("idRubro", detalleProceso.getIdRubroAdq().getIdRubroInteres());
        }
        q.setParameter("idAnho", detalleProceso.getIdProcesoAdq().getIdAnho().getIdAnho());

        List lst = q.getResultList();
        if (!lst.isEmpty()) {
            Departamento depaCalificado = (Departamento) lst.get(0);
            if (!depaCalificado.getCodigoDepartamento().equals("00")) {
                valor = depaCalificado.getCodigoDepartamento().equals(codigoDepartamento);
            } else {
                valor = depaCalificado.getCodigoDepartamento().equals("00");
            }
        }
        return valor;
    }

    public CatalogoProducto findProducto(String codigo) {
        Query q = em.createQuery("SELECT c FROM CatalogoProducto c WHERE c.idProducto=:codigo", CatalogoProducto.class);
        q.setParameter("codigo", new BigDecimal(codigo));

        try {
            return (CatalogoProducto) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public NivelEducativo findNivelEducativo(String idNivel) {
        Query q = em.createQuery("SELECT n FROM NivelEducativo n WHERE n.idNivelEducativo=:idNivel", NivelEducativo.class);
        q.setParameter("idNivel", new BigDecimal(idNivel));

        try {
            return (NivelEducativo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<NivelEducativo> findNivelEducativoProveedor(Empresa empresa) {
        Query q = em.createQuery("SELECT distinct d.idNivelEducativo FROM DetCapaSegunRubro d WHERE d.idMuestraInteres.idEmpresa=:empresa", NivelEducativo.class);
        q.setParameter("empresa", empresa);

        return q.getResultList();
    }

    public List<CatalogoProducto> findItemProveedor(Empresa empresa, DetalleProcesoAdq detProcesoAdq) {
        Query q = em.createQuery("SELECT distinct d.idProducto FROM DetCapaSegunRubro d WHERE d.idMuestraInteres.estadoEliminacion=0 and d.idMuestraInteres.idEmpresa=:empresa and d.idMuestraInteres.idDetProcesoAdq=:detProcesoAdq and d.estadoEliminacion=0", CatalogoProducto.class);
        q.setParameter("empresa", empresa);
        q.setParameter("detProcesoAdq", detProcesoAdq);

        return q.getResultList();
    }

    public List<DetalleOfertas> findDetalleOfertas(Participantes participante, Boolean libros) {
        Query q = em.createQuery("SELECT d FROM DetalleOfertas d WHERE d.estadoEliminacion=0 and d.idParticipante=:participante and d.idProducto.idProducto " + (libros ? "" : "not") + " in (1) ORDER BY FUNC('TO_NUMBER', d.noItem)", CatalogoProducto.class);
        q.setParameter("participante", participante);

        return q.getResultList();
    }

    public List<CapaInstPorRubro> getLstCapaEmpPorNitOrRazonSocialAndRubroAndMunicipioCe(DetalleProcesoAdq rubro, String codigoEntidad, Boolean municipioIgual) {
        String codMunicipio;
        String codDepartamento;
        DetalleProcesoAdq detTemp = null;
        List<CapaInstPorRubro> lstCapa = new ArrayList<>();
        Query q = em.createNativeQuery("select codigo_municipio, codigo_departamento from vw_catalogo_entidad_educativa WHERE codigo_entidad = '" + codigoEntidad + "'");
        List lst = q.getResultList();
        codMunicipio = ((Object[]) lst.get(0))[0].toString();
        codDepartamento = ((Object[]) lst.get(0))[1].toString();

        if (rubro.getIdProcesoAdq().getPadreIdProcesoAdq() != null) {
            for (DetalleProcesoAdq object : rubro.getIdProcesoAdq().getPadreIdProcesoAdq().getDetalleProcesoAdqList()) {
                if (object.getIdRubroAdq().getIdRubroInteres().compareTo(rubro.getIdRubroAdq().getIdRubroInteres()) == 0) {
                    detTemp = object;
                    break;
                }
            }
        } else {
            detTemp = rubro;
        }

        BigDecimal idRubro, idAnho;
        if (detTemp.getIdRubroAdq().getDescripcionRubro().contains("2do")) {
            idRubro = new BigDecimal(4);
        } else {
            idRubro = detTemp.getIdRubroAdq().getIdRubroInteres();
        }
        idAnho = detTemp.getIdProcesoAdq().getIdAnho().getIdAnho();

        q = em.createNativeQuery("SELECT * FROM capa_inst_por_rubro WHERE id_muestra_interes in (" + findLstIdEmpresa(codMunicipio, codDepartamento, idRubro, idAnho, municipioIgual) + ") ORDER BY dbms_random.value", CapaInstPorRubro.class);
        
        lstCapa.addAll(q.getResultList());
        return lstCapa;
    }

    private String findLstIdEmpresa(String codMun, String codDep, BigDecimal idRubro, BigDecimal idAnho, Boolean municipioIgual) {
        String sql = "select det.id_muestra_interes \n"
                + "from \n"
                + "    det_rubro_muestra_interes det\n"
                + "    inner join empresa emp                  on emp.id_empresa = det.id_empresa\n"
                + "    inner join municipio mun_e              on mun_e.id_municipio = emp.id_municipio\n"
                + "    inner join detalle_proceso_adq dpa      on dpa.id_det_proceso_adq = det.id_det_proceso_adq\n"
                + "    inner join proceso_adquisicion pa       on pa.id_proceso_adq = dpa.id_proceso_adq\n"
                + "    inner join capa_distribucion_acre cap   on det.id_muestra_interes = cap.id_muestra_interes\n"
                + "    inner join dis_municipio_interes dis    on dis.id_capa_distribucion = cap.id_capa_distribucion\n"
                + "    inner join municipio mun                on mun.id_municipio = dis.id_municipio\n"
                + "    inner join departamento dep             on mun.codigo_departamento = dep.codigo_departamento\n"
                + "where \n"
                + "    mun.codigo_municipio = '" + codMun + "' and\n"
                + "    dep.codigo_departamento = '" + codDep + "' and\n"
                + "    dis.estado_eliminacion = 0 and\n"
                + "    dpa.id_rubro_adq = " + idRubro + " and\n"
                + "    pa.id_anho = " + idAnho + " and\n"
                + "    det.estado_eliminacion = 0 and\n"
                + "    mun_e.codigo_municipio " + (municipioIgual ? "=" : "<>") + "'" + codMun + "'";
        /*Query q = em.createNativeQuery(sql);
        q.setParameter(1, codMun);
        q.setParameter(2, codDep);
        q.setParameter(3, idRubro);
        q.setParameter(4, idAnho);*/

        return sql;
    }

    /**
     * Verifica la existencia de los precios de referencia para un proveedor y
     * año en particular
     *
     * @param idEmpresa
     * @param anho
     * @return devuelve true si existen
     */
    public Boolean isPrecioRef(Empresa idEmpresa, String anho) {
        Query query = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.idEmpresa=:idEmpresa and p.idDetProcesoAdq.idProcesoAdq.idAnho.anho=:anho and p.estadoEliminacion=0", PreciosRefRubroEmp.class);
        query.setParameter("idEmpresa", idEmpresa);
        query.setParameter("anho", anho);

        List<PreciosRefRubroEmp> lstPrecios = query.getResultList();

        return !lstPrecios.isEmpty();
    }

    public PreciosRefRubroEmp getPrecioRef(Empresa idEmpresa, BigDecimal idNivelEdu, BigDecimal idProducto, String anho) {
        Query query = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.idEmpresa=:idEmpresa and p.idNivelEducativo.idNivelEducativo=:idNivelEdu and p.idProducto.idProducto=:idProducto and p.idDetProcesoAdq.idProcesoAdq.idAnho.anho=:anho and p.estadoEliminacion=0", PreciosRefRubroEmp.class);
        query.setParameter("idEmpresa", idEmpresa);
        query.setParameter("idNivelEdu", idNivelEdu);
        query.setParameter("idProducto", idProducto);
        query.setParameter("anho", anho);

        List<PreciosRefRubroEmp> lstPrecios = query.getResultList();
        if (lstPrecios.isEmpty()) {
            return null;
        } else {
            return lstPrecios.get(0);
        }
    }

    public List<CapaInstPorRubro> findCapaInstPorRubro(Participantes participante) {
        Query query = em.createQuery("SELECT c FROM CapaInstPorRubro c WHERE c.idMuestraInteres.idEmpresa=:idEmpresa and c.idMuestraInteres.idDetProcesoAdq.idProcesoAdq.idAnho.idAnho=:idAnho and c.estadoEliminacion=0 and c.idMuestraInteres.estadoEliminacion=0", CapaInstPorRubro.class);

        query.setParameter("idEmpresa", participante.getIdEmpresa());
        query.setParameter("idAnho", participante.getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getIdAnho());
        List<CapaInstPorRubro> lstCapa = query.getResultList();

        return lstCapa;
    }

    public String getRespresentanteLegalEmp(BigDecimal idPersona) {
        Persona persona = em.find(Persona.class, idPersona);
        String representante;

        representante = persona.getPrimerNombre();

        if (persona.getSegundoNombre() != null) {
            representante = representante.concat(" ").concat(persona.getSegundoNombre());
        }
        representante = representante.concat(" ").concat(persona.getPrimerApellido());

        if (persona.getSegundoApellido() != null) {
            if (persona.getAcasada() != null) {
                representante = representante.concat(" ").concat(persona.getAcasada());
            } else {
                representante = representante.concat(" ").concat(persona.getSegundoApellido());
            }
        }
        return representante;
    }

    public List findAvanceDocumentosProcesados(String codDepartamento, Integer... procesos) {
        try {
            String query = "SELECT VW_CE_ASISTENTES.CODIGO_MUNICIPIO, \n"
                    + "  NOMBRE_MUNICIPIO, \n"
                    + "  CANTIDAD_CE, \n"
                    + "  uni.CANTIDAD_PROCESADOS,\n"
                    + "  uti.CANTIDAD_PROCESADOS,\n"
                    + "  zap.CANTIDAD_PROCESADOS\n"
                    + "FROM VW_CE_ASISTENTES \n"
                    + "  INNER JOIN VW_CE_PROCESADOS uni ON uni.CODIGO_DEPARTAMENTO = VW_CE_ASISTENTES.CODIGO_DEPARTAMENTO AND uni.CODIGO_MUNICIPIO = VW_CE_ASISTENTES.CODIGO_MUNICIPIO \n"
                    + "  INNER JOIN VW_CE_PROCESADOS uti ON uti.CODIGO_DEPARTAMENTO = VW_CE_ASISTENTES.CODIGO_DEPARTAMENTO AND uti.CODIGO_MUNICIPIO = VW_CE_ASISTENTES.CODIGO_MUNICIPIO \n"
                    + "  INNER JOIN VW_CE_PROCESADOS zap ON zap.CODIGO_DEPARTAMENTO = VW_CE_ASISTENTES.CODIGO_DEPARTAMENTO AND zap.CODIGO_MUNICIPIO = VW_CE_ASISTENTES.CODIGO_MUNICIPIO \n"
                    + "where uni.ID_DET_PROCESO_ADQ = " + procesos[0]
                    + "  and uti.ID_DET_PROCESO_ADQ = " + procesos[1]
                    + "  and zap.ID_DET_PROCESO_ADQ = " + procesos[2]
                    + "  and VW_CE_ASISTENTES.CODIGO_DEPARTAMENTO='" + codDepartamento + "'";
            Query q = em.createNativeQuery(query);
            return q.getResultList();
        } finally {
        }
    }

    public List findAvanceContratacionByDepartamento(DetalleProcesoAdq proceso, String idDep) {
        Query q;
        if (idDep.equals("00")) {
            q = em.createNativeQuery("select * from vw_total_tipo_empresa_adj where ID_DET_PROCESO_ADQ=?1");
            q.setParameter(1, proceso.getIdDetProcesoAdq());
        } else {
            q = em.createNativeQuery("SELECT DESCRIPCION_TIPO_EMP, SUM(SUBTOTAL) SUBTOTAL, SUM(TOTAL) TOTAL "
                    + "FROM VW_ADJ_TIPO_EMPRESA_RUBRO WHERE codigo_departamento =?1   "
                    + "AND id_det_proceso_adq= ?2 GROUP BY ID_TIPO_EMPRESA, DESCRIPCION_TIPO_EMP, id_det_proceso_adq");
            q.setParameter(1, idDep);
            q.setParameter(2, proceso.getIdDetProcesoAdq());
        }
        return q.getResultList();
    }

    public List<DetalleAdjudicacionEmpDto> resumenAdjProveedor(String nit, Integer idProceso) {
        Query q = em.createNativeQuery(Constantes.QUERY_PROVEEDOR_RESUMEN_ADJ_EMP, DetalleAdjudicacionEmpDto.class);
        q.setParameter(1, nit);
        q.setParameter(2, idProceso);
        return q.getResultList();
    }

    public List<DetalleAdjudicacionEmpDto> detalleAdjProveedor(String nit, Integer idProceso) {
        Query q = em.createNativeQuery(Constantes.QUERY_PROVEEDOR_DETALLE_ADJ_EMP, DetalleAdjudicacionEmpDto.class);
        q.setParameter(1, nit);
        q.setParameter(2, idProceso);
        return q.getResultList();
    }

    public List<DetalleOfertas> getDetalleAdjProveedor(BigDecimal idEmpresa, Integer idDetProceso) {
        List<DetalleOfertas> lst = new ArrayList<>();
        Query q = em.createQuery("SELECT c.idResolucionAdj.idParticipante FROM ContratosOrdenesCompras c where c.idResolucionAdj.idEstadoReserva.idEstadoReserva=2 and c.idResolucionAdj.idParticipante.idEmpresa.idEmpresa=:idEmpresa and c.idResolucionAdj.idParticipante.idOferta.idDetProcesoAdq.idDetProcesoAdq=:idDetProceso order by c.idResolucionAdj.idParticipante.idOferta.codigoEntidad.codigoDepartamento, c.idResolucionAdj.idParticipante.idOferta.codigoEntidad.codigoMunicipio, c.idResolucionAdj.idParticipante.idOferta.codigoEntidad.codigoEntidad", Participantes.class);
        q.setParameter("idEmpresa", idEmpresa);
        q.setParameter("idDetProceso", idDetProceso);
        if (!q.getResultList().isEmpty()) {
            for (Object par : q.getResultList()) {
                lst.addAll(((Participantes) par).getDetalleOfertasList());
            }
        }
        return lst;
    }

    public List<DetRubroMuestraInteres> findDetRubroMuestraInteresEntitiesByRubroAndEmpresa(DetalleProcesoAdq detProceso, Empresa empresa) {
        Query q = em.createQuery("SELECT d FROM DetRubroMuestraInteres d WHERE d.estadoEliminacion = 0 and d.idDetProcesoAdq.idProcesoAdq.idAnho=:detProceso and d.idEmpresa=:empresa", DetRubroMuestraInteres.class);
        q.setParameter("detProceso", detProceso.getIdProcesoAdq().getIdAnho());
        q.setParameter("empresa", empresa);

        return q.getResultList();
    }

    public List findAvanceSeguimientos(String codDepartamento, DetalleProcesoAdq proceso) {
        if (proceso != null) {
            String query = "SELECT (SELECT COUNT( VW_BUSQUEDA_CONTRATO.ID_CONTRATO) "
                    + "FROM VW_BUSQUEDA_CONTRATO "
                    + "WHERE ID_DET_PROCESO_ADQ =" + proceso.getIdDetProcesoAdq() + "AND ID_ESTADO_RESERVA IN (2, 5))  TOTAL,  (SELECT COUNT(VW_BUSQUEDA_CONTRATO.ID_CONTRATO) "
                    + "FROM VW_BUSQUEDA_CONTRATO INNER JOIN RECEPCION_BIENES_SERVICIOS ON VW_BUSQUEDA_CONTRATO.ID_CONTRATO = RECEPCION_BIENES_SERVICIOS.ID_CONTRATO "
                    + "WHERE ID_DET_PROCESO_ADQ =" + proceso.getIdDetProcesoAdq() + "AND ID_ESTADO_RESERVA IN (2, 5) "
                    + "AND RECEPCION_BIENES_SERVICIOS.ID_ESTADO_SEGUIMIENTO = 1) PROCESO, (SELECT COUNT(VW_BUSQUEDA_CONTRATO.ID_CONTRATO) "
                    + "FROM VW_BUSQUEDA_CONTRATO INNER JOIN RECEPCION_BIENES_SERVICIOS ON VW_BUSQUEDA_CONTRATO.ID_CONTRATO = RECEPCION_BIENES_SERVICIOS.ID_CONTRATO "
                    + "WHERE ID_DET_PROCESO_ADQ =" + proceso.getIdDetProcesoAdq() + "AND ID_ESTADO_RESERVA IN (2, 5) AND RECEPCION_BIENES_SERVICIOS.ID_ESTADO_SEGUIMIENTO = 2) FINALIZADO FROM DUAL";

            Query q = em.createNativeQuery(query);
            return q.getResultList();
        } else {
            return new ArrayList();
        }
    }

    public List findAvanceSeguimientosPorProveedor(String codDepartamento, DetalleProcesoAdq proceso) {
        try {
            if (proceso != null) {
                String query = "SELECT (SELECT COUNT(distinct VW_BUSQUEDA_CONTRATO.NUMERO_NIT) FROM VW_BUSQUEDA_CONTRATO "
                        + "WHERE ID_DET_PROCESO_ADQ =" + proceso.getIdDetProcesoAdq() + "AND ID_ESTADO_RESERVA IN (2, 5))  TOTAL, "
                        + "(SELECT COUNT(distinct VW_BUSQUEDA_CONTRATO.NUMERO_NIT) "
                        + "FROM VW_BUSQUEDA_CONTRATO INNER JOIN RECEPCION_BIENES_SERVICIOS "
                        + "ON VW_BUSQUEDA_CONTRATO.ID_CONTRATO = RECEPCION_BIENES_SERVICIOS.ID_CONTRATO "
                        + "WHERE ID_DET_PROCESO_ADQ =" + proceso.getIdDetProcesoAdq() + " AND ID_ESTADO_RESERVA IN (2, 5)  "
                        + "AND RECEPCION_BIENES_SERVICIOS.ID_ESTADO_SEGUIMIENTO = 1) PROCESO, (SELECT COUNT(distinct VW_BUSQUEDA_CONTRATO.NUMERO_NIT) "
                        + "FROM VW_BUSQUEDA_CONTRATO INNER JOIN RECEPCION_BIENES_SERVICIOS ON VW_BUSQUEDA_CONTRATO.ID_CONTRATO = RECEPCION_BIENES_SERVICIOS.ID_CONTRATO "
                        + "WHERE ID_DET_PROCESO_ADQ =" + proceso.getIdDetProcesoAdq() + " AND ID_ESTADO_RESERVA IN (2, 5) AND RECEPCION_BIENES_SERVICIOS.ID_ESTADO_SEGUIMIENTO = 2) FINALIZADO FROM DUAL";
                Query q = em.createNativeQuery(query);
                return q.getResultList();
            } else {
                return new ArrayList();
            }
        } finally {
        }
    }

    public List findAvanceSeguimientosPorCentroEducativo(String codDepartamento, DetalleProcesoAdq proceso) {
        if (proceso != null) {
            String query = "SELECT (SELECT COUNT(distinct VW_BUSQUEDA_CONTRATO.CODIGO_ENTIDAD) "
                    + "FROM VW_BUSQUEDA_CONTRATO WHERE ID_DET_PROCESO_ADQ = " + proceso.getIdDetProcesoAdq() + " "
                    + "AND ID_ESTADO_RESERVA IN (2, 5))  TOTAL, (SELECT COUNT(distinct VW_BUSQUEDA_CONTRATO.CODIGO_ENTIDAD) "
                    + "FROM VW_BUSQUEDA_CONTRATO INNER JOIN RECEPCION_BIENES_SERVICIOS "
                    + "ON VW_BUSQUEDA_CONTRATO.ID_CONTRATO = RECEPCION_BIENES_SERVICIOS.ID_CONTRATO "
                    + "WHERE ID_DET_PROCESO_ADQ = " + proceso.getIdDetProcesoAdq() + " AND ID_ESTADO_RESERVA IN (2, 5)  "
                    + "AND RECEPCION_BIENES_SERVICIOS.ID_ESTADO_SEGUIMIENTO = 1) "
                    + "PROCESO, (SELECT COUNT(distinct VW_BUSQUEDA_CONTRATO.CODIGO_ENTIDAD) "
                    + "FROM VW_BUSQUEDA_CONTRATO INNER JOIN RECEPCION_BIENES_SERVICIOS "
                    + "ON VW_BUSQUEDA_CONTRATO.ID_CONTRATO = RECEPCION_BIENES_SERVICIOS.ID_CONTRATO "
                    + "WHERE ID_DET_PROCESO_ADQ = " + proceso.getIdDetProcesoAdq() + " "
                    + "AND ID_ESTADO_RESERVA IN (2, 5) "
                    + "AND RECEPCION_BIENES_SERVICIOS.ID_ESTADO_SEGUIMIENTO = 2) FINALIZADO FROM DUAL";

            Query q = em.createNativeQuery(query);
            return q.getResultList();
        } else {
            return new ArrayList();
        }
    }

    public List<Bancos> getLstBancos() {
        Query q = em.createQuery("select b from Bancos b where b.idBanco=2 ", Bancos.class);
        return q.getResultList();
    }

    public List<CuentaBancaria> getLstCuentaByDepa(String codigoDepartamento) {
        Query q = em.createQuery("select c from CuentaBancaria c WHERE c.codigoDepartamento=:codDepa and c.activo=1", CuentaBancaria.class);
        q.setParameter("codDepa", codigoDepartamento);
        return q.getResultList();
    }

    public void generarRequerimiento(String anho, String codDep, Integer idBanco, String numCuenta, String concep, String compon, String line, String usu, String tieneCredito, BigDecimal montoTot, Integer idNivel, Integer idDepPro) {

        Query q = em.createNamedQuery("SP_GENERAR_REQ_PRE_CARGA");
        q.setParameter("param1", anho);
        q.setParameter("param2", codDep);
        q.setParameter("param3", idBanco);
        q.setParameter("param4", numCuenta);
        q.setParameter("param5", concep);
        q.setParameter("param6", compon);
        q.setParameter("param7", line);
        q.setParameter("param8", usu);
        q.setParameter("param9", tieneCredito);
        q.setParameter("param10", montoTot);
        q.setParameter("param11", idNivel);
        q.setParameter("param12", idDepPro);
        q.getResultList();
    }

    public List<RequerimientoFondos> getLstRequerimientos(String codigoDepartamento, Integer idDetProcesoAdq) {
        Query q = em.createQuery("SELECT r FROM RequerimientoFondos r WHERE r.idDetProcesoAdq.idDetProcesoAdq =:id and r.codigoDepartamento=:codDep and r.estadoEliminacion=0 ORDER BY r.numeroRequerimiento ASC", RequerimientoFondos.class);
        q.setParameter("id", idDetProcesoAdq);
        q.setParameter("codDep", codigoDepartamento);
        return q.getResultList();
    }

    public RequerimientoFondos getRequerimientoByNumero(String numeroRequerimiento, String codigoDepartamento, Integer idProcesoAdq) {
        String sql = "SELECT r FROM RequerimientoFondos r WHERE r.idDetProcesoAdq.idProcesoAdq.idProcesoAdq =:id %s and r.formatoRequerimiento=:numReq and r.estadoEliminacion = 0 ORDER BY r.idRequerimiento ASC";

        if (codigoDepartamento == null) {
            sql = String.format(sql, "");
        } else {
            sql = String.format(sql, "and r.codigoDepartamento=:codDep");
        }

        Query q = em.createQuery(sql, RequerimientoFondos.class);
        q.setParameter("id", idProcesoAdq);
        if (codigoDepartamento != null) {
            q.setParameter("codDep", codigoDepartamento);
        }
        q.setParameter("numReq", numeroRequerimiento);
        return q.getResultList().isEmpty() ? null : (RequerimientoFondos) q.getSingleResult();
    }

    public List<PlanillaPago> getLstPlanillaPagos(BigDecimal idRequerimiento) {
        Query q = em.createQuery("SELECT p FROM PlanillaPago p where p.estadoEliminacion = 0 and p.idRequerimiento.idRequerimiento=:idReq ORDER BY p.fechaInsercion ASC", PlanillaPago.class);
        q.setParameter("idReq", idRequerimiento);
        return q.getResultList();
    }

    public List<RequerimientoFondos> getLstRequerimientoFondos() {
        Query q = em.createQuery("SELECT r FROM RequerimientoFondos r", RequerimientoFondos.class);
        return q.getResultList();
    }

    public PlanillaPago guardar(String usuario, PlanillaPago planillaPago) {
        try {
            if (planillaPago.getIdPlanilla() == null) {
                planillaPago.setFechaInsercion(new Date());
                planillaPago.setUsuarioInsercion(usuario);
                planillaPago.setEstadoEliminacion((short) 0);
                em.persist(planillaPago);
            } else {
                planillaPago.setFechaModificacion(new Date());
                planillaPago.setUsuarioModificacion(usuario);
                em.merge(planillaPago);
            }
            return planillaPago;
        } catch (Exception e) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    public void guardarDetallePlanilla(List<DetallePlanilla> detallePlanillaLst) {
        for (DetallePlanilla detallePlanilla : detallePlanillaLst) {
            if (detallePlanilla.getIdDetallePlanilla() == null) {
                em.persist(detallePlanilla);
            } else {
                em.merge(detallePlanilla);
            }
        }
    }

    public List<ResumenRequerimientoDto> getLstResumenRequerimiento(String codigoDepartamento, int idDetProcesoAdq) {
        List<ResumenRequerimientoDto> lstResumen;
        Query q = em.createNamedQuery("PagoProve.QueryConsultaRequerimiento", ResumenRequerimientoDto.class);
        q.setParameter(1, codigoDepartamento);
        q.setParameter(2, idDetProcesoAdq);
        lstResumen = q.getResultList();
        return lstResumen;
    }

    /**
     * Devuele las diferentes entidades financieras que estan involucradas en un
     * requerimiento
     *
     * @param idRequerimiento
     * @return
     */
    public List<String> getEntidadesPorRequerimiento(BigDecimal idRequerimiento) {
        Query q = em.createNativeQuery(Constantes.QUERY_PAGOS_ENTIDADES_POR_REQUERIMIENTO);
        q.setParameter(1, idRequerimiento);
        return q.getResultList();
    }

    public List<VwRptProveedoresContratadosDto> getLstProveedoresHacienda(Integer idDetalleProcesoAdq, String codigoDepartamento) {
        String sql = "select rubro, count(ID_CONTRATO), DEPA_CE, DEPA_EMP, NUMERO_NIT, sum(monto) from VW_RPT_PROVEEDORES_HACIENDA where ID_DET_PROCESO_ADQ = ?1 ";
        List<VwRptProveedoresContratadosDto> lst = new ArrayList<>();

        if (!codigoDepartamento.equals("00")) {
            sql += " AND codigo_departamento = ?2";
        }
        Query q = em.createNativeQuery(sql + " group by rubro, DEPA_CE, DEPA_EMP, NUMERO_NIT order by DEPA_EMP");
        q.setParameter(1, idDetalleProcesoAdq);
        if (!codigoDepartamento.equals("00")) {
            q.setParameter(2, codigoDepartamento);
        }

        for (Object object : q.getResultList()) {
            Object[] datos = (Object[]) object;
            VwRptProveedoresContratadosDto vw = new VwRptProveedoresContratadosDto();
            vw.setRubro(datos[0].toString());
            vw.setCantidadContrato(new BigDecimal(datos[1].toString()));
            vw.setNombreDepartamentoCe(datos[2].toString());
            vw.setNombreDepartamentoEmp(datos[3].toString());
            vw.setNumeroNit(datos[4].toString());
            vw.setMontoContrato(new BigDecimal(datos[5].toString()));
            lst.add(vw);
        }
        return lst;
    }

    public List<VwRptProveedoresContratadosDto> getLstResumenContratacionByProcesoAndDepartamento(Integer idDetProceso, String codigoDepartamento) {
        try {
            Query q;
            if (codigoDepartamento.equals("00")) {
                q = em.createNativeQuery(Constantes.QUERY_CONTRATACION_RESUMEN_CONTRATACIONES, VwRptProveedoresContratadosDto.class);
            } else {
                q = em.createNamedQuery("Ejecucion.ResumenContratacionesByProcesoAndDepa", VwRptProveedoresContratadosDto.class);
            }

            q.setParameter(1, idDetProceso);
            q.setParameter(2, codigoDepartamento);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Metodo utilizado en la modificativa de contratos
     *
     * @param numItem
     * @param rubro
     * @return
     */
    public HashMap<String, Object> validarItemProveedor(String numItem, Integer rubro) {
        HashMap param = new HashMap();
        String error = "";
        CatalogoProducto item = null;
        NivelEducativo nivel = null;

        if (numItem != null && !numItem.isEmpty()) {
            switch (rubro) {
                case 1: //UNIFORMES
                case 4:
                case 5:
                    switch (Integer.parseInt(numItem)) {
                        case 0:
                            break;
                        case 1:
                        case 6:
                        case 10:
                            item = findProducto("30");
                            switch (Integer.parseInt(numItem)) {
                                case 1:
                                    nivel = findNivelEducativo("1");
                                    break;
                                case 6:
                                    nivel = findNivelEducativo("2");
                                    break;
                                default:
                                    nivel = findNivelEducativo("6");
                                    break;
                            }
                            break;
                        case 2:
                        case 7:
                        case 11:
                            item = findProducto("44");
                            switch (Integer.parseInt(numItem)) {
                                case 2:
                                    nivel = findNivelEducativo("1");
                                    break;
                                case 7:
                                    nivel = findNivelEducativo("2");
                                    break;
                                default:
                                    nivel = findNivelEducativo("6");
                                    break;
                            }
                            break;
                        case 3:
                        case 8:
                        case 12:
                            item = findProducto("29");
                            switch (Integer.parseInt(numItem)) {
                                case 3:
                                    nivel = findNivelEducativo("1");
                                    break;
                                case 8:
                                    nivel = findNivelEducativo("2");
                                    break;
                                default:
                                    nivel = findNivelEducativo("6");
                                    break;
                            }
                            break;
                        case 4:
                            item = findProducto("31");
                            nivel = findNivelEducativo("1");
                            break;
                        case 5:
                            item = findProducto("34");
                            nivel = findNivelEducativo("1");
                            break;
                        case 9:
                            item = findProducto("34");
                            nivel = findNivelEducativo("2");
                            break;
                        case 13:
                            item = findProducto("34");
                            switch (Integer.parseInt(numItem)) {
                                case 5:
                                    nivel = findNivelEducativo("1");
                                    break;
                                case 9:
                                    nivel = findNivelEducativo("2");
                                    break;
                                default:
                                    nivel = findNivelEducativo("6");
                                    break;
                            }
                            break;
                        default:
                            error = "El item ingresado no es válido.";
                            break;
                    }
                    break;
                case 2: //UTILES
                    item = findProducto("54");

                    switch (Integer.parseInt(numItem)) {
                        case 1:
                            nivel = findNivelEducativo("1");
                            break;
                        case 2:
                            nivel = findNivelEducativo("3");
                            break;
                        case 3:
                            nivel = findNivelEducativo("4");
                            break;
                        case 4:
                            nivel = findNivelEducativo("5");
                            break;
                        case 5:
                            nivel = findNivelEducativo("6");
                            break;
                        default:
                            error = "El item ingresado no es válido.";
                            break;
                    }
                    break;
                case 3: //ZAPATOS
                    switch (Integer.parseInt(numItem)) {
                        case 1:
                            item = findProducto("21");
                            nivel = findNivelEducativo("1");
                            break;
                        case 2:
                            item = findProducto("43");
                            nivel = findNivelEducativo("1");
                            break;
                        case 3:
                            item = findProducto("21");
                            nivel = findNivelEducativo("3");
                            break;
                        case 4:
                            item = findProducto("43");
                            nivel = findNivelEducativo("3");
                            break;
                        case 5:
                            item = findProducto("21");
                            nivel = findNivelEducativo("4");
                            break;
                        case 6:
                            item = findProducto("43");
                            nivel = findNivelEducativo("4");
                            break;
                        case 7:
                            item = findProducto("21");
                            nivel = findNivelEducativo("5");
                            break;
                        case 8:
                            item = findProducto("43");
                            nivel = findNivelEducativo("5");
                            break;
                        case 9:
                            item = findProducto("21");
                            nivel = findNivelEducativo("6");
                            break;
                        case 10:
                            item = findProducto("43");
                            nivel = findNivelEducativo("6");
                            break;
                        default:
                            error = "El item ingresado no es válido.";
                            break;
                    }
                    break;
            }
        }

        if (error.isEmpty()) {
            param.put("item", item);
            param.put("nivel", nivel);
        } else {
            param.put("error", error);
        }

        return param;
    }

    public EntidadFinanciera getEntidadByNombre(String nombre) {
        Query q = em.createQuery("SELECT e FROM EntidadFinanciera e WHERE e.nombreEntFinan=:nombre", EntidadFinanciera.class);
        q.setParameter("nombre", nombre);
        return (EntidadFinanciera) q.getSingleResult();
    }

    public List<DetalleContratacionPorItemDto> getLstDetalleContratacionPorItem(Integer idDetalleProcesoAdq) {
        Query q = em.createNamedQuery("Contratacion.DetalleContratacionPorItemDto", DetalleContratacionPorItemDto.class);
        q.setParameter(1, idDetalleProcesoAdq);
        return q.getResultList();
    }
}
