package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class TransaccionEMVDepositRequest extends AdqRequest implements Serializable{

    private static final long serialVersionUID = 891915655067780642L;

    private String amount = "";
    private EmvData emvData;
    private ImplicitData implicitData;
    private boolean isEMVTransaction;
    private String noSerie = "";
    private String noTicket = "";
    private String noTransaction = "";
    private SwipeData swipeData;
    private String transactionDateTime = "";
    private String TipoCliente = "";
    private AccountDepositData accountDepositData;

    public String cardHolderName;
    public String maskedPAN;
    public String noAuthorization;
    public String aditionalAmount;


    public TransaccionEMVDepositRequest() {
        emvData = new EmvData();
        implicitData = new ImplicitData();
        swipeData = new SwipeData();
        accountDepositData = new AccountDepositData();

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public EmvData getEmvData() {
        return emvData;
    }

    public void setEmvData(EmvData emvData) {
        this.emvData = emvData;
    }

    public ImplicitData getImplicitData() {
        return implicitData;
    }

    public void setImplicitData(ImplicitData implicitData) {
        this.implicitData = implicitData;
    }

    public boolean getIsEMVTransaction() {
        return isEMVTransaction;
    }

    public void setIsEMVTransaction(boolean isEMVTransaction) {
        this.isEMVTransaction = isEMVTransaction;
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

    public String getNoTransaction() {
        return noTransaction;
    }

    public void setNoTransaction(String noTransaction) {
        this.noTransaction = noTransaction;
    }

    public SwipeData getSwipeData() {
        return swipeData;
    }

    public void setSwipeData(SwipeData swipeData) {
        this.swipeData = swipeData;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getTipoCliente() {
        return TipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        TipoCliente = tipoCliente;
    }

    public AccountDepositData getAccountDepositData() {
        return accountDepositData;
    }

    public void setAccountDepositData(AccountDepositData accountDepositData) {
        this.accountDepositData = accountDepositData;
    }

    public boolean isEMVTransaction() {
        return isEMVTransaction;
    }

    public void setEMVTransaction(boolean EMVTransaction) {
        isEMVTransaction = EMVTransaction;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getMaskedPAN() {
        return maskedPAN;
    }

    public void setMaskedPAN(String maskedPAN) {
        this.maskedPAN = maskedPAN;
    }

    public String getNoAuthorization() {
        return noAuthorization;
    }

    public void setNoAuthorization(String noAuthorization) {
        this.noAuthorization = noAuthorization;
    }

    public String getAditionalAmount() {
        return aditionalAmount;
    }

    public void setAditionalAmount(String aditionalAmount) {
        this.aditionalAmount = aditionalAmount;
    }
}
