package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataActivacion implements Serializable{

    private String NumeroTelefono = "";
    private String TokenAutenticacion = "";

    public DataActivacion() {

    }

    public String getNumeroTelefono() {
        return NumeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        NumeroTelefono = numeroTelefono;
    }

    public String getTokenAutenticacion() {
        return TokenAutenticacion;
    }

    public void setTokenAutenticacion(String tokenAutenticacion) {
        TokenAutenticacion = tokenAutenticacion;
    }
}
