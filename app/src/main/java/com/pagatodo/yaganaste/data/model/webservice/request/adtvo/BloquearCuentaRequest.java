package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 21/08/2017.
 * Request para enviar el cambio de estado en bloqueo de cuenta
 */

public class BloquearCuentaRequest implements Serializable {

    private String TipoOperacion = "";

    public BloquearCuentaRequest(String operation) {
        this.TipoOperacion = operation;
    }
}
