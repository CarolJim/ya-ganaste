package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;


public class DataObtenerNumeroSMS implements Serializable {

    private String NumeroTelefono = "";

    public DataObtenerNumeroSMS() {
    }

    public String getNumeroTelefono() {
        return NumeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        NumeroTelefono = numeroTelefono;
    }
}
