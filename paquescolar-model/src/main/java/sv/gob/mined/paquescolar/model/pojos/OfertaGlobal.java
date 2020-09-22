/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultOfertaGlobal",
        entities = @EntityResult(entityClass = OfertaGlobal.class))
public class OfertaGlobal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idRow;
    private String departamento;
    private String razonSocial;
    @Transient
    private String anho;
    private String rubro;
    private String lugarFecha;
    private String nombreRepresentante;
    private String duiRepresentante;
    private String nitRepresentante;
    private String nitSociedad;
    private BigDecimal capacidadCalificada;
    @Transient
    private List<DetItemOfertaGlobal> lstDetItemOfertaGlobal = new ArrayList();
    @Transient
    private List<DetItemOfertaGlobal> lstDetItemOfertaGlobalLibros = new ArrayList();
    @Transient
    private List<DetMunIntOfertaGlobal> lstMunIntOfertaGlobal = new ArrayList();
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    public OfertaGlobal() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getLugarFecha() {
        return lugarFecha;
    }

    public void setLugarFecha(String lugarFecha) {
        this.lugarFecha = lugarFecha;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getNitRepresentante() {
        return nitRepresentante;
    }

    public void setNitRepresentante(String nitRepresentante) {
        this.nitRepresentante = nitRepresentante;
    }

    public List<DetItemOfertaGlobal> getLstDetItemOfertaGlobal() {
        return lstDetItemOfertaGlobal;
    }

    public void setLstDetItemOfertaGlobal(List<DetItemOfertaGlobal> lstDetItemOfertaGlobal) {
        this.lstDetItemOfertaGlobal = lstDetItemOfertaGlobal;
    }

    public List<DetItemOfertaGlobal> getLstDetItemOfertaGlobalLibros() {
        return lstDetItemOfertaGlobalLibros;
    }

    public void setLstDetItemOfertaGlobalLibros(List<DetItemOfertaGlobal> lstDetItemOfertaGlobalLibros) {
        this.lstDetItemOfertaGlobalLibros = lstDetItemOfertaGlobalLibros;
    }

    public List<DetMunIntOfertaGlobal> getLstMunIntOfertaGlobal() {
        return lstMunIntOfertaGlobal;
    }

    public void setLstMunIntOfertaGlobal(List<DetMunIntOfertaGlobal> lstMunIntOfertaGlobal) {
        this.lstMunIntOfertaGlobal = lstMunIntOfertaGlobal;
    }

    public BigDecimal getCapacidadCalificada() {
        return capacidadCalificada;
    }

    public void setCapacidadCalificada(BigDecimal capacidadCalificada) {
        this.capacidadCalificada = capacidadCalificada;
    }

    public String getDuiRepresentante() {
        return duiRepresentante;
    }

    public void setDuiRepresentante(String duiRepresentante) {
        this.duiRepresentante = duiRepresentante;
    }

    public String getNitSociedad() {
        return nitSociedad;
    }

    public void setNitSociedad(String nitSociedad) {
        this.nitSociedad = nitSociedad;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
