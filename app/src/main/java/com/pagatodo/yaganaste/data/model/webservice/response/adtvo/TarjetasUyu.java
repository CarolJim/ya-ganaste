package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

public class TarjetasUyu implements Serializable{
    private Boolean AsignoNip;
    private String Numero = "";

    public Boolean getAsignoNip() {
        return AsignoNip;
    }

    public void setAsignoNip(Boolean asignoNip) {
        AsignoNip = asignoNip;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }
}
