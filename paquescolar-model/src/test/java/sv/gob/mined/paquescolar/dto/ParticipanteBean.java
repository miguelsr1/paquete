/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.paquescolar.dto;

/**
 *
 * @author misanchez
 */
public class ParticipanteBean {
    private int id;
    private String razonSocial;

    public ParticipanteBean(int id, String razonSocial) {
        this.id = id;
        this.razonSocial = razonSocial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
}
