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
import sv.gob.mined.paquescolar.model.pojos.credito.CreditoProveedorDto;
import sv.gob.mined.paquescolar.model.pojos.credito.ResumenCreditosDto;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;
import sv.gob.mined.paquescolar.model.pojos.credito.DatosProveedoresFinanDto;
import sv.gob.mined.paquescolar.util.Constantes;

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
     * PRESTAMO) dependiendo del parametro que reciba 0 - Modulo de créditos 1 -
     * Bancos asociados a cuentas de los proveedores 2 - Las 2 anteriores
     *
     * @param tipoEntidad
     * @return
     */
    public List<EntidadFinanciera> findEntidadFinancieraEntities(Short tipoEntidad) {
        Query q = em.createQuery("SELECT e FROM EntidadFinanciera e WHERE e.estadoEliminacion=0 AND e.bandera=:tipoEntidad ORDER BY e.nombreEntFinan", EntidadFinanciera.class);
        q.setParameter("tipoEntidad", tipoEntidad);
        return q.getResultList();
    }

    public List<EntidadFinanciera> findEntidadFinancieraByIdDetProcesoAdq(Integer idDetProcesoAdq) {
        Query query = em.createNamedQuery("Credito.EntidadFinancieraByIdDetProcesoAdq", EntidadFinanciera.class);
        query.setParameter(1, idDetProcesoAdq);
        return query.getResultList();
    }

    public List<EntidadFinanciera> getlstEntFinanUsuario(String usuario, Integer idDetProcesoAdq) {
        Query query = em.createNamedQuery("Credito.EntidadFinancieraByUsuarioAndIdDetProcesoAdq", EntidadFinanciera.class);
        query.setParameter(1, usuario);
        query.setParameter(2, idDetProcesoAdq);
        return query.getResultList();

        /*Query query = em.createQuery("SELECT u.codEntFinanciera FROM UsuarioEntidadFinanciera u WHERE u.idUsuario.idPersona.usuario=:usuario", EntidadFinanciera.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();*/
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

    public void guardarCredito(CreditoBancario creditoBancario, String usuario) {
        if (creditoBancario.getIdCredito() == null) {
            crearCredito(creditoBancario, usuario);
        } else {
            editCreditoBancario(creditoBancario, usuario);
        }
    }

    public void crearCredito(CreditoBancario creditoBancario, String usuario) {
        creditoBancario.setFechaInsercion(new Date());
        creditoBancario.setUsuarioInsercion(usuario);
        creditoBancario.setEstadoEliminacion(BigInteger.ZERO);
        em.persist(creditoBancario);

        em.find(CreditoBancario.class, creditoBancario.getIdCredito());
    }

    public void editCreditoBancario(CreditoBancario creditoBancario, String usuario) {
        creditoBancario.getDetalleCreditoList().stream().map((detalleCredito) -> {
            if (detalleCredito.getEliminado()) {
                detalleCredito.setFechaEliminacion(new Date());
                detalleCredito.setUsuarioModificacion(usuario);
            }
            return detalleCredito;
        }).forEachOrdered((detalleCredito) -> {
            em.merge(detalleCredito);
        });

        creditoBancario.setFechaModificacion(new Date());
        creditoBancario.setUsuarioModificacion(usuario);
        creditoBancario = em.merge(creditoBancario);

        //return em.find(CreditoBancario.class, creditoBancario.getIdCredito());
    }

    public List<ContratosOrdenesCompras> getLstContratosDisponiblesCreditos(Empresa empresa, DetalleProcesoAdq proceso) {
        List<ContratosOrdenesCompras> lst = new ArrayList();
        
        Query q = em.createQuery("SELECT c FROM ContratosOrdenesCompras c WHERE c.estadoEliminacion=0 and c.idResolucionAdj.estadoEliminacion = 0 and c.idResolucionAdj.idEstadoReserva.idEstadoReserva=2 and c.idResolucionAdj.idParticipante.idEmpresa=:idEmpresa and c.idResolucionAdj.idParticipante.estadoEliminacion=0 and c.idResolucionAdj.idParticipante.idOferta.estadoEliminacion=0 and c.idResolucionAdj.idParticipante.idOferta.idDetProcesoAdq=:idProceso and c.idContrato not in (SELECT d.idContrato.idContrato FROM DetalleCredito d WHERE d.estadoEliminacion=0 and d.idCredito.idDetProcesoAdq=:idProceso and d.idCredito.numeroNit=:numeroNit) order by c.idResolucionAdj.idParticipante.idOferta.codigoEntidad.codigoEntidad", ContratosOrdenesCompras.class);
        q.setParameter("idEmpresa", empresa);
        q.setParameter("idProceso", proceso);

        q.setParameter("numeroNit", empresa.getNumeroNit());

        lst.addAll(q.getResultList());
        
        q = em.createQuery("SELECT r.idContrato FROM ResolucionesModificativas r WHERE r.idEstadoReserva=2 and r.estadoEliminacion=0 and r.idContrato.idResolucionAdj.idParticipante.idEmpresa=:idEmpresa and r.idContrato.idResolucionAdj.idParticipante.idOferta.idDetProcesoAdq=:idProceso and r.idContrato.idContrato not in (SELECT d.idContrato.idContrato FROM DetalleCredito d WHERE d.estadoEliminacion=0 and d.idCredito.idDetProcesoAdq=:idProceso and d.idCredito.numeroNit=:numeroNit) ", ContratosOrdenesCompras.class);
        q.setParameter("idEmpresa", empresa);
        q.setParameter("idProceso", proceso);

        q.setParameter("numeroNit", empresa.getNumeroNit());
        
        lst.addAll(q.getResultList());
        
        return lst;
    }

    public List<ResumenCreditosDto> generarResumen(BigDecimal idAnho) {
        Query q = em.createNamedQuery("Credito.ResumenGenralDeCreditos", ResumenCreditosDto.class);
        q.setParameter(1, idAnho);
        return q.getResultList();
    }

    public List<CreditoProveedorDto> getCreditosActivosPorProveedor(String codigoDepartamento, DetalleProcesoAdq idDetProceso) {
        String strWhere = "";
        if (codigoDepartamento != null) {
            if (!codigoDepartamento.equals("00")) {
                strWhere = Constantes.addCampoToWhere("", "COD_DEPA_EMP", codigoDepartamento);
            }
        }
        if (idDetProceso != null) {
            strWhere = Constantes.addCampoToWhere(strWhere, "ID_DET_PROCESO_ADQ", idDetProceso.getIdDetProcesoAdq());
        }

        Query query = em.createNativeQuery(Constantes.QUERY_CREDITO_ACTIVOS_POR_PROVEEDOR + strWhere + " ORDER BY monto_credito,id_credito,codigo_entidad", CreditoProveedorDto.class);

        return query.getResultList();
    }

    public List<DatosProveedoresFinanDto> buscarListadoProveedor(BigDecimal rubro, EntidadFinanciera entidadSeleccionado, DetalleProcesoAdq proceso, BigInteger estadoCredito) {
        String cadenaWhere = Constantes.addCampoToWhere("", "id_rubro_interes", rubro);

        if (entidadSeleccionado != null && !entidadSeleccionado.getCodEntFinanciera().equals("00000")) {
            cadenaWhere = Constantes.addCampoToWhere(cadenaWhere, "cod_ent_financiera", entidadSeleccionado.getCodEntFinanciera());
        }

        cadenaWhere = Constantes.addCampoToWhere(cadenaWhere, "ID_DET_PROCESO_ADQ", proceso.getIdDetProcesoAdq());
        cadenaWhere = Constantes.addCampoToWhere(cadenaWhere, "credito_activo", estadoCredito);

        if (!cadenaWhere.isEmpty()) {
            cadenaWhere = cadenaWhere + " AND monto_credito is not null";
        }

        em.clear();
        Query q = em.createNativeQuery(Constantes.QUERY_RPT_PROVEEDOR_ENT_FINAN + cadenaWhere, DatosProveedoresFinanDto.class);
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
