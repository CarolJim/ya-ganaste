package com.pagatodo.yaganaste.ui.payments.managers;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.interfaces.IProgressView;

/**
 * Created by Jordan on 27/04/2017.
 */

public interface PaymentsProcessingManager extends IProgressView<ErrorObject> {

    void onSuccessPaymentRespone(DataSourceResult result);

    void onFailPaimentResponse(DataSourceResult error);

}
