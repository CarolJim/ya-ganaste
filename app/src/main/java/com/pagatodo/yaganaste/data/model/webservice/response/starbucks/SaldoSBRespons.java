package com.pagatodo.yaganaste.data.model.webservice.response.starbucks;

import java.io.Serializable;

public class SaldoSBRespons implements Serializable {

    RespuestaStarbucks respuesta;

    String saldo = "";

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public RespuestaStarbucks getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaStarbucks respuesta) {
        this.respuesta = respuesta;
    }
}
