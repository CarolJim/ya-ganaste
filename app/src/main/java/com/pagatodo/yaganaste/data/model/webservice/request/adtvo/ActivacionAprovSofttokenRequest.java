package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ActivacionAprovSofttokenRequest implements Serializable{

    private String codigoActivacion = "";

    public ActivacionAprovSofttokenRequest() {
    }

    public ActivacionAprovSofttokenRequest(String codigoActivacion) {
        this.codigoActivacion = codigoActivacion;
    }

    public String getCodigoActivacion() {
        return codigoActivacion;
    }

    public void setCodigoActivacion(String codigoActivacion) {
        this.codigoActivacion = codigoActivacion;
    }
}
