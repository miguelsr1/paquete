package sv.gob.mined.paquescolar.model.pojos.pagoprove;

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
@SqlResultSetMapping(name = "defaultInformeF14Dto",
        entities = @EntityResult(entityClass = InformeF14Dto.class))
public class InformeF14Dto implements Serializable {

    @Id
    private BigDecimal idRow;
    private String colA;
    private String colB;
    private String colC;
    private String colD;
    private String colE;
    private String colF;
    private String colG;
    private String colH;
    private String colI;
    private String colJ;
    private String colK;
    private String colL;
    private String colLl;
    private String colM;
    private String colN;
    private String colO;
    private String colP;
    private String colQ;
    private String colR;
    private String colS;

    public BigDecimal getIdRow() {
        return idRow;
    }

    public void setIdRow(BigDecimal idRow) {
        this.idRow = idRow;
    }

    public String getColA() {
        return colA;
    }

    public void setColA(String colA) {
        this.colA = colA;
    }

    public String getColB() {
        return colB;
    }

    public void setColB(String colB) {
        this.colB = colB;
    }

    public String getColC() {
        return colC;
    }

    public void setColC(String colC) {
        this.colC = colC;
    }

    public String getColD() {
        return colD;
    }

    public void setColD(String colD) {
        this.colD = colD;
    }

    public String getColE() {
        return colE;
    }

    public void setColE(String colE) {
        this.colE = colE;
    }

    public String getColF() {
        return colF;
    }

    public void setColF(String colF) {
        this.colF = colF;
    }

    public String getColG() {
        return colG;
    }

    public void setColG(String colG) {
        this.colG = colG;
    }

    public String getColH() {
        return colH;
    }

    public void setColH(String colH) {
        this.colH = colH;
    }

    public String getColI() {
        return colI;
    }

    public void setColI(String colI) {
        this.colI = colI;
    }

    public String getColJ() {
        return colJ;
    }

    public void setColJ(String colJ) {
        this.colJ = colJ;
    }

    public String getColK() {
        return colK;
    }

    public void setColK(String colK) {
        this.colK = colK;
    }

    public String getColL() {
        return colL;
    }

    public void setColL(String colL) {
        this.colL = colL;
    }

    public String getColLl() {
        return colLl;
    }

    public void setColLl(String colLl) {
        this.colLl = colLl;
    }

    public String getColM() {
        return colM;
    }

    public void setColM(String colM) {
        this.colM = colM;
    }

    public String getColN() {
        return colN;
    }

    public void setColN(String colN) {
        this.colN = colN;
    }

    public String getColO() {
        return colO;
    }

    public void setColO(String colO) {
        this.colO = colO;
    }

    public String getColP() {
        return colP;
    }

    public void setColP(String colP) {
        this.colP = colP;
    }

    public String getColQ() {
        return colQ;
    }

    public void setColQ(String colQ) {
        this.colQ = colQ;
    }

    public String getColR() {
        return colR;
    }

    public void setColR(String colR) {
        this.colR = colR;
    }

    public String getColS() {
        return colS;
    }

    public void setColS(String colS) {
        this.colS = colS;
    }

}
