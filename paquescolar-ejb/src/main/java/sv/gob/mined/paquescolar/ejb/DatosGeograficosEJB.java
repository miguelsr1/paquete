/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.Departamento;
import sv.gob.mined.paquescolar.model.Municipio;
import sv.gob.mined.paquescolar.model.MunicipioAledanho;
import sv.gob.mined.paquescolar.model.pojos.proveedor.MunicipioDto;
import sv.gob.mined.paquescolar.util.Constantes;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class DatosGeograficosEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    public List<Departamento> getLstDepartamentos() {
        Query q = em.createQuery("SELECT d FROM Departamento d ORDER BY FUNC('TO_NUMBER', d.codigoDepartamento)", Departamento.class);
        return q.getResultList();
    }

    public List<Municipio> getLstMunicipios() {
        Query q = em.createQuery("SELECT m FROM Municipio m ORDER BY m.idMunicipio", Municipio.class);
        return q.getResultList();
    }

    public List<Municipio> getLstMunicipiosByDepartamento(String codigoDepartamento) {
        Query query;
        if (codigoDepartamento == null) {
            query = em.createQuery("SELECT m FROM Municipio m WHERE m.codigoDepartamento.codigoDepartamento=:departamento", Municipio.class);
            query.setParameter("departamento", codigoDepartamento);
        } else if (codigoDepartamento.equals("00")) {
            query = em.createQuery("SELECT m FROM Municipio m ORDER BY FUNC('TO_NUMBER',m.codigoDepartamento.codigoDepartamento),  FUNC('TO_NUMBER',m.codigoMunicipio)", Municipio.class);
        } else {
            query = em.createQuery("SELECT m FROM Municipio m WHERE m.codigoDepartamento.codigoDepartamento=:departamento ORDER BY FUNC('TO_NUMBER',m.codigoDepartamento.codigoDepartamento),  FUNC('TO_NUMBER',m.codigoMunicipio)", Municipio.class);
            query.setParameter("departamento", codigoDepartamento);
        }
        return query.getResultList();
    }

    public List<MunicipioDto> getLstMunicipiosDisponiblesDeInteres(BigDecimal idCapaDistribucion, String codigoDepartamento) {
        String sql = Constantes.QUERY_PROVEEDOR_MUNICIPIOS_DISPONIBLES_DE_INTERES;
        sql = codigoDepartamento.equals("00") ? sql.replace("COMODIN_DEPARTAMENTO", "")
                : sql.replace("COMODIN_DEPARTAMENTO", "and depa.codigo_departamento = '" + codigoDepartamento + "'");
        Query q = em.createNativeQuery(sql, MunicipioDto.class);
        q.setParameter(1, idCapaDistribucion);
        return q.getResultList();
    }

    public List<MunicipioDto> getLstMunicipiosDeInteres(BigDecimal idCapaDistribucion) {
        Query q = em.createNamedQuery("Proveedor.MunicipiosDeIntegeres", MunicipioDto.class);
        q.setParameter(1, idCapaDistribucion);
        return q.getResultList();
    }

    public String findNombreMunicipioCe(String codigoEntidad) {
        Query q = em.createNativeQuery("select mun.nombre_municipio || case when vw.nombre_canton is null then null end || case when vw.nombre_canton is not null then ', Cantón: '||vw.nombre_canton end nombre_canton "
                + "from vw_catalogo_entidad_educativa vw "
                + "inner join municipio mun on vw.codigo_municipio = mun.codigo_municipio "
                + "inner join departamento dep on mun.codigo_departamento = dep.codigo_departamento and dep.codigo_departamento = vw.codigo_departamento "
                + "WHERE vw.codigo_entidad = '" + codigoEntidad + "'");
        List lst = q.getResultList();
        String codMunicipio = lst.get(0).toString();

        return codMunicipio;
    }

    public List<MunicipioAledanho> getLstMunicipiosAledanhos() {
        Query q = em.createQuery("SELECT m FROM MunicipioAledanho m", MunicipioAledanho.class);
        return q.getResultList();
    }
}
