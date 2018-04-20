package com.pagatodo.yaganaste.data.model.webservice.request.starbucks;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asandovals on 19/04/2018.
 */

public class DispositivoStartBucks implements Serializable {

    @SerializedName("udid")
    private String udid;
    @SerializedName("idTokenNotificacion")
    private String idTokenNotificacion;
    @SerializedName("latitud")
    private String latitud;
    @SerializedName("longitud")
    private String longitud;

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getIdTokenNotificacion() {
        return idTokenNotificacion;
    }

    public void setIdTokenNotificacion(String idTokenNotificacion) {
        this.idTokenNotificacion = idTokenNotificacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
