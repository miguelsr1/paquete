/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.RequerimientoFondos;
import sv.gob.mined.paquescolar.model.TransferenciaRequerimiento;

@ManagedBean
@ViewScoped
public class InfoRequerimientoView extends RecuperarProcesoUtil implements Serializable {

    private String codigoDepartamento = "";
    private String anho;
    private BigDecimal idRubro = BigDecimal.ZERO;
    private BigDecimal totalTransferido = BigDecimal.ZERO;

    private RequerimientoFondos requerimientoFondos = new RequerimientoFondos();
    private DetalleProcesoAdq detalleProcesoAdq = new DetalleProcesoAdq();
    private TransferenciaRequerimiento transReq = new TransferenciaRequerimiento();

    private List<RequerimientoFondos> lstRequerimientoFondos = new ArrayList();
    private List<TransferenciaRequerimiento> lstTransferenciaRequerimientos = new ArrayList();

    @EJB
    private ReportesEJB reportesEJB;
    @EJB
    private ProveedorEJB proveedorEJB;

    @PostConstruct
    public void ini() {
        codigoDepartamento = getRecuperarProceso().getDepartamento();
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        if (codigoDepartamento != null) {
            this.codigoDepartamento = codigoDepartamento;
        }
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    public BigDecimal getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(BigDecimal rubro) {
        this.idRubro = rubro;
    }

    public BigDecimal getTotalTransferido() {
        return totalTransferido;
    }

    public RequerimientoFondos getRequerimientoFondos() {
        return requerimientoFondos;
    }

    public void setRequerimientoFondos(RequerimientoFondos requerimientoFondos) {
        if (requerimientoFondos != null) {
            this.requerimientoFondos = requerimientoFondos;
        }
    }

    public TransferenciaRequerimiento getTransReq() {
        return transReq;
    }

    public void setTransReq(TransferenciaRequerimiento transReq) {
        this.transReq = transReq;
    }

    public List<RequerimientoFondos> getLstRequerimientoFondos() {
        return lstRequerimientoFondos;
    }

    public void setLstRequerimientoFondos(List<RequerimientoFondos> lstRequerimientoFondos) {
        this.lstRequerimientoFondos = lstRequerimientoFondos;
    }

    public List<TransferenciaRequerimiento> getLstTransferenciaRequerimientos() {
        return lstTransferenciaRequerimientos;
    }

    public void setLstTransferenciaRequerimientos(List<TransferenciaRequerimiento> lstTransferenciaRequerimientos) {
        this.lstTransferenciaRequerimientos = lstTransferenciaRequerimientos;
    }

    public void imprimirRequerimiento() {
        try {
            List<JasperPrint> jasperPrintList = new ArrayList();

            jasperPrintList.add(imprimirRpt(requerimientoFondos, JsfUtil.getNombreDepartamentoByCodigo(codigoDepartamento), "rptRequerimientoFondos.jasper", "requerimientoFondosDet"));
            jasperPrintList.add(imprimirRpt(requerimientoFondos, JsfUtil.getNombreDepartamentoByCodigo(codigoDepartamento), "rptResumenRequerimiento.jasper", "resumenRequerimientoFondos"));

            Reportes.generarReporte(jasperPrintList, "requerimiento_" + codigoDepartamento.replace(" ", ""));
        } catch (IOException | JRException ex) {
            Logger.getLogger(PagoProveedoresController.class.getName()).log(Level.WARNING, "Error en la generacion del requerimiento {0}", requerimientoFondos.getFormatoRequerimiento());
        }
    }

    public JasperPrint imprimirRpt(RequerimientoFondos req, String nombreDepartamento, String nombreRpt, String nombrePdf) {
        JasperPrint jp;
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        HashMap param = new HashMap();
        param.put("pEscudo", ctx.getRealPath(Reportes.PATH_IMAGENES) + File.separator);
        param.put("pDepartamental", nombreDepartamento);
        param.put("pUniforme", detalleProcesoAdq.getIdDetProcesoAdq() == 25 ? 1 : 0);
        param.put("pIdRequerimiento", req.getIdRequerimiento().intValue());
        param.put("pAnho", "20" + anho);
        jp = reportesEJB.getRpt(param, Reportes.getPathReporte("sv/gob/mined/apps/reportes/pagoproveedor" + File.separator + nombreRpt));
        return jp;
    }

    public void buscarRequerimientosImp() {
        detalleProcesoAdq = JsfUtil.findDetalle(getRecuperarProceso().getProcesoAdquisicion(), idRubro);
        if (idRubro != null) {
            lstRequerimientoFondos = proveedorEJB.getLstRequerimientos(codigoDepartamento, detalleProcesoAdq.getIdDetProcesoAdq());
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar un rubro de adquisición.");
        }
    }

    public void showTransferenciaRealizadas() {
        lstTransferenciaRequerimientos = proveedorEJB.getLstTransferenciaRequerimientos(requerimientoFondos);
        totalTransferido = BigDecimal.ZERO;

        lstTransferenciaRequerimientos.forEach(trans -> {
            if (trans.getEstadoEliminacion() == 0) {
                totalTransferido = totalTransferido.add(trans.getMontoTransferido());
            }
        });
        PrimeFaces.current().executeScript("PF('dlgTransferencia').show()");
    }

    public void guardarTransferencia() {
        if (transReq.getMontoTransferido() != null && transReq.getFechaTransferencia() != null) {
            totalTransferido = BigDecimal.ZERO;

            lstTransferenciaRequerimientos.forEach(trans -> {
                if (trans.getEstadoEliminacion() == 0) {
                    totalTransferido = totalTransferido.add(trans.getMontoTransferido());
                }
            });

            if (requerimientoFondos.getMontoTotal().compareTo(totalTransferido.add(transReq.getMontoTransferido())) != -1) {
                if (transReq.getIdTransferencia() == null) {
                    transReq.setIdRequerimiento(requerimientoFondos);
                    transReq.setFechaInsercion(new Date());
                    transReq.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                    transReq.setEstadoEliminacion((short) 0);
                } else {
                    transReq.setFechaModificacion(new Date());
                    transReq.setUsuarioModificacion(VarSession.getVariableSessionUsuario());
                }

                proveedorEJB.guardarTransferenciaRequerimiento(transReq);
                showTransferenciaRealizadas();
                JsfUtil.mensajeInsert();
            } else {
                JsfUtil.mensajeAlerta("Con este monto se sobrepasa el monto total del requerimiento, por favor revisar.");
            }
        } else {
            JsfUtil.mensajeAlerta("Debe de ingresar monto y fecha de transferencia");
        }
    }
}
