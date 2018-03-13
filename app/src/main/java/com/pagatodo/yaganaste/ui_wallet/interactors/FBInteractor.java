package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.RegisterFBTokenRequest;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IFBInteractor;

/**
 * Created by FranciscoManzo on 17/01/2018.
 * Interactor esclusivo para eventos de FBMessage, es un MVP con el modelo de
 * AL
 * Se comunica con el ApiAdtvo, recibe los elementos con IRequestResult y usa los una interfase
 * FBInteractor.IFBInteractorListener para contestar de manera directa, sin tener que hacerlo con la
 * interfase del Presenter
 */

public class FBInteractor implements IFBInteractor, IRequestResult {

    IFBInteractorListener listener;

    @Override
    public void registerFBToken(IFBInteractorListener listener, String tokenFB) {
        this.listener = listener;
        try {

            RegisterFBTokenRequest request = new RegisterFBTokenRequest(
                    SingletonUser.getInstance().getDataUser().getUsuario().getIdUsuario(),
                    tokenFB);
            ApiAdtvo.registerFBToken(request, this);
        } catch (OfflineException e) {

        }


    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        listener.onSuccess(dataSourceResult);
    }

    @Override
    public void onFailed(DataSourceResult error) {
        listener.onError(error);
    }
}
