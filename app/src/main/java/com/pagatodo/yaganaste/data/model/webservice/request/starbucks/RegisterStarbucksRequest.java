package com.pagatodo.yaganaste.data.model.webservice.request.starbucks;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asandovals on 25/04/2018.
 */

public class RegisterStarbucksRequest implements Serializable {
    @SerializedName("noTarjetaSbx")
    private String noTarjetaSbx;
    @SerializedName("codigoVerificador")
    private String codigoVerificador;
    @SerializedName("udid")
    private String udid;

    public String getNoTarjetaSbx() {
        return noTarjetaSbx;
    }

    public void setNoTarjetaSbx(String noTarjetaSbx) {
        this.noTarjetaSbx = noTarjetaSbx;
    }

    public String getCodigoVerificador() {
        return codigoVerificador;
    }

    public void setCodigoVerificador(String codigoVerificador) {
        this.codigoVerificador = codigoVerificador;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }
}
