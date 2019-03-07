package com.pagatodo.yaganaste.freja.provisioning.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.freja.provisioning.iteractor.ProvisioningIteractor;
import com.pagatodo.yaganaste.freja.provisioning.iteractor.ProvisioningIteractorImp;
import com.pagatodo.yaganaste.freja.provisioning.manager.ProvisioningManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.TabPresenterImpl;

import java.util.concurrent.Executors;

import static com.pagatodo.yaganaste.utils.Recursos.PT_CLIENT_CODE;

/**
 *
 */

public abstract class ProvisioningPresenterAbs extends TabPresenterImpl implements ProvisioningPresenter, ProvisioningManager {

    public static final String EVENT_APROV_SUCCES = "EVENT_APROV_SUCCES";
    public static final String EVENT_APROV_FAILED = "EVENT_APROV_FAILED";

    private ProvisioningIteractor provisioningIteractor;

    public ProvisioningPresenterAbs(Context context) {
        this.provisioningIteractor = new ProvisioningIteractorImp(this, Executors.newFixedThreadPool(1));
        this.provisioningIteractor.init(context);
    }

    @Override
    public void getActivationCode() {
        provisioningIteractor.getActivationCode(PT_CLIENT_CODE);
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
