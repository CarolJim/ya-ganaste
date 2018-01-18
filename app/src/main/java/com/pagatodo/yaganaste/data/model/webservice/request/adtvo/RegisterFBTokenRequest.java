package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 21/03/2017.
 */

public class RegisterFBTokenRequest extends Request implements Serializable {

    private int IdUsuario = 0;
    private String TokenFirebase = "";

    public RegisterFBTokenRequest() {
    }

    public RegisterFBTokenRequest(int idUsuario, String tokenFirebase) {
        this.IdUsuario = idUsuario;
        this.TokenFirebase = tokenFirebase;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public String getTokenFirebase() {
        return TokenFirebase;
    }
}
