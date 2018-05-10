package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import android.media.Image;

import com.pagatodo.yaganaste.ui_wallet.dto.DtoImageCardStrabucks;

import java.io.Serializable;
import java.util.List;

/**
 * Created by asandovals on 20/04/2018.
 */

public class CardStarbucks implements Serializable {

    String alias = "";
    boolean esPrincipal;
    String fechaActivacion = "";
    String numeroTarjeta = "";
    double saldo;
    String statusTarjeta = "";
    String tipoTarjeta = "";
    DtoImageCardStrabucks imagenes;

    public DtoImageCardStrabucks getImagenes() {
        return imagenes;
    }

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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
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
