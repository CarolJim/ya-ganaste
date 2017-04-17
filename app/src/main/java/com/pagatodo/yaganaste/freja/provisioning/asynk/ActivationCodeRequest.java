package com.pagatodo.yaganaste.freja.provisioning.asynk;

import android.os.AsyncTask;
import com.verisec.freja.mobile.core.FmcManager;

import com.pagatodo.yaganaste.freja.provisioning.manager.ExceptionCallback;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public class ActivationCodeRequest extends AsyncTask<Void, Void, Object> {

    private String clientCode;
    private FmcManager fmcManager;
    private ActivationCodeCallback activationCodeCallback;

    public ActivationCodeRequest(FmcManager fmcManager, String clientCode, ActivationCodeCallback activationCodeCallback) {
        this.clientCode = clientCode;
        this.fmcManager = fmcManager;
        this.activationCodeCallback = activationCodeCallback;
    }

    public interface ActivationCodeCallback extends ExceptionCallback {
        void onActivationCodeReceived(String activationCode);
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            return fmcManager.getFmcWSHandler().getActivationCode(clientCode);
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object response) {
        if (response instanceof Exception){
            this.activationCodeCallback.handleException((Exception) response);
        }
        this.activationCodeCallback.onActivationCodeReceived(response.toString());
    }
}