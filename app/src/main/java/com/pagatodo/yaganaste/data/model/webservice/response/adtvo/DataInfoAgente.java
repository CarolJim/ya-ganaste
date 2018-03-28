package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

public class DataInfoAgente implements Serializable {

    boolean EsAgente, EsAgenteRechazado;
    int EstatusAgente, EstatusDocumentacion, IdEstatus;
    String IdUsuarioAdquirente, NombreNegocio, TipoAgente, TokenSesionAdquirente;

    public boolean isEsAgente() {
        return EsAgente;
    }

    public void setEsAgente(boolean esAgente) {
        EsAgente = esAgente;
    }

    public boolean isEsAgenteRechazado() {
        return EsAgenteRechazado;
    }

    public void setEsAgenteRechazado(boolean esAgenteRechazado) {
        EsAgenteRechazado = esAgenteRechazado;
    }

    public int getEstatusAgente() {
        return EstatusAgente;
    }

    public void setEstatusAgente(int estatusAgente) {
        EstatusAgente = estatusAgente;
    }

    public int getEstatusDocumentacion() {
        return EstatusDocumentacion;
    }

    public void setEstatusDocumentacion(int estatusDocumentacion) {
        EstatusDocumentacion = estatusDocumentacion;
    }

    public int getIdEstatus() {
        return IdEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        IdEstatus = idEstatus;
    }

    public String getIdUsuarioAdquirente() {
        return IdUsuarioAdquirente;
    }

    public void setIdUsuarioAdquirente(String idUsuarioAdquirente) {
        IdUsuarioAdquirente = idUsuarioAdquirente;
    }

    public String getNombreNegocio() {
        return NombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        NombreNegocio = nombreNegocio;
    }

    public String getTipoAgente() {
        return TipoAgente;
    }

    public void setTipoAgente(String tipoAgente) {
        TipoAgente = tipoAgente;
    }

    public String getTokenSesionAdquirente() {
        return TokenSesionAdquirente;
    }

    public void setTokenSesionAdquirente(String tokenSesionAdquirente) {
        TokenSesionAdquirente = tokenSesionAdquirente;
    }
}
