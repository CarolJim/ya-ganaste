package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataResultAdq;

import java.io.Serializable;

public class GetResumenDiaResponse implements Serializable {

    private DataResultAdq result;

    private String numeroCobros;

    private String saldo;

    private String ticketPromedio;

    public DataResultAdq getResult() {
        return result;
    }

    public void setResult(DataResultAdq result) {
        this.result = result;
    }

    public String getNumeroCobros() {
        return numeroCobros;
    }

    public void setNumeroCobros(String numeroCobros) {
        this.numeroCobros = numeroCobros;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getTicketPromedio() {
        return ticketPromedio;
    }

    public void setTicketPromedio(String ticketPromedio) {
        this.ticketPromedio = ticketPromedio;
    }

    public GetResumenDiaResponse() {
        result = new DataResultAdq();
    }


}
