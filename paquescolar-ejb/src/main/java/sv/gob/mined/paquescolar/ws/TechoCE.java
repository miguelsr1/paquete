/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.ws;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
import sv.gob.mined.paquescolar.ejb.ResolucionAdjudicativaEJB;
import sv.gob.mined.paquescolar.ejb.UtilEJB;
import sv.gob.mined.paquescolar.model.CatalogoProducto;
import sv.gob.mined.paquescolar.model.ContratosOrdenesCompras;
import sv.gob.mined.paquescolar.model.DetRubroMuestraInteres;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.DetalleResguardo;
import sv.gob.mined.paquescolar.model.EstadisticaCenso;
import sv.gob.mined.paquescolar.model.NivelEducativo;
import sv.gob.mined.paquescolar.model.NotaPruebasZapatero;
import sv.gob.mined.paquescolar.model.PreciosRefRubro;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.Resguardo;
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
    @EJB
    ResolucionAdjudicativaEJB resolucionAdjudicativaEJB;

    @WebMethod(operationName = "asignarTecho")
    public String asignarTecho(@WebParam(name = "codigoEntidad") String codigoEntidad, @WebParam(name = "idProceso") int idProceso,
            @WebParam(name = "ini2Mas") int ini2Mas, @WebParam(name = "ini2Fem") int ini2Fem,
            @WebParam(name = "ini3Mas") int ini3Mas, @WebParam(name = "ini3Fem") int ini3Fem,
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

        /*FileInputStream file = null;
        try {
            file = new FileInputStream(new File("C:\\Users\\Desarrollo\\Documents\\MINED\\paquete\\sigesmatpaqesc-2022.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            
            * Obtenemos la primera pestaña a la que se quiera procesar indicando el indice.
            * Una vez obtenida la hoja excel con las filas que se quieren leer obtenemos el iterator
            * que nos permite recorrer cada una de las filas que contiene.
             
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            // Recorremos todas las filas para mostrar el contenido de cada celda
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                if (row.getRowNum() != 0) {*/

 /*codigoEntidad = String.valueOf((int) row.getCell(0).getNumericCellValue());
                    ini2Fem = (int) row.getCell(1).getNumericCellValue();
                    ini2Mas = (int) row.getCell(2).getNumericCellValue();
                    ini3Fem = (int) row.getCell(3).getNumericCellValue();
                    ini3Mas = (int) row.getCell(4).getNumericCellValue();
                    parFem = (int) row.getCell(5).getNumericCellValue();
                    parMas = (int) row.getCell(6).getNumericCellValue();
                    grado1fem = (int) row.getCell(7).getNumericCellValue();
                    grado1mas = (int) row.getCell(8).getNumericCellValue();
                    grado2fem = (int) row.getCell(9).getNumericCellValue();
                    grado2mas = (int) row.getCell(10).getNumericCellValue();
                    grado3fem = (int) row.getCell(11).getNumericCellValue();
                    grado3mas = (int) row.getCell(12).getNumericCellValue();
                    grado4fem = (int) row.getCell(13).getNumericCellValue();
                    grado4mas = (int) row.getCell(14).getNumericCellValue();
                    grado5fem = (int) row.getCell(15).getNumericCellValue();
                    grado5mas = (int) row.getCell(16).getNumericCellValue();
                    grado6fem = (int) row.getCell(17).getNumericCellValue();
                    grado6mas = (int) row.getCell(18).getNumericCellValue();
                    grado7fem = (int) row.getCell(19).getNumericCellValue();
                    grado7mas = (int) row.getCell(20).getNumericCellValue();
                    grado8fem = (int) row.getCell(21).getNumericCellValue();
                    grado8mas = (int) row.getCell(22).getNumericCellValue();
                    grado9fem = (int) row.getCell(23).getNumericCellValue();
                    grado9mas = (int) row.getCell(24).getNumericCellValue();
                    b1fem = (int) row.getCell(25).getNumericCellValue();
                    b1mas = (int) row.getCell(26).getNumericCellValue();
                    b2fem = (int) row.getCell(27).getNumericCellValue();
                    b2mas = (int) row.getCell(28).getNumericCellValue();
                    b3fem = (int) row.getCell(29).getNumericCellValue();
                    b3mas = (int) row.getCell(30).getNumericCellValue();*/
        DetalleProcesoAdq detProAdqUni;
        DetalleProcesoAdq detProAdqUni2;
        DetalleProcesoAdq detProAdqUti;
        DetalleProcesoAdq detProAdqZap;
        EstadisticaCenso estIni2;
        EstadisticaCenso estIni3;
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

        ProcesoAdquisicion procesoAdquisicion = utilEJB.find(ProcesoAdquisicion.class, 21);

        detProAdqUni = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(4));
        detProAdqUni2 = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(5));
        detProAdqUti = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(2));
        detProAdqZap = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion.getIdProcesoAdq(), new BigDecimal(3));

        estIni2 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("25"), procesoAdquisicion);
        estIni3 = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("26"), procesoAdquisicion);
        estPar = entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("22"), procesoAdquisicion);
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
            estIni2.setCodigoEntidad(codigoEntidad);
            estIni2.setFemenimo(new BigInteger(String.valueOf(ini2Fem)));
            estIni2.setMasculino(new BigInteger(String.valueOf(ini2Mas)));
            estIni2.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("25")));
            estIni2.setIdProcesoAdq(procesoAdquisicion);

            estIni3.setCodigoEntidad(codigoEntidad);
            estIni3.setFemenimo(new BigInteger(String.valueOf(ini3Fem)));
            estIni3.setMasculino(new BigInteger(String.valueOf(ini3Mas)));
            estIni3.setIdNivelEducativo(utilEJB.find(NivelEducativo.class, new BigDecimal("26")));
            estIni3.setIdProcesoAdq(procesoAdquisicion);

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
            estBac.setFemenimo(new BigInteger(String.valueOf(b1fem + b2fem + b3fem)));
            estBac.setMasculino(new BigInteger(String.valueOf(b1mas + b2mas + b3mas)));
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

            entidadEducativaEJB.asignarTechoCe(estIni2, idProceso);
            entidadEducativaEJB.asignarTechoCe(estIni3, idProceso);
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

            Logger.getLogger(TechoCE.class.getName()).log(Level.INFO, "codigo:{0}", codigoEntidad);
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
        /* }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TechoCE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TechoCE.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(TechoCE.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/

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

    @WebMethod(operationName = "resguardo")
    public String resguardo(@WebParam(name = "codigoEntidad") String codigoEntidad,
            @WebParam(name = "idDetProcesoAdq") int idDetProcesoAdq,
            @WebParam(name = "item1") Integer item1, @WebParam(name = "item2") Integer item2,
            @WebParam(name = "item3") Integer item3, @WebParam(name = "item4") Integer item4,
            @WebParam(name = "item5") Integer item5, @WebParam(name = "item6") Integer item6,
            @WebParam(name = "item7") Integer item7, @WebParam(name = "item8") Integer item8,
            @WebParam(name = "item9") Integer item9, @WebParam(name = "item10") Integer item10,
            @WebParam(name = "item11") Integer item11, @WebParam(name = "item12") Integer item12,
            @WebParam(name = "item13") Integer item13) {

        Resguardo resguardo = new Resguardo();

        ContratosOrdenesCompras con = resolucionAdjudicativaEJB.findContratoByCodEntAndIdDetProceso(codigoEntidad, new BigDecimal(idDetProcesoAdq));
        resguardo.setIdContrato(con);
        resguardo.setUsuarioInsercion("ADMIN");
        resguardo.setFechaInsercion(new Date());
        resguardo.setEstadoEliminacion((short) 0);

        switch (con.getIdResolucionAdj().getIdParticipante().getIdOferta().getIdDetProcesoAdq().getIdRubroAdq().getIdRubroInteres().intValue()) {
            case 4:
                crearDetalle(resguardo, 4, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12, item13);
                break;
            case 2:
                crearDetalle(resguardo, 2, item1, item2, item3, item4, item5);
                break;
            case 3:
                crearDetalle(resguardo, 3, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10);
                break;
        }

        utilEJB.createEntity(resguardo);

        return "ok";
    }

    private Resguardo crearDetalle(Resguardo resguardo, int idRubro, Integer... items) {

        switch (idRubro) {
            case 4:
                resguardo = crearDetalleUniforme(resguardo, items);
                break;
            case 2:
                resguardo = crearDetalleUtiles(resguardo, items);
                break;
            case 3:
                resguardo = crearDetalleZapatos(resguardo, items);
                break;
        }

        return resguardo;
    }

    private Resguardo crearDetalleUniforme(Resguardo resguardo, Integer... items) {
        BigInteger cantidad;
        CatalogoProducto producto = null;
        NivelEducativo nivelPar = utilEJB.find(NivelEducativo.class, new BigDecimal(1));
        NivelEducativo nivelBasi = utilEJB.find(NivelEducativo.class, new BigDecimal(2));
        NivelEducativo nivelMedia = utilEJB.find(NivelEducativo.class, new BigDecimal(6));

        for (int i = 0; i < items.length; i++) {
            Integer valor = items[i];

            if (valor > 0) {
                DetalleResguardo det = new DetalleResguardo();
                cantidad = new BigInteger(valor.toString());

                switch (i + 1) {
                    case 1:
                    case 6:
                    case 10:
                        producto = utilEJB.find(CatalogoProducto.class, new BigDecimal(30));
                        break;
                    case 2:
                    case 7:
                    case 11:
                        producto = utilEJB.find(CatalogoProducto.class, new BigDecimal(44));
                        break;
                    case 3:
                    case 8:
                    case 12:
                        producto = utilEJB.find(CatalogoProducto.class, new BigDecimal(29));
                        break;
                    case 4:
                        producto = utilEJB.find(CatalogoProducto.class, new BigDecimal(31));
                        break;
                    case 5:
                    case 9:
                    case 13:
                        producto = utilEJB.find(CatalogoProducto.class, new BigDecimal(34));
                        break;
                }

                switch (i + 1) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        det.setIdNivelEducativo(nivelPar);
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        det.setIdNivelEducativo(nivelBasi);
                        break;
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                        det.setIdNivelEducativo(nivelMedia);
                        break;
                }

                det.setEstadoEliminacion((short) 0);
                det.setFechaInsercion(new Date());
                det.setIdResguardo(resguardo);
                det.setUsuarioInsercion("ADMIN");
                det.setNoItem(String.valueOf(i + 1));
                det.setCantidad(cantidad);
                det.setIdProducto(producto);
                resguardo.getDetalleResguardoList().add(det);
            }
        }
        return resguardo;
    }

    private Resguardo crearDetalleUtiles(Resguardo resguardo, Integer... items) {
        BigInteger cantidad;
        CatalogoProducto producto = utilEJB.find(CatalogoProducto.class, new BigDecimal(54));
        NivelEducativo nivelPar = utilEJB.find(NivelEducativo.class, new BigDecimal(1));
        NivelEducativo nivelC1 = utilEJB.find(NivelEducativo.class, new BigDecimal(3));
        NivelEducativo nivelC2 = utilEJB.find(NivelEducativo.class, new BigDecimal(4));
        NivelEducativo nivelC3 = utilEJB.find(NivelEducativo.class, new BigDecimal(5));
        NivelEducativo nivelMedia = utilEJB.find(NivelEducativo.class, new BigDecimal(6));

        for (int i = 0; i < items.length; i++) {
            Integer valor = items[i];
            if (valor > 0) {
                DetalleResguardo det = new DetalleResguardo();
                cantidad = new BigInteger(valor.toString());

                switch (i + 1) {
                    case 1:
                        det.setIdNivelEducativo(nivelPar);
                        break;
                    case 2:
                        det.setIdNivelEducativo(nivelC1);
                        break;
                    case 3:
                        det.setIdNivelEducativo(nivelC2);
                        break;
                    case 4:
                        det.setIdNivelEducativo(nivelC3);
                        break;
                    case 5:
                        det.setIdNivelEducativo(nivelMedia);
                        break;
                }

                det.setNoItem(String.valueOf(i + 1));
                det.setEstadoEliminacion((short) 0);
                det.setFechaInsercion(new Date());
                det.setIdResguardo(resguardo);
                det.setUsuarioInsercion("ADMIN");
                det.setCantidad(cantidad);
                det.setIdProducto(producto);
                resguardo.getDetalleResguardoList().add(det);
            }
        }
        return resguardo;
    }

    private Resguardo crearDetalleZapatos(Resguardo resguardo, Integer... items) {
        BigInteger cantidad;
        CatalogoProducto producto = null;
        NivelEducativo nivelPar = utilEJB.find(NivelEducativo.class, new BigDecimal(1));
        NivelEducativo nivelC1 = utilEJB.find(NivelEducativo.class, new BigDecimal(3));
        NivelEducativo nivelC2 = utilEJB.find(NivelEducativo.class, new BigDecimal(4));
        NivelEducativo nivelC3 = utilEJB.find(NivelEducativo.class, new BigDecimal(5));
        NivelEducativo nivelMedia = utilEJB.find(NivelEducativo.class, new BigDecimal(6));

        for (int i = 0; i < items.length; i++) {
            Integer valor = items[i];
            if (valor > 0) {
                DetalleResguardo det = new DetalleResguardo();
                cantidad = new BigInteger(valor.toString());

                switch (i + 1) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 9:
                        producto = utilEJB.find(CatalogoProducto.class, new BigDecimal(43));
                        break;
                    case 2:
                    case 4:
                    case 6:
                    case 8:
                    case 10:
                        producto = utilEJB.find(CatalogoProducto.class, new BigDecimal(21));
                        break;
                }

                switch (i + 1) {
                    case 1:
                    case 2:
                        det.setIdNivelEducativo(nivelPar);
                        break;
                    case 3:
                    case 4:
                        det.setIdNivelEducativo(nivelC1);
                        break;
                    case 5:
                    case 6:
                        det.setIdNivelEducativo(nivelC2);
                        break;
                    case 7:
                    case 8:
                        det.setIdNivelEducativo(nivelC3);
                        break;
                    case 9:
                    case 10:
                        det.setIdNivelEducativo(nivelMedia);
                        break;
                }

                det.setNoItem(String.valueOf(i + 1));
                det.setEstadoEliminacion((short) 0);
                det.setFechaInsercion(new Date());
                det.setIdResguardo(resguardo);
                det.setUsuarioInsercion("ADMIN");
                det.setCantidad(cantidad);
                det.setIdProducto(producto);
                resguardo.getDetalleResguardoList().add(det);
            }
        }
        return resguardo;
    }

    @WebMethod(operationName = "cargarNotasPruebasZapateros")
    public void cargarNotasPruebasZapateros(@WebParam(name = "numeroNit") String numeroNit, @WebParam(name = "anho") String anho, @WebParam(name = "notaNina") Short notaNina, @WebParam(name = "notaNino") Short notaNino) {
        DetRubroMuestraInteres det = proveedorEJB.findDetByNitAndIdAnho(numeroNit, anho);
        NotaPruebasZapatero nota = new NotaPruebasZapatero();
        nota.setIdMuestraInteres(det);
        nota.setNotaZapatoNina(notaNina);
        nota.setNotaZapatoNino(notaNino);
        
        utilEJB.createEntity(nota);
    }
}
