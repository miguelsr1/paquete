package sv.gob.mined.cooperacion.model.paquete;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.gob.mined.cooperacion.model.paquete.Municipio;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-02-09T15:23:10")
@StaticMetamodel(Departamento.class)
public class Departamento_ { 

    public static volatile SingularAttribute<Departamento, String> codigoDepartamento;
    public static volatile SingularAttribute<Departamento, String> nombreDepartamento;
    public static volatile ListAttribute<Departamento, Municipio> municipioList;

}