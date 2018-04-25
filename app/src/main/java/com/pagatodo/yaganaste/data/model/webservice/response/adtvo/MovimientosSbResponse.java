package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovimientosSbResponse implements Serializable {

    private String mes = "";
    private List<TransaccionesSbResponse> transacciones;

    public MovimientosSbResponse(){
        this.transacciones = new ArrayList<>();
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public List<TransaccionesSbResponse> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<TransaccionesSbResponse> transacciones) {
        this.transacciones = transacciones;
    }
}
