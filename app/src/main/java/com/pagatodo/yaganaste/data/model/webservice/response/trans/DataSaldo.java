package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataSaldo implements Serializable {

    private Double Saldo;

    public DataSaldo() {
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double saldo) {
        Saldo = saldo;
    }
}
