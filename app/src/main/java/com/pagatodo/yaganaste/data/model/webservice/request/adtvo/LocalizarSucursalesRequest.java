package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class LocalizarSucursalesRequest implements Serializable {

    @SerializedName("latitud")
    private Double Latitud;
    @SerializedName("longitud")
    private Double Longitud;

    public LocalizarSucursalesRequest() {
    }

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }

}
