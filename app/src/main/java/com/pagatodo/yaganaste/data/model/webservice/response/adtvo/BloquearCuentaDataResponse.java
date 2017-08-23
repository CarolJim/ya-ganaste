package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 21/08/2017.
 */

public class BloquearCuentaDataResponse extends GenericResponse implements Serializable {

    String NumeroAutorizacion = "";

    public BloquearCuentaDataResponse() {
    }

    public String getNumeroAutorizacion() {
        return NumeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        NumeroAutorizacion = numeroAutorizacion;
    }
}
