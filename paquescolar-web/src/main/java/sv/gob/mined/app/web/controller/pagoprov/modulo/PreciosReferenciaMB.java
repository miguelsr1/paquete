/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov.modulo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.PreciosReferenciaEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.model.Anho;
import sv.gob.mined.paquescolar.model.CapaInstPorRubro;
import sv.gob.mined.paquescolar.model.CatalogoProducto;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.Empresa;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.PreciosRefRubro;
import sv.gob.mined.paquescolar.model.PreciosRefRubroEmp;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.pojos.contratacion.DetalleItemDto;

/**
 *
 * @author MISanchez
 */
@ManagedBean
@ViewScoped
public class PreciosReferenciaMB implements Serializable {

    private String msjError = "";
    private String rowEdit;

    private Empresa empresa = new Empresa();
    private CapaInstPorRubro capacidadInst = new CapaInstPorRubro();
    private DetalleProcesoAdq detalleProcesoAdq = new DetalleProcesoAdq();

    private List<CatalogoProducto> lstItem = new ArrayList();
    private List<PreciosRefRubroEmp> lstPreciosReferencia = new ArrayList();
    private List<DetalleItemDto> lstPreciosMaximos = new ArrayList();

    private PreciosRefRubroEmp precioRef = new PreciosRefRubroEmp();

    private PreciosRefRubro preMaxRefPar = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefCi = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefCii = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefCiii = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefBac = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG1 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG2 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG3 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG4 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG5 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG6 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG7 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG8 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefG9 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefB1 = new PreciosRefRubro();
    private PreciosRefRubro preMaxRefB2 = new PreciosRefRubro();

    @EJB
    private ProveedorEJB proveedorEJB;
    @EJB
    private PreciosReferenciaEJB preciosReferenciaEJB;

    @PostConstruct
    public void ini() {
        if (VarSession.isVariableSession("idEmpresa")) {
            cargarDetalleCalificacion();
            cargarPrecioRef();
            cargarPreciosMaximos();
        }
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<PreciosRefRubroEmp> getLstPreciosReferencia() {
        return lstPreciosReferencia;
    }

    public void setLstPreciosReferencia(List<PreciosRefRubroEmp> lstPreciosReferencia) {
        this.lstPreciosReferencia = lstPreciosReferencia;
    }

    public PreciosRefRubroEmp getPrecioRef() {
        return precioRef;
    }

    public void setPrecioRef(PreciosRefRubroEmp preciosRef) {
        this.precioRef = preciosRef;
    }

    public List<DetalleItemDto> getLstPreciosMaximos() {
        return lstPreciosMaximos;
    }

    public DetalleProcesoAdq getDetalleProcesoAdq() {
        return detalleProcesoAdq;
    }

    public void setDetalleProcesoAdq(DetalleProcesoAdq detalleProcesoAdq) {
        this.detalleProcesoAdq = detalleProcesoAdq;
    }

    public void guardarPreciosRef() {
        if (detalleProcesoAdq.getIdDetProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de contratación");
        } else {
            Boolean preciosValidos = true;
            for (PreciosRefRubroEmp precio : lstPreciosReferencia) {
                if (precio.getNoItem() != null && !precio.getNoItem().isEmpty() && precio.getIdNivelEducativo() != null && precio.getIdProducto() != null && precio.getPrecioReferencia() != null && precio.getPrecioReferencia().compareTo(BigDecimal.ZERO) == 1) {
                } else {
                    preciosValidos = false;
                    break;
                }
            }

            if (preciosValidos) {
                for (PreciosRefRubroEmp precio : lstPreciosReferencia) {
                    proveedorEJB.guardar(precio);
                }
                lstPreciosReferencia = proveedorEJB.findPreciosRefRubroEmpRubro(getEmpresa(), getDetalleProcesoAdq());
                JsfUtil.mensajeUpdate();
            } else {
                JsfUtil.mensajeInformacion("Los precios de referencia no han sido guardados debido a que existen datos incompletos o erroneos.");
            }
        }
    }

    private void cargarDetalleCalificacion() {
        ProveedorMB proveedorMB = ((ProveedorMB) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "proveedorMB"));
        empresa = proveedorMB.getEmpresa();
        Anho anho = proveedorMB.getAnho();
        ProcesoAdquisicion proceso = anho.getProcesoAdquisicionList().get(0);
        
        if (proceso == null || proceso.getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe seleccionar un proceso de contratación");
        } else {
            if (proceso.getPadreIdProcesoAdq() != null) {
                proceso = proceso.getPadreIdProcesoAdq();
            }
            capacidadInst = proveedorEJB.findDetProveedor(proceso, empresa, CapaInstPorRubro.class);
            if (capacidadInst == null) {
                JsfUtil.mensajeAlerta("No se han cargado los datos de este proveedor para el proceso de contratación del año " + proceso.getIdAnho().getAnho());
            } else {
                detalleProcesoAdq = capacidadInst.getIdMuestraInteres().getIdDetProcesoAdq();
            }
        }
    }

