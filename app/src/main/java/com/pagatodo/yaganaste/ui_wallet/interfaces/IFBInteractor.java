package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;

/**
 * Created by FranciscoManzo on 17/01/2018.
 */

public interface IFBInteractor {

    void registerFBToken(IFBInteractorListener listener, String tokenFB);

    interface IFBInteractorListener {
        void onError(DataSourceResult error);

        void onSuccess(DataSourceResult dataSourceResult);
    }
}
