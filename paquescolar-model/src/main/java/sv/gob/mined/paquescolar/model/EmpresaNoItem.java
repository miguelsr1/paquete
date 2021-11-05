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
@Table(name = "EMPRESA_NO_ITEM")
@NamedQueries({
    @NamedQuery(name = "EmpresaNoItem.findAll", query = "SELECT e FROM EmpresaNoItem e")})
public class EmpresaNoItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EMP_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMP_NOITEM")
    @SequenceGenerator(name = "SEQ_EMP_NOITEM", sequenceName = "SEQ_EMP_NOITEM", allocationSize = 1, initialValue = 1)
    private Long idEmpNo;
    @Column(name = "ID_EMPRESA")
    private BigDecimal idEmpresa;
    @Column(name = "ITEM_1")
    private String item1;
    @Column(name = "ITEM_2")
    private String item2;
    @Column(name = "ITEM_3")
    private String item3;
    @Column(name = "ITEM_4")
    private String item4;
    @Column(name = "ITEM_4_4")
    private String item44;
    @Column(name = "ITEM_5")
    private String item5;
    @Column(name = "ITEM_5_1")
    private String item51;
    @Column(name = "ITEM_6")
    private String item6;
    @Column(name = "ITEM_7")
    private String item7;
    @Column(name = "ITEM_8")
    private String item8;
    @Column(name = "ITEM_9")
    private String item9;
    @Column(name = "ITEM_10")
    private String item10;
    @Column(name = "ITEM_11")
    private String item11;
    @Column(name = "ITEM_12")
    private String item12;
    @Column(name = "ITEM_13")
    private String item13;
    @Column(name = "ITEM_22")
    private String item22;
    @Column(name = "ID_MUESTRA_INTERES")
    private BigDecimal idMuestraInteres;

    public EmpresaNoItem() {
    }

    public EmpresaNoItem(Long idEmpNo) {
        this.idEmpNo = idEmpNo;
    }

    public Long getIdEmpNo() {
        return idEmpNo;
    }

    public void setIdEmpNo(Long idEmpNo) {
        this.idEmpNo = idEmpNo;
    }

    public BigDecimal getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigDecimal idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public String getItem44() {
        return item44;
    }

    public void setItem44(String item44) {
        this.item44 = item44;
    }

    public String getItem51() {
        return item51;
    }

    public void setItem51(String item51) {
        this.item51 = item51;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getItem5() {
        return item5;
    }

    public void setItem5(String item5) {
        this.item5 = item5;
    }

    public String getItem6() {
        return item6;
    }

    public void setItem6(String item6) {
        this.item6 = item6;
    }

    public String getItem7() {
        return item7;
    }

    public void setItem7(String item7) {
        this.item7 = item7;
    }

    public String getItem8() {
        return item8;
    }

    public void setItem8(String item8) {
        this.item8 = item8;
    }

    public String getItem9() {
        return item9;
    }

    public void setItem9(String item9) {
        this.item9 = item9;
    }

    public String getItem10() {
        return item10;
    }

    public void setItem10(String item10) {
        this.item10 = item10;
    }

    public String getItem11() {
        return item11;
    }

    public void setItem11(String item11) {
        this.item11 = item11;
    }

    public String getItem12() {
        return item12;
    }

    public void setItem12(String item12) {
        this.item12 = item12;
    }

    public String getItem13() {
        return item13;
    }

    public void setItem13(String item13) {
        this.item13 = item13;
    }

    public String getItem22() {
        return item22;
    }

    public void setItem22(String item22) {
        this.item22 = item22;
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
        hash += (idEmpNo != null ? idEmpNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaNoItem)) {
            return false;
        }
        EmpresaNoItem other = (EmpresaNoItem) object;
        return !((this.idEmpNo == null && other.idEmpNo != null) || (this.idEmpNo != null && !this.idEmpNo.equals(other.idEmpNo)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.EmpresaNoItem[ idEmpNo=" + idEmpNo + " ]";
    }

}
