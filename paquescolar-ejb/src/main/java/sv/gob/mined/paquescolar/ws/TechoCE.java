/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ws;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import org.apache.commons.beanutils.BeanUtils;
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

    @WebMethod(operationName = "asignarTecho")
    public String asignarTecho(@WebParam(name = "codigoEntidad") String codigoEntidad, @WebParam(name = "idProceso") int idProceso,
            @WebParam(name = "parMas") int parMas, @WebParam(name = "parFem") int parFem,
            @WebParam(name = "grado1mas") int grado1mas, @WebParam(name = "grado1fem") int grado1fem,
            @WebParam(name = "grado2mas") int grado2mas, @WebParam(name = "grado2fem") int grado2fem,
            @WebParam(name = "grado3mas") int grado3mas, @WebParam(name = "grado3fem") int grado3fem,
            //@WebParam(name = "cicloIMas") int cicloIMas, @WebParam(name = "cicloIFem") int cicloIFem,
            @WebParam(name = "grado4mas") int grado4mas, @WebParam(name = "grado4fem") int grado4fem,
            @WebParam(name = "grado5mas") int grado5mas, @WebParam(name = "grado5fem") int grado5fem,
            @WebParam(name = "grado6mas") int grado6mas, @WebParam(name = "grado6fem") int grado6fem,
            //@WebParam(name = "cicloIIMas") int cicloIIMas, @WebParam(name = "cicloIIFem") int cicloIIFem,
            @WebParam(name = "grado7mas") int grado7mas, @WebParam(name = "grado7fem") int grado7fem,
            @WebParam(name = "grado8mas") int grado8mas, @WebParam(name = "grado8fem") int grado8fem,
            @WebParam(name = "grado9mas") int grado9mas, @WebParam(name = "grado9fem") int grado9fem,
            //@WebParam(name = "cicloIIIMas") int cicloIIIMas, @WebParam(name = "cicloIIIFem") int cicloIIIFem,
            @WebParam(name = "b1mas") int b1mas, @WebParam(name = "b1fem") int b1fem,
            @WebParam(name = "b2mas") int b2mas, @WebParam(name = "b2fem") int b2fem,
            @WebParam(name = "b3mas") int b3mas, @WebParam(name = "b3fem") int b3fem/*,
            @WebParam(name = "barMas") int barMas, @WebParam(name = "barFem") int barFem*/) {

        DetalleProcesoAdq detProAdqUni;
        DetalleProcesoAdq detProAdqUni2;
        DetalleProcesoAdq detProAdqUti;
        DetalleProcesoAdq detProAdqZap;
        EstadisticaCenso estPar;
        EstadisticaCenso estCiclo1;
        EstadisticaCenso estCiclo2;
        EstadisticaCenso estCiclo3;
        EstadisticaCenso estGrado1;
        EstadisticaCenso estGrado2;
        EstadisticaCenso estGrado3;
        EstadisticaCenso estGrado4;
        EstadisticaCenso estGrado5;
        EstadisticaCenso estGrado6;
        EstadisticaCenso estGrado7;
        EstadisticaCenso estGrado8;
        EstadisticaCenso estGrado9;
        EstadisticaCenso estB1;
        EstadisticaCenso estB2;
        EstadisticaCenso estB3;
        EstadisticaCenso estBac;
        TechoRubroEntEdu techoUni;
        TechoRubroEntEdu techoUni2 = new TechoRubroEntEdu();
        TechoRubroEntEdu techoUti;
        TechoRubroEntEdu techoZap;

        ProcesoAdquisicion procesoAdquisicion = utilEJB.find(ProcesoAdquisicion.class, idProceso);

        detProAdqUni = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(4));
        detProAdqUni2 = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(5));
        detProAdqUti = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(2));
        detProAdqZap = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(3));

        estPar = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, BigDecimal.ONE, procesoAdquisicion);
        estCiclo1 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("3"), procesoAdquisicion);
        estCiclo2 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("4"), procesoAdquisicion);
        estCiclo3 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("5"), procesoAdquisicion);
        estBac = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("6"), procesoAdquisicion);
        estGrado7 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("7"), procesoAdquisicion);
        estGrado8 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("8"), procesoAdquisicion);
        estGrado9 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("9"), procesoAdquisicion);
        estGrado1 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("10"), procesoAdquisicion);
        estGrado2 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("11"), procesoAdquisicion);
        estGrado3 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("12"), procesoAdquisicion);
        estGrado4 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("13"), procesoAdquisicion);
        estGrado5 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("14"), procesoAdquisicion);
        estGrado6 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("15"), procesoAdquisicion);
        estB1 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("16"), procesoAdquisicion);
        estB2 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("17"), procesoAdquisicion);
        estB3 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("18"), procesoAdquisicion);

        if (estPar.getIdEstadistica() == null) {
            //try {
                estPar.setCodigoEntidad(codigoEntidad);
                estPar.setFemenimo(new BigInteger(String.valueOf(parFem)));
                estPar.setMasculino(new BigInteger(String.valueOf(parMas)));
                estPar.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("22")));
                estPar.setIdProcesoAdq(procesoAdquisicion);

                estCiclo1.setCodigoEntidad(codigoEntidad);
                estCiclo1.setFemenimo(new BigInteger(String.valueOf(grado1fem + grado2fem + grado3fem)));
                estCiclo1.setMasculino(new BigInteger(String.valueOf(grado1mas + grado2mas + grado3mas)));
                estCiclo1.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("3")));
                estCiclo1.setIdProcesoAdq(procesoAdquisicion);

                estCiclo2.setCodigoEntidad(codigoEntidad);
                estCiclo2.setFemenimo(new BigInteger(String.valueOf(grado4fem + grado5fem + grado6fem)));
                estCiclo2.setMasculino(new BigInteger(String.valueOf(grado4mas + grado5mas + grado6mas)));
                estCiclo2.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("4")));
                estCiclo2.setIdProcesoAdq(procesoAdquisicion);

                estCiclo3.setCodigoEntidad(codigoEntidad);
                estCiclo3.setFemenimo(new BigInteger(String.valueOf(grado7fem + grado8fem + grado9fem)));
                estCiclo3.setMasculino(new BigInteger(String.valueOf(grado7fem + grado8fem + grado9fem)));
                estCiclo3.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("5")));
                estCiclo3.setIdProcesoAdq(procesoAdquisicion);

                estBac.setCodigoEntidad(codigoEntidad);
                estBac.setFemenimo(new BigInteger(String.valueOf(b1fem+b2fem+b3fem)));
                estBac.setMasculino(new BigInteger(String.valueOf(b1mas+b2mas+b3mas)));
                estBac.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("6")));
                estBac.setIdProcesoAdq(procesoAdquisicion);

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

                estGrado1.setCodigoEntidad(codigoEntidad);
                estGrado1.setFemenimo(new BigInteger(String.valueOf(grado1fem)));
                estGrado1.setMasculino(new BigInteger(String.valueOf(grado1mas)));
                estGrado1.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("10")));
                estGrado1.setIdProcesoAdq(procesoAdquisicion);

                estGrado2.setCodigoEntidad(codigoEntidad);
                estGrado2.setFemenimo(new BigInteger(String.valueOf(grado2fem)));
                estGrado2.setMasculino(new BigInteger(String.valueOf(grado2mas)));
                estGrado2.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("11")));
                estGrado2.setIdProcesoAdq(procesoAdquisicion);

                estGrado3.setCodigoEntidad(codigoEntidad);
                estGrado3.setFemenimo(new BigInteger(String.valueOf(grado3fem)));
                estGrado3.setMasculino(new BigInteger(String.valueOf(grado3mas)));
                estGrado3.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("12")));
                estGrado3.setIdProcesoAdq(procesoAdquisicion);

                estGrado4.setCodigoEntidad(codigoEntidad);
                estGrado4.setFemenimo(new BigInteger(String.valueOf(grado4fem)));
                estGrado4.setMasculino(new BigInteger(String.valueOf(grado4mas)));
                estGrado4.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("13")));
                estGrado4.setIdProcesoAdq(procesoAdquisicion);

                estGrado5.setCodigoEntidad(codigoEntidad);
                estGrado5.setFemenimo(new BigInteger(String.valueOf(grado5fem)));
                estGrado5.setMasculino(new BigInteger(String.valueOf(grado5mas)));
                estGrado5.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("14")));
                estGrado5.setIdProcesoAdq(procesoAdquisicion);

                estGrado6.setCodigoEntidad(codigoEntidad);
                estGrado6.setFemenimo(new BigInteger(String.valueOf(grado6fem)));
                estGrado6.setMasculino(new BigInteger(String.valueOf(grado6mas)));
                estGrado6.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("15")));
                estGrado6.setIdProcesoAdq(procesoAdquisicion);

                estB1.setCodigoEntidad(codigoEntidad);
                estB1.setFemenimo(new BigInteger(String.valueOf(b1fem)));
                estB1.setMasculino(new BigInteger(String.valueOf(b1mas)));
                estB1.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("16")));
                estB1.setIdProcesoAdq(procesoAdquisicion);

                estB2.setCodigoEntidad(codigoEntidad);
                estB2.setFemenimo(new BigInteger(String.valueOf(b2fem)));
                estB2.setMasculino(new BigInteger(String.valueOf(b2mas)));
                estB2.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("17")));
                estB2.setIdProcesoAdq(procesoAdquisicion);

                estB3.setCodigoEntidad(codigoEntidad);
                estB3.setFemenimo(new BigInteger(String.valueOf(b3fem)));
                estB3.setMasculino(new BigInteger(String.valueOf(b3mas)));
                estB3.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("18")));
                estB3.setIdProcesoAdq(procesoAdquisicion);

                entidadEducativaEJB.asignarTechoCe(estPar, idProceso);
                entidadEducativaEJB.asignarTechoCe(estCiclo1, idProceso);
                entidadEducativaEJB.asignarTechoCe(estCiclo2, idProceso);
                entidadEducativaEJB.asignarTechoCe(estCiclo3, idProceso);
                entidadEducativaEJB.asignarTechoCe(estBac, idProceso);
                entidadEducativaEJB.asignarTechoCe(estGrado7, idProceso);
                entidadEducativaEJB.asignarTechoCe(estGrado8, idProceso);
                entidadEducativaEJB.asignarTechoCe(estGrado9, idProceso);
                entidadEducativaEJB.asignarTechoCe(estGrado1, idProceso);
                entidadEducativaEJB.asignarTechoCe(estGrado2, idProceso);
                entidadEducativaEJB.asignarTechoCe(estGrado3, idProceso);
                entidadEducativaEJB.asignarTechoCe(estGrado4, idProceso);
                entidadEducativaEJB.asignarTechoCe(estGrado5, idProceso);
                entidadEducativaEJB.asignarTechoCe(estGrado6, idProceso);
                entidadEducativaEJB.asignarTechoCe(estB1, idProceso);
                entidadEducativaEJB.asignarTechoCe(estB2, idProceso);
                entidadEducativaEJB.asignarTechoCe(estB3, idProceso);

                /*techoUni = entidadEducativaEJB.findTechoByProceso(detProAdqUni, codigoEntidad, "ADMIN");
                techoUti = entidadEducativaEJB.findTechoByProceso(detProAdqUti, codigoEntidad, "ADMIN");
                techoZap = entidadEducativaEJB.findTechoByProceso(detProAdqZap, codigoEntidad, "ADMIN");

                techoUni.setMontoPresupuestado(calcularPresupuesto(1, estPar, estCiclo1, estCiclo2, estCiclo3, estGrado7, estGrado8, estGrado9, estBac, detProAdqUni, detProAdqUti, detProAdqZap, estGrado1, estGrado2, estGrado3, estGrado4, estGrado5, estGrado6, estB1, estB2, estB3));
                if (techoUni.getMontoAdjudicado().compareTo(BigDecimal.ZERO) == 0) {
                    techoUni.setMontoDisponible(techoUni.getMontoPresupuestado());
                } else {
                    techoUni.setMontoDisponible(techoUni.getMontoPresupuestado().add(techoUni.getMontoAdjudicado().negate()));
                }

                techoUti.setMontoPresupuestado(calcularPresupuesto(2, estPar, estCiclo1, estCiclo2, estCiclo3, estGrado7, estGrado8, estGrado9, estBac, detProAdqUni, detProAdqUti, detProAdqZap, estGrado1, estGrado2, estGrado3, estGrado4, estGrado5, estGrado6, estB1, estB2, estB3));
                if (techoUti.getMontoAdjudicado().compareTo(BigDecimal.ZERO) == 0) {
                    techoUti.setMontoDisponible(techoUti.getMontoPresupuestado());
                } else {
                    techoUti.setMontoDisponible(techoUti.getMontoPresupuestado().add(techoUti.getMontoAdjudicado().negate()));
                }
                techoZap.setMontoPresupuestado(calcularPresupuesto(3, estPar, estCiclo1, estCiclo2, estCiclo3, estGrado7, estGrado8, estGrado9, estBac, detProAdqUni, detProAdqUti, detProAdqZap, estGrado1, estGrado2, estGrado3, estGrado4, estGrado5, estGrado6, estB1, estB2, estB3));
                if (techoZap.getMontoAdjudicado().compareTo(BigDecimal.ZERO) == 0) {
                    techoZap.setMontoDisponible(techoZap.getMontoPresupuestado());
                } else {
                    techoZap.setMontoDisponible(techoZap.getMontoPresupuestado().add(techoZap.getMontoAdjudicado().negate()));
                }

                BeanUtils.copyProperties(techoUni2, techoUni);

                techoUni2.setIdDetProcesoAdq(detProAdqUni2);*/

                //Logger.getLogger(TechoCE.class.getName()).log(Level.INFO, "codigo: {0} - {1}", new Object[]{codigoEntidad, entidadEducativaEJB.guardarPresupuesto("ADMIN", techoUni, techoUni2, techoUti, techoZap)});
           
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
            DetalleProcesoAdq detProAdqUni, DetalleProcesoAdq detProAdqUti, DetalleProcesoAdq detProAdqZap,
            EstadisticaCenso estGrado1,
            EstadisticaCenso estGrado2,
            EstadisticaCenso estGrado3,
            EstadisticaCenso estGrado4,
            EstadisticaCenso estGrado5,
            EstadisticaCenso estGrado6,
            EstadisticaCenso estB1,
            EstadisticaCenso estB2,
            EstadisticaCenso estB3) {
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
        PreciosRefRubro preGrado1Uti = new PreciosRefRubro();
        PreciosRefRubro preGrado2Uti = new PreciosRefRubro();
        PreciosRefRubro preGrado3Uti = new PreciosRefRubro();
        PreciosRefRubro preGrado4Uti = new PreciosRefRubro();
        PreciosRefRubro preGrado5Uti = new PreciosRefRubro();
        PreciosRefRubro preGrado6Uti = new PreciosRefRubro();
        PreciosRefRubro preB1Uti = new PreciosRefRubro();
        PreciosRefRubro preB2Uti = new PreciosRefRubro();
        PreciosRefRubro preB3Uti = new PreciosRefRubro();

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
                preGrado1Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estGrado1.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado2Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estGrado2.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado3Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estGrado3.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado4Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estGrado4.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado5Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estGrado5.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado6Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estGrado6.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preB1Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estB1.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preB2Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estB2.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preB3Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estB3.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
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
            presupuesto = presupuesto.add(new BigDecimal(estGrado1.getMasculino().add(estGrado1.getFemenimo())).multiply(preGrado1Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(estGrado2.getMasculino().add(estGrado2.getFemenimo())).multiply(preGrado2Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(estGrado3.getMasculino().add(estGrado3.getFemenimo())).multiply(preGrado3Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(estGrado4.getMasculino().add(estGrado4.getFemenimo())).multiply(preGrado4Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(estGrado5.getMasculino().add(estGrado5.getFemenimo())).multiply(preGrado4Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(estGrado6.getMasculino().add(estGrado6.getFemenimo())).multiply(preGrado5Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(estB1.getMasculino().add(estB1.getFemenimo())).multiply(preB1Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(estB2.getMasculino().add(estB2.getFemenimo())).multiply(preB2Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(estB3.getMasculino().add(estB3.getFemenimo())).multiply(preB3Uti.getPrecioMaxMas()));
        }

        return presupuesto;
    }

    @WebMethod(operationName = "updateCuentaEmpresa")
    public boolean updateCuentaEmpresa(@WebParam(name = "numeroNit") String numeroNit, @WebParam(name = "numeroCuenta") String numeroCuenta) {
        return proveedorEJB.updateCuentaEmpresa(numeroNit, numeroCuenta);
    }
}
