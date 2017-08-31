package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class DatosCuentaResponse implements Serializable {

    private String AliasCuenta = "";
    private String BIN7 = "";
    private String CLABE = "";
    private String Cuenta = "";
    private String Descripcion = "";
    private int IdCuenta;
    private String Marca = "";
    private String Modelo = "";
    private Double Saldo;
    private String SistemaOperativo = "";
    private String Tarjeta = "";
    private String Telefono = "";
    private String UltimaTransaccion = "";
    private String UltimoInicioSesion = "";

    public String getAliasCuenta() {
        return AliasCuenta;
    }

    public void setAliasCuenta(String aliasCuenta) {
        AliasCuenta = aliasCuenta;
    }

    public String getBIN7() {
        return BIN7;
    }

    public void setBIN7(String BIN7) {
        this.BIN7 = BIN7;
    }

    public String getCLABE() {
        return CLABE;
    }

    public void setCLABE(String CLABE) {
        this.CLABE = CLABE;
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

    public int getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        IdCuenta = idCuenta;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double saldo) {
        Saldo = saldo;
    }

    public String getSistemaOperativo() {
        return SistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        SistemaOperativo = sistemaOperativo;
    }

    public String getTarjeta() {
        return Tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        Tarjeta = tarjeta;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getUltimaTransaccion() {
        return UltimaTransaccion;
    }

    public void setUltimaTransaccion(String ultimaTransaccion) {
        UltimaTransaccion = ultimaTransaccion;
    }

    public String getUltimoInicioSesion() {
        return UltimoInicioSesion;
    }

    public void setUltimoInicioSesion(String ultimoInicioSesion) {
        UltimoInicioSesion = ultimoInicioSesion;
    }
}

