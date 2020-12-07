package sv.gob.mined.cooperacion.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-07T13:19:18")
@StaticMetamodel(HisCambioEstadoPro.class)
public class HisCambioEstadoPro_ { 

    public static volatile SingularAttribute<HisCambioEstadoPro, Long> idProyecto;
    public static volatile SingularAttribute<HisCambioEstadoPro, Short> idEstadoAnt;
    public static volatile SingularAttribute<HisCambioEstadoPro, Date> fechaCambio;
    public static volatile SingularAttribute<HisCambioEstadoPro, Long> usuarioCambio;
    public static volatile SingularAttribute<HisCambioEstadoPro, Short> idEstadoNew;
    public static volatile SingularAttribute<HisCambioEstadoPro, Integer> idHistorico;

}