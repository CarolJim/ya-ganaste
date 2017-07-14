package com.pagatodo.yaganaste.ui.onlinetx.presenters;

import android.content.Context;
import android.util.Log;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.transactions.presenter.TransactionPresenterAbs;
import com.pagatodo.yaganaste.ui.onlinetx.controllers.OnlineTxView;
import com.verisec.freja.mobile.core.wsHandler.beans.transaction.response.FmcTransactionResponse;
import com.verisec.freja.mobile.core.wsHandler.beans.transaction.response.fromV1_5.FmcTransactionListResponse;

import java.util.List;

/**
 * @author Juan Guerra on 18/04/2017.
 */

public class OnlineTxPresenter extends TransactionPresenterAbs {

    private static final String TAG = OnlineTxPresenter.class.getName();

    private String idFreja;
    private OnlineTxView onlineTxView;
    private Context context;

    public OnlineTxPresenter(Context context, OnlineTxView onlineTxView, String idFreja) {
        super(context);
        this.context = context;
        this.idFreja = idFreja;
        this.onlineTxView = onlineTxView;
    }


    @Override
    public void getTransactions() {
        onlineTxView.showLoader(context.getString(R.string.verifying_transaction));
        super.getTransactions();
    }

    @Override
    public void aproveTransaction(String idFreja, String nip) {
        onlineTxView.showLoader(context.getString(R.string.aproving_transaction));
        super.aproveTransaction(idFreja, nip);
    }

    @Override
    public void setTransactions(FmcTransactionListResponse txs) {
        onlineTxView.hideLoader();
        List<FmcTransactionResponse> transactions = txs.getTransactions();
        for (FmcTransactionResponse transaction : transactions) {
            if (transaction.getTransactionReference().equals(idFreja)) {
                onlineTxView.loadTransactionData();
                return;
            }
        }
        onError(Errors.NO_PENDING_TRANSACTIONS);
    }

    @Override
    public void onTransactionAproved() {
        onlineTxView.hideLoader();
        onlineTxView.onTxAproved();
    }


    @Override
    public void onError(Errors error) {
        onlineTxView.hideLoader();
        onlineTxView.showError(error);
    }

    @Override
    public void handleException(Exception e) {
        onlineTxView.hideLoader();
        Log.e(TAG, e.toString());
    }
}