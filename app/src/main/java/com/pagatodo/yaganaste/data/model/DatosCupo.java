package com.pagatodo.yaganaste.data.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jordan on 23/08/2017.
 */

public class DatosCupo implements Serializable {


    @SerializedName("LimiteDeCredito")
    private String limiteDeCredito;

    @SerializedName("SaldoDisponible")
    private String saldoDisponible;

    @SerializedName("TotalADepositar")
    private String totalADepositar;

    @SerializedName("TotalAReembolsar")
    private String totalAReembolsar;

    public DatosCupo() {

    }

    public DatosCupo(String limiteCredito, String saldoDisponible, String totalDepositar, String totalAReembolsar) {
        this.limiteDeCredito = limiteCredito;
        this.saldoDisponible = saldoDisponible;
        this.totalADepositar = totalDepositar;
        this.totalAReembolsar = totalAReembolsar;
    }

    public String getLimiteDeCredito() {
        return TextUtils.isEmpty(limiteDeCredito) ? "0" : limiteDeCredito;
    }

    public void setLimiteDeCredito(String limiteDeCredito) {
        this.limiteDeCredito = limiteDeCredito;
    }

    public String getSaldoDisponible() {
        return TextUtils.isEmpty(saldoDisponible) ? "0" : saldoDisponible;
    }

    public void setSaldoDisponible(String saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public String getTotalADepositar() {
        return TextUtils.isEmpty(totalADepositar) ? "0" : totalADepositar;
    }

    public void setTotalADepositar(String totalADepositar) {
        this.totalADepositar = totalADepositar;
    }

    public String getTotalAReembolsar() {
        return totalAReembolsar;
    }

    public void setTotalAReembolsar(String totalAReembolsar) {
        this.totalAReembolsar = totalAReembolsar;
    }
}
