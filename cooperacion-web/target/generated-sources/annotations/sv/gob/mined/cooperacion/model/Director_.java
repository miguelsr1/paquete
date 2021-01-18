package sv.gob.mined.cooperacion.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.gob.mined.cooperacion.model.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-01-15T14:05:42")
@StaticMetamodel(Director.class)
public class Director_ { 

    public static volatile SingularAttribute<Director, String> nombreDirector;
    public static volatile SingularAttribute<Director, Usuario> idUsuario;
    public static volatile SingularAttribute<Director, Long> idDirector;
    public static volatile SingularAttribute<Director, String> codigoEntidad;
    public static volatile SingularAttribute<Director, String> correoElectronico;
    public static volatile SingularAttribute<Director, Short> activo;

}