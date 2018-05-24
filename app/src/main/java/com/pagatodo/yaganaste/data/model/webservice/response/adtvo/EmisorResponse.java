package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;

public class EmisorResponse implements Serializable{

    private ArrayList<CuentaUyUResponse> Cuentas;

    public EmisorResponse(){
        Cuentas = new ArrayList<>();
    }

    public ArrayList<CuentaUyUResponse> getCuentas() {
        return Cuentas;
    }

    public void setCuentas(ArrayList<CuentaUyUResponse> cuentas) {
        Cuentas = cuentas;
    }
}
