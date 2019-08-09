/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.RecuperarProceso;
import sv.gob.mined.app.web.util.RptExcel;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.model.pojos.VwRptProveedoresContratadosDto;
import sv.gob.mined.paquescolar.model.pojos.contratacion.DetalleContratacionPorItemDto;

/**
 *
 * @author DesarrolloPc
 */
@ManagedBean
@ViewScoped
public class ReportesWebController implements Serializable {

    private String codigoDepartamento;
    private Integer idDetProceso;
    private BigDecimal idRubro;
    private BigDecimal montoTotal;
    private BigDecimal cantidadTotal;
    private List<VwRptProveedoresContratadosDto> lstProveedoresHaciendaDto = new ArrayList<>();

    @EJB
    ProveedorEJB proveedorEJB;
    @EJB
    AnhoProcesoEJB anhoProcesoEJB;
    
    @ManagedProperty("#{recuperarProceso}")
    private RecuperarProceso recuperarProceso;

    public ReportesWebController() {
    }
    
    public RecuperarProceso getRecuperarProceso() {
        return recuperarProceso;
    }

    public void setRecuperarProceso(RecuperarProceso recuperarProceso) {
        this.recuperarProceso = recuperarProceso;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public BigDecimal getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(BigDecimal idRubro) {
        this.idRubro = idRubro;
    }

    public List<VwRptProveedoresContratadosDto> getLstProveedoresHaciendaDto() {
        return lstProveedoresHaciendaDto;
    }

    public void generarRptProveedoresHacienda() {
        montoTotal = BigDecimal.ZERO;
        cantidadTotal = BigDecimal.ZERO;
        idDetProceso = anhoProcesoEJB.getDetProcesoAdq(recuperarProceso.getProcesoAdquisicion(), idRubro).getIdDetProcesoAdq();
        lstProveedoresHaciendaDto = proveedorEJB.getLstProveedoresHacienda(idDetProceso, codigoDepartamento);
        for (VwRptProveedoresContratadosDto vwRptProveedoresHaciendaDto : lstProveedoresHaciendaDto) {
            cantidadTotal = cantidadTotal.add(vwRptProveedoresHaciendaDto.getCantidadContrato());
            montoTotal = montoTotal.add((vwRptProveedoresHaciendaDto.getMontoContrato()));
        }
    }

    public void generarRptResumenContrataciones() {
        montoTotal = BigDecimal.ZERO;
        cantidadTotal = BigDecimal.ZERO;
        idDetProceso = anhoProcesoEJB.getDetProcesoAdq(recuperarProceso.getProcesoAdquisicion(), idRubro).getIdDetProcesoAdq();
        lstProveedoresHaciendaDto = proveedorEJB.getLstResumenContratacionByProcesoAndDepartamento(idDetProceso, codigoDepartamento);
        for (VwRptProveedoresContratadosDto vwRptProveedoresHaciendaDto : lstProveedoresHaciendaDto) {
            cantidadTotal = cantidadTotal.add(vwRptProveedoresHaciendaDto.getCantidadContrato());
            montoTotal = montoTotal.add((vwRptProveedoresHaciendaDto.getMontoContrato()));
        }
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public BigDecimal getCantidadTotal() {
        return cantidadTotal;
    }

    public void resumenContratacionesXls(Object document) {
        int[] numEnt = {0, 14};
        int[] numDec = {15};
        RptExcel.generarRptExcelGenerico((HSSFWorkbook) document, numEnt, numDec);
    }
    
    public void resumenPagoRequerimientoPorProveedorXls(Object document) {
        int[] numEnt = {2};
        int[] numDec = {3,4,5,6,7};
        RptExcel.generarRptExcelGenerico((HSSFWorkbook) document, numEnt, numDec);
    }

    public void generarReporte() {
        idDetProceso = anhoProcesoEJB.getDetProcesoAdq(recuperarProceso.getProcesoAdquisicion(), idRubro).getIdDetProcesoAdq();
        List<DetalleContratacionPorItemDto> lst = proveedorEJB.getLstDetalleContratacionPorItem(idDetProceso);

        if (codigoDepartamento != null) {
            if (!lst.isEmpty()) {

            } else {
                JsfUtil.mensajeInformacion("No se encontraron registros.");
            }
        }
    }
}
