package com.pagatodo.yaganaste.data.model;

/**
 * Created by flima on 20/04/2017.
 */

public class DatosSaldo {

    private String saldoEmisor = "";
    private String saldoAdq = "";
    private String lineaCredito = "";
    private String saldoCredito = "";
    private String montoDeposito = "";

    public DatosSaldo() {
    }

    public DatosSaldo(String saldoEmisor) {
        this.saldoEmisor = saldoEmisor;
    }

    public String getSaldoEmisor() {
        return saldoEmisor;
    }

    public void setSaldoEmisor(String saldoEmisor) {
        this.saldoEmisor = saldoEmisor;
    }

    public String getSaldoAdq() {
        return saldoAdq;
    }

    public void setSaldoAdq(String saldoAdq) {
        this.saldoAdq = saldoAdq;
    }

    public String getLineaCredito() {
        return lineaCredito;
    }

    public void setLineaCredito(String lineaCredito) {
        this.lineaCredito = lineaCredito;
    }

    public String getSaldoCredito() {
        return saldoCredito;
    }

    public void setSaldoCredito(String saldoCredito) {
        this.saldoCredito = saldoCredito;
    }

    public String getMontoDeposito() {
        return montoDeposito;
    }

    public void setMontoDeposito(String montoDeposito) {
        this.montoDeposito = montoDeposito;
    }
}
