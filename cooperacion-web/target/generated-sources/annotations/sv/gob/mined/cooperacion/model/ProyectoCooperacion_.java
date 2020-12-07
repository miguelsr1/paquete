package sv.gob.mined.cooperacion.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.gob.mined.cooperacion.model.FechaCapacitacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-07T15:55:55")
@StaticMetamodel(ProyectoCooperacion.class)
public class ProyectoCooperacion_ { 

    public static volatile SingularAttribute<ProyectoCooperacion, String> descripcion;
    public static volatile SingularAttribute<ProyectoCooperacion, Short> estadoEliminacion;
    public static volatile SingularAttribute<ProyectoCooperacion, Long> idProyecto;
    public static volatile SingularAttribute<ProyectoCooperacion, Long> idTipoCooperacion;
    public static volatile SingularAttribute<ProyectoCooperacion, Date> fechaEstimadaFin;
    public static volatile SingularAttribute<ProyectoCooperacion, Long> cantidadBeneficiarios;
    public static volatile SingularAttribute<ProyectoCooperacion, Short> otros;
    public static volatile SingularAttribute<ProyectoCooperacion, String> nombreProyecto;
    public static volatile SingularAttribute<ProyectoCooperacion, BigDecimal> montoInversion;
    public static volatile SingularAttribute<ProyectoCooperacion, Short> media;
    public static volatile SingularAttribute<ProyectoCooperacion, Long> idCooperante;
    public static volatile SingularAttribute<ProyectoCooperacion, String> objetivos;
    public static volatile SingularAttribute<ProyectoCooperacion, String> anho;
    public static volatile SingularAttribute<ProyectoCooperacion, Short> basicaCi;
    public static volatile SingularAttribute<ProyectoCooperacion, Date> fechaInsercion;
    public static volatile SingularAttribute<ProyectoCooperacion, Long> idModalidad;
    public static volatile SingularAttribute<ProyectoCooperacion, Short> basicaCiii;
    public static volatile ListAttribute<ProyectoCooperacion, FechaCapacitacion> fechaCapacitacionList;
    public static volatile SingularAttribute<ProyectoCooperacion, Long> idTipoInstrumento;
    public static volatile SingularAttribute<ProyectoCooperacion, Short> docente;
    public static volatile SingularAttribute<ProyectoCooperacion, Long> usuarioInsercion;
    public static volatile SingularAttribute<ProyectoCooperacion, Short> inicial;
    public static volatile SingularAttribute<ProyectoCooperacion, Short> parvularia;
    public static volatile SingularAttribute<ProyectoCooperacion, Short> basicaCii;
    public static volatile SingularAttribute<ProyectoCooperacion, Short> idEstado;
    public static volatile SingularAttribute<ProyectoCooperacion, String> codigoEntidad;
    public static volatile SingularAttribute<ProyectoCooperacion, Date> fechaEstimadaInicio;
    public static volatile SingularAttribute<ProyectoCooperacion, Long> cantidad;

}