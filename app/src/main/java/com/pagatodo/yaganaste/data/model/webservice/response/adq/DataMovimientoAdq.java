package com.pagatodo.yaganaste.data.model.webservice.response.adq;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataMovimientoAdq implements Serializable {

    private String BancoEmisor, Comision, ComisionIva, Concepto, Estatus,
            Fecha, FechaTransaccionOriginal, ID, IdTransaction, MarcaTarjetaBancaria, Monto,
            NoAutorizacion, NoSecUnicoPT, NoTicket, Nombre, Operacion, Referencia, TipoReembolso,
            TipoTransaccion, TransactionIdentity;
    private boolean EsReversada, EsClosedLoop;

    public DataMovimientoAdq() {
    }

    public void setBancoEmisor(String bancoEmisor) {
        BancoEmisor = bancoEmisor;
    }

    public void setComision(String comision) {
        Comision = comision;
    }

    public void setComisionIva(String comisionIva) {
        ComisionIva = comisionIva;
    }

    public void setConcepto(String concepto) {
        Concepto = concepto;
    }

    public void setEstatus(String estatus) {
        Estatus = estatus;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public void setFechaTransaccionOriginal(String fechaTransaccionOriginal) {
        FechaTransaccionOriginal = fechaTransaccionOriginal;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setIdTransaction(String idTransaction) {
        IdTransaction = idTransaction;
    }

    public void setMarcaTarjetaBancaria(String marcaTarjetaBancaria) {
        MarcaTarjetaBancaria = marcaTarjetaBancaria;
    }

    public void setMonto(String monto) {
        Monto = monto;
    }

    public void setNoAutorizacion(String noAutorizacion) {
        NoAutorizacion = noAutorizacion;
    }

    public void setNoSecUnicoPT(String noSecUnicoPT) {
        NoSecUnicoPT = noSecUnicoPT;
    }

    public void setNoTicket(String noTicket) {
        NoTicket = noTicket;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setOperacion(String operacion) {
        Operacion = operacion;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public void setTipoReembolso(String tipoReembolso) {
        TipoReembolso = tipoReembolso;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        TipoTransaccion = tipoTransaccion;
    }

    public void setTransactionIdentity(String transactionIdentity) {
        TransactionIdentity = transactionIdentity;
    }

    public void setEsClosedLoop(boolean esClosedLoop) {
        EsClosedLoop = esClosedLoop;
    }

    public String getBancoEmisor() {
        return BancoEmisor;
    }

    public void setEsReversada(boolean esReversada) {
        EsReversada = esReversada;
    }

    public String getComision() {
        return Comision;
    }

    public String getComisionIva() {
        return ComisionIva;
    }

    public String getConcepto() {
        return Concepto;
    }

    public boolean isEsClosedLoop() {
        return EsClosedLoop;
    }

    public boolean isEsReversada() {
        return EsReversada;
    }

    public String getEstatus() {
        return Estatus;
    }

    public String getFecha() {
        return Fecha;
    }

    public String getFechaTransaccionOriginal() {
        return FechaTransaccionOriginal;
    }

    public String getID() {
        return ID;
    }

    public String getIdTransaction() {
        return IdTransaction;
    }

    public String getMarcaTarjetaBancaria() {
        return MarcaTarjetaBancaria;
    }

    public String getMonto() {
        return Monto;
    }

    public String getNoAutorizacion() {
        return NoAutorizacion;
    }

    public String getNoSecUnicoPT() {
        return NoSecUnicoPT;
    }

    public String getNoTicket() {
        return NoTicket;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getOperacion() {
        return Operacion;
    }

    public String getReferencia() {
        return Referencia;
    }

    public String getTipoReembolso() {
        return TipoReembolso;
    }

    public String getTipoTransaccion() {
        return TipoTransaccion;
    }

    public String getTransactionIdentity() {
        return TransactionIdentity;
    }
}
