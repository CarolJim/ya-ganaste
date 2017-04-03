package com.pagatodo.yaganaste.data.model;

/**
 * Created by flima on 22/03/2017.
 */

public final class ExtraInfoUser {

    private boolean needSetPin = false;
    private String mail = "";
    private String phone = "";
    private String pass = "";
    private String saldo = "";
    private String saldoAdq = "";
    private String clabe = "";

    /*Estatus de usuario/agente*/
    private int statusDocuments;
    private int statusAgent;
    private int tipoAgente;

    public ExtraInfoUser() {
    }

    public ExtraInfoUser(boolean needSetPin, int statusDocuments, int statusAgent) {
        this.needSetPin = needSetPin;
        this.statusDocuments = statusDocuments;
        this.statusAgent = statusAgent;
    }

    public boolean isNeedSetPin() {
        return needSetPin;
    }

    public void setNeedSetPin(boolean needSetPin) {
        this.needSetPin = needSetPin;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getSaldoAdq() {
        return saldoAdq;
    }

    public void setSaldoAdq(String saldoAdq) {
        this.saldoAdq = saldoAdq;
    }

    public String getClabe() {
        return clabe;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public int getStatusDocuments() {
        return statusDocuments;
    }

    public void setStatusDocuments(int statusDocuments) {
        this.statusDocuments = statusDocuments;
    }

    public int getStatusAgent() {
        return statusAgent;
    }

    public void setStatusAgent(int statusAgent) {
        this.statusAgent = statusAgent;
    }

    public int getTipoAgente() {
        return tipoAgente;
    }

    public void setTipoAgente(int tipoAgente) {
        this.tipoAgente = tipoAgente;
    }
}
