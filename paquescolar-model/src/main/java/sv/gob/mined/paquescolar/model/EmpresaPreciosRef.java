/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "EMPRESA_PRECIOS_REF")
@NamedQueries({
    @NamedQuery(name = "EmpresaPreciosRef.findAll", query = "SELECT e FROM EmpresaPreciosRef e")})
public class EmpresaPreciosRef implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EMPRESA_PRE_REF")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMP_PRE_REF")
    @SequenceGenerator(name = "SEQ_EMP_PRE_REF", sequenceName = "SEQ_EMP_PRE_REF", allocationSize = 1, initialValue = 1)
    private Long idEmpresaPreRef;
    @Column(name = "ID_EMPRESA")
    private BigDecimal idEmpresa;
    @Column(name = "ITEM_1")
    private BigDecimal item1;
    @Column(name = "ITEM_2")
    private BigDecimal item2;
    @Column(name = "ITEM_3")
    private BigDecimal item3;
    @Column(name = "ITEM_4")
    private BigDecimal item4;
    @Column(name = "ITEM_5")
    private BigDecimal item5;
    @Column(name = "ITEM_6")
    private BigDecimal item6;
    @Column(name = "ITEM_7")
    private BigDecimal item7;
    @Column(name = "ITEM_8")
    private BigDecimal item8;
    @Column(name = "ITEM_9")
    private BigDecimal item9;
    @Column(name = "ITEM_10")
    private BigDecimal item10;
    @Column(name = "ITEM_11")
    private BigDecimal item11;
    @Column(name = "ITEM_12")
    private BigDecimal item12;
    @Column(name = "ITEM_13")
    private BigDecimal item13;
    @Column(name = "ID_MUESTRA_INTERES")
    private BigDecimal idMuestraInteres;

    public EmpresaPreciosRef() {
    }

    public EmpresaPreciosRef(Long idEmpresaPreRef) {
        this.idEmpresaPreRef = idEmpresaPreRef;
    }

    public Long getIdEmpresaPreRef() {
        return idEmpresaPreRef;
    }

    public void setIdEmpresaPreRef(Long idEmpresaPreRef) {
        this.idEmpresaPreRef = idEmpresaPreRef;
    }

    public BigDecimal getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigDecimal idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public BigDecimal getItem1() {
        return item1;
    }

    public void setItem1(BigDecimal item1) {
        this.item1 = item1;
    }

    public BigDecimal getItem2() {
        return item2;
    }

    public void setItem2(BigDecimal item2) {
        this.item2 = item2;
    }

    public BigDecimal getItem3() {
        return item3;
    }

    public void setItem3(BigDecimal item3) {
        this.item3 = item3;
    }

    public BigDecimal getItem4() {
        return item4;
    }

    public void setItem4(BigDecimal item4) {
        this.item4 = item4;
    }

    public BigDecimal getItem5() {
        return item5;
    }

    public void setItem5(BigDecimal item5) {
        this.item5 = item5;
    }

    public BigDecimal getItem6() {
        return item6;
    }

    public void setItem6(BigDecimal item6) {
        this.item6 = item6;
    }

    public BigDecimal getItem7() {
        return item7;
    }

    public void setItem7(BigDecimal item7) {
        this.item7 = item7;
    }

    public BigDecimal getItem8() {
        return item8;
    }

    public void setItem8(BigDecimal item8) {
        this.item8 = item8;
    }

    public BigDecimal getItem9() {
        return item9;
    }

    public void setItem9(BigDecimal item9) {
        this.item9 = item9;
    }

    public BigDecimal getItem10() {
        return item10;
    }

    public void setItem10(BigDecimal item10) {
        this.item10 = item10;
    }

    public BigDecimal getItem11() {
        return item11;
    }

    public void setItem11(BigDecimal item11) {
        this.item11 = item11;
    }

    public BigDecimal getItem12() {
        return item12;
    }

    public void setItem12(BigDecimal item12) {
        this.item12 = item12;
    }

    public BigDecimal getItem13() {
        return item13;
    }

    public void setItem13(BigDecimal item13) {
        this.item13 = item13;
    }

    public BigDecimal getIdMuestraInteres() {
        return idMuestraInteres;
    }

    public void setIdMuestraInteres(BigDecimal idMuestraInteres) {
        this.idMuestraInteres = idMuestraInteres;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresaPreRef != null ? idEmpresaPreRef.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaPreciosRef)) {
            return false;
        }
        EmpresaPreciosRef other = (EmpresaPreciosRef) object;
        return !((this.idEmpresaPreRef == null && other.idEmpresaPreRef != null) || (this.idEmpresaPreRef != null && !this.idEmpresaPreRef.equals(other.idEmpresaPreRef)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.EmpresaPreciosRef[ idEmpresaPreRef=" + idEmpresaPreRef + " ]";
    }

}
