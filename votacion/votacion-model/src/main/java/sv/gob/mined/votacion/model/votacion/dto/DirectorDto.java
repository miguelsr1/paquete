/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.votacion.model.votacion.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DesarrolloPc
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultDirectorDto",
        entities = @EntityResult(entityClass = DirectorDto.class))
public class DirectorDto implements Serializable {

    @Id
    private Integer rownum;
    private BigDecimal idDirector;
    private String dui;
    private String nip;
    private String nombre;
    private String codigoEntidad;
    private String nombreCe;
    private String codigoDepartamento;
    private String nombreDepartamento;
    private String voto;
    private String nomnbreCandidato;

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public BigDecimal getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(BigDecimal idDirector) {
        this.idDirector = idDirector;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public String getNomnbreCandidato() {
        return nomnbreCandidato;
    }

    public void setNomnbreCandidato(String nomnbreCandidato) {
        this.nomnbreCandidato = nomnbreCandidato;
    }

}
