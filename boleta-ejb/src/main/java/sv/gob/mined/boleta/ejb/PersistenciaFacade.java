/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.ejb;

import java.io.File;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.boleta.model.CodigoGenerado;
import sv.gob.mined.boleta.model.CorreoDocente;
import sv.gob.mined.boleta.model.DetalleCodigo;
import sv.gob.mined.boleta.model.DominiosCorreo;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class PersistenciaFacade {

    @PersistenceContext(unitName = "boletaPU")
    private EntityManager em;

    public void guardarCodigoGeneradoPorNip(Integer idCodigo, String nip, String codigoGenerado) {
        DetalleCodigo detalleCodigo = new DetalleCodigo();
        detalleCodigo.setCodigoGenerado(codigoGenerado);
        detalleCodigo.setIdCodigo(idCodigo);
        detalleCodigo.setNip(nip);

        em.persist(detalleCodigo);
    }

    public Integer getPkCodigoGeneradoByCodDepaAndMesAnho(String codDepa, String mesAnho) {
        CodigoGenerado codigoGenerado;

        Query q = em.createQuery("SELECT c FROM CodigoGenerado c WHERE c.codigoDepartamento=:codDepa AND c.mesAnho=:mesAnho", CodigoGenerado.class);
        q.setParameter("codDepa", codDepa);
        q.setParameter("mesAnho", mesAnho);
        if (q.getResultList().isEmpty()) {
            codigoGenerado = new CodigoGenerado();
            codigoGenerado.setMesAnho(mesAnho);
            codigoGenerado.setCodigoDepartamento(codDepa);
            codigoGenerado.setFechaInicio(new Date());
            em.persist(codigoGenerado);
        } else {
            codigoGenerado = (CodigoGenerado) q.getResultList().get(0);
        }
        return codigoGenerado.getIdCodigo();
    }

    public String getDetalleCodigo(String codigoGenerado) {
        Query q = em.createQuery("SELECT d FROM DetalleCodigo d WHERE d.codigoGenerado=:codigo", DetalleCodigo.class);
        q.setParameter("codigo", codigoGenerado);

        DetalleCodigo det = (DetalleCodigo) q.getResultList().get(0);

        q = em.createQuery("SELECT c FROM CodigoGenerado c WHERE c.idCodigo=:id", CodigoGenerado.class);
        q.setParameter("id", det.getIdCodigo());
        CodigoGenerado cod = (CodigoGenerado) q.getSingleResult();

        return cod.getCodigoDepartamento() + File.separator + cod.getMesAnho() + File.separator + "procesado" + File.separator + det.getNip() + ".pdf";
    }

    public Integer getCantidadDeBoletasEnviadas(String codigoDepartamento) {
        Query q = em.createNativeQuery("select count(distinct nip) num_docentes from detalle_codigo dc inner join codigo_generado cg on dc.id_codigo = cg.id_codigo where cg.codigo_Departamento=:codDepa");
        q.setParameter("codDepa", codigoDepartamento);
        return (Integer) q.getSingleResult();
    }

    public CodigoGenerado getCodigoGenerado(String codigoDepartamento, String mesAnho) {
        Query q = em.createQuery("SELECT c FROM CodigoGenerado c WHERE c.codigoDepartamento=:codDepa and c.mesAnho=:mesAnho", CodigoGenerado.class);
        q.setParameter("codDepa", codigoDepartamento);
        q.setParameter("mesAnho", mesAnho);
        return q.getResultList().isEmpty() ? new CodigoGenerado() : (CodigoGenerado) q.getResultList().get(0);
    }

    public CodigoGenerado registrarFinDeProcesoDeEnvio(String pathRoot, String codigoDepartamento, String mesAnho) {
        CodigoGenerado codigoGenerado = getCodigoGenerado(codigoDepartamento, mesAnho);

        //calcular boletas no procesadas, no enviadas y enviadas
        File folderRoot = new File(pathRoot + File.separator + codigoDepartamento + File.separator + mesAnho + File.separator);
        for (File folder : folderRoot.listFiles()) {
            if (folder.isDirectory()) {
                switch (folder.getName()) {
                    case "no_encontrado":
                        codigoGenerado.setSinCorreo(folder.listFiles().length);
                        break;
                    case "errores":
                        codigoGenerado.setError(folder.listFiles().length);
                        break;
                    case "procesado":
                        codigoGenerado.setEnviado(folder.listFiles().length);
                        break;
                }
            }
        }

        codigoGenerado.setFechaFin(new Date());

        em.merge(codigoGenerado);

        return codigoGenerado;
    }

    public List<DominiosCorreo> getLstDominiosCorreo() {
        Query q = em.createQuery("SELECT d FROM DominiosCorreo d ORDER BY d.dominio", DominiosCorreo.class);
        return q.getResultList();
    }

    public void guardarDominio(DominiosCorreo dominio) {
        if (dominio.getIdDominio() == null) {
            em.persist(dominio);
        } else {
            em.merge(dominio);
        }
    }

    public int guardarDocente(CorreoDocente correo, String usuario) {
        if (correo.getIdCorreo() == null) {
            Query q = em.createQuery("SELECT c FROM CorreoDocente c WHERE c.nip=:nip or c.correoElectronico=:correo", CorreoDocente.class);
            q.setParameter("nip", correo.getNip());
            q.setParameter("correo", correo.getCorreoElectronico());
            if (q.getResultList().isEmpty()) {
                correo.setFechaInsercion(new Date());
                correo.setUsuarioInsercion(usuario);
                em.persist(correo);
                return 1;
            } else {
                return 0;
            }
        } else {
            correo.setFechaModificacion(new Date());
            correo.setUsuarioModificacion(usuario);
            em.merge(correo);
            return 1;
        }
    }

    public List<CorreoDocente> getLstCorreoDocentes() {
        Query q = em.createQuery("SELECT c FROM CorreoDocente c ORDER BY c.idCorreo", CorreoDocente.class);
        return q.getResultList();
    }

    public List<CorreoDocente> getLstCorreoDocenteByCriterio(String criterio) {
        Query q = em.createQuery("SELECT c FROM CorreoDocente c WHERE c.nip like :nip or c.correoElectronico like :correo ORDER BY c.idCorreo", CorreoDocente.class);
        q.setParameter("nip", "%" + criterio + "%");
        q.setParameter("correo", "%" + criterio + "%");
        return q.getResultList();
    }
}
