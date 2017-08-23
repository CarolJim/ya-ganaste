package com.pagatodo.yaganaste.freja.provisioning.iteractor;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.general.FmcIteractorImp;
import com.pagatodo.yaganaste.freja.general.callbacks.PinPolicyCallback;
import com.pagatodo.yaganaste.freja.provisioning.asynk.ActivationCodeRequest;
import com.pagatodo.yaganaste.freja.provisioning.asynk.PinPolicyRequest;
import com.pagatodo.yaganaste.freja.provisioning.asynk.SetTokenRequest;
import com.pagatodo.yaganaste.freja.provisioning.asynk.VerifyPinRequest;
import com.pagatodo.yaganaste.freja.provisioning.manager.ProvisioningManager;

import java.util.concurrent.Executor;

/**
 * @author Juan Guerra on 30/03/2017.
 * @version 1.0
 */

public class ProvisioningIteractorImp extends FmcIteractorImp implements ProvisioningIteractor, ActivationCodeRequest.ActivationCodeCallback, PinPolicyCallback, VerifyPinRequest.VerifyPinCallback, SetTokenRequest.SetTokenCallback {

    private ProvisioningManager provisioningManager;
    private Executor mExecutor;

    public ProvisioningIteractorImp(ProvisioningManager provisioningManager, Executor mExecutor) {
        this.provisioningManager = provisioningManager;
        this.mExecutor = mExecutor;
    }

    @Override
    public void getActivationCode(String clientCode) {
        ActivationCodeRequest asyncTask = new ActivationCodeRequest(fmcManager, clientCode, this);
        if (mExecutor == null) {
            asyncTask.execute();
        } else {
            asyncTask.executeOnExecutor(mExecutor);
        }
    }

    @Override
    public void onActivationCodeReceived(String activationCode) {
        provisioningManager.setActivationCode(activationCode);
    }

    @Override
    public void getPinPolicy() {
        PinPolicyRequest pinPolicyRequest = new PinPolicyRequest(fmcManager, this);
        if (mExecutor == null) {
            pinPolicyRequest.execute();
        } else {
            pinPolicyRequest.executeOnExecutor(mExecutor);
        }
    }

    @Override
    public void onPinPolicyReceived(int minLength, int maxLength) {
        provisioningManager.setPinPolicy(minLength, maxLength);
    }

    @Override
    public void verifyProvisioning(byte[] pin) {
        VerifyPinRequest verifyPinRequest = new VerifyPinRequest(fmcManager, this);
        if (mExecutor == null) {
            verifyPinRequest.execute(pin);
        } else {
            verifyPinRequest.executeOnExecutor(mExecutor, pin);
        }
    }

    @Override
    public void onVerifyPinSuccessful() {
        provisioningManager.endProvisioning();
    }

    @Override
    public void setTokenNotificationId(String tokenNotificationId, byte[] nip) {
        SetTokenRequest setTokenRequest = new SetTokenRequest(fmcManager, this, tokenNotificationId);
        if (mExecutor == null) {
            setTokenRequest.execute(nip);
        } else {
            setTokenRequest.executeOnExecutor(mExecutor, nip);
        }
    }

    @Override
    public void onSetTokenSuccess() {
        provisioningManager.endTokenNotification();
    }

    @Override
    public void onError(Errors error) {
        provisioningManager.onError(error);
    }

    @Override
    public void throwInitException(Exception e) {
        provisioningManager.onError(Errors.cast(e));
    }


}
