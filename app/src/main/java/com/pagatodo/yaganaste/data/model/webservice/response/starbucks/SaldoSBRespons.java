package com.pagatodo.yaganaste.data.model.webservice.response.starbucks;

import java.io.Serializable;

public class SaldoSBRespons implements Serializable {

    RespuestaStarbucks respuesta;

    String Saldo = "";

    public String getSaldo() {
        return Saldo;
    }

    public void setSaldo(String saldo) {
        this.Saldo = saldo;
    }

    public RespuestaStarbucks getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaStarbucks respuesta) {
        this.respuesta = respuesta;
    }
}
