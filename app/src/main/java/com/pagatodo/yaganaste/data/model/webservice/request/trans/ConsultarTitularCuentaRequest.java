package com.pagatodo.yaganaste.data.model.webservice.request.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ConsultarTitularCuentaRequest implements Serializable{

    private String Cuenta = "";

    public ConsultarTitularCuentaRequest() {
    }

    public String getCuenta() {
        return Cuenta;
    }

    public void setCuenta(String cuenta) {
        Cuenta = cuenta;
    }
}
