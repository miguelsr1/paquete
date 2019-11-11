/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultAvanceContratosDto",
        entities = @EntityResult(entityClass = AvanceContratosDto.class))
public class AvanceContratosDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private BigDecimal idRow;
    private String codigoDepartamento;
    private String nombreDepartamento;
    private String descripcionPersoneria;
    private Integer cantidadCe;
    private Integer cantidadEmpresa;
    private BigDecimal montoContrato;

    public AvanceContratosDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getDescripcionPersoneria() {
        return descripcionPersoneria;
    }

    public void setDescripcionPersoneria(String descripcionPersoneria) {
        this.descripcionPersoneria = descripcionPersoneria;
    }

    public Integer getCantidadCe() {
        return cantidadCe;
    }

    public void setCantidadCe(Integer cantidadCe) {
        this.cantidadCe = cantidadCe;
    }

    public Integer getCantidadEmpresa() {
        return cantidadEmpresa;
    }

    public void setCantidadEmpresa(Integer cantidadEmpresa) {
        this.cantidadEmpresa = cantidadEmpresa;
    }

    public BigDecimal getMontoContrato() {
        return montoContrato;
    }

    public void setMontoContrato(BigDecimal montoContrato) {
        this.montoContrato = montoContrato;
    }

}
