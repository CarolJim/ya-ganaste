package com.pagatodo.yaganaste.freja.change.iteractors;

import android.content.Context;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public interface ChangePinIteractor {

    void init(Context context);

    void init(Context context, long connectionTimeout, long readTimeout);

    void getChangePinPolicy();

    void changePin(byte[] oldPin, byte[] newPin);
}
