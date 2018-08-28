/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author misanchez
 */
public class RptActaAdjudicacion  implements Serializable{
    private static final long serialVersionUID = 1L;
    private String nombreCentroEscolar;
    private String codigoEntidad;
    private String fechaApertura;
    private int horaRegistro;
    private int minutoRegistro;
    private String modalidad;
    private String descripcionRubro;
    private String justificacionAdj;
    private String inicialesUsuario;
    
    private List<ParticipanteBean> lstParticipantes;
    private List<DetalleItemBean> lstDetalleItem = new ArrayList<DetalleItemBean>();

    public RptActaAdjudicacion() {
    }

    public String getNombreCentroEscolar() {
        return nombreCentroEscolar;
    }

    public void setNombreCentroEscolar(String nombreCentroEscolar) {
        this.nombreCentroEscolar = nombreCentroEscolar;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public int getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(int horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public int getMinutoRegistro() {
        return minutoRegistro;
    }

    public void setMinutoRegistro(int minutoRegistro) {
        this.minutoRegistro = minutoRegistro;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public String getJustificacionAdj() {
        return justificacionAdj;
    }

    public void setJustificacionAdj(String justificacionAdj) {
        this.justificacionAdj = justificacionAdj;
    }

    public String getInicialesUsuario() {
        return inicialesUsuario;
    }

    public void setInicialesUsuario(String inicialesUsuario) {
        this.inicialesUsuario = inicialesUsuario;
    }

    public List<ParticipanteBean> getLstParticipantes() {
        return lstParticipantes;
    }

    public void setLstParticipantes(List<ParticipanteBean> lstParticipantes) {
        this.lstParticipantes = lstParticipantes;
    }   

    public List<DetalleItemBean> getLstDetalleItem() {
        return lstDetalleItem;
    }

    public void setLstDetalleItem(List<DetalleItemBean> lstDetalleItem) {
        this.lstDetalleItem = lstDetalleItem;
    }
}