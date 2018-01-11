package com.pagatodo.yaganaste.ui.payments.presenters.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.exceptions.OfflineException;

/**
 * Created by Jordan on 27/04/2017.
 */

public interface IPaymentsProcessingPresenter {
    void sendPayment(int typeOperation, Object obj) throws OfflineException;

    void onSuccessPayment(DataSourceResult result);

    void onFailPayment(DataSourceResult error);
}
