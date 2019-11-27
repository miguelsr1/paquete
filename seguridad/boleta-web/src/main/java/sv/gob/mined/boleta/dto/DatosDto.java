/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.dto;

import java.io.Serializable;

/**
 *
 * @author misanchez
 */
public class DatosDto implements Serializable {

    private String codigo;
    private String correoElectronico;

    public DatosDto() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Override
    public String toString() {
        return "DatosDto{" + "codigo=" + codigo + ", correoElectronico=" + correoElectronico + '}';
    }
}
