package com.pagatodo.yaganaste.freja.transactions.async;

import android.os.AsyncTask;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.provisioning.manager.ExceptionCallback;
import com.verisec.freja.mobile.core.FmcManager;
import com.verisec.freja.mobile.core.wsHandler.beans.general.response.FmcPollingResponse;
import com.verisec.freja.mobile.core.wsHandler.beans.transaction.response.fromV1_5.FmcTransactionListResponse;

import java.util.Arrays;

/**
 * @author Juan Guerra on 03/04/2017.
 */

public class AproveTransactionRequest extends AsyncTask<Void, Void, Exception> {

    private FmcManager fmcManager;
    private AproveTxCallback aproveTxCallback;
    private byte[] pin;
    private String idFreja;

    public AproveTransactionRequest(FmcManager fmcManager,
                                    AproveTxCallback aproveTxCallback,
                                    String idFreja, byte[] pin) {
        this.fmcManager = fmcManager;
        this.aproveTxCallback = aproveTxCallback;
        this.idFreja = idFreja;
        this.pin = pin;
    }

    public interface AproveTxCallback extends ExceptionCallback {
        void onTxAproved();
    }

    @Override
    protected Exception doInBackground(Void... params) {
        try {
            fmcManager.getFmcWSHandler().approveTransactionByReference(pin, idFreja);
            Arrays.fill(pin, (byte)0xFF);
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Exception response) {
        if (response == null){
            this.aproveTxCallback.onTxAproved();
        } else {
            this.aproveTxCallback.handleException(response);
        }
    }
}