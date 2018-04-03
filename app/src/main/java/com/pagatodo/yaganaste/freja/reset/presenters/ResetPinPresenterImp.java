package com.pagatodo.yaganaste.freja.reset.presenters;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.GenerarCodigoRecuperacionResponse;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.reset.iteractors.ResetPinIteractor;
import com.pagatodo.yaganaste.freja.reset.iteractors.ResetPinIteractorImp;
import com.pagatodo.yaganaste.freja.reset.managers.IResetNIPView;
import com.pagatodo.yaganaste.freja.reset.managers.ResetPinManager;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;

import java.lang.reflect.Method;
import java.util.concurrent.Executors;

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_PROVISIONING;
import static com.pagatodo.yaganaste.utils.Recursos.OLD_NIP;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;

/**
 * @author Juan Guerra on 31/03/2017.
 */

public class ResetPinPresenterImp implements ResetPinPresenter, ResetPinManager {

    private static final String TAG = ResetPinPresenterImp.class.getSimpleName();

    private ResetPinIteractor resetPinIteractor;
    private IResetNIPView resetNIPView;

    private Method currentMethod;
    private Method initResetNipMethod;
    private Object[] currentMethodParams;
    private Object[] initResetParams;

    private int generalReintent;
    private int individualReintent;

    private final int maxIntents = 5;

    private boolean userFeedback;

    private byte[] rpcCode;
    private byte[] newPin;

    public ResetPinPresenterImp(boolean userFeedback) {
        this.resetPinIteractor = new ResetPinIteractorImp(this, Executors.newFixedThreadPool(1));
        this.resetPinIteractor.init(App.getInstance());
        this.userFeedback = userFeedback;
    }

    @Override
    public void setResetNIPView(IResetNIPView resetNIPView) {
        this.resetNIPView = resetNIPView;
    }

    @Override
    public void doReseting(String newNip) {
        resetNIPView.showLoader("");
        this.newPin = newNip.getBytes();
        this.generalReintent = 0;
        this.individualReintent = 0;
        getResetCode();
    }


    private void getResetCode() {
        if (BuildConfig.DEBUG)
            Log.e(TAG, "Get Reset Code");
        individualReintent++;
        this.currentMethod = new Object() {
        }.getClass().getEnclosingMethod();
        this.currentMethodParams = new Object[]{};
        this.initResetNipMethod = currentMethod;
        this.initResetParams = currentMethodParams;
        resetPinIteractor.getResetCode();
    }

    @Override
    public void setResetCode(String rpc) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, "Set Reset Code");
        this.rpcCode = translateUserPinToFmcFormat(rpc.getBytes());
        individualReintent = 0;
        resetNip();
    }

    private byte[] translateUserPinToFmcFormat(byte[] pin) {
        byte[] transformedUserPin = new byte[pin.length];
        for (int i = 0; i < pin.length; i++) {
            transformedUserPin[i] = (byte) (pin[i] - 48);
        }
        return transformedUserPin;
    }

    private void resetNip() {
        individualReintent++;
        this.currentMethod = new Object() {
        }.getClass().getEnclosingMethod();
        this.currentMethodParams = new Object[]{};
        resetPinIteractor.getResetPinPolicy();
    }

    @Override
    public void setResetPinPolicy(int min, int max) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, "SET Reset NIP Policy");
        individualReintent = 0;
        int size = newPin.length;
        if (min <= size && size <= max) {
            callResetPin();
        } else {
            onError(Errors.BAD_CHANGE_POLICY);
        }
    }

    private void callResetPin() {
        individualReintent++;
        this.currentMethod = new Object() {
        }.getClass().getEnclosingMethod();
        this.currentMethodParams = new Object[]{};
        resetPinIteractor.resetPin(rpcCode, newPin);
    }

    @Override
    public void endResetPin() {
        SingletonUser.getInstance().setNeedsReset(false);
        App.getInstance().getPrefs().saveData(OLD_NIP, App.getInstance().getPrefs().loadData(SHA_256_FREJA));
        resetNIPView.hideLoader();
        resetNIPView.finishReseting();
    }


    @Override
    public void onError(final Errors error) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, "onErrorValidateService: " + error.getMessage() + "\n Code: " + String.valueOf(error.getErrorCode()));

        if (individualReintent < 3) { //Si el reintento individual aun no excede el maximo
            if (userFeedback) {
                handleErrorUserFeedback(error);
                return;
            } else {
                if (error.allowsReintent()) {
                    handleError(error);
                    return;
                }
            }
        }


        if (generalReintent < maxIntents - 1) { //Si el numero de intentos general aun no excede
            generalReintent++;
            individualReintent = 0;
            if (userFeedback) {
                currentMethod = initResetNipMethod;
                currentMethodParams = initResetParams;
                error.setAllowReintent(true);
                handleErrorUserFeedback(error);
                return;
            } else {
                getResetCode();
                return;
            }
        }

        resetNIPView.hideLoader();
        App.getInstance().getPrefs().saveDataBool(HAS_PROVISIONING, false);
        resetNIPView.onResetingFailed();
    }

    private void handleError(final Errors error) {
        currentMethod.setAccessible(true);
        try {
            currentMethod.invoke(ResetPinPresenterImp.this, currentMethodParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleErrorUserFeedback(final Errors error) {
        DialogDoubleActions actions = new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                if (error.allowsReintent()) {
                    currentMethod.setAccessible(true);
                    try {
                        currentMethod.invoke(ResetPinPresenterImp.this, currentMethodParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } //Falta else avidsando a la vista que no se pudo actualizar
            }

            @Override
            public void actionCancel(Object... params) {
                //Avisar a la vista que no se pudo actualizar
            }
        };

        ErrorObject.Builder builder = new ErrorObject.Builder().setMessage(error.getMessage())
                .setHasCancel(error.allowsReintent()).setActions(actions);

        resetNIPView.showErrorReset(builder.build());
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        if (result.getData() != null) {
            GenerarCodigoRecuperacionResponse response = (GenerarCodigoRecuperacionResponse) result.getData();
            if (response.getCodigoRespuesta() == CODE_OK) {
                setResetCode(response.getData().getCodigoRecuperacion());
                return;
            }
        }
        onError(Errors.UNEXPECTED);
    }

    @Override
    public void onFailed(DataSourceResult error) {
        onError(Errors.UNEXPECTED);
    }
}