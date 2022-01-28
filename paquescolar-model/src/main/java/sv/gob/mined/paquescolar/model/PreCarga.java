/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PRE_CARGA")
@NamedQueries({
    @NamedQuery(name = "PreCarga.findAll", query = "SELECT p FROM PreCarga p")})
public class PreCarga implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PRECARGA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRE_CARGA")
    @SequenceGenerator(name = "SEQ_PRE_CARGA", sequenceName = "SEQ_PRE_CARGA", allocationSize = 1, initialValue = 1)
    private BigDecimal idPrecarga;
    @JoinColumn(name = "ID_DET_PROCESO_ADQ", referencedColumnName = "ID_DET_PROCESO_ADQ")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private DetalleProcesoAdq idDetProcesoAdq;
    @Basic(optional = false)
    @Column(name = "ESTADO_PRECARGA")
    private Character estadoPrecarga;
    @Basic(optional = false)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @Column(name = "NUM_ENTREGA")
    private short numEntrega;
    @OneToMany(mappedBy = "idPrecarga", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DetallePreCarga> detallePreCargaList;

    public PreCarga() {
    }

    public BigDecimal getIdPrecarga() {
        return idPrecarga;
    }

    public void setIdPrecarga(BigDecimal idPrecarga) {
        this.idPrecarga = idPrecarga;
    }

    public DetalleProcesoAdq getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(DetalleProcesoAdq idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    public Character getEstadoPrecarga() {
        return estadoPrecarga;
    }

    public void setEstadoPrecarga(Character estadoPrecarga) {
        this.estadoPrecarga = estadoPrecarga;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public short getNumEntrega() {
        return numEntrega;
    }

    public void setNumEntrega(short numEntrega) {
        this.numEntrega = numEntrega;
    }

    public List<DetallePreCarga> getDetallePreCargaList() {
        return detallePreCargaList;
    }

    public void setDetallePreCargaList(List<DetallePreCarga> detallePreCargaList) {
        this.detallePreCargaList = detallePreCargaList;
    }
    
    public Integer getDetallePreCargaSize(){
        if(detallePreCargaList != null){
            return detallePreCargaList.size();
        }else{
            return 0;
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrecarga != null ? idPrecarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreCarga)) {
            return false;
        }
        PreCarga other = (PreCarga) object;
        if ((this.idPrecarga == null && other.idPrecarga != null) || (this.idPrecarga != null && !this.idPrecarga.equals(other.idPrecarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.PreCarga[ idPrecarga=" + idPrecarga + " ]";
    }

}
