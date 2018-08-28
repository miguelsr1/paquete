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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
import sv.gob.mined.paquescolar.model.CreditoBancario;
import sv.gob.mined.paquescolar.model.DetalleCredito;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.EntFinanDetProAdq;
import sv.gob.mined.paquescolar.model.EntidadFinanciera;
import sv.gob.mined.paquescolar.model.RubrosAmostrarInteres;
import sv.gob.mined.paquescolar.model.Usuario;
import sv.gob.mined.paquescolar.model.UsuarioEntidadFinanciera;
import sv.gob.mined.paquescolar.model.pojos.credito.CreditoProveedor;
import sv.gob.mined.paquescolar.model.pojos.credito.ResumenCreditosDto;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;
import sv.gob.mined.paquescolar.model.pojos.credito.DatosProveedoresFinanDto;
import sv.gob.mined.paquescolar.util.Fechas;
import sv.gob.mined.paquescolar.util.StringUtils;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class CreditosEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    public EntidadFinanciera guardarEntidadFinanciera(EntidadFinanciera entidad) {
        try {
            if (em.find(EntidadFinanciera.class, entidad.getCodEntFinanciera()) == null) {
                entidad.setBandera((short) 0);
                em.persist(entidad);
            } else {
                entidad = em.merge(entidad);
            }
        } catch (Exception e) {
            Logger.getLogger(CreditosEJB.class.getName()).log(Level.SEVERE, null, e);
        }
        return entidad;
    }

    /**
     * Devuelve un listado de entidades financieras (BANCOS o CAJAS DE CREDITO O
     * PRESTAMO) dependiendo del parametro que reciba 0 - modulo de créditos 1 -
     * Bancos asociados a cuentas de los proveedores 2 - las 2 anteriores
     *
     * @param tipoEntidad
     * @return
     */
    public List<EntidadFinanciera> findEntidadFinancieraEntities(Short tipoEntidad) {
        Query q = em.createQuery("SELECT e FROM EntidadFinanciera e WHERE e.estadoEliminacion=0 AND e.bandera=:tipoEntidad ORDER BY e.nombreEntFinan", EntidadFinanciera.class);
        q.setParameter("tipoEntidad", tipoEntidad);
        return q.getResultList();
    }

    public List<EntidadFinanciera> findEntidadFinancieraEntitiesByName(List<String> nombres) {
        Query q = em.createQuery("SELECT e FROM EntidadFinanciera e WHERE e.estadoEliminacion=0 and e.nombreEntFinan IN :list ORDER BY e.nombreEntFinan");
        q.setParameter("list", nombres);
        return q.getResultList();
    }

    public EntidadFinanciera findEntidadFinanciera(String id) {
        return em.find(EntidadFinanciera.class, id);
    }

    public boolean utilesInsCredito(Integer idPersona) {
        Query q = em.createQuery("SELECT u FROM UsuarioEntidadFinanciera u WHERE u.codEntFinanciera.codEntFinanciera in ('0053','0032','00036','00034','0057','0058','051','0059','0039') and u.idUsuario.idPersona.idPersona=:idPersona", UsuarioEntidadFinanciera.class);
        q.setParameter("idPersona", idPersona);

        return !q.getResultList().isEmpty();
    }

    public List<EntidadFinanciera> getlstEntFinanUsuario(Usuario usuario) {
        Query query = em.createQuery("SELECT e.codEntFinanciera FROM UsuarioEntidadFinanciera e WHERE e.idUsuario=:usuario ORDER BY e.codEntFinanciera.nombreEntFinan", EntidadFinanciera.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }

    public List<EntidadFinanciera> findEntidadFinancieraEntitiesByCodigo(String codigos) {
        Query q = em.createQuery("SELECT e FROM EntidadFinanciera e WHERE e.codEntFinanciera not in (" + codigos + ") ORDER BY e.nombreEntFinan");
        return q.getResultList();
    }

    public int deleteEntFinanUsuario(Usuario usuario) {
        Query query = em.createQuery("DELETE FROM UsuarioEntidadFinanciera e WHERE e.idUsuario=:usuario");
        query.setParameter("usuario", usuario);
        int valor = query.executeUpdate();

        return valor;
    }

    public void create(UsuarioEntidadFinanciera usuarioEntidadFinanciera) {
        try {
            em.persist(usuarioEntidadFinanciera);
        } catch (Exception ex) {
            Logger.getLogger(CreditosEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<EntidadFinanciera> getlstEntFinanUsuario(String usuario) {
        Query query = em.createQuery("SELECT u.codEntFinanciera FROM UsuarioEntidadFinanciera u WHERE u.idUsuario.idPersona.usuario=:usuario", EntidadFinanciera.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }

    public List<CreditoBancario> findCreditoBancarioByEmpresa(String numeroNit, DetalleProcesoAdq proceso) {
        Query q = em.createQuery("SELECT c FROM CreditoBancario c WHERE c.numeroNit=:numeroNit and c.estadoEliminacion=0 and c.idDetProcesoAdq=:proceso", CreditoBancario.class);
        q.setParameter("numeroNit", numeroNit);
        q.setParameter("proceso", proceso);

        return q.getResultList();
    }

    public List<DetalleCredito> findDetalleCreditoEntitiesByCreditoAndProceso(CreditoBancario idCredito) {
        Query q = em.createQuery("SELECT d FROM DetalleCredito d Where d.estadoEliminacion=0 and d.idCredito=:idCredito and d.idCredito.idDetProcesoAdq=:idProceso", DetalleCredito.class);
        q.setParameter("idCredito", idCredito);
        q.setParameter("idProceso", idCredito.getIdDetProcesoAdq());

        return q.getResultList();
    }

    public CreditoBancario guardarCredito(CreditoBancario creditoBancario, String usuario) {
        if (creditoBancario.getIdCredito() == null) {
            crearCredito(creditoBancario, usuario);
        } else {
            creditoBancario = editCreditoBancario(creditoBancario, usuario);
        }

        return creditoBancario;
    }

    public void crearCredito(CreditoBancario creditoBancario, String usuario) {
        creditoBancario.setFechaInsercion(new Date());
        creditoBancario.setUsuarioInsercion(usuario);
        creditoBancario.setEstadoEliminacion(BigInteger.ZERO);
        em.persist(creditoBancario);
    }

    public CreditoBancario editCreditoBancario(CreditoBancario creditoBancario, String usuario) {
        creditoBancario.setFechaModificacion(new Date());
        creditoBancario.setUsuarioModificacion(usuario);
        creditoBancario = em.merge(creditoBancario);
        return creditoBancario;
    }

    public List<ContratosOrdenesCompras> getLstContratosDisponiblesCreditos(Empresa empresa, DetalleProcesoAdq proceso) {
        Query q = em.createQuery("SELECT c FROM ContratosOrdenesCompras c WHERE c.estadoEliminacion=0 and c.idResolucionAdj.estadoEliminacion = 0 and c.idResolucionAdj.idEstadoReserva.idEstadoReserva=2 and c.idResolucionAdj.idParticipante.idEmpresa=:idEmpresa and c.idResolucionAdj.idParticipante.estadoEliminacion=0 and c.idResolucionAdj.idParticipante.idOferta.estadoEliminacion=0 and c.idResolucionAdj.idParticipante.idOferta.idDetProcesoAdq=:idProceso and c.idContrato not in (SELECT d.idContrato.idContrato FROM DetalleCredito d WHERE d.estadoEliminacion=0 and d.idCredito.idDetProcesoAdq=:idProceso and d.idCredito.numeroNit=:numeroNit) order by c.idResolucionAdj.idParticipante.idOferta.codigoEntidad.codigoEntidad", ContratosOrdenesCompras.class);
        q.setParameter("idEmpresa", empresa);
        q.setParameter("idProceso", proceso);

        q.setParameter("numeroNit", empresa.getNumeroNit());

        return q.getResultList();
    }

    public List<ResumenCreditosDto> generarResumen(BigDecimal idAnho) {
        Query q = em.createNamedQuery("Credito.ResumenGenralDeCreditos", ResumenCreditosDto.class);
        q.setParameter(1, idAnho);
        return q.getResultList();
    }

    public List<CreditoProveedor> getCreditosActivosPorProveedor(String codigoDepartamento, DetalleProcesoAdq idDetProceso) {
        List<CreditoProveedor> lstCreditosProveedor = new ArrayList<>();
        String sql = "SELECT * FROM vw_creditos_proveedores ";
        if (codigoDepartamento != null) {
            if (!codigoDepartamento.equals("00")) {
                if (sql.contains("WHERE")) {
                    sql = sql.concat(" COD_DEPA_EMP = ").concat(codigoDepartamento);
                } else {
                    sql = sql.concat(" WHERE COD_DEPA_EMP = ").concat(codigoDepartamento);
                }
            }
        }
        if (idDetProceso != null) {
            if (sql.contains("WHERE")) {
                sql = sql.concat(" and ID_DET_PROCESO_ADQ = ").concat(String.valueOf(idDetProceso.getIdDetProcesoAdq()));
            } else {
                sql = sql.concat(" WHERE ID_DET_PROCESO_ADQ = ").concat(String.valueOf(idDetProceso.getIdDetProcesoAdq()));
            }
        }

        Query query = em.createNativeQuery(sql);
        List lst = query.getResultList();

        for (Object object : lst) {
            Object[] datos = (Object[]) object;
            CreditoProveedor credito = new CreditoProveedor();
            credito.setIdCredito(new BigDecimal(datos[0].toString()));
            credito.setNumeroNit(datos[1].toString());
            credito.setMontoCredito(datos[2] == null ? null : new BigDecimal(datos[2].toString()));
            credito.setNombreEntFinan(datos[3].toString());
            credito.setFechaVencimiento(datos[4] == null ? null : Fechas.getStringToDate(datos[4].toString()));
            credito.setCodigoEntidad(datos[5].toString());
            credito.setNombreCE(datos[6].toString());
            credito.setNombreDepartamento(datos[7].toString());
            credito.setIdProceso(new BigDecimal(datos[8].toString()));
            credito.setCodigoDepartamento(datos[9].toString());
            credito.setCodigoDepaEmp(datos[10].toString());
            credito.setCreditoActivo(new BigInteger(datos[11].toString()));
            credito.setMontoContrato(new BigDecimal(datos[12].toString()));
            credito.setNombreDepartamentoPro(datos[13].toString());
            credito.setRazonSocial(datos[14].toString());
            credito.setCodigoDepaEmp(datos[15].toString());
            lstCreditosProveedor.add(credito);
        }
        return lstCreditosProveedor;
    }

    public List<DatosProveedoresFinanDto> buscarListadoProveedor(BigDecimal rubro, EntidadFinanciera entidadSeleccionado, DetalleProcesoAdq proceso, BigInteger estadoCredito) {
        String cadenaWhere = StringUtils.addCampoToWhere("", "id_rubro_interes", rubro);

        if (entidadSeleccionado != null && !entidadSeleccionado.getCodEntFinanciera().equals("00000")) {
            cadenaWhere = StringUtils.addCampoToWhere(cadenaWhere, "cod_ent_financiera", entidadSeleccionado.getCodEntFinanciera());
        }

        cadenaWhere = StringUtils.addCampoToWhere(cadenaWhere, "ID_DET_PROCESO_ADQ", proceso.getIdDetProcesoAdq());
        cadenaWhere = StringUtils.addCampoToWhere(cadenaWhere, "credito_activo", estadoCredito);

        if (!cadenaWhere.isEmpty()) {
            cadenaWhere = cadenaWhere + " AND monto_credito is not null";
        }

        em.clear();
        Query q = em.createNativeQuery(StringUtils.QUERY_RPT_PROVEEDOR_ENT_FINAN + cadenaWhere, DatosProveedoresFinanDto.class);
        return q.getResultList();
    }

    public List<RubrosAmostrarInteres> getLstRubrosHabilitados(Integer idDetProcesoAdq, String codEntFinanciera) {
        Query q = em.createNamedQuery("Credito.ProcesoCreditoHabilitado", RubrosAmostrarInteres.class);
        q.setParameter(1, idDetProcesoAdq);
        q.setParameter(2, codEntFinanciera);
        return q.getResultList();
    }

    public List<DetalleProcesoAdq> getLstDetalleProcesoCredito(Integer idProceso) {
        Query q = em.createQuery("SELECT d FROM DetalleProcesoAdq d WHERE d.idProcesoAdq.idProcesoAdq=:idProceso ORDER by d.idDetProcesoAdq ", DetalleProcesoAdq.class);
        q.setParameter("idProceso", idProceso);
        return q.getResultList();
    }

    public List<EntFinanDetProAdq> getLstEntidadesCredito(Integer idDetProcesoAdq) {
        Query q = em.createQuery("SELECT e FROM EntFinanDetProAdq e WHERE e.idDetProcesoAdq=:idDetProcesoAdq", EntFinanDetProAdq.class);
        q.setParameter("idDetProcesoAdq", idDetProcesoAdq);
        return q.getResultList();
    }

    public List<EntidadFinanciera> getLstEntFinanHabilitadoCredito(Integer idDetProcesoAdq) {
        Query q = em.createNamedQuery("Credito.EntidadCreditoHabilitado", EntidadFinanciera.class);
        q.setParameter(1, idDetProcesoAdq);
        return q.getResultList();
    }

    public void createEntFinanHabilitado(EntFinanDetProAdq entidadFinanciera) {
        em.persist(entidadFinanciera);
    }

    public void deleteEntFinanHabilitado(BigDecimal idEntFinan) {
        EntFinanDetProAdq ent = em.find(EntFinanDetProAdq.class, idEntFinan);
        em.remove(ent);
    }

    public List<DetalleCredito> findDetalleCreditoEntitiesByCredito(CreditoBancario credito) {
        Query q = em.createQuery("SELECT d FROM DetalleCredito d WHERE d.idCredito=:idCredito and d.estadoEliminacion=0", CreditoBancario.class);
        q.setParameter("idCredito", credito);
        return q.getResultList();
    }

    public List<VwCatalogoEntidadEducativa> findCENoDisponibleCredito(String codigos) {
        Query q = em.createQuery("SELECT v FROM VwCatalogoEntidadEducativa v WHERE v.codigoEntidad in (" + codigos + ")", VwCatalogoEntidadEducativa.class);
        return q.getResultList();
    }
}
