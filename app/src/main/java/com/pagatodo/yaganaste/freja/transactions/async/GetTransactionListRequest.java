package com.pagatodo.yaganaste.freja.transactions.async;

import android.os.AsyncTask;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.provisioning.manager.ExceptionCallback;
import com.verisec.freja.mobile.core.FmcManager;
import com.verisec.freja.mobile.core.wsHandler.beans.general.response.FmcPollingResponse;
import com.verisec.freja.mobile.core.wsHandler.beans.transaction.response.fromV1_5.FmcTransactionListResponse;

/**
 * @author Juan Guerra on 03/04/2017.
 */

public class GetTransactionListRequest extends AsyncTask<Void, Void, Object> {

    private FmcManager fmcManager;
    private GetTransactionsCallback getTransactionsCallback;

    public GetTransactionListRequest(FmcManager fmcManager,
                                     GetTransactionsCallback getTransactionsCallback) {
        this.fmcManager = fmcManager;
        this.getTransactionsCallback = getTransactionsCallback;
    }

    public interface GetTransactionsCallback extends ExceptionCallback {
        void onGetTransactions(FmcTransactionListResponse listResponse);
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            return fmcManager.getFmcWSHandler().getTransactionList();
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object response) {
        if (response instanceof FmcTransactionListResponse) {
            getTransactionsCallback.onGetTransactions((FmcTransactionListResponse) response);
        } else if (response instanceof FmcPollingResponse) {
            getTransactionsCallback.onError(Errors.NO_PENDING_TRANSACTIONS);
        } else {
            getTransactionsCallback.handleException((Exception) response);
        }
    }
}