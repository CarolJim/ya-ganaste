package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataBloquearTarjeta implements Serializable {

    private String NumeroAutorizacion = "";

    public DataBloquearTarjeta() {
    }

    public String getNumeroAutorizacion() {
        return NumeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        NumeroAutorizacion = numeroAutorizacion;
    }
}
