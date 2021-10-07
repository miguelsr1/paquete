/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.pagoprov;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.gob.mined.app.web.util.RecuperarProcesoUtil;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.model.pojos.proveedor.SeguimientoIngresoProveDto;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class ReporteView extends RecuperarProcesoUtil implements Serializable {

    private BigDecimal idRubro = new BigDecimal(0);
    private Date fecha;
    private List<SeguimientoIngresoProveDto> lstDatos = new ArrayList();

    @EJB
    private ProveedorEJB proveedorEJB;

    public BigDecimal getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(BigDecimal idRubro) {
        this.idRubro = idRubro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<SeguimientoIngresoProveDto> getLstDatos() {
        return lstDatos;
    }

    public void generarExcelUpdateProveedores() {
        if (idRubro.intValue() > 0 && fecha != null) {
            lstDatos = proveedorEJB.findSeguimiento(idRubro, getRecuperarProceso().getProcesoAdquisicion().getIdAnho().getIdAnho(), JsfUtil.getFechaString(fecha));
        }else{
            JsfUtil.mensajeAlerta("Por favor, seleccione un rubro de adquisición y una fecha para realizar la búsqueda");
        }
    }
}
