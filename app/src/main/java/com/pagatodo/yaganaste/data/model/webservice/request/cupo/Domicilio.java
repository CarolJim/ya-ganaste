package com.pagatodo.yaganaste.data.model.webservice.request.cupo;

/**
 * Created by Tato on 09/08/17.
 */

public class Domicilio {

    private String Calle = "";
    private String NumeroExterior = "";
    private String NumeroInterior = "";
    private String CP = "";
    private String IdEstado = "";
    private String IdColonia = "";

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        NumeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        NumeroInterior = numeroInterior;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(String idEstado) {
        IdEstado = idEstado;
    }

    public String getIdColonia() {
        return IdColonia;
    }

    public void setIdColonia(String idColonia) {
        IdColonia = idColonia;
    }
}
