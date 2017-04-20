package com.pagatodo.yaganaste.freja.transactions.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.freja.transactions.iteractor.TransactionsIteractor;
import com.pagatodo.yaganaste.freja.transactions.iteractor.TransactionsIteractorImp;
import com.pagatodo.yaganaste.freja.transactions.manager.TransactionManager;

import java.util.concurrent.Executors;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public abstract class TransactionPresenterAbs implements TransactionsPresenter, TransactionManager {

    private TransactionsIteractor transactionsIteractor;

    public TransactionPresenterAbs(Context context) {
        this.transactionsIteractor = new TransactionsIteractorImp(this, Executors.newFixedThreadPool(1));
        this.transactionsIteractor.init(context);
    }


    @Override
    public void getTransactions() {
        transactionsIteractor.getTransactions();
    }

    @Override
    public void aproveTransaction(String idFreja, String nip) {
        transactionsIteractor.aproveTransaction(idFreja, nip.getBytes());
    }

    @Override
    public void onTransactionCanceled() {
        //NO-OP
    }


}