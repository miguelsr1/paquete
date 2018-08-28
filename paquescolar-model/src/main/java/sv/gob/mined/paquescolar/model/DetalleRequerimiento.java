/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_REQUERIMIENTO")
@NamedQueries({
    @NamedQuery(name = "DetalleRequerimiento.findAll", query = "SELECT d FROM DetalleRequerimiento d")})
public class DetalleRequerimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DET_REQUERIMIENTO")
    private BigDecimal idDetRequerimiento;
    @Column(name = "ACTIVO")
    private Short activo;
    @Column(name = "CODIGO_DEPARTAMENTO")
    private String codigoDepartamento;
    @Column(name = "CODIGO_MUNICIPIO")
    private String codigoMunicipio;
    @Column(name = "NOMBRE_DEPARTAMENTO")
    private String nombreDepartamento;
    @Column(name = "NOMBRE_MUNICIPIO")
    private String nombreMunicipio;
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @Column(name = "NOMBRE_CE")
    private String nombreCe;
    @Column(name = "MODALIDAD")
    private String modalidad;
    @Column(name = "DIRECCION_CE")
    private String direccionCe;
    @Column(name = "TELEFONO_CE")
    private String telefonoCe;
    @Column(name = "FIRMANTE_CE")
    private String firmanteCe;
    @Column(name = "CELULAR_CE")
    private String celularCe;
    @Column(name = "NUMERO_NIT")
    private String numeroNit;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column(name = "DIRECCION_EMP")
    private String direccionEmp;
    @Column(name = "TELEFONO_EMP")
    private String telefonoEmp;
    @Column(name = "CELULAR_EMP")
    private String celularEmp;
    @Column(name = "DESCRIPCION_RUBRO")
    private String descripcionRubro;
    @Column(name = "FECHA_CONTRATO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaContrato;
    @Column(name = "NUMERO_CONTRATO")
    private String numeroContrato;
    @Column(name = "CANTIDAD_PAR")
    private Integer cantidadPar;
    @Column(name = "MONTO_PAR")
    private BigDecimal montoPar;
    @Column(name = "CANTIDAD_CI")
    private Integer cantidadCi;
    @Column(name = "MONTO_CI")
    private BigDecimal montoCi;
    @Column(name = "CANTIDAD_CII")
    private Integer cantidadCii;
    @Column(name = "MONTO_CII")
    private BigDecimal montoCii;
    @Column(name = "CANTIDAD_CIII")
    private Integer cantidadCiii;
    @Column(name = "MONTO_CIII")
    private BigDecimal montoCiii;
    @Column(name = "CANTIDAD_BAS")
    private Integer cantidadBas;
    @Column(name = "MONTO_BAS")
    private BigDecimal montoBas;
    @Column(name = "CANTIDAD_BAC")
    private Integer cantidadBac;
    @Column(name = "MONTO_BAC")
    private BigDecimal montoBac;
    @Column(name = "TOTAL_NINA")
    private Integer totalNina;
    @Column(name = "TOTAL_NINO")
    private Integer totalNino;
    @Column(name = "CREDITO")
    private String credito;
    @Column(name = "NOMBRE_ENT_FINAN")
    private String nombreEntFinan;
    @Column(name = "ID_CONTRATO")
    private BigInteger idContrato;
    @Column(name = "MONTO_TOTAL")
    private BigDecimal montoTotal;
    @Column(name = "CANTIDAD_TOTAL")
    private Integer cantidadTotal;
    @Column(name = "COD_ENT_FINANCIERA")
    private String codEntFinanciera;
    @JoinColumn(name = "ID_REQUERIMIENTO", referencedColumnName = "ID_REQUERIMIENTO")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private RequerimientoFondos idRequerimiento;
    
    @Transient
    private String numeroCuenta;
    @Transient
    private BigDecimal idPlanilla;

    @OneToMany(mappedBy = "idDetRequerimiento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DetalleDocPago> detalleDocPagoList;

    @Transient
    private Boolean regCompleto = false;

    public DetalleRequerimiento() {
    }

    public String getCodEntFinanciera() {
        return codEntFinanciera;
    }

    public void setCodEntFinanciera(String codEntFinanciera) {
        this.codEntFinanciera = codEntFinanciera;
    }

    public Boolean getRegCompleto() {
        return getDetalleDocPagoList().isEmpty() ? false : (getDetalleDocPagoList().get(0).getIdDetalleDocPago()!= null);
    }

    public void setRegCompleto(Boolean regCompleto) {
        this.regCompleto = regCompleto;
    }

    public DetalleRequerimiento(BigDecimal idDetRequerimiento) {
        this.idDetRequerimiento = idDetRequerimiento;
    }

    public BigDecimal getIdDetRequerimiento() {
        return idDetRequerimiento;
    }

    public void setIdDetRequerimiento(BigDecimal idDetRequerimiento) {
        this.idDetRequerimiento = idDetRequerimiento;
    }

    public Short getActivo() {
        return activo;
    }

    public void setActivo(Short activo) {
        this.activo = activo;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getNombreCe() {
        return nombreCe;
    }

    public void setNombreCe(String nombreCe) {
        this.nombreCe = nombreCe;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getDireccionCe() {
        return direccionCe;
    }

    public void setDireccionCe(String direccionCe) {
        this.direccionCe = direccionCe;
    }

    public String getTelefonoCe() {
        return telefonoCe;
    }

    public void setTelefonoCe(String telefonoCe) {
        this.telefonoCe = telefonoCe;
    }

    public String getFirmanteCe() {
        return firmanteCe;
    }

    public void setFirmanteCe(String firmanteCe) {
        this.firmanteCe = firmanteCe;
    }

    public String getCelularCe() {
        return celularCe;
    }

    public void setCelularCe(String celularCe) {
        this.celularCe = celularCe;
    }

    public String getNumeroNit() {
        return numeroNit;
    }

    public void setNumeroNit(String numeroNit) {
        this.numeroNit = numeroNit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccionEmp() {
        return direccionEmp;
    }

    public void setDireccionEmp(String direccionEmp) {
        this.direccionEmp = direccionEmp;
    }

    public String getTelefonoEmp() {
        return telefonoEmp;
    }

    public void setTelefonoEmp(String telefonoEmp) {
        this.telefonoEmp = telefonoEmp;
    }

    public String getCelularEmp() {
        return celularEmp;
    }

    public void setCelularEmp(String celularEmp) {
        this.celularEmp = celularEmp;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public Integer getCantidadPar() {
        return cantidadPar;
    }

    public void setCantidadPar(Integer cantidadPar) {
        this.cantidadPar = cantidadPar;
    }

    public BigDecimal getMontoPar() {
        return montoPar;
    }

    public void setMontoPar(BigDecimal montoPar) {
        this.montoPar = montoPar;
    }

    public Integer getCantidadCi() {
        return cantidadCi;
    }

    public void setCantidadCi(Integer cantidadCi) {
        this.cantidadCi = cantidadCi;
    }

    public BigDecimal getMontoCi() {
        return montoCi;
    }

    public void setMontoCi(BigDecimal montoCi) {
        this.montoCi = montoCi;
    }

    public Integer getCantidadCii() {
        return cantidadCii;
    }

    public void setCantidadCii(Integer cantidadCii) {
        this.cantidadCii = cantidadCii;
    }

    public BigDecimal getMontoCii() {
        return montoCii;
    }

    public void setMontoCii(BigDecimal montoCii) {
        this.montoCii = montoCii;
    }

    public Integer getCantidadCiii() {
        return cantidadCiii;
    }

    public void setCantidadCiii(Integer cantidadCiii) {
        this.cantidadCiii = cantidadCiii;
    }

    public BigDecimal getMontoCiii() {
        return montoCiii;
    }

    public void setMontoCiii(BigDecimal montoCiii) {
        this.montoCiii = montoCiii;
    }

    public Integer getCantidadBas() {
        return cantidadBas;
    }

    public void setCantidadBas(Integer cantidadBas) {
        this.cantidadBas = cantidadBas;
    }

    public BigDecimal getMontoBas() {
        return montoBas;
    }

    public void setMontoBas(BigDecimal montoBas) {
        this.montoBas = montoBas;
    }

    public Integer getCantidadBac() {
        return cantidadBac;
    }

    public void setCantidadBac(Integer cantidadBac) {
        this.cantidadBac = cantidadBac;
    }

    public BigDecimal getMontoBac() {
        return montoBac;
    }

    public void setMontoBac(BigDecimal montoBac) {
        this.montoBac = montoBac;
    }

    public Integer getTotalNina() {
        return totalNina;
    }

    public void setTotalNina(Integer totalNina) {
        this.totalNina = totalNina;
    }

    public Integer getTotalNino() {
        return totalNino;
    }

    public void setTotalNino(Integer totalNino) {
        this.totalNino = totalNino;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getNombreEntFinan() {
        return nombreEntFinan;
    }

    public void setNombreEntFinan(String nombreEntFinan) {
        this.nombreEntFinan = nombreEntFinan;
    }

    public BigInteger getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(BigInteger idContrato) {
        this.idContrato = idContrato;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Integer getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Integer cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public RequerimientoFondos getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(RequerimientoFondos idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetRequerimiento != null ? idDetRequerimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleRequerimiento)) {
            return false;
        }
        DetalleRequerimiento other = (DetalleRequerimiento) object;
        if ((this.idDetRequerimiento == null && other.idDetRequerimiento != null) || (this.idDetRequerimiento != null && !this.idDetRequerimiento.equals(other.idDetRequerimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetalleRequerimiento[ idDetRequerimiento=" + idDetRequerimiento + " ]";
    }

    public List<DetalleDocPago> getDetalleDocPagoList() {
        if (detalleDocPagoList == null) {
            detalleDocPagoList = new ArrayList<DetalleDocPago>();
        }
        return detalleDocPagoList;
    }

    public void setDetalleDocPagoList(List<DetalleDocPago> detalleDocPagoList) {
        this.detalleDocPagoList = detalleDocPagoList;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public BigDecimal getIdPlanilla() {
        idPlanilla = null;
        if(!detalleDocPagoList.isEmpty()){
            if(!detalleDocPagoList.get(0).getDetallePlanillaList().isEmpty()){
                idPlanilla = detalleDocPagoList.get(0).getDetallePlanillaList().get(0).getIdPlanilla().getIdPlanilla();
            }
        }
        return idPlanilla;
    }
}
