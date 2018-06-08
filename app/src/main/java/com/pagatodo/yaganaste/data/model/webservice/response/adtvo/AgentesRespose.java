package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;

public class AgentesRespose implements Serializable{
    private Boolean EsAgenteRechazado;
    private Boolean EsComercioUYU;
    private int EstatusAgente;
    private int EstatuDocumentacion;
    private String NombreNegocio = "";
    private String NumeroAgente = "";
    private ArrayList<OperadoresResponse> Operadores = new ArrayList<>();

    public Boolean getEsAgenteRechazado() {
        return EsAgenteRechazado;
    }

    public void setEsAgenteRechazado(Boolean esAgenteRechazado) {
        EsAgenteRechazado = esAgenteRechazado;
    }

    public Boolean getEsComercioUYU() {
        return EsComercioUYU;
    }

    public void setEsComercioUYU(Boolean esComercioUYU) {
        EsComercioUYU = esComercioUYU;
    }

    public int getEstatusAgente() {
        return EstatusAgente;
    }

    public void setEstatusAgente(int estatusAgente) {
        EstatusAgente = estatusAgente;
    }

    public int getEstatuDocumentacion() {
        return EstatuDocumentacion;
    }

    public void setEstatuDocumentacion(int estatuDocumentacion) {
        EstatuDocumentacion = estatuDocumentacion;
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

    public ArrayList<OperadoresResponse> getOperadores() {
        return Operadores;
    }

    public void setOperadores(ArrayList<OperadoresResponse> operadores) {
        Operadores = operadores;
    }
}
