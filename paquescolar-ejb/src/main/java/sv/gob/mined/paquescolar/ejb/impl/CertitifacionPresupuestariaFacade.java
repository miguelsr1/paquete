/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.mined.paquescolar.ejb.impl;

import javax.ejb.Stateless;
import sv.gob.mined.paquescolar.ejb.AbstractFacade;
import sv.gob.mined.paquescolar.model.pojos.VwRptCertificacionPresupuestaria;

/**
 *
 * @author misanchez
 */
@Stateless
public class CertitifacionPresupuestariaFacade extends AbstractFacade<VwRptCertificacionPresupuestaria, String> {

    public CertitifacionPresupuestariaFacade() {
        super(VwRptCertificacionPresupuestaria.class);
    }

}
