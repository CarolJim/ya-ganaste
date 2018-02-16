package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ListaNotificationRequest;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IUserNotificationInteractor;
import com.pagatodo.yaganaste.ui_wallet.presenter.UserNotificationPresenter;

/**
 * Created by FranciscoManzo on 13/02/2018.
 */

public class UserNotificationInteractor implements IUserNotificationInteractor, IRequestResult {

    UserNotificationPresenter mPresenter;

    /**
     *  Obtenemos la 1era parte de las notificaciones, enviamos el request al API
     * @param mPresenter
     * @param notifRequest
     */
    @Override
    public void getFirstDataToInteractor(UserNotificationPresenter mPresenter,
                                         ListaNotificationRequest notifRequest) {
        this.mPresenter = mPresenter;
        try {
            ApiAdtvo.getFirstDataNotification(this, notifRequest);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtenemos la siguiente parte de las notificaciones, enviamos el request del API
     * @param notifRequest
     */
    @Override
    public void getNextDataToInteractor(ListaNotificationRequest notifRequest) {
        try {
            ApiAdtvo.getNextDataNotification(this, notifRequest);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exito en la peticion del API
     * @param dataSourceResult
     */
    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
       // Log.d("NotifInteractor", "onSucess " + dataSourceResult);
        mPresenter.onSuccess(dataSourceResult);
    }

    /**
     * Fail en la peticion del API
     * @param error
     */
    @Override
    public void onFailed(DataSourceResult error) {
        // Log.d("NotifInteractor", "onFailed " + error);
        mPresenter.onError(error);
    }
}
