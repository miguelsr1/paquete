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
public class CredencialJwtDto {
    private String username;
    private String password;
    private String address;
    private Integer tokenExpirationMinutes;
    private Integer[] categoriasOperacion;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTokenExpirationMinutes() {
        return tokenExpirationMinutes;
    }

    public void setTokenExpirationMinutes(Integer tokenExpirationMinutes) {
        this.tokenExpirationMinutes = tokenExpirationMinutes;
    }

    public Integer[] getCategoriasOperacion() {
        return categoriasOperacion;
    }

    public void setCategoriasOperacion(Integer[] categoriasOperacion) {
        this.categoriasOperacion = categoriasOperacion;
    }
}
