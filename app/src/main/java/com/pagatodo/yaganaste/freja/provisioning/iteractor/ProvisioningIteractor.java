package com.pagatodo.yaganaste.freja.provisioning.iteractor;

import com.pagatodo.yaganaste.freja.general.FmcIteractor;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public interface ProvisioningIteractor extends FmcIteractor{

    void getActivationCode(String clientCode);
    void getPinPolicy();
    void verifyProvisioning(byte[] pin);
    void setTokenNotificationId(String tokenNotificationId, byte[] nip);
}
