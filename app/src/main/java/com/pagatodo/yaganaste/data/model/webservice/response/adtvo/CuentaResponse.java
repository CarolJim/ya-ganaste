package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class CuentaResponse implements Serializable {

    private int IdCuenta=0;
    private boolean AsignoNip; // TODO validar, ya que no se encuentra en la documentacion
    private String Cuenta = "";
    private String CLABE = "";
    private String Tarjeta = "";
    @SerializedName("Telefono")
    private String telefono;

    public CuentaResponse() {
    }

    public int getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        IdCuenta = idCuenta;
    }

    public String getCuenta() {
        return Cuenta;
    }

    public void setCuenta(String cuenta) {
        Cuenta = cuenta;
    }

    public String getCLABE() {
        return CLABE;
    }

    public void setCLABE(String CLABE) {
        this.CLABE = CLABE;
    }

    public String getTarjeta() {
        return Tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        Tarjeta = tarjeta;
    }

    public boolean isAsignoNip() {
        return AsignoNip;
    }

    public void setAsignoNip(boolean asignoNip) {
        AsignoNip = asignoNip;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

