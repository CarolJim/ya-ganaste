package com.pagatodo.yaganaste.freja.reset.async;

import android.os.AsyncTask;

import com.pagatodo.yaganaste.freja.general.callbacks.PinPolicyCallback;
import com.verisec.freja.mobile.core.FmcManager;
import com.verisec.freja.mobile.core.wsHandler.beans.general.response.FmcPinPolicy;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public class ResetPinPolicyRequest extends AsyncTask<Void, Void, Object> {

    private FmcManager fmcManager;
    private PinPolicyCallback pinPolicyCallback;

    public ResetPinPolicyRequest(FmcManager fmcManager, PinPolicyCallback pinPolicyCallback) {
        this.fmcManager = fmcManager;
        this.pinPolicyCallback = pinPolicyCallback;
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            return fmcManager.getFmcWSHandler().getResetPinPinPolicy();
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object response) {
        if (response instanceof Exception){
            this.pinPolicyCallback.handleException((Exception) response);
        } else if (response instanceof FmcPinPolicy) {
            FmcPinPolicy pinPolicy = (FmcPinPolicy) response;
            this.pinPolicyCallback.onPinPolicyReceived(pinPolicy.getMin(), pinPolicy.getMax());
        }
    }
}