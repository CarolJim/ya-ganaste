package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jordan on 16/06/2017.
 */

public class CancelaTransaccionDepositoEmvRequest extends AdqRequest implements Serializable {

    private String noSerie;
    private String noTicket;
    private String amount;
    private SwipeData swipeData;
    private boolean isEMVTransaction;
    @SerializedName("CancellationData")
    private CancellationData cancellationData;
    private String transactionDateTime;
    private EmvData emvData;
    @SerializedName("TipoCliente")
    private String tipoCliente;
    private String noTransaction;
    private AccountDepositData accountDepositData;
    private ImplicitData implicitData;
    @SerializedName("TipoCancelacion")
    private int tipoCancelación;

    public int getTipoCancelación() {
        return tipoCancelación;
    }

    public void setTipoCancelación(int tipoCancelación) {
        this.tipoCancelación = tipoCancelación;
    }

    public String getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
    }

    public String getNoTicket() {
        return noTicket;
    }

    public void setNoTicket(String noTicket) {
        this.noTicket = noTicket;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public SwipeData getSwipeData() {
        return swipeData;
    }

    public void setSwipeData(SwipeData swipeData) {
        this.swipeData = swipeData;
    }

    public boolean isEMVTransaction() {
        return isEMVTransaction;
    }

    public void setEMVTransaction(boolean EMVTransaction) {
        isEMVTransaction = EMVTransaction;
    }

    public CancellationData getCancellationData() {
        return cancellationData;
    }

    public void setCancellationData(CancellationData cancellationData) {
        this.cancellationData = cancellationData;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public EmvData getEmvData() {
        return emvData;
    }

    public void setEmvData(EmvData emvData) {
        this.emvData = emvData;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getNoTransaction() {
        return noTransaction;
    }

    public void setNoTransaction(String noTransaction) {
        this.noTransaction = noTransaction;
    }

    public AccountDepositData getAccountDepositData() {
        return accountDepositData;
    }

    public void setAccountDepositData(AccountDepositData accountDepositData) {
        this.accountDepositData = accountDepositData;
    }

    public ImplicitData getImplicitData() {
        return implicitData;
    }

    public void setImplicitData(ImplicitData implicitData) {
        this.implicitData = implicitData;
    }
}
