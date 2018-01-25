package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.ui_wallet.interactors.FBInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IFBInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IFBPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IFBView;
import com.pagatodo.yaganaste.utils.StringConstants;

/**
 * Created by FranciscoManzo on 17/01/2018.
 * Presenter exclusivo para eventos relacionados con FBMessage
 * Usamos un modelo de MVP de AL, creando el interactor desde la vista y solo recibiendo
 * los eventos desde el presenter
 */

public class FBPresenter implements IFBPresenter, IFBInteractor.IFBInteractorListener {
    IFBView mView;
    IFBInteractor fbInteractor;
    private Preferencias prefs = App.getInstance().getPrefs();

    public FBPresenter(IFBView mView, FBInteractor fbInteractor) {
        this.mView = mView;
        this.fbInteractor = fbInteractor;
    }

    @Override
    public void registerFirebaseToken(String tokenFB) {
        fbInteractor.registerFBToken(this, tokenFB);
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        Log.d("FBPresenter", "Cool");
        prefs.saveData(StringConstants.TOKEN_FIREBASE_STATUS, StringConstants.TOKEN_FIREBASE_SUCCESS);
    }

    @Override
    public void onError(DataSourceResult error) {
        Log.d("FBPresenter", "Not Cool");
        prefs.saveData(StringConstants.TOKEN_FIREBASE_STATUS, StringConstants.TOKEN_FIREBASE_FAIL);
    }




}
