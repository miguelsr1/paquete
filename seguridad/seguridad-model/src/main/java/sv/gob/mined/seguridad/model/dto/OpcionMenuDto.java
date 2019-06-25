/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.seguridad.model.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultOpcionMenuDto",
        entities = @EntityResult(entityClass = OpcionMenuDto.class))
public class OpcionMenuDto implements Serializable {

    @Id
    private Long idOpcMenu;
    private Long padreIdOpcMenu;
    private String nombreOpcion;
    private String nombrePadre;

    public Long getIdOpcMenu() {
        return idOpcMenu;
    }

    public void setIdOpcMenu(Long idOpcMenu) {
        this.idOpcMenu = idOpcMenu;
    }

    public Long getPadreIdOpcMenu() {
        return padreIdOpcMenu;
    }

    public void setPadreIdOpcMenu(Long padreIdOpcMenu) {
        this.padreIdOpcMenu = padreIdOpcMenu;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }
}
