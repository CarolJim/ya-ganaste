package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class CuentaResponse implements Serializable {

    private int IdCuenta;
    private String Cuenta = "";
    private String Descripcion = "";
    private String CLABE = "";
    private String Tarjeta = "";
    private String BIN7 = "";
    private double Saldo;
    private List<MovimientosResponse> ListaMovimientos;
    private List<FavoritosResponse> ListaFavoritos;


    public CuentaResponse() {

        ListaMovimientos = new ArrayList<MovimientosResponse>();
        ListaFavoritos = new ArrayList<FavoritosResponse>();
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

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
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

    public String getBIN7() {
        return BIN7;
    }

    public void setBIN7(String BIN7) {
        this.BIN7 = BIN7;
    }

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double saldo) {
        Saldo = saldo;
    }

    public List<MovimientosResponse> getListaMovimientos() {
        return ListaMovimientos;
    }

    public void setListaMovimientos(List<MovimientosResponse> listaMovimientos) {
        ListaMovimientos = listaMovimientos;
    }

    public List<FavoritosResponse> getListaFavoritos() {
        return ListaFavoritos;
    }

    public void setListaFavoritos(List<FavoritosResponse> listaFavoritos) {
        ListaFavoritos = listaFavoritos;
    }
}

