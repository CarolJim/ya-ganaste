package com.pagatodo.yaganaste.data.model.webservice.request.trans;

import java.io.Serializable;

public class ConsultaAsignacionTarjetaRequest implements Serializable {
    private String NumeroTarjeta = "";

    public ConsultaAsignacionTarjetaRequest() {
    }

    public ConsultaAsignacionTarjetaRequest(String numeroTarjeta) {
        NumeroTarjeta = numeroTarjeta.replace(" ", "");
    }

    public String getNumeroTarjeta() {
        return NumeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        NumeroTarjeta = numeroTarjeta;
    }
}
