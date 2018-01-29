package com.pagatodo.yaganaste.ui_wallet.interfaces;

/**
 * Created by icruz on 26/12/2017.
 */

public interface WlletNotifaction {
    void onFailed(int errorCode, int action, String error);
    void onSuccess(boolean error);
    void onSuccessADQ(String responds);
}
