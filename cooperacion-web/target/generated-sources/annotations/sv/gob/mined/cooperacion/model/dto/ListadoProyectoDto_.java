package sv.gob.mined.cooperacion.model.dto;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-08T16:24:42")
@StaticMetamodel(ListadoProyectoDto.class)
public class ListadoProyectoDto_ { 

    public static volatile SingularAttribute<ListadoProyectoDto, Long> idProyecto;
    public static volatile SingularAttribute<ListadoProyectoDto, BigDecimal> geoPy;
    public static volatile SingularAttribute<ListadoProyectoDto, String> codigoDepartamento;
    public static volatile SingularAttribute<ListadoProyectoDto, BigDecimal> geoPx;
    public static volatile SingularAttribute<ListadoProyectoDto, String> nombreDepartamento;
    public static volatile SingularAttribute<ListadoProyectoDto, String> nombreCe;
    public static volatile SingularAttribute<ListadoProyectoDto, String> nombreMunicipio;
    public static volatile SingularAttribute<ListadoProyectoDto, String> nombreProyecto;
    public static volatile SingularAttribute<ListadoProyectoDto, Date> fechaFin;
    public static volatile SingularAttribute<ListadoProyectoDto, String> nombreDirector;
    public static volatile SingularAttribute<ListadoProyectoDto, Short> idEstado;
    public static volatile SingularAttribute<ListadoProyectoDto, Date> fechaInicio;
    public static volatile SingularAttribute<ListadoProyectoDto, String> codigoEntidad;
    public static volatile SingularAttribute<ListadoProyectoDto, String> codigoMunicipio;
    public static volatile SingularAttribute<ListadoProyectoDto, String> correoElectronico;

}