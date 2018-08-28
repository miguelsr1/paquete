/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.DisMunicipioInteres;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.PreciosRefRubro;
import sv.gob.mined.paquescolar.model.PreciosRefRubroEmp;
import sv.gob.mined.paquescolar.model.pojos.DeclaracionJurada;
import sv.gob.mined.paquescolar.model.pojos.DetItemOfertaGlobal;
import sv.gob.mined.paquescolar.model.pojos.DetMunIntOfertaGlobal;
import sv.gob.mined.paquescolar.model.pojos.OfertaGlobal;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class ReportesEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    /**
     * Llenado de un reporte que tiene como fuente de datos una consulta SQL
     *
     * @param input
     * @param map
     * @return
     */
    public JasperPrint getRpt(HashMap map, InputStream input) {
        try {
            JasperPrint jp;
            Connection conn = em.unwrap(java.sql.Connection.class);
            jp = JasperFillManager.fillReport(input, map, conn);
            return jp;
        } catch (JRException ex) {
            Logger.getLogger(ReportesEJB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Llenado de un reporte a partir de un DataSource del tipo BeanCollection
     *
     * @param input
     * @param map
     * @param lst
     * @return
     */
    public JasperPrint getRpt(HashMap map, InputStream input, List lst) {
        try {
            JasperPrint jp;

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lst);

            jp = JasperFillManager.fillReport(input, map, ds);
            return jp;
        } catch (JRException ex) {
            Logger.getLogger(ReportesEJB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public List<OfertaGlobal> getLstOfertaGlobal(String nit, Integer idDetProcesoAdq, int idRubro) {
        PreciosRefRubroEmp preTem;
        List<OfertaGlobal> lstRpt;
        //Query q = em.createNativeQuery("SELECT * FROM VW_RPT_CABECERA_OFERTA_GLOBAL WHERE nit_empresa=?1 and id_det_proceso_adq=?2");
        Query q = em.createNamedQuery("DatosProveDto.ofertaGlobal", OfertaGlobal.class);
        q.setParameter(1, nit);
        q.setParameter(2, idDetProcesoAdq);

        lstRpt = q.getResultList();
        //for (Object object : lst) {
        //Object[] datos = (Object[]) object;
        //OfertaGlobal oferta = new OfertaGlobal();

        lstRpt.get(0).setAnho(em.find(DetalleProcesoAdq.class, idDetProcesoAdq).getIdProcesoAdq().getIdAnho().getAnho());

        q = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.estadoEliminacion=0 and p.idEmpresa.numeroNit=:nit and p.idDetProcesoAdq.idDetProcesoAdq=:idDetProcesoAdq AND p.idProducto.codigoProducto not in (1) ORDER BY  FUNC('TO_NUMBER', p.noItem)", PreciosRefRubroEmp.class);
        q.setParameter("nit", nit);
        q.setParameter("idDetProcesoAdq", idDetProcesoAdq);
        List<PreciosRefRubroEmp> lstPrecios = q.getResultList();

        q = em.createQuery("SELECT p FROM PreciosRefRubroEmp p WHERE p.estadoEliminacion=0 and p.idEmpresa.numeroNit=:nit and p.idDetProcesoAdq.idDetProcesoAdq=:idDetProcesoAdq AND p.idProducto.codigoProducto in (1) ORDER BY p.noItem", PreciosRefRubroEmp.class);
        q.setParameter("nit", nit);
        q.setParameter("idDetProcesoAdq", idDetProcesoAdq);
        List<PreciosRefRubroEmp> lstPreciosLibros = q.getResultList();

        //q = em.createQuery("select p from PreciosRefRubroEmp p where p.idDetProcesoAdq.idDetProcesoAdq = ?1 order by p.idNivelEducativo", PreciosRefRubro.class);
        q = em.createNativeQuery("select prr.* from PRECIOS_REF_RUBRO prr inner join NIVEL_EDUCATIVO niv on niv.ID_NIVEL_EDUCATIVO = prr.ID_NIVEL_EDUCATIVO where ID_DET_PROCESO_ADQ = ?1 order by niv.ORDEN2", PreciosRefRubro.class);
        q.setParameter(1, idDetProcesoAdq);
        List<PreciosRefRubro> lstPrecioMax = q.getResultList();

        switch (idRubro) {
            case 1:
            case 4:
                for (int i = 0; i < 13; i++) {
                    DetItemOfertaGlobal det = new DetItemOfertaGlobal();

                    switch ((i + 1)) {
                        case 1:
                            det.setDescripcionItem("Confección Blusa Parvularia");
                            det.setPrecioMaxReferencia(new BigDecimal("4.25"));
                            break;
                        case 2:
                            det.setDescripcionItem("Confección Falda Parvularia");
                            det.setPrecioMaxReferencia(new BigDecimal("4.25"));
                            break;
                        case 3:
                            det.setDescripcionItem("Confección Camisa Parvularia");
                            det.setPrecioMaxReferencia(new BigDecimal("4.25"));
                            break;
                        case 4:
                            det.setDescripcionItem("Confección Pantalón Corto Parvularia");
                            det.setPrecioMaxReferencia(new BigDecimal("4.00"));
                            break;
                        case 5:
                            det.setDescripcionItem("Confección Pantalón niño y niña de Parvularia zona con clima templado arriba de los 1000 metros sobre el nivel del mar");
                            det.setPrecioMaxReferencia(new BigDecimal("6.00"));
                            break;
                        case 6:
                            det.setDescripcionItem("Confección Blusa Básica (I, II y III ciclo)");
                            det.setPrecioMaxReferencia(new BigDecimal("4.50"));
                            break;
                        case 7:
                            det.setDescripcionItem("Confección Falda Básica (I, II y III ciclo)");
                            det.setPrecioMaxReferencia(new BigDecimal("4.50"));
                            break;
                        case 8:
                            det.setDescripcionItem("Confección Camisa Básica (I, II y III ciclo)");
                            det.setPrecioMaxReferencia(new BigDecimal("4.50"));
                            break;
                        case 9:
                            det.setDescripcionItem("Confección Pantalón Básica (I, II y III ciclo)");
                            det.setPrecioMaxReferencia(new BigDecimal("6.00"));
                            break;
                        case 10:
                            det.setDescripcionItem("Confección Blusa Bachillerato");
                            det.setPrecioMaxReferencia(new BigDecimal("4.50"));
                            break;
                        case 11:
                            det.setDescripcionItem("Confección Falda Bachillerato");
                            det.setPrecioMaxReferencia(new BigDecimal("4.50"));
                            break;
                        case 12:
                            det.setDescripcionItem("Confección Camisa Bachillerato");
                            det.setPrecioMaxReferencia(new BigDecimal("4.50"));
                            break;
                        case 13:
                            det.setDescripcionItem("Confección Pantalón Bachillerato");
                            det.setPrecioMaxReferencia(new BigDecimal("6.00"));
                            break;
                    }

                    for (PreciosRefRubroEmp preciosRefRubroEmp : lstPrecios) {
                        if ((i + 1) == Integer.parseInt(preciosRefRubroEmp.getNoItem())) {
                            det.setPrecioUnitario(preciosRefRubroEmp.getPrecioReferencia());
                            break;
                        }
                    }
                    lstRpt.get(0).getLstDetItemOfertaGlobal().add(det);
                }
                break;
            case 2:
                for (int i = 0; i < 5; i++) {
                    DetItemOfertaGlobal det = new DetItemOfertaGlobal();

                    switch ((i + 1)) {
                        case 1:
                            det.setDescripcionItem("Paquete de útiles escolares parvularia");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(0).getPrecioMaxMas());
                            break;
                        case 2:
                            det.setDescripcionItem("Paquete de útiles escolares primer ciclo");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(1).getPrecioMaxMas());
                            break;
                        case 3:
                            det.setDescripcionItem("Paquete de útiles escolares segundo ciclo");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(2).getPrecioMaxMas());
                            break;
                        case 4:
                            det.setDescripcionItem("Paquete de útiles escolares tercer ciclo");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(3).getPrecioMaxMas());
                            break;
                        case 5:
                            det.setDescripcionItem("Paquete de útiles escolares bachillerato");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(4).getPrecioMaxMas());
                            break;
                    }

                    preTem = getPrecioMax(lstPrecios, String.valueOf(i + 1));
                    if (preTem != null) {
                        det.setPrecioUnitario(preTem.getPrecioReferencia());
                    }
                    lstRpt.get(0).getLstDetItemOfertaGlobal().add(det);
                }

                for (PreciosRefRubroEmp lstPreciosLibro : lstPreciosLibros) {
                    DetItemOfertaGlobal det = new DetItemOfertaGlobal();
                    switch (lstPreciosLibro.getNoItem()) {
                        case "2.1":
                            det.setDescripcionItem("Libros de Matemáticas 1er Grado (2 Tomos)");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(5).getPrecioMaxMas());
                            break;
                        case "2.2":
                            det.setDescripcionItem("Libros de Matemáticas 2do Grado (2 Tomos)");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(6).getPrecioMaxMas());
                            break;
                        case "2.3":
                            det.setDescripcionItem("Libros de Matemáticas 3er Grado (Texto y Trabajo)");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(7).getPrecioMaxMas());
                            break;
                        case "3.1":
                            det.setDescripcionItem("Libros de Matemáticas 4to Grado (Texto y Trabajo)");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(8).getPrecioMaxMas());
                            break;
                        case "3.2":
                            det.setDescripcionItem("Libros de Matemáticas 5to Grado (Texto y Trabajo)");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(9).getPrecioMaxMas());
                            break;
                        case "3.3":
                            det.setDescripcionItem("Libros de Matemáticas 6to Grado (Texto y Trabajo)");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(10).getPrecioMaxMas());
                            break;
                        case "4.1":
                            det.setDescripcionItem("Libros de Matemáticas 7mo Grado (Texto y Trabajo)");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(11).getPrecioMaxMas());
                            break;
                        case "4.2":
                            det.setDescripcionItem("Libros de Matemáticas 8vo Grado (Texto y Trabajo)");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(12).getPrecioMaxMas());
                            break;
                        case "4.3":
                            det.setDescripcionItem("Libros de Matemáticas 9no Grado (Texto y Trabajo)");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(13).getPrecioMaxMas());
                            break;
                        case "5.1":
                            det.setDescripcionItem("Libros de Matemáticas 1er Año de Bachillerato (Texto)");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(14).getPrecioMaxMas());
                            break;
                        case "5.2":
                            det.setDescripcionItem("Libros de Matemáticas 2do Año de Bachillerato (Texto)");
                            det.setPrecioMaxReferencia(lstPrecioMax.get(15).getPrecioMaxMas());
                            break;
                    }
                    
                    preTem = getPrecioMax(lstPreciosLibros, lstPreciosLibro.getNoItem());
                    if (preTem != null) {
                        det.setPrecioUnitario(preTem.getPrecioReferencia());
                    }

                    lstRpt.get(0).getLstDetItemOfertaGlobalLibros().add(det);
                }
                break;
            case 3:
                for (int i = 0; i < 10; i++) {
                    DetItemOfertaGlobal det = new DetItemOfertaGlobal();

                    switch ((i + 1)) {
                        case 1:
                            det.setDescripcionItem("Zapatos para niña, parvularia");
                            det.setPrecioMaxReferencia(new BigDecimal("14.60"));
                            break;
                        case 2:
                            det.setDescripcionItem("Zapatos para niño, parvularia");
                            det.setPrecioMaxReferencia(new BigDecimal("14.60"));
                            break;
                        case 3:
                            det.setDescripcionItem("Zapatos para niña, primer ciclo");
                            det.setPrecioMaxReferencia(new BigDecimal("14.60"));
                            break;
                        case 4:
                            det.setDescripcionItem("Zapatos para niño, primer ciclo");
                            det.setPrecioMaxReferencia(new BigDecimal("14.60"));
                            break;
                        case 5:
                            det.setDescripcionItem("Zapatos para niña, segundo ciclo");
                            det.setPrecioMaxReferencia(new BigDecimal("14.60"));
                            break;
                        case 6:
                            det.setDescripcionItem("Zapatos para niño, segundo ciclo");
                            det.setPrecioMaxReferencia(new BigDecimal("14.60"));
                            break;
                        case 7:
                            det.setDescripcionItem("Zapatos para niña, tercer ciclo");
                            det.setPrecioMaxReferencia(new BigDecimal("14.60"));
                            break;
                        case 8:
                            det.setDescripcionItem("Zapatos para niño, tercer ciclo");
                            det.setPrecioMaxReferencia(new BigDecimal("14.60"));
                            break;
                        case 9:
                            det.setDescripcionItem("Zapatos para niña, bachillerato");
                            det.setPrecioMaxReferencia(new BigDecimal("16.00"));
                            break;
                        case 10:
                            det.setDescripcionItem("Zapatos para niño, bachillerato");
                            det.setPrecioMaxReferencia(new BigDecimal("16.00"));
                            break;
                    }

                    for (PreciosRefRubroEmp preciosRefRubroEmp : lstPrecios) {
                        if ((i + 1) == Integer.parseInt(preciosRefRubroEmp.getNoItem())) {
                            det.setPrecioUnitario(preciosRefRubroEmp.getPrecioReferencia());
                            break;
                        }
                    }
                    lstRpt.get(0).getLstDetItemOfertaGlobal().add(det);
                }
                break;
        }

        q = em.createQuery("SELECT d FROM DisMunicipioInteres d WHERE d.estadoEliminacion=0 and d.idCapaDistribucion.idMuestraInteres.idEmpresa.numeroNit=:nit and d.idCapaDistribucion.idMuestraInteres.idDetProcesoAdq.idDetProcesoAdq=:idDetProcesoAdq ORDER BY d.idMunicipio.codigoDepartamento.codigoDepartamento, d.idMunicipio.codigoMunicipio ASC", DisMunicipioInteres.class);
        q.setParameter("nit", nit);
        q.setParameter("idDetProcesoAdq", idDetProcesoAdq);
        List<DisMunicipioInteres> lstMunicipios = q.getResultList();

        for (DisMunicipioInteres disMunicipioInteres : lstMunicipios) {
            DetMunIntOfertaGlobal detMun = new DetMunIntOfertaGlobal();
            detMun.setCodigoDepartamento(disMunicipioInteres.getIdMunicipio().getCodigoDepartamento().getCodigoDepartamento());
            detMun.setNombreDepartamento(disMunicipioInteres.getIdMunicipio().getCodigoDepartamento().getNombreDepartamento());
            detMun.setCodigoMunicipio(disMunicipioInteres.getIdMunicipio().getCodigoMunicipio());
            detMun.setNombreMunicipio(disMunicipioInteres.getIdMunicipio().getNombreMunicipio());

            lstRpt.get(0).getLstMunIntOfertaGlobal().add(detMun);
        }

        //lstRpt.add(oferta);
        //}
        return lstRpt;
    }

    public PreciosRefRubroEmp getPrecioMax(List<PreciosRefRubroEmp> lstPrecioMax, final String noItem) {
        return ((PreciosRefRubroEmp) CollectionUtils.find(lstPrecioMax, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return (((PreciosRefRubroEmp) o).getNoItem().equals(noItem));
            }
        }));
    }

    public List<DeclaracionJurada> getDeclaracionJurada(Empresa empresa, DetalleProcesoAdq idDetProcesoAdq, String ciudad) {
        List<DeclaracionJurada> lstDeclaracion = new ArrayList();
        Query q = em.createNativeQuery("SELECT * FROM VW_RPT_DECLARACION_JURADA2 WHERE nit_empresa=?1 and id_det_proceso_adq=?2");
        q.setParameter(1, empresa.getNumeroNit());
        q.setParameter(2, idDetProcesoAdq.getIdDetProcesoAdq());

        List lst = q.getResultList();
        for (Object object : lst) {
            Object[] datos = (Object[]) object;

            DeclaracionJurada decla = new DeclaracionJurada();
            decla.setCiudad(ciudad);
            decla.setFecha(new Date());
            decla.setRubro(datos[0].toString());
            decla.setAnho(datos[1].toString());
            decla.setRazonSocial(datos[2].toString());
            decla.setRepresentanteLegal(datos[3].toString());
            decla.setNitEmpresa(datos[4].toString());
            decla.setDireccionEmpresa(datos[5].toString());
            decla.setNitRepre(datos[6].toString());
            decla.setDuiRepre(datos[7].toString());
            decla.setDireccionRepre(datos[8].toString());

            lstDeclaracion.add(decla);
        }
        return lstDeclaracion;
    }
}
