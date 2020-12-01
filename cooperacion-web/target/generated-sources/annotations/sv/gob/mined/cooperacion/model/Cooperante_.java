package sv.gob.mined.cooperacion.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.gob.mined.cooperacion.model.TipoCooperante;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-30T10:40:16")
@StaticMetamodel(Cooperante.class)
public class Cooperante_ { 

    public static volatile SingularAttribute<Cooperante, Long> idCooperante;
    public static volatile SingularAttribute<Cooperante, String> correo;
    public static volatile SingularAttribute<Cooperante, String> celular;
    public static volatile SingularAttribute<Cooperante, TipoCooperante> idTipoCooperante;
    public static volatile SingularAttribute<Cooperante, String> nombreContanto;
    public static volatile SingularAttribute<Cooperante, String> telefono;
    public static volatile SingularAttribute<Cooperante, String> nombreCooperante;

}