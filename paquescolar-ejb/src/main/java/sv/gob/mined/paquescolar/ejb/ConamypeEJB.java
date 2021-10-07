/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sv.gob.mined.paquescolar.model.CapaDistribucionAcre;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.CatalogoProducto;
import sv.gob.mined.paquescolar.model.Departamento;
import sv.gob.mined.paquescolar.model.DetCapaSegunRubro;
import sv.gob.mined.paquescolar.model.DetRubroMuestraInteres;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.EmpresaCodigoSeg;
import sv.gob.mined.paquescolar.model.EstadoRegistro;
import sv.gob.mined.paquescolar.model.Genero;
import sv.gob.mined.paquescolar.model.Municipio;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.Persona;
import sv.gob.mined.paquescolar.model.Productor;
import sv.gob.mined.paquescolar.model.ProveedorEmpresa;
import sv.gob.mined.paquescolar.model.RubrosAmostrarInteres;
import sv.gob.mined.paquescolar.model.TipoEmpresa;
import sv.gob.mined.paquescolar.model.TipoPersoneria;
import sv.gob.mined.paquescolar.util.RC4Crypter;

/**
 *
 * @author misanchez
 */
@WebService(name = "ConamypeEJB")
@Stateless
public class ConamypeEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;
    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;
    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private UtilEJB utilEJB;
    @EJB
    private EMailEJB eMailEJB;

    @WebMethod
    public void setDatosProveedor(String jsonString, String clave) {
        Boolean activar = false;

        switch (clave) {
            case "CONAMYPE_MINED2019":
                if (isActivoWsConamype()) {
                    activar = true;
                } else {
                    Logger.getLogger(ConamypeEJB.class.getName()).log(Level.INFO, "WS NO ACTIVO");
                }
                break;
            case "PRUEBAS_MINED":
                activar = true;
                break;
            default:
                Logger.getLogger(ConamypeEJB.class.getName()).log(Level.INFO, null, "Error en la clave de acceso");
                break;
        }

        if (activar) {
            try {
                String usuario;
                JSONParser jsonParser = new JSONParser();
                JSONObject json = (JSONObject) jsonParser.parse(jsonString);

                for (Object object : json.values()) {
                    JSONObject json1 = (JSONObject) object;
                    for (Object object1 : json1.values()) {
                        JSONArray json2 = (JSONArray) object1;
                        for (Object obj : json2) {
                            JSONObject jsonObj = (JSONObject) obj;

                            Empresa empresa = proveedorEJB.findEmpresaByNit(jsonObj.get("nit").toString(), true);
                            Persona persona;
                            usuario = "CONAMYPE_" + jsonObj.get("usuario").toString();

                            if (empresa == null) {
                                //nuevo proveedor
                                empresa = new Empresa();
                                empresa.setUsuarioInsercion(usuario);
                                empresa.setFechaInsercion(new Date());
                                empresa.setEstadoEliminacion(BigInteger.ZERO);

                                persona = new Persona();
                                persona.setUsuarioCreacion(usuario);
                                persona.setFechaInsercion(new Date());
                                persona.setEstadoEliminacion(BigInteger.ZERO);
                                persona.setUrlImagen("sin_foto.png");

                                empresa.setIdPersona(persona);
                                persona.setEmpresaList(new ArrayList());
                                persona.getEmpresaList().add(empresa);
                            } else {
                                //actualizar proveedor
                                empresa.setUsuarioModificacion(usuario);
                                empresa.setFechaModificacion(new Date());

                                persona = empresa.getIdPersona();
                                persona.setUsuarioModificacion(usuario);
                                persona.setFechaModificacion(new Date());
                            }

                            empresa.setIdPersoneria(utilEJB.find(TipoPersoneria.class, new BigDecimal(jsonObj.get("personeria").toString())));
                            empresa.setIdTipoEmpresa(utilEJB.find(TipoEmpresa.class, new BigDecimal(jsonObj.get("tipo_empresa").toString())));
                            empresa.setNumeroNit(jsonObj.get("nit").toString().equals("") ? null : jsonObj.get("nit").toString());
                            empresa.setIdEstadoRegistro(utilEJB.find(EstadoRegistro.class, new BigDecimal(jsonObj.get("estado_registro").toString())));
                            empresa.setIdMunicipio(utilEJB.find(Municipio.class, new BigDecimal(jsonObj.get("id_municipio").toString())));
                            empresa.setDireccionCompleta(jsonObj.get("direccion") == null ? null : jsonObj.get("direccion").toString());
                            empresa.setCorreoElectronico(jsonObj.get("email") == null ? null : jsonObj.get("email").toString());
                            empresa.setTelefonos(jsonObj.get("telefonos") == null ? null : jsonObj.get("telefonos").toString());
                            empresa.setNumeroCelular(jsonObj.get("celular") == null ? null : jsonObj.get("celular").toString());

                            JSONObject js = (JSONObject) jsonParser.parse(jsonObj.get("representante_legal").toString());

                            persona.setNumeroNit(js.get("nit").toString());
                            persona.setIdMunicipio(utilEJB.find(Municipio.class, new BigDecimal(js.get("id_municipio").toString())));
                            persona.setIdGenero(utilEJB.find(Genero.class, new BigDecimal(js.get("id_genero").toString())));
                            persona.setPrimerNombre(js.get("primer_nombre") == null ? null : js.get("primer_nombre").toString());
                            persona.setSegundoNombre(js.get("segundo_nombre") == null ? null : js.get("segundo_nombre").toString());
                            persona.setPrimerApellido(js.get("primer_apellido") == null ? null : js.get("primer_apellido").toString());
                            persona.setSegundoApellido(js.get("segundo_apellido") == null ? null : js.get("segundo_apellido").toString());
                            persona.setAcasada(js.get("apellido_casada") == null ? null : js.get("apellido_casada").toString());
                            persona.setNumeroDui(js.get("dui") == null ? null : js.get("dui").toString());
                            persona.setNumeroTelefono(js.get("telefono") == null ? null : js.get("telefono").toString());
                            persona.setNumeroCelular(js.get("celular") == null ? null : js.get("celular").toString());
                            persona.setDomicilio(js.get("direccion") == null ? null : js.get("direccion").toString());
                            persona.setProfesion(js.get("profesion") == null ? null : js.get("profesion").toString());
                            persona.setEmail(js.get("email") == null ? null : js.get("email").toString());

                            if (empresa.getIdPersoneria().getIdPersoneria().intValue() == 1) {
                                if (js.get("apellido_casada") != null && !js.get("apellido_casada").toString().isEmpty()) {
                                    empresa.setRazonSocial(
                                            (js.get("primer_nombre") == null ? "" : js.get("primer_nombre").toString())
                                            + (js.get("segundo_nombre") == null ? "" : " " + js.get("segundo_nombre").toString())
                                            + (js.get("primer_apellido") == null ? "" : " " + js.get("primer_apellido").toString())
                                            + (js.get("apellido_casada") == null ? "" : " " + js.get("apellido_casada").toString()));
                                } else {
                                    empresa.setRazonSocial(
                                            (js.get("primer_nombre") == null ? "" : js.get("primer_nombre").toString())
                                            + (js.get("segundo_nombre") == null ? "" : " " + js.get("segundo_nombre").toString())
                                            + (js.get("primer_apellido") == null ? "" : " " + js.get("primer_apellido").toString())
                                            + (js.get("segundo_apellido") == null ? "" : " " + js.get("segundo_apellido").toString()));
                                }
                            } else {
                                empresa.setRazonSocial(jsonObj.get("razon_social").toString().equals("") ? null : jsonObj.get("razon_social").toString());
                            }

                            js = (JSONObject) jsonParser.parse(jsonObj.get("datos_calificacion").toString());
                            BigDecimal rubro = new BigDecimal(js.get("rubro").toString());
                            String anho = js.get("anho").toString();

                            if (rubro.intValue() == 4) {
                                rubro = rubro.add(BigDecimal.ONE.negate());
                            }
                            switch (rubro.intValue()) {
                                case 4:
                                case 1:
                                    rubro = new BigDecimal(4);
                                    break;
                            }
                            //DetalleProcesoAdq detalleProceso = anhoProcesoEJB.getDetProcesoAdq(String.valueOf(Integer.parseInt(js.get("anho").toString()) + 1), rubro);

                            DetRubroMuestraInteres detRubro = getDetRubroMuestraInteres(rubro, String.valueOf(Integer.parseInt(anho) + 1), empresa);
                            if (detRubro == null) {
                                //nuevo registro de calificacion
                                detRubro = new DetRubroMuestraInteres();
                                detRubro.setUsuarioInsercion(usuario);
                                detRubro.setFechaInsercion(new Date());
                                detRubro.setEstadoEliminacion(BigInteger.ZERO);
                                detRubro.setIdEmpresa(empresa);
                                detRubro.setIdRubroInteres(utilEJB.find(RubrosAmostrarInteres.class, rubro));
                                detRubro.setIdAnho(anhoProcesoEJB.getAnhoByAnho(String.valueOf(Integer.parseInt(anho) + 1)));
                                empresa.getDetRubroMuestraInteresList().add(detRubro);

                                CapaDistribucionAcre capaDistribucion = new CapaDistribucionAcre();
                                capaDistribucion.setCodigoDepartamento(utilEJB.find(Departamento.class, js.get("departamento").toString()));
                                capaDistribucion.setEstadoEliminacion(BigInteger.ZERO);
                                capaDistribucion.setUsuarioInsercion(usuario);
                                capaDistribucion.setFechaInsercion(new Date());
                                capaDistribucion.setIdMuestraInteres(detRubro);
                                detRubro.getCapaDistribucionAcreList().add(capaDistribucion);

                                CapaInstPorRubro capaInstalada = new CapaInstPorRubro();

                                if (js.containsKey("capacidad") && js.get("capacidad") != null) {
                                    if (rubro.intValue() == 1) {
                                        capaInstalada.setCapacidadAcreditada(new BigInteger(js.get("capacidad").toString()).multiply(new BigInteger("2")));
                                    } else {
                                        capaInstalada.setCapacidadAcreditada(new BigInteger(js.get("capacidad").toString()));
                                    }
                                } else {
                                    capaInstalada.setCapacidadAcreditada(BigInteger.ZERO);
                                }

                                capaInstalada.setCapacidadAdjudicada(BigInteger.ZERO);
                                capaInstalada.setEstadoEliminacion(BigInteger.ZERO);
                                capaInstalada.setFechaInsercion(new Date());
                                capaInstalada.setUsuarioInsercion(usuario);
                                capaInstalada.setIdMuestraInteres(detRubro);
                                detRubro.getCapaInstPorRubroList().add(capaInstalada);

                                detRubro.setDetCapaSegunRubroList(new ArrayList());
                                for (DetCapaSegunRubro item : detRubro.getDetCapaSegunRubroList()) {
                                    item.setEstadoEliminacion(BigInteger.ONE);
                                }

                                JSONArray jsItems = (JSONArray) jsonParser.parse(js.get("items").toString());
                                for (Object itemTemp : jsItems.toArray()) {
                                    JSONObject jsItem = (JSONObject) itemTemp;

                                    if (rubro.intValue() == 3) {
                                        DetCapaSegunRubro detItem = new DetCapaSegunRubro();
                                        detItem.setEstadoEliminacion(BigInteger.ZERO);
                                        detItem.setFechaInsercion(new Date());
                                        detItem.setIdMuestraInteres(detRubro);
                                        detItem.setUsuarioInsercion(usuario);
                                        detItem.setIdProducto(utilEJB.find(CatalogoProducto.class, new BigDecimal(jsItem.get("id").toString())));
                                        detItem.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal(1)));
                                        detRubro.getDetCapaSegunRubroList().add(detItem);

                                        detItem = new DetCapaSegunRubro();
                                        detItem.setEstadoEliminacion(BigInteger.ZERO);
                                        detItem.setFechaInsercion(new Date());
                                        detItem.setIdMuestraInteres(detRubro);
                                        detItem.setUsuarioInsercion(usuario);
                                        detItem.setIdProducto(utilEJB.find(CatalogoProducto.class, new BigDecimal(jsItem.get("id").toString())));
                                        detItem.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal(3)));
                                        detRubro.getDetCapaSegunRubroList().add(detItem);

                                        detItem = new DetCapaSegunRubro();
                                        detItem.setEstadoEliminacion(BigInteger.ZERO);
                                        detItem.setFechaInsercion(new Date());
                                        detItem.setIdMuestraInteres(detRubro);
                                        detItem.setUsuarioInsercion(usuario);
                                        detItem.setIdProducto(utilEJB.find(CatalogoProducto.class, new BigDecimal(jsItem.get("id").toString())));
                                        detItem.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal(4)));
                                        detRubro.getDetCapaSegunRubroList().add(detItem);

                                        detItem = new DetCapaSegunRubro();
                                        detItem.setEstadoEliminacion(BigInteger.ZERO);
                                        detItem.setFechaInsercion(new Date());
                                        detItem.setIdMuestraInteres(detRubro);
                                        detItem.setUsuarioInsercion(usuario);
                                        detItem.setIdProducto(utilEJB.find(CatalogoProducto.class, new BigDecimal(jsItem.get("id").toString())));
                                        detItem.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal(5)));
                                        detRubro.getDetCapaSegunRubroList().add(detItem);

                                        detItem = new DetCapaSegunRubro();
                                        detItem.setEstadoEliminacion(BigInteger.ZERO);
                                        detItem.setFechaInsercion(new Date());
                                        detItem.setIdMuestraInteres(detRubro);
                                        detItem.setUsuarioInsercion(usuario);
                                        detItem.setIdProducto(utilEJB.find(CatalogoProducto.class, new BigDecimal(jsItem.get("id").toString())));
                                        detItem.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal(6)));
                                        detRubro.getDetCapaSegunRubroList().add(detItem);
                                    } else {
                                        DetCapaSegunRubro detItem = new DetCapaSegunRubro();
                                        detItem.setEstadoEliminacion(BigInteger.ZERO);
                                        detItem.setFechaInsercion(new Date());
                                        detItem.setIdMuestraInteres(detRubro);
                                        detItem.setUsuarioInsercion(usuario);

                                        //Logger.getLogger(ConamypeEJB.class.getName()).log(Level.INFO, "{0}:::{1}", new Object[]{jsItem.get("id").toString(), jsItem.get("nivel").toString()});
                                        detItem.setIdProducto(utilEJB.find(CatalogoProducto.class, new BigDecimal(jsItem.get("id").toString())));
                                        detItem.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal(jsItem.get("nivel").toString())));
                                        detRubro.getDetCapaSegunRubroList().add(detItem);
                                    }
                                }

                                if (empresa.getIdEmpresa() == null) {
                                    em.persist(empresa);
                                } else {
                                    em.merge(empresa);
                                }
                                em.persist(detRubro);
                            } else {
                                //actualizar registro de calificacion
                                detRubro.setUsuarioModificacion(usuario);
                                detRubro.setFechaModificacion(new Date());

                                CapaDistribucionAcre capaDistribucion = detRubro.getCapaDistribucionAcreList().get(0);
                                capaDistribucion.setCodigoDepartamento(utilEJB.find(Departamento.class, js.get("departamento").toString()));
                                capaDistribucion.setUsuarioModificacion(usuario);
                                capaDistribucion.setFechaModificacion(new Date());

                                CapaInstPorRubro capaInstalada = detRubro.getCapaInstPorRubroList().get(0);
                                if (rubro.intValue() == 1) {
                                    capaInstalada.setCapacidadAcreditada(new BigInteger(js.get("capacidad") == null ? "0" : js.get("capacidad").toString()).multiply(new BigInteger("2")));
                                } else {
                                    capaInstalada.setCapacidadAcreditada(new BigInteger(js.get("capacidad") == null ? "0" : js.get("capacidad").toString()));
                                }
                                //capaInstalada.setCapacidadAdjudicada(BigInteger.ZERO);
                                capaInstalada.setEstadoEliminacion(BigInteger.ZERO);
                                capaInstalada.setFechaModificacion(new Date());

                                for (DetCapaSegunRubro item : detRubro.getDetCapaSegunRubroList()) {
                                    item.setEstadoEliminacion(BigInteger.ONE);
                                }

                                em.merge(detRubro);

                                JSONArray jsItems = (JSONArray) jsonParser.parse(js.get("items").toString());
                                for (Object itemTemp : jsItems.toArray()) {
                                    JSONObject jsItem = (JSONObject) itemTemp;

                                    DetCapaSegunRubro detCapaSegunRubro = findDetCapaSegunRubro(new BigDecimal(jsItem.get("id").toString()), new BigDecimal(jsItem.get("nivel").toString()), detRubro.getIdMuestraInteres());

                                    if (detCapaSegunRubro == null) {
                                        detCapaSegunRubro = new DetCapaSegunRubro();
                                        detCapaSegunRubro.setEstadoEliminacion(BigInteger.ZERO);
                                        detCapaSegunRubro.setFechaInsercion(new Date());
                                        detCapaSegunRubro.setIdMuestraInteres(detRubro);
                                        detCapaSegunRubro.setUsuarioInsercion(usuario);
                                        detCapaSegunRubro.setIdProducto(utilEJB.find(CatalogoProducto.class, new BigDecimal(jsItem.get("id").toString())));
                                        detCapaSegunRubro.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal(jsItem.get("nivel").toString())));
                                        em.persist(detCapaSegunRubro);
                                    } else {
                                        detCapaSegunRubro.setEstadoEliminacion(BigInteger.ZERO);
                                        em.merge(detCapaSegunRubro);
                                    }
                                }
                            }

                            em.merge(empresa);

                            //proveedor de zapatos
                            for (ProveedorEmpresa proEmp : detRubro.getProveedorEmpresaList()) {
                                proEmp.setEstadoEliminacion(BigInteger.ONE);
                            }

                            if (rubro.intValue() == 3) {
                                JSONArray jsProveedores = (JSONArray) jsonParser.parse(js.get("proveedores").toString());
                                for (Object provTemp : jsProveedores.toArray()) {
                                    JSONObject jsProv = (JSONObject) provTemp;
                                    setProductorDeZapatos(usuario, detRubro, jsProv);
                                }
                            }

                            Logger.getLogger(ConamypeEJB.class.getName()).log(Level.INFO, "Empresa: {0}", empresa);
                        }
                    }
                }

            } catch (NumberFormatException | ParseException ex) {
                Logger.getLogger(ConamypeEJB.class.getName()).log(Level.SEVERE, "Error en el json\n: json: {0}", jsonString);

                //eMailEJB.enviarMailDeError("Error - WS CONAMYPE - MINED", "Ah ocurrido el sigueinte error en el proceso de exportación de proveedores.", ex, null);
            }
        }
    }

    private Boolean isActivoWsConamype() {
        Query q = em.createNativeQuery("SELECT valor_parametro FROM PARAMETROS WHERE nombre_parametro='WS_CONAMYPE'");
        List ls = q.getResultList();
        if (ls.isEmpty()) {
            return false;
        } else {
            return ls.get(0).toString().equals("1");
        }
    }

    /*private DetRubroMuestraInteres getDetRubroMuestraInteres(DetalleProcesoAdq detalleProceso, Empresa empresa) {
        Query q = em.createQuery("SELECT d FROM DetRubroMuestraInteres d WHERE d.idDetProcesoAdq=:detProceso and d.idEmpresa=:idEmpresa and d.estadoEliminacion = 0", DetRubroMuestraInteres.class);
        q.setParameter("detProceso", detalleProceso);
        q.setParameter("idEmpresa", empresa);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (DetRubroMuestraInteres) q.getSingleResult();
        }
    }*/
    private DetRubroMuestraInteres getDetRubroMuestraInteres(BigDecimal idRubro, String anho, Empresa empresa) {
        Query q = em.createQuery("SELECT d FROM DetRubroMuestraInteres d WHERE d.idRubroInteres.idRubroInteres=:pIdRubro and d.idAnho.anho=:pAnho and d.idEmpresa=:idEmpresa and d.estadoEliminacion = 0", DetRubroMuestraInteres.class);
        q.setParameter("pIdRubro", idRubro);
        q.setParameter("pAnho", anho);
        q.setParameter("idEmpresa", empresa);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (DetRubroMuestraInteres) q.getSingleResult();
        }
    }

    private void setProductorDeZapatos(String usuario, DetRubroMuestraInteres detRubro, JSONObject js) {
        Productor p;
        if (js.get("nit") != null) {
            Query q = em.createQuery("SELECT p FROM Productor p WHERE p.numeroNit=:nit", Productor.class);
            q.setParameter("nit", js.get("nit").toString());
            if (q.getResultList().isEmpty()) {
                p = new Productor();
                p.setDireccion(js.get("direccion").toString());
                p.setNombreProducto(js.get("razon_social").toString());
                p.setNumeroNit(js.get("nit").toString());

                em.persist(p);
            } else {
                p = (Productor) q.getSingleResult();
                p.setDireccion(js.get("direccion").toString());
            }

            if (detRubro.getProveedorEmpresaList() == null) {
                detRubro.setProveedorEmpresaList(new ArrayList());
            }

            ProveedorEmpresa proEmp;

            q = em.createQuery("SELECT p FROM ProveedorEmpresa p WHERE p.idMuestraInteres =:detRubro and p.idProductor=:idProductor", ProveedorEmpresa.class);
            q.setParameter("detRubro", detRubro);
            q.setParameter("idProductor", p);
            if (q.getResultList().isEmpty()) {
                proEmp = new ProveedorEmpresa();
                proEmp.setEstadoEliminacion(BigInteger.ZERO);
                proEmp.setFechaInsercion(new Date());
                proEmp.setUsuarioInsercion(usuario);
                proEmp.setIdMuestraInteres(detRubro);
                proEmp.setIdProductor(p);
                detRubro.getProveedorEmpresaList().add(proEmp);
            } else {
                proEmp = (ProveedorEmpresa) q.getSingleResult();
                proEmp.setEstadoEliminacion(BigInteger.ZERO);
            }

            em.merge(p);
        }
    }

    private DetCapaSegunRubro findDetCapaSegunRubro(BigDecimal idProducto, BigDecimal idNivelEducativo, BigDecimal idDetMuestra) {
        Query q = em.createQuery("SELECT d FROM DetCapaSegunRubro d WHERE d.idProducto.idProducto=:idProducto AND d.idNivelEducativo.idNivelEducativo=:idNivelEducativo and d.idMuestraInteres.idMuestraInteres=:idDetMuestra and d.estadoEliminacion = 0", DetCapaSegunRubro.class);
        q.setParameter("idProducto", idProducto);
        q.setParameter("idNivelEducativo", idNivelEducativo);
        q.setParameter("idDetMuestra", idDetMuestra);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (DetCapaSegunRubro) q.getSingleResult();
        }
    }

    /**
     * Web service operation
     *
     * @param numeroNit
     * @param idRubro
     * @param idAnho
     * @param capacidad
     */
    @WebMethod(operationName = "updCapacidadByNitAndIdDet")
    public void updCapacidadByNitAndIdDet(String numeroNit, BigDecimal idRubro, BigDecimal idAnho, BigInteger capacidad) {
        Query q = em.createQuery("SELECT c FROM CapaInstPorRubro c WHERE c.idMuestraInteres.idEmpresa.numeroNit=:nit and c.idMuestraInteres.idRubroInteres.idRubroInteres=:pIdRubro and c.idMuestraInteres.idAnho.idAnho=:pIdAnho and c.idMuestraInteres.estadoEliminacion=0", CapaInstPorRubro.class);
        q.setParameter("nit", numeroNit);
        q.setParameter("pIdRubro", idRubro);
        q.setParameter("pIdAnho", idAnho);
        if (q.getResultList().isEmpty()) {
            Logger.getLogger(ProveedorEJB.class.getName()).log(Level.INFO, "No se encontro la empresa con numero de nit: {0}", numeroNit);
        } else {
            CapaInstPorRubro capa = (CapaInstPorRubro) q.getResultList().get(0);
            capa.setCapacidadAcreditada(capacidad);
            //capa.setCapacidadAdjudicada(BigInteger.ZERO);
            em.merge(capa);

        }
    }

    @WebMethod(operationName = "updCorreoElectronicoByNit")
    public void updCorreoElectronicoByNit(String numeroNit, String correoElectronico) {
        Query q = em.createQuery("SELECT p FROM Persona p WHERE p.numeroNit=:nit", Persona.class);
        q.setParameter("nit", numeroNit);
        if (q.getResultList().isEmpty()) {
            System.out.println("Proveedor no encontrado: " + numeroNit);
        } else {
            Persona per = (Persona) q.getResultList().get(0);
            per.setEmail(correoElectronico);
            per.setFechaModificacion(new Date());

            em.persist(per);
        }

    }

    @WebMethod(operationName = "generarCodigoSeguridad")
    public void generarCodigoSeguridad(Integer idDetProcesoAdq, String fecha) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Query q = em.createQuery("SELECT d.idEmpresa FROM DetRubroMuestraInteres d WHERE d.idDetProcesoAdq.idDetProcesoAdq=:id and d.fechaInsercion>:date", Empresa.class);
            q.setParameter("id", idDetProcesoAdq);
            q.setParameter("date", sdf.parse(fecha));
            List<Empresa> lst = q.getResultList();

            RC4Crypter encript = new RC4Crypter();

            for (Empresa empresa : lst) {
                EmpresaCodigoSeg ecs = new EmpresaCodigoSeg();

                ecs.setIdEmpresa(empresa.getIdEmpresa());
                ecs.setCodigoSeguridad(encript.encrypt("ha", empresa.getIdEmpresa().toString().concat(empresa.getNumeroNit())).substring(0, 10));
                ecs.setUsuarioActivado((short) 0);

                em.persist(ecs);
            }
        } catch (java.text.ParseException ex) {
            Logger.getLogger(ConamypeEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @WebMethod(operationName = "generarCodigoSeguridadByNit")
    public void generarCodigoSeguridadByNit(String anho, String nit) {
        Query q = em.createQuery("SELECT d.idEmpresa FROM DetRubroMuestraInteres d WHERE d.idAnho.anho=:id and d.idEmpresa.idPersona.numeroNit=:nit", Empresa.class);
        q.setParameter("id", anho);
        q.setParameter("nit", nit);
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

    @WebMethod
    public void setCantonProveedor(String numeroNit, String codigoCanton) {
        proveedorEJB.guardarCantonProveedor(numeroNit, codigoCanton);
    }

    @WebMethod
    public Integer getIdMunicipio(String codigoDepartamento, String codigoMunicipio) {
        return utilEJB.findMunicipioByCodigo(codigoDepartamento, codigoMunicipio);
    }

    @WebMethod
    public void guardarCanton(Integer idMunicipio, String nombre, String codigoCanton) {
        utilEJB.guardarCanton(codigoCanton, nombre, idMunicipio);
    }
}
