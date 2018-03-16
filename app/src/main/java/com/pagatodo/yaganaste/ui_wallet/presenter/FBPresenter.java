package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.ui_wallet.interactors.FBInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IFBInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IFBPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IFBView;
import com.pagatodo.yaganaste.utils.StringConstants;

import static com.pagatodo.yaganaste.utils.StringConstants.TOKEN_FIREBASE_SUCCESS;

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
    public static final String TAG2 = "RegistroToken";

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
        //  Log.d(TAG2, "onSuccess " + TOKEN_FIREBASE_SUCCESS + " " + dataSourceResult.getData());
        prefs.saveData(StringConstants.TOKEN_FIREBASE_STATUS, TOKEN_FIREBASE_SUCCESS);
    }

    @Override
    public void onError(DataSourceResult error) {
        // Log.d(TAG2, "onError " + error.getData());
        prefs.saveData(StringConstants.TOKEN_FIREBASE_STATUS, StringConstants.TOKEN_FIREBASE_FAIL);
    }




}
