package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

public class AgentesRespose implements Serializable{
    private String ClaveAgente = "";
    private Boolean EsAgenteRechazado;
    private int EstatusAgente;
    private String NombreNegocio = "";
    private String NumeroAgente = "";
    private String PetroNumero = "";
    private int TipoAgente;

    public String getClaveAgente() {
        return ClaveAgente;
    }

    public void setClaveAgente(String claveAgente) {
        ClaveAgente = claveAgente;
    }

    public Boolean getEsAgenteRechazado() {
        return EsAgenteRechazado;
    }

    public void setEsAgenteRechazado(Boolean esAgenteRechazado) {
        EsAgenteRechazado = esAgenteRechazado;
    }

    public int getEstatusAgente() {
        return EstatusAgente;
    }

    public void setEstatusAgente(int estatusAgente) {
        EstatusAgente = estatusAgente;
    }

    public String getNombreNegocio() {
        return NombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        NombreNegocio = nombreNegocio;
    }

    public String getNumeroAgente() {
        return NumeroAgente;
    }

    public void setNumeroAgente(String numeroAgente) {
        NumeroAgente = numeroAgente;
    }

    public String getPetroNumero() {
        return PetroNumero;
    }

    public void setPetroNumero(String petroNumero) {
        PetroNumero = petroNumero;
    }

    public int getTipoAgente() {
        return TipoAgente;
    }

    public void setTipoAgente(int tipoAgente) {
        TipoAgente = tipoAgente;
    }

    /*
    "ClaveAgente": "",
                    "EsAgenteRechazado": false,
                    "EstatusAgente": 2,
                    "EstatusDocumentacion": 2,
                    "NombreNegocio": "",
                    "NumeroAgente": "",
                    "PetroNumero": "",
                    "TipoAgente": 1
     */
}
