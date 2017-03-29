package com.pagatodo.yaganaste.data.model.webservice.request.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class AsignarCuentaDisponibleRequest implements Serializable{
    private int IdCuenta;

    public AsignarCuentaDisponibleRequest() {
    }

    public AsignarCuentaDisponibleRequest(int idCuenta) {
        IdCuenta = idCuenta;
    }

    public int getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        IdCuenta = idCuenta;
    }
}
