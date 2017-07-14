package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ConsultaSaldoCupoResponse implements Serializable {

    private String agente = "";
    private DataResultAdq result;
    private String saldo = "";


    public ConsultaSaldoCupoResponse() {
        result = new DataResultAdq();
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public DataResultAdq getResult() {
        return result;
    }

    public void setResult(DataResultAdq result) {
        this.result = result;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
