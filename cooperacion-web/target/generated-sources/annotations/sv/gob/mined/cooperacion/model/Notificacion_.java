package sv.gob.mined.cooperacion.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-08T16:24:42")
@StaticMetamodel(Notificacion.class)
public class Notificacion_ { 

    public static volatile SingularAttribute<Notificacion, Short> estadoEliminacion;
    public static volatile SingularAttribute<Notificacion, Integer> idTipoCooperacion;
    public static volatile SingularAttribute<Notificacion, Integer> idNotificacion;
    public static volatile SingularAttribute<Notificacion, String> correo;
    public static volatile SingularAttribute<Notificacion, Short> tipoDestinatario;
    public static volatile SingularAttribute<Notificacion, String> usuarioInsercion;
    public static volatile SingularAttribute<Notificacion, Date> fechaInsercion;
    public static volatile SingularAttribute<Notificacion, String> nombre;

}