    public void onCellEdit(CellEditEvent event) {
        msjError = "";
        FacesContext context = FacesContext.getCurrentInstance();
        precioRef = context.getApplication().evaluateExpressionGet(context, "#{precio}", PreciosRefRubroEmp.class);
        boolean valido = true;
        if (!valido) {
            precioRef.setIdProducto(null);
            precioRef.setIdNivelEducativo(null);
        } else {
            this.rowEdit = String.valueOf(event.getRowIndex());
            if (event.getColumn().getColumnKey().contains("item")) {
                String numItem = event.getNewValue().toString();
                editarNumeroDeItem(event.getRowIndex(), numItem);
            } else if (event.getColumn().getColumnKey().contains("precio")) {
                agregarPrecio();
            }
        }
    }

    private void cargarPrecioRef() {
        if (capacidadInst != null && capacidadInst.getIdCapInstRubro() != null) {
            lstItem = proveedorEJB.findItemProveedor(empresa, detalleProcesoAdq);
            lstPreciosReferencia = proveedorEJB.findPreciosRefRubroEmpRubro(getEmpresa(), getDetalleProcesoAdq());
            preMaxRefPar = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(BigDecimal.ONE, detalleProcesoAdq);
            preMaxRefCi = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(3), detalleProcesoAdq);
            preMaxRefCii = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(4), detalleProcesoAdq);
            preMaxRefCiii = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(5), detalleProcesoAdq);
            preMaxRefBac = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(6), detalleProcesoAdq);
            if (detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho().intValue() > 6
                    && detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue() == 2) {
                //procesos de contratación mayores a 2018 y rubro de utiles
                preMaxRefG1 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(10), detalleProcesoAdq);
                preMaxRefG2 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(11), detalleProcesoAdq);
                preMaxRefG3 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(12), detalleProcesoAdq);
                preMaxRefG4 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(13), detalleProcesoAdq);
                preMaxRefG5 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(14), detalleProcesoAdq);
                preMaxRefG6 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(15), detalleProcesoAdq);
                preMaxRefG7 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(7), detalleProcesoAdq);
                preMaxRefG8 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(8), detalleProcesoAdq);
                preMaxRefG9 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(9), detalleProcesoAdq);
                preMaxRefB1 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(16), detalleProcesoAdq);
                preMaxRefB2 = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(new BigDecimal(17), detalleProcesoAdq);
            }
        }
        PrimeFaces.current().ajax().update("frmPrincipal");
    }

    public void agregarPrecio() {
        if (precioRef != null) {
            BigDecimal preRef = BigDecimal.ZERO;

            if (precioRef.getIdNivelEducativo() != null) {
                switch (detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                    case 1://UNIFORMES
                    case 4:
                    case 5:
                        preRef = getPrecioRefUniforme();
                        break;
                    case 2:
                        preRef = getPrecioRefUtiles();
                        break;
                    case 3:
                        if (precioRef.getIdNivelEducativo().getIdNivelEducativo().compareTo(new BigDecimal("6")) == 0) {
                            preRef = new BigDecimal("16.00");
                        } else {
                            preRef = new BigDecimal("14.60");
                        }
                        break;
                }
            }

            if (precioRef.getPrecioReferencia() != null && precioRef.getPrecioReferencia().compareTo(preRef) == 1) {
                precioRef.setPrecioReferencia(BigDecimal.ZERO);
                switch (detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                    case 1:
                    case 5:
                        msjError = "Precio Máximo de Referencia para: <br/>"
                                + "1)<strong> Parvularia</strong>: - Blusa, Falda y Camisa: $ 4.25 y Pantalon Corto $ 4.00<br />"
                                + "2)<strong> Básica y Bachillerato</strong>: - Blusa, Falda y Camisa: $ 4.50 y Pantalon Corto y Pantalon: $ 6.00<br/>";
                        break;
                    case 2:
                        msjError = "Precio Máximo de Referencia para: <br/>"
                                + "1)<strong> Parvularia</strong>: $ " + preMaxRefPar.getPrecioMaxMas() + "<br/>"
                                + "2)<strong> Primer Ciclo</strong>: $ " + preMaxRefCi.getPrecioMaxMas() + "<br/>"
                                + "3)<strong> Segundo Ciclo</strong>: $ " + preMaxRefCii.getPrecioMaxMas() + "<br/>"
                                + "4)<strong> Tercer Ciclo</strong>: $ " + preMaxRefCiii.getPrecioMaxMas() + "<br/>"
                                + "5)<strong> Bachillerato: $ " + preMaxRefBac.getPrecioMaxMas() + "</strong>";
                        break;
                    case 3:
                        msjError = "Precio Máximo de Referencia para Zapatos escolares de:<br/> "
                                + "<strong>Parvularia y Básica</strong>: $ 14.60 <br/>"
                                + "<strong>Bachillerato</strong>: $16.00";
                        break;
                }
            }
        }
    }

    private BigDecimal getPrecioRefUniforme() {
        BigDecimal preRef = BigDecimal.ZERO;

        switch (precioRef.getIdProducto().getIdProducto().intValue()) {
            case 29:
            case 30:
            case 44:
                switch (precioRef.getIdNivelEducativo().getIdNivelEducativo().intValue()) {
                    case 1:
                        preRef = new BigDecimal("4.25");
                        break;
                    case 2:
                    case 6:
                        preRef = new BigDecimal("4.50");
                        break;
                }
                break;
            case 31:
                switch (precioRef.getIdNivelEducativo().getIdNivelEducativo().intValue()) {
                    case 1:
                        preRef = new BigDecimal("4.00");
                        break;
                }
                break;
            case 34:
                switch (precioRef.getIdNivelEducativo().getIdNivelEducativo().intValue()) {
                    case 1:
                        preRef = new BigDecimal("6.00");
                        break;
                    case 2:
                    case 6:
                        preRef = new BigDecimal("6.00");
                        break;
                }
                break;
        }

        return preRef;
    }

    private BigDecimal getPrecioRefUtiles() {
        BigDecimal preRef = BigDecimal.ZERO;

        switch (precioRef.getIdNivelEducativo().getIdNivelEducativo().intValue()) {
            case 1: //parvularia
                preRef = preMaxRefPar.getPrecioMaxMas();
                break;
            case 3: //ciclo I
                preRef = preMaxRefCi.getPrecioMaxMas();
                break;
            case 4: //ciclo II
                preRef = preMaxRefCii.getPrecioMaxMas();
                break;
            case 5://ciclo III
                preRef = preMaxRefCiii.getPrecioMaxMas();
                break;
            case 6: //Bachillerato
                preRef = preMaxRefBac.getPrecioMaxMas();
                break;
            case 7: //grado 7
                preRef = preMaxRefG7.getPrecioMaxMas();
                break;
            case 8: //grado 8
                preRef = preMaxRefG8.getPrecioMaxMas();
                break;
            case 9: //grado 9
                preRef = preMaxRefG9.getPrecioMaxMas();
                break;
            case 10: //grado 1
                preRef = preMaxRefG1.getPrecioMaxMas();
                break;
            case 11: //grado 2
                preRef = preMaxRefG2.getPrecioMaxMas();
                break;
            case 12: //grado 3
                preRef = preMaxRefG3.getPrecioMaxMas();
                break;
            case 13: //grado 4
                preRef = preMaxRefG4.getPrecioMaxMas();
                break;
            case 14: //grado 5
                preRef = preMaxRefG5.getPrecioMaxMas();
                break;
            case 15: //grado 6
                preRef = preMaxRefG6.getPrecioMaxMas();
                break;
            case 16: //1 bachillerato
                preRef = preMaxRefB1.getPrecioMaxMas();
                break;
            case 17: //2 bachillerato
                preRef = preMaxRefB2.getPrecioMaxMas();
                break;
        }

        return preRef;
    }

    public void cargarPreciosMaximos() {
        List<PreciosRefRubro> lstPrecios = preciosReferenciaEJB.getLstPreciosRefRubroByRubro(detalleProcesoAdq);
        DetalleItemDto det = new DetalleItemDto();

        if (detalleProcesoAdq.getIdRubroAdq().getIdRubroUniforme().intValue() == 1) {

            det.setNoItem("1");
            det.setConsolidadoEspTec("Blusas, PARVULARIA");
            det.setPrecioUnitario(new BigDecimal("4.25"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("2");
            det.setConsolidadoEspTec("Falda, PARVULARIA");
            det.setPrecioUnitario(new BigDecimal("4.25"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("3");
            det.setConsolidadoEspTec("Camisas, PARVULARIA");
            det.setPrecioUnitario(new BigDecimal("4.25"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("4");
            det.setConsolidadoEspTec("Pantalon Corto, PARVULARIA");
            det.setPrecioUnitario(new BigDecimal("4.00"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("5");
            det.setConsolidadoEspTec("Pantalon, PARVULARIA");
            det.setPrecioUnitario(new BigDecimal("6.00"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("6");
            det.setConsolidadoEspTec("Blusas, BASICA(I, II Y III)");
            det.setPrecioUnitario(new BigDecimal("4.50"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("7");
            det.setConsolidadoEspTec("Falda, BASICA(I, II Y III)");
            det.setPrecioUnitario(new BigDecimal("4.50"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("8");
            det.setConsolidadoEspTec("Camisas, BASICA(I, II Y III)");
            det.setPrecioUnitario(new BigDecimal("4.50"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("9");
            det.setConsolidadoEspTec("Pantalon, BASICA(I, II Y III)");
            det.setPrecioUnitario(new BigDecimal("6.00"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("10");
            det.setConsolidadoEspTec("Blusas, BACHILLERATO");
            det.setPrecioUnitario(new BigDecimal("4.50"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("11");
            det.setConsolidadoEspTec("Falda, BACHILLERATO");
            det.setPrecioUnitario(new BigDecimal("4.50"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("12");
            det.setConsolidadoEspTec("Camisas, BACHILLERATO");
            det.setPrecioUnitario(new BigDecimal("4.50"));
            lstPreciosMaximos.add(det);

            det = new DetalleItemDto();
            det.setNoItem("13");
            det.setConsolidadoEspTec("Pantalon, BACHILLERATO");
            det.setPrecioUnitario(new BigDecimal("6.00"));
            lstPreciosMaximos.add(det);
        }

        for (PreciosRefRubro precio : lstPrecios) {
            det = new DetalleItemDto();
            switch (detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                case 2:
                    switch (precio.getIdNivelEducativo().getIdNivelEducativo().intValue()) {
                        case 1:
                            det.setNoItem("1");
                            det.setConsolidadoEspTec("Utiles Escolares, PARVULARIA");
                            break;
                        case 3:
                            det.setNoItem("2");
                            det.setConsolidadoEspTec("Utiles Escolares, PRIMER CICLO");
                            break;
                        case 4:
                            det.setNoItem("3");
                            det.setConsolidadoEspTec("Utiles Escolares, SEGUNDO CICLO");
                            break;
                        case 5:
                            det.setNoItem("4");
                            det.setConsolidadoEspTec("Utiles Escolares, TERCER CICLO");
                            break;
                        case 6:
                            det.setNoItem("5");
                            det.setConsolidadoEspTec("Utiles Escolares, BACHILLERATO");
                            break;
                        case 7:
                            det.setNoItem("6");
                            det.setConsolidadoEspTec("Utiles Escolares, INICIAL");
                            break;
                        case 8:
                            det.setNoItem("7");
                            det.setConsolidadoEspTec("Utiles Escolares, MODALIDAD FLEXIBLE");
                            break;
                    }
                    det.setPrecioUnitario(precio.getPrecioMaxFem());
                    break;
                case 3:
                    switch (precio.getIdNivelEducativo().getIdNivelEducativo().intValue()) {
                        case 1:
                            det.setNoItem("1");
                            det.setConsolidadoEspTec("Zapato de niña, PARVULARIA");
                            det.setPrecioUnitario(precio.getPrecioMaxFem());
                            lstPreciosMaximos.add(det);

                            det = new DetalleItemDto();
                            det.setNoItem("2");
                            det.setConsolidadoEspTec("Zapato de niño, PARVULARIA");
                            det.setPrecioUnitario(precio.getPrecioMaxMas());
                            lstPreciosMaximos.add(det);
                            break;
                        case 3:
                            det.setNoItem("3");
                            det.setConsolidadoEspTec("Zapato de niña, PRIMER CICLO");
                            det.setPrecioUnitario(precio.getPrecioMaxFem());
                            lstPreciosMaximos.add(det);

                            det = new DetalleItemDto();
                            det.setNoItem("4");
                            det.setConsolidadoEspTec("Zapato de niño, PRIMER CICLO");
                            det.setPrecioUnitario(precio.getPrecioMaxMas());
                            lstPreciosMaximos.add(det);
                            break;
                        case 4:
                            det.setNoItem("5");
                            det.setConsolidadoEspTec("Zapato de niña, SEGUNDO CICLO");
                            det.setPrecioUnitario(precio.getPrecioMaxFem());
                            lstPreciosMaximos.add(det);

                            det = new DetalleItemDto();
                            det.setNoItem("6");
                            det.setConsolidadoEspTec("Zapato de niño, SEGUNDO CICLO");
                            det.setPrecioUnitario(precio.getPrecioMaxMas());
                            lstPreciosMaximos.add(det);
                            break;
                        case 5:
                            det.setNoItem("7");
                            det.setConsolidadoEspTec("Zapato de niña, TERCER CICLO");
                            det.setPrecioUnitario(precio.getPrecioMaxFem());
                            lstPreciosMaximos.add(det);

                            det = new DetalleItemDto();
                            det.setNoItem("8");
                            det.setConsolidadoEspTec("Zapato de niño, TERCER CICLO");
                            det.setPrecioUnitario(precio.getPrecioMaxMas());
                            lstPreciosMaximos.add(det);
                            break;
                        case 6:
                            det.setNoItem("9");
                            det.setConsolidadoEspTec("Zapato de niña, BACHILLERATO");
                            det.setPrecioUnitario(precio.getPrecioMaxFem());
                            lstPreciosMaximos.add(det);

                            det = new DetalleItemDto();
                            det.setNoItem("10");
                            det.setConsolidadoEspTec("Zapato de niño, BACHILLERATO");
                            det.setPrecioUnitario(precio.getPrecioMaxMas());
                            lstPreciosMaximos.add(det);
                            break;
                    }
                    break;
            }
        }
    }

    private void editarNumeroDeItem(int rowEdit, String numItem) {
        Boolean itemRepetido = false;
        BigDecimal precioLibro = BigDecimal.ZERO;
        for (int i = 0; i < lstPreciosReferencia.size(); i++) {
            if (i != rowEdit) {
                if (lstPreciosReferencia.get(i).getNoItem() != null
                        && lstPreciosReferencia.get(i).getNoItem().equals(numItem)) {
                    itemRepetido = true;
                    break;
                }
            }
        }

        if (itemRepetido) {
            precioRef.setNoItem("");
            msjError = "Este Item ya fue ingresado.";
        } else {
            CatalogoProducto item = null;
            NivelEducativo nivel = null;
            if (numItem != null && !numItem.isEmpty()) {
                switch (detalleProcesoAdq.getIdRubroAdq().getIdRubroInteres().intValue()) {
                    case 1: //UNIFORMES
                    case 4:
                        switch (Integer.parseInt(numItem)) {
                            case 0:
                                break;
                            case 1:
                            case 6:
                            case 10:
                                item = proveedorEJB.findProducto("30");
                                switch (Integer.parseInt(numItem)) {
                                    case 1:
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case 6:
                                        nivel = proveedorEJB.findNivelEducativo("2");
                                        break;
                                    default:
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                }
                                break;
                            case 2:
                            case 7:
                            case 11:
                                item = proveedorEJB.findProducto("44");
                                switch (Integer.parseInt(numItem)) {
                                    case 2:
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case 7:
                                        nivel = proveedorEJB.findNivelEducativo("2");
                                        break;
                                    default:
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                }
                                break;
                            case 3:
                            case 8:
                            case 12:
                                item = proveedorEJB.findProducto("29");
                                switch (Integer.parseInt(numItem)) {
                                    case 3:
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case 8:
                                        nivel = proveedorEJB.findNivelEducativo("2");
                                        break;
                                    default:
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                }
                                break;
                            case 4:
                                item = proveedorEJB.findProducto("31");
                                nivel = proveedorEJB.findNivelEducativo("1");
                                break;
                            case 5:
                            case 9:
                            case 13:
                                item = proveedorEJB.findProducto("34");
                                switch (Integer.parseInt(numItem)) {
                                    case 5:
                                        nivel = proveedorEJB.findNivelEducativo("1");
                                        break;
                                    case 9:
                                        nivel = proveedorEJB.findNivelEducativo("2");
                                        break;
                                    default:
                                        nivel = proveedorEJB.findNivelEducativo("6");
                                        break;
                                }
                                break;
                            default:
                                item = null;
                                nivel = null;
                                msjError = "El item ingresado no es válido.";
                                break;
                        }
                        break;
                    case 2: //UTILES
                        if (detalleProcesoAdq.getIdProcesoAdq().getIdAnho().getIdAnho().intValue() < 7) {
                            //procesos antes de la contratacion de 2019
                            switch (Integer.parseInt(numItem)) {
                                case 1:
                                    item = proveedorEJB.findProducto("54");
                                    nivel = proveedorEJB.findNivelEducativo("1");
                                    break;
                                case 2:
                                    item = proveedorEJB.findProducto("54");
                                    nivel = proveedorEJB.findNivelEducativo("3");
                                    break;
                                case 3:
                                    item = proveedorEJB.findProducto("54");
                                    nivel = proveedorEJB.findNivelEducativo("4");
                                    break;
                                case 4:
                                    item = proveedorEJB.findProducto("54");
                                    nivel = proveedorEJB.findNivelEducativo("5");
                                    break;
                                case 5:
                                    item = proveedorEJB.findProducto("54");
                                    nivel = proveedorEJB.findNivelEducativo("6");
                                    break;
                                default:
                                    item = null;
                                    nivel = null;
                                    msjError = "El item ingresado no es válido.";
                                    break;
                            }
                        } else {
                            //procesos mayor o igual a la contratacion de 2019
                            switch (numItem) {
                                case "1":
                                    item = proveedorEJB.findProducto("54");
                                    nivel = proveedorEJB.findNivelEducativo("1");
                                    break;
                                case "2":
                                    item = proveedorEJB.findProducto("54");
                                    nivel = proveedorEJB.findNivelEducativo("3");
                                    break;
                                case "3":
                                    item = proveedorEJB.findProducto("54");
                                    nivel = proveedorEJB.findNivelEducativo("4");
                                    break;
                                case "4":
                                    item = proveedorEJB.findProducto("54");
                                    nivel = proveedorEJB.findNivelEducativo("5");
                                    break;
                                case "5":
                                    item = proveedorEJB.findProducto("54");
                                    nivel = proveedorEJB.findNivelEducativo("6");
                                    break;
                                case "2.1": //grado 1
                                    item = proveedorEJB.findProducto("1");
                                    nivel = proveedorEJB.findNivelEducativo("10");
                                    precioLibro = new BigDecimal("4.10");
                                    break;
                                case "2.2": //grado 2
                                    item = proveedorEJB.findProducto("1");
                                    nivel = proveedorEJB.findNivelEducativo("11");
                                    precioLibro = new BigDecimal("4.10");
                                    break;
                                case "2.3": //grado 3
                                    item = proveedorEJB.findProducto("1");
                                    nivel = proveedorEJB.findNivelEducativo("12");
                                    precioLibro = new BigDecimal("3.62");
                                    break;
                                case "3.1": //grado 4
                                    item = proveedorEJB.findProducto("1");
                                    nivel = proveedorEJB.findNivelEducativo("13");
                                    precioLibro = new BigDecimal("3.62");
                                    break;
                                case "3.2": //grado 5
                                    item = proveedorEJB.findProducto("1");
                                    nivel = proveedorEJB.findNivelEducativo("14");
                                    precioLibro = new BigDecimal("3.62");
                                    break;
                                case "3.3": //grado 6
                                    item = proveedorEJB.findProducto("1");
                                    nivel = proveedorEJB.findNivelEducativo("15");
                                    precioLibro = new BigDecimal("3.62");
                                    break;
                                case "4.1": //grado 7
                                    item = proveedorEJB.findProducto("1");
                                    nivel = proveedorEJB.findNivelEducativo("7");
                                    precioLibro = new BigDecimal("3.62");
                                    break;
                                case "4.2": //grado 8
                                    item = proveedorEJB.findProducto("1");
                                    nivel = proveedorEJB.findNivelEducativo("8");
                                    precioLibro = new BigDecimal("3.62");
                                    break;
                                case "4.3": //grado 9
                                    item = proveedorEJB.findProducto("1");
                                    nivel = proveedorEJB.findNivelEducativo("9");
                                    precioLibro = new BigDecimal("3.62");
                                    break;
                                case "5.1": //1er año bachillerato
                                    item = proveedorEJB.findProducto("1");
                                    nivel = proveedorEJB.findNivelEducativo("16");
                                    precioLibro = new BigDecimal("2.05");
                                    break;
                                case "5.2": //2do año bachillerato
                                    item = proveedorEJB.findProducto("1");
                                    nivel = proveedorEJB.findNivelEducativo("17");
                                    precioLibro = new BigDecimal("2.05");
                                    break;
                                default:
                                    item = null;
                                    nivel = null;
                                    msjError = "El item ingresado no es válido.";
                                    break;
                            }
                        }
                        break;
                    case 3: //ZAPATOS
                        switch (Integer.parseInt(numItem)) {
                            case 1:
                                item = proveedorEJB.findProducto("21");
                                nivel = proveedorEJB.findNivelEducativo("1");
                                break;
                            case 2:
                                item = proveedorEJB.findProducto("43");
                                nivel = proveedorEJB.findNivelEducativo("1");
                                break;
                            case 3:
                                item = proveedorEJB.findProducto("21");
                                nivel = proveedorEJB.findNivelEducativo("3");
                                break;
                            case 4:
                                item = proveedorEJB.findProducto("43");
                                nivel = proveedorEJB.findNivelEducativo("3");
                                break;
                            case 5:
                                item = proveedorEJB.findProducto("21");
                                nivel = proveedorEJB.findNivelEducativo("4");
                                break;
                            case 6:
                                item = proveedorEJB.findProducto("43");
                                nivel = proveedorEJB.findNivelEducativo("4");
                                break;
                            case 7:
                                item = proveedorEJB.findProducto("21");
                                nivel = proveedorEJB.findNivelEducativo("5");
                                break;
                            case 8:
                                item = proveedorEJB.findProducto("43");
                                nivel = proveedorEJB.findNivelEducativo("5");
                                break;
                            case 9:
                                item = proveedorEJB.findProducto("21");
                                nivel = proveedorEJB.findNivelEducativo("6");
                                break;
                            case 10:
                                item = proveedorEJB.findProducto("43");
                                nivel = proveedorEJB.findNivelEducativo("6");
                                break;
                            default:
                                item = null;
                                nivel = null;
                                msjError = "El item ingresado no es válido.";
                        }
                        break;
                }

                if (item == null && nivel == null) {
                } else if (isProductoIsValid(item.getIdProducto())) {
                    precioRef.setIdProducto(item);
                    precioRef.setIdNivelEducativo(nivel);
                    if (precioLibro.intValue() > 0) {
                        precioRef.setPrecioReferencia(precioLibro);
                    }
                } else {
                    precioRef.setNoItem("");
                    msjError = "El proveedore NO ESTA CALIFICADO para ofertar este ITEM";
                }

            }
        }
    }

    private boolean isProductoIsValid(BigDecimal idProducto) {
        for (CatalogoProducto producto : lstItem) {
            if (producto.getIdProducto().intValue() == idProducto.intValue()) {
                return true;
            }
        }
        JsfUtil.mensajeError("El proveedore NO ESTA CALIFICADO para ofertar este ITEM");
        return false;
    }

    public void updateFilaDetalle() {
        if (rowEdit != null) {
            PrimeFaces.current().ajax().update("tblDetallePrecio:" + rowEdit + ":descripcionItem");
            PrimeFaces.current().ajax().update("tblDetallePrecio:" + rowEdit + ":nivelEducativo");
            PrimeFaces.current().ajax().update("tblDetallePrecio:" + rowEdit + ":precio");
            PrimeFaces.current().ajax().update("tblDetallePrecio:" + rowEdit + ":precio2");
        }
        if (!msjError.isEmpty()) {
            JsfUtil.mensajeAlerta(msjError);
        }
    }
}
