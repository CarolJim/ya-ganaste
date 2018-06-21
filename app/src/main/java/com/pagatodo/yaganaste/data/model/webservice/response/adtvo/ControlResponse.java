package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

public class ControlResponse implements Serializable{
    private Boolean EsAgente;
    private Boolean EsCliente;
    private Boolean EsUsuario;
    private Boolean RequiereActivacionSMS;

    public Boolean getEsAgente() {
        return EsAgente;
    }

    public void setEsAgente(Boolean esAgente) {
        EsAgente = esAgente;
    }

    public Boolean getEsCliente() {
        return EsCliente;
    }

    public void setEsCliente(Boolean esCliente) {
        EsCliente = esCliente;
    }

    public Boolean getEsUsuario() {
        return EsUsuario;
    }

    public void setEsUsuario(Boolean esUsuario) {
        EsUsuario = esUsuario;
    }

    public Boolean getRequiereActivacionSMS() {
        return RequiereActivacionSMS;
    }

    public void setRequiereActivacionSMS(Boolean requiereActivacionSMS) {
        RequiereActivacionSMS = requiereActivacionSMS;
    }

    /*
    "EsAgente": true,
            "EsCliente": true,
            "EsUsuario": true,
            "RequiereActivacionSMS": false
     */
}
