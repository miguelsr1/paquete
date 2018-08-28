/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.model.pojos;

/**
 *
 * @author misanchez
 */
public class Descriptor {

    private String atributo;
    private String valor;

    public Descriptor() {
    }

    public Descriptor(int atributo, String valor) {
        this.atributo = String.valueOf(atributo);
        this.valor = valor;
    }

    public Descriptor(String atributo, String valor) {
        this.atributo = atributo;
        this.valor = valor;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Descriptor{" + "atributo=" + atributo + ", valor=" + valor + '}';
    }
}
