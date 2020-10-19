/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.cooperacion.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PROYECTO_COOPERACION")
@NamedQueries({
    @NamedQuery(name = "ProyectoCooperacion.findAll", query = "SELECT p FROM ProyectoCooperacion p")})
public class ProyectoCooperacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROYECTO")
    private Long idProyecto;
    @Column(name = "NOMBRE_PROYECTO")
    private String nombreProyecto;
    @Column(name = "ID_COOPERANTE")
    private Long idCooperante;
    @Column(name = "ID_MODALIDAD")
    private Long idModalidad;
    @Column(name = "ID_TIPO_INSTRUMENTO")
    private Long idTipoInstrumento;
    @Column(name = "OBJETIVOS")
    private String objetivos;
    @Column(name = "FECHA_ESTIMADA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstimadaInicio;
    @Column(name = "FECHA_ESTIMADA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstimadaFin;
    @Column(name = "ID_TIPO_COOPERACION")
    private Long idTipoCooperacion;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CANTIDAD")
    private Long cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MONTO_INVERSION")
    private BigDecimal montoInversion;
    @Column(name = "CANTIDAD_BENEFICIARIOS")
    private Long cantidadBeneficiarios;
    @Column(name = "INICIAL")
    private Short inicial;
    @Column(name = "PARVULARIA")
    private Short parvularia;
    @Column(name = "BASICA_CI")
    private Short basicaCi;
    @Column(name = "BASICA_CII")
    private Short basicaCii;
    @Column(name = "BASICA_CIII")
    private Short basicaCiii;
    @Column(name = "MEDIA")
    private Short media;
    @Column(name = "DOCENTE")
    private Short docente;
    @Column(name = "OTROS")
    private Short otros;
    @Column(name = "ID_ESTADO")
    private Short idEstado;
    @Basic(optional = false)
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Basic(optional = false)
    @Column(name = "USUARIO_INSERCION")
    private short usuarioInsercion;
    @Column(name = "ESTADO_ELIMINACION")
    private Short estadoEliminacion;
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @Column(name = "ANHO")
    private String anho;

    public ProyectoCooperacion() {
    }

    public ProyectoCooperacion(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public ProyectoCooperacion(Long idProyecto, Date fechaInsercion, short usuarioInsercion) {
        this.idProyecto = idProyecto;
        this.fechaInsercion = fechaInsercion;
        this.usuarioInsercion = usuarioInsercion;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public Long getIdCooperante() {
        return idCooperante;
    }

    public void setIdCooperante(Long idCooperante) {
        this.idCooperante = idCooperante;
    }

    public Long getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(Long idModalidad) {
        this.idModalidad = idModalidad;
    }

    public Long getIdTipoInstrumento() {
        return idTipoInstrumento;
    }

    public void setIdTipoInstrumento(Long idTipoInstrumento) {
        this.idTipoInstrumento = idTipoInstrumento;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public Date getFechaEstimadaInicio() {
        return fechaEstimadaInicio;
    }

    public void setFechaEstimadaInicio(Date fechaEstimadaInicio) {
        this.fechaEstimadaInicio = fechaEstimadaInicio;
    }

    public Date getFechaEstimadaFin() {
        return fechaEstimadaFin;
    }

    public void setFechaEstimadaFin(Date fechaEstimadaFin) {
        this.fechaEstimadaFin = fechaEstimadaFin;
    }

    public Long getIdTipoCooperacion() {
        return idTipoCooperacion;
    }

    public void setIdTipoCooperacion(Long idTipoCooperacion) {
        this.idTipoCooperacion = idTipoCooperacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getMontoInversion() {
        return montoInversion;
    }

    public void setMontoInversion(BigDecimal montoInversion) {
        this.montoInversion = montoInversion;
    }

    public Long getCantidadBeneficiarios() {
        return cantidadBeneficiarios;
    }

    public void setCantidadBeneficiarios(Long cantidadBeneficiarios) {
        this.cantidadBeneficiarios = cantidadBeneficiarios;
    }

    public Short getInicial() {
        return inicial;
    }

    public void setInicial(Short inicial) {
        this.inicial = inicial;
    }

    public Short getParvularia() {
        return parvularia;
    }

    public void setParvularia(Short parvularia) {
        this.parvularia = parvularia;
    }

    public Short getBasicaCi() {
        return basicaCi;
    }

    public void setBasicaCi(Short basicaCi) {
        this.basicaCi = basicaCi;
    }

    public Short getBasicaCii() {
        return basicaCii;
    }

    public void setBasicaCii(Short basicaCii) {
        this.basicaCii = basicaCii;
    }

    public Short getBasicaCiii() {
        return basicaCiii;
    }

    public void setBasicaCiii(Short basicaCiii) {
        this.basicaCiii = basicaCiii;
    }

    public Short getMedia() {
        return media;
    }

    public void setMedia(Short media) {
        this.media = media;
    }

    public Short getDocente() {
        return docente;
    }

    public void setDocente(Short docente) {
        this.docente = docente;
    }

    public Short getOtros() {
        return otros;
    }

    public void setOtros(Short otros) {
        this.otros = otros;
    }

    public Short getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Short idEstado) {
        this.idEstado = idEstado;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public short getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(short usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public Short getEstadoEliminacion() {
        return estadoEliminacion;
    }

    public void setEstadoEliminacion(Short estadoEliminacion) {
        this.estadoEliminacion = estadoEliminacion;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProyecto != null ? idProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProyectoCooperacion)) {
            return false;
        }
        ProyectoCooperacion other = (ProyectoCooperacion) object;
        if ((this.idProyecto == null && other.idProyecto != null) || (this.idProyecto != null && !this.idProyecto.equals(other.idProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.cooperacion.web.model.ProyectoCooperacion[ idProyecto=" + idProyecto + " ]";
    }
    
}