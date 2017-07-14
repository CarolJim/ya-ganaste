package com.pagatodo.yaganaste.freja.change.async;

import android.os.AsyncTask;

import com.pagatodo.yaganaste.freja.provisioning.manager.ExceptionCallback;
import com.verisec.freja.mobile.core.FmcManager;

import java.util.Arrays;

/**
 * @author Juan Guerra on 03/04/2017.
 */

public class ChangePinRequest extends AsyncTask<Void, Void, Exception> {

    private FmcManager fmcManager;
    private byte[] oldPin;
    private byte[] newPin;
    private ChangePinCallback changePinCallback;

    public ChangePinRequest(FmcManager fmcManager, ChangePinCallback changePinCallback,
                            byte[] oldPin, byte[] newPin) {
        this.fmcManager = fmcManager;
        this.changePinCallback = changePinCallback;
        this.oldPin = oldPin;
        this.newPin = newPin;
    }

    @Override
    protected Exception doInBackground(Void... params) {
        try {
            fmcManager.getFmcWSHandler().changePin(oldPin, newPin);
            cleanPins();
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    private void cleanPins() {
        Arrays.fill(oldPin, (byte) 0xFF);
        Arrays.fill(newPin, (byte) 0xFF);
    }

    @Override
    protected void onPostExecute(Exception response) {
        if (response == null) {
            this.changePinCallback.onChangeNipSuccessful();
        } else {
            this.changePinCallback.handleException(response);
        }
    }

    public interface ChangePinCallback extends ExceptionCallback {
        void onChangeNipSuccessful();
    }
}