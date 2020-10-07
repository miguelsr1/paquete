/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.envio.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.jboss.ejb3.annotation.TransactionTimeout;
import sv.gob.mined.envio.model.DetalleEnvio;
import sv.gob.mined.envio.model.EnvioMasivo;

/**
 *
 * @author MISanchez
 */
@Singleton
public class PersistenciaFacade {

    @PersistenceContext(unitName = "sv.gob.mined_envio-web_war_1.0PU")
    private EntityManager em;

    public EnvioMasivo findEnvio(BigDecimal idEnvio) {
        return em.find(EnvioMasivo.class, idEnvio);
    }

    /*public BigDecimal guardarEnvio(String correo, String titulo, String mensaje, String archivo) {
        EnvioMasivo eMasivo = new EnvioMasivo();
        eMasivo.setArchivo(archivo);
        eMasivo.setCorreRemitente(correo);
        eMasivo.setFechaEnvio(new Date());
        eMasivo.setMensaje(mensaje);
        eMasivo.setTitulo(titulo);

        em.persist(eMasivo);

        return eMasivo.getIdEnvio();
    }*/
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @TransactionTimeout(value = 120, unit = TimeUnit.MINUTES)
    public BigDecimal guardarEnvio(EnvioMasivo eMasivo) {
        em.persist(eMasivo);

        return eMasivo.getIdEnvio();
    }

    /*public void guardarDetalleEnviado(String nip, String nombre, String correo, EnvioMasivo idEnvio, Boolean enviado) {
        DetalleEnvio de = new DetalleEnvio();
        de.setCorreoDestinatario(correo);
        de.setIdEnvio(idEnvio);
        de.setNip(nip);
        de.setNombreDestinatario(nombre);
        de.setEnviado(enviado ? (short) 1 : 0);

        em.persist(de);
    }*/
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<DetalleEnvio> findDetalleEnvio(BigDecimal idEnvio) {
        Query q = em.createQuery("SELECT d FROM DetalleEnvio d WHERE d.enviado=0 and d.idEnvio.idEnvio=:idEnvio ORDER BY d.idDetalle", DetalleEnvio.class);
        q.setParameter("idEnvio", idEnvio);
        return q.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void actualizarDetalleEnviado(List<BigDecimal> detalleEnvios) {
        List<BigDecimal> param = new ArrayList<>();
        for (BigDecimal detalleEnvio : detalleEnvios) {
            param.add(detalleEnvio);
            if (param.size() == 999) {
                Query q = em.createQuery("UPDATE DetalleEnvio d set d.enviado=1 WHERE d.idDetalle in :list");
                q.setParameter("list", param);
                q.executeUpdate();

                param.clear();
            }
        }

        if (!param.isEmpty()) {
            Query q = em.createQuery("UPDATE DetalleEnvio d set d.enviado=1 WHERE d.idDetalle in :list");
            q.setParameter("list", param);
            q.executeUpdate();
        }
    }
}