package com.pagatodo.yaganaste.data.model.webservice.response;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

public class ObtenerInfoComercioResponse extends GenericResponse {

    private InformacionComercio Data;

    public InformacionComercio getData() {
        return Data;
    }

    public void setData(InformacionComercio data) {
        Data = data;
    }

    public DataObtenerDomicilio getDomicilioNegocio() {
        return Data.DomicilioNegocio;
    }

    public void setDomicilioNegocio(DataObtenerDomicilio domicilioNegocio) {
        Data.DomicilioNegocio = domicilioNegocio;
    }

    public int getGiro() {
        return Data.Giro;
    }

    public void setGiro(int giro) {
        Data.Giro = giro;
    }

    public int getSubGiro() {
        return Data.SubGiro;
    }

    public void setSubGiro(int subGiro) {
        Data.SubGiro = subGiro;
    }

    public String getNombreNegocio() {
        return Data.NombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        Data.NombreNegocio = nombreNegocio;
    }

    public String getNumeroTelefono() {
        return Data.NumeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        Data.NumeroTelefono = numeroTelefono;
    }

    class InformacionComercio {
        DataObtenerDomicilio DomicilioNegocio;
        int Giro, SubGiro;
        String NombreNegocio, NumeroTelefono;
    }
}
