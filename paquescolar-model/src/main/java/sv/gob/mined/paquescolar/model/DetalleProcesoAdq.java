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

    @OneToMany(mappedBy = "idDetProcesoAdq")
    private List<RptDocumentos> rptDocumentosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDetProcesoAdq", fetch = FetchType.LAZY)
    private List<CreditoBancario> creditoBancarioList;
    @OneToMany(mappedBy = "idDetProcesoAdq", fetch = FetchType.LAZY)
    private List<DetRubroMuestraInteres> detRubroMuestraInteresList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDetProcesoAdq")
    private List<OfertaBienesServicios> ofertaBienesServiciosList;
    @OneToMany(mappedBy = "idDetProcesoAdq", fetch = FetchType.LAZY)
    private List<TechoRubroEntEdu> techoRubroEntEduList;
    @OneToMany(mappedBy = "idDetProcesoAdq", fetch = FetchType.LAZY)
    private List<PreciosRefRubro> preciosRefRubroList;
    @OneToMany(mappedBy = "idDetProcesoAdq", fetch = FetchType.LAZY)
    private List<PreciosRefRubroEmp> preciosRefRubroEmpList;
    @OneToMany(mappedBy = "idDetProcesoAdq", fetch = FetchType.LAZY)
    private List<RequerimientoFondos> requerimientoFondosList;
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
    @Transient
    private Boolean habilitarCredito;

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

    public List<PreciosRefRubroEmp> getPreciosRefRubroEmpList() {
        return preciosRefRubroEmpList;
    }

    public void setPreciosRefRubroEmpList(List<PreciosRefRubroEmp> preciosRefRubroEmpList) {
        this.preciosRefRubroEmpList = preciosRefRubroEmpList;
    }

    public List<PreciosRefRubro> getPreciosRefRubroList() {
        return preciosRefRubroList;
    }

    public void setPreciosRefRubroList(List<PreciosRefRubro> preciosRefRubroList) {
        this.preciosRefRubroList = preciosRefRubroList;
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

    public List<DetRubroMuestraInteres> getDetRubroMuestraInteresList() {
        return detRubroMuestraInteresList;
    }

    public void setDetRubroMuestraInteresList(List<DetRubroMuestraInteres> detRubroMuestraInteresList) {
        this.detRubroMuestraInteresList = detRubroMuestraInteresList;
    }

    public List<CreditoBancario> getCreditoBancarioList() {
        return creditoBancarioList;
    }

    public void setCreditoBancarioList(List<CreditoBancario> creditoBancarioList) {
        this.creditoBancarioList = creditoBancarioList;
    }

    public List<RptDocumentos> getRptDocumentosList() {
        return rptDocumentosList;
    }

    public void setRptDocumentosList(List<RptDocumentos> rptDocumentosList) {
        this.rptDocumentosList = rptDocumentosList;
    }

}
