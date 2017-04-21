package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class TransaccionEMVDepositResponse  implements Serializable {

    private String ARQC = "";
    private String Afiliacion = "";
    private String BancoEmisor = "";
    private String MarcaTarjetaBancaria = "";
    private String RazonSocialAgente = "";
    private String autorizacion = "";
    private String cupon = "";
    private DataResultAdq error;
    private String id_transaction = "";
    private boolean isSignRequired;
    private String maskedPan = "";
    private String name = "";
    private String saldo = "";
    private String tipoTarjeta = "";
    private String tipoTarjetaBanco = "";
    private String tlvResult = "";

    private String amount = "";

    public TransaccionEMVDepositResponse() {
        error = new DataResultAdq();
    }

    public String getARQC() {
        return ARQC;
    }

    public void setARQC(String ARQC) {
        this.ARQC = ARQC;
    }

    public String getAfiliacion() {
        return Afiliacion;
    }

    public void setAfiliacion(String afiliacion) {
        Afiliacion = afiliacion;
    }

    public String getBancoEmisor() {
        return BancoEmisor;
    }

    public void setBancoEmisor(String bancoEmisor) {
        BancoEmisor = bancoEmisor;
    }

    public String getMarcaTarjetaBancaria() {
        return MarcaTarjetaBancaria;
    }

    public void setMarcaTarjetaBancaria(String marcaTarjetaBancaria) {
        MarcaTarjetaBancaria = marcaTarjetaBancaria;
    }

    public String getRazonSocialAgente() {
        return RazonSocialAgente;
    }

    public void setRazonSocialAgente(String razonSocialAgente) {
        RazonSocialAgente = razonSocialAgente;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getCupon() {
        return cupon;
    }

    public void setCupon(String cupon) {
        this.cupon = cupon;
    }

    public DataResultAdq getError() {
        return error;
    }

    public void setError(DataResultAdq error) {
        this.error = error;
    }

    public String getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(String id_transaction) {
        this.id_transaction = id_transaction;
    }

    public boolean isSignRequired() {
        return isSignRequired;
    }

    public void setSignRequired(boolean signRequired) {
        isSignRequired = signRequired;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(String maskedPan) {
        this.maskedPan = maskedPan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getTipoTarjetaBanco() {
        return tipoTarjetaBanco;
    }

    public void setTipoTarjetaBanco(String tipoTarjetaBanco) {
        this.tipoTarjetaBanco = tipoTarjetaBanco;
    }

    public String getTlvResult() {
        return tlvResult;
    }

    public void setTlvResult(String tlvResult) {
        this.tlvResult = tlvResult;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

