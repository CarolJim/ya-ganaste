package com.pagatodo.yaganaste.freja.provisioning.asynk;

import android.os.AsyncTask;

import com.pagatodo.yaganaste.freja.provisioning.manager.ExceptionCallback;
import com.verisec.freja.mobile.core.FmcManager;

import java.util.Arrays;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public class VerifyPinRequest extends AsyncTask<byte[], Void, Exception> {

    private FmcManager fmcManager;
    private VerifyPinCallback verifyPinCallback;

    public VerifyPinRequest(FmcManager fmcManager, VerifyPinCallback verifyPinCallback) {
        this.fmcManager = fmcManager;
        this.verifyPinCallback = verifyPinCallback;
    }

    @Override
    protected Exception doInBackground(byte[]... params) {
        try {
            fmcManager.getFmcWSHandler().verifyProvisioning(params[0]);
            Arrays.fill(params[0], (byte) 0XFF);
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Exception response) {
        if (response == null) {
            this.verifyPinCallback.onVerifyPinSuccessful();
        } else {
            this.verifyPinCallback.handleException(response);
        }
    }

    public interface VerifyPinCallback extends ExceptionCallback {
        void onVerifyPinSuccessful();
    }
}