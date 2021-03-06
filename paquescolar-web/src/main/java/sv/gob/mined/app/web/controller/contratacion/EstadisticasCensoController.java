/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.web.controller.contratacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.web.controller.AnhoProcesoController;
import sv.gob.mined.app.web.util.JsfUtil;
import sv.gob.mined.app.web.util.Reportes;
import sv.gob.mined.app.web.util.VarSession;
import sv.gob.mined.paquescolar.ejb.AnhoProcesoEJB;
import sv.gob.mined.paquescolar.ejb.EntidadEducativaEJB;
import sv.gob.mined.paquescolar.ejb.PreciosReferenciaEJB;
import sv.gob.mined.paquescolar.ejb.ReportesEJB;
import sv.gob.mined.paquescolar.model.DetalleProcesoAdq;
import sv.gob.mined.paquescolar.model.EstadisticaCenso;
import sv.gob.mined.paquescolar.model.OrganizacionEducativa;
import sv.gob.mined.paquescolar.model.PreciosRefRubro;
import sv.gob.mined.paquescolar.model.ProcesoAdquisicion;
import sv.gob.mined.paquescolar.model.TechoRubroEntEdu;
import sv.gob.mined.paquescolar.model.pojos.VwRptCertificacionPresupuestaria;
import sv.gob.mined.paquescolar.model.view.VwCatalogoEntidadEducativa;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class EstadisticasCensoController implements Serializable {

    private String codigoEntidad;
    private Boolean deshabilitado = true;
    private Boolean isProcesoAdq = true;
    private Boolean uniformes = true;
    private Boolean utiles = true;
    private Boolean zapatos = true;
    private BigInteger totalAlumnosMas = BigInteger.ZERO;
    private BigInteger totalAlumnosFem = BigInteger.ZERO;
    private BigInteger totalMatricula = BigInteger.ZERO;
    private BigDecimal nivelParUni = BigDecimal.ZERO;
    private BigDecimal nivelCiclo1Uni = BigDecimal.ZERO;
    private BigDecimal nivelCiclo2Uni = BigDecimal.ZERO;
    private BigDecimal nivelCiclo3Uni = BigDecimal.ZERO;
    private BigDecimal nivelMediaUni = BigDecimal.ZERO;
    private BigDecimal nivelParUti = BigDecimal.ZERO;
    private BigDecimal nivelCiclo1Uti = BigDecimal.ZERO;
    private BigDecimal nivelCiclo2Uti = BigDecimal.ZERO;
    private BigDecimal nivelCiclo3Uti = BigDecimal.ZERO;
    private BigDecimal nivelMediaUti = BigDecimal.ZERO;
    private BigDecimal nivelParZap = BigDecimal.ZERO;
    private BigDecimal nivelCiclo1Zap = BigDecimal.ZERO;
    private BigDecimal nivelCiclo2Zap = BigDecimal.ZERO;
    private BigDecimal nivelCiclo3Zap = BigDecimal.ZERO;
    private BigDecimal nivelMediaZap = BigDecimal.ZERO;
    private BigDecimal preUniformes = BigDecimal.ZERO;
    private BigDecimal preUtiles = BigDecimal.ZERO;
    private BigDecimal preZapatos = BigDecimal.ZERO;
    private VwCatalogoEntidadEducativa entidadEducativa;
    private ProcesoAdquisicion procesoAdquisicion = new ProcesoAdquisicion();
    private OrganizacionEducativa organizacionEducativa;
    private DetalleProcesoAdq detProAdqUni;
    private DetalleProcesoAdq detProAdqUni2;
    private DetalleProcesoAdq detProAdqUti;
    private DetalleProcesoAdq detProAdqZap;
    private EstadisticaCenso estaditicaPar = new EstadisticaCenso();
    private EstadisticaCenso estaditicaCiclo1 = new EstadisticaCenso();
    private EstadisticaCenso estaditicaCiclo2 = new EstadisticaCenso();
    private EstadisticaCenso estaditicaCiclo3 = new EstadisticaCenso();
    private EstadisticaCenso est1grado = new EstadisticaCenso();
    private EstadisticaCenso est2grado = new EstadisticaCenso();
    private EstadisticaCenso est3grado = new EstadisticaCenso();
    private EstadisticaCenso est4grado = new EstadisticaCenso();
    private EstadisticaCenso est5grado = new EstadisticaCenso();
    private EstadisticaCenso est6grado = new EstadisticaCenso();
    private EstadisticaCenso est7grado = new EstadisticaCenso();
    private EstadisticaCenso est8grado = new EstadisticaCenso();
    private EstadisticaCenso est9grado = new EstadisticaCenso();
    private EstadisticaCenso est1media = new EstadisticaCenso();
    private EstadisticaCenso est2media = new EstadisticaCenso();
    private EstadisticaCenso est3media = new EstadisticaCenso();
    private EstadisticaCenso estaditicaBac = new EstadisticaCenso();
    private PreciosRefRubro preParUni = new PreciosRefRubro();
    private PreciosRefRubro preCicloIUni = new PreciosRefRubro();
    private PreciosRefRubro preCicloIIUni = new PreciosRefRubro();
    private PreciosRefRubro preCicloIIIUni = new PreciosRefRubro();
    private PreciosRefRubro preBacUni = new PreciosRefRubro();

    private PreciosRefRubro preParUti = new PreciosRefRubro();
    private PreciosRefRubro preCicloIUti = new PreciosRefRubro();
    private PreciosRefRubro preCicloIIUti = new PreciosRefRubro();
    private PreciosRefRubro preCicloIIIUti = new PreciosRefRubro();
    private PreciosRefRubro preBacUti = new PreciosRefRubro();

    private PreciosRefRubro preParZap = new PreciosRefRubro();
    private PreciosRefRubro preCicloIZap = new PreciosRefRubro();
    private PreciosRefRubro preCicloIIZap = new PreciosRefRubro();
    private PreciosRefRubro preCicloIIIZap = new PreciosRefRubro();
    private PreciosRefRubro preBacZap = new PreciosRefRubro();

    private PreciosRefRubro preGrado1Uti = new PreciosRefRubro();
    private PreciosRefRubro preGrado2Uti = new PreciosRefRubro();
    private PreciosRefRubro preGrado3Uti = new PreciosRefRubro();
    private PreciosRefRubro preGrado4Uti = new PreciosRefRubro();
    private PreciosRefRubro preGrado5Uti = new PreciosRefRubro();
    private PreciosRefRubro preGrado6Uti = new PreciosRefRubro();
    private PreciosRefRubro preGrado7Uti = new PreciosRefRubro();
    private PreciosRefRubro preGrado8Uti = new PreciosRefRubro();
    private PreciosRefRubro preGrado9Uti = new PreciosRefRubro();
    private PreciosRefRubro preBachi1Uti = new PreciosRefRubro();
    private PreciosRefRubro preBachi2Uti = new PreciosRefRubro();

    private TechoRubroEntEdu techoUni = new TechoRubroEntEdu();
    private TechoRubroEntEdu techoUni2 = new TechoRubroEntEdu();
    private TechoRubroEntEdu techoUti = new TechoRubroEntEdu();
    private TechoRubroEntEdu techoZap = new TechoRubroEntEdu();
    @EJB
    private EntidadEducativaEJB entidadEducativaEJB;
    @EJB
    private PreciosReferenciaEJB preciosReferenciaEJB;
    @EJB
    private AnhoProcesoEJB anhoProcesoEJB;
    @EJB
    private ReportesEJB reportesEJB;

    /**
     * Creates a new instance of EstadisticaCensoController
     */
    public EstadisticasCensoController() {
    }

    @PostConstruct
    public void init() {
        VarSession.setVariableSessionED("0");
        prepareEdit();
    }

    public Boolean getUniformes() {
        return uniformes;
    }

    public void setUniformes(Boolean uniformes) {
        this.uniformes = uniformes;
    }

    public Boolean getUtiles() {
        return utiles;
    }

    public void setUtiles(Boolean utiles) {
        this.utiles = utiles;
    }

    public Boolean getZapatos() {
        return zapatos;
    }

    public void setZapatos(Boolean zapatos) {
        this.zapatos = zapatos;
    }

    public Boolean getIsProcesoAdq() {
        return isProcesoAdq;
    }

    public void setIsProcesoAdq(Boolean isProcesoAdq) {
        this.isProcesoAdq = isProcesoAdq;
    }

    public void habilitarFrm() {
        isProcesoAdq = false;
    }

    public ProcesoAdquisicion getProcesoAdquisicion() {
        return procesoAdquisicion;
    }

    public void setProcesoAdquisicion(ProcesoAdquisicion procesoAdquisicion) {
        this.procesoAdquisicion = procesoAdquisicion;
    }

    public Boolean getDeshabilitado() {
        return deshabilitado;
    }

    public void setDeshabilitado(Boolean deshabilitado) {
        this.deshabilitado = deshabilitado;
    }

    public BigDecimal getNivelParUni() {
        return nivelParUni;
    }

    public void setNivelParUni(BigDecimal nivelParUni) {
        this.nivelParUni = nivelParUni;
    }

    public BigDecimal getNivelCiclo1Uni() {
        return nivelCiclo1Uni;
    }

    public void setNivelCiclo1Uni(BigDecimal nivelCiclo1Uni) {
        this.nivelCiclo1Uni = nivelCiclo1Uni;
    }

    public BigDecimal getNivelCiclo2Uni() {
        return nivelCiclo2Uni;
    }

    public void setNivelCiclo2Uni(BigDecimal nivelCiclo2Uni) {
        this.nivelCiclo2Uni = nivelCiclo2Uni;
    }

    public BigDecimal getNivelCiclo3Uni() {
        return nivelCiclo3Uni;
    }

    public void setNivelCiclo3Uni(BigDecimal nivelCiclo3Uni) {
        this.nivelCiclo3Uni = nivelCiclo3Uni;
    }

    public BigDecimal getNivelMediaUni() {
        return nivelMediaUni;
    }

    public void setNivelMediaUni(BigDecimal nivelMediaUni) {
        this.nivelMediaUni = nivelMediaUni;
    }

    public BigDecimal getNivelParUti() {
        return nivelParUti;
    }

    public void setNivelParUti(BigDecimal nivelParUti) {
        this.nivelParUti = nivelParUti;
    }

    public BigDecimal getNivelCiclo1Uti() {
        return nivelCiclo1Uti;
    }

    public void setNivelCiclo1Uti(BigDecimal nivelCiclo1Uti) {
        this.nivelCiclo1Uti = nivelCiclo1Uti;
    }

    public BigDecimal getNivelCiclo2Uti() {
        return nivelCiclo2Uti;
    }

    public void setNivelCiclo2Uti(BigDecimal nivelCiclo2Uti) {
        this.nivelCiclo2Uti = nivelCiclo2Uti;
    }

    public BigDecimal getNivelCiclo3Uti() {
        return nivelCiclo3Uti;
    }

    public void setNivelCiclo3Uti(BigDecimal nivelCiclo3Uti) {
        this.nivelCiclo3Uti = nivelCiclo3Uti;
    }

    public BigDecimal getNivelMediaUti() {
        return nivelMediaUti;
    }

    public void setNivelMediaUti(BigDecimal nivelMediaUti) {
        this.nivelMediaUti = nivelMediaUti;
    }

    public BigDecimal getNivelParZap() {
        return nivelParZap;
    }

    public void setNivelParZap(BigDecimal nivelParZap) {
        this.nivelParZap = nivelParZap;
    }

    public BigDecimal getNivelCiclo1Zap() {
        return nivelCiclo1Zap;
    }

    public void setNivelCiclo1Zap(BigDecimal nivelCiclo1Zap) {
        this.nivelCiclo1Zap = nivelCiclo1Zap;
    }

    public BigDecimal getNivelCiclo2Zap() {
        return nivelCiclo2Zap;
    }

    public void setNivelCiclo2Zap(BigDecimal nivelCiclo2Zap) {
        this.nivelCiclo2Zap = nivelCiclo2Zap;
    }

    public BigDecimal getNivelCiclo3Zap() {
        return nivelCiclo3Zap;
    }

    public void setNivelCiclo3Zap(BigDecimal nivelCiclo3Zap) {
        this.nivelCiclo3Zap = nivelCiclo3Zap;
    }

    public BigDecimal getNivelMediaZap() {
        return nivelMediaZap;
    }

    public void setNivelMediaZap(BigDecimal nivelMediaZap) {
        this.nivelMediaZap = nivelMediaZap;
    }

    public BigInteger getTotalAlumnosMas() {
        return totalAlumnosMas;
    }

    public void setTotalAlumnosMas(BigInteger totalAlumnosMas) {
        this.totalAlumnosMas = totalAlumnosMas;
    }

    public BigInteger getTotalAlumnosFem() {
        return totalAlumnosFem;
    }

    public void setTotalAlumnosFem(BigInteger totalAlumnosFem) {
        this.totalAlumnosFem = totalAlumnosFem;
    }

    public BigInteger getTotalMatricula() {
        return totalMatricula;
    }

    public void setTotalMatricula(BigInteger totalMatricula) {
        this.totalMatricula = totalMatricula;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public VwCatalogoEntidadEducativa getEntidadEducativa() {
        return entidadEducativa;
    }

    public void setEntidadEducativa(VwCatalogoEntidadEducativa entidadEducativa) {
        this.entidadEducativa = entidadEducativa;
    }

    public OrganizacionEducativa getOrganizacionEducativa() {
        return organizacionEducativa;
    }

    public void setOrganizacionEducativa(OrganizacionEducativa organizacionEducativa) {
        this.organizacionEducativa = organizacionEducativa;
    }

    public boolean getEENuevo() {
        return VarSession.getVariableSessionED() == 1;
    }

    public boolean getEEModificar() {
        return VarSession.getVariableSessionED() == 2;
    }

    public void prepareCreate() {
        VarSession.setVariableSessionED("1");
        deshabilitado = false;
    }

    public void prepareEdit() {
        VarSession.setVariableSessionED("0");
        deshabilitado = false;
        codigoEntidad = "";
        entidadEducativa = new VwCatalogoEntidadEducativa();
        organizacionEducativa = new OrganizacionEducativa();
        estaditicaPar = new EstadisticaCenso();
        estaditicaCiclo1 = new EstadisticaCenso();
        estaditicaCiclo2 = new EstadisticaCenso();
        estaditicaCiclo3 = new EstadisticaCenso();
        estaditicaBac = new EstadisticaCenso();
    }

    public EstadisticaCenso getEstaditicaPar() {
        return estaditicaPar;
    }

    public void setEstaditicaPar(EstadisticaCenso estaditicaPar) {
        this.estaditicaPar = estaditicaPar;
    }

    public EstadisticaCenso getEstaditicaCiclo1() {
        return estaditicaCiclo1;
    }

    public void setEstaditicaCiclo1(EstadisticaCenso estaditicaCiclo1) {
        this.estaditicaCiclo1 = estaditicaCiclo1;
    }

    public EstadisticaCenso getEstaditicaCiclo2() {
        return estaditicaCiclo2;
    }

    public void setEstaditicaCiclo2(EstadisticaCenso estaditicaCiclo2) {
        this.estaditicaCiclo2 = estaditicaCiclo2;
    }

    public EstadisticaCenso getEstaditicaCiclo3() {
        return estaditicaCiclo3;
    }

    public void setEstaditicaCiclo3(EstadisticaCenso estaditicaCiclo3) {
        this.estaditicaCiclo3 = estaditicaCiclo3;
    }

    public EstadisticaCenso getEstaditicaBac() {
        return estaditicaBac;
    }

    public void setEstaditicaBac(EstadisticaCenso estaditicaBac) {
        this.estaditicaBac = estaditicaBac;
    }

    private void recuperarProcesoAdq() {
        procesoAdquisicion = ((AnhoProcesoController) FacesContext.getCurrentInstance().getApplication().getELResolver().
                getValue(FacesContext.getCurrentInstance().getELContext(), null, "anhoProcesoController")).getProceso();
        if (procesoAdquisicion == null || procesoAdquisicion.getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de adquisición.");
        }
    }

    public void buscarEntidadEducativa() {
        if (codigoEntidad != null && codigoEntidad.length() == 5) {
            recuperarProcesoAdq();

            if (procesoAdquisicion != null) {
                organizacionEducativa = entidadEducativaEJB.getPresidenteOrganismoEscolar(codigoEntidad);

                if (organizacionEducativa.getIdOrganizacionEducativa() == null) {
                    organizacionEducativa.setCargo("Presidente Propietario, Director");
                    organizacionEducativa.setCodigoEntidad(codigoEntidad);
                    organizacionEducativa.setEstadoEliminacion(BigInteger.ZERO);
                    organizacionEducativa.setFechaInsercion(new Date());
                    organizacionEducativa.setFirmaContrato(BigInteger.ONE);
                    organizacionEducativa.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                }

                isProcesoAdq = false;
                entidadEducativa = entidadEducativaEJB.getEntidadEducativa(codigoEntidad);

                if (procesoAdquisicion.getIdAnho().getIdAnho().intValue() < 6) {//menor a 2018
                    detProAdqUni = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion, BigDecimal.ONE);
                } else {
                    detProAdqUni = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion, new BigDecimal(4));
                    detProAdqUni2 = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion, new BigDecimal(5));
                }

                detProAdqUti = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion, new BigDecimal(2));
                detProAdqZap = anhoProcesoEJB.getDetProcesoAdq(procesoAdquisicion, new BigDecimal(3));

                setEstaditicaPar(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, BigDecimal.ONE, procesoAdquisicion));
                setEstaditicaCiclo1(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("3"), procesoAdquisicion));
                setEstaditicaCiclo2(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("4"), procesoAdquisicion));
                setEstaditicaCiclo3(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("5"), procesoAdquisicion));
                setEstaditicaBac(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("6"), procesoAdquisicion));
                setEst7grado(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("7"), procesoAdquisicion));
                setEst8grado(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("8"), procesoAdquisicion));
                setEst9grado(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("9"), procesoAdquisicion));

                preParUni = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaPar.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUni);
                preCicloIUni = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaCiclo1.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUni);
                preCicloIIUni = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaCiclo2.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUni);
                preCicloIIIUni = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaCiclo3.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUni);
                preBacUni = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaBac.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUni);

                preParUti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaPar.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preCicloIUti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaCiclo1.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preCicloIIUti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaCiclo2.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preCicloIIIUti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaCiclo3.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preBacUti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaBac.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);

                setEst1grado(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("10"), procesoAdquisicion));
                setEst2grado(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("11"), procesoAdquisicion));
                setEst3grado(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("12"), procesoAdquisicion));
                setEst4grado(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("13"), procesoAdquisicion));
                setEst5grado(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("14"), procesoAdquisicion));
                setEst6grado(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("15"), procesoAdquisicion));
                setEst1media(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("16"), procesoAdquisicion));
                setEst2media(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("17"), procesoAdquisicion));
                setEst3media(entidadEducativaEJB.getEstadisticaByCodEntAndNivelAndProceso(codigoEntidad, new BigDecimal("18"), procesoAdquisicion));
                preGrado1Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(est1grado.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado2Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(est2grado.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado3Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(est3grado.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado4Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(est4grado.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado5Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(est5grado.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado6Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(est6grado.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preBachi1Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(est1media.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preBachi2Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(est2media.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);

                preGrado7Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(est7grado.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado8Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(est8grado.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);
                preGrado9Uti = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(est9grado.getIdNivelEducativo().getIdNivelEducativo(), detProAdqUti);

                preParZap = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaPar.getIdNivelEducativo().getIdNivelEducativo(), detProAdqZap);
                preCicloIZap = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaCiclo1.getIdNivelEducativo().getIdNivelEducativo(), detProAdqZap);
                preCicloIIZap = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaCiclo2.getIdNivelEducativo().getIdNivelEducativo(), detProAdqZap);
                preCicloIIIZap = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaCiclo3.getIdNivelEducativo().getIdNivelEducativo(), detProAdqZap);
                preBacZap = preciosReferenciaEJB.findPreciosRefRubroByNivelEduAndRubro(estaditicaBac.getIdNivelEducativo().getIdNivelEducativo(), detProAdqZap);

                techoUni = entidadEducativaEJB.findTechoByProceso(detProAdqUni, codigoEntidad, VarSession.getVariableSessionUsuario());
                if (procesoAdquisicion.getIdAnho().getIdAnho().intValue() > 5) {
                    techoUni2 = entidadEducativaEJB.findTechoByProceso(detProAdqUni2, codigoEntidad, VarSession.getVariableSessionUsuario());
                }
                techoUti = entidadEducativaEJB.findTechoByProceso(detProAdqUti, codigoEntidad, VarSession.getVariableSessionUsuario());
                techoZap = entidadEducativaEJB.findTechoByProceso(detProAdqZap, codigoEntidad, VarSession.getVariableSessionUsuario());
            } else {
                JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de adquisición.");
            }

            PrimeFaces.current().executeScript("actualizarDatos();");
        } else {
            isProcesoAdq = true;
        }
    }

    public PreciosRefRubro getPreParUni() {
        return preParUni;
    }

    public void setPreParUni(PreciosRefRubro preParUni) {
        this.preParUni = preParUni;
    }

    public PreciosRefRubro getPreCicloIUni() {
        return preCicloIUni;
    }

    public void setPreCicloIUni(PreciosRefRubro preCicloIUni) {
        this.preCicloIUni = preCicloIUni;
    }

    public PreciosRefRubro getPreCicloIIUni() {
        return preCicloIIUni;
    }

    public void setPreCicloIIUni(PreciosRefRubro preCicloIIUni) {
        this.preCicloIIUni = preCicloIIUni;
    }

    public PreciosRefRubro getPreCicloIIIUni() {
        return preCicloIIIUni;
    }

    public void setPreCicloIIIUni(PreciosRefRubro preCicloIIIUni) {
        this.preCicloIIIUni = preCicloIIIUni;
    }

    public PreciosRefRubro getPreBacUni() {
        return preBacUni;
    }

    public void setPreBacUni(PreciosRefRubro preBacUni) {
        this.preBacUni = preBacUni;
    }

    public PreciosRefRubro getPreParUti() {
        return preParUti;
    }

    public void setPreParUti(PreciosRefRubro preParUti) {
        this.preParUti = preParUti;
    }

    public PreciosRefRubro getPreCicloIUti() {
        return preCicloIUti;
    }

    public void setPreCicloIUti(PreciosRefRubro preCicloIUti) {
        this.preCicloIUti = preCicloIUti;
    }

    public PreciosRefRubro getPreCicloIIUti() {
        return preCicloIIUti;
    }

    public void setPreCicloIIUti(PreciosRefRubro preCicloIIUti) {
        this.preCicloIIUti = preCicloIIUti;
    }

    public PreciosRefRubro getPreCicloIIIUti() {
        return preCicloIIIUti;
    }

    public void setPreCicloIIIUti(PreciosRefRubro preCicloIIIUti) {
        this.preCicloIIIUti = preCicloIIIUti;
    }

    public PreciosRefRubro getPreBacUti() {
        return preBacUti;
    }

    public void setPreBacUti(PreciosRefRubro preBacUti) {
        this.preBacUti = preBacUti;
    }

    public PreciosRefRubro getPreParZap() {
        return preParZap;
    }

    public void setPreParZap(PreciosRefRubro preParZap) {
        this.preParZap = preParZap;
    }

    public PreciosRefRubro getPreCicloIZap() {
        return preCicloIZap;
    }

    public void setPreCicloIZap(PreciosRefRubro preCicloIZap) {
        this.preCicloIZap = preCicloIZap;
    }

    public PreciosRefRubro getPreCicloIIZap() {
        return preCicloIIZap;
    }

    public void setPreCicloIIZap(PreciosRefRubro preCicloIIZap) {
        this.preCicloIIZap = preCicloIIZap;
    }

    public PreciosRefRubro getPreCicloIIIZap() {
        return preCicloIIIZap;
    }

    public void setPreCicloIIIZap(PreciosRefRubro preCicloIIIZap) {
        this.preCicloIIIZap = preCicloIIIZap;
    }

    public PreciosRefRubro getPreBacZap() {
        return preBacZap;
    }

    public void setPreBacZap(PreciosRefRubro preBacZap) {
        this.preBacZap = preBacZap;
    }

    public void updateFilaTotal() {
        PrimeFaces.current().ajax().update("lblUni6");
        PrimeFaces.current().ajax().update("lblUti6");
        PrimeFaces.current().ajax().update("lblZap6");
    }

    public void guardar() {
        Boolean error;

        estaditicaCiclo1.setFemenimo(est1grado.getFemenimo().add(est2grado.getFemenimo()).add(est3grado.getFemenimo()));
        estaditicaCiclo1.setMasculino(est1grado.getMasculino().add(est2grado.getMasculino()).add(est3grado.getMasculino()));

        estaditicaCiclo2.setFemenimo(est4grado.getFemenimo().add(est5grado.getFemenimo()).add(est6grado.getFemenimo()));
        estaditicaCiclo2.setMasculino(est4grado.getMasculino().add(est5grado.getMasculino()).add(est6grado.getMasculino()));

        estaditicaCiclo3.setFemenimo(est7grado.getFemenimo().add(est8grado.getFemenimo()).add(est9grado.getFemenimo()));
        estaditicaCiclo3.setMasculino(est7grado.getMasculino().add(est8grado.getMasculino()).add(est9grado.getMasculino()));

        estaditicaBac.setFemenimo(est1media.getFemenimo().add(est2media.getFemenimo()).add(est3media.getFemenimo()));
        estaditicaBac.setMasculino(est1media.getMasculino().add(est2media.getMasculino()).add(est3media.getMasculino()));

        if (procesoAdquisicion == null || procesoAdquisicion.getIdProcesoAdq() == null) {
            JsfUtil.mensajeAlerta("Debe de seleccionar un proceso de adquisición.");
            return;
        }

        if (entidadEducativa == null || entidadEducativa.getCodigoEntidad() == null || entidadEducativa.getCodigoEntidad().isEmpty()) {
            JsfUtil.mensajeAlerta("Debe de ingresar un código válido de un Centro Educativo");
        } else {
            String msjError = "Hubo error en los niveles: ";
            String msjInfo = "Se guardaron los niveles: ";
            if (entidadEducativaEJB.guardarEstadistica(estaditicaPar, VarSession.getVariableSessionUsuario())) {
                msjError += "Parvularia, ";
            } else {
                msjInfo += "Parvularia, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(estaditicaCiclo1, VarSession.getVariableSessionUsuario())) {
                msjError += "1er Ciclo, ";
            } else {
                msjInfo += "1er Ciclo, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(estaditicaCiclo2, VarSession.getVariableSessionUsuario())) {
                msjError += "2do Ciclo, ";
            } else {
                msjInfo += "2do Ciclo, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(estaditicaCiclo3, VarSession.getVariableSessionUsuario())) {
                msjError += "3er Ciclo, ";
            } else {
                msjInfo += "3er Ciclo, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(est1grado, VarSession.getVariableSessionUsuario())) {
                msjError += "1er Grado, ";
            } else {
                msjInfo += "1er Grado, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(est2grado, VarSession.getVariableSessionUsuario())) {
                msjError += "2do Grado, ";
            } else {
                msjInfo += "2do Grado, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(est3grado, VarSession.getVariableSessionUsuario())) {
                msjError += "3er Grado, ";
            } else {
                msjInfo += "3er Grado, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(est4grado, VarSession.getVariableSessionUsuario())) {
                msjError += "4to Grado, ";
            } else {
                msjInfo += "4to Grado, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(est5grado, VarSession.getVariableSessionUsuario())) {
                msjError += "5to Grado, ";
            } else {
                msjInfo += "5to Grado, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(est6grado, VarSession.getVariableSessionUsuario())) {
                msjError += "6to Grado, ";
            } else {
                msjInfo += "6to Grado, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(est7grado, VarSession.getVariableSessionUsuario())) {
                msjError += "7mo Grado, ";
            } else {
                msjInfo += "7mo Grado, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(est8grado, VarSession.getVariableSessionUsuario())) {
                msjError += "8vo Grado, ";
            } else {
                msjInfo += "8vo Grado, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(est9grado, VarSession.getVariableSessionUsuario())) {
                msjError += "9no Grado, ";
            } else {
                msjInfo += "9no Grado, ";
            }
            if (entidadEducativaEJB.guardarEstadistica(estaditicaBac, VarSession.getVariableSessionUsuario())) {
                msjError += "Bachillerato";
            } else {
                msjInfo += "Bachillerato";
            }
            if (entidadEducativaEJB.guardarEstadistica(est1media, VarSession.getVariableSessionUsuario())) {
                msjError += "1er año de Bachillerato";
            } else {
                msjInfo += "1er año de Bachillerato";
            }
            if (entidadEducativaEJB.guardarEstadistica(est2media, VarSession.getVariableSessionUsuario())) {
                msjError += "2do año de Bachillerato";
            } else {
                msjInfo += "2do año de Bachillerato";
            }
            if (entidadEducativaEJB.guardarEstadistica(est3media, VarSession.getVariableSessionUsuario())) {
                msjError += "3er año de Bachillerato";
            } else {
                msjInfo += "3er año de Bachillerato";
            }

            if (procesoAdquisicion.getIdAnho().getIdAnho().intValue() < 6) {//menor a 2018
                techoUni.setMontoPresupuestado(calcularPresupuesto(1));
            } else {
                techoUni.setMontoPresupuestado(calcularPresupuesto(4));

                techoUni2.setMontoPresupuestado(calcularPresupuesto(5));
                if (techoUni2.getMontoAdjudicado().compareTo(BigDecimal.ZERO) == 0) {
                    techoUni2.setMontoDisponible(techoUni2.getMontoPresupuestado());
                } else {
                    techoUni2.setMontoDisponible(techoUni2.getMontoPresupuestado().add(techoUni2.getMontoAdjudicado().negate()));
                }
            }

            if (techoUni.getMontoAdjudicado().compareTo(BigDecimal.ZERO) == 0) {
                techoUni.setMontoDisponible(techoUni.getMontoPresupuestado());
            } else {
                techoUni.setMontoDisponible(techoUni.getMontoPresupuestado().add(techoUni.getMontoAdjudicado().negate()));
            }

            techoUti.setMontoPresupuestado(calcularPresupuesto(2));
            if (techoUti.getMontoAdjudicado().compareTo(BigDecimal.ZERO) == 0) {
                techoUti.setMontoDisponible(techoUti.getMontoPresupuestado());
            } else {
                techoUti.setMontoDisponible(techoUti.getMontoPresupuestado().add(techoUti.getMontoAdjudicado().negate()));
            }
            techoZap.setMontoPresupuestado(calcularPresupuesto(3));
            if (techoZap.getMontoAdjudicado().compareTo(BigDecimal.ZERO) == 0) {
                techoZap.setMontoDisponible(techoZap.getMontoPresupuestado());
            } else {
                techoZap.setMontoDisponible(techoZap.getMontoPresupuestado().add(techoZap.getMontoAdjudicado().negate()));
            }

            if (!msjInfo.replace("Se guardaron los niveles: ", "").isEmpty()) {
                JsfUtil.mensajeInformacion(msjInfo);
            }

            if (!msjError.replace("Hubo error en los niveles: ", "").isEmpty()) {
                JsfUtil.mensajeError(msjError);
            }

            if (procesoAdquisicion.getIdAnho().getIdAnho().intValue() < 6) {
                error = entidadEducativaEJB.guardarPresupuesto(VarSession.getVariableSessionUsuario(), techoUni, techoUti, techoZap);
            } else {
                error = entidadEducativaEJB.guardarPresupuesto(VarSession.getVariableSessionUsuario(), techoUni, techoUni2, techoUti, techoZap);
            }

            if (error) {
                JsfUtil.mensajeError("No se ha podido crear el presupuesto del C.E.");
            }

            if (organizacionEducativa.getIdOrganizacionEducativa() == null) {
                entidadEducativaEJB.create(organizacionEducativa);
            } else {
                entidadEducativaEJB.edit(organizacionEducativa);
            }
        }
    }

    public BigDecimal getPreUniformes() {
        return preUniformes;
    }

    public void setPreUniformes(BigDecimal preUniformes) {
        this.preUniformes = preUniformes;
    }

    public BigDecimal getPreUtiles() {
        return preUtiles;
    }

    public void setPreUtiles(BigDecimal preUtiles) {
        this.preUtiles = preUtiles;
    }

    public BigDecimal getPreZapatos() {
        return preZapatos;
    }

    public void setPreZapatos(BigDecimal preZapatos) {
        this.preZapatos = preZapatos;
    }

    private BigDecimal calcularPresupuesto(int idRubro) {
        BigDecimal num = BigDecimal.ONE;
        BigDecimal presupuesto = BigDecimal.ZERO;
        PreciosRefRubro preParTemp, preCiclo1Temp, preCiclo2Temp, preCiclo3Temp, preBacTemp;

        switch (idRubro) {
            case 1:
                preParTemp = preParUni;
                preCiclo1Temp = preCicloIUni;
                preCiclo2Temp = preCicloIIUni;
                preCiclo3Temp = preCicloIIIUni;
                preBacTemp = preBacUni;
                num = new BigDecimal(2);
                break;
            case 2:
                preParTemp = preParUti;
                preCiclo1Temp = preCicloIUti;
                preCiclo2Temp = preCicloIIUti;
                preCiclo3Temp = preCicloIIIUti;
                preBacTemp = preBacUti;
                break;
            case 3:
                preParTemp = preParZap;
                preCiclo1Temp = preCicloIZap;
                preCiclo2Temp = preCicloIIZap;
                preCiclo3Temp = preCicloIIIZap;
                preBacTemp = preBacZap;
                break;
            default://para rubro 4 y 5, 1er y 2do uniforme respectivamente
                preParTemp = preParUni;
                preCiclo1Temp = preCicloIUni;
                preCiclo2Temp = preCicloIIUni;
                preCiclo3Temp = preCicloIIIUni;
                preBacTemp = preBacUni;
                num = BigDecimal.ONE;
                break;
        }

        presupuesto = presupuesto.add(preParTemp.getPrecioMaxMas().multiply(new BigDecimal(estaditicaPar.getMasculino())).multiply(num));
        presupuesto = presupuesto.add(preParTemp.getPrecioMaxFem().multiply(new BigDecimal(estaditicaPar.getFemenimo())).multiply(num));

        presupuesto = presupuesto.add(preCiclo1Temp.getPrecioMaxMas().multiply(new BigDecimal(estaditicaCiclo1.getMasculino())).multiply(num));
        presupuesto = presupuesto.add(preCiclo1Temp.getPrecioMaxFem().multiply(new BigDecimal(estaditicaCiclo1.getFemenimo())).multiply(num));

        presupuesto = presupuesto.add(preCiclo2Temp.getPrecioMaxMas().multiply(new BigDecimal(estaditicaCiclo2.getMasculino())).multiply(num));
        presupuesto = presupuesto.add(preCiclo2Temp.getPrecioMaxFem().multiply(new BigDecimal(estaditicaCiclo2.getFemenimo())).multiply(num));

        presupuesto = presupuesto.add(preCiclo3Temp.getPrecioMaxMas().multiply(new BigDecimal(estaditicaCiclo3.getMasculino())).multiply(num));
        presupuesto = presupuesto.add(preCiclo3Temp.getPrecioMaxFem().multiply(new BigDecimal(estaditicaCiclo3.getFemenimo())).multiply(num));

        presupuesto = presupuesto.add(preBacTemp.getPrecioMaxFem().multiply(new BigDecimal(estaditicaBac.getFemenimo())).multiply(num));
        presupuesto = presupuesto.add(preBacTemp.getPrecioMaxMas().multiply(new BigDecimal(estaditicaBac.getMasculino())).multiply(num));

        //Presupuesto de libros
        if (idRubro == 2 && detProAdqUti.getIdProcesoAdq().getIdAnho().getIdAnho().intValue() >= 6) { //mayor o igual de anho 2018

            //este calculo es comun para años 2018 y 2019
            presupuesto = presupuesto.add(new BigDecimal(est7grado.getMasculino().add(est7grado.getFemenimo())).multiply(preGrado7Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(est8grado.getMasculino().add(est8grado.getFemenimo())).multiply(preGrado8Uti.getPrecioMaxMas()));
            presupuesto = presupuesto.add(new BigDecimal(est9grado.getMasculino().add(est9grado.getFemenimo())).multiply(preGrado9Uti.getPrecioMaxMas()));

            switch (detProAdqUti.getIdProcesoAdq().getIdAnho().getIdAnho().intValue()) {
                case 7://2019 libros desde 1 grado a 2 año de bachillerato
                    presupuesto = presupuesto.add(new BigDecimal(est1grado.getMasculino().add(est1grado.getFemenimo())).multiply(preGrado1Uti.getPrecioMaxMas()));
                    presupuesto = presupuesto.add(new BigDecimal(est2grado.getMasculino().add(est2grado.getFemenimo())).multiply(preGrado2Uti.getPrecioMaxMas()));
                    presupuesto = presupuesto.add(new BigDecimal(est3grado.getMasculino().add(est3grado.getFemenimo())).multiply(preGrado3Uti.getPrecioMaxMas()));
                    presupuesto = presupuesto.add(new BigDecimal(est4grado.getMasculino().add(est4grado.getFemenimo())).multiply(preGrado4Uti.getPrecioMaxMas()));
                    presupuesto = presupuesto.add(new BigDecimal(est5grado.getMasculino().add(est5grado.getFemenimo())).multiply(preGrado5Uti.getPrecioMaxMas()));
                    presupuesto = presupuesto.add(new BigDecimal(est6grado.getMasculino().add(est6grado.getFemenimo())).multiply(preGrado6Uti.getPrecioMaxMas()));

                    presupuesto = presupuesto.add(new BigDecimal(est1media.getMasculino().add(est1media.getFemenimo())).multiply(preBachi1Uti.getPrecioMaxMas()));
                    presupuesto = presupuesto.add(new BigDecimal(est2media.getMasculino().add(est2media.getFemenimo())).multiply(preBachi2Uti.getPrecioMaxMas()));
                    break;
            }

        }

        return presupuesto;
    }

    public void imprimir() {
        try {
            if (entidadEducativa == null || entidadEducativa.getCodigoEntidad() == null) {
                JsfUtil.mensajeAlerta("Debe de ingresar un código de centro escolar válido!");
            } else {
                List<VwRptCertificacionPresupuestaria> lst = new ArrayList();
                HashMap param = new HashMap();
                String reportes = "";

                VwRptCertificacionPresupuestaria vw = entidadEducativaEJB.getCertificacion(codigoEntidad, procesoAdquisicion, (detProAdqUti.getIdProcesoAdq().getIdProcesoAdq() >= 12));
                vw.setUsuarioInsercion(VarSession.getVariableSessionUsuario());
                lst.add(vw);

                if (uniformes) {
                    reportes = "rptCertUni.jasper";
                }
                if (utiles) {
                    if (detProAdqUti.getIdDetProcesoAdq() == 32) {
                        reportes += (reportes.isEmpty() ? "" : ",") + "rptCertUti2018.jasper";
                    } else if (detProAdqUti.getIdDetProcesoAdq() == 42) {
                        reportes += (reportes.isEmpty() ? "" : ",") + "rptCertUti2019.jasper";
                    }
                }
                if (zapatos) {
                    reportes += (reportes.isEmpty() ? "" : ",") + "rptCertZap.jasper";
                }
                Reportes.generarRptsContractuales(lst, param, codigoEntidad, procesoAdquisicion.getDescripcionProcesoAdq().contains("SOBREDEMANDA"), reportesEJB, procesoAdquisicion.getIdAnho().getAnho(), reportes.split(","));
            }
        } catch (Exception e) {
            Logger.getLogger(EstadisticasCensoController.class.getName()).log(Level.INFO, null, "=============================================================");
            Logger.getLogger(EstadisticasCensoController.class.getName()).log(Level.INFO, null, "Error en la impresion de reporte de la certificación presupuestaria");
            Logger.getLogger(EstadisticasCensoController.class.getName()).log(Level.INFO, null, "Código Entidad: " + codigoEntidad);
            Logger.getLogger(EstadisticasCensoController.class.getName()).log(Level.INFO, null, "Rubros uniformes: " + uniformes + " - útiles: " + utiles + " - zapatos: " + zapatos);
            Logger.getLogger(EstadisticasCensoController.class.getName()).log(Level.INFO, null, "Error: " + e.getMessage());
            Logger.getLogger(EstadisticasCensoController.class.getName()).log(Level.INFO, null, "=====================================================");
        }
    }

    public EstadisticaCenso getEst7grado() {
        return est7grado;
    }

    public void setEst7grado(EstadisticaCenso est7grado) {
        this.est7grado = est7grado;
    }

    public EstadisticaCenso getEst8grado() {
        return est8grado;
    }

    public void setEst8grado(EstadisticaCenso est8grado) {
        this.est8grado = est8grado;
    }

    public EstadisticaCenso getEst9grado() {
        return est9grado;
    }

    public void setEst9grado(EstadisticaCenso est9grado) {
        this.est9grado = est9grado;
    }

    public EstadisticaCenso getEst1grado() {
        return est1grado;
    }

    public void setEst1grado(EstadisticaCenso est1grado) {
        this.est1grado = est1grado;
    }

    public EstadisticaCenso getEst2grado() {
        return est2grado;
    }

    public void setEst2grado(EstadisticaCenso est2grado) {
        this.est2grado = est2grado;
    }

    public EstadisticaCenso getEst3grado() {
        return est3grado;
    }

    public void setEst3grado(EstadisticaCenso est3grado) {
        this.est3grado = est3grado;
    }

    public EstadisticaCenso getEst4grado() {
        return est4grado;
    }

    public void setEst4grado(EstadisticaCenso est4grado) {
        this.est4grado = est4grado;
    }

    public EstadisticaCenso getEst5grado() {
        return est5grado;
    }

    public void setEst5grado(EstadisticaCenso est5grado) {
        this.est5grado = est5grado;
    }

    public EstadisticaCenso getEst6grado() {
        return est6grado;
    }

    public void setEst6grado(EstadisticaCenso est6grado) {
        this.est6grado = est6grado;
    }

    public EstadisticaCenso getEst1media() {
        return est1media;
    }

    public void setEst1media(EstadisticaCenso est1media) {
        this.est1media = est1media;
    }

    public EstadisticaCenso getEst2media() {
        return est2media;
    }

    public void setEst2media(EstadisticaCenso est2media) {
        this.est2media = est2media;
    }

    public EstadisticaCenso getEst3media() {
        return est3media;
    }

    public void setEst3media(EstadisticaCenso est3media) {
        this.est3media = est3media;
    }

    public PreciosRefRubro getPreGrado1Uti() {
        return preGrado1Uti;
    }

    public PreciosRefRubro getPreGrado2Uti() {
        return preGrado2Uti;
    }

    public PreciosRefRubro getPreGrado3Uti() {
        return preGrado3Uti;
    }

    public PreciosRefRubro getPreGrado4Uti() {
        return preGrado4Uti;
    }

    public PreciosRefRubro getPreGrado5Uti() {
        return preGrado5Uti;
    }

    public PreciosRefRubro getPreGrado6Uti() {
        return preGrado6Uti;
    }

    public PreciosRefRubro getPreGrado7Uti() {
        return preGrado7Uti;
    }

    public PreciosRefRubro getPreGrado8Uti() {
        return preGrado8Uti;
    }

    public PreciosRefRubro getPreGrado9Uti() {
        return preGrado9Uti;
    }

    public PreciosRefRubro getPreBachi1Uti() {
        return preBachi1Uti;
    }

    public PreciosRefRubro getPreBachi2Uti() {
        return preBachi2Uti;
    }

    public void updateCantidadesCiclo3() {
        getEstaditicaCiclo3().setFemenimo(getEst7grado().getFemenimo().add(getEst8grado().getFemenimo()).add(getEst9grado().getFemenimo()));
        getEstaditicaCiclo3().setMasculino(getEst7grado().getMasculino().add(getEst8grado().getMasculino()).add(getEst9grado().getMasculino()));
    }
}
