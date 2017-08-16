package com.pagatodo.yaganaste.freja.provisioning.asynk;

import android.os.AsyncTask;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.general.callbacks.PinPolicyCallback;
import com.verisec.freja.mobile.core.FmcManager;
import com.verisec.freja.mobile.core.wsHandler.beans.general.response.FmcPinPolicy;
import com.verisec.freja.mobile.core.wsHandler.beans.general.response.FmcPollingResponse;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public class PinPolicyRequest extends AsyncTask<Void, Void, Object> {

    private FmcManager fmcManager;
    private PinPolicyCallback pinPolicyCallback;

    public PinPolicyRequest(FmcManager fmcManager, PinPolicyCallback pinPolicyCallback) {
        this.fmcManager = fmcManager;
        this.pinPolicyCallback = pinPolicyCallback;
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            Object pinPolicyObject = null;
            for (int current = 0; current < 10; current++) {
                pinPolicyObject = fmcManager.getFmcWSHandler().getProvisioningPinPolicy();
                if (pinPolicyObject instanceof FmcPollingResponse &&
                        (((FmcPollingResponse) pinPolicyObject).getTimeout() > 0)) {
                    Thread.sleep(((FmcPollingResponse) pinPolicyObject).getTimeout());
                } else {
                    break;
                }
            }
            return pinPolicyObject;
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
        } else if (response instanceof FmcPollingResponse) {
            this.pinPolicyCallback.onError(Errors.NO_PIN_POLICY);
        }
    }
}