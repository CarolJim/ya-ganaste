package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;

/**
 * Created by FranciscoManzo on 12/02/2018.
 */

public interface INotificationHistory {
    void onSuccessFirstData(DataSourceResult dataSourceResult);

    void onErrorListNotif(DataSourceResult error);

    void loadNextDataToView(int idNotificacion);

    void onSuccessNextData(DataSourceResult dataSourceResult);
}
