package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 21/08/2017.
 */

public class EstatusCuentaRequest implements Serializable {
    private String Tarjeta = "";

    public EstatusCuentaRequest(String mTDC) {
        setTarjeta(mTDC);
    }

    public String getTarjeta() {
        return Tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        Tarjeta = tarjeta;
    }
}
