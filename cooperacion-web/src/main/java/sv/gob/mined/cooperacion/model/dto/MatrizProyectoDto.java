package sv.gob.mined.cooperacion.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultMatrizProyectoDto",
        entities = @EntityResult(entityClass = MatrizProyectoDto.class))
public class MatrizProyectoDto implements Serializable {

    @Id
    private Long idProyecto;
    private String nombreProyecto;
    private String descripcion;
    private String institucion;
    private String sectorIntervencion;
    private String tmCooperacion;
    private String descripcionObjetivo;
    private String descripcionMeta;
    private String fechaEstimadaInicio;
    private String fechaEstimadaFin;
    private String nombreCooperante;
    private BigDecimal montoInversion;
    private String anho;

    public MatrizProyectoDto() {
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getSectorIntervencion() {
        return sectorIntervencion;
    }

    public void setSectorIntervencion(String sectorIntervencion) {
        this.sectorIntervencion = sectorIntervencion;
    }

    public String getTmCooperacion() {
        return tmCooperacion;
    }

    public void setTmCooperacion(String tmCooperacion) {
        this.tmCooperacion = tmCooperacion;
    }

    public String getDescripcionObjetivo() {
        return descripcionObjetivo;
    }

    public void setDescripcionObjetivo(String descripcionObjetivo) {
        this.descripcionObjetivo = descripcionObjetivo;
    }

    public String getDescripcionMeta() {
        return descripcionMeta;
    }

    public void setDescripcionMeta(String descripcionMeta) {
        this.descripcionMeta = descripcionMeta;
    }

    public String getFechaEstimadaInicio() {
        return fechaEstimadaInicio;
    }

    public void setFechaEstimadaInicio(String fechaEstimadaInicio) {
        this.fechaEstimadaInicio = fechaEstimadaInicio;
    }

    public String getFechaEstimadaFin() {
        return fechaEstimadaFin;
    }

    public void setFechaEstimadaFin(String fechaEstimadaFin) {
        this.fechaEstimadaFin = fechaEstimadaFin;
    }

    public String getNombreCooperante() {
        return nombreCooperante;
    }

    public void setNombreCooperante(String nombreCooperante) {
        this.nombreCooperante = nombreCooperante;
    }

    public BigDecimal getMontoInversion() {
        return montoInversion;
    }

    public void setMontoInversion(BigDecimal montoInversion) {
        this.montoInversion = montoInversion;
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }
    
}
