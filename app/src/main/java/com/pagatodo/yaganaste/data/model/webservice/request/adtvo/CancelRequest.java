package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CancelRequest implements Serializable {


    @SerializedName("EstatusAfiliacion")
    private int EstatusAfiliacion;
    @SerializedName("IdUsuarioAdquirente")
    private String IdUsuarioAdquirente;

    public CancelRequest(int estatusAfiliacion, String idAgente) {
        EstatusAfiliacion = estatusAfiliacion;
        IdUsuarioAdquirente = idAgente;
    }


    public int getEstatusAfiliacion() {
        return EstatusAfiliacion;
    }

    public void setEstatusAfiliacion(int estatusAfiliacion) {
        EstatusAfiliacion = estatusAfiliacion;
    }

    public String getIdAgente() {
        return IdUsuarioAdquirente;
    }

    public void setIdAgente(String idAgente) {
        IdUsuarioAdquirente = idAgente;
    }
}
