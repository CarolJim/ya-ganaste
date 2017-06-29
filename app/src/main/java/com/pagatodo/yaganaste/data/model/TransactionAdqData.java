package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.model.webservice.response.adq.TransaccionEMVDepositResponse;

import java.io.Serializable;

import static com.pagatodo.yaganaste.utils.Recursos.CARD_PAY;

/**
 * Created by flima on 17/04/2017.
 */

public  class TransactionAdqData implements Serializable{

    private int statusTransaction;
    private int responseCode;
    private PageResult pageResult;
    private TransaccionEMVDepositResponse transaccionResponse;
    private String amount = "";
    private String description = "";

    private static TransactionAdqData transactionAdqResult;

    private TransactionAdqData() {
        transaccionResponse = new TransaccionEMVDepositResponse();
    }

    public synchronized static TransactionAdqData getCurrentTransaction(){

        if(transactionAdqResult == null){
            transactionAdqResult = new TransactionAdqData();
        }

        return transactionAdqResult;

    }

    public static void resetCurrentTransaction(){
        transactionAdqResult = null;
    }

    public static void resetDataToRetry(){
        transactionAdqResult.statusTransaction = 0;
        transactionAdqResult.responseCode = 0;
        transactionAdqResult.transaccionResponse = new TransaccionEMVDepositResponse();
        transactionAdqResult.pageResult = null;
    }

    public int getStatusTransaction() {
        return statusTransaction;
    }

    public void setStatusTransaction(int statusTransaction) {
        this.statusTransaction = statusTransaction;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public PageResult getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult pageResult) {
        this.pageResult = pageResult;
    }

    public TransaccionEMVDepositResponse getTransaccionResponse() {
        return transaccionResponse;
    }

    public void setTransaccionResponse(TransaccionEMVDepositResponse transaccionResponse) {
        this.transaccionResponse = transaccionResponse;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


