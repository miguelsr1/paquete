package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.Canton;
import sv.gob.mined.paquescolar.model.Municipio;

@Stateless
@LocalBean
public class UtilEJB {

    private final List<SelectItem> lstDocumentosImp = new ArrayList<>();
    private final SelectItem garantiaUsoTela = new SelectItem(6, "Garantía  de buen uso y resguardo de la tela");

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    public void executeSql(String sql) {
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();
        em.flush();
    }

    /**
     * @param <T>
     * @param type
     * @param o
     * @return
     */
    public <T extends Object> T find(Class<T> type, Object o) {
        if (o == null) {
            return null;
        } else {
            return em.find(type, o);
        }
    }

    public void refreshEntity(Object obj) {
        em.refresh(obj);
    }

    public void updateEntity(Object obj) {
        em.merge(obj);
    }

    public void createEntity(Object obj) {
        em.persist(obj);
    }

    public Municipio getMunicipioPrimerByDepartamento(String departamento) {
        Query q = em.createQuery("SELECT  m FROM Municipio m WHERE m.codigoDepartamento.codigoDepartamento=:depa", Municipio.class);
        q.setParameter("depa", departamento);
        return (Municipio) q.getResultList().get(0);
    }

    public List<SelectItem> getLstDocumentosImp(Boolean uniforme, Integer idAnho) {
        if (lstDocumentosImp.isEmpty()) {
            //Id son los mismos que estan el la tabla TIPO_RPT
            lstDocumentosImp.add(new SelectItem(7, "Contrato"));
            lstDocumentosImp.add(new SelectItem(5, "Garantía Contrato"));
            lstDocumentosImp.add(new SelectItem(4, "Nota Adjudicación"));
            lstDocumentosImp.add(new SelectItem(3, "Acta Adjudicación"));
            if (idAnho != 10) {
                lstDocumentosImp.add(new SelectItem(12, "Orden de Inicio"));
            }
            lstDocumentosImp.add(new SelectItem(10, "Declaración Adjudicatorio"));
            lstDocumentosImp.add(new SelectItem(13, "Acta de Recomendación"));
            lstDocumentosImp.add(new SelectItem(2, "Cotización"));
            lstDocumentosImp.add(new SelectItem(11, "Oferta Global del Proveedor"));

        }
        if (uniforme) {
            if (!lstDocumentosImp.contains(garantiaUsoTela)) {
                lstDocumentosImp.add(garantiaUsoTela);
            }
        } else {
            lstDocumentosImp.remove(garantiaUsoTela);
        }

        return lstDocumentosImp;
    }

    public String getValorDeParametro(String nombreParametro) {
        Query q = em.createNativeQuery("SELECT valor_parametro FROM parametros WHERE nombre_parametro = ?1");
        q.setParameter(1, nombreParametro);
        if (q.getResultList().isEmpty()) {
            return "";
        } else {
            return q.getResultList().get(0).toString();
        }
    }

    public void guardarCanton(String codigoCanton, String nombreCanton, Integer idMunicipio) {
        Canton canton = new Canton();
        canton.setCodigoCanton(codigoCanton);
        canton.setIdMunicipio(new BigInteger(idMunicipio.toString()));
        canton.setNombreCanton(nombreCanton);

        em.persist(canton);
    }

    public Integer findMunicipioByCodigo(String codigoDepartamento, String codigoMunicipio) {
        Query q = em.createQuery("SELECT m FROM Municipio m WHERE m.codigoDepartamento.codigoDepartamento=:codDepa and m.codigoMunicipio=:codMun", Municipio.class);
        q.setParameter("codDepa", codigoDepartamento);
        q.setParameter("codMun", codigoMunicipio);

        if (!q.getResultList().isEmpty()) {
            return ((Municipio) q.getSingleResult()).getIdMunicipio().intValue();
        } else {
            return 0;
        }
    }

    public List<String> getLstCcPagoByCodDepa(String codigoDepartamento) {
        Query q = em.createQuery("SELECT l.cuentaCorreo FROM ListaNotificacionPago l wHERE l.codigoDepartamento=:codDepa and l.activo=1");
        q.setParameter("codDepa", codigoDepartamento);
        return q.getResultList();
    }

