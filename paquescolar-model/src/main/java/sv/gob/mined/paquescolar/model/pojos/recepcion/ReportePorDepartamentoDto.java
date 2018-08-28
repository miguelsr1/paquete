/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.recepcion;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jaime Ramirez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultReportePorDepartamento",
        entities = @EntityResult(entityClass = ReportePorDepartamentoDto.class))
public class ReportePorDepartamentoDto implements Serializable{
    @Id
    private String codigoDepartamento;
    private String nombreDepartamento;
    private Integer totalContratosUniforme;
    private Integer totalEntregasUniforme;
    private Integer totalContratosUtiles;
    private Integer totalEntregasUtiles;
    private Integer totalContratosZapatos;    
    private Integer totalEntregasZapatos;
    private Integer totalContratos;
    private Integer totalEntregas;
    private String porcentajeDeAvance; 

    public ReportePorDepartamentoDto() {
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

    public Integer getTotalContratosUniforme() {
        return totalContratosUniforme;
    }

    public void setTotalContratosUniforme(Integer totalContratosUniforme) {
        this.totalContratosUniforme = totalContratosUniforme;
    }

    public Integer getTotalEntregasUniforme() {
        return totalEntregasUniforme;
    }

    public void setTotalEntregasUniforme(Integer totalEntregasUniforme) {
        this.totalEntregasUniforme = totalEntregasUniforme;
    }

    public Integer getTotalContratosUtiles() {
        return totalContratosUtiles;
    }

    public void setTotalContratosUtiles(Integer totalContratosUtiles) {
        this.totalContratosUtiles = totalContratosUtiles;
    }

    public Integer getTotalEntregasUtiles() {
        return totalEntregasUtiles;
    }

    public void setTotalEntregasUtiles(Integer totalEntregasUtiles) {
        this.totalEntregasUtiles = totalEntregasUtiles;
    }

    public Integer getTotalContratosZapatos() {
        return totalContratosZapatos;
    }

    public void setTotalContratosZapatos(Integer totalContratosZapatos) {
        this.totalContratosZapatos = totalContratosZapatos;
    }

    public Integer getTotalEntregasZapatos() {
        return totalEntregasZapatos;
    }

    public void setTotalEntregasZapatos(Integer totalEntregasZapatos) {
        this.totalEntregasZapatos = totalEntregasZapatos;
    }

    public Integer getTotalContratos() {
        return totalContratos;
    }

    public void setTotalContratos(Integer totalContratos) {
        this.totalContratos = totalContratos;
    }

    public Integer getTotalEntregas() {
        return totalEntregas;
    }

    public void setTotalEntregas(Integer totalEntregas) {
        this.totalEntregas = totalEntregas;
    }

    public String getPorcentajeDeAvance() {
        return porcentajeDeAvance;
    }

    public void setPorcentajeDeAvance(String porcentajeDeAvance) {
        this.porcentajeDeAvance = porcentajeDeAvance;
    }
    
}