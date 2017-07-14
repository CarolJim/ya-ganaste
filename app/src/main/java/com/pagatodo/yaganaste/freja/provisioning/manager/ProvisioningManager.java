package com.pagatodo.yaganaste.freja.provisioning.manager;

import com.pagatodo.yaganaste.freja.general.ErrorFmcManager;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public interface ProvisioningManager extends ErrorFmcManager {

    void setActivationCode(String activationCode);

    void setPinPolicy(int min, int max);

    void endProvisioning();

    void endTokenNotification();
}
