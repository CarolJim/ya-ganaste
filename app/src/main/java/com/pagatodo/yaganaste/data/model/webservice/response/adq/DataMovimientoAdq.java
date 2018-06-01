package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataMovimientoAdq implements Serializable {

    private String fecha;
    private String idTransaction;
    private String monto;
    private String noAutorizacion;
    private String noTicket;
    private String nombre;
    private String operacion;
    private String referencia;
    @SerializedName("BancoEmisor")
    private String bancoEmisor;
    @SerializedName("MarcaTarjetaBancaria")
    private String marcaTarjetaBancaria;
    @SerializedName("MontoAdqComision")
    private String montoAdqComision;
    @SerializedName("MontoAdqComisionIva")
    private String montoAdqComisionIva;
    private boolean esReversada;
    @SerializedName("TipoTrans")
    private String tipoTrans;
    @SerializedName("Estatus")
    private String estatus;
    @SerializedName("Concepto")
    private String concepto;
    @SerializedName("FechaOriginalTransaction")
    private String fechaOriginalTransaction;
    @SerializedName("idTipoReembolso")
    private String idTipoRembolso;
    @SerializedName("isClosedLoop")
    private boolean isClosedLoop;

    public DataMovimientoAdq() {
    }

    public String getFechaOriginalTransaction() {
        return fechaOriginalTransaction;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getNoAutorizacion() {
        return noAutorizacion;
    }

    public void setNoAutorizacion(String noAutorizacion) {
        this.noAutorizacion = noAutorizacion;
    }

    public String getNoTicket() {
        return noTicket;
    }

    public void setNoTicket(String noTicket) {
        this.noTicket = noTicket;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getBancoEmisor() {
        return bancoEmisor;
    }

    public void setBancoEmisor(String bancoEmisor) {
        this.bancoEmisor = bancoEmisor;
    }

    public String getMarcaTarjetaBancaria() {
        return marcaTarjetaBancaria;
    }

    public void setMarcaTarjetaBancaria(String marcaTarjetaBancaria) {
        this.marcaTarjetaBancaria = marcaTarjetaBancaria;
    }

    public String getMontoAdqComision() {
        return montoAdqComision;
    }

    public void setMontoAdqComision(String montoAdqComision) {
        this.montoAdqComision = montoAdqComision;
    }

    public String getMontoAdqComisionIva() {
        return montoAdqComisionIva;
    }

    public void setMontoAdqComisionIva(String montoAdqComisionIva) {
        this.montoAdqComisionIva = montoAdqComisionIva;
    }

    public boolean isEsReversada() {
        return esReversada;
    }

    public void setEsReversada(boolean esReversada) {
        this.esReversada = esReversada;
    }

    public String getTipoTrans() {
        return tipoTrans;
    }

    public void setTipoTrans(String tipoTrans) {
        this.tipoTrans = tipoTrans;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getIdTipoRembolso() {
        return idTipoRembolso;
    }

    public void setIdTipoRembolso(String idTipoRembolso) {
        this.idTipoRembolso = idTipoRembolso;
    }

    public boolean isClosedLoop() {
        return isClosedLoop;
    }

    public void setClosedLoop(boolean closedLoop) {
        isClosedLoop = closedLoop;
    }
}
