package sv.gob.mined.cooperacion.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.gob.mined.cooperacion.model.Director;
import sv.gob.mined.cooperacion.model.UsuarioOrg;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-01-15T14:05:42")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, Short> idPerfil;
    public static volatile SingularAttribute<Usuario, Director> director;
    public static volatile SingularAttribute<Usuario, Long> idUsuario;
    public static volatile ListAttribute<Usuario, UsuarioOrg> usuarioOrgList;
    public static volatile SingularAttribute<Usuario, String> usuario;
    public static volatile SingularAttribute<Usuario, Short> activo;

}