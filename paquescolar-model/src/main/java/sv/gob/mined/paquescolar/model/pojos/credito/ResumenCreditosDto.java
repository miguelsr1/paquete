/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos.credito;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DesarrolloPc
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultRptCredito",
        entities = @EntityResult(entityClass = ResumenCreditosDto.class))
public class ResumenCreditosDto implements Serializable {

    @Id
    private BigDecimal idRow;
    private String codEntFinanciera;
    private String nombreEntFinan;
    private BigInteger cantidadCreditos;
    private BigDecimal montoCreditoUniforme;
    private BigDecimal montoCreditoUniforme1;
    private BigDecimal montoCreditoUniforme2;
    private BigDecimal montoCreditoUtiles;
    private BigDecimal montoCreditoZapatos;

    public ResumenCreditosDto() {
    }

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getCodEntFinanciera() {
        return codEntFinanciera;
    }

    public void setCodEntFinanciera(String codEntFinanciera) {
        this.codEntFinanciera = codEntFinanciera;
    }

    public String getNombreEntFinan() {
        return nombreEntFinan;
    }

    public void setNombreEntFinan(String nombreEntFinan) {
        this.nombreEntFinan = nombreEntFinan;
    }

    public BigInteger getCantidadCreditos() {
        return cantidadCreditos;
    }

    public void setCantidadCreditos(BigInteger cantidadCreditos) {
        this.cantidadCreditos = cantidadCreditos;
    }

    public BigDecimal getMontoCreditoUniforme() {
        return montoCreditoUniforme;
    }

    public void setMontoCreditoUniforme(BigDecimal montoCreditoUniforme) {
        this.montoCreditoUniforme = montoCreditoUniforme;
    }

    public BigDecimal getMontoCreditoUniforme1() {
        return montoCreditoUniforme1;
    }

    public void setMontoCreditoUniforme1(BigDecimal montoCreditoUniforme1) {
        this.montoCreditoUniforme1 = montoCreditoUniforme1;
    }

    public BigDecimal getMontoCreditoUniforme2() {
        return montoCreditoUniforme2;
    }

    public void setMontoCreditoUniforme2(BigDecimal montoCreditoUniforme2) {
        this.montoCreditoUniforme2 = montoCreditoUniforme2;
    }

    public BigDecimal getMontoCreditoUtiles() {
        return montoCreditoUtiles;
    }

    public void setMontoCreditoUtiles(BigDecimal montoCreditoUtiles) {
        this.montoCreditoUtiles = montoCreditoUtiles;
    }

    public BigDecimal getMontoCreditoZapatos() {
        return montoCreditoZapatos;
    }

    public void setMontoCreditoZapatos(BigDecimal montoCreditoZapatos) {
        this.montoCreditoZapatos = montoCreditoZapatos;
    }
}
