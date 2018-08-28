/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "CAPA_DISTRIBUCION_ACRE")
@NamedQueries({
    @NamedQuery(name = "CapaDistribucionAcre.findAll", query = "SELECT c FROM CapaDistribucionAcre c")})
public class CapaDistribucionAcre implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CAPA_DISTRIBUCION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "distribucion")
    @SequenceGenerator(name = "distribucion", sequenceName = "SEQ_CAP_DIS_ACRE", allocationSize = 1, initialValue = 1)
    private BigDecimal idCapaDistribucion;
    @Basic(optional = false)
    @Column(name = "USUARIO_INSERCION")
    private String usuarioInsercion;
    @Basic(optional = false)
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "FECHA_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Basic(optional = false)
    @Column(name = "ESTADO_ELIMINACION")
    private BigInteger estadoEliminacion;
    @JoinColumn(name = "ID_MUESTRA_INTERES", referencedColumnName = "ID_MUESTRA_INTERES")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetRubroMuestraInteres idMuestraInteres;
    @JoinColumn(name = "CODIGO_DEPARTAMENTO", referencedColumnName = "CODIGO_DEPARTAMENTO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Departamento codigoDepartamento;
    @OneToMany(mappedBy = "idCapaDistribucion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DisMunicipioInteres> disMunicipioInteresList;

    public CapaDistribucionAcre() {
    }

    public CapaDistribucionAcre(BigDecimal idCapaDistribucion) {
        this.idCapaDistribucion = idCapaDistribucion;
    }

    public CapaDistribucionAcre(BigDecimal idCapaDistribucion, String usuarioInsercion, Date fechaInsercion, BigInteger estadoEliminacion) {
        this.idCapaDistribucion = idCapaDistribucion;
        this.usuarioInsercion = usuarioInsercion;
        this.fechaInsercion = fechaInsercion;
        this.estadoEliminacion = estadoEliminacion;
    }

    public BigDecimal getIdCapaDistribucion() {
        return idCapaDistribucion;
    }

    public void setIdCapaDistribucion(BigDecimal idCapaDistribucion) {
        this.idCapaDistribucion = idCapaDistribucion;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public BigInteger getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(BigInteger estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public DetRubroMuestraInteres getIdMuestraInteres() {
        return idMuestraInteres;
    }

    public void setIdMuestraInteres(DetRubroMuestraInteres idMuestraInteres) {
        this.idMuestraInteres = idMuestraInteres;
    }

    public Departamento getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(Departamento codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public List<DisMunicipioInteres> getDisMunicipioInteresList() {
        return disMunicipioInteresList;
    }

    public void setDisMunicipioInteresList(List<DisMunicipioInteres> disMunicipioInteresList) {
        this.disMunicipioInteresList = disMunicipioInteresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCapaDistribucion != null ? idCapaDistribucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CapaDistribucionAcre)) {
            return false;
        }
        CapaDistribucionAcre other = (CapaDistribucionAcre) object;
        if ((this.idCapaDistribucion == null && other.idCapaDistribucion != null) || (this.idCapaDistribucion != null && !this.idCapaDistribucion.equals(other.idCapaDistribucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.CapaDistribucionAcre[ idCapaDistribucion=" + idCapaDistribucion + " ]";
    }

}
