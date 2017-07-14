package com.pagatodo.yaganaste.data.model.webservice.request.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class AsociarTarjetaCuentaRequest implements Serializable {

    private String Tarjeta = "";

    public AsociarTarjetaCuentaRequest() {
    }

    public String getTarjeta() {
        return Tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        Tarjeta = tarjeta;
    }
}
