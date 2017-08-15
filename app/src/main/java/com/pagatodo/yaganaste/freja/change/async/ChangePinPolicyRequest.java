package com.pagatodo.yaganaste.freja.change.async;

import android.os.AsyncTask;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.general.callbacks.PinPolicyCallback;
import com.verisec.freja.mobile.core.FmcManager;
import com.verisec.freja.mobile.core.wsHandler.beans.general.response.FmcPinPolicy;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public class ChangePinPolicyRequest extends AsyncTask<Void, Void, Object> {

    private FmcManager fmcManager;
    private PinPolicyCallback pinPolicyCallback;

    public ChangePinPolicyRequest(FmcManager fmcManager, PinPolicyCallback pinPolicyCallback) {
        this.fmcManager = fmcManager;
        this.pinPolicyCallback = pinPolicyCallback;
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            return fmcManager.getFmcWSHandler().getChangePinPinPolicy();
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object response) {
        if (response instanceof Exception) {
            this.pinPolicyCallback.onError(Errors.cast((Exception) response));
        } else if (response instanceof FmcPinPolicy) {
            FmcPinPolicy pinPolicy = (FmcPinPolicy) response;
            this.pinPolicyCallback.onPinPolicyReceived(pinPolicy.getMin(), pinPolicy.getMax());
        }
    }
}