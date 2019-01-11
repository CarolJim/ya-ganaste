package com.pagatodo.yaganaste.modules.emisor.GeneratePIN;

import java.io.Serializable;

public class CardRequest implements Serializable {
    private String NumeroTarjeta;

    public CardRequest(String numeroTarjeta) {
        NumeroTarjeta = numeroTarjeta;
    }

    public CardRequest() {
    }

    public String getNumeroTarjeta() {
        return NumeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        NumeroTarjeta = numeroTarjeta;
    }
}
