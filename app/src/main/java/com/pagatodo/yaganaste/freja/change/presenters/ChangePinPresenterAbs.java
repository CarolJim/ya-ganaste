package com.pagatodo.yaganaste.freja.change.presenters;

import android.content.Context;

import java.util.concurrent.Executors;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.change.iteractors.ChangePinIteractor;
import com.pagatodo.yaganaste.freja.change.iteractors.ChangePinIteractorImp;
import com.pagatodo.yaganaste.freja.change.managers.ChangePinManager;

/**
 * @author Juan Guerra on 31/03/2017.
 */

public abstract class ChangePinPresenterAbs implements ChangePinPresenter, ChangePinManager {

    private ChangePinIteractor changePinIteractor;
    private byte[] oldPin;
    private byte[] newPin;

    public ChangePinPresenterAbs(Context context) {
        this.changePinIteractor = new ChangePinIteractorImp(this, Executors.newFixedThreadPool(1));
        this.changePinIteractor.init(context);
    }

    @Override
    public void changeNip(String oldPin, String newPin) {
        this.oldPin = oldPin.getBytes();
        this.newPin = newPin.getBytes();
        changePinIteractor.getChangePinPolicy();
    }

    @Override
    public void setChangePinPolicy(int min, int max) {
        int size = newPin.length;
        if (min <= size && size<= max) {
            changePinIteractor.changePin(oldPin, newPin);
        } else {
            onError(Errors.BAD_CHANGE_POLICY);
        }
    }

}