package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ListaNotificationRequest;
import com.pagatodo.yaganaste.ui_wallet.presenter.UserNotificationPresenter;

/**
 * Created by FranciscoManzo on 13/02/2018.
 */

public interface IUserNotificationInteractor {

    void getFirstDataToInteractor(UserNotificationPresenter userNotificationPresenter, ListaNotificationRequest listaNotificationRequest);

    void getNextDataToInteractor(ListaNotificationRequest listaNotificationRequest);

    interface OnUserNotifListener{
        void onSuccess(DataSourceResult dataSourceResult);
        void onError(DataSourceResult error);
    }
}
