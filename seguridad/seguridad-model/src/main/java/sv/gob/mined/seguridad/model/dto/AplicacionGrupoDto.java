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
@SqlResultSetMapping(name = "defaultAppGrp",
        entities = @EntityResult(entityClass = AplicacionGrupoDto.class))
public class AplicacionGrupoDto implements Serializable {
    @Id
    private Long idGruApp;
    private String nombreAplicacionGrupo;

    public Long getIdGruApp() {
        return idGruApp;
    }

    public void setIdGruApp(Long idGruApp) {
        this.idGruApp = idGruApp;
    }

    public String getNombreAplicacionGrupo() {
        return nombreAplicacionGrupo;
    }

    public void setNombreAplicacionGrupo(String nombreAplicacionGrupo) {
        this.nombreAplicacionGrupo = nombreAplicacionGrupo;
    }

    @Override
    public String toString() {
        return nombreAplicacionGrupo;
    }
}
