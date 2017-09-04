package com.pagatodo.yaganaste.freja.reset.async;

import android.os.AsyncTask;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.provisioning.manager.ExceptionCallback;
import com.verisec.freja.mobile.core.FmcManager;

import java.util.Arrays;

/**
 * @author Juan Guerra on 03/04/2017.
 */

public class ResetPinRequest extends AsyncTask<Void, Void, Exception> {

    private FmcManager fmcManager;
    private byte[] rpcCode;
    private byte[] newPin;
    private ResetPinCallback resetPinCallback;

    public ResetPinRequest(FmcManager fmcManager, ResetPinCallback resetPinCallback,
                           byte[] rpcCode, byte[] newPin) {
        this.fmcManager = fmcManager;
        this.resetPinCallback = resetPinCallback;
        this.rpcCode = rpcCode;
        this.newPin = newPin;
    }

    @Override
    protected Exception doInBackground(Void... params) {
        try {
            fmcManager.getFmcWSHandler().resetPin(rpcCode, newPin);
            //cleanPins();
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    private void cleanPins() {
        Arrays.fill(rpcCode, (byte) 0xFF);
        Arrays.fill(newPin, (byte) 0xFF);
    }

    @Override
    protected void onPostExecute(Exception response) {
        if (response == null) {
            this.resetPinCallback.onResetNipSuccessful();
        } else {
            this.resetPinCallback.onError(Errors.cast(response));
        }
    }

    public interface ResetPinCallback extends ExceptionCallback {
        void onResetNipSuccessful();
    }
}