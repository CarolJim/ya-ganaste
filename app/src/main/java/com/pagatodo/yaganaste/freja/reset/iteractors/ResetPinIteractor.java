package com.pagatodo.yaganaste.freja.reset.iteractors;

import android.content.Context;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public interface ResetPinIteractor {

    void init(Context context);

    void init(Context context, long connectionTimeout, long readTimeout);

    void getResetPinPolicy();

    void resetPin(byte[] rpcCode, byte[] newPin);
}
