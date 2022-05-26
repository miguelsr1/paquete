/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.gob.mined.paquescolar.util;

/**
 *
 * @author misanchez
 */
public class Filtro {

    public static final int EQUALS = 1;
    public static final int LIKE = 2;
    
    private int tipoOperacion;
    private String clave;
    private Object valor;

    public Filtro(int tipoOperacion, String clave, Object valor) {
        this.tipoOperacion = tipoOperacion;
        this.clave = clave;
        this.valor = valor;
    }

    public int getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(int tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

}
