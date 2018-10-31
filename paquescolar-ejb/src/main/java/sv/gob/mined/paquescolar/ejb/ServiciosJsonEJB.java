/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.pojos.contratacion.SaldoProveedorDto;
import sv.gob.mined.paquescolar.model.pojos.dashboard.TotalContratadoDto;
import sv.gob.mined.paquescolar.model.pojos.dashboard.TotalResumenDto;
import sv.gob.mined.paquescolar.model.pojos.dashboard.TotalTipoEmpDto;
import sv.gob.mined.paquescolar.model.pojos.pagoprove.DatosResumenPagosDto;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class ServiciosJsonEJB {

    @PersistenceContext(unitName = "paquescolarUP")
    private EntityManager em;

    public List<DatosResumenPagosDto> getResumenPagoJsonByDepaAndDetProcesoAdq(String codigoDepa, Integer idDetProcesoAdq) {
        Query q = em.createNamedQuery("PagoProve.ResumenPagoByDepaAndDetProcesoAdq", DatosResumenPagosDto.class);
        q.setParameter(1, codigoDepa);
        q.setParameter(2, idDetProcesoAdq);
        return q.getResultList();
    }

    public List<DatosResumenPagosDto> getResumenPagoJsonByDetProcesoAdq(Integer idDetProcesoAdq) {
        Query q = em.createNamedQuery("PagoProve.ResumenPagoByDetProcesoAdq", DatosResumenPagosDto.class);
        q.setParameter(1, idDetProcesoAdq);
        return q.getResultList();
    }

    public List<TotalContratadoDto> getLstTotalContratado(Integer idDetProcesoAdq, String codigoDepartamento) {
        Query q;
        if (codigoDepartamento.equals("00")) {
            q = em.createNamedQuery("TotalContratadoDto.resumenContratado", TotalContratadoDto.class);
            q.setParameter(1, idDetProcesoAdq);
        } else {
            q = em.createNamedQuery("TotalContratadoDto.resumenContratadoByDepa", TotalContratadoDto.class);
            q.setParameter(1, idDetProcesoAdq);
            q.setParameter(2, codigoDepartamento);
        }

        return q.getResultList();
    }

    public List<TotalTipoEmpDto> getLstTotalTipoEmpContratado(Integer idDetProcesoAdq, String codigoDepartamento) {
        Query q;
        if (codigoDepartamento.equals("00")) {
            q = em.createNamedQuery("TotalTipoEmpDto.resumenTipoEmpContratado", TotalTipoEmpDto.class);
            q.setParameter(1, idDetProcesoAdq);
        } else {
            q = em.createNamedQuery("TotalTipoEmpDto.resumenTipoEmpContratadoByDepa", TotalTipoEmpDto.class);
            q.setParameter(1, idDetProcesoAdq);
            q.setParameter(2, codigoDepartamento);
        }
        return q.getResultList();
    }

    public List<TotalResumenDto> getLstTotalGeneroContratado(Integer idDetProcesoAdq, String codigoDepartamento, BigDecimal idTipoEmp) {
        Query q;
        if (codigoDepartamento.equals("00")) {
            if (idTipoEmp == null) {
                q = em.createNamedQuery("TotalResumenDto.resumenGeneroEmp", TotalResumenDto.class);
                q.setParameter(1, idDetProcesoAdq);
            } else {
                q = em.createNamedQuery("TotalResumenDto.resumenGeneroEmpAndTipoEmp", TotalResumenDto.class);
                q.setParameter(1, idDetProcesoAdq);
                q.setParameter(2, idTipoEmp);
            }
        } else {
            if (idTipoEmp == null) {
                q = em.createNamedQuery("TotalResumenDto.resumenGeneroEmpByDepa", TotalResumenDto.class);
                q.setParameter(1, idDetProcesoAdq);
                q.setParameter(2, codigoDepartamento);
            } else {
                q = em.createNamedQuery("TotalResumenDto.resumenGeneroEmpByDepaAndTipoEmp", TotalResumenDto.class);
                q.setParameter(1, idDetProcesoAdq);
                q.setParameter(2, codigoDepartamento);
                q.setParameter(3, idTipoEmp);
            }
        }
        return q.getResultList();
    }

    public List<SaldoProveedorDto> getLstSaldoProveedoresByDepAndCodDepa(DetalleProcesoAdq idDet, String codigoDepartamentoCe) {
        Query q = em.createNamedQuery("Contratacion.RptSaldoProveedor", SaldoProveedorDto.class);
        q.setParameter(1, idDet.getIdDetProcesoAdq());
        q.setParameter(2, codigoDepartamentoCe);
        q.setParameter(3, codigoDepartamentoCe);

        return q.getResultList();
    }
}
