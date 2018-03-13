package com.pagatodo.yaganaste.freja.reset.iteractors;

import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.general.FmcIteractorImp;
import com.pagatodo.yaganaste.freja.general.callbacks.PinPolicyCallback;
import com.pagatodo.yaganaste.freja.reset.async.ResetPinPolicyRequest;
import com.pagatodo.yaganaste.freja.reset.async.ResetPinRequest;
import com.pagatodo.yaganaste.freja.reset.managers.ResetPinManager;
import com.pagatodo.yaganaste.net.ApiAdtvo;

import java.util.concurrent.Executor;

/**
 * @author Juan Guerra on 03/04/2017.
 */

public class ResetPinIteractorImp extends FmcIteractorImp implements ResetPinIteractor, PinPolicyCallback, ResetPinRequest.ResetPinCallback {

    private ResetPinManager resetPinManager;
    private Executor mExecutor;

    public ResetPinIteractorImp(ResetPinManager resetPinManager, Executor mExecutor) {
        this.resetPinManager = resetPinManager;
        this.mExecutor = mExecutor;
    }

    @Override
    public void throwInitException(Exception e) {
        resetPinManager.onError(Errors.cast(e));
    }

    @Override
    public void getResetPinPolicy() {
        ResetPinPolicyRequest resetPinPolicyRequest = new ResetPinPolicyRequest(fmcManager, this);
        if (mExecutor == null) {
            resetPinPolicyRequest.execute();
        } else {
            resetPinPolicyRequest.executeOnExecutor(mExecutor);
        }
    }

    @Override
    public void resetPin(byte[] rpcCode, byte[] newPin) {
        ResetPinRequest resetPinRequest = new ResetPinRequest(fmcManager, this, rpcCode, newPin);
        if (mExecutor == null) {
            resetPinRequest.execute();
        } else {
            resetPinRequest.executeOnExecutor(mExecutor);
        }
    }

    @Override
    public void onPinPolicyReceived(int minLength, int maxLength) {
        resetPinManager.setResetPinPolicy(minLength, maxLength);
    }

    @Override
    public void onResetNipSuccessful() {
        resetPinManager.endResetPin();
    }

    @Override
    public void onError(Errors error) {
        resetPinManager.onError(error);
    }

    @Override
    public void getResetCode() {
        try {
            ApiAdtvo.generarRPC(resetPinManager);
        } catch (OfflineException e) {
            resetPinManager.onError(Errors.E2);
        }
    }
}