    /*public void iniciar() {

        Query q = em.createQuery("SELECT DISTINCT c.idResolucionAdj.idParticipante.idEmpresa FROM ContratosOrdenesCompras c WHERE c.idResolucionAdj.idParticipante.idOferta.idDetProcesoAdq.idDetProcesoAdq=64 and c.idResolucionAdj.idEstadoReserva.idEstadoReserva=2", Empresa.class);
        List<Empresa> lst = q.getResultList();

        //String rubro = "SERVICIOS DE CONFECCION DE 1er UNIFORMES";
        //String rubro = "PRODUCCION DE ZAPATOS";
        String rubro = "SUMINISTRO DE PAQUETES DE UTILES ESCOLARES";

        DetalleProcesoAdq detalleProceso = em.find(DetalleProcesoAdq.class, 64);

        for (Empresa emp : lst) {
            try {
                List<JasperPrint> lstRptAImprimir = new ArrayList<>();

                CapaInstPorRubro capacidadInst = proveedorEJB.findDetProveedor(detalleProceso.getIdRubroAdq().getIdRubroInteres(),
                        detalleProceso.getIdProcesoAdq().getIdAnho().getIdAnho(), emp, CapaInstPorRubro.class);

                /*lstRptAImprimir = Reportes.getReporteOfertaDeProveedor(capacidadInst, contrato.getIdResolucionAdj().getIdParticipante().getIdEmpresa(), detalleProceso,
                getLstOfertaGlobal(contrato.getIdResolucionAdj().getIdParticipante().getIdEmpresa().getNumeroNit(), detalleProceso.getIdRubroAdq().getIdRubroInteres(), detalleProceso.getIdProcesoAdq().getIdAnho().getIdAnho()),
                getDeclaracionJurada(contrato.getIdResolucionAdj().getIdParticipante().getIdEmpresa(), detalleProceso, emp.getIdMunicipio().getNombreMunicipio()));/
                HashMap param = new HashMap();
                param.put("ubicacionImagenes", "/media/misanchez/Datos/git_paquete/paquete/paquescolar-web/src/main/webapp/resources/images/");
                param.put("pEscudo", "/media/misanchez/Datos/git_paquete/paquete/paquescolar-web/src/main/webapp/resources/images/");
                param.put("usuarioInsercion", "ADMIN");
                param.put("pLugar", emp.getIdMunicipio().getNombreMunicipio().concat(", ").concat(emp.getIdMunicipio().getCodigoDepartamento().getNombreDepartamento()));
                param.put("pRubro", rubro);
                param.put("pIdRubro", capacidadInst.getIdMuestraInteres().getIdRubroInteres().getIdRubroInteres().intValue());
                param.put("pCorreoPersona", capacidadInst.getIdMuestraInteres().getIdEmpresa().getIdPersona().getEmail());
                param.put("pIdGestion", "");

                List<OfertaGlobal> lstDatos = reportesEJB.getLstOfertaGlobal(emp.getNumeroNit(), detalleProceso.getIdRubroAdq().getIdRubroInteres(), detalleProceso.getIdProcesoAdq().getIdAnho().getIdAnho());
                lstDatos.get(0).setRubro(rubro);
                if (lstDatos.get(0).getDepartamento().contains("TODO EL PAIS")) {
                    param.put("productor", Boolean.TRUE);
                } else {
                    param.put("productor", Boolean.FALSE);
                }

                lstRptAImprimir.add(JasperFillManager.fillReport(
                        new FileInputStream("/opt/soporte/paquete/archivos/reportes/sv/gob/mined/apps/reportes/oferta" + File.separator + "rptOfertaGlobalProv" + detalleProceso.getIdProcesoAdq().getIdAnho().getAnho() + ".jasper"),
                        param, new JRBeanCollectionDataSource(lstDatos)));

                OutputStream output = new FileOutputStream(new File("/home/misanchez/Escritorio/Temp/utiles/" + emp.getNumeroNit() + ".pdf"));
                JasperExportManager.exportReportToPdfStream(lstRptAImprimir.get(0), output);

                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, lstRptAImprimir);
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.FALSE);
                exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, output);
                exporter.exportReport();
                output.close();
            } catch (JRException | FileNotFoundException ex) {
                Logger.getLogger(UtilEJB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(UtilEJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }*/
    public Integer getIdProcesoAdqAnt(BigDecimal idAnho) {
        Query q = em.createQuery("SELECT p.idProcesoAdq FROM ProcesoAdquisicion p wHERE p.idAnho.idAnho=:pIdAnho and p.padreIdProcesoAdq is null");
        q.setParameter("pIdAnho", idAnho.add(BigDecimal.ONE.negate()));
        return (Integer) q.getSingleResult();
    }
}
