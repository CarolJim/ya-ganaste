package com.pagatodo.yaganaste.freja.provisioning.presenter;

import android.content.Context;

import java.util.concurrent.Executors;

import com.pagatodo.yaganaste.freja.provisioning.iteractor.ProvisioningIteractor;
import com.pagatodo.yaganaste.freja.provisioning.iteractor.ProvisioningIteractorImp;
import com.pagatodo.yaganaste.freja.provisioning.manager.ProvisioningManager;

/**
 * @author Juan Guerra on 31/03/2017.
 */

public abstract class ProvisioningPresenterAbs implements ProvisioningPresenter, ProvisioningManager {

    private ProvisioningIteractor provisioningIteractor;

    public ProvisioningPresenterAbs(Context context) {
        this.provisioningIteractor = new ProvisioningIteractorImp(this, Executors.newFixedThreadPool(1));
        this.provisioningIteractor.init(context);
    }

    @Override
    public void getActivationCode() {
        provisioningIteractor.getActivationCode("775363");
    }

    @Override
    public void getPinPolicy() {
        provisioningIteractor.getPinPolicy();
    }

    @Override
    public void registerPin(String pin) {
        provisioningIteractor.verifyProvisioning(pin.getBytes());
    }

    @Override
    public void setTokenNotificationId(String tokenNotificationId, String pin) {
        provisioningIteractor.setTokenNotificationId(tokenNotificationId, pin.getBytes());
    }
}
