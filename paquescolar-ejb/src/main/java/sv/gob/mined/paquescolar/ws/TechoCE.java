/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ws;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.ejb.PreciosReferenciaEJB;
import sv.gob.mined.paquescolar.ejb.ProveedorEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.EstadisticaCenso;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.PreciosRefRubro;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.TechoRubroEntEdu;

/**
 *
 * @author misanchez
 */
@WebService(serviceName = "TechoCE")
@Stateless()
public class TechoCE {

    @EJB
    UtilEJB utilEJB;
    @EJB
    EntidadEducativaEJB entidadEducativaEJB;
    @EJB
    PreciosReferenciaEJB preciosReferenciaEJB;
    @EJB
    AnhoProcesoEJB anhoProcesoEJB;
    @EJB 
    ProveedorEJB proveedorEJB;

    /**
     *
     * @param codigoEntidad
     * @param idProceso
     * @param parMas
     * @param parFem
     * @param cicloIMas
     * @param cicloIFem
     * @param cicloIIMas
     * @param cicloIIFem
     * @param grado7mas
     * @param grado7fem
     * @param grado8mas
     * @param grado8fem
     * @param grado9mas
     * @param grado9fem
     * @param barMas
     * @param barFem
     * @return
     */
    @WebMethod(operationName = "asignarTecho")
    public String asignarTecho(@WebParam(name = "codigoEntidad") String codigoEntidad, @WebParam(name = "idProceso") int idProceso,
            @WebParam(name = "parMas") int parMas, @WebParam(name = "parFem") int parFem,
            @WebParam(name = "cicloIMas") int cicloIMas, @WebParam(name = "cicloIFem") int cicloIFem,
            @WebParam(name = "cicloIIMas") int cicloIIMas, @WebParam(name = "cicloIIFem") int cicloIIFem,
            @WebParam(name = "grado7mas") int grado7mas, @WebParam(name = "grado7fem") int grado7fem,
            @WebParam(name = "grado8mas") int grado8mas, @WebParam(name = "grado8fem") int grado8fem,
            @WebParam(name = "grado9mas") int grado9mas, @WebParam(name = "grado9fem") int grado9fem,
            @WebParam(name = "barMas") int barMas, @WebParam(name = "barFem") int barFem) {
        int cicloIIIFem = grado7fem + grado8fem + grado9fem;
        int cicloIIIMas = grado7mas + grado8mas + grado9mas;
        DetalleProcesoAdq detProAdqUni;
        DetalleProcesoAdq detProAdqUti;
        DetalleProcesoAdq detProAdqZap;
        EstadisticaCenso estPar;
        EstadisticaCenso estCiclo1;
        EstadisticaCenso estCiclo2;
        EstadisticaCenso estCiclo3;
        EstadisticaCenso estGrado7;
        EstadisticaCenso estGrado8;
        EstadisticaCenso estGrado9;
        EstadisticaCenso estBac;
        TechoRubroEntEdu techoUni;
        TechoRubroEntEdu techoUti;
        TechoRubroEntEdu techoZap;

        ProcesoAdquisicion procesoAdquisicion = utilEJB.find(ProcesoAdquisicion.class, idProceso);

        detProAdqUni = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion, BigDecimal.ONE);
        detProAdqUti = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion, new BigDecimal(2));
        detProAdqZap = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion, new BigDecimal(3));

        estPar = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, BigDecimal.ONE, procesoAdquisicion);
        estCiclo1 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("3"), procesoAdquisicion);
        estCiclo2 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("4"), procesoAdquisicion);
        estCiclo3 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("5"), procesoAdquisicion);
        estGrado7 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("7"), procesoAdquisicion);
        estGrado8 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("8"), procesoAdquisicion);
        estGrado9 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("9"), procesoAdquisicion);
        estBac = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("6"), procesoAdquisicion);

        if (estPar.getIdEstadistica() == null) {
            estPar.setCodigoEntidad(codigoEntidad);
            estPar.setFemenimo(new BigInteger(String.valueOf(parFem)));
            estPar.setMasculino(new BigInteger(String.valueOf(parMas)));
            estPar.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("1")));
            estPar.setIdProcesoAdq(procesoAdquisicion);

            estCiclo1.setCodigoEntidad(codigoEntidad);
            estCiclo1.setFemenimo(new BigInteger(String.valueOf(cicloIFem)));
            estCiclo1.setMasculino(new BigInteger(String.valueOf(cicloIMas)));
            estCiclo1.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("3")));
            estCiclo1.setIdProcesoAdq(procesoAdquisicion);

            estCiclo2.setCodigoEntidad(codigoEntidad);
            estCiclo2.setFemenimo(new BigInteger(String.valueOf(cicloIIFem)));
            estCiclo2.setMasculino(new BigInteger(String.valueOf(cicloIIMas)));
            estCiclo2.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("4")));
            estCiclo2.setIdProcesoAdq(procesoAdquisicion);

            estCiclo3.setCodigoEntidad(codigoEntidad);
            estCiclo3.setFemenimo(new BigInteger(String.valueOf(cicloIIIFem)));
            estCiclo3.setMasculino(new BigInteger(String.valueOf(cicloIIIMas)));
            estCiclo3.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("5")));
            estCiclo3.setIdProcesoAdq(procesoAdquisicion);

            estGrado7.setCodigoEntidad(codigoEntidad);
            estGrado7.setFemenimo(new BigInteger(String.valueOf(grado7fem)));
            estGrado7.setMasculino(new BigInteger(String.valueOf(grado7mas)));
            estGrado7.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("7")));
            estGrado7.setIdProcesoAdq(procesoAdquisicion);

            estGrado8.setCodigoEntidad(codigoEntidad);
            estGrado8.setFemenimo(new BigInteger(String.valueOf(grado8fem)));
            estGrado8.setMasculino(new BigInteger(String.valueOf(grado8mas)));
            estGrado8.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("8")));
            estGrado8.setIdProcesoAdq(procesoAdquisicion);

            estGrado9.setCodigoEntidad(codigoEntidad);
            estGrado9.setFemenimo(new BigInteger(String.valueOf(grado9fem)));
            estGrado9.setMasculino(new BigInteger(String.valueOf(grado9mas)));
            estGrado9.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("9")));
            estGrado9.setIdProcesoAdq(procesoAdquisicion);

            estBac.setCodigoEntidad(codigoEntidad);
            estBac.setFemenimo(new BigInteger(String.valueOf(barFem)));
            estBac.setMasculino(new BigInteger(String.valueOf(barMas)));
            estBac.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("6")));
            estBac.setIdProcesoAdq(procesoAdquisicion);

            entidadEducativaEJB.asignarTechoCe(estPar, idProceso);
            entidadEducativaEJB.asignarTechoCe(estCiclo1, idProceso);
            entidadEducativaEJB.asignarTechoCe(estCiclo2, idProceso);
            entidadEducativaEJB.asignarTechoCe(estCiclo3, idProceso);
            entidadEducativaEJB.asignarTechoCe(estGrado7, idProceso);
            entidadEducativaEJB.asignarTechoCe(estGrado8, idProceso);
            entidadEducativaEJB.asignarTechoCe(estGrado9, idProceso);
            entidadEducativaEJB.asignarTechoCe(estBac, idProceso);

            techoUni = entidadEducativaEJB.findTechoByProceso(detProAdqUni, codigoEntidad, "ADMIN");
            techoUti = entidadEducativaEJB.findTechoByProceso(detProAdqUti, codigoEntidad, "ADMIN");
            techoZap = entidadEducativaEJB.findTechoByProceso(detProAdqZap, codigoEntidad, "ADMIN");

            techoUni.setMontoPresupuestado(calcularPresupuesto(1, estPar, estCiclo1, estCiclo2, estCiclo3, estGrado7, estGrado8, estGrado9, estBac, detProAdqUni, detProAdqUti, detProAdqZap));
            if (techoUni.getMontoAdjudicado().compareTo(BigDecimal.ZERO) == 0) {
                techoUni.setMontoDisponible(techoUni.getMontoPresupuestado());
            } else {
                techoUni.setMontoDisponible(techoUni.getMontoPresupuestado().add(techoUni.getMontoAdjudicado().negate()));
            }

            techoUti.setMontoPresupuestado(calcularPresupuesto(2, estPar, estCiclo1, estCiclo2, estCiclo3, estGrado7, estGrado8, estGrado9, estBac, detProAdqUni, detProAdqUti, detProAdqZap));
            if (techoUti.getMontoAdjudicado().compareTo(BigDecimal.ZERO) == 0) {
                techoUti.setMontoDisponible(techoUti.getMontoPresupuestado());
            } else {
                techoUti.setMontoDisponible(techoUti.getMontoPresupuestado().add(techoUti.getMontoAdjudicado().negate()));
            }
            techoZap.setMontoPresupuestado(calcularPresupuesto(3, estPar, estCiclo1, estCiclo2, estCiclo3, estGrado7, estGrado8, estGrado9, estBac, detProAdqUni, detProAdqUti, detProAdqZap));
            if (techoZap.getMontoAdjudicado().compareTo(BigDecimal.ZERO) == 0) {
                techoZap.setMontoDisponible(techoZap.getMontoPresupuestado());
            } else {
                techoZap.setMontoDisponible(techoZap.getMontoPresupuestado().add(techoZap.getMontoAdjudicado().negate()));
            }

            Logger.getLogger(TechoCE.class.getName()).log(Level.INFO, "codigo: {0} - {1}", new Object[]{codigoEntidad, entidadEducativaEJB.guardarPresupuesto("ADMIN", techoUni, techoUti, techoZap)});
        }
        return "ok";
    }

    private BigDecimal calcularPresupuesto(int idRubro,
            EstadisticaCenso estPar,
            EstadisticaCenso estCiclo1,
            EstadisticaCenso estCiclo2,
            EstadisticaCenso estCiclo3,
            EstadisticaCenso estGrado7,
            EstadisticaCenso estGrado8,
            EstadisticaCenso estGrado9,
            EstadisticaCenso estBac,
            DetalleProcesoAdq detProAdqUni, DetalleProcesoAdq detProAdqUti, DetalleProcesoAdq detProAdqZap) {
        BigDecimal num = BigDecimal.ONE;
        BigDecimal presupuesto = BigDecimal.ZERO;
        PreciosRefRubro preParTemp, preCiclo1Temp, preCiclo2Temp, preCiclo3Temp, preBarTemp;

        PreciosRefRubro preParUni;
        PreciosRefRubro preCicloIUni;
        PreciosRefRubro preCicloIIUni;
        PreciosRefRubro preCicloIIIUni;
        PreciosRefRubro preBarUni;
        PreciosRefRubro preParUti;
        PreciosRefRubro preCicloIUti;
        PreciosRefRubro preCicloIIUti;
        PreciosRefRubro preCicloIIIUti;
        PreciosRefRubro preBarUti;
        PreciosRefRubro preParZap;
        PreciosRefRubro preCicloIZap;
        PreciosRefRubro preCicloIIZap;
        PreciosRefRubro preCicloIIIZap;
        PreciosRefRubro preBarZap;
        PreciosRefRubro preGrado7Uti = new PreciosRefRubro();
        PreciosRefRubro preGrado8Uti = new PreciosRefRubro();
        PreciosRefRubro preGrado9Uti = new PreciosRefRubro();

        preParUni = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estPar.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUni);
        preCicloIUni = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estCiclo1.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUni);
        preCicloIIUni = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estCiclo2.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUni);
        preCicloIIIUni = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estCiclo3.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUni);
        preBarUni = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estBac.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUni);

        preParUti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estPar.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
        preCicloIUti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estCiclo1.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
        preCicloIIUti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estCiclo2.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
        preCicloIIIUti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estCiclo3.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
        preBarUti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estBac.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);

        preParZap = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estPar.getIdNivelEducativo().getIdNivelEducativo(), detProAdqZap);
        preCicloIZap = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estCiclo1.getIdNivelEducativo().getIdNivelEducativo(), detProAdqZap);
        preCicloIIZap = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estCiclo2.getIdNivelEducativo().getIdNivelEducativo(), detProAdqZap);
        preCicloIIIZap = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estCiclo3.getIdNivelEducativo().getIdNivelEducativo(), detProAdqZap);
        preBarZap = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estBac.getIdNivelEducativo().getIdNivelEducativo(), detProAdqZap);

        switch (idRubro) {
            case 1:
                preParTemp = preParUni;
                preCiclo1Temp = preCicloIUni;
                preCiclo2Temp = preCicloIIUni;
                preCiclo3Temp = preCicloIIIUni;
                preBarTemp = preBarUni;
                num = new BigDecimal(1);
                break;
            case 2:
                preParTemp = preParUti;
                preCiclo1Temp = preCicloIUti;
                preCiclo2Temp = preCicloIIUti;
                preCiclo3Temp = preCicloIIIUti;
                preBarTemp = preBarUti;

                preGrado7Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estGrado7.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado8Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estGrado8.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado9Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estGrado9.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                break;
            default:
                preParTemp = preParZap;
                preCiclo1Temp = preCicloIZap;
                preCiclo2Temp = preCicloIIZap;
                preCiclo3Temp = preCicloIIIZap;
                preBarTemp = preBarZap;
                break;
        }

        presupuesto = presupuesto.add(preParTemp.getPrecioMaxMas().multiply(new BigDecimal(estPar.getMasculino())).multiply(num));
        presupuesto = presupuesto.add(preParTemp.getPrecioMaxFem().multiply(new BigDecimal(estPar.getFemenimo())).multiply(num));

        presupuesto = presupuesto.add(preCiclo1Temp.getPrecioMaxMas().multiply(new BigDecimal(estCiclo1.getMasculino())).multiply(num));
        presupuesto = presupuesto.add(preCiclo1Temp.getPrecioMaxFem().multiply(new BigDecimal(estCiclo1.getFemenimo())).multiply(num));

        presupuesto = presupuesto.add(preCiclo2Temp.getPrecioMaxMas().multiply(new BigDecimal(estCiclo2.getMasculino())).multiply(num));
        presupuesto = presupuesto.add(preCiclo2Temp.getPrecioMaxFem().multiply(new BigDecimal(estCiclo2.getFemenimo())).multiply(num));

        presupuesto = presupuesto.add(preCiclo3Temp.getPrecioMaxMas().multiply(new BigDecimal(estCiclo3.getMasculino())).multiply(num));
        presupuesto = presupuesto.add(preCiclo3Temp.getPrecioMaxFem().multiply(new BigDecimal(estCiclo3.getFemenimo())).multiply(num));

        presupuesto = presupuesto.add(preBarTemp.getPrecioMaxFem().multiply(new BigDecimal(estBac.getFemenimo())).multiply(num));
        presupuesto = presupuesto.add(preBarTemp.getPrecioMaxMas().multiply(new BigDecimal(estBac.getMasculino())).multiply(num));

        if (idRubro == 2 && detProAdqUti.getIdProcesoAdq().getIdAnho().getIdAnho().intValue() >= 6) { //mayor o igual de anho 2018
            presupuesto = presupuesto.add(new BigDecimal(estGrado7.getMasculino().add(estGrado7.getFemenimo())).multiply(preGrado7Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(estGrado8.getMasculino().add(estGrado8.getFemenimo())).multiply(preGrado8Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(estGrado9.getMasculino().add(estGrado9.getFemenimo())).multiply(preGrado9Uti.getPrecioMaxMas()));
        }

        return presupuesto;
    }
    
    @WebMethod(operationName = "updateCuentaEmpresa")
    public boolean updateCuentaEmpresa(@WebParam(name = "numeroNit") String numeroNit, @WebParam(name = "numeroCuenta") String numeroCuenta) {
        return proveedorEJB.updateCuentaEmpresa(numeroNit, numeroCuenta);
    }
}