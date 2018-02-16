package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;

import java.io.Serializable;

/**
 * Created by FranciscoManzo on 13/02/2018.
 */

public class ListaNotificationRequest extends Request implements Serializable {
    private int IdUsuario;
    private int IdMovimiento;

    /**
     * "IdUsuario": 4053,
     * "IdMovimiento": 0
     */
    public ListaNotificationRequest(int idUsuario, int idMovimiento) {
        IdUsuario = idUsuario;
        IdMovimiento = idMovimiento;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public int getIdMovimiento() {
        return IdMovimiento;
    }
}
