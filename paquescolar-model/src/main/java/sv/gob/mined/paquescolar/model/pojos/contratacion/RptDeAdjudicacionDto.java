/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultAdjudicacionDto",
        entities = @EntityResult(entityClass = RptDeAdjudicacionDto.class))
public class RptDeAdjudicacionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idRow;
    private String nombreCentroEscolar;
    private String codigoEntidad;
    private String modalidad;
    private String fechaApertura;
    private int horaRegistro;
    private int minutoRegistro;

    private String descripcionRubro;
    private String justificacionAdj;
    private String inicialesUsuario;

    private List<ParticipanteDto> lstParticipantes;
    private List<DetalleItemDto> lstDetalleItem = new ArrayList();

    public RptDeAdjudicacionDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
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

    public List<ParticipanteDto> getLstParticipantes() {
        return lstParticipantes;
    }

    public void setLstParticipantes(List<ParticipanteDto> lstParticipantes) {
        this.lstParticipantes = lstParticipantes;
    }

    public List<DetalleItemDto> getLstDetalleItem() {
        return lstDetalleItem;
    }

    public void setLstDetalleItem(List<DetalleItemDto> lstDetalleItem) {
        this.lstDetalleItem = lstDetalleItem;
    }
}
