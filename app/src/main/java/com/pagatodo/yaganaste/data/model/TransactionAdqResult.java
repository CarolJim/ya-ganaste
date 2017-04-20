package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.model.webservice.response.adq.TransaccionEMVDepositResponse;
import com.pagatodo.yaganaste.interfaces.Command;

import java.io.Serializable;

/**
 * Created by flima on 17/04/2017.
 */

public  class TransactionAdqResult  implements Serializable{

    private int statusTransaction;
    private int responseCode;
    private PageResult pageResult;
    private TransaccionEMVDepositResponse transaccionResponse;

    private static TransactionAdqResult transactionAdqResult;

    private TransactionAdqResult() {
    }

    public synchronized static TransactionAdqResult getCurrentTransaction(){

        if(transactionAdqResult == null){
            transactionAdqResult = new TransactionAdqResult();
        }

        return transactionAdqResult;

    }

    public static void resetCurrentTransaction(){
        transactionAdqResult = null;
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
}


