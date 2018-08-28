/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author oamartinez
 */
public class BeanProveedores {

    private String nombreProveedor;
    private List<Bean> listadoDatos = new LinkedList<Bean>();

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public List<Bean> getListadoDatos() {
        return listadoDatos;
    }

    public void setListadoDatos(List<Bean> listadoDatos) {
        this.listadoDatos = listadoDatos;
    }
}
