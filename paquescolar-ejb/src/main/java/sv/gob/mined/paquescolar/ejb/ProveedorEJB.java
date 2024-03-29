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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import sv.gob.mined.paquescolar.model.Bancos;
import sv.gob.mined.paquescolar.model.CapaDistribucionAcre;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.CatalogoProducto;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
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
import sv.gob.mined.paquescolar.model.EmpresaCodigoSeg;
import sv.gob.mined.paquescolar.model.EmpresaNoItem;
import sv.gob.mined.paquescolar.model.EmpresaPreciosRef;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.OfertaBienesServicios;
import sv.gob.mined.paquescolar.model.Participantes;
import sv.gob.mined.paquescolar.model.Persona;
import sv.gob.mined.paquescolar.model.PlanillaPago;
import sv.gob.mined.paquescolar.model.PlanillaPagoCheque;
import sv.gob.mined.paquescolar.model.PorcentajeEvaluacion;
import sv.gob.mined.paquescolar.model.PreciosRefRubroEmp;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.RequerimientoFondos;
import sv.gob.mined.paquescolar.model.TipoPersoneria;
import sv.gob.mined.paquescolar.model.TipoUsuario;
import sv.gob.mined.paquescolar.model.TransferenciaRequerimiento;
import sv.gob.mined.paquescolar.model.Usuario;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.ResumenRequerimientoDto;
import sv.gob.mined.paquescolar.model.pojos.proveedor.DetalleAdjudicacionEmpDto;
import sv.gob.mined.paquescolar.model.pojos.VwRptProveedoresContratadosDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.CantidadPorNivelDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.DetalleContratacionPorItemDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.PrecioReferenciaEmpresaDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.ProveedorDisponibleDto;
import sv.gob.mined.paquescolar.model.pojos.proveedor.NotificacionOfertaProvDto;
import sv.gob.mined.paquescolar.model.pojos.proveedor.SeguimientoIngresoProveDto;
import sv.gob.mined.paquescolar.model.view.DatosPreliminarRequerimiento;
import sv.gob.mined.paquescolar.util.Constantes;
import sv.gob.mined.paquescolar.util.RC4Crypter;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class ProveedorEJB {

    @EJB
    private EMailEJB eMailEJB;
    @EJB
    private ReportesEJB reportesEJB;

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

        q.getResultList().forEach((object) -> {
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
        });
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

    public Empresa findEmpresaByNit(String nit, Boolean empresa) {
        Query q = em.createQuery("SELECT e FROM Empresa e WHERE e" + (empresa ? "" : ".idPersona") + ".numeroNit=:nit and e.estadoEliminacion=0", Empresa.class);
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

    public DetRubroMuestraInteres findDetRubroByAnhoAndRubro(BigDecimal idAnho, BigDecimal idEmpresa) {
        Query q = em.createQuery("SELECT d FROM DetRubroMuestraInteres d WHERE d.idEmpresa.idEmpresa=:idEmpresa and d.idAnho.idAnho=:pIdAnho and d.estadoEliminacion=0 ORDER BY d.idMuestraInteres", DetRubroMuestraInteres.class);
        q.setParameter("idEmpresa", idEmpresa);
        q.setParameter("pIdAnho", idAnho);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (DetRubroMuestraInteres) q.getResultList().get(0);
        }
    }

    public <T extends Object> T findDetProveedor(DetRubroMuestraInteres detRubro, Integer idPro, Class clase) {
        Query q = em.createQuery("SELECT d FROM " + clase.getSimpleName() + " d WHERE d.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro and d.idMuestraInteres.idAnho.idAnho=:pIdAnho and d.idMuestraInteres.idEmpresa=:idEmpresa and d.estadoEliminacion=0 and d.idMuestraInteres.estadoEliminacion=0 " + (clase.equals(CapaInstPorRubro.class) ? " and d.idProcesoAdq.idProcesoAdq=:pIdPro " : "") + " ORDER BY d.idMuestraInteres", clase);
        q.setParameter("pIdRubro", detRubro.getIdRubroInteres().getIdRubroInteres());
        q.setParameter("pIdAnho", detRubro.getIdAnho().getIdAnho());
        q.setParameter("idEmpresa", detRubro.getIdEmpresa());
        if (clase.equals(CapaInstPorRubro.class)) {
            q.setParameter("pIdPro", idPro);
        }

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
            Query query = em.createQuery("UPDATE CapaInstPorRubro c SET c.capacidadAcreditada = :capaCalificada, c.capacidadPropuesta=:capaPropuesta WHERE c.idMuestraInteres.idEmpresa.idEmpresa = :idEmpresa and c.idMuestraInteres.idAnho.idAnho = :pIdAnho and c.idMuestraInteres.idRubroInteres.idRubroInteres = :pIdRubro");
            query.setParameter("capaCalificada", capaInstPorRubro.getCapacidadAcreditada());
            query.setParameter("capaPropuesta", capaInstPorRubro.getCapacidadPropuesta());
            query.setParameter("idEmpresa", capaInstPorRubro.getIdMuestraInteres().getIdEmpresa().getIdEmpresa());
            query.setParameter("pIdAnho", capaInstPorRubro.getIdMuestraInteres().getIdAnho().getIdAnho());
            query.setParameter("pIdRubro", capaInstPorRubro.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres());

            query.executeUpdate();

            query = em.createQuery("UPDATE CapaDistribucionAcre c SET c.codigoDepartamento = :codigoDepartamento WHERE c.idMuestraInteres.idEmpresa.idEmpresa = :idEmpresa and c.idMuestraInteres.idAnho.idAnho=:pIdAnho and c.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro");
            query.setParameter("codigoDepartamento", capaDistribucionAcre.getCodigoDepartamento());
            query.setParameter("idEmpresa", capaInstPorRubro.getIdMuestraInteres().getIdEmpresa().getIdEmpresa());
            query.setParameter("pIdAnho", capaInstPorRubro.getIdMuestraInteres().getIdAnho().getIdAnho());
            query.setParameter("pIdRubro", capaInstPorRubro.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres());

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
            lstDetalleOfertas.forEach((detalleOfertas) -> {
                if (detalleOfertas.getIdDetalleOfe() == null) {
                    em.persist(detalleOfertas);
                } else {
                    detalleOfertas = em.merge(detalleOfertas);
                }
            });
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

    public List<PreciosRefRubroEmp> findPreciosRefRubroEmpRubro(Empresa idEmpresa, BigDecimal idRubro, BigDecimal idAnho) {
        Query q = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.idMuestraInteres.idEmpresa.idEmpresa=:pIdEmpresa and (p.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro AND p.idMuestraInteres.idAnho.idAnho=:pIdAnho) and p.estadoEliminacion=0 ORDER BY FUNC('TO_NUMBER', p.noItem)", PreciosRefRubroEmp.class);
        q.setParameter("pIdEmpresa", idEmpresa.getIdEmpresa());
        q.setParameter("pIdRubro", idRubro);
        q.setParameter("pIdAnho", idAnho);
        return q.getResultList();
    }

    public Boolean isDepaCalificado(Empresa empresa, String codigoDepartamento, DetalleProcesoAdq detalleProceso) {
        Boolean valor = false;
        Query q;
        q = em.createQuery("SELECT c.codigoDepartamento FROM CapaDistribucionAcre c WHERE c.idMuestraInteres.idEmpresa=:idEmpresa and (c.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro and c.idMuestraInteres.idAnho.idAnho=:pIdAnho) and c.estadoEliminacion=0");
        q.setParameter("idEmpresa", empresa);
        q.setParameter("pIdRubro", detalleProceso.getIdRubroAdq().getIdRubroInteres());
        q.setParameter("pIdAnho", detalleProceso.getIdProcesoAdq().getIdAnho().getIdAnho());
        /*if (detalleProceso.getIdRubroAdq().getDescripcionRubro().contains("2do")) {
            q.setParameter("idRubro", new BigDecimal(4));
        } else {
            q.setParameter("idRubro", detalleProceso.getIdRubroAdq().getIdRubroInteres());
        }*/

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
        Query q = em.createQuery("SELECT distinct d.idProducto FROM DetCapaSegunRubro d WHERE d.idMuestraInteres.estadoEliminacion=0 and d.idMuestraInteres.idEmpresa=:empresa and d.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro and d.idMuestraInteres.idAnho.idAnho=:pIdAnho and d.estadoEliminacion=0", CatalogoProducto.class);
        q.setParameter("empresa", empresa);
        q.setParameter("pIdRubro", detProcesoAdq.getIdRubroAdq().getIdRubroInteres());
        q.setParameter("pIdAnho", detProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho());

        return q.getResultList();
    }

    public List<DetalleOfertas> findDetalleOfertas(Participantes participante, Boolean libros) {
        Query q = em.createQuery("SELECT d FROM DetalleOfertas d WHERE d.estadoEliminacion=0 and d.idParticipante=:participante and d.idProducto.idProducto " + (libros ? "" : "not") + " in (1) ORDER BY FUNC('TO_NUMBER', d.noItem)", CatalogoProducto.class);
        q.setParameter("participante", participante);

        return q.getResultList();
    }

    /**
     * PROCESO 2020
     *
     * @param detProcesoAdq
     * @param codigoEntidad
     * @param codDepartamento
     * @param codMunicipio
     * @param codCanton
     * @param idMunicipio
     * @param idMunicipios
     * @param municipioIgual
     * @param cantidad
     * @param mapItems
     * @return
     */
    public List<ProveedorDisponibleDto> getLstCapaEmpPorNitOrRazonSocialAndRubroAndMunicipioCe(DetalleProcesoAdq detProcesoAdq,
            String codigoEntidad, String codDepartamento, String codMunicipio, String codCanton,
            Integer idMunicipio, String idMunicipios, Boolean municipioIgual, BigInteger cantidad, HashMap<String, String> mapItems) {
        Integer idDetTemp;
        List<ProveedorDisponibleDto> lstCapa = new ArrayList<>();

        if (detProcesoAdq.getIdProcesoAdq().getPadreIdProcesoAdq() != null) {
            idDetTemp = getIdDetProcesoPadre(detProcesoAdq);
        } else {
            idDetTemp = detProcesoAdq.getIdDetProcesoAdq();
        }

        Integer idDetT1 = getIdDetProcesoPadre(detProcesoAdq);

        if (idDetT1 == 41) {
            idDetT1 = 40;
            idDetTemp = 40;
        }

        Query q = em.createNativeQuery(findLstIdEmpresa(codDepartamento, codMunicipio, codCanton, idMunicipio, idMunicipios, detProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue(), detProcesoAdq, idDetTemp,
                municipioIgual, cantidad.intValue(), mapItems.get("noItemSeparados"), mapItems.get("noItems")), ProveedorDisponibleDto.class);

        lstCapa.addAll(q.getResultList());

        return lstCapa;
    }

    private List<PorcentajeEvaluacion> getPorcentajesEvaluacionByAnho(BigDecimal idAnho, BigInteger idRubro) {
        Query q = em.createQuery("SELECT p FROM PorcentajeEvaluacion p WHERE p.idAnho=:idAnho AND p.idRubroInteres=:idRubro", PorcentajeEvaluacion.class);
        q.setParameter("idAnho", idAnho.longValue());
        q.setParameter("idRubro", idRubro);
        return q.getResultList();
    }

    /**
     * PROCESOS 2019 HACIA ATRAS
     *
     * @param detProcesoAdq
     * @param codigoEntidad
     * @param municipioIgual
     * @param byCapacidad
     * @param cantidad
     * @return
     */
    public List<CapaInstPorRubro> getLstCapaEmpPorNitOrRazonSocialAndRubroAndMunicipioCe(DetalleProcesoAdq detProcesoAdq, String codigoEntidad,
            Boolean municipioIgual, Boolean byCapacidad, BigInteger cantidad) {
        String codMunicipio;
        String codDepartamento;
        DetalleProcesoAdq detTemp = null;
        List<CapaInstPorRubro> lstCapa = new ArrayList<>();
        Query q = em.createNativeQuery("select codigo_municipio, codigo_departamento from vw_catalogo_entidad_educativa WHERE codigo_entidad = '" + codigoEntidad + "'");
        List lst = q.getResultList();
        codMunicipio = ((Object[]) lst.get(0))[0].toString();
        codDepartamento = ((Object[]) lst.get(0))[1].toString();
        if (detProcesoAdq.getIdProcesoAdq().getPadreIdProcesoAdq() != null) {
            for (DetalleProcesoAdq object : detProcesoAdq.getIdProcesoAdq().getPadreIdProcesoAdq().getDetalleProcesoAdqList()) {
                if (object.getIdRubroAdq().getIdRubroInteres().compareTo(detProcesoAdq.getIdRubroAdq().getIdRubroInteres()) == 0) {
                    detTemp = object;
                    break;
                }
            }
        } else {
            detTemp = detProcesoAdq;
        }
        BigDecimal idRubro, idAnho;
        if (detTemp.getIdRubroAdq().getDescripcionRubro().contains("2do")) {
            idRubro = new BigDecimal(4);
        } else {
            idRubro = detTemp.getIdRubroAdq().getIdRubroInteres();
        }
        idAnho = detTemp.getIdProcesoAdq().getIdAnho().getIdAnho();
        //dbms_random.value
        q = em.createNativeQuery(findLstIdEmpresa(codMunicipio, codDepartamento, idRubro, idAnho,
                municipioIgual, byCapacidad, cantidad.intValue()), CapaInstPorRubro.class);
        q.setParameter(1, codigoEntidad);
        q.setParameter(2, detProcesoAdq.getIdProcesoAdq().getIdProcesoAdq());
        q.setParameter(3, detProcesoAdq.getIdDetProcesoAdq());
        q.setParameter(2, getProcesoAdqPadre(detProcesoAdq.getIdProcesoAdq()));
        q.setParameter(3, getProcesoAdqPadre(detProcesoAdq.getIdProcesoAdq()));
        lstCapa.addAll(q.getResultList());
        return lstCapa;
    }

    private Integer getProcesoAdqPadre(ProcesoAdquisicion proAdq) {
        if (proAdq.getPadreIdProcesoAdq() != null) {
            return getProcesoAdqPadre(proAdq.getPadreIdProcesoAdq());
        } else {
            return proAdq.getIdProcesoAdq();
        }
    }

    private Integer getIdDetProcesoPadre(DetalleProcesoAdq detalleProcesoAdq) {
        if (detalleProcesoAdq.getIdProcesoAdq().getPadreIdProcesoAdq() != null) {
            for (DetalleProcesoAdq det : detalleProcesoAdq.getIdProcesoAdq().getPadreIdProcesoAdq().getDetalleProcesoAdqList()) {
                if (det.getIdRubroAdq().getIdRubroInteres().intValue() == detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                    return det.getIdDetProcesoAdq();
                }
            }
        }
        return detalleProcesoAdq.getIdDetProcesoAdq();
    }

    /**
     * PROCESO 2020
     *
     * @param codDep
     * @param codMun
     * @param codCanton
     * @param idMunicipio
     * @param idMunicipios
     * @param idRubro
     * @param idDetProcesoAdq
     * @param idDetProcesoAdqPrecio
     * @param municipioIgual
     * @param cantidad
     * @param noItemSeparados
     * @param noItems
     * @return -
     */
    private String findLstIdEmpresa(String codDep, String codMun, String codCanton, Integer idMunicipio, String idMunicipios, Integer idRubro, DetalleProcesoAdq idDetProcesoAdq, Integer idDetProcesoAdqPrecio,
            Boolean municipioIgual, Integer cantidad, String noItemSeparados, String noItems) {
        BigDecimal idAnho = idDetProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho();

        List<PorcentajeEvaluacion> lstPorcentajes = getPorcentajesEvaluacionByAnho(idAnho, new BigInteger(idRubro.toString()));
        BigDecimal porPrecio = BigDecimal.ZERO;
        BigDecimal porUbicacionLocal = BigDecimal.ZERO;
        BigDecimal porMunAledanhos = BigDecimal.ZERO;
        BigDecimal porUbicacionOtros = BigDecimal.ZERO;

        BigDecimal porCapacidadCompleta = BigDecimal.ZERO;
        BigDecimal porCapacidadParcial = BigDecimal.ZERO;

        for (PorcentajeEvaluacion lstPorcentaje : lstPorcentajes) {
            switch (idRubro) {
                case 4:
                case 5:
                    break;
                case 2:
                    switch (lstPorcentaje.getIdCriterio().intValue()) {
                        case 1:
                            porPrecio = lstPorcentaje.getPorcentaje();
                        case 3:
                            porUbicacionLocal = lstPorcentaje.getPorcentaje();
                            break;
                        case 4:
                            porMunAledanhos = lstPorcentaje.getPorcentaje();
                            break;
                        case 5:
                            porUbicacionOtros = lstPorcentaje.getPorcentaje();
                            break;
                        case 7:
                            porCapacidadCompleta = lstPorcentaje.getPorcentaje();
                            break;
                        case 8:
                            porCapacidadParcial = lstPorcentaje.getPorcentaje();
                            break;
                    }
                    break;
                case 3:
                    switch (lstPorcentaje.getIdCriterio().intValue()) {
                        case 1:
                            porPrecio = lstPorcentaje.getPorcentaje();
                        case 3:
                            porUbicacionLocal = lstPorcentaje.getPorcentaje();
                            break;
                        case 4:
                            porUbicacionOtros = lstPorcentaje.getPorcentaje();
                            break;
                        case 6:
                            porCapacidadCompleta = lstPorcentaje.getPorcentaje();
                            break;
                        case 7:
                            porCapacidadParcial = lstPorcentaje.getPorcentaje();
                            break;
                    }
                    break;
            }
        }

        String sql = "select \n"
                + "    rownum                  as idRow,\n"
                + "    tb1.id_empresa          as idEmpresa,\n"
                + "    razon_social            as razonSocial,\n"
                + "    distribuidor,\n"
                + "    nombre_municipio        as nombreMunicipio,\n"
                + "    nombre_departamento     as nombreDepartamento,\n"
                + "    precio_promedio         as puAvg,\n"
                + "    capacidad_acreditada    as capacidadAcreditada,\n"
                + "    capacidad_adjudicada    as capacidadAdjudicada,\n"
                + "    porcentaje_precio       as porcentajePrecio,\n"
                + "    porcentaje_geo          as porcentajeGeo,\n"
                + "    porcentaje_capacidad_i  as porcentajeCapacidadItem,\n"
                + "    porcentaje_capacidad    as porcentajeCapacidad,\n"
                + "    porcentaje_precio+porcentaje_geo+porcentaje_capacidad_i+porcentaje_capacidad" + ((idAnho.intValue() == 11 && idRubro == 3) ? "+porcentaje_nota" : "") + " as porcentajeEvaluacion,\n"
                + "    ((capacidad_adjudicada*100)/capacidad_acreditada) porcentajeAdjudicacion\n"
                + ((idAnho.intValue() == 11 && idRubro == 3) ? " ,porcentaje_nota as porcentajeNota " : "")
                + "from (select \n"
                + "        emp.id_empresa,\n"
                + "        det.id_muestra_interes,\n"
                + "        emp.razon_social,\n"
                + "        nvl(emp.distribuidor,0)      as distribuidor,\n"
                + "        mun_e.nombre_municipio,\n"
                + "        dep_e.nombre_departamento,\n"
                + "        tbl.precio_promedio,\n"
                + "        round((((min( distinct tbl.precio_promedio) OVER (order by tbl.precio_promedio))*100)/tbl.precio_promedio)*" + (idAnho.intValue() > 9 ? "0.4" : (porPrecio.intValue() / 100)) + ",2) as porcentaje_precio,\n"
                + "        mun_e.id_municipio,\n"
                + "        mun_e.codigo_departamento,\n"
                + "        emp.codigo_canton,\n"
                + "        " + (idAnho.intValue() > 8 ? "0" : "tbl.porcentaje_capacidad") + " as porcentaje_capacidad_i,\n"
                + "        " + getParteSelectUbicacion(idRubro, codCanton, idMunicipio, codDep, idMunicipios, porUbicacionLocal, porMunAledanhos, porUbicacionOtros) + "\n"
                + ((idAnho.intValue() == 11 && idRubro == 3) ? " ,(nota.nota_zapato_nino+nota.nota_zapato_nina) porcentaje_nota " : "")
                + "    from det_rubro_muestra_interes det\n"
                + "        inner join empresa emp                  on emp.id_empresa = det.id_empresa\n"
                + ((idAnho.intValue() == 11 && idRubro == 3) ? " inner join nota_pruebas_zapatero nota on nota.id_muestra_interes = det.id_muestra_interes " : "")
                + "        inner join municipio mun_e              on mun_e.id_municipio = emp.id_municipio\n"
                + "        inner join departamento dep_e           on mun_e.codigo_departamento = dep_e.codigo_departamento\n"
                + "        inner join (select pre.id_muestra_interes,pre.id_empresa, round(avg(precio_referencia),3) precio_promedio,((count(pre.id_empresa)*100)/" + noItems.split(",").length + ")*" + getPorcentajePorItems(idRubro) + " porcentaje_capacidad\n"
                + "                    from precios_ref_rubro_emp pre\n"
                + "                        inner join empresa_no_item emp on emp.id_muestra_interes = pre.id_muestra_interes\n"
                + "                        inner join det_rubro_muestra_interes det on emp.id_muestra_interes = det.id_muestra_interes\n"
                + "                    where " + (municipioIgual ? " (" + noItemSeparados + ") and " : "") + "\n"
                + "                        no_item in (" + noItems + ") and pre.estado_eliminacion = 0 and\n"
                + "                        det.id_rubro_interes =  " + idRubro + " and det.id_anho = " + idAnho + " and pre.precio_referencia > 0 \n"
                + "                    group by pre.id_muestra_interes, pre.id_empresa) tbl on det.id_muestra_interes = tbl.id_muestra_interes\n"
                + "    where \n"
                + "        det.estado_eliminacion = 0) tb1\n"
                + "    inner join (select \n"
                + "                    cip.id_muestra_interes,\n"
                + "                    cip.capacidad_acreditada,\n"
                + "                    cip.capacidad_adjudicada,\n"
                + "                    case when (cip.capacidad_acreditada-cip.capacidad_adjudicada) >= " + cantidad + " then " + (idRubro == 4 ? "12.50" : (idRubro == 5 ? "12.50" : porCapacidadCompleta.toString())) + " \n"
                + "                    else (((cip.capacidad_acreditada-cip.capacidad_adjudicada)*100)/" + cantidad + ") * " + (idRubro == 4 ? "0.1250" : (idRubro == 5 ? "0.1250" : porCapacidadParcial.divide(BigDecimal.valueOf(100)).toString())) + " \n"
                + "                    end porcentaje_capacidad\n"
                + "                from \n"
                + "                    capa_inst_por_rubro cip\n"
                + "                    inner join capa_distribucion_acre cda on cda.id_muestra_interes = cip.id_muestra_interes and cda.id_capa_distribucion in (select id_capa_distribucion from dis_municipio_interes dis inner join municipio mun on mun.id_municipio = dis.id_municipio where dis.estado_eliminacion = 0 and dis.id_municipio = " + idMunicipio + " and  dis.id_capa_distribucion = cda.id_capa_distribucion and " + (municipioIgual ? "mun.codigo_municipio ='" + codMun + "' and mun.codigo_departamento = '" + codDep + "'" : " mun.id_municipio in (" + (idMunicipios.isEmpty() ? idMunicipio : idMunicipios + "," + idMunicipio) + ")") + ")\n"
                + "                    inner join dis_municipio_interes mun on mun.id_capa_distribucion = cda.id_capa_distribucion and mun.id_municipio =  " + idMunicipio + "\n"
                + "                where \n"
                + "                    cip.capacidad_acreditada>0 and cip.id_proceso_adq = " + idDetProcesoAdq.getIdProcesoAdq().getIdProcesoAdq() + ") tb2 on tb1.id_muestra_interes = tb2.id_muestra_interes\n"
                + "where \n"
                + "    id_municipio " + (municipioIgual ? "=" : "<>") + " (select id_municipio from municipio where codigo_municipio = '" + codMun + "' and codigo_departamento = '" + codDep + "') \n"
                + "    " + (idRubro == 2 ? " and codigo_departamento = '" + codDep + "' " : "")
                + "order by\n"
                + "    (porcentaje_precio+porcentaje_geo+porcentaje_capacidad_i+porcentaje_capacidad" + ((idAnho.intValue() == 11 && idRubro == 3) ? "+porcentaje_nota" : "") + ") desc,((capacidad_adjudicada*100)/capacidad_acreditada) asc";

        return sql;
    }

    /**
     * PROCESOS 2019 HACIA ATRAS
     *
     * @param codMun
     * @param codDep
     * @param idRubro
     * @param idAnho
     * @param municipioIgual
     * @param byCapacidad
     * @param cantidad
     * @return
     */
    private String findLstIdEmpresa(String codMun, String codDep, BigDecimal idRubro, BigDecimal idAnho,
            Boolean municipioIgual, Boolean byCapacidad, Integer cantidad) {
        String sql = "select distinct \n"
                + "     cip.ID_CAP_INST_RUBRO,\n"
                + "     cip.ID_MUESTRA_INTERES,\n"
                + "     cip.CAPACIDAD_ACREDITADA,\n"
                + "     cip.CAPACIDAD_ADJUDICADA,\n"
                + "     cip.USUARIO_INSERCION,\n"
                + "     cip.FECHA_INSERCION,\n"
                + "     cip.USUARIO_MODIFICACION,\n"
                + "     cip.FECHA_MODIFICACION,\n"
                + "     cip.FECHA_ELIMINACION,\n"
                + "     cip.ESTADO_ELIMINACION,\n"
                + "     vw.pu_avg\n"
                + " from \n"
                + "     det_rubro_muestra_interes det\n"
                + "     inner join empresa emp                  on emp.id_empresa = det.id_empresa\n"
                + "     inner join municipio mun_e              on mun_e.id_municipio = emp.id_municipio\n"
                + "     inner join detalle_proceso_adq dpa      on dpa.id_det_proceso_adq = det.id_det_proceso_adq\n"
                + "     inner join proceso_adquisicion pa       on pa.id_proceso_adq = dpa.id_proceso_adq\n"
                + "     inner join capa_distribucion_acre cap   on det.id_muestra_interes = cap.id_muestra_interes\n"
                + "     inner join dis_municipio_interes dis    on dis.id_capa_distribucion = cap.id_capa_distribucion\n"
                + "     inner join municipio mun                on mun.id_municipio = dis.id_municipio\n"
                + "     inner join departamento dep             on mun.codigo_departamento = dep.codigo_departamento\n"
                + "     inner join capa_inst_por_rubro  cip     on cip.id_muestra_interes = cap.id_muestra_interes\n"
                + "     inner join precios_ref_rubro_emp pre    on emp.id_empresa = pre.id_empresa\n"
                + "     inner join vw_sub_empresa_avg_pu vw     on vw.id_empresa = emp.id_empresa and vw.id_det_proceso_adq = dpa.id_det_proceso_adq and pre.id_empresa = vw.id_empresa\n"
                + " where \n"
                + "     mun.codigo_municipio = '" + codMun + "' and\n"
                + "     dep.codigo_departamento = '" + codDep + "' and\n"
                + "     dis.estado_eliminacion = 0 and\n"
                + "     dpa.id_rubro_adq = " + idRubro + " and\n"
                + "     pa.id_anho = " + idAnho + " and\n"
                + "     det.estado_eliminacion = 0 and\n"
                + "     mun_e.codigo_municipio " + (municipioIgual ? "=" : "<>") + "'" + codMun + "' and\n"
                + "     pre.id_nivel_educativo in (select id_nivel from vw_sub_niveles_by_cod_pro where codigo_entidad = ?1 and id_proceso_adq = ?2) and \n"
                + "     pre.id_det_proceso_adq = ?3\n"
                + "     pre.id_det_proceso_adq in (select id_det_proceso_adq from detalle_proceso_Adq where id_proceso_adq = ?3) \n"
                + "     " + (byCapacidad ? " and (cip.CAPACIDAD_ACREDITADA - cip.CAPACIDAD_ADJUDICADA) >= " + cantidad : "")
                + " order by vw.pu_avg asc";
        //+ "    mun_e.codigo_municipio " + (municipioIgual ? "=" : "<>") + "'" + codMun + "'";
        return sql;
    }

    public List<ProveedorDisponibleDto> getLstProveedorPorcentajeEval(OfertaBienesServicios oferta) {
        String sql = "select \n"
                + "    rownum                   as idRow,\n"
                + "    emp.razon_social         as razonSocial,\n"
                + "    par.porcentaje_precio    as porcentajePrecio,\n"
                + "    par.porcentaje_geo       as porcentajeGeo,\n"
                + "    par.porcentaje_capacidad as porcentajeCapacidad,\n"
                + "    par.porcentaje_precio+par.porcentaje_geo+par.porcentaje_capacidad+nvl((npz.nota_zapato_nina + npz.nota_zapato_nino),0) as porcentajeEvaluacion,\n"
                + "    nvl((npz.nota_zapato_nina + npz.nota_zapato_nino),0) as porcentajeCalificacion \n"
                + "from participantes par \n"
                + "    inner join empresa emp on par.id_empresa = emp.id_empresa\n"
                + "    inner join oferta_bienes_servicios ofe on par.id_oferta = ofe.id_oferta\n"
                + "    inner join det_rubro_muestra_interes det on emp.id_empresa = det.id_empresa\n"
                + "    left join nota_pruebas_zapatero npz on npz.id_muestra_interes = det.id_muestra_interes\n"
                + "where \n"
                + "    par.estado_eliminacion = 0 and\n"
                + "    ofe.estado_eliminacion = 0 and\n"
                + "    ofe.id_oferta =" + oferta.getIdOferta() + " and\n"
                + "    det.id_anho = " + oferta.getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getIdAnho() + " and\n"
                + "    det.id_rubro_interes = " + oferta.getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres() + "\n"
                + "order by\n"
                + "    (par.porcentaje_precio+par.porcentaje_geo+par.porcentaje_capacidad+nvl((npz.nota_zapato_nina + npz.nota_zapato_nino),0)) asc";
        Query q = em.createNativeQuery(sql, ProveedorDisponibleDto.class);
        return q.getResultList();
    }

    private String getPorcentajePorItems(int idRubro) {
        switch (idRubro) {
            case 1:
            case 4:
            case 5:
                return "0.125 ";
            default:
                return "0.175 ";
        }
    }

    private String getParteSelectUbicacion(int idRubro, String codigoCanton, int idMunicipio, String codigosDepartamento, String idMunicipios,
            BigDecimal porUbicacionLocal, BigDecimal porMunAledanhos, BigDecimal porUbicacionOtros) {
        switch (idRubro) {
            case 1:
            case 4:
            case 5:
                if (codigoCanton != null && !codigoCanton.isEmpty()) {
                    return " case when mun_e.id_municipio = " + idMunicipio + " and nvl(emp.codigo_canton,'00') = '" + codigoCanton + "' then 35.00 when mun_e.id_municipio = " + idMunicipio + " and nvl(emp.codigo_canton,'00') != '" + codigoCanton + "' then 26.25 when mun_e.id_municipio in (" + idMunicipios + ") then 17.50 else 8.75 end porcentaje_geo ";
                } else {
                    return " case when mun_e.id_municipio = " + idMunicipio + " then 35.00 when mun_e.id_municipio in (" + idMunicipios + ") then 23.00 else 12.00 end porcentaje_geo ";
                }
            case 2:
                return " case when mun_e.id_municipio = " + idMunicipio + " then " + porUbicacionLocal.toString() + " when mun_e.id_municipio in (" + idMunicipios + ") then " + porMunAledanhos.toString() + " else " + porUbicacionOtros.toString() + " end porcentaje_geo";
            default:
                return " case when mun_e.codigo_departamento in (" + codigosDepartamento + ") then " + porUbicacionLocal.toString() + " else " + porUbicacionOtros.toString() + " end porcentaje_geo";
        }
    }

    /**
     * Verifica la existencia de los precios de referencia para un proveedor y
     * año en particular
     *
     * @param numeroNit
     * @param idRubro
     * @param idAnho
     * @return devuelve true si existen
     */
    public Boolean isPrecioRef(String numeroNit, BigDecimal idRubro, BigDecimal idAnho) {
        Query query = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.estadoEliminacion=0 and p.idMuestraInteres.idEmpresa.numeroNit=:pNumeroNit and p.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro and  p.idMuestraInteres.idAnho.idAnho=:pIdAnho", PreciosRefRubroEmp.class);
        query.setParameter("pNumeroNit", numeroNit);
        query.setParameter("pIdRubro", idRubro);
        query.setParameter("pIdAnho", idAnho);

        List<PreciosRefRubroEmp> lstPrecios = query.getResultList();

        return !lstPrecios.isEmpty();
    }

    public PreciosRefRubroEmp getPrecioRef(Empresa idEmpresa, BigDecimal idNivelEdu, BigDecimal idProducto, BigDecimal idRubro, BigDecimal idAnho) {
        Query query = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.idEmpresa=:idEmpresa and p.idNivelEducativo.idNivelEducativo=:idNivelEdu and p.idProducto.idProducto=:idProducto and p.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro and p.idMuestraInteres.idAnho.idAnho=:pIdAnho and p.estadoEliminacion=0", PreciosRefRubroEmp.class);
        query.setParameter("idEmpresa", idEmpresa);
        query.setParameter("idNivelEdu", idAnho.intValue() > 8 ? (idNivelEdu.compareTo(BigDecimal.ONE) == 0 ? new BigDecimal(22) : idNivelEdu) : idNivelEdu);
        query.setParameter("idProducto", idProducto);
        query.setParameter("pIdRubro", idRubro);
        query.setParameter("pIdAnho", idAnho);

        List<PreciosRefRubroEmp> lstPrecios = query.getResultList();
        if (lstPrecios.isEmpty()) {
            return null;
        } else {
            return lstPrecios.get(0);
        }
    }

    public List<CapaInstPorRubro> findCapaInstPorRubro(Participantes participante) {
        Query query = em.createQuery("SELECT c FROM CapaInstPorRubro c WHERE c.idMuestraInteres.idEmpresa=:idEmpresa and c.idMuestraInteres.idAnho.idAnho=:pIdAnho and c.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro and c.estadoEliminacion=0 and c.idMuestraInteres.estadoEliminacion=0 and c.idProcesoAdq=:pIdPro", CapaInstPorRubro.class);

        query.setParameter("idEmpresa", participante.getIdEmpresa());
        query.setParameter("pIdAnho", participante.getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq().getIdAnho().getIdAnho());
        query.setParameter("pIdRubro", participante.getIdOferta().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres());
        query.setParameter("pIdPro", participante.getIdOferta().getIdDetProcesoAdq().getIdProcesoAdq());
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
            q.getResultList().forEach((par) -> {
                lst.addAll(((Participantes) par).getDetalleOfertasList());
            });
        }
        return lst;
    }

    public List<DetRubroMuestraInteres> findDetRubroMuestraInteresEntitiesByRubroAndEmpresa(DetalleProcesoAdq detProceso, Empresa empresa) {
        Query q = em.createQuery("SELECT d FROM DetRubroMuestraInteres d WHERE d.estadoEliminacion = 0 and d.idAnho.idAnho=:pIdAnho and d.idRubroInteres.idRubroInteres=:pIdRubro and d.idEmpresa=:empresa", DetRubroMuestraInteres.class);
        q.setParameter("pIdAnho", detProceso.getIdProcesoAdq().getIdAnho().getIdAnho());
        q.setParameter("pIdRubro", detProceso.getIdRubroAdq().getIdRubroInteres());
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

    public List<TransferenciaRequerimiento> getLstTransferenciaRequerimientos(RequerimientoFondos idRequerimiento) {
        Query q = em.createQuery("SELECT t FROM TransferenciaRequerimiento t WHERE t.estadoEliminacion = 0 and t.idRequerimiento=:pIdReq ORDER BY t.fechaTransferencia", TransferenciaRequerimiento.class);
        q.setParameter("pIdReq", idRequerimiento);
        return q.getResultList();
    }

    public void guardarTransferenciaRequerimiento(TransferenciaRequerimiento tr) {
        if (tr.getIdTransferencia() == null) {
            em.persist(tr);
        } else {
            em.merge(tr);
        }
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
        detallePlanillaLst.forEach((detallePlanilla) -> {
            if (detallePlanilla.getIdDetallePlanilla() == null) {
                em.persist(detallePlanilla);
            } else {
                em.merge(detallePlanilla);
            }
        });
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
                    switch (numItem) {
                        case "0":
                            break;
                        case "1":
                        case "6":
                        case "10":
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
                        case "2":
                        case "7":
                        case "11":
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
                        case "3":
                        case "8":
                        case "12":
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
                        case "4":
                            item = findProducto("31");
                            nivel = findNivelEducativo("1");
                            break;
                        case "5":
                            item = findProducto("34");
                            nivel = findNivelEducativo("1");
                            break;
                        case "9":
                            item = findProducto("34");
                            nivel = findNivelEducativo("2");
                            break;
                        case "13":
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

                    switch (numItem) {
                        case "1":
                            nivel = findNivelEducativo("1");
                            break;
                        case "2":
                            nivel = findNivelEducativo("3");
                            break;
                        case "3":
                            nivel = findNivelEducativo("4");
                            break;
                        case "4":
                            nivel = findNivelEducativo("5");
                            break;
                        case "4.4":
                            nivel = findNivelEducativo("23");
                            break;
                        case "5":
                            nivel = findNivelEducativo("6");
                            break;
                        case "5.1":
                            nivel = findNivelEducativo("24");
                            break;
                        default:
                            error = "El item ingresado no es válido.";
                            break;
                    }
                    break;
                case 3: //ZAPATOS
                    switch (numItem) {
                        case "1":
                            item = findProducto("21");
                            nivel = findNivelEducativo("1");
                            break;
                        case "2":
                            item = findProducto("43");
                            nivel = findNivelEducativo("1");
                            break;
                        case "3":
                            item = findProducto("21");
                            nivel = findNivelEducativo("3");
                            break;
                        case "4":
                            item = findProducto("43");
                            nivel = findNivelEducativo("3");
                            break;
                        case "5":
                            item = findProducto("21");
                            nivel = findNivelEducativo("4");
                            break;
                        case "6":
                            item = findProducto("43");
                            nivel = findNivelEducativo("4");
                            break;
                        case "7":
                            item = findProducto("21");
                            nivel = findNivelEducativo("5");
                            break;
                        case "8":
                            item = findProducto("43");
                            nivel = findNivelEducativo("5");
                            break;
                        case "9":
                            item = findProducto("21");
                            nivel = findNivelEducativo("6");
                            break;
                        case "10":
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

    public List<CantidadPorNivelDto> getLstContratacionPorItemByIdAnho(Integer idProcesoAdq, String codigoDepartamento) {
        String where = " WHERE id_proceso_adq = " + idProcesoAdq;
        if (codigoDepartamento.equals("00")) {
        } else {
            where += " and dep.codigo_departamento = '" + codigoDepartamento + "' ";
        }

        Query q = em.createNativeQuery(Constantes.QUERY_CONTRATACION_MATRICULA.replace(":CONDICION:", where), CantidadPorNivelDto.class);
        q.setParameter(1, idProcesoAdq);
        return q.getResultList();
    }

    public void calcularNoItems(BigDecimal idRubro, BigDecimal idAnho) {
        //idDet = 52;
        Query q = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.estadoEliminacion = 0 and p.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro and p.idMuestraInteres.idAnho.idAnho=:pIdAnho ORDER BY p.idEmpresa", PreciosRefRubroEmp.class);
        q.setParameter("pIdRubro", idRubro);
        q.setParameter("pIdAnho", idAnho);

        List<PreciosRefRubroEmp> lstPre = q.getResultList();
        BigDecimal idEmpTemp = BigDecimal.ZERO;
        EmpresaNoItem emp = null;

        for (PreciosRefRubroEmp precios : lstPre) {

            if (idEmpTemp.intValue() == 0) {
                idEmpTemp = precios.getIdEmpresa().getIdEmpresa();
                emp = new EmpresaNoItem();
                emp.setIdEmpresa(idEmpTemp);
                emp.setIdMuestraInteres(findDetRubroByIdEmpresaAndIdRubroAndIdAnho(precios.getIdEmpresa().getIdEmpresa(), idRubro, idAnho).getIdMuestraInteres());
            } else if (idEmpTemp.intValue() == precios.getIdEmpresa().getIdEmpresa().intValue()) {

            } else {
                em.persist(emp);

                idEmpTemp = precios.getIdEmpresa().getIdEmpresa();
                emp = new EmpresaNoItem();
                emp.setIdEmpresa(idEmpTemp);
                emp.setIdMuestraInteres(findDetRubroByIdEmpresaAndIdRubroAndIdAnho(precios.getIdEmpresa().getIdEmpresa(), idRubro, idAnho).getIdMuestraInteres());
            }

            switch (precios.getNoItem()) {
                case "1":
                    emp.setItem1("1");
                    break;
                case "2":
                    emp.setItem2("2");
                    break;
                case "3":
                    emp.setItem3("3");
                    break;
                case "4":
                    emp.setItem4("4");
                    break;
                case "4.4":
                    emp.setItem44("44");
                    break;
                case "5":
                    emp.setItem5("5");
                    break;
                case "5.1":
                    emp.setItem51("51");
                    break;
                case "6":
                    emp.setItem6("6");
                    break;
                case "7":
                    emp.setItem7("7");
                    break;
                case "8":
                    emp.setItem8("8");
                    break;
                case "9":
                    emp.setItem9("9");
                    break;
                case "10":
                    emp.setItem10("10");
                    break;
                case "11":
                    emp.setItem11("11");
                    break;
                case "12":
                    emp.setItem12("12");
                    break;
                case "13":
                    emp.setItem13("13");
                    break;
            }
        }

        em.persist(emp);
    }

    private DetRubroMuestraInteres findDetRubroByIdEmpresaAndIdRubroAndIdAnho(BigDecimal idEmpresa, BigDecimal idRubro, BigDecimal idAnho) {
        Query q = em.createQuery("SELECT d FROM DetRubroMuestraInteres d WHERE d.estadoEliminacion=0 and d.idEmpresa.idEmpresa=:pIdEmpresa and d.idRubroInteres.idRubroInteres=:pIdRubro and d.idAnho.idAnho=:pIdAnho", DetRubroMuestraInteres.class);
        q.setParameter("pIdEmpresa", idEmpresa);
        q.setParameter("pIdRubro", idRubro);
        q.setParameter("pIdAnho", idAnho);

        return (DetRubroMuestraInteres) q.getResultList().get(0);
    }

    public void calcularNoItems(BigDecimal idRubro, BigDecimal idAnho, String numeroNit, DetRubroMuestraInteres detRubro) {
        Query q = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.idEmpresa.numeroNit=:pNit and p.estadoEliminacion = 0 and p.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro and p.idMuestraInteres.idAnho.idAnho=:pIdAnho ORDER BY p.idEmpresa", PreciosRefRubroEmp.class);
        q.setParameter("pNit", numeroNit);
        q.setParameter("pIdRubro", idRubro);
        q.setParameter("pIdAnho", idAnho);

        List<PreciosRefRubroEmp> lstPre = q.getResultList();
        BigDecimal idEmpTemp = BigDecimal.ZERO;
        EmpresaNoItem emp = null;
        if (detRubro == null) {
            detRubro = lstPre.get(0).getIdMuestraInteres();
        }

        for (PreciosRefRubroEmp precios : lstPre) {

            if (idEmpTemp.intValue() == 0) {
                idEmpTemp = precios.getIdEmpresa().getIdEmpresa();
                emp = new EmpresaNoItem();
                emp.setIdEmpresa(idEmpTemp);
                emp.setIdMuestraInteres(detRubro.getIdMuestraInteres());
            } else if (idEmpTemp.intValue() == precios.getIdEmpresa().getIdEmpresa().intValue()) {

            } else {
                em.persist(emp);

                idEmpTemp = precios.getIdEmpresa().getIdEmpresa();
                emp = new EmpresaNoItem();
                emp.setIdEmpresa(idEmpTemp);
                emp.setIdMuestraInteres(detRubro.getIdMuestraInteres());
            }

            switch (precios.getNoItem()) {
                case "1":
                    emp.setItem1("1");
                    break;
                case "2":
                    emp.setItem2("2");
                    break;
                case "3":
                    emp.setItem3("3");
                    break;
                case "4":
                    emp.setItem4("4");
                    break;
                case "5":
                    emp.setItem5("5");
                    break;
                case "6":
                    emp.setItem6("6");
                    break;
                case "7":
                    emp.setItem7("7");
                    break;
                case "8":
                    emp.setItem8("8");
                    break;
                case "9":
                    emp.setItem9("9");
                    break;
                case "10":
                    emp.setItem10("10");
                    break;
                case "11":
                    emp.setItem11("11");
                    break;
                case "12":
                    emp.setItem12("12");
                    break;
                case "13":
                    emp.setItem13("13");
                    break;
            }
        }

        em.persist(emp);
    }

    public void calcularPreRef(BigDecimal idRubro, BigDecimal idAnho) {
        Query q = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.estadoEliminacion = 0 and p.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro and p.idMuestraInteres.idAnho.idAnho=:pIdAnho ORDER BY p.idEmpresa", PreciosRefRubroEmp.class);
        q.setParameter("pIdRubro", idRubro);
        q.setParameter("pIdAnho", idAnho);

        List<PreciosRefRubroEmp> lstPre = q.getResultList();
        BigDecimal idEmpTemp = BigDecimal.ZERO;
        EmpresaPreciosRef emp = null;

        for (PreciosRefRubroEmp precios : lstPre) {

            if (idEmpTemp.intValue() == 0) {
                idEmpTemp = precios.getIdEmpresa().getIdEmpresa();
                emp = new EmpresaPreciosRef();
                emp.setIdEmpresa(idEmpTemp);
                emp.setIdMuestraInteres(precios.getIdMuestraInteres().getIdMuestraInteres());
            } else {
                if (idEmpTemp.intValue() == precios.getIdEmpresa().getIdEmpresa().intValue()) {

                } else {
                    em.persist(emp);

                    idEmpTemp = precios.getIdEmpresa().getIdEmpresa();
                    emp = new EmpresaPreciosRef();
                    emp.setIdEmpresa(idEmpTemp);
                    emp.setIdMuestraInteres(precios.getIdMuestraInteres().getIdMuestraInteres());
                }

                switch (precios.getNoItem()) {
                    case "1":
                        emp.setItem1(precios.getPrecioReferencia());
                        break;
                    case "2":
                        emp.setItem2(precios.getPrecioReferencia());
                        break;
                    case "3":
                        emp.setItem3(precios.getPrecioReferencia());
                        break;
                    case "4":
                        emp.setItem4(precios.getPrecioReferencia());
                        break;
                    case "5":
                        emp.setItem5(precios.getPrecioReferencia());
                        break;
                    case "6":
                        emp.setItem6(precios.getPrecioReferencia());
                        break;
                    case "7":
                        emp.setItem7(precios.getPrecioReferencia());
                        break;
                    case "8":
                        emp.setItem8(precios.getPrecioReferencia());
                        break;
                    case "9":
                        emp.setItem9(precios.getPrecioReferencia());
                        break;
                    case "10":
                        emp.setItem10(precios.getPrecioReferencia());
                        break;
                    case "11":
                        emp.setItem11(precios.getPrecioReferencia());
                        break;
                    case "12":
                        emp.setItem12(precios.getPrecioReferencia());
                        break;
                    case "13":
                        emp.setItem13(precios.getPrecioReferencia());
                        break;
                }
            }
        }
    }

    public void calcularPreRefByNit(BigDecimal idRubro, BigDecimal idAnho, String numeroNit) {
        Query q = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.idMuestraInteres.idEmpresa.numeroNit=:pNit and p.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro and p.idMuestraInteres.idAnho.idAnho=:pIdAnho ORDER BY p.idEmpresa", PreciosRefRubroEmp.class);
        q.setParameter("pNit", numeroNit);
        q.setParameter("pIdRubro", idRubro);
        q.setParameter("pIdAnho", idAnho);

        List<PreciosRefRubroEmp> lstPre = q.getResultList();
        BigDecimal idEmpTemp = BigDecimal.ZERO;
        EmpresaPreciosRef emp = null;

        for (PreciosRefRubroEmp precios : lstPre) {

            if (idEmpTemp.intValue() == 0) {
                idEmpTemp = precios.getIdEmpresa().getIdEmpresa();
                emp = new EmpresaPreciosRef();
                emp.setIdEmpresa(idEmpTemp);
                emp.setIdMuestraInteres(precios.getIdMuestraInteres().getIdMuestraInteres());
            } else {
                if (idEmpTemp.intValue() == precios.getIdEmpresa().getIdEmpresa().intValue()) {

                } else {
                    em.persist(emp);

                    idEmpTemp = precios.getIdEmpresa().getIdEmpresa();
                    emp = new EmpresaPreciosRef();
                    emp.setIdEmpresa(idEmpTemp);
                    emp.setIdMuestraInteres(precios.getIdMuestraInteres().getIdMuestraInteres());
                }

                switch (precios.getNoItem()) {
                    case "1":
                        emp.setItem1(precios.getPrecioReferencia());
                        break;
                    case "2":
                        emp.setItem2(precios.getPrecioReferencia());
                        break;
                    case "3":
                        emp.setItem3(precios.getPrecioReferencia());
                        break;
                    case "4":
                        emp.setItem4(precios.getPrecioReferencia());
                        break;
                    case "5":
                        emp.setItem5(precios.getPrecioReferencia());
                        break;
                    case "6":
                        emp.setItem6(precios.getPrecioReferencia());
                        break;
                    case "7":
                        emp.setItem7(precios.getPrecioReferencia());
                        break;
                    case "8":
                        emp.setItem8(precios.getPrecioReferencia());
                        break;
                    case "9":
                        emp.setItem9(precios.getPrecioReferencia());
                        break;
                    case "10":
                        emp.setItem10(precios.getPrecioReferencia());
                        break;
                    case "11":
                        emp.setItem11(precios.getPrecioReferencia());
                        break;
                    case "12":
                        emp.setItem12(precios.getPrecioReferencia());
                        break;
                    case "13":
                        emp.setItem13(precios.getPrecioReferencia());
                        break;
                }
            }
        }
    }

    public List<PrecioReferenciaEmpresaDto> getLstPreciosByIdEmpresaAndIdProcesoAdq(BigDecimal idEmpresa, BigDecimal idAnho, String idNivelesCe) {
        Query q = em.createNativeQuery("select \n"
                + "                rownum                  idRow,\n"
                + "                pemp.no_item            noItem,\n"
                + "                cat.nombre_producto     nombreProducto,\n"
                + "                niv.descripcion_nivel   descripcionNivel,\n"
                + "                pmax.precio_maximo      precioMaximo,\n"
                + "                pemp.precio_referencia  precioReferencia\n"
                + "            from precio_maximo_referencia pmax\n"
                + "                inner join det_rubro_muestra_interes det on pmax.id_rubro_interes = det.id_rubro_interes and\n"
                + "                                                            pmax.id_anho = det.id_anho                                                            \n"
                + "                inner join precios_ref_rubro_emp pemp on pmax.id_producto = pemp.id_producto and \n"
                + "                                                        pmax.no_item = pemp.no_item and\n"
                + "                                                        pemp.id_muestra_interes = det.id_muestra_interes\n"
                + "                inner join catalogo_producto cat      on pemp.id_producto = cat.id_producto\n"
                + "                inner join nivel_educativo niv        on pemp.id_nivel_educativo = niv.id_nivel_educativo\n"
                + "            where \n"
                + "                det.id_empresa = " + idEmpresa + " and \n"
                + "                det.id_anho = " + idAnho + " and\n"
                + "                pemp.estado_eliminacion = 0 and\n"
                + (idAnho.intValue() > 8 ? " pemp.id_nivel_educativo in (" + (idNivelesCe.replace("1", "22").replace("5", "5,23").replace("6", "6,24")) + ")\n" : "                pemp.id_nivel_educativo in (" + (idNivelesCe) + ")\n")
                + "            order by to_number(pemp.no_item)", PrecioReferenciaEmpresaDto.class);
        return q.getResultList();
    }

    public void guardarCantonProveedor(String numeroNit, String codigoCanton) {
        Query q = em.createNativeQuery("UPDATE Empresa SET codigo_canton='" + codigoCanton + "' WHERE numero_nit='" + numeroNit + "'");

        int i = q.executeUpdate();

        if (i == 0) {
            System.out.println("empresa no encontrada: " + numeroNit);
        }
    }

    public List<ContratosOrdenesCompras> getLstContratosByNitAndAnho(String numeroNit, String anho) {
        Query q = em.createQuery("SELECT c FROM ContratosOrdenesCompras c WHERE c.idResolucionAdj.idParticipante.idEmpresa.numeroNit=:nit and c.idResolucionAdj.idParticipante.idOferta.idDetProcesoAdq.idProcesoAdq.idAnho.anho=:anho ORDER BY c.idResolucionAdj.idParticipante.idOferta.codigoEntidad.codigoEntidad", ContratosOrdenesCompras.class);
        q.setParameter("nit", numeroNit);
        q.setParameter("anho", anho);
        return q.getResultList();
    }

    private Empresa findEmpresaByCodSeg(String codigoSeg) {
        Empresa emp = null;
        Query q;
        List lst;

        q = em.createNativeQuery("SELECT id_empresa FROM EMPRESA_CODIGO_SEG WHERE codigo_seguridad=?1 AND usuario_activado = 0");
        q.setParameter(1, codigoSeg);

        lst = q.getResultList();

        if (!lst.isEmpty()) {
            emp = findEmpresaByPk((BigDecimal) lst.get(0));
        }

        return emp;
    }

    private void updateCodigoValidacionProveedor(BigDecimal idEmpresa, String codigoGenerado, Boolean fechaActivacion) {
        String sql = "";
        if (fechaActivacion) {
            sql = "UPDATE EMPRESA_CODIGO_SEG SET fecha_activacion = sysdate WHERE ID_EMPRESA=" + idEmpresa;
        } else {
            sql = "UPDATE EMPRESA_CODIGO_SEG SET USUARIO_ACTIVADO = 1, TOKEN_ACTIVACION='" + codigoGenerado + "' WHERE ID_EMPRESA=" + idEmpresa;
        }
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
    }

    /**
     *
     * @param codigoSeg
     * @param nit
     * @param dui
     * @param tituloEmail
     * @param cuerpoEmail
     * @return 1. Todo bien, 2. Datos no encontrados, 3. Error en el correo
     */
    public int validarCodigoSegEmpresa(String codigoSeg, String nit, String dui, String tituloEmail, String cuerpoEmail, Session sesionMail) {
        int codigoOperacion = 1;
        Empresa emp = findEmpresaByCodSeg(codigoSeg);

        if (emp == null) {
            codigoOperacion = 2;
        } else if (emp.getIdPersona().getNumeroNit().equals(nit)
                && emp.getIdPersona().getNumeroDui().equals(dui)) {

        } else {
            codigoOperacion = 2;
        }

        if (codigoOperacion == 1) {
            String codigoGenerado = (new RC4Crypter()).encrypt("ha", emp.getIdEmpresa().toString().concat(":").concat(codigoSeg));

            String cuerpo = MessageFormat.format(cuerpoEmail, codigoGenerado);

            if (eMailEJB.enviarMail(emp.getIdPersona().getEmail(), tituloEmail, cuerpo, sesionMail)) {
                updateCodigoValidacionProveedor(emp.getIdEmpresa(), codigoGenerado, false);
            } else {
                codigoOperacion = 3;
            }
        }
        return codigoOperacion;
    }

    public void guardarPasswordProv(BigDecimal idEmpresa, String password) {
        Empresa emp = findEmpresaByPk(idEmpresa);
        Persona per = emp.getIdPersona();

        per.setClaveAcceso((new RC4Crypter()).encrypt("ha", password));
        per.setUsuario(per.getNumeroNit());

        em.merge(per);

        Query q = em.createQuery("SELECT u FROM Usuario u where u.idPersona.idPersona=:idPersona", Usuario.class);
        q.setParameter("idPersona", per.getIdPersona());
        Usuario usu;

        if (q.getResultList().isEmpty()) {
            usu = new Usuario();
            usu.setActivo((short) 1);
            usu.setCodigoDepartamento("00");
            usu.setEstadoEliminacion(BigInteger.ZERO);
            usu.setFechaInsercion(new Date());
            usu.setRangoFechaLogin((short) 0);
            usu.setIdPersona(per);
            usu.setIdTipoUsuario(em.find(TipoUsuario.class, new BigDecimal(9)));
            usu.setUsuarioInsercion("AUTOMATICO");

            em.persist(usu);
        } else {
            usu = (Usuario) q.getResultList().get(0);
            usu.setIdTipoUsuario(em.find(TipoUsuario.class, new BigDecimal(9)));
            em.merge(usu);
        }
        updateCodigoValidacionProveedor(idEmpresa, null, true);
    }

    public void guardarPasswordPer(String numeroNit, String password) {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.idPersona.numeroNit = :nit", Usuario.class);
        q.setParameter("nit", numeroNit);
        Usuario usr = (Usuario) q.getResultList().get(0);
        usr.setActivo((short) 1);
        usr.setTokenCambioClave(null);

        em.merge(usr);

        Persona per = usr.getIdPersona();
        per.setClaveAcceso((new RC4Crypter()).encrypt("ha", password));

        em.merge(per);
    }

    public String datosConfirmados(BigDecimal idDetRubro, BigDecimal idEmpresa, String usuario) {
        DetRubroMuestraInteres detRubro = em.find(DetRubroMuestraInteres.class, idDetRubro);

        if (detRubro.getAceptacionTerminos() == 0) {
            detRubro.setFechaModificacion(new Date());
            detRubro.setDatosVerificados((short) 1);
            detRubro.setAceptacionTerminos((short) 1);
            detRubro.setUsuarioModificacion(usuario);
            em.merge(detRubro);

            calcularNoItems(detRubro.getIdRubroInteres().getIdRubroInteres(), detRubro.getIdAnho().getIdAnho(), detRubro.getIdEmpresa().getNumeroNit(), detRubro);
            calcularPreRefByNit(detRubro.getIdRubroInteres().getIdRubroInteres(), detRubro.getIdAnho().getIdAnho(), detRubro.getIdEmpresa().getNumeroNit());
        }

        return getIdGestionByProceso(idEmpresa, detRubro.getIdMuestraInteres());
    }

    public String getIdGestionByProceso(BigDecimal idEmpresa, BigDecimal idMuestraInteres) {
        StoredProcedureQuery q = em.createNamedStoredProcedureQuery("SP_GET_ID_GESTION");
        q.setParameter("P_ID_EMPRESA", idEmpresa);
        q.setParameter("P_ID_MUESTRA_INTERES", idMuestraInteres);
        q.execute();
        return (String) q.getOutputParameterValue(3);
    }

    public void resetAceptacion(String numeroNit, BigDecimal idAnho) {
        StoredProcedureQuery q = em.createNamedStoredProcedureQuery("SP_RESET_ACEPTACION");
        q.setParameter("P_NUMERO_NIT", numeroNit);
        q.setParameter("P_ID_ANHO", idAnho);
        q.execute();
    }

    public void resetActivacion(String numeroNit) {
        StoredProcedureQuery q = em.createNamedStoredProcedureQuery("SP_RESET_ACTIVACION_USER");
        q.setParameter("P_NUMERO_NIT", numeroNit);
        q.execute();
    }

    public void enviarNotificacionModProv(String titulo, String mensaje, List<String> to, List<String> cc, List<String> bcc, Session mailSession) {
        eMailEJB.enviarMail(titulo, mensaje, to, cc, bcc, mailSession);
    }

    public Boolean tokenUsuarioValido(String token) {
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.tokenCambioClave=:token", Usuario.class);
        q.setParameter("token", token);
        return !q.getResultList().isEmpty();
    }

    public void generarCodigoSeguridad(Integer idDetProcesoAdq) {
        Query q = em.createQuery("SELECT d.idEmpresa FROM DetRubroMuestraInteres d WHERE d.idDetProcesoAdq.idDetProcesoAdq=:id", Empresa.class);
        q.setParameter("id", idDetProcesoAdq);
        List<Empresa> lst = q.getResultList();

        RC4Crypter encript = new RC4Crypter();

        for (Empresa empresa : lst) {
            EmpresaCodigoSeg ecs = new EmpresaCodigoSeg();

            ecs.setIdEmpresa(empresa.getIdEmpresa());
            ecs.setCodigoSeguridad(encript.encrypt("ha", empresa.getIdEmpresa().toString().concat(empresa.getNumeroNit())).substring(0, 10));
            ecs.setUsuarioActivado((short) 0);

            em.persist(ecs);
        }
    }

    public void generarCodigoSeguridad(EmpresaCodigoSeg ecs) {
        em.persist(ecs);
    }

    public NotificacionOfertaProvDto getNotificacionOfertaProv(BigDecimal idEmpresa, BigDecimal idAnho, BigDecimal idRubroInteres) {
        NotificacionOfertaProvDto notificacionOfertaProvDto;

        Query q = em.createNamedQuery("Proveedor.NotificacionOfertaProv", NotificacionOfertaProvDto.class);
        q.setParameter(1, idEmpresa);
        q.setParameter(2, idAnho);
        q.setParameter(3, idRubroInteres);

        notificacionOfertaProvDto = q.getResultList().isEmpty() ? null : (NotificacionOfertaProvDto) q.getResultList().get(0);

        if (notificacionOfertaProvDto != null) {
            notificacionOfertaProvDto.setLstDetItemOfertaGlobal(reportesEJB.getLstItemOfertaGlobal(notificacionOfertaProvDto.getNumeroNit(), idRubroInteres, idAnho));
            notificacionOfertaProvDto.setLstMunIntOfertaGlobal(reportesEJB.getLstMunIntOfertaGlobal(notificacionOfertaProvDto.getNumeroNit(), idRubroInteres, idAnho));
        }
        return notificacionOfertaProvDto;
    }

    public List<SeguimientoIngresoProveDto> findSeguimiento(BigDecimal idRubro, BigDecimal idAnho, String fecha) {
        Query q = em.createNamedQuery("Proveedor.SeguimientoIngresoProveDto", SeguimientoIngresoProveDto.class);
        q.setParameter(1, idAnho);
        q.setParameter(2, idRubro);
        q.setParameter(3, fecha);

        return q.getResultList();
    }

    public DetRubroMuestraInteres findDetByNitAndIdAnho(String nit, String anho) {
        Query q = em.createQuery("SELECT d FROM DetRubroMuestraInteres d WHERE d.idEmpresa.numeroNit=:nit and d.idAnho.anho=:anho", DetRubroMuestraInteres.class);
        q.setParameter("nit", nit);
        q.setParameter("anho", anho);

        return q.getResultList().isEmpty() ? null : (DetRubroMuestraInteres) q.getResultList().get(0);

    }
}
