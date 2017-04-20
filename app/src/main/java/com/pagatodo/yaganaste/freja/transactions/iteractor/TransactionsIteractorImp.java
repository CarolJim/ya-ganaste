package com.pagatodo.yaganaste.freja.transactions.iteractor;

import android.content.Context;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.general.FmcIteractorImp;
import com.pagatodo.yaganaste.freja.transactions.async.AproveTransactionRequest;
import com.pagatodo.yaganaste.freja.transactions.async.GetTransactionListRequest;
import com.pagatodo.yaganaste.freja.transactions.manager.TransactionManager;
import com.verisec.freja.mobile.core.wsHandler.beans.transaction.response.fromV1_5.FmcTransactionListResponse;

import java.util.concurrent.Executor;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public class TransactionsIteractorImp extends FmcIteractorImp implements TransactionsIteractor, GetTransactionListRequest.GetTransactionsCallback, AproveTransactionRequest.AproveTxCallback {

    private TransactionManager transactionManager;
    private Executor mExecutor;

    public TransactionsIteractorImp(TransactionManager transactionManager, Executor mExecutor) {
        this.transactionManager = transactionManager;
        this.mExecutor = mExecutor;
    }

    @Override
    public void throwInitException(Exception e) {
        transactionManager.handleException(e);
    }

    @Override
    public void getTransactions() {
        GetTransactionListRequest asyncTask = new GetTransactionListRequest(fmcManager, this);
        if (mExecutor == null){
            asyncTask.execute();
        } else {
            asyncTask.executeOnExecutor(mExecutor);
        }
    }

    @Override
    public void onGetTransactions(FmcTransactionListResponse listResponse) {
        transactionManager.setTransactions(listResponse);
    }

    @Override
    public void aproveTransaction(String idFreja, byte[] nip) {
        AproveTransactionRequest asyncTask = new AproveTransactionRequest(fmcManager, this, idFreja, nip);
        if (mExecutor == null){
            asyncTask.execute();
        } else {
            asyncTask.executeOnExecutor(mExecutor);
        }
    }

    @Override
    public void onTxAproved() {
        transactionManager.onTransactionAproved();
    }

    @Override
    public void handleException(Exception e) {
        transactionManager.handleException(e);
    }

    @Override
    public void onError(Errors error) {
        transactionManager.onError(error);
    }
}
