package com.pagatodo.yaganaste.freja.general.callbacks;

import com.pagatodo.yaganaste.freja.provisioning.manager.ExceptionCallback;

/**
 * @author Juan Guerra on 03/04/2017.
 */

public interface PinPolicyCallback extends ExceptionCallback {
    void onPinPolicyReceived(int minLength, int maxLength);
}