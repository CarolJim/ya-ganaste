package com.pagatodo.yaganaste.freja.provisioning.asynk;

import android.os.AsyncTask;
import android.util.Log;

import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.provisioning.manager.ExceptionCallback;
import com.verisec.freja.mobile.core.FmcManager;

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
        if (response instanceof Exception) {
            this.activationCodeCallback.onError(Errors.cast((Exception) response));
        } else {
            this.activationCodeCallback.onActivationCodeReceived(response.toString());
        }
    }

    public interface ActivationCodeCallback extends ExceptionCallback {
        void onActivationCodeReceived(String activationCode);
    }
}
