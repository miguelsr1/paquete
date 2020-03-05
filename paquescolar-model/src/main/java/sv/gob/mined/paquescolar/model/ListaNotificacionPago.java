/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model;

import java.io.Serializable;
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
import javax.persistence.Transient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "LISTA_NOTIFICACION_PAGO")
@NamedQueries({
    @NamedQuery(name = "ListaNotificacionPago.findAll", query = "SELECT l FROM ListaNotificacionPago l")})
public class ListaNotificacionPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LISTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLista")
    @SequenceGenerator(name = "seqLista", sequenceName = "SEQ_LISTA_NOTIFICACION", allocationSize = 1, initialValue = 1)
    private Integer idLista;
    @Column(name = "CODIGO_DEPARTAMENTO")
    private String codigoDepartamento;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CUENTA_CORREO")
    private String cuentaCorreo;
    @Column(name = "ACTIVO")
    private Short activo;
    
    @Transient
    private Boolean desactivar = false;

    public ListaNotificacionPago() {
    }

    public Boolean getDesactivar() {
        return activo == 1;
    }

    public void setDesactivar(Boolean desactivar) {
        if(desactivar){
            activo = 1;
        }else{
            activo = 0;
        }
    }

    public ListaNotificacionPago(Integer idLista) {
        this.idLista = idLista;
    }

    public Integer getIdLista() {
        return idLista;
    }

    public void setIdLista(Integer idLista) {
        this.idLista = idLista;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuentaCorreo() {
        return cuentaCorreo;
    }

    public void setCuentaCorreo(String cuentaCorreo) {
        this.cuentaCorreo = cuentaCorreo;
    }

    public Short getActivo() {
        return activo;
    }

    public void setActivo(Short activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLista != null ? idLista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaNotificacionPago)) {
            return false;
        }
        ListaNotificacionPago other = (ListaNotificacionPago) object;
        if ((this.idLista == null && other.idLista != null) || (this.idLista != null && !this.idLista.equals(other.idLista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.paquescolar.model.ListaNotificacionPago[ idLista=" + idLista + " ]";
    }
    
}
