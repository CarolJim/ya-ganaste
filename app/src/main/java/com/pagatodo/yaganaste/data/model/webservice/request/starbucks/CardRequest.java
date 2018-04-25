package com.pagatodo.yaganaste.data.model.webservice.request.starbucks;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CardRequest implements Serializable {

    @SerializedName("numeroTarjeta")
    String numeroTarjeta;

    public CardRequest(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
}
