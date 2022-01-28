/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.ws.siges.api.dto;

/**
 *
 * @author misanchez
 */
public class ConsultarEntidadDto {

    private int maxResults;
    private String[] incluirCampos;
    private String sedPk;

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public String[] getIncluirCampos() {
        return incluirCampos;
    }

    public void setIncluirCampos(String[] incluirCampos) {
        this.incluirCampos = incluirCampos;
    }

    public String getSedPk() {
        return sedPk;
    }

    public void setSedPk(String sedPk) {
        this.sedPk = sedPk;
    }
}
