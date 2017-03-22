package com.pagatodo.yaganaste.data.model.webservice.response.trans;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataFondeoCupo implements Serializable {

    private String NumeroAutorizacion = "";
    private Double Saldo;
    private String ClaveRastreo = "";


    public DataFondeoCupo() {
    }


    public String getNumeroAutorizacion() {
        return NumeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        NumeroAutorizacion = numeroAutorizacion;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double saldo) {
        Saldo = saldo;
    }

    public String getClaveRastreo() {
        return ClaveRastreo;
    }

    public void setClaveRastreo(String claveRastreo) {
        ClaveRastreo = claveRastreo;
    }
}
