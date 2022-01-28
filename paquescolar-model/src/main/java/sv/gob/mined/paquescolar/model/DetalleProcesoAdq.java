/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_PROCESO_ADQ")
@NamedQueries({
    @NamedQuery(name = "DetalleProcesoAdq.findAll", query = "SELECT d FROM DetalleProcesoAdq d")})
public class DetalleProcesoAdq implements Serializable {

    @OneToMany(mappedBy = "idDetProcesoAdq", fetch = FetchType.LAZY)
    private List<ResguardoBienes> reguardoBienesList;

    @OneToMany(mappedBy = "idDetProcesoAdq")
    private List<RptDocumentos> rptDocumentosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDetProcesoAdq", fetch = FetchType.LAZY)

    
    private List<OfertaBienesServicios> ofertaBienesServiciosList;
    @OneToMany(mappedBy = "idDetProcesoAdq", fetch = FetchType.LAZY)
    private List<TechoRubroEntEdu> techoRubroEntEduList;
    
    
    @OneToMany(mappedBy = "idDetProcesoAdq", fetch = FetchType.LAZY)
    private List<RequerimientoFondos> requerimientoFondosList;
    @OneToMany(mappedBy = "idDetProcesoAdq", fetch = FetchType.LAZY)
    private List<PreCarga> preCargaList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DET_PROCESO_ADQ")
    private Integer idDetProcesoAdq;
    @JoinColumn(name = "ID_RUBRO_ADQ", referencedColumnName = "ID_RUBRO_INTERES")
    @ManyToOne(fetch = FetchType.EAGER)
    private RubrosAmostrarInteres idRubroAdq;
    @JoinColumn(name = "ID_PROCESO_ADQ", referencedColumnName = "ID_PROCESO_ADQ")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProcesoAdquisicion idProcesoAdq;
    @Column(name = "CREDITO")
    private Short credito;
    @Column(name = "HABILITAR")
    private Short habilitar;
    @Transient
    private Boolean habilitarCredito;
    @Transient
    private Boolean habilitarRegistro;

    public DetalleProcesoAdq() {
    }

    public List<RequerimientoFondos> getRequerimientoFondosList() {
        return requerimientoFondosList;
    }

    public void setRequerimientoFondosList(List<RequerimientoFondos> requerimientoFondosList) {
        this.requerimientoFondosList = requerimientoFondosList;
    }

    public DetalleProcesoAdq(Integer idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    public Integer getIdDetProcesoAdq() {
        return idDetProcesoAdq;
    }

    public void setIdDetProcesoAdq(Integer idDetProcesoAdq) {
        this.idDetProcesoAdq = idDetProcesoAdq;
    }

    public RubrosAmostrarInteres getIdRubroAdq() {
        return idRubroAdq;
    }

    public void setIdRubroAdq(RubrosAmostrarInteres idRubroAdq) {
        this.idRubroAdq = idRubroAdq;
    }

    public ProcesoAdquisicion getIdProcesoAdq() {
        return idProcesoAdq;
    }

    public void setIdProcesoAdq(ProcesoAdquisicion idProcesoAdq) {
        this.idProcesoAdq = idProcesoAdq;
    }

    public Short getCredito() {
        return credito;
    }

    public void setCredito(Short credito) {
        this.credito = credito;
    }

    public Boolean getHabilitarCredito() {
        return (this.credito == 1);
    }

    public void setHabilitarCredito(Boolean habilitarCredito) {
        this.habilitarCredito = habilitarCredito;
        this.credito = this.habilitarCredito ? (short) 1 : 0;
    }
    
    public Boolean getHabilitarRegistro() {
        return (this.habilitar == 1);
    }

    public void setHabilitarRegistro(Boolean habilitarRegistro) {
        this.habilitarRegistro = habilitarRegistro;
        this.habilitar = this.habilitarRegistro ? (short) 1 : 0;
    }

    public Short getHabilitar() {
        return habilitar;
    }

    public void setHabilitar(Short habilitar) {
        this.habilitar = habilitar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetProcesoAdq != null ? idDetProcesoAdq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleProcesoAdq)) {
            return false;
        }
        DetalleProcesoAdq other = (DetalleProcesoAdq) object;
        return !((this.idDetProcesoAdq == null && other.idDetProcesoAdq != null) || (this.idDetProcesoAdq != null && !this.idDetProcesoAdq.equals(other.idDetProcesoAdq)));
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetalleProcesoAdq[ idDetProcesoAdq=" + idDetProcesoAdq + " ]";
    }

    public List<TechoRubroEntEdu> getTechoRubroEntEduList() {
        return techoRubroEntEduList;
    }

    public void setTechoRubroEntEduList(List<TechoRubroEntEdu> techoRubroEntEduList) {
        this.techoRubroEntEduList = techoRubroEntEduList;
    }

    public List<OfertaBienesServicios> getOfertaBienesServiciosList() {
        return ofertaBienesServiciosList;
    }

    public void setOfertaBienesServiciosList(List<OfertaBienesServicios> ofertaBienesServiciosList) {
        this.ofertaBienesServiciosList = ofertaBienesServiciosList;
    }

    public List<RptDocumentos> getRptDocumentosList() {
        return rptDocumentosList;
    }

    public void setRptDocumentosList(List<RptDocumentos> rptDocumentosList) {
        this.rptDocumentosList = rptDocumentosList;
    }

    public List<PreCarga> getPreCargaList() {
        return preCargaList;
    }

    public void setPreCargaList(List<PreCarga> preCargaList) {
        this.preCargaList = preCargaList;
    }

    public List<ResguardoBienes> getReguardoBienesList() {
        return reguardoBienesList;
    }

    public void setReguardoBienesList(List<ResguardoBienes> reguardoBienesList) {
        this.reguardoBienesList = reguardoBienesList;
    }

}
