/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RCeron
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultAvanceFeriaDto",
        entities = @EntityResult(entityClass = AvanceFeriaDto.class))
public class AvanceFeriaDto implements Serializable {

    @Id
    private BigDecimal idRow;
    private String codigoMunicipio;
    private String nombreMun;
    private Integer asistenciaCE;
    private Integer procesadoUniformes;
    private Integer procesadoUtiles;
    private Integer procesadoZapatos;

    public AvanceFeriaDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public Integer getAsistenciaCE() {
        return asistenciaCE;
    }

    public void setAsistenciaCE(Integer asistenciaCE) {
        this.asistenciaCE = asistenciaCE;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getNombreMun() {
        return nombreMun;
    }

    public void setNombreMun(String nombreMun) {
        this.nombreMun = nombreMun;
    }

    public Integer getProcesadoUniformes() {
        return procesadoUniformes;
    }

    public void setProcesadoUniformes(Integer procesadoUniformes) {
        this.procesadoUniformes = procesadoUniformes;
    }

    public Integer getProcesadoUtiles() {
        return procesadoUtiles;
    }

    public void setProcesadoUtiles(Integer procesadoUtiles) {
        this.procesadoUtiles = procesadoUtiles;
    }

    public Integer getProcesadoZapatos() {
        return procesadoZapatos;
    }

    public void setProcesadoZapatos(Integer procesadoZapatos) {
        this.procesadoZapatos = procesadoZapatos;
    }

    @Override
    public String toString() {
        return "AvanceFeria{" + "codigoMunicipio=" + codigoMunicipio + ", nombreMun=" + nombreMun + ", asistenciaCE=" + asistenciaCE + "}";
    }
}
