package com.pagatodo.yaganaste.freja.change.presenters;

import android.os.Handler;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.IChangeNipView;

import java.lang.reflect.Method;

import static com.pagatodo.yaganaste.utils.Recursos.OLD_NIP;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_FREJA;

/**
 * @author Juan on 17/08/2017.
 */

public class ChangeNipPresenterImp extends ChangePinPresenterAbs {

    private static final String TAG = ChangeNipPresenterImp.class.getSimpleName();

    private IChangeNipView mIChangeNipView;

    private Method currentMethod;
    private Method initChangeNipMethod;
    private Object[] currentMethodParams;
    private Object[] initChangeParams;

    private int generalReintent;
    private int individualReintent;

    private final int maxIntents = 5;


    public ChangeNipPresenterImp() {
        super(App.getInstance());
    }

    public void setIChangeNipView(IChangeNipView view) {
        this.mIChangeNipView = view;
    }

    private void reset() {
        this.generalReintent = 0;
        this.individualReintent = 0;
    }

    //Debe recibir los nips en sha256
    public void doChangeNip(String oldOne, String newOne) {
        App.getInstance().getPrefs().saveData(SHA_256_FREJA, newOne);
        reset();
        mIChangeNipView.showLoader("");
        changeNip(oldOne, newOne);
    }

    /**
     * Este metodo es el que se encarga de pedir las politicas de cambio de nip
     *
     * @param oldPin
     * @param newPin
     */
    @Override
    public void changeNip(String oldPin, String newPin) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, "Get Change Nip Policy");
        individualReintent++;
        this.currentMethod = new Object() {
        }.getClass().getEnclosingMethod();
        this.currentMethodParams = new Object[]{oldPin, newPin};
        this.initChangeNipMethod = new Object() {
        }.getClass().getEnclosingMethod();
        this.initChangeParams = new Object[]{oldPin, newPin};
        super.changeNip(oldPin, newPin);
    }

    /**
     * Metodo que se llama cuando responde el metodo de politicas para cambiar nip, estge mismo
     * metodo llama al cambio de NIP
     *
     * @param min
     * @param max
     */
    @Override
    public void setChangePinPolicy(int min, int max) {
        individualReintent = 0;
        callChangeNip(min, max);
    }

    public void callChangeNip(int min, int max) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, "Change NIP");
        individualReintent++;
        this.currentMethod = new Object() {
        }.getClass().getEnclosingMethod();
        this.currentMethodParams = new Object[]{min, max};
        super.setChangePinPolicy(min, max);
    }


    @Override
    public void endChangePin() {
        App.getInstance().getPrefs().saveData(OLD_NIP, App.getInstance().getPrefs().loadData(SHA_256_FREJA));
        SingletonUser.getInstance().setNeedsReset(false);
        mIChangeNipView.hideLoader();
        mIChangeNipView.onFrejaNipChanged();
    }


    @Override
    public void onError(final Errors error) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, "onErrorValidateService: " + error.getMessage() + "\n Code: " + String.valueOf(error.getErrorCode()));

        if (individualReintent < 3 && error.allowsReintent()) { //Si el reintento individual aun no excede el maximo
            handleError(error);
            return;
        }

        if (generalReintent < maxIntents - 1) { //Si el numero de intentos general aun no excede
            generalReintent++;
            individualReintent = 0;
            currentMethod = initChangeNipMethod;
            currentMethodParams = initChangeParams;
            error.setAllowReintent(true);
            handleError(error);
            return;
        }
        mIChangeNipView.hideLoader();
        mIChangeNipView.onFrejaNipFailed();
    }

    private void handleError(final Errors error) {
        //En este caso no se maneja feedback del usuario, si se requiere descomentar el resto de codigo
        //e implementar el resto del codigo

        currentMethod.setAccessible(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    currentMethod.invoke(ChangeNipPresenterImp.this, currentMethodParams);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 6000);


        /*DialogDoubleActions actions = new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                if (error.allowsReintent()) {
                    currentMethod.setAccessible(true);
                    try {
                        currentMethod.invoke(ChangeNipPresenterImp.this, currentMethodParams);
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

        ErrorObject.Builder Builder = new ErrorObject.Builder().setMessage(error.getMessage())
                .setHasCancel(error.allowsReintent()).setActions(actions);

        mIChangeNipView.showErrorNip(Builder.build());*/
    }

}
