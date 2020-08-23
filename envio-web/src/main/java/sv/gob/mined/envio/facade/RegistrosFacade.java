/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.envio.facade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.envio.model.DetalleEnvio;
import sv.gob.mined.envio.model.EnvioMasivo;

/**
 *
 * @author MISanchez
 */
@Stateless
@LocalBean
public class RegistrosFacade {

    @PersistenceContext(unitName = "sv.gob.mined_envio-web_war_1.0PU")
    private EntityManager em;

    @EJB
    private EMailFacade eMailFacade;

    public BigDecimal guardarEnvio(String correo, String titulo, String mensaje, String archivo) {
        EnvioMasivo eMasivo = new EnvioMasivo();
        eMasivo.setArchivo(archivo);
        eMasivo.setCorreRemitente(correo);
        eMasivo.setFechaEnvio(new Date());
        eMasivo.setMensaje(mensaje);
        eMasivo.setTitulo(titulo);

        em.persist(eMasivo);

        return eMasivo.getIdEnvio();
    }

    public void guardarDetalleEnviado(String nip, String nombre, String correo, BigDecimal idEnvio, Boolean enviado) {
        DetalleEnvio de = new DetalleEnvio();
        de.setCorreoDestinatario(correo);
        de.setIdEnvio(idEnvio);
        de.setNip(nip);
        de.setNombreDestinatario(nombre);
        de.setEnviado(enviado ? (short) 1 : 0);

        em.persist(de);
    }

    public List<DetalleEnvio> findDetalleEnvio(BigDecimal idEnvio) {
        Query q = em.createQuery("SELECT d FROM DetalleEnvio d WHERE d.enviado=0 and d.idEnvio=" + idEnvio, DetalleEnvio.class);
        return q.getResultList();
    }

    public void enviarCorreos(String correoRemitente, String titulo, String mensaje, DetalleEnvio detalleEnvio, Session sessionMail) {
        String msjTemp;

        msjTemp = mensaje.replace(":DOCENTE:", detalleEnvio.getNombreDestinatario().concat(" - ").concat(detalleEnvio.getNip()));

        if (eMailFacade.enviarMail(detalleEnvio.getCorreoDestinatario(),
                correoRemitente,
                titulo,
                msjTemp,
                sessionMail)) {
            detalleEnvio.setEnviado((short) 1);
            em.merge(detalleEnvio);
        }
    }
}
