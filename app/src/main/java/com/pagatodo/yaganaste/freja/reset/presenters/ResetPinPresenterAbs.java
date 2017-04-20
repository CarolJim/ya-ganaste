package com.pagatodo.yaganaste.freja.reset.presenters;

import android.content.Context;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.reset.iteractors.ResetPinIteractor;
import com.pagatodo.yaganaste.freja.reset.iteractors.ResetPinIteractorImp;
import com.pagatodo.yaganaste.freja.reset.managers.ResetPinManager;

import java.util.concurrent.Executors;

/**
 * @author Juan Guerra on 31/03/2017.
 */

public abstract class ResetPinPresenterAbs implements ResetPinPresenter, ResetPinManager {

    private ResetPinIteractor resetPinIteractor;
    private byte[] rpcCode;
    private byte[] newPin;

    public ResetPinPresenterAbs(Context context) {
        this.resetPinIteractor = new ResetPinIteractorImp(this, Executors.newFixedThreadPool(1));
        this.resetPinIteractor.init(context);
    }

    @Override
    public void resetNip(String rpcCode, String newPin) {
        this.rpcCode = rpcCode.getBytes();
        this.newPin = newPin.getBytes();
        resetPinIteractor.getResetPinPolicy();
    }

    @Override
    public void setResetPinPolicy(int min, int max) {
        int size = newPin.length;
        if (min <= size && size<= max) {
            resetPinIteractor.resetPin(rpcCode, newPin);
        } else {
            onError(Errors.BAD_CHANGE_POLICY);
        }
    }

}