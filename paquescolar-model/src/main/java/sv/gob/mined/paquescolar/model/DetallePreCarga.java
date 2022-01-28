/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_PRE_CARGA")
@NamedQueries({
    @NamedQuery(name = "DetallePreCarga.findAll", query = "SELECT d FROM DetallePreCarga d")})
public class DetallePreCarga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_PRECARGA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DETALLE_PRE_CARGA")
    @SequenceGenerator(name = "SEQ_DETALLE_PRE_CARGA", sequenceName = "SEQ_DETALLE_PRE_CARGA", allocationSize = 1, initialValue = 1)
    private Integer idDetallePrecarga;
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
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "INICIALES_MODALIDAD")
    private String inicialesModalidad;
    @Column(name = "DIRECCION_CE")
    private String direccionCe;
    @Column(name = "TELEFONOS")
    private String telefonos;
    @Column(name = "MIEMBRO_FIRMA")
    private String miembroFirma;
    @Column(name = "TEL_DIRECTOR")
    private String telDirector;
    @Column(name = "NUMERO_NIT")
    private String numeroNit;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column(name = "DIR_EMPRESA")
    private String dirEmpresa;
    @Column(name = "TELEFONOS_EMP")
    private String telefonosEmp;
    @Column(name = "NUMERO_CELULAR_EMP")
    private String numeroCelularEmp;
    @Column(name = "DESCRIPCION_RUBRO")
    private String descripcionRubro;
    @Column(name = "FECHA_EMISION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;
    @Column(name = "NUMERO_CONTRATO")
    private String numeroContrato;
    @Column(name = "PAR_CANTIDAD")
    private Integer parCantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PAR_MONTO")
    private BigDecimal parMonto;
    @Column(name = "CI_CANTIDAD")
    private Integer ciCantidad;
    @Column(name = "CI_MONTO")
    private BigDecimal ciMonto;
    @Column(name = "CII_CANTIDAD")
    private Integer ciiCantidad;
    @Column(name = "CII_MONTO")
    private BigDecimal ciiMonto;
    @Column(name = "CIII_CANTIDAD")
    private Integer ciiiCantidad;
    @Column(name = "CIII_MONTO")
    private BigDecimal ciiiMonto;
    @Column(name = "BAS_CANTIDAD")
    private Integer basCantidad;
    @Column(name = "BAS_MONTO")
    private BigDecimal basMonto;
    @Column(name = "BAC_CANTIDAD")
    private Integer bacCantidad;
    @Column(name = "BAC_MONTO")
    private BigDecimal bacMonto;
    @Column(name = "CANTIDAD_TOTAL")
    private Integer cantidadTotal;
    @Column(name = "MONTO_TOTAL")
    private BigDecimal montoTotal;
    @Column(name = "TIENE_CREDITO")
    private String tieneCredito;
    @Column(name = "NOMBRE_ENT_FINAN")
    private String nombreEntFinan;
    @Column(name = "ID_CONTRATO")
    private BigInteger idContrato;
    @JoinColumn(name = "ID_PRECARGA", referencedColumnName = "ID_PRECARGA")
    @ManyToOne(fetch = FetchType.LAZY)
    private PreCarga idPrecarga;

    public DetallePreCarga() {
    }

    public DetallePreCarga(Integer idDetallePrecarga) {
        this.idDetallePrecarga = idDetallePrecarga;
    }

    public Integer getIdDetallePrecarga() {
        return idDetallePrecarga;
    }

    public void setIdDetallePrecarga(Integer idDetallePrecarga) {
        this.idDetallePrecarga = idDetallePrecarga;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInicialesModalidad() {
        return inicialesModalidad;
    }

    public void setInicialesModalidad(String inicialesModalidad) {
        this.inicialesModalidad = inicialesModalidad;
    }

    public String getDireccionCe() {
        return direccionCe;
    }

    public void setDireccionCe(String direccionCe) {
        this.direccionCe = direccionCe;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getMiembroFirma() {
        return miembroFirma;
    }

    public void setMiembroFirma(String miembroFirma) {
        this.miembroFirma = miembroFirma;
    }

    public String getTelDirector() {
        return telDirector;
    }

    public void setTelDirector(String telDirector) {
        this.telDirector = telDirector;
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

    public String getDirEmpresa() {
        return dirEmpresa;
    }

    public void setDirEmpresa(String dirEmpresa) {
        this.dirEmpresa = dirEmpresa;
    }

    public String getTelefonosEmp() {
        return telefonosEmp;
    }

    public void setTelefonosEmp(String telefonosEmp) {
        this.telefonosEmp = telefonosEmp;
    }

    public String getNumeroCelularEmp() {
        return numeroCelularEmp;
    }

    public void setNumeroCelularEmp(String numeroCelularEmp) {
        this.numeroCelularEmp = numeroCelularEmp;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public Integer getParCantidad() {
        return parCantidad;
    }

    public void setParCantidad(Integer parCantidad) {
        this.parCantidad = parCantidad;
    }

    public BigDecimal getParMonto() {
        return parMonto;
    }

    public void setParMonto(BigDecimal parMonto) {
        this.parMonto = parMonto;
    }

    public Integer getCiCantidad() {
        return ciCantidad;
    }

    public void setCiCantidad(Integer ciCantidad) {
        this.ciCantidad = ciCantidad;
    }

    public BigDecimal getCiMonto() {
        return ciMonto;
    }

    public void setCiMonto(BigDecimal ciMonto) {
        this.ciMonto = ciMonto;
    }

    public Integer getCiiCantidad() {
        return ciiCantidad;
    }

    public void setCiiCantidad(Integer ciiCantidad) {
        this.ciiCantidad = ciiCantidad;
    }

    public BigDecimal getCiiMonto() {
        return ciiMonto;
    }

    public void setCiiMonto(BigDecimal ciiMonto) {
        this.ciiMonto = ciiMonto;
    }

    public Integer getCiiiCantidad() {
        return ciiiCantidad;
    }

    public void setCiiiCantidad(Integer ciiiCantidad) {
        this.ciiiCantidad = ciiiCantidad;
    }

    public BigDecimal getCiiiMonto() {
        return ciiiMonto;
    }

    public void setCiiiMonto(BigDecimal ciiiMonto) {
        this.ciiiMonto = ciiiMonto;
    }

    public Integer getBasCantidad() {
        return basCantidad;
    }

    public void setBasCantidad(Integer basCantidad) {
        this.basCantidad = basCantidad;
    }

    public BigDecimal getBasMonto() {
        return basMonto;
    }

    public void setBasMonto(BigDecimal basMonto) {
        this.basMonto = basMonto;
    }

    public Integer getBacCantidad() {
        return bacCantidad;
    }

    public void setBacCantidad(Integer bacCantidad) {
        this.bacCantidad = bacCantidad;
    }

    public BigDecimal getBacMonto() {
        return bacMonto;
    }

    public void setBacMonto(BigDecimal bacMonto) {
        this.bacMonto = bacMonto;
    }

    public Integer getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Integer cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getTieneCredito() {
        return tieneCredito;
    }

    public void setTieneCredito(String tieneCredito) {
        this.tieneCredito = tieneCredito;
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

    public PreCarga getIdPrecarga() {
        return idPrecarga;
    }

    public void setIdPrecarga(PreCarga idPrecarga) {
        this.idPrecarga = idPrecarga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetallePrecarga != null ? idDetallePrecarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallePreCarga)) {
            return false;
        }
        DetallePreCarga other = (DetallePreCarga) object;
        if ((this.idDetallePrecarga == null && other.idDetallePrecarga != null) || (this.idDetallePrecarga != null && !this.idDetallePrecarga.equals(other.idDetallePrecarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.DetallePreCarga[ idDetallePrecarga=" + idDetallePrecarga + " ]";
    }
    
}
