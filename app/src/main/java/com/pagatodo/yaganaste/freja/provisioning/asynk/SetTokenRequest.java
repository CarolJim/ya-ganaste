package com.pagatodo.yaganaste.freja.provisioning.asynk;

import android.os.AsyncTask;

import com.pagatodo.yaganaste.freja.provisioning.manager.ExceptionCallback;
import com.verisec.freja.mobile.core.FmcManager;

import java.util.Arrays;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public class SetTokenRequest extends AsyncTask<byte[], Void, Exception> {

    private FmcManager fmcManager;
    private SetTokenCallback setTokenCallback;
    private String token;

    public SetTokenRequest(FmcManager fmcManager, SetTokenCallback setTokenCallback, String token) {
        this.fmcManager = fmcManager;
        this.setTokenCallback = setTokenCallback;
        this.token = token;
    }

    @Override
    protected Exception doInBackground(byte[]... params) {
        try {
            fmcManager.getFmcWSHandler().setTokenNotificationId(token, params[0]);
            Arrays.fill(params[0], (byte) 0XFF);
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Exception response) {
        if (response == null) {
            this.setTokenCallback.onSetTokenSuccess();
        } else {
            this.setTokenCallback.handleException(response);
        }
    }

    public interface SetTokenCallback extends ExceptionCallback {
        void onSetTokenSuccess();
    }
}