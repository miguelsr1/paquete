/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DesarrolloPc
 */
public class VwDepartamentoModificativas {
    private String nombreDepartamento;
    private List<VwDetalleModificativas> lstDetalleModificativas = new ArrayList();

    public VwDepartamentoModificativas() {
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public List<VwDetalleModificativas> getLstDetalleModificativas() {
        return lstDetalleModificativas;
    }

    public void setLstDetalleModificativas(List<VwDetalleModificativas> lstDetalleModificativas) {
        this.lstDetalleModificativas = lstDetalleModificativas;
    }
}
