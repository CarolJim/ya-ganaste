package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class LoginAdqResponse  implements Serializable {

    private String agente = "";
    private DataResultAdq error;
    private String id_user = "";
    private String token = "";

    public LoginAdqResponse() {
        error = new DataResultAdq();
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public DataResultAdq getError() {
        return error;
    }

    public void setError(DataResultAdq error) {
        this.error = error;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
