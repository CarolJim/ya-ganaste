package com.pagatodo.yaganaste.freja.change.iteractors;

import java.util.concurrent.Executor;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.general.callbacks.PinPolicyCallback;
import com.pagatodo.yaganaste.freja.change.async.ChangePinPolicyRequest;
import com.pagatodo.yaganaste.freja.change.async.ChangePinRequest;
import com.pagatodo.yaganaste.freja.change.managers.ChangePinManager;
import com.pagatodo.yaganaste.freja.general.FmcIteractorImp;

/**
 * @author Juan Guerra on 03/04/2017.
 */

public class ChangePinIteractorImp extends FmcIteractorImp implements ChangePinIteractor, PinPolicyCallback, ChangePinRequest.ChangePinCallback {

    private ChangePinManager changePinManager;
    private Executor mExecutor;

    public ChangePinIteractorImp(ChangePinManager changePinManager, Executor mExecutor) {
        this.changePinManager = changePinManager;
        this.mExecutor = mExecutor;
    }

    @Override
    public void throwInitException(Exception e) {
        changePinManager.handleException(e);
    }

    @Override
    public void getChangePinPolicy() {
        ChangePinPolicyRequest changePinPolicyRequest = new ChangePinPolicyRequest(fmcManager, this);
        if (mExecutor == null){
            changePinPolicyRequest.execute();
        } else {
            changePinPolicyRequest.executeOnExecutor(mExecutor);
        }
    }

    @Override
    public void changePin(byte[] oldPin, byte[] newPin) {
        ChangePinRequest changePinRequest = new ChangePinRequest(fmcManager, this, oldPin, newPin);
        if (mExecutor == null){
            changePinRequest.execute();
        } else {
            changePinRequest.executeOnExecutor(mExecutor);
        }
    }

    @Override
    public void onPinPolicyReceived(int minLength, int maxLength) {
        changePinManager.setChangePinPolicy(minLength, maxLength);
    }

    @Override
    public void onChangeNipSuccessful() {
        changePinManager.endChangePin();
    }

    @Override
    public void handleException(Exception e) {
        changePinManager.handleException(e);
    }

    @Override
    public void onError(Errors error) {
        changePinManager.onError(error);
    }
}
