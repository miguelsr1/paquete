package sv.gob.mined.cooperacion.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.gob.mined.cooperacion.model.ProyectoCooperacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-01-15T14:05:42")
@StaticMetamodel(TipoCooperacion.class)
public class TipoCooperacion_ { 

    public static volatile SingularAttribute<TipoCooperacion, Long> idTipoCooperacion;
    public static volatile SingularAttribute<TipoCooperacion, String> descripcionCooperacion;
    public static volatile ListAttribute<TipoCooperacion, ProyectoCooperacion> proyectoCooperacionList;

}