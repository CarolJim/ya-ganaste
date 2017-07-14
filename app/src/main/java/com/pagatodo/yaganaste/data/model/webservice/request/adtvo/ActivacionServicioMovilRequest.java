package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ActivacionServicioMovilRequest implements Serializable {

    private int IdUsuario;
    private int IdCuenta;
    private String NumeroTelefono = "";
    private String TokenValidacion = "";

    public ActivacionServicioMovilRequest() {
    }


    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public int getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        IdCuenta = idCuenta;
    }

    public String getNumeroTelefono() {
        return NumeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        NumeroTelefono = numeroTelefono;
    }

    public String getTokenValidacion() {
        return TokenValidacion;
    }

    public void setTokenValidacion(String tokenValidacion) {
        TokenValidacion = tokenValidacion;
    }
}
