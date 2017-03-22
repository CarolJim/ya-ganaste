package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataTransaccion implements Serializable {

    private String NumeroAutorizacion = "";
    private Double Saldo;
    private String ClaveRastreo = "";
    private Double Comision;
    private String IdTransaccion = "";

    public DataTransaccion() {
    }

    public Double getSaldo() {
        return Saldo;
    }


    public void setSaldo(Double saldo) {
        Saldo = saldo;
    }
}
