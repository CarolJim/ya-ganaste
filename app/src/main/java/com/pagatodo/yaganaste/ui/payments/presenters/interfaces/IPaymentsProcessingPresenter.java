package com.pagatodo.yaganaste.ui.payments.presenters.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;

/**
 * Created by Jordan on 27/04/2017.
 */

public interface IPaymentsProcessingPresenter {
    void sendPayment(MovementsTab tab, Object obj) throws OfflineException;

    void onSuccessPayment(DataSourceResult result);

    void onFailPayment(DataSourceResult error);
}
