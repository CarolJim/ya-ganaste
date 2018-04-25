package com.pagatodo.yaganaste.data.model.webservice.response.starbucks;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asandovals on 20/04/2018.
 */

public class InfoRewardsStarbucks implements Serializable {
    @SerializedName("miembroDesde")
    String miembroDesde="";
    @SerializedName("nivelActual")
    String nivelActual="";
    @SerializedName("numEstrellas")
    int numEstrellas;

    @SerializedName("numEstrellasFaltantes")
    int numEstrellasFaltantes;
    @SerializedName("siguienteNivel")
    String siguienteNivel="";

    public String getMiembroDesde() {
        return miembroDesde;
    }

    public void setMiembroDesde(String miembroDesde) {
        this.miembroDesde = miembroDesde;
    }

    public String getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(String nivelActual) {
        this.nivelActual = nivelActual;
    }

    public int getNumEstrellas() {
        return numEstrellas;
    }

    public void setNumEstrellas(int numEstrellas) {
        this.numEstrellas = numEstrellas;
    }

    public int getNumEstrellasFaltantes() {
        return numEstrellasFaltantes;
    }

    public void setNumEstrellasFaltantes(int numEstrellasFaltantes) {
        this.numEstrellasFaltantes = numEstrellasFaltantes;
    }

    public String getSiguienteNivel() {
        return siguienteNivel;
    }

    public void setSiguienteNivel(String siguienteNivel) {
        this.siguienteNivel = siguienteNivel;
    }
}
