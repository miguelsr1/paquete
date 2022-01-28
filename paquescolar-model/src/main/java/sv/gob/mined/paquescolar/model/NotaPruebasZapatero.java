/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Desarrollo
 */
@Entity
@Table(name = "NOTA_PRUEBAS_ZAPATERO")
@NamedQueries({
    @NamedQuery(name = "NotaPruebasZapatero.findAll", query = "SELECT n FROM NotaPruebasZapatero n")})
public class NotaPruebasZapatero implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Column(name = "ID_NOTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NOTA")
    @SequenceGenerator(name = "SEQ_NOTA", sequenceName = "SEQ_NOTA", allocationSize = 1, initialValue = 1)
    private BigDecimal idNota;
    @Column(name = "NOTA_ZAPATO_NINO")
    private Short notaZapatoNino;
    @Column(name = "NOTA_ZAPATO_NINA")
    private Short notaZapatoNina;
    @JoinColumn(name = "ID_MUESTRA_INTERES", referencedColumnName = "ID_MUESTRA_INTERES")
    @ManyToOne(fetch = FetchType.LAZY)
    private DetRubroMuestraInteres idMuestraInteres;

    public NotaPruebasZapatero() {
    }

    public NotaPruebasZapatero(BigDecimal idNota) {
        this.idNota = idNota;
    }

    public BigDecimal getIdNota() {
        return idNota;
    }

    public void setIdNota(BigDecimal idNota) {
        this.idNota = idNota;
    }

    public Short getNotaZapatoNino() {
        return notaZapatoNino;
    }

    public void setNotaZapatoNino(Short notaZapatoNino) {
        this.notaZapatoNino = notaZapatoNino;
    }

    public Short getNotaZapatoNina() {
        return notaZapatoNina;
    }

    public void setNotaZapatoNina(Short notaZapatoNina) {
        this.notaZapatoNina = notaZapatoNina;
    }

    public DetRubroMuestraInteres getIdMuestraInteres() {
        return idMuestraInteres;
    }

    public void setIdMuestraInteres(DetRubroMuestraInteres idMuestraInteres) {
        this.idMuestraInteres = idMuestraInteres;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNota != null ? idNota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaPruebasZapatero)) {
            return false;
        }
        NotaPruebasZapatero other = (NotaPruebasZapatero) object;
        if ((this.idNota == null && other.idNota != null) || (this.idNota != null && !this.idNota.equals(other.idNota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.NotaPruebasZapatero[ idNota=" + idNota + " ]";
    }
    
}
