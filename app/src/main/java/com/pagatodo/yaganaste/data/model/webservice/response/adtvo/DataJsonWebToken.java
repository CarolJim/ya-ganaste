package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataJsonWebToken implements Serializable {

    private String JsonWebToken = "";

    public DataJsonWebToken() {
    }

    public String getJsonWebToken() {
        return JsonWebToken;
    }

    public void setJsonWebToken(String jsonWebToken) {
        JsonWebToken = jsonWebToken;
    }
}
