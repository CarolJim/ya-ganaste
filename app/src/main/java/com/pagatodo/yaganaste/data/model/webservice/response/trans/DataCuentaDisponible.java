package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CuentaResponse;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataCuentaDisponible implements Serializable {

    private CuentaResponse Cuenta;

    public DataCuentaDisponible() {
        Cuenta = new CuentaResponse();
    }

    public CuentaResponse getCuenta() {
        return Cuenta;
    }

    public void setCuenta(CuentaResponse cuenta) {
        Cuenta = cuenta;
    }
}
