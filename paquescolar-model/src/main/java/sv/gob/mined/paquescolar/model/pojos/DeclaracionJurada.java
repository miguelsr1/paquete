/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
@SqlResultSetMapping(name = "defaultDeclaracionJurada",
        entities = @EntityResult(entityClass = DeclaracionJurada.class))
public class DeclaracionJurada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idRow;
    private String rubro;
    private String anho;
    @Transient
    private String ciudad;
    @Transient
    private Date fecha;
    private String razonSocial;
    private String direccionEmpresa;
    private String nitEmpresa;
    private String representanteLegal;
    private String direccionRepre;
    private String nitRepre;
    private String duiRepre;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    public DeclaracionJurada() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getDireccionRepre() {
        return direccionRepre;
    }

    public void setDireccionRepre(String direccionRepre) {
        this.direccionRepre = direccionRepre;
    }

    public String getNitRepre() {
        return nitRepre;
    }

    public void setNitRepre(String nitRepre) {
        this.nitRepre = nitRepre;
    }

    public String getDuiRepre() {
        return duiRepre;
    }

    public void setDuiRepre(String duiRepre) {
        this.duiRepre = duiRepre;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
