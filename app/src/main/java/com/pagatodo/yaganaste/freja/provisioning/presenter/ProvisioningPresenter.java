package com.pagatodo.yaganaste.freja.provisioning.presenter;

/**
 * @author Juan Guerra on 31/03/2017.
 */

public interface ProvisioningPresenter {
    void getActivationCode();

    void getPinPolicy();

    void registerPin(String pin);

    void setTokenNotificationId(String tokenNotificationId, String pin);
}
