package com.pagatodo.yaganaste.freja.reset.presenters;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.GenerarCodigoRecuperacionResponse;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.reset.iteractors.ResetPinIteractor;
import com.pagatodo.yaganaste.freja.reset.iteractors.ResetPinIteractorImp;
import com.pagatodo.yaganaste.freja.reset.managers.IResetNIPView;
import com.pagatodo.yaganaste.freja.reset.managers.ResetPinManager;

import java.util.concurrent.Executors;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * @author Juan Guerra on 31/03/2017.
 */

public class ResetPinPresenterAbs implements ResetPinPresenter, ResetPinManager {

    private ResetPinIteractor resetPinIteractor;
    private IResetNIPView resetNIPView;

    private boolean userFeedback;

    private byte[] rpcCode;
    private byte[] newPin;

    public ResetPinPresenterAbs(IResetNIPView resetNIPView, boolean userFeedback) {
        this.resetPinIteractor = new ResetPinIteractorImp(this, Executors.newFixedThreadPool(1));
        this.resetPinIteractor.init(App.getInstance());
        this.resetNIPView = resetNIPView;
        this.userFeedback = userFeedback;
    }

    @Override
    public void doReseting(String newNip) {
        this.newPin = newNip.getBytes();
        getResetCode();
    }


    private void getResetCode() {
        resetPinIteractor.getResetCode();
    }

    @Override
    public void setResetCode(String rpc) {
        this.rpcCode = rpc.getBytes();
        resetNip();
    }

    public void resetNip() {
        resetPinIteractor.getResetPinPolicy();
    }

    @Override
    public void setResetPinPolicy(int min, int max) {
        int size = newPin.length;
        if (min <= size && size <= max) {
            resetPinIteractor.resetPin(rpcCode, newPin);
        } else {
            onError(Errors.BAD_CHANGE_POLICY);
        }
    }

    @Override
    public void endResetPin() {
        resetNIPView.finishReseting();
    }


    @Override
    public void onError(Errors error) {

    }

    @Override
    public void onSuccess(DataSourceResult result) {
        if (result.getData() != null) {
            GenerarCodigoRecuperacionResponse response = (GenerarCodigoRecuperacionResponse) result.getData();
            if (response.getCodigoRespuesta() == CODE_OK) {
                setResetCode(response.getData().getCodigoRecuperacion());
            }
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        onError(Errors.UNEXPECTED);
    }
}