package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ListaNotificationRequest;
import com.pagatodo.yaganaste.interfaces.INotificationHistory;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IUserNotificationInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IUserNotificationPresenter;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.GET_FIRST_DATA_NOTIFICATION;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.GET_NEXT_DATA_NOTIFICATION;

/**
 * Created by FranciscoManzo on 13/02/2018.
 */

public class UserNotificationPresenter implements IUserNotificationPresenter,
        IUserNotificationInteractor.OnUserNotifListener {

    INotificationHistory mView;
    IUserNotificationInteractor mInteractor;

    public UserNotificationPresenter(INotificationHistory mView,
                                     IUserNotificationInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    /**
     * Obtenemos la 1era parte de las notificaciones, enviamos el request al Interactor
     */
    @Override
    public void getFirstDataToPresenter() {
        ListaNotificationRequest listaNotificationRequest = new ListaNotificationRequest(4053, 0);
        mInteractor.getFirstDataToInteractor(this, listaNotificationRequest);
    }

    /**
     * Obtenemos el siguiente bloque de datos, envuamos el request al Interactor
     * @param idNotificacion
     */
    @Override
    public void getNextDataToPresenter(int idNotificacion) {
        ListaNotificationRequest listaNotificationRequest = new ListaNotificationRequest(4053, idNotificacion);
        mInteractor.getNextDataToInteractor(listaNotificationRequest);
    }

    /**
     * Manejamos la respuesta del API, en el 1er camino tenemos la primera parte de los regustros
     * en el 2do se repite el actualizar la lista con respecto al ultimo elemento
     * @param dataSourceResult
     */
    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        //  Toast.makeText(App.getContext(), "onSuccess", Toast.LENGTH_SHORT).show();
        if (dataSourceResult.getWebService().equals(GET_FIRST_DATA_NOTIFICATION)) {
            mView.onSuccessFirstData(dataSourceResult);
        } else if (dataSourceResult.getWebService().equals(GET_NEXT_DATA_NOTIFICATION)) {
            mView.onSuccessNextData(dataSourceResult);
        }
    }

    @Override
    public void onError(DataSourceResult error) {
        //Toast.makeText(App.getContext(), "onError", Toast.LENGTH_SHORT).show();
        mView.onErrorListNotif(error);
    }

}
