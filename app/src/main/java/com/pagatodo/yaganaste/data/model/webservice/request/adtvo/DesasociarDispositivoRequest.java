package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 09/06/2017.
 */

public class DesasociarDispositivoRequest extends Request implements Serializable {

    private String IdComponente;
    private String NombreUsuario;
    private String IdDispositivo;
    private String TokenSesion;
    private String ContentType;
    private String IdCuenta;

    public String getIdComponente() {
        return IdComponente;
    }

    public void setIdComponente(String idComponente) {
        IdComponente = idComponente;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getIdDispositivo() {
        return IdDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        IdDispositivo = idDispositivo;
    }

    public String getTokenSesion() {
        return TokenSesion;
    }

    public void setTokenSesion(String tokenSesion) {
        TokenSesion = tokenSesion;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    public String getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        IdCuenta = idCuenta;
    }

    /*  IdComponente	RequestHeaders.getComponent()
    NombreUsuario	RequestHeaders.getUsername()
    IdDispositivo	RequestHeaders.getDevice()
    TokenSesion	RequestHeaders.getTokensesion()
    Content-Type	application/json
    IdCuenta	RequestHeaders.getIdCuenta()*/
}
