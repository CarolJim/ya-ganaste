package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by asandovals on 20/04/2018.
 */

public class Tarjetas implements Serializable {

    String alias ="";
    boolean esPrincipal;
    String fechaActivacion= "";
    String numeroTarjeta ="";
    int saldo;
    Tarjetas tarjetas;
    String statusTarjeta ="";
    String tipoTarjeta="";

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

    public String getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(String fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Tarjetas getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(Tarjetas tarjetas) {
        this.tarjetas = tarjetas;
    }

    public String getStatusTarjeta() {
        return statusTarjeta;
    }

    public void setStatusTarjeta(String statusTarjeta) {
        this.statusTarjeta = statusTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }
}
