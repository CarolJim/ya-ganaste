package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CuentaUyUResponse implements Serializable{

    private String CLABE = "";
    private int IdCuenta = 0;
    private String Cuenta = "";
    private ArrayList<TarjetasUyu> Tarjetas = new ArrayList<>();
    @SerializedName("Telefono")
    private String telefono;

    public String getCLABE() {
        return CLABE;
    }

    public void setCLABE(String CLABE) {
        this.CLABE = CLABE;
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

    public ArrayList<TarjetasUyu> getTarjetas() {
        return Tarjetas;
    }

    public void setTarjetas(ArrayList<TarjetasUyu> tarjetas) {
        Tarjetas = tarjetas;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
