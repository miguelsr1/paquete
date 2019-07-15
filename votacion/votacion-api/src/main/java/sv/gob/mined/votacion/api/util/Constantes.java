/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.votacion.api.util;

/**
 *
 * @author DesarrolloPc
 */
public class Constantes {

    public static final String SQL_DIRECTOR_DTO = "select \n"
            + "    rownum,\n"
            + "    VW.id_director         as idDirector,\n"
            + "    VW.dui,\n"
            + "    nip,\n"
            + "    nombre,\n"
            + "    codigo_entidad          as codigoEntidad,\n"
            + "    nombre_ce               as nombreCe,\n"
            + "    VW.codigo_departamento  as codigoDepartamento,\n"
            + "    nombre_departamento     as nombreDepartamento,\n"
            + "    DECODE(ID_VOTO,NULL,'No', 'Sí') as voto,\n"
            + "    cad.nombres                     as nombreCandidato\n"
            + "from \n"
            + "    vw_director VW\n"
            + "    LEFT OUTER JOIN VOTO ON vw.id_director = voto.id_director \n"
            + "    LEFT OUTER join candidato cad on cad.id_candidato = voto.id_candidato";
}